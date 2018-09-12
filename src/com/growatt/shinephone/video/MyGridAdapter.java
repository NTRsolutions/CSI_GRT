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
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.Urlsutil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyGridAdapter extends MyBaseAdapter<Map<String, String>>{

	public MyGridAdapter(Context context, List<Map<String, String>> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh=null;
		if(convertView==null){
			vh=new ViewHolder();
			convertView=inflater.inflate(R.layout.item_gridview_play, parent, false);
			vh.iv=(ImageView) convertView.findViewById(R.id.play_image);
			vh.tv=(TextView)convertView.findViewById(R.id.play_title);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		vh.tv.setText(list.get(position).get("videoTitle"));
		ImageHttp.ImageLoader(vh.iv, Urlsutil.getInstance().getProductImageInfo+list.get(position).get("videoImgurl"));
		return convertView;
	}

	
	class ViewHolder{
		ImageView iv;
		TextView tv;
	}
}
