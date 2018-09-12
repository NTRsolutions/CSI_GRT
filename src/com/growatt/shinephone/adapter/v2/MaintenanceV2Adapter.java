package com.growatt.shinephone.adapter.v2;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.growatt.shinephone.R;

import java.util.List;
import java.util.Map;

/**
 * Created by dg on 2017/10/12.
 */

public class MaintenanceV2Adapter extends BaseQuickAdapter<Map<String,String>,BaseViewHolder> {
    public MaintenanceV2Adapter(@LayoutRes int layoutResId, @Nullable List<Map<String, String>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, String> item) {
        helper.setText(R.id.tv_faqact_count,(helper.getAdapterPosition() + 1) + "");
        helper.setText(R.id.tv_faqact_title,item.get("title"));
    }
}
