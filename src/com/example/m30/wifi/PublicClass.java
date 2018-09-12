package com.example.m30.wifi;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.growatt.shinephone.R;





public class PublicClass {
	
	public static final int REQ_SELECT_WIFI = 0;
	public static final int REQ_SELECT_ROUTER = 1;
	
	public static final int RES_SELECT_WIFI_OK = 10;
	public static final int RES_SELECT_WIFI_FAIL = 11;
	public static final int RES_SELECT_ROUTER_OK = 20;
	public static final int RES_SELECT_ROUTER_FAIL = 21;

	public static final String START_WIFI = "WIFI";
	public static final String START_ROUTER = "ROUTER";
	
	public static final String SECURITY_WEP = "WEP";
	public static final String SECURITY_OPEN_NONE = "OPEN_NONE";
	public static final String SECURITY_OPEN_WEP_A = "OPEN_WEPA";
	public static final String SECURITY_OPEN_WEP_H = "OPEN_WEPH";
	public static final String SECURITY_SHARED_WEP_A = "SHARED_WEPA";
	public static final String SECURITY_SHARED_WEP_H = "SHARED_WEPH";
	public static final String SECURITY_WPAPSK_AES = "WPAPSK_AES";
	public static final String SECURITY_WPAPSK_TKIP = "WPAPSK_TKIP";
	public static final String SECURITY_WPA2PSK_AES = "WPA2PSK_AES";
	public static final String SECURITY_WPA2PSK_TKIP = "WPA2PSK_TKIP";	
	
	public static final String PEER_ADDRESS = "192.168.10.100";
	public static final String CMD_SCAN_MODULE = "HF-A11ASSISTHREAD";
	public static final String CMD_ENTER_CMD = "+ok";
	public static final String CMD_EXIT_CMD = "AT+Q\r";
	public static final String CMD_QUERY_WSSSID = "AT+WSSSID\r";
	public static final String CMD_QUERY_WANN = "AT+WANN\r";
	public static final String CMD_SET_WSSSID = "AT+WSSSID=%s\r";
	public static final String CMD_SET_WSKEY = "AT+WSKEY=%s\r";
	public static final String CMD_RELOAD_MODULE = "AT+RELD\r";
	public static final String CMD_RESET_MODULE = "AT+Z\r";
	public static final String RESPONSE_SCAN_MODULE = "192.168.10.100";
	public static final String RESPONSE_SET_OK = "+ok";
	public static final String RESPONSE_QUERY_OK = "+ok=";
	public static final String RESPONSE_SET_ERR = "+ERR";
	public static final String CMD_SET_WAKEY = "AT+WAKEY=%s\r";
	public static final String CMD_QUERY_WAP = "AT+WAP\r";
	
	public static final int UDP_PORT = 48899;
	
	public static final int[] STATE_SECURED = {R.attr.state_encrypted};
	public static final int[] STATE_NONE = {};	
	
    public static final int SECID_NONE = 0;
    public static final int SECID_WEP = 1;
    public static final int SECID_PSK = 2;
    public static final int SECID_EAP = 3;
        
    public final static int LINKRESULT_PASS = 0;
    public final static int LINKRESULT_FAIL = 1;
    public final static int LINKRESULT_SYSTEM = 2;
    
    public static final String DEFAULT_WIFIPWD = "12345678";
    
    public static final int UDPRUN = 10000;
    public static final int UDPSTOP = 10001;
    
    
    public static enum ProcessStage {
    	
        TimerStart,
        WifiScaning,
        WifiFreshed,
        WifiLinking,
        WifiLinked,
        StartScanModule,
        EndScanModule,
        StartQuerySSID,
        EndQuerySSID,
        StartQueryStatus,
        EndQueryStatus,
        StartSetSSID,
        EndSetSSID,
        StartSetPwd,
        EndSetPwd,
        StartSetWifiPsw,
        EndSetWifiPsw,
        StartGetCollectorNumber,
        EndGetCollectorNumber,
        StartResetModule,
        EndResetModule,
        Complete,
        TimerClose,
        chkWifiStatus
    }    

    public static enum UDPSTATUS {
        close,
        run
    }
    
    public static enum Results {
        Sucess,
        Fail
    }
    
    public static enum SelectMethod {
        Auto,
        Smart,
        System,
        Invalid
    }
    
    public static enum DownloadStage {
        DownloadStart,
        Downloading,
        DownloadFinish
    }
	
	/* 鏋勯�犳柟娉�? */
	public PublicClass() {

	}
	
	public static String validateWebbox(String serialNum) {
		if (serialNum == null || "".equals(serialNum.trim())) {
			return "";
		}
		byte[] snBytes = serialNum.getBytes();
		int sum = 0;
		for (byte snByte : snBytes) {
			sum += snByte;
		}
		int B = sum % 8;
		String text = Integer.toHexString(sum * sum);
		int length = text.length();
		String resultTemp = text.substring(0, 2) + text.substring(length - 2, length) + B;
		String result = "";
		char[] charArray = resultTemp.toCharArray();
		for (char c : charArray) {
			if (c == 0x30 || c == 0x4F || c == 0x6F) {
				c++;
			}
			result += c;
		}
		return result.toUpperCase();
	}
	
	/* 鑾峰彇杞欢鐗堟湰鍙凤紝瀵瑰簲AndroidManifest.xml涓媋ndroid:versionCode */
	public static int getCurrentVersion(Context context)
	{
		int versionCode = 0;

		try{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			versionCode = packInfo.versionCode;
		} catch (NameNotFoundException e){
			e.printStackTrace();
		}
		
		return versionCode;
	}
	
	/* 鑾峰彇杞欢鐗堟湰鍚嶇О锛屽搴擜ndroidManifest.xml涓媋ndroid:versionName */
	public static String getVersionName(Context context)
	{
		String versionName = "";

		try{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e){
			e.printStackTrace();
		}
		
		return versionName;
	}
}