package com.growatt.shinephone.bean;

public class CitynameBean {
	
	String cityName;
	String pyName;
	char sortLetter;
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getPyName() {
		return pyName;
	}
	public void setPyName(String pyName) {
		this.pyName = pyName;
	}
	public char getSortLetter() {
		return sortLetter;
	}
	public void setSortLetter(char sortLetter) {
		this.sortLetter = sortLetter;
	}
	@Override
	public String toString() {
		return "CitynameBean [cityName=" + cityName + ", pyName=" + pyName
				+ ", sortLetter=" + sortLetter + "]";
	}
	
	
	
	
	
}
