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

public class EnergyDetialAdapter extends MyBaseAdapter<List<Entry>> {
    List<Entry> list1;
    List<Entry> list2;
    List<Entry> list3;
    List<Entry> list4;
    public EnergyDetialAdapter(Context context, List<List<Entry>> list) {
        super(context, list);
        list1 = list.get(0);
        list2 = list.get(1);
        list3 = list.get(2);
        list4 = list.get(3);
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
            convertView = inflater.inflate(R.layout.item_energydetial_1, parent,false);
            hoder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            hoder.tvSolar = (TextView) convertView.findViewById(R.id.tvSolar);
            hoder.tvStorage = (TextView) convertView.findViewById(R.id.tvStorage);
            hoder.tvHome = (TextView) convertView.findViewById(R.id.tvHome);
            hoder.tvGrid = (TextView) convertView.findViewById(R.id.tvGrid);
            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        Entry entry1 = list1.get(position);
        Entry entry2 = list2.get(position);
        Entry entry3 = list3.get(position);
        Entry entry4 = list4.get(position);
        hoder.tvTime.setText( MyUtils.sdf_hm.format(new Date((long) (entry1.getX() * MyUtils.minTamp))));
        hoder.tvSolar.setText((float)(Math.round(entry1.getY() * 10))/10 + "");
        hoder.tvStorage.setText((float)(Math.round(entry2.getY() * 10))/10 + "");
        hoder.tvHome.setText((float)(Math.round(entry3.getY() * 10))/10 + "");
        hoder.tvGrid.setText((float)(Math.round(entry4.getY() * 10))/10 + "");
        return convertView;
    }
    class ViewHoder {
        public TextView tvTime;
        public TextView tvSolar;
        public TextView tvStorage;
        public TextView tvHome;
        public TextView tvGrid;
    }
}
