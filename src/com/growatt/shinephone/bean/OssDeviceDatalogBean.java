package com.growatt.shinephone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dg on 2017/6/12.
 * oss设备列表下采集器实体类
 */

public class OssDeviceDatalogBean implements Parcelable{
    //采集器序列号
    private String serialNum;
    //采集器别名
    private String alias;
    //创建日期
    private String createDate;
    //最后更新时间
    private String lastUpdateTimeText;
    //是否掉线，false：在线，true：掉线
    private boolean lost;
    //	tcpServerIp	String	采集器TCP服务器IP地址
    private String tcpServerIp;
    //	deviceType	String	采集器类型如：ShineWIFI
    private String deviceType;
    //采集器类型对应编号：如：ShineWIFI对应的是5：  "0" --> "Shine WebBox";"1" --> "Shine Pano";
//			"2" --> "ShineWifiBox";
//			"3" --> "ShineNet";
//			"5" --> "ShineLan";
//			"6" --> "ShineWIFI";
//			"7" --> "Shine3G";
//			"8" --> "ShineGPRS";
//			"9" --> "ShineLanBox";
//			"10" --> "ShineRFStick";

    private int deviceTypeIndicate;
    //采集器所属用户名
    private String userName;
    //连接IP和端口
    private String clientUrl;
    //所属电站ID
    private int plantId;
    private ParamBean paramBean;

    protected OssDeviceDatalogBean(Parcel in) {
        serialNum = in.readString();
        alias = in.readString();
        createDate = in.readString();
        lastUpdateTimeText = in.readString();
        lost = in.readByte() != 0;
        tcpServerIp = in.readString();
        deviceType = in.readString();
        deviceTypeIndicate = in.readInt();
        userName = in.readString();
        clientUrl = in.readString();
        plantId = in.readInt();
        paramBean = in.readParcelable(ParamBean.class.getClassLoader());
    }

    public static final Creator<OssDeviceDatalogBean> CREATOR = new Creator<OssDeviceDatalogBean>() {
        @Override
        public OssDeviceDatalogBean createFromParcel(Parcel in) {
            return new OssDeviceDatalogBean(in);
        }

        @Override
        public OssDeviceDatalogBean[] newArray(int size) {
            return new OssDeviceDatalogBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serialNum);
        dest.writeString(alias);
        dest.writeString(createDate);
        dest.writeString(lastUpdateTimeText);
        dest.writeByte((byte) (lost ? 1 : 0));
        dest.writeString(tcpServerIp);
        dest.writeString(deviceType);
        dest.writeInt(deviceTypeIndicate);
        dest.writeString(userName);
        dest.writeString(clientUrl);
        dest.writeInt(plantId);
        dest.writeParcelable(paramBean, flags);
    }

    public static class ParamBean implements Parcelable{
        private String firmwareVersionBuild;
        private String serverUrl;

        protected ParamBean(Parcel in) {
            firmwareVersionBuild = in.readString();
            serverUrl = in.readString();
        }

        public static final Creator<ParamBean> CREATOR = new Creator<ParamBean>() {
            @Override
            public ParamBean createFromParcel(Parcel in) {
                return new ParamBean(in);
            }

            @Override
            public ParamBean[] newArray(int size) {
                return new ParamBean[size];
            }
        };

        public String getFirmwareVersionBuild() {
            return firmwareVersionBuild;
        }

        public void setFirmwareVersionBuild(String firmwareVersionBuild) {
            this.firmwareVersionBuild = firmwareVersionBuild;
        }

        public String getServerUrl() {
            return serverUrl;
        }

        public void setServerUrl(String serverUrl) {
            this.serverUrl = serverUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(firmwareVersionBuild);
            dest.writeString(serverUrl);
        }
    }
    public OssDeviceDatalogBean() {

    }

    public ParamBean getParamBean() {
        return paramBean;
    }

    public void setParamBean(ParamBean paramBean) {
        this.paramBean = paramBean;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateTimeText() {
        return lastUpdateTimeText;
    }

    public void setLastUpdateTimeText(String lastUpdateTimeText) {
        this.lastUpdateTimeText = lastUpdateTimeText;
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

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getDeviceTypeIndicate() {
        return deviceTypeIndicate;
    }

    public void setDeviceTypeIndicate(int deviceTypeIndicate) {
        this.deviceTypeIndicate = deviceTypeIndicate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientUrl() {
        return clientUrl;
    }

    public void setClientUrl(String clientUrl) {
        this.clientUrl = clientUrl;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    @Override
    public String toString() {
        return "OssDeviceDatalogBean{" +
                "serialNum='" + serialNum + '\'' +
                ", alias='" + alias + '\'' +
                ", createDate='" + createDate + '\'' +
                ", lastUpdateTimeText='" + lastUpdateTimeText + '\'' +
                ", lost=" + lost +
                ", tcpServerIp='" + tcpServerIp + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", deviceTypeIndicate=" + deviceTypeIndicate +
                ", userName='" + userName + '\'' +
                ", clientUrl='" + clientUrl + '\'' +
                ", plantId=" + plantId +
                ", paramBean=" + paramBean +
                '}';
    }
}
