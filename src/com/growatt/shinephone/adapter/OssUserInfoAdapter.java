package com.growatt.shinephone.adapter;

import android.content.Context;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.OssUserInfoBean;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * oss修改用户信息适配器
 * Created by dg on 2017/6/20.
 */

public class OssUserInfoAdapter extends com.zhy.adapter.recyclerview.CommonAdapter<OssUserInfoBean> {

    public OssUserInfoAdapter(Context context, int layoutId, List<OssUserInfoBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, OssUserInfoBean bean, int position) {
        holder.setText(R.id.tvName,bean.getName());
        holder.setText(R.id.tvValue,bean.getValue());
    }
}
