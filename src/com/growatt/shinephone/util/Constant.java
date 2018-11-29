package com.growatt.shinephone.util;

import android.content.Context;

public class Constant {
    //oss动态服务器地址
//    public static  String ossUrl = "http://192.168.3.32:8081/ShineOSS";
    public static String ossUrl = "http://oss1.growatt.com";
    //	public static  String ossUrl = "http://oss1.growatt.com:8080/ShineOSS";
    //热更新版本号
    public static int TinkerCode = 0;

    //        public static  String ossUrl = "http://192.168.3.214/ShineOSS";
//    public static  String ossUrl = "http://192.168.3.117:8080/ShineOSS";
//    public static  String ossUrl = "http://test.growatt.com";
//    public static  String ossUrl = "http://odm.growatt.com";
//    public static  String ossUrl = "http://oss1.growatt.com";
//	public static  String ossUrl = "http://server.growatt.com";
//	public static  String ossUrl = "http://120.76.153.241:8080/ShineServer_2016";
    public static void setOssUrl(String url) {
        ossUrl = url;
    }

    //自动登录常量
    public static String AUTO_LOGIN = "com.growatt.auto_login";//0:代表不自动登录；1：代表自动登录
    public static String AUTO_LOGIN_TYPE = "com.growatt.auto_login_type";//0:oss用户；1：server用户
    //����ˢ��fragment1�Ĺ㲥������action
    public static String Frag_Receiver = "com.action.fragment1.refresh";
    //����ˢ��MyQuestionfragment�Ĺ㲥������action
    public static String MyQuestionfragment_Receiver = "com.action.MyQuestionfragment.refresh";
    //�����µĵط�
    public static int AboutActivity_Updata = 1;
    public static int LoginActivity_Updata = 2;

    //activity request code
    public static int VideoCenterActivity_search = 201;

    public static int CacheVideoActToPlayerAct = 100;//�ӻ����б���ת�����Ž���
    public static int OtherToPlayerAct = 101;
    //刷新fragment2广播
    public static String Frag2_Receiver = "com.action.fragment2.refresh";
    //刷新EnergyFragment广播
    public static String Energy_Receiver = "com.action.energy.refresh";
    //设备页Demo设备类型
    public static String Device_Type = "type";//intent跳转类型
    public static int Device_Inv = 1;//逆变器
    public static int Device_Power = 2;//功率调节器
    public static int Device_Charge = 3;//充电桩
    public static int Device_storage = 4;//储能机
    //oss常量
    public static String OssUser_Phone;//oss用户电话
    public static String OssNewsTime = "0";//oss最新消息时间
    public final static String OssServer_cn = "cnName";//oss服务器中文字段
    public final static String OssServer_en = "enName";//oss服务器英文字段
    public final static String OssServer_url = "url";//oss服务器地址
    //集成商常量
    public final static String OssJK_State = "OssJK_State";//集成商设备状态
    //逆变器
    public final static int OssJK_State_all = 0;//在线
    public final static int OssJK_State_offline = 1;//离线
    public final static int OssJK_State_online = 2;//在线
    public final static int OssJK_State_err = 3;//故障
    public final static int OssJK_State_wait_inv = 4;//等待
    public final static int OssJK_State_charge_storage = 4;//充电
    public final static int OssJK_State_discharge_storage = 5;//放电

    public final static String OssJK_Dev_type = "OssJK_Dev_type";//集成商设备类型：0：逆变器；1：储能机
    public final static int OssJK_DeviceType_inv = 0;//逆变器类型
    public final static int OssJK_DeviceType_stor = 1;//储能机类型

    public final static String OssJK_Dev_status = "OssJK_Dev_status";//集成商接入状态：2：已接入设备；3：未接入设备
    public final static int OssJK_DeviceStatus_all = 0;//已接入设备
    public final static int OssJK_DeviceStatus_yes = 1;//已接入设备
    public final static int OssJK_DeviceStatus_no = 2;//未接入设备


    //代理商
    public final static String Agent_Company = "agentCompany";//代理商公司名称
    public final static String Agent_Code = "code";//代理商编码
    public final static String Agent_Name = "company";//代理商公司名

    //所有涉及到分页的页码以及list
    public final static String currentPage = "currentPage";//当前页码
    public final static String totalPage = "totalPage";//总页码
    public final static String jumpList = "list";//要跳转的list集合

    //界面跳转常量
    public final static String ossJumpToDeviceList = "OssDeviceListActivity";//跳转oss设备列表常量
    public final static int ossPlantToDeviceList = 100;//从电站列表跳转到设备列表；根据电站id获取数据
    public final static int ossSnToDeviceList = 101;//从sn或别名跳转到设备列表；根据sn或者别名获取数据
    //包名:用来识别 是否提示更新或是否显示更新
    public final static String google_package_name = "com.growatt.shinephones";
    public final static String shinephone_package_name = "com.growatt.shinephone";

    //数据库版本
    public static String getSqliteName(Context context) {
//		if (google_package_name.equals(context.getPackageName())){
//			return "sqldata4.db";
//		}else if ("com.smten.shinephone".equals(context.getPackageName())){
//			return "sqldata3.db";
//		}else{
//			return "sqldata2.db";
//		}
        return "Canadian.db";
    }

    //wifi配置识别
    public final static String WiFi_Type_ShineWIFI = "shinewifi";
    public final static String WiFi_Type_ShineWIFI_S = "shinewifi-s";
    public final static String WIFI_TYPE_CSIWIFI = "CSI";

    //Max控制密码常量：
    public static boolean isOss2Server = false;
}
