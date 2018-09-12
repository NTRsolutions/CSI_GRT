package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/6/14.
 */

public class OssDeviceDeticalBean {
    private boolean isTextItem;
    private String name1;
    private String value1;
    private int imgResId;
    private String value2;

    public OssDeviceDeticalBean(boolean isTextItem) {
        this.isTextItem = isTextItem;
    }

    public boolean isTextItem() {
        return isTextItem;
    }

    public void setTextItem(boolean textItem) {
        isTextItem = textItem;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }
}
