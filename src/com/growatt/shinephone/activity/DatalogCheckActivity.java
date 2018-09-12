package com.growatt.shinephone.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.growatt.shinephone.R;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.PostUtil.postListener;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class DatalogCheckActivity extends DoActivity {

	private EditText et1;
	private EditText et2;
	private Button bt;
	private Button register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datalog_check);
		initHeaderView();
		Setviews();
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
		setHeaderTitle(headerView,getString(R.string.Register_add_collector));
	}
	private void Setviews() {
		bt=(Button)findViewById(R.id.button1);
		et1=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		//注册
		register=(Button)findViewById(R.id.register);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data!=null&&resultCode==RESULT_OK){
			if(requestCode==105){
				Bundle bundle=data.getExtras();
				String s=bundle.getString("result");
				System.out.println("result="+s);
				if(!TextUtils.isEmpty(s)){
					et1.setText(s);
//					et2.setText(AppUtils.validateWebbox(s));
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void SetListeners() {
		bt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(DatalogCheckActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, 105);
			}
		});
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

			
				Mydialog.Show(DatalogCheckActivity.this, "");
				Log.i("TAG", "url:"+Cons.url);
				if(TextUtils.isEmpty(Cons.url)){
					toast(R.string.all_server_url);
					jumpTo(CountryandCityActivity.class, true);
					return;
				}
//				PostUtil.post("http://"+Cons.url+"/newRegisterAPI.do?op=creatAccount", new postListener() {
				PostUtil.post(new Urlsutil().creatAccount, new postListener() {

					@Override 
					public void success(String json) {
						Mydialog.Dismiss();
						try {
							JSONObject jsonObject=new JSONObject(json).getJSONObject("back");
							String msg=jsonObject.optString("msg");
							if(jsonObject.opt("success").toString().equals("true")){
								if(msg.equals("200")){
									toast(R.string.DatalogCheckActivity_regist_success);
									String type = jsonObject.get("datalogType").toString().toLowerCase();
									if (Constant.WiFi_Type_ShineWIFI.equals(type) || Constant.WiFi_Type_ShineWIFI_S.equals(type)){
										MyUtils.configWifi(DatalogCheckActivity.this,type,"1",et1.getText().toString().trim());
									}else {
										MyControl.autoLogin(DatalogCheckActivity.this,Cons.regMap.getRegUserName(),Cons.regMap.getRegPassword());
									}
//									if(type.contains("shinewifi")){
//										ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//										State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//										if(wifi == State.CONNECTED||wifi==State.CONNECTING){
//												Map<String, Object> map = new GetWifiInfo(DatalogCheckActivity.this).Info();
//												if(map.get("mAuthString").toString().equals("")){
//													AlertDialog builder = new AlertDialog.Builder(DatalogCheckActivity.this).
//															setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_connectwifi).
//															setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
//
//																@Override
//																public void onClick(DialogInterface arg0, int arg1) {
//																	arg0.dismiss();
//																}
//															}).create();
//													builder.show();
//													return;
//												}
//												Intent intent=new Intent(DatalogCheckActivity.this,SmartConnection.class);
//												Bundle bundle=new Bundle();
//												bundle.putString("type","1");
//												bundle.putString("id",et1.getText().toString().trim());
//												bundle.putString("ssid",map.get("ssid").toString());
//												bundle.putString("mAuthString",map.get("mAuthString").toString());
//												bundle.putByte("mAuthMode",(Byte) map.get("mAuthMode"));
//												intent.putExtras(bundle);
//												startActivity(intent);
//
//										}else{
////											T.make(R.string.dataloggers_dialog_connectwifi);
//											Intent intent=new Intent(DatalogCheckActivity.this,RegsuecessActivity.class);
//											Bundle bundle=new Bundle();
//											bundle.putString("sn", et1.getText().toString().trim());
//											bundle.putString("type", type);
//											bundle.putString("act", "datalogcheck");
//											intent.putExtras(bundle);
//											startActivity(intent);
//											finish();
//										}
//									}else{
////										startActivity(new Intent(DatalogCheckActivity.this,LoginActivity.class));
////										finish();
//										MyControl.autoLogin(DatalogCheckActivity.this,Cons.regMap.getRegUserName(),Cons.regMap.getRegPassword());
//									}

								}
							}else{

								if(msg.equals("501")){
									toast(R.string.datalogcheck_check_no_overstep);
									return;
								}
								if(msg.equals("502")){
									MyControl.putAppErrMsg("注册:"+Cons.regMap.getRegUserName()+"-msg:"+msg,DatalogCheckActivity.this);
									toast(R.string.datalogcheck_check_no_server);
									return;
								}
								if(msg.equals("503")){
									toast(R.string.datalogcheck_check_no_userexist);
									return;
								}
								if(msg.equals("602")){
									MyControl.putAppErrMsg("注册:"+Cons.regMap.getRegUserName()+"-msg:"+msg,DatalogCheckActivity.this);
									toast(R.string.datalogcheck_code_602);
									return;
								}
								if(msg.equals("506")){
									toast(R.string.datalogcheck_check_no_verification);
									return;
								}
								if(msg.equals("603")){
									toast(R.string.datalogcheck_check_add_datalog_err);
									return;
								}
								if(msg.equals("604")){
									toast(R.string.datalogcheck_check_no_agentcode);
									return;
								}
								if(msg.equals("605")){
									toast(R.string.datalogcheck_check_no_datalog_exist);
									return;
								}
								if(msg.equals("606")){
									toast(R.string.datalogcheck_check_no_datalog_server);
									return;
								}
								if(msg.equals("607")){
									toast(R.string.datalogcheck_check_no_datalog_server);
									return;
								}
								
								if(msg.equals("504")){
									toast(R.string.DatalogCheckAct_username_pwd_empty);
									return;
								}
								if(msg.equals("505")){
									toast(R.string.DatalogCheckAct_email_empty);
									return;
								}
								if(msg.equals("509")){
									toast(R.string.DatalogCheckAct_country_empty);
									return;
								}
								if(msg.equals("608")){
									toast(R.string.datalogcheck_code_608);
									return;
								}
								if(msg.equals("609")){
									toast(R.string.datalogcheck_code_609);
									return;
								}
								if(msg.equals("701")){
									MyControl.putAppErrMsg("注册:"+Cons.regMap.getRegUserName()+"-msg:"+msg,DatalogCheckActivity.this);
									toast(R.string.datalogcheck_code_701);
									return;
								}
								if(msg.equals("702")){
									toast(R.string.datalogcheck_code_702);
									return;
								}
								if(msg.equals("507")){
									toast(R.string.datalogcheck_check_no_agentcode);
									return;
								}

								MyControl.putAppErrMsg("注册:"+Cons.regMap.getRegUserName()+"-msg:"+msg,DatalogCheckActivity.this);
								toast(msg+":"+getString(R.string.datalogcheck_check_no_server));

							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					@SuppressLint("DefaultLocale") @Override
					public void Params(Map<String, String> params) {
						params.put("regUserName", Cons.regMap.getRegUserName());
						params.put("regPassword",Cons.regMap.getRegPassword());
						params.put("regEmail", Cons.regMap.getRegEmail());
						params.put("regDataLoggerNo",et1.getText().toString().trim());
//						params.put("regValidateCode", et2.getText().toString().trim());
						params.put("regValidateCode", "");
						params.put("regPhoneNumber", Cons.regMap.getRegPhoneNumber());
//						Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
//						String s=calendar.getTime().toString();
//						s=s.substring(s.indexOf("+")+1);
//						s=s.substring(0, 2);
						String s = new SimpleDateFormat("Z", Locale.ENGLISH).format(new Date());
						if (s.length() > 2) {
							s = s.substring(0, s.length() - 2);
						}else {
							s = "8";
						}
						if (s.startsWith("+13")){
							s = "12" ;
						}
						Locale locale = getResources().getConfiguration().locale;
						String language = locale.getLanguage();
						if(language.toLowerCase().contains("zh")){
							language="zh_cn";
						}else if(language.toLowerCase().contains("en")){
							language="en";
						}else if(language.toLowerCase().contains("fr")){
							language="fr";
						}else if(language.toLowerCase().contains("ja")){
							language="ja";
						}else if(language.toLowerCase().contains("it")){
							language="it";
						}else if(language.toLowerCase().contains("ho")){
							language="ho";
						}else if(language.toLowerCase().contains("tk")){
							language="tk";
						}else if(language.toLowerCase().contains("pl")){
							language="pl";
						}else if(language.toLowerCase().contains("gk")){
							language="gk";
						}else if(language.toLowerCase().contains("gm")){
							language="gm";
						}else {
							language = "en";
						}
						params.put("regTimeZone", s);
						params.put("regLanguage", language);
						params.put("regCountry", Cons.regMap.getRegCountry());
						params.put("regCity",Cons.regMap.getRegCity());
						params.put("agentCode",Cons.regMap.getAgentCode());
						params.put("regLng",Cons.regMap.getRegLng());
						params.put("regLat",Cons.regMap.getRegLat());
						
					}
					@Override
					public void LoginError(String str) {

					}
				});
			
			}
		});
//		textView_ok.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				if(et1.getText().toString().equals("")){
//					T.make(R.string.all_blank);
//					toast(R.string.all_success);
//					return;
//				}
//				if(et2.getText().toString().equals("")){
//					T.make(R.string.all_blank);
//					toast(R.string.all_success);
//					return;
//				}
//				Mydialog.Show(DatalogCheckActivity.this, "");
//				SqliteUtil.time((System.currentTimeMillis()+500000)+"");
//				if(Cons.url.equals("")){
//					T.make(R.string.all_server_url);
//					toast(R.string.all_success);
//					return;
//				}
//				PostUtil.post("http://"+Cons.url+"/newRegisterAPI.do?op=creatAccount", new postListener() {
//
//					@Override 
//					public void success(String json) {
//						Mydialog.Dismiss();
//						try {
//							JSONObject jsonObject=new JSONObject(json).getJSONObject("back");
//							String msg=jsonObject.optString("msg");
//							if(jsonObject.opt("success").toString().equals("true")){
//								if(msg.equals("200")){
//									T.make(R.string.DatalogCheckActivity_regist_success);
//									toast(R.string.all_success);
//									String type=jsonObject.get("datalogType").toString();
//									type=type.toLowerCase();
//									if(type.contains("shinewifi")){
//										ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//										State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//										if(wifi == State.CONNECTED||wifi==State.CONNECTING){
//												Map<String, Object> map = new GetWifiInfo().Info();
//												if(map.get("mAuthString").toString().equals("")){
//													AlertDialog builder = new AlertDialog.Builder(DatalogCheckActivity.this).
//															setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_prompt).
//															setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
//
//																@Override
//																public void onClick(DialogInterface arg0, int arg1) {
//																	arg0.dismiss();
//																}
//															}).create();
//													builder.show();
//													return;
//												}
//												Intent intent=new Intent(DatalogCheckActivity.this,WiFiNewtoolActivity.class);
//												Bundle bundle=new Bundle();
//												bundle.putString("type","1");
//												bundle.putString("id",et1.getText().toString().trim());
//												bundle.putString("ssid",map.get("ssid").toString());
//												bundle.putString("mAuthString",map.get("mAuthString").toString());
//												bundle.putByte("mAuthMode",(Byte) map.get("mAuthMode"));
//												intent.putExtras(bundle);
//												startActivity(intent);
//
//										}else{
////											T.make(R.string.dataloggers_dialog_connectwifi);
//											Intent intent=new Intent(DatalogCheckActivity.this,RegsuecessActivity.class);
//											Bundle bundle=new Bundle();
//											bundle.putString("sn", et1.getText().toString().trim());
//											bundle.putString("type", type);
//											bundle.putString("act", "datalogcheck");
//											intent.putExtras(bundle);
//											startActivity(intent);
//											finish();	
//										}
//									}else{
//										startActivity(new Intent(DatalogCheckActivity.this,LoginActivity.class));
//										finish();
//									}
//
//
//
//
//
//
////									AlertDialog.Builder builder = new Builder(DatalogCheckActivity.this);
////									builder.setTitle(R.string.all_prompt)
////									.setMessage(R.string.configure).setNegativeButton(R.string.login, new DialogInterface.OnClickListener() {
////
////										@Override
////										public void onClick(DialogInterface arg0, int arg1) {
////											startActivity(new Intent(DatalogCheckActivity.this,LoginActivity.class));
////											finish();
////										}
////									}).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
////
////										@Override
////										public void onClick(DialogInterface arg0, int arg1) {
////											Intent intent=new Intent(DatalogCheckActivity.this,RegsuecessActivity.class);
////											Bundle bundle=new Bundle();
////											bundle.putString("sn", et1.getText().toString());
////											bundle.putString("type", type);
////											intent.putExtras(bundle);
////											startActivity(intent);
////											finish();	
////										}
////									}).create();
////									builder.show();
//								}
//							}else{
//								if(msg.equals("501")){
//									T.make(R.string.datalogcheck_check_no_overstep);
//									return;
//								}
//								if(msg.equals("502")){
//									T.make(R.string.datalogcheck_check_no_server);
//									return;
//								}
//								if(msg.equals("503")){
//									T.make(R.string.datalogcheck_check_no_userexist);
//									return;
//								}
//								if(msg.equals("602")){
//									T.make(R.string.datalogcheck_check_no_plant);
//									return;
//								}
//								if(msg.equals("506")){
//									T.make(R.string.datalogcheck_check_no_verification);
//									return;
//								}
//								if(msg.equals("603")){
//									T.make(R.string.datalogcheck_check_no_verification);
//									return;
//								}
//								if(msg.equals("604")){
//									T.make(R.string.datalogcheck_check_no_agentcode);
//									return;
//								}
//								if(msg.equals("605")){
//									T.make(R.string.datalogcheck_check_no_datalog_exist);
//									return;
//								}
//								if(msg.equals("606")){
//									T.make(R.string.datalogcheck_check_no_datalog_server);
//									return;
//								}
//								if(msg.equals("607")){
//									T.make(R.string.datalogcheck_check_no_datalog_server);
//									return;
//								}
//								
//								T.make(R.string.datalogcheck_check_no);
//
//							}
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//					@Override
//					public void Params(Map<String, String> params) {
//						params.put("regUserName", Cons.regMap.getRegUserName());
//						params.put("regPassword",Cons.regMap.getRegPassword());
//						params.put("regEmail", Cons.regMap.getRegEmail());
//						params.put("regDataLoggerNo",et1.getText().toString().trim());
//						params.put("regValidateCode", et2.getText().toString().trim());
//						params.put("regPhoneNumber", Cons.regMap.getRegPhoneNumber());
//						Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));  
//						String s=calendar.getTime().toString();
//						s=s.substring(s.indexOf("+")+1);
//						s=s.substring(0, 2);
//						Locale locale = getResources().getConfiguration().locale;
//						String language = locale.getLanguage();
//						System.out.println(s);
//						System.out.println("����="+language);
//						if(language.toLowerCase().contains("zh")){
//							language="zh_cn";
//						}
//						if(language.toLowerCase().contains("en")){
//							language="en";
//						}
//						if(language.toLowerCase().contains("fr")){
//							language="fr";
//						}
//						if(language.toLowerCase().contains("ja")){
//							language="ja";
//						}
//						if(language.toLowerCase().contains("it")){
//							language="it";
//						}
//						if(language.toLowerCase().contains("ho")){
//							language="ho";
//						}
//						if(language.toLowerCase().contains("tk")){
//							language="tk";
//						}
//						if(language.toLowerCase().contains("pl")){
//							language="pl";
//						}
//						if(language.toLowerCase().contains("gk")){
//							language="gk";
//						}
//						if(language.toLowerCase().contains("gm")){
//							language="gm";
//						}
//
//						params.put("regTimeZone", s);
//						params.put("regLanguage", language);
//						params.put("regCountry", Cons.regMap.getRegCountry());
//						params.put("regCity",Cons.regMap.getRegCity());
//					}
//					@Override
//					public void LoginError(String str) {
//
//					}
//				});
//			}
//		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
//			Cons.regMap=new RegisterMap();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
