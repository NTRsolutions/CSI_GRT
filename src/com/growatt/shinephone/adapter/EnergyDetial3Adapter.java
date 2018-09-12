package com.growatt.shinephone.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.MyUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by dg on 2017/5/4.
 */

public class EnergyDetial3Adapter extends MyBaseAdapter<Entry> {
    public EnergyDetial3Adapter(Context context, List<Entry> list) {
        super(context, list);
    }
    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHoder hoder = null;
        if (convertView == null) {
            hoder = new ViewHoder();
            convertView = inflater.inflate(R.layout.item_energydetial_2, parent,false);
            hoder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            hoder.tvSoc = (TextView) convertView.findViewById(R.id.tvSoc);
            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        Entry entry = list.get(position);
        hoder.tvTime.setText( MyUtils.sdf_hm.format(new Date((long) (entry.getX() * MyUtils.minTamp))));
        hoder.tvSoc.setText(entry.getY() + "%");
        return convertView;
    }
    class ViewHoder {
        public TextView tvTime;
        public TextView tvSoc;
    }
}
