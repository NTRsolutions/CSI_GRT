package com.growatt.shinephone.util;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.growatt.shinephone.R;
public class Mydialog {
	/**
	 * ��ת��dialog
	 * @param act
	 * @param msg
	 * @return
	 */
	public static Dialog initDialog(Activity act, String msg) {
		Dialog dialog=null;
		try {
			View view = View.inflate(act, R.layout.dialog_layout, null);
			dialog = new Dialog(act, R.style.mydialog);
			dialog.setContentView(view);
			setDialogText(view, msg);
			Window win = dialog.getWindow();
			WindowManager.LayoutParams params = win.getAttributes();
			int cxScreen = AppUtils.getScreenWidth(act);
			params.width = cxScreen / 9 * 7;
//			params.height=(int) (((Context)act).getResources().getDisplayMetrics().density*50);
			params.height = (int)(act.getResources().getDimension(R.dimen.x50));
			dialog.setCanceledOnTouchOutside(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dialog;
	}
	public static Dialog initDialog(Activity act, int msg) {
		try {
			View view = View.inflate(act, R.layout.dialog_layout, null);
			Dialog dialog = new Dialog(act, R.style.mydialog);
			dialog.setContentView(view);
			setDialogText(view, msg);
			Window win = dialog.getWindow();
			WindowManager.LayoutParams params = win.getAttributes();
			int cxScreen = AppUtils.getScreenWidth(act);
			params.width = cxScreen / 9 * 7;
//			params.height=(int) (((Context)act).getResources().getDisplayMetrics().density*50);
			params.height = (int)(act.getResources().getDimension(R.dimen.x50));
			dialog.setCanceledOnTouchOutside(false);
			return dialog;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void setDialogText(View dialog, String msg) {
		TextView tv=(TextView)dialog.findViewById(R.id.loading_tips);
		tv.setText(msg);
	}
	public static void setDialogText(View dialog, int msg) {
		TextView tv=(TextView)dialog.findViewById(R.id.loading_tips);
		tv.setText(msg);
	}
	private static Dialog dialog;
	private static Activity mActivity;
	public static void Show(Activity act, String msg,final MydialogListener mydialogListener){
		mActivity=act;
		dialog=initDialog(act, msg);
		if(dialog!=null){
		dialog.show();
		dialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				if(dialog.isShowing()){
				dialog.dismiss();
			}
			mydialogListener.stopdialog();
			}
		});
		mydialogListener.stopdialog();
		thread();
		}
	}
	public static void Show(Activity act, int msg,final MydialogListener mydialogListener){
		mActivity=act;
		dialog=initDialog(act, msg);
		if(dialog!=null){
		dialog.show();
//		dialog.setOnDismissListener(new OnDismissListener() {
//			
//			@Override
//			public void onDismiss(DialogInterface arg0) {
//				if(dialog.isShowing()){
//					dialog.dismiss();
//				}
//				mydialogListener.stopdialog();
//			}
//		});
		dialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface arg0) {
				
			}
		});
		mydialogListener.stopdialog();
		thread();
		}
	}
	public static void Show(Activity act, String msg){
		mActivity=act;
		dialog=initDialog(act,msg);
		if(dialog!=null && !dialog.isShowing()){
		dialog.show();
		}
		thread();
	}
	public static void Show(Activity act){
		Show(act,"");
	}
	public static void Show(Activity act, int msg){
		mActivity=act;
		dialog=initDialog(act,msg);
		if(dialog!=null){
			dialog.show();
			}
		thread();
	}
	public static void Show(Context act){
		Show(act,"");
	}
	public static void Show(Context act, String msg){
		if (act instanceof Activity){
			mActivity=(Activity) act;
			dialog=initDialog(mActivity,msg);
			if(dialog!=null){
				dialog.show();
			}
			thread();
		}
	}
	public static void Show(Context act, int msg){
		if (act instanceof Activity){
			mActivity=(Activity) act;
			dialog=initDialog(mActivity,msg);
			if(dialog!=null){
				dialog.show();
			}
			thread();
		}
	}
	public static void Dismiss(){
		if(dialog!=null){
			if(mActivity!=null&&!mActivity.isFinishing()&&dialog.isShowing()){
				dialog.cancel();
				}
		}
	}
	public interface MydialogListener{
		void stopdialog();
	}
	private static void thread(){
		new Thread(){
			public void run() {
				try {
					Thread.sleep(18000);
					handler.sendEmptyMessage(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	private static Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Dismiss();
				break;

			default:
				break;
			}
		};
	};
}
