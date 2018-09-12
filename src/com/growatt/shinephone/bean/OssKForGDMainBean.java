package com.growatt.shinephone.bean;

/**
 * Created by dg on 2017/7/3.
 * oss客服主入口数据
 */

public class OssKForGDMainBean {
    private int waitFollowNum;
    private int notProcessedNum;
    private int waitReceiveNum;
    private int inServiceNum;
    private QuestionReply questionReply;
    private WorkOrder workOrder;

    public QuestionReply getQuestionReply() {
        return questionReply;
    }

    public void setQuestionReply(QuestionReply questionReply) {
        this.questionReply = questionReply;
    }

    public WorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(WorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public int getWaitFollowNum() {
        return waitFollowNum;
    }

    public void setWaitFollowNum(int waitFollowNum) {
        this.waitFollowNum = waitFollowNum;
    }

    public int getNotProcessedNum() {
        return notProcessedNum;
    }

    public void setNotProcessedNum(int notProcessedNum) {
        this.notProcessedNum = notProcessedNum;
    }

    public int getWaitReceiveNum() {
        return waitReceiveNum;
    }

    public void setWaitReceiveNum(int waitReceiveNum) {
        this.waitReceiveNum = waitReceiveNum;
    }

    public int getInServiceNum() {
        return inServiceNum;
    }

    public void setInServiceNum(int inServiceNum) {
        this.inServiceNum = inServiceNum;
    }



    public static class QuestionReply{
        private int id;
        private int questionId;
        private int workerId;
        private String message;
        private String time;
        private String attachment;
        private int isAdmin;//
        private String accountName;
        private String jobId;
        private int userId;

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

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }

        public int getIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(int isAdmin) {
            this.isAdmin = isAdmin;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getJobId() {
            return jobId;
        }

        public void setJobId(String jobId) {
            this.jobId = jobId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
    public static class WorkOrder{
            private int id;
        private String workOrder;
            private String title;
        private int issueId;
        private int serviceType;
            private int groupId;
        private String applicationTime;
        private String appointment;
            private int status;
        private String doorTime;
        private String completeTime;
            private int workerId;
        private String customerName;
        private String contact;
        private String location;
        private String address;
        private String fieldService;
        private String otherServices;
        private String remarks;
        private int serviceScore;
        private String recommended;
        private String groupName;
        private int deviceType;
        private String deviseSerialNumber;
        private int completeState;
        private String lastUpdateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWorkOrder() {
            return workOrder;
        }

        public void setWorkOrder(String workOrder) {
            this.workOrder = workOrder;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIssueId() {
            return issueId;
        }

        public void setIssueId(int issueId) {
            this.issueId = issueId;
        }

        public int getServiceType() {
            return serviceType;
        }

        public void setServiceType(int serviceType) {
            this.serviceType = serviceType;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getApplicationTime() {
            return applicationTime;
        }

        public void setApplicationTime(String applicationTime) {
            this.applicationTime = applicationTime;
        }

        public String getAppointment() {
            return appointment;
        }

        public void setAppointment(String appointment) {
            this.appointment = appointment;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDoorTime() {
            return doorTime;
        }

        public void setDoorTime(String doorTime) {
            this.doorTime = doorTime;
        }

        public String getCompleteTime() {
            return completeTime;
        }

        public void setCompleteTime(String completeTime) {
            this.completeTime = completeTime;
        }

        public int getWorkerId() {
            return workerId;
        }

        public void setWorkerId(int workerId) {
            this.workerId = workerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getFieldService() {
            return fieldService;
        }

        public void setFieldService(String fieldService) {
            this.fieldService = fieldService;
        }

        public String getOtherServices() {
            return otherServices;
        }

        public void setOtherServices(String otherServices) {
            this.otherServices = otherServices;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getServiceScore() {
            return serviceScore;
        }

        public void setServiceScore(int serviceScore) {
            this.serviceScore = serviceScore;
        }

        public String getRecommended() {
            return recommended;
        }

        public void setRecommended(String recommended) {
            this.recommended = recommended;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(int deviceType) {
            this.deviceType = deviceType;
        }

        public String getDeviseSerialNumber() {
            return deviseSerialNumber;
        }

        public void setDeviseSerialNumber(String deviseSerialNumber) {
            this.deviseSerialNumber = deviseSerialNumber;
        }

        public int getCompleteState() {
            return completeState;
        }

        public void setCompleteState(int completeState) {
            this.completeState = completeState;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }
    }
}
