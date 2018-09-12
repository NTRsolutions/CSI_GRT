package com.growatt.shinephone.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.growatt.shinephone.R;

import java.lang.reflect.Method;

/**
 * Created by dg on 2017/5/24.
 * 自定义popuwindow
 */

public abstract class CustomBasePopupWindow extends PopupWindow {
    /**
     * 布局文件
     */
    protected PopupWindow mPreviewPopup;
    public View mPopView;

    /**
     * @param context 上下文
     * @param layoutResID 布局文件
     * @param width 窗口宽度
     * @param height 窗口高度
     */
    public CustomBasePopupWindow(Context context, int layoutResID, int width, int height,boolean outsideDismiss) {
        mPreviewPopup = new PopupWindow(context);
        mPopView = LayoutInflater.from(context).inflate(layoutResID, null);
        mPreviewPopup.setWidth(width);
        mPreviewPopup.setHeight(height);
        mPreviewPopup.setContentView(mPopView);
        mPreviewPopup.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPreviewPopup.setFocusable(true);
        mPreviewPopup.setTouchable(outsideDismiss);
        mPreviewPopup.setOutsideTouchable(outsideDismiss);
        init();
    }
    public CustomBasePopupWindow(Context context, int layoutResID, int width,boolean outsideDismiss) {
        mPopView = LayoutInflater.from(context).inflate(layoutResID, null);
        mPreviewPopup = new PopupWindow(mPopView,width, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPreviewPopup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPreviewPopup.setTouchable(outsideDismiss);
        mPreviewPopup.setOutsideTouchable(outsideDismiss);
        mPreviewPopup.setFocusable(true);
        mPreviewPopup.setAnimationStyle(R.style.Popup_Anim);
        init();
    }
    /**
     * @param x 显示位置所在x坐标
     * @param y 显示位置所在y坐标
     */
    public void show(int x, int y) {
        if (mPreviewPopup.isShowing()){
            dismiss();
        }else {
            mPreviewPopup.showAtLocation(mPopView, Gravity.NO_GRAVITY, x, y);
        }
    }
    /**
     */
    public void showAsDowm(View v) {
        if (mPreviewPopup.isShowing()){
            dismiss();
        }else {
            mPreviewPopup.showAsDropDown(v,0,2);
        }
    }
    /**
     * 关闭
     */
    public void dismiss() {
        if (mPreviewPopup != null) {
            mPreviewPopup.dismiss();
        }
    }

    /**
     * 当点击外部不消失窗口，并且能相应外部控件的点击事件
     */
    public void setPopupWindowTouchModal(boolean touchModal) {
        if (null == mPreviewPopup) {
            return;
        }
        Method method;
        try {
            method = PopupWindow.class.getDeclaredMethod("setTouchModal", boolean.class);
            method.setAccessible(true);
            method.invoke(mPreviewPopup, touchModal);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @author xc
     * @date 2016-3-31 下午2:43:15
     * @describe 初始化需要更新数据或者处理点击事件的控件
     */
    public abstract void init();

}
