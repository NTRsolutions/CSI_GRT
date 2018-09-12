package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.PostUtil.postListener;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

@ContentView(R.layout.activity_amendaddress)
public class AmendsActivity extends DoActivity {
	@ViewInject(R.id.tvTitle2) TextView tvTitle2;
	private EditText et1;
	private String type;
	private String PhoneNum;
	private String email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initHeaderView();
		Bundle bundle=getIntent().getExtras();
		PhoneNum=bundle.getString("PhoneNum");
		email=bundle.getString("email");
		type=bundle.getString("type");
		String s=bundle.getString("");
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
		setHeaderTitle(headerView,getString(R.string.about_title));
		setHeaderTvTitle(headerView,getString(R.string.all_ok),new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				valPhoneOrEmail();
			}
		});
	}
	private void SetViews() {
		et1=(EditText)findViewById(R.id.editText1);
		if(type.equals("1")){
			et1.setText(PhoneNum);
			tvTitle2.setText(R.string.修改手机号);
			setHeaderTitle(headerView,getString(R.string.修改手机号));
		}else{
			et1.setText(email);
			tvTitle2.setText(R.string.修改邮箱);
			setHeaderTitle(headerView,getString(R.string.修改邮箱));
		}
	}

	/**
	 * 验证手机号或者邮箱
	 */
	public void valPhoneOrEmail(){
		if(type.equals("1")){
			String phone = et1.getText().toString().trim();
			Intent intent = new Intent(this,NewPhoneVerActivity.class);
			intent.putExtra("phone",phone);
			intent.putExtra("type",101);
			startActivityForResult(intent,1001);
		}else{
			String email = et1.getText().toString().trim();
			Intent intent = new Intent(this,NewEmailVerActivity.class);
			intent.putExtra("email",email);
			intent.putExtra("type",101);
			startActivityForResult(intent,1002);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == 1001) {//验证手机通过
			save(Cons.userBean.getPhoneNum());
		}
		if (resultCode == RESULT_OK && requestCode == 1002) {//验证邮箱通过
			save(Cons.userBean.getEmail());
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void save(final String valStr){
			if(Cons.isflag==true){
				toast(R.string.all_experience);
				return;
			}
			Mydialog.Show(AmendsActivity.this,"");
			PostUtil.post(new Urlsutil().updateUser, new postListener() {

				@Override
				public void success(String json) {
					Mydialog.Dismiss();
					try {
						JSONObject jsonObject=new JSONObject(json);
						if(jsonObject.get("success").toString().equals("true")){
							Intent intent=new Intent(AmendsActivity.this,UserActivity.class);
							Bundle bundle=new Bundle();
							if(type.equals("1")){
								bundle.putString("PhoneNum", valStr);
								bundle.putString("email", email);
								intent.putExtras(bundle);
								setResult(1, intent);
							}else if(type.equals("2")){
								bundle.putString("PhoneNum", PhoneNum);
								bundle.putString("email", valStr);
								intent.putExtras(bundle);
								setResult(2, intent);
							}
							toast(R.string.all_success);
							finish();
						} else if ("701".equals(jsonObject.get("msg").toString())){
							toast(R.string.m7);
						}else{
							toast(R.string.all_failed);
						}
					} catch (JSONException e) {
						e.printStackTrace();
						Mydialog.Dismiss();
					}
				}
				@Override
				public void Params(Map<String, String> params) {
					params.put("accountName", Cons.userBean.accountName);
					if(type.equals("1")){
						params.put("phoneNum",  valStr);
						params.put("email",  email);
					}else{
						params.put("phoneNum",  PhoneNum);
						params.put("email",  valStr);
					}
				}
				@Override
				public void LoginError(String str) {}
			});

	}
	private void SetListeners() {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
