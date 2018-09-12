package com.growatt.shinephone.bean;

public class Calendar {
	private String minimalDaysInFirstWeek;
	private String weekYear;
	private String time;
	private String weeksInWeekYear;
	private String timeInMillis;
	private String lenient;
	private String firstDayOfWeek;
	private String weekDateSupported;
	private Time times;
	private GregorianChange gregorianChange;
	private TimeZone timeZone;
	public Calendar(String minimalDaysInFirstWeek, String weekYear,
			String time, String weeksInWeekYear, String timeInMillis,
			String lenient, String firstDayOfWeek, String weekDateSupported,
			Time times, GregorianChange gregorianChange, TimeZone timeZone) {
		super();
		this.minimalDaysInFirstWeek = minimalDaysInFirstWeek;
		this.weekYear = weekYear;
		this.time = time;
		this.weeksInWeekYear = weeksInWeekYear;
		this.timeInMillis = timeInMillis;
		this.lenient = lenient;
		this.firstDayOfWeek = firstDayOfWeek;
		this.weekDateSupported = weekDateSupported;
		this.times = times;
		this.gregorianChange = gregorianChange;
		this.timeZone = timeZone;
	}
	public Calendar() {
		super();
	}
	public String getMinimalDaysInFirstWeek() {
		return minimalDaysInFirstWeek;
	}
	public void setMinimalDaysInFirstWeek(String minimalDaysInFirstWeek) {
		this.minimalDaysInFirstWeek = minimalDaysInFirstWeek;
	}
	public String getWeekYear() {
		return weekYear;
	}
	public void setWeekYear(String weekYear) {
		this.weekYear = weekYear;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getWeeksInWeekYear() {
		return weeksInWeekYear;
	}
	public void setWeeksInWeekYear(String weeksInWeekYear) {
		this.weeksInWeekYear = weeksInWeekYear;
	}
	public String getTimeInMillis() {
		return timeInMillis;
	}
	public void setTimeInMillis(String timeInMillis) {
		this.timeInMillis = timeInMillis;
	}
	public String getLenient() {
		return lenient;
	}
	public void setLenient(String lenient) {
		this.lenient = lenient;
	}
	public String getFirstDayOfWeek() {
		return firstDayOfWeek;
	}
	public void setFirstDayOfWeek(String firstDayOfWeek) {
		this.firstDayOfWeek = firstDayOfWeek;
	}
	public String getWeekDateSupported() {
		return weekDateSupported;
	}
	public void setWeekDateSupported(String weekDateSupported) {
		this.weekDateSupported = weekDateSupported;
	}
	public Time getTimes() {
		return times;
	}
	public void setTimes(Time times) {
		this.times = times;
	}
	public GregorianChange getGregorianChange() {
		return gregorianChange;
	}
	public void setGregorianChange(GregorianChange gregorianChange) {
		this.gregorianChange = gregorianChange;
	}
	public TimeZone getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
	
}
