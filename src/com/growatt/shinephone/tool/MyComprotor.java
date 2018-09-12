package com.growatt.shinephone.tool;

import java.util.Comparator;

import com.vo.EventsInfo;


public class MyComprotor implements Comparator<EventsInfo>{

	

	public int compare(EventsInfo lhs, EventsInfo rhs) {
		if(lhs.getDate().equals(rhs.getDate())){
			return 0;
		}else if(lhs.getDate().compareTo(rhs.getDate())>0){
			return 1;
		}else{
			return -1;
		}
	}
	public static void main(String[] args) {
	}
}
