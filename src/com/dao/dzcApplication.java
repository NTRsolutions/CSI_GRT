package com.dao;

import android.app.Application;

import com.vo.CUserInfo;
/**
 * 										
 * <pre>										
 * [名 称]：全局数据缓存信息类										
 * [功 能]：对需要进行全局用到的信息进行缓存，方便这些信息的使用；										
 * [描 述]：无										
 * </pre>										
 *										
 * @author 	jim									
 * @创建时间  2011-10-25
 */
public class dzcApplication extends Application {
	private CUserInfo user;

	public CUserInfo getUser() {
		return user;
	}

	public void setUser(CUserInfo user) {
		this.user = user;
	}	
	
}
