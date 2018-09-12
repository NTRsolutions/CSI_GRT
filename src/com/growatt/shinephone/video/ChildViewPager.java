package com.growatt.shinephone.video;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ChildViewPager extends ViewPager{

	 public ChildViewPager(Context context) {  
		             super(context);  
		          }  
		          public ChildViewPager(Context context, AttributeSet attrs) {  
		              super(context, attrs);  
		         }  
		         public boolean dispatchTouchEvent(MotionEvent ev) {  

		          //������仰������ ���߸�view���ҵĵ����¼������д�����Ҫ�谭�ҡ�

		            getParent().requestDisallowInterceptTouchEvent(true);
		            return super.dispatchTouchEvent(ev); 
		        } 



}
