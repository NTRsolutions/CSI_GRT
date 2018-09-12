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

public class ZhibaoAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>>list;
	private LayoutInflater layoutInflater;
	public ZhibaoAdapter(Context context,List<Map<String, Object>>list){
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
			convertView = layoutInflater.inflate(R.layout.zhibao_item, null);
			hoder = new ViewHoder();
			hoder.tv3 = (TextView) convertView.findViewById(R.id.textView3);
			hoder.tv4 = (TextView) convertView.findViewById(R.id.textView6);
			hoder.tv5 = (TextView) convertView.findViewById(R.id.textView_sellbuy);
			hoder.tv6 = (TextView) convertView.findViewById(R.id.textView_buytime);
			hoder.name = (TextView) convertView.findViewById(R.id.textView_name);
			hoder.imageView=(ImageView)convertView.findViewById(R.id.imageView1);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
		String is=list.get(position).get("isHas").toString();
		if(is.equals("true")){
			hoder.tv3.setText(R.string.all_have);
		}else{
			hoder.tv3.setText(R.string.all_nothave);
		}
		hoder.tv4.setText(list.get(position).get("deviceSN").toString());
		hoder.tv5.setText(list.get(position).get("outTime").toString());
		hoder.tv6.setText(list.get(position).get("maturityTime").toString());
		
		String type=list.get(position).get("deviceType").toString();
		if(type.equals("Inverter")){
			hoder.name.setText(R.string.all_interver);
//			hoder.imageView.setImageResource(R.drawable.chunengji);
		}else if(type.equals("Storage")){
			hoder.name.setText(R.string.all_storage);
//			hoder.imageView.setImageResource(R.drawable.nibianqi);
		}else if(type.equals("Datalogger")){
			hoder.name.setText(R.string.all_else);
//			hoder.imageView.setImageResource(R.drawable.nibianqi);
		}else {
			hoder.name.setText(R.string.all_else);
//			hoder.imageView.setImageResource(R.drawable.nibianqi);
		}
		String name=list.get(position).get("model").toString();
		if(!name.equals("")){
		name=name.substring(0, name.length()-4);
		
//		ImageHttp.LoadImage(hoder.imageView, new Urlsutil().getProductImage+name,name);
		}
		return convertView;
	}
	class ViewHoder {
		public TextView name;
		public TextView tv4;
		public TextView tv3;
		public TextView tv5;
		public TextView tv6;
		public ImageView imageView;
	}
}