package com.growatt.shinephone.video;

import java.io.File;
import java.io.IOException;

import org.xutils.common.Callback.Cancelable;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DoActivity;
import com.growatt.shinephone.activity.InverterActivity;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.Mydialog;

public class PlayerActivity extends DoActivity {

	private VideoView videoView;
	private MediaController mediaController;
	private RelativeLayout container;
	private String LOG_TAG="tag";;
	private String VIDEO_CACHE_NAME;
	private String VIDEO_URL;
	private int type;
	private ImageView ivBack;
	private int current;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_player);
		type=getIntent().getIntExtra("type", 101);
		if(type==Constant.CacheVideoActToPlayerAct){
			VIDEO_URL=getIntent().getStringExtra("uri");
		}else{
			VIDEO_URL=getIntent().getStringExtra("videoPicurl");
			VIDEO_CACHE_NAME=getIntent().getStringExtra("videoName");
		}
		try {
			initView();
			initListener();
			playMP4();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  private void initListener() {
        ivBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
        videoView.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				new Handler().postDelayed(new Runnable() {
					
					@Override
					public void run() {
						Mydialog.Dismiss();
					}
				}, 500);
				
			}
		});
        
		//������Ļ���п������
		container.setOnTouchListener(new View.OnTouchListener() {
			boolean result = false;
			float wDown;
			float wMove;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int currentPosition = videoView.getCurrentPosition();
				int duration = videoView.getDuration();
				if(duration==-1) return false;
				int conWidth=v.getWidth();
				int action = event.getAction();
				
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					wDown=event.getX();
					
//					break;
                case MotionEvent.ACTION_MOVE:
                	  int dx = (int) (event.getX() - wDown);
                	result=true;
					break;
                case MotionEvent.ACTION_UP:
                	int dx1 = (int) (event.getX() - wDown);
                	    int seek=duration*dx1/conWidth;
                	    if(Math.abs(dx1)>10){
                	    if(currentPosition+seek>duration){
                	      videoView.seekTo(duration);
                	    }else{
                		  videoView.seekTo(currentPosition+seek);
                	    }
                	   }
                	result=true;
					break;
				
				}
				return result;
			}
		});
	}
@Override
protected void onResume() {
	super.onResume();
	videoView.seekTo(current);
	videoView.start();
}
@Override
protected void onPause() {
	super.onPause();
	current=videoView.getCurrentPosition();
	if(videoView.canPause()){
	   videoView.pause();
	}
}
@Override
public void onSaveInstanceState(Bundle outState) {
	super.onSaveInstanceState(outState);
	 outState.putInt("current", current);
}
@Override
protected void onRestoreInstanceState(Bundle savedInstanceState) {
	super.onRestoreInstanceState(savedInstanceState);
	if(savedInstanceState!=null){
		current=savedInstanceState.getInt("current");
	}
	
}
private void initView() {
	videoView = (VideoView)this.findViewById(R.id.video1);
	container=(RelativeLayout)findViewById(R.id.relativeLayout1);
    ivBack=(ImageView)findViewById(R.id.iv_back);
	mediaController = new MediaController(this);
	videoView.setOnErrorListener(new OnErrorListener() {
		
		@Override
		public boolean onError(MediaPlayer arg0, int what, int extra) {

             return false;  
		}
	});
	videoView.setOnCompletionListener(new OnCompletionListener() {
		
		@Override
		public void onCompletion(MediaPlayer mp) {
		}
	});
	 /* 
    ������ 

MEDIA_ERROR_IO 
�ļ������ڻ���󣬻����粻�ɷ��ʴ��� 
ֵ: -1004 (0xfffffc14) 

MEDIA_ERROR_MALFORMED 
���������йر�׼���ļ��ı���淶 
ֵ: -1007 (0xfffffc11) 

MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK 
��Ƶ����������������������������Ƶ��ָ�꣨���磺MOOVԭ�ӣ������ļ��Ŀ�ʼ. 
ֵ: 200 (0x000000c8) 

MEDIA_ERROR_SERVER_DIED 
ý��������ҵ��ˡ���ʱ����������ͷ�MediaPlayer ���󣬲�����new һ���µġ� 
ֵ: 100 (0x00000064) 

MEDIA_ERROR_TIMED_OUT 
һЩ����ʹ���˹�����ʱ�䣬Ҳ���ǳ�ʱ�ˣ�ͨ���ǳ�����3-5�� 
ֵ: -110 (0xffffff92) 

MEDIA_ERROR_UNKNOWN 
δ֪���� 
ֵ: 1 (0x00000001) 

MEDIA_ERROR_UNSUPPORTED 
������������ر����׼���ļ��Ĺ�񣬵�ý���ܲ�֧�ִ˹��� 
ֵ: -1010 (0xfffffc0e) 


what int: ��ǵĴ�������: 
MEDIA_ERROR_UNKNOWN 
MEDIA_ERROR_SERVER_DIED 
extra int: ��ǵĴ�������. 
MEDIA_ERROR_IO 
MEDIA_ERROR_MALFORMED 
MEDIA_ERROR_UNSUPPORTED 
MEDIA_ERROR_TIMED_OUT 
MEDIA_ERROR_SYSTEM (-2147483648) - low-level system error. 

* */  
  }


	/**
	 *����������Ƶ
	 */
	private void playMP4() {
		Mydialog.Show(PlayerActivity.this,"");    
	try {
		if(type==Constant.CacheVideoActToPlayerAct){
			videoView.setVideoURI(Uri.parse("file://"+VIDEO_URL));	
	}else{
		HttpProxyCacheServer proxy = getProxy();
		File cached = proxy.isCached(VIDEO_URL,VIDEO_CACHE_NAME);
		if(cached.exists()){
		    String absolutePath = cached.getAbsolutePath();
		    videoView.setVideoURI(Uri.parse("file://"+absolutePath));
		}else{
	    String proxyUrl = proxy.getProxyUrl(VIDEO_URL);
	    videoView.setVideoPath(proxyUrl);
		}
	}
        videoView.setMediaController(mediaController);
    } catch (Exception e) {
        Log.e(LOG_TAG, "Error playing video", e);
    }
}
	private HttpProxyCacheServer getProxy() {
	    return ShineApplication.getProxy(getApplicationContext(),VIDEO_CACHE_NAME);
	}
    //�ж��ļ��Ƿ����  
    public boolean fileIsExists(String strFile)  
    {  
        try  
        {  
            File f=new File(strFile);  
            if(!f.exists())  
            {  
                    return false;  
            }  
  
        }  
        catch (Exception e)  
        {  
            return false;  
        }  
  
        return true;  
    }  


}
