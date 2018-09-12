package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/6/19.
 */

public class OssJKInvDeviceNumBean {
    private int onlineNum;
    private int offlineNum;
    private int faultNum;
    private int waitNum;
    private int nullNum;
    private int totalNum;

    @Override
    public String toString() {
        return "OssJKInvDeviceNumBean{" +
                "onlineNum=" + onlineNum +
                ", offlineNum=" + offlineNum +
                ", faultNum=" + faultNum +
                ", waitNum=" + waitNum +
                ", nullNum=" + nullNum +
                ", totalNum=" + totalNum +
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

    public int getWaitNum() {
        return waitNum;
    }

    public void setWaitNum(int waitNum) {
        this.waitNum = waitNum;
    }

    public int getNullNum() {
        return nullNum;
    }

    public void setNullNum(int nullNum) {
        this.nullNum = nullNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
