package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/7/3.
 * 集成商主界面实体
 */

public class OssJKMainBean {
    private String todayEnergy;
    private String totalEnergy;
    private String totalInvNum;
    private String totalPower;

    public String getTodayEnergy() {
        return todayEnergy;
    }

    public void setTodayEnergy(String todayEnergy) {
        this.todayEnergy = todayEnergy;
    }

    public String getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(String totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public String getTotalInvNum() {
        return totalInvNum;
    }

    public void setTotalInvNum(String totalInvNum) {
        this.totalInvNum = totalInvNum;
    }

    public String getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(String totalPower) {
        this.totalPower = totalPower;
    }
}
