package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/6/10.
 * 搜索类型为用户名、手机号、邮箱的实体类
 */

public class OssSearchAllUserListBean {
    private int id;
    private int parentUserId;
    private String accountName;
    private String phoneNum;
    private String email;
    private boolean enabled;
    private int rightlevel;
    private String counrty;
    private String userLanguage;
    private int isPhoneNumReg;
    private int timeZone;
    private String createDate;
    private String lastLoginTime;
    private String activeName;//真实名字
    private String company;//公司

    public String getActiveName() {
        return activeName;
    }

    public void setActiveName(String activeName) {
        this.activeName = activeName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(int parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getRightlevel() {
        return rightlevel;
    }

    public void setRightlevel(int rightlevel) {
        this.rightlevel = rightlevel;
    }

    public String getCounrty() {
        return counrty;
    }

    public void setCounrty(String counrty) {
        this.counrty = counrty;
    }

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public int getIsPhoneNumReg() {
        return isPhoneNumReg;
    }

    public void setIsPhoneNumReg(int isPhoneNumReg) {
        this.isPhoneNumReg = isPhoneNumReg;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
