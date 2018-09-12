package com.vo;

public class StatusInfo {
	private int id;
	private String status;//状态
	private double powerGL;//功率
	private double capacity_today;//今天发电量
	private double capacity_total;//今天总发电量
	private double profit;//收益
	private String addr;//蓝牙地址
	private int salveAddr;//逆变器台地址与蓝牙地址绑定
	private double Carbon_dioxide_emissions;//co2排放量
	private String monitor;//监控方式
	private String alias;//别名
	private String sequenceNum;//序列号 
	private String date;//时间
	public StatusInfo(){
		
	}
	public StatusInfo(int id,String status,double powerGL,double capacity_tody,double capacity_total,double profit,String addr,int salveAddr,
			double carbon_dioxide_emissions,String monitor,String alias,String swquenceNmu,String date){
		this.setId(id);
		this.setStatus(status);
		this.setPowerGL(powerGL);
		this.setCapacity_today(capacity_tody);
		this.setCapacity_total(capacity_total);
		this.setProfit(profit);
		this.setAddr(addr);
		this.setSalveAddr(salveAddr);
		this.setCarbon_dioxide_emissions(carbon_dioxide_emissions);
		this.setMonitor(monitor);
		this.setAlias(alias);
		this.setSequenceNum(swquenceNmu);
		this.setDate(date);
	}
	public double getCapacity_today() {
		return capacity_today;
	}
	public void setCapacity_today(double capacity_today) {
		this.capacity_today = capacity_today;
	}
	public double getCapacity_total() {
		return capacity_total;
	}
	public void setCapacity_total(double capacity_total) {
		this.capacity_total = capacity_total;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public int getSalveAddr() {
		return salveAddr;
	}
	public void setSalveAddr(int salveAddr) {
		this.salveAddr = salveAddr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getPowerGL() {
		return powerGL;
	}
	public void setPowerGL(double powerGL) {
		this.powerGL = powerGL;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
	public double getCarbon_dioxide_emissions() {
		return Carbon_dioxide_emissions;
	}
	public void setCarbon_dioxide_emissions(double carbon_dioxide_emissions) {
		Carbon_dioxide_emissions = carbon_dioxide_emissions;
	}
	public String getMonitor() {
		return monitor;
	}
	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getSequenceNum() {
		return sequenceNum;
	}
	public void setSequenceNum(String sequenceNum) {
		this.sequenceNum = sequenceNum;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
