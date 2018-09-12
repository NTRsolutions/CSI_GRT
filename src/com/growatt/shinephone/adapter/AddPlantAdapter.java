package com.growatt.shinephone.adapter;

import java.util.List;

import com.growatt.shinephone.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddPlantAdapter extends MyBaseAdapter<String>{

	public AddPlantAdapter(Context context, List<String> list) {
		super(context, list);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHoder hoder = null;
		if (convertView == null) {
			hoder = new ViewHoder();
			convertView = inflater.inflate(R.layout.spinner_item_addplant, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}

		hoder.tv1.setText(list.get(position));
		
		return convertView;
	}
	class ViewHoder {
		public TextView tv1;
	}
}
