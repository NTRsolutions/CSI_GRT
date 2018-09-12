package com.growatt.shinephone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dg on 2017/6/23.
 * Oss工单问题列表实体
 */

public class OssGDQuestionListBean implements Parcelable{
    //客服实体
    private int type = 0;//1代表客服问题;2:代表工单问题
    private int id;
    private int userId;
    private String questionType;
    private String title;
    private String questionDevice;
    private int groupId;
    private String content;
    private String remark;
    private String timezone;
    private int status;
    private String createrTime;
    private String lastTime;
    private int workerId;
    private String country;
    private String attachment;
    private String serverUrl;
    private String accountName;
    private String name;
    private String jobId;
    //工单实体
//    private int id;
    private String workOrder;
//    private String title;
    private int issueId;
    private int serviceType;
//    private int groupId;
    private String applicationTime;
    private String appointment;
//    private int status;
    private String doorTime;
    private String completeTime;
//    private int workerId;
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
    private String receiveTime;

    public OssGDQuestionListBean() {
    }

    protected OssGDQuestionListBean(Parcel in) {
        type = in.readInt();
        id = in.readInt();
        userId = in.readInt();
        questionType = in.readString();
        title = in.readString();
        questionDevice = in.readString();
        groupId = in.readInt();
        content = in.readString();
        remark = in.readString();
        timezone = in.readString();
        status = in.readInt();
        createrTime = in.readString();
        lastTime = in.readString();
        workerId = in.readInt();
        country = in.readString();
        attachment = in.readString();
        serverUrl = in.readString();
        accountName = in.readString();
        name = in.readString();
        jobId = in.readString();
        workOrder = in.readString();
        issueId = in.readInt();
        serviceType = in.readInt();
        applicationTime = in.readString();
        appointment = in.readString();
        doorTime = in.readString();
        completeTime = in.readString();
        customerName = in.readString();
        contact = in.readString();
        location = in.readString();
        address = in.readString();
        fieldService = in.readString();
        otherServices = in.readString();
        remarks = in.readString();
        serviceScore = in.readInt();
        recommended = in.readString();
        groupName = in.readString();
        deviceType = in.readInt();
        deviseSerialNumber = in.readString();
        completeState = in.readInt();
        lastUpdateTime = in.readString();
        receiveTime = in.readString();
    }

    public static final Creator<OssGDQuestionListBean> CREATOR = new Creator<OssGDQuestionListBean>() {
        @Override
        public OssGDQuestionListBean createFromParcel(Parcel in) {
            return new OssGDQuestionListBean(in);
        }

        @Override
        public OssGDQuestionListBean[] newArray(int size) {
            return new OssGDQuestionListBean[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
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

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(questionType);
        dest.writeString(title);
        dest.writeString(questionDevice);
        dest.writeInt(groupId);
        dest.writeString(content);
        dest.writeString(remark);
        dest.writeString(timezone);
        dest.writeInt(status);
        dest.writeString(createrTime);
        dest.writeString(lastTime);
        dest.writeInt(workerId);
        dest.writeString(country);
        dest.writeString(attachment);
        dest.writeString(serverUrl);
        dest.writeString(accountName);
        dest.writeString(name);
        dest.writeString(jobId);
        dest.writeString(workOrder);
        dest.writeInt(issueId);
        dest.writeInt(serviceType);
        dest.writeString(applicationTime);
        dest.writeString(appointment);
        dest.writeString(doorTime);
        dest.writeString(completeTime);
        dest.writeString(customerName);
        dest.writeString(contact);
        dest.writeString(location);
        dest.writeString(address);
        dest.writeString(fieldService);
        dest.writeString(otherServices);
        dest.writeString(remarks);
        dest.writeInt(serviceScore);
        dest.writeString(recommended);
        dest.writeString(groupName);
        dest.writeInt(deviceType);
        dest.writeString(deviseSerialNumber);
        dest.writeInt(completeState);
        dest.writeString(lastUpdateTime);
        dest.writeString(receiveTime);
    }
}
