package com.growatt.shinephone.tool;

import java.lang.reflect.Field;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

/**
 * 自定义的日期控件，只有年和月，没有日
 */
public class YMPickerDialog extends DatePickerDialog {
	public YMPickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear) {
		super(context, callBack, year, monthOfYear, 3);
		this.setTitle(year + "-" + (monthOfYear + 1) + "");
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int month, int day) {
		super.onDateChanged(view, year, month, day);
		this.setTitle(year + "-" + (month + 1) + "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.DatePickerDialog#show()
	 */
	@Override
	public void show() {
		super.show();
		DatePicker dp = findDatePicker((ViewGroup) this.getWindow().getDecorView());
		if (dp != null) {
			// Class c = dp.getClass();
			// Field f;
			try {
				int sysVersion = Integer.parseInt(VERSION.SDK);
				if (sysVersion > 13) {
					// f = c.getDeclaredField("mDaySpinner");
					// f.setAccessible(true);
					// LinearLayout l = (LinearLayout) f.get(dp);
					// l.setVisibility(View.GONE);

					((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
				} else {
					// f = c.getDeclaredField("mDayPicker");
					// f.setAccessible(true);
					// LinearLayout l = (LinearLayout) f.get(dp);
					// l.setVisibility(View.GONE);

					((ViewGroup) dp.getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 从当前Dialog中查找DatePicker子控件
	 * 
	 * @param group
	 * @return
	 */
	private DatePicker findDatePicker(ViewGroup group) {
		if (group != null) {
			for (int i = 0, j = group.getChildCount(); i < j; i++) {
				View child = group.getChildAt(i);
				if (child instanceof DatePicker) {
					return (DatePicker) child;
				} else if (child instanceof ViewGroup) {
					DatePicker result = findDatePicker((ViewGroup) child);
					if (result != null)
						return result;
				}
			}
		}
		return null;

	}

}
