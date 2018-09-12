package com.example.m30.wifi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.AuthAlgorithm;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.net.wifi.WifiConfiguration.Protocol;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

public class WiFiAdmin {
	
	//瀹氫箟鍚姩锟�?
	private String mStartSource;
		
	// 瀹氫箟WifiManager瀵硅薄
	private WifiManager mWifiManager;
	
	// 瀹氫箟WifiInfo瀵硅薄
	private WifiInfo mWifiInfo;
	
	// 瀹氫箟锟�?涓猈ifiLock
	WifiLock mWifiLock;

	/* 鏋勶拷?锟芥柟锟�? */
	public WiFiAdmin(Context context,String startSource) {
		mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		mWifiInfo = mWifiManager.getConnectionInfo();
		mStartSource = startSource;
	}

	/* 鎵撳紑Wi-Fi缃戝崱 */
	public void openNetCard() {
		if (!mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(true);
		}
	}

	/* 鍏抽棴Wi-Fi缃戝崱 */
	public void closeNetCard() {
		if (mWifiManager.isWifiEnabled()) {
			mWifiManager.setWifiEnabled(false);
		}
	}

	/* 锟�?鏌ュ綋鍓峎i-Fi缃戝崱鐘讹拷?? */
	public boolean checkNetCardState() {
		
		switch (mWifiManager.getWifiState()){
			case WifiManager.WIFI_STATE_ENABLED:	//缃戝崱宸茬粡鎵撳紑
				return true;
			case WifiManager.WIFI_STATE_DISABLING:	//缃戝崱姝ｅ湪鍏抽棴
			case WifiManager.WIFI_STATE_DISABLED:	//缃戝崱宸茬粡鍏抽棴
			case WifiManager.WIFI_STATE_ENABLING:	//缃戝崱姝ｅ湪鎵撳紑
			case WifiManager.WIFI_STATE_UNKNOWN:	//缃戝崱鐘讹拷?锟芥湭锟�?
			default:	
				return false;
		}
	}

