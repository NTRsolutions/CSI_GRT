package com.growatt.shinephone.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.growatt.shinephone.R;

public class RegionAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>>list;
	private boolean flag;
	private LayoutInflater layoutInflater;
	public RegionAdapter(Context context,List<Map<String, Object>>list,boolean flag){
		this.context=context;
		this.list=list;
		this.flag=flag;
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
			convertView = layoutInflater.inflate(R.layout.spinner_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
//		TextPaint tp = hoder.tv1.getPaint(); 
//		tp.setFakeBoldText(true); 
		if(flag==true){
		if(position==0){
			Resources resources=context.getResources(); 
			Drawable drawable=resources.getDrawable(R.color.white2);
				hoder.tv1.setBackgroundDrawable(drawable);
		}else{
			Resources resources=context.getResources(); 
			Drawable drawable=resources.getDrawable(R.color.white1);
				hoder.tv1.setBackgroundDrawable(drawable);
			
		}
		}else{
			Resources resources=context.getResources();
			Drawable drawable=resources.getDrawable(R.color.white);
				hoder.tv1.setBackgroundDrawable(drawable);
		}
		
		hoder.tv1.setText(list.get(position).get("plantName").toString());
		return convertView;
	}	
	class ViewHoder {
		public TextView tv1;
	}
}