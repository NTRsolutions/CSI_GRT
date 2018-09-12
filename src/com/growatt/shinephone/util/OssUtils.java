package com.growatt.shinephone.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.LoginActivity;
import com.growatt.shinephone.activity.NewWifiS2ConfigActivity;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.listener.OnCirclerDialogListener;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.ossactivity.OssRFStickActivity;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.view.GetWifiListNew;
import com.mylhyl.circledialog.CircleDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import mediatek.android.IoTManager.SmartConnection;

/**
 * Created by dg on 2017/6/14.
 */

public class OssUtils {
    /**
     * 采集器设置
     * @param datalogSn ：序列号
     * @param paramType ：设置类型
     * @param param_1 ：参数值
     * @param param_2
     * @param handlerListener
     */
    public static void datalogSet(final Context context, final String datalogSn, final String paramType, final String param_1, final String param_2, final OnHandlerListener handlerListener){
        Mydialog.Show(context,"");
        PostUtil.post(OssUrls.postOssDatalogSet(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("datalogSn",datalogSn);
                params.put("paramType",paramType);
                params.put("param_1",param_1);
                params.put("param_2",param_2);
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    String msg = jsonObject.getString("msg");
                    if (result == 1){
                        msg = context.getString(R.string.m103操作成功);
                    }
                   handlerListener.handlerDeal(result,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {
                Mydialog.Dismiss();
            }
        });
    }
    public static void deviceEdit(final Context context, final String deviceSn, final String alias, final String other, final int deviceType, final OnHandlerListener handlerListener){
        Mydialog.Show(context,"");
        PostUtil.post(OssUrls.postOssDeviceEdit(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("deviceSn",deviceSn);
                params.put("alias",alias);
                params.put("other",other);
                params.put("deviceType",deviceType + "");
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    String msg = jsonObject.getString("msg");
                    if (result == 1){
                        msg = context.getString(R.string.m103操作成功);
                    }
                    handlerListener.handlerDeal(result,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }

            @Override
            public void LoginError(String str) {
            }
        });
    }

    public static void deviceDelete(final Context context, final String deviceSn, final int deviceType, final OnHandlerListener handlerListener) {
        Mydialog.Show(context,"");
        PostUtil.post(OssUrls.postOssDeviceDelete(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("deviceSn",deviceSn);
                params.put("deviceType",deviceType + "");
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    String msg = jsonObject.getString("msg");
                    if (result == 1){
                        msg = context.getString(R.string.m103操作成功);
                    }
                    handlerListener.handlerDeal(result,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {
            }
        });
    }

    /**
     * Oss逆变器设置
     * @param context
     * @param sn
     * @param paramId
     * @param param_1
     * @param param_2
     */
    public static void invSet(final Context context, final String sn, final String paramId, final String param_1, final String param_2, final OnHandlerListener handlerListener) {
        Mydialog.Show(context,"");
        PostUtil.post(OssUrls.postOssInvSet(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("inverterSn",sn);
                params.put("paramId",paramId);
                params.put("param_1",param_1);
                params.put("param_2",param_2);
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    String msg = jsonObject.getString("msg");
                    if (result == 1){
                        msg = context.getString(R.string.m103操作成功);
                    }
                    handlerListener.handlerDeal(result,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {
                Mydialog.Dismiss();
            }
        });
    }

    public static void storageSet(final Context context, final String sn, final String paramId, final String param_1, final String param_2, final String param_3, final String param_4, final OnHandlerListener handlerListener) {
        Mydialog.Show(context,"");
        PostUtil.post(OssUrls.postOssStorageSet(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("storageSn",sn);
                params.put("paramId",paramId);
                params.put("param_1",param_1);
                params.put("param_2",param_2);
                params.put("param_3",param_3);
                params.put("param_4",param_4);
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    String msg = jsonObject.getString("msg");
                    if (result == 1){
                        msg = context.getString(R.string.m103操作成功);
                    }
                    handlerListener.handlerDeal(result,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {
                Mydialog.Dismiss();
            }
        });
    }

    public static void circlerDialog( FragmentActivity act, String text, int result) {
        circlerDialog(act,text,result,true);
    }
    public static void circlerDialog( FragmentActivity act, String text, int result,  boolean isFinish) {
        circlerDialog(act,text,result,isFinish,null);
    }
    public static void circlerDialog( FragmentActivity act, String text, int result,  OnCirclerDialogListener circlerDialogListener) {
        circlerDialog(act,text,result,false,circlerDialogListener);
    }
    public static void circlerDialog(final FragmentActivity act, String text, int result,final boolean isFinish, final OnCirclerDialogListener circlerDialogListener) {
//        if (act.isFinishing() || act.isDestroyed()) return;
        try{
        new CircleDialog.Builder(act)
                .setCancelable(false)
                .setWidth(0.7f)
                .setTitle(act.getString(R.string.温馨提示) +(result == -1 ? "" : ("(" + result + ")")))
                .setText(text)
                .setPositive(act.getString(R.string.all_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isFinish){
                            act.finish();
                        }else {
                            if (circlerDialogListener != null){
                                circlerDialogListener.onCirclerPositive();
                            }
                        }
                    }
                })
                .show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 修改oss用户信息
     * @param context
     * @param userName
     * @param userEmail
     * @param userTimezone
     * @param userLanguage
     * @param userActiveName
     * @param userCompanyName
     * @param userPhoneNum
     * @param userEnableResetPass
     * @param handlerListener
     */
    public static void editOssUserInfo(final Context context, final String userName, final String userEmail, final String userTimezone, final String userLanguage, final String userActiveName, final String userCompanyName, final String userPhoneNum, final String userEnableResetPass, final OnHandlerListener handlerListener) {
        Mydialog.Show((Activity) context);
        PostUtil.post(OssUrls.postOssUserInfoEdit(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("userName",userName);
                params.put("userEmail",userEmail);
                params.put("userTimezone",userTimezone);
                params.put("userLanguage",userLanguage);
                params.put("userActiveName",userActiveName);
                params.put("userCompanyName",userCompanyName);
                params.put("userPhoneNum",userPhoneNum);
                params.put("userEnableResetPass",userEnableResetPass);
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    String msg = jsonObject.getString("msg");
                    if (result == 1){
                        msg = context.getString(R.string.m103操作成功);
                    }
                    handlerListener.handlerDeal(result,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {
            }
        });
    }
    /**
     *
     * @param act
     * @param wifiType
     * @param datalogSn
     */
    public static void configWifiOss(final Activity act, int wifiType,final String datalogSn){
        ConnectivityManager manager = (ConnectivityManager)act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(wifi == NetworkInfo.State.CONNECTED||wifi== NetworkInfo.State.CONNECTING){
            switch (wifiType){
                case 2://shinewifibox:laowifi
                    GetWifiListNew md=new GetWifiListNew(act);
                    md.setCancelable(false);
                    md.show();
                    break;
                case 6://shinewifi
                    Map<String, Object> map = new GetWifiInfo(act).Info();
                    if(TextUtils.isEmpty(map.get("mAuthString").toString())){
                        AlertDialog builder = new AlertDialog.Builder(act).setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_connectwifi).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();
                            }
                        }).create();
                        builder.show();
                        return;
                    }
                    Intent intent=new Intent(act,SmartConnection.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("type","101");
                    bundle.putString("id",datalogSn);
                    bundle.putString("ssid",map.get("ssid").toString());
                    bundle.putString("mAuthString",map.get("mAuthString").toString());
                    bundle.putByte("mAuthMode",(Byte) map.get("mAuthMode"));
                    intent.putExtras(bundle);
                    act.startActivity(intent);
                    break;
                case 9://shinelinebox
                    Intent rfstickIntent = new Intent(act, OssRFStickActivity.class);
                    rfstickIntent.putExtra("datalogSn",datalogSn);
                    rfstickIntent.putExtra("jumpType",101);
                    act.startActivity(rfstickIntent);
                    break;
                case 11://shineWifi-s(新wifi第二版)
                    Intent intent2 = new Intent(act,NewWifiS2ConfigActivity.class);
                    intent2.putExtra("jumpType","101");
                    intent2.putExtra("sn",datalogSn);
                    act.startActivity(intent2);
                    break;
            }
        }else{
            T.make(R.string.all_wifi_failed,act);
            AlertDialog builder = new AlertDialog.Builder(act).setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_connectwifi).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            }).create();
            builder.show();
        }
    }

    /**
     * 登出oss系统
     * @param act
     * @param isClearPhone：清除手机号；集成商不清除
     */
    public static void logoutOss(final FragmentActivity act, final boolean isClearPhone){
        new CircleDialog.Builder(act)
                .setWidth(0.7f)
                .setTitle(act.getString(R.string.温馨提示))
                .setText(act.getString(R.string.user_islogout))
                .setPositive(act.getString(R.string.all_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SqliteUtil.url("");
                        SqliteUtil.plant("");
                        if (isClearPhone) {
                            SharedPreferencesUnit.getInstance(act).put(Constant.OssUser_Phone, "0");
                        }
                        act.startActivity(new Intent(act,LoginActivity.class));
                        act.finish();
                    }
                })
                .setNegative(act.getString(R.string.all_no), null)
                .show();
    }

    /**
     * oss接口状态码，以及错误信息
     * @param msg
     * @param result
     * @param context
     * @param type:0:代表弹框；1：代表toast
     */
    public static void showOssToastOrDialog(Context context,String msg,int result,int type,boolean isFinish,OnCirclerDialogListener circlerDialogListener){
        if (result == 22 ){//做登录超时处理
            T.make(msg,ShineApplication.context);
            Map<String, Object> map = SqliteUtil.inquirylogin();
            if (map != null && map.size()>0){
//                MyUtils.ossLogin(1, context,map.get("name").toString().trim(), map.get("pwd").toString().trim(), new OnViewEnableListener() {});
                ossLoginUrl(context,map.get("name").toString().trim(), map.get("pwd").toString().trim(),OssUrls.ossCRUDUrl);
            }else {
                context.startActivity(new Intent(context,LoginActivity.class));
            }
        }else {
            if (type == 0){//弹框
                circlerDialog((FragmentActivity)context,msg,result,isFinish,circlerDialogListener);
            }else {//toast
                StringBuilder sb = new StringBuilder().append(msg).append("(").append(result).append(")");
                T.make(sb.toString(), ShineApplication.context);
            }
        }
    }
    public static void showOssDialog(FragmentActivity context,String msg,int result,boolean isFinish,OnCirclerDialogListener circlerDialogListener){
        showOssToastOrDialog(context,msg,result,0,isFinish,circlerDialogListener);
    }
    public static void showOssToast(Context context,String msg,int result){
        showOssToastOrDialog(context,msg,result,1,false,null);
    }
    public static void showOssToast(String msg,int result){
        showOssToastOrDialog(ShineApplication.context,msg,result,1,false,null);
    }

    /**
     * oss登录超时重新登录接口:传搜索服务器url
     * @param userName
     * @param userPassword
     * @param serverUrl
     */
    public static void ossLoginUrl(final Context context, final String userName, final String userPassword, final String serverUrl){
        PostUtil.post(OssUrls.getOssLoginServer(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("userName",userName);
                params.put("password",MD5andKL.encryptPassword(userPassword));
                params.put("serverUrl",serverUrl);
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result != 1){
                        context.startActivity(new Intent(context,LoginActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void LoginError(String str) {
                context.startActivity(new Intent(context,LoginActivity.class));
            }
        });
    }
    public static void storageSetSPF5k(final Context context, final String sn, final String paramId, final String param_1, final String param_2, final String param_3, final String param_4, final OnHandlerListener handlerListener) {
        Mydialog.Show(context,"");
        PostUtil.post(OssUrls.postSetOssStorageSPF5k(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("storageSn",sn);
                params.put("paramId",paramId);
                params.put("param_1",param_1);
                params.put("param_2",param_2);
                params.put("param_3",param_3);
                params.put("param_4",param_4);
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    String msg = jsonObject.getString("msg");
                    if (result == 1){
                        msg = context.getString(R.string.m103操作成功);
                    }
                    handlerListener.handlerDeal(result,msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {
                Mydialog.Dismiss();
            }
        });
    }
}
