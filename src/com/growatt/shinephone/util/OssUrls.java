package com.growatt.shinephone.util;

/**
 * Created by dg on 2017/5/31.
 */

public class OssUrls {
    //oss搜索以及数据操作访问的服务器地址
    public static String ossCRUDUrl = "server-cn.growatt.com";//默认为中国服务器:不需要http
    //oss搜索以及数据操作访问的服务器hostName
    public static String ossCRUDUrlHostName = "server-cn.growatt.com";//默认为中国服务器


    public static OssUrls instance;
    public OssUrls(){}
    public static OssUrls getInstance() {
        if (instance == null) {
            instance = new OssUrls();
        }
        return instance;
    }

    //总接口
//    public static String ossUrl1 = "http://oss1.growatt.com";
//    public static  String ossUrl1 = "http://192.168.3.32:8081/ShineOSS";
    public static  String ossUrl1 = "http://oss1.growatt.com";
//    public static  String ossUrl1 = "http://oss1.growatt.com:8080/ShineOSS";
//    public static  String ossUrl1 = "http://192.168.3.214/ShineOSS";
//    public static  String ossUrl1 = "http://192.168.3.35:8081/ShineOSS";
//    public static  String ossUrl1 = "http://192.168.3.117:8080/ShineOSS";
//    public static String ossUrl1 = ossServerUrl;
    //登陆接口
//    public static String ossLogin = ossUrl1 + "/api/v1/login/userLogin";
    //第二版登录
    public static String ossLogin = ossUrl1 + "/api/v2/login";
    public static String getOssLoginServer(){
        return ossUrl1 + "/api/v2/login";
    }
    //重新登陆接口
    public static String ossLoginReset = ossUrl1 + "/api/v1/login/userResetLogin";
    //根据手机号发送验证码并获取验证码
    public  static String postOssSmsVerification(){
        return  Constant.ossUrl+"/api/v1/phone/send/validate";
    }
    //搜索用户和电站信息接口
    public  static String postOssSearchAll(){
        return Constant.ossUrl+"/api/v1/search/all";
    }
    //搜索用户下电站列表
    public  static String postOssSearchUser_PlantList(){
        return Constant.ossUrl+"/api/v1/search/user_plant_list";
    }
    //搜索电站下设备列表
    public  static String postOssSearchPlant_DeviceList(){
        return Constant.ossUrl+"/api/v1/device/list";
    }
    //搜索单个设备接口
    public  static String postOssSearchDevice(){
        return Constant.ossUrl+"/api/v1/device/info";
    }
    //采集器设置接口
    public  static String postOssDatalogSet(){
        return Constant.ossUrl+"/api/v1/deviceSet/set/datalog";
    }
    //逆变器设置接口
//    public static String postOssInvSet = ossUrl+"/api/v1/deviceSet/set/inverter";
    public  static String postOssInvSet(){
        return Constant.ossUrl+"/api/v1/deviceSet/set/inverter";
    }
    //储能机设置接口
//    public static String postOssStorageSet = ossUrl+"/api/v1/deviceSet/set/storage";
    public  static String postOssStorageSet(){
        return Constant.ossUrl+"/api/v1/deviceSet/set/storage";
    }
    //设备编辑接口
//    public static String postOssDeviceEdit = ossUrl+"/api/v1/device/update";
    public  static String postOssDeviceEdit(){
        return Constant.ossUrl+"/api/v1/device/update";
    }
    //设备删除接口
//    public static String postOssDeviceDelete = ossUrl+"/api/v1/device/delete";
    public  static String postOssDeviceDelete(){
        return Constant.ossUrl+"/api/v1/device/delete";
    }
    //根据用户名修改用户信息接口
//    public static String postOssUserInfoEdit = ossUrl+"/api/v1/user/update/info";
    public  static String postOssUserInfoEdit(){
        return Constant.ossUrl+"/api/v1/user/update/info";
    }
    //RFStick配对接口
//    public static String postOssRFStick = ossUrl+"/api/v1/deviceSet/set/pairRFStick";
    public  static String postOssRFStick(){
        return Constant.ossUrl+"/api/v1/deviceSet/set/pairRFStick";
    }
    /*
    集成商接口
    */
    //获取集成商下代理商列表
//    public static String postOssJKAgentList = ossUrl+"/api/v1/customer/agent_list";
    public  static String postOssJKAgentList(){
        return Constant.ossUrl+"/api/v2/customer/agent_list";
    }
    //根据条件搜索集成商下面的设备数量信息
//    public static String postOssJKDvice_num = ossUrl+"/api/v1/customer/device_num";
    public  static String postOssJKDvice_num(){
        return Constant.ossUrl+"/api/v2/customer/device_num";
    }
    //根据条件搜索集成商下面的设备列表信息
//    public static String postOssJKDvice_list = ossUrl+"/api/v1/customer/device_list";
    public  static String postOssJKDvice_list(){
        return Constant.ossUrl+"/api/v2/customer/device_list";
    }
    //集成商获取单个设备详情接口
//    public static String postOssJKDvice_info = ossUrl+"/api/v1/customer/device_info";
    public  static String postOssJKDvice_info(){
        return Constant.ossUrl+"/api/v2/customer/device_info";
    }
 /*
    工单接口
    */
 //根据条件搜索客服问题列表接口
// public static String postOssGD_questionList = ossUrl+"/api/v1/serviceQuestion/question/list";
    public  static String postOssGD_questionList(){
        return Constant.ossUrl+"/api/v1/serviceQuestion/question/list";
    }
 //根据条件获取客服问题详情
// public static String postOssGD_questionDetial = ossUrl+"/api/v1/serviceQuestion/question/detail_info";
    public  static String postOssGD_questionDetial(){
        return Constant.ossUrl+"/api/v1/serviceQuestion/question/detail_info";
    }
 //客服回复问题
// public static String postOssGD_questionReply = ossUrl+"/api/v1/serviceQuestion/question/reply";
    public  static String postOssGD_questionReply(){
        return Constant.ossUrl+"/api/v1/serviceQuestion/question/reply";
    }
    //根据条件搜索工单派单列表接口
//    public static String postOssGD_gdList = ossUrl+"/api/v1/workOrder/work/list";
    public  static String postOssGD_gdList(){
        return Constant.ossUrl+"/api/v1/workOrder/work/list";
    }
    //根据条件搜索工单派单详情
//    public static String postOssGD_gdDetical = ossUrl+"/api/v1/workOrder/work/detail_info";
    public  static String postOssGD_gdDetical(){
        return Constant.ossUrl+"/api/v1/workOrder/work/detail_info";
    }
    //根据状态完善工单派单详情
//    public static String postOssGD_Perfect = ossUrl+"/api/v1/workOrder/work/perfect_info";
    public  static String postOssGD_Perfect(){
        return Constant.ossUrl+"/api/v1/workOrder/work/perfect_info";
    }
    //OSS的工单派单图片获取
    public static String oss_photo_url = "http://cdn.growatt.com/onlineservice/";
    //客服用户获取概览数据
//    public static String postOssKF_MainData = ossUrl+"/api/v1/serviceQuestion/question/service_overview_data";
    public  static String postOssKF_MainData(){
        return Constant.ossUrl+"/api/v1/serviceQuestion/question/service_overview_data";
    }
    //集成商账户获取概览数据
//    public static String postOssJK_MainData = ossUrl+"/api/v1/customer/customer_overview_data";
    public  static String postOssJK_MainData(){
        return Constant.ossUrl+"/api/v2/customer/customer_overview_data";
    }
    //设置储能机SPF5000参数
//    public static String postOssJK_MainData = ossUrl+"/api/v1/customer/customer_overview_data";
    public  static String postSetOssStorageSPF5k(){
        return Constant.ossUrl+"/api/v1/deviceSet/set/storageSPF5000";
    }
    /**
     * oss第二版
     */
    //客户添加问题
    public  static String addQuestionCus(){
        return ossUrl1+"/api/v2/question/add";
    }
    //客户问题列表
    public  static String questionListCus(){
        return ossUrl1+"/api/v2/question/list";
    }
    //客户问题详情
    public  static String questionDetailCus(){
        return ossUrl1+"/api/v2/question/detail";
    }
    //客户回复问题
    public  static String questionReplyCus(){
        return ossUrl1+"/api/v2/question/reply";
    }
    //客户问题删除
    public  static String questionDeleteCus(){
        return ossUrl1+"/api/v2/question/delete";
    }
    //客户问题已解决
    public  static String questionSolveCus(){
        return ossUrl1+"/api/v2/question/solve";
    }
    //客服问题列表接口
    public  static String questionListOss(){
        return Constant.ossUrl+"/api/v2/question/worker/list";
    }
    //客服问题详情
    public  static String questionDeticalOss(){
        return Constant.ossUrl+"/api/v2/question/worker/detail";
    }
    //客服回复问题
    public  static String questionReplyOssKF(){
        return Constant.ossUrl+"/api/v2/question/worker/reply";
    }
    //工单列表
    public  static String orderListOssGD(){
        return Constant.ossUrl+"/api/v2/order/list";
    }
    //完善工单
    public  static String orderPerfectOssGD(){
        return Constant.ossUrl+"/api/v2/order/perfect";
    }
    //工单详情
    public  static String orderDeticalOssGD(){
        return Constant.ossUrl+"/api/v2/order/detail";
    }
    //客服主界面概览数据
    public  static String questionOverviewOss(){
        return Constant.ossUrl+"/api/v2/order/overview";
    }
    //获取安装商下电站列表
    public  static String postOssAZPlantList(){
        return Constant.ossUrl+"/api/v2/customer/plant_list";
    }
}
