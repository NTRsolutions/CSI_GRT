package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.CommonAdapter;
import com.growatt.shinephone.bean.Powerdata;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlantAdminActivity extends DoActivity {
	public static Powerdata powerdata;
	private ArrayList<Map<String, Object>> list;
	private ListView listview;
	private CommonAdapter adapter;
	private int[]titles={R.string.plantadmin_install,R.string.plantadmin_location,R.string.plantadmin_income
			,R.string.plantadmin_pic};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plant_admin);
		initHeaderView();
		SetViews();
		SetListeners();
	}
	private View headerView;
	private void initHeaderView() {
		headerView = findViewById(R.id.headerView);
		setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		setHeaderImage(headerView,R.drawable.add_shebei, Position.RIGHT,new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				if(Cons.isflag==true){
					toast(R.string.all_experience);
					return;
				}
				jumpTo(AddPlantActivity.class, false);
			}
		});
		setHeaderTitle(headerView,getString(R.string.plantadmin_title));
	}
	private void SetViews() {
		powerdata=new Powerdata();
		list=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object>map=new HashMap<String, Object>();
			map.put("title", getResources().getString(titles[i]));
			list.add(map);
		}
		listview=(ListView)findViewById(R.id.listView1);
		adapter=new CommonAdapter(this, list);
		listview.setAdapter(adapter);
		Mydialog.Show(PlantAdminActivity.this, "");
		GetUtil.get(new Urlsutil().getPlant+"&plantId="+Cons.plant, new GetListener() {
			
			@Override
			public void success(String json) {
				Mydialog.Dismiss();
				try {
					JSONObject jsonObject=new JSONObject(json);
						powerdata.setPlantName(jsonObject.get("plantName").toString());
						powerdata.setDesignCompany(jsonObject.get("designCompany").toString());
//						powerdata.setTimeZone(jsonObject.get("timezone").toString());
						powerdata.setTimeZone(jsonObject.get("timezone").toString());
						powerdata.setTimezoneText(jsonObject.get("timezoneText").toString());
						powerdata.setFormulaCo2(jsonObject.get("formulaCo2").toString());
						powerdata.setFormulaCoal(jsonObject.get("formulaCoal").toString());
						powerdata.setNormalPower(jsonObject.get("nominalPower").toString());
						powerdata.setCity(jsonObject.get("city").toString());
						powerdata.setLocationImgName(jsonObject.get("locationImgName").toString());
						powerdata.setPlantImgName(jsonObject.get("plantImgName").toString());
						powerdata.setPlant_lat(jsonObject.get("plant_lat").toString());
						powerdata.setPlant_lng(jsonObject.get("plant_lng").toString());
						powerdata.setCountry(jsonObject.get("country").toString());
						powerdata.setCreateData(jsonObject.get("createDateText").toString());
						powerdata.setFormulaMoney(jsonObject.get("formulaMoney").toString());
						powerdata.setFormulaMoneyUnit(jsonObject.get("formulaMoneyUnitId").toString());
						powerdata.setFormulaSo2(jsonObject.get("formulaSo2").toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void error(String json) {
				
			}
		});
	}

	private void SetListeners() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				switch (position) {
				case 0:
					 startActivity(new Intent(PlantAdminActivity.this,PowerstationdataActivity.class));
					break;
				case 1:
					startActivity(new Intent(PlantAdminActivity.this,GeographyDataActivity.class));
					
					break;
				case 2:
					startActivity(new Intent(PlantAdminActivity.this,CapitalActivity.class));
					break;
				case 3:
					startActivity(new Intent(PlantAdminActivity.this,PlantImagesActivity.class));
					break;

				default:
					break;
				}
			}
		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
//			powerdata = null;
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}


}
