package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.growatt.shinephone.R;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.tool.DrawCharts;
import com.growatt.shinephone.tool.RoundProgressBar;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.util.v2.DeviceTypeItemStr;

import org.achartengine.chart.XYChart;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class InverterActivity extends DoActivity {

	private int progress = 0;
	private RoundProgressBar mRoundProgressBar1;
//	private ViewGroup view;
	private LayoutParams lp;
	private DrawCharts ds;
	private Map<String, Object> timemap;
	private String plant;
	private ImageView imageview1;
	private ImageView imageview2;
	private ImageView imageview3;
	private TextView eToday;
	private TextView eTotal;
	private TextView power;
	private ImageView imageview5;
	private String datalogSn;
	private String deviceAilas;
	private String snominalPower;
	List<double[]> x;
	private TextView powerunit;
	private Intent frgIntent;
	private View mPopupView;
	private View mPopupView2;
	private TextView tvXY;
    private RelativeLayout rlView;
	private LineChart lineChart;
	//逆变器类型：0：逆变器；1：Max
	private int invType = 0;
	private String dateFormat = "yyyy-MM-dd";
	//获取逆变器功率图标url
	private String dataUrl;
	//逆变器跳转类型
	private String deviceType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inverter);
		init();
		frgIntent=new Intent(Constant.Frag_Receiver);
		initIntent();
