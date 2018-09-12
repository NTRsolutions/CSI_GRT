package com.growatt.shinephone.adapter;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.growatt.shinephone.R;

public class CommonAdapter extends MyBaseAdapter<Map<String, Object>>{

	public CommonAdapter(Context context, List<Map<String, Object>> list) {
		super(context, list);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHoder hoder = null;
		if (convertView == null) {
			hoder = new ViewHoder();
			convertView = inflater.inflate(R.layout.commonquestion_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
		hoder.tv1.setText(String.valueOf(list.get(position).get("title")));
		return convertView;
	}
	class ViewHoder {
	public TextView tv1;
}
}