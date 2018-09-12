package com.growatt.shinephone.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.growatt.shinephone.R;
import com.growatt.shinephone.update.ParseXmlService;
import com.growatt.shinephone.update.UpdateClass;
import com.growatt.shinephone.update.UpdateInfo;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * �������
 * @author Administrator
 *
 */
public class UpdateManager {
	private Context mContext;
	private String apkUrl="" ;
	private Dialog downloadDialog;
    private static final String savePath =Environment.getExternalStorageDirectory()+ "/growatts/";
    private static final String saveFileName = savePath + "ShinePhone.apk";
	//微信热更新路径
	private static final String tinkerFilePath = savePath + "ShinePhone_TinkerPatch";
    private ProgressBar mProgress;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private int progress;
    public  Thread downLoadThread=null;
    private boolean interceptFlag = false;
    private String msg;
 	private static boolean bUpdating = false;
 	int res;
 	
    private Handler mHandler = new Handler(){
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:
				
				installApk();
				break;
			default:
				break;
			}
    	};
    };
    
	public UpdateManager(Context context,String apkUrl,String msg) {
		this.mContext = context;
		this.apkUrl = apkUrl;
		this.msg=msg;
	}
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			Mydialog.Dismiss();
			switch (msg.what) {
			case 3:
				Download();
				break;
			case 100:
				//下载热更新包完成,加载更新包
				loadPatch();
				break;
			default:
				if(res==Constant.AboutActivity_Updata){
				T.make(R.string.soft_update_no,mContext);
				}
				break;
			}
		};
	};

	private void downloadTinker() {
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						try {
							URL url = new URL(mUpdateInfo.getTinkerUrl());
							HttpURLConnection conn = (HttpURLConnection)url.openConnection();
							conn.connect();
							int length = conn.getContentLength();
							InputStream is = conn.getInputStream();
							File file = new File(savePath);
							if(!file.exists()){
								file.mkdirs();
							}
							String apkFile = tinkerFilePath;
							File ApkFile = new File(apkFile);
							if(!ApkFile.exists()){
								ApkFile.createNewFile();
							}
							FileOutputStream fos = new FileOutputStream(ApkFile);
							int numread = 0;
							byte buf[] = new byte[1024];
							while ((numread = is.read(buf)) != -1){
								fos.write(buf,0,numread);
							}
							fos.close();
							is.close();
							handler.sendEmptyMessage(100);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch(IOException e){
							e.printStackTrace();
						}
					}
				}
		).start();
	}

	public void Getupdateurl(int res){
		this.res=res;
		new Thread(){
			public void run() {
				int m=checkUpdate(mContext, apkUrl);
				Message msg=new Message();
				msg.what=m;
				handler.sendMessage(msg);
				//热更新
				if (mUpdateInfo != null && mUpdateInfo.getTinkerCode() > Constant.TinkerCode){
					downloadTinker();
				}
			};
		}.start();
	}
	private UpdateInfo mUpdateInfo;
	public int checkUpdate(Context context,String urls){
		int updateMsg;
		InputStream inputStream = null;
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection)new URL(urls).openConnection();
					
		}  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			updateMsg = 1;
			return updateMsg;
		}
		conn.setConnectTimeout(12000);//�������ӳ�ʱ
		try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			updateMsg = 1;
			return updateMsg;
		}
		try {
			if (conn.getResponseCode()==200) {
				inputStream=conn.getInputStream();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			updateMsg = 1;
			return updateMsg;
		}
		
		// ����XML�ļ�
		ParseXmlService service = new ParseXmlService();
		try{
			mUpdateInfo = service.parseXml(inputStream);
		} catch (Exception e){
			e.printStackTrace();
			updateMsg = 1;
			return updateMsg;
		}
		if(mUpdateInfo == null){
				updateMsg = 1;
				return updateMsg;
		}
		//�жϰ汾��
		if (mUpdateInfo.getVersionCode() <= UpdateClass.getCurrentVersion(context)){
			
				updateMsg = 0;
				return updateMsg;
		}
		updateMsg = 3;
		// ��ʾ��ʾ�Ի���
				bUpdating = true;
		return updateMsg;
	}
	public void Download(){
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.soft_update_title);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		StringBuffer strInfo = new StringBuffer();
		strInfo.append(mContext.getString(R.string.update_version) + mUpdateInfo.getVersionName() + "\n");
		strInfo.append(mContext.getString(R.string.update_size) + mUpdateInfo.getFileSize() + "\n");
		strInfo.append(mContext.getString(R.string.update_log) + "\n");
		strInfo.append(mUpdateInfo.getUpdateLog().replace("|", "\n"));
		builder.setMessage(strInfo);
		
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar)v.findViewById(R.id.progress);
		Button bt1=(Button)v.findViewById(R.id.button1);
		Button bt2=(Button)v.findViewById(R.id.button2);
	
		bt1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(downLoadThread!=null){
					downLoadThread.interrupt();
					downLoadThread=null;
				}
				downloadDialog.dismiss();
			}
		});
		bt2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(downLoadThread==null){
				downloadApk();
				}else{
					T.make(mContext.getString(R.string.UpdateMananger_downloading),mContext);
				}
						
			}
		});
		builder.setView(v);
		downloadDialog = builder.create();
		downloadDialog.setCancelable(false);
		downloadDialog.show();
	}
	
	private Runnable mdownApkRunnable = new Runnable() {	
		@Override
		public void run() {
			try {
				URL url = new URL(mUpdateInfo.getDownloadURL());
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				File file = new File(savePath);
				if(!file.exists()){
					file.mkdirs();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				if(!ApkFile.exists()){
					ApkFile.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(ApkFile);
				int count = 0;
				byte buf[] = new byte[1024];
				do{
		    		int numread = is.read(buf);
		    		count += numread;
		    	    progress =(int)(((float)count / length) * 100);
		    	    mHandler.sendEmptyMessage(DOWN_UPDATE);
		    		if(numread <= 0){	
		    			mHandler.sendEmptyMessage(DOWN_OVER);
		    			break;
		    		}
		    		fos.write(buf,0,numread);
		    	}while(!interceptFlag);//���ȡ����ֹͣ����.
				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}
			
		}
	};
	
	 /**
     * ����apk
     */
	
	private void downloadApk(){
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.setDaemon(true);
		downLoadThread.start();
	}
	 /**
     * ��װapk
     */
	private void installApk(){
		downloadDialog.dismiss();
		File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }    
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
        mContext.startActivity(i);
        downLoadThread=null;
	}

	/**
	 * 加载热补丁插件
	 */
	public void loadPatch() {
		TinkerInstaller.onReceiveUpgradePatch(mContext.getApplicationContext(),tinkerFilePath);
	}

	/**
	 * 杀死应用加载补丁
	 */
	public void killApp() {
		ShareTinkerInternals.killAllOtherProcess(mContext.getApplicationContext());
		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