	/* 鑾峰彇Wi-Fi鐑偣鎵弿缁撴灉*/
	public List<HashMap<String, String>> getWiFiScanResult() {
		boolean bNormalScan;
		String strSSID;
		String strSecrity;
		String strFrequency;
		String strLevel;
		
		int iSSIDLenthFilter[] = {10,12,25};
		int iFrequencyFilter[] = {24};
		Arrays.sort(iSSIDLenthFilter);
		Arrays.sort(iFrequencyFilter);
	
		ScanResult mItemScanResult;
		List<ScanResult> mTotalScanResult;

		List<HashMap<String, String>> mWiFiInfoList = new ArrayList<HashMap<String, String>>();
		
		// 鎵弿鍛ㄨ竟缃戠粶
		bNormalScan = mWifiManager.startScan();
		if (bNormalScan != true){
			return null;
		}
		
		//鑾峰緱鎵弿缁撴灉
		mTotalScanResult = mWifiManager.getScanResults();
		if (mTotalScanResult == null){
			return null;
		}
		
		//瀵规壂鎻忕粨鏋滆繘琛屽锟�?
		for (int i = 0; i < mTotalScanResult.size(); i++) {
			
			//鑾峰彇鍘熷鏁版嵁
			mItemScanResult = mTotalScanResult.get(i);
			
			strSSID = mItemScanResult.SSID;
			strSecrity = mItemScanResult.capabilities;
			strFrequency = Integer.toString(mItemScanResult.frequency);
			strLevel = Integer.toString(mItemScanResult.level);
			
			//浠ヤ笅锟�?濮嬪鍘熷鏁版嵁杩涜杩囨护锛屽舰鎴愭湁鏁堟暟锟�?//
//			if (mStartSource.equals(PublicClass.START_WIFI)){
//				//杩囨护SSID鐨勯暱锟�?
//				if (Arrays.binarySearch(iSSIDLenthFilter, strSSID.length()) < 0){
//					continue;
//				}
//
//				//鍒ゆ柇SSID鐨勫紑澶存槸鍚︾鍚堜骇鍝佺壒锟�?
////				String regEx01 = "^(AH)|^(ShineWifiBox)";
////				String regEx02 = "^(YX)|^(ShineWifiBox)";
////				String regEx03 = "^(ZZ)|^(ShineWifiBox)";
//				String regEx = "^(AH)|^(ShineWifiBox)|^(YX)|^(ShineWifiBox)|^(ZZ)|^(ShineWifiBox)|^(4K)|^(ShineWifiBox)|^(YU)|^(ShineWifiBox)|^(2Q)|^(ShineWifiBox)";
////				boolean bMatchResult01 = Pattern.compile(regEx01,Pattern.MULTILINE).matcher(strSSID).find();
////				boolean bMatchResult02 = Pattern.compile(regEx02,Pattern.MULTILINE).matcher(strSSID).find();
////				boolean bMatchResult03 = Pattern.compile(regEx03,Pattern.MULTILINE).matcher(strSSID).find();
//				boolean bMatchResult = Pattern.compile(regEx,Pattern.MULTILINE).matcher(strSSID).find();
//				if ((bMatchResult != true)){
//					continue;
//				}
//			}else{
//
//				//鍒ゆ柇SSID鐨勫紑澶存槸鍚︾鍚堜骇鍝佺壒锟�?
//				String regEx01 = "^(AH)|^(ShineWifiBox)";
//				String regEx02 = "^(YX)|^(ShineWifiBox)";
//				String regEx03 = "^(ZZ)|^(ShineWifiBox)";
//				String regEx04 = "^(4K)|^(ShineWifiBox)";
//				String regEx05 = "^(YU)|^(ShineWifiBox)";
//				String regEx06 = "^(2Q)|^(ShineWifiBox)";
//				boolean bMatchResult01 = Pattern.compile(regEx01,Pattern.MULTILINE).matcher(strSSID).find();
//				boolean bMatchResult02 = Pattern.compile(regEx02,Pattern.MULTILINE).matcher(strSSID).find();
//				boolean bMatchResult03 = Pattern.compile(regEx03,Pattern.MULTILINE).matcher(strSSID).find();
//				boolean bMatchResult04 = Pattern.compile(regEx04,Pattern.MULTILINE).matcher(strSSID).find();
//				boolean bMatchResult05 = Pattern.compile(regEx05,Pattern.MULTILINE).matcher(strSSID).find();
//				boolean bMatchResult06 = Pattern.compile(regEx06,Pattern.MULTILINE).matcher(strSSID).find();
//				if ((bMatchResult01 == true)){
//					continue;
//				}else if(bMatchResult02 == true)
//				{
//					continue;
//				}else if(bMatchResult03 == true)
//				{
//					continue;
//				}else if(bMatchResult04 == true)
//				{
//					continue;
//				}else if(bMatchResult05 == true)
//				{
//					continue;
//				}else if(bMatchResult06 == true)
//				{
//					continue;
//				}
//			}

			//杩囨护骞惰鏁翠俊鍙烽锟�?
			if (Arrays.binarySearch(iFrequencyFilter, Integer.parseInt(strFrequency)/100) < 0){
				continue;
			}
			strFrequency = Integer.parseInt(strFrequency)/1000 + "." + Integer.parseInt(strFrequency)%1000 + "GHz";
			
			//杩囨护骞惰鏁翠俊鍙峰己锟�?(寮哄害鑼冨洿锛歔0,4],鏁板瓧瓒婂ぇ锛屽己搴﹁秺锟�?)
			strLevel = Integer.toString(WifiManager.calculateSignalLevel(Integer.parseInt(strLevel), 5));
			
			//杩囨护骞惰鏁村姞瀵嗘柟锟�?
			strSecrity = parseSecurity(strSecrity).toString();
			
			//瀛樺偍鏈夋晥鏁版嵁
			HashMap<String, String> mTempMap = new HashMap<String, String>();
			mTempMap.put("SSID", strSSID);
			mTempMap.put("SEC", strSecrity);
			mTempMap.put("FREQ", strFrequency);
			mTempMap.put("LEVEL", strLevel);
			
			mWiFiInfoList.add(mTempMap);
		}
		
		return mWiFiInfoList;
	}

