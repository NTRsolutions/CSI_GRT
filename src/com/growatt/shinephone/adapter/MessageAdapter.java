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

public class MessageAdapter extends BaseAdapter{
	public Context context;
	public List<Map<String, Object>>list;
	private LayoutInflater layoutInflater;
	public MessageAdapter(List<Map<String, Object>>list,Context context){
		
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
			convertView = layoutInflater.inflate(R.layout.message_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
			hoder.tv2 = (TextView) convertView.findViewById(R.id.textView2);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
		hoder.tv1.setText(list.get(position).get("title").toString());
		hoder.tv2.setText(context.getString(R.string.MessageAdapter_time)+list.get(position).get("date").toString());
		return convertView;
	}
	class ViewHoder {
		public TextView tv1;
		public TextView tv2;
	}

}
