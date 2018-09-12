package com.growatt.shinephone.tool;

import java.util.Calendar;
/**
 * 根据年份和月份 得到当前月的天数
 * @author mike
 *
 */
public class GetDaysForYearAndMonth {
	public   static   int   getDaysInMonth(int   year,   int   mon)   { 
		java.util.GregorianCalendar   date   =   new   java.util.GregorianCalendar(year,mon,1); 
		date.add(Calendar.DATE,   -1); 
		return   (date.get(Calendar.DAY_OF_MONTH)); 
		} 
}
