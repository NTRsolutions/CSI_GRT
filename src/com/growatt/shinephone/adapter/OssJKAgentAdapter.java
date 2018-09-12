package com.growatt.shinephone.adapter;

import android.content.Context;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Constant;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import java.util.List;
import java.util.Map;

/**
 * Created by dg on 2017/6/6.
 * 集成商下代理商列表适配器
 */

public class OssJKAgentAdapter extends com.zhy.adapter.recyclerview.CommonAdapter<Map<String,String>> {
    public OssJKAgentAdapter(Context context, int layoutId, List<Map<String, String>> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Map<String, String> map, int position) {
        holder.setText(R.id.tvItemServer,map.get(Constant.Agent_Name));
    }
}
