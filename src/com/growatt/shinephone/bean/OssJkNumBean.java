package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/6/19.
 */

public class OssJkNumBean {
    private int type;
    private String name;
    private int num;
    private int imgRes;

    @Override
    public String toString() {
        return "OssJkNumBean{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", imgRes=" + imgRes +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }
}
