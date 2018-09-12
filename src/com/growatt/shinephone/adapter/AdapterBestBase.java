package com.growatt.shinephone.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class AdapterBestBase<T> extends BaseAdapter {

	public Context mContext;
	public List<T> mList;
	public int layoutId;

	public AdapterBestBase(Context context, int layoutId, List<T> mList) {
		this.mContext = context;
		this.mList = mList;
		this.layoutId = layoutId;
	}

	public List<T> getList() {
		return mList;
	}

	public void appendToList(List<T> list) {
		if (list == null) {
			return;
		}
		mList.addAll(list);
		notifyDataSetChanged();
	}

	public void appendToTopList(List<T> list) {
		if (list == null) {
			return;
		}
		mList.addAll(0, list);
		notifyDataSetChanged();
	}

	public void appendT(T object) {
		if (object == null) {
			return;
		}
		mList.add(object);
		notifyDataSetChanged();
	}

	public void removeT(T object) {
		if (object == null) {
			return;
		}
		mList.remove(object);
		notifyDataSetChanged();
	}

	public void clear() {
		mList.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public T getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = ViewHolder.get(mContext, layoutId, convertView);
		getExView(holder, mList.get(position));
		return holder.getConvertView();
	}

	protected abstract void getExView(ViewHolder holder, T t);

}