	/* 杩炴帴鎸囧畾鐨刉i-Fi鐑偣*/
	public boolean Connect(HashMap<String, String> WifiInfo,String Password)  
    {  
		String strSSID;
		String strPassword;
		String strSecrity;
		HashMap<String, String> aSelectedWifi = WifiInfo;
		
		if(aSelectedWifi == null){
			return false;
		}
		
		if(aSelectedWifi.isEmpty()){
			return false;
		}
		
		if(checkNetCardState() != true){
			return false;
		}
		
		strSSID = aSelectedWifi.get("SSID");
		strSecrity = aSelectedWifi.get("SEC");
		strPassword = Password;
		
		//鍒涘缓Wi-Fi閰嶇疆瀵硅薄
		WifiConfiguration mWifiConfiguration = new WifiConfiguration();    
		//mWifiConfiguration.allowedAuthAlgorithms.clear();  
		//mWifiConfiguration.allowedGroupCiphers.clear();  
		//mWifiConfiguration.allowedKeyManagement.clear();  
		//mWifiConfiguration.allowedPairwiseCiphers.clear();  
		//mWifiConfiguration.allowedProtocols.clear();
		
        //瀵筗i-Fi閰嶇疆瀵硅薄璧嬶拷??
		mWifiConfiguration.SSID = "\"" + strSSID + "\"";

		switch(getSecurityId(strSecrity)){
			case PublicClass.SECID_NONE:				
				mWifiConfiguration.wepKeys[0] = "";
				mWifiConfiguration.allowedKeyManagement.set(KeyMgmt.NONE);
				break;
				
			case PublicClass.SECID_WEP:
				mWifiConfiguration.allowedKeyManagement.set(KeyMgmt.NONE);
				mWifiConfiguration.allowedAuthAlgorithms.set(AuthAlgorithm.OPEN);
				mWifiConfiguration.allowedAuthAlgorithms.set(AuthAlgorithm.SHARED);
				mWifiConfiguration.wepKeys[0] = "\"" + strPassword + "\"";
				break;
				
			case PublicClass.SECID_PSK:				
				mWifiConfiguration.preSharedKey = "\"" + strPassword + "\"";
		        mWifiConfiguration.allowedKeyManagement.set(KeyMgmt.WPA_PSK);
                if (strSecrity.contains("WPA2") && strSecrity.contains("PSK")) {
                	mWifiConfiguration.allowedProtocols.set(Protocol.RSN);
                	
    			}else if (strSecrity.contains("WPA") && strSecrity.contains("PSK")) {
    				mWifiConfiguration.allowedProtocols.set(Protocol.WPA);
    			}
				break;
				
			case PublicClass.SECID_EAP:
				mWifiConfiguration.allowedKeyManagement.set(KeyMgmt.WPA_EAP);
				mWifiConfiguration.allowedKeyManagement.set(KeyMgmt.IEEE8021X);
				mWifiConfiguration.preSharedKey = "\"" + strPassword + "\"";			
				break;
			default:
				return false;
		}
		
		//锟�?鏌ヨ缃戠粶鏄惁宸茬粡閰嶇疆锟�?
		List<WifiConfiguration> mExistingConfigs = mWifiManager.getConfiguredNetworks(); 
        for (WifiConfiguration existingConfig : mExistingConfigs){
          if (existingConfig.SSID.equals("\"" + strSSID + "\"")){
        	  mWifiManager.removeNetwork(existingConfig.networkId);
          }
        }
        //mWifiManager.saveConfiguration();
        //mWifiManager.disconnect();
		
		//娣诲姞鍒扮綉锟�?
        int iNetworkID = mWifiManager.addNetwork(mWifiConfiguration);
        if(iNetworkID == -1){
			return false;
		}
        
        //鍚敤缃戠粶
        boolean bEnableRet = mWifiManager.enableNetwork(iNetworkID, true);
        if(bEnableRet != true){
        	return false;
        }
        
        mWifiManager.saveConfiguration();
        boolean bReconnect = mWifiManager.reconnect();
        return bReconnect;  
    }  
      
	
	/* 鑾峰彇褰撳墠Wi-Fi杩炴帴鐨勭浉鍏充俊锟�?*/
	public WifiInfo getCurrentInfo() {
		mWifiInfo = mWifiManager.getConnectionInfo();
		return mWifiInfo;
	}

