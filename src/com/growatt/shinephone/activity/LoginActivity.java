package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.listener.OnViewEnableListener;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.SharedPreferencesUnit;
import com.growatt.shinephone.util.Urlsutil;
import com.mylhyl.circledialog.CircleDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends DemoBase implements TextView.OnEditorActionListener{
	private EditText et2;
	private EditText et1;
	private Button login;
	private Button register;
	private ImageView delectuser;
	private ImageView delectword;
	private Button mimaback;
	private ImageView imageView3;
	private Button demo;
	private Button register_yijian;
    //第一次进入
    private boolean isFirst;
	private EditText etServer;//修改服务器地址
	private Button btnServer;//修改服务器地址

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		SetViews();
		SetListeners();
		JPushInterface.init(this);
	}
	private void SetViews() {
		etServer=(EditText)findViewById(R.id.etServer);
		etServer.setText(OssUrls.ossUrl1);
		btnServer = (Button) findViewById(R.id.btnServer);
		et1=(EditText)findViewById(R.id.editText_username);
		et2=(EditText)findViewById(R.id.editText_pwd);
		et1.clearFocus();
		et2.clearFocus();
		et1.setSelected(true);
		et2.setSelected(false);
		login=(Button)findViewById(R.id.button_login);
		demo=(Button)findViewById(R.id.button_demo);
		register=(Button)findViewById(R.id.button_register);
		register_yijian=(Button)findViewById(R.id.button_mark);
        //根据语言显示或者隐藏一键注册
        if (getLanguage() == 0){
            register_yijian.setVisibility(View.VISIBLE);
        }else {
            register_yijian.setVisibility(View.INVISIBLE);
        }
		delectuser=(ImageView)findViewById(R.id.imageView4);
		delectword=(ImageView)findViewById(R.id.imageView5);
		imageView3=(ImageView)findViewById(R.id.imageView3);
		mimaback=(Button)findViewById(R.id.button_mimaback);
		String is=SqliteUtil.inquiryIs();
		Map<String, Object> map = SqliteUtil.inquirylogin();

		if(map.size()!=0){
			et1.setText(map.get("name").toString());
			et2.setText(map.get("pwd").toString());
			et1.setSelection(map.get("name").toString().length());
			et2.setSelection(map.get("pwd").toString().length());
			delectuser.setVisibility(View.VISIBLE);
			delectword.setVisibility(View.VISIBLE);
		}else{
			delectuser.setVisibility(View.GONE);
			delectword.setVisibility(View.GONE);
		}
	}
	@Override
	protected void onResume() {
//        if (isFirst){
//            MyUtils.showSoftInputFromWindow(this,et1);
//			isFirst = false;
//		}
		Map<String, Object> map = SqliteUtil.inquirylogin();
		if(map.size()!=0){
			et1.setText(map.get("name").toString());
			et2.setText(map.get("pwd").toString());
			et1.setSelection(map.get("name").toString().length());
			et2.setSelection(map.get("pwd").toString().length());
			delectuser.setVisibility(View.VISIBLE);
			delectword.setVisibility(View.VISIBLE);
		}
		//设置不自动登录
		SharedPreferencesUnit.getInstance(this).putInt(Constant.AUTO_LOGIN,0);
		//设置为oss登录
		SharedPreferencesUnit.getInstance(this).putInt(Constant.AUTO_LOGIN_TYPE,0);
		SqliteUtil.plant("");
		Cons.plant = null;
		super.onResume();
	}
	private void SetListeners() {
		et2.setOnEditorActionListener(this);
		demo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				demo.setEnabled(false);
				Mydialog.Show(LoginActivity.this, "");
				SqliteUtil.time((System.currentTimeMillis()+500000)+"");
				GetUtil.get(new Urlsutil().getServerUrlList+"&language="+getLanguage(), new GetListener() {
					@Override
					public void success(String json) {
						demo.setEnabled(true);
						Mydialog.Dismiss();
						Map<String, Object> map=AppUtils.toHashMap(json);
						String[] strs=new String[map.size()];
						List<String> listKey = new ArrayList<String>();
						final List<String> listValue = new ArrayList<String>();
						Iterator it = map.keySet().iterator();  
						while (it.hasNext()) {  
							String key = it.next().toString();  
							listKey.add(key);  
							listValue.add(map.get(key).toString());  
						}  
						for (int i = 0; i < strs.length; i++) {
							strs[i]=listKey.get(i).toString();
						}
						new CircleDialog.Builder(LoginActivity.this)
								.setItems(strs, new AdapterView.OnItemClickListener() {
									@Override
									public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
										Cons.isflag=true;
										String url = listValue.get(position).toString();
										SqliteUtil.time((System.currentTimeMillis()+500000)+"");
										demo.setEnabled(false);
										MyUtils.serverLogin(LoginActivity.this,url, "guest", "123456", new OnViewEnableListener() {
											@Override
											public void onViewEnable() {
												if (!demo.isEnabled()){
													demo.setEnabled(true);
												}
												SharedPreferencesUnit.getInstance(LoginActivity.this).putInt(Constant.AUTO_LOGIN,0);
											}
										});
									}
								})
								.setNegative(getString(R.string.all_no),null)
								.show();
					}

					@Override
					public void error(String json) {
						Mydialog.Dismiss();
						demo.setEnabled(true);
					}
				});
			}
		});
		mimaback.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(LoginActivity.this,RebackPwdActivity.class));
			}
		});
		et1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}
			@Override
			public void afterTextChanged(Editable s){

				if(s.length()>0){
					delectuser.setVisibility(View.VISIBLE);
				}else{
					delectuser.setVisibility(View.GONE);
				}
			}
		});
		et2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {  

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {  

			}

			@Override
			public void afterTextChanged(Editable s) {

				if(s.length()>0){
					delectword.setVisibility(View.VISIBLE);
				}else{
					delectword.setVisibility(View.GONE);
				}
			}
		});
		delectuser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				et1.setText("");
				et2.setText("");
			}
		});
		delectword.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				et2.setText("");

			}
		});

		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnLogin();
			}
		});
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(LoginActivity.this,CountryandCityActivity.class));
			}
		});
		register_yijian.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("activity","LoginActivity");
				startActivityForResult(intent, 105);
			}
		});
		/**
		 * 修改服务地址
		 */
		btnServer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String server = etServer.getText().toString().trim();
				String userName = et1.getText().toString().trim();
				if (!TextUtils.isEmpty(userName)){
					Urlsutil.serverurl = userName;
					String url = userName.substring(7);
					SqliteUtil.url(url);
				}
                 OssUrls.ossUrl1 = server;
				Constant.ossUrl = server;
			}
		});
	}
	public void btnLogin(){
			if(TextUtils.isEmpty(et1.getText().toString())){
				toast(R.string.login_no_user);
				return;
			}
			if(TextUtils.isEmpty(et2.getText().toString())){
				toast(R.string.login_no_pwd);
				return;
			}
			login.setEnabled(false);
			//正式登录
			MyUtils.ossErrAutoLogin(mContext, et1.getText().toString().trim(), et2.getText().toString().trim(), new OnViewEnableListener() {
				@Override
				public void onViewEnable() {
					if (!login.isEnabled()){
						login.setEnabled(true);
					}
				}
			});
			//测试server登录
//				MyUtils.serverLogin(mContext, "", et1.getText().toString().trim(), et2.getText().toString().trim(), new OnViewEnableListener() {
//					@Override
//					public void onViewEnable() {
//						login.setEnabled(true);
//					}
//				});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Cons.isExit=true;
			ShineApplication.getInstance().exit();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void setSerVer(View view) {
		Cons.server = et1.getText().toString().trim();
		et1.setText("");
		toast("设置成功");
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {

			case 105:
				if(data!=null){
					Bundle bundle=data.getExtras();
					String sn=bundle.getString("result");
					Intent intent = new Intent(LoginActivity.this,MobileRegisterActivity.class);
					intent.putExtra("sn" ,sn);
					jumpTo(intent,false);
				}
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch (actionId){
			case EditorInfo.IME_ACTION_NEXT:
				btnLogin();
				break;
		}
		return true;
	}
}
