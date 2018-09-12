package com.growatt.shinephone.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PullableRelative extends RelativeLayout implements Pullable
{

	public PullableRelative(Context context)
	{
		super(context);
	}

	public PullableRelative(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public PullableRelative(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown()
	{
		return true;
	}

	@Override
	public boolean canPullUp()
	{
		/**
		 * 改成true是启动加载更多功能的
		 */
		return false;
	}

}
