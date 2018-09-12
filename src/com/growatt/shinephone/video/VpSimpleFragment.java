package com.growatt.shinephone.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.view.ViewPagerScroller;

public class VpSimpleFragment extends Fragment
{
	public static final String BUNDLE_TITLE = "title";
	private String mTitle = "DefaultValue";
    View view;
    View headerView;
	private ViewPager mViewPager;
//	 int[] imgs={R.drawable.pic1,R.drawable.pic1,R.drawable.pic1,R.drawable.pic1};
	private MyPagerAdapter adapter;
//	private JazzyViewPager jazzViewPager;
	private int currentItem = 0; //
	private ScheduledExecutorService se;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			mViewPager.setCurrentItem(currentItem);// �л���ǰ��ʾ��ͼƬ
		};
	};
	private HeaderGridView mGridView;
	List<Map<String, String>> mGridList;
	private List<ImageView> imageViews;
	private ImageView iv;//
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		view=getActivity().getLayoutInflater().inflate(R.layout.fragment_vp, null);
		headerView=getActivity().getLayoutInflater().inflate(R.layout.header_fragvp_gridview, null);
		
//		Bundle arguments = getArguments();
//		if (arguments != null)
//		{
//			mTitle = arguments.getString(BUNDLE_TITLE);
//		}
//
//		TextView tv = new TextView(getActivity());
//		tv.setText(mTitle);
//		tv.setGravity(Gravity.CENTER);
        initView();
        initImageView();
		return view;
	}

	private void initImageView() {
		if(imageViews==null){
			imageViews = new ArrayList<ImageView>();
			}else{
				imageViews.clear();
			}
			
			for (int i = 0; i < Cons.videoList.size(); i++) {
				ImageView imageView = new ImageView(getActivity());

				imageView.setImageResource(R.drawable.pic_service);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				imageView.setScaleType(ScaleType.FIT_XY);
				imageViews.add(imageView);
			}
			//viewPager.setAdapter(vpAdapter);
			adapter.notifyDataSetChanged();
			//����ָʾ��
//			setIndicator();
	}

	private void initView() {
		mViewPager=(ChildViewPager)headerView.findViewById(R.id.viewPager);
		mViewPager.setPageMargin(20);
		mViewPager.setOffscreenPageLimit(3);
       imageViews=new ArrayList<ImageView>();
		iv=new ImageView(getActivity());
		iv.setImageResource(R.drawable.pic_service);
		iv.setScaleType(ScaleType.CENTER_CROP);
		imageViews.add(iv);
		
		adapter=new MyPagerAdapter(getActivity(), imageViews);
		mViewPager.setCurrentItem(Cons.videoList.size()*500);
		mViewPager.setAdapter(adapter);
		//��װ��һ���࣬����viewpager�Զ��ֲ�ʱ�����һ�ŵ���һ�ų��������ֲ�һ�飬��ʱ������Ϊ0����
		 ViewPagerScroller viewPagerScroller = new ViewPagerScroller(getActivity());
	      viewPagerScroller.initViewPagerScroll(mViewPager); 
		mViewPager.setOnPageChangeListener(new MyPageChangeListener());
		 mViewPager.setPageTransformer(true, new ScaleInTransformer());
		 
		 mGridView=(HeaderGridView)view.findViewById(R.id.gridView);
		 mGridList=new ArrayList<Map<String,String>>();
		 for(int i=0;i<Cons.videoList.size();i++){
			 Map<String, String> map2 = Cons.videoList.get(i);
			Map<String, String> map=new HashMap<String, String>();
			map.put("videoImgurl", map2.get("videoImgurl"));
			map.put("videoTitle", map2.get("videoTitle"));
			map.put("videoPicurl", map2.get("videoPicurl"));
			map.put("videoOutline", map2.get("videoOutline"));
			map.put("videoName", map2.get("videoName"));
			 mGridList.add(map);
		 }
		 mGridView.addHeaderView(headerView);
		 mGridView.setAdapter(new MyGridAdapter(getActivity(), mGridList));
		 mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(position>1){
				Intent intent=new Intent(getActivity(), PlayCenterActivity.class);
				intent.putExtra("videoImgurl", mGridList.get(position-2).get("videoImgurl"));
				intent.putExtra("videoTitle", mGridList.get(position-2).get("videoTitle"));
				intent.putExtra("videoPicurl", mGridList.get(position-2).get("videoPicurl"));
				intent.putExtra("videoOutline", mGridList.get(position-2).get("videoOutline"));
				intent.putExtra("videoName", mGridList.get(position-2).get("videoName"));
				getActivity().startActivity(intent);
				}
			}
		});
	}

	public static VpSimpleFragment newInstance(String title)
	{
		Bundle bundle = new Bundle();
		bundle.putString(BUNDLE_TITLE, title);
		VpSimpleFragment fragment = new VpSimpleFragment();
		fragment.setArguments(bundle);
		return fragment;
	}
	
	//by gai
	 @Override
	  public void onResume() {
	  	   se = Executors.newSingleThreadScheduledExecutor();
	  		// ��Activity��ý��㣬ÿ�������л�һ��ͼƬ��ʾ
	  		se.scheduleAtFixedRate(new ScrollTask(), 1, 5, TimeUnit.SECONDS);
	  	super.onResume();
	  }
	  	@Override
	  	public void onPause() {
	  		// ��Activityʧȥ�����ʱ��ֹͣ�л�
	  		se.shutdown();
	  		super.onPause();
	  	}
	  	/**
	  	 *
	  	 * 
	  	 * @author Administrator
	  	 * 
	  	 */
	  	private class ScrollTask implements Runnable {

	  		public void run() {
	  			synchronized (mViewPager) {
	  				//				System.out.println("currentItem: " + currentItem);
//	  				currentItem = (currentItem + 1) % imageViews.size();
	  				if(currentItem<Cons.videoList.size()*1000-2){
	  					currentItem = currentItem + 1;
	  				}else{
	  					currentItem=Cons.videoList.size()*500;
	  				}
	  				handler.obtainMessage().sendToTarget(); // ͨ��Handler�л�ͼƬ
	  			}
	  		}

	  	}
	
		/**
		 * ��ViewPager��ҳ���״̬�����ı�ʱ����
		 * 
		 * @author Administrator
		 * 
		 */
		private class MyPageChangeListener implements OnPageChangeListener {
			private int oldPosition = 0;

			/**
			 * This method will be invoked when a new page becomes selected.
			 * position: Position index of the new selected page.
			 */
			public void onPageSelected(int position) {
				currentItem = position;
				oldPosition = position;
				mGridView.requestDisallowInterceptTouchEvent(false);
			}

			public void onPageScrollStateChanged(int arg0) {
				mGridView.requestDisallowInterceptTouchEvent(true);
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
                    
			}
		}

	
	
	
	
	
	
	
	
	
	
	

