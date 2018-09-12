package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.ui.InverterDialog;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.PostUtil.postListener;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class TimeActivity extends DoActivity {

	private String[]hours={"00","01","02","03","04","05","06","07","08","09"
			,"10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
	private String[]mins=new String[60];
	public static TextView starttime;
	public static TextView stoptime;
	public static InverterDialog dialogtime;
	public static String flag;
	public boolean flag2=false;
	public boolean isfinish=false;
	private String serialNum;
	private String pv;
	private String id;
	public static String time=""; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time);
		Bundle bundle=getIntent().getExtras();
		pv=bundle.getString("pv");
		id=bundle.getString("id");
		initHeaderView();
		setViews();
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
		setHeaderTitle(headerView,getString(R.string.calendar_amend));
		setHeaderTvTitle(headerView, getString(R.string.all_ok), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tvOK();
			}
		});
	}
	private void setViews() {
		for (int i = 0; i < mins.length; i++) {
			if(i<10){
				mins[i]="0"+i;
			}else{
				mins[i]=i+"";
			}
		}
		starttime=(TextView)findViewById(R.id.starttime);
		stoptime=(TextView)findViewById(R.id.stoptime);
	}
	public void tvOK(){
			String s1=starttime.getText().toString();
			String s2=stoptime.getText().toString();
			System.out.println(s1);
			System.out.println(s2);
			if(s1.equals("")){
				toast(R.string.all_blank);
				return;
			}
			if(s2.equals("")){
				toast(R.string.all_blank);
				return;
			}
			Mydialog.Show(TimeActivity.this,"");
			PostUtil.post(new Urlsutil().storageSet, new postListener() {

				@Override
				public void success(String json) {
					try {
						Mydialog.Dismiss();
						JSONObject jsonObject=new JSONObject(json);
						String msg=jsonObject.getString("msg");
						int m=Integer.parseInt(msg);
						switch (m) {
							case 200:
								toast(R.string.all_success);
								finish();
								break;
							case 501:
								toast(R.string.inverterset_set_no_server);
								break;
							case 502:
								toast(R.string.inverterset_set_interver_no_server);
								break;
							case 503:
								toast(R.string.inverterset_set_no_numberblank);
								break;
							case 504:
								toast(R.string.inverterset_set_interver_no_online);
								break;
							case 505:
								toast(R.string.inverterset_set_no_online);

								break;
							case 506:
								toast(R.string.storageset_no_type);

								break;
							case 507:
								toast(R.string.inverterset_set_no_blank);

								break;
							case 508:
								toast(R.string.inverterset_set_no_value);
								break;
							case 509:
								toast(R.string.inverterset_set_no_time);
								break;

							default:
								toast(R.string.inverterset_set_other);
								break;
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void Params(Map<String, String> params) {
					params.put("serialNum", id);
					params.put("paramId", pv);
					String s1=starttime.getText().toString();
					String s2=stoptime.getText().toString();
					String[]array1=s1.split(":");
					String[]array2=s2.split(":");
					params.put("command_1",array1[0]);
					params.put("command_2",array1[1]);
					params.put("command_2",array2[0]);
					params.put("command_2",array2[1]);
				}

				@Override
				public void LoginError(String str) {
					// TODO Auto-generated method stub

				}
			});
	}
	private void SetListeners() {
		starttime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				time="";
				flag="1";
				dialogtime=new InverterDialog(TimeActivity.this,R.string.all_time_start, hours,mins);
				dialogtime.show();
			}
		});
		stoptime.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				flag="2";
				time="";
				dialogtime=new InverterDialog(TimeActivity.this,R.string.all_time_stop, hours,mins);
				dialogtime.show();
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
