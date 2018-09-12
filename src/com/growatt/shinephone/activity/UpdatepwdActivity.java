package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.growatt.shinephone.R;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.PostUtil.postListener;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class UpdatepwdActivity extends DoActivity {

	private EditText et1;
	private EditText et2;
	private EditText et3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatepwd);
		initHeaderView();
		SetViews();
		Setlisteners();
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
		setHeaderTitle(headerView,getString(R.string.updatepwd_title));
		setHeaderTvTitle(headerView, getString(R.string.all_ok), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tvOK();
			}
		});
	}
	private void SetViews() {
		et1=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		et3=(EditText)findViewById(R.id.editText3);

	}
	public void tvOK(){
			if(Cons.isflag==true){
				toast(R.string.all_experience);
				return;
			}
			String s1=et1.getText().toString();
			String s2=et2.getText().toString();
			String s3=et3.getText().toString();
			if(s1.equals("")||s2.equals("")||s3.equals("")){
				toast(R.string.all_blank);
				return;
			}
			if(!s2.equals(s3)){
				toast(R.string.register_password_no_same);
				return;
			}
			Mydialog.Show(UpdatepwdActivity.this,"");
			PostUtil.post(new Urlsutil().updateUserPassword, new postListener() {

				@Override
				public void success(String json) {
					try {
						Mydialog.Dismiss();
						JSONObject jsonObject=new JSONObject(json);
						if(jsonObject.get("msg").toString().equals("200")){
							toast(R.string.all_success);
							jumpTo(LoginActivity.class, true);
						}else if(jsonObject.get("msg").toString().equals("502")){
							toast(R.string.updatepwd_oldpwd_failed);
						}else if("701".equals(jsonObject.get("msg").toString())){
							toast(R.string.m7);
						}else{
							toast(R.string.serviceerror);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void Params(Map<String, String> params) {
					Map<String, Object> map = SqliteUtil.inquirylogin();
					params.put("accountName",map.get("name").toString());
					params.put("passwordOld", et1.getText().toString());
					params.put("passwordNew", et2.getText().toString());
				}

				@Override
				public void LoginError(String str) {
					// TODO Auto-generated method stub

				}
			});
	}
	private void Setlisteners() {

	}
	
	@Override
	protected void onDestroy() {
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
