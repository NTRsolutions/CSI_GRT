package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.growatt.shinephone.R;
import com.growatt.shinephone.ui.ZoomImageView;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.Urlsutil;

public class ZoomimageActivity extends DoActivity {

	private ZoomImageView zoomImg;
	private String  imagename;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoomimage);
		Bundle bundle=getIntent().getExtras();
		if(bundle.containsKey("imagename")){
			imagename=bundle.getString("imagename");
		}
		SetViews();
	}

	private void SetViews() {
		zoomImg = (ZoomImageView) findViewById(R.id.image);
//		imageLoader.displayImage(Urlsutil.getInstance().getProductImageInfo+imagename ,zoomImg, AppUtils.Options());
		ImageHttp.ImageLoader(zoomImg, Urlsutil.getInstance().getProductImageInfo+imagename);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
