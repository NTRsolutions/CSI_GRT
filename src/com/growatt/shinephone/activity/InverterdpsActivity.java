package com.growatt.shinephone.activity;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.DateAdapter;
import com.growatt.shinephone.adapter.InverterDpsSpinnerAdapter;
import com.growatt.shinephone.adapter.TopScrollAdapter;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.tool.DrawCharts;
import com.growatt.shinephone.tool.DrawHistogram;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.MyUtilsTotal;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.view.SpecialCalendar;

import org.achartengine.chart.XYChart;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.growatt.shinephone.R.id.textView1;
import static com.growatt.shinephone.util.v2.DeviceTypeItemStr.DEVICE_INVERTER_JLINV_STR;
import static com.growatt.shinephone.util.v2.DeviceTypeItemStr.DEVICE_INVERTER_MAX_STR;


public class InverterdpsActivity extends DemoBase implements View.OnTouchListener ,GestureDetector.OnGestureListener {
	private ViewGroup view;
	private Map<String, Object> timemap;
	private String url;
	private DrawHistogram dh;
	private ArrayList<double[]> timeData;
	private DrawCharts ds;
	private XYChart mChart;
	private LayoutParams lp;
	private ArrayList<double[]> yearData;
	private ArrayList<double[]> monthData;
	private RadioGroup radioGroup;
	int index = 1;
	private double AllmaxY;
	private double daymaxY;
	private double monthmaxY;
	private double yearmaxY;
	private FrameLayout bt1;
	private FrameLayout bt2;
	private TextView txData;
	private String s;
	private Map<String, Object> newTimemap;
	private String id;
	private ArrayList<double[]> x=new ArrayList<double[]>();
	private TextView power_units;
	private TextView Pv;
	private int type=1;
	private View mPopupView;
	private View mPopupView2;
	private TextView tvXY;
	private LinearLayout llDate;
	//增加上滑属性
	/**
	 * 滚动显示和隐藏menu,手指所需达到的速度
	 */
	public static final int SNAP_VELOCITY = 200;
	/**
	 * 屏幕宽度
	 */
	private int screenHeight;
	/**
	 * menu最多可以滑动到的左边缘。值有menu布局的宽度来定，marginLeft到达此值后不能再减少
	 */
	private static int topEdge;
	/**
	 * menu最多可以滑动到的右边缘。值恒为0，即marginLeft达到0之后，不能增加
	 */
	private static int rightEdge = 0;
	/**
	 * menu完全显示时，留给content的宽度值
	 */
	private int menuPadding = 0;
	/**
	 * 主内容布局
	 */
	private View content;
	/**
	 * 菜单布局
	 */
	private static View menu;
	/**
	 * menu布局参数，通过此参数来更改leftMargin值
	 */
	private static LayoutParams menuParams;
	/**
	 * 记录手指按下时的纵坐标
	 */
	private float yDown;
	/**
	 * 记录手指移动时的纵坐标
	 */
	private float yMove;
	/**
	 * 记录手指抬起时的纵坐标
	 */
	private float yUp;
	/**
	 * 当前menu是显示还是隐藏，只有完全显示或者隐藏时此值才更改，活动过程中此值无效
	 */
	private static boolean isMenuVisible = true;
	/**
	 * 用于计算手指滑动的速度
	 */
	private VelocityTracker mVelocityTracker;
	private TextView tvSlideUp;
	private ImageView ivSlideUp;
	private LineChart lineChart;
	private BarChart barChart;
	private List<ArrayList<Entry>> dataListLine;
	private List<List<BarEntry>> dataListBar;
	private   int speedDown;
	private  int speedUp ;
	//设置类型：inv或max
	/**
	 * DeviceTypeItemStr.DEVICE_INVERTER_MAX_STR
	 */
	private String mDeviceType;
	//接口url:日月年总
	private String dayUrl;
	private String monthUrl;
	private String yearUrl;
	private String totalUrl;
	/**
	 * 单位
	 */
	private String mUnit = "W";
	SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inverterdps);
		Bundle bundle=getIntent().getExtras();
		id=bundle.getString("inverterId");
		mDeviceType=bundle.getString("deviceType");
		initString();
		int speed = MyUtils.getSpeed(this);
		speedDown = -speed;
		speedUp = speed;
		initHeaderView();
		initSlideAnim();
		initSlideView();
		init();
		SetViews();
		SetListeners();
		initListView();
		refreshData();
	}

	private void initString() {
		if (DEVICE_INVERTER_MAX_STR.equals(mDeviceType)){
			dayUrl = new Urlsutil().getInverterData_max;
			monthUrl = new Urlsutil().getMaxMonthPac;
			yearUrl = new Urlsutil().getMaxYearPac;
			totalUrl = new Urlsutil().getMaxTotalPac;
		}else if (DEVICE_INVERTER_JLINV_STR.equals(mDeviceType)){
			dayUrl = new Urlsutil().getInverterData_jlinv;
			monthUrl = new Urlsutil().getMonthPac_jlinv;
			yearUrl = new Urlsutil().getYearPac_jlinv;
			totalUrl = new Urlsutil().getTotalPac_jlinv;
		}else {
			dayUrl = new Urlsutil().inverterAgetDps;
			monthUrl = new Urlsutil().inverterAgetgetMps;
			yearUrl = new Urlsutil().inverterAgetgetYps;
			totalUrl = new Urlsutil().inverterAgetgetTps;
		}
	}


	private View headerView;
	private void initHeaderView() {
		headerView = findViewById(R.id.headerView);
		setHeaderImage(headerView,R.drawable.back, Position.LEFT,new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		setHeaderTitle(headerView,getString(R.string.inverter_title));
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
//		tvDate.setText(txData.getText().toString());
//		refreshData();
		GetUtil.get(url, new GetListener() {
			@Override
			public void error(String json) {
			}
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					JSONObject jsonObject2=jsonObject.getJSONObject("invPacData");
					dataListLine = new ArrayList<>();
					dataListLine.add(new ArrayList<Entry>());
					dataListLine = MyUtils.parseLineChart1Data(dataListLine,jsonObject2,1);
					MyUtils.setLineChartData(InverterdpsActivity.this,lineChart,dataListLine,new int[]{R.color.chart_green_click_line},new int[]{R.color.chart_green_normal_line},1,R.color.note_bg_white);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
					dataListBar = new ArrayList<>();
					dataListBar.add(new ArrayList<BarEntry>());
					dataListBar = MyUtils.parseBarChart1Data(dataListBar,jsonObject,1);
					MyUtils.setBarChartData(InverterdpsActivity.this,barChart,dataListBar,new int[]{R.color.chart_green_normal},new int[]{R.color.chart_green_click},1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void initSlideAnim() {
		tvSlideUp = (TextView)findViewById(R.id.tvSlideUp);
		ivSlideUp = (ImageView) findViewById(R.id.ivSlideUp);
		AnimationSet animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.set_slide_up);
		ivSlideUp.startAnimation(animationSet);
		animationSet.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				tvSlideUp.setVisibility(View.VISIBLE);
				ivSlideUp.setVisibility(View.INVISIBLE);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

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
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);
		bt1=(FrameLayout)findViewById(R.id.btnadvance);
		bt2=(FrameLayout)findViewById(R.id.btnback);
		txData=(TextView)findViewById(R.id.txData);
		power_units=(TextView)findViewById(R.id.power_units);
		Pv=(TextView)findViewById(textView1);
		power_units.setText("W");
		view = (ViewGroup) findViewById(R.id.chartsview);
		lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		ds = new DrawCharts();
		dh=new DrawHistogram();
		long l = System.currentTimeMillis();
		AppUtils.newtime=l;
		timemap=AppUtils.Timemap(l,0);
		newTimemap=timemap;
		txData.setText(timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day"));
		url=dayUrl+"&id="+id+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day")+"&type="+type;
		addLineChart();

//		setLineChart(url);
	}
	private static final int DATE_PICKER_ID = 1; 
	
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
	OnDateSetListener datePickerListener = new OnDateSetListener() {

		public void onDateSet(DatePicker views, int year, int monthOfYear, 
				int dayOfMonth) {
			if (MyUtils.isFutureTime(mContext,index,year,monthOfYear,dayOfMonth)){return;}
				monthOfYear = monthOfYear + 1;
				if (monthOfYear < 10) {
					timemap.put("month", "0" + monthOfYear);
				} else {
					timemap.put("month", monthOfYear + "");
				}
				timemap.put("year", year + "");
				if (dayOfMonth < 10) {
					timemap.put("day", "0" + dayOfMonth + "");
				} else {
					timemap.put("day", dayOfMonth + "");
				}
				switch (index) {
					case 1:
						url = dayUrl + "&id=" + id + "&date=" + timemap.get("year") + "-" + timemap.get("month") + "-" + timemap.get("day") + "&type=" + 1;
						setLineChart(url);
						break;
					case 2:
						url = monthUrl + "&id=" + id + "&date=" + timemap.get("year") + "-" + timemap.get("month") + "&type=" + 2;
						setBarChart(url);
						break;
					case 3:
						url = yearUrl + "&id=" + id + "&date=" + timemap.get("year") + "&type=" + 3;
						setBarChart(url);
						break;

					default:
						break;
				}

				getLong();
		}
	};

	private void SetListeners() {
		txData.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
//				if(type==1){
//				if (index == 1) {
					showDialog(DATE_PICKER_ID);
//				}
//				}
			}
		});
		Pv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showPopupWindow(arg0);
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
					addLineChart();
					power_units.setText("W");
					Pv.setText(getString(R.string.inverterdps_pv));
					type = 1;
					Pv.setVisibility(View.VISIBLE);
					index = 1;
					url=dayUrl+"&id="+id+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day")+"&type="+type;
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
					power_units.setText("kWh");
					Pv.setVisibility(View.INVISIBLE);
					index = 2;
					url=monthUrl+"&id="+id+"&date="+timemap.get("year")+"-"+timemap.get("month")+"&type="+type;
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
						power_units.setText("kWh");
					Pv.setVisibility(View.INVISIBLE);
					index = 3;
					url=yearUrl+"&id="+id+"&date="+timemap.get("year")+"&type="+type;
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
						power_units.setText("kWh");
					Pv.setVisibility(View.INVISIBLE);
					txData.setText(R.string.all_time_all);
					index = 4;
					url=totalUrl+"&id="+id;
					setBarChart(url);
					getLong();
					break;
				}
			}
		});
		bt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				switch (index) {
				case 1:
					timemap=AppUtils.Timemap(AppUtils.newtime, -86400000);
					url=dayUrl+"&id="+id+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day")+"&type="+type;
					setLineChart(url);
					break;
				case 2:
					getMonthreduce();
					url=monthUrl+"&id="+id+"&date="+timemap.get("year")+"-"+timemap.get("month")+"&type="+type;
					setBarChart(url);
					break;
				case 3:
					getYearreduce();
					url=yearUrl+"&id="+id+"&date="+timemap.get("year")+"&type="+type;
					setBarChart(url);
					break;
				case 4:
					
					break;

				default:
					break;
				}
				getLong();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				switch (index) {
				case 1:
					if (MyUtils.isFutureTime(mContext,1)) return;
					timemap=AppUtils.Timemap(AppUtils.newtime,86400000);
					url=dayUrl+"&id="+id+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day")+"&type="+type;
					setLineChart(url);
					break;
				case 2:
					if (MyUtils.isFutureTime(mContext,2)) return;
					getMonthplus();
					url=monthUrl+"&id="+id+"&date="+timemap.get("year")+"-"+timemap.get("month")+"&type="+type;
					setBarChart(url);

					break;
				case 3:
					if (MyUtils.isFutureTime(mContext,3)) return;
					getYearplus();
					url=yearUrl+"&id="+id+"&date="+timemap.get("year")+"&type="+type;
					setBarChart(url);
					break;
				case 4:

					break;
				}
				getLong();
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
			AppUtils.newtime= date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void showPopupWindow(final View views) {

        // һ���Զ���Ĳ��֣���Ϊ��ʾ������
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.dialog_device_detial, null);
        // ���ð�ť�ĵ���¼�
        final PopupWindow popupWindow = new PopupWindow(contentView,
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
       ListView listView=(ListView)contentView.findViewById(R.id.listView1);
		final int[] list;
		if (DEVICE_INVERTER_MAX_STR.equals(mDeviceType)){
			list =new int[]{R.string.inverterdps_pv,R.string.inverterdps_power_output,R.string.inverterdps_power_r,
					R.string.inverterdps_power_s,R.string.inverterdps_power_t
					,R.string.inverterdps_pv1_voltage,R.string.inverterdps_pv1_electricity,
					R.string.inverterdps_pv2_voltage,R.string.inverterdps_pv2_electricity,
					R.string.PV3电压,R.string.PV3电流,
					R.string.PV4电压,R.string.PV4电流,
					R.string.PV5电压,R.string.PV5电流,
					R.string.PV6电压,R.string.PV6电流,
					R.string.PV7电压,R.string.PV7电流,
					R.string.PV8电压,R.string.PV8电流,
			};
		}else if (DEVICE_INVERTER_JLINV_STR.equals(mDeviceType)){
            list =new int[]{R.string.inverterdps_pv,R.string.inverterdps_pv1_voltage,R.string.inverterdps_pv1_electricity,
                    R.string.inverterdps_pv2_voltage,R.string.inverterdps_pv2_electricity,R.string.inverterdps_power_r,
                    R.string.inverterdps_power_s,R.string.inverterdps_power_t,R.string.inverterdps_power_output
                    ,R.string.PV3电压,R.string.PV4电压,R.string.PV3电流,
                    R.string.PV4电流
            };
        }else {
			list =new int[]{R.string.inverterdps_pv,R.string.inverterdps_pv1_voltage,R.string.inverterdps_pv1_electricity,
					R.string.inverterdps_pv2_voltage,R.string.inverterdps_pv2_electricity,R.string.inverterdps_power_r,
					R.string.inverterdps_power_s,R.string.inverterdps_power_t,R.string.inverterdps_power_output
			};
		}
       InverterDpsSpinnerAdapter adapter = new InverterDpsSpinnerAdapter(InverterdpsActivity.this, list);
       listView.setAdapter(adapter);
       listView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			if(popupWindow.isShowing()){
				popupWindow.dismiss();
				}
			if(index!=1){
//				toast(R.string.inverterdps_pv_data);
				return;
			}
//			if(position==0){
//				findViewById(R.id.radio_button11).setClickable(true);
//				findViewById(R.id.radio_button22).setClickable(true);
//				findViewById(R.id.radio_button33).setClickable(true);
//				findViewById(R.id.radio_button44).setClickable(true);
//			}else{
////				toast(R.string.inverterdps_today);
//				findViewById(R.id.radio_button11).setClickable(false);
//				findViewById(R.id.radio_button22).setClickable(false);
//				findViewById(R.id.radio_button33).setClickable(false);
//				findViewById(R.id.radio_button44).setClickable(false);
//			}
			if (DEVICE_INVERTER_MAX_STR.equals(mDeviceType)){
				switch (position + 1){
					case 1:type = 1;mUnit = "W";break;
					case 2:type = 9;mUnit = "W";break;
					case 3:type = 6;mUnit = "W";break;
					case 4:type = 7;mUnit = "W";break;
					case 5:type = 8;mUnit = "W";break;

					case 6:type = 2;mUnit = "V";break;
					case 7:type = 3;mUnit = "A";break;
					case 8:type = 4;mUnit = "V";break;
					case 9:type = 5;mUnit = "A";break;
					case 10:type = 10;mUnit = "V";break;
					case 11:type = 16;mUnit = "A";break;
					case 12:type = 11;mUnit = "V";break;
					case 13:type = 17;mUnit = "A";break;
					case 14:type = 12;mUnit = "V";break;
					case 15:type = 18;mUnit = "A";break;
					case 16:type = 13;mUnit = "V";break;
					case 17:type = 19;mUnit = "A";break;
					case 18:type = 14;mUnit = "V";break;
					case 19:type = 20;mUnit = "A";break;
					case 20:type = 15;mUnit = "V";break;
					case 21:type = 21;mUnit = "A";break;
				}
			}else if (DEVICE_INVERTER_JLINV_STR.equals(mDeviceType)){
                type = position + 1;
                if (type == 1 || type == 8 || type == 6 || type == 7 || type == 9) {
                    mUnit = "W";
                } else if (type == 2 || type == 4||type==10||type==11) {
                    mUnit = "V";
                } else if (type == 3 || type == 5||type==12||type==13) {
                    mUnit = "A";
                }
            }else {
				type = position + 1;
				if (type == 1 || type == 8 || type == 6 || type == 7 || type == 9) {
					mUnit = "W";
				} else if (type == 2 || type == 4) {
					mUnit = "V";
				} else if (type == 3 || type == 5) {
					mUnit = "A";
				}
			}
			power_units.setText(mUnit);
			Pv.setText(list[position]);
			switch (index) {
			case 1:
				url=dayUrl+"&id="+id+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day")+"&type="+type;
			setLineChart(url);
//				refreshData();
				getLong();
				break;
			case 2:
				url=monthUrl+"&id="+id+"&date="+timemap.get("year")+"-"+timemap.get("month")+"&type="+type;
				setBarChart(url);
				getLong();
				break;
			case 3:
				url=yearUrl+"&id="+id+"&date="+timemap.get("year")+"&type="+type;
				setBarChart(url);
				getLong();
				break;

			default:
				break;
			}
		}
	});
    

        popupWindow.setBackgroundDrawable(new BitmapDrawable());  
        //���õ��������ߴ�����ʧ  
        popupWindow.setOutsideTouchable(true);  
        // ���ô˲�����ý��㣬�����޷����  
        popupWindow.setFocusable(true);  
        popupWindow.showAsDropDown(views, 0, 0);

    }
	private void initSlideView() {
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		screenHeight = dm.heightPixels - MyUtilsTotal.getStatusBarHeight(this);
		content = findViewById(R.id.content);
		menu = findViewById(R.id.menu);
		menuParams = (LayoutParams) menu.getLayoutParams();
		menuParams.height = screenHeight - menuPadding;
		menuParams.width = dm.widthPixels;
		topEdge = -menuParams.height;
		menuParams.topMargin = 0;
		content.getLayoutParams().height = screenHeight;
//
		content.setOnTouchListener(this);
		menu.setOnTouchListener(this);
	}
	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		createVelocityTracker(motionEvent);
		switch (motionEvent.getAction()){
			case MotionEvent.ACTION_DOWN:
				yDown = motionEvent.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				yMove = motionEvent.getRawY();
				int distanceX = (int) (yMove - yDown);
				if (isMenuVisible) {
					menuParams.topMargin = distanceX;
				} else {
					menuParams.topMargin = topEdge + distanceX;
				}
				if (menuParams.topMargin < topEdge){
					menuParams.topMargin = topEdge;
				} else if (menuParams.topMargin > rightEdge){
					menuParams.topMargin = rightEdge;
				}
				menu.setLayoutParams(menuParams);
				break;
			case MotionEvent.ACTION_UP:
				yUp = motionEvent.getRawY();
				if (wantToShowMenu()){
					if (shouldScrollToMenu()){
						scrollToMenu();
					} else {
						scrollToContent();
					}
				} else if (wantToShowContent()) {
					if (shouldScrollToContent()) {
						scrollToContent();
					} else {
						scrollToMenu();
					}
				}
				recycleVelocityTracker();
				break;

		}
		return true;
	}

	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	private void scrollToContent() {
		new ScrollTask().execute(speedDown);
	}

	private void scrollToMenu() {
		new ScrollTask().execute(speedUp);
	}

	private boolean shouldScrollToContent() {
		return yDown - yUp + menuPadding > screenHeight /5 || getScrollVelocity() > SNAP_VELOCITY;
	}

	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}

	private boolean shouldScrollToMenu() {
		return yUp - yDown > screenHeight /5 || getScrollVelocity() > SNAP_VELOCITY;
	}

	private boolean wantToShowContent() {
		return yUp - yDown < 0 && isMenuVisible;
	}

	private boolean wantToShowMenu() {
		return yUp - yDown > 0 && !isMenuVisible;
	}

	private void createVelocityTracker(MotionEvent motionEvent) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(motionEvent);
	}
	public static class ScrollTask extends AsyncTask<Integer,Integer,Integer> {

		@Override
		protected Integer doInBackground(Integer... speed) {
			int leftMargin = menuParams.topMargin;
			LogUtil.i("speed:leftMargin="+leftMargin);
			while (true) {
				leftMargin = leftMargin + speed[0];
				if (leftMargin > rightEdge){
					leftMargin = rightEdge;
					break;
				}
				if (leftMargin < topEdge){
					leftMargin = topEdge;
					break;
				}
				publishProgress(leftMargin);
				sleep(5);
			}
			if (speed[0] > 0){
				isMenuVisible = true;
			} else {
				isMenuVisible = false;
			}
			return leftMargin;
		}

		@Override
		protected void onProgressUpdate(Integer... leftMargin) {
			menuParams.topMargin = leftMargin[0];
			menu.setLayoutParams(menuParams);
		}

		@Override
		protected void onPostExecute(Integer leftMargin) {
			menuParams.topMargin = leftMargin;
			menu.setLayoutParams(menuParams);
		}
	}

	private static void sleep(long i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private static String TAG = "MainActivity";
	private ViewFlipper flipper1 = null;
	private GridView gridView = null;
	private GestureDetector gestureDetector = null;
	private int year_c = 0;
	private int month_c = 0;
	private int day_c = 0;
	private int week_c = 0;
	private int week_num = 0;
	private String currentDate = "";
	private DateAdapter dateAdapter;
	private int daysOfMonth = 0; // 某月的天数
	private int dayOfWeek = 0; // 具体某一天是星期几
	private int weeksOfMonth = 0;
	private SpecialCalendar sc = null;
	private boolean isLeapyear = false; // 是否为闰年
	private int selectPostion = 0;
	private String dayNumbers[] = new String[7];
	private TextView tvDate;
	private int currentYear;
	private int currentMonth;
	private int currentWeek;
	private int currentDay;
	private int currentNum;
	private ListView mListView;
	List<Map<String,String>> mList = new ArrayList<>();
	private TopScrollAdapter mAdapter;
	private  int minScroll;
	public InverterdpsActivity() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = sdf.format(date);
		year_c = Integer.parseInt(currentDate.split("-")[0]);
		month_c = Integer.parseInt(currentDate.split("-")[1]);
		day_c = Integer.parseInt(currentDate.split("-")[2]);
		currentYear = year_c;
		currentMonth = month_c;
		currentDay = day_c;
		sc = new SpecialCalendar();
		getCalendar(year_c, month_c);
		week_num = getWeeksOfMonth();
		currentNum = week_num;
		if (dayOfWeek == 7) {
			week_c = currentDay / 7 + 1;
		} else {
			if (currentDay <= (7 - dayOfWeek)) {
				week_c = 1;
			} else {
				if ((currentDay - (7 - dayOfWeek)) % 7 == 0) {
					week_c = (currentDay - (7 - dayOfWeek)) / 7 + 1;
				} else {
					week_c = (currentDay - (7 - dayOfWeek)) / 7 + 2;
				}
			}
		}
		currentWeek = week_c;
		getCurrent();

	}
	/**
	 * 判断某年某月所有的星期数
	 *
	 * @param year
	 * @param month
	 */
	public int getWeeksOfMonth(int year, int month) {
		// 先判断某月的第一天为星期几
		int preMonthRelax = 0;
		int dayFirst = getWhichDayOfWeek(year, month);
		int days = sc.getDaysOfMonth(sc.isLeapYear(year), month);
		if (dayFirst != 7) {
			preMonthRelax = dayFirst;
		}
		if ((days + preMonthRelax) % 7 == 0) {
			weeksOfMonth = (days + preMonthRelax) / 7;
		} else {
			weeksOfMonth = (days + preMonthRelax) / 7 + 1;
		}
		return weeksOfMonth;

	}

	/**
	 * 判断某年某月的第一天为星期几
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public int getWhichDayOfWeek(int year, int month) {
		return sc.getWeekdayOfMonth(year, month);

	}

	/**
	 *
	 * @param year
	 * @param month
	 */
	public int getLastDayOfWeek(int year, int month) {
		return sc.getWeekDayOfLastMonth(year, month,
				sc.getDaysOfMonth(isLeapyear, month));
	}

	public void getCalendar(int year, int month) {
		isLeapyear = sc.isLeapYear(year); // 是否为闰年
		daysOfMonth = sc.getDaysOfMonth(isLeapyear, month); // 某月的总天数
		dayOfWeek = sc.getWeekdayOfMonth(year, month); // 某月第一天为星期几
	}

	public int getWeeksOfMonth() {
		// getCalendar(year, month);
		int preMonthRelax = 0;
		if (dayOfWeek != 7) {
			preMonthRelax = dayOfWeek;
		}
		if ((daysOfMonth + preMonthRelax) % 7 == 0) {
			weeksOfMonth = (daysOfMonth + preMonthRelax) / 7;
		} else {
			weeksOfMonth = (daysOfMonth + preMonthRelax) / 7 + 1;
		}
		return weeksOfMonth;
	}
	// private boolean isStart;// 是否是交接的月初
	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		int gvFlag = 0;
		if (e1.getX() - e2.getX() > 80) {
			// 向左滑
			addGridView();
			currentWeek++;
			getCurrent();
			dateAdapter = new DateAdapter(this, currentYear, currentMonth,
					currentWeek, currentWeek == 1 ? true : false);
			dayNumbers = dateAdapter.getDayNumbers();
			gridView.setAdapter(dateAdapter);
			setDate(selectPostion);
			gvFlag++;
			flipper1.addView(gridView, gvFlag);
//            dateAdapter.setSeclection(selectPostion);
			this.flipper1.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_left_in));
			this.flipper1.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_left_out));
			this.flipper1.showNext();
			flipper1.removeViewAt(0);
			return true;

		} else if (e1.getX() - e2.getX() < -80) {
			addGridView();
			currentWeek--;
			getCurrent();
			dateAdapter = new DateAdapter(this, currentYear, currentMonth,
					currentWeek, currentWeek == 1 ? true : false);
			dayNumbers = dateAdapter.getDayNumbers();
			gridView.setAdapter(dateAdapter);
			setDate(selectPostion);
			gvFlag++;
			flipper1.addView(gridView, gvFlag);
//            dateAdapter.setSeclection(selectPostion);
			this.flipper1.setInAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_right_in));
			this.flipper1.setOutAnimation(AnimationUtils.loadAnimation(this,
					R.anim.push_right_out));
			this.flipper1.showPrevious();
			flipper1.removeViewAt(0);
			return true;
		}
		return false;
	}
	private void initListView() {
		tvDate = (TextView) findViewById(R.id.tv_date);
//        tvDate.setText(year_c + "年" + month_c + "月" + day_c + "日");
		tvDate.setText(currentDate);
		gestureDetector = new GestureDetector(this);
		flipper1 = (ViewFlipper) findViewById(R.id.flipper1);
		dateAdapter = new DateAdapter(this, currentYear, currentMonth,
				currentWeek, currentWeek == 1 ? true : false);
		addGridView();
		dayNumbers = dateAdapter.getDayNumbers();
		gridView.setAdapter(dateAdapter);
		selectPostion = dateAdapter.getTodayPosition();
		gridView.setSelection(selectPostion);
		flipper1.addView(gridView, 0);
		mListView = (ListView) findViewById(R.id.lvData);
		minScroll = MyControl.dip2px(InverterdpsActivity.this,100f);

//		XYMultipleSeriesDataset dataset = mChart.getDataset();
//		XYSeries seriesAt = dataset.getSeriesAt(0);
//		for(int i=0;i<seriesAt.getItemCount();i++){
//			Map<String,String> map = new HashMap<>();
//			map.put("energy",seriesAt.getY(i)+"");
//			map.put("time",seriesAt.getX(i)+"");
//			mList.add(i,map);
//		}
		mAdapter = new TopScrollAdapter(this,mList);
		mListView.setAdapter(mAdapter);
		mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			int index = 0;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(index == 0  && scrollState == this.SCROLL_STATE_IDLE && (view.getChildAt(0).getTop() >= 0) && (yUp - yDown >minScroll )){
					new ScrollTask().execute(speedUp);
//					mListView.setOnTouchListener(InverterdpsActivity.this);
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				index = firstVisibleItem;
			}
		});
		mListView.setOnTouchListener(new View.OnTouchListener() {
			@Override

			public boolean onTouch(View v, MotionEvent motionEvent) {
				switch (motionEvent.getAction()){
					case MotionEvent.ACTION_DOWN:
						yDown = motionEvent.getRawY();
						break;
					case MotionEvent.ACTION_MOVE:
						break;
					case MotionEvent.ACTION_UP:
						yUp = motionEvent.getRawY();
						break;

				}
				return false;
			}
		});
	}

	private void addGridView() {
		LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		gridView = new GridView(this);
		gridView.setNumColumns(7);
		gridView.setGravity(Gravity.CENTER_VERTICAL);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gridView.setVerticalSpacing(1);
		gridView.setHorizontalSpacing(1);
		gridView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return InverterdpsActivity.this.gestureDetector.onTouchEvent(event);
			}
		});
