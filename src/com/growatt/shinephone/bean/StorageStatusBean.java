package com.growatt.shinephone.bean;

import android.text.TextUtils;

/**
 * Created by dg on 2017/3/22.
 */

public class StorageStatusBean {
    //储能机设备类型，储能机类型(0：SP2000,1：SP3000,2：SPF5000)
    String deviceType;
    //储能机电池容量百分百
    String capacity;
    //面板输入功率2
    String ppv2;
    //面板输入功率1
    String ppv1;
    //状态
    String status;
    private String invStatus;
    private String statusText;
/*spf2k/3k*/
    //电网侧功率
    String pacToGrid;
    //用户侧功率
    String pacToUser;
    //总充电功率
    String pCharge;
    //充电功率1
    String pCharge1;
    //充电功率2
    String pCharge2;
    //用户耗电
    String userLoad;
    //总放电功率
    String pDisCharge;
    //放电功率1
    String pDisCharge1;
    //放电功率2
    String pDisCharge2;
    //pac功率
    String pacCharge;
/*spf5k
* */
    private String panelPower;
    private String gridPower;
    private String batPower;
    private String loadPower;
    private String vBat;

    private String vPv1;
    private String vPv2;
    private String iTotal;
    private String iPv1;
    private String iPv2;
    private String vAcInput;
    private String fAcInput;
    private String vAcOutput;
    private String fAcOutput;
    private String loadPrecent;
    private String apparentPower;

    public String getApparentPower() {
        return apparentPower;
    }

    public void setApparentPower(String apparentPower) {
        this.apparentPower = apparentPower;
    }

    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public String getPanelPower() {
        return panelPower;
    }

    public void setPanelPower(String panelPower) {
        this.panelPower = panelPower;
    }

    public String getGridPower() {
        return gridPower;
    }

    public void setGridPower(String gridPower) {
        this.gridPower = gridPower;
    }

    public String getBatPower() {
        return batPower;
    }

    public void setBatPower(String batPower) {
        this.batPower = batPower;
    }

    public String getLoadPower() {
        return loadPower;
    }

    public void setLoadPower(String loadPower) {
        this.loadPower = loadPower;
    }

    public String getvBat() {
        return vBat;
    }

    public void setvBat(String vBat) {
        this.vBat = vBat;
    }

    public String getvPv1() {
        return vPv1;
    }

    public void setvPv1(String vPv1) {
        this.vPv1 = vPv1;
    }

    public String getvPv2() {
        return vPv2;
    }

    public void setvPv2(String vPv2) {
        this.vPv2 = vPv2;
    }

    public String getiTotal() {
        return iTotal;
    }

    public void setiTotal(String iTotal) {
        this.iTotal = iTotal;
    }

    public String getiPv1() {
        return iPv1;
    }

    public void setiPv1(String iPv1) {
        this.iPv1 = iPv1;
    }

    public String getiPv2() {
        return iPv2;
    }

    public void setiPv2(String iPv2) {
        this.iPv2 = iPv2;
    }

    public String getvAcInput() {
        return vAcInput;
    }

    public void setvAcInput(String vAcInput) {
        this.vAcInput = vAcInput;
    }

    public String getfAcInput() {
        return fAcInput;
    }

    public void setfAcInput(String fAcInput) {
        this.fAcInput = fAcInput;
    }

    public String getvAcOutput() {
        return vAcOutput;
    }

    public void setvAcOutput(String vAcOutput) {
        this.vAcOutput = vAcOutput;
    }

    public String getfAcOutput() {
        return fAcOutput;
    }

    public void setfAcOutput(String fAcOutput) {
        this.fAcOutput = fAcOutput;
    }

    public String getLoadPrecent() {
        return loadPrecent;
    }

    public void setLoadPrecent(String loadPrecent) {
        this.loadPrecent = loadPrecent;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getpCharge1() {
        return pCharge1;
    }

    public void setpCharge1(String pCharge1) {
        this.pCharge1 = pCharge1;
    }

    public String getpCharge2() {
        return pCharge2;
    }

    public void setpCharge2(String pCharge2) {
        this.pCharge2 = pCharge2;
    }

    public String getpDisCharge1() {
        return pDisCharge1;
    }

    public void setpDisCharge1(String pDisCharge1) {
        this.pDisCharge1 = pDisCharge1;
    }

    public String getpDisCharge2() {
        return pDisCharge2;
    }

    public void setpDisCharge2(String pDisCharge2) {
        this.pDisCharge2 = pDisCharge2;
    }

    public String getPacToGrid() {
        return pacToGrid;
    }

    public void setPacToGrid(String pacToGrid) {
        this.pacToGrid = pacToGrid;
    }

    public String getPacToUser() {
        return pacToUser;
    }

    public void setPacToUser(String pacToUser) {
        this.pacToUser = pacToUser;
    }

    public String getpCharge() {
        return pCharge;
    }

    public void setpCharge(String pCharge) {
        this.pCharge = pCharge;
    }

    public String getUserLoad() {
        return userLoad;
    }

    public void setUserLoad(String userLoad) {
        this.userLoad = userLoad;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getPpv2() {
        return ppv2;
    }

    public void setPpv2(String ppv2) {
        this.ppv2 = ppv2;
    }

    public String getPpv1() {
        return ppv1;
    }

    public void setPpv1(String ppv1) {
        this.ppv1 = ppv1;
    }

    public String getpDisCharge() {
        return pDisCharge;
    }

    public void setpDisCharge(String pDisCharge) {
        this.pDisCharge = pDisCharge;
    }

    public String getStatus() {
        if (TextUtils.isEmpty(status)){
            return "-1";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPacCharge() {
        return pacCharge;
    }

    public void setPacCharge(String pacCharge) {
        this.pacCharge = pacCharge;
    }

    @Override
    public String toString() {
        return "StorageStatusBean{" +
                "pacToGrid='" + pacToGrid + '\'' +
                ", pacToUser='" + pacToUser + '\'' +
                ", pCharge='" + pCharge + '\'' +
                ", pCharge1='" + pCharge1 + '\'' +
                ", pCharge2='" + pCharge2 + '\'' +
                ", userLoad='" + userLoad + '\'' +
                ", capacity='" + capacity + '\'' +
                ", ppv2='" + ppv2 + '\'' +
                ", ppv1='" + ppv1 + '\'' +
                ", pDisCharge='" + pDisCharge + '\'' +
                ", pDisCharge1='" + pDisCharge1 + '\'' +
                ", pDisCharge2='" + pDisCharge2 + '\'' +
                ", status='" + status + '\'' +
                ", pacCharge='" + pacCharge + '\'' +
                ", deviceType='" + deviceType + '\'' +
                '}';
    }
}
