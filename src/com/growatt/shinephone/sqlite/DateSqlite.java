package com.growatt.shinephone.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DateSqlite extends SQLiteOpenHelper{
	/**
	 * SQLiteOpenHelper��һ�������࣬�����������ݿ�Ĵ����Ͱ汾�������ṩ��������Ĺ���
	 * ��һ��getReadableDatabase()��getWritableDatabase()���Ի��SQLiteDatabase����ͨ���ö�����Զ����ݿ���в���
	 * �ڶ����ṩ��onCreate()��onUpgrade()�����ص����������������ٴ������������ݿ�ʱ�������Լ��Ĳ���
	 */
	private static  int version = 5; //���ݿ�汾
	public DateSqlite(Context context) {
		super(context, "zcsql1.db", null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table IF NOT EXISTS ids(plant varchar(100) not null,deviceAilas varchar(100) not null," +
				"deviceType varchar(100) not null,deviceSn varchar(100) not null,deviceStatus varchar(100) not null,energy varchar(100) not null," +
				"datalogSn varchar(100) not null,id varchar(100) not null,userId text)");
		db.execSQL("create table IF NOT EXISTS times(time varchar(100) not null)");
		db.execSQL("create table IF NOT EXISTS url(url varchar(100) not null)");
		db.execSQL("create table IF NOT EXISTS islogin(falg varchar(100) not null)");
		db.execSQL("create table IF NOT EXISTS plant(plant varchar(100) not null)");
		db.execSQL("create table IF NOT EXISTS login(name varchar(100) not null , pwd varchar(100) not null)");
		db.execSQL("create table IF NOT EXISTS message(content varchar(1000) not null , date varchar(100) not null, type varchar(100) not null, title varchar(100) not null, id varchar(100) not null, msgid varchar(100) not null)");
		db.execSQL("create table IF NOT EXISTS service(service varchar(100) not null,app_code integer)");
//		//��ȡ���ɱ��е��ֶΡ�Ҳ�����±��б������ֶΡ�
//				String columns = getColumnNames( db, "ids" );
//						//����
//				updateTable( db, "ids", columns );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		switch (arg1) {
		case 2:
			db.execSQL("create table IF NOT EXISTS service(service varchar(100) not null)");
		case 3:
			db.execSQL("alter table ids add column userId text");
		case 4:
			db.execSQL("alter table service add column app_code integer");
		default:
			
		}
		//this.onCreate(db);
		
		
		
	}
	
//����ĳ����ṹ
	private void updateTable(SQLiteDatabase db, String tableName, String columns)
	{
	try
	{


	db.beginTransaction();
	String reColumn = columns.substring( 0, columns.length() - 1 );
	// rename the table
	String tempTable = tableName + "texp_temptable";
	String sql = "alter table " + tableName + " rename to " + tempTable;
	db.execSQL( sql );


	// drop the oldtable
	String dropString = "drop table if exists " + tableName;
	db.execSQL( dropString );
	// creat table
	String ss = "create table if not exists " + tableName + "(plant varchar(100) not null,deviceAilas varchar(100) not null," +
				"deviceType varchar(100) not null,deviceSn varchar(100) not null,deviceStatus varchar(100) not null,energy varchar(100) not null," +
				"datalogSn varchar(100) not null,id varchar(100) not null,userId varchar(100) not null)";
	db.execSQL( ss );
	// load data
	String newStr = "userId";
	String newreColumn = reColumn + "," + newStr;
	String ins = "insert into " + tableName + " (" + newreColumn + ") " + "select " + reColumn + "" + " " + " from "
	+ tempTable;
	db.equals( ins );
	db.setTransactionSuccessful();
	}
	catch (Exception e)
	{
	// TODO: handle exception
	Log.i( "tag", e.getMessage() );
	}
	finally
	{
	db.endTransaction();
	}


	}




	// ��ȡ����ǰ���е��ֶ�
	protected String getColumnNames(SQLiteDatabase db, String tableName)
	{
	StringBuffer sb = null;
	Cursor c = null;
	try
	{
	c = db.rawQuery( "PRAGMA table_info(" + tableName + ")", null );
	if (null != c)
	{
	int columnIndex = c.getColumnIndex( "id" );
	if (-1 == columnIndex)
	{
	return null;
	}


	int index = 0;
	sb = new StringBuffer( c.getCount() );
	for ( c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
	{
	sb.append( c.getString( columnIndex ) );
	sb.append( "," );
	index++;
	}
	}
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	finally
	{
	if (c != null)
	{
	c.close();
	}
	}


	return sb.toString();
	}


	}


