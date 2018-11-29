package com.growatt.shinephone.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.danikula.videocache.HttpProxyCacheServer;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.Constant;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.xsj.crasheye.Crasheye;

import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class ShineApplication extends Application {
//	public ShineApplication() {
//		super(7, "com.growatt.shinephone.activity.SampleApplication", "com.tencent.tinker.loader.TinkerLoader", false);
//	}
	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	public static  String Path = Environment.getExternalStorageDirectory()+"/pic";//
	public static  String VIDEO_PATH = Environment.getExternalStorageDirectory()+"/videocash/";
	public static  String IMAGE_FILE_LOCATION = "pic1.png";
	public static  String plantimage = Path + "/plant"+System.currentTimeMillis()+".png";
	public static Context context;
	//运用list来保存们每一个activity是关键
	private List<Activity> mList = new ArrayList<Activity>();
	//为了实现每次使用该类时不创建新的对象而创建的静态对象
	private static ShineApplication instance;
	//构造方法
	//实例化一次
	public synchronized static ShineApplication getInstance(){

		return instance;
	}
	//网络访问队列
	public static RequestQueue queue;
	//获取屏幕密度
	public static float density=1;

	// add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);
	}
	//关闭每一个list内的activity
	public void exit() {
		try {
			for (Activity activity:mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	//杀进程
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		context= this;
		density=context.getResources().getDisplayMetrics().density;
		if(queue==null){
			queue=Volley.newRequestQueue(context);
		}
		//crasheye
		if (!Constant.google_package_name.equals(context.getPackageName())){
			Crasheye.init(context, "465ae8c0");
		}
		// 初始化
		x.Ext.init(this);
		// 设置是否输出debug
		x.Ext.setDebug(false);
		int maxMemory = (int) (Runtime.getRuntime().maxMemory());
		File cacheDir = StorageUtils.getOwnCacheDirectory(context.getApplicationContext(), "imageloader/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.memoryCacheExtraOptions(480, 800) // max width, max height锛屽嵆淇濆瓨鐨勬瘡涓紦瀛樻枃浠剁殑鏈�澶ч暱瀹� 
				.threadPoolSize(3) //绾跨▼姹犲唴鍔犺浇鐨勬暟閲�
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(new WeakMemoryCache())
				.memoryCacheSize(maxMemory/8)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator()) //灏嗕繚瀛樼殑鏃跺�欑殑URI鍚嶇О鐢∕D5 鍔犲瘑
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/浣犲彲浠ラ�氳繃鑷繁鐨勫唴瀛樼紦瀛樺疄鐜�
				.diskCacheSize(50 * 1024 * 1024)  // 50 Mb sd鍗�(鏈湴)缂撳瓨鐨勬渶澶у��
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// 鐢卞師鍏堢殑discCache -> diskCache
				.discCacheFileCount(80)
				.discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径  
				.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)瓒呮椂鏃堕棿  
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
		File file=new File(Path+"/");
		if(!file.exists()){
			file.mkdir();
		}
		File file1=new File(Environment.getExternalStorageDirectory()+"/"+ShineApplication.IMAGE_FILE_LOCATION);
		if(!file1.exists()){
			try {
				file1.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		JPushInterface.setDebugMode(true);
		JPushInterface.init(context);
	}

	//视频库缓存相关
	private static HttpProxyCacheServer proxy;
	private static File file;
	private static String name;

	public static HttpProxyCacheServer getProxy(Context context,String cacheName) {
		name=cacheName;
		return proxy=newProxy();
	}

	private static HttpProxyCacheServer newProxy() {
		if(AppUtils.sdcardIsExist()){
			file=new File(VIDEO_PATH);
		}else{
			return new HttpProxyCacheServer.Builder(context,name)
					.build(name);
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		return new HttpProxyCacheServer.Builder(context,name)
				.cacheDirectory(file)
				.build(name);
	}
}