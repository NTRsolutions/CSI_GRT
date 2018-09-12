package com.growatt.shinephone.adapter;

import android.content.Context;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.OssJKDeviceTitleBean;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;


/**
 * 集成商主入口设备管理：OssJKActivity
 * Created by dg on 2017/6/28.
 */

public class OssJKDeviceTitleAdapter extends com.zhy.adapter.recyclerview.CommonAdapter<OssJKDeviceTitleBean> {
    public OssJKDeviceTitleAdapter(Context context, int layoutId, List<OssJKDeviceTitleBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, OssJKDeviceTitleBean bean, int position) {
        holder.setImageResource(R.id.ivIcon,bean.getImgRes());
        holder.setText(R.id.tvContent,bean.getContent());
        holder.setText(R.id.tvTitle,bean.getTitle());
        holder.setText(R.id.tvUnit,bean.getUnit());
    }
}
