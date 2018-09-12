package com.growatt.shinephone.bean.max;

import com.growatt.shinephone.util.max.Arith;

/**
 * Created by dg on 2017/10/25.
 */

public class MaxDataDeviceBean {
    private final double muilt = 0.1;
    private final double muiltT = 0.5;
    private final double muiltF = 0.01;
    //序列号
    private String sn;
    //厂商信息
    private String company;
    //pv输入功率
    private String pvInPower;
    //pv总电量
    private String pvTotalEnergy;
    //内部固件版本
    private String firmVersionIn;
    //外部固件版本
    private String firmVersionOut;
    //总工作时长
    private String totalTime;
    //电网频率
    private String gridFre;
    //逆变器温度:inv94
    private String deviceTemp;
    //boost温度95
    private String boostTemp;
    //环境温度93
    private String envTemp;
    //ipm温度
    private String ipmTemp;
    //ipf
    private String ipf;
    //pbus电压
    private String pBusV;
    private String nBusV;
//最大输出功率
    private String maxOutPower;
    //实际输出功率百分比
    private String realOPowerPercent;
    //降额模式
    private String derateMode;
    //不匹配的串
    private String mismatchC;
    //断开的串
    private String disConnectC;
    //电量不平衡的串
    private String unblanceC;
    //pid 故障码
    private String pidErrCode;
    //pid状态
    private int pidStatus;
    //额定功率
    private String normalPower;
    //model号
    private int model;
    //并网倒计时
    private String lastTime;
    //机器型号
    private String deviceType;

    public String getEnvTemp() {
        return envTemp;
    }
    public void setEnvTemp(int envTemp) {
        this.envTemp = Arith.mul(envTemp,muilt)+ "℃";
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setNormalPower(int normalPower) {
        this.normalPower = Arith.mul(normalPower,muilt) + "";
    }

    public void setLastTime(int lastTime) {
        this.lastTime = lastTime + "s";
    }

    public void setModel(int model) {
        this.model = model;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPvInPower(int pvInPower) {
        this.pvInPower = Arith.mul(pvInPower,muilt) + "";
    }

    public void setPvTotalEnergy(int pvTotalEnergy) {
        this.pvTotalEnergy = Arith.mul(pvTotalEnergy,muilt) + "";
    }

    public void setFirmVersionIn(String firmVersionIn) {
        this.firmVersionIn = firmVersionIn;
    }

    public void setFirmVersionOut(String firmVersionOut) {
        this.firmVersionOut = firmVersionOut;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = Arith.mul(totalTime,muiltT) + "s";
    }

    public void setGridFre(int gridFre) {
        this.gridFre = Arith.mul(gridFre,muiltF) + "";
    }

    public void setDeviceTemp(int deviceTemp) {
        this.deviceTemp = Arith.mul(deviceTemp,muilt) + "℃";
    }

    public void setBoostTemp(int boostTemp) {
        this.boostTemp = Arith.mul(boostTemp,muilt)+ "℃";
    }

    public void setIpmTemp(int ipmTemp) {
        this.ipmTemp = Arith.mul(ipmTemp,muilt) + "";
    }

    public void setIpf(int ipf) {
        this.ipf = Arith.div(ipf - 10000,10000.0,4) + "";
    }

    public void setpBusV(int pBusV) {
        this.pBusV = Arith.mul(pBusV,muilt) + "";
    }

    public void setnBusV(int nBusV) {
        this.nBusV = Arith.mul(nBusV,muilt) + "";
    }

    public void setMaxOutPower(int maxOutPower) {
        this.maxOutPower = Arith.mul(maxOutPower,muilt) + "";
    }

    public void setRealOPowerPercent(int realOPowerPercent) {
        this.realOPowerPercent = realOPowerPercent + "%";
    }

    public void setDerateMode(int derateMode) {
        this.derateMode = derateMode + "";
    }

    public void setMismatchC(int mismatchC) {
        this.mismatchC = mismatchC + "";
    }

    public void setDisConnectC(int disConnectC) {
        this.disConnectC = disConnectC + "";
    }

    public void setUnblanceC(int unblanceC) {
        this.unblanceC = unblanceC + "";
    }

    public void setPidErrCode(int pidErrCode) {
        this.pidErrCode = pidErrCode + "";
    }

    public void setPidStatus(int pidStatus) {
        this.pidStatus = pidStatus;
    }

    public String getSn() {
        return sn;
    }
    public String getCompany() {
        return company;
    }

    public String getPvInPower() {
        return pvInPower;
    }

    public String getPvTotalEnergy() {
        return pvTotalEnergy;
    }

    public String getFirmVersionIn() {
        return firmVersionIn;
    }

    public String getFirmVersionOut() {
        return firmVersionOut;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public String getGridFre() {
        return gridFre;
    }

    public String getDeviceTemp() {
        return deviceTemp;
    }

    public String getBoostTemp() {
        return boostTemp;
    }

    public String getIpmTemp() {
        return ipmTemp;
    }

    public String getIpf() {
        return ipf;
    }

    public String getpBusV() {
        return pBusV;
    }

    public String getnBusV() {
        return nBusV;
    }

    public String getMaxOutPower() {
        return maxOutPower;
    }

    public String getRealOPowerPercent() {
        return realOPowerPercent;
    }

    public String getDerateMode() {
        return derateMode;
    }

    public String getMismatchC() {
        return mismatchC;
    }

    public String getDisConnectC() {
        return disConnectC;
    }

    public String getUnblanceC() {
        return unblanceC;
    }

    public String getPidErrCode() {
        return pidErrCode;
    }

    public int getPidStatus() {
        return pidStatus;
    }

    public String getNormalPower() {
        return normalPower;
    }

    public int getModel() {
        return model;
    }

    public String getLastTime() {
        return lastTime;
    }
}
