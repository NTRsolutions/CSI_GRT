package com.growatt.shinephone.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.growatt.shinephone.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Created by dg on 2016/12/19.
 */
public class MyUtilsTotal {
    /**
     * 获取手机状态栏高度
     * @param context:全局对象
     * @return 返回状态栏高度的像素值
     */
    public static int getStatusBarHeight(Context context){
        int status_bar_height=0;
        try {
            Class<?> mClass = Class.forName("com.android.internal.R$dimen");
            Object newInstance = mClass.newInstance();
            Field field = mClass.getField("status_bar_height");
            int resId = (Integer)field.get(newInstance) ;
            status_bar_height = context.getResources().getDimensionPixelSize(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status_bar_height;
    }

    /**
     * 通过国家码获取国家或者国家区号
     * @param context
     * @param status:status=1(获取国家);status=2(获取国际区号);
     * @return
     */
    public static String getCountryAndPhoneCodeByCountryCode(Context context,int status){
        String countryCode = Locale.getDefault().getCountry();
        String country = getCountryAndPhoneCode(context,countryCode,status);
        return country;
    }

    private static String getCountryAndPhoneCode(Context context, String countryCode, int status) {
        String countryOrPhoneCode = null;
        if (status == 1){
            countryOrPhoneCode = "Other";
        }else if (status == 2){
            countryOrPhoneCode = "86";
        }
        try {
            String json = readCountryByAssets(context);
            countryOrPhoneCode = parseJsonByCountryCode(countryOrPhoneCode,json,countryCode,status);
            return countryOrPhoneCode;
        }catch (Exception e) {
            e.printStackTrace();
            return countryOrPhoneCode;
        }
    }

    private static String parseJsonByCountryCode(String countryOrPhoneCode,String json, String countryCode, int status) throws Exception{
        //解析Json
        JSONObject jsonObj=new JSONObject(json);
        JSONArray array = jsonObj.getJSONArray("data");
        if (array!=null){
            int length = array.length();
            for (int i = 0; i <length; i++){
                JSONObject countryObj = array.getJSONObject(i);
                if (status == 1){
                    if (countryCode.contains(countryObj.getString("countryCode")) || countryCode.toUpperCase().contains(countryObj.getString("countryCode"))){
                        countryOrPhoneCode = countryObj.getString("countryName");
                        break;
                    }
                }else if (status == 2){
                    if (countryCode.contains(countryObj.getString("countryCode")) || countryCode.toUpperCase().contains(countryObj.getString("countryCode"))){
                        countryOrPhoneCode = countryObj.getString("phoneCode");
                        break;
                    }
                }

            }
        }
        return countryOrPhoneCode;
    }

    private static String readCountryByAssets(Context context) throws Exception{
        InputStream inputStream = context.getAssets().open("englishCountryJson.txt");
        byte[] buffer=new byte[inputStream.available()];
        inputStream.read(buffer);
        String json = new String(buffer,"GB2312");
        return json;
    }
    public static void textClick(View view,String text) {
        //弹出提示，显示全部内容
        textViewShowAll(view,text);
    }
    public static void textViewShowAll(View textView,String preText) {
//        textView.getLeft()-textView.getWidth()
        String text = preText;
        int resId = 0;
        if ("".equals(text)){
            text = ((TextView)textView).getText().toString();
            resId = R.layout.textview_dialog_m;
        }else {
            resId =R.layout.textview_dialog_right;
        }
        View view = LayoutInflater.from(textView.getContext()).inflate(resId,null);
        TextView tvStr = (TextView) view.findViewById(R.id.textViewAll);
        tvStr.setMovementMethod(new ScrollingMovementMethod());
        tvStr.setText(text);
        tvStr.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width2=tvStr.getMeasuredWidth();
        PopupWindow pop = new PopupWindow(view);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new ColorDrawable(0x00000000));
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.Popup_Anim);
        //设置动画
//        pop.setAnimationStyle(R.style.mypopwindow_anim_style);
        int width1 = textView.getWidth();
        pop.showAsDropDown(textView,(width1-width2)/2,0);
    }
    /**
     * 多个文本的点击弹框显示详情
     */
    public static void mulitTextViewClick(TextView ...textViews){
        for (TextView tv : textViews){
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textViewShowAll(v,"");
                }
            });
        }
    }

}
