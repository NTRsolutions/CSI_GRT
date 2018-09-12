package com.growatt.shinephone.adapter;

import android.content.Context;

import com.growatt.shinephone.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by dg on 2017/8/14.
 * map:"name","value"
 */

public class StorageSp5kDeticalAdapter extends com.zhy.adapter.recyclerview.CommonAdapter<Map<String,String>>{
    private String name = "name";
    private String value = "value";
    public StorageSp5kDeticalAdapter(Context context, int layoutId, List<Map<String, String>> datas) {
        super(context, layoutId, datas);
    }
    @Override
    protected void convert(ViewHolder holder, Map<String, String> map, int position) {
        holder.setText(R.id.tvName,map.get(name));
        holder.setText(R.id.tvValue,map.get(value));
    }
}
