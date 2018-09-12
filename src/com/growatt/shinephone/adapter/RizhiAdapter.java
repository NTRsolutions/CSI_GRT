package com.growatt.shinephone.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.growatt.shinephone.R;

public class RizhiAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>>list;
	private LayoutInflater layoutInflater;
	public RizhiAdapter(Context context,List<Map<String, Object>>list){
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
			convertView = layoutInflater.inflate(R.layout.rizhi_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView7);
			hoder.tv2 = (TextView) convertView.findViewById(R.id.textView8);
			hoder.tv3 = (TextView) convertView.findViewById(R.id.textView9);
			hoder.tv4 = (TextView) convertView.findViewById(R.id.textView10);
			hoder.tv11 = (TextView) convertView.findViewById(R.id.textView11);
			hoder.tv5 = (TextView) convertView.findViewById(R.id.textView5);
			hoder.tv6 = (TextView) convertView.findViewById(R.id.textView6);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
		hoder.tv1.setText(list.get(position).get("deviceSerialNum").toString());
		hoder.tv3.setText(list.get(position).get("deviceType").toString());
		hoder.tv2.setText(list.get(position).get("eventId").toString());
		hoder.tv4.setText(list.get(position).get("eventName").toString());
		hoder.tv5.setText(list.get(position).get("eventName").toString());
		String s=list.get(position).get("occurTime").toString();
		hoder.tv6.setText(s.substring(0, s.length()-2));
		return convertView;
	}
	class ViewHoder {
		public TextView tv11;
		public TextView tv1;
		public TextView tv2;
		public TextView tv3;
		public TextView tv4;
		public TextView tv5;
		public TextView tv6;
	}
}