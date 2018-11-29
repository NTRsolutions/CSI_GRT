package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.GetWifiInfo;
import com.growatt.shinephone.util.Position;

import org.xutils.common.util.LogUtil;

import java.util.Map;

import mediatek.android.IoTManager.SmartConnection;

public class ShineWifiSetActivity extends DoActivity {
    EditText etSN;
//    EditText etCode;
    Button btnNext;
    Button btnScan;
    Button btn_newWifi2;
    View headerView;
	private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shine_wifi_set);
        initView();
        initHead();
        initListener();
    }
    private void initListener() {
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(isEmpty(etSN)) return;
				String sn=etSN.getText().toString().trim();
//				String code=etCode.getText().toString().trim();
//				if(code.equals(AppUtils.validateWebbox(sn))){
					Map<String, Object> map = new GetWifiInfo(ShineWifiSetActivity.this).Info();
					if(map.get("mAuthString").toString().equals("")){
						AlertDialog builder = new AlertDialog.Builder(ShineWifiSetActivity.this).setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_connectwifi).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();
							}
						}).create();
						builder.show();
						return;
					}
				jumpWifiConfig(sn, map);

//				}else{
//					toast(getString(R.string.retrievepwd_failed_suited));
//				}
			}
		});
		btnScan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ShineWifiSetActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, 105);
			}
		});
        btn_newWifi2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(NewWifiS2ConfigActivity.class,false);
            }
        });
	}

	private void jumpWifiConfig(String sn, Map<String, Object> map) {
	/*	Class clazz = null;
		if (radioGroup.getCheckedRadioButtonId() == R.id.radio_button2){
            clazz = NewWifiS2ConfigActivity.class;
            LogUtil.i("ShineWifiSet:"+"NewWifiS2ConfigActivity");
        }else if(radioGroup.getCheckedRadioButtonId() == R.id.radio_button1){
			clazz = SmartConnection.class;
		}else{
			toast(R.string.m179请选择采集器类型);
			return;
		}
		Intent intent=new Intent(ShineWifiSetActivity.this,clazz);*/
		Intent intent=new Intent(ShineWifiSetActivity.this,NewWifiS2ConfigActivity.class);
		Bundle bundle=new Bundle();
		bundle.putString("type","100");
		bundle.putString("id",sn);
		bundle.putString("ssid",map.get("ssid").toString());
		bundle.putString("mAuthString",map.get("mAuthString").toString());
		bundle.putByte("mAuthMode",(Byte) map.get("mAuthMode"));
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void initHead(){
         setHeaderImage(headerView, R.drawable.back, Position.LEFT, new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
         setHeaderTitle(headerView, getString(R.string.m71));
    }
    public void initView(){
        headerView=findViewById(R.id.headerView);
        etSN=(EditText)findViewById(R.id.et_sn);
//        etCode=(EditText)findViewById(R.id.et_code);
        btnNext=(Button)findViewById(R.id.btn_next);
        btnScan=(Button)findViewById(R.id.btn_scan);
        btn_newWifi2=(Button)findViewById(R.id.btn_newWifi2);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
    }
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {

		case 105:
			if(data!=null){
				Bundle bundle=data.getExtras();
				final String s=bundle.getString("result");
				etSN.setText(s);
				Map<String, Object> map = new GetWifiInfo(ShineWifiSetActivity.this).Info();
				if(map.get("mAuthString").toString().equals("")){
					AlertDialog builder = new AlertDialog.Builder(ShineWifiSetActivity.this).setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_connectwifi).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							arg0.dismiss();
						}
					}).create();
					builder.show();
					return;
				}
				jumpWifiConfig(s, map);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
