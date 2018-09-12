package com.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.growatt.shinephone.tool.CDefault;
import com.vo.CUserInfo;
import com.vo.EventsInfo;
import com.vo.StatusInfo;

/**
 * 
 * <pre>
 * [名 称]：数据源信息操作类										
 * [功 能]：对软件存储数据进行增，删，改，查；										
 * [描 述]：无
 * </pre>
 * 
 * @author jim
 * @创建时间 2011-10-25
 */
public class dataSources implements Serializable {

	private static final long serialVersionUID = -7060210544600464481L;

	private Context context;
	private DataBase db;
	private SQLiteDatabase SLDB;
	private static final String TAG = "dataSources";
	public long createUserInfo(CUserInfo user) {

		ContentValues args = new ContentValues();

		long id = -1;
		try {
			id = SLDB.insert(CDefault.TABLE_USER, null, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public long nodifyUserInfo(CUserInfo user) {

		ContentValues args = new ContentValues();

		return 0;
	}

	public Cursor findUserInfo() {
		Cursor mCursor = null;

		try {
			mCursor = SLDB.query(CDefault.TABLE_USER, CDefault.USER_CONDITION,
					null, null, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mCursor;
	}

	public void deleteUserInfo() {
		SLDB.delete(CDefault.TABLE_USER, null, null);
	}
	public List<StatusInfo> getStatusList() {

		Cursor cursor = SLDB.query(CDefault.TABLE_DEVICEINOF,
				CDefault.DEVICEINFO_CONDITION, null, null, null, null, null);
		List<StatusInfo> list = new ArrayList<StatusInfo>();
		if (cursor != null) {
			int deviceIdIndex = cursor.getColumnIndex("deviceId");
			int statusIndex = cursor.getColumnIndex("status");
			int powerGLIndex = cursor.getColumnIndex("powerGL");
			int capacity1Index = cursor.getColumnIndex("capacity_today");
			int capacity2Index =cursor.getColumnIndex("capacity_total");
			int profitIndex = cursor.getColumnIndex("profit");
			int addrIndex = cursor.getColumnIndex("addr");
			int salveAddrIndex = cursor.getColumnIndex("salveAddr");
			int CO2emissionsIndex = cursor.getColumnIndex("CO2emissions");
			int monitorIndex = cursor.getColumnIndex("monitor");
			int aliasIndex = cursor.getColumnIndex("alias");
			int sequenceNumIndex = cursor.getColumnIndex("sequenceNum");
			int datetimeIndex = cursor.getColumnIndex("datetime");
			while (cursor.moveToNext()) {
				int deviceId = cursor.getInt(deviceIdIndex);
				String status = cursor.getString(statusIndex);
				double powerGL = cursor.getDouble(powerGLIndex);
				double capacity_today = cursor.getDouble(capacity1Index);
				double capacity_total = cursor.getDouble(capacity2Index);
				double profit = cursor.getDouble(profitIndex);
				String addr = cursor.getString(addrIndex);
				int salveAddr = cursor.getInt(salveAddrIndex);
						
				double CO2emissions = cursor.getDouble(CO2emissionsIndex);
				String monitor = cursor.getString(monitorIndex);
				String alias = cursor.getString(aliasIndex);
				String sequenceNum = cursor.getString(sequenceNumIndex);
				String datetime = cursor.getString(datetimeIndex);
				StatusInfo si = new StatusInfo(deviceId, status, powerGL,
						capacity_today, capacity_total,profit, addr,salveAddr,CO2emissions, monitor, alias,
						sequenceNum, datetime);
				list.add(si);
			}
		}
		cursor.close();
		return list;
	}

	public boolean deleteEventInfo(EventsInfo ei) {
		int result = SLDB.delete(CDefault.TABLE_EVENTSINFO,
				CDefault.EVENTS_DELETE_CONDITION, new String[] { ei.getId(),ei.getDate() });
		if (result <= 0) {
			return false;
		}
		return true;

	}

	public List<EventsInfo> getEventsInfo(String addr) {
		Cursor cursor = SLDB.query(CDefault.TABLE_EVENTSINFO,
				CDefault.EVENTSINFO_CONDITION, "addr=? limit 10",
				new String[] { addr }, null, null, null);
		List<EventsInfo> list = new ArrayList<EventsInfo>();
		if (cursor != null) {
			int eventsIdIndex = cursor.getColumnIndex("eventsId");
			int addrIndex = cursor.getColumnIndex("addr");
			int datetimeIndex = cursor.getColumnIndex("datetime");
			int eventstypeIndex = cursor.getColumnIndex("eventstype");
			int descIndex = cursor.getColumnIndex("desc");
			while (cursor.moveToNext()) {
				String eventsId = cursor.getString(eventsIdIndex);
				String addrs = cursor.getString(addrIndex);
				String datetime = cursor.getString(datetimeIndex);
				String eventstype = cursor.getString(eventstypeIndex);
				String desc = cursor.getString(descIndex);
				EventsInfo ei = new EventsInfo(eventsId, addrs,datetime, eventstype,
						desc);
				list.add(ei);
			}
		}
		cursor.close();
		return list;
	}

	public List<EventsInfo> getEventsInfo() {
		Cursor cursor = SLDB.query(CDefault.TABLE_EVENTSINFO,
				CDefault.EVENTSINFO_CONDITION, null, null, null, null, null);
		List<EventsInfo> list = new ArrayList<EventsInfo>();
		if (cursor != null) {
			int eventsIdIndex = cursor.getColumnIndex("eventsId");
			int addrIndex = cursor.getColumnIndex("addr");
			int datetimeIndex = cursor.getColumnIndex("datetime");
			int eventstypeIndex = cursor.getColumnIndex("eventstype");
			int descIndex = cursor.getColumnIndex("desc");
			while (cursor.moveToNext()) {
				String eventsId = cursor.getString(eventsIdIndex);
				String addr = cursor.getString(addrIndex);
				String datetime = cursor.getString(datetimeIndex);
				String eventstype = cursor.getString(eventstypeIndex);
				String desc = cursor.getString(descIndex);
				EventsInfo ei = new EventsInfo(eventsId,addr, datetime, eventstype,
						desc);
				list.add(ei);
			}
		}
		cursor.close();
		return list;
	}

	/**
	 * 添加月份数据
	 * @param si
	 * @return
	 */
	public boolean insertDevice(StatusInfo si) {
		ContentValues values = new ContentValues();
		values.put("status", si.getStatus());
		values.put("powerGL", si.getPowerGL());
		values.put("capacity_today", si.getCapacity_today());
		values.put("capacity_total", si.getCapacity_total());
		values.put("profit", si.getProfit());
		values.put("addr", si.getAddr());
		values.put("salveAddr", si.getSalveAddr());
		values.put("CO2emissions", si.getCarbon_dioxide_emissions());
		values.put("monitor", si.getMonitor());
		values.put("alias", si.getAlias());
		values.put("sequenceNum", si.getSequenceNum());
		values.put("datetime", si.getDate());
		long i = SLDB.insert(CDefault.TABLE_DEVICEINOF, null, values);
		if (i <= 0) {
			return false;
		}
//		SLDB.close();
		return true;

	}
	public boolean updateDeviceAlias(String addr,String alias){
		ContentValues values = new ContentValues();;
		values.put("alias", alias);
		int i = SLDB.update(CDefault.TABLE_DEVICEINOF, values, "addr=?", new String[]{addr});
		if(i<=0){
			return false;
		}else{
			return true;
		}
	}
//	public boolean updateAlais(String addr,String alais){
//		ContentValues values = new ContentValues();
//		values.put("alias", alais);
//		int i = SLDB.update(CDefault.TABLE_DEVICEINOF, values, "addr=?", new String[]{addr});
//		if(i<=0){
//			return false;
//		}else{
//			return true;
//		}
//	}
	public boolean insertEvents(EventsInfo ei) {
		ContentValues values = new ContentValues();
		values.put("eventsId", ei.getId());
		values.put("addr", ei.getAddr());
		values.put("datetime", ei.getDate());
		values.put("eventstype", ei.getType());
		values.put("desc", ei.getDesc());
		long i = SLDB.insert(CDefault.TABLE_EVENTSINFO, null, values);
		if (i <= 0) {
			return false;
		}
		return true;

	}

	public StatusInfo getDeviceInfo(String address,String dataTime) {
		Cursor cursor = SLDB.query(CDefault.TABLE_DEVICEINOF, null,
				"addr=? and datetime>? ", new String[] { address ,dataTime}, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			int deviceIdIndex = cursor.getColumnIndex("deviceId");
			int statusIndex = cursor.getColumnIndex("status");
			int powerGLIndex = cursor.getColumnIndex("powerGL");
			int capacity1Index = cursor.getColumnIndex("capacity_today");
			int capacity2Index =cursor.getColumnIndex("capacity_total");
			int profitIndex = cursor.getColumnIndex("profit");
			int addrIndex = cursor.getColumnIndex("addr");
			int salveAddrIndex = cursor.getColumnIndex("salveAddr");
			int CO2emissionsIndex = cursor.getColumnIndex("CO2emissions");
			int monitorIndex = cursor.getColumnIndex("monitor");
			int aliasIndex = cursor.getColumnIndex("alias");
			int sequenceNumIndex = cursor.getColumnIndex("sequenceNum");
			int datetimeIndex = cursor.getColumnIndex("datetime");
			int deviceId = cursor.getInt(deviceIdIndex);
			String status = cursor.getString(statusIndex);
			double powerGL = cursor.getDouble(powerGLIndex);
			double capacity_today = cursor.getDouble(capacity1Index);
			double capacity_total = cursor.getDouble(capacity2Index);
			double profit = cursor.getDouble(profitIndex);
			String addr = cursor.getString(addrIndex);
			int salveAddr = cursor.getInt(salveAddrIndex);
			double CO2emissions = cursor.getDouble(CO2emissionsIndex);
			String monitor = cursor.getString(monitorIndex);
			String alias = cursor.getString(aliasIndex);
			String sequenceNum = cursor.getString(sequenceNumIndex);
			String datetime = cursor.getString(datetimeIndex);
			StatusInfo si = new StatusInfo(deviceId, status, powerGL, capacity_today,capacity_total,
					profit, addr,salveAddr,CO2emissions, monitor, alias, sequenceNum, datetime);
			cursor.close();
			return si;
		}
		return null;

	}

	public List<StatusInfo> getgetDevicesInfo(String address) {
		Cursor cursor = SLDB.query(CDefault.TABLE_DEVICEINOF, null,
				"addr=?", new String[] { address }, null, null, null);
		if (cursor != null) {
			int deviceIdIndex = cursor.getColumnIndex("deviceId");
			int statusIndex = cursor.getColumnIndex("status");
			int powerGLIndex = cursor.getColumnIndex("powerGL");
			int capacity1Index = cursor.getColumnIndex("capacity_today");
			int capacity2Index =cursor.getColumnIndex("capacity_total");
			int addrIndex = cursor.getColumnIndex("addr");
			int salveAddrIndex = cursor.getColumnIndex("salveAddr");
			int profitIndex = cursor.getColumnIndex("profit");
			int CO2emissionsIndex = cursor.getColumnIndex("CO2emissions");
			int monitorIndex = cursor.getColumnIndex("monitor");
			int aliasIndex = cursor.getColumnIndex("alias");
			int sequenceNumIndex = cursor.getColumnIndex("sequenceNum");
			int datetimeIndex = cursor.getColumnIndex("datetime");
			List<StatusInfo> list = new ArrayList<StatusInfo>();
			while (cursor.moveToNext()) {
				int deviceId = cursor.getInt(deviceIdIndex);
				String status = cursor.getString(statusIndex);
				double powerGL = cursor.getDouble(powerGLIndex);
				double capacity_today = cursor.getDouble(capacity1Index);
				double capacity_total = cursor.getDouble(capacity2Index);
				double profit = cursor.getDouble(profitIndex);
				String addr = cursor.getString(addrIndex);
				int salveAddr = cursor.getInt(salveAddrIndex);
				double CO2emissions = cursor.getDouble(CO2emissionsIndex);
				String monitor = cursor.getString(monitorIndex);
				String alias = cursor.getString(aliasIndex);
				String sequenceNum = cursor.getString(sequenceNumIndex);
				String datetime = cursor.getString(datetimeIndex);
				StatusInfo si = new StatusInfo(deviceId, status, powerGL,
						capacity_today,capacity_total, profit,addr,salveAddr, CO2emissions, monitor, alias,
						sequenceNum, datetime);
				list.add(si);
			}
			cursor.close();
			return list;
		}
		return null;

	}
	public double getMonthData(String address,String dataTime,String time){
		Cursor cursor = SLDB.query(CDefault.TABLE_DEVICEINOF, new String []{"sum(capacity_today) as capacity "},
				"addr=? and datetime>? and datetime<?", new String[] { address ,dataTime,time}, null, null, null);
		double result = 0.0;
		if(cursor !=null){
			if(cursor.getCount() > 0){
				cursor.moveToFirst();
			result = cursor.getDouble(0);
			Log.i(TAG, "ddd"+cursor.getDouble(0));
			}
		}
		cursor.close();
		return result;
	}
	public StatusInfo getStatus(String address,String dataTime,String time){
		Cursor cursor = SLDB.query(CDefault.TABLE_DEVICEINOF, null,
				"addr=? and datetime>? and datetime<?", new String[] { address ,dataTime,time}, null, null, "datetime asc");
		if(cursor != null&&cursor.getCount()>0){
			cursor.moveToLast();
			int deviceIdIndex = cursor.getColumnIndex("deviceId");
			int statusIndex = cursor.getColumnIndex("status");
			int powerGLIndex = cursor.getColumnIndex("powerGL");
			int capacity1Index = cursor.getColumnIndex("capacity_today");
			int capacity2Index =cursor.getColumnIndex("capacity_total");
			int profitIndex = cursor.getColumnIndex("profit");
			int addrIndex = cursor.getColumnIndex("addr");
			int salveAddrIndex = cursor.getColumnIndex("salveAddr");
			int CO2emissionsIndex = cursor.getColumnIndex("CO2emissions");
			int monitorIndex = cursor.getColumnIndex("monitor");
			int aliasIndex = cursor.getColumnIndex("alias");
			int sequenceNumIndex = cursor.getColumnIndex("sequenceNum");
			int datetimeIndex = cursor.getColumnIndex("datetime");
			int deviceId = cursor.getInt(deviceIdIndex);
			String status = cursor.getString(statusIndex);
			double powerGL = cursor.getDouble(powerGLIndex);
			double capacity_today = cursor.getDouble(capacity1Index);
			double capacity_total = cursor.getDouble(capacity2Index);
			double profit = cursor.getDouble(profitIndex);
			String addr = cursor.getString(addrIndex);
			int salveAddr = cursor.getInt(salveAddrIndex);
			double CO2emissions = cursor.getDouble(CO2emissionsIndex);
			String monitor = cursor.getString(monitorIndex);
			String alias = cursor.getString(aliasIndex);
			String sequenceNum = cursor.getString(sequenceNumIndex);
			String datetime = cursor.getString(datetimeIndex);
			StatusInfo si = new StatusInfo(deviceId, status, powerGL, capacity_today,capacity_total,
					profit, addr,salveAddr,CO2emissions, monitor, alias, sequenceNum, datetime);
			cursor.close();
			return si;
		}
		
		return null;
		
	}
	public List<StatusInfo> getgetDevicesInfo(String address,String dataTime,String time) {
		Cursor cursor = SLDB.query(CDefault.TABLE_DEVICEINOF, null,
				"addr=? and datetime>? and datetime<?", new String[] { address ,dataTime,time}, null, null, null);
		if (cursor != null) {
			int deviceIdIndex = cursor.getColumnIndex("deviceId");
			int statusIndex = cursor.getColumnIndex("status");
			int powerGLIndex = cursor.getColumnIndex("powerGL");
			int capacity1Index = cursor.getColumnIndex("capacity_today");
			int capacity2Index =cursor.getColumnIndex("capacity_total");
			int addrIndex = cursor.getColumnIndex("addr");
			int salveAddrIndex = cursor.getColumnIndex("salveAddr");
			int profitIndex = cursor.getColumnIndex("profit");
			int CO2emissionsIndex = cursor.getColumnIndex("CO2emissions");
			int monitorIndex = cursor.getColumnIndex("monitor");
			int aliasIndex = cursor.getColumnIndex("alias");
			int sequenceNumIndex = cursor.getColumnIndex("sequenceNum");
			int datetimeIndex = cursor.getColumnIndex("datetime");
			List<StatusInfo> list = new ArrayList<StatusInfo>();
			while (cursor.moveToNext()) {
				int deviceId = cursor.getInt(deviceIdIndex);
				String status = cursor.getString(statusIndex);
				double powerGL = cursor.getDouble(powerGLIndex);
				double capacity_today = cursor.getDouble(capacity1Index);
				double capacity_total = cursor.getDouble(capacity2Index);
				double profit = cursor.getDouble(profitIndex);
				String addr = cursor.getString(addrIndex);
				int salveAddr = cursor.getInt(salveAddrIndex);
				double CO2emissions = cursor.getDouble(CO2emissionsIndex);
				String monitor = cursor.getString(monitorIndex);
				String alias = cursor.getString(aliasIndex);
				String sequenceNum = cursor.getString(sequenceNumIndex);
				String datetime = cursor.getString(datetimeIndex);
				StatusInfo si = new StatusInfo(deviceId, status, powerGL,
						capacity_today,capacity_total, profit,addr,salveAddr, CO2emissions, monitor, alias,
						sequenceNum, datetime);
				list.add(si);
			}
			cursor.close();
			return list;
		}
		return null;

	}
	

	
//	public String getDeviceAlais(String addr){
//		
//		Cursor cursor = SLDB.query(CDefault.TABLE_DEVICEINOF, new String []{"alias"}, "addr=?", new String []{addr}, null, null, null);
//		
//		String alias="";
//		if(cursor !=null){
//			if(cursor.getCount() > 0){
//				cursor.moveToFirst();
//				alias = cursor.getString(0);
//			Log.i(TAG, "ddd"+cursor.getDouble(0));
//			}
//		}
//		cursor.close();
//		return alias;
//		
//	}
	
	public dataSources(Context context) {
		this.context = context;
	}

	public dataSources open() throws SQLException {
		db = new DataBase(context, CDefault.DATABASE_NAME, null,
				CDefault.DATABASE_VERSION);
		SLDB = db.getWritableDatabase();
		return this;
	}

	public void close() {
		db.close();
	}

	private static class DataBase extends SQLiteOpenHelper {

		public DataBase(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
		}

		public void onCreate(SQLiteDatabase sdb) {
			sdb.execSQL(CDefault.CREATE_USER_TABLE);
			sdb.execSQL(CDefault.CREATE_DEVICEINFO);
			sdb.execSQL(CDefault.CREATE_EVENTSINFO);
		}

		public void onUpgrade(SQLiteDatabase sdb, int oldVersion, int newVersion) {
			sdb.execSQL(CDefault.UP_USER_TABLE);
			onCreate(sdb);
		}
	}
}
