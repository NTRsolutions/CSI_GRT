package com.growatt.shinephone.bean;

public class Inverter {
	private String lost;
	private String energyMonthText;
	private String id;
	private String serialNum;
	private String dataLogSn;
	private String innerVersion;
	private String energyMonth;
	private String alias;
	private String eToday;
	private String status;
	private String eTotal;
	private String lastUpdateTimeText;
	private String address;
	public Inverter(String lost, String energyMonthText, String id,
			String serialNum, String dataLogSn, String innerVersion,
			String energyMonth, String alias, String eToday, String status,
			String eTotal, String lastUpdateTimeText, String address) {
		super();
		this.lost = lost;
		this.energyMonthText = energyMonthText;
		this.id = id;
		this.serialNum = serialNum;
		this.dataLogSn = dataLogSn;
		this.innerVersion = innerVersion;
		this.energyMonth = energyMonth;
		this.alias = alias;
		this.eToday = eToday;
		this.status = status;
		this.eTotal = eTotal;
		this.lastUpdateTimeText = lastUpdateTimeText;
		this.address = address;
	}
	public String getLost() {
		return lost;
	}
	public void setLost(String lost) {
		this.lost = lost;
	}
	public String getEnergyMonthText() {
		return energyMonthText;
	}
	public void setEnergyMonthText(String energyMonthText) {
		this.energyMonthText = energyMonthText;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getDataLogSn() {
		return dataLogSn;
	}
	public void setDataLogSn(String dataLogSn) {
		this.dataLogSn = dataLogSn;
	}
	public String getInnerVersion() {
		return innerVersion;
	}
	public void setInnerVersion(String innerVersion) {
		this.innerVersion = innerVersion;
	}
	public String getEnergyMonth() {
		return energyMonth;
	}
	public void setEnergyMonth(String energyMonth) {
		this.energyMonth = energyMonth;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String geteToday() {
		return eToday;
	}
	public void seteToday(String eToday) {
		this.eToday = eToday;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String geteTotal() {
		return eTotal;
	}
	public void seteTotal(String eTotal) {
		this.eTotal = eTotal;
	}
	public String getLastUpdateTimeText() {
		return lastUpdateTimeText;
	}
	public void setLastUpdateTimeText(String lastUpdateTimeText) {
		this.lastUpdateTimeText = lastUpdateTimeText;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Inverter() {
		super();
	}
	
}
