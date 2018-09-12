package com.growatt.shinephone.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.bean.v2.Fragment1ListBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SqliteUtil {
	public static String inquiryIs(){
		String times="";
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.query("islogin",null,null,null,null,null,null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					times=c.getString(c.getColumnIndex("falg"));
					c.moveToNext();
				}
			}
		}
		c.close();
		base.close();
		return times;
	}
	public static void islogin(String time){
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		base.delete("islogin",null, null);
		ContentValues values = new ContentValues();
		values.put("falg", time);
		base.insert("islogin", null, values);
		base.close();
	}
	public static String inquirytime(){
		String times = "";
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.query("times",null,null,null,null,null,null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					times=c.getString(c.getColumnIndex("time"));
					c.moveToNext();
				}
			}
		}
		c.close();
		base.close();
		return times;
	}
	public static String inquiryplant(){
		String times = "";
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.query("plant",null,null,null,null,null,null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					times=c.getString(c.getColumnIndex("plant"));
					c.moveToNext();
				}
			}
		}
		c.close();
		base.close();

		return times;
	}
	public static List<Map<String, Object>> inquirymsg(){
		List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.query("message",null,null,null,null,null,null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					Map<String, Object>map=new HashMap<String, Object>();
					map.put("content", c.getString(c.getColumnIndex("content")));
					map.put("id", c.getString(c.getColumnIndex("id")));
					map.put("msgid", c.getString(c.getColumnIndex("msgid")));
					map.put("title", c.getString(c.getColumnIndex("title")));
					map.put("type", c.getString(c.getColumnIndex("type")));
					map.put("date", c.getString(c.getColumnIndex("date")));
					list.add(map);
					c.moveToNext();
				}
			}
		}
		c.close();
		base.close();

		return list;
	}
	public static String inquiryurl(){
		String times = "";
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.query("url",null,null,null,null,null,null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					times=c.getString(c.getColumnIndex("url"));
					c.moveToNext();
				}
			}
		}
		c.close();
		base.close();

		return times;
	}
	public static Map<String, Object> inquirylogin(){
		Map<String, Object >map=new HashMap<String, Object>();
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.query("login",null,null,null,null,null,null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					map.put("name", c.getString(c.getColumnIndex("name")));
					map.put("pwd", c.getString(c.getColumnIndex("pwd")));
					c.moveToNext();
				}
			}
		}
		c.close();
		base.close();
		return map;
	}
	public static void delectmessages(){
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		base.delete("message",null,null);
		base.close();
	}
	public static void delectmessage(String id){
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		base.delete("message","msgid = ?", new String[]{id});
		
		base.close();
	}
	public static void url(String url){
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		base.delete("url",null, null);
		ContentValues values = new ContentValues();
		values.put("url", url);
		base.insert("url", null, values);
		base.close();
	}
	public static void time(String time){
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		base.delete("times",null, null);
		ContentValues values = new ContentValues();
		values.put("time", time);
		base.insert("times", null, values);
		base.close();
	}
	public static void Message(Map<String, Object> map){
		System.out.println(map.toString());
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		ContentValues values = new ContentValues();
		try {
			JSONObject jsonObject=new JSONObject(map.get("cn.jpush.android.EXTRA").toString());
			values.put("content", jsonObject.get("content").toString());
			values.put("type", jsonObject.get("type").toString());
			values.put("date", jsonObject.get("date").toString());
			values.put("title", jsonObject.get("title").toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		values.put("id", map.get("cn.jpush.android.NOTIFICATION_ID").toString());
		values.put("msgid", map.get("cn.jpush.android.MSG_ID").toString());
		Cursor c = base.query("message",new String[]{"msgid"},"msgid = ?",new String[]{map.get("cn.jpush.android.MSG_ID").toString()},null,null,null);
		if(c.getCount()==0){
			base.insert("message", null, values);
		}
		c.close();
		base.close();
	}
	public static void login(String name,String pwd){
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		base.delete("login",null, null);
		ContentValues values = new ContentValues();
		values.put("name", name);
		values.put("pwd", pwd);
		base.insert("login", null, values);
		base.close();
	}
	public static void deleteUser(){
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		base.delete("login",null, null);
		base.close();
	}
	public static void plant(String time){
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		base.delete("plant",null, null);
		ContentValues values = new ContentValues();
		values.put("plant", time);
		base.insert("plant", null, values);
		base.close();
	}

	public static void ids(Map<String, Object> map){
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.query("ids",new String[]{"deviceSn"},"deviceSn = ?",new String[]{map.get("deviceSn").toString()},null,null,null);
		ContentValues values = new ContentValues();
		values.put("plant",map.get("plant").toString() );
		values.put("deviceAilas",map.get("deviceAilas").toString() );
		values.put("deviceType",map.get("deviceType").toString() );
		values.put("deviceSn",map.get("deviceSn").toString() );
		values.put("deviceStatus", map.get("deviceStatus").toString());
		values.put("energy",map.get("energy").toString() );
		values.put("datalogSn", map.get("datalogSn").toString());
		values.put("id",map.get("id").toString() );
		
		values.put("userId", map.get("userId").toString());
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					String s = c.getString(c.getColumnIndex("deviceSn"));
					//					System.out.println("deviceSn="+s);
					if(s.equals(map.get("deviceSn").toString())){
						base.execSQL("update ids set id='"+map.get("id").toString()+"' where deviceSn='"+map.get("deviceSn").toString()+"'");
						c.close();
						base.close();
						break;
					}else{
						// update ids set deviceSn='"+deviceSn+"' where id=?;
						c.moveToNext();
					}
				}
			}
		}else{
			base.insert("ids", null, values);
			c.close();
			base.close();
		}
	}

	public static void ids(Fragment1ListBean bean){
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.query("ids",new String[]{"deviceSn"},"deviceSn = ?",new String[]{bean.getDeviceSn()},null,null,null);
		ContentValues values = new ContentValues();
		values.put("plant",bean.getPlantId() );
		values.put("deviceAilas",bean.getDeviceAilas() );
		values.put("deviceType",bean.getDeviceType() );
		values.put("deviceSn",bean.getDeviceSn() );
		values.put("deviceStatus", bean.getDeviceStatus());
		values.put("energy",bean.getEnergy());
		values.put("datalogSn", bean.getDatalogSn());
		values.put("id",bean.getTimeId());

		values.put("userId", bean.getUserId());
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					String s = c.getString(c.getColumnIndex("deviceSn"));
					//					System.out.println("deviceSn="+s);
					if(s.equals(bean.getDeviceSn())){
						base.execSQL("update ids set id='"+bean.getTimeId()+"' where deviceSn='"+bean.getDeviceSn()+"'");
						c.close();
						base.close();
						break;
					}else{
						// update ids set deviceSn='"+deviceSn+"' where id=?;
						c.moveToNext();
					}
				}
			}
		}else{
			base.insert("ids", null, values);
			c.close();
			base.close();
		}
	}
	//	map.put("deviceAilas", jsonObject.get("deviceAilas").toString());
	//	map.put("deviceType", jsonObject.get("deviceType").toString());
	//	map.put("deviceSn", jsonObject.get("deviceSn").toString());
	//	map.put("deviceStatus", jsonObject.get("deviceStatus").toString());
	//	map.put("energy", jsonObject.get("energy").toString());
	//	map.put("datalogSn", jsonObject.get("datalogSn").toString());

	public static List<Map<String, Object>> inquiryids(String plants){
		List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.rawQuery("select * from ids where plant = '"+plants+"';", null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					Map<String, Object>map=new HashMap<String, Object>();
					String plant = c.getString(c.getColumnIndex("plant"));
					String deviceAilas=c.getString(c.getColumnIndex("deviceAilas"));
					String deviceType=c.getString(c.getColumnIndex("deviceType"));
					String deviceSn=c.getString(c.getColumnIndex("deviceSn"));
					String energy=c.getString(c.getColumnIndex("energy"));
					String deviceStatus=c.getString(c.getColumnIndex("deviceStatus"));
					String datalogSn=c.getString(c.getColumnIndex("datalogSn"));
					String id=c.getString(c.getColumnIndex("id"));
					String userId=c.getString(c.getColumnIndex("userId"));
					map.put("plant", plant);
					map.put("deviceAilas", deviceAilas);
					map.put("deviceType", deviceType);
					map.put("deviceSn", deviceSn);
					map.put("deviceStatus", deviceStatus);
					map.put("energy", energy);
					map.put("datalogSn", datalogSn);
					map.put("id", id);
					map.put("userId", userId);
					list.add(map);
					c.moveToNext();
				}
			}
		}
		c.close();
		base.close();
		return list;
	}
	public static List<Fragment1ListBean> inquiryidsByPlant(String plants){
		List<Fragment1ListBean> list=new ArrayList<>();
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.rawQuery("select * from ids where plant = '"+plants+"';", null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					Fragment1ListBean bean = new Fragment1ListBean();
					String plant = c.getString(c.getColumnIndex("plant"));
					String deviceAilas=c.getString(c.getColumnIndex("deviceAilas"));
					String deviceType=c.getString(c.getColumnIndex("deviceType"));
					String deviceSn=c.getString(c.getColumnIndex("deviceSn"));
					String energy=c.getString(c.getColumnIndex("energy"));
					String deviceStatus=c.getString(c.getColumnIndex("deviceStatus"));
					String datalogSn=c.getString(c.getColumnIndex("datalogSn"));
					String id=c.getString(c.getColumnIndex("id"));
					String userId=c.getString(c.getColumnIndex("userId"));
					bean.setPlantId(plant);
					bean.setDeviceAilas(deviceAilas);
					bean.setDeviceType(deviceType);
					bean.setDeviceSn(deviceSn);
					bean.setEnergy(energy);
					bean.setDatalogSn(datalogSn);
					bean.setTimeId(id);
					bean.setUserId(userId);
					list.add(bean);
					c.moveToNext();
				}
			}
		}
		c.close();
		base.close();
		return list;
	}

	public static void setService(String service,int app_code){
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("service", service);
		values.put("app_code", app_code);
		base.insert("service", null, values);
		base.close();
	}
	public static String getService(){
		String service="";
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.query("service",null,null,null,null,null,null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					service=c.getString(c.getColumnIndex("service"));
					c.moveToNext();
				}
			}
		}
		c.close();
		base.close();
		return service;
	}
	//��ȡapp_code���º�
	public static int getApp_Code(){
		int app_code=-1;
		DateSqlite dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.query("service",null,null,null,null,null,null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					app_code=c.getInt(c.getColumnIndex("app_code"));
					c.moveToNext();
				}
			}
		}
		c.close();
		base.close();
		return app_code;
	}
	/*查找apn*/
	public static String apn(String simOperatorName,String numeric){

		String name = "";
		SQLiteOpenHelper dataSQiLte=new DateSqlite(ShineApplication.context);
		SQLiteDatabase base = dataSQiLte.getWritableDatabase();
		Cursor c = base.query("carriers",new String[]{"numeric"},"numeric = "+numeric,null,null,null,null);
		if(c.getCount()>0){
			if(c.moveToFirst()){
				for(int i=0;i<c.getCount();i++){
					name=c.getString(c.getColumnIndex("name"));
					Log.i("apnName", "apnName="+name);
					c.moveToNext();
				}
			}
		}
		c.close();
		base.close();

		return name;
	}
}













