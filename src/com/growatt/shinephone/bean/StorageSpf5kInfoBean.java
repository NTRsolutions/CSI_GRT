package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/8/28.
 */

public class StorageSpf5kInfoBean {
    /*
    * vbat：电池电压
capacity：电池容量百分比
vpv1:pv1电压
vpv2:pv2电压
iChargePV1：pv1电流
iChargePV2：pv2电流
pCharge1：pv1功率
pCharge2：pv2功率
vGrid：ac输入电压
freqGrid：ac输入频率
outPutVolt：ac输出电压
freqOutPut：ac输出频率
outPutPower：负载功率
loadPercent：负载百分比
epvToday：pv今日电量pv1+pv2
epvTotal：pv总电量
eBatChargeToday：电池今日充电量
eBatChargeTotal：电池总充电量
eBatDisChargeToday：电池今日放电量
eBatDisChargeTotal：电池总放电量
*/
    private String freqGrid;
    private String eBatDisChargeTotal;
    private String outPutPower;
    private String vGrid;
    private String epvToday;
    private String loadPercent;
    private String iChargePV2;
    private String iChargePV1;
    private String outPutVolt;
    private String vpv1;
    private String vpv2;
    private String eBatChargeToday;
    private String pCharge2;
    private String capacity;
    private String pCharge1;
    private String eBatChargeTotal;
    private String eBatDisChargeToday;
    private String freqOutPut;
    private String epvTotal;
    private String vbat;

    public String getFreqGrid() {
        return freqGrid;
    }

    public void setFreqGrid(String freqGrid) {
        this.freqGrid = freqGrid;
    }

    public String geteBatDisChargeTotal() {
        return eBatDisChargeTotal;
    }

    public void seteBatDisChargeTotal(String eBatDisChargeTotal) {
        this.eBatDisChargeTotal = eBatDisChargeTotal;
    }

    public String getOutPutPower() {
        return outPutPower;
    }

    public void setOutPutPower(String outPutPower) {
        this.outPutPower = outPutPower;
    }

    public String getvGrid() {
        return vGrid;
    }

    public void setvGrid(String vGrid) {
        this.vGrid = vGrid;
    }

    public String getEpvToday() {
        return epvToday;
    }

    public void setEpvToday(String epvToday) {
        this.epvToday = epvToday;
    }

    public String getLoadPercent() {
        return loadPercent;
    }

    public void setLoadPercent(String loadPercent) {
        this.loadPercent = loadPercent;
    }

    public String getiChargePV2() {
        return iChargePV2;
    }

    public void setiChargePV2(String iChargePV2) {
        this.iChargePV2 = iChargePV2;
    }

    public String getiChargePV1() {
        return iChargePV1;
    }

    public void setiChargePV1(String iChargePV1) {
        this.iChargePV1 = iChargePV1;
    }

    public String getOutPutVolt() {
        return outPutVolt;
    }

    public void setOutPutVolt(String outPutVolt) {
        this.outPutVolt = outPutVolt;
    }

    public String getVpv1() {
        return vpv1;
    }

    public void setVpv1(String vpv1) {
        this.vpv1 = vpv1;
    }

    public String getVpv2() {
        return vpv2;
    }

    public void setVpv2(String vpv2) {
        this.vpv2 = vpv2;
    }

    public String geteBatChargeToday() {
        return eBatChargeToday;
    }

    public void seteBatChargeToday(String eBatChargeToday) {
        this.eBatChargeToday = eBatChargeToday;
    }

    public String getpCharge2() {
        return pCharge2;
    }

    public void setpCharge2(String pCharge2) {
        this.pCharge2 = pCharge2;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getpCharge1() {
        return pCharge1;
    }

    public void setpCharge1(String pCharge1) {
        this.pCharge1 = pCharge1;
    }

    public String geteBatChargeTotal() {
        return eBatChargeTotal;
    }

    public void seteBatChargeTotal(String eBatChargeTotal) {
        this.eBatChargeTotal = eBatChargeTotal;
    }

    public String geteBatDisChargeToday() {
        return eBatDisChargeToday;
    }

    public void seteBatDisChargeToday(String eBatDisChargeToday) {
        this.eBatDisChargeToday = eBatDisChargeToday;
    }

    public String getFreqOutPut() {
        return freqOutPut;
    }

    public void setFreqOutPut(String freqOutPut) {
        this.freqOutPut = freqOutPut;
    }

    public String getEpvTotal() {
        return epvTotal;
    }

    public void setEpvTotal(String epvTotal) {
        this.epvTotal = epvTotal;
    }

    public String getVbat() {
        return vbat;
    }

    public void setVbat(String vbat) {
        this.vbat = vbat;
    }
}
