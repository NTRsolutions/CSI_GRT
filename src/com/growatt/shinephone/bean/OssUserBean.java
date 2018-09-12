package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/6/1.
 * oss用户实体信息
 */

public class OssUserBean {
    private String name;
    private int role = -1;
    private String jobId;
    private int enabled;
    private String email;
    private String phone;
    private String company;//公司名称（集成商才有）
    private String code;//集成商代理商编码
    //oss角色静态常量
    public static final int SUPER_ADMIN = 1;//管理员
    public static final int CUSTOMER_ADMIN = 2;//客服主管
    public static final int CUSTOMER_PERSON = 3;//普通客服
    public static final int DISTRIBUTOR_PERSON = 6;//分销商
    public static final int INSTALLER_PERSON = 7;//安装商
    public static final int DISTRIBUTOR_NO_PERFECT = 14;//分销商未完善资料
    public static final int INSTALLER_NO_PERFECT = 15;//安装商未完善资料

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "OssUserBean{" +
                "name='" + name + '\'' +
                ", role=" + role +
                ", jobId='" + jobId + '\'' +
                ", enabled=" + enabled +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", company='" + company + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
