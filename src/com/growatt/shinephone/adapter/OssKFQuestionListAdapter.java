package com.growatt.shinephone.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.growatt.shinephone.bean.CustomQuestionListBean;

import java.util.List;

/**
 * Created by dg on 2017/8/25.
 */

public class OssKFQuestionListAdapter extends BaseQuickAdapter<CustomQuestionListBean,BaseViewHolder> {
    public OssKFQuestionListAdapter(@LayoutRes int layoutResId, @Nullable List<CustomQuestionListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomQuestionListBean item) {

    }
}
