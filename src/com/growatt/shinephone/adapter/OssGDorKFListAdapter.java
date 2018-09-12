package com.growatt.shinephone.adapter;

import android.content.Context;

import com.growatt.shinephone.bean.OssGDQuestionListBean;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by dg on 2017/6/29.
 */

public class OssGDorKFListAdapter extends MultiItemTypeAdapter<OssGDQuestionListBean> {
    public OssGDorKFListAdapter(Context context, List<OssGDQuestionListBean> datas) {
        super(context, datas);
        addItemViewDelegate(new OssKFListAdapter(context));
        addItemViewDelegate(new OssGDListAdapter(context));
    }
}
