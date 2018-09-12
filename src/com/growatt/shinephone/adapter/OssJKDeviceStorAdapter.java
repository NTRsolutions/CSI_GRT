package com.growatt.shinephone.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.OssJkDeviceStorBean;

import java.util.List;

/**
 * Created by dg on 2017/6/5.
 */

//public class OssJKDeviceStorAdapter extends com.zhy.adapter.recyclerview.CommonAdapter<OssJkDeviceStorBean> {
public class OssJKDeviceStorAdapter extends BaseQuickAdapter<OssJkDeviceStorBean,BaseViewHolder> {


    public OssJKDeviceStorAdapter(@LayoutRes int layoutResId, @Nullable List<OssJkDeviceStorBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, OssJkDeviceStorBean bean) {
        holder.setText(R.id.tvAliasTitle,bean.getAlias());
        holder.setText(R.id.tvUserName,bean.getUserName());
        holder.setText(R.id.tvPlant,bean.getPlantName());
        holder.setText(R.id.tvDatalogSn,bean.getDatalog_sn());
        String status = bean.getStatus();
        String statusText = "";
        int color = 0;
        if ("0".equals(status)){
            statusText = mContext.getString(R.string.all_Standby);
            color = R.color.oss_status_oprating;
        }else if ("1".equals(status)){
            statusText = mContext.getString(R.string.all_Charge);
            color = R.color.oss_status_charge;
        }else if ("2".equals(status)){
            statusText = mContext.getString(R.string.all_Discharge);
            color = R.color.oss_status_discharge;
        }else if ("3".equals(status)){
            statusText = mContext.getString(R.string.all_Fault);
            color = R.color.oss_status_fault;
        }else {
            statusText = mContext.getString(R.string.all_Flash);
            color = R.color.oss_status_flash;
        }
        holder.setText(R.id.tvLost,statusText);
        holder.setTextColor(R.id.tvLost, ContextCompat.getColor(mContext, color));
    }
}
