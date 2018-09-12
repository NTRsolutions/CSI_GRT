package com.growatt.shinephone.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.InverterdpsActivity;
import com.growatt.shinephone.activity.LoginActivity;
import com.growatt.shinephone.activity.MainActivity;
import com.growatt.shinephone.activity.NewWifiS2ConfigActivity;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.activity.WelcomeActivity;
import com.growatt.shinephone.bean.OssUserBean;
import com.growatt.shinephone.bean.UserBean;
import com.growatt.shinephone.bean.max.WifiList;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnAnimationEndLinster;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.listener.OnHandlerStrListener;
import com.growatt.shinephone.listener.OnViewEnableListener;
import com.growatt.shinephone.ossactivity.OssAZActivity;
import com.growatt.shinephone.ossactivity.OssJKActivity;
import com.growatt.shinephone.ossactivity.OssKeFuActivity;
import com.growatt.shinephone.ossactivity.OssPhoneVerActivity;
import com.growatt.shinephone.ossactivity.OssRFStickActivity;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.tool.GraphicalView;
import com.growatt.shinephone.util.PostUtil.postListener;
import com.growatt.shinephone.view.FixedPopupWindow;
import com.growatt.shinephone.view.GetWifiListNew;
import com.growatt.shinephone.view.MyMarkerView;
import com.mylhyl.circledialog.CircleDialog;

import org.achartengine.chart.XYChart;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mediatek.android.IoTManager.SmartConnection;

import static com.growatt.shinephone.activity.ShineApplication.context;


@SuppressLint("SimpleDateFormat") public class MyUtils {
	public static final int dayTamp = 24*60*60*1000;//一天的时间戳
	public static final int minTamp = 60*1000;//一分钟的时间戳
	public static SimpleDateFormat sdf_hm = new SimpleDateFormat("HH:mm");
	public static SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
	public static void autoLogin( Context context,  String userName, String password) {
			ossErrAutoLogin(context, userName, password, new OnViewEnableListener(){});
	}

	public static boolean regexCheckPhone(String phone) {
		boolean flag = false;
		  try{
		    Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
		    Matcher matcher = regex.matcher(phone);
		    flag = matcher.matches();
		   }catch(Exception e){
		    flag = false;
		   }
		  return flag;
	}

