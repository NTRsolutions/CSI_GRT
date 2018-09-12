package com.growatt.shinephone.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.AZPlantBean;

import java.util.List;

/**
 * Created by dg on 2017/9/20.
 * 安装商电站列表adapter
 */

public class OssAZPlantListAdapter extends BaseQuickAdapter<AZPlantBean,BaseViewHolder>{
    public OssAZPlantListAdapter(@LayoutRes int layoutResId, @Nullable List<AZPlantBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, AZPlantBean item) {
        int pos = helper.getAdapterPosition();
        StringBuilder sb = new StringBuilder();
        if (pos != 0) {
            sb.append(item.getAccountName());
            sb.append(":");
        }
         sb.append(item.getPlantName());
        helper.setText(R.id.tvItemServer,sb.toString());
    }
}
