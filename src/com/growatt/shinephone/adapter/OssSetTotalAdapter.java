package com.growatt.shinephone.adapter;

import android.content.Context;

import com.growatt.shinephone.bean.OssDeviceDeticalBean;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by dg on 2017/6/14.
 */

public class OssSetTotalAdapter extends MultiItemTypeAdapter<OssDeviceDeticalBean>{

    public OssSetTotalAdapter(Context context, List<OssDeviceDeticalBean> datas) {
        super(context, datas);
        addItemViewDelegate(new OssSetTotalItem1Adapter());
        addItemViewDelegate(new OssSetTotalItem2Adapter());
    }
}
