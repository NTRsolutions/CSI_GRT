package com.growatt.shinephone.adapter;

import android.content.Context;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.OssPlantListBean;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by dg on 2017/5/16.
 * oss设备总搜索界面
 */

public class OssDeviceAdapter extends com.zhy.adapter.recyclerview.CommonAdapter<OssPlantListBean>{
    private Context mContext;
    public OssDeviceAdapter(Context context, int layoutId, List<OssPlantListBean> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }
    @Override
    protected void convert(ViewHolder holder, OssPlantListBean bean, int position) {
            holder.setText(R.id.tvName1,bean.getName1());
            holder.setText(R.id.tvName2,bean.getName2());
            holder.setText(R.id.tvValue1,bean.getValue1());
            holder.setText(R.id.tvValue2,bean.getValue2());
    }
}
