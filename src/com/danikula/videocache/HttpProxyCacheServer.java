package com.danikula.videocache;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.danikula.videocache.file.DiskUsage;
import com.danikula.videocache.file.FileNameGenerator;
import com.danikula.videocache.file.Md5FileNameGenerator;
import com.danikula.videocache.file.TotalCountLruDiskUsage;
import com.danikula.videocache.file.TotalSizeLruDiskUsage;
import com.danikula.videocache.sourcestorage.SourceInfoStorage;
import com.danikula.videocache.sourcestorage.SourceInfoStorageFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import static com.danikula.videocache.Preconditions.checkAllNotNull;
import static com.danikula.videocache.Preconditions.checkNotNull;
import static com.danikula.videocache.ProxyCacheUtils.LOG_TAG;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Simple lightweight proxy server with file caching support that handles HTTP requests.
 * Typical usage:
 * <pre><code>
 * public onCreate(Bundle state) {
 *      super.onCreate(state);
 * <p/>
 *      HttpProxyCacheServer proxy = getProxy();
 *      String proxyUrl = proxy.getProxyUrl(VIDEO_URL);
 *      videoView.setVideoPath(proxyUrl);
 * }
 * <p/>
 * private HttpProxyCacheServer getProxy() {
 * // should return single instance of HttpProxyCacheServer shared for whole app.
 * }
 * <code/></pre>
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
public class HttpProxyCacheServer {

    private static final String PROXY_HOST = "127.0.0.1";
    private static final String PING_REQUEST = "ping";
    private static final String PING_RESPONSE = "ping ok";

    private final Object clientsLock = new Object();
    private final ExecutorService socketProcessor = Executors.newFixedThreadPool(8);
    private final Map<String, HttpProxyCacheServerClients> clientsMap = new ConcurrentHashMap<String, HttpProxyCacheServerClients>();
    private final ServerSocket serverSocket;
    private final int port;
    private final Thread waitConnectionThread;
    private final Config config;
    private boolean pinged;
    private static String mVideoName;

    public HttpProxyCacheServer(Context context,String videoName) {
        this(new Builder(context,videoName).buildConfig(videoName), videoName);
    }

    private HttpProxyCacheServer(Config config,String videoName) {
    	mVideoName=videoName;
        this.config = checkNotNull(config);
        try {
            InetAddress inetAddress = InetAddress.getByName(PROXY_HOST);
            this.serverSocket = new ServerSocket(0, 8, inetAddress);
            this.port = serverSocket.getLocalPort();
            CountDownLatch startSignal = new CountDownLatch(1);
            this.waitConnectionThread = new Thread(new WaitRequestsRunnable(startSignal));
            this.waitConnectionThread.start();
            startSignal.await(); // freeze thread, wait for server starts
            Log.i(LOG_TAG, "Proxy cache server started. Ping it...");
            makeSureServerWorks();
        } catch (Exception e) {
            socketProcessor.shutdown();
            throw new IllegalStateException("Error starting local proxy server", e);
        }
    }

    private void makeSureServerWorks() {
        int maxPingAttempts = 3;
        int delay = 300;
        int pingAttempts = 0;
        while (pingAttempts < maxPingAttempts) {
            try {
                Future<Boolean> pingFuture = socketProcessor.submit(new PingCallable());
                this.pinged = pingFuture.get(delay, MILLISECONDS);
                if (this.pinged) {
                    return;
                }
                SystemClock.sleep(delay);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error pinging server [attempt: " + pingAttempts + ", timeout: " + delay + "]. ", e);
            }
            pingAttempts++;
            delay *= 2;
        }
        Log.e(LOG_TAG, "Shutdown server鈥� Error pinging server [attempts: " + pingAttempts + ", max timeout: " + delay / 2 + "]. " +
                "If you see this message, please, email me danikula@gmail.com");
        shutdown();
    }

    private boolean pingServer() throws ProxyCacheException {
        String pingUrl = appendToProxyUrl(PING_REQUEST);
        HttpUrlSource source = new HttpUrlSource(pingUrl);
        try {
            byte[] expectedResponse = PING_RESPONSE.getBytes();
            source.open(0);
            byte[] response = new byte[expectedResponse.length];
            source.read(response);
            boolean pingOk = Arrays.equals(expectedResponse, response);
            Log.d(LOG_TAG, "Ping response: `" + new String(response) + "`, pinged? " + pingOk);
            return pingOk;
        } catch (ProxyCacheException e) {
            Log.e(LOG_TAG, "Error reading ping response", e);
            return false;
        } finally {
            source.close();
        }
    }

