package com.growatt.shinephone.adapter.v2;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.v2.Fragment1ListBean;
import com.growatt.shinephone.util.GlideUtils;

import java.util.List;

import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_INV;
import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_JLINV;
import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_MAX;
import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_MIX;
import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_STORAGE_SPF2K;
import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_STORAGE_SPF3K;
import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_STORAGE_SPF5K;

/**
 * Created：2017/11/30 on 9:15
 * Author:gaideng on dg
 * Description:
 */

public class Fragment1V2Adapter extends BaseMultiItemQuickAdapter<Fragment1ListBean,BaseViewHolder> {
    public Fragment1V2Adapter(List<Fragment1ListBean> data) {
        super(data);
        //共用逆变器布局
        addItemType(DEVICE_INV, R.layout.item_frg1_device_inverter);
        addItemType(DEVICE_MAX, R.layout.item_frg1_device_inverter);
        addItemType(DEVICE_JLINV, R.layout.item_frg1_device_inverter);
        //共用储能机布局
        addItemType(DEVICE_STORAGE_SPF2K, R.layout.item_frg1_device_storage);
        addItemType(DEVICE_STORAGE_SPF3K, R.layout.item_frg1_device_storage);
        addItemType(DEVICE_STORAGE_SPF5K, R.layout.item_frg1_device_storage_spf5k);
        addItemType(DEVICE_MIX, R.layout.item_frg1_device_storage);
    }

