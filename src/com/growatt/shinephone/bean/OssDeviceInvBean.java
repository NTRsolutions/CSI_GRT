package com.growatt.shinephone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dg on 2017/6/12.
 * oss设备列表下逆变器实体类
 */

public class OssDeviceInvBean implements Parcelable{
    //逆变器序列号
    private String serialNum;
    //别名
    private String alias;
    //所属采集器序列号
    private String dataLogSn;
    //创建日期
    private String createDate;
    //额定功率
    private int nominalPower;
    //最后更新时间
    private String lastUpdateTimeText;
    //逆变器版本
    private String fwVersion;
    //内部版本
    private String innerVersion;
    //	lost	Boolean	是否掉线：false：在线，true：掉线
    private boolean lost;
    //逆变器TCP所属服务器IP地址
    private String tcpServerIp;
    //逆变器型号
    private String modelText;
    //日发电量
    private float eToday;
    //总发电量
    private float eTotal;
    //当前功率
    private float power;
    private int status;

    protected OssDeviceInvBean(Parcel in) {
        serialNum = in.readString();
        alias = in.readString();
        dataLogSn = in.readString();
        createDate = in.readString();
        nominalPower = in.readInt();
        lastUpdateTimeText = in.readString();
        fwVersion = in.readString();
        innerVersion = in.readString();
        lost = in.readByte() != 0;
        tcpServerIp = in.readString();
        modelText = in.readString();
        eToday = in.readFloat();
        eTotal = in.readFloat();
        power = in.readFloat();
        status = in.readInt();
    }

    public static final Creator<OssDeviceInvBean> CREATOR = new Creator<OssDeviceInvBean>() {
        @Override
        public OssDeviceInvBean createFromParcel(Parcel in) {
            return new OssDeviceInvBean(in);
        }

        @Override
        public OssDeviceInvBean[] newArray(int size) {
            return new OssDeviceInvBean[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OssDeviceInvBean() {
    }


    @Override
    public String toString() {
        return "OssDeviceInvBean{" +
                "serialNum='" + serialNum + '\'' +
                ", alias='" + alias + '\'' +
                ", dataLogSn='" + dataLogSn + '\'' +
                ", createDateText='" + createDate + '\'' +
                ", nominalPower=" + nominalPower +
                ", lastUpdateTimeText='" + lastUpdateTimeText + '\'' +
                ", fwVersion='" + fwVersion + '\'' +
                ", innerVersion='" + innerVersion + '\'' +
                ", lost=" + lost +
                ", tcpServerIp='" + tcpServerIp + '\'' +
                ", modelText='" + modelText + '\'' +
                ", eToday=" + eToday +
                ", eTotal=" + eTotal +
                ", power=" + power +
                '}';
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getNominalPower() {
        return nominalPower;
    }

    public void setNominalPower(int nominalPower) {
        this.nominalPower = nominalPower;
    }

    public String getLastUpdateTimeText() {
        return lastUpdateTimeText;
    }

    public void setLastUpdateTimeText(String lastUpdateTimeText) {
        this.lastUpdateTimeText = lastUpdateTimeText;
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

    public String getModelText() {
        return modelText;
    }

    public void setModelText(String modelText) {
        this.modelText = modelText;
    }

    public float geteToday() {
        return eToday;
    }

    public void seteToday(float eToday) {
        this.eToday = eToday;
    }

    public float geteTotal() {
        return eTotal;
    }

    public void seteTotal(float eTotal) {
        this.eTotal = eTotal;
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
        dest.writeString(createDate);
        dest.writeInt(nominalPower);
        dest.writeString(lastUpdateTimeText);
        dest.writeString(fwVersion);
        dest.writeString(innerVersion);
        dest.writeByte((byte) (lost ? 1 : 0));
        dest.writeString(tcpServerIp);
        dest.writeString(modelText);
        dest.writeFloat(eToday);
        dest.writeFloat(eTotal);
        dest.writeFloat(power);
        dest.writeInt(status);
    }
}
