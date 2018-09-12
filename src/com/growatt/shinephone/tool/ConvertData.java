package com.growatt.shinephone.tool;

import com.growatt.shinephone.util.ModebusUtil;


public class ConvertData {
	//btye to 16
	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString((int) (0xFF & bArray[i]));
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

//	public static byte lowByte(short value) {
//		return (byte) (value & 0x00FF);
//	}
//
//	public static byte highByte(short value) {
//		return (byte) ((value >> 8) & 0x00FF);
//	}
	public static String getHex(byte[] bArray){
		return bytesToHexString(bArray).substring(6);
	}
	
	public static String intToHex(int value){
		return Integer.toHexString(value);
	}
	public static byte[] requestData(byte address, byte funcCode, byte crcHi,
			byte crcLo) {
		byte[] data = new byte[8];
		data[0] = address;
		data[1] = funcCode;
		data[2] = 0x00;
		data[3] = 0x00;
		data[4] = 0x00;
		data[5] = 0x2d;
		data[6] = crcHi;
		data[7] = crcLo;
		return data;
	}
	public static int getData0(String data,int start,int end) {

		if (data == null || data.equals("")) {
			return 0;
		}
		String getData = data.substring(start, end);
		int result = 0;
		try {
			result = Integer.parseInt(getData,16);
		} catch (Exception e) {
			result = 0;
		}
		
		return result;
	}
	public static int hexToInt(String hex){
		int result = 0;
		try{
			result = Integer.parseInt(hex);
		}catch(Exception e){
			result = Integer.parseInt(hex,16);
		}
		return result;
	}
	
	public static double getDouble(String data,int start,int count){
		int hi = getData0(data,0,2);
		int lo = getData0(data,2,4);
		int result = ModebusUtil.makeWord(hi, lo);
		double strData = result*0.1;
		return strData;
	}
	public static double convert(double data){
		return Math.floor(data*10)/10;
	}
}
