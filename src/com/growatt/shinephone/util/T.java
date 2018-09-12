package com.growatt.shinephone.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.control.MyControl;

public class T {
//	public static void make(int object) {
//		make(object, ShineApplication.context);
//	}
//	public static void make(String object) {
//		make(object , ShineApplication.context);
//	}
	public static void make(int object, Context context) {
		if(MyControl.isNotificationEnabled(context)){
			Toast.makeText(context, object, Toast.LENGTH_LONG).show();
		}else{
		    EToast.makeText(context, object, EToast.LENGTH_LONG).show();
		}
	}
	public static void make(String object, Context context) {
		if(MyControl.isNotificationEnabled(context)){
			Toast.makeText(context, object, Toast.LENGTH_LONG).show();
		}else{
		    EToast.makeText(context, object, EToast.LENGTH_LONG).show();
		}
	}
	public static void dialog(Context context,String object){
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.all_prompt).setMessage(object).setNegativeButton(R.string.all_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		}).create();
		builder.show();
	}
	public static void dialog(Context context,int object){
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.all_prompt).setMessage(object).setNegativeButton(R.string.all_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		}).create();
		builder.show();
	}
}
