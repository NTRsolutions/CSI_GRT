package com.growatt.shinephone.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.growatt.shinephone.R;

public class AnalyseAdapter extends BaseAdapter{
	private List<Map<String, Object>>list;
	private Context context;
	private LayoutInflater layoutInflater;
	private String a="";
	private String b="";
	public AnalyseAdapter(Context context,List<Map<String, Object>>list){
		this.context=context;
		this.list=list;
		layoutInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHoder hoder = null;
		if (convertView == null) {
			hoder = new ViewHoder();
			convertView = layoutInflater.inflate(R.layout.analyse_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
			hoder.tv2 = (TextView) convertView.findViewById(R.id.textView2);
			hoder.tv3 = (TextView) convertView.findViewById(R.id.textView3);
			hoder.imageView=(ImageView)convertView.findViewById(R.id.imageView1);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
		if(position!=3){
		 a=context.getResources().getString(R.string.all_time_day);
		 b=context.getResources().getString(R.string.all_time_month);
		}else{
			a=context.getResources().getString(R.string.all_Take_charge);
			b=context.getResources().getString(R.string.all_save_charge);
			
		}
		hoder.tv1.setText(list.get(position).get("title").toString());
		hoder.tv2.setText(a+":"+list.get(position).get("day").toString());
		hoder.tv3.setText(b+":"+list.get(position).get("mouth").toString());
		hoder.imageView.setImageResource((Integer)list.get(position).get("image"));
		return convertView;
	}
	class ViewHoder {
		public TextView tv1;
		public TextView tv2;
		public TextView tv3;
		public ImageView imageView;
	}

}
