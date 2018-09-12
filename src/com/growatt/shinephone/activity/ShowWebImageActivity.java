package com.growatt.shinephone.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.LoadLocalImageUtil;
import com.growatt.shinephone.view.ZoomableImageView;

public class ShowWebImageActivity extends DoActivity {
	 private TextView imageTextView = null;  
	    private String imagePath = null;  
	    private ZoomableImageView imageView = null;  
	 Handler handler=new Handler(){
		 @Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 100:
				Bitmap bitmap=(Bitmap) msg.obj;
				if(imageView!=null){
					imageView.setImageBitmap(bitmap);
				}
				break;

			default:
				break;
			}
			
		}
	 };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_web_image);
		 this.imagePath = getIntent().getStringExtra("image");  
		
	        this.imageTextView = (TextView) findViewById(R.id.show_webimage_imagepath_textview);  
	        imageTextView.setText(this.imagePath);  
	        imageView = (ZoomableImageView) findViewById(R.id.show_webimage_imageview);  
	  
	     
	           try {
				loadImageFromUrl(this.imagePath);
			} catch (IOException e) {
				e.printStackTrace();
			}  
	       
	    }  
	  
	    public  void loadImageFromUrl(final String url) throws IOException {  
//	      new Thread(){
//	    	  public void run() {
//				try {
//					URL m= new URL(url);
//	    		  InputStream i = (InputStream) m.getContent();  
////	    		  Drawable d = Drawable.createFromStream(i, "src");
//	    		  Bitmap bitmap=LoadLocalImageUtil.compress(i, ShowWebImageActivity.this, imageView);
//	    		  Message msg=new Message();
//	    		  msg.what=100;
//	    		  msg.obj=bitmap;
//	    		  handler.sendMessage(msg);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}  
//	    	  };
//	      }.start();
	    	ImageHttp.ImageLoader(imageView, url);
	    }  
}
