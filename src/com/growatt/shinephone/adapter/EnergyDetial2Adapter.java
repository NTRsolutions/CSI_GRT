package com.growatt.shinephone.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.data.BarEntry;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.MyUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dg on 2017/5/4.
 */

public class EnergyDetial2Adapter extends MyBaseAdapter<ArrayList<BarEntry>> {
    List<BarEntry> list1;
    List<BarEntry> list2;
    public EnergyDetial2Adapter(Context context, List<ArrayList<BarEntry>> list) {
        super(context, list);
        list1 = list.get(0);
        list2 = list.get(1);
    }
    @Override
    public int getCount() {
        return list1.size();
    }
    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHoder hoder = null;
        if (convertView == null) {
            hoder = new ViewHoder();
            convertView = inflater.inflate(R.layout.item_energydetial_3, parent,false);
            hoder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            hoder.tvSolar = (TextView) convertView.findViewById(R.id.tvSolar);
            hoder.tvHome = (TextView) convertView.findViewById(R.id.tvHome);
            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        BarEntry entry1 = list1.get(position);
        BarEntry entry2 = list2.get(position);
        hoder.tvTime.setText( MyUtils.sdf.format(new Date((long)(entry1.getX() * MyUtils.dayTamp))));
        hoder.tvSolar.setText((float)(Math.round(entry1.getY() * 10))/10 + "");
        hoder.tvHome.setText((float)(Math.round(entry2.getY() * 10))/10 + "");
        return convertView;
    }
    class ViewHoder {
        public TextView tvTime;
        public TextView tvSolar;
        public TextView tvHome;
    }
}
