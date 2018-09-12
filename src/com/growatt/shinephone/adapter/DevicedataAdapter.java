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
import com.growatt.shinephone.util.AppUtils;

public class DevicedataAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>>list;
	private LayoutInflater layoutInflater;
	public DevicedataAdapter(Context context,List<Map<String, Object>>list){
		this.context=context;
		this.list=list;
		layoutInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHoder hoder = null;
		if (convertView == null) {
			hoder = new ViewHoder();
			convertView = layoutInflater.inflate(R.layout.devicedata_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView_data1);
			hoder.tv2 = (TextView) convertView.findViewById(R.id.textView_data2);
			hoder.tv3 = (TextView) convertView.findViewById(R.id.textView_data3);
			hoder.tv4 = (TextView) convertView.findViewById(R.id.textView_data4);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
		hoder.tv1.setText(list.get(position).get("pv").toString());
		hoder.tv2.setText(AppUtils.getFormat(list.get(position).get("current").toString()));
		hoder.tv3.setText(AppUtils.getFormat(list.get(position).get("volt").toString()));
		hoder.tv4.setText(AppUtils.getFormat(list.get(position).get("watt").toString()));
		return convertView;
	}
	class ViewHoder {
		public TextView tv1;
		public TextView tv2;
		public TextView tv3;
		public TextView tv4;
	}
}
