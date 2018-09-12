package com.growatt.shinephone.adapter;

import android.content.Context;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.MyUtils;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by dg on 2017/6/2.
 */

public class OssServerListAdapter extends com.zhy.adapter.recyclerview.CommonAdapter<Map<String,String>> {
    public OssServerListAdapter(Context context, int layoutId, List<Map<String, String>> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, Map<String, String> map, int position) {
        if (map == null) return;
        StringBuilder sb = new StringBuilder();
        String url = map.get(Constant.OssServer_url);
        if (MyUtils.getLanguage(mContext) == 0){//中文
            sb.append(map.get(Constant.OssServer_cn));
        }else {
            sb.append(map.get(Constant.OssServer_en));
        }
        sb.append(":").append(url);
        holder.setText(R.id.tvItemServer,sb.toString());
    }
}
