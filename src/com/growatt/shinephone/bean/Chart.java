package com.growatt.shinephone.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Chart {
	private static double[] data;

	/**
	 *
	 * @param json
	 * @param cmdId
	 * @param type
	 * @return
	 * @throws JSONException
	 */
	public static double[] getResponseJson(String json, int type) throws JSONException {
			JSONObject jsonObject = new JSONObject(json);
		if (type == 1) {
			JSONObject item4 = jsonObject.getJSONObject("back");
			if (item4.get("success").toString().equals("true")) {
				JSONObject timejson = item4.getJSONObject("data");
				double[] data = new double[47];
				if(timejson.length()>10){
					data[0] = timejson.getDouble("00:30");
					data[1] = timejson.getDouble("01:00");
					data[2] = timejson.getDouble("01:30");
					data[3] = timejson.getDouble("02:00");
					data[4] = timejson.getDouble("02:30");
					data[5] = timejson.getDouble("03:00");
					data[6] = timejson.getDouble("03:30");
					data[7] = timejson.getDouble("04:00");
					data[8] = timejson.getDouble("04:30");
					data[9] = timejson.getDouble("05:00");
					data[10] = timejson.getDouble("05:30");
					data[11] = timejson.getDouble("06:00");
					data[12] = timejson.getDouble("06:30");
					data[13] = timejson.getDouble("07:00");
					data[14] = timejson.getDouble("07:30");
					data[15] = timejson.getDouble("08:00");
					data[16] = timejson.getDouble("08:30");
					data[17] = timejson.getDouble("09:00");
					data[18] = timejson.getDouble("09:30");
					data[19] = timejson.getDouble("10:00");
					data[20] = timejson.getDouble("10:30");
					data[21] = timejson.getDouble("11:00");
					data[22] = timejson.getDouble("11:30");
					data[23] = timejson.getDouble("12:00");
					data[24] = timejson.getDouble("12:30");
					data[25] = timejson.getDouble("13:00");
					data[26] = timejson.getDouble("13:30");
					data[27] = timejson.getDouble("14:00");
					data[28] = timejson.getDouble("14:30");
					data[29] = timejson.getDouble("15:00");
					data[30] = timejson.getDouble("15:30");
					data[31] = timejson.getDouble("16:00");
					data[32] = timejson.getDouble("16:30");
					data[33] = timejson.getDouble("17:00");
					data[34] = timejson.getDouble("17:30");
					data[35] = timejson.getDouble("18:00");
					data[36] = timejson.getDouble("18:30");
					data[37] = timejson.getDouble("19:00");
					data[38] = timejson.getDouble("19:30");
					data[39] = timejson.getDouble("20:00");
					data[40] = timejson.getDouble("20:30");
					data[41] = timejson.getDouble("21:00");
					data[42] = timejson.getDouble("21:30");
					data[43] = timejson.getDouble("22:00");
					data[44] = timejson.getDouble("22:30");
					data[45] = timejson.getDouble("23:00");
					data[46] = timejson.getDouble("23:30");
				}
				return data;
			}

		} else if (type == 2) {
			JSONObject item6 = jsonObject.getJSONObject("back");
			if (item6.getBoolean("success") == true) {
				JSONObject hintjson = item6.getJSONObject("plantData");
				double[] data = new double[31];
				JSONObject prodSort = item6.getJSONObject("data");
				data[0] = (prodSort.getDouble("01"));
				data[1] = (prodSort.getDouble("02"));
				data[2] = (prodSort.getDouble("03"));
				data[3] = (prodSort.getDouble("04"));
				data[4] = (prodSort.getDouble("05"));
				data[5] = (prodSort.getDouble("06"));
				data[6] = (prodSort.getDouble("07"));
				data[7] = (prodSort.getDouble("08"));
				data[8] = (prodSort.getDouble("09"));
				data[9] = (prodSort.getDouble("10"));
				data[10] = (prodSort.getDouble("11"));
				data[11] = (prodSort.getDouble("12"));
				data[12] = (prodSort.getDouble("13"));
				data[13] = (prodSort.getDouble("14"));
				data[14] = (prodSort.getDouble("15"));
				data[15] = (prodSort.getDouble("16"));
				data[16] = (prodSort.getDouble("17"));
				data[17] = (prodSort.getDouble("18"));
				data[18] = (prodSort.getDouble("19"));
				data[19] = (prodSort.getDouble("20"));
				data[20] = (prodSort.getDouble("21"));
				data[21] = (prodSort.getDouble("22"));
				data[22] = (prodSort.getDouble("23"));
				data[23] = (prodSort.getDouble("24"));
				data[24] = (prodSort.getDouble("25"));
				data[25] = (prodSort.getDouble("26"));
				data[26] = (prodSort.getDouble("27"));
				data[27] = (prodSort.getDouble("28"));
				if(!prodSort.isNull("29")){
					data[28] = (prodSort.getDouble("29"));
				}
				if(!prodSort.isNull("30")){
					data[29] = (prodSort.getDouble("30"));
				}
				if(!prodSort.isNull("31")){
					data[30] = (prodSort.getDouble("31"));
				}
				return data;

			}
		} else if (type == 3) {
			JSONObject item6s = jsonObject.getJSONObject("back");
			if (item6s.getBoolean("success") == true) {
				JSONObject prodSort = item6s.getJSONObject("data");
				double[] data = new double[12];
				data[0] = (prodSort.getDouble("01"));
				data[1] = (prodSort.getDouble("02"));
				data[2] = (prodSort.getDouble("03"));
				data[3] = (prodSort.getDouble("04"));
				data[4] = (prodSort.getDouble("05"));
				data[5] = (prodSort.getDouble("06"));
				data[6] = (prodSort.getDouble("07"));
				data[7] = (prodSort.getDouble("08"));
				data[8] = (prodSort.getDouble("09"));
				data[9] = (prodSort.getDouble("10"));
				data[10] = (prodSort.getDouble("11"));
				data[11] = (prodSort.getDouble("12"));
				return data;
			}
		} else if (type == 4) {
			JSONObject item6s = jsonObject.getJSONObject("back");
			if (item6s.getBoolean("success") == true) {
				return returnYearData(item6s);
			}
		}
		return null;
	}
	/**
	 * @param json
	 * @return
	 */	
	private static double[] returnYearData(JSONObject json){
		double[] data = new double[6];
		try {
			JSONObject prodSort = json.getJSONObject("data");
			GregorianCalendar gc =new GregorianCalendar();
			int curYear = gc.get(Calendar.YEAR);
			data[0] = (prodSort.getDouble(""+(curYear-5)));
			data[1] = (prodSort.getDouble(""+(curYear-4)));
			data[2] = (prodSort.getDouble(""+(curYear-3)));
			data[3] = (prodSort.getDouble(""+(curYear-2)));
			data[4] = (prodSort.getDouble(""+(curYear-1)));
			data[5] = (prodSort.getDouble(""+curYear));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static double[] get(String json,int index){
		try {
			JSONObject jsonObject = new JSONObject(json);
			if(index==2){
				data = new double[31];
				for (int i = 1; i < data.length+1; i++) {
					if(!jsonObject.isNull(""+i)){
						data[i-1]=Double.parseDouble(jsonObject.get(""+i).toString());
					}

				}
			}else if(index==3){
				data = new double[12];
				for (int i = 1; i < data.length+1; i++) {
					if(!jsonObject.isNull(""+i)){
						data[i-1]=Double.parseDouble(jsonObject.get(""+i).toString());
					}

				}
			}else if(index==4){
				data = new double[6];
				GregorianCalendar gc =new GregorianCalendar();
				int curYear = gc.get(Calendar.YEAR);
				if(!jsonObject.isNull(""+(curYear-5))){
					data[0] = (jsonObject.getDouble(""+(curYear-5)));
				}
				if(!jsonObject.isNull(""+(curYear-4))){
					data[1] = (jsonObject.getDouble(""+(curYear-4)));
				}
				if(!jsonObject.isNull(""+(curYear-3))){
					data[2] = (jsonObject.getDouble(""+(curYear-3)));
				}
				if(!jsonObject.isNull(""+(curYear-2))){
					data[3] = (jsonObject.getDouble(""+(curYear-2)));
				}
				if(!jsonObject.isNull(""+(curYear-1))){
					data[4] = (jsonObject.getDouble(""+(curYear-1)));
				}
				if(!jsonObject.isNull(""+(curYear))){
					data[5] = (jsonObject.getDouble(""+curYear));
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	public static List<Object> getpower(String json) throws Exception{
		List<Object> list = new ArrayList<Object>();
		
		JSONObject jsonObject=new JSONObject(json);
		JSONObject jsonObject2=jsonObject.getJSONObject("plantData");
		String plantMoneyText=jsonObject2.getString("plantMoneyText");
		String plantName=jsonObject2.getString("plantName");
		String energyDischarge=jsonObject2.getString("energyDischarge");
		
		JSONObject jsonObject3=jsonObject.getJSONObject("data");
		
		
		
		
		
		return null;
		
	}
}
