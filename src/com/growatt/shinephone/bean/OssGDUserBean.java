package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/6/6.
 */

public class OssGDUserBean {
    private int status;
    private String title;
    private String userName;
    private String content;
    private String time;

    public OssGDUserBean(int status, String title, String userName, String content, String time) {
        this.status = status;
        this.title = title;
        this.userName = userName;
        this.content = content;
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "OssGDUserBean{" +
                "status=" + status +
                ", title='" + title + '\'' +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
