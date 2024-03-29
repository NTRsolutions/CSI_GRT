package com.growatt.shinephone.view;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Interpolator;
import android.support.v4.view.ViewPager;
import android.widget.Scroller;

public class ViewPagerScroller extends Scroller{
	 private int mScrollDuration = 0;             // 滑动速度
	  
	    /**
	     * 设置速度速度
	     * @param duration
	     */
	    public void setScrollDuration(int duration){
	        this.mScrollDuration = duration;
	    }
	      
	    public ViewPagerScroller(Context context) {
	        super(context);
	    }
	  

	  
	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
	        super.startScroll(startX, startY, dx, dy, mScrollDuration);
	    }
	  
	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy) {
	        super.startScroll(startX, startY, dx, dy, mScrollDuration);
	    }
	  
	      
	      
	    public void initViewPagerScroll(ViewPager viewPager) {
	        try {
	            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
	            mScroller.setAccessible(true);
	            mScroller.set(viewPager, this);
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }
}
