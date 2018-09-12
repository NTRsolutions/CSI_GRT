package com.growatt.shinephone.bean.mix;

/**
 * Created：2017/12/13 on 19:21
 * Author:gaideng on dg
 * Description:
 */

public class HtmlBean {
    private String title;
    private String content;
    /**
     * 16进制：#121212
     */
    private String color;
    private String value;
    private String last;

    public HtmlBean() {
    }

    public HtmlBean(String title, String color, String value, String last) {
        this.title = title;
        this.color = color;
        this.value = value;
        this.last = last;
    }

    public HtmlBean(String color, String value, String last) {
        this.color = color;
        this.value = value;
        this.last = last;
    }

    public HtmlBean(String color, String value) {
        this.color = color;
        this.value = value;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }
}
