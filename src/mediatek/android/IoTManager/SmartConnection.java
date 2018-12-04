package mediatek.android.IoTManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.activity.Gif2Activity;
import com.growatt.shinephone.activity.GifActivity;
import com.growatt.shinephone.activity.LoginActivity;
import com.growatt.shinephone.activity.MainActivity;
import com.growatt.shinephone.bean.OssDeviceDatalogBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.FragUtil;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetWifiInfo;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.view.RippleBackground;
import com.mylhyl.circledialog.CircleDialog;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.util.List;
import java.util.Map;

public class SmartConnection extends DemoBase {

	private IoTManagerNative IoTManager;
	private String mAuthString;
	private byte mAuthMode;
	private ImageView back;
	private EditText textview;
	private Button bt1;
	private Button bt2;
	private EditText et1;
	private String id;
	//	private windowDialog md;
	private TextView mode;
	private String ssid;
	private String type="";//100:从工具跳转；101：从oss跳转
	private String isNewWIFI = "0";
	private TextView tvConnectWifi;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			Mydialog.Dismiss();
			switch (msg.what) {
				case 102:
					if(!stop){
						getDataLogInfo(FragUtil.dataLogUrl);
					}
					break;
				case 105:
					timeCount--;
					if(timeCount < 0||btnStop.getVisibility()==View.INVISIBLE){
						handler.sendEmptyMessage(106);
						if(timeCount<0){
							tvTime.setVisibility(View.INVISIBLE);
							if ("1".equals(isNewWIFI)){
								jumpTo(Gif2Activity.class, false);
							}else{
								jumpTo(GifActivity.class, false);
							}
						}
					}else{
						handler.sendEmptyMessageDelayed(105, 1000);
						tvTime.setText(timeCount+getString(R.string.WifiNewtoolAct_time_s));
					}

					break;
				case 106:
					timeCount=TIME_COUNT;
					rippleBackground.stopRippleAnimation();
					bt2.setVisibility(View.VISIBLE);
					btnStop.setVisibility(View.INVISIBLE);
					IoTManager.StopSmartConnection();
					tvTime.setText(TIME_COUNT+getString(R.string.WifiNewtoolAct_time_s));
					break;
				default:
					break;
			}
		}
	};

	int num;//��ѯ�ɼ�������
	Intent intent;
	private RippleBackground rippleBackground;
	private TextView tvTime;
	private Button btnStop;
	private final int TIME_COUNT=200;
	int timeCount=TIME_COUNT;
	private InputMethodManager imm;
	private boolean stop;
	private WifiManager mWifiManager;
	private byte AuthModeOpen = 0x00;
	private byte AuthModeShared = 0x01;
	private byte AuthModeAutoSwitch = 0x02;
	private byte AuthModeWPA = 0x03;
	private byte AuthModeWPAPSK = 0x04;
	private byte AuthModeWPANone = 0x05;
	private byte AuthModeWPA2 = 0x06;
	private byte AuthModeWPA2PSK = 0x07;
	private byte AuthModeWPA1WPA2 = 0x08;
	private byte AuthModeWPA1PSKWPA2PSK = 0x09;
	private String mConnectedSsid;
	private String mConnectedPassword;
