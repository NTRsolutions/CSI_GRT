package com.growatt.shinephone.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.OssDeviceListBean;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by dg on 2017/5/20.
 */

public class OssDeviceListAdapter extends com.zhy.adapter.recyclerview.CommonAdapter<OssDeviceListBean>{

    public OssDeviceListAdapter(Context context, int layoutId, List<OssDeviceListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, OssDeviceListBean ossDeviceListBean, int position) {
        holder.setText(R.id.tvSn,ossDeviceListBean.getTvSn());
        holder.setText(R.id.tvAlias,ossDeviceListBean.getTvAlias());
        holder.setText(R.id.tvDatalogTitle,ossDeviceListBean.getTvDatalogTitle());
        holder.setText(R.id.tvDatalogContent,ossDeviceListBean.getTvDatalogContent());
//        holder.setText(R.id.tvLost,ossDeviceListBean.getTvLost());
//        if (mContext.getString(R.string.all_online).equals(ossDeviceListBean.getTvLost())){
//            holder.setTextColor(R.id.tvLost, ContextCompat.getColor(mContext,R.color.oss_device_status_online));
//        }else {
//            holder.setTextColor(R.id.tvLost,ContextCompat.getColor(mContext,R.color.oss_device_status_offline));
//        }
        holder.setText(R.id.tvType,ossDeviceListBean.getTvType());
        int deviceType = ossDeviceListBean.getDeviceType();
        int status = ossDeviceListBean.getStatus();
        int color = R.color.oss_device_status_offline;
        String  statusText = ossDeviceListBean.getTvLost();
        switch (deviceType){
            case 0:
                switch (status){
                    case 0://断开
                        statusText = mContext.getString(R.string.all_Lost);
                        color = R.color.oss_device_status_offline;
                        break;
                    case 1://连接
                        statusText = mContext.getString(R.string.all_online);
                        color = R.color.oss_device_status_online;
                        break;
                }
                break;
            case 1:
                switch (status){
                    case 0://等待
                        statusText = mContext.getString(R.string.all_Waiting);
                        color = R.color.oss_status_wait;
                        break;
                    case 1://正常
                        statusText = mContext.getString(R.string.all_Normal);
                        color = R.color.oss_status_normal;
                        break;
                    case 3://故障
                        statusText = mContext.getString(R.string.all_Fault);
                        color = R.color.oss_status_fault;
                        break;
                    default://断开
                        statusText = mContext.getString(R.string.all_Lost);
                        color = R.color.oss_status_flash;
                        break;
                }
                break;
            case 2:
                switch (status){
                    case 0://闲置
                        statusText = mContext.getString(R.string.all_Standby);
                        color = R.color.oss_status_oprating;
                        break;
                    case 1://充电
                        statusText = mContext.getString(R.string.all_Charge);
                        color = R.color.oss_status_charge;
                        break;
                    case 2://放电
                        statusText = mContext.getString(R.string.all_Discharge);
                        color = R.color.oss_status_discharge;
                        break;
                    case 3://故障
                        statusText = mContext.getString(R.string.all_Fault);
                        color = R.color.oss_status_fault;
                        break;
                    case 4://flash等待
                        statusText = mContext.getString(R.string.all_Flash);
                        color = R.color.oss_status_flash;
                        break;
                    default://断开
                        statusText = mContext.getString(R.string.all_Flash);
                        color = R.color.oss_status_flash;
                        break;
                }
                break;
            default:
                break;
        }
        holder.setTextColor(R.id.tvLost, ContextCompat.getColor(mContext,color));
        holder.setText(R.id.tvLost,statusText);
    }
}
