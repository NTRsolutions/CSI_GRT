package com.growatt.shinephone.bean.mix;

/**
 * Created：2017/11/23 on 15:16
 * Author:gaideng on dg
 * Description:MixInfo-->MixParamsActivity
 */

public class MixInfoBean {
    private String vbat;
    private String vpv1;
    private String vpv2;
    private String capacity;
    private String pCharge1;
    private String epvTotal;
    private String epvToday;
    private String eBatChargeToday;
    private String eBatChargeTotal;
    private String eBatDisChargeToday;
    private String eBatDisChargeTotal;
    //新增
    private String acChargePower;
    private String acChargeEnergyToday;
    private String acChargeEnergyTotal;
    private String soc;

    public String getAcChargePower() {
        return acChargePower;
    }

    public void setAcChargePower(String acChargePower) {
        this.acChargePower = acChargePower;
    }

    public String getAcChargeEnergyToday() {
        return acChargeEnergyToday;
    }

    public void setAcChargeEnergyToday(String acChargeEnergyToday) {
        this.acChargeEnergyToday = acChargeEnergyToday;
    }

    public String getAcChargeEnergyTotal() {
        return acChargeEnergyTotal;
    }

    public void setAcChargeEnergyTotal(String acChargeEnergyTotal) {
        this.acChargeEnergyTotal = acChargeEnergyTotal;
    }

    public String getSoc() {
        return soc;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String getVbat() {
        return vbat;
    }

    public void setVbat(String vbat) {
        this.vbat = vbat;
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

    public String getEpvTotal() {
        return epvTotal;
    }

    public void setEpvTotal(String epvTotal) {
        this.epvTotal = epvTotal;
    }

    public String getEpvToday() {
        return epvToday;
    }

    public void setEpvToday(String epvToday) {
        this.epvToday = epvToday;
    }

    public String geteBatChargeToday() {
        return eBatChargeToday;
    }

    public void seteBatChargeToday(String eBatChargeToday) {
        this.eBatChargeToday = eBatChargeToday;
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

    public String geteBatDisChargeTotal() {
        return eBatDisChargeTotal;
    }

    public void seteBatDisChargeTotal(String eBatDisChargeTotal) {
        this.eBatDisChargeTotal = eBatDisChargeTotal;
    }
}
