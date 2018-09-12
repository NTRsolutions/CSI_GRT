package com.growatt.shinephone.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.growatt.shinephone.activity.ShineApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetWifiInfo {
	private WifiManager mWifiManager;
	private String ssid;
	private String mAuthString="WPA-PSK";
	private byte AuthModeOpen = 0x00;
	private byte AuthModeShared = 0x01;
	private byte AuthModeAutoSwitch = 0x02;
	private byte AuthModeWPA = 0x03;
	private byte AuthModeWPAPSK = 0x04;
	private byte AuthModeWPANone = 0x05;
	private byte AuthModeWPA2 = 0x06;
	private byte AuthModeWPA2PSK = 0x07;   
	private byte AuthModeWPA1WPA2 = 0x08;
	private byte AuthModeWPA1PSKWPA2PSK = 0x09;
	private byte mAuthMode;
	private Context context;
	
	public GetWifiInfo(Context context) {
		this.context=context;
	}
	public Map<String, Object> Info(){
		Map<String, Object> map=new HashMap<String, Object>();
		System.out.println("��ȡȨ��1");
		mWifiManager = (WifiManager) ShineApplication.context.getSystemService (Context.WIFI_SERVICE); 
		if(mWifiManager==null){
			T.make("mWifiManager==null",context);
			return map;
		}
		WifiInfo WifiInfo = mWifiManager.getConnectionInfo();
			if(WifiInfo==null){
				return map;
			}
			System.out.println("��ȡȨ��2");
			ssid = WifiInfo.getSSID();
			int iLen = ssid.length();	
			if (ssid.startsWith("\"") && ssid.endsWith("\""))
			{
				ssid = ssid.substring(1, iLen - 1);
			}
			map.put("ssid", ssid);
			List<ScanResult> ScanResultlist = mWifiManager.getScanResults();
			for (int i = 0, len = ScanResultlist.size(); i < len; i++) {
				ScanResult AccessPoint = ScanResultlist.get(i);			

				if (AccessPoint.SSID.equals(ssid)){
					
					boolean WpaPsk = AccessPoint.capabilities.contains("WPA-PSK");
					boolean Wpa2Psk = AccessPoint.capabilities.contains("WPA2-PSK");
					boolean Wpa = AccessPoint.capabilities.contains("WPA-EAP");
					boolean Wpa2 = AccessPoint.capabilities.contains("WPA2-EAP");

					if (AccessPoint.capabilities.contains("WEP"))
					{
						mAuthString = "OPEN-WEP";
						mAuthMode = AuthModeOpen;
						break;
					}

					if (WpaPsk && Wpa2Psk)
					{
						mAuthString = "WPA-PSK WPA2-PSK";
						mAuthMode = AuthModeWPA1PSKWPA2PSK;
						break;
					}
					else if (Wpa2Psk)
					{
						mAuthString = "WPA2-PSK";
						mAuthMode = AuthModeWPA2PSK;
						break;
					}
					else if (WpaPsk)
					{
						mAuthString = "WPA-PSK";
						mAuthMode = AuthModeWPAPSK;
						break;
					}

					if (Wpa && Wpa2)
					{
						mAuthString = "WPA-EAP WPA2-EAP";
						mAuthMode = AuthModeWPA1WPA2;
						break;
					}
					else if (Wpa2)
					{
						mAuthString = "WPA2-EAP";
						mAuthMode = AuthModeWPA2;
						break;
					}
					else if (Wpa)
					{
						mAuthString = "WPA-EAP";
						mAuthMode = AuthModeWPA;
						break;
					}				

					mAuthString = "OPEN";
					mAuthMode = AuthModeOpen;

				}
			}
			map.put("mAuthString", mAuthString);
			map.put("mAuthMode", mAuthMode);
			System.out.println(mAuthString);
			System.out.println(mAuthMode);
			return map;
//			handler.sendEmptyMessage(0);
	}

}
