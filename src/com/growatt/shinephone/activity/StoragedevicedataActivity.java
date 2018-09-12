package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.DevicedataAdapter;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoragedevicedataActivity extends DoActivity {

	private TextView tv2;
	private TextView tv4;
	private TextView tv6;
	private TextView tv8;
	private TextView tv10;
	private TextView tv12;
	private TextView data1;
	private TextView data2;
	private TextView data3;
	private TextView data4;
	private ListView listview;
	private List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
	private DevicedataAdapter adapter;
	private String vac;
	private String alias;
	private String serialNum;
	private int capacity;
	private String vBat;
	private String datalogSn;
	private String[]titles={"PV","Bat","Grid"};
	private String StorageType;
	private String modelText;
	private String fwVersion;
	private String normalPower;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_storagedevicedata);
		Bundle bundle=getIntent().getExtras();
		vac=bundle.getString("vac");
		vBat=bundle.getString("vBat");
		fwVersion=bundle.getString("fwVersion");
		alias=bundle.getString("alias");
		modelText=bundle.getString("modelText");
		normalPower=bundle.getString("normalPower");
		StorageType=bundle.getString("StorageType");
		datalogSn=bundle.getString("datalogSn");
		serialNum=bundle.getString("serialNum");
		capacity = Integer.parseInt(bundle.getString("capacity"));
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
		setHeaderTitle(headerView,alias);
	}
	private void SetViews() {
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object>map=new HashMap<String, Object>();
			map.put("pv",titles[i]);
			map.put("volt","0");
			map.put("current","0");
			map.put("watt","0");
			list.add(map);
		}
		tv2=(TextView)findViewById(R.id.textView2);
		tv4=(TextView)findViewById(R.id.textView4);
		tv6=(TextView)findViewById(R.id.textView6);
		tv8=(TextView)findViewById(R.id.textView8);
		tv10=(TextView)findViewById(R.id.textView10);
		tv12=(TextView)findViewById(R.id.textView12);
		data1=(TextView)findViewById(R.id.textView_data1);
		data2=(TextView)findViewById(R.id.textView_data2);
		data3=(TextView)findViewById(R.id.textView_data3);
		data4=(TextView)findViewById(R.id.textView_data4);
		tv2.setText(serialNum);
		tv4.setText(datalogSn);
		tv6.setText(StorageType);
		tv8.setText(normalPower);
		tv10.setText(fwVersion);
		tv12.setText(modelText);
		TextPaint tp = data1.getPaint(); 
		tp.setFakeBoldText(true); 
		listview=(ListView)findViewById(R.id.listview1);
		adapter=new DevicedataAdapter(this, list);
		listview.setAdapter(adapter);
	}

	private void Setlisteners() {
		Mydialog.Show(StoragedevicedataActivity.this, "");
		GetUtil.get(new Urlsutil().getStorageInfo+"&storageId="+serialNum, new GetListener() {
			
			@Override
			public void success(String json) {
				try {
					Mydialog.Dismiss();
					if(json.length()>15){
					JSONObject jsonObject=new JSONObject(json);
					list.get(0).put("volt", jsonObject.get("ipv").toString());
					list.get(0).put("current", jsonObject.get("vpv").toString());
					list.get(0).put("watt", jsonObject.get("ppv").toString());
					list.get(1).put("volt", jsonObject.get("ibat").toString());
					list.get(1).put("current", jsonObject.get("vbat").toString());
					list.get(1).put("watt", jsonObject.get("pbat").toString());
					list.get(2).put("volt", jsonObject.get("iGuid").toString());
					list.get(2).put("current", jsonObject.get("vGuid").toString());
					list.get(2).put("watt", jsonObject.get("pGuid").toString());
					adapter.notifyDataSetChanged();
					}else{
						toast(R.string.all_data_inexistence);
					}
				} catch (Exception e) {
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
