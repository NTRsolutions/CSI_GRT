package com.growatt.shinephone.bean;

import android.text.TextUtils;

/**
 * Created by dg on 2017/6/5.
 * 集成商设备：逆变器实体类
 */

public class OssJkDeviceInvBean {
    private String emptyStr = "-暂无-";
    private String seriaNum;
    private String userName;
    private String plant_id;
    private String plantName;
    private String datalog_sn;
    private String agentCode;
    private String agentName;
    private String lost;
    private String lastUpdateTime;
    private String alias;
    private String fw_version;
    private String inner_version;
    private String tcp_server_ip;
    private String etoday;
    private String etotal;
    private String pac;
    private String status = "4";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OssJkDeviceInvBean{" +
                "seriaNum='" + seriaNum + '\'' +
                ", userName='" + userName + '\'' +
                ", plant_id='" + plant_id + '\'' +
                ", plantName='" + plantName + '\'' +
                ", datalog_sn='" + datalog_sn + '\'' +
                ", agentCode='" + agentCode + '\'' +
                ", agentName='" + agentName + '\'' +
                ", lost='" + lost + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", alias='" + alias + '\'' +
                ", fw_version='" + fw_version + '\'' +
                ", inner_version='" + inner_version + '\'' +
                ", tcp_server_ip='" + tcp_server_ip + '\'' +
                ", etoday='" + etoday + '\'' +
                ", etotal='" + etotal + '\'' +
                ", pac='" + pac + '\'' +
                '}';
    }

    public String getAgentName() {
        if (TextUtils.isEmpty(agentName)){
            return emptyStr;
        }
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getSeriaNum() {
        return seriaNum;
    }

    public void setSeriaNum(String seriaNum) {
        this.seriaNum = seriaNum;
    }

    public String getUserName() {
        if (TextUtils.isEmpty(userName)){
            return emptyStr;
        }
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(String plant_id) {
        this.plant_id = plant_id;
    }

    public String getPlantName() {
        if (TextUtils.isEmpty(plantName)){
            return emptyStr;
        }
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getDatalog_sn() {
        return datalog_sn;
    }

    public void setDatalog_sn(String datalog_sn) {
        this.datalog_sn = datalog_sn;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getLost() {
        return lost;
    }

    public void setLost(String lost) {
        this.lost = lost;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getAlias() {
        if (TextUtils.isEmpty(alias)){
            return emptyStr;
        }
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFw_version() {
        return fw_version;
    }

    public void setFw_version(String fw_version) {
        this.fw_version = fw_version;
    }

    public String getInner_version() {
        return inner_version;
    }

    public void setInner_version(String inner_version) {
        this.inner_version = inner_version;
    }

    public String getTcp_server_ip() {
        return tcp_server_ip;
    }

    public void setTcp_server_ip(String tcp_server_ip) {
        this.tcp_server_ip = tcp_server_ip;
    }

    public String getEtoday() {
        return etoday;
    }

    public void setEtoday(String etoday) {
        this.etoday = etoday;
    }

    public String getEtotal() {
        return etotal;
    }

    public void setEtotal(String etotal) {
        this.etotal = etotal;
    }

    public String getPac() {
        return pac;
    }

    public void setPac(String pac) {
        this.pac = pac;
    }
}
