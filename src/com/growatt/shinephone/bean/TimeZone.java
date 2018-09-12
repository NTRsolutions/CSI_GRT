package com.growatt.shinephone.bean;

public class TimeZone {
	private String lastRuleInstance;
	private String rawOffset;
	private String DSTSavings;
	private String dirty;
	private String ID;
	private String displayName;
	public TimeZone(String lastRuleInstance, String rawOffset,
			String dSTSavings, String dirty, String iD, String displayName) {
		super();
		this.lastRuleInstance = lastRuleInstance;
		this.rawOffset = rawOffset;
		DSTSavings = dSTSavings;
		this.dirty = dirty;
		ID = iD;
		this.displayName = displayName;
	}
	public TimeZone() {
		super();
	}
	public String getLastRuleInstance() {
		return lastRuleInstance;
	}
	public void setLastRuleInstance(String lastRuleInstance) {
		this.lastRuleInstance = lastRuleInstance;
	}
	public String getRawOffset() {
		return rawOffset;
	}
	public void setRawOffset(String rawOffset) {
		this.rawOffset = rawOffset;
	}
	public String getDSTSavings() {
		return DSTSavings;
	}
	public void setDSTSavings(String dSTSavings) {
		DSTSavings = dSTSavings;
	}
	public String getDirty() {
		return dirty;
	}
	public void setDirty(String dirty) {
		this.dirty = dirty;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