	public static boolean regexCheckEmail(String email) {
		boolean flag = false;
		  try{
		    String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		    Pattern regex = Pattern.compile(check);
		    Matcher matcher = regex.matcher(email);
		    flag = matcher.matches();
		   }catch(Exception e){
		    flag = false;
		   }
		  return flag;
	}
	private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
	private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
	@SuppressLint("NewApi") public static boolean isNotificationEnabled(Context context) {
	       if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT){
	           return true;
	       }
	       AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
	       ApplicationInfo appInfo = context.getApplicationInfo();
	       String pkg = context.getApplicationContext().getPackageName();
	       int uid = appInfo.uid;
	       Class appOpsClass = null; /* Context.APP_OPS_MANAGER */
	       try {
	           appOpsClass = Class.forName(AppOpsManager.class.getName());
	           Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
	           Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
	           int value = (Integer)opPostNotificationValue.get(Integer.class);
	           return ((Integer)checkOpNoThrowMethod.invoke(mAppOps,value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       return true;
	}

	public static int getScreenWidth(Context context) {
		return getScreenDisplayMetrics(context).widthPixels;
	}

	public static int getScreenHeight(Context context) {
		return getScreenDisplayMetrics(context).heightPixels;
	}

	public static DisplayMetrics getScreenDisplayMetrics(Context context) {
//		DisplayMetrics dm = new DisplayMetrics();
//		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return context.getResources().getDisplayMetrics();
	}

	public static String getAppVersion(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "1.0";
	}
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>(); 
    /**
     * 获取SimpleDateFormat对象，线程安全
     * @param dateFormat："yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static DateFormat getDateFormat(String dateFormat)   
    {  
        DateFormat df = threadLocal.get();  
        if(df==null){  
            df = new SimpleDateFormat(dateFormat);  
            threadLocal.set(df);  
        }  
        return df;  
    }  
    public static String getFormatDate(String dateFromat,Date date){
    	if(TextUtils.isEmpty(dateFromat)){
    		dateFromat=DATE_FORMAT;
    	}
    	if(date==null){
    		date=new Date();
    	}
        return getDateFormat(dateFromat).format(date);
    }

    public static Date getParseDate(String dateFromat,String strDate){
    	if(TextUtils.isEmpty(dateFromat)){
    		dateFromat=DATE_FORMAT;
    	}
        try {
			return getDateFormat(dateFromat).parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
        return null;
    }

	public static void putAppErrMsg(final String errMsg,final Context context) {
		final String msg="SystemType:1"+ 
				";AppVersion:"+getAppVersion(context)+
				";"+"SystemVersion:"+android.os.Build.VERSION.RELEASE+
				";"+"PhoneModel:"+android.os.Build.MODEL+
				";"+"UserName:"+(Cons.userBean!=null?Cons.userBean.getAccountName():"")+
				";"+"msg:"+errMsg;
				Log.i("TAG", msg);
         PostUtil.post(new Urlsutil().postSaveAppErrorMsg, new postListener() {
			
			@Override
			public void success(String json) {
			}
			
			@Override
			public void Params(Map<String, String> params) {
				params.put("time", MyControl.getFormatDate());
				params.put("msg", msg);
//				params.put("msg", "AppVersion:"+getAppVersion(context));
			}
			@Override
			public void LoginError(String str) {
			}
		});
	}

	@SuppressLint("ClickableViewAccessibility")
	public static void markXY(final Context context, final GraphicalView view4, final List<double[]> x, final int marginLeft, final int marginBottom, final int marginLeftY, final int marginBottomY, final XYChart mChart, final TextView tvXY, final int lineWidth, final View mPopupView, final View mPopupView2, final int type){
		
		OnTouchListener chartViewOnTouchListener = new OnTouchListener() {
			PopupWindow mPopupWindow=null;
			PopupWindow mPopupWindow2=null;
			private float yDown = 0;
			private float yUP = 0;
			private int minScroll = dip2px(context,100f);
	        public boolean onTouch(View v, MotionEvent event) {
//				if (tvXY != null) {
//					tvXY.setTextColor(Color.parseColor("#5080eb"));
//					tvXY.setGravity(Gravity.RIGHT);
//				}
				XYMultipleSeriesDataset dataset = mChart.getDataset();
				XYMultipleSeriesRenderer renderer=mChart.getRenderer();
				XYSeries seriesAt = dataset.getSeriesAt(0);
				switch (event.getAction()) {
	            case MotionEvent.ACTION_DOWN:
					yDown = event.getRawY();
	                mPopupWindow = new FixedPopupWindow(mPopupView,lineWidth, view4.getHeight());
	                mPopupWindow2 = new FixedPopupWindow(mPopupView2,view4.getWidth(), lineWidth);
	                break;
	            case MotionEvent.ACTION_MOVE:
//	                //自定义设置xy
	                //获取点击位置屏幕xy
	                float screenX = event.getX();
	                float screenY = event.getY();
	                //装换为图标xy
	                double[] realPoint = mChart.toRealPoint(screenX, screenY);
					if(x!=null&&x.size()>0){
					int index=binarySearch(x.get(0), realPoint[0],type);
					if(index<x.get(0).length){
					double[] screenPoint2 = mChart.toScreenPoint(new double[]{seriesAt.getX(index),seriesAt.getY(index)});
					double[] screenPoint = mChart.toScreenPoint(new double[]{0,0});
					int toBottom=(int) (view4.getHeight()-screenPoint[1]);
					int toLeft=(int)(view4.getWidth()-screenPoint[0]);
					if(tvXY!=null){
					if(seriesAt.getY(index)==0.0){
						tvXY.setText("");
					}else{
						if (renderer.getXTextLabel((double) (index+1))==null){
							tvXY.setText(context.getText(R.string.m4)+":"+seriesAt.getX(index)+"\n"+context.getText(R.string.m5)+":"+seriesAt.getY(index));
						}else {
							tvXY.setText(context.getText(R.string.m4)+":"+renderer.getXTextLabel((double) (index+1))+context.getText(R.string.m5)+":"+seriesAt.getY(index));
						}
					}
					}
	                if (mPopupWindow.isShowing()) {
	                    mPopupWindow.update((int)screenPoint2[0]+dip2px(context, marginLeftY),dip2px(context, marginBottomY)+toBottom, lineWidth,view4.getHeight()-toBottom);
	                    mPopupWindow2.update(dip2px(context, marginLeft),view4.getHeight()-(int)screenPoint2[1]+dip2px(context, marginBottom), view4.getWidth(),lineWidth);
//						mPopupWindow.showAtLocation(view4, Gravity.BOTTOM|Gravity.LEFT, (int)screenPoint2[0]+dip2px(context, marginLeftY)+toLeft,dip2px(context, marginBottomY));
//						mPopupWindow2.showAtLocation(view4,Gravity.BOTTOM|Gravity.LEFT, dip2px(context, marginLeft),view4.getHeight()-(int)screenPoint2[1]+dip2px(context, marginBottom));

	                } else {
	                    mPopupWindow.showAtLocation(view4, Gravity.BOTTOM|Gravity.LEFT, (int)screenPoint2[0]+dip2px(context, marginLeftY)+toLeft,dip2px(context, marginBottomY));
	                    mPopupWindow2.showAtLocation(view4,Gravity.BOTTOM|Gravity.LEFT, dip2px(context, marginLeft),view4.getHeight()-(int)screenPoint2[1]+dip2px(context, marginBottom));
	                }

					}
					}
	                break;
	            case MotionEvent.ACTION_UP:
					yUP = event.getRawY();
	            	//隐藏定位轴
	            	if (mPopupWindow != null) {
	                    if (mPopupWindow.isShowing()) {
	                        mPopupWindow.dismiss();
	                    }
	                    mPopupWindow = null;
	                }
	                if (mPopupWindow2 != null) {
	                    if (mPopupWindow2.isShowing()) {
	                        mPopupWindow2.dismiss();
	                    }
	                    mPopupWindow2 = null;
	                }
					if (Math.abs(yUP-yDown) > minScroll && context instanceof InverterdpsActivity) {
						//做下拉滑动
						new InverterdpsActivity.ScrollTask().execute(-30);
					}
	                break;
	            }
	            return true;
	        }
	    };
		view4.setOnTouchListener(chartViewOnTouchListener);
		view4.setId(0);
	}


	/**
	* 二分查找算法
	*
	* @param srcArray 有序数组
	* @param des 查找元素
	* @return des的数组下标，没找到返回-1
	*/
   public static int binarySearch(double[] srcArray, double des,int type)
	    {
	   if(type==1){
	        int low = 0;
	        int high = srcArray.length-1;
	        int index=0;
	        while(low <= high) 
	        {
	            int middle = (low + high)/2;
	            index=middle;
	            if(des == srcArray[middle]) 
	            {
	                return middle;
	            }
	            else if(des <srcArray[middle]) 
	            {
	                high = middle - 1;
	            }
	            else 
	            {
	                low = middle + 1;
	            }
	        }
	        return index;
	   }else{
	    	int index=0;
	    	double s=Math.abs(srcArray[0]-des);
	    	 for(int i=0;i<srcArray.length;i++){
	    		 double ss=Math.abs(srcArray[i]-des);
	    		 if(ss<s){
	    			 index=i;
	    			 s=ss;
	    		 }
	    	 }
	    	return index;
	   }
	    } 
	  
		    //dp转像素
		    public static int dip2px(Context context, float dipValue) {
		        final float scale = context.getResources().getDisplayMetrics().density;
		        return (int) (dipValue * scale + 0.5f);
		    }
	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	public static void configRFStick(Activity act,String url, final String datalogSn, final String rfStickSN , final OnHandlerListener handlerListener){
		Mydialog.Show(act);
		PostUtil.post(url, new postListener() {
			@Override
			public void Params(Map<String, String> params) {
				params.put("serialNum",datalogSn);
				params.put("rfStickSN",rfStickSN);
			}
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					int result = jsonObject.getInt("result");
					handlerListener.handlerDeal(result,jsonObject.getString("msg"));
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
	private static  int status_bar_height;
	/**
	 * 获取手机状态栏高度
	 * @param context:全局对象
	 * @return 返回状态栏高度的像素值
	 */
	public static int getStatusBarHeight(Context context){
		if (status_bar_height == 0) {
			try {
				Class<?> mClass = Class.forName("com.android.internal.R$dimen");
				Object newInstance = mClass.newInstance();
				Field field = mClass.getField("status_bar_height");
				int resId = (Integer) field.get(newInstance);
				status_bar_height = context.getResources().getDimensionPixelSize(resId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status_bar_height;
	}
	/**
	 * 储能动画，根据某条线平移动画效果
	 * @param context
	 * @param point:动画对象
	 * @param line:point在line上移动
	 * @param horizontal:0代表非水平移动，1代表水平向右移动，-1代表水平向左移动
	 * @param vertical:0代表非垂直移动，1代表垂直向下移动，-1代表垂直向上移动
	 * @param lineLength:0代表line不拆分成两部分，1代表拆分后的前半段，-1代表拆分后的后半段
	 * @param time:动画持续时间
	 * @param onAnimationEndLinster:动画结束后监听
	 */
	public static void startStorageAnimation(Context context, ImageView point, View line, int horizontal, int vertical, int lineLength, long time, OnAnimationEndLinster onAnimationEndLinster) {
		//1.获取线的起始和终止坐标
		int[] location = new  int[2] ;
//        line.getLocationInWindow(location); //获取在当前窗口内的绝对坐标，含toolBar
//		line.getLocationOnScreen(location); //获取在整个屏幕内的绝对坐标，含statusBar
		location[0] = line.getLeft();
		location[1] = line.getTop();
		int lineWidth = line.getWidth();
		int lineHeight = line.getHeight();
		//根据linelength(1,0,-1)判断是前半段还是后半段
		if (lineLength == 0) {
			//2.根据方向设置动画
			//水平方向和垂直方向
			if (horizontal != 0) {
				int x1 = location[0];
				int y1 = location[1] + lineHeight / 2 - point.getHeight() / 2 ;
				int x2 = x1 + lineWidth - point.getWidth();
				int y2 = y1;
				//水平正方向
				if (horizontal == 1) {
					startAnimationByDerition(context,x1, y1, x2, y2, point, time, onAnimationEndLinster);
				}
				//水平负方向
				if (horizontal == -1) {
					startAnimationByDerition(context,x2, y2, x1, y1, point, time, onAnimationEndLinster);
				}
			} else {
				int x1 = location[0] - point.getWidth() / 2 + lineWidth / 2;
				int y1 = location[1];
				int x2 = x1;
				int y2 = y1 + lineHeight - point.getHeight();
				//垂直向下
				if (vertical == 1) {
					startAnimationByDerition(context,x1, y1, x2, y2, point, time, onAnimationEndLinster);
				}
				//垂直向上
				if (vertical == -1) {
					startAnimationByDerition(context,x2, y2, x1, y1, point, time, onAnimationEndLinster);
				}
			}
		}else {

			//前半段操作
			if (lineLength == 1){
				//水平方向和垂直方向
				if (horizontal != 0) {
					int x1 = location[0];
					int y1 = location[1] + lineHeight / 2 - point.getHeight() / 2;
					int x2 = x1 + lineWidth/2 - point.getWidth();
					int y2 = y1;
					//水平正方向
					if (horizontal == 1) {
						startAnimationByDerition(context,x1, y1, x2, y2, point, time, onAnimationEndLinster);
					}
					//水平负方向
					if (horizontal == -1) {
						startAnimationByDerition(context,x2, y2, x1, y1, point, time, onAnimationEndLinster);
					}
				} else {
					int x1 = location[0] - point.getWidth() / 2 + lineWidth / 2;
					int y1 = location[1];
					int x2 = x1;
					int y2 = y1 + lineHeight/2 - point.getHeight();
					//垂直向下
					if (vertical == 1) {
						startAnimationByDerition(context,x1, y1, x2, y2, point, time, onAnimationEndLinster);
					}
					//垂直向上
					if (vertical == -1) {
						startAnimationByDerition(context,x2, y2, x1, y1, point, time, onAnimationEndLinster);
					}
				}
				//后半段操作
			}else if (lineLength == -1){
				if (horizontal != 0) {
					int x1 = location[0] + lineWidth/2;
					int y1 = location[1] + lineHeight / 2 - point.getHeight() / 2 ;
					int x2 = x1 + lineWidth/2 - point.getWidth();
					int y2 = y1;
					//水平正方向
					if (horizontal == 1) {
						startAnimationByDerition(context,x1, y1, x2, y2, point, time, onAnimationEndLinster);
					}
					//水平负方向
					if (horizontal == -1) {
						startAnimationByDerition(context,x2, y2, x1, y1, point, time, onAnimationEndLinster);
					}
				} else {
					int x1 = location[0] - point.getWidth() / 2 + lineWidth / 2;
					int y1 = location[1]  + lineHeight/2;
					int x2 = x1;
					int y2 = y1 + lineHeight/2 - point.getHeight();
					//垂直向下
					if (vertical == 1) {
						startAnimationByDerition(context,x1, y1, x2, y2, point, time, onAnimationEndLinster);
					}
					//垂直向上
					if (vertical == -1) {
						startAnimationByDerition(context,x2, y2, x1, y1, point, time, onAnimationEndLinster);
					}
				}
			}
		}
	}

	/**
	 * 通过坐标点，在两点中移动
	 * @param x1：起始横坐标
	 * @param y1：起始纵坐标
	 * @param x2：终止横坐标
	 * @param y2：终止纵坐标
	 * @param view：移动的对象
	 * @param time：移动的时间
	 * @param listener：动画完成时监听
	 */
	private static void startAnimationByDerition(Context context,int x1, int y1, int x2, int y2, View view,long time ,final OnAnimationEndLinster listener) {
//        AnimationSet animationSet = new AnimationSet(true);
//        LogUtil.i("y1:"+y1 +";y2:"+y2 );
		AnimationSet set = new AnimationSet(true);
		//平移动画
		TranslateAnimation translateAnimation =
				new TranslateAnimation(
						Animation.ABSOLUTE,x1,
						Animation.ABSOLUTE,x2,
						Animation.ABSOLUTE,y1 ,
						Animation.ABSOLUTE,y2);

		translateAnimation.setDuration(time);
		//缩放动画
		ScaleAnimation scaleAnimation = new ScaleAnimation(1,0.5f,1,0.5f,
				Animation.INFINITE,0.5f,Animation.INFINITE,0.5f);
		scaleAnimation.setFillAfter(true);
//		scaleAnimation.setRepeatMode(ScaleAnimation.REVERSE);
		scaleAnimation.setRepeatCount(1000);
		scaleAnimation.setDuration(100);
		set.addAnimation(translateAnimation);
//		set.addAnimation(scaleAnimation);
		view.startAnimation(set);
		//3.动画结束后回调监听
		set.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				listener.endAnimation();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
	}
	private static int screenHeightNoStatusBar;

	/**
	 * 获取除去状态栏的屏幕高度
	 * @param context
	 * @return
	 */
	public static int getScreenHeightNoStatusBar(Context context){
		if (screenHeightNoStatusBar == 0){
			screenHeightNoStatusBar = getScreenHeight(context) - getStatusBarHeight(context);
		}
		return screenHeightNoStatusBar;
	}
		/**
	 * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
	 *@param timeFormat 时间格式
	 * @param time
	 * @return
	 */
	public static long dataToTimetamps(String timeFormat,String time) {
		SimpleDateFormat sdr = new SimpleDateFormat(timeFormat);
		Date date;
		long timesTamp = System.currentTimeMillis();
		try {
			date = sdr.parse(time);
			timesTamp = date.getTime() +1000;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return timesTamp;
	}
	/**
	 * 曲线图汇总
	 */
	/**
	 * 初始化折线图
	 * @param lineChart：折线图控件
	 * @param format：y轴刻度显示类型
	 *              1：代表转化成百分比
	 *              0：代表转换成数字保留一位小数
	 * @param unit:代表刻度单位
	 * @param isTouchEnable:是否允许触摸显示数值
	 * @param XYTextColorId:xy轴文本颜色
	 * @param XAxisLineColorId:x轴颜色
	 * @param yGridLineColorId:y轴网格线颜色
	 * @param highTextColor:高亮文本颜色：0：代表默认白色；其他：设置成具体颜色
	 * @param showXYName:显示高亮文本Xy名称
	 * @param xTextId:高亮X文本id
	 * @param yTextId:高亮Y文本id
	 */
	public static void initLineChart(Context context, LineChart lineChart, int format, final String unit, boolean isTouchEnable, int XYTextColorId, int XAxisLineColorId, int yGridLineColorId, int highTextColor, boolean showXYName, int xTextId, int yTextId) {
		//颜色转换
		int mXYTextColorId = ContextCompat.getColor(context,XYTextColorId);
		int mXAxisLineColorId = ContextCompat.getColor(context,XAxisLineColorId);
		int myGridLineColorId = ContextCompat.getColor(context,yGridLineColorId);
		lineChart.setDrawGridBackground(false);
		// no description text
		lineChart.getDescription().setEnabled(false);
		// enable touch gestures
		lineChart.setTouchEnabled(isTouchEnable);
		// enable scaling and dragging
		lineChart.setDragEnabled(true);
		lineChart.setScaleEnabled(false);
		lineChart.setPinchZoom(true);//设置mark轴
		if (isTouchEnable){
			if (highTextColor == 0){
				MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view);
				mv.setChartView(lineChart); // For bounds control
				lineChart.setMarker(mv); // Set the marker to the chart
			}else {
				if (showXYName){
					MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view,highTextColor,showXYName,xTextId,yTextId);
					mv.setChartView(lineChart); // For bounds control
					lineChart.setMarker(mv); // Set the marker to the chart
				}else {
					MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view,highTextColor);
					mv.setChartView(lineChart); // For bounds control
					lineChart.setMarker(mv); // Set the marker to the chart
				}
			}
		}
		XAxis xAxis = lineChart.getXAxis();
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
		xAxis.setAxisMinimum(0f);
		xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
		xAxis.setDrawAxisLine(true);//是否绘制轴线
		xAxis.setDrawGridLines(false);//设置x轴上每个点对应的线
		xAxis.setDrawLabels(true);
		xAxis.setTextColor(mXYTextColorId);//设置x轴文本颜色
//        xAxis.setGridColor(colorId);
		xAxis.setAxisLineColor(mXAxisLineColorId);
		xAxis.enableAxisLineDashedLine(10f,0f,0f);
		xAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
//				return String.format("%.2f", value).replace",":");
				return sdf_hm.format(new Date((long) (value * minTamp)));
			}
		});
		YAxis leftAxis = lineChart.getAxisLeft();
		if (format==1){
			leftAxis.setValueFormatter(new PercentFormatter());
			leftAxis.setAxisMaximum(100);
		}else if (format == 0){
			leftAxis.setValueFormatter(new IAxisValueFormatter() {
				@Override
				public String getFormattedValue(float value, AxisBase axis) {
//                    return value + "w";
					return (float)(Math.round(value*10))/10 + unit;
				}
			});
		}
		leftAxis.setTextColor(mXYTextColorId);
		leftAxis.enableGridDashedLine(10f,10f,0f);
		leftAxis.setDrawAxisLine(false);
		leftAxis.setAxisMinimum(0f);
		leftAxis.setGridColor(myGridLineColorId);
//		leftAxis.setAxisLineColor(colorId);
		YAxis rightAxis = lineChart.getAxisRight();
		rightAxis.setEnabled(false);
////        xAxis.setTypeface(mTf); // 设置字体
//        xAxis.setEnabled(false);
//        // 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine
//        xAxis.setDrawAxisLine(true);
//
//        // 前面xAxis.setEnabled(false);则下面绘制的Grid不会有"竖的线"（与X轴有关）
//        xAxis.setDrawGridLines(true); // 效果如下图
		lineChart.animateX(2000);
		Legend mLegend = lineChart.getLegend(); // 设置坐标线描述?? 的样式
		mLegend.setEnabled(false);
	}
	/**
	 * 曲线图汇总
	 */
	/**
	 * 初始化折线图
	 * @param lineChart：折线图控件
	 * @param format：y轴刻度显示类型
	 *              1：代表转化成百分比
	 *              0：代表转换成数字保留一位小数
	 * @param unit:代表刻度单位
	 * @param hasXGrid:是否有x轴网格线
	 * @param xGridColor:x轴网格线颜色
	 * @param hasYAxis:是否有y轴
	 * @param yAxixColor:y轴颜色
	 * @param isTouchEnable:是否允许触摸显示数值
	 * @param XYTextColorId:xy轴文本颜色
	 * @param XAxisLineColorId:x轴颜色
	 * @param yGridLineColorId:y轴网格线颜色
	 * @param highTextColor:高亮文本颜色：0：代表默认白色；其他：设置成具体颜色
	 * @param showXYName:显示高亮文本Xy名称
	 * @param xTextId:高亮X文本id
	 * @param yTextId:高亮Y文本id
	 */
	public static void initLineChart(Context context, LineChart lineChart, int format, final String unit,boolean hasXGrid,int xGridColor,boolean hasYAxis,int yAxixColor, boolean isTouchEnable, int XYTextColorId, int XAxisLineColorId, int yGridLineColorId, int highTextColor, boolean showXYName, int xTextId, int yTextId) {
		//颜色转换
		int mXYTextColorId = ContextCompat.getColor(context,XYTextColorId);
		int mXAxisLineColorId = ContextCompat.getColor(context,XAxisLineColorId);
		int myGridLineColorId = ContextCompat.getColor(context,yGridLineColorId);
		int myXGridColor = ContextCompat.getColor(context,xGridColor);
		int myYAxixColor = ContextCompat.getColor(context,yAxixColor);
		lineChart.setDrawGridBackground(false);
		// no description text
		lineChart.getDescription().setEnabled(false);
		// enable touch gestures
		lineChart.setTouchEnabled(isTouchEnable);
		// enable scaling and dragging
		lineChart.setDragEnabled(true);
		lineChart.setScaleEnabled(false);
		lineChart.setPinchZoom(true);//设置mark轴
		if (isTouchEnable){
			if (highTextColor == 0){
				MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view);
				mv.setChartView(lineChart); // For bounds control
				lineChart.setMarker(mv); // Set the marker to the chart
			}else {
				if (showXYName){
					MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view,highTextColor,showXYName,xTextId,yTextId);
					mv.setChartView(lineChart); // For bounds control
					lineChart.setMarker(mv); // Set the marker to the chart
				}else {
					MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view,highTextColor);
					mv.setChartView(lineChart); // For bounds control
					lineChart.setMarker(mv); // Set the marker to the chart
				}
			}
		}
		XAxis xAxis = lineChart.getXAxis();
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
		xAxis.setAxisMinimum(0f);
		xAxis.setEnabled(true);//设置轴启用或禁用 如果禁用以下的设置全部不生效
		xAxis.setDrawAxisLine(true);//是否绘制轴线
		xAxis.setDrawGridLines(hasXGrid);//设置x轴上每个点对应的线
		xAxis.setGridColor(myXGridColor);
		xAxis.setDrawLabels(true);
		xAxis.setAxisLineWidth(1.0f);
		xAxis.setGridLineWidth(0.5f);
		xAxis.setTextColor(mXYTextColorId);//设置x轴文本颜色
//        xAxis.setGridColor(colorId);
		xAxis.setAxisLineColor(mXAxisLineColorId);
//		xAxis.enableAxisLineDashedLine(10f,0f,0f);
		xAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
//				return String.format("%.2f", value).replace",":");
				return sdf_hm.format(new Date((long) (value * minTamp)));
			}
		});
		YAxis leftAxis = lineChart.getAxisLeft();
		if (format==1){
			leftAxis.setValueFormatter(new PercentFormatter());
			leftAxis.setAxisMaximum(100);
		}else if (format == 0){
			leftAxis.setValueFormatter(new IAxisValueFormatter() {
				@Override
				public String getFormattedValue(float value, AxisBase axis) {
//                    return value + "w";
					return (float)(Math.round(value*10))/10 + unit;
				}
			});
		}
		leftAxis.setTextColor(mXYTextColorId);
