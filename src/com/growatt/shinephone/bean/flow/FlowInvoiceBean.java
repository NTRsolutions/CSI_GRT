package com.growatt.shinephone.bean.flow;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dg on 2017/11/10.
 * 发票信息实体
 */

public class FlowInvoiceBean implements Parcelable{
    private boolean isInvoice;//是否开票
    private String inCompany;//发票抬头
    private String inTaxId;//企业税号
    private String inPhone;//联系电话
    private String inAddress;//详细地址
    private String inNote;//备注信息

    public FlowInvoiceBean() {
    }

    protected FlowInvoiceBean(Parcel in) {
        isInvoice = in.readByte() != 0;
        inCompany = in.readString();
        inTaxId = in.readString();
        inPhone = in.readString();
        inAddress = in.readString();
        inNote = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isInvoice ? 1 : 0));
        dest.writeString(inCompany);
        dest.writeString(inTaxId);
        dest.writeString(inPhone);
        dest.writeString(inAddress);
        dest.writeString(inNote);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FlowInvoiceBean> CREATOR = new Creator<FlowInvoiceBean>() {
        @Override
        public FlowInvoiceBean createFromParcel(Parcel in) {
            return new FlowInvoiceBean(in);
        }

        @Override
        public FlowInvoiceBean[] newArray(int size) {
            return new FlowInvoiceBean[size];
        }
    };

    public boolean isInvoice() {
        return isInvoice;
    }

    public void setInvoice(boolean invoice) {
        isInvoice = invoice;
    }

    public String getInCompany() {
        return inCompany;
    }

    public void setInCompany(String inCompany) {
        this.inCompany = inCompany;
    }

    public String getInTaxId() {
        return inTaxId;
    }

    public void setInTaxId(String inTaxId) {
        this.inTaxId = inTaxId;
    }

    public String getInPhone() {
        return inPhone;
    }

    public void setInPhone(String inPhone) {
        this.inPhone = inPhone;
    }

    public String getInAddress() {
        return inAddress;
    }

    public void setInAddress(String inAddress) {
        this.inAddress = inAddress;
    }

    public String getInNote() {
        return inNote;
    }

    public void setInNote(String inNote) {
        this.inNote = inNote;
    }
}
