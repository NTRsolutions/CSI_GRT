package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/5/20.
 */

public class  OssDeviceListBean {
    //设备序列号
    private String tvSn;
    //设备别名
    private String tvAlias;
    //设备状态
    private String tvLost;
    //采集器标题
    private String tvDatalogTitle;
    //采集器内容
    private String tvDatalogContent;
    //设备类型:具体某种设备下的小类型
    private String tvType;
    //设备类型：0：采集器；1：逆变器；2：储能机
    private int deviceType;
    //设备状态：采集器（0：断开，1：在线）；1：逆变器；2：储能机
    private int status;

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OssDeviceListBean() {
    }

    public OssDeviceListBean(String tvSn, String tvAlias, String tvLost, String tvDatalogTitle, String tvDatalogContent, String tvType) {
        this.tvSn = tvSn;
        this.tvAlias = tvAlias;
        this.tvLost = tvLost;
        this.tvDatalogTitle = tvDatalogTitle;
        this.tvDatalogContent = tvDatalogContent;
        this.tvType = tvType;
    }

    public void setTvSn(String tvSn) {
        this.tvSn = tvSn;
    }

    public void setTvAlias(String tvAlias) {
        this.tvAlias = tvAlias;
    }

    public void setTvLost(String tvLost) {
        this.tvLost = tvLost;
    }

    public void setTvDatalogTitle(String tvDatalogTitle) {
        this.tvDatalogTitle = tvDatalogTitle;
    }

    public void setTvDatalogContent(String tvDatalogContent) {
        this.tvDatalogContent = tvDatalogContent;
    }

    public void setTvType(String tvType) {
        this.tvType = tvType;
    }

    public String getTvSn() {
        return tvSn;
    }

    public String getTvAlias() {
        return tvAlias;
    }

    public String getTvLost() {
        return tvLost;
    }

    public String getTvDatalogTitle() {
        return tvDatalogTitle;
    }

    public String getTvDatalogContent() {
        return tvDatalogContent;
    }


    public String getTvType() {
        return tvType;
    }

    @Override
    public String toString() {
        return "OssDeviceListBean{" +
                "tvSn='" + tvSn + '\'' +
                ", tvAlias='" + tvAlias + '\'' +
                ", tvLost='" + tvLost + '\'' +
                ", tvDatalogTitle='" + tvDatalogTitle + '\'' +
                ", tvDatalogContent='" + tvDatalogContent + '\'' +
                ", tvType='" + tvType + '\'' +
                '}';
    }
}
