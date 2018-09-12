package com.growatt.shinephone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.growatt.shinephone.R;

public class InverterDpsSpinnerAdapter extends BaseAdapter{
	private Context context;
	private int[]list;
	private LayoutInflater layoutInflater;
	public InverterDpsSpinnerAdapter(Context context, int[]list){
		this.context=context;
		this.list=list;
		layoutInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list[arg0];
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
			convertView = layoutInflater.inflate(R.layout.inverterdps_popup_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
//		TextPaint tp = hoder.tv1.getPaint(); 
//		tp.setFakeBoldText(true); 
		hoder.tv1.setText(context.getResources().getString(list[position]));
		return convertView;
	}
	class ViewHoder {
		public TextView tv1;
	}
}