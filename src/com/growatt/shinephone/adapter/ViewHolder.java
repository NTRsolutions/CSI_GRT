package com.growatt.shinephone.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.growatt.shinephone.util.ImageHttp;

public class ViewHolder {

	private View mConvertView;
	private SparseArray<View> mViews;

	public ViewHolder(Context context, int layoutId) {
		mViews = new SparseArray<View>();
		mConvertView = View.inflate(context, layoutId, null);
		mConvertView.setTag(this);
	}

	public static ViewHolder get(Context context, int layoutId, View convertView) {
		if (convertView == null) {
			return new ViewHolder(context, layoutId);
		} else {
			return (ViewHolder) convertView.getTag();
		}
	}

	public View getView(int viewId) {
		View v = mViews.get(viewId);
		if (v == null) {
			v = mConvertView.findViewById(viewId);
			mViews.put(viewId, v);
		}
		return v;
	}

	public View getConvertView() {
		return mConvertView;
	}

	public ViewHolder setText(int viewId, String content,int color) {
		TextView v = (TextView) getView(viewId);
		v.setText(content);
		v.setBackgroundColor(color);
		return this;
	}
	public ViewHolder setText(int viewId, int content,int color) {
		TextView v = (TextView) getView(viewId);
		v.setText(content);
		v.setBackgroundColor(color);
		return this;
	}
	public ViewHolder setText(int viewId, String content) {
		TextView v = (TextView) getView(viewId);
		v.setText(content);
		return this;
	}
	public ViewHolder setText(int viewId, int content) {
		TextView v = (TextView) getView(viewId);
		v.setText(content);
		return this;
	}

	public ViewHolder setImageView(int viewId, String uri) {
		ImageView v = (ImageView) getView(viewId);
//		ImageLoader.getInstance().displayImage(uri, v,AppUtils.Options());
		ImageHttp.ImageLoader(v, uri);
		return this;
	}

}

