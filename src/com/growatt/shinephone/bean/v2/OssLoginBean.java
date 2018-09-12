package com.growatt.shinephone.bean.v2;

import com.growatt.shinephone.bean.OssUserBean;

import java.util.List;

/**
 * Created：2018/1/18 on 20:11
 * Author:gaideng on dg
 * Description:oss用户登录实体
 */

public class OssLoginBean {
    /**
     * oss关联服务器信息
     */
    private List<OssServerListBean> serverList;
    /**
     * 当前登录oss服务器地址
     */
    private String ossServerUrl;
    /**
     * oss用户权限表
     */
    private List<String> permissions;
    private String userServerUrl;
    private String userName;
    /**
     * 登录用户类型
     */
    private int userType;
    /**
     * 当前用户实体
     */
    private OssUserBean user;

    public List<OssServerListBean> getServerList() {
        return serverList;
    }

    public void setServerList(List<OssServerListBean> serverList) {
        this.serverList = serverList;
    }

    public String getOssServerUrl() {
        return ossServerUrl;
    }

    public void setOssServerUrl(String ossServerUrl) {
        this.ossServerUrl = ossServerUrl;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public String getUserServerUrl() {
        return userServerUrl;
    }

    public void setUserServerUrl(String userServerUrl) {
        this.userServerUrl = userServerUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public OssUserBean getUser() {
        return user;
    }

    public void setUser(OssUserBean user) {
        this.user = user;
    }
}