//		leftAxis.enableGridDashedLine(10f,10f,0f);//虚线
		leftAxis.setDrawAxisLine(hasYAxis);
		leftAxis.setAxisLineColor(myYAxixColor);
		leftAxis.setAxisMinimum(0f);
		leftAxis.setAxisLineWidth(1.0f);
		leftAxis.setGridLineWidth(0.5f);
		leftAxis.setGridColor(myGridLineColorId);
//		leftAxis.setAxisLineColor(colorId);
		YAxis rightAxis = lineChart.getAxisRight();
		rightAxis.setEnabled(false);
////        xAxis.setTypeface(mTf); // 设置字体
//        xAxis.setEnabled(false);
//        // 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine
//        xAxis.setDrawAxisLine(true);
//
//        // 前面xAxis.setEnabled(false);则下面绘制的Grid不会有"竖的线"（与X轴有关）
//        xAxis.setDrawGridLines(true); // 效果如下图
		lineChart.animateX(2000);
		Legend mLegend = lineChart.getLegend(); // 设置坐标线描述?? 的样式
		mLegend.setEnabled(false);
	}
	/**
	 * 优化折线图数据
	 * @param dataList：原始数据集合
	 * @param count：优化数量；3：代表3笔数据留一笔；4：代表4笔数据留一笔
	 * @return
	 */
	public static List<ArrayList<Entry>> removeDataLineChart(List<ArrayList<Entry>> dataList,int count){
		List<ArrayList<Entry>> newDatalist = new ArrayList<>();
		if (dataList != null){
			int size = dataList.size();
			if (size>0){
				for (int i = 0;i<size;i++){
					ArrayList<Entry> newList = new ArrayList<>();
					ArrayList<Entry> entrys = dataList.get(i);
					for (int j=0,jsize = entrys.size();j<jsize;j++){
						if (j%count == 0){
							newList.add(entrys.get(j));
						}
					}
					newDatalist.add(newList);
				}
			}
		}
		return newDatalist;
	}
	/**
	 * 初始化折线图
	 * @param lineChart：折线图控件
	 * @param format：y轴刻度显示类型
	 *              1：代表转化成百分比
	 *              0：代表转换成数字保留一位小数
	 * @param unit:代表刻度单位
	 * @param isTouchEnable:是否允许触摸显示数值
	 * @param XYTextColorId:xy轴文本颜色
	 * @param XAxisLineColorId:x轴颜色
	 * @param yGridLineColorId:y轴网格线颜色
	 * @param highTextColor:高亮文本颜色：0：代表默认白色；其他：设置成具体颜色
	 */
	public static void initLineChart(Context context,LineChart lineChart, int format,final String unit,boolean isTouchEnable,int XYTextColorId,int XAxisLineColorId,int yGridLineColorId,int highTextColor) {
		initLineChart(context,lineChart,format,unit,isTouchEnable,XYTextColorId,XAxisLineColorId,yGridLineColorId,highTextColor,false,0 ,0);
	}

	/**
	 *初始化饼图
	 * @param mChart：饼图控件view
	 */
	public static void initPieChart(PieChart mChart) {
		mChart.setUsePercentValues(true);
		mChart.getDescription().setEnabled(false);
		mChart.setExtraOffsets(10, 10, 10, 10);
		mChart.setRotationAngle(45);                   //设置pieChart图表起始角度
		mChart.setRotationEnabled(true);              //设置pieChart图表是否可以手动旋转
		mChart.setDrawSlicesUnderHole(true);
//        mChart.setDrawSliceText(true);
//        mChart.setDrawEntryLabels(true);
//        mChart.setDrawCenterText(false);
//        mChart.setHoleRadius(0f);
//        mChart.setTransparentCircleRadius(0f);
		mChart.setDragDecelerationFrictionCoef(0.95f);
//        mChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
		mChart.setDrawHoleEnabled(false);//设置饼图中心高亮是否显示
//        mChart.setHoleColor(Color.WHITE);
//        mChart.setTransparentCircleColor(Color.WHITE);
//        mChart.setTransparentCircleAlpha(110);
//        mChart.setHoleRadius(58f);
//        mChart.setTransparentCircleRadius(61f);
		// enable rotation of the chart by touch
		mChart.setHighlightPerTapEnabled(true);
		// mChart.setUnit(" €");
		// mChart.setDrawUnitsInChart(true);
		// add a selection listener
//        setData(mChart,null);
		mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
		// mChart.spin(2000, 0, 360);
		Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
		l.setEnabled(false);
	}

	/**
	 * 初始化柱状图
	 * @param context
	 * @param mChart
	 * @param unit
	 * @param isTouchEnable
	 * @param XYTextColorId
	 * @param XAxisLineColorId
	 * @param yGridLineColorId
	 * @param labelCount
	 */
	public static void initBarChart(Context context,BarChart mChart,final String unit,boolean isTouchEnable,int XYTextColorId,int XAxisLineColorId,int yGridLineColorId,int labelCount) {
		//颜色转换
		int mXYTextColorId = ContextCompat.getColor(context,XYTextColorId);
		int mXAxisLineColorId = ContextCompat.getColor(context,XAxisLineColorId);
		int myGridLineColorId = ContextCompat.getColor(context,yGridLineColorId);
		mChart.getDescription().setEnabled(false);
		mChart.setTouchEnabled(isTouchEnable);
		// enable scaling and dragging
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(false);
//        mChart.setDrawBorders(true);
		// scaling can now only be done on x- and y-axis separately
		mChart.setPinchZoom(false);
		mChart.setDrawBarShadow(false);
//        mChart.setMaxVisibleValueCount(barGroupCount);
//        mChart.setDrawYValues(true); // 设置是否显示y轴的值的数据
//        mChart.setValuePaintColor(colorId);//设置表格中y轴的值的颜色，但是必须设置
		mChart.setDrawGridBackground(false);
		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it
		if (isTouchEnable){
			MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view);
			mv.setChartView(mChart); // For bounds control
			mChart.setMarker(mv); // Set the marker to the chart
		}
		mChart.animateY(1500);
		Legend l = mChart.getLegend();
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
		l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setForm(Legend.LegendForm.CIRCLE);//这是左边显示小图标的形状
		l.setDrawInside(true);
		l.setYOffset(0f);
		l.setXOffset(10f);
		l.setYEntrySpace(0f);
		l.setTextSize(context.getResources().getDimension(R.dimen.x3));
		l.setTextColor(mXYTextColorId);
		XAxis xAxis = mChart.getXAxis();
		xAxis.setDrawGridLines(false);
		xAxis.setGranularity(1f);
		xAxis.setLabelCount(labelCount);
		xAxis.setCenterAxisLabels(true);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
		xAxis.setTextColor(mXYTextColorId);//设置x轴文本颜色
		xAxis.setAxisLineColor(mXAxisLineColorId);
		xAxis.setDrawLabels(true);
		xAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				//                    String day = MyControl.getFormatDate("dd",new Date(timeTamps));
