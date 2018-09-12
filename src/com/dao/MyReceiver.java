package com.dao;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.growatt.shinephone.activity.DatadetailedActivity;
import com.growatt.shinephone.activity.MainActivity;
import com.growatt.shinephone.activity.MessagesActivity;
import com.growatt.shinephone.activity.UserdataActivity;
import com.growatt.shinephone.sqlite.SqliteUtil;

/**
 * 自定义接收器
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "推送消息";
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "[shinephone] 1111111111111111111111111 - " + intent.getAction());

		Map<String, Object>map=new HashMap<String, Object>();
		for (String key : bundle.keySet()) {
			map.put(key, bundle.get(key));
		}
		System.out.println(map.toString());
		//推送消息存入本地全部
		try {
			if(map.containsKey("cn.jpush.android.EXTRA")){
				JSONObject jsonObject=new JSONObject(map.get("cn.jpush.android.EXTRA").toString());
				String str=jsonObject.get("type").toString();
				if("0".equals(str)){
					SqliteUtil.Message(map);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}


		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "[shinephone] 接收Registration Id : " + regId);
			//send the Registration Id to your server...

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

			Log.d(TAG, "[shinephone] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

//        	processCustomMessage(context, bundle);
//        	T.make(JPushInterface.EXTRA_MESSAGE);
//        	T.dialog(context,bundle.getString(JPushInterface.EXTRA_MESSAGE));
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[shinephone] 接收到推送下来的通知");
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "[shinephone] 接收到推送下来的通知的ID: " + notifactionId);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "[shinephone] 用户点击打开了通知");
			//打开自定义的Activity
			try {

				if(map.containsKey("cn.jpush.android.EXTRA")){
					JSONObject jsonObject=new JSONObject(map.get("cn.jpush.android.EXTRA").toString());
					String str=jsonObject.get("type").toString();
					if("0".equals(str)){
//    					SqliteUtil.Message(map);
						Intent i = new Intent(context, MessagesActivity.class);
						i.putExtras(bundle);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
						context.startActivity(i);
					}
					if("1".equals(str)){
						String id=jsonObject.get("id").toString();

						Intent in = context.getPackageManager()
								.getLaunchIntentForPackage(context.getPackageName());
						in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(in);
						//发广播至main进行界面跳转
						Intent in2=new Intent("intent.action.Message_My");
						if (android.os.Build.VERSION.SDK_INT >= 12) {
							in2.setFlags(32);//3.1以后的版本需要设置Intent.FLAG_INCLUDE_STOPPED_PACKAGES
						}
						in2.putExtra("id", id);
						context.sendStickyBroadcast(in2);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			Log.d(TAG, "[shinephone] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
			//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

		} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
			boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
			Log.w(TAG, "[shinephone]" + intent.getAction() +" connected state change to "+connected);
		} else {
			Log.d(TAG, "[shinephone] Unhandled intent - " + intent.getAction());
		}

	}
	// 打印所有的 intent extra 数据
//	private static String printBundle(Bundle bundle) {
//		StringBuilder sb = new StringBuilder();
//
//
//		}
//		if(map.containsKey("cn.jpush.android.NOTIFICATION_ID")){
//			values.put("id", map.get("cn.jpush.android.NOTIFICATION_ID").toString());
//		}
//		if(map.containsKey("cn.jpush.android.MSG_ID")){
//			values.put("msgid", map.get("cn.jpush.android.MSG_ID").toString());
//			base.insert("message", null, values);
//		}
//		return sb.toString();
//	}

	//接受到消息在这里面处理
	private void processCustomMessage(Context context, Bundle bundle) {
		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
		msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
		if (!ExampleUtil.isEmpty(extras)) {
			try {
				JSONObject extraJson = new JSONObject(extras);
				if (null != extraJson && extraJson.length() > 0) {
					msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
				}
			} catch (JSONException e) {

			}

		}
		context.sendBroadcast(msgIntent);
	}
}


//if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
//	System.out.println(1);
//	sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
//}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
//	sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
//} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
//	if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
//		Log.i(TAG, "This message has no Extra data");
//		continue;
//	}
//
//	try {
//		JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//		Iterator<String> it =  json.keys();
//
//		while (it.hasNext()) {
//			String myKey = it.next().toString();
//			sb.append("\nkey:" + key + ", value: [" +
//					myKey + " - " +json.optString(myKey) + "]");
//		}
//	} catch (JSONException e) {
//		Log.e(TAG, "Get message extra JSON error!");
//	}
//
//} else {
//	sb.append("\n键:" + key + ", 值:" + bundle.getString(key));
//}
