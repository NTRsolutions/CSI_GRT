package com.growatt.shinephone.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListview extends ListView{

	public MyListview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public MyListview(Context context) {
		super(context);
	}
	 public MyListview(Context context, AttributeSet attrs, int defStyle) {
		 super(context, attrs, defStyle);
	 }
	 @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		 int expandSpec = MeasureSpec.makeMeasureSpec(
				                 Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
