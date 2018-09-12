package com.growatt.shinephone.util.flow;

import android.content.Context;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.growatt.shinephone.bean.flow.FlowInvoiceBean;
import com.growatt.shinephone.bean.flow.FlowMainBean;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.SharedPreferencesUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dg on 2017/11/9.
 */

public class FlowUtil {
    public static final String INVOICE_NOTE = "_invoice";
    /**
     * 获取选中的item
     * @param adapter
     * @return
     */
    public static ArrayList<FlowMainBean> getAllSelect(BaseQuickAdapter adapter){
        ArrayList<FlowMainBean> selectList = new ArrayList<>();
        List<FlowMainBean> allList = adapter.getData();
        if (allList != null){
            for (FlowMainBean bean : allList){
                if (bean.isCheck()){
                    selectList.add(bean);
                }
            }
        }
        return selectList;
    }

    /**
     * 根据支付状态获取支付结果
     * @return
     */
    public static String getPayRestultByStatus(String status, Context context){
        String result = "";
        if ("-2".equals(status) || "6001".equals(status)){
            result = "用户取消";
        }
        else if ("9000".equals(status)||"0".equals(status)){
            result = "支付成功";
        }else {
            result = "支付失败";
        }
        return result;
    }
    /**
     * 根据支付状态获取支付结果:服务器状态
     * @return
     */
    public static String getPayRestultByStatus(int status, Context context){
        String result = "";
        switch (status){
            case 1: result = "续费成功";break;
            case 2: result = "待支付";break;
            case 3: result = "支付成功,续费中";break;
            case 4: result = "支付成功,续费中";break;
            case 5: result = "支付失败";break;
            case 6: result = "支付成功,续费中";break;
            case 7: result = "部分续费成功";break;
            default:result = "支付失败";
                result = new StringBuilder().append(result).append("(").append(status).append(")").toString();
                break;
        }
        return result;
    }

    /**
     * 保存发票信息到偏好设置
     * @param context
     * @param bean
     */
    public static void putInvoice(Context context,FlowInvoiceBean bean){
        if (Cons.userBean != null && bean != null) {
            String json = new Gson().toJson(bean);
            SharedPreferencesUnit.getInstance(context).put(Cons.userBean.accountName + INVOICE_NOTE, json);
        }
    }

    /**
     * 获取发票信息从偏好设置
     * @param context
     */
    public static FlowInvoiceBean getInvoice(Context context){
        try {
            String json = SharedPreferencesUnit.getInstance(context).get(Cons.userBean.accountName + INVOICE_NOTE);
            if (!TextUtils.isEmpty(json)){
                FlowInvoiceBean bean = new Gson().fromJson(json, FlowInvoiceBean.class);
                return bean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
