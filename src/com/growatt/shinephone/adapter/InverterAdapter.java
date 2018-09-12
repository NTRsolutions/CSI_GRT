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

public class InverterAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>>list;
	private LayoutInflater layoutInflater;
	public InverterAdapter(Context context,List<Map<String, Object>>list){
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
			convertView = layoutInflater.inflate(R.layout.inverter_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
			hoder.tv2 = (TextView) convertView.findViewById(R.id.textView2);
			hoder.tv3 = (TextView) convertView.findViewById(R.id.textView3);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
//		TextPaint tp = hoder.tv1.getPaint(); 
//		tp.setFakeBoldText(true); 
		hoder.tv1.setText(list.get(position).get("str1").toString());
		hoder.tv2.setText(list.get(position).get("str2").toString());
		hoder.tv3.setBackgroundColor((Integer)list.get(position).get("color"));
		return convertView;
	}
	class ViewHoder {
		public TextView tv1;
		public TextView tv2;
		public TextView tv3;
	}
}