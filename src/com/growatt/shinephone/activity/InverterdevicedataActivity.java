package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.DevicedataAdapter;
import com.growatt.shinephone.bean.v2.InverterDataBean;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.util.v2.DeviceTypeItemStr;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InverterdevicedataActivity extends DoActivity {

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
	private String[]titles={"PV1","PV2","AC1","AC2","AC3"};
	private ListView listview;
	private List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
	private DevicedataAdapter adapter;
	//跳转数据
	private String datalogSn;
	private String deviceAilas;
	private String inverterId;
	private String snominalPower;
	private String deviceType = DeviceTypeItemStr.DEVICE_INVERTER_STR;
	//获取数据url
	private String dataUrl;
	//获取详情数据url
	private String dataDetailUrl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devicedata);
		initIntent();
		initString();
		initHeaderView();
		SetViews();
		Setlisteners();
	}

	private void initString() {
		if (DeviceTypeItemStr.DEVICE_INVERTER_MAX_STR.equals(deviceType)){
			dataUrl = new Urlsutil().getInverterParams_max+"&maxId="+inverterId;
			dataDetailUrl = new Urlsutil().getInverterDetailData_max+"&inverterId="+inverterId;
			titles = new String[]{
					"PV1","PV2","PV3","PV4","PV5","PV6","PV7","PV8","AC1","AC2","AC3"
			};
		}else if (DeviceTypeItemStr.DEVICE_INVERTER_JLINV_STR.equals(deviceType)){
			dataUrl = new Urlsutil().getInverterParams_jlinv+"&jlinvId="+inverterId;
			dataDetailUrl = new Urlsutil().getInverterDetailData_jlinv+"&jlinvId="+inverterId;
			titles = new String[]{
					"PV1","PV2","PV3","PV4","AC1","AC2","AC3"
			};
		}else {
			dataUrl = new Urlsutil().getInverterParams+"&inverterId="+inverterId;
			dataDetailUrl = new Urlsutil().getInverterDetailData+"&inverterId="+inverterId;
			titles = new String[]{
					"PV1","PV2","AC1","AC2","AC3"
			};
		}
	}
	private void initIntent() {
		Bundle bundle=getIntent().getExtras();
		inverterId=bundle.getString("inverterId");
		datalogSn=bundle.getString("datalogSn");
		deviceAilas=bundle.getString("deviceAilas");
		snominalPower=bundle.getString("snominalPower");
		deviceType=bundle.getString("deviceType");
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
		setHeaderTitle(headerView,getString(R.string.inverter_parameter));
	}
	private void SetViews() {
		for (int i = 0 ,len = titles.length; i < len; i++) {
			Map<String, Object>map=new HashMap<String, Object>();
			map.put("pv", titles[i]);
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
		TextPaint tp = data1.getPaint(); 
		tp.setFakeBoldText(true); 
		tv2.setText(inverterId);
		tv4.setText(datalogSn);
		tv8.setText(snominalPower);
		listview=(ListView)findViewById(R.id.listview1);
		adapter=new DevicedataAdapter(this, list);
		listview.setAdapter(adapter);
		Mydialog.Show(InverterdevicedataActivity.this, "");
			GetUtil.get(dataUrl, new GetListener() {
				
				@Override
				public void success(String json) {
					try {
						JSONObject jsonObject=new JSONObject(json);
						JSONObject jsonObject2=jsonObject.getJSONObject("newBean");
						tv6.setText(jsonObject.get("inverterType").toString()); 
						tv10.setText(jsonObject2.get("fwVersion").toString()+"/"+jsonObject2.get("innerVersion").toString()); 
						tv12.setText(jsonObject2.get("modelText").toString()); 
						GetUtil.get(dataDetailUrl, new GetListener() {
							@Override
							public void success(String json) {
								try {
									Mydialog.Dismiss();
									JSONObject jsonObject=new JSONObject(json);
									if (TextUtils.isEmpty(json)) return;
									InverterDataBean bean = new Gson().fromJson(json,InverterDataBean.class);
									if (bean  == null) return;
									list.get(0).put("volt", bean.getIpv1());
									list.get(0).put("current", bean.getVpv1());
									list.get(0).put("watt", bean.getPpv1());
									list.get(1).put("volt", bean.getIpv2());
									list.get(1).put("current", bean.getVpv2());
									list.get(1).put("watt", bean.getPpv2());
									if (DeviceTypeItemStr.DEVICE_INVERTER_MAX_STR.equals(deviceType)){
										list.get(2).put("volt", bean.getIpv3());
										list.get(2).put("current", bean.getVpv3());
										list.get(2).put("watt", bean.getPpv3());
										list.get(3).put("volt", bean.getIpv4());
										list.get(3).put("current", bean.getVpv4());
										list.get(3).put("watt", bean.getPpv4());
										list.get(4).put("volt", bean.getIpv5());
										list.get(4).put("current", bean.getVpv5());
										list.get(4).put("watt", bean.getPpv5());
										list.get(5).put("volt", bean.getIpv6());
										list.get(5).put("current", bean.getVpv6());
										list.get(5).put("watt", bean.getPpv6());

										list.get(6).put("volt", bean.getIpv7());
										list.get(6).put("current", bean.getVpv7());
										list.get(6).put("watt", bean.getPpv7());
										list.get(7).put("volt", bean.getIpv8());
										list.get(7).put("current", bean.getVpv8());
										list.get(7).put("watt", bean.getPpv8());

										list.get(8).put("volt", bean.getIacr());
										list.get(8).put("current", bean.getVacr());
										list.get(8).put("watt", bean.getPacr());
										list.get(9).put("volt", bean.getIacs());
										list.get(9).put("current", bean.getVacs());
										list.get(9).put("watt", bean.getPacs());
										list.get(10).put("volt", bean.getIact());
										list.get(10).put("current", bean.getVact());
										list.get(10).put("watt", bean.getPact());
									}else if (DeviceTypeItemStr.DEVICE_INVERTER_JLINV_STR.equals(deviceType)){
										list.get(2).put("volt", bean.getIpv3());
										list.get(2).put("current", bean.getVpv3());
										list.get(2).put("watt", bean.getPpv3());
										list.get(3).put("volt", bean.getIpv4());
										list.get(3).put("current", bean.getVpv4());
										list.get(3).put("watt", bean.getPpv4());

										list.get(4).put("volt", bean.getIacr());
										list.get(4).put("current", bean.getVacr());
										list.get(4).put("watt", bean.getPacr());
										list.get(5).put("volt", bean.getIacs());
										list.get(5).put("current", bean.getVacs());
										list.get(5).put("watt", bean.getPacs());
										list.get(6).put("volt", bean.getIact());
										list.get(6).put("current", bean.getVact());
										list.get(6).put("watt", bean.getPact());
									}else {
										list.get(2).put("volt", bean.getIacr());
										list.get(2).put("current", bean.getVacr());
										list.get(2).put("watt", bean.getPacr());
										list.get(3).put("volt", bean.getIacs());
										list.get(3).put("current", bean.getVacs());
										list.get(3).put("watt", bean.getPacs());
										list.get(4).put("volt", bean.getIact());
										list.get(4).put("current", bean.getVact());
										list.get(4).put("watt", bean.getPact());
									}
									adapter.notifyDataSetChanged();
									} catch (Exception e) {
									e.printStackTrace();
								}
							}
							@Override
							public void error(String json) {
//								T.make(R.string.all_error);
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				@Override
				public void error(String json) {
					toast(R.string.all_error);
				}
			});

		
	}
	private void Setlisteners() {
	}

}
