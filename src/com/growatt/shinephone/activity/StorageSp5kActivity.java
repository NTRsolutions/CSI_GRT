package com.growatt.shinephone.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.StorageParamsBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.fragment.WaveHelper;
import com.growatt.shinephone.tool.DrawCharts;
import com.growatt.shinephone.tool.DrawHistogram;
import com.growatt.shinephone.ui.WaveView;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

import org.achartengine.chart.XYChart;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@ContentView(R.layout.activity_storage_sp5k)
public class StorageSp5kActivity extends DoActivity {
    @ViewInject(R.id.tvIncomeToday) TextView tvIncomeToday;
    @ViewInject(R.id.tvIncomeTotal) TextView tvIncomeTotal;
    private WaveView waveview;
    private WaveHelper waveHelper;
//    private StorageBean bean;
    private StorageParamsBean bean;
    private String plant;
    private TextView textview2;
    private TextView textview4;
    private ImageView imageview;
    private ImageView imageview2;
    private ImageView imageview3;
    private ImageView imageview4;
    private TextView textview7;
    private TextView textview9;
    private String datalogSn;
    private ViewGroup view;
    private LayoutParams lp;
    private DrawCharts ds;
    private DrawHistogram dh;
    private Map<String, Object> timemap;
    private String url;
    private ArrayList<double[]> dayData;
    private double daymaxY;
    private XYChart mChart;
    private TextView textview10;
    private String alias;
    private Intent frgIntent;
    private View mPopupView;
    private View mPopupView2;
    private TextView tvXY;
    private LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frgIntent=new Intent(Constant.Frag_Receiver);
        Bundle bundle=getIntent().getExtras();
        plant=bundle.getString("id");
        datalogSn=bundle.getString("datalogSn");
        alias=bundle.getString("deviceAilas");
        initHeaderView();
        init();
        initLineChart();
        SetViews();
        SetListeners();
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
        setHeaderTitle(headerView,alias);
    }
    private void initLineChart() {
        lineChart = (LineChart)findViewById(R.id.lineChart);
//        MyUtils.initLineChart(this,lineChart,1,"",true,R.color.title_2,R.color.xy_grid_st,R.color.xy_grid_st,R.color.highLightColor,true,R.string.m4,R.string.m5);
        MyUtils.initLineChart(this,lineChart,1,"",true,R.color.grid_bg_white,true,R.color.grid_bg_white,true,R.color.note_bg_white,R.color.grid_bg_white,R.color.grid_bg_white,R.color.highLightColor,true,R.string.m4,R.string.m5);
        lineChart.setScaleXEnabled(true);
    }
    private void init() {
        mPopupView = View.inflate(this, R.layout.pop_msg, null);
        mPopupView2 = View.inflate(this, R.layout.pop_msg2, null);
        tvXY = (TextView)findViewById(R.id.tvXY);
    }
    private void SetViews() {
        waveview=(WaveView)findViewById(R.id.waveview);
        textview2=(TextView)findViewById(R.id.textView2);
        textview4=(TextView)findViewById(R.id.textView4);
        textview7=(TextView)findViewById(R.id.textView7);
        textview9=(TextView)findViewById(R.id.textView9);
        textview10=(TextView)findViewById(R.id.textView10);
        imageview=(ImageView)findViewById(R.id.imageView1);
        imageview2=(ImageView)findViewById(R.id.imageView2);
        imageview3=(ImageView)findViewById(R.id.imageView3);
        imageview4=(ImageView)findViewById(R.id.imageView5);
        view = (ViewGroup) findViewById(R.id.chartsview);
        lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ds = new DrawCharts();
        dh=new DrawHistogram();
        long l = System.currentTimeMillis();
        AppUtils.newtime=l;
        timemap=AppUtils.Timemap(l,0);
        url=new Urlsutil().storageAgetgetDls+"&id="+plant+"&date="+timemap.get("year")+"-"+timemap.get("month")+"-"+timemap.get("day")+"&type=7";
    }

    private void SetListeners() {
        textview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyControl.textClick(v);
            }
        });
        textview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyControl.textClick(v);
            }
        });
        textview7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyControl.textClick(v);
            }
        });
        imageview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(bean==null) return;
                Intent intent=new Intent(StorageSp5kActivity.this,StorageSet2Spf5kActivity.class);
                Bundle bundle=new Bundle();
                StorageParamsBean.StorageDetailBean detailBean = bean.getStorageDetailBean();
                bundle.putString("serialNum", detailBean.getSerialNum());
                bundle.putString("ppv", detailBean.getPpv() + "");
                if(detailBean.getAlias().equals("")){
                    bundle.putString("alias", detailBean.getSerialNum());
                }else{
                    bundle.putString("alias",detailBean.getAlias());
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        imageview2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(bean==null) return;
                Intent intent=new Intent(StorageSp5kActivity.this,StorageDeviceDataSp5kActivity.class);
                Bundle bundle=new Bundle();
                StorageParamsBean.StorageDetailBean detailBean = bean.getStorageDetailBean();
                StorageParamsBean.StorageBean storageBean = bean.getStorageBean();
                bundle.putString("capacity", detailBean.getCapacity() + "");
                bundle.putString("vBat", detailBean.getvBat() + "");
                bundle.putString("normalPower", detailBean.getNormalPower() + "");
                bundle.putString("serialNum", plant);
                bundle.putString("datalogSn", datalogSn);
                bundle.putString("modelText", storageBean.getModelText());
                bundle.putString("fwVersion", storageBean.getFwVersion());
                bundle.putString("StorageType", bean.getStorageType() + "");
                bundle.putString("vac", detailBean.getVac() + "");
                bundle.putString("activePower", bean.getActivePower() + "");
                bundle.putString("apparentPower", bean.getApparentPower() + "");
                if(detailBean.getAlias().equals("")){
                    bundle.putString("alias", detailBean.getSerialNum());
                }else{
                    bundle.putString("alias",detailBean.getAlias());
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        imageview3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent(StorageSp5kActivity.this,RizhiActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("plant", plant);
                bundle.putString("type", "storage");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        imageview4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent=new Intent(StorageSp5kActivity.this,StoragedpsSpf5kActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("id", plant);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Mydialog.Show(this,"");
        GetUtil.get(new Urlsutil().getStorageParams+"&storageId="+plant, new GetListener() {
            @Override
            public void success(String json) {
                Mydialog.Dismiss();
                try{
                if(json.length()>30){
                    bean = new Gson().fromJson(json,StorageParamsBean.class);
                    final StorageParamsBean.StorageDetailBean detailBean = bean.getStorageDetailBean();
                    tvIncomeToday.setText(bean.getTodayRevenue());
                    tvIncomeTotal.setText(bean.getTotalRevenue());
//                    String edischarge=bean.geteDischargeToday();
//                    String capacity=bean.geteDischargeTotal();
                    double eToday = detailBean.geteDischargeToday();
                    double eTotal = detailBean.geteDischargeTotal();
//                    double d=Double.parseDouble(edischarge);
                    if(eToday>=10000){
                        eToday=divide(eToday, 1000, 2);
//						System.out.println("a="+d);
                        textview2.setText(eToday+"MWh");
                    }else{
                        textview2.setText(eToday+"kWh");
                    }
//                    d=Double.parseDouble(capacity);
                    if(eTotal>=10000){
                        eTotal=divide(eTotal, 1000, 2);
//						System.out.println("a="+d);
                        textview4.setText(eTotal+"MWh");
                    }else{
                        textview4.setText(eTotal+"kWh");
                    }
//                    float s=Float.parseFloat(bean.getCapacity());
                    float f=detailBean.getCapacity()/100f;
//                    String status=bean.getStorageBean2().getStatus();
//                    int a=Integer.parseInt(bean.getCapacity());
                    String status = bean.getStorageBean().getStatus() + "";
                    if(status.equals("0")){
                        textview9.setText(R.string.storage_leave);
                        waveview.setWaveColor(Color.parseColor("#2de2e9"), Color.parseColor("#2de2e9"));
                    }else if(status.equals("1")){
                        textview9.setText(R.string.storage_electricize);
//                        textview10.setText(bean.getpChargeText());
                        textview10.setText(detailBean.getpChargeText());
                        waveview.setWaveColor(Color.parseColor("#79e681"), Color.parseColor("#79e681"));
                    }else if(status.equals("2")){
                        textview9.setText(R.string.storage_discharge);
//                        textview10.setText(bean.getpDischargeText());
                        textview10.setText(detailBean.getpDischargeText());
                        waveview.setWaveColor(Color.parseColor("#ded35b"), Color.parseColor("#ded35b"));
                    }else if(status.equals("3")){
                        textview9.setText(R.string.storage_fault);
                        textview10.setText("0 W");
                        waveview.setWaveColor(Color.parseColor("#ff5652"), Color.parseColor("#ff5652"));
                    }else if(status.equals("4")){
                        textview9.setText(R.string.storage_electricity);
                        textview10.setText("0 W");
                        waveview.setWaveColor(Color.parseColor("#a3a3a3"), Color.parseColor("#a3a3a3"));
                    }else {
                        textview9.setText(R.string.storage_electricity);
                        textview10.setText("0 W");
//						waveview.setWaveColor(Color.parseColor("#c3c3c3"), Color.parseColor("#c3c3c3"));
                    }
//					if(a<=15 ||status.equals("3")){
////						waveview.setWaveColor(Color.parseColor("#ff0000"), Color.parseColor("#ff0000"));
//						textview9.setTextColor(Color.parseColor("#ff0000"));
//					}else{
//						textview9.setTextColor(Color.parseColor("#ffffff"));
//					}
                    if(!status.equals("-1")){
                        waveHelper=new WaveHelper(waveview,f,3000);
                        waveHelper.start();
                        new Thread(){
                            public void run() {
                                int a=detailBean.getCapacity();
                                if(a!=0){
                                    int b=3000/a;
                                    for (int i = 0; i <=a; i++) {
                                        try {
                                            Message msg=new Message();
                                            msg.what=1;
                                            msg.obj=i+"";
                                            handler.sendMessage(msg);
                                            Thread.sleep(b);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            };
                        }.start();
                    }else{
                        Message msg=new Message();
                        msg.what=2;
                        handler.sendMessage(msg);
                    }

                }else{
                    try {
                        JSONObject jsonObject=new JSONObject(json);
                        String msg=jsonObject.getString("msg");
                        if(msg.equals("502")){
                            toast(R.string.storage_none);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void error(String json) {
                Mydialog.Dismiss();
            }

        });
        GetUtil.get(url, new GetListener() {
            @Override
            public void error(String json) {

            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject2 = new JSONObject(json);
                    List<ArrayList<Entry>> dataList = new ArrayList<>();
                    dataList.add(new ArrayList<Entry>());
                    dataList = MyUtils.parseLineChart1Data(dataList,jsonObject2,1);
//                    MyUtils.setLineChartData(StorageSp5kActivity.this,lineChart,dataList,new int[]{R.color.lineChart_inv},new int[]{R.color.lineChart_invfill},1,R.color.highLightColor);
                    MyUtils.setLineChartData(StorageSp5kActivity.this,lineChart,dataList,new int[]{R.color.chart_green_click_line},new int[]{R.color.chart_green_normal_line},1,R.color.note_bg_white);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    textview7.setText(msg.obj+"%");
                    break;
                case 2:
                    textview7.setText(bean.getStorageDetailBean().getCapacity()+"%");
                    break;
            }
        };
    };
    @Override
    public void onStop() {
        if(waveHelper!=null){

            waveHelper.cancel();
        }
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        if(waveHelper!=null){
            waveHelper.cancel();
        }
        super.onDestroy();
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            sendBroadcast(frgIntent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
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
        return b1.divide(b2,scale).doubleValue();
    }
}
