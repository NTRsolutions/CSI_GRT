package com.growatt.shinephone.tool;

public class CDefault {
	// 数据库名称
	public static final String DATABASE_NAME = "mmandroid.db";
	// 数据版本
	public static final int DATABASE_VERSION = 1;
	// 用户信息表名称
	public static final String TABLE_USER = "userinfo";
	// 创建用户表语句
	public static final String CREATE_USER_TABLE = "create table userInfo (userid varchar(30) PRIMARY KEY,name varchar(30), pwd varchar(30),sex varchar(4),age varchar(30));";
	// 用户表查询条件
	public static final String[] USER_CONDITION = { "userid", "name", "pwd", "sex", "age" };
	// 更新用户表语句
	public static final String UP_USER_TABLE = "DROP TABLE IF EXISTS userInfo";
	//设备信息表
	public static final String TABLE_DEVICEINOF = "deviceinfo";
	//事件信息表
	public static final String TABLE_EVENTSINFO = "eventsinfo";
	
	//创建设备信息表语句
	public static final String CREATE_DEVICEINFO = "create table "+TABLE_DEVICEINOF+"(deviceId Integer," +
			"status varchar(30),powerGL double,capacity_today double,capacity_total double,profit double," +"addr varchar(30),"+"salveAddr Integer,"+
			"CO2emissions double,monitor varchar(30),alias varchar(30),sequenceNum varchar(50),datetime varchar(30));";
	//创建事件信息表
	public static final String CREATE_EVENTSINFO = "create table "+ TABLE_EVENTSINFO+"(eventsId varchar(30),addr varchar(30) " +
			",datetime varchar(30),eventstype varchar(30),desc text);";
	
	public static final String [] DEVICEINFO_CONDITION = {"deviceId","status","powerGL","capacity_today","capacity_total","profit","addr","salveAddr","CO2emissions"
		,"monitor","alias","sequenceNum","datetime"};
	
	public static final String [] EVENTSINFO_CONDITION = {"eventsId","addr","datetime","eventstype","desc"};
	//删除表语句
	public static final String DROP_EVENTSINFO="drop table if exists "+TABLE_EVENTSINFO;
	public static final String DROP_DEVICEINFO="drop table if exists "+TABLE_DEVICEINOF;
	//删除事件
	public static final String  EVENTS_DELETE_CONDITION = "eventsId = ? and datetime=?";
	
}
