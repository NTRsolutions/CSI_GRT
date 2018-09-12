package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.RegisterMap;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.GetWifiInfo;
import com.growatt.shinephone.util.Position;

import java.util.Map;

import mediatek.android.IoTManager.SmartConnection;

public class RegsuecessActivity extends DoActivity {

	private String sn;
	private String type;
	String act;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regsuecess);
		Bundle bundle=getIntent().getExtras();
		sn=bundle.getString("sn");
		type=bundle.getString("type");
		act=bundle.getString("act");
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
				if("datalogcheck".equals(act)){
					Cons.regMap=new RegisterMap();
					startActivity(new Intent(RegsuecessActivity.this,LoginActivity.class));
				}
				finish();
			}
		});
		setHeaderTitle(headerView,getString(R.string.datalogcheck_check_ok));
	}
	private TextView tv1;
	private Button bt2;
	private void SetViews() {
		tv1=(TextView)findViewById(R.id.textView1);
		tv1.setText(R.string.dataloggers_dialog_connectwifi);
		SqliteUtil.login(Cons.regMap.getRegUserName(), Cons.regMap.getRegPassword());
		bt2=(Button)findViewById(R.id.button2);
		bt2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
				System.out.println(type);
				if(wifi == State.CONNECTED||wifi==State.CONNECTING){
						Map<String, Object> map = new GetWifiInfo(RegsuecessActivity.this).Info();
						if(map.get("mAuthString").toString().equals("")){
							AlertDialog builder = new AlertDialog.Builder(RegsuecessActivity.this).setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_connectwifi).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									arg0.dismiss();
								}
							}).create();
							builder.show();
							return;
						}
						Intent intent=new Intent(RegsuecessActivity.this,SmartConnection.class);
						Bundle bundle=new Bundle();
						bundle.putString("id",sn);
						bundle.putString("ssid",map.get("ssid").toString());
						bundle.putString("mAuthString",map.get("mAuthString").toString());
						bundle.putByte("mAuthMode",(Byte) map.get("mAuthMode"));
						intent.putExtras(bundle);
						startActivity(intent);
					
				}else{
					toast(R.string.dataloggers_dialog_connectwifi);
				}
			}
		});
			
	}

	private void SetListeners() {
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if("datalogcheck".equals(act)){
			Cons.regMap=new RegisterMap();
			startActivity(new Intent(RegsuecessActivity.this,LoginActivity.class));
			}
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
