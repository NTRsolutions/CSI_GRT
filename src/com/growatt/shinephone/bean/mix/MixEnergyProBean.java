package com.growatt.shinephone.bean.mix;

/**
 * Created：2017/11/17 on 18:50
 * Author:gaideng on dg
 * Description:mix的能源产耗饼图实体
 */

public class MixEnergyProBean {
    private String echarge1;//光伏产出今日电量
    private String eAcCharge;//馈回电网今日电量
    private String eChargeToday1;//光伏自发自用今日电量
    private String eChargeToday2;//电池自发自用今日电量
    private String elocalLoad;//用电消耗今日电量
    private String etouser;//电网取电今日电量
    private String echargeToat;//来自电池今日电量
    private String eCharge;//来自电池总电量
    private String photovoltaic;

    public String getPhotovoltaic() {
        return photovoltaic;
    }

    public void setPhotovoltaic(String photovoltaic) {
        this.photovoltaic = photovoltaic;
    }

    public String getEcharge1() {
        return echarge1;
    }

    public void setEcharge1(String echarge1) {
        this.echarge1 = echarge1;
    }

    public String geteAcCharge() {
        return eAcCharge;
    }

    public void seteAcCharge(String eAcCharge) {
        this.eAcCharge = eAcCharge;
    }

    public String geteChargeToday1() {
        return eChargeToday1;
    }

    public void seteChargeToday1(String eChargeToday1) {
        this.eChargeToday1 = eChargeToday1;
    }

    public String geteChargeToday2() {
        return eChargeToday2;
    }

    public void seteChargeToday2(String eChargeToday2) {
        this.eChargeToday2 = eChargeToday2;
    }

    public String getElocalLoad() {
        return elocalLoad;
    }

    public void setElocalLoad(String elocalLoad) {
        this.elocalLoad = elocalLoad;
    }

    public String getEtouser() {
        return etouser;
    }

    public void setEtouser(String etouser) {
        this.etouser = etouser;
    }

    public String getEchargeToat() {
        return echargeToat;
    }

    public void setEchargeToat(String echargeToat) {
        this.echargeToat = echargeToat;
    }

    public String geteCharge() {
        return eCharge;
    }

    public void seteCharge(String eCharge) {
        this.eCharge = eCharge;
    }
}
