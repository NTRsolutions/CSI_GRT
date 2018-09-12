package com.growatt.shinephone.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class Parse {
	public static StorageBean set(String s){
		StorageBean storageBean=new StorageBean();
		try {	
			JSONObject jsonObjects=new JSONObject(s);
			storageBean.setStorageType(jsonObjects.get("storageType").toString());
			JSONObject jsonObject=new JSONObject(s).getJSONObject("storageDetailBean");
			storageBean.seteChargeTotal(jsonObject.get("eChargeTotal").toString());
			storageBean.setvBuckText(jsonObject.get("vBuckText").toString());
			storageBean.seteToGridTotal(jsonObject.get("eToGridTotal").toString());
			storageBean.setGaugeRM1(jsonObject.get("gaugeRM1").toString());
			storageBean.setNormalPower(jsonObject.get("normalPower").toString());
			storageBean.setvBat(jsonObject.get("vBus").toString());
			storageBean.setIpv(jsonObject.get("ipv").toString());
			storageBean.setBmsTemperature(jsonObject.get("bmsTemperature").toString());
			storageBean.seteDischargeToday(jsonObject.get("eDischargeToday").toString());
			storageBean.setGaugeRM2(jsonObject.get("gaugeRM2").toString());
			storageBean.seteToUserTotal(jsonObject.get("eToUserTotal").toString());
			storageBean.setTemperature(jsonObject.get("temperature").toString());
			storageBean.setEpvToday(jsonObject.get("epvToday").toString());
			storageBean.setErrorText(jsonObject.get("errorText").toString());
			storageBean.setWarnCode(jsonObject.get("warnCode").toString());
			storageBean.setTime(jsonObject.get("time").toString());
			storageBean.setiDischargeText(jsonObject.get("iDischargeText").toString());
			storageBean.setvBatText(jsonObject.get("vBatText").toString());
			storageBean.setDischargeToStandbyReasonText(jsonObject.get("dischargeToStandbyReasonText").toString());
			storageBean.seteDischargeTotalText(jsonObject.get("eDischargeTotalText").toString());
			storageBean.setGaugeOperationStatus(jsonObject.get("gaugeOperationStatus").toString());
			storageBean.setGaugePackStatus(jsonObject.get("gaugePackStatus").toString());
			storageBean.setSerialNum(jsonObject.get("serialNum").toString());
			storageBean.setpDischarge(jsonObject.get("pDischarge").toString());
			storageBean.setBmsCurrent(jsonObject.get("bmsCurrent").toString());
			storageBean.setPacToGridText(jsonObject.get("pacToGridText").toString());
			storageBean.setStatus(jsonObject.get("status").toString());
			storageBean.setCycleCount(jsonObject.get("cycleCount").toString());
			storageBean.setMaxChargeOrDischargeCurrent(jsonObject.get("maxChargeOrDischargeCurrent").toString());
			storageBean.setChargeToStandbyReason(jsonObject.get("chargeToStandbyReason").toString());
			storageBean.setIpmTemperature(jsonObject.get("ipmTemperature").toString());
			storageBean.setPpv(jsonObject.get("ppv").toString());
			storageBean.setIacToUserText(jsonObject.get("iacToUserText").toString());
			storageBean.setIacToGrid(jsonObject.get("iacToGrid").toString());
			storageBean.setpChargeText(jsonObject.get("pChargeText").toString());
			storageBean.setPacToUserText(jsonObject.get("pacToUserText").toString());
			storageBean.seteChargeToday(jsonObject.get("eChargeToday").toString());
			storageBean.setChargeToStandbyReasonText(jsonObject.get("chargeToStandbyReasonText").toString());
			storageBean.setDay(jsonObject.get("day").toString());
			storageBean.setFaultCode(jsonObject.get("faultCode").toString());
			storageBean.setGaugeICCurrent(jsonObject.get("gaugeICCurrent").toString());
//			storageBean.setLos(jsonObject.getString(""));
			storageBean.seteDischargeTodayText(jsonObject.get("eDischargeTodayText").toString());
			storageBean.seteToUserToday(jsonObject.get("eToUserToday").toString());
			storageBean.setVpvText(jsonObject.get("vpvText").toString());
			storageBean.seteChargeTotalText(jsonObject.get("eChargeTotalText").toString());
			storageBean.setGaugeBattteryStatus(jsonObject.get("gaugeBattteryStatus").toString());
			storageBean.setWarnText(jsonObject.get("warnText").toString());
			storageBean.setvBat(jsonObject.get("vBat").toString());
			storageBean.setPacToUser(jsonObject.get("pacToUser").toString());
			storageBean.setiDischarge(jsonObject.get("iDischarge").toString());
			storageBean.seteDischargeTotal(jsonObject.get("eDischargeTotal").toString());
			storageBean.setCapacity(jsonObject.get("capacity").toString());
			storageBean.setWithTime(jsonObject.get("withTime").toString());
			storageBean.setPpvText(jsonObject.get("ppvText").toString());
			storageBean.setIpvText(jsonObject.get("ipvText").toString());
			storageBean.setDataLogSn(jsonObject.get("dataLogSn").toString());
			storageBean.setBmsError(jsonObject.get("bmsError").toString());
			storageBean.setEpvTotal(jsonObject.get("epvTotal").toString());
			storageBean.setiCharge(jsonObject.get("iCharge").toString());
			storageBean.setDischargeToStandbyReason(jsonObject.get("dischargeToStandbyReason").toString());
			JSONObject jsonObject2=jsonObject.getJSONObject("calendar");
			Calendar calendar=new Calendar();
			calendar.setMinimalDaysInFirstWeek(jsonObject2.get("minimalDaysInFirstWeek").toString());
			calendar.setWeekYear(jsonObject2.get("weekYear").toString());
			calendar.setWeeksInWeekYear(jsonObject2.get("weeksInWeekYear").toString());
			calendar.setTimeInMillis(jsonObject2.get("timeInMillis").toString());
			calendar.setLenient(jsonObject2.get("lenient").toString());
			calendar.setFirstDayOfWeek(jsonObject2.get("firstDayOfWeek").toString());
			calendar.setWeekDateSupported(jsonObject2.get("weekDateSupported").toString());
			storageBean.setCalendar(calendar);
			JSONObject jsonObject3=jsonObject2.getJSONObject("time");
			Time time=new Time();
			time.setTime(jsonObject3.get("time").toString());
			time.setMinutes(jsonObject3.get("minutes").toString());
			time.setSeconds(jsonObject3.get("seconds").toString());
			time.setHours(jsonObject3.get("hours").toString());
			time.setMonth(jsonObject3.get("month").toString());
			time.setYear(jsonObject3.get("year").toString());
			time.setTimezoneOffset(jsonObject3.get("timezoneOffset").toString());
			time.setDay(jsonObject3.get("day").toString());
			time.setDate(jsonObject3.get("date").toString());
			calendar.setTimes(time);
			GregorianChange gregorianChange=new GregorianChange();
			JSONObject jsonObject4=jsonObject2.getJSONObject("gregorianChange");
			gregorianChange.setTime(jsonObject4.get("time").toString());
			gregorianChange.setMinutes(jsonObject4.get("minutes").toString());
			gregorianChange.setSeconds(jsonObject4.get("seconds").toString());
			gregorianChange.setHours(jsonObject4.get("hours").toString());
			gregorianChange.setMonth(jsonObject4.get("month").toString());
			gregorianChange.setYear(jsonObject4.get("year").toString());
			gregorianChange.setTimezoneOffset(jsonObject4.get("timezoneOffset").toString());
			gregorianChange.setDay(jsonObject4.get("day").toString());
			gregorianChange.setDate(jsonObject4.get("date").toString());
			calendar.setGregorianChange(gregorianChange);
			TimeZone timeZone=new TimeZone();
			JSONObject jsonObject5=jsonObject2.getJSONObject("timeZone");
			timeZone.setLastRuleInstance(jsonObject5.get("lastRuleInstance").toString());
			timeZone.setRawOffset(jsonObject5.get("rawOffset").toString());
			timeZone.setDSTSavings(jsonObject5.get("DSTSavings").toString());
			timeZone.setDirty(jsonObject5.get("dirty").toString());
			timeZone.setID(jsonObject5.get("ID").toString());
			timeZone.setDisplayName(jsonObject5.get("displayName").toString());
			calendar.setTimeZone(timeZone);
			storageBean.setPacToGrid(jsonObject.get("pacToGrid").toString());
			storageBean.setIacToGridText(jsonObject.get("iacToGridText").toString());
			storageBean.setAlias(jsonObject.get("alias").toString());
			storageBean.seteChargeTodayText(jsonObject.get("eChargeTodayText").toString());
			storageBean.setBatTemp(jsonObject.get("batTemp").toString());
			storageBean.setIacToUser(jsonObject.get("iacToUser").toString());
			storageBean.setBmsStatus(jsonObject.get("bmsStatus").toString());
			storageBean.setiChargeText(jsonObject.get("iChargeText").toString());
			storageBean.setvBuck(jsonObject.get("vBuck").toString());
			storageBean.seteToGridToday(jsonObject.get("eToGridToday").toString());
			storageBean.setpCharge(jsonObject.get("pCharge").toString());
			storageBean.setpDischargeText(jsonObject.get("pDischargeText").toString());
			storageBean.setAddress(jsonObject.get("address").toString());
			storageBean.setCapacityText(jsonObject.get("capacityText").toString());
			storageBean.setVacText(jsonObject.get("vacText").toString());
			storageBean.setErrorCode(jsonObject.get("errorCode").toString());
			storageBean.setVac(jsonObject.get("vac").toString());
			storageBean.setVpv(jsonObject.get("vpv").toString());
			StorageBean2 storageBean2=new StorageBean2();
			JSONObject jsonObject6=new JSONObject(s).getJSONObject("storageBean");
			storageBean2.setFwVersion(jsonObject6.get("fwVersion").toString());
			storageBean2.setTreeName(jsonObject6.get("treeName").toString());
			storageBean2.setLost(jsonObject6.get("lost").toString());
			storageBean2.setLocation(jsonObject6.get("location").toString());
			storageBean2.setTreeID(jsonObject6.get("treeID").toString());
			storageBean2.setAddr(jsonObject6.get("addr").toString());
			storageBean2.setStatusText(jsonObject6.get("statusText").toString());
			storageBean2.setLevel(jsonObject6.get("level").toString());
			storageBean2.setImgPath(jsonObject6.get("imgPath").toString());
			storageBean2.setPortName(jsonObject6.get("portName").toString());
			storageBean2.setStatus(jsonObject6.get("status").toString());
			storageBean2.setModelText(jsonObject6.get("modelText").toString());
			storageBean2.setTcpServerIp(jsonObject6.get("tcpServerIp").toString());
			storageBean2.setLastUpdateTimeText(jsonObject6.get("lastUpdateTimeText").toString());
			storageBean2.setGroupId(jsonObject6.get("groupId").toString());
			storageBean2.setStatusLed1(jsonObject6.get("statusLed1").toString());
			storageBean2.setStatusLed2(jsonObject6.get("statusLed2").toString());
			storageBean2.setStatusLed3(jsonObject6.get("statusLed3").toString());
			storageBean2.setStatusLed4(jsonObject6.get("statusLed4").toString());
			storageBean2.setStatusLed5(jsonObject6.get("statusLed5").toString());
			storageBean2.setStatusLed6(jsonObject6.get("statusLed6").toString());
			storageBean.setStorageBean2(storageBean2);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return storageBean;

	}
}
