package com.growatt.shinephone.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import com.growatt.shinephone.CacheVideoActivity;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.util.GetUtil.GetListener;

public class VideoCenterActivity extends DemoBase
{
	private List<Fragment> mTabContents = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;
	private ViewPager mViewPager;
	private List<String> mDatas ;
	private List<String> mIds ;
	private ViewPagerIndicator mIndicator;
	private View headerView;
	private TextView mSearch;
//��Ƶ����
	private List<Map<String, String>> videoList;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.vp_indicator);
		initList();
        initHeaderView();
		initView();
		initListener();
		//设置Tab上的标题
//		initDatas();
//		mIndicator.setTabItemTitles(mDatas);
//		mViewPager.setAdapter(mAdapter);
//		mIndicator.setViewPager(mViewPager,0);

	}
	private void initList() {
		 Mydialog.Show(this, "");
         GetUtil.get(Urlsutil.getInstance().getVideoDirList+"&language="+getLanguage(), new GetListener() {
			
			@Override
			public void success(String json) {
				Mydialog.Dismiss();
				try {
					JSONObject jsonObj=new JSONObject(json);
					String result = jsonObj.opt("result").toString();
					if("1".equals(result)){
						Object opt = jsonObj.opt("obj");
						if(opt!=null){
							JSONArray jsonArray = jsonObj.getJSONArray("obj");
							mDatas=new ArrayList<String>();
							mIds=new ArrayList<String>();
							for(int i=0;i<jsonArray.length();i++){
								JSONObject jsonObj2 = jsonArray.getJSONObject(i);
								mDatas.add(jsonObj2.optString("dirName"));
								mIds.add(jsonObj2.optString("id"));
							}
							
							if(mIds.size()>0){
								//TODO ���޸�
								getVideoInfo(mIds.get(0));
							}
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void error(String json) {
				
			}
		});
		
	}
     protected void getVideoInfo(String dirId) {
    	 Mydialog.Show(this, "");
    	 GetUtil.get(Urlsutil.getInstance().getVideoInfoList+"&language="+getLanguage()+"&dirId="+dirId, new GetListener() {
 			
 			@Override
 			public void success(String json) {
 				Mydialog.Dismiss();
				try {
					JSONObject jsonObj=new JSONObject(json);
					String result = jsonObj.opt("result").toString();
					if("1".equals(result)){
						Object opt = jsonObj.opt("obj");
						if(opt!=null){
							JSONArray jsonArray = jsonObj.getJSONArray("obj");
							videoList=new ArrayList<Map<String,String>>();
							for(int i=0;i<jsonArray.length();i++){
								//TODO
								Map<String, String> map=new HashMap<String, String>();
								JSONObject jsonObj2 = jsonArray.getJSONObject(i);
								map.put("videoPicurl", jsonObj2.opt("videoPicurl").toString());
								map.put("videoTitle", jsonObj2.opt("videoTitle").toString());
								map.put("videoImgurl", jsonObj2.opt("videoImgurl").toString());
								map.put("videoOutline", jsonObj2.opt("videoOutline").toString());
								map.put("videoName", jsonObj2.opt("videoName").toString());
								videoList.add(map);
							}
							Cons.videoList=videoList;
							//����tab����
							initDatas();
							mIndicator.setTabItemTitles(mDatas);
							mViewPager.setAdapter(mAdapter);
							mIndicator.setViewPager(mViewPager,0);
							
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
 			
 			@Override
 			public void error(String json) {
 				
 			}
 		});
	}
	private void initListener() {
		mSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(VideoCenterActivity.this,SearchActivity.class);
				intent.putExtra("title", getResources().getString(R.string.VideoCenterAct_title));
				startActivityForResult(intent,Constant.VideoCenterActivity_search);
			}
		});
	}
	private void initHeaderView() {
		headerView=findViewById(R.id.headerView);
//		headerView=findViewById(R.id.headerView);
//		TextView tv = (TextView) headerView.findViewById(R.id.tvTitle);
//		headerView.setBackgroundColor(Color.WHITE);
//		tv.setTextColor(Color.BLACK);
		setHeaderTitle(headerView, getResources().getString(R.string.VideoCenterAct_title));
		setHeaderImage(headerView, R.drawable.back, Position.LEFT, new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		setHeaderTvTitle(headerView, getString(R.string.VideoCenterAct_cache), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				jumpTo(CacheVideoActivity.class, false);
			}
		});
	}
	//	 android:layout_width="0dp"
//	            android:layout_height="fill_parent"
//	            android:layout_weight="1"
//	            android:gravity="center"
//	            android:text="����1"
//	            android:textColor="#ff000000"
//	            android:textSize="16sp"
	private void initDatas()
	{

		for (String data : mDatas)
		{   
//			TextView tv=new TextView(this);
//			tv.setGravity(Gravity.CENTER);
//			tv.setText(data);
//			tv.setTextColor(Color.BLACK);
//			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//			tv.setWidth(mTvWidth);
//			mIndicator.addView(tv);
			VpSimpleFragment fragment = VpSimpleFragment.newInstance(data);
			mTabContents.add(fragment);
		}

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{
			@Override
			public int getCount()
			{
				return mTabContents.size();
			}

			@Override
			public Fragment getItem(int position)
			{
				return mTabContents.get(position);
			}
		};
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==Constant.VideoCenterActivity_search){
			if(resultCode==1){
				String result=data.getStringExtra("search");
				
				if(!TextUtils.isEmpty(result)){
					mSearch.setText(result);
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void initView()
	{
		mViewPager = (ViewPager) findViewById(R.id.id_vp);
		mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
		mSearch=(TextView)findViewById(R.id.search);
	}


}
