package com.growatt.shinephone.bean.mix;

/**
 * Created：2017/11/21 on 17:03
 * Author:gaideng on dg
 * Description:Mix点击后界面，详细信息
 */

public class MixDetailBean {
    private String serialNum; // 序列号
    private String dataLogSn;
    private String alias;
    private int address;
    private boolean withTime; // 发来的数据中是否自带有时间
    private int status = -1; // Mix Status 0: waiting, 1: normal, 2: fault
    private boolean isAgain;// 是否为续传
    private boolean lost = true; // 是否通讯丢失
    private String day;

    private double ppv; // PV输入总功率1-2;0.1W
    private float vpv1; // PV1输入电压3;0.1V
    private double ppv1; // PV1输入功率5-6;0.1W
    private float vpv2; // PV2输入电压7;0.1V
    private double ppv2; // PV2输入功率9-10;0.1W
    private double pac;// 逆变器输出功率35-36;0.1W
    private float fac;// 电网频率37;0.01Hz
    private float vac1;// 电网电压38;0.1V
    private double pac1;// 逆变器输出视在功率40-41;0.1VA
    private float eacToday;// 逆变器日输出电量53-54;0.1kWH
    private double eacTotal;// 逆变器总输出电量55-56;0.1kWH
    private double timeTotal;;// 总运行时间57-58;0.5s
    private float epv1Today;// PV1日发电量59-60;0.1kWh
    private double epv1Total;// PV1总发电量61-62;0.1kWh
    private float epv2Today;// PV2日发电量63-64;0.1kWh
    private double epv2Total;// PV2总发电量65-66;0.1kWh
    private double epvTotal;// PV总发电量91-92;0.1kWh
    private float soc;
    private double edischarge1Today;
    private double edischarge1Total;
    private double pdischarge1;
    private double pcharge1;

    public double getPdischarge1() {
        return pdischarge1;
    }

    public void setPdischarge1(double pdischarge1) {
        this.pdischarge1 = pdischarge1;
    }

    public double getPcharge1() {
        return pcharge1;
    }

    public void setPcharge1(double pcharge1) {
        this.pcharge1 = pcharge1;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getDataLogSn() {
        return dataLogSn;
    }

    public void setDataLogSn(String dataLogSn) {
        this.dataLogSn = dataLogSn;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public boolean isWithTime() {
        return withTime;
    }

    public void setWithTime(boolean withTime) {
        this.withTime = withTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isAgain() {
        return isAgain;
    }

    public void setAgain(boolean again) {
        isAgain = again;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getPpv() {
        return ppv;
    }

    public void setPpv(double ppv) {
        this.ppv = ppv;
    }

    public float getVpv1() {
        return vpv1;
    }

    public void setVpv1(float vpv1) {
        this.vpv1 = vpv1;
    }

    public double getPpv1() {
        return ppv1;
    }

    public void setPpv1(double ppv1) {
        this.ppv1 = ppv1;
    }

    public float getVpv2() {
        return vpv2;
    }

    public void setVpv2(float vpv2) {
        this.vpv2 = vpv2;
    }

    public double getPpv2() {
        return ppv2;
    }

    public void setPpv2(double ppv2) {
        this.ppv2 = ppv2;
    }

    public double getPac() {
        return pac;
    }

    public void setPac(double pac) {
        this.pac = pac;
    }

    public float getFac() {
        return fac;
    }

    public void setFac(float fac) {
        this.fac = fac;
    }

    public float getVac1() {
        return vac1;
    }

    public void setVac1(float vac1) {
        this.vac1 = vac1;
    }

    public double getPac1() {
        return pac1;
    }

    public void setPac1(double pac1) {
        this.pac1 = pac1;
    }

    public float getEacToday() {
        return eacToday;
    }

    public void setEacToday(float eacToday) {
        this.eacToday = eacToday;
    }

    public double getEacTotal() {
        return eacTotal;
    }

    public void setEacTotal(double eacTotal) {
        this.eacTotal = eacTotal;
    }

    public double getTimeTotal() {
        return timeTotal;
    }

    public void setTimeTotal(double timeTotal) {
        this.timeTotal = timeTotal;
    }

    public float getEpv1Today() {
        return epv1Today;
    }

    public void setEpv1Today(float epv1Today) {
        this.epv1Today = epv1Today;
    }

    public double getEpv1Total() {
        return epv1Total;
    }

    public void setEpv1Total(double epv1Total) {
        this.epv1Total = epv1Total;
    }

    public float getEpv2Today() {
        return epv2Today;
    }

    public void setEpv2Today(float epv2Today) {
        this.epv2Today = epv2Today;
    }

    public double getEpv2Total() {
        return epv2Total;
    }

    public void setEpv2Total(double epv2Total) {
        this.epv2Total = epv2Total;
    }

    public double getEpvTotal() {
        return epvTotal;
    }

    public void setEpvTotal(double epvTotal) {
        this.epvTotal = epvTotal;
    }

    public float getSoc() {
        return soc;
    }

    public void setSoc(float soc) {
        this.soc = soc;
    }

    public double getEdischarge1Today() {
        return edischarge1Today;
    }

    public void setEdischarge1Today(double edischarge1Today) {
        this.edischarge1Today = edischarge1Today;
    }

    public double getEdischarge1Total() {
        return edischarge1Total;
    }

    public void setEdischarge1Total(double edischarge1Total) {
        this.edischarge1Total = edischarge1Total;
    }
}
