package com.growatt.shinephone.bean;

/**
 * 集成商下面的设备数量信息:储能机实体
 * Created by dg on 2017/6/19.
 */

public class OssJKStorageDeviceNumBean {
    private int onlineNum;
    private int offlineNum;
    private int faultNum;
    private int totalNum;
    private int chargeNum;
    private int dischargeNum;
    private int nullNum;

    @Override
    public String toString() {
        return "OssJKStorageDeviceNumBean{" +
                "onlineNum=" + onlineNum +
                ", offlineNum=" + offlineNum +
                ", faultNum=" + faultNum +
                ", totalNum=" + totalNum +
                ", chargeNum=" + chargeNum +
                ", dischargeNum=" + dischargeNum +
                ", nullNum=" + nullNum +
                '}';
    }

    public int getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(int onlineNum) {
        this.onlineNum = onlineNum;
    }

    public int getOfflineNum() {
        return offlineNum;
    }

    public void setOfflineNum(int offlineNum) {
        this.offlineNum = offlineNum;
    }

    public int getFaultNum() {
        return faultNum;
    }

    public void setFaultNum(int faultNum) {
        this.faultNum = faultNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getChargeNum() {
        return chargeNum;
    }

    public void setChargeNum(int chargeNum) {
        this.chargeNum = chargeNum;
    }

    public int getDischargeNum() {
        return dischargeNum;
    }

    public void setDischargeNum(int dischargeNum) {
        this.dischargeNum = dischargeNum;
    }

    public int getNullNum() {
        return nullNum;
    }

    public void setNullNum(int nullNum) {
        this.nullNum = nullNum;
    }
}
