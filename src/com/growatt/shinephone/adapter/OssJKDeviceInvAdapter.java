package com.growatt.shinephone.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.OssJkDeviceInvBean;

import java.util.List;

/**
 * Created by dg on 2017/6/5.
 */

//public class OssJKDeviceInvAdapter extends com.zhy.adapter.recyclerview.CommonAdapter<OssJkDeviceInvBean> {
public class OssJKDeviceInvAdapter extends BaseQuickAdapter<OssJkDeviceInvBean,BaseViewHolder>{

    public OssJKDeviceInvAdapter(@LayoutRes int layoutResId, @Nullable List<OssJkDeviceInvBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, OssJkDeviceInvBean bean) {
        holder.setText(R.id.tvAliasTitle,bean.getAlias());
        holder.setText(R.id.tvUserName,bean.getUserName());
        holder.setText(R.id.tvPlant,bean.getPlantName());
        holder.setText(R.id.tvDatalogSn,bean.getDatalog_sn());
        String status = bean.getStatus();
        String statusText = "";
        int color = 0;
        if ("0".equals(status)){
            statusText = mContext.getString(R.string.all_Waiting);
            color = R.color.oss_status_wait;
        }else  if ("1".equals(status)){
            statusText = mContext.getString(R.string.all_Normal);
            color = R.color.oss_status_normal;
        }else  if ("3".equals(status)){
            statusText = mContext.getString(R.string.all_Fault);
            color = R.color.oss_status_fault;
        }else {
            statusText = mContext.getString(R.string.all_Lost);
            color = R.color.oss_status_flash;
        }
        holder.setText(R.id.tvLost,statusText);
        holder.setTextColor(R.id.tvLost,ContextCompat.getColor(mContext, color));
    }
}
