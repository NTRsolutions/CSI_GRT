package com.vo;

import java.io.Serializable;

public class CInverterInfo implements Serializable {
	private String name;
	private String status;
	private String powerGeneral;
	private double profy;

	public CInverterInfo() {

	}

	public CInverterInfo(String name,String status,String powerGeneral,double profy) {
		this.setName(name);
		this.setStatus(status);
		this.setPowerGeneral(powerGeneral);
		this.setProfy(profy);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPowerGeneral() {
		return powerGeneral;
	}

	public void setPowerGeneral(String powerGeneral) {
		this.powerGeneral = powerGeneral;
	}

	public double getProfy() {
		return profy;
	}

	public void setProfy(double profy) {
		this.profy = profy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
