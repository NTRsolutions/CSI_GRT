package com.growatt.shinephone.bean.flow;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dg on 2017/11/8.
 * 流量续费主界面实体
 */

public class FlowMainBean implements Parcelable{
    //是否被选中
    private boolean isCheck;
    //采集器序列号
    private String datalogSn;
    //到期时间
    private String productDate;
    //流量价格
    private double flowPrice = 20;
    /*
    是否能续费
1 带表可以续费
0不能续费
     */
    private int simRenewFee;
    public FlowMainBean() {
    }

    protected FlowMainBean(Parcel in) {
        isCheck = in.readByte() != 0;
        datalogSn = in.readString();
        productDate = in.readString();
        flowPrice = in.readDouble();
        simRenewFee = in.readInt();
    }

    public static final Creator<FlowMainBean> CREATOR = new Creator<FlowMainBean>() {
        @Override
        public FlowMainBean createFromParcel(Parcel in) {
            return new FlowMainBean(in);
        }

        @Override
        public FlowMainBean[] newArray(int size) {
            return new FlowMainBean[size];
        }
    };

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getDatalogSn() {
        return datalogSn;
    }

    public void setDatalogSn(String datalogSn) {
        this.datalogSn = datalogSn;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public double getFlowPrice() {
        return flowPrice;
    }

    public void setFlowPrice(double flowPrice) {
        this.flowPrice = flowPrice;
    }

    public int getSimRenewFee() {
        return simRenewFee;
    }

    public void setSimRenewFee(int simRenewFee) {
        this.simRenewFee = simRenewFee;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isCheck ? 1 : 0));
        dest.writeString(datalogSn);
        dest.writeString(productDate);
        dest.writeDouble(flowPrice);
        dest.writeInt(simRenewFee);
    }
}
