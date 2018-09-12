package com.growatt.shinephone.adapter;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.growatt.shinephone.R;

public class PriorityAdapter extends MyBaseAdapter<Map<String, Object>>{

	public PriorityAdapter(Context context, List<Map<String, Object>> list) {
		super(context, list);
	}
	@Override
	public View getItemView(int position, View convertView, ViewGroup arg2) {
		ViewHoder hoder = null;
		if (convertView == null) {
			hoder = new ViewHoder();
			convertView = inflater.inflate(R.layout.priorty_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
			hoder.imageView=(ImageView)convertView.findViewById(R.id.imageView1);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
		hoder.tv1.setText(String.valueOf(list.get(position).get("title")));
		hoder.imageView.setImageBitmap(BitmapFactory.decodeStream(context.getResources().openRawResource((Integer) list.get(position).get("image"))));
//		hoder.imageView.setImageResource((Integer) list.get(position).get("image"));
		return convertView;
	}
	class ViewHoder {
		public TextView tv1;
		public ImageView imageView;
	}

}