package com.growatt.shinephone.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.growatt.shinephone.R;
import com.growatt.shinephone.listener.OnViewEnableListener;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.SharedPreferencesUnit;

import java.util.ArrayList;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class WelcomeActivity extends DoActivity {
	
//	Handler handler=new Handler(){
//		public void handleMessage(android.os.Message msg) {
//			switch (msg.what) {
//			case 0:
//				startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
//				T.make(R.string.all_server_overtime);
//				finish();
//				break;
//
//			case 1:
//				break;
//			}
//		};
//	};
//	private Thread thread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		JPushInterface.init(getApplicationContext());
		
		
		
//		new Handler().postDelayed(new Runnable() {
//
//			@Override
//			public void run() {
				SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
				String name=preferences.getString("name", "");
				if(TextUtils.isEmpty(name)){
					Editor editor=preferences.edit();
					name="true";
					editor.putString("name", name);
					editor.commit();
					startActivity(new Intent(WelcomeActivity.this,GuiActivity.class));
					finish();
				}else{
						final Map<String, Object> map = SqliteUtil.inquirylogin();
						int autoLoginNum = SharedPreferencesUnit.getInstance(this).getInt(Constant.AUTO_LOGIN);
						if(autoLoginNum == 0 || map.size()==0){
							startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
							finish();
							return;
						}
						SqliteUtil.time((System.currentTimeMillis()+500000)+"");
						Cons.plants=new ArrayList<Map<String,Object>>();
						Mydialog.Show(WelcomeActivity.this,"");
					//oss登录
					int autoLoginType = SharedPreferencesUnit.getInstance(this).getInt(Constant.AUTO_LOGIN_TYPE);
					switch (autoLoginType){
						case 0://oss登录
							MyUtils.ossLogin(mContext, map.get("name").toString().trim(), map.get("pwd").toString().trim(), new OnViewEnableListener(){});
							break;
						case 1://server登录
						String url=SqliteUtil.inquiryurl();
						if(TextUtils.isEmpty(url)){
							MyUtils.autoLogin(mContext,map.get("name").toString().trim(), map.get("pwd").toString().trim());
						 }else {
							MyUtils.serverLogin(mContext,url,map.get("name").toString().trim(), map.get("pwd").toString().trim(), new OnViewEnableListener(){});
						}
						break;
						default:
							MyUtils.ossLogin(mContext, map.get("name").toString().trim(), map.get("pwd").toString().trim(), new OnViewEnableListener(){});
							break;
					}
//						GetUtil.getParams(new Urlsutil().getUserServerUrl, new postListener() {
//
//							@Override
//							public void success(String json) {
//
//								Mydialog.Dismiss();
//								try {
//									JSONObject jsonObject=new JSONObject(json);
//									String success=jsonObject.get("success").toString();
//									if(success.equals("true")){
//										String msg=jsonObject.getString("msg");
//										System.out.println(msg);
//										SqliteUtil.url(msg);
//										Cons.guestyrl="http://"+msg;
//										PostUtil.post(new Urlsutil().cusLogin, new postListener() {
//											Map<String, Object>map=SqliteUtil.inquirylogin();
//											@Override
//											public void success(String json) {
//												try {
//													JSONObject jsonObject10 = new JSONObject(json);
//													JSONObject jsonObject=jsonObject10.getJSONObject("back");
//													if(jsonObject.opt("success").toString().equals("true")){
//														//�ж��Ƿ�ͨ�ͷ�����ϵͳ
//														if("1".equals(jsonObject.opt("service").toString())){
//															Cons.addQuestion=true;
//														}else{
//															Cons.addQuestion=false;
//														}
//														int app_code=jsonObject.optInt("app_code", 0);
//														if(app_code>SqliteUtil.getApp_Code()||SqliteUtil.getApp_Code()==-1){
//															Cons.isCodeUpdate=true;
//														}else{
//															Cons.isCodeUpdate=false;
//														}
//														SqliteUtil.setService("", app_code);
//														JSONArray jsonArray=jsonObject.getJSONArray("data");
//														if(Cons.plants==null){
//															Cons.plants=new ArrayList<Map<String,Object>>();
//														}
//														Cons.plants.clear();
//														for (int i = 0; i < jsonArray.length(); i++) {
//															JSONObject jsonObject2=jsonArray.getJSONObject(i);
//															Map<String, Object>map=new HashMap<String, Object>();
//															String id=jsonObject2.getString("plantId").toString();
//															map.put("plantId", id);
//															map.put("plantName",jsonObject2.getString("plantName").toString());
//															Cons.plants.add(map);
//														}
//														JSONObject jsonObject2=jsonObject.getJSONObject("user");
//														UserBean userBean=new UserBean();
//														userBean.setEnabled(jsonObject2.opt("enabled").toString());
//														userBean.setAgentCode(jsonObject2.opt("agentCode").toString());
//														userBean.setUserLanguage(jsonObject2.opt("userLanguage").toString());
//														userBean.setTimeZone(jsonObject2.opt("timeZone").toString());
//														userBean.setPassword(jsonObject2.opt("password").toString());
//														userBean.setMailNotice(jsonObject2.opt("mailNotice").toString());
//														userBean.setId(jsonObject2.opt("id").toString());
//														Cons.userId=jsonObject2.opt("id").toString();
//														userBean.setLastLoginIp(jsonObject2.opt("lastLoginIp").toString());
//														userBean.setPhoneNum(jsonObject2.get("phoneNum").toString());
//														userBean.setAccountName(jsonObject2.opt("accountName").toString());
//														userBean.setApproved(jsonObject2.get("approved").toString());
//														userBean.setSmsNotice(jsonObject2.get("smsNotice").toString());
//														userBean.setEmail(jsonObject2.get("email").toString());
//														userBean.setParentUserId(jsonObject2.getInt("parentUserId"));
//														userBean.setCompany(jsonObject2.get("company").toString());
//														userBean.setActiveName(jsonObject2.get("activeName").toString());
//														userBean.setCounrty(jsonObject2.get("counrty").toString());
//														userBean.setIsBigCustomer(jsonObject2.get("isBigCustomer").toString());
//														userBean.setCreateDate(jsonObject2.get("createDate").toString());
//														userBean.setRightlevel(jsonObject2.get("rightlevel").toString());
//														userBean.setLastLoginTime(jsonObject2.get("lastLoginTime").toString());
//														userBean.setNoticeType(jsonObject2.get("noticeType").toString());
//														Cons.userBean=userBean;
//
//														startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
//														finish();
//													}else{
//														startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
//														toast(R.string.all_login_error);
//														finish();
//													}
//												} catch (JSONException e) {
//													e.printStackTrace();
//													startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
//													finish();
//												}
//											}
//											@Override
//											public void Params(Map<String, String> params) {
//												params.put("userName",map.get("name").toString());
//												params.put("password",MD5andKL.encryptPassword(map.get("pwd").toString()));
//											}
//											@Override
//											public void LoginError(String str) {
//
//												startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
//												finish();
//											}
//										});
//									}
//
//								} catch (JSONException e) {
//									e.printStackTrace();
//								}
//
//							}
//
//							@Override
//							public void Params(Map<String, String> params) {
//								params.put("userName", map.get("name").toString());
//							}
//
//							@Override
//							public void LoginError(String str) {
//								startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
//								finish();
//							}
//						});
//						GetUtil.get(new Urlsutil().getUserServerUrl+"&userName="+map.get("name").toString(), new GetListener() {
//							
//							@Override
//							public void success(String json) {
//								Mydialog.Dismiss();
//								try {
//									JSONObject jsonObject=new JSONObject(json);
//									String success=jsonObject.get("success").toString();
//									if(success.equals("true")){
//										String msg=jsonObject.getString("msg");
//										System.out.println(msg);
//										SqliteUtil.url(msg);
//										Cons.guestyrl="http://"+msg;
//										PostUtil.post(new Urlsutil().cusLogin, new postListener() {
//											Map<String, Object>map=SqliteUtil.inquirylogin();
//											@Override
//											public void success(String json) {
//												try {
//													JSONObject jsonObject10 = new JSONObject(json);
//													JSONObject jsonObject=jsonObject10.getJSONObject("back");
//													if(jsonObject.opt("success").toString().equals("true")){
//														//�ж��Ƿ�ͨ�ͷ�����ϵͳ
//														if("1".equals(jsonObject.opt("service").toString())){
//															Cons.addQuestion=true;
//														}else{
//															Cons.addQuestion=false;
//														}
//														int app_code=jsonObject.optInt("app_code", 0);
//														if(app_code>SqliteUtil.getApp_Code()||SqliteUtil.getApp_Code()==-1){
//															Cons.isCodeUpdate=true;
//														}else{
//															Cons.isCodeUpdate=false;
//														}
//														SqliteUtil.setService("", app_code);
//														JSONArray jsonArray=jsonObject.getJSONArray("data");
//														if(Cons.plants==null){
//															Cons.plants=new ArrayList<Map<String,Object>>();
//														}
//														Cons.plants.clear();
//														for (int i = 0; i < jsonArray.length(); i++) {
//															JSONObject jsonObject2=jsonArray.getJSONObject(i);
//															Map<String, Object>map=new HashMap<String, Object>();
//															String id=jsonObject2.getString("plantId").toString();
//															map.put("plantId", id);
//															map.put("plantName",jsonObject2.getString("plantName").toString());
//															Cons.plants.add(map);
//														}
//														JSONObject jsonObject2=jsonObject.getJSONObject("user");
//														UserBean userBean=new UserBean();
//														userBean.setEnabled(jsonObject2.opt("enabled").toString());
//														userBean.setAgentCode(jsonObject2.opt("agentCode").toString());
//														userBean.setUserLanguage(jsonObject2.opt("userLanguage").toString());
//														userBean.setTimeZone(jsonObject2.opt("timeZone").toString());
//														userBean.setPassword(jsonObject2.opt("password").toString());
//														userBean.setMailNotice(jsonObject2.opt("mailNotice").toString());
//														userBean.setId(jsonObject2.opt("id").toString());
//														Cons.userId=jsonObject2.opt("id").toString();
//														userBean.setLastLoginIp(jsonObject2.opt("lastLoginIp").toString());
//														userBean.setPhoneNum(jsonObject2.get("phoneNum").toString());
//														userBean.setAccountName(jsonObject2.opt("accountName").toString());
//														userBean.setApproved(jsonObject2.get("approved").toString());
//														userBean.setSmsNotice(jsonObject2.get("smsNotice").toString());
//														userBean.setEmail(jsonObject2.get("email").toString());
//														userBean.setParentUserId(jsonObject2.getInt("parentUserId"));
//														userBean.setCompany(jsonObject2.get("company").toString());
//														userBean.setActiveName(jsonObject2.get("activeName").toString());
//														userBean.setCounrty(jsonObject2.get("counrty").toString());
//														userBean.setIsBigCustomer(jsonObject2.get("isBigCustomer").toString());
//														userBean.setCreateDate(jsonObject2.get("createDate").toString());
//														userBean.setRightlevel(jsonObject2.get("rightlevel").toString());
//														userBean.setLastLoginTime(jsonObject2.get("lastLoginTime").toString());
//														userBean.setNoticeType(jsonObject2.get("noticeType").toString());
//														Cons.userBean=userBean;
//												
//														startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
//														finish();
//													}else{
//														startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
//														T.make(R.string.all_login_error);
//														finish();
//													}
//												} catch (JSONException e) {
//													e.printStackTrace();
//													startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
//													finish();
//												}
//											}
//											@Override
//											public void Params(Map<String, String> params) {
//												params.put("userName",map.get("name").toString());
//												params.put("password",MD5andKL.encryptPassword(map.get("pwd").toString()));
//											}
//											@Override
//											public void LoginError(String str) {
//												
//												startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
//												finish();
//											}
//										});
//									}
//									
//								} catch (JSONException e) {
//									e.printStackTrace();
//								}
//							}
//							@Override
//							public void error(String json) {
//								startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
//								finish();
//							}
//						});
					
				}
			}

//		},2000);
//		thread=new Thread(){
//			public void run() {
//				try {
//					Thread.sleep(10000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				if(Cons.flag==true){
//					handler.sendEmptyMessage(1);
//				}else{
//					handler.sendEmptyMessage(0);
//				}
//			};
//		};
//		thread.setDaemon(true);
//		thread.start();
//	}
	
	
	@Override
	public void onStop() {
//		Cons.flag=true;
		Mydialog.Dismiss();
		super.onStop();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			ShineApplication.getInstance().exit();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
