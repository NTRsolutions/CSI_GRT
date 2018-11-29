package com.growatt.shinephone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Constant;

import java.util.List;
import java.util.Map;

public class DataloggersAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>>list;
	private LayoutInflater layoutInflater;
	public DataloggersAdapter(Context context,List<Map<String, Object>>list){
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
			convertView = layoutInflater.inflate(R.layout.dataloggers_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView2);
			hoder.tv2 = (TextView) convertView.findViewById(R.id.textView4);
			hoder.tv3 = (TextView) convertView.findViewById(R.id.textView6);
			hoder.tv4 = (TextView) convertView.findViewById(R.id.textView8);
			hoder.tv5 = (TextView) convertView.findViewById(R.id.textView10);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
		String a;
		if(list.get(position).get("lost").toString().equals("true")){
			a=convertView.getResources().getString(R.string.all_Lost);
		}else{
			a=convertView.getResources().getString(R.string.all_online);
		}
		hoder.tv1.setText(list.get(position).get("alias").toString()+"("+a+")");
		hoder.tv2.setText(list.get(position).get("datalog_sn").toString());
		hoder.tv5.setText(list.get(position).get("update_interval").toString());
		String deviceType=list.get(position).get("device_type").toString();
		deviceType=deviceType.toLowerCase();
		if (Constant.WiFi_Type_ShineWIFI_S.equals(deviceType)||deviceType.contains(Constant.WIFI_TYPE_CSIWIFI)){
			hoder.tv4.setText("CSI-TL-WIFI");
		}else {
			hoder.tv4.setText(deviceType);
		}

//		hoder.tv5.setText(list.get(position).get("unit_id").toString());
		
		return convertView;
	}
	class ViewHoder {
		public TextView tv5;
		public TextView tv1;
		public TextView tv2;
		public TextView tv3;
		public TextView tv4;
	}
}
