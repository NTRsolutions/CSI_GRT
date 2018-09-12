package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.ScrollViewPagerAdapter;
import com.growatt.shinephone.fragment.CommonQuestionfragment;
import com.growatt.shinephone.fragment.MyQuestionfragment;
import com.growatt.shinephone.util.Cons;

import java.util.ArrayList;

public class UserdataActivity extends DemoBase {

	private ImageView back;
	private ImageView putin;
	private RadioGroup radioGroup;
//	private FrameLayout FrameLayout;
	private ViewPager viewPager;
	private ArrayList<Fragment> fragments;
//	private QuestionFragmentAdapter fragmentAdapter;
	private ScrollViewPagerAdapter fragmentAdapter;
	private TextView tv1;
	private TextView tv2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userdata);
		SetViews();
		SetListeners();
	}
	private void SetViews() {
		back=(ImageView)findViewById(R.id.back);
		putin=(ImageView)findViewById(R.id.button1);
		tv1=(TextView)findViewById(R.id.textView1);
		tv2=(TextView)findViewById(R.id.textView2);
		tv1.setVisibility(View.VISIBLE);
		tv2.setVisibility(View.INVISIBLE);
		//����������ⰴť�Ƿ���ʾ
//		Cons.addQuestion = true;
//		if (Cons.isflag==true || !Cons.addQuestion){
//			putin.setVisibility(View.GONE);
//		}
		putin.setVisibility(View.GONE);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
//		FrameLayout=(FrameLayout)findViewById(R.id.frameLayout);
		viewPager=(ViewPager) findViewById(R.id.viewpager);
		fragments=new ArrayList<Fragment>();
		MyQuestionfragment f1=new MyQuestionfragment();
		CommonQuestionfragment f2=new CommonQuestionfragment();
		fragments.add(f1);
		fragments.add(f2);
		fragmentAdapter = new ScrollViewPagerAdapter(getSupportFragmentManager(),fragments);
		viewPager.setAdapter(fragmentAdapter);
//		Fragment fragment = (Fragment) fragmentAdapter.instantiateItem(FrameLayout, 0);
//		fragmentAdapter.setPrimaryItem(FrameLayout, 0, fragment);
//		fragmentAdapter.finishUpdate(FrameLayout);
		
	}
	private void SetListeners() {
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		putin.setOnClickListener(new View.OnClickListener(){
			
			@Override
			public void onClick(View arg0) { 
				if(Cons.addQuestion){
				AlertDialog dialog=new AlertDialog.Builder(UserdataActivity.this)
		    .setIcon(android.R.drawable.ic_menu_info_details)
		    .setTitle(R.string.reminder)
		    .setMessage(R.string.userdata_dialog_title)
		    .setNegativeButton(R.string.all_no, null)
		    .setPositiveButton(R.string.all_ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent=new Intent(UserdataActivity.this,PutinV2Activity.class);
//					Intent intent=new Intent(UserdataActivity.this,PutinActivity.class);
					startActivity(intent);
				}
			})
		    .create();
		    dialog.show();
			}
			}
		});
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				int index = 0;
//				switch (checkedId) {
//				case R.id.radio_button1:
//					index = 0;
//					break;
//				case R.id.radio_button2:
//					index = 1;
//					break;
//				}
//				// ��frameLayout�滻�ɵ�index��Fragment
//				Fragment fragment = (Fragment) fragmentAdapter.instantiateItem(FrameLayout, index);
//				fragmentAdapter.setPrimaryItem(FrameLayout, index, fragment);
//				fragmentAdapter.finishUpdate(FrameLayout);
				int index=group.indexOfChild(group.findViewById(checkedId));
				if(index==0){
					tv1.setVisibility(View.VISIBLE);
					tv2.setVisibility(View.INVISIBLE);
				}else{
					tv2.setVisibility(View.VISIBLE);
					tv1.setVisibility(View.INVISIBLE);
				}
				viewPager.setCurrentItem(index);
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				RadioButton rb=(RadioButton) radioGroup.getChildAt(arg0);
				rb.setChecked(true);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	public FragmentTransaction mFragmentTransaction;
    public FragmentManager fragmentManager;
    public String curFragmentTag = "";
	/*��fragment�Ĺ������У�����Ҫʵ���ⲿ��������������Ҫ�����ǣ���D���activity�ش����ݵ�
	������Ƭ�����������fragnment��ʱ�������ᾭ������������е�onActivityResult�ķ�����*/
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        /*���������ͨ����Ƭ�������е�Tag������ÿ����Ƭ�����ƣ�����ȡ��Ӧ��fragment*/
	        Fragment f = fragmentManager.findFragmentByTag(curFragmentTag);
	        /*Ȼ������Ƭ�е�����д��onActivityResult����*/
	        f.onActivityResult(requestCode, resultCode, data);
	    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
