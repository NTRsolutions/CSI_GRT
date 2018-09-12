package com.growatt.shinephone.adapter;

import android.content.Context;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.OssJKQuesTitleBean;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by dg on 2017/6/28.
 */

public class OssJKQuesTitleAdapter extends com.zhy.adapter.recyclerview.CommonAdapter<OssJKQuesTitleBean> {
    public OssJKQuesTitleAdapter(Context context, int layoutId, List<OssJKQuesTitleBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, OssJKQuesTitleBean bean, int position) {
        holder.setImageResource(R.id.ivIcon,bean.getImgRes());
        holder.setText(R.id.tvTitle,bean.getTitle());
        holder.setText(R.id.tvContent,bean.getContent());
    }
}