//
//	    @Override
//	    public boolean onOptionsItemSelected(MenuItem item)
//	    {
//	        String title = item.getTitle().toString();
//	        mViewPager.setCurrentItem(imgs.length*500);
//	        mViewPager.setAdapter(adapter);
//
//	        if ("RotateDown".equals(title))
//	        {
//	            mViewPager.setPageTransformer(true, new RotateDownPageTransformer());
//	        } else if ("RotateUp".equals(title))
//	        {
//	            mViewPager.setPageTransformer(true, new RotateUpPageTransformer());
//	        } else if ("RotateY".equals(title))
//	        {
//	            mViewPager.setPageTransformer(true, new RotateYTransformer(45));
//	        } else if ("Standard".equals(title))
//	        {
////	            mViewPager.setClipChildren(false);
//	            mViewPager.setPageTransformer(true, NonPageTransformer.INSTANCE);
//	        } else if ("Alpha".equals(title))
//	        {
////	            mViewPager.setClipChildren(false);
//	            mViewPager.setPageTransformer(true, new AlphaPageTransformer());
//	        } else if ("ScaleIn".equals(title))
//	        {
//	            mViewPager.setPageTransformer(true, new ScaleInTransformer());
//	        } else if ("RotateDown and Alpha".equals(title))
//	        {
//	            mViewPager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer()));
//	        }else if ("RotateDown and Alpha And ScaleIn".equals(title))
//	        {
//	            mViewPager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer(new ScaleInTransformer())));
//	        }
//
//
//	        return true;
//	    }
}
