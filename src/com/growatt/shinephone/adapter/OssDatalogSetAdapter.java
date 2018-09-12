package com.growatt.shinephone.adapter;

import android.content.Context;

import com.growatt.shinephone.R;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by dg on 2017/5/22.
 */

public class OssDatalogSetAdapter extends com.zhy.adapter.recyclerview.CommonAdapter<String> {

    public OssDatalogSetAdapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {
        holder.setText(R.id.tvDatalogSet,s);
    }
}
