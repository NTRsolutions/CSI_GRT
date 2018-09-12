package com.growatt.shinephone.activity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Position;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
@SuppressLint("SetJavaScriptEnabled")
public class CommondataActivity extends DoActivity {

	private WebView tv2;
	private String content;
	private LinearLayout llQuestion;
	private LinearLayout mll;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commondata);
		Bundle bundle=getIntent().getExtras();
		content=bundle.getString("content");
		initHeaderView();
		SetViews();
		SetListeners();
	}
	private View headerView;
	private void initHeaderView() {
		headerView = findViewById(R.id.headerView);
		setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		setHeaderTitle(headerView,getString(R.string.commondata_title));
	}
       @SuppressLint("JavascriptInterface") private void SetViews() {
		//权限不够直接屏蔽掉提问
		LinearLayout llQ = (LinearLayout) findViewById(R.id.linearlayout);
//		if (Cons.isflag==true || !Cons.addQuestion){
//			   llQ.setVisibility(View.GONE);
//		}
		   llQ.setVisibility(View.GONE);
		llQuestion=(LinearLayout)findViewById(R.id.ll_question);
		tv2 = new WebView(getApplicationContext()); 
		LinearLayout.LayoutParams mWebViewLP = new LinearLayout.LayoutParams(  
				 LinearLayout.LayoutParams.MATCH_PARENT,  
               LinearLayout.LayoutParams.MATCH_PARENT);  
       tv2.setLayoutParams(mWebViewLP);
		mll  = (LinearLayout) findViewById(R.id.tvContent); 
		mll.addView(tv2);
	 
		
		tv2.getSettings().setJavaScriptEnabled(true);
		tv2.getSettings().setLoadWithOverviewMode(true); 
		tv2.getSettings().setSupportZoom(true);
		tv2.getSettings().setBuiltInZoomControls(true);
		tv2.getSettings().setDisplayZoomControls(false);
		tv2.loadDataWithBaseURL(null, getNewContent1(content), "text/html", "utf-8", null);
		tv2.addJavascriptInterface(new JavascriptInterface(this), "imagelistner"); 
		tv2.getSettings().setDefaultZoom(ZoomDensity.FAR);
		  tv2.setWebViewClient(new MyWebViewClient());

	}
	private String getNewContent(String htmltext){
	      
	    Document doc=Jsoup.parse(htmltext);  
	       Elements elements=doc.getElementsByTag("img");  
	       DisplayMetrics metric = new DisplayMetrics();  
	       getWindowManager().getDefaultDisplay().getMetrics(metric);
	         int width=(int) metric.xdpi;
	       for (Element element : elements) {
	    	   String eleWidth=element.attr("width");
	    	   try{
	 	          if(!TextUtils.isEmpty(eleWidth)&&Integer.parseInt(eleWidth)>width){
	 	    	     element.attr("width","100%").attr("height","auto");
	 	          }
	 	    	}catch(Exception e){
	 	    		e.printStackTrace();
	 	    	 }
	    }  
	         
	       return doc.toString();  
	   }
	private String getNewContent1(String htmltext){  
	      
	    Document doc=Jsoup.parse(htmltext);  
	       Elements elements=doc.getElementsByTag("img");  
	         if(elements!=null&&elements.size()!=0){
	        	 for(Element element : elements){
//	        		 element.attr("style", "width:100%");
	        		 element.attr("width","100%").attr("height","auto");
	        	 }
	         }
	       return doc.toString();  
	   }
		private void addImageClickListner() {
			if(tv2!=null){
			tv2.loadUrl("javascript:(function(){" +
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

		private class MyWebViewClient extends WebViewClient {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
              view.loadUrl(url);
              return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {

				view.getSettings().setJavaScriptEnabled(true);

				super.onPageFinished(view, url);
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
	private void SetListeners() {
		llQuestion.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(Cons.isflag==true){
					toast(R.string.all_experience);
					return;
				}
				if(Cons.addQuestion){
					AlertDialog dialog=new AlertDialog.Builder(CommondataActivity.this)
			    .setIcon(android.R.drawable.ic_menu_info_details)
			    .setTitle(R.string.reminder)
			    .setMessage(R.string.userdata_dialog_title)
			    .setNegativeButton(R.string.all_no, null)
			    .setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent intent=new Intent(CommondataActivity.this,PutinV2Activity.class);
						startActivity(intent);
					}
				})
			    .create();
			    dialog.show();
				}else{
//					toast(getString(R.string.fragment2_loading));
				}
				
			}
		});
	}
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    mll.removeView(tv2);
	    if(tv2!= null) {
	    	tv2.stopLoading();
	    	tv2.removeAllViews();
	    	tv2.destroy();
	    	tv2=null;
	    } 
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
