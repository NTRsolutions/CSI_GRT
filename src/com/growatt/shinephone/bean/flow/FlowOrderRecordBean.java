package com.growatt.shinephone.bean.flow;

/**
 * Created by dg on 2017/11/17.
 * 流量支付记录实体
 */

public class FlowOrderRecordBean {
    private int id;   //唯一标识序号
    private int serverId;  //订单所属用户的服务器ID
    private String serverUrl;   //订单所属用户的服务器URL

    // 以下参数是APP提交订单的参数
    private String username;// (用户名)
    private String growattOrderId;// growatt订单号
    private String datalogSn;// (采集器序列号)
    private int year;// (续费年限)
    private double money;// (续费金额)//
    private int haveInvoice;// (是否需要发票0：不需要，1：需要)
    private String invoiceName;// (发票抬头)
    private String invoiceNum;// (发票税号)
    private String invoicePhone;// (发票邮寄电话)
    private String invoiceAddr;// (发票邮寄地址)
    private String remark;// (备注)
    private String app_trade_status;// (APP发送订单发送状态)

    //以下参数是从回调方法里填写进去
    private String subject;// (订单标题)//
    private String body;// (商品描述)//
    private String out_trade_no;// (支付宝交易号)//
    private String total_amount;// (订单金额)//
    private String trade_no;// (支付宝订单号)//
    private String invoice_amount;//开票金额
    private String trade_status;// (交易状态: WAIT_BUYER_PAY(交易创建，等待买家付款)/
    // TRADE_CLOSED(未付款交易超时关闭，或支付完成后全额退款)/
    // TRADE_SUCCESS(交易支付成功)/
    // TRADE_FINISHED(交易结束，不可退款))、
    private String gmt_create;// (交易创建时间)//
    private String buyer_pay_amount;// (付款金额)
    private String receipt_amount;// (实收金额)
    private String buyer_logon_id;// (买家支付宝账号)
    private String gmt_payment;    //交易付款时间
    private String gmt_close;      //交易结束时间
    private String buyer_id;       //买家支付宝用户号 ,买家支付宝账号对应的支付宝唯一用户号。以2088开头的纯16位数字
    private String seller_id;           //卖家支付宝用户号
    private String seller_email;         //卖家支付宝账号

    // 以下参数是与供应商相关参数
    private String iccid;    //SIM卡iccid
    private String packageId;       //续费套餐ID
    private int order_status;       //续费订单供应商状态
    private String supplier_order_no;       //供应商订单编号

    //订单总状态
    private int status;
    //部分续费失败参数
    private String failureIccid;
    private int failureNum;
    private String failureDatalog;
    public FlowOrderRecordBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGrowattOrderId() {
        return growattOrderId;
    }

    public void setGrowattOrderId(String growattOrderId) {
        this.growattOrderId = growattOrderId;
    }

    public String getDatalogSn() {
        return datalogSn;
    }

    public void setDatalogSn(String datalogSn) {
        this.datalogSn = datalogSn;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getHaveInvoice() {
        return haveInvoice;
    }

    public void setHaveInvoice(int haveInvoice) {
        this.haveInvoice = haveInvoice;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getInvoicePhone() {
        return invoicePhone;
    }

    public void setInvoicePhone(String invoicePhone) {
        this.invoicePhone = invoicePhone;
    }

    public String getInvoiceAddr() {
        return invoiceAddr;
    }

    public void setInvoiceAddr(String invoiceAddr) {
        this.invoiceAddr = invoiceAddr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApp_trade_status() {
        return app_trade_status;
    }

    public void setApp_trade_status(String app_trade_status) {
        this.app_trade_status = app_trade_status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getInvoice_amount() {
        return invoice_amount;
    }

    public void setInvoice_amount(String invoice_amount) {
        this.invoice_amount = invoice_amount;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(String gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getBuyer_pay_amount() {
        return buyer_pay_amount;
    }

    public void setBuyer_pay_amount(String buyer_pay_amount) {
        this.buyer_pay_amount = buyer_pay_amount;
    }

    public String getReceipt_amount() {
        return receipt_amount;
    }

    public void setReceipt_amount(String receipt_amount) {
        this.receipt_amount = receipt_amount;
    }

    public String getBuyer_logon_id() {
        return buyer_logon_id;
    }

    public void setBuyer_logon_id(String buyer_logon_id) {
        this.buyer_logon_id = buyer_logon_id;
    }

    public String getGmt_payment() {
        return gmt_payment;
    }

    public void setGmt_payment(String gmt_payment) {
        this.gmt_payment = gmt_payment;
    }

    public String getGmt_close() {
        return gmt_close;
    }

    public void setGmt_close(String gmt_close) {
        this.gmt_close = gmt_close;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getSupplier_order_no() {
        return supplier_order_no;
    }

    public void setSupplier_order_no(String supplier_order_no) {
        this.supplier_order_no = supplier_order_no;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFailureIccid() {
        return failureIccid;
    }

    public void setFailureIccid(String failureIccid) {
        this.failureIccid = failureIccid;
    }

    public int getFailureNum() {
        return failureNum;
    }

    public void setFailureNum(int failureNum) {
        this.failureNum = failureNum;
    }

    public String getFailureDatalog() {
        return failureDatalog;
    }

    public void setFailureDatalog(String failureDatalog) {
        this.failureDatalog = failureDatalog;
    }

    @Override
    public String toString() {
        return "FlowOrderRecordBean{" +
                "id=" + id +
                ", serverId=" + serverId +
                ", serverUrl='" + serverUrl + '\'' +
                ", username='" + username + '\'' +
                ", growattOrderId='" + growattOrderId + '\'' +
                ", datalogSn='" + datalogSn + '\'' +
                ", year=" + year +
                ", money=" + money +
                ", haveInvoice=" + haveInvoice +
                ", invoiceName='" + invoiceName + '\'' +
                ", invoiceNum='" + invoiceNum + '\'' +
                ", invoicePhone='" + invoicePhone + '\'' +
                ", invoiceAddr='" + invoiceAddr + '\'' +
                ", remark='" + remark + '\'' +
                ", app_trade_status='" + app_trade_status + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", out_trade_no='" + out_trade_no + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", trade_no='" + trade_no + '\'' +
                ", invoice_amount='" + invoice_amount + '\'' +
                ", trade_status='" + trade_status + '\'' +
                ", gmt_create='" + gmt_create + '\'' +
                ", buyer_pay_amount='" + buyer_pay_amount + '\'' +
                ", receipt_amount='" + receipt_amount + '\'' +
                ", buyer_logon_id='" + buyer_logon_id + '\'' +
                ", gmt_payment='" + gmt_payment + '\'' +
                ", gmt_close='" + gmt_close + '\'' +
                ", buyer_id='" + buyer_id + '\'' +
                ", seller_id='" + seller_id + '\'' +
                ", seller_email='" + seller_email + '\'' +
                ", iccid='" + iccid + '\'' +
                ", packageId='" + packageId + '\'' +
                ", order_status=" + order_status +
                ", supplier_order_no='" + supplier_order_no + '\'' +
                ", status=" + status +
                '}';
    }
}
