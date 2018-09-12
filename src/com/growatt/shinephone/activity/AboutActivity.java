package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.GlideCacheUtil;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.UpdateManager;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class AboutActivity extends DoActivity {

	private RelativeLayout update;
	private RelativeLayout agreement;
	private TextView phone;
	private RelativeLayout r3;
	private RelativeLayout r4;
	private TextView versionName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
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
		setHeaderTitle(headerView,getString(R.string.about_title));
	}
	private void SetViews() {
		versionName=(TextView)findViewById(R.id.textView1);
		try {
			String name=getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			versionName.setText(getString(R.string.app_name)+name);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		phone=(TextView)findViewById(R.id.textView7);
//		phone.setText(Cons.userBean.phoneNum);
		update=(RelativeLayout)findViewById(R.id.relative_update);
		if (Constant.google_package_name.equals(getPackageName())){
			MyUtils.hideAllView(View.GONE,update);
		}else {
			MyUtils.showAllView(update);
		}
		agreement=(RelativeLayout)findViewById(R.id.relative_agreement);
		r3=(RelativeLayout)findViewById(R.id.r3);
		r4=(RelativeLayout)findViewById(R.id.r4);
	}
	private UpdateManager up;
	private void SetListeners() {
        r3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				String str=getResources().getString(R.string.AboutActivity_determine_call);
				final String call=phone.getText().toString().trim();
				if(TextUtils.isEmpty(call)) return;
				AlertDialog builder = new AlertDialog.Builder(AboutActivity.this).setTitle(R.string.AboutActivity_call)
						.setMessage(str+call).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+call));
					               AboutActivity.this.startActivity(intent);//�ڲ���
					               finish();
							}
						}).setNegativeButton(R.string.all_no, null).create();
					builder.show();
				
			}
		});
//		r4.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				Mydialog.Show(AboutActivity.this, "");
//				String size = AppUtils.getAutoFileOrFilesSize(ShineApplication.Path+"/");
//				Mydialog.Dismiss();
//				AlertDialog builder = new AlertDialog.Builder(AboutActivity.this).setTitle(R.string.about_system)
//						.setMessage(size).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface arg0, int arg1) {
//								arg0.dismiss();
//								AppUtils.deleteSDFile(new File(ShineApplication.Path+"/"));
//								File file=new File(ShineApplication.Path+"/");
//								toast(R.string.about_cache_ok);
//								if(!file.exists()){
//									file.mkdir();
//								}
//							}
//						}).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface arg0, int arg1) {
//								arg0.dismiss();
//
//							}
//						}).create();
//					builder.show();
//			}
//		});
		r4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Mydialog.Show(AboutActivity.this, "");
				String size = GlideCacheUtil.getInstance().getCacheSize(mContext);
				Mydialog.Dismiss();
				AlertDialog builder = new AlertDialog.Builder(AboutActivity.this).setTitle(R.string.about_system)
						.setMessage(size).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();
								GlideCacheUtil.getInstance().clearImageAllCache(mContext);
								toast(R.string.about_cache_ok);
							}
						}).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();

							}
						}).create();
				builder.show();
			}
		});
		update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Mydialog.Show(AboutActivity.this,"");
				MyUtils.checkUpdate(AboutActivity.this,Constant.AboutActivity_Updata);
			}
		});
		agreement.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// ����
				startActivity(new Intent(AboutActivity.this,AgreementActivity.class));
			}
		});
		Locale locale = getResources().getConfiguration().locale;
		String language = locale.getLanguage().toLowerCase();
		int a;
		if(language.contains("cn")||language.contains("zh")){
			a=0;
		}else if(language.contains("en")){
			a=1;
		}else{
			a=3;
		}
		GetUtil.get(new Urlsutil().getServicePhoneNum+a, new GetListener() {
			
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject=new JSONObject(json);
					String num=jsonObject.getString("servicePhoneNum");
					phone.setText(num);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void error(String json) {
				
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
