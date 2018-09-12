package com.growatt.shinephone.bean.mix;

/**
 * Created：2017/11/20 on 13:49
 * Author:gaideng on dg
 * Description:Mix在fragment1中的状态实体
 */

public class MixStatusBean {
    //放电功率
    private float pdisCharge1;
    /*
    mix状态
    0:等待模式
    1:自检模式
    3:故障模式
    4:升级中
    5、6、7、8:正常模式
     */
    private int status;
    //电网功率馈电
    private float pactogrid;
    //pv功率
    private float ppv;
    //用电功率
    private float pLocalLoad;
    //优先级模式读取0:Load;1:Battery;2:Grid
    private String priorityChoose;
    /**
     * 0:Lithium
     1:Lead-acid
     2:other
     */
    private int wBatteryType;
    //充电功率
    private float chargePower;
    //电网功率取电
    private float pactouser;
    /*
    系统状态建议使用该寄存器1000
0x00:等待模式
0x01:自检模式，可选
0x02 : 保留
0x03：SysFault模式
0x04: Flash模式
0x05：PVBATOnline模式:
0x06：BatOnline模式
0x07：PVOfflineMode模式
0x08：BatOfflineMode
     */
    private int uwSysWorkMode = -1;

    private String vPv1;
    private String pPv2;
    private String upsFac;
    private String pPv1;
    private String SOC;
    private String fAc;
    private String vBat;
    private String upsVac1;
    private String deviceType;
    private String vAc1;
    private String vPv2;
    private int invStatus;
/*新增lost
mix.status.operating
 mix.status.checking
 mix.status.fault
 mix.status.flash
 mix.status.normal
 mix.status.lost
* */
    private String lost;

    public String getLost() {
        return lost;
    }

    public String getPriorityChoose() {
        return priorityChoose;
    }

    public void setPriorityChoose(String priorityChoose) {
        this.priorityChoose = priorityChoose;
    }

    public void setLost(String lost) {
        this.lost = lost;
    }

    public float getPdisCharge1() {
        return pdisCharge1;
    }

    public void setPdisCharge1(float pdisCharge1) {
        this.pdisCharge1 = pdisCharge1;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getPactogrid() {
        return pactogrid;
    }

    public void setPactogrid(float pactogrid) {
        this.pactogrid = pactogrid;
    }

    public float getPpv() {
        return ppv;
    }

    public void setPpv(float ppv) {
        this.ppv = ppv;
    }

    public float getpLocalLoad() {
        return pLocalLoad;
    }

    public void setpLocalLoad(float pLocalLoad) {
        this.pLocalLoad = pLocalLoad;
    }

    public int getwBatteryType() {
        return wBatteryType;
    }

    public void setwBatteryType(int wBatteryType) {
        this.wBatteryType = wBatteryType;
    }

    public float getChargePower() {
        return chargePower;
    }

    public void setChargePower(float chargePower) {
        this.chargePower = chargePower;
    }

    public float getPactouser() {
        return pactouser;
    }

    public void setPactouser(float pactouser) {
        this.pactouser = pactouser;
    }

    public int getUwSysWorkMode() {
        return uwSysWorkMode;
    }

    public void setUwSysWorkMode(int uwSysWorkMode) {
        this.uwSysWorkMode = uwSysWorkMode;
    }

    public String getvPv1() {
        return vPv1;
    }

    public void setvPv1(String vPv1) {
        this.vPv1 = vPv1;
    }

    public String getpPv2() {
        return pPv2;
    }

    public void setpPv2(String pPv2) {
        this.pPv2 = pPv2;
    }

    public String getUpsFac() {
        return upsFac;
    }

    public void setUpsFac(String upsFac) {
        this.upsFac = upsFac;
    }

    public String getpPv1() {
        return pPv1;
    }

    public void setpPv1(String pPv1) {
        this.pPv1 = pPv1;
    }

    public String getSOC() {
        return SOC;
    }

    public void setSOC(String SOC) {
        this.SOC = SOC;
    }

    public String getfAc() {
        return fAc;
    }

    public void setfAc(String fAc) {
        this.fAc = fAc;
    }

    public String getvBat() {
        return vBat;
    }

    public void setvBat(String vBat) {
        this.vBat = vBat;
    }

    public String getUpsVac1() {
        return upsVac1;
    }

    public void setUpsVac1(String upsVac1) {
        this.upsVac1 = upsVac1;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getvAc1() {
        return vAc1;
    }

    public void setvAc1(String vAc1) {
        this.vAc1 = vAc1;
    }

    public String getvPv2() {
        return vPv2;
    }

    public void setvPv2(String vPv2) {
        this.vPv2 = vPv2;
    }

    public int getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(int invStatus) {
        this.invStatus = invStatus;
    }
}
