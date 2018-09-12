package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.RegisterMap;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.GPRSutil;
import com.growatt.shinephone.util.GPRSutil.GPRSlisteners;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;
import com.mylhyl.circledialog.CircleDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.net.URLEncoder;
import java.util.Map;

public class CountryandCityActivity extends DemoBase {

	private TextView tv1;
	private TextView tv2;
//	private TextView textview_regLng;
	double lat = -1;//定位的纬度
    double lng = -1;//定位的经度
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_countryand_city);
		initHeaderView();
		SetViews();
		initLocation();
		SetListeners();
	}

	@Override
	public void onStop() {

		super.onStop();
	}

	private void initLocation() {
		mLocationClient = new LocationClient(getApplicationContext());
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
		);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//		option.setCoorType("WGS84");//可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000 * 10;
		option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(true);//可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
		myListener = new MyLocationListener();
	}

	private View headerView;
	private void initHeaderView() {
		headerView = findViewById(R.id.headerView);
		setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Cons.regMap=new RegisterMap();
				finish();
			}
		});
		setHeaderTitle(headerView,getString(R.string.countryandcity_title));
		setHeaderTvTitle(headerView,getString(R.string.countryandcity_next),new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				next();
			}
		});
	}
	private void SetViews() {
		tv1=(TextView)findViewById(R.id.editText3);
		tv2=(TextView)findViewById(R.id.editText4);
	}
	public void next(){
			String country=tv1.getText().toString().trim();
			if(TextUtils.isEmpty(country)||getString(R.string.countryandcity_select).equals(country)){
				toast(R.string.countryandcity_must_country);
				return;
			}
			Cons.regMap.setRegCountry(country);
			//				startActivity(new Intent(CountryandCityActivity.this,RegisterActivity.class));
			Mydialog.Show(CountryandCityActivity.this, "");
			SqliteUtil.time((System.currentTimeMillis()+500000)+"");
			try {
				GetUtil.get(new Urlsutil().getServerUrl+"&country="+URLEncoder.encode(Cons.regMap.getRegCountry(),"UTF-8"), new GetListener() {

					@Override
					public void success(String json) {
						Mydialog.Dismiss();
						try {
							JSONObject jsonObject=new JSONObject(json);
							String success=jsonObject.get("success").toString();
							if(success.equals("true")){
								String msg=jsonObject.getString("server");
								jumpRegister(msg,jsonObject.getString("customerCode"));
							}else {
								jumpRegister(Urlsutil.url_host,"1");
							}
						} catch (JSONException e) {
							e.printStackTrace();
							jumpRegister(Urlsutil.url_host,"1");
						}
					}

					@Override
					public void error(String json) {
						jumpRegister(Urlsutil.url_host,"1");
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				jumpRegister(Urlsutil.url_host,"1");
			}

	}
	private void jumpRegister(String url,String code){
		Cons.url=url;
		SqliteUtil.url(url);
		Intent intent=new Intent(CountryandCityActivity.this,RegisterActivity.class);
		Bundle bundle=new Bundle();
		bundle.putString("code", code);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	private void SetListeners() {
		//获取经纬度
		tv2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				GetGprs();
				Mydialog.Show(CountryandCityActivity.this,R.string.register_gain_longandlat);
				getMyLocation();
			}
		});
		tv1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivityForResult(new Intent(CountryandCityActivity.this,CountryActivity.class),101);
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==101){
			if(resultCode==1){
				String result=data.getStringExtra("country");
				if(getString(R.string.Country_china).equals(result)){
					result="China";
				}
				if(!TextUtils.isEmpty(result)){
					Cons.regMap.setRegCountry(result);
					tv1.setText(result);
				}
			}
		}else{
			if(resultCode==1){
				String result=data.getStringExtra("city");
				Cons.regMap.setRegCity(result);
				tv2.setText(result);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void GetGprs(){
		Mydialog.Show(CountryandCityActivity.this,R.string.register_gain_longandlat);
		GPRSutil.Gprs(CountryandCityActivity.this, new GPRSlisteners() {
			
			@Override
			public void success(Map<String, Object> map) {
				Mydialog.Dismiss();
				String a=getResources().getString(R.string.all_long);
				String b=getResources().getString(R.string.all_lat);
				//根据经纬度获取城市
				String city=String.valueOf(map.get("city"));
				tv2.setText(city+":"+a+":"+"\\n"+map.get("lon")+"  "+b+":"+map.get("lat"));
				Cons.regMap.setRegLng(map.get("lon").toString());
				Cons.regMap.setRegLat(map.get("lat").toString()); 
				if(!TextUtils.isEmpty(city)&&!"null".equals(city)){
				  Cons.regMap.setRegCity(city);
				}
			}
			@Override
			public void error() {
				try {
					AlertDialog.Builder builder = new Builder(CountryandCityActivity.this);
					builder.setTitle(R.string.geographydata_obtain_no).setMessage(R.string.geographydata_obtain_again).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							GetGprs();
						}
					}).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							dialog.dismiss();
						}
					}).create();
					builder.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	@Override
	protected void onDestroy() {
		GPRSutil.stopGprs();
		super.onDestroy();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Cons.regMap=new RegisterMap();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	private void getMyLocation() {
		mLocationClient.registerLocationListener(myListener);
        mLocationClient.start();
    }
	public class MyLocationListener implements BDLocationListener {
		 
        @Override
        public void onReceiveLocation(BDLocation location) {
			if (null != location){
				Message msg = Message.obtain();
				msg.what = 100;
				msg.obj = location;
				locationHandler.sendMessage(msg);
			}
		}
		@Override
		public void onConnectHotSpotMessage(String s, int i) {
		}
	}
	Handler locationHandler = new Handler(Looper.getMainLooper()){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case 100:
				BDLocation location = (BDLocation) msg.obj;
					//获取定位结果
					StringBuffer sb = new StringBuffer(256);
					sb.append("time : ");
					sb.append(location.getTime());    //获取定位时间
					sb.append("\nerror code : ");
					sb.append(location.getLocType());    //获取类型类型
					sb.append("\nlatitude : ");
					sb.append(location.getLatitude());    //获取纬度信息
					sb.append("\nlontitude : ");
					sb.append(location.getLongitude());    //获取经度信息
					sb.append("\nradius : ");
					sb.append(location.getRadius());    //获取定位精准度
					LogUtil.i("定位："+ Thread.currentThread().getName()+sb.toString());
					Mydialog.Dismiss();
					int code = location.getLocType();
					if (code == 61 || code == 66 || code == 161) {
						String city=location.getCity();
						lat = location.getLatitude();
						lng = location.getLongitude();
						Cons.regMap.setRegLng(lng+"");
						Cons.regMap.setRegLat(lat+"");
						if(!TextUtils.isEmpty(city)&&!"null".equals(city)){
							Cons.regMap.setRegCity(city);
							tv2.setText(city+"\n"+"(lat:"+lat+";lng:"+lng+")");
						}else{
							tv2.setText("(lat:"+lat+";lng:"+lng+")");
						}
						if (mLocationClient.isStarted()) {
							mLocationClient.stop();
//						mLocationClient.unRegisterLocationListener(myListener);
						}
					}else if(code == 167){//定位权限被关闭
						new CircleDialog.Builder(CountryandCityActivity.this)
								.setWidth(0.7f)
								.setTitle(getString(R.string.reminder))
								.setText(getString(R.string.utf_open_gprs))
								.setPositive(getString(R.string.action_settings), new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										Intent intent = new Intent();
										intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
										Uri uri = Uri.fromParts("package", getPackageName(), null);
										intent.setData(uri);
										startActivity(intent);
									}
								})
								.show();
					} else {
						//定位失败
						try {
							AlertDialog.Builder builder = new Builder(CountryandCityActivity.this);
							builder.setTitle(R.string.geographydata_obtain_no).setMessage(R.string.geographydata_obtain_again).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int arg1) {
									dialog.dismiss();
									Mydialog.Show(CountryandCityActivity.this,R.string.register_gain_longandlat);
									if (!mLocationClient.isStarted()){
										mLocationClient.start();
									}
//								getMyLocation();
								}
							}).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int arg1) {
									dialog.dismiss();
									if (mLocationClient.isStarted()){
										mLocationClient.stop();
									}
								}
							}).create();
							builder.show();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				break;
			}
		}
	};
}
