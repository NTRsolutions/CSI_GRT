package com.growatt.shinephone.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_register2)
public class RegisterActivity extends DoActivity implements TextView.OnEditorActionListener{
	@ViewInject(R.id.tvUrl) TextView tvUrl;
	private EditText username;
	private EditText password;
	private EditText password2;
	private EditText phone;
	private EditText email;
	private EditText agentCode;
	private String code;
	private LinearLayout rela;
	private CheckBox checkBox;
	private TextView terms;
	private boolean isFirst = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		code=getIntent().getExtras().getString("code");
		initHeaderView();
		SetListeners();
		SetViews();

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isFirst){
			MyUtils.showSoftInputFromWindow(this,username);
		}
		isFirst = false;
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
		setHeaderTitle(headerView,getString(R.string.all_register));
	}
	@Event(value = R.id.btnNext)
	private void btnNext(View v){
		registerNext();
	}
	private void SetListeners() {
		rela=(LinearLayout)findViewById(R.id.relative_agent);
		if(code.equals("1")){
			rela.setVisibility(View.VISIBLE);
		}else{
			rela.setVisibility(View.GONE);
		}
		username=(EditText)findViewById(R.id.editText1);
		password=(EditText)findViewById(R.id.editText2);
		password2=(EditText)findViewById(R.id.editText3);
		phone=(EditText)findViewById(R.id.editText4);
		email=(EditText)findViewById(R.id.editText5);
		agentCode=(EditText)findViewById(R.id.editText6);
		checkBox=(CheckBox)findViewById(R.id.checkBox1);
		terms=(TextView)findViewById(R.id.textView4);
		terms.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); //下划线
		terms.getPaint().setAntiAlias(true);//抗锯齿
		impAction(agentCode);
	}
	public void registerNext(){
			if(!checkBox.isChecked()){
				toast(R.string.all_terms_message);
				return;
			}
			if(username.getText().toString().equals("")){
				toast(R.string.register_username_no_blank);
				return;
			}
			if(username.getText().toString().length()<3){
				toast(R.string.register_username_no_short);
				return;
			}
			if(password.getText().toString().equals("")){
				toast(R.string.register_password_no_blank);
				return;
			}
			if(password2.getText().toString().equals("")){
				toast(R.string.register_password_no_blank);
				return;
			}
			if(phone.getText().toString().equals("")){
				toast(R.string.register_phone_no_blank);
				return;
			}
			if(email.getText().toString().equals("")){
				toast(R.string.register_email_no_blank);
				return;
			}
			if(!password2.getText().toString().trim().equals(password.getText().toString().trim())){
				toast(R.string.register_password_no_same);
				return;
			}
			Cons.regMap.setRegEmail(email.getText().toString().trim());
			Cons.regMap.setRegPassword(password.getText().toString().trim());
			Cons.regMap.setRegPhoneNumber(phone.getText().toString().trim());
			Cons.regMap.setRegUserName(username.getText().toString().trim());
			Cons.regMap.setAgentCode(agentCode.getText().toString().trim());
			startActivity(new Intent(RegisterActivity.this,DatalogCheckActivity.class));
//				finish();
	}
	private void SetViews() {
		tvUrl.setText(Urlsutil.url_host);
		terms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(RegisterActivity.this,AgreementActivity.class));
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
	public void impAction(EditText... ets){
		for (EditText et:ets){
			et.setOnEditorActionListener(this);
		}
	}


	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch (actionId){
			case EditorInfo.IME_ACTION_NEXT:
				if (v == agentCode){
					registerNext();
				}
				break;
		}
		return true;
	}
}