//		initTest();
		initUrlOrString();
		initHeaderView();
		initLineChart();
		SetViews();
		SetListeners();
	}

	private void initIntent() {
		Intent mIntent = getIntent();
		Bundle bundle=mIntent.getExtras();
		plant=bundle.getString("id");
		datalogSn=bundle.getString("datalogSn");
		deviceAilas=bundle.getString("deviceAilas");
		invType = mIntent.getIntExtra("invType",0);
	}

	private void initTest() {
		plant = "INVERSNERR";
		invType = 1;
	}

	private void initUrlOrString() {
		StringBuilder sb = new StringBuilder();
		switch (invType){
			case 1://Max
				sb.append(new Urlsutil().getInverterData_max).append("&id=");
				deviceType = DeviceTypeItemStr.DEVICE_INVERTER_MAX_STR;
				break;
			case 2://JLINV 锦浪
				sb.append(new Urlsutil().getInverterData_jlinv).append("&id=");
				deviceType = DeviceTypeItemStr.DEVICE_INVERTER_JLINV_STR;
				break;
			default://逆变器
				sb.append(new Urlsutil().getInverterData).append("&id=");
				deviceType = DeviceTypeItemStr.DEVICE_INVERTER_STR;
				break;
		}
		sb.append(plant).append("&type=1&date=").append(new SimpleDateFormat(dateFormat).format(new Date()));
		dataUrl = sb.toString();
	}

	private View headerView;
	private void initHeaderView() {
		headerView = findViewById(R.id.headerView);
		setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				sendBroadcast(frgIntent);
				finish();
			}
		});
		setHeaderTitle(headerView,deviceAilas);
	}
	private void initLineChart() {
		lineChart = (LineChart)findViewById(R.id.lineChart);
		MyUtils.initLineChart(this,lineChart,0,"",true,R.color.grid_bg_white,true,R.color.grid_bg_white,true,R.color.note_bg_white,R.color.grid_bg_white,R.color.grid_bg_white,R.color.highLightColor,true,R.string.m4,R.string.m5);
		lineChart.setScaleXEnabled(true);
	}

	private void init() {
	        mPopupView = View.inflate(this, R.layout.pop_msg, null);
	        mPopupView2 = View.inflate(this, R.layout.pop_msg2, null);
	        tvXY=(TextView)findViewById(R.id.tvXY);
	    }
	private void SetViews() {
		rlView=(RelativeLayout)findViewById(R.id.view);
		imageview1=(ImageView)findViewById(R.id.imageView1);
		imageview2=(ImageView)findViewById(R.id.imageView2);
		imageview3=(ImageView)findViewById(R.id.imageView3);
		imageview5=(ImageView)findViewById(R.id.imageView5);
		eToday=(TextView)findViewById(R.id.textView2);
		eTotal=(TextView)findViewById(R.id.textView4);
		power=(TextView)findViewById(R.id.textView9);
		powerunit=(TextView)findViewById(R.id.textView11);
		mRoundProgressBar1 = (RoundProgressBar) findViewById(R.id.roundProgressBar1);
//		view = (ViewGroup) findViewById(R.id.chartsview);
		lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		ds = new DrawCharts();
		long l = System.currentTimeMillis();
		AppUtils.newtime=l;
		timemap=AppUtils.Timemap(l,0);
		Mydialog.Show(InverterActivity.this,"");

		try {
			plant=URLEncoder.encode(plant, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GetUtil.get(dataUrl, new GetListener() {
			
			private int c;
			private XYChart mChart;
			@Override
			public void success(String json) {
				Mydialog.Dismiss();
				try {
					if(json.length()<30){
						JSONObject jsonObject=new JSONObject(json);
						String msg=jsonObject.getString("msg");
						if(msg.equals("501")){
							toast(R.string.inverter_inexistence);
						}
					}else{
					JSONObject jsonObject=new JSONObject(json);
					String seToday=jsonObject.optString("eToday");
					String seTotal=jsonObject.optString("eTotal");
					snominalPower=jsonObject.optString("nominalPower");
					String spower=jsonObject.optString("power");
					
					double d=Double.parseDouble(seToday);
					if(d>10000){
						d=divide(d, 1000, 2);
//						System.out.println("a="+d);
						eToday.setText(d+"MWh");
					}else{
						eToday.setText(seToday+"kWh");
					}
					 d=Double.parseDouble(seTotal);
					if(d>10000){
						d=divide(d, 1000, 2);
//						System.out.println("a="+d);
						eTotal.setText(d+"MWh");
					}else{
						eTotal.setText(seTotal+"kWh");
					}
//						eTotal.setText(seTotal+"kWh");
//					d=Double.parseDouble(spower);
//					if(d>1000){
//						d=divide(d, 1000, 2);
//						System.out.println("a="+d);
//						power.setText(d+"");
//						powerunit.setText("kW");
//					}else{
						power.setText(spower);
//						powerunit.setText("W");
//					}
//					int a=Integer.parseInt(getFormat(spower).replace(".", ""));
//					int b=Integer.parseInt(snominalPower);
//					if(a!=0&&b!=0){
//					c=a/b;
//					}
					double a=Double.parseDouble(spower)*100;
					double b=Double.parseDouble(snominalPower);
					if(a!=0&&b!=0){
						c=(int) (a/b);
						}
					   JSONObject jsonObject2=jsonObject.getJSONObject("invPacData");
						List<ArrayList<Entry>> dataList = new ArrayList<>();
						dataList.add(new ArrayList<Entry>());
						dataList = MyUtils.parseLineChart1Data(dataList,jsonObject2,1);
						MyUtils.setLineChartData(InverterActivity.this,lineChart,dataList,new int[]{R.color.chart_green_click_line},new int[]{R.color.chart_green_normal_line},1,R.color.note_bg_white);
					}

					new Thread(new Runnable(){
						
						@Override
						public void run() {
							while(progress < c){
								progress += 1;
								mRoundProgressBar1.setProgress(progress);
								try {
									Thread.sleep(35);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}).start();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			
			@Override
			public void error(String json) {
				
			}
		});
	}


	private void SetListeners() {
		eToday.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyControl.textClick(v);
			}
		});
		eTotal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyControl.textClick(v);
			}
		});
		power.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyControl.textClick(v);
			}
		});
		imageview1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(InverterActivity.this,InverterSet2Activity.class);
				Bundle bundle=new Bundle();
				bundle.putString("inverterId", plant);
				bundle.putString("deviceType", deviceType);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		imageview2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(InverterActivity.this,InverterdevicedataActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("inverterId", plant);
				bundle.putString("datalogSn", datalogSn);
				bundle.putString("deviceAilas", deviceAilas);
				bundle.putString("snominalPower", snominalPower);
				bundle.putString("deviceType", deviceType);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		imageview3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(InverterActivity.this,RizhiActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("plant", plant);
				bundle.putString("type", deviceType);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		imageview5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(InverterActivity.this,InverterdpsActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("inverterId", plant);
				bundle.putString("deviceType", deviceType);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			sendBroadcast(frgIntent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

    /**
     * @param v1
     * @param v2
     * @param scale �Խ��������λС��
     * @return
     */
    public  double divide(double v1, int v2,int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(v2+"");
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
