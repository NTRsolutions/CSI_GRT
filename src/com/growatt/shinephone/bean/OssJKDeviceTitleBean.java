package com.growatt.shinephone.bean;

import android.text.TextUtils;

/**
 * oss集成商主入口设备管理
 * Created by dg on 2017/6/28.
 */

public class OssJKDeviceTitleBean {
    private int imgRes;//图标资源
    private String title;//标题
    private String content;//内容
    private String unit;//内容单位

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        if (TextUtils.isEmpty(content)){
            return "0";
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
