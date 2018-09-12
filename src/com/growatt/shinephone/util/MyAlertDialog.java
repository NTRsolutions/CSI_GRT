package com.growatt.shinephone.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.growatt.shinephone.R;

public class MyAlertDialog {
    //一个标题加确定按钮
	public static void setDialogOne(Context context,int titleId,int msgId){
	TextView tvTitle=new TextView(context);
	tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);
	tvTitle.setText(titleId);
	tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
	tvTitle.setPadding(0, 20, 0, 20);
	
	AlertDialog dialog=new AlertDialog.Builder(context)
	.setCustomTitle(tvTitle)
	.setMessage(msgId)
	.setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			arg0.dismiss();
			
		}
	})
	.create();
	dialog.show();
	}
}
