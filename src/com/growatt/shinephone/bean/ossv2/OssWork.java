package com.growatt.shinephone.bean.ossv2;

/**
 * Created：2018/1/3 on 10:04
 * Author:gaideng on dg
 * Description:
 */

public class OssWork {

    /**
     * 编号
     */
    private int id;

    /**
     * 工单号
     */
    private String workOrder;

    /**
     * 标题
     */
    private String title;

    /**
     * 状态：1已分配 ,2待接收,3服务中,4待回访,5已完成
     */
    private int status;

    /**
     * 问题编号
     */
    private int issueId;

    /**
     * 服务类型
     */
    private int serviceType;

    /**
     * 区域
     */
    private int groupId;

    /**
     * 创建人
     */
    private String jobId;

    /**
     * 客服编号（可以是多个客服）
     */
    private String workId;

    /**
     * 回访人
     */
    private String returnVisit;

    /**
     * 申请时间
     */
    private String applicationTime;

    /**
     * 预约时间
     */
    private String appointment;

    /**
     * 要求到达时间
     */
    private String doorTime;

    /**
     * 工单（项目）完成时间
     */
    private String completeTime;

    /**
     * 最后跟新时间
     */
    private String lastUpdateTime;

    /**
     * 客服接收时间
     */
    private String receiveTime;

    /**
     * 客户(公司)名称
     */
    private String customerName;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 现场服务记录附件
     */
    private String fieldService;

    /**
     * 其它服务记录
     */
    private String otherServices;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 服务评分
     */
    private int serviceScore;

    /**
     * 评分建议
     */
    private String recommended;

    /**
     * 设备类型(1.逆变器、2.储能机、3.采集器)
     */
    private int deviceType;

    /**
     * 设备序列号
     */
    private String deviseSerialNumber;

    /**
     * 完成状态(1已完成，2待观察)
     */
    private int completeState;

    /**
     * 分销商、安装商编码
     */
    private String code;

    /**
     * 工单来源(0、电话系统转化，1、在线客服转化，2、问题转化，3、客服主管创建)
     */
    private int ticketSource;

    /**
     * 服务报告
     */
    private String serviceReport;

    /**
     * 完成情况
     */
    private String completion;

    /**
     * 回访时间
     */
    private String visitTime;

    /**
     * 满意理由
     */
    private String satisfactoryReason;

    /**
     * 不满意原因
     */
    private String theReason;

    /**
     * 备注
     */
    private String returnNote;

    /**
     * 基本信息是否有误
     */
    private int basicInformation;

    /**
     * 满意度打分
     */
    private int satisfaction;

    /**
     * 是否按约定时间到达现场
     */
    private int agreedTime;

    /**
     * 是否签字确认
     */
    private int signToConfirm;

    /**
     * 是否接听
     */
    private int phoneIswitched;

    /**
     * 省份
     */
    private String province;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    //DTO
    /**
     * 用户名
     * @Transient
     */
    private String userName;

    /**
     * 区域名
     * @Transient
     */
    private String groupName;

    /**
     * 基本信息是否有误
     */
    private String isTheInformation;

    /**
     * 满意度打分
     */
    private String rate;

    /**
     * 是否签字确认
     */
    private String signature;

    /**
     * 设备类型
     */
    private String equipmenType;

}
