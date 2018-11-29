package com.growatt.shinephone.update;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.util.Xml;

import com.growatt.shinephone.util.Urlsutil;

public class UpdateClass {
    
//    public static final String url ="http://server.growatt.com/app/xml/ShinePhone.xml";
//    public static final String url =Urlsutil.url + "/app/xml/ShinePhone.xml";
    public static final String UPDATE_LOG__XML = Urlsutil.getInstance().GetUrl() +"/app/xml/UpdateLog.xml";
    
    public static final String DIR = Environment.getExternalStorageDirectory().toString()+"/ShinePhone";
    public static final String xmlFileName = "/LatLong.xml";
    public static final String xmlFileName2 = "/ServerAddress.xml";
    public static final String xmlFileName3 = "/UpdateLog.xml";
    
    public static enum DownloadStage {
        DownloadStart,
        Downloading,
        DownloadFinish
    }
    
    public static enum UpdateStage {
    	UpdateLogin,
    	UpdateAbout
    }
    
    public static enum LatLngType {
    	googleType,
    	baiduType
    }
    
    public static enum LoginStatus {
        StartLogin,
        IsLogining,
        SuccessLogin,
        NetworkError,
        UserInfoError,
        ServerError,
        EndLogin
    }

	public UpdateClass() {

	}
	
	/* 获取软件版本号，对应AndroidManifest.xml下android:versionCode */
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
	
	/* 获取软件版本名称，对应AndroidManifest.xml下android:versionName */
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

	
	
	public static double[] parse(String xmlFileName){
		double[] arr = new double[2];
		double lat = 0;
		double lon = 0;
        FileInputStream inputStream=null;
        XmlPullParser xmlParser = Xml.newPullParser(); 
        try {
        	inputStream = new FileInputStream(xmlFileName);
            xmlParser.setInput(inputStream, "utf-8");
            int evtType=xmlParser.getEventType();
         while(evtType!=XmlPullParser.END_DOCUMENT){ 
            switch(evtType){ 
            case XmlPullParser.START_DOCUMENT:
            	
            	break;
            case XmlPullParser.START_TAG:
                String tag = xmlParser.getName(); 
                if (tag.equals("Latitude")) { 
                		lat =  Double.parseDouble(xmlParser.nextText());
                		arr[0] = lat;
                }
                if (tag.equals("Longitude")) { 
            		lon =  Double.parseDouble(xmlParser.nextText());
            		arr[1] = lon;
            }
                break;
           case XmlPullParser.END_TAG:
        	   break;
           default:
				break;
            }
            evtType=xmlParser.next();
         }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } 
        
        return arr; 
    }
	
	/**
	 * 
	 * @author Administrator
	 * @throws IOException 
	 * 
	 */
	public static String subString(String str)
	{
		String newStr = null;
		if(str.length()>6){
			newStr = str.substring(0, 6);
		}else{
			newStr = str;
		}
		return newStr;
		
	}
	
	
	/**
	 * 取经纬度
	 * 
	 * @author Administrator
	 * @throws IOException 
	 * 
	 */
	public static String[] getLatLng(Context context,int type)
	{
		String[] str = new String[2];
		SharedPreferences shared = context.getSharedPreferences("LatLngMap", context.MODE_PRIVATE);
		if(LatLngType.googleType.ordinal() == type)
		{
			String googleLat = shared.getString("googleLat", "");
			String googlelng = shared.getString("googlelng", "");
			if(googleLat.equals("") && googlelng.equals(""))
			{
				str[0] = 0+"";
				str[1] = 0+"";
				
			}else
			{
				str[0] = googleLat;
				str[1] = googlelng;
			}
		}else if(LatLngType.baiduType.ordinal() == type)
		{
			String baiduLat = shared.getString("baiduLat", "");
			String baidulng = shared.getString("baidulng", "");
			if(baiduLat.equals("") && baidulng.equals(""))
			{
				str[0] = 0+"";
				str[1] = 0+"";
				
			}else
			{
				str[0] = baiduLat;
				str[1] = baidulng;
			}
		}
			return str;
	}
	
