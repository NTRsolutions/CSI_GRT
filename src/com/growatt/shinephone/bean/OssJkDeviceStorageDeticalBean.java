package com.growatt.shinephone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dg on 2017/6/20.
 */

public class OssJkDeviceStorageDeticalBean implements Parcelable{
    //储能机序列号
    private String serialNum;
    //别名
    private String alias;
    //所属采集器序列号
    private String dataLogSn;
    //储能机版本
    private String fwVersion;
    //内部版本
    private String innerVersion;
    //是否掉线：false：在线，true：掉线
    private boolean lost;
    //储能机TCP所属服务器IP地址
    private String tcpServerIp;
    //储能机最后更新时间
    private String lastUpdateTimeText;
    //储能机类型：0-SP2000,1-SP3000,2:-SPF5000
    private int deviceType;
    //储能机型号
    private String modelText;
    //充电功率
    private double pCharge;
    //放电功率
    private double pDischarge;
    //0: Operating, 1: Charge, 2: Discharge, 3: Fault, 4: Flash
    private int status;

    public OssJkDeviceStorageDeticalBean() {
    }

    protected OssJkDeviceStorageDeticalBean(Parcel in) {
        serialNum = in.readString();
        alias = in.readString();
        dataLogSn = in.readString();
        fwVersion = in.readString();
        innerVersion = in.readString();
        lost = in.readByte() != 0;
        tcpServerIp = in.readString();
        lastUpdateTimeText = in.readString();
        deviceType = in.readInt();
        modelText = in.readString();
        pCharge = in.readDouble();
        pDischarge = in.readDouble();
        status = in.readInt();
    }

    public static final Creator<OssJkDeviceStorageDeticalBean> CREATOR = new Creator<OssJkDeviceStorageDeticalBean>() {
        @Override
        public OssJkDeviceStorageDeticalBean createFromParcel(Parcel in) {
            return new OssJkDeviceStorageDeticalBean(in);
        }

        @Override
        public OssJkDeviceStorageDeticalBean[] newArray(int size) {
            return new OssJkDeviceStorageDeticalBean[size];
        }
    };

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDataLogSn() {
        return dataLogSn;
    }

    public void setDataLogSn(String dataLogSn) {
        this.dataLogSn = dataLogSn;
    }

    public String getFwVersion() {
        return fwVersion;
    }

    public void setFwVersion(String fwVersion) {
        this.fwVersion = fwVersion;
    }

    public String getInnerVersion() {
        return innerVersion;
    }

    public void setInnerVersion(String innerVersion) {
        this.innerVersion = innerVersion;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public String getTcpServerIp() {
        return tcpServerIp;
    }

    public void setTcpServerIp(String tcpServerIp) {
        this.tcpServerIp = tcpServerIp;
    }

    public String getLastUpdateTimeText() {
        return lastUpdateTimeText;
    }

    public void setLastUpdateTimeText(String lastUpdateTimeText) {
        this.lastUpdateTimeText = lastUpdateTimeText;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getModelText() {
        return modelText;
    }

    public void setModelText(String modelText) {
        this.modelText = modelText;
    }

    public double getpCharge() {
        return pCharge;
    }

    public void setpCharge(double pCharge) {
        this.pCharge = pCharge;
    }

    public double getpDischarge() {
        return pDischarge;
    }

    public void setpDischarge(double pDischarge) {
        this.pDischarge = pDischarge;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serialNum);
        dest.writeString(alias);
        dest.writeString(dataLogSn);
        dest.writeString(fwVersion);
        dest.writeString(innerVersion);
        dest.writeByte((byte) (lost ? 1 : 0));
        dest.writeString(tcpServerIp);
        dest.writeString(lastUpdateTimeText);
        dest.writeInt(deviceType);
        dest.writeString(modelText);
        dest.writeDouble(pCharge);
        dest.writeDouble(pDischarge);
        dest.writeInt(status);
    }
}
