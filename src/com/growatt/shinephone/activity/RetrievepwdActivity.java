package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.PostUtil.postListener;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class RetrievepwdActivity extends DoActivity {

	private Button bt1;
	private Button bt2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_retrievepwd);
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
		setHeaderTitle(headerView,getString(R.string.retrievepwd_title));
	}
	private void SetViews() {
		bt1=(Button)findViewById(R.id.button1);
		bt2=(Button)findViewById(R.id.button2);

	}

	private void SetListeners() {
		bt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				final EditText et=new EditText(RetrievepwdActivity.this);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				layoutParams.setMargins(5,10,5,10);//4��������˳��ֱ�����������
				et.setLayoutParams(layoutParams);
				Dialog dialog=new AlertDialog.Builder(RetrievepwdActivity.this).setMessage(R.string.retrievepwd_message).
						setTitle(R.string.retrievepwd_putinpwd).setView(et).
						setPositiveButton(R.string.all_ok, new OnClickListener() {

							@Override
							public void onClick(final DialogInterface arg0, int arg1) {
								final String s=et.getText().toString();
								if(TextUtils.isEmpty(s)){
									toast(R.string.retrievepwd_failed_username_blank);
									return;
								}
								Mydialog.Show(RetrievepwdActivity.this,"");
								PostUtil.post(new Urlsutil().postGetServerUrlByParam, new postListener() {
									
									@Override
									public void success(String json) {
										try {
											JSONObject jsonObject=new JSONObject(json);
											if(jsonObject.opt("success").toString().equals("true")){
												String url=jsonObject.getString("msg").toString();
												if(TextUtils.isEmpty(url)){
													url=Urlsutil.url_host;
												}
										        //���ݷ��������ص�ַ�����ʼ�
												PostUtil.post("http://"+url+"/newForgetAPI.do?op=sendResetEmailByAccount", new postListener() {
												
																					@Override
																					public void success(String json) {
																						arg0.dismiss();
																						Mydialog.Dismiss();
																						try {
																							JSONObject jsonObject=new JSONObject(json).getJSONObject("back");
																							if(jsonObject.opt("success").toString().equals("true")){
																								String str=jsonObject.getString("msg").toString();
																								String a=getResources().getString(R.string.retrievepwd_message);
																								toast(a+str);
																							}else{
																								String str=jsonObject.getString("msg").toString();
																								if(str.equals("501")){
																									toast(R.string.retrievepwd_failed_email);
																								}
																								if(str.equals("502")){
																									toast(R.string.retrievepwd_failed_blank);
																								}
																								if(str.equals("503")){
																									toast(R.string.serviceerror);
																								}
																							}
																						} catch (Exception e) {
																							e.printStackTrace();
																						}
																					}
																					@Override
																					public void Params(Map<String, String> params) {
																						String s=et.getText().toString();
																						params.put("accountName", s);
																					}
												
																					@Override
																					public void LoginError(String str) {
																						// TODO Auto-generated method stub
												
																					}
																				});
											}else{
												String str=jsonObject.getString("msg").toString();
												if(str.equals("501")){
													toast(R.string.retrievepwd_failed_email);
												}
												if(str.equals("502")){
													toast(R.string.retrievepwd_failed_blank);
												}
												if(str.equals("503")){
													toast(R.string.serviceerror);
												}
											}
											
										} catch (JSONException e) {
											e.printStackTrace();
										}
									}
									
									@Override
									public void Params(Map<String, String> params) {
										params.put("type","1");
										params.put("param", s);
									}
									
									@Override
									public void LoginError(String str) {
										// TODO Auto-generated method stub
										
									}
								});
							
								


							}
						}).setNegativeButton(R.string.all_no, new OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();
							}
						}).create();
				dialog.show();

			}
		});
	
		bt2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Dialog dialog=new AlertDialog.Builder(RetrievepwdActivity.this).setPositiveButton(R.string.all_twodimension, new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent intent = new Intent();
						intent.setClass(RetrievepwdActivity.this, MipcaActivityCapture.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivityForResult(intent, 101);
						arg0.dismiss();
					}
				}).setNegativeButton(R.string.all_edittext, new OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
						final View v=LayoutInflater.from(RetrievepwdActivity.this).inflate(R.layout.retrievepwd, null);
						Dialog dialog=new AlertDialog.Builder(RetrievepwdActivity.this).setTitle("")
								.setView(v).setPositiveButton(R.string.all_ok, new OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										final EditText et1=(EditText)v.findViewById(R.id.editText1);
										final EditText et2=(EditText)v.findViewById(R.id.editText2);
										if(et1.getText().toString().equals("")||et2.getText().toString().equals("")){
											toast(R.string.all_data_inexistence);
											return;
										}
										arg0.dismiss();
										Getmima(et1.getText().toString(), et2.getText().toString());
									}
								}).setNegativeButton(R.string.all_no, new OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0, int arg1) {

										arg0.dismiss();
									}
								}).create();
						dialog.show();
					}
				}).create();
				dialog.show();

			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==101){
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				//��ʾɨ�赽������
				final String sn = bundle.getString("result");
				final String snyan=AppUtils.validateWebbox(sn);
				if(TextUtils.isEmpty(sn)) return;
				if(TextUtils.isEmpty(snyan)) return;
				Getmima(sn, snyan);
			}
		}
	}
	public void Getmima(final String sn,final String snyan){
		Mydialog.Show(RetrievepwdActivity.this,"");
		PostUtil.post(new Urlsutil().postGetServerUrlByParam, new postListener() {
			
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject=new JSONObject(json);
					if(jsonObject.opt("success").toString().equals("true")){
						String url=jsonObject.getString("msg").toString();
						if(TextUtils.isEmpty(url)){
							url=Urlsutil.url_host;
						}
				        //ͨ��sn�һ�����
						PostUtil.post("http://"+url+"/newForgetAPI.do?op=sendResetEmailBySn", new postListener() {

							@Override
							public void success(String json) {
								Mydialog.Dismiss();
								try {
									JSONObject jsonObject=new JSONObject(json).getJSONObject("back");
									String str=jsonObject.opt("success").toString();
									String msg=jsonObject.opt("msg").toString();
									if(str.equals("true")){
										String a=getResources().getString(R.string.retrievepwd_email_message);
										Dialog dialog=new AlertDialog.Builder(RetrievepwdActivity.this).setTitle(R.string.all_success).
												setMessage(a+msg).setPositiveButton(R.string.all_ok, new OnClickListener() {

													@Override
													public void onClick(DialogInterface arg0, int arg1) {
														arg0.dismiss();
													}
												}).create();
										dialog.show();
									}else{
										if(str.equals("501")){
											toast(R.string.retrievepwd_failed_suited);
										}
										if(str.equals("502")){
											toast(R.string.retrievepwd_failed_email);
										}
										if(str.equals("503")){
											toast(R.string.retrievepwd_failed_blank);
										}
										if(str.equals("504")){
											toast(R.string.serviceerror);
										}
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}

							@Override
							public void Params(Map<String, String> params) {
								params.put("dataLogSn", sn);
								params.put("validateCode", snyan);
							}

							@Override
							public void LoginError(String str) {
								// TODO Auto-generated method stub

							}
						});
						
					}else{
						String str=jsonObject.getString("msg").toString();
						if(str.equals("501")){
							toast(R.string.retrievepwd_failed_email);
						}
						if(str.equals("502")){
							toast(R.string.retrievepwd_failed_blank);
						}
						if(str.equals("503")){
							toast(R.string.serviceerror);
						}
					}
			}catch(Exception e){
				e.printStackTrace();
				
				}
			}
			
			@Override
			public void Params(Map<String, String> params) {
				params.put("type","2");
				params.put("param", sn);
			}
			
			@Override
			public void LoginError(String str) {
				// TODO Auto-generated method stub
				
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
