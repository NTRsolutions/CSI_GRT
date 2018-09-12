package com.growatt.shinephone.bean.v2;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_INV;
import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_MAX;
import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_MIX;
import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_STORAGE_SPF2K;
import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_STORAGE_SPF3K;
import static com.growatt.shinephone.adapter.v2.Fragment1V2Item.DEVICE_STORAGE_SPF5K;

/**
 * Created：2017/11/30 on 9:16
 * Author:gaideng on dg
 * Description:fragment1设备列表item
 */

public class Fragment1ListBean implements Parcelable, MultiItemEntity {
 /*共有数据*/
    private int deviceStatus = -1;
    //通讯是否丢失（设备的状态需要先判断这个参数值，为true表示是离线的，为false再判断deviceStatus的状态）
    private boolean lost = true;
    private String datalogSn;//采集器序列号
    //设备类型包括inverter、storage、mix...
    private String deviceType;
    private String deviceAilas;//设备别名
    private String deviceSn;//设备序列号
    private String energy;//总发电量
/*逆变器*/
    //逆变器型号：0 逆变器   1  max
    private String invType = "0";
    private String power;//当前功率
    private String eToday;//今日发电
/*储能机*/
    //储能机类型：（0:SP2000,1:SP3000,2:SPF5000）
    private int storageType;
    private String capacity;//电池容量百分比99%
    private String pCharge;//当天功率
    private String eChargeToday;//当天发电
    //spf5000类型
    private String activePower;
    private String apparentPower;
    /*Mix:与储能机数据一致*/
//    private String capacity;//电池容量百分比:没有%
//    private String pCharge;//当天功率
//    private String eChargeToday;//当天发电
    //置顶时间id
    private String timeId = "0";
    //用户id
    private String userId;
    //电站id
    private String plantId;
    public Fragment1ListBean() {
    }

    protected Fragment1ListBean(Parcel in) {
        deviceStatus = in.readInt();
        lost = in.readByte() != 0;
        datalogSn = in.readString();
        deviceType = in.readString();
        deviceAilas = in.readString();
        deviceSn = in.readString();
        energy = in.readString();
        invType = in.readString();
        power = in.readString();
        eToday = in.readString();
        storageType = in.readInt();
        capacity = in.readString();
        pCharge = in.readString();
        eChargeToday = in.readString();
        activePower = in.readString();
        apparentPower = in.readString();
        timeId = in.readString();
        userId = in.readString();
        plantId = in.readString();
    }

    public static final Creator<Fragment1ListBean> CREATOR = new Creator<Fragment1ListBean>() {
        @Override
        public Fragment1ListBean createFromParcel(Parcel in) {
            return new Fragment1ListBean(in);
        }

        @Override
        public Fragment1ListBean[] newArray(int size) {
            return new Fragment1ListBean[size];
        }
    };

    public String getActivePower() {
        return activePower;
    }

    public void setActivePower(String activePower) {
        this.activePower = activePower;
    }

    public String getApparentPower() {
        return apparentPower;
    }

    public void setApparentPower(String apparentPower) {
        this.apparentPower = apparentPower;
    }

    public int getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(int deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public String getDatalogSn() {
        return datalogSn;
    }

    public void setDatalogSn(String datalogSn) {
        this.datalogSn = datalogSn;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceAilas() {
        return deviceAilas;
    }

    public void setDeviceAilas(String deviceAilas) {
        this.deviceAilas = deviceAilas;
    }

    public String getDeviceSn() {
        return deviceSn;
    }

    public void setDeviceSn(String deviceSn) {
        this.deviceSn = deviceSn;
    }

    public String getEnergy() {
        return energy;
    }

    public void setEnergy(String energy) {
        this.energy = energy;
    }

    public String getInvType() {
        return invType;
    }

    public void setInvType(String invType) {
        this.invType = invType;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String geteToday() {
        return eToday;
    }

    public void seteToday(String eToday) {
        this.eToday = eToday;
    }

    public int getStorageType() {
        return storageType;
    }

    public void setStorageType(int storageType) {
        this.storageType = storageType;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getpCharge() {
        return pCharge;
    }

    public void setpCharge(String pCharge) {
        this.pCharge = pCharge;
    }

    public String geteChargeToday() {
        return eChargeToday;
    }

    public void seteChargeToday(String eChargeToday) {
        this.eChargeToday = eChargeToday;
    }

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    @Override
    public int getItemType() {
        int type = 0;
        if ("inverter".equals(deviceType) && "1".equals(invType)){
            type = DEVICE_MAX;
        }
        else if ("inverter".equals(deviceType)){
            type = DEVICE_INV;
        }
        else if ("storage".equals(deviceType) && storageType == 1){
            type = DEVICE_STORAGE_SPF3K;
        }
        else if ("storage".equals(deviceType) && storageType == 2){
            type = DEVICE_STORAGE_SPF5K;
        }
        else if ("storage".equals(deviceType)){
            type = DEVICE_STORAGE_SPF2K;
        }
        else if ("mix".equals(deviceType)){
            type = DEVICE_MIX;
        }

        return type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(deviceStatus);
        dest.writeByte((byte) (lost ? 1 : 0));
        dest.writeString(datalogSn);
        dest.writeString(deviceType);
        dest.writeString(deviceAilas);
        dest.writeString(deviceSn);
        dest.writeString(energy);
        dest.writeString(invType);
        dest.writeString(power);
        dest.writeString(eToday);
        dest.writeInt(storageType);
        dest.writeString(capacity);
        dest.writeString(pCharge);
        dest.writeString(eChargeToday);
        dest.writeString(activePower);
        dest.writeString(apparentPower);
        dest.writeString(timeId);
        dest.writeString(userId);
        dest.writeString(plantId);
    }
}
