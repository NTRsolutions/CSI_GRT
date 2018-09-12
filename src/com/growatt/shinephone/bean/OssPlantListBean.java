package com.growatt.shinephone.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dg on 2017/5/20.
 */

public class OssPlantListBean implements Parcelable{
    //当搜索为用户名时：name1 = 用户名；name2 = 创建时间；
    //当搜索为手机号时：name1 = 手机号；name2 = 所属用户名；
    //当搜索为邮箱时：name1 = 邮箱；name2 = 所属用户名；
    //当搜索为电站名时：name1 = 电站名；name2 = 所属用户名；
    //searchType:搜索类型
    //id:如果是电站：代表电站Id;用户则代表用户Id
    private String name1;
    private String name2;
    private String value1;
    private String value2;
    private int searchType;
    private int id;

    public OssPlantListBean() {
    }

    protected OssPlantListBean(Parcel in) {
        name1 = in.readString();
        name2 = in.readString();
        value1 = in.readString();
        value2 = in.readString();
        searchType = in.readInt();
        id = in.readInt();
    }

    @Override
    public String toString() {
        return "OssPlantListBean{" +
                "name1='" + name1 + '\'' +
                ", name2='" + name2 + '\'' +
                ", value1='" + value1 + '\'' +
                ", value2='" + value2 + '\'' +
                ", searchType=" + searchType +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name1);
        dest.writeString(name2);
        dest.writeString(value1);
        dest.writeString(value2);
        dest.writeInt(searchType);
        dest.writeInt(id);
    }
    public static final Creator<OssPlantListBean> CREATOR = new Creator<OssPlantListBean>() {
        @Override
        public OssPlantListBean createFromParcel(Parcel in) {
            return new OssPlantListBean(in);
        }

        @Override
        public OssPlantListBean[] newArray(int size) {
            return new OssPlantListBean[size];
        }
    };
}
