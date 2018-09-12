package com.growatt.shinephone.video;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.MyBaseAdapter;
import com.growatt.shinephone.video.PlayCenterAdapter.ViewHolder;

public class FAQAdapter extends MyBaseAdapter<Map<String, String>>{

	public FAQAdapter(Context context, List<Map<String, String>> list) {
		super(context, list);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh=null;
		if(convertView==null){
			vh=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_faqactivity_listview, parent, false);
			vh.tvCount=(TextView) convertView.findViewById(R.id.tv_faqact_count);
			vh.tvTitle=(TextView)convertView.findViewById(R.id.tv_faqact_title);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		vh.tvCount.setText((position+1)+"");
		vh.tvTitle.setText(list.get(position).get("title"));
		return convertView;
	}
   
	public class ViewHolder{
		TextView tvCount;
		TextView tvTitle;
	}

}
