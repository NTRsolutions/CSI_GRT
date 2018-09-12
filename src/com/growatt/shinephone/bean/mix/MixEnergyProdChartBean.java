package com.growatt.shinephone.bean.mix;

import java.util.List;

/**
 * Created：2017/12/13 on 14:12
 * Author:gaideng on dg
 * Description:
 */

public class MixEnergyProdChartBean {
    private List<Float> sysOut;
    private List<Float> pacToUser;
    private List<Float> userLoad;
    private List<Float> ppv;
    private String date;

    public List<Float> getSysOut() {
        return sysOut;
    }

    public void setSysOut(List<Float> sysOut) {
        this.sysOut = sysOut;
    }

    public List<Float> getPacToUser() {
        return pacToUser;
    }

    public void setPacToUser(List<Float> pacToUser) {
        this.pacToUser = pacToUser;
    }

    public List<Float> getUserLoad() {
        return userLoad;
    }

    public void setUserLoad(List<Float> userLoad) {
        this.userLoad = userLoad;
    }

    public List<Float> getPpv() {
        return ppv;
    }

    public void setPpv(List<Float> ppv) {
        this.ppv = ppv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
