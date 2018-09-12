package com.growatt.shinephone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.growatt.shinephone.activity.DoActivity;
import com.growatt.shinephone.activity.ShowWebImageActivity;
import com.growatt.shinephone.util.Position;

public class CompanyActivity extends DoActivity {

	private WebView mWebView;
	private LinearLayout mll;
	private String url;
	private View headerView;
private ProgressBar pg1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company);
		initHeaderView();
		initView();
	}

	@SuppressLint("JavascriptInterface") private void initView() {
		pg1=(ProgressBar) findViewById(R.id.progressBar1);
		url="http://www.canadiansolar.com/";
		mWebView = new WebView(this.getApplicationContext());
		LinearLayout.LayoutParams mWebViewLP = new LinearLayout.LayoutParams(  
                LinearLayout.LayoutParams.MATCH_PARENT,  
                LinearLayout.LayoutParams.MATCH_PARENT);  
        mWebView.setLayoutParams(mWebViewLP);  
        mWebView.setInitialScale(25);  
		mll  = (LinearLayout) findViewById(R.id.llContainer); 
		mll.addView(mWebView);
		
		 WebSettings settings = mWebView.getSettings();  
		  settings.setUseWideViewPort(true); 
	        settings.setLoadWithOverviewMode(true); 
	        settings.setSupportZoom(true);
	        settings.setBuiltInZoomControls(true);
	        settings.setDisplayZoomControls(false);
		settings.setDomStorageEnabled(true);//有可能是DOM储存API没有打开
	        settings.setJavaScriptEnabled(true);  
		mWebView.addJavascriptInterface(new JavascriptInterface(this), "imagelistner"); 
		settings.setDefaultZoom(ZoomDensity.FAR);
		mWebView.setWebViewClient(new MyWebViewClient());
		mWebView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO 自动生成的方法存根

				if(newProgress==100){
					pg1.setVisibility(View.GONE);//加载完网页进度条消失
				}
				else{
					pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
					pg1.setProgress(newProgress);//设置进度值
				}

			}
		});
		mWebView.loadUrl(url);
	}

	private void initHeaderView() {
		headerView=findViewById(R.id.headerView);
		setHeaderTitle(headerView, getString(R.string.InfoCenterAct_company));
		setHeaderImage(headerView, R.drawable.back, Position.LEFT, new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	// ע��js��������
			private void addImageClickListner() {
				// ���js�����Ĺ��ܾ��ǣ��������е�img���㣬�����onclick�������ڻ���ִ�е�ʱ����ñ��ؽӿڴ���url��ȥ
				if(mWebView!=null){
					mWebView.loadUrl("javascript:(function(){" +
				"var objs = document.getElementsByTagName(\"img\"); " + 
						"for(var i=0;i<objs.length;i++)  " + 
				"{"
						+ "    objs[i].onclick=function()  " + 
				"    {  " 
						+ "        window.imagelistner.openImage(this.src);  " + 
				"    }  " + 
				"}" + 
				"})()");
				}
			}

			// jsͨ�Žӿ�
			public class JavascriptInterface {

				private Context context;
				public JavascriptInterface(Context context) {
					this.context = context;
				}
				
				public void openImage(String img) {
					System.out.println(img);
					//
					Intent intent = new Intent();
					intent.putExtra("image", img);
					intent.setClass(context, ShowWebImageActivity.class);
					context.startActivity(intent);
					System.out.println(img);
				}
			}

			// ����
			private class MyWebViewClient extends WebViewClient {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					 //��APP�ڲ������ӣ���Ҫ����ϵͳ�����
	              view.loadUrl(url);
	              return true;
				}

				@Override
				public void onPageFinished(WebView view, String url) {

					view.getSettings().setJavaScriptEnabled(true);

					super.onPageFinished(view, url);
					// html�������֮����Ӽ���ͼƬ�ĵ��js����
					addImageClickListner();

				}

				@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon) {
					view.getSettings().setJavaScriptEnabled(true);

					super.onPageStarted(view, url, favicon);
				}

				@Override
				public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

					super.onReceivedError(view, errorCode, description, failingUrl);

				}
			}

			@Override
			protected void onDestroy() {
//				ViewGroup view = (ViewGroup) getWindow().getDecorView();  
//			    view.removeAllViews();  
			    super.onDestroy();
			    mll.removeView(mWebView);
			    if(mWebView!= null) {
			    	mWebView.stopLoading();
			    	mWebView.removeAllViews();
			    	mWebView.destroy();
			    	mWebView=null;
			    } 
			}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWebView.canGoBack()){
				mWebView.goBack();// 返回前一个页面
				return true;
			}else {
				CompanyActivity.this.finish();
                return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
