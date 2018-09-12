package com.growatt.shinephone.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.dao.ExampleUtil;
import com.growatt.shinephone.R;
import com.growatt.shinephone.fragment.EnergyFragment;
import com.growatt.shinephone.fragment.EnergySpf5kFragment;
import com.growatt.shinephone.fragment.Fragment1V2;
import com.growatt.shinephone.fragment.fragment2;
import com.growatt.shinephone.fragment.fragment3;
import com.growatt.shinephone.fragment.fragment4;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.GlideUtils;
import com.growatt.shinephone.util.SharedPreferencesUnit;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.util.v2.Fragment1Field;
import com.xsj.crasheye.Crasheye;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends DemoBase {
	private RadioGroup radioGroup;
	private long mExitTime;
	Intent intent;
	private FragmentManager fragmentManager;
	private Fragment1V2 f1V2;
	private fragment3 f3;
	private fragment4 f4;
	private EnergyFragment f2;
	private Fragment fragment2;
	private Fragment spf5kFrag;
	private Intent intent2;
	private int guideIndex;
	private FragmentTransaction fragmentTransaction;
	private Intent intent3;
	//	private FrameLayout flGuede;
	private ImageView ivGuide;
	private GlideUtils glide;
	private Activity mAct;
	@Override
    protected void onCreate(Bundle savedInstanceState){
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		mAct = this;
		fragmentManager = getSupportFragmentManager();
		ivGuide=(ImageView) findViewById(R.id.ivGuide);
		glide = GlideUtils.getInstance();
		initGuidePage();
        if(Cons.userBean!=null){
    		Crasheye.setUserIdentifier(Cons.userBean.getAccountName());
    		Crasheye.addExtraData("userName", Cons.userBean.toString());
            }
            Crasheye.addExtraData("url", Urlsutil.getInstance().GetUrl());
            Crasheye.getCrasheyeUUID();
        SetViews();
        SetListeners();
        registerMessageReceiver();  
        
    }
	private void showF1(){
		showF1V2();
//		 fragmentTransaction = fragmentManager.beginTransaction();
//		if(f1 == null ){
//			f1 = new fragment1();
//			fragmentTransaction.add(R.id.frameLayout,f1);
//		}
//		fragmentTransaction.commit();
//		hideFragment(fragmentTransaction);
//		fragmentTransaction.show(f1);
	}
	private void showF1V2(){
		fragmentTransaction = fragmentManager.beginTransaction();
		if(f1V2 == null ){
			f1V2 = new Fragment1V2();
			fragmentTransaction.add(R.id.frameLayout,f1V2);
		}
		fragmentTransaction.commit();
		hideFragment(fragmentTransaction);
		fragmentTransaction.show(f1V2);
	}
	private void showFragment2(){
		fragmentTransaction = fragmentManager.beginTransaction();
		if(fragment2 == null ){
			fragment2 = new fragment2();
			fragmentTransaction.add(R.id.frameLayout,fragment2);
		}
		fragmentTransaction.commit();
		hideFragment(fragmentTransaction);
		fragmentTransaction.show(fragment2);
	}
	private void showF2(){
		 fragmentTransaction = fragmentManager.beginTransaction();
		if(f2 == null ){
			f2 = new EnergyFragment();
			fragmentTransaction.add(R.id.frameLayout,f2);
		}
		fragmentTransaction.commit();
		hideFragment(fragmentTransaction);
		fragmentTransaction.show(f2);
	}
	private void showF2SPF5k(){
		fragmentTransaction = fragmentManager.beginTransaction();
		if(spf5kFrag == null ){
			spf5kFrag = new EnergySpf5kFragment();
			fragmentTransaction.add(R.id.frameLayout,spf5kFrag);
		}
		fragmentTransaction.commit();
		hideFragment(fragmentTransaction);
		fragmentTransaction.show(spf5kFrag);
	}
	private void showF3(){
		 fragmentTransaction = fragmentManager.beginTransaction();
		if(f3 == null ){
			f3 = new fragment3();
			fragmentTransaction.add(R.id.frameLayout,f3);
		}
		fragmentTransaction.commit();
		hideFragment(fragmentTransaction);
		fragmentTransaction.show(f3);
	}
	private void showF4(){
		 fragmentTransaction = fragmentManager.beginTransaction();
		if(f4 == null ){
			f4 = new fragment4();
			fragmentTransaction.add(R.id.frameLayout,f4);
		}
		fragmentTransaction.commit();
		hideFragment(fragmentTransaction);
		fragmentTransaction.show(f4);
	}
	/**
	 * 隐藏所有Fragment
	 * @param fragmentTransaction
	 */
	private void hideFragment(FragmentTransaction fragmentTransaction) {
		if (f1V2 != null){
			fragmentTransaction.hide(f1V2);
		}
		if(fragment2 != null){
			fragmentTransaction.hide(fragment2);
		}
		if(f2 != null){
			fragmentTransaction.hide(f2);
		}
		if(f3 != null){
			fragmentTransaction.hide(f3);
		}
		if(f4 != null){
			fragmentTransaction.hide(f4);
		}
		if (spf5kFrag != null){
			fragmentTransaction.hide(spf5kFrag);
		}

	}
	public void initGuidePage(){
		SharedPreferencesUnit sdf=SharedPreferencesUnit.getInstance(this);
		String guide=sdf.get("deviceGuide");
		try {
			if (!"1".equals(guide)){
				ivGuide.setVisibility(View.VISIBLE);
				sdf.put("deviceGuide","1");
				if(getLanguage()==0) {
					glide.showImageAct(mAct,R.drawable.device_guide_1,ivGuide);
//					ivGuide.setBackgroundResource(R.drawable.device_guide_1);
				}else{
					glide.showImageAct(mAct,R.drawable.device_guide_1_en,ivGuide);
//					flGuede.setBackgroundResource(R.drawable.device_guide_1_en);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			ivGuide.setVisibility(View.GONE);
			SharedPreferencesUnit.getInstance(this).put("deviceGuide","1");
		}

		ivGuide.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					guideIndex++;
					if(getLanguage()==0) {
						if (guideIndex == 1) {
							glide.showImageAct(mAct,R.drawable.device_guide_2,ivGuide,0);
//							flGuede.setBackgroundResource(R.drawable.device_guide_2);
						} else if (guideIndex == 2) {
							glide.showImageAct(mAct,R.drawable.device_guide_3,ivGuide,0);
//							flGuede.setBackgroundResource(R.drawable.device_guide_3);
//						} else if (guideIndex == 3) {
//							glide.showImageAct(mAct,R.drawable.pv_cn,ivGuide,0);
////							flGuede.setBackgroundResource(R.drawable.pv_cn);
//						}  else if (guideIndex == 4) {
//							glide.showImageAct(mAct,R.drawable.sp_cn,ivGuide,0);
////							flGuede.setBackgroundResource(R.drawable.sp_cn);
						}
						else if (guideIndex == 3) {
							glide.showImageAct(mAct,R.drawable.device_guide_4,ivGuide,0);
//							flGuede.setBackgroundResource(R.drawable.device_guide_4);
						}else {

							ivGuide.setVisibility(View.GONE);
						}
					}else{
						if (guideIndex == 1) {
							glide.showImageAct(mAct,R.drawable.device_guide_2_en,ivGuide,0);
//							flGuede.setBackgroundResource(R.drawable.device_guide_2_en);
						} else if (guideIndex == 2) {
							glide.showImageAct(mAct,R.drawable.device_guide_3_en,ivGuide,0);
//							flGuede.setBackgroundResource(R.drawable.device_guide_3_en);
//						} else if (guideIndex == 3) {
//							glide.showImageAct(mAct,R.drawable.pv_en,ivGuide,0);
////							flGuede.setBackgroundResource(R.drawable.pv_en);
//						} else if (guideIndex == 4) {
//							glide.showImageAct(mAct,R.drawable.sp_en,ivGuide,0);
////							flGuede.setBackgroundResource(R.drawable.sp_en);
						}
						else if (guideIndex == 3) {
							glide.showImageAct(mAct,R.drawable.device_guide_4_en,ivGuide,0);
//							flGuede.setBackgroundResource(R.drawable.device_guide_4_en);
						} else {

							ivGuide.setVisibility(View.GONE);
						}
					}
				}catch (Exception e){
					e.printStackTrace();
					ivGuide.setVisibility(View.GONE);
					SharedPreferencesUnit.getInstance(MainActivity.this).put("deviceGuide","1");
				}
			}
		});
	}
	private void SetViews() {
		intent=new Intent(Constant.Frag_Receiver);
		intent2=new Intent(Constant.Frag2_Receiver);
		intent3=new Intent(Constant.Energy_Receiver);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		showF1();
		}

	private void SetListeners() {
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_button1:
					sendBroadcast(intent);
					showF1();
					break;
				case R.id.radio_button2:
					if (!TextUtils.isEmpty(Fragment1Field.mixSn)){
//						showMix();
					}else if (TextUtils.isEmpty(Fragment1Field.sn)){
						sendBroadcast(intent2);
						showFragment2();
					}else {
						if ("2".equals(Fragment1Field.deviceType)){
							showF2SPF5k();
						}else {
							showF2();
						}
						sendBroadcast(intent3);
					}
					break;
				case R.id.radio_button3:
				showF3();
					break;
				case R.id.radio_button4:
						showF4();
					break;
				}
			}
		});
		JPushInterface.setAlias(MainActivity.this, Cons.userBean.getAccountName(),new TagAliasCallback() {
		
		@Override
		public void gotResult(int arg0, String alias, Set<String> tags) {
			if(arg0==0){
				System.out.println("设置别名成功");
				System.out.println(arg0);
				System.out.println(alias);
			}else{
				System.out.println("设置别名失败");
			}
		}
	});
	Set<String>tags=new HashSet<String>();
	tags.add(Cons.userBean.getAccountName()); 
	JPushInterface.setTags(MainActivity.this, tags, new TagAliasCallback() {
		
		@Override
		public void gotResult(int arg0, String arg1, Set<String> tags) {
			if(arg0==0){
				System.out.println("设置标签成功");
				System.out.println(arg0);
			}else{
				System.out.println("设置标签失败");
			}
		}
	});
	}
	private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.growatt.shinephone.activity.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}
	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
              String messge = intent.getStringExtra(KEY_MESSAGE);
              String extras = intent.getStringExtra(KEY_EXTRAS);
              StringBuilder showMsg = new StringBuilder();
              showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
              if (!ExampleUtil.isEmpty(extras)) {
            	  showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
              }
			}
		}
	}
		@Override
		protected void onDestroy() {
			super.onDestroy();
//			Cons.regMap=new RegisterMap();
			if(mMessageReceiver!=null){
				unregisterReceiver(mMessageReceiver);
				}
		}
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
					if ((System.currentTimeMillis() - mExitTime) > 2000) {
						toast(R.string.MainActivity_exit);
						mExitTime = System.currentTimeMillis();	
					} else {
						ShineApplication.getInstance().exit();
					}
					return true;
				}
			return super.onKeyDown(keyCode, event);
		}
}
