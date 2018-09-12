package com.growatt.shinephone.util.flow;

/**
 * Created by dg on 2017/11/9.
 */

public class ConstantFlow {
    //跳转到本账号设备请求码
    public static final int MY_ACCOUNT_FLOW = 100;
    //跳转到其他账号设备请求码
    public static final int OTHER_ACCOUNT_FLOW = 101;
    //跳转到发票界面
    public static final int PAY_TO_INVOICE_FLOW = 102;

    //界面跳转标题字段
    public static final String TITLE_FLOW = "title";
    //界面跳转后返回的数据字段
    public static final String DATA_FLOW = "data";
    //界面跳转后金额字段
    public static final String AMOUNT_FLOW = "amount";
    //界面跳转后续费年限
    public static final String YEAR_FLOW = "year_flow";
    //界面跳转后gprs序列号
    public static final String SNS_FLOW = "sns_flow";
    //界面跳转后是否开发票字段
    public static final String INVOICE_FLOW = "invoice";
    //界面跳转后发票实体字段
    public static final String INVOICE_BEAN_FLOW = "invoice_bean";
}