	public static String[] getLatLngFromService(Context context,String json)
	{
		String[] str = new String[2];
		JSONObject jsonObject = null;
		if (json != null) {
			try {
				jsonObject = new JSONObject(json);
				if(jsonObject.getBoolean("success")==true)
				{
					if((jsonObject.getString("Lat")!=null) && (jsonObject.getString("Lng")!=null))
					{
						str[0] = jsonObject.getString("Lat");
						str[1] = jsonObject.getString("Lng");
					}else
					{
						str[0] = "0";
						str[1] = "0";
					}
					
				}else if(jsonObject.getBoolean("success")==false){
					str[0] = "0";
					str[1] = "0";
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else
		{
			str[0] = "0";
			str[1] = "0";
		}
		return str;
	}
	
	public static double getDouble(double d)
	{
		String s = Double.toString(d);
		int index = s.substring(s.indexOf('.') + 1).length();
		double db = 0;
		if(index>1)
		{
			BigDecimal bd = new BigDecimal(s);   
			db  =  bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
			return db;
		}else
		{
			return d;
		}
	}
	
	public static String getStr(String str)
	{
		String s = "";
		if(str.contains(" kWh"))
		{
			s = str.replace(" kWh", "");
		}else if(str.contains(" "))
		{
			s = str.replace(" ", "");
		}else if(str.contains(" MWh"))
		{
			s = str.replace(" MWh", "");
		}else if(str.contains("MWh"))
		{
			s = str.replace("MWh", "");
		}else if(str.contains(" W"))
		{
			s = str.replace(" W", "");
		}else
		{
			s = str;
		}
		int index = s.substring(s.indexOf('.') + 1).length();
		double db = 0;
		if(index>1)
		{
			BigDecimal bd = new BigDecimal(s);   
			db  =  bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
			return Double.toString(db);
		}else
		{
			return s;
		}
	}
	
	public static String getServerAddress(Context context)
	{
		String serverAddress = "";
		SharedPreferences server = context.getSharedPreferences("serverAddress", Context.MODE_PRIVATE);
		serverAddress = server.getString("serverAddress", "");
		
		return serverAddress;
	}
	
	public static void saveServerAddress(Context context,String serverAddress)
	{
		SharedPreferences server = context.getSharedPreferences("serverAddress", Context.MODE_PRIVATE);
		Editor edit = server.edit();
		
		if(serverAddress != null)
		{
			edit.putString("serverAddress", serverAddress);
		}else
		{
			edit.putString("serverAddress", "");
		}
		
	}
	
	public static String getServerAddressXML()
	{
			String serverAddress = "";
			if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
				serverAddress = "";
				return serverAddress;
			}
			
			File locationFile = new File(DIR + xmlFileName2);
			
			if(!locationFile.exists())
			{
				serverAddress = "";
				return serverAddress;
			}
			
		    FileInputStream inputStream=null;
	        XmlPullParser xmlParser = Xml.newPullParser(); 
	        try {
	        	inputStream = new FileInputStream(DIR + xmlFileName2);
	            xmlParser.setInput(inputStream, "utf-8");
	            int evtType=xmlParser.getEventType();
	         while(evtType!=XmlPullParser.END_DOCUMENT){ 
	            switch(evtType){ 
	            case XmlPullParser.START_DOCUMENT:
	            	
	            	break;
	            case XmlPullParser.START_TAG:
	                String tag = xmlParser.getName(); 
	                if (tag.equals("serverAddress")) { 
	                	serverAddress =  xmlParser.nextText();
	                }
	                break;
	           case XmlPullParser.END_TAG:
	        	   break;
	           default:
					break;
	            }
	            evtType=xmlParser.next();
	         }
	        } catch (XmlPullParserException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }catch (IOException e1) {
	            // TODO Auto-generated catch block
	            e1.printStackTrace();
	        } 
	        
	        return serverAddress; 
	}
	
	public static boolean saveServerAddressXML(String serverAddress)
	{
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			return false;
		}
		
		File xmlFile = new File(DIR);
		if(!xmlFile.exists())
		{
			xmlFile.mkdir();
		}
		
		String name = DIR + xmlFileName2;
		File locationFile = new File(name);
		
		if(!locationFile.exists())
		{
			try {
				locationFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
		FileOutputStream fileos = null;
		try {
		fileos = new FileOutputStream(locationFile);
		} catch (FileNotFoundException e) {
			return false;
		}
		
		XmlSerializer serializer = Xml.newSerializer();
		try {
		serializer.setOutput(fileos, "UTF-8");
		serializer.startDocument(null, Boolean.valueOf(true));
		serializer.setFeature(
		"http://xmlpull.org/v1/doc/features.html#indent-output",
		true);
			serializer.startTag(null, "serverAddress");
			serializer.text(serverAddress+"");
			serializer.endTag(null, "serverAddress");
			serializer.endDocument();
	
		serializer.flush();
		fileos.close();
		} catch (Exception e) {
			
			return false;
		}
		
		return true;
	}
	
	
}