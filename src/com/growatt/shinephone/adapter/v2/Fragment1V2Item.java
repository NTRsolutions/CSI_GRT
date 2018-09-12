package com.growatt.shinephone.adapter.v2;

/**
 * Created：2017/11/30 on 10:33
 * Author:gaideng on dg
 * Description:
 */

public class Fragment1V2Item {
    public static final int DEVICE_INV = 0;//逆变器
    public static final int DEVICE_STORAGE_SPF2K = 1;//储能机SPF2K
    public static final int DEVICE_STORAGE_SPF3K = 2;//储能机SPF3K
    public static final int DEVICE_STORAGE_SPF5K = 3;//储能机SPF5K
    public static final int DEVICE_MIX = 4;//MIX
    public static final int DEVICE_MAX = 5;//MAX:逆变器
    public static final int DEVICE_JLINV = 6;//JLINV逆变器
    /*添加条目类型修改最大最小值*/
    //侧滑的类型最大最小值
    public static final int DEVICE_TYPE_MIN = DEVICE_INV;
    public static final int DEVICE_TYPE_MAX= DEVICE_JLINV;
}
