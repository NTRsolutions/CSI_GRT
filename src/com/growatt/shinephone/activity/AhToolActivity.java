package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.m30.wifi.PublicClass;
import com.example.m30.wifi.PublicClass.ProcessStage;
import com.example.m30.wifi.PublicClass.Results;
import com.example.m30.wifi.UdpComm;
import com.example.m30.wifi.UdpComm.UdpCommListener;
import com.example.m30.wifi.WiFiAdmin;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Position;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class AhToolActivity extends DoActivity implements OnClickListener {

	private EditText wifipwd_text = null;
	private CheckBox wifipwd_checkbox = null;
	private CheckBox wifi_modify_disptext = null;
	private TableLayout wifi_modify_ly = null;

	private EditText txtRouterSSID = null;
	private Button btnSelectRouter = null;	
	private EditText txtRouterPwd = null;	
	private CheckBox cbxDispRouterPwd = null;

	private Button btnSetingPara = null;
	private TextView txtUdpStatus = null;
	int type;
	private ProcessStage mProcessStage;
	private String mWifiPwd = new String("");
	private String mRouterPwd = new String("");
	private WiFiAdmin mAutoWiFiAdmin = null;
	private IntentFilter mIntentFilter = null;
	private HashMap<String,String> WiFiMap = null; 
	private HashMap<String,String> LuyouMap= null;

	private Timer mTimer = null;
	private TimerTask mTimerTask = null;
	private Handler mHandler = null;

	private UdpComm mUdpComm = null;
	Thread thread = null;
	boolean flag = true;
	private String ssid;
	private String WifiPwd;
	private String et1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ah_tool);
		initHeaderView();
		Bundle mBundle = getIntent().getExtras();
		ssid = mBundle.getString("ssid");
		WifiPwd = mBundle.getString("WifiPwd");
		et1 = mBundle.getString("et1");
		txtRouterSSID = (EditText) findViewById(R.id.routerssid_text);
		btnSelectRouter = (Button) findViewById(R.id.router_button);
		txtRouterPwd = (EditText) findViewById(R.id.routerpwd_text);
		txtRouterPwd.setText(ssid);
		cbxDispRouterPwd = (CheckBox) findViewById(R.id.routerpwd_checkbox);
		btnSetingPara = (Button) findViewById(R.id.setting_button);
		txtUdpStatus = (TextView) findViewById(R.id.udp_disptext);
		wifipwd_text = (EditText) findViewById(R.id.wifipwd_text);
		wifipwd_text.setText(WifiPwd);
		wifipwd_checkbox = (CheckBox) findViewById(R.id.wifipwd_checkbox);
		wifi_modify_disptext = (CheckBox) findViewById(R.id.wifi_modify_disptext);
		wifi_modify_ly = (TableLayout) findViewById(R.id.wifi_modify_ly);
		WiFiMap = new HashMap<String,String>();
		LuyouMap = new HashMap<String,String>();
		WiFiMap.put("SSID", ssid);
		WiFiMap.put("WifiPwd", WifiPwd);
		LuyouMap.put("et1", et1);	
		mIntentFilter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		mIntentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
		btnSelectRouter.setOnClickListener(this);
		btnSetingPara.setOnClickListener(this);
		txtRouterPwd.setOnClickListener(this);
		cbxDispRouterPwd.setOnClickListener(this);
		wifipwd_text.setOnClickListener(this);
		wifipwd_checkbox.setOnClickListener(this);
		wifi_modify_disptext.setOnClickListener(this);
		mAutoWiFiAdmin =  new WiFiAdmin(this,PublicClass.START_WIFI);
		mAutoWiFiAdmin.openNetCard();
		mHandler = new ServiceHandler();
		mUdpComm = new UdpComm();
		mUdpComm.setListener(new UdpClient());
		mUdpComm.open();
		startTimer();
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
		setHeaderTitle(headerView,getString(R.string.ahtool_title));
	}
	@Override
	protected void onDestroy() {
		closeTimer();
		CloseUdpComm();
		super.onDestroy();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.router_button:
			selectRouter();
			break;
		case R.id.setting_button:
			setParameter();
			break;
		case R.id.routerpwd_text:
			setRouterpwd();
			break;	
		case R.id.routerpwd_checkbox:
			chkRouterpwd();
			break;
		case R.id.wifipwd_text:
			setWifipwd();
			break;
		case R.id.wifipwd_checkbox:
			chkWifipwd();
			break;
		case R.id.wifi_modify_disptext:
			modifyWifiPwd();
			break;
		default:
			break;
		}
	}
	private void selectRouter() {
		Intent mIntent = new Intent();
		mIntent.setClass(this,WIFIlistActivity.class);
		Bundle mBundle = new Bundle();
		mBundle.putString("Data",PublicClass.START_ROUTER);
		mIntent.putExtras(mBundle);
		startActivityForResult(mIntent, PublicClass.REQ_SELECT_ROUTER);
	}
	private void setParameter() {
		WifiInfo mcurrentWifiInfo = mAutoWiFiAdmin.getCurrentInfo();
		if(mcurrentWifiInfo == null){
			txtUdpStatus.setTextColor(getResources().getColor(R.color.red));
			txtUdpStatus.setText(R.string.ahtool_wifi_failed);
			return;
		}else if(mcurrentWifiInfo.getSSID() == null){
			txtUdpStatus.setTextColor(getResources().getColor(R.color.red));
			txtUdpStatus.setText(R.string.ahtool_wifi_failed);
			return;

		}
		if(LuyouMap.isEmpty()){
			txtUdpStatus.setTextColor(getResources().getColor(R.color.red));
			txtUdpStatus.setText(R.string.ahtool_wifi_blank);
			return;
		}

		mRouterPwd = txtRouterPwd.getText().toString().trim();
		if(txtRouterPwd.isEnabled()){
			if(mRouterPwd.length() == 0){
				txtUdpStatus.setTextColor(getResources().getColor(R.color.red));
				txtUdpStatus.setText(R.string.ahtool_wifipwd_blank);
				return;
			}

			if(mRouterPwd.length() < 8){
				txtUdpStatus.setTextColor(getResources().getColor(R.color.red));
				return;
			}
		}		

		txtUdpStatus.setTextColor(Color.parseColor("#00ff00"));
		txtUdpStatus.setText(R.string.ahtool_wifi_start);
		mProcessStage = ProcessStage.StartScanModule;
	}

	private void setRouterpwd() {
		Editable mPwd = txtRouterPwd.getText();
		Selection.setSelection(mPwd, mPwd.length());
	}

	private void setWifipwd() {
		Editable mPwd = wifipwd_text.getText();
		Selection.setSelection(mPwd, mPwd.length());
	}

	private void chkRouterpwd() {
		if(cbxDispRouterPwd.isChecked()){
			txtRouterPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}else{
			txtRouterPwd.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}

		setRouterpwd();
	}

	private void modifyWifiPwd() {
		if(wifi_modify_disptext.isChecked()){
			wifi_modify_ly.setVisibility(View.VISIBLE);
		}else{
			wifi_modify_ly.setVisibility(View.GONE);
		}
	}

	private void chkWifipwd() {
		if(wifipwd_checkbox.isChecked()){
			wifipwd_text.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
		}else{
			wifipwd_text.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
		}

		setWifipwd();
	}

	private void CloseUdpComm() {

		startThread(PublicClass.CMD_EXIT_CMD);
		mProcessStage = ProcessStage.Complete;
		mUdpComm.close();
	}


	//������ʱ��
	public void startTimer() {
		if (mTimer == null) {

			mTimer = new Timer();
			mTimerTask = new ServiceTimerTask();
			mTimer.schedule(mTimerTask, 500, 2000);

			mHandler.sendEmptyMessage(ProcessStage.TimerStart.ordinal());
		}
	}

	//ֹͣ��ʱ��
	public void closeTimer() {

		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		if (mTimerTask != null) {
			mTimerTask = null;
		}

		mHandler.sendEmptyMessage(ProcessStage.TimerClose.ordinal());
	}

	private class ServiceTimerTask extends TimerTask{

		@Override
		public void run() {

			if(mProcessStage == null){
				return;
			}
			if((mProcessStage.ordinal() > ProcessStage.StartScanModule.ordinal())
					&&(mProcessStage != ProcessStage.Complete)){
				return;
			}

			if(mProcessStage == ProcessStage.StartScanModule){
				mUdpComm.close();

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mUdpComm.open();
				mUdpComm.send(PublicClass.CMD_SCAN_MODULE);
				return;
			}
			mHandler.sendEmptyMessage(PublicClass.ProcessStage.chkWifiStatus.ordinal());
		}
	}

	private class ServiceHandler extends Handler{
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what >= ProcessStage.values().length){
				return;
			}
			switch (ProcessStage.values()[msg.what]) {

			case EndScanModule:
				txtUdpStatus.setTextColor(getResources().getColor(R.color.light_blue));
				txtUdpStatus.setText("");

				startThread(PublicClass.CMD_ENTER_CMD);
				startThread(PublicClass.CMD_QUERY_WSSSID);

				mProcessStage = ProcessStage.StartQuerySSID;
				break;

			case EndQuerySSID:
				txtUdpStatus.setTextColor(getResources().getColor(R.color.light_blue));
				String strSSIDData = (String)msg.obj;
				if(LuyouMap.get("SSID").equals(strSSIDData)){
					txtUdpStatus.setText(R.string.ahtool_wifi_checkdata);
					startThread(PublicClass.CMD_QUERY_WANN);
					mProcessStage = ProcessStage.StartQueryStatus;
				}else{
					txtUdpStatus.setText(R.string.ahtool_wifi_setsidd);
					String strTemp = LuyouMap.get("SSID");
					final String strCmd = String.format(PublicClass.CMD_SET_WSSSID,strTemp);
					startThread(strCmd);
					mProcessStage = ProcessStage.StartSetSSID;
				}

				break;

			case EndQueryStatus:
				txtUdpStatus.setTextColor(getResources().getColor(R.color.light_blue));
				txtUdpStatus.setText(R.string.ahtool_wifi_checkdata);

				//���IP�ǲ�����Ч��
				String strStatusData = (String)msg.obj;
				String[] arrData = strStatusData.split(",");
				//�����Ч����ֱ�����ã����������ʾ
				if(arrData[1].equals("0.0.0.0")){
					String strTemp = LuyouMap.get("SSID");
					String strCmd = String.format(PublicClass.CMD_SET_WSSSID,strTemp);
					startThread(strCmd);
					mProcessStage = ProcessStage.StartSetSSID;
				}else{
					Builder ConfirmDialog = new AlertDialog.Builder(AhToolActivity.this);

					ConfirmDialog.setTitle(R.string.all_prompt);
					ConfirmDialog.setIcon(android.R.drawable.ic_dialog_info);
					ConfirmDialog.setMessage(R.string.ahtool_wifi_promptdata);
					ConfirmDialog.setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							txtUdpStatus.setTextColor(getResources().getColor(R.color.light_blue));
							txtUdpStatus.setText(R.string.ahtool_wifi_setsidd);

							String strTemp = LuyouMap.get("SSID");
							String strCmd = String.format(PublicClass.CMD_SET_WSSSID,strTemp);
							startThread(strCmd);
							mProcessStage = ProcessStage.StartSetSSID;
						}
					});

					ConfirmDialog.setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();

							txtUdpStatus.setTextColor(getResources().getColor(R.color.light_blue));
							txtUdpStatus.setText(R.string.all_no);
							CloseUdpComm();
						}
					});

					ConfirmDialog.create();
					ConfirmDialog.show();
				}

				break;

			case EndSetSSID:
				if (msg.arg1 >= Results.values().length){
					txtUdpStatus.setTextColor(getResources().getColor(R.color.red));
					txtUdpStatus.setText(R.string.ahtool_wifi_falied_retry);
					CloseUdpComm();
					break;
				}
				if(Results.values()[msg.arg1] == Results.Fail){
					txtUdpStatus.setTextColor(getResources().getColor(R.color.red));
					txtUdpStatus.setText(R.string.ahtool_wifi_falied_numberenough);

					CloseUdpComm();
					break;
				}

				txtUdpStatus.setTextColor(getResources().getColor(R.color.light_blue));
				txtUdpStatus.setText(R.string.ahtool_wifi_setsidd_ok);

				//�ж���û�м���
				String strTemp = LuyouMap.get("SEC");

				if(null != strTemp && strTemp.length() != 0){
					strTemp = strTemp.replace("_", ",") + "," + mRouterPwd;
				}else{
					strTemp = PublicClass.SECURITY_OPEN_NONE;
					strTemp = strTemp.replace("_", ",") + ",";
				}

				String strCmd = String.format(PublicClass.CMD_SET_WSKEY,strTemp);
				startThread(strCmd);
				mProcessStage = ProcessStage.StartSetPwd;
				break;
			}
			super.handleMessage(msg);
		}		
	}
	private class UdpClient implements UdpCommListener {

		@Override
		public void onReceived(byte[] data, int length) {
			Message mMessage = new Message();
			String strReceivedData = new String(data, 0, length);

			Log.i("msg", "strReceivedData:"+strReceivedData);

			strReceivedData = strReceivedData.replace("\r\n", "");
			if (strReceivedData.startsWith(PublicClass.RESPONSE_SCAN_MODULE)){
				mHandler.sendEmptyMessage(ProcessStage.EndScanModule.ordinal());
			}

			else if(strReceivedData.startsWith(PublicClass.RESPONSE_QUERY_OK)){
				String strParaData = strReceivedData.substring(PublicClass.RESPONSE_QUERY_OK.length()).trim();

				switch(mProcessStage){
				case StartQuerySSID:
					mMessage.what = ProcessStage.EndQuerySSID.ordinal();
					mMessage.obj = strParaData;
					mHandler.sendMessage(mMessage);	
					break;
				case StartQueryStatus:
					mMessage.what = ProcessStage.EndQueryStatus.ordinal();
					mMessage.obj = strParaData;
					mHandler.sendMessage(mMessage);	
					break;
				case StartGetCollectorNumber:
					mMessage.what = ProcessStage.EndGetCollectorNumber.ordinal();
					mMessage.obj = strParaData;
					mHandler.sendMessage(mMessage);
					break;
				default:
					break;
				}	
			}

			else if(strReceivedData.startsWith(PublicClass.RESPONSE_SET_OK)){
				mMessage.arg1 = Results.Sucess.ordinal();
				switch(mProcessStage){
				case StartSetSSID:
					mMessage.what = ProcessStage.EndSetSSID.ordinal();
					mHandler.sendMessage(mMessage);
					break;
				case StartSetPwd:
					mMessage.what = ProcessStage.EndSetPwd.ordinal();
					mHandler.sendMessage(mMessage);
					break;
				case StartSetWifiPsw:
					mMessage.what = ProcessStage.EndSetWifiPsw.ordinal();
					mHandler.sendMessage(mMessage);
					break;
				case StartResetModule:
					mMessage.what = ProcessStage.EndResetModule.ordinal();
					mHandler.sendMessage(mMessage);
					break;
				default:
					break;
				}
			}

			else if(strReceivedData.startsWith(PublicClass.RESPONSE_SET_ERR)){
				mMessage.arg1 = Results.Fail.ordinal();
				switch(mProcessStage){
				case StartSetSSID:
					mMessage.what = ProcessStage.EndSetSSID.ordinal();
					mHandler.sendMessage(mMessage);
					break;
				case StartSetPwd:
					mMessage.what = ProcessStage.EndSetPwd.ordinal();
					mHandler.sendMessage(mMessage);
					break;
				case StartSetWifiPsw:
					mMessage.what = ProcessStage.EndSetWifiPsw.ordinal();
					mHandler.sendMessage(mMessage);
					break;
				case StartResetModule:
					mMessage.what = ProcessStage.EndResetModule.ordinal();
					mHandler.sendMessage(mMessage);
					break;
				default:
					break;
				}
			}
		}

	}
	private void startThread(final String tag)
	{
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				mUdpComm.send(tag);
			}
		};
		thread = new Thread(runnable);
		thread.start();
	}
	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == PublicClass.RES_SELECT_ROUTER_OK)
		{
			Bundle mRecData = data.getExtras();
			LuyouMap.clear();
			LuyouMap.putAll((HashMap<String, String>) mRecData.getSerializable("SelectedData"));
			txtRouterSSID.setText(LuyouMap.get("SSID"));
			if(cbxDispRouterPwd.isChecked()){
				txtRouterPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			}else{
				txtRouterPwd.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
			}
			txtRouterPwd.setEnabled(true);
			cbxDispRouterPwd.setEnabled(true);
			txtRouterPwd.setFocusable(true);
			txtRouterPwd.setFocusableInTouchMode(true);
			txtRouterPwd.requestFocus();

			Timer mTimer = new Timer();
			mTimer.schedule(new TimerTask(){
				public void run(){
					InputMethodManager mInputManager = 
							(InputMethodManager)txtRouterPwd.getContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
					mInputManager.showSoftInput(txtRouterPwd, 0);
				}
			},998);
		}else{
			mRouterPwd = "";
			LuyouMap.clear();
		}
	}
}