/**
 * 加载数据
 */
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				Log.i(TAG, "day:" + dayNumbers[position]);
				selectPostion = position;
				dateAdapter.setSeclection(position);
				dateAdapter.notifyDataSetChanged();
				setDate(position);
				//判断是否是未来数据
				if (MyUtils.isFutureTime(InverterdpsActivity.this,tvDate.getText().toString())) return;
				//重新加载数据
				refreshData();

			}


		});
		gridView.setLayoutParams(params);
	}



	private void refreshData() {
		url=dayUrl+"&id="+id+"&date="+tvDate.getText().toString()+"&type="+type;
//		url=new Urlsutil().inverterAgetDps+"&id="+id+"&date="+txData.getText().toString()+"&type="+1;
		GetUtil.get(url, new GetListener() {
			@Override
			public void error(String json) {
			}
			@Override
			public void success(String json) {
				try {
					JSONObject jsonObject = new JSONObject(json);
					JSONObject jsonObject2=jsonObject.getJSONObject("invPacData");
					dataListLine = new ArrayList<>();
					dataListLine.add(new ArrayList<Entry>());
					dataListLine = MyUtils.parseLineChart1Data(dataListLine,jsonObject2,1);
					MyUtils.setLineChartData(InverterdpsActivity.this,lineChart,dataListLine,new int[]{R.color.chart_green_click_line},new int[]{R.color.chart_green_normal_line},1,R.color.note_bg_white);
					//TODO 更新listview
					List<Map<String,String>> newList = new ArrayList<Map<String, String>>();
					List<Entry> entrys = dataListLine.get(0);
					for(int i=0,j=0,size = entrys.size();i<size;i++){
						Entry entry = entrys.get(i);
//						if (entry.getX() < 0.0001) continue;
						Map<String,String> map = new HashMap<>();
						map.put("energy",entry.getY()+mUnit);
						String day = MyUtils.sdf_hm.format(new Date((long) (entry.getX() * MyUtils.minTamp)));
						map.put("time",day);
						newList.add(j,map);
						j++;
					}
					mAdapter.addAll(newList,true);
					if (mAdapter.isEmpty()){
						mListView.setOnTouchListener(InverterdpsActivity.this);
					}else {
						mListView.setOnTouchListener(new View.OnTouchListener() {
							@Override

							public boolean onTouch(View v, MotionEvent motionEvent) {
								switch (motionEvent.getAction()){
									case MotionEvent.ACTION_DOWN:
										yDown = motionEvent.getRawY();
										break;
									case MotionEvent.ACTION_MOVE:
										break;
									case MotionEvent.ACTION_UP:
										yUp = motionEvent.getRawY();
										break;

								}
								return false;
							}
						});
					}
					txData.setText(tvDate.getText().toString());

					if (radioGroup.getCheckedRadioButtonId() != R.id.radio_button11){
						RadioButton rb = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
						rb.setChecked(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * 重新计算当前的年月
	 */
	public void getCurrent() {
		if (currentWeek > currentNum) {
			if (currentMonth + 1 <= 12) {
				currentMonth++;
			} else {
				currentMonth = 1;
				currentYear++;
			}
			currentWeek = 1;
			currentNum = getWeeksOfMonth(currentYear, currentMonth);
		} else if (currentWeek == currentNum) {
			if (getLastDayOfWeek(currentYear, currentMonth) == 6) {
			} else {
				if (currentMonth + 1 <= 12) {
					currentMonth++;
				} else {
					currentMonth = 1;
					currentYear++;
				}
				currentWeek = 1;
				currentNum = getWeeksOfMonth(currentYear, currentMonth);
			}

		} else if (currentWeek < 1) {
			if (currentMonth - 1 >= 1) {
				currentMonth--;
			} else {
				currentMonth = 12;
				currentYear--;
			}
			currentNum = getWeeksOfMonth(currentYear, currentMonth);
			currentWeek = currentNum - 1;
		}
	}
	//设置textview日期
	public void setDate(int position){
		tvDate.setText(dateAdapter.getCurrentYear(position) + "-"
				+ (dateAdapter.getCurrentMonth(position)<10 ? "0"+dateAdapter.getCurrentMonth(position) : dateAdapter.getCurrentMonth(position)) + "-"
				+ (dayNumbers[position].length()==1 ? "0"+dayNumbers[position] : dayNumbers[position]));

		try {
			Date date = format.parse(tvDate.getText().toString());
			AppUtils.newtime= date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}