    public String getProxyUrl(String url) {
        if (!pinged) {
            Log.e(LOG_TAG, "Proxy server isn't pinged. Caching doesn't work. If you see this message, please, email me danikula@gmail.com");
        }
        return pinged ? appendToProxyUrl(url) : url;
    }

    private String appendToProxyUrl(String url) {
        return String.format(Locale.US, "http://%s:%d/%s", PROXY_HOST, port, ProxyCacheUtils.encode(url));
    }

    public void registerCacheListener(CacheListener cacheListener, String url) {
        checkAllNotNull(cacheListener, url);
        synchronized (clientsLock) {
            try {
                getClients(url).registerCacheListener(cacheListener);
            } catch (ProxyCacheException e) {
                Log.d(LOG_TAG, "Error registering cache listener", e);
            }
        }
    }

    public void unregisterCacheListener(CacheListener cacheListener, String url) {
        checkAllNotNull(cacheListener, url);
        synchronized (clientsLock) {
            try {
                getClients(url).unregisterCacheListener(cacheListener);
            } catch (ProxyCacheException e) {
                Log.d(LOG_TAG, "Error registering cache listener", e);
            }
        }
    }

    public void unregisterCacheListener(CacheListener cacheListener) {
        checkNotNull(cacheListener);
        synchronized (clientsLock) {
            for (HttpProxyCacheServerClients clients : clientsMap.values()) {
                clients.unregisterCacheListener(cacheListener);
            }
        }
    }

    /**
     * Checks is cache contains fully cached file for particular url.
     *
     * @param url an url cache file will be checked for.
     * @return {@code true} if cache contains fully cached file for passed in parameters url.
     */
    public File isCached(String url,String videoName) {
        checkNotNull(url, "Url can't be null!");
        File cacheDir = config.cacheRoot;
        String fileName = config.fileNameGenerator.generate(url,videoName);
        File cacheFile = new File(cacheDir, fileName);
//        return cacheFile.exists();
        return cacheFile;
    }

