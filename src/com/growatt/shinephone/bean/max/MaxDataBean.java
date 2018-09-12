package com.growatt.shinephone.bean.max;

import com.growatt.shinephone.util.max.Arith;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dg on 2017/10/25.
 * max主界面所有数据实体
 */

public class MaxDataBean {
    private final double muilt = 0.1;
    //逆变器状态：0:Waiting, 1:Normal, 2:Upgrade;3:Fault
    private int status = -1;
    //今日和累计电量，倍数0.1
    private int todayEnergy;
    private int totalEnergy;
    //当前功率和装机功率，*0.1
    private int normalPower;
    private int totalPower;
    //故障和告警
    private int errCode;
    private int warmCode;
    //故障位
    private int error1;
    private int error2;
    //设备信息
    private MaxDataDeviceBean mDeviceBeen = new MaxDataDeviceBean();
    //pv信息
    private List<String> mPVList = new ArrayList<>();
    //PV串信息
    private List<String> mPVCList = new ArrayList<>();
    //ac信息
    private List<String> mACList = new ArrayList<>();
    //pid信息
    private List<String> mPIDList = new ArrayList<>();

    public double getTodayEnergy() {
        return Arith.mul(todayEnergy,muilt);
    }

    public void setTodayEnergy(int todayEnergy) {
        this.todayEnergy = todayEnergy;
    }

    public double getTotalEnergy() {
        return Arith.mul(totalEnergy,muilt);
    }

    public void setTotalEnergy(int totalEnergy) {
        this.totalEnergy = totalEnergy;
    }

    public double getNormalPower() {
        return Arith.mul(normalPower,muilt);
    }

    public void setNormalPower(int normalPower) {
        this.normalPower = normalPower;
    }

    public double getTotalPower() {
        return Arith.mul(totalPower,muilt);
    }

    public void setTotalPower(int todayPower) {
        this.totalPower = todayPower;
    }

    public MaxDataDeviceBean getDeviceBeen() {
        return mDeviceBeen;
    }

    public void setDeviceBeen(MaxDataDeviceBean deviceBeen) {
        mDeviceBeen = deviceBeen;
    }

    public List<String> getPVList() {
        return mPVList;
    }

    public void setPVList(List<String> PVList) {
        mPVList = PVList;
    }

    public List<String> getPVCList() {
        return mPVCList;
    }

    public void setPVCList(List<String> PVCList) {
        mPVCList = PVCList;
    }

    public List<String> getACList() {
        return mACList;
    }

    public void setACList(List<String> ACList) {
        mACList = ACList;
    }

    public List<String> getPIDList() {
        return mPIDList;
    }

    public void setPIDList(List<String> PIDList) {
        mPIDList = PIDList;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public int getWarmCode() {
        return warmCode;
    }

    public void setWarmCode(int warmCode) {
        this.warmCode = warmCode;
    }

    public int getError1() {
        return error1;
    }

    public void setError1(int error1) {
        this.error1 = error1;
    }

    public int getError2() {
        return error2;
    }

    public void setError2(int error2) {
        this.error2 = error2;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
