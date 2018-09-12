package com.growatt.shinephone.adapter;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.OssDeviceDeticalBean;
import com.zhy.adapter.recyclerview.base.*;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by dg on 2017/6/14.
 */

public class OssSetTotalItem2Adapter implements ItemViewDelegate<OssDeviceDeticalBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_oss_devecedetical_img;
    }
    @Override
    public boolean isForViewType(OssDeviceDeticalBean item, int position) {
        return !item.isTextItem();
    }
    @Override
    public void convert(ViewHolder holder, OssDeviceDeticalBean ossDeviceDeticalBean, int position) {
        holder.setImageResource(R.id.ivName,ossDeviceDeticalBean.getImgResId());
        holder.setText(R.id.tvValue,ossDeviceDeticalBean.getValue2());
    }
}
