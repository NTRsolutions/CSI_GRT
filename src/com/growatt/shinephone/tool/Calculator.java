package com.growatt.shinephone.tool;

import java.util.List;

import android.content.Context;

import com.dao.dbtools;
import com.vo.StatusInfo;

public class Calculator {
	/**
	 * 电站发电总量(所有逆变器发电量之和)
	 * @param mContext
	 * @return
	 */
	public static double tatolGeneralPower(Context mContext){
		List<StatusInfo> list = dbtools.getDeviceList(mContext);
		double today = 0.0;
		double first_total = 0.0;
		double last_total = 0.0;
		double tatol =0.0;
		if(list.size()>0){
		for(int i = 0;i<list.size();i++){
			StatusInfo si = list.get(i);
			tatol+= si.getCapacity_today();
		}
		}
		return ConvertData.convert(tatol);
	}
	/**
	 * 电站发电总功率(所有逆变器功率之和)
	 * @param mContext
	 * @return
	 */
	public static double tatolPowerGL(Context mContext){
		List<StatusInfo> list = dbtools.getDeviceList(mContext);
		double tatol = 0.0;
		if(list.size()>0){
			for(StatusInfo si:list){
				tatol+= si.getPowerGL();
			}
		}
		return ConvertData.convert(tatol);
	}
	/**
	 * 二氧化碳排放量(所有逆变器排放量之和)
	 * @param mContext
	 * @return
	 */
	public static double tatolCO2(Context mContext){
		List<StatusInfo> list = dbtools.getDeviceList(mContext);
		double tatol = 0.0;
		if(list.size()>0){
			for(StatusInfo si:list){
			tatol += si.getCarbon_dioxide_emissions();
		}
		}
		return ConvertData.convert(tatol);
	}
	/**
	 * 电站发电收益(所有逆变器收益之和)
	 * @param mContext
	 * @return
	 */
	
	public static double tatolProfit(Context mContext){
		List<StatusInfo> list = dbtools.getDeviceList(mContext);
		double tatol = 0.0;
		if(list.size()>0){
			for(StatusInfo si:list){
				tatol += si.getProfit();
			}
		}
		 return ConvertData.convert(tatol);
	}
	
	/**
	 * 单个逆变器发电总量
	 * @param mContext
	 * @return
	 */
	public static double tatolSingleGeneralPower(Context mContext,String addr){
		List<StatusInfo> list = dbtools.getDeviceInfoAddr(addr, mContext);
		double tatol =0.0;
		if(list.size()>0){
		for(int i = 0;i<list.size();i++){
			StatusInfo si = list.get(i);
			tatol+= si.getCapacity_today();
		}
		}
		return ConvertData.convert(tatol);
	}
	/**
	 * 单个逆变器发电总功率
	 * @param mContext
	 * @return
	 */
	public static double tatolSinglePowerGL(Context mContext,String addr){
		List<StatusInfo> list = dbtools.getDeviceInfoAddr(addr, mContext);
		double tatol = 0.0;
		if(list.size()>0){
			for(StatusInfo si:list){
				tatol+= si.getPowerGL();
			}
		}
		return tatol;
	}
	/**
	 * 单个逆变器二氧化碳排放量
	 * @param mContext
	 * @return
	 */
	public static double tatolSingleCO2(Context mContext,String addr){
		List<StatusInfo> list = dbtools.getDeviceInfoAddr(addr, mContext);
		double tatol = 0.0;
		if(list.size()>0){
			for(StatusInfo si:list){
			tatol += si.getCarbon_dioxide_emissions();
		}
		}
		return ConvertData.convert(tatol);
	}
	/**
	 * 单个逆变器发电收益
	 * @param mContext
	 * @return
	 */
	
	public static double tatolSingelProfit(Context mContext,String addr){
		List<StatusInfo> list = dbtools.getDeviceInfoAddr(addr, mContext);
		double tatol = 0.0;
		if(list.size()>0){
			for(StatusInfo si:list){
				tatol += si.getProfit();
			}
		}
		return ConvertData.convert(tatol);
	}
	
	/**
	 * 减排量
	 * @param si 电量
	 * @return
	 */
	public static double calculationPowerToCO2(double si) {
		return si * (0.0001722 / 0.0007194);
	}
	
	public static double getProfit(double si,double singalProfit){
		return si * singalProfit;
	}
	
}
