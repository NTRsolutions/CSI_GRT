package com.example.m30.wifi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;

public class InputWifi {
	private static String mStartSource;
	private static WiFiAdmin mWiFiAdmin;
	private static WiFiAdapter mWiFiAdapter;
	private static List<HashMap<String, String>> mWiFiList;
	public static void getwifi(Context context, InputListener inputListener){
		
		mStartSource=PublicClass.START_WIFI;
		mWiFiAdmin =  new WiFiAdmin(context,mStartSource);
		mWiFiList = new ArrayList<HashMap<String, String>>();
		scanWiFiSignal(inputListener);
		if (mWiFiList.isEmpty()){
			inputListener.Error();
		}else{
			System.out.println(mWiFiList+"==============");
			inputListener.Right(mWiFiList);
		}
		
	}
	public static void getwifi(Context context, ListView listView,InputListener inputListener){
		mStartSource=PublicClass.START_WIFI;
		mWiFiAdmin =  new WiFiAdmin(context,mStartSource);
		mWiFiList = new ArrayList<HashMap<String, String>>();
		scanWiFiSignal(inputListener);
		mWiFiAdapter = new WiFiAdapter(context, mStartSource, mWiFiList);
		if (mWiFiList.isEmpty()){
			inputListener.Error();
		}else{
			listView.setAdapter(mWiFiAdapter);
			inputListener.Right(mWiFiList);
		}
	}
	private static void scanWiFiSignal(InputListener inputListener){
		mWiFiList.clear();
		mWiFiAdmin.openNetCard();
		int iLoop =0;
		while(iLoop++ < 4){
			if (mWiFiAdmin.checkNetCardState()){
				break;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
		if(mWiFiAdmin.checkNetCardState() != true){
			inputListener.Error();
			return;
		}

		List<HashMap<String, String>> mTempList = mWiFiAdmin.getWiFiScanResult();
		if (mTempList == null){
			return;
		}
		iLoop =0;
		while(mTempList.isEmpty()){

			mTempList = mWiFiAdmin.getWiFiScanResult();

			iLoop++;
			if(iLoop > 4){
				break;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}        
		mWiFiList.addAll(mTempList);
	}
	public interface InputListener{
		void Error();
		void Right(List<HashMap<String, String>> WiFiList);
	}
}
