package com.growatt.shinephone.video;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.MyBaseAdapter;
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.video.FAQAdapter.ViewHolder;

public class ManualAdapter extends MyBaseAdapter<Map<String, String>>{

	public ManualAdapter(Context context, List<Map<String, String>> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh=null;
		if(convertView==null){
			vh=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_manualact_listview, parent, false);
			vh.tvCount=(TextView) convertView.findViewById(R.id.tv_faqact_count);
			vh.tvTitle=(TextView)convertView.findViewById(R.id.tv_faqact_title);
			vh.iv=(ImageView)convertView.findViewById(R.id.iv_bg);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		vh.tvCount.setText((position+1)+"");
		vh.tvTitle.setText(list.get(position).get("title"));
		if(!TextUtils.isEmpty(list.get(position).get("imgurl"))){
			ImageHttp.ImageLoader(vh.iv, new Urlsutil().getProductImageInfo+list.get(position).get("imgurl"));
		}
		return convertView;
	}
   
	public class ViewHolder{
		TextView tvCount;
		TextView tvTitle;
		ImageView iv;
	}



}
