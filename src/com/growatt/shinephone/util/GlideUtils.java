package com.growatt.shinephone.util;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.growatt.shinephone.R;



/**
 * Created by dg on 2017/7/25.
 */

public class GlideUtils {
    private static GlideUtils inst;

    public static GlideUtils getInstance() {
        if (inst == null) {
            inst = new GlideUtils();
        }
        return inst;
    }
    public  void showImageContext(Context context,String path, ImageView iv){
        showImageContext(context,R.drawable.loading,R.drawable.pic_service,path,iv);
    }
    public  void showImageContext(Context context,int placeholderRes,int errorRes,String path,ImageView iv){
        Glide.with(context).load(path).placeholder(placeholderRes).error(errorRes).dontAnimate().into(iv);
    }
    public  void showImageAct(Activity act,String path ,ImageView iv){
        showImageAct(act,R.drawable.loading,R.drawable.pic_service,path,iv);
    }
    public  void showImageAct(Activity act,int resId ,ImageView iv){
        showImageAct(act,R.drawable.loading,R.drawable.pic_service,resId,iv);
    }
    public  void showImageAct(Activity act,int placeholderRes,int errorRes,String path ,ImageView iv){
        Glide.with(act).load(path).placeholder(placeholderRes).error(errorRes).dontAnimate().into(iv);
    }
    public  void showImageAct(Activity act,int placeholderRes,int errorRes,int resId ,ImageView iv){
        Glide.with(act).load(resId).placeholder(placeholderRes).error(errorRes).dontAnimate().into(iv);
    }
    public  void showImageContext(Context con,int placeholderRes,int errorRes,int resId ,ImageView iv){
        Glide.with(con).load(resId).placeholder(placeholderRes).error(errorRes).dontAnimate().into(iv);
    }

    /**
     *
     * @param act
     * @param resId
     * @param iv
     * @param type:0:表示没有占位图;1:默认占位图；其他为咱位图资源
     */
    public  void showImageAct(Activity act,int resId ,ImageView iv,int type){
        switch (type){
            case 0:
                Glide.with(act).load(resId).error(R.drawable.pic_service).dontAnimate().into(iv);
                break;
            case 1:
                Glide.with(act).load(resId).placeholder(R.drawable.loading).error(R.drawable.pic_service).dontAnimate().into(iv);
                break;
            default:
                Glide.with(act).load(resId).placeholder(type).error(R.drawable.pic_service).dontAnimate().into(iv);
                break;
        }
    }
}
