package com.growatt.shinephone.bean.ossv2;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created：2017/12/29 on 14:35
 * Author:gaideng on dg
 * Description:Oss工单报表
 */

public class OssGDTotalReportBean implements MultiItemEntity {
    //0:为默认一行带编辑框；1：为两行
    private int type;
    private String title;
    private String content;
    private String hintText;

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getItemType() {
        return type;
    }

    @Override
    public String toString() {
        return "OssGDTotalReportBean{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", hintText='" + hintText + '\'' +
                '}';
    }
}
