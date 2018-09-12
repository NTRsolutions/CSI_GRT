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

public class UserdataAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>>list;
	private LayoutInflater layoutInflater;
	public UserdataAdapter(Context context,List<Map<String, Object>>list){
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
			convertView = layoutInflater.inflate(R.layout.userdata_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
			hoder.tv2 = (TextView) convertView.findViewById(R.id.textView2);
			hoder.tv3 = (TextView) convertView.findViewById(R.id.textView3);
			hoder.tv4 = (TextView) convertView.findViewById(R.id.textView4);
			hoder.tv5 = (TextView) convertView.findViewById(R.id.textView5);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
		String able= convertView.getResources().getConfiguration().locale.getCountry(); 
		String s=list.get(position).get("status").toString();
		if(able.equals("CN")||able.equals("cn")){
			if(s.equals("0")){
				hoder.tv1.setBackgroundResource(R.drawable.pend_ing_cn);
			}else if(s.equals("1")){
				hoder.tv1.setBackgroundResource(R.drawable.proce_ssing_cn);
			}else if(s.equals("2")){
				hoder.tv1.setBackgroundResource(R.drawable.proce_ssed_cn);
			}else if("3".equals(s)){
				hoder.tv1.setBackgroundResource(R.drawable.status_waiting);
			}
		}else{
			if(s.equals("0")){
				hoder.tv1.setBackgroundResource(R.drawable.pend_ing_en);
			}else if(s.equals("1")){
				hoder.tv1.setBackgroundResource(R.drawable.proce_ssing_en);
			}else if(s.equals("2")){
				hoder.tv1.setBackgroundResource(R.drawable.proce_ssed_en);
			}else if("3".equals(s)){
				hoder.tv1.setBackgroundResource(R.drawable.status_waiting_en);
			}
		}
		hoder.tv2.setText(list.get(position).get("title").toString());
		Object str=list.get(position).get("serviceQuestionReplyBean");
		if(str.toString().length()>10){
			@SuppressWarnings("unchecked")
			Map<String, Object>map=(Map<String, Object>) str;
			hoder.tv3.setText(map.get("userName").toString()+":");
			hoder.tv4.setText(map.get("message").toString());
		}else{
			hoder.tv4.setText(list.get(position).get("content").toString());
			String name=list.get(position).get("name").toString();
			if(name.equals("null")){
				hoder.tv3.setText("");
			}else{
				hoder.tv3.setText(name+":");
			}
		}
		String lasttime=list.get(position).get("lastTime").toString();
		lasttime=lasttime.substring(0, lasttime.length()-5);
		hoder.tv5.setText(lasttime);
		return convertView;
	}
	class ViewHoder {
		public TextView tv1;
		public TextView tv2;
		public TextView tv3;
		public TextView tv4;
		public TextView tv5;

	}
}