	//瑙ｆ瀽Wi-Fi淇″彿瀹夊叏鑳藉姏
	private StringBuffer parseSecurity(String strCapabilities){
		
		String strTemp;
		int iWPA = -1;
		int iWPA2 = -1;
		
		StringBuffer strSecurityTemp = new StringBuffer("");
		strSecurityTemp.delete(0, strSecurityTemp.length());
		
		if (strCapabilities == null) {
			return strSecurityTemp;
		}
		
		if (strCapabilities.length() == 0) {
			return strSecurityTemp;
		}
		
		//灏哤i-Fi瀹夊叏淇℃伅杞崲涓轰互閫楀彿鍒嗛殧鐨勫舰锟�?
		strTemp = strCapabilities.replace("][", ";").replace("[", "").replace("]", "");

		if (strTemp.contains("WEP")) {
			strSecurityTemp.append(PublicClass.SECURITY_WEP);
			strSecurityTemp.append(" / ");
		}

		String[] astrCapabilities = strTemp.split(";");
		for (int iLoop = 0; iLoop < astrCapabilities.length; iLoop++) {
			if (astrCapabilities[iLoop].contains("WPA2") && astrCapabilities[iLoop].contains("PSK")) {
				iWPA2 = iLoop;
			}else if (astrCapabilities[iLoop].contains("WPA") && astrCapabilities[iLoop].contains("PSK")) {
				iWPA = iLoop;
			}
		}
		
		if (iWPA2 != -1) {
			if (astrCapabilities[iWPA2].contains("CCMP")) {
				strSecurityTemp.append(PublicClass.SECURITY_WPA2PSK_AES);
				strSecurityTemp.append(" / ");
			}else if (astrCapabilities[iWPA2].contains("TKIP")) {
				strSecurityTemp.append(PublicClass.SECURITY_WPA2PSK_TKIP);
				strSecurityTemp.append(" / ");
			}
		}else if (iWPA != -1) {
			if (astrCapabilities[iWPA].contains("CCMP")) {
				strSecurityTemp.append(PublicClass.SECURITY_WPAPSK_AES);
				strSecurityTemp.append(" / ");
			}else if (astrCapabilities[iWPA].contains("TKIP")) {
				strSecurityTemp.append(PublicClass.SECURITY_WPAPSK_TKIP);
				strSecurityTemp.append(" / ");
			}
		}
		
		//鍒犻櫎锟�?鍚庝竴锟�?" / "
		if (strSecurityTemp.length() > 3){
			strSecurityTemp.delete(strSecurityTemp.length()-3, strSecurityTemp.length());
		}

		return strSecurityTemp;
	}
	
	//鑾峰彇瀹夊叏ID
	private int getSecurityId(String strCapabilities) {
        if (strCapabilities.contains("WEP")) {
            return PublicClass.SECID_WEP;
        } else if (strCapabilities.contains("PSK")) {
            return PublicClass.SECID_PSK;
        } else if (strCapabilities.contains("EAP")) {
            return PublicClass.SECID_EAP;
        }
        return PublicClass.SECID_NONE;
    }   
}