    public void shutdown() {
        Log.i(LOG_TAG, "Shutdown proxy server");

        shutdownClients();

        config.sourceInfoStorage.release();

        waitConnectionThread.interrupt();
        try {
            if (!serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            onError(new ProxyCacheException("Error shutting down proxy server", e));
        }
    }

    private void shutdownClients() {
        synchronized (clientsLock) {
            for (HttpProxyCacheServerClients clients : clientsMap.values()) {
                clients.shutdown();
            }
            clientsMap.clear();
        }
    }

    private void waitForRequest() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Socket socket = serverSocket.accept();
                Log.d(LOG_TAG, "Accept new socket " + socket);
                socketProcessor.submit(new SocketProcessorRunnable(socket));
            }
        } catch (IOException e) {
            onError(new ProxyCacheException("Error during waiting connection", e));
        }
    }

    private void processSocket(Socket socket) {
        try {
            GetRequest request = GetRequest.read(socket.getInputStream());
            Log.i(LOG_TAG, "Request to cache proxy:" + request);
            String url = ProxyCacheUtils.decode(request.uri);
            if (PING_REQUEST.equals(url)) {
                responseToPing(socket);
            } else {
                HttpProxyCacheServerClients clients = getClients(url);
                clients.processRequest(request, socket);
            }
        } catch (SocketException e) {
            // There is no way to determine that client closed connection http://stackoverflow.com/a/10241044/999458
            // So just to prevent log flooding don't log stacktrace
            Log.d(LOG_TAG, "Closing socket鈥� Socket is closed by client.");
        } catch (Exception e) {
            onError(new ProxyCacheException("Error processing request", e));
        } finally {
            releaseSocket(socket);
            Log.d(LOG_TAG, "Opened connections: " + getClientsCount());
        }
    }

    private void responseToPing(Socket socket) throws IOException {
        OutputStream out = socket.getOutputStream();
        out.write("HTTP/1.1 200 OK\n\n".getBytes());
        out.write(PING_RESPONSE.getBytes());
    }

    private HttpProxyCacheServerClients getClients(String url) throws ProxyCacheException {
        synchronized (clientsLock) {
            HttpProxyCacheServerClients clients = clientsMap.get(url);
            if (clients == null) {
                clients = new HttpProxyCacheServerClients(url, config);
                clientsMap.put(url, clients);
            }
            return clients;
        }
    }

    private int getClientsCount() {
        synchronized (clientsLock) {
            int count = 0;
            for (HttpProxyCacheServerClients clients : clientsMap.values()) {
                count += clients.getClientsCount();
            }
            return count;
        }
    }

    private void releaseSocket(Socket socket) {
        closeSocketInput(socket);
        closeSocketOutput(socket);
        closeSocket(socket);
    }

    private void closeSocketInput(Socket socket) {
        try {
            if (!socket.isInputShutdown()) {
                socket.shutdownInput();
            }
        } catch (SocketException e) {
            // There is no way to determine that client closed connection http://stackoverflow.com/a/10241044/999458
            // So just to prevent log flooding don't log stacktrace
            Log.d(LOG_TAG, "Releasing input stream鈥� Socket is closed by client.");
        } catch (IOException e) {
            onError(new ProxyCacheException("Error closing socket input stream", e));
        }
    }

    private void closeSocketOutput(Socket socket) {
        try {
            if (!socket.isOutputShutdown()) {
                socket.shutdownOutput();
            }
        } catch (IOException e) {
            onError(new ProxyCacheException("Error closing socket output stream", e));
        }
    }

    private void closeSocket(Socket socket) {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            onError(new ProxyCacheException("Error closing socket", e));
        }
    }

    private void onError(Throwable e) {
        Log.e(LOG_TAG, "HttpProxyCacheServer error", e);
    }

    private final class WaitRequestsRunnable implements Runnable {

        private final CountDownLatch startSignal;

        public WaitRequestsRunnable(CountDownLatch startSignal) {
            this.startSignal = startSignal;
        }

        @Override
        public void run() {
            startSignal.countDown();
            waitForRequest();
        }
    }

    private final class SocketProcessorRunnable implements Runnable {

        private final Socket socket;

        public SocketProcessorRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            processSocket(socket);
        }
    }

    private class PingCallable implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            return pingServer();
        }
    }

    /**
     * Builder for {@link HttpProxyCacheServer}.
     */
    public static final class Builder {

        private static final long DEFAULT_MAX_SIZE = 512 * 1024 * 1024;

        private File cacheRoot;
        private FileNameGenerator fileNameGenerator;
        private DiskUsage diskUsage;
        private SourceInfoStorage sourceInfoStorage;
        private String videoName;
        public Builder(Context context,String videoName) {
        	this.videoName=videoName;
            this.sourceInfoStorage = SourceInfoStorageFactory.newSourceInfoStorage(context);
            this.cacheRoot = StorageUtils.getIndividualCacheDirectory(context);
            this.diskUsage = new TotalSizeLruDiskUsage(DEFAULT_MAX_SIZE);
            this.fileNameGenerator = new Md5FileNameGenerator(videoName);
        }

        /**
         * Overrides default cache folder to be used for caching files.
         * <p/>
         * By default AndroidVideoCache uses
         * '/Android/data/[app_package_name]/cache/video-cache/' if card is mounted and app has appropriate permission
         * or 'video-cache' subdirectory in default application's cache directory otherwise.
         * <p/>
         * <b>Note</b> directory must be used <b>only</b> for AndroidVideoCache files.
         *
         * @param file a cache directory, can't be null.
         * @return a builder.
         */
        public Builder cacheDirectory(File file) {
            this.cacheRoot = checkNotNull(file);
            return this;
        }

        /**
         * Overrides default cache file name generator {@link Md5FileNameGenerator} .
         *
         * @param fileNameGenerator a new file name generator.
         * @return a builder.
         */
        public Builder fileNameGenerator(FileNameGenerator fileNameGenerator) {
            this.fileNameGenerator = checkNotNull(fileNameGenerator);
            return this;
        }

        /**
         * Sets max cache size in bytes.
         * All files that exceeds limit will be deleted using LRU strategy.
         * Default value is 512 Mb.
         * <p/>
         * Note this method overrides result of calling {@link #maxCacheFilesCount(int)}
         *
         * @param maxSize max cache size in bytes.
         * @return a builder.
         */
        public Builder maxCacheSize(long maxSize) {
            this.diskUsage = new TotalSizeLruDiskUsage(maxSize);
            return this;
        }

        /**
         * Sets max cache files count.
         * All files that exceeds limit will be deleted using LRU strategy.
         * <p/>
         * Note this method overrides result of calling {@link #maxCacheSize(long)}
         *
         * @param count max cache files count.
         * @return a builder.
         */
        public Builder maxCacheFilesCount(int count) {
            this.diskUsage = new TotalCountLruDiskUsage(count);
            return this;
        }

        /**
         * Builds new instance of {@link HttpProxyCacheServer}.
         *
         * @return proxy cache. Only single instance should be used across whole app.
         */
        public HttpProxyCacheServer build(String videoName) {
            Config config = buildConfig(videoName);
            return new HttpProxyCacheServer(config,videoName);
        }

        private Config buildConfig(String videoName) {
            return new Config(videoName,cacheRoot, fileNameGenerator, diskUsage, sourceInfoStorage);
        }

    }
}
