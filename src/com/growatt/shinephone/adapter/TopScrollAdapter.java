package com.growatt.shinephone.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.growatt.shinephone.R;

import java.util.List;
import java.util.Map;

/**
 * Created by dg on 2017/1/4.
 */

public class TopScrollAdapter extends MyBaseAdapter<Map<String ,String>> {

    public TopScrollAdapter(Context context, List<Map<String, String>> list) {
        super(context, list);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (vh == null){
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_top_scroll,null);
            vh.tvEnergy = (TextView) convertView.findViewById(R.id.tvEnergy);
            vh.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tvEnergy.setText(list.get(position).get("energy"));
        vh.tvTime.setText(list.get(position).get("time"));

        return convertView;
    }
    class ViewHolder{
        TextView tvEnergy;
        TextView tvTime;
    }
}
