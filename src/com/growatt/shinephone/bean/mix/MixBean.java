package com.growatt.shinephone.bean.mix;

/**
 * Created：2017/11/21 on 17:03
 * Author:gaideng on dg
 * Description:Mix点击后界面，基础信息
 */

public class MixBean {
    private String serialNum; // 序列号
    private String portName; // 通讯口信息 通讯口类型和地址
    private String dataLogSn; // DataLog serial number
    private int groupId = -1; // Inverter分组
    private String alias; // 别名
    private String location; // 位置
    private int addr = 0; // Inverter地址
    private String fwVersion; // 固件版本
    private int model;// 模式
    private String modelText;// 模式
    private String innerVersion;// 内部版本号
    private boolean lost = true; // 是否通讯丢失
    private int status = -1; // Mix Status 0:等待模式 1:自检模式 3:故障模式  4:升级中  5、6、7、8:正常模式
    private String tcpServerIp; // TCP服务器Ip地址
    private String sysTime;    //系统时间

    public String getModelText() {
        return modelText;
    }

    public void setModelText(String modelText) {
        this.modelText = modelText;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public String getDataLogSn() {
        return dataLogSn;
    }

    public void setDataLogSn(String dataLogSn) {
        this.dataLogSn = dataLogSn;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public String getFwVersion() {
        return fwVersion;
    }

    public void setFwVersion(String fwVersion) {
        this.fwVersion = fwVersion;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTcpServerIp() {
        return tcpServerIp;
    }

    public void setTcpServerIp(String tcpServerIp) {
        this.tcpServerIp = tcpServerIp;
    }


    public String getSysTime() {
        return sysTime;
    }

    public void setSysTime(String sysTime) {
        this.sysTime = sysTime;
    }
}