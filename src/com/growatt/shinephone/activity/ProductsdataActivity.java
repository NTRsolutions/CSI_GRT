package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

public class ProductsdataActivity extends DoActivity {

	private String feature;
	private String outline;
	private String productName;
	private String technologyParams;
	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private RadioGroup radiogroup;
	private ImageView imageview1;
	private ImageView imageview2;
	private String productImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productsdata);
		Bundle bundle=getIntent().getExtras();
		feature=bundle.get("feature").toString();
		outline=bundle.get("outline").toString();
		productImage=bundle.get("productImage").toString();
		String[] names = productImage.split("/");
		productImage=names[names.length-1];
		
		productName=bundle.get("productName").toString();
		
		technologyParams=bundle.get("technologyParams").toString();
		String[] name1s = technologyParams.split("/");
		technologyParams=name1s[name1s.length-1];
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
		setHeaderTitle(headerView,getString(R.string.product_detial));
	}
	private void SetViews() {
//		productName=productName.substring(0, productName.length()-4);
//		technologyParams=technologyParams.substring(0, productName.length()-4);
		tv1=(TextView)findViewById(R.id.textView1);
		tv2=(TextView)findViewById(R.id.textView2);
		tv3=(TextView)findViewById(R.id.textView3);
		imageview1=(ImageView)findViewById(R.id.imageView1);
		imageview2=(ImageView)findViewById(R.id.imageView2);
		radiogroup=(RadioGroup)findViewById(R.id.radioGroup1);
		tv1.setText(productName);
		tv2.setText(feature);
		tv3.setText(outline);
	}
	int index = 0;
	private void SetListeners() {
		radiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup radio, int position) {
				
				switch (position) {
				case R.id.radio_button1:
					index = 0;
					tv3.setVisibility(View.VISIBLE);
					imageview2.setVisibility(View.GONE);
					break;
				case R.id.radio_button2:
					index = 1;
					tv3.setVisibility(View.GONE);
					imageview2.setVisibility(View.VISIBLE);
					break;
				}
			}
		});
		imageview1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
					Intent intent=new Intent(ProductsdataActivity.this,ZoomimageActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("imagename", productImage);
					intent.putExtras(bundle);
					startActivity(intent);
			}
		});
		imageview2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(ProductsdataActivity.this,ZoomimageActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("imagename", technologyParams);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
//		imageLoader.displayImage(Urlsutil.getInstance().getProductImageInfo+productImage ,imageview1, AppUtils.Options());
//		imageLoader.displayImage(Urlsutil.getInstance().getProductImageInfo+technologyParams,imageview2, AppUtils.Options());
		ImageHttp.ImageLoader(imageview1, Urlsutil.getInstance().getProductImageInfo+productImage);
		ImageHttp.ImageLoader(imageview2, Urlsutil.getInstance().getProductImageInfo+technologyParams);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
