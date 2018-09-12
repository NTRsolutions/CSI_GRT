package com.growatt.shinephone.bean;

import android.text.TextUtils;

/**
 * Created by dg on 2017/8/30.
 */

public class SPFBarChartBean {
    /*SPF5000：
eCharge:pv充电
eAcCharge :ac充电
eChargeTotal:总充电
eDisCharge:电池放电
eAcDisCharge:ac放电
eDisChargeTotal:总放电
*/
    private String eCharge;
    private String eAcCharge;
    private String eChargeTotal;
    private String eDisCharge;
    private String eAcDisCharge;
    private String eDisChargeTotal;

    public String geteCharge() {
        return eCharge;
    }

    public void seteCharge(String eCharge) {
        this.eCharge = eCharge;
    }

    public String geteAcCharge() {
        return eAcCharge;
    }

    public void seteAcCharge(String eAcCharge) {
        this.eAcCharge = eAcCharge;
    }

    public String geteChargeTotal() {
        if (TextUtils.isEmpty(eChargeTotal)){
            return "0";
        }
        return eChargeTotal;
    }

    public void seteChargeTotal(String eChargeTotal) {
        this.eChargeTotal = eChargeTotal;
    }

    public String geteDisCharge() {
        return eDisCharge;
    }

    public void seteDisCharge(String eDisCharge) {
        this.eDisCharge = eDisCharge;
    }

    public String geteAcDisCharge() {
        return eAcDisCharge;
    }

    public void seteAcDisCharge(String eAcDisCharge) {
        this.eAcDisCharge = eAcDisCharge;
    }

    public String geteDisChargeTotal() {
        if (TextUtils.isEmpty(eDisChargeTotal)){
            return "0";
        }
        return eDisChargeTotal;
    }

    public void seteDisChargeTotal(String eDisChargeTotal) {
        this.eDisChargeTotal = eDisChargeTotal;
    }
}
