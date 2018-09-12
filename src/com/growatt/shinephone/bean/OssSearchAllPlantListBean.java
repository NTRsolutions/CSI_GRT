package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/6/10.
 * 搜索类型为电站的实体类
 */

public class OssSearchAllPlantListBean {
    //电站ID
    private int id;
    //电站名称
    private String plantName;
    //所属用户名
    private String userAccount;
    //创建日期
    private String createDateText;
    //电站额定功率
    private int nominalPower;
    //设计厂商
    private String designCompany;
    //国家
    private String country;
    //城市
    private String city;
    //时区
    private String timezone;
    //纬度
    private String plant_lat;
    //经度
    private String plant_lng;

    @Override
    public String toString() {
        return "OssSearchAllPlantListBean{" +
                "id=" + id +
                ", plantName='" + plantName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", createDateText='" + createDateText + '\'' +
                ", nominalPower=" + nominalPower +
                ", designCompany='" + designCompany + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", timezone='" + timezone + '\'' +
                ", plant_lat='" + plant_lat + '\'' +
                ", plant_lng='" + plant_lng + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getCreateDateText() {
        return createDateText;
    }

    public void setCreateDateText(String createDateText) {
        this.createDateText = createDateText;
    }

    public int getNominalPower() {
        return nominalPower;
    }

    public void setNominalPower(int nominalPower) {
        this.nominalPower = nominalPower;
    }

    public String getDesignCompany() {
        return designCompany;
    }

    public void setDesignCompany(String designCompany) {
        this.designCompany = designCompany;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getPlant_lat() {
        return plant_lat;
    }

    public void setPlant_lat(String plant_lat) {
        this.plant_lat = plant_lat;
    }

    public String getPlant_lng() {
        return plant_lng;
    }

    public void setPlant_lng(String plant_lng) {
        this.plant_lng = plant_lng;
    }
}