//                    String day = sdf.format(new Date(timeTamps));
//                    LogUtil.i("dayday-->"+day);
//                    Float key = Float.parseFloat(day);

//                String i = String.valueOf((int)value);
//                StringBuilder sb = new StringBuilder(i);
//                if (i.length()>2) {
//                    sb.insert(i.length() - 2, ".");
//                }
				String day = sdf.format(new Date((long)(value*dayTamp)));
				LogUtil.i("day-->"+day);
//                String day = sdf.format(new Date(((int)value)*dayTamp));
				return day;
			}
		});
		YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setAxisMinValue(0.0f);//设置0值下面没有间隙
		leftAxis.setAxisMinimum(0.0f);//设置0值下面没有间隙
//        leftAxis.setValueFormatter(new LargeValueFormatter());
//        leftAxis.setValueFormatter(new DefaultAxisValueFormatter(1));
		leftAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				return (float)(Math.round(value*10))/10 + unit;
			}
		});
		leftAxis.setDrawAxisLine(false);
		leftAxis.enableGridDashedLine(10f,10f,0f);
		leftAxis.setTextColor(mXYTextColorId);
		leftAxis.setGridColor(myGridLineColorId);
//        leftAxis.setAxisLineColor(colorId);
		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setEnabled(false);
	}
	/**
	 * 初始化柱状图
	 * @param context
	 * @param mChart
	 * @param unit
	 * @param isTouchEnable
	 * @param XYTextColorId
	 * @param XAxisLineColorId
	 * @param yGridLineColorId
	 * @param labelCount
	 */
	public static void initBarChartEnergy(Context context,BarChart mChart,final String unit,boolean isTouchEnable,int XYTextColorId,int XAxisLineColorId,int yGridLineColorId,int labelCount,boolean hasYAxis,int yAxisColor,boolean hasXGrid,int xGridColor,int heighLightColor) {
		//颜色转换
		int mXYTextColorId = ContextCompat.getColor(context,XYTextColorId);
		int mXAxisLineColorId = ContextCompat.getColor(context,XAxisLineColorId);
		int myGridLineColorId = ContextCompat.getColor(context,yGridLineColorId);
		int myYAxisColor = ContextCompat.getColor(context,yAxisColor);
		int myXGridColor = ContextCompat.getColor(context,xGridColor);
		mChart.getDescription().setEnabled(false);
		mChart.setTouchEnabled(isTouchEnable);
		// enable scaling and dragging
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(false);
//        mChart.setDrawBorders(true);
		// scaling can now only be done on x- and y-axis separately
		mChart.setPinchZoom(false);
		mChart.setDrawBarShadow(false);
//        mChart.setMaxVisibleValueCount(barGroupCount);
//        mChart.setDrawYValues(true); // 设置是否显示y轴的值的数据
//        mChart.setValuePaintColor(colorId);//设置表格中y轴的值的颜色，但是必须设置
		mChart.setDrawGridBackground(false);
		// create a custom MarkerView (extend MarkerView) and specify the layout
		// to use for it
		if (isTouchEnable){
			MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view,heighLightColor);
			mv.setChartView(mChart); // For bounds control
			mChart.setMarker(mv); // Set the marker to the chart
		}
		mChart.animateY(1500);
		Legend l = mChart.getLegend();
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
		l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setForm(Legend.LegendForm.CIRCLE);//这是左边显示小图标的形状
		l.setDrawInside(true);
		l.setYOffset(0f);
		l.setXOffset(10f);
		l.setYEntrySpace(0f);
		l.setTextSize(context.getResources().getDimension(R.dimen.x3));
		l.setTextColor(mXYTextColorId);
		XAxis xAxis = mChart.getXAxis();
		xAxis.setDrawGridLines(hasXGrid);
		xAxis.setGridColor(myXGridColor);
		xAxis.setAxisLineWidth(1.0f);
		xAxis.setGridLineWidth(0.5f);
		xAxis.setGranularity(1f);
		xAxis.setLabelCount(labelCount);
		xAxis.setCenterAxisLabels(true);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
		xAxis.setTextColor(mXYTextColorId);//设置x轴文本颜色
		xAxis.setAxisLineColor(mXAxisLineColorId);
		xAxis.setDrawLabels(true);
		xAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				//                    String day = MyControl.getFormatDate("dd",new Date(timeTamps));
//                    String day = sdf.format(new Date(timeTamps));
//                    LogUtil.i("dayday-->"+day);
//                    Float key = Float.parseFloat(day);

//                String i = String.valueOf((int)value);
//                StringBuilder sb = new StringBuilder(i);
//                if (i.length()>2) {
//                    sb.insert(i.length() - 2, ".");
//                }
				String day = sdf.format(new Date((long)(value*dayTamp)));
				LogUtil.i("day-->"+day);
//                String day = sdf.format(new Date(((int)value)*dayTamp));
				return day;
			}
		});
		YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setAxisMinValue(0.0f);//设置0值下面没有间隙
		leftAxis.setAxisMinimum(0.0f);//设置0值下面没有间隙
//        leftAxis.setValueFormatter(new LargeValueFormatter());
//        leftAxis.setValueFormatter(new DefaultAxisValueFormatter(1));
		leftAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				return (float)(Math.round(value*10))/10 + unit;
			}
		});
		leftAxis.setDrawAxisLine(hasYAxis);
		leftAxis.setAxisLineColor(myYAxisColor);
		leftAxis.setAxisLineWidth(1.0f);
		leftAxis.setGridLineWidth(0.5f);
//		leftAxis.enableGridDashedLine(10f,10f,0f);
		leftAxis.setTextColor(mXYTextColorId);
		leftAxis.setGridColor(myGridLineColorId);
//        leftAxis.setAxisLineColor(colorId);
		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setEnabled(false);
	}
	public static void initBarChart(Context context,BarChart mChart,final String unit,boolean isTouchEnable,int XYTextColorId,int XAxisLineColorId,int yGridLineColorId) {
		//颜色转换
		int mXYTextColorId = ContextCompat.getColor(context,XYTextColorId);
		int mXAxisLineColorId = ContextCompat.getColor(context,XAxisLineColorId);
		int myGridLineColorId = ContextCompat.getColor(context,yGridLineColorId);
		mChart.getDescription().setEnabled(false);
		mChart.setTouchEnabled(isTouchEnable);
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(false);
		mChart.setPinchZoom(false);
		mChart.setDrawBarShadow(false);
		mChart.setDrawGridBackground(false);
		if (isTouchEnable){
			MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view);
			mv.setChartView(mChart); // For bounds control
			mChart.setMarker(mv); // Set the marker to the chart
		}
		mChart.animateY(1500);
		Legend l = mChart.getLegend();
		l.setEnabled(false);
		XAxis xAxis = mChart.getXAxis();
		xAxis.setDrawGridLines(false);
		xAxis.setGranularity(1f);
//		xAxis.setAxisMinimum(1f);
//		xAxis.setCenterAxisLabels(true);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
		xAxis.setTextColor(mXYTextColorId);//设置x轴文本颜色
		xAxis.setAxisLineColor(mXAxisLineColorId);
		xAxis.setDrawLabels(true);
		xAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				String day = (int) value+"";
				LogUtil.i("day-->"+day);
				return day;
			}
		});
		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.setAxisMinimum(0.0f);//设置0值下面没有间隙
		leftAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				return (float)(Math.round(value*10))/10 + unit;
			}
		});
		leftAxis.setDrawAxisLine(false);
		leftAxis.enableGridDashedLine(10f,10f,0f);
		leftAxis.setTextColor(mXYTextColorId);
		leftAxis.setGridColor(myGridLineColorId);
		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setEnabled(false);
	}
	public static void initBarChart(Context context,BarChart mChart,final String unit,boolean isTouchEnable,int XYTextColorId,int XAxisLineColorId,int yGridLineColorId,boolean hasYAxis,int yAxisColor,boolean hasXGrid,int xGridColor,int heighLightColor) {
		//颜色转换
		int mXYTextColorId = ContextCompat.getColor(context,XYTextColorId);
		int mXAxisLineColorId = ContextCompat.getColor(context,XAxisLineColorId);
		int myGridLineColorId = ContextCompat.getColor(context,yGridLineColorId);
		int myYAxisColor = ContextCompat.getColor(context,yAxisColor);
		int myXGridColor = ContextCompat.getColor(context,xGridColor);
		mChart.getDescription().setEnabled(false);
		mChart.setTouchEnabled(isTouchEnable);
		mChart.setDragEnabled(true);
		mChart.setScaleEnabled(false);
		mChart.setPinchZoom(false);
		mChart.setDrawBarShadow(false);
		mChart.setDrawGridBackground(false);
		if (isTouchEnable){
			MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view,heighLightColor);
			mv.setChartView(mChart); // For bounds control
			mChart.setMarker(mv); // Set the marker to the chart
		}
		mChart.animateY(1500);
		Legend l = mChart.getLegend();
		l.setEnabled(false);
		XAxis xAxis = mChart.getXAxis();
		xAxis.setDrawGridLines(hasXGrid);
		xAxis.setGridColor(myXGridColor);
		xAxis.setAxisLineWidth(1.0f);
		xAxis.setGridLineWidth(0.5f);
		xAxis.setGranularity(1f);
//		xAxis.setAxisMinimum(1f);
//		xAxis.setCenterAxisLabels(true);
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
		xAxis.setTextColor(mXYTextColorId);//设置x轴文本颜色
		xAxis.setAxisLineColor(mXAxisLineColorId);
		xAxis.setDrawLabels(true);
		xAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				String day = (int) value+"";
				LogUtil.i("day-->"+day);
				return day;
			}
		});
		YAxis leftAxis = mChart.getAxisLeft();
		leftAxis.setAxisMinimum(0.0f);//设置0值下面没有间隙
		leftAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				return (float)(Math.round(value*10))/10 + unit;
			}
		});
		leftAxis.setDrawAxisLine(hasYAxis);
		leftAxis.setAxisLineColor(myYAxisColor);
		leftAxis.setAxisLineWidth(1.0f);
		leftAxis.setGridLineWidth(0.5f);
