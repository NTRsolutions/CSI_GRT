package com.growatt.shinephone.activity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.growatt.shinephone.R;
import com.growatt.shinephone.tool.DrawCharts1;
import com.growatt.shinephone.tool.DrawHistogram1;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

import org.achartengine.chart.XYChart;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class PhotovoltaicActivity extends DemoBase {

	private ViewGroup view;
	private Map<String, Object> timemap;
	private String url;
	private DrawHistogram1 dh;
	private ArrayList<double[]> timeData;
	private DrawCharts1 ds;
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
	private FrameLayout bt1;
	private FrameLayout bt2;
	private String s;
	private Map<String, Object> newTimemap;
	private TextView tv2;
	private TextView tv4;
	private TextView tv6;
	private TextView txData;
	private TextView tv5;
	private String name;
	DecimalFormat    df;
	String current;
	private View mPopupView;
	private View mPopupView2;
	private TextView tvXY;
	private LineChart lineChart;
	private BarChart barChart;
	private List<ArrayList<Entry>> dataListLine;
	private List<List<BarEntry>> dataListBar;
	private float maxY;
	private float minY;
	private float averageY;
	private LinearLayout llDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photovoltaic);
		Bundle bundle=getIntent().getExtras();
		if(bundle.containsKey("title")){
			name=bundle.getString("title");
		}
		df   = new DecimalFormat("#0.00");
		initHeaderView();
		init();
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
		setHeaderTitle(headerView,name);
	}
	private void initLineChart() {
		MyUtils.initLineChart(this,lineChart,0,"",true,R.color.grid_bg_white,true,R.color.grid_bg_white,true,R.color.note_bg_white,R.color.grid_bg_white,R.color.grid_bg_white,R.color.highLightColor,true,R.string.m4,R.string.m5);
		lineChart.setScaleXEnabled(true);
	}
	private void initBarChart() {
		MyUtils.initBarChart(this,barChart,"",true,R.color.note_bg_white,R.color.grid_bg_white,R.color.grid_bg_white,true,R.color.grid_bg_white,true,R.color.grid_bg_white,R.color.highLightColor);
	}
	public void addLineChart(){
		view.removeAllViews();
		lineChart = new LineChart(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		lineChart.setLayoutParams(params);
		view.addView(lineChart);
		initLineChart();
	}
	public void addBarChart(){
		view.removeAllViews();
		barChart = new BarChart(this);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		barChart.setLayoutParams(params);
		view.addView(barChart);
		initBarChart();
	}
	public void setLineChart(String url){
		txData.setText(timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day"));
		GetUtil.get(url, new GetListener() {
			@Override
			public void error(String json) {
			}
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					JSONObject jsonObjectData = jsonObject.getJSONObject("data");
					String  currentEnergy = jsonObject.getJSONObject("plantData").getString("energyDischarge");
					tv2.setText(currentEnergy);
					dataListLine = new ArrayList<>();
					dataListLine.add(new ArrayList<Entry>());
					dataListLine = MyUtils.parseLineChart1Data(dataListLine,jsonObjectData,1);
					initMaxAndMin(dataListLine);
					tv4.setText(maxY + "W");
					tv6.setText(averageY + "W");
//					MyUtils.setLineChartData(PhotovoltaicActivity.this,lineChart,dataListLine,new int[]{R.color.home_line},new int[]{R.color.home_line},1,R.color.highLightColor);
					MyUtils.setLineChartData(PhotovoltaicActivity.this,lineChart,dataListLine,new int[]{R.color.chart_green_click_line},new int[]{R.color.chart_green_normal_line},1,R.color.note_bg_white);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 初始化折线图最大小值和平均值
	 * @param dataListLine
	 */
	private void initMaxAndMin(List<ArrayList<Entry>> dataListLine) {
		averageY = 0f;
		if (dataListLine != null && dataListLine.size() > 0){
			List<Entry> entries = dataListLine.get(0);
			int size = entries.size();
			if (size>0) {
				Entry entry1 = entries.get(0);
				maxY= entry1.getY();
				minY = entry1.getY();
				for (int i = 0; i < size; i++) {
					Entry entry = entries.get(i);
					float y = entry.getY();
					averageY = averageY + y;
					if (y >= maxY){ maxY = y;}
					if (y < minY){ minY = y;}
				}
				averageY = (float) ((Math.round(averageY/size * 100))/100.0);
			}
		}
	}

	/**
	 * 初始化柱状图最大小值和平均值
	 * @param dataListLine
	 */
	private void initMaxAndMinBarEntry(List<List<BarEntry>> dataListLine) {
		averageY = 0f;
		if (dataListLine != null && dataListLine.size() > 0){
			List<BarEntry> entries = dataListLine.get(0);
			int size = entries.size();
			if (size>0) {
				Entry entry1 = entries.get(0);
				maxY= entry1.getY();
				minY = entry1.getY();
				for (int i = 0; i < size; i++) {
					Entry entry = entries.get(i);
					float y = entry.getY();
					averageY = averageY + y;
					if (y >= maxY){ maxY = y;}
					if (y < minY){ minY = y;}
				}
				averageY = (float) ((Math.round(averageY/size * 100))/100.0);
			}
		}
	}
	public void setBarChart(String url){
		switch (index){
			case 1:
				txData.setText(timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day"));
				break;
			case 2:
				txData.setText(timemap.get("year")+"-"+timemap.get("month"));
				break;
			case 3:
				txData.setText(timemap.get("year")+"");
				break;
			case 4:
				txData.setText(getText(R.string.all_time_all));
				break;
		}
		GetUtil.get(url, new GetListener() {
			@Override
			public void error(String json) {
			}
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					JSONObject jsonObjectData = jsonObject.getJSONObject("data");
					String  currentEnergy = jsonObject.getJSONObject("plantData").getString("energyDischarge");
					tv2.setText(currentEnergy);
					dataListBar = new ArrayList<>();
					dataListBar.add(new ArrayList<BarEntry>());
					dataListBar = MyUtils.parseBarChart1Data(dataListBar,jsonObjectData,1);
					initMaxAndMinBarEntry(dataListBar);
					tv4.setText(maxY + "kWh");
					tv6.setText(averageY + "kWh");
//					MyUtils.setBarChartData(PhotovoltaicActivity.this,barChart,dataListBar,new int[]{R.color.white1},1);
					MyUtils.setBarChartData(PhotovoltaicActivity.this,barChart,dataListBar,new int[]{R.color.chart_green_normal},new int[]{R.color.chart_green_click},1);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void init() {
		mPopupView = View.inflate(this, R.layout.pop_msg, null);
		mPopupView2 = View.inflate(this, R.layout.pop_msg2, null);
		tvXY=(TextView)findViewById(R.id.tvXY);
	}
	private void SetViews() {
		llDate = (LinearLayout) findViewById(R.id.llDate);
		power_units = (TextView) findViewById(R.id.power_units);
		power_units.setText("W");
		tv2 = (TextView) findViewById(R.id.textView2);
		tv4 = (TextView) findViewById(R.id.textView4);
		tv5 = (TextView) findViewById(R.id.textView5);
		tv6 = (TextView) findViewById(R.id.textView6);
		chartday = (TextView) findViewById(R.id.chartday);
		txData = (TextView) findViewById(R.id.txData);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);
		bt1=(FrameLayout)findViewById(R.id.btnadvance);
		bt2=(FrameLayout)findViewById(R.id.btnback);
		view = (ViewGroup) findViewById(R.id.chartsview);
		lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		ds = new DrawCharts1();
		dh=new DrawHistogram1();
		long l = System.currentTimeMillis();
		AppUtils.newtime=l;
		timemap=AppUtils.Timemap(l,0);
		newTimemap=timemap;
		txData.setText(timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day"));
		url=new Urlsutil().getStroageAllEnergy+"1"+"&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day");
		addLineChart();
		setLineChart(url);
	}
	private static final int DATE_PICKER_ID = 1; 

	//��д�÷�����ʾ�Ի��� 
	@Override 
	protected Dialog onCreateDialog(int id) {
		if(id == DATE_PICKER_ID){ 
			return new DatePickerDialog(this,datePickerListener,Integer.parseInt(timemap.get("year").toString()),Integer.parseInt(timemap.get("month").toString())-1,Integer.parseInt(timemap.get("day").toString())){
				@Override
				protected void onStop() {
				}
			};
		}
		return null; 
	}
	DatePickerDialog.OnDateSetListener datePickerListener = new OnDateSetListener() {
		public void onDateSet(DatePicker views, int year, int monthOfYear, 
				int dayOfMonth) {
			if (MyUtils.isFutureTime(mContext,index,year,monthOfYear,dayOfMonth)){return;}
			monthOfYear=monthOfYear+1;
			if(monthOfYear<10){
				timemap.put("month","0"+monthOfYear );
			}else{
				timemap.put("month", monthOfYear+"");
			}
			timemap.put("year", year+"");
			if(dayOfMonth<10){

				timemap.put("day", "0"+dayOfMonth+"");
			}else{
				timemap.put("day", dayOfMonth+"");
			}
			switch (index) {
			case 1:
				url=new Urlsutil().getStroageAllEnergy+"1"+"&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day");
				setLineChart(url);
				break;
			case 2:
				url=new Urlsutil().getStroageAllEnergy+"2"+"&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month");
				setBarChart(url);
				break;
			case 3:
				url=new Urlsutil().getStroageAllEnergy+"3"+"&plantId="+Cons.plant+"&date="+timemap.get("year");
				setBarChart(url);
				break;

			default:
				break;
			}
			getLong();
		} 
	}; 

	private ArrayList<double[]> x;	private void SetListeners() {
		txData.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				//				if(type==1){
//				if (index == 1) {
					showDialog(DATE_PICKER_ID);
//				}
				//				}
			}
		});
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.radio_button11:
					if (llDate.getVisibility() == View.INVISIBLE) {
						llDate.setVisibility(View.VISIBLE);
					}
					power_units.setText("W");
					addLineChart();
					index = 1;
					url=new Urlsutil().getStroageAllEnergy+"1&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day");
					setLineChart(url);
					getLong();
					break;
				case R.id.radio_button22:
					if (llDate.getVisibility() == View.INVISIBLE) {
						llDate.setVisibility(View.VISIBLE);
					}
					if (index == 1){
						addBarChart();
					}
					index = 2;
					power_units.setText("kWh");
					url=new Urlsutil().getStroageAllEnergy+"2&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month");
					setBarChart(url);
					getLong();
					break;
				case R.id.radio_button33:
					if (llDate.getVisibility() == View.INVISIBLE) {
						llDate.setVisibility(View.VISIBLE);
					}
					if (index == 1){
						addBarChart();
					}
					index = 3;
					power_units.setText("kWh");
					url=new Urlsutil().getStroageAllEnergy+"3&plantId="+Cons.plant+"&date="+timemap.get("year");
					setBarChart(url);
					getLong();
					break;
				case R.id.radio_button44:
					if (llDate.getVisibility() == View.VISIBLE) {
					llDate.setVisibility(View.INVISIBLE);
				}
					if (index == 1){
						addBarChart();
					}
					index = 4;
					power_units.setText("kWh");
					url=new Urlsutil().getStroageAllEnergy+"4&plantId="+Cons.plant;
					setBarChart(url);
					getLong();
					break;
				}
			}
		});
		bt1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//ǰһ��
				switch (index) {
				case 1:
					timemap=AppUtils.Timemap(AppUtils.newtime, -86400000);
					url=new Urlsutil().getStroageAllEnergy+"1&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day");
					setLineChart(url);
					getLong();
					break;
				case 2:

					getMonthreduce();
					url=new Urlsutil().getStroageAllEnergy+"2&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month");
					setBarChart(url);
					getLong();
					break;
				case 3:
					getYearreduce();
					url=new Urlsutil().getStroageAllEnergy+"3&plantId="+Cons.plant+"&date="+timemap.get("year");
					setBarChart(url);
					getLong();
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
					if (MyUtils.isFutureTime(mContext,1)) return;
					timemap=AppUtils.Timemap(AppUtils.newtime,86400000);
					url=new Urlsutil().getStroageAllEnergy+"1&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day");
					setLineChart(url);
					getLong();
					break;
				case 2:
					if (MyUtils.isFutureTime(mContext,2)) return;
					getMonthplus();
					url=new Urlsutil().getStroageAllEnergy+"2&plantId="+Cons.plant+"&date="+timemap.get("year")+"-"+timemap.get("month");
					setBarChart(url);
					getLong();

					break;
				case 3:
					if (MyUtils.isFutureTime(mContext,3)) return;
					getYearplus();
					url=new Urlsutil().getStroageAllEnergy+"3&plantId="+Cons.plant+"&date="+timemap.get("year");
					setBarChart(url);
					getLong();
					break;
				case 4:

					break;
				}
			}
		});
	}
	public String  getMonthplus(){
		int a=Integer.parseInt(timemap.get("month").toString())+1;
		if(a<10){
			s="0"+a;
		}else{
			s=a+"";
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
		int a=Integer.parseInt(timemap.get("month").toString());
		a=a-1;
		if(a>0&&a<10){
			s="0"+a;
		}else{
			s=a+"";
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
	public void getLong(){
		try {
			SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");  
			Date date = format.parse(timemap.get("year").toString()+"-"+timemap.get("month")+"-"+timemap.get("day"));
			System.out.println("ʱ��="+timemap.get("year").toString()+"-"+timemap.get("month")+"-"+timemap.get("day"));
			AppUtils.newtime= date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	}
