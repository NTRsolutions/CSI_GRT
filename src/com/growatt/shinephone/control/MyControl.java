package com.growatt.shinephone.control;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.growatt.shinephone.activity.InverterdpsActivity;
import com.growatt.shinephone.bean.mix.MixSetBean;
import com.growatt.shinephone.listener.OnAnimationEndLinster;
import com.growatt.shinephone.listener.OnCirclerDialogListener;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.listener.OnHandlerStrListener;
import com.growatt.shinephone.tool.GraphicalView;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.MyUtilsTotal;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.mix.MixUtil;

import org.achartengine.chart.XYChart;

import java.util.Date;
import java.util.List;

public class MyControl {
	/**
	 * 
     * 根据用户名密码进行登陆
	 * @param Context
	 * @param userName:用户名
	 * @param password:密码
	 */
	public static void autoLogin(Context Context,String userName, String password) {
		MyUtils.autoLogin(Context,userName,password);
	}
   /**
    * 正则验证电话
    * @param phone
    */
	public static boolean regexCheckPhone(String phone) {
		return MyUtils.regexCheckPhone(phone);
	}
	/**
	 * 正则验证邮箱
	 * @param email
	 */
	public static boolean regexCheckEmail(String email) {
		return MyUtils.regexCheckEmail(email);
	}
	/**
	 * 是否开启通知权限
	 * @param context
	 * @return
	 */
	public static boolean isNotificationEnabled(Context context){
		return MyUtils.isNotificationEnabled(context);
	}
	/**
	 * 获得屏幕宽度(像素)
	 * @param context
	 * @return px
	 */
	public static int getScreenWidth(Context context) {
		return MyUtils.getScreenWidth(context);
	}
   /**
    * 获得屏幕高度(像素)
    * @param context
    * @return px
    */
	public static int getScreenHeight(Context context) {
		return MyUtils.getScreenHeight(context);
	}
   /**
    * 获得屏幕显示指标
    * @param context
    * @return
    */
	public static DisplayMetrics getScreenDisplayMetrics(Context context) {
		return MyUtils.getScreenDisplayMetrics(context);
	}
  /**
   * 获得app版本号
   * @param context
   * @return
   */
	public static String getAppVersion(Context context) {
		return MyUtils.getAppVersion(context);
	}
	/**
	 * 获取日期格式:默认为"yyyy-MM-dd HH:mm:ss"和当前日期
	 * @return
	 */
	public static String getFormatDate(){
	        return MyUtils.getFormatDate("", null);
	    }
	/**
	 * 获取日期格式:"yyyy-MM-dd HH:mm:ss"
	 * @param dateFromat:定义格式化样式
	 * @param date:被格式化对象
	 * @return
	 */
	public static String getFormatDate(String dateFromat,Date date){
	        return MyUtils.getFormatDate(dateFromat, date);
	    }
	/**
	 * 获取日期格式:Mon May 24 06:02:20 CST 2021
	 * @param dateFromat:定义格式化样式
	 * @param strDate:被格式化对象（yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static Date getParseDate(String dateFromat,String strDate){
	        return MyUtils.getParseDate(dateFromat, strDate);
	    }
	/**
	 * 提交app错误信息至服务器
	 * @param errMsg：错误信息
	 */
	public static void putAppErrMsg(String errMsg,Context context){
        MyUtils.putAppErrMsg(errMsg,context);
    }
	/**
	 * 
	 * @param context
	 * @param view4:曲线view
	 * @param x:曲线x轴集合
	 * @param marginLeft：移动x轴左边距
	 * @param marginBottom：移动x轴下边距
	 * @param marginLeftY：移动y轴左边距
	 * @param marginBottomY:移动y轴左边距
	 * @param mChart
	 * @param tvXY:显示textview
	 * @param lineWidth:一定xy线宽
	 * @param mPopupView：移动y轴视图
	 * @param mPopupView2：移动x轴视图
	 * @param type：二分查询类型
	 */
	public static void markXY(Context context, final GraphicalView view4, final List<double[]> x, final int marginLeft,  int marginBottom, final int marginLeftY,  int marginBottomY, final XYChart mChart,  TextView tvXY, final int lineWidth, final View mPopupView, final View mPopupView2, final int type){
		  if (context instanceof InverterdpsActivity){
			  marginBottom = marginBottom +30;
			  marginBottomY = marginLeftY +24;
		  }
//		tvXY = null;
           MyUtils.markXY(context, view4, x, marginLeft, marginBottom, marginLeftY, marginBottomY, mChart, tvXY, lineWidth, mPopupView, mPopupView2, type);
	}

   /**
    * 二分查找
    * @param srcArray 原数组
    * @param des      目标数值
    * @param type     类型：1：代表执行二分搜索；else:执行逐一对比查询
    * @return
    */
   public static int binarySearch(double[] srcArray, double des,int type)
	    {
	        return MyUtils.binarySearch(srcArray, des, type);
	    } 
	  
