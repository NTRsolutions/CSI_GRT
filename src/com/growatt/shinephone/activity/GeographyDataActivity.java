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
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.GPRSutil;
import com.growatt.shinephone.util.GPRSutil.GPRSlisteners;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.UpdateplantUtil;
import com.mylhyl.circledialog.CircleDialog;

import org.xutils.common.util.LogUtil;

import java.util.Map;

public class GeographyDataActivity extends DemoBase {

	private TextView tv2;
	private TextView tv4;
	private TextView tv6;
	private TextView tv8;
	private RelativeLayout r1;
	private RelativeLayout r2;
	private RelativeLayout r3;
	private RelativeLayout r4;
	private TextView tv10;
	private RelativeLayout r5;
	private String[] GTMs=new String[24];
	private int a=-12;
	double lat = -1;//定位的纬度
	double lng = -1;//定位的经度
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener;
	private int clickType = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geography);
		initHeaderView();
		SetViews();
		SetListeners();
		initLocation();
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
		setHeaderTitle(headerView,getString(R.string.geographydata_title));
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
	private void SetViews() {
		tv2=(TextView)findViewById(R.id.textView2);
		tv4=(TextView)findViewById(R.id.textView4);
		tv6=(TextView)findViewById(R.id.textView6);
		tv8=(TextView)findViewById(R.id.textView8);
		tv10=(TextView)findViewById(R.id.textView10);
		r1=(RelativeLayout)findViewById(R.id.power_r1);
		r2=(RelativeLayout)findViewById(R.id.power_r2);
		r3=(RelativeLayout)findViewById(R.id.power_r3);
		r4=(RelativeLayout)findViewById(R.id.power_r4);
		r5=(RelativeLayout)findViewById(R.id.power_r5);
		tv2.setText(PlantAdminActivity.powerdata.getCountry());
		tv4.setText(PlantAdminActivity.powerdata.getCity());
		tv6.setText(PlantAdminActivity.powerdata.getTimezoneText());
		tv8.setText(PlantAdminActivity.powerdata.getPlant_lng());
		tv10.setText(PlantAdminActivity.powerdata.getPlant_lat());
		for (int i = 0; i < 24; i++) {
			if (a > 0){
				GTMs[i] = "+"+a;
			} else {
				GTMs[i]=a+"";
			}
			a++;
			if(a==0){
				a++;
			}
		}
	}
	private void SetListeners() {
		r1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivityForResult(new Intent(GeographyDataActivity.this,CountryActivity.class),101);
			}
		});
		r2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				String s=tv2.getText().toString();
//				if(TextUtils.isEmpty(s)){
//					toast(R.string.geographydata_select);
//					return;
//				}
//				Intent intent=new Intent(GeographyDataActivity.this,CityActivity.class);
//				Bundle bundle=new Bundle();
//				bundle.putString("country",s);
//				intent.putExtras(bundle);
//				startActivityForResult(intent,102);
				Mydialog.Show(GeographyDataActivity.this);
				clickType = 1;
				getMyLocation();
			}
		});
		r3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showupdate(R.string.geographydata_changetimezone);
			}
		});
		r4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Mydialog.Show(GeographyDataActivity.this);
				clickType = 2;
				getMyLocation();
//				GetGprs();
			}
		});
		r5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Mydialog.Show(GeographyDataActivity.this);
				clickType = 2;
				getMyLocation();
//				GetGprs();
			}
		});
	}

	private void getMyLocation() {
		mLocationClient.registerLocationListener(myListener);
		mLocationClient.start();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==101){
			if(resultCode==1){
				String result=data.getStringExtra("country");
				
					tv2.setText(result);
				
				PlantAdminActivity.powerdata.setCountry(result);
				updateplant();
			}
		}else{
			if(resultCode==1){
				String result=data.getStringExtra("city");
				tv4.setText(result);
				PlantAdminActivity.powerdata.setCity(result);
				updateplant();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	public void showupdate(int title){
		AlertDialog.Builder builder = new Builder(GeographyDataActivity.this);
		builder.setTitle(title).setItems(GTMs, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int position) {
				arg0.dismiss();
				PlantAdminActivity.powerdata.setTimeZone(GTMs[position]);
				updateplant();
			}
		}).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
				
			}
		}).create();
		
		builder.show();
	}
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Mydialog.Dismiss();
				String str=(String) msg.obj;
				if(str.equals("200")){
					toast(R.string.all_success);
					tv6.setText("GMT"+PlantAdminActivity.powerdata.getTimeZone());
				}else if(str.equals("501")){
					toast(R.string.geographydata_change_no_data);
				}else if(str.equals("502")){
					toast(R.string.geographydata_change_no_namerepetition);
				}else if(str.equals("503")){
					toast(R.string.geographydata_change_no_picture);
				}else if(str.equals("504")){
					toast(R.string.geographydata_change_no_map);
				}else if ("701".equals(str)){
					toast(R.string.m7);
				}
				break;
			case 1:
				Mydialog.Dismiss();
				toast(R.string.geographydata_change_no_data);
				break;
			default:
				break;
			}
		};
	};
	public void updateplant(){
		UpdateplantUtil.updateplant(GeographyDataActivity.this, handler);
	}
	public void GetGprs(){
		Mydialog.Show(GeographyDataActivity.this, R.string.geographydata_obtain_longandlat);
		GPRSutil.Gprs(GeographyDataActivity.this, new  GPRSlisteners() {
			
			@Override
			public void success(Map<String, Object> map) {
				Mydialog.Dismiss();
				tv10.setText(map.get("lat").toString());
				tv8.setText(map.get("lon").toString());
				PlantAdminActivity.powerdata.setPlant_lat(map.get("lon").toString());
				PlantAdminActivity.powerdata.setPlant_lng(map.get("lat").toString());
				AlertDialog.Builder builder = new Builder(GeographyDataActivity.this);
				builder.setTitle(R.string.geographydata_obtain_ok).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
						updateplant();
					}
				}).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						dialog.dismiss();
					}
				}).create();
				builder.show();
			}
			@Override
			public void error() {
				AlertDialog.Builder builder = new Builder(GeographyDataActivity.this);
				builder.setTitle(R.string.geographydata_obtain_no).setMessage(R.string.geographydata_obtain_again).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						GetGprs();
					}
				}).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create();
				builder.show();
				
			}
		});
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
						switch (clickType){
							case 1://修改城市
								tv4.setText(city);
								PlantAdminActivity.powerdata.setCity(city);
								updateplant();
								break;
							case 2:
								tv10.setText(lat + "");
								tv8.setText(lng + "");
								PlantAdminActivity.powerdata.setPlant_lat(lat + "");
								PlantAdminActivity.powerdata.setPlant_lng(lng + "");
								updateplant();
								break;
						}
						if (mLocationClient.isStarted()) {
							mLocationClient.stop();
//						mLocationClient.unRegisterLocationListener(myListener);
						}
					}else if(code == 167){//定位权限被关闭
						new CircleDialog.Builder(GeographyDataActivity.this)
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
							AlertDialog.Builder builder = new Builder(GeographyDataActivity.this);
							builder.setTitle(R.string.geographydata_obtain_no).setMessage(R.string.geographydata_obtain_again).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int arg1) {
									dialog.dismiss();
									Mydialog.Show(GeographyDataActivity.this,R.string.register_gain_longandlat);
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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		GPRSutil.stopGprs();
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		GPRSutil.stopGprs();
		super.onDestroy();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