    @Override
    protected void convert(BaseViewHolder helper, Fragment1ListBean item) {
        int deviceStatus = item.getDeviceStatus();
        //状态颜色id
        int statusColorId = R.color.inverter_lost;
        //状态文本id
        int  statusText = R.string.all_Lost;
        //sn颜色id,默认为蓝色；只有断开为灰色
        int snColorId = R.color.inverter_wait;
        //图像id
        int iconId = R.drawable.frg1_inverter;
//设备类型：inverter;storage;mix;max...
        switch (item.getItemType()){
            case DEVICE_INV:
            case DEVICE_MAX:
            case DEVICE_JLINV:
                //设置功率和日电量
                helper.setText(R.id.tvParam1,item.getPower() + "W");
                helper.setText(R.id.tvParam2,item.geteToday() + "kWh");
                iconId = R.drawable.frg1_inverter;
                //判断状态字体颜色：1-等待；2-正常；4-故障；5-断开
                if (item.isLost() == true){
                    statusColorId = R.color.inverter_lost;
                    statusText = R.string.all_Lost;
                    snColorId = R.color.inverter_lost;
                }else {
                    switch (deviceStatus) {
                        case 1:
                            statusColorId = R.color.inverter_wait;
                            statusText = R.string.all_Waiting;
                            break;
                        case 2:
                            statusColorId = R.color.oss_status_normal;
                            statusText = R.string.all_Normal;
                            break;
                        case 4:
                            statusColorId = R.color.oss_status_fault;
                            statusText = R.string.all_Fault;
                            break;
                        case 5:
                            statusColorId = R.color.inverter_lost;
                            statusText = R.string.all_Lost;
                            break;
                        default:
                            statusColorId = R.color.inverter_lost;
                            statusText = R.string.all_Lost;
                            snColorId = R.color.inverter_lost;
                            break;
                    }
                }
                break;
            case DEVICE_STORAGE_SPF2K:
            case DEVICE_STORAGE_SPF3K:
                helper.setText(R.id.tvParam1,item.getpCharge() + "W");
                helper.setText(R.id.tvParam2,item.getCapacity());
                iconId = R.drawable.frg1_storage;
                //0-闲置；1-充电；2-放电；3-故障；4-断开；-1-断开；其他-正常
                if (item.isLost() == true){
                    statusText = R.string.all_Flash;
                    statusColorId = R.color.inverter_lost;
                    snColorId = R.color.inverter_lost;
                }else {
                    switch (deviceStatus) {
                        case 0:
                            statusText = R.string.all_Standby;
                            statusColorId = R.color.oss_status_oprating;
                            break;
                        case 1:
                            statusText = R.string.all_Charge;
                            statusColorId = R.color.oss_status_charge;
                            break;
                        case 2:
                            statusText = R.string.all_Discharge;
                            statusColorId = R.color.oss_status_discharge;
                            break;
                        case 3:
                            statusText = R.string.all_Fault;
                            statusColorId = R.color.oss_status_fault;
                            break;
                        case 4:
                        case -1:
                            statusText = R.string.all_Flash;
                            statusColorId = R.color.inverter_lost;
                            snColorId = R.color.inverter_lost;
                            break;
                        default:
                            statusText = R.string.m186在线;
                            statusColorId = R.color.oss_status_normal;
                            break;
                    }
                }
                break;
            case DEVICE_STORAGE_SPF5K:
//                helper.setText(R.id.tvParam1,item.getpCharge() + "W");
//                helper.setText(R.id.tvParam1, String.format("%s/%sW",item.getActivePower(),item.getApparentPower()));
                helper.setText(R.id.tvParam2,item.getCapacity());
                iconId = R.drawable.frg1_storage;
                //0-闲置；1-充电；2-放电；3-故障；4-断开；-1-断开；其他-正常
                if (item.isLost() == true){
                    statusText = R.string.all_Flash;
                    statusColorId = R.color.inverter_lost;
                    snColorId = R.color.inverter_lost;
                }else {
                    switch (deviceStatus) {
                        case 0:
                            statusText = R.string.all_Standby;
                            statusColorId = R.color.oss_status_oprating;
                            break;
                        case 1:
                            statusText = R.string.all_Charge;
                            statusColorId = R.color.oss_status_charge;
                            break;
                        case 2:
                            statusText = R.string.all_Discharge;
                            statusColorId = R.color.oss_status_discharge;
                            break;
                        case 3:
                            statusText = R.string.all_Fault;
                            statusColorId = R.color.oss_status_fault;
                            break;
                        case 4:
                        case -1:
                            statusText = R.string.all_Flash;
                            statusColorId = R.color.inverter_lost;
                            snColorId = R.color.inverter_lost;
                            break;
                        default:
                            statusText = R.string.m186在线;
                            statusColorId = R.color.oss_status_normal;
                            break;
                    }
                }
                break;
            case DEVICE_MIX:
                helper.setText(R.id.tvParam1,item.getpCharge() + "W");
                helper.setText(R.id.tvParam2,item.getCapacity());
                iconId = R.drawable.mix_icon;
                //0-等待；1-自检；2-保留；3-故障；4-Flash;5~8-正常；-1、9-断开
                if (item.isLost() == true){
                    statusText = R.string.all_Lost;
                    statusColorId = R.color.inverter_lost;
                    snColorId = R.color.inverter_lost;
                }else {
                    switch (deviceStatus) {
                        case 0:
                            statusText = R.string.m201等待模式;
                            statusColorId = R.color.inverter_wait;
                            break;
                        case 1:
                            statusText = R.string.all_Standby;
                            statusColorId = R.color.oss_status_oprating;
                            break;
                        case 2:
                            statusText = R.string.m203保留模式;
                            statusColorId = R.color.oss_status_normal;
                            break;
                        case 3:
                            statusText = R.string.all_Fault;
                            statusColorId = R.color.oss_status_fault;
                            break;
                        case 4:
                            statusText = R.string.m205Flash模式;
                            statusColorId = R.color.oss_status_discharge;
                            break;
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            statusText = R.string.all_Normal;
                            statusColorId = R.color.oss_status_normal;
                            break;
                        case 9:
                            statusText = R.string.all_Lost;
                            statusColorId = R.color.inverter_lost;
                            break;
                        default:
                            statusText = R.string.all_Lost;
                            statusColorId = R.color.inverter_lost;
                            snColorId = R.color.inverter_lost;
                            break;
                    }
                }
                break;
        }
        //别名控件
        TextView tvSn = helper.getView(R.id.tvDeviceSn);
        tvSn.setText(item.getDeviceAilas());
        tvSn.setTextColor(ContextCompat.getColor(mContext,snColorId));
        //状态控件
        TextView tvStatus = helper.getView(R.id.tvDeviceStatus);
        tvStatus.setText(mContext.getString(statusText));
        tvStatus.setTextColor(ContextCompat.getColor(mContext,statusColorId));
        //设置图片
        ImageView ivIcon = helper.getView(R.id.ivIcon);
        GlideUtils.getInstance().showImageContext(mContext,iconId,iconId,iconId,ivIcon);
    }
}