   /**
    * dp转px
    * @param context
    * @param dipValue dp
    * @return
    */
   public static int dip2px(Context context, float dipValue) {
		       return MyUtils.dip2px(context, dipValue);
		}
	/**
	 * 通过国家码获取国家或者国家区号
	 * @param context
	 * @param status:status=1(获取国家);status=2(获取国际区号);
	 * @return
	 */
	public static String getCountryAndPhoneCodeByCountryCode(Context context,int status){
		return  MyUtilsTotal.getCountryAndPhoneCodeByCountryCode(context,status);
	}
	public static void startStorageAnimation(Context context, ImageView point, View line, int horizontal, int vertical, int lineLength, long time, OnAnimationEndLinster onAnimationEndLinster) {
		MyUtils.startStorageAnimation(context,point,line,horizontal,vertical,lineLength,time,onAnimationEndLinster);
	}
	public static void startStorageAnimation(Context context, ImageView point, View line, int horizontal, int vertical,OnAnimationEndLinster onAnimationEndLinster) {
		startStorageAnimation(context,point,line,horizontal,vertical,0,2000,onAnimationEndLinster);
	}
	public static void startStorageAnimation(Context context, ImageView point, View line, int horizontal, int vertical,long time, OnAnimationEndLinster onAnimationEndLinster) {
		MyUtils.startStorageAnimation(context,point,line,horizontal,vertical,0,time,onAnimationEndLinster);
	}

	public static void textClick(View view) {
		textClick(view,"");
	}
	public static void textClick(View view,String text) {
		MyUtilsTotal.textViewShowAll(view,text);
	}
	public static void datalogSet(Context context,  String datalogSn,  String paramType,  String param_1,  String param_2, OnHandlerListener handlerListener){
		OssUtils.datalogSet(context,datalogSn,paramType,param_1,param_2,handlerListener);
	}
	public static void deviceEdit(Context context, String deviceSn,String alias,  String other,  int deviceType, OnHandlerListener handlerListener){
		OssUtils.deviceEdit(context,deviceSn,alias,other,deviceType,handlerListener);
	}
	public static void deviceDelete(Context context, String deviceSn,int deviceType, OnHandlerListener handlerListener){
		OssUtils.deviceDelete(context,deviceSn,deviceType,handlerListener);
	}
	public static void invSet(Context context, String sn, String paramId, String param_1, String param_2, OnHandlerListener handlerListener){
		OssUtils.invSet(context,sn,paramId,param_1,param_2,handlerListener);
	}
	public static void storageSet(Context context, String sn, String paramId, String param_1, String param_2,String param_3, String param_4, OnHandlerListener handlerListener){
		OssUtils.storageSet(context,sn,paramId,param_1,param_2,param_3,param_4,handlerListener);
	}
	public static void storageSet(Context context, String sn, String paramId, String param_1, OnHandlerListener handlerListener){
		OssUtils.storageSet(context,sn,paramId,param_1,"","","",handlerListener);
	}
	public static void circlerDialog(FragmentActivity act, String text, int result){
		OssUtils.circlerDialog(act,text,result);
	}
	public static void circlerDialog(FragmentActivity act, String text, int result,boolean isFinish){
		OssUtils.circlerDialog(act,text,result,isFinish);
	}
	public static void editOssUserInfo(Context context, final String userName, final String userEmail, final String userTimezone, final String userLanguage, final String userActiveName, final String userCompanyName, final String userPhoneNum, final String userEnableResetPass, OnHandlerListener handlerListener){
		OssUtils.editOssUserInfo(context,userName,userEmail,userTimezone,userLanguage,userActiveName,userCompanyName,userPhoneNum,userEnableResetPass,handlerListener);
	}
	public static void configWifiOss(Activity act,int wifiType,String datalogSn){
		OssUtils.configWifiOss(act,wifiType,datalogSn);
	}
	public static void configRFStick(Activity act,String url,String datalogSn,String rfStickSn,OnHandlerListener onHandlerListener){
		MyUtils.configRFStick(act,url,datalogSn,rfStickSn,onHandlerListener);
	}
	public static void configWifiServer(Activity act,String wifiType,String datalogSn){
		MyUtils.configWifi(act, wifiType, datalogSn);
	}
	public static void circlerDialog(FragmentActivity act, String text, int result, OnCirclerDialogListener circlerDialogListener){
		OssUtils.circlerDialog(act,text,result,circlerDialogListener);
	}
	public static void getPasswordByDevice(FragmentActivity act, int type,  OnHandlerStrListener handlerListener){
		MyUtils.getPasswordByDevice(act,type,handlerListener);
	}
	public static void storageSetServer(Context context, String sn, String paramId, String param_1, String param_2,String param_3, String param_4, OnHandlerListener handlerListener){
		MyUtils.storageSetServer(context,sn,paramId,param_1,param_2,param_3,param_4,handlerListener);
	}
	public static void storageSPF5KSetServer(Context context, String sn, String paramId, String param_1, String param_2,String param_3, String param_4, OnHandlerListener handlerListener){
		MyUtils.storageSPF5KSetServer(context,sn,paramId,param_1,param_2,param_3,param_4,handlerListener);
	}
	public static void invSetServer(Context context, String sn, String paramId, String param_1, String param_2, OnHandlerListener handlerListener){
		MyUtils.invSetServer(context,sn,paramId,param_1,param_2,handlerListener);
	}
	public static void invSetMaxServer(Context context, String sn, String paramId, String param_1, String param_2, OnHandlerListener handlerListener){
		MyUtils.invSetMaxServer(context,sn,paramId,param_1,param_2,handlerListener);
	}
	public static void invSetJLINVServer(Context context, String sn, String paramId, String param_1, String param_2, OnHandlerListener handlerListener){
		MyUtils.invSetJLINVServer(context,sn,paramId,param_1,param_2,handlerListener);
	}
	public static void showJumpWifiSet(FragmentActivity act){
		MyUtils.showJumpWifiSet(act);
	}
	public static void showJumpWifiSet(FragmentActivity act,String noteText){
		MyUtils.showJumpWifiSet(act,noteText);
	}
	public static void mixSet(Context context, MixSetBean bean, OnHandlerListener handlerListener){
		MixUtil.mixSet(context,bean,handlerListener);
	}
}
