package com.growatt.shinephone.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.EditText;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.PlantAdminActivity;
import com.growatt.shinephone.util.GetUtil.GetListener;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateplantUtil {
	public static void showupdate(final Activity context,int title,int msg,String et,final GetplantListener getListener){
		 final Handler handler=new Handler(){
				public void handleMessage(android.os.Message msg) {
					switch (msg.what) {
					case 0:
						Mydialog.Dismiss();
						String str=(String) msg.obj;
						if(str.equals("200")){
							getListener.ok();
							T.make(R.string.all_success,context);
						}else if(str.equals("501")){
							T.make(R.string.geographydata_change_no_data,context);
							getListener.no();
						}else if(str.equals("502")){
							getListener.no();
							T.make(R.string.geographydata_change_no_namerepetition,context);
						}else if(str.equals("503")){
							T.make(R.string.geographydata_change_no_picture,context);
							getListener.no();
						}else if(str.equals("504")){
							T.make(R.string.geographydata_change_no_map,context);
							getListener.no();
						}else if ("701".equals(str)){
							T.make(R.string.m7,context);
							getListener.no();
						}
						break;
					case 1:
						Mydialog.Dismiss();
						T.make(R.string.geographydata_change_no_data,context);
						break;
					default:
						break;
					}
				};
			};
		AlertDialog.Builder builder = new Builder(context);
		final EditText editText=new EditText(context);
		editText.setText(et);
		editText.setSelection(et.length());
		builder.setTitle(title).setMessage(msg).setView(editText).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				if(!editText.getText().toString().equals("")){
				getListener.put(editText);
				updateplant(context ,handler);
				}else{
					T.make(R.string.all_data_inexistence,context);
				}
			}
		}).setNegativeButton(R.string.all_no, new  DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		}).create();
		builder.show();
	}
	public static interface GetplantListener {
		void put(EditText editText);
		void ok();
		void no();
	}
	
	public static void updateplant(Activity context,final Handler handler){
		Mydialog.Show(context, "");
         AddCQ.postUp(new Urlsutil().updatePlant, AddCQ.powerdataBeanToMap(PlantAdminActivity.powerdata), new GetListener() {
			
			@Override
			public void success(String str) {
				if(TextUtils.isEmpty(str)){
					handler.sendEmptyMessage(1);
					return;
				}
				try {
					JSONObject jsonObject=new JSONObject(str);
					Message msg=new Message();
					msg.what=0;
					msg.obj=jsonObject.get("msg").toString();
					handler.sendMessage(msg);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void error(String json) {
			}
		});
	}
}