//	private String mAuthString;
//	private byte mAuthMode;
	private static final String TAG = "SmartConnection";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smartconnection);
		try {
			rippleBackground=(RippleBackground)findViewById(R.id.content);
			tvTime=(TextView)findViewById(R.id.centerImage);
			btnStop=(Button)findViewById(R.id.button_stop);
			IoTManager = new IoTManagerNative();
			intent=new Intent(Constant.Frag_Receiver);
			SetViews();
			SetListeners();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (IoTManager != null){
			IoTManager.StopSmartConnection();
		}
	}

	private void SetViews() {
		if(getIntent().getExtras().containsKey("type")){
			type=getIntent().getExtras().getString("type");
		}
		id=getIntent().getExtras().getString("id");
		ssid=getIntent().getExtras().getString("ssid");
//		mAuthString=getIntent().getExtras().getString("mAuthString");
//		mAuthMode=getIntent().getExtras().getByte("mAuthMode");
		back=(ImageView)findViewById(R.id.back);
		textview=(EditText)findViewById(R.id.textView1);
		mode=(TextView)findViewById(R.id.textView3);
		bt1=(Button)findViewById(R.id.button1);
		bt2=(Button)findViewById(R.id.button2);
		et1=(EditText)findViewById(R.id.editText1);
		tvConnectWifi=findViewById(R.id.tvConnectWifi);
		String tips=getString(R.string.dataloggers_dialog_connectwifi)+getString(R.string.m492配置wifi提示);
		SpannableString spannableString = new SpannableString(tips);
		int start=tips.lastIndexOf(getString(R.string.m492配置wifi提示));
		int end=start+getString(R.string.m492配置wifi提示).length();
		spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.connect_wifi_tips)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tvConnectWifi.setText(spannableString);
		textview.setText(ssid);
		mode.setText("Mode:"+mAuthString);
		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//Get Authentication mode of AP
		mWifiManager = (WifiManager) getApplicationContext().getSystemService (Context.WIFI_SERVICE);

		initWifiManager();
		//init smartconnection
		IoTManager.InitSmartConnection();
	}

	private void initWifiManager() {
		if(mWifiManager.isWifiEnabled()){
			WifiInfo WifiInfo = mWifiManager.getConnectionInfo();
			mConnectedSsid = WifiInfo.getSSID();
			int iLen = mConnectedSsid.length();

			if (iLen == 0)
			{
				return;
			}

			if (mConnectedSsid.startsWith("\"") && mConnectedSsid.endsWith("\""))
			{
				mConnectedSsid = mConnectedSsid.substring(1, iLen - 1);
			}
			//		mConnectedSsid = mConnectedSsid.replace('\"', ' ');
			//		mConnectedSsid = mConnectedSsid.trim();
//			mEditSSID.setText(mConnectedSsid);
			Log.d(TAG, "SSID = " + mConnectedSsid);

			List<ScanResult> ScanResultlist = mWifiManager.getScanResults();
			for (int i = 0, len = ScanResultlist.size(); i < len; i++) {
				ScanResult AccessPoint = ScanResultlist.get(i);

				if (AccessPoint.SSID.equals(mConnectedSsid))
				{
					boolean WpaPsk = AccessPoint.capabilities.contains("WPA-PSK");
					boolean Wpa2Psk = AccessPoint.capabilities.contains("WPA2-PSK");
					boolean Wpa = AccessPoint.capabilities.contains("WPA-EAP");
					boolean Wpa2 = AccessPoint.capabilities.contains("WPA2-EAP");

					if (AccessPoint.capabilities.contains("WEP"))
					{
						mAuthString = "OPEN-WEP";
						mAuthMode = AuthModeOpen;
						break;
					}

					if (WpaPsk && Wpa2Psk)
					{
						mAuthString = "WPA-PSK WPA2-PSK";
						mAuthMode = AuthModeWPA1PSKWPA2PSK;
						break;
					}
					else if (Wpa2Psk)
					{
						mAuthString = "WPA2-PSK";
						mAuthMode = AuthModeWPA2PSK;
						break;
					}
					else if (WpaPsk)
					{
						mAuthString = "WPA-PSK";
						mAuthMode = AuthModeWPAPSK;
						break;
					}

					if (Wpa && Wpa2)
					{
						mAuthString = "WPA-EAP WPA2-EAP";
						mAuthMode = AuthModeWPA1WPA2;
						break;
					}
					else if (Wpa2)
					{
						mAuthString = "WPA2-EAP";
						mAuthMode = AuthModeWPA2;
						break;
					}
					else if (Wpa)
					{
						mAuthString = "WPA-EAP";
						mAuthMode = AuthModeWPA;
						break;
					}

					mAuthString = "OPEN";
					mAuthMode = AuthModeOpen;

				}
			}

			Log.d(TAG, "Auth Mode = " + mAuthString);

//			mAuthModeView.setText(mAuthString);
		}
	}

	private void SetListeners() {
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(type.equals("1")){
					startActivity(new Intent(SmartConnection.this,LoginActivity.class));
					finish();
				}else if(type.equals("100")){
					finish();
				}else if ("101".equals(type)){
					finish();
				}else{
					startActivity(new Intent(SmartConnection.this,MainActivity.class));
					sendBroadcast(intent);
					finish();
				}

			}
		});

		bt1.setOnClickListener(new View.OnClickListener() {



			@Override
			public void onClick(View arg0) {
				initWifiManager();
				Map<String, Object> map = new GetWifiInfo(SmartConnection.this).Info();
//				if(map.get("mAuthString").toString().equals("")){
//					AlertDialog builder = new AlertDialog.Builder(SmartConnection.this).setTitle(R.string.all_prompt).setMessage("��������-��˽��Ȩ-Ӧ��Ȩ�޹���-������ǰAPP��λȨ�޲�������ʹ��(�����ʽ�ֻ�����Ȩ�޷�ʽ��ͬ������Լ��ֻ�����)").setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface arg0, int arg1) {
//							arg0.dismiss();
//						}
//					}).create();
//					builder.show();
//					return;
//				}
				if(map.size()>0){
					textview.setText(map.get("ssid").toString());
					mode.setText("Mode:"+map.get("mAuthString").toString());
					AlertDialog builder = new AlertDialog.Builder(SmartConnection.this).setTitle(R.string.all_prompt).setMessage(R.string.geographydata_obtain_ok).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							arg0.dismiss();
						}
					}).create();
					builder.show();
				}else{
					AlertDialog builder = new AlertDialog.Builder(SmartConnection.this).setTitle(R.string.all_prompt).setMessage(R.string.geographydata_obtain_no).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							arg0.dismiss();
						}
					}).create();
					builder.show();
				}
			}
		});

		bt2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String wifiname=textview.getText().toString().trim();
				String wifipwd=et1.getText().toString().trim();
				if(wifiname.equals("")){
					toast(R.string.wifitool_obtain_name);
					return;
				}
				if(wifipwd.equals("")){
					toast(R.string.wifitool_no_pwd);
					return;
				}
				//开始配置
				stop=false;
				//隐藏输入法
				if(imm==null){
					imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				}
				imm.hideSoftInputFromWindow(et1.getWindowToken(), 0);
				//开启动画
				rippleBackground.startRippleAnimation();
				bt2.setVisibility(View.INVISIBLE);
				btnStop.setVisibility(View.VISIBLE);
				tvTime.setVisibility(View.VISIBLE);
				handler.sendEmptyMessageDelayed(105, 1000);
				String url=new Urlsutil().getDatalogInfo+"&datalogSn="+id;
				if ("101".equals(type)){
					url = OssUrls.postOssSearchDevice();
				}
				ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
				if(wifi == State.CONNECTED||wifi==State.CONNECTING){
					getDataLogInfo(url);
					System.out.println(mAuthMode);
					IoTManager.StartSmartConnection(wifiname, wifipwd,"FF:FF:FF:FF:FF:FF",mAuthMode);
//					md=new windowDialog(WiFiNewtoolActivity.this);
//					md.setCancelable(true);
//					md.show();
				}else{
					toast(R.string.all_wifi_failed);
					return;
				}
			}
		});
		btnStop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				stop=true;
				num=0;
				tvTime.setVisibility(View.INVISIBLE);
				handler.sendEmptyMessage(106);
			}
		});

	}
	public void getDataLogInfo(final String url) {
		FragUtil.dataLogUrl=url;
		if ("101".equals(type)){//oss界面跳转：服务器地址为oss
			parseOssConfig(url);
		}else {//服务器地址为server
			parseServerConfig(url);
		}
	}

	private void parseServerConfig(String url) {
		if(num>=60||btnStop.getVisibility()==View.INVISIBLE){
//			handler.sendEmptyMessage(106);
			num=0;
			return;
		}
		GetUtil.get1(url, new GetUtil.GetListener() {
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					boolean lost=jsonObject.getBoolean("lost");
					isNewWIFI = jsonObject.get("isNewWIFI").toString();
					LogUtil.i(lost+";isNewWifi:"+isNewWIFI);
					if(lost==true){
						num++;
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								handler.sendEmptyMessage(102);
							}
						}, 5000);

					}else{
//						IoTManager.StopSmartConnection();
						handler.sendEmptyMessage(106);
						num=0;
						FragUtil.dataLogUrl=null;
						new CircleDialog.Builder(SmartConnection.this)
								.setWidth(0.7f)
								.setTitle(getString(R.string.dataloggers_add_success))
								.setText(getString(R.string.all_success_reminder))
								.setPositive(getString(R.string.all_ok), new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										if("1".equals(type)){
											MyControl.autoLogin(SmartConnection.this, Cons.regMap.getRegUserName(),Cons.regMap.getRegPassword());
										}else if ("101".equals(type)){
											finish();
										}else{
											startActivity(new Intent(SmartConnection.this,MainActivity.class));
											sendBroadcast(intent);
											finish();
										}
									}
								})
								.show();
					}
				} catch (Exception e) {e.printStackTrace();}
			}
			@Override
			public void error(String json) {}
		});
	}

	private void parseOssConfig(String url) {
		if(num>=60||btnStop.getVisibility()==View.INVISIBLE){
			num=0;
			return;
		}
		searchSnDownDeviceInfor(id,"",url,0,1);
	}
	/**
	 * 根据设备sn或者别名搜索设备列表信息
	 * @param deviceSn：设备序列号
	 * @param alias：别名
	 * @param url：服务器地址
	 * @param deviceType：设备类型
	 * @param page：
	 */
	private void searchSnDownDeviceInfor(final String deviceSn, final String alias, final String url, final int deviceType, final int page) {
		PostUtil.post(OssUrls.postOssSearchDevice(), new PostUtil.postListener() {
			@Override
			public void Params(Map<String, String> params) {
				params.put("deviceSn",deviceSn);
				params.put("alias",alias);
				params.put("serverAddr",OssUrls.ossCRUDUrl);
				params.put("deviceType",deviceType+"");
				params.put("page",page+"");
			}
			@Override
			public void success(String json) {
				parseDeviceInfor(json);
			}
			@Override
			public void LoginError(String str) {

			}
		});
	}
	private void parseDeviceInfor(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			int result = jsonObject.getInt("result");
			if (result == 1){
				JSONObject obj = jsonObject.getJSONObject("obj");
				int deviceType = obj.getInt("deviceType");
				switch (deviceType){
					case 0:
						JSONArray jsonArrDatalog = obj.getJSONArray("datalogList");
						if (jsonArrDatalog != null && jsonArrDatalog.length()>0){
							OssDeviceDatalogBean datalogBean = new Gson().fromJson(jsonArrDatalog.getJSONObject(0).toString(), OssDeviceDatalogBean.class);
							boolean lost = datalogBean.isLost();
							if(lost==true){
								num++;
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
										handler.sendEmptyMessage(102);
									}
								}, 5000);

							}else{
								handler.sendEmptyMessage(106);
								num=0;
								FragUtil.dataLogUrl=null;
								new CircleDialog.Builder(SmartConnection.this)
										.setWidth(0.7f)
										.setTitle(getString(R.string.dataloggers_add_success))
										.setText(getString(R.string.all_success_reminder))
										.setPositive(getString(R.string.all_ok), new View.OnClickListener() {
											@Override
											public void onClick(View v) {
												if(type.equals("1")){
													MyControl.autoLogin(SmartConnection.this, Cons.regMap.getRegUserName(),Cons.regMap.getRegPassword());
												}else if ("101".equals(type)){
													finish();
												}else{
													startActivity(new Intent(SmartConnection.this,MainActivity.class));
													sendBroadcast(intent);
													finish();
												}
											}
										})
										.show();
							}
						}
						break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public class windowDialog extends Dialog{
		private Context context;
		public windowDialog(Context context) {
			super(context,R.style.dialog1);
			this.context=context;
			//���ز����ļ�
			LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View  view=inflater.inflate(R.layout.window, null);
			Button bt1=(Button)view.findViewById(R.id.button1);
			bt1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					IoTManager.StopSmartConnection();
				}
			});
			setContentView(view);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if("1".equals(type)){
				startActivity(new Intent(SmartConnection.this,LoginActivity.class));
				finish();
			}else if ("101".equals(type)){
				finish();
			}else{
				startActivity(new Intent(SmartConnection.this,MainActivity.class));
				sendBroadcast(intent);
				finish();
			}

		}
		return super.onKeyDown(keyCode, event);
	}

}