//		leftAxis.enableGridDashedLine(10f,10f,0f);
		leftAxis.setTextColor(mXYTextColorId);
		leftAxis.setGridColor(myGridLineColorId);
		YAxis rightAxis = mChart.getAxisRight();
		rightAxis.setEnabled(false);
	}
	/**
	 * 解析储能能源折线图和饼图数据
	 * @param dataList
	 * @param jsonObject
	 * @param itemCount
	 * @return
	 * @throws JSONException
	 */
	public static List<ArrayList<Entry>> parseLineChartData(List<ArrayList<Entry>> dataList, JSONObject jsonObject,int itemCount) throws Exception
	{
		if(jsonObject.length()>0){
			List<Map<Float,Object>> mList = new ArrayList<>();
			for (int i = 0;i< itemCount;i++){
				Map<Float, Object> data = new HashMap<>();
				mList.add(data);
			}
			Iterator it = jsonObject.keys();
			while (it.hasNext()){
				String strKey = String.valueOf(it.next().toString());
				JSONObject value =  jsonObject.getJSONObject(strKey);
//				Float key = 0f;
//				if(strKey.contains(":")){
//					strKey=strKey.replace(":",".");
//					key = Float.parseFloat(strKey);
//				}
				float key = sdf_hm.parse(strKey).getTime()/minTamp;
				mList.get(0).put(key, value.get("ppv").toString());
				mList.get(1).put(key, value.get("sysOut").toString());
				mList.get(2).put(key, value.get("userLoad").toString());
				mList.get(3).put(key, value.get("pacToUser").toString());
			}
			List< List<Map.Entry<Float, Object>>> infoIdList = new ArrayList<>();
			for (int i = 0;i< mList.size();i++){
				List<Map.Entry<Float, Object>> infoId = new ArrayList<>(mList.get(i).entrySet());
				Collections.sort(infoId, new Comparator<Map.Entry<Float, Object>>() {
					@Override
					public int compare(Map.Entry<Float, Object> o1, Map.Entry<Float, Object> o2) {
						return o1.getKey().compareTo(o2.getKey());
					}
				});

				infoIdList.add(infoId);
			}
			for (int j = 0;j< infoIdList.size();j++){
				List<Map.Entry<Float, Object>> infoId = infoIdList.get(j);
				List<Entry> entrys = dataList.get(j);
				for (int i = 0,size =infoId.size(); i < size; i++) {
					Map.Entry<Float, Object> id = infoId.get(i);
					Entry entry = new Entry(id.getKey(),Float.valueOf(id.getValue().toString()));
//                    Entry entry = new Entry(Float.valueOf(id.getKey()),Float.valueOf(id.getKey()));
					entrys.add(entry);
				}
			}
			return dataList;
		}else{
			for (int j = 0;j<itemCount;j++){
				List<Entry> entrys = dataList.get(j);
				for (int i = 0; i < 7; i++) {
					Entry entry = new Entry(i,0);
					entrys.add(entry);
				}
			}
			return dataList;
		}
	}
	public static List<ArrayList<Entry>> parseLineChart1Data(List<ArrayList<Entry>> dataList, JSONObject jsonObject,int itemCount) throws Exception
	{
		if(jsonObject.length()>0){
			List<Map<Float,Object>> mList = new ArrayList<>();
			for (int i = 0;i< itemCount;i++){
				Map<Float, Object> data = new HashMap<>();
				mList.add(data);
			}
			Iterator it = jsonObject.keys();
			while (it.hasNext()){
				String strKey = String.valueOf(it.next().toString());
				String value =  jsonObject.get(strKey).toString();
//				Float key = 0f;
				if (strKey.length()>6) {
					strKey = strKey.substring(strKey.length() - 5);
				}
				float key = sdf_hm.parse(strKey).getTime()/minTamp;
				mList.get(0).put(key, value);
			}
			List< List<Map.Entry<Float, Object>>> infoIdList = new ArrayList<>();
			for (int i = 0;i< mList.size();i++){
				List<Map.Entry<Float, Object>> infoId = new ArrayList<>(mList.get(i).entrySet());
				Collections.sort(infoId, new Comparator<Map.Entry<Float, Object>>() {
					@Override
					public int compare(Map.Entry<Float, Object> o1, Map.Entry<Float, Object> o2) {
						return o1.getKey().compareTo(o2.getKey());
					}
				});

				infoIdList.add(infoId);
			}
			for (int j = 0;j< infoIdList.size();j++){
				List<Map.Entry<Float, Object>> infoId = infoIdList.get(j);
				List<Entry> entrys = dataList.get(j);
				for (int i = 0,size =infoId.size(); i < size; i++) {
					Map.Entry<Float, Object> id = infoId.get(i);
					Entry entry = new Entry(id.getKey(),Float.valueOf(id.getValue().toString()));
//                    Entry entry = new Entry(Float.valueOf(id.getKey()),Float.valueOf(id.getKey()));
					entrys.add(entry);
				}
			}
			return dataList;
		}else{
			for (int j = 0;j<itemCount;j++){
				List<Entry> entrys = dataList.get(j);
				for (int i = 0; i < 7; i++) {
					Entry entry = new Entry(i,0);
					entrys.add(entry);
				}
			}
			return dataList;
		}
	}
	/**
	 * 设置曲线图数据
	 * @param context
	 * @param lineChart：控件
	 * @param dataList：数据集
	 * @param colors：曲线线条颜色集合
	 * @param colors_a：填充曲线内部颜色集合
	 * @param count：曲线条数
	 * @param highLightColor：高亮颜色
	 */
	public static void setLineChartData(Context context,LineChart lineChart,List<ArrayList<Entry>> dataList,int[] colors,int[] colors_a,int count,int highLightColor) {
		if (lineChart == null || dataList == null) return;
		List<ILineDataSet> 	dataSets = new ArrayList<>();
		LineData lineData = lineChart.getData();
		//计算最小横坐标
		float minX = dataList.get(0).get(0).getX();
		if (lineData != null && lineData.getDataSetCount() >= count ){
			for (int i=0;i<count;i++){
				LineDataSet dataSet = (LineDataSet) lineData.getDataSetByIndex(i);
				dataSet.setValues(dataList.get(i));
			}
			lineChart.getXAxis().setAxisMinimum(minX);
			lineChart.getData().notifyDataChanged();
			lineChart.notifyDataSetChanged();
		}else {
			for (int i=0;i<count;i++){
				LineDataSet dataSet = new LineDataSet(dataList.get(i),"");
				dataSet.setDrawIcons(false);
				//设置启用立体效果
				dataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
				//设置立体效果
				dataSet.setCubicIntensity(0.5f);
				// 设置为true，将绘制 LineDataSet 的线圈。默认为true。
				dataSet.setDrawCircles(false);
//            dataSet.enableDashedLine(10f, 5f, 0f);
//            dataSet.enableDashedHighlightLine(10f, 5f, 0f);
				dataSet.setHighLightColor(ContextCompat.getColor(context,highLightColor));//设置高亮线颜色
				dataSet.setColor(ContextCompat.getColor(context, colors[i]));
//            dataSet.setCircleColor(colors[i]);
				dataSet.setLineWidth(1.5f);
//            dataSet.setCircleRadius(3f);
				dataSet.setDrawCircleHole(false);
				dataSet.setValueTextSize(9f);
				dataSet.setDrawFilled(true);
				dataSet.setFormLineWidth(1f);
				dataSet.setDrawValues(false);
				dataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
				dataSet.setFormSize(15.0f);
				if (Utils.getSDKInt() >= 18) {
					// fill drawable only supported on api level 18 and above
					Drawable drawable = ContextCompat.getDrawable(context, colors_a[i]);
					dataSet.setFillDrawable(drawable);
				} else {
					dataSet.setFillColor(ContextCompat.getColor(context, colors_a[i]));
				}
				dataSets.add(dataSet);
			}
			lineData = new LineData(dataSets);
			lineChart.setData(lineData);
			lineChart.getXAxis().setAxisMinimum(minX);
			lineData.notifyDataChanged();
			lineChart.notifyDataSetChanged();
		}
		lineChart.animateX(2000);
		lineChart.invalidate();
	}
	public static List<List<BarEntry>> parseBarChart1Data(List<List<BarEntry>> dataList, JSONObject jsonObject,int itemCount) throws Exception
	{
		if(jsonObject.length()>0){
			List<Map<Float,Object>> mList = new ArrayList<>();
			for (int i = 0;i< itemCount;i++){
				Map<Float, Object> data = new HashMap<>();
				mList.add(data);
			}
			Iterator it = jsonObject.keys();
			while (it.hasNext()){
				String strKey = String.valueOf(it.next().toString());
				String value =  jsonObject.get(strKey).toString();
				mList.get(0).put(Float.valueOf(strKey), value);
			}
			List< List<Map.Entry<Float, Object>>> infoIdList = new ArrayList<>();
			for (int i = 0;i< mList.size();i++){
				List<Map.Entry<Float, Object>> infoId = new ArrayList<>(mList.get(i).entrySet());
				Collections.sort(infoId, new Comparator<Map.Entry<Float, Object>>() {
					@Override
					public int compare(Map.Entry<Float, Object> o1, Map.Entry<Float, Object> o2) {
						return o1.getKey().compareTo(o2.getKey());
					}
				});

				infoIdList.add(infoId);
			}
			for (int j = 0;j< infoIdList.size();j++){
				List<Map.Entry<Float, Object>> infoId = infoIdList.get(j);
				List<BarEntry> entrys = dataList.get(j);
				for (int i = 0,size =infoId.size(); i < size; i++) {
					Map.Entry<Float, Object> id = infoId.get(i);
					BarEntry entry = new BarEntry(id.getKey(),Float.valueOf(id.getValue().toString()));
//                    Entry entry = new Entry(Float.valueOf(id.getKey()),Float.valueOf(id.getKey()));
					entrys.add(entry);
				}
			}
			return dataList;
		}else{
			for (int j = 0;j<itemCount;j++){
				List<BarEntry> entrys = dataList.get(j);
				for (int i = 1; i < 7; i++) {
					BarEntry entry = new BarEntry(i,0);
					entrys.add(entry);
				}
			}
			return dataList;
		}
	}
	public static void setBarChartData(Context context, BarChart mChart, List<List<BarEntry>> barYList, int[] colors, int count) {
		setBarChartData(context,mChart,barYList,colors,new int[]{-1},count);
	}
	public static void setBarChartData(Context context, BarChart mChart, List<List<BarEntry>> barYList, int[] colors,int[] colorHights, int count) {
		if (mChart == null || barYList == null) return;
		List<IBarDataSet> barSetDatas = new ArrayList<>();
		BarData barData = mChart.getBarData();
		//计算最小横坐标
		float minX = barYList.get(0).get(0).getX();
		if (barData != null && barData.getDataSetCount() >= count){
			for ( int i = 0;i<count;i++){
				BarDataSet dataSet = (BarDataSet) barData.getDataSetByIndex(i);
				dataSet.setValues(barYList.get(i));
			}
//			mChart.getXAxis().setAxisMaximum(minX);
			mChart.getData().notifyDataChanged();
			mChart.notifyDataSetChanged();
		}else {
			for (int i=0;i<count;i++){
				BarDataSet barSet = new BarDataSet(barYList.get(i),"");
				barSet.setColor(ContextCompat.getColor(context,colors[i]));
				if (colorHights[i] != -1) {
					barSet.setHighLightColor(ContextCompat.getColor(context, colorHights[i]));
				}
				barSet.setDrawValues(false);
//                barSet.setValueTextColor(colorId);
				barSetDatas.add(barSet);
			}
			BarData data = new BarData(barSetDatas);
			mChart.setData(data);
			mChart.setFitBars(true);
			mChart.getBarData().setBarWidth(0.75f);
//			mChart.getXAxis().setAxisMinimum(minX);
		}
		mChart.animateY(3000);
		mChart.invalidate();
	}
	/**
	 * 根据字体数量及控件大小设置字体大小
	 * @param
	 */
	public static void autoTextSize(final TextView textView) {
		ViewTreeObserver.OnGlobalLayoutListener ll = new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				} else {
					//noinspection deprecation
					textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);//取消监听，很重要，要不然会很卡的
				}
				String text = textView.getText().toString();
				int width = textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight();
				int avaiWidth = width - dip2px(context, 5);
				if (avaiWidth <= 0) {
					return;
				}
				TextPaint textPaintClone = new TextPaint(textView.getPaint());
				float trySize = dip2px(context,18);
				textPaintClone.setTextSize(trySize);
				while (textPaintClone.measureText(text) > avaiWidth && trySize > trySize/3) {
					trySize--;
					textPaintClone.setTextSize(trySize);
				}
				textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
			}
		};
		textView.getViewTreeObserver().addOnGlobalLayoutListener(ll);
	}
	/**
	 * 根据字体数量及控件大小设置字体大小
	 * @param
	 */
	public static void autoAllTextSize(TextView... textViews) {
		for (final TextView textView : textViews) {
			ViewTreeObserver.OnGlobalLayoutListener ll = new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
						textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					} else {
						//noinspection deprecation
						textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);//取消监听，很重要，要不然会很卡的
					}
					String text = textView.getText().toString();
					//实际字体大小
					float size = textView.getTextSize();
					int width = textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight();
					int avaiWidth = width - dip2px(context, 5);
					if (avaiWidth <= 0) {
						return;
					}
					TextPaint textPaintClone = new TextPaint(textView.getPaint());
					float trySize = dip2px(context, 18);
					textPaintClone.setTextSize(trySize);
					while (textPaintClone.measureText(text) > avaiWidth && trySize > trySize / 3) {
						trySize--;
						textPaintClone.setTextSize(trySize);
					}
					LogUtil.i("trySize="+trySize+";size="+size);
					if (size < trySize){ trySize = size;}
					textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
				}
			};
			textView.getViewTreeObserver().addOnGlobalLayoutListener(ll);
		}
	}
		//动态修改字体大小
	private void adjustTvTextSize(TextView textView) {
		String text = textView.getText().toString();
		int width = textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight();
		int avaiWidth = (int) (width - dip2px(context, 2));
		if (avaiWidth <= 0) {
			return;
		}
		TextPaint textPaintClone = new TextPaint(textView.getPaint());
		float trySize = dip2px(context,18);
		textPaintClone.setTextSize(trySize);
		while (textPaintClone.measureText(text) > avaiWidth && trySize > trySize/3) {
			trySize--;
			textPaintClone.setTextSize(trySize);
		}
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
	}

	/**
	 * 隐藏所有的view
	 * @param visibity VISIBLE, INVISIBLE, or GONE
	 * @param views
	 */
	public static void hideAllView(int visibity,View... views){
		for (View view : views){
			if (view != null && view.getVisibility() == View.VISIBLE){
				view.setVisibility(visibity);
			}
		}
	}
	/**
	 * 隐藏所有的view
	 * @param views
	 */
	public static void showAllView(View... views){
		for (View view : views){
			if (view != null && view.getVisibility() != View.VISIBLE){
				view.setVisibility(View.VISIBLE);
			}
		}
	}

	/**
	 *oss登录接口:按钮添加禁止点击，然后开启
	 * @param context
	 * @param userName
	 * @param password
	 */
	public static void ossLogin(final Context context, final String userName, final String password, final OnViewEnableListener enableListener){
		ossLogin(0,context,userName,password,enableListener);
    }
	/**
	 *oss登录接口:按钮添加禁止点击，然后开启
	 * @param context
	 * @param userName
	 * @param password
	 * @param loginType :1：代表oss超时重新登录；2：代表server超时重新登录；0和其他：代表正常登录
	 */
	public static void ossLogin(final int loginType, final Context context, final String userName, final String password, final OnViewEnableListener enableListener){
		Mydialog.Show(context);
		PostUtil.postOssLoginTimeOut(OssUrls.getOssLoginServer(), new postListener() {
			@Override
			public void success(String json) {
				Mydialog.Dismiss();
				try {
					JSONObject jsonObject = new JSONObject(json);
					int result = jsonObject.getInt("result");
					switch (result){
						case 0:
							enableListener.onViewEnable();
							T.make(R.string.all_http_failed,context);
							jumpLoginActivity(context);
							errAutoLoginServer(context,userName,password,enableListener);
							break;
						case 1:
							JSONObject obj = jsonObject.getJSONObject("obj");
							//用户类型
							int userType = obj.getInt("userType");
							if (userType == 0){//监控用户
//								Mydialog.Show((Activity) ShineApplication.context,"");
								String userServerUrl = obj.getString("userServerUrl");
								serverLogin(context,userServerUrl,userName,password,enableListener);
							}else{//oss用户
								//oss登录超时
								if (loginType == 1){return;}
								//oss实体user
								String user = obj.getJSONObject("user").toString();
								Gson gson = new Gson();
								Cons.ossUserBean = new OssUserBean();
								Cons.ossUserBean = gson.fromJson(user, OssUserBean.class);
								//oss中文英文服务器名称列表列表
								Cons.ossServerList = new ArrayList<Map<String, String>>();
								JSONArray serverList = obj.getJSONArray("serverList");
								for (int i = 0,size = serverList.length();i < size;i++){
									JSONObject server = serverList.getJSONObject(i);
									Map<String,String> map = new HashMap<String, String>();
									map.put(Constant.OssServer_cn,server.getString(Constant.OssServer_cn));
									map.put(Constant.OssServer_en,server.getString(Constant.OssServer_en));
									map.put(Constant.OssServer_url,server.getString(Constant.OssServer_url));
									Cons.ossServerList.add(map);
								}
								//oss动态服务器地址
								Constant.setOssUrl("http://" + obj.getString("ossServerUrl"));
								LogUtil.i("动态服务器："+ Constant.ossUrl);
								LogUtil.i("动态服务器："+ OssUrls.postOssSearchAll());
								SqliteUtil.login(userName, password);
								SharedPreferencesUnit.getInstance(context).putInt(Constant.AUTO_LOGIN,1);
								SharedPreferencesUnit.getInstance(context).putInt(Constant.AUTO_LOGIN_TYPE,0);
								//oss用户判断手机号是否正确，直接跳转至Oss主界面
								int role = Cons.ossUserBean.getRole();
//								jumpActivity(context,OssKeFuActivity.class);
//								if (role == 6 || role ==14 || role == 7 || role ==15){
								//分销商
								if (role == OssUserBean.DISTRIBUTOR_PERSON || role == OssUserBean.DISTRIBUTOR_NO_PERFECT ){
									jumpActivity(context,OssJKActivity.class);
								//安装商
								}else if ( role == OssUserBean.INSTALLER_PERSON || role ==OssUserBean.INSTALLER_NO_PERFECT){
									jumpActivity(context,OssAZActivity.class);
								}else {
									String phone = SharedPreferencesUnit.getInstance(context).get(Constant.OssUser_Phone);
									if (phone.equals(Cons.ossUserBean.getPhone())){
//										if ( role == 1 || role == 2 || role == 3){
										if ( role == OssUserBean.SUPER_ADMIN || role == OssUserBean.CUSTOMER_ADMIN || role == OssUserBean.CUSTOMER_PERSON){
											jumpActivity(context,OssKeFuActivity.class);
										}else {
											T.make(R.string.dataloggers_add_no_jurisdiction,context);
											jumpActivity(context,LoginActivity.class);
										}
									}else {
										jumpActivity(context,OssPhoneVerActivity.class);
									}
								}
								enableListener.onViewEnable();
							}
							break;
						case 3:
							enableListener.onViewEnable();
							T.make(R.string.login_no_user,context);
							jumpLoginActivity(context);
							errAutoLoginServer(context,userName,password,enableListener);
							break;
						case 4:
							enableListener.onViewEnable();
							T.make(R.string.all_login_error,context);
							jumpLoginActivity(context);
							errAutoLoginServer(context,userName,password,enableListener);
							break;
						case 5:
							enableListener.onViewEnable();
							T.make(R.string.all_login_error,context);
							jumpLoginActivity(context);
							errAutoLoginServer(context,userName,password,enableListener);
							break;
						default:
							enableListener.onViewEnable();
							errAutoLoginServer(context,userName,password,enableListener);
							break;
					}
				}catch (Exception e){
					e.printStackTrace();
					enableListener.onViewEnable();
					jumpLoginActivity(context);
				}
			}
			@Override
			public void Params(Map<String, String> params) {
				params.put("userName",userName);
				params.put("password",MD5andKL.encryptPassword(password));
			}
			@Override
			public void LoginError(String str) {
				ossErrAutoLogin(context,userName,password,enableListener);
			}
		});
	}
	public static void errAutoLoginServer( Context context, final String userName, final String password, final OnViewEnableListener enableListener){
//		ossErrAutoLogin(context,userName,password,enableListener);
	}
	/**
	 * server 用户直接登录无需获取服务器地址
	 * @param context
	 * @param userName
	 * @param password
	 */
    public static void serverLogin( Context context,String userServerUrl,  String userName,  String password,  OnViewEnableListener enableListener){
		serverLogin(0,context,userServerUrl,userName,password,enableListener);
	}
	/**
	 * server 用户直接登录无需获取服务器地址
	 * @param context
	 * @param userName
	 * @param password
	 * @param loginType :代表登录类型：0：代表正常登录；1：代表server登录超时重新登录
	 */
    public static void serverLogin(final int loginType, final Context context, String userServerUrl, final String userName, final String password, final OnViewEnableListener enableListener){
		SqliteUtil.url(userServerUrl);
		Cons.guestyrl="http://"+userServerUrl;
		Mydialog.Show(context);
		PostUtil.post(new Urlsutil().cusLogin, new postListener() {
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject10 = new JSONObject(json);
					JSONObject jsonObject=jsonObject10.getJSONObject("back");
					if(jsonObject.opt("success").toString().equals("true")){
						if("1".equals(jsonObject.opt("service").toString())){
							Cons.addQuestion=true;
						}else{
							Cons.addQuestion=false;
						}
						int app_code=jsonObject.optInt("app_code", 0);
						if(app_code>SqliteUtil.getApp_Code()||SqliteUtil.getApp_Code()==-1){
							Cons.isCodeUpdate=true;
						}else{
							Cons.isCodeUpdate=false;
						}
						SqliteUtil.setService("", app_code);
						JSONArray jsonArray=jsonObject.getJSONArray("data");
						if(Cons.plants==null){
							Cons.plants=new ArrayList<Map<String,Object>>();
						}
						Cons.plants.clear();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject2=jsonArray.getJSONObject(i);
							Map<String, Object> map=new HashMap<String, Object>();
							String id=jsonObject2.getString("plantId").toString();
							map.put("plantId", id);
							map.put("plantName",jsonObject2.getString("plantName").toString());
							Cons.plants.add(map);
						}
						JSONObject jsonObject2=jsonObject.getJSONObject("user");
						UserBean userBean=new UserBean();
						userBean.setEnabled(jsonObject2.opt("enabled").toString());
						userBean.setUserLanguage(jsonObject2.opt("userLanguage").toString());
						userBean.setTimeZone(jsonObject2.opt("timeZone").toString());
						userBean.setPassword(jsonObject2.opt("password").toString());
						userBean.setMailNotice(jsonObject2.opt("mailNotice").toString());
						userBean.setId(jsonObject2.opt("id").toString());
						Cons.userId=jsonObject2.opt("id").toString();
						userBean.setLastLoginIp(jsonObject2.opt("lastLoginIp").toString());
						userBean.setPhoneNum(jsonObject2.get("phoneNum").toString());
						userBean.setAccountName(jsonObject2.opt("accountName").toString());
						userBean.setApproved(jsonObject2.get("approved").toString());
						userBean.setSmsNotice(jsonObject2.get("smsNotice").toString());
						userBean.setEmail(jsonObject2.get("email").toString());
						userBean.setParentUserId(jsonObject2.getInt("parentUserId"));
						userBean.setCompany(jsonObject2.get("company").toString());
						userBean.setActiveName(jsonObject2.get("activeName").toString());
						userBean.setCounrty(jsonObject2.get("counrty").toString());
						userBean.setIsBigCustomer(jsonObject2.get("isBigCustomer").toString());
						userBean.setCreateDate(jsonObject2.get("createDate").toString());
						userBean.setRightlevel(jsonObject2.get("rightlevel").toString());
						userBean.setLastLoginTime(jsonObject2.get("lastLoginTime").toString());
						userBean.setNoticeType(jsonObject2.get("noticeType").toString());
						userBean.setIsValiEmail(jsonObject2.getInt("isValiEmail"));
						userBean.setIsValiPhone(jsonObject2.getInt("isValiPhone"));
						if (userBean.getIsValiEmail() == 1){Cons.isValiEmail = true;}else { Cons.isValiEmail = false; }
						if (userBean.getIsValiPhone() == 1){Cons.isValiPhone = true;}else { Cons.isValiPhone = false; }
						Cons.userBean=userBean;
						SqliteUtil.login(userName, password);
						SharedPreferencesUnit.getInstance(context).putInt(Constant.AUTO_LOGIN,1);
						SharedPreferencesUnit.getInstance(context).putInt(Constant.AUTO_LOGIN_TYPE,1);
						enableListener.onViewEnable();
						if (loginType == 0){
							jumpActivity(context,MainActivity.class);
						}
						Mydialog.Dismiss();
					}else{
						Mydialog.Dismiss();
						enableListener.onViewEnable();
						T.make(R.string.all_login_error,context);
						jumpLoginActivity(context);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Mydialog.Dismiss();
					enableListener.onViewEnable();
					jumpLoginActivity(context);
				}
			}
			@Override
			public void Params(Map<String, String> params) {
				params.put("userName",userName);
				params.put("password",MD5andKL.encryptPassword(password));
			}
			@Override
			public void LoginError(String str) {
				Mydialog.Dismiss();
				enableListener.onViewEnable();
				jumpLoginActivity(context);
			}
		});
	}

	/**
	 * server登录超时，重新登录
	 */
	public static void serverTimeOutLogin(){
		Map<String, Object> map = SqliteUtil.inquirylogin();
		String url=SqliteUtil.inquiryurl();
		if (map != null && map.size()>0 && (!TextUtils.isEmpty(url))){
			serverLogin(1,ShineApplication.context,url,map.get("name").toString().trim(), map.get("pwd").toString().trim(), new OnViewEnableListener(){});
		}else {
			jumpLoginActivity(ShineApplication.context);
		}
	}
	/**
	 * 当登陆oss发送错误时直接在server登陆，根据语言从server或-cn获取服务器地址，获取失败后交换服务器访问
	 * @param context
	 * @param userName
	 * @param password
	 */
	public static int  serverNum = 1;
	public static void ossErrAutoLogin(final Context context, final String userName, final String password, final OnViewEnableListener enableListener) {
		Mydialog.Show(context);
		String serverUrl = Urlsutil.getServerUrlByNameNew;
		if (getLanguage(context) == 0){
			serverUrl = Urlsutil.getServerUrlByNameNew_cn;
		}
		if (serverNum == 2){
			if (Urlsutil.getServerUrlByNameNew_cn.equals(serverUrl)){
				serverUrl = Urlsutil.getServerUrlByNameNew;
			}else {
				serverUrl = Urlsutil.getServerUrlByNameNew_cn;
			}
		}
		GetUtil.getParamsServerUrl(serverUrl, new postListener() {
			@Override
			public void success(String json) {
				Mydialog.Dismiss();
				serverNum = 1;
				try {
					JSONObject jsonObject=new JSONObject(json);
					String success=jsonObject.get("success").toString();
					if(success.equals("true")){
						String msg=jsonObject.getString("msg");
						serverLogin(context,msg,userName,password,enableListener);
					}else{
						Mydialog.Dismiss();
						enableListener.onViewEnable();
						if (context instanceof WelcomeActivity){
							jumpLoginActivity(context);
						}
						T.make(R.string.all_login_error,context);
					}

				} catch (JSONException e) {
					e.printStackTrace();
					Mydialog.Dismiss();
					enableListener.onViewEnable();
					if (context instanceof WelcomeActivity){
						jumpLoginActivity(context);
					}
				}
			}
			@Override
			public void Params(Map<String, String> params) {
				params.put("userName", userName);
			}

			@Override
			public void LoginError(String str) {
				Mydialog.Dismiss();
				switch (serverNum){
					case 1:
						serverNum = 2;
						ossErrAutoLogin(context,userName,password,enableListener);
						break;
					case 2:
						serverNum = 1;
						enableListener.onViewEnable();
						jumpLoginActivity(context);
						break;
				}
			}
		});
	}
	//隐藏虚拟键盘
	public static void hideKeyboard(View v)
	{
		InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
		if ( imm.isActive( ) ) {
			imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );

		}
	}

	//显示虚拟键盘
	public static void showKeyboard(View v)
	{
		InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );

		imm.showSoftInput(v,0);

	}

    /**
     * 获取是中文还是其他：0：代表中国；1：代表外国
     * @param context
     * @return
     */
    public static int getLanguage(Context context){
        int lan=1;
        Locale locale=context.getResources().getConfiguration().locale;
        String language=locale.getLanguage();
        if(language.toLowerCase().contains("zh")){
            lan=0;
        }
        return lan;
    }

	/**
	 *判断时间控件是否是未来时间
	 * @param context
	 * @param increaseType:增加的时间类型：1：代表天；2：代表月；3：代表年
	 */
    public static boolean isFutureTime(Context context,int increaseType){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(AppUtils.newtime);
		if (increaseType == 1){
			calendar.add(Calendar.DAY_OF_MONTH,1);
		}else if (increaseType ==2){
			calendar.add(Calendar.MONTH,1);
			calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),1);
		}else if (increaseType == 3){
			calendar.add(Calendar.YEAR,1);
			calendar.set(calendar.get(Calendar.YEAR),0,1);
		}
		LogUtil.i("year:"+calendar.get(Calendar.YEAR)+";month:"+calendar.get(Calendar.MONTH)+";day:"+calendar.get(Calendar.DAY_OF_MONTH)+";ms:"+calendar.getTimeInMillis()+";nowms:"+System.currentTimeMillis());
		if (calendar.getTimeInMillis() > System.currentTimeMillis()){
			if (context instanceof FragmentActivity){
				new CircleDialog.Builder((FragmentActivity)context)
						.setWidth(0.7f)
						.setTitle(context.getString(R.string.温馨提示))
						.setText(context.getString(R.string.m72))
						.setPositive(context.getString(R.string.all_ok), null)
						.show();
			}else {
				T.make(R.string.m72,context);
			}
			return true;
		}else {
			return false;
		}
	}

	/**
	 *
	 * @param context
	 * @param textDate:2017-12-12
	 * @return
	 */
	public static boolean isFutureTime(Context context,String textDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(textDate);
			if (new Date().getTime()<date.getTime()){
				if (context instanceof FragmentActivity){
					new CircleDialog.Builder((FragmentActivity)context)
							.setWidth(0.7f)
							.setTitle(context.getString(R.string.温馨提示))
							.setText(context.getString(R.string.m72))
							.setPositive(context.getString(R.string.all_ok), null)
							.show();
				}else {
					T.make(R.string.m72,context);
				}
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 *判断时间控件是否是未来时间
	 * @param context
	 * @param nowTamp:现在的时间戳
	 */
	public static boolean isFutureTime(Context context,long nowTamp){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(nowTamp);
		calendar.add(Calendar.DAY_OF_MONTH,1);
		LogUtil.i("year:"+calendar.get(Calendar.YEAR)+";month:"+calendar.get(Calendar.MONTH)+";day:"+calendar.get(Calendar.DAY_OF_MONTH)+";ms:"+calendar.getTimeInMillis()+";nowms:"+System.currentTimeMillis());
		if (calendar.getTimeInMillis() > System.currentTimeMillis()){
			if (context instanceof FragmentActivity){
				new CircleDialog.Builder((FragmentActivity)context)
						.setWidth(0.7f)
						.setTitle(context.getString(R.string.温馨提示))
						.setText(context.getString(R.string.m72))
						.setPositive(context.getString(R.string.all_ok), null)
						.show();
			}else {
				T.make(R.string.m72,context);
			}
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 判断时间控件是否是未来时间
	 * @param context
	 * @param year:日期控件选中的年份
	 * @param month:日期控件选中的月份
	 * @param day:日期控件选中的天
	 * @param increaseType:搜索的时间类型：1：代表天；2：代表月；3：代表年（暂时不用）
	 * @return
	 */
	public static boolean isFutureTime(Context context,int increaseType,int year,int month,int day){
		Calendar calendar = Calendar.getInstance();
		calendar.set(year,month,day);
		LogUtil.i("year:"+calendar.get(Calendar.YEAR)+";month:"+calendar.get(Calendar.MONTH)+";day:"+calendar.get(Calendar.DAY_OF_MONTH)+";ms:"+calendar.getTimeInMillis()+";nowms:"+System.currentTimeMillis()+";index:"+increaseType);
		if (calendar.getTimeInMillis() > System.currentTimeMillis()){
			if (context instanceof FragmentActivity){
				new CircleDialog.Builder((FragmentActivity)context)
						.setWidth(0.7f)
						.setTitle(context.getString(R.string.温馨提示))
						.setText(context.getString(R.string.m72))
						.setPositive(context.getString(R.string.all_ok), null)
						.show();
			}else {
				T.make(R.string.m72,context);
			}
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 显示简单的圆角dialog
	 * @param context
	 * @param titleId:标题strId
	 * @param textId:内容strId
	 * @param okId:确定strId
	 * @param noId:取消strId
	 */
	public static void showCircleDialog(Context context,int titleId,int textId,int okId,int noId){
		if (context instanceof FragmentActivity){
			CircleDialog.Builder builder = new CircleDialog.Builder((FragmentActivity)context).setWidth(0.7f);
					if (titleId > 0) builder.setTitle(context.getString(titleId));
					if (textId > 0) builder.setText(context.getString(textId));
					if (okId > 0) builder.setPositive(context.getString(okId),null);
					if (noId > 0) builder.setNegative(context.getString(noId),null);
			builder.show();
		}else {
			T.make(textId,context);
		}
	}

	/**
	 *
	 * @param act
	 * @param wifiType
	 * @param datalogSn
	 */
	public static void configWifi(final Activity act, String wifiType,final String datalogSn){
		configWifi(act,wifiType,"3",datalogSn);
	}

	/**
	 *
	 * @param act
	 * @param wifiType
	 * @param jumpType:由哪跳转：3：采集器列表界面；1：注册添加采集器界面；(针对ShineWifi类型和-s类型)
	 * @param datalogSn
	 */
	public static void configWifi(final Activity act, String wifiType,String jumpType,final String datalogSn){
		String type = wifiType.toLowerCase();
		if ("shinewifi".equals(type)
				|| Constant.WiFi_Type_ShineWIFI_S.equals(type)
				|| "shinelanbox".equals(type)
				|| "shinewifibox".equals(type)
				) {
			ConnectivityManager manager = (ConnectivityManager)act.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
			if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
				if ("shinewifi".equals(type)) {//新wifi
					Map<String, Object> map = new GetWifiInfo(act).Info();
					if (TextUtils.isEmpty(map.get("mAuthString").toString())) {
						AlertDialog builder = new AlertDialog.Builder(act).setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_connectwifi).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();
							}
						}).create();
						builder.show();
						return;
					}
					Intent intent = new Intent(act, SmartConnection.class);
					Bundle bundle = new Bundle();
					bundle.putString("type", jumpType);
					bundle.putString("id", datalogSn);
					bundle.putString("ssid", map.get("ssid").toString());
					bundle.putString("mAuthString", map.get("mAuthString").toString());
					bundle.putByte("mAuthMode", (Byte) map.get("mAuthMode"));
					intent.putExtras(bundle);
					act.startActivity(intent);
				} else if (Constant.WiFi_Type_ShineWIFI_S.equals(type)) {//新wifi第二版
					Intent intent = new Intent(act, NewWifiS2ConfigActivity.class);
					intent.putExtra("jumpType", jumpType);
					intent.putExtra("sn", datalogSn);
					jumpActivity(act, intent);
				} else if ("shinelanbox".equals(type)) {//rfstick
					Intent rfstickIntent = new Intent(act, OssRFStickActivity.class);
					rfstickIntent.putExtra("datalogSn", datalogSn);
					rfstickIntent.putExtra("jumpType", 100);
					act.startActivity(rfstickIntent);
				} else if ("shinewifibox".equals(type)) {//老wifi
					GetWifiListNew md = new GetWifiListNew(act);
					md.setCancelable(false);
					md.show();
				} else {//提示是否是shinewifi类型
					questionShineWifi(act, "shinewifi", datalogSn);
				}
			} else {
				T.make(R.string.all_wifi_failed, act);
				AlertDialog builder = new AlertDialog.Builder(act).setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_connectwifi).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				}).create();
				builder.show();
			}
		}
	}
	private static void questionShineWifi(final Activity act, final String shinewifi, final String datalogSn) {
		StringBuilder sb = new StringBuilder().append(act.getString(R.string.dataloggers_dialog_Configuration)).append(act.getString(R.string.datadetailed_genre)).append("ShineWiFi ?");
		AlertDialog builder = new AlertDialog.Builder(act).setTitle(R.string.all_prompt).setMessage(sb.toString())
				.setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
						configWifi(act,shinewifi,datalogSn);
					}
				})
				.setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
				.create();
		builder.show();
	}
	/**
	 * 获取应用详情页面intent
	 *
	 * @return
	 */
	public static Intent getAppDetailSettingIntent() {
		Intent localIntent = new Intent();
		localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
		localIntent.setData(Uri.fromParts("package", ShineApplication.context.getPackageName(), null));
		return localIntent;
	}

	/**
	 * 通过设备类型和日期获取设置密码
	 * @param act
	 * @param type:0-采集器；1-逆变器；2-储能机；
	 * @param handlerListener
	 */
	public static void getPasswordByDevice(final FragmentActivity act, final int type, final OnHandlerStrListener handlerListener){
		Mydialog.Show(act);
		PostUtil.post(new Urlsutil().postGetSetPassword, new postListener() {
			@Override
			public void Params(Map<String, String> params) {
				params.put("type",type + "");
				params.put("stringTime",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					int result = jsonObject.getInt("result");
					if (result == 1){
						handlerListener.handlerDealStr(jsonObject.getString("msg"));
					}else {
						MyControl.circlerDialog(act,act.getString(R.string.all_failed),result,false);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Mydialog.Dismiss();
				}
			}
			@Override
			public void LoginError(String str) {}
		});
	}

	/**
	 * server:储能机设置
	 * @param context
	 * @param sn
	 * @param paramId
	 * @param param_1
	 * @param param_2
	 * @param param_3
	 * @param param_4
	 * @param handlerListener
	 */
	public static void storageSetServer(Context context, final String sn, final String paramId, final String param_1, final String param_2, final String param_3, final String param_4, final OnHandlerListener handlerListener) {
		Mydialog.Show(context);
		PostUtil.post(new Urlsutil().storageSet, new postListener() {
			@Override
			public void Params(Map<String, String> params) {
				params.put("serialNum",sn);
				params.put("type",paramId);
				params.put("param1",param_1);
				params.put("param2",param_2);
				params.put("param3",param_3);
				params.put("param4",param_4);
			}
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					int result = Integer.parseInt(jsonObject.getString("msg"));
					handlerListener.handlerDeal(result,"");
				} catch (Exception e) {
					e.printStackTrace();
					Mydialog.Dismiss();
				}
			}
			@Override
			public void LoginError(String str) {}
		});
	}
	/**
	 * server:储能机SPF5000设置
	 * @param context
	 * @param sn
	 * @param paramId
	 * @param param_1
	 * @param param_2
	 * @param param_3
	 * @param param_4
	 * @param handlerListener
	 */
	public static void storageSPF5KSetServer(Context context, final String sn, final String paramId, final String param_1, final String param_2, final String param_3, final String param_4, final OnHandlerListener handlerListener) {
		Mydialog.Show(context);
		PostUtil.post(new Urlsutil().postStorageSpf5kSet, new postListener() {
			@Override
			public void Params(Map<String, String> params) {
				params.put("serialNum",sn);
				params.put("type",paramId);
				params.put("param1",param_1);
				params.put("param2",param_2);
				params.put("param3",param_3);
				params.put("param4",param_4);
			}
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					int result = Integer.parseInt(jsonObject.getString("msg"));
					handlerListener.handlerDeal(result,"");
				} catch (Exception e) {
					e.printStackTrace();
					Mydialog.Dismiss();
				}
			}
			@Override
			public void LoginError(String str) {}
		});
	}
	public static void invSetServer(Context context, final String sn, final String paramId, final String param_1, final String param_2, final OnHandlerListener handlerListener) {
		Mydialog.Show(context);
		PostUtil.post(new Urlsutil().inverterSet, new postListener() {
			@Override
			public void Params(Map<String, String> params) {
				params.put("serialNum",sn);
				params.put("paramId",paramId);
				params.put("command_1",param_1);
				params.put("command_2",param_2);
			}
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					int result = Integer.parseInt(jsonObject.getString("msg"));
					handlerListener.handlerDeal(result,"");
				} catch (Exception e) {
					e.printStackTrace();
					Mydialog.Dismiss();
				}
			}
			@Override
			public void LoginError(String str) {}
		});
	}



	/**
	 * 检测版本更新
	 * @param context
	 * @param type：Constant.LoginActivity_Updata:静默提示；Constant.AboutActivity_Updata：显示提示
	 */
	public static void checkUpdate(Context context,int type){
		if (Constant.google_package_name.equals(context.getPackageName())){
			return;
		}
		String str=context.getResources().getString(R.string.newversion);
		StringBuilder sb = new StringBuilder("http://cdn.growatt.com/apk/Canadian")
				.append("/ShinePhone_TinkerPatch.xml");

		UpdateManager up=new UpdateManager(context,sb.toString(), str);
		up.Getupdateurl(type);
	}

	/**
	 * 跳转到登录界面
	 * @param context
	 */
	public static void jumpLoginActivity(Context context){
		jumpActivity(context,LoginActivity.class);
	}

	/**
	 * 跳转到指定界面
	 * @param context
	 * @param clazz
	 */
	public static void jumpActivity(Context context,Class<?> clazz){
	try {
		if (context == null){
			context = ShineApplication.context;
		}
		if (context instanceof Activity){
			Activity act = (Activity) context;
			act.startActivity(new Intent(act,clazz));
		}else {
			Intent intent=new Intent(context,clazz);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
			context.startActivity(intent);
		}
	}catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 跳转到指定界面,传递数据
	 * @param context
	 */
	public static void jumpActivity(Context context,Intent intent){
		try {
			if (context == null){
				context = ShineApplication.context;
			}
			if (context instanceof Activity){
				Activity act = (Activity) context;
				act.startActivity(intent);
			}else {
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
				context.startActivity(intent);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * EditText获取焦点并显示软键盘
	 */
	public static void showSoftInputFromWindow(Activity activity, EditText editText) {
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.requestFocus();
		activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}
	/**
	 * 计算多个数的和
	 */
	public static float addString(String... strings){
		float sum = 0;
		try {
			for (String s : strings){
				float value = Float.parseFloat(s);
				sum += value;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return sum;
	}
	/**
	 * 计算百分比
	 */
	public static String mathPercent(float total,String value){
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		String result = "0";
		if (total > 0){
			result = numberFormat.format((Float.parseFloat(value)*100)/total);
		}
		return result + "%";
	}
	/**
	 * 获取滑动速度
	 */
	public static int getSpeed(Context c){
		int height = MyUtils.getScreenHeight(c);
		int speed = height/30;
		return speed;
	}
	/**
	 * 一个textview设置不同字体大小
	 */
	public static void setTextView2TextSize(Context context,String content,String unit,TextView textView){
		Resources res = context.getResources();
		String str = content + unit;
		SpannableStringBuilder sp = new  SpannableStringBuilder(str);
		sp.setSpan(new AbsoluteSizeSpan((int)res.getDimension(R.dimen.x16)),0,content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new AbsoluteSizeSpan((int)res.getDimension(R.dimen.x10)),content.length(),str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(sp);
	}

	/**
	 * server  Max 设置总方法
	 * @param context
	 * @param sn
	 * @param paramId
	 * @param param_1
	 * @param param_2
	 * @param handlerListener
	 */
	public static void invSetMaxServer(Context context, final String sn, final String paramId, final String param_1, final String param_2, final OnHandlerListener handlerListener) {
		Mydialog.Show(context);
		PostUtil.post(new Urlsutil().postInverterSet_max, new postListener() {
			@Override
			public void Params(Map<String, String> params) {
				params.put("serialNum",sn);
				params.put("paramId",paramId);
				params.put("param1",param_1);
				params.put("param2",param_2);
			}
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					int result = Integer.parseInt(jsonObject.getString("msg"));
					handlerListener.handlerDeal(result,"");
				} catch (Exception e) {
					e.printStackTrace();
					Mydialog.Dismiss();
				}
			}
			@Override
			public void LoginError(String str) {}
		});
	}
	/**
	 * server  JLINV锦浪逆变器 设置总方法
	 * @param context
	 * @param sn
	 * @param paramId
	 * @param param_1
	 * @param param_2
	 * @param handlerListener
	 */
	public static void invSetJLINVServer(Context context, final String sn, final String paramId, final String param_1, final String param_2, final OnHandlerListener handlerListener) {
		Mydialog.Show(context);
		PostUtil.post(new Urlsutil().postInverterSet_jlinv, new postListener() {
			@Override
			public void Params(Map<String, String> params) {
				params.put("serialNum",sn);
				params.put("paramId",paramId);
				params.put("command_1",param_1);
				params.put("command_2",param_2);
			}
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					int result = Integer.parseInt(jsonObject.getString("msg"));
					handlerListener.handlerDeal(result,"");
				} catch (Exception e) {
					e.printStackTrace();
					Mydialog.Dismiss();
				}
			}
			@Override
			public void LoginError(String str) {}
		});
	}

	/**
	 * 提示是否调整到wifi界面
	 */
	public static void showJumpWifiSet(final FragmentActivity act){
		showJumpWifiSet(act,"WiFi模块通讯失败,请检查WiFi连接.");
	}
	/**
	 * 提示是否调整到wifi界面
	 */
	public static void showJumpWifiSet(final FragmentActivity act,String str){
		Mydialog.Dismiss();
		new CircleDialog.Builder(act)
				.setWidth(0.7f)
				.setTitle(act.getString(R.string.reminder))
				.setText(str)
				.setNegative(act.getString(R.string.all_no),null)
				.setPositive(act.getString(R.string.action_settings), new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
						act.startActivityForResult(intent, WifiList.FIRSTACT_TO_WIFI);
					}
				})
				.show();
	}
}
