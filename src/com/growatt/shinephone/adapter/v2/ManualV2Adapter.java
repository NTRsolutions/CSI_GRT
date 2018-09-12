package com.growatt.shinephone.adapter.v2;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.GlideUtils;
import com.growatt.shinephone.util.Urlsutil;

import java.util.List;
import java.util.Map;

/**
 * Created by dg on 2017/10/12.
 * ManualActivity知识手册适配器
 * FAQActivity 常见问题适配器
 */

public class ManualV2Adapter extends BaseQuickAdapter<Map<String,String>,BaseViewHolder> {
    public ManualV2Adapter(@LayoutRes int layoutResId, @Nullable List<Map<String, String>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, String> item) {
        helper.setText(R.id.tv_faqact_count,(helper.getAdapterPosition() + 1) + "");
        helper.setText(R.id.tv_faqact_title,item.get("title"));
        ImageView imageView = helper.getView(R.id.iv_bg);
        if(!TextUtils.isEmpty(item.get("imgurl"))){
            GlideUtils.getInstance().showImageContext(mContext,new Urlsutil().getProductImageInfo+item.get("imgurl"),imageView);
        }
    }
}
