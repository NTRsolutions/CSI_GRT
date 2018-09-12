package com.vo;

public class EventsInfo {
	private String id;
	private String addr;

	private String date;
	private String type;
	private String desc;
	
	public EventsInfo(){
		
	}
	public EventsInfo(String id,String addr,String date,String type,String desc){
		this.setId(id);
		this.setAddr(addr);
		this.setDate(date);
		this.setType(type);
		this.setDesc(desc);
	}
	
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
