package com.growatt.shinephone.video;


import java.util.List;
import java.util.Map;

import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.Urlsutil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;


public class MyPagerAdapter extends PagerAdapter{
	List<ImageView> imageViews;
	Context context;
    public MyPagerAdapter() {
	}
    public MyPagerAdapter(Context context,List<ImageView> imageViews) {
    	this.imageViews=imageViews;
    	this.context=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		return imageViews.size();
		return Cons.videoList.size()*1000;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

//	@Override
//	public Object instantiateItem(ViewGroup container, final int index) {
//		ImageView iv=imageViews.get(index);
//		if(Cons.videoList!=null&&Cons.videoList.size()>0){
////		ImageLoader.getInstance().displayImage(Urlsutil.getInstance().GetUrl()+"/"+advlist.get(index).get("name"),imageViews.get(index),options );
//			ImageHttp.ImageLoader(iv, Urlsutil.getInstance().getProductImageInfo+Cons.videoList.get(index%imageViews.size()).get("videoImgurl"));
//		}
//		iv.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//              Intent intent=new Intent(context, PlayerActivity.class);
//              intent.putExtra("videoPicurl", Cons.videoList.get(index).get("videoPicurl"));
//				intent.putExtra("videoName", Cons.videoList.get(index).get("videoName"));
//				intent.putExtra("type", Constant.OtherToPlayerAct);
//				context.startActivity(intent);
//			}
//		});
//		container.addView(iv);
//		return iv;
//	}
	@Override
	public Object instantiateItem(ViewGroup container, final int index) {
		ImageView iv=new ImageView(context);
		final Map<String, String> map = Cons.videoList.get(index%Cons.videoList.size());
		iv.setScaleType(ScaleType.FIT_XY);
		if(Cons.videoList!=null&&Cons.videoList.size()>0){
//	ImageLoader.getInstance().displayImage(Urlsutil.getInstance().GetUrl()+"/"+advlist.get(index).get("name"),imageViews.get(index),options );
		ImageHttp.ImageLoader(iv, Urlsutil.getInstance().getProductImageInfo+map.get("videoImgurl"));
	}
		iv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
              Intent intent=new Intent(context, PlayerActivity.class);
              intent.putExtra("videoPicurl", map.get("videoPicurl"));
				intent.putExtra("videoName",map.get("videoName"));
				intent.putExtra("type", Constant.OtherToPlayerAct);
				context.startActivity(intent);
			}
		});
		container.addView(iv);
		return iv;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((ImageView)object);
	}
}
