package com.growatt.shinephone.adapter;

import java.util.List;

import com.growatt.shinephone.activity.ShineApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter{
    public Context context;
    public List<T> list;
    public LayoutInflater inflater;
	public MyBaseAdapter(Context context, List<T> list) {
		super();
		this.context = context;
		this.list = list;
		//inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(context==null){
			inflater=LayoutInflater.from(ShineApplication.context);
		}else{
			inflater = LayoutInflater.from(context);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public T getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getItemView(position, convertView, parent);
	}
	
	public abstract View getItemView(int position, View convertView, ViewGroup parent);
	
	public void addAll(List<T> addList,boolean isClear){
		if(isClear){
			list.clear();
		}
		list.addAll(addList);
		notifyDataSetChanged();
	}
	public void remove(T t){
		list.remove(t);
		notifyDataSetChanged();
	}
   
	public void add(T t){
		list.add(t);
		notifyDataSetChanged();
	}
}
