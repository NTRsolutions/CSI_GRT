package com.growatt.shinephone.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.growatt.shinephone.R;

public class UserAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>>list;
	private LayoutInflater layoutInflater;
	public UserAdapter(Context context,List<Map<String, Object>>list){
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
			convertView = layoutInflater.inflate(R.layout.user_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
			hoder.imageView=(ImageView)convertView.findViewById(R.id.imageView1);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
//		TextPaint tp = hoder.tv1.getPaint(); 
//		tp.setFakeBoldText(true); 
		Log.i("tag", "tv="+hoder.tv1+",list="+list.toString());
		hoder.tv1.setText(list.get(position).get("str").toString());
		hoder.imageView.setImageResource((Integer) list.get(position).get("image"));
		return convertView;
	}
	class ViewHoder {
		public TextView tv1;
		public ImageView imageView;
	}
}