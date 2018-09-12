package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.RizhiAdapter;
import com.growatt.shinephone.bean.Time;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.T;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.util.v2.DeviceTypeItemStr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RizhiActivity extends DoActivity {

	private List<Map<String, Object>>list;
	private List<Map<String, Object>> listx;
	private ListView listview;
	private RizhiAdapter adapter;
	private String plant;
	private String type;
	private ImageView emptyView;
	protected String errorName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rizhi);
		Bundle bundle=getIntent().getExtras();
		plant=bundle.getString("plant");
		type=bundle.getString("type");
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
		setHeaderTitle(headerView,getString(R.string.rizhi_title));
	}
	private void SetViews() {
		listview=(ListView)findViewById(R.id.listView1);
		emptyView=(ImageView)findViewById(R.id.emptyView);
		try {
			if(getLanguage()==0){
				emptyView.setImageResource(R.drawable.rizhi_empthview);
			}else{
				emptyView.setImageResource(R.drawable.rizhi_empthview_en);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void SetListeners() {
		Mydialog.Show(RizhiActivity.this,"");
		//默认为储能机
		String url = "";
		if("inverter".equals(type)){
			url = new Urlsutil().getInverterAlarm+"&inverterId="+plant+"&pageNum=1"+"&pageSize=50";
		}
		else if (DeviceTypeItemStr.DEVICE_INVERTER_MAX_STR.equals(type)){
			url = new Urlsutil().getInverterAlarm_max+"&maxId="+plant+"&pageNum=1"+"&pageSize=50";
		}
		else if (DeviceTypeItemStr.DEVICE_INVERTER_JLINV_STR.equals(type)){
			url = new Urlsutil().getInverterAlarm_jlinv+"&jlinvId="+plant+"&pageNum=1"+"&pageSize=50";
		}
		else if ("mix".equals(type)){
			url = new Urlsutil().getMixAlarm+"&mixId="+plant+"&pageNum=1"+"&pageSize=50";
		}
		else{
			url = new Urlsutil().getStorageAlarm+"&storageId="+plant+"&pageNum=1"+"&pageSize=50";
		}
		getrizhi(url);
				listview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
//						if(Cons.addQuestion){
//						Intent intent=new Intent(RizhiActivity.this, PutinActivity.class);
//						Bundle bundle=new Bundle();
//						bundle.putString("title", title.getText().toString());
//						bundle.putString("deviceType", list.get(position).get("deviceType").toString());
//						bundle.putString("deviceSerialNum", list.get(position).get("deviceSerialNum").toString());
//						intent.putExtras(bundle);
//						startActivity(intent);
//						}
						//跳转至常见问题
						String eventId=list.get(position).get("eventId").toString();
						errorName="abcdefg";
						if(getLanguage()==0){
							errorName="常见错误代码";
						}else{
						if("31".equals(eventId)){
							errorName="AC F Outrange";
						}else if("25".equals(eventId)){
							errorName="No AC Connection";
						}else if("27".equals(eventId)){
							errorName="Residual I High";
						}else if("26".equals(eventId)){
							errorName="PV Isolation Low";
						}else if("32".equals(eventId)){
							errorName="Module Hot";
						}else if("28".equals(eventId)){
							errorName="Output High DCI";
						}else if("30".equals(eventId)){
							errorName="AC V Outrange";
						}else{
							errorName="Error xxx";
						}
						}
						//获取常见问题标题
						if(listx!=null&&listx.size()>0){
							for(int i=0;i<listx.size();i++){
								if(listx.get(i).get("title").toString().contains(errorName)){
									//跳转
									getQuestionDetial(listx.get(i).get("id").toString());
									break;
								}
							}
						}else{
						Mydialog.Show(RizhiActivity.this,"");
						GetUtil.get(new Urlsutil().getUsualQuestionList+"&language="+AppUtils.getLocale(), new GetListener() {
							
							@Override
							public void success(String json) {
								Mydialog.Dismiss();
								try {
									if(json.length()<15){
										T.make(R.string.all_data_inexistence,RizhiActivity.this);
										return;
									}
									JSONArray jsonArray=new JSONArray(json);
									listx=new ArrayList<Map<String,Object>>();
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject jsonObject=jsonArray.getJSONObject(i);
										Map<String, Object>map=new HashMap<String, Object>();
										map.put("id", jsonObject.get("id").toString());
										map.put("title", jsonObject.get("title").toString());
										map.put("content", jsonObject.get("content").toString());
										listx.add(map);
									}
									for(int i=0;i<listx.size();i++){
										if(listx.get(i).get("title").toString().contains(errorName)){
											//跳转
											getQuestionDetial(listx.get(i).get("id").toString());
											break;
										}
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
								
							}
							
							@Override
							public void error(String json) {
								Mydialog.Dismiss();
							}
						});
						}
					}
				});
	}
	//通过id获取常见问题详情
		public void getQuestionDetial(String id){
			Mydialog.Show(RizhiActivity.this,"");
			GetUtil.get(new Urlsutil().getUsualQuestionInfo+"&id="+id+"&language="+AppUtils.getLocale(), new GetListener() {
				
				@Override
				public void success(String json) {
					Mydialog.Dismiss();
					try {
						JSONObject jsonObject=new JSONObject(json);
						Intent intent=new Intent(RizhiActivity.this,CommondataActivity.class);
						Bundle bundle=new Bundle();
						bundle.putString("id", jsonObject.get("id").toString());
						bundle.putString("title", jsonObject.get("title").toString());
						bundle.putString("content", jsonObject.get("content").toString());
						intent.putExtras(bundle);
						startActivity(intent);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				
				@Override
				public void error(String json) {
					
				}
			});
		}
	public void getrizhi(String url){
		GetUtil.get(url, new GetListener() {
			@Override
			public void success(String json) {
				try {
					JSONArray jsonArray=new JSONArray(json);
					if(json.length()<20){
						toast(R.string.rizhi_failed);
					}else{
					list=new ArrayList<Map<String,Object>>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject=jsonArray.getJSONObject(i);
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("dataLogSerialNum", jsonObject.opt("dataLogSerialNum").toString());
						map.put("plantName", jsonObject.opt("plantName").toString());
						map.put("adminRead", jsonObject.opt("adminRead").toString());
						map.put("readUser", jsonObject.opt("readUser").toString());
						map.put("commonRead", jsonObject.opt("commonRead").toString());
						map.put("inverterEvent", jsonObject.opt("inverterEvent").toString());
						map.put("deviceSerialNum", jsonObject.opt("deviceSerialNum").toString());
						map.put("deviceLocation", jsonObject.opt("deviceLocation").toString());
						map.put("dataLogEvent", jsonObject.opt("dataLogEvent").toString());
						map.put("dataLogLocation", jsonObject.opt("dataLogLocation").toString());
						map.put("deviceAlias", jsonObject.opt("deviceAlias").toString());
						map.put("id", jsonObject.opt("id").toString());
						map.put("occurTime", jsonObject.opt("occurTime").toString());
						map.put("dataLogAlias", jsonObject.opt("dataLogAlias").toString());
						map.put("userID", jsonObject.opt("userID").toString());
						map.put("eventId", jsonObject.opt("eventId").toString());
						map.put("event", jsonObject.opt("event").toString());
						map.put("deviceType", jsonObject.opt("deviceType").toString());
						map.put("eventName", jsonObject.opt("eventName").toString());
						map.put("language", jsonObject.opt("language").toString());
						map.put("mailContent", jsonObject.opt("mailContent").toString());
						map.put("superRead", jsonObject.opt("superRead").toString());
						JSONObject jsonObject2=jsonObject.getJSONObject("time");
						Time time=new Time();
						time.setDate(jsonObject2.opt("date").toString());
						time.setDay(jsonObject2.opt("day").toString());
						time.setYear(jsonObject2.opt("year").toString());
						time.setHours(jsonObject2.opt("hours").toString());
						time.setMinutes(jsonObject2.opt("minutes").toString());
						time.setMonth(jsonObject2.opt("month").toString());
						time.setSeconds(jsonObject2.opt("seconds").toString());
						time.setTimezoneOffset(jsonObject2.opt("timezoneOffset").toString());
						time.setTime(jsonObject2.opt("time").toString());
						map.put("time", time);
						list.add(map);
					}
					adapter=new RizhiAdapter(RizhiActivity.this, list);
					listview.setEmptyView(emptyView);
					listview.setAdapter(adapter);
					
					}
					Mydialog.Dismiss();
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
}
