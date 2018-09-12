package com.growatt.shinephone.bean.mix;

/**
 * Created：2017/11/21 on 17:23
 * Author:gaideng on dg
 * Description:
 */

public class MixParamsBean {
    private MixBean mixBean;
    private MixDetailBean mixDetailBean;
    private String todayRevenue;
    private String totalRevenue;
    private String apparentPower;
    private String totalRevenuetext;
    private String todayRevenuetext;

    public MixBean getMixBean() {
        return mixBean;
    }

    public void setMixBean(MixBean mixBean) {
        this.mixBean = mixBean;
    }

    public MixDetailBean getMixDetailBean() {
        return mixDetailBean;
    }

    public void setMixDetailBean(MixDetailBean mixDetailBean) {
        this.mixDetailBean = mixDetailBean;
    }

    public String getTodayRevenue() {
        return todayRevenue;
    }

    public void setTodayRevenue(String todayRevenue) {
        this.todayRevenue = todayRevenue;
    }

    public String getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(String totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public String getApparentPower() {
        return apparentPower;
    }

    public void setApparentPower(String apparentPower) {
        this.apparentPower = apparentPower;
    }

    public String getTotalRevenuetext() {
        return totalRevenuetext;
    }

    public void setTotalRevenuetext(String totalRevenuetext) {
        this.totalRevenuetext = totalRevenuetext;
    }

    public String getTodayRevenuetext() {
        return todayRevenuetext;
    }

    public void setTodayRevenuetext(String todayRevenuetext) {
        this.todayRevenuetext = todayRevenuetext;
    }
}
