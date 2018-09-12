package com.growatt.shinephone.bean;

import java.util.List;

/**
 * Created by dg on 2017/6/7.
 * 工单系统回复问题详情bean
 */

public class OssGDReplyQuesBean {
    private int id;
    private int userID;
    private int status;
    private int workerId;
    private int groupId;
    private int isImport;
    private String questionType;
    private String title;
    private String questionDevice;
    private String content;
    private String attachment;
    private String createrTime;
    private String lastTime;
    private String name;
    private String country;
    private String groupName;
    private String workOrder;
//    private String bigCustomerSN;
//    private String serverUrl;
//    private String attachmentList;
    private String accountName;
//    private String phoneNum;
//    private String email;
//    private String isValiPhoneNum;
//    private String isValiEmail;
//    private String remark;
    private List<ServiceQuestionReplyBean>  serviceQuestionReplyBean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getIsImport() {
        return isImport;
    }

    public void setIsImport(int isImport) {
        this.isImport = isImport;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestionDevice() {
        return questionDevice;
    }

    public void setQuestionDevice(String questionDevice) {
        this.questionDevice = questionDevice;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCreaterTime() {
        return createrTime;
    }

    public void setCreaterTime(String createrTime) {
        this.createrTime = createrTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public List<ServiceQuestionReplyBean> getServiceQuestionReplyBean() {
        return serviceQuestionReplyBean;
    }

    public void setServiceQuestionReplyBean(List<ServiceQuestionReplyBean> serviceQuestionReplyBean) {
        this.serviceQuestionReplyBean = serviceQuestionReplyBean;
    }

    public static class ServiceQuestionReplyBean{
        private int id;
        private int questionId;
        private int workerId;
        private int isAdmin;//1:客服；2：客户
        private int userId;
        private String message;
        private String time;
        private String userName;
        private String attachment;
//        private String jobId;
//        private String attachmentList;

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQuestionId() {
            return questionId;
        }

        public void setQuestionId(int questionId) {
            this.questionId = questionId;
        }

        public int getWorkerId() {
            return workerId;
        }

        public void setWorkerId(int workerId) {
            this.workerId = workerId;
        }

        public int getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(int isAdmin) {
            this.isAdmin = isAdmin;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
