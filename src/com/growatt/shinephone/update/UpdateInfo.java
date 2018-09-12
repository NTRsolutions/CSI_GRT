package com.growatt.shinephone.update;

public class UpdateInfo {
	
	private int mVersionCode;
	private String mVersionName;
	private String mFileSize;
	private String mDownloadURL;
	private String mUpdateLog;
	//热更新属性
	private int mTinkerCode;
	private String mTinkerUrl;
	
	public UpdateInfo(){
		this.mVersionCode = 1;
		this.mVersionName = "1.0";
		this.mFileSize = "";
		this.mDownloadURL = "";
		this.mUpdateLog = "";
		this.mTinkerCode = 1;
		this.mTinkerUrl = "";

	}

	public int getTinkerCode() {
		return mTinkerCode;
	}

	public void setTinkerCode(int tinkerCode) {
		mTinkerCode = tinkerCode;
	}

	public String getTinkerUrl() {
		return mTinkerUrl;
	}

	public void setTinkerUrl(String tinkerUrl) {
		mTinkerUrl = tinkerUrl;
	}

	public UpdateInfo(int VersionCode, String VersionName, String FileSize, String DownloadURL, String UpdateLog){
		this.mVersionCode = VersionCode;
		this.mVersionName = VersionName;
		this.mFileSize = FileSize;
		this.mDownloadURL = DownloadURL;
		this.mUpdateLog = UpdateLog;
	}
	
	public void setVersionCode(int VersionCode){
		this.mVersionCode = VersionCode;
	}
	
	public int getVersionCode(){
		return mVersionCode;
	}
	
	public void setVersionName(String VersionName){
		this.mVersionName = VersionName;
	}
	
	public String getVersionName(){
		return mVersionName;
	}

	public void setFileSize(String FileSize){
		this.mFileSize = FileSize;
	}
	
	public String getFileSize(){
		return mFileSize;
	}
	
	public void setDownloadURL(String DownloadURL){
		this.mDownloadURL = DownloadURL;
	}
	
	public String getDownloadURL(){
		return mDownloadURL;
	}
	
	public void setUpdateLog(String UpdateLog){
		this.mUpdateLog = UpdateLog;
	}
	
	public String getUpdateLog(){
		return mUpdateLog;
	}	
}
