package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/4/10.
 */

public class EnergyOverviewBean {
    private String useEnergyTotal;
    private String useEnergyToday;
    private String eToUserToday;
    private String eToUserTotal;
    private String eDischargeToday;
    private String eDischargeTotal;
    private String epvToday;
    private String epvTotal;
    /*spf5k*/
    private String eChargeToday;
    private String eChargeTotal;

    @Override
    public String toString() {
        return "EnergyOverviewBean{" +
                "useEnergyTotal='" + useEnergyTotal + '\'' +
                ", useEnergyToday='" + useEnergyToday + '\'' +
                ", eToUserToday='" + eToUserToday + '\'' +
                ", eToUserTotal='" + eToUserTotal + '\'' +
                ", eDischargeToday='" + eDischargeToday + '\'' +
                ", eDischargeTotal='" + eDischargeTotal + '\'' +
                ", epvToday='" + epvToday + '\'' +
                ", epvTotal='" + epvTotal + '\'' +
                '}';
    }

    public String geteChargeToday() {
        return eChargeToday;
    }

    public void seteChargeToday(String eChargeToday) {
        this.eChargeToday = eChargeToday;
    }

    public String geteChargeTotal() {
        return eChargeTotal;
    }

    public void seteChargeTotal(String eChargeTotal) {
        this.eChargeTotal = eChargeTotal;
    }

    public String getUseEnergyTotal() {
        return useEnergyTotal;
    }

    public void setUseEnergyTotal(String useEnergyTotal) {
        this.useEnergyTotal = useEnergyTotal;
    }

    public String getUseEnergyToday() {
        return useEnergyToday;
    }

    public void setUseEnergyToday(String useEnergyToday) {
        this.useEnergyToday = useEnergyToday;
    }

    public String geteToUserToday() {
        return eToUserToday;
    }

    public void seteToUserToday(String eToUserToday) {
        this.eToUserToday = eToUserToday;
    }

    public String geteToUserTotal() {
        return eToUserTotal;
    }

    public void seteToUserTotal(String eToUserTotal) {
        this.eToUserTotal = eToUserTotal;
    }

    public String geteDischargeToday() {
        return eDischargeToday;
    }

    public void seteDischargeToday(String eDischargeToday) {
        this.eDischargeToday = eDischargeToday;
    }

    public String geteDischargeTotal() {
        return eDischargeTotal;
    }

    public void seteDischargeTotal(String eDischargeTotal) {
        this.eDischargeTotal = eDischargeTotal;
    }

    public String getEpvToday() {
        return epvToday;
    }

    public void setEpvToday(String epvToday) {
        this.epvToday = epvToday;
    }

    public String getEpvTotal() {
        return epvTotal;
    }

    public void setEpvTotal(String epvTotal) {
        this.epvTotal = epvTotal;
    }
}
