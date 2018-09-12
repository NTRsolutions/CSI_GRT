package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.Urlsutil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PhotoenlargeActivity extends DoActivity {

	private ImageView imageview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photoenlarge);
		SetViews();
		SetListeners();
	}

	private void SetViews() {
		imageview=(ImageView)findViewById(R.id.imageView1);
		String name=getIntent().getExtras().getString("name");
//		name=name.substring(0, name.length()-4);
//		ImageHttp.LoadImage(imageview, new Urlsutil().getImageInfo+SqliteUtil.inquirylogin().get("name")+"/"+name,name);
		String accountName="";
		if(Cons.userBean!=null){
			accountName=Cons.userBean.accountName;
		}
		ImageHttp.ImageLoader(imageview, new Urlsutil().getImageInfo+accountName+"/"+name);
	    //ImageLoader.getInstance().displayImage(new Urlsutil().getQuestionIng+"&userId="+Cons.userId+"&imageName="+name.substring(0, name.length()-4), imageview);
	}

	private void SetListeners() {
		imageview.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
