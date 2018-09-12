package com.growatt.shinephone.tool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;
import com.vo.EventsInfo;

public class GetEventsInfoTools {
	private static final String tag = "GetEventsInfoTools";

	public static List<EventsInfo> getDataInfo(byte[] data,String addr,int salveAddr) {
		List<EventsInfo> list = new ArrayList<EventsInfo>();
		if (data == null || data.length < 6) {
			Log.e(tag, "数据错误");
			return null;
		}
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = df.format(date);
//		String dataStr = String.valueOf(data);
		String id =Integer.toString(salveAddr) ;
		Log.i(tag, addr);
		Log.i(tag, " id:"+id);
		 if(data.length>=72&&data.length<74){
			 EventsInfo events = new EventsInfo();
			 events.setId(id);
			 events.setAddr(addr);
			 events.setType("IOS fault Value");
			 events.setDesc("IOS fault Value");
			 events.setDate(time);
			 list.add(events);
		 }
		 if(data.length>=74&&data.length<76){
			 EventsInfo events = new EventsInfo();
			 events.setId(id);
			 events.setAddr(addr);
			 events.setType("GFCI fault Value");
			 events.setDesc("GFCI fault Value");
			 events.setDate(time);
			 list.add(events);
		 }
		 if(data.length>=76&&data.length<78){
			 EventsInfo events = new EventsInfo();
			 events.setAddr(addr);
			 events.setId(id);
			 events.setType("DCI fault Value");
			 events.setDesc("DCI fault Value");
			 events.setDate(time);
			 list.add(events);
		 }
		 if(data.length>=78&&data.length<80){
			 EventsInfo events = new EventsInfo();
			 events.setAddr(addr);
			 events.setId(id);
			 events.setType("Vpv fault Value");
			 events.setDesc("Vpv fault Value");
			 events.setDate(time);
			 list.add(events);
		 }
		 if(data.length>=80&&data.length<82){
			 EventsInfo events = new EventsInfo();
			 events.setId(id);
			 events.setAddr(addr);
			 events.setType("Vpv fault Value");
			 events.setDesc("pv voltage fault Value");
			 events.setDate(time);
			 list.add(events);
		 }
		 if(data.length>=82&&data.length<84){
			 EventsInfo events = new EventsInfo();
			 events.setId(id);
			 events.setAddr(addr);
			 events.setType("Vac fault Value");
			 events.setDesc("AC voltage fault Value");
			 events.setDate(time);
			 list.add(events);
		 }
		 if(data.length>=84&&data.length<86){
			 EventsInfo events = new EventsInfo();
			 events.setId(id);
			 events.setAddr(addr);
			 events.setType("Fac fault Value");
			 events.setDesc("AC frequence fault Value");
			 events.setDate(time);
			 list.add(events);
		 }
		 if(data.length>=86&&data.length<88){
			 EventsInfo events = new EventsInfo();
			 events.setId(id);
			 events.setAddr(addr);
			 events.setType("Temperature fault Value");
			 events.setDesc("Temperature fault Value");
			 events.setDate(time);
			 list.add(events);
		 }
		if(data.length>=88&&data.length<90){
			 EventsInfo events = new EventsInfo();
			 events.setId(id);
			 events.setAddr(addr);
			 events.setType("Fault code");
			 events.setDesc("invert fault bit");
			 events.setDate(time);
			 list.add(events);
		}
		if(data.length>=90&&data.length<92){
			 EventsInfo events = new EventsInfo();
			 events.setId(id);
			 events.setAddr(addr);
			 events.setType("bErrorOld");
			 events.setDesc("reserved");
			 events.setDate(time);
			 list.add(events);
		}
		
		return list;

	}
}
