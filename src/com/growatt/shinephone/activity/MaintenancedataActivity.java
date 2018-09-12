package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MaintenancedataActivity extends DoActivity {

	private TextView tv1;
	private TextView tv3;
	private TextView tv5;
	private TextView tv7;
	private TextView tv9;
	private TextView tv11;
	private String id;
	private TextView tv13;
	private String name;
	private ImageView image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintenancedata);
		id=getIntent().getExtras().getString("id");
		initHeaderView();
		SetViews();
		Setlisteners();
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
		setHeaderTitle(headerView,getString(R.string.maintenancedata_title));
	}
	private void SetViews() {
		image=(ImageView)findViewById(R.id.imageView1);
		tv1=(TextView)findViewById(R.id.textView1);
		tv3=(TextView)findViewById(R.id.textView3);
		tv5=(TextView)findViewById(R.id.textView5);
		tv7=(TextView)findViewById(R.id.textView7);
		tv9=(TextView)findViewById(R.id.textView9);
		tv11=(TextView)findViewById(R.id.textView11);
		tv13=(TextView)findViewById(R.id.textView13);
		image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, AppUtils.getScreenWidth(this)*5/8));
	}

	private void Setlisteners() {
		image.setOnClickListener(new View.OnClickListener() {
			

			@Override
			public void onClick(View arg0) {
//				Intent intent=new Intent(MaintenancedataActivity.this,PhotoenlargeActivity.class);
//				Bundle bundle=new Bundle();
//				bundle.putString("name", name);
//				intent.putExtras(bundle);
//				startActivity(intent);
			}
		});
		Mydialog.Show(MaintenancedataActivity.this, "");
		GetUtil.get(new Urlsutil().getAppreciationInfo+"&id="+id+"&language="+AppUtils.getLocale(), new GetListener() {
			
			@Override
			public void success(String json) {
				Mydialog.Dismiss();
				try {
					JSONObject jsonObject=new JSONObject(json);
					Map<String, Object>map=new HashMap<String, Object>();
					map.put("title", jsonObject.get("title").toString());
					map.put("id", jsonObject.get("id").toString());
					map.put("phoneNum", jsonObject.get("phoneNum").toString());
					map.put("area", jsonObject.get("area").toString());
					map.put("price", jsonObject.get("price").toString());
					map.put("outline", jsonObject.get("outline").toString());
					map.put("recommend", jsonObject.get("recommend").toString());
					map.put("supplier", jsonObject.get("supplier").toString());
					map.put("imageName", jsonObject.get("imageName").toString());
					tv1.setText(map.get("title").toString());
					tv3.setText(map.get("outline").toString());
					tv5.setText(map.get("recommend").toString());
					tv7.setText(map.get("supplier").toString());
					tv9.setText(map.get("area").toString());
					tv11.setText(map.get("price").toString());
					tv13.setText(map.get("phoneNum").toString());
					name=map.get("imageName").toString();
					if(!name.equals("")){
//					name=name.substring(0, name.length()-4);
//					imageLoader.displayImage(Urlsutil.getInstance().getProductImageInfo+name,image, AppUtils.Options());
					ImageHttp.ImageLoader(image, Urlsutil.getInstance().getProductImageInfo+name);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void error(String json) {
				
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
