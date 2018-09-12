package com.vo;

public class CUserInfo {
	private String name;
	private String pwd;
	private String sex;
	private int age;
	
	public CUserInfo(){
		
	}
	public CUserInfo(String name,String pwd,String sex,int age){
		this.setName(name);
		this.setPwd(pwd);
		this.setSex(sex);
		this.setAge(age);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
