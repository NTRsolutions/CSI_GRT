package com.dao;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import com.vo.CUserInfo;
import com.vo.EventsInfo;
import com.vo.StatusInfo;

/**
 * 
 * <pre>
 * [名 称]：数据基本操作2次封装信息类										
 * [功 能]：对软件存储数据进行增，删，改，查；										
 * [描 述]：无
 * </pre>
 * 
 * @author jim
 * @创建时间 2011-10-25
 */
public class dbtools {

	public static boolean createUser(Context mContext, CUserInfo user) {
		dataSources db = new dataSources(mContext);
		db.open();
		db.deleteUserInfo();
		long id = db.createUserInfo(user);
		db.close();

		if (id == -1) {
			return false;
		}
		return true;
	}

	public static boolean nodifyUser(Context mContext, CUserInfo user) {
		dataSources db = new dataSources(mContext);
		db.open();
		long id = db.nodifyUserInfo(user);
		db.close();

		if (id == -1) {
			return false;
		}
		return true;
	}

	public static CUserInfo findUser(Context mContext) {
		dataSources db = new dataSources(mContext);
		db.open();
		Cursor mCursor = db.findUserInfo();
		if (mCursor == null || mCursor.getCount() <= 0) {
			if (mCursor != null) {
				mCursor.close();
			}
			db.close();
			return null;
		} else {
			mCursor.moveToFirst();
			CUserInfo tmpUser = new CUserInfo();

			mCursor.close();
			db.close();
			return tmpUser;
		}
	}

	public static List<StatusInfo> getDeviceList(Context mContext) {
		dataSources db = new dataSources(mContext);
		db.open();
		List<StatusInfo>  tmpList = db.getStatusList();
		db.close();
		return tmpList;

	}

	public static List<EventsInfo> getEventsList(Context mContext) {
		dataSources db = new dataSources(mContext);
		db.open();
		List<EventsInfo> tmpList = db.getEventsInfo();
		db.close();
		return tmpList;
	}
	public static List<EventsInfo> getEventsList(Context mContext,String addr){
		dataSources db = new dataSources(mContext);
		db.open();
		List<EventsInfo> tmpList = db.getEventsInfo(addr);
		db.close();
		return tmpList;
	}

	public static boolean deleteEvent(Context mContext, EventsInfo ei) {
		dataSources db = new dataSources(mContext);
		db.open();
		boolean tmpList = db.deleteEventInfo(ei);
		db.close();
		return tmpList;
	}

	public static boolean insertDevice(Context mContext, StatusInfo si) {
		dataSources db = new dataSources(mContext);
		db.open();
		boolean tmpList = db.insertDevice(si);
		db.close();
		return tmpList;
	}

	public static boolean insertEvents(Context mContext, EventsInfo ei) {
		dataSources db = new dataSources(mContext);
		db.open();
		boolean tmpList = db.insertEvents(ei);
		db.close();
		return tmpList;
	}
	public static void insertEvents(Context mContext, List<EventsInfo> list) {
		for(EventsInfo ei:list){
			insertEvents(mContext, ei);
		}
	}
	public static StatusInfo getDeviceInfo(String address,String datatime, Context mContext) {
		dataSources db = new dataSources(mContext);
		db.open();
		StatusInfo tmpList = db.getDeviceInfo(address,datatime);
		db.close();
		return tmpList;
	}
	public static List<StatusInfo> getDeviceInfoAddr(String addr,Context mContext){
		dataSources db = new dataSources(mContext);
		db.open();
		List<StatusInfo> tmpList = db.getgetDevicesInfo(addr);
		db.close();
		return tmpList;
	}
	public static List<StatusInfo> getDeviceInfoAddr(String addr,String dataTime,String time,Context mContext){
		dataSources db = new dataSources(mContext);
		db.open();
		List<StatusInfo> tmpList = db.getgetDevicesInfo(addr,dataTime,time);
		db.close();
		return tmpList;
	}
	public static  double getMonthData(String address,String dataTime,String time,Context mContext){
		dataSources db = new dataSources(mContext);
		db.open();
		double result = db.getMonthData(address, dataTime, time);
		db.close();
		return result;
	}
	public static boolean updateDeviceAlias(String addr,String alias,Context mContext){
		dataSources db = new dataSources(mContext);
		db.open();
		boolean  result = db.updateDeviceAlias(addr, alias);
		db.close();
		return result;
	}
//	public static String getDeviceAlais(String addr,Context mContext){
//		dataSources db = new dataSources(mContext);
//		db.open();
////		String  result = db.getDeviceAlais(addr);
//		db.close();
//		return result;
//	}
	public static StatusInfo getStatus(String address,String dataTime,String time,Context mContext){
		dataSources db = new dataSources(mContext);
		db.open();
		StatusInfo  result = db.getStatus(address,dataTime,time);
		db.close();
		return result;
		
	}
 }
