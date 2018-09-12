package com.growatt.shinephone.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.growatt.shinephone.bean.v2.Fragment1ListBean;

import java.util.List;

/**
 * Created by dg on 2017/8/17.
 * 根据电站获取所有设备和收益实体
 */

public class DeviceTotalBean implements Parcelable{
    private String plantMoneyText;
    private String todayDischarge;
    private String storagePuser;
    private String useEnergy;
    private String storagePgrid;
    private String todayEnergy;
    private String storageTodayPpv;
    private String invTodayPpv;
    private String totalEnergy;//总电量
    private String totalMoneyText;//z总收益
    private String Co2Reduction;
    private int nominalPower;
    private int nominal_Power;
    private List<Fragment1ListBean> deviceList;

    public DeviceTotalBean(){

    }

    protected DeviceTotalBean(Parcel in) {
        plantMoneyText = in.readString();
        todayDischarge = in.readString();
        storagePuser = in.readString();
        useEnergy = in.readString();
        storagePgrid = in.readString();
        todayEnergy = in.readString();
        storageTodayPpv = in.readString();
        invTodayPpv = in.readString();
        totalEnergy = in.readString();
        totalMoneyText = in.readString();
        Co2Reduction = in.readString();
        nominalPower = in.readInt();
        nominal_Power = in.readInt();
        deviceList = in.createTypedArrayList(Fragment1ListBean.CREATOR);
    }

    public static final Creator<DeviceTotalBean> CREATOR = new Creator<DeviceTotalBean>() {
        @Override
        public DeviceTotalBean createFromParcel(Parcel in) {
            return new DeviceTotalBean(in);
        }

        @Override
        public DeviceTotalBean[] newArray(int size) {
            return new DeviceTotalBean[size];
        }
    };

    public String getPlantMoneyText() {
        return plantMoneyText;
    }

    public void setPlantMoneyText(String plantMoneyText) {
        this.plantMoneyText = plantMoneyText;
    }

    public String getTodayDischarge() {
        return todayDischarge;
    }

    public void setTodayDischarge(String todayDischarge) {
        this.todayDischarge = todayDischarge;
    }

    public String getStoragePuser() {
        return storagePuser;
    }

    public void setStoragePuser(String storagePuser) {
        this.storagePuser = storagePuser;
    }

    public String getUseEnergy() {
        return useEnergy;
    }

    public void setUseEnergy(String useEnergy) {
        this.useEnergy = useEnergy;
    }

    public String getStoragePgrid() {
        return storagePgrid;
    }

    public void setStoragePgrid(String storagePgrid) {
        this.storagePgrid = storagePgrid;
    }

    public String getTodayEnergy() {
        return todayEnergy;
    }

    public void setTodayEnergy(String todayEnergy) {
        this.todayEnergy = todayEnergy;
    }

    public String getStorageTodayPpv() {
        return storageTodayPpv;
    }

    public void setStorageTodayPpv(String storageTodayPpv) {
        this.storageTodayPpv = storageTodayPpv;
    }

    public String getInvTodayPpv() {
        return invTodayPpv;
    }

    public void setInvTodayPpv(String invTodayPpv) {
        this.invTodayPpv = invTodayPpv;
    }

    public String getTotalEnergy() {
        return totalEnergy;
    }

    public void setTotalEnergy(String totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public String getTotalMoneyText() {
        return totalMoneyText;
    }

    public void setTotalMoneyText(String totalMoneyText) {
        this.totalMoneyText = totalMoneyText;
    }

    public String getCo2Reduction() {
        return Co2Reduction;
    }

    public void setCo2Reduction(String co2Reduction) {
        Co2Reduction = co2Reduction;
    }

    public int getNominalPower() {
        return nominalPower;
    }

    public void setNominalPower(int nominalPower) {
        this.nominalPower = nominalPower;
    }

    public int getNominal_Power() {
        return nominal_Power;
    }

    public void setNominal_Power(int nominal_Power) {
        this.nominal_Power = nominal_Power;
    }

    public List<Fragment1ListBean> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Fragment1ListBean> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(plantMoneyText);
        dest.writeString(todayDischarge);
        dest.writeString(storagePuser);
        dest.writeString(useEnergy);
        dest.writeString(storagePgrid);
        dest.writeString(todayEnergy);
        dest.writeString(storageTodayPpv);
        dest.writeString(invTodayPpv);
        dest.writeString(totalEnergy);
        dest.writeString(totalMoneyText);
        dest.writeString(Co2Reduction);
        dest.writeInt(nominalPower);
        dest.writeInt(nominal_Power);
        dest.writeTypedList(deviceList);
    }
}
