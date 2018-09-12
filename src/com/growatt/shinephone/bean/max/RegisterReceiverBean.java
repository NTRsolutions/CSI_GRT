package com.growatt.shinephone.bean.max;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dg on 2017/10/18.
 * 接收内容实体
 */

public class RegisterReceiverBean {
    private int slaveAddress;
    //功能码
    private byte function;
    //字节数
    private byte byteCount;
    //数据Map
    private Map<Byte,Byte> datas = new HashMap<>();
    //加密
    private Map<Byte,Byte> lrc = new HashMap<>();

    public int getSlaveAddress() {
        return slaveAddress;
    }

    public void setSlaveAddress(int slaveAddress) {
        this.slaveAddress = slaveAddress;
    }

    public byte getFunction() {
        return function;
    }

    public void setFunction(byte function) {
        this.function = function;
    }

    public byte getByteCount() {
        return byteCount;
    }

    public void setByteCount(byte byteCount) {
        this.byteCount = byteCount;
    }

    public Map<Byte, Byte> getDatas() {
        return datas;
    }

    public void setDatas(Map<Byte, Byte> datas) {
        this.datas = datas;
    }

    public Map<Byte, Byte> getLrc() {
        return lrc;
    }

    public void setLrc(Map<Byte, Byte> lrc) {
        this.lrc = lrc;
    }
}
