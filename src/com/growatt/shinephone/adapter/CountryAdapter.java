package com.growatt.shinephone.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.CitynameBean;

public class CountryAdapter extends MyBaseAdapter<CitynameBean> implements SectionIndexer{

	public CountryAdapter(Context context, List<CitynameBean> ts) {
		super(context, ts);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder vh;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.city_letter_item, parent,false);
			vh = new ViewHolder();
			vh.tvSortLetter = (TextView) convertView.findViewById(R.id.tv_item_sortletter);
			vh.tvCityname = (TextView) convertView.findViewById(R.id.textView1);
			convertView.setTag(vh);
		}else{
			vh = (ViewHolder) convertView.getTag();
		}
		
		CitynameBean bean = getItem(position);
		vh.tvSortLetter.setText(String.valueOf(bean.getSortLetter()));
		vh.tvCityname.setText(bean.getCityName());
		
		if(getPositionForSection(getSectionForPosition(position))==position){
			vh.tvSortLetter.setVisibility(View.VISIBLE);
		}else{
			vh.tvSortLetter.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	public class ViewHolder{
		TextView tvSortLetter,tvCityname;
	}

	@Override
	public int getPositionForSection(int section) {

		for(int i=0;i<getCount();i++){
			CitynameBean bean = getItem(i);
			if(bean.getSortLetter()==section){
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getSectionForPosition(int position) {
		CitynameBean bean = getItem(position);
		return bean.getSortLetter();
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}
	

}