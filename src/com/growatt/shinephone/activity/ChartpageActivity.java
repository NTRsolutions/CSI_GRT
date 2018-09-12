package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.Chart;
import com.growatt.shinephone.tool.DrawCharts;
import com.growatt.shinephone.tool.DrawHistogram;
import com.growatt.shinephone.tool.GraphicalView;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

import org.achartengine.chart.XYChart;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

public class ChartpageActivity extends DoActivity {

	private ViewGroup view;
	private Map<String, Object> timemap;
	private String url;
	private DrawHistogram dh;
	private ArrayList<double[]> timeData;
	private DrawCharts ds;
	private XYChart mChart;
	private LayoutParams lp;
	private TextView power_units;
	private TextView chartday;
	private ArrayList<double[]> yearData;
	private ArrayList<double[]> monthData;
	private ArrayList<double[]> dayData;
	private RadioGroup radioGroup;
	int index = 1;
	private double AllmaxY;
	private double daymaxY;
	private double monthmaxY;
	private double yearmaxY;
	private ImageButton bt1;
	private ImageButton bt2;
	private TextView tv;
	private int timeStemp;
	private long today=0;
	private String s;
	private Map<String, Object> newTimemap;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chartpage);
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
		setHeaderTitle(headerView,getString(R.string.utf_data_monitor));
	}
	private void SetViews() {
		yearData = new ArrayList<double[]>();
		monthData = new ArrayList<double[]>();
		dayData = new ArrayList<double[]>();
		timeData = new ArrayList<double[]>();
		power_units = (TextView) findViewById(R.id.power_units);
		chartday = (TextView) findViewById(R.id.chartday);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);
		bt1=(ImageButton)findViewById(R.id.btnadvance);
		bt2=(ImageButton)findViewById(R.id.btnback);
		tv=(TextView)findViewById(R.id.txData);
		view = (ViewGroup) findViewById(R.id.chartsview);
		lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
		ds = new DrawCharts();
		dh=new DrawHistogram();
		long l = System.currentTimeMillis();
		AppUtils.newtime=l;
		timemap=AppUtils.Timemap(l,0);
		newTimemap=timemap;
		tv.setText(timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day"));
		url=new Urlsutil().PlantDetailAPI1+"&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day");
		GetData(url, index, new GetDataListener() {

			@Override
			public void success(double[] data,int index) {
				dayData.add(data);
				daymaxY=maxY(data);
				DayData(dayData, index);
			}
		});
	}
	private void SetListeners() {
		bt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_button11:
					tv.setText(timemap.get("year").toString()+"-"+timemap.get("month")+"-"+timemap.get("day"));
					index = 1;
					url=new Urlsutil().PlantDetailAPI1+"&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day");
					if(dayData.size()<=0){
						GetData(url, index, new GetDataListener() {

							@Override
							public void success(double[] data,int index){
								dayData.add(data);
								daymaxY=maxY(data);
								DayData(dayData, index);
							}
						});
					}else{
						DayData(dayData, index);
					}
					break;
				case R.id.radio_button22:
					tv.setText(timemap.get("year").toString()+"-"+timemap.get("month"));
					index = 2;
					url=new Urlsutil().PlantDetailAPI2+"&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month");
					if(monthData.size()<=0){
						GetData(url, index, new GetDataListener() {

							@Override
							public void success(double[] data,int index) {
								monthData.add(data);
								monthmaxY=maxY(data);
								GetMonthData(monthData, index);
							}
						});
					}else{
						GetMonthData(monthData, index);
					}
					break;
				case R.id.radio_button33:
					tv.setText(timemap.get("year").toString());
					index = 3;
					url=new Urlsutil().PlantDetailAPI3+"&plantId="+Cons.plant+"&date="+timemap.get("year");
					if(yearData.size()<=0){
						GetData(url, index, new GetDataListener() {

							@Override
							public void success(double[] data,int index) {
								yearData.add(data);
								yearmaxY=maxY(data);
								GetYearData(yearData, index);

							}
						});
					}else{
						GetYearData(yearData, index);
					}
					break;
				case R.id.radio_button44:
					tv.setText("ȫ��");
					index = 4;
					url=new Urlsutil().PlantDetailAPI4+"&plantId="+Cons.plant;
					if(timeData.size()<=0){
						GetData(url, index, new GetDataListener() {

							@Override
							public void success(double[] data,int index) {
								timeData.add(data);
								AllmaxY=maxY(data);
								GetAllyearData(timeData, index);
							}
						});
					}else{
						GetYearData(timeData, index);
					}
					break;
				}
			}
		});
		bt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				switch (index) {
				case 1:
					timemap=AppUtils.Timemap(AppUtils.newtime, -86400000);
					url=new Urlsutil().PlantDetailAPI1+"&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day");
					GetData(url, index, new GetDataListener() {

						@Override
						public void success(double[] data,int index) {
							dayData.clear();
							dayData.add(data);
							daymaxY=maxY(data);
							DayData(dayData, index);
							tv.setText(timemap.get("year").toString()+"-"+timemap.get("month")+"-"+timemap.get("day"));
						}
					});
					break;
				case 2:
					
					getMonthreduce();
					url=new Urlsutil().PlantDetailAPI2+"&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month");
					GetData(url, index, new GetDataListener() {

						@Override
						public void success(double[] data,int index) {
							monthData.add(data);
							monthmaxY=maxY(data);
							GetMonthData(monthData, index);
							tv.setText(timemap.get("year").toString()+"-"+timemap.get("month"));
						}
					});
					break;
				case 3:
					getYearreduce();
					url=new Urlsutil().PlantDetailAPI3+"&plantId="+Cons.plant+"&date="+timemap.get("year");
						GetData(url, index, new GetDataListener() {

							@Override
							public void success(double[] data,int index) {
								yearData.add(data);
								yearmaxY=maxY(data);
								GetYearData(yearData, index);
								tv.setText(timemap.get("year").toString());
							}
						});
					break;
				case 4:
					
					break;

				default:
					break;
				}
			}
		});
		bt2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				switch (index) {
				case 1:
					int day1=Integer.parseInt(timemap.get("day").toString());
					int day2=Integer.parseInt(newTimemap.get("day").toString());
					System.err.println(day1==day2);
					if(day1==day2){
						break;
					}
					timemap=AppUtils.Timemap(AppUtils.newtime,86400000);
					url=new Urlsutil().PlantDetailAPI1+"&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day");
					GetData(url, index, new GetDataListener() {

						@Override
						public void success(double[] data,int index) {
							dayData.clear();
							dayData.add(data);
							daymaxY=maxY(data);
							DayData(dayData, index);
							tv.setText(timemap.get("year").toString()+"-"+timemap.get("month")+"-"+timemap.get("day"));
						}
					});
					break;
				case 2:
					int month1=Integer.parseInt(timemap.get("month").toString());
					int month2=Integer.parseInt(newTimemap.get("month").toString());
					System.out.println(month1==month2);
					if(month1==month2){
						break;
					}
					getMonthplus();
					url=new Urlsutil().PlantDetailAPI2+"&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month");
					GetData(url, index, new GetDataListener() {

						@Override
						public void success(double[] data,int index) {
							monthData.add(data);
							monthmaxY=maxY(data);
							GetMonthData(monthData, index);
							tv.setText(timemap.get("year").toString()+"-"+timemap.get("month"));
						}
					});	

					break;
				case 3:
					int year1=Integer.parseInt(timemap.get("year").toString());
					int year2=Integer.parseInt(newTimemap.get("year").toString());
					if(year1==year2){
						break;
					}
					getYearplus();
					url=new Urlsutil().PlantDetailAPI3+"&plantId="+Cons.plant+"&date="+timemap.get("year");
						GetData(url, index, new GetDataListener() {

							@Override
							public void success(double[] data,int index) {
								yearData.add(data);
								yearmaxY=maxY(data);
								GetYearData(yearData, index);
								tv.setText(timemap.get("year").toString());
							}
						});
					break;
				case 4:

					break;
				}
			}
		});
	}

	public void GetData(String url,final int index,final GetDataListener getDataListener){
		Mydialog.Show(ChartpageActivity.this,"");
		GetUtil.get(url, new GetListener() {

			@Override
			public void success(String json) {
				double[] data;
				try {
					data = Chart.getResponseJson(json, index);
					if(data.length>0){
						getDataListener.success(data,index);
					}else{
						toast(R.string.all_data_inexistence);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void error(String json) {
				
			}
		});
	}
	//��
	public void DayData(ArrayList<double[]> dayData,int index) {
		view.removeAllViews();
		mChart = ds.execute(ChartpageActivity.this, dayData, getString(R.string.utf_day), "11", 24, 18, ((int) daymaxY + 20),Integer.parseInt(timemap.get("month").toString()), getResources().getColor(R.color.albumback));
		GraphicalView view2 = new GraphicalView(ChartpageActivity.this, mChart, 1);
		view2.setBackgroundColor(getResources().getColor(R.color.blue));
		view.addView(view2, lp);
		view.setBackgroundColor(getResources().getColor(R.color.blue));
	}

	//��
	public void GetMonthData(ArrayList<double[]> monthData,int index){
		view.removeAllViews();
		mChart = dh.execute(ChartpageActivity.this, 2, monthData, getString(R.string.utf_month), "", 30, 30, (int) monthmaxY + 10,2, getResources().getColor(R.color.datatimeselected));
		GraphicalView view4 = new GraphicalView(ChartpageActivity.this, mChart, 0);
		view4.setBackgroundColor(getResources().getColor(R.color.white1));
		view.addView(view4, lp);
		view.setBackgroundColor(getResources().getColor(R.color.white1));
		view.invalidate();
	}
	//��
	public void GetYearData(ArrayList<double[]> yearData,int index){
		view.removeAllViews();
		mChart = dh.execute(ChartpageActivity.this, 3, yearData,getString(R.string.utf_year), "", 30, 30, (int) yearmaxY + 10, 2016, getResources().getColor(R.color.datatimeselected));
		GraphicalView view4 = new GraphicalView(ChartpageActivity.this, mChart, 0);
		view4.setBackgroundColor(getResources().getColor(R.color.white1));
		view.addView(view4, lp);
		view.setBackgroundColor(getResources().getColor(R.color.white1));
		view.invalidate();
	}
	public void GetAllyearData(ArrayList<double[]> timeData,int index){
		if (timeData != null && timeData.size() > 0) {
			mChart = dh.execute(ChartpageActivity.this, 4, timeData,getString(R.string.utf_all_year), "ʲô��", 60, 20, (int) AllmaxY + 10, 2016, getResources().getColor(
					R.color.datatimeselected));
			GraphicalView view4 = new GraphicalView(ChartpageActivity.this, mChart, 0);
			view.addView(view4, lp);
			view.setBackgroundColor(getResources().getColor(R.color.white1));

		}
	}
	public interface GetDataListener {
		void success(double[] data,int index);
	}
	public double maxY(double[] data){
		double maxY = 0;
		if (data != null && data.length > 0) {
			maxY = maxDouble(data);
		} else {
			maxY = 100;
			data = new double[100];
		}
		return maxY;
	}
	private double maxDouble(double[] data) {
		double x = 0.0;
		if (data != null) {
			for (int i = 0; i < data.length; i++) {
				if (x < data[i]) {
					x = data[i];
				}
			}
		}
		return x;
	}
	public String  getMonthplus(){
		int a=Integer.parseInt(timemap.get("month").toString())+1;
		if(a<10){
			s="0"+a;
		}
		if(a==13){
			s="01";
			timemap.put("year", Integer.parseInt(timemap.get("year").toString())+1);
		}

		System.out.println(s);
		timemap.put("month", s);
		return s;
	}
	public String  getMonthreduce(){
		int a=Integer.parseInt(timemap.get("month").toString())-1;
		if(a>0&&a<10){
			s="0"+a;
		}
		if(a==0){
			s="12";
			timemap.put("year", Integer.parseInt(timemap.get("year").toString())-1);
		}

		System.out.println(s);
		timemap.put("month", s);
		return s;
	}
	public String getYearplus(){
		int a=Integer.parseInt(timemap.get("year").toString())+1;
		timemap.put("year", a);
		return a+"";
	}
	public String getYearreduce(){
		int a=Integer.parseInt(timemap.get("year").toString())-1;
		timemap.put("year", a);
		return a+"";
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
