package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/9/20.
 */

/**
 * 安装商获取电站列表信息实体
 */
public class AZPlantBean {
    private String accountName;
    private String plantName;
    private String alias;
    private String plantId;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    @Override
    public String toString() {
        return "AZPlantBean{" +
                "accountName='" + accountName + '\'' +
                ", plantName='" + plantName + '\'' +
                ", alias='" + alias + '\'' +
                ", plantId='" + plantId + '\'' +
                '}';
    }
}
