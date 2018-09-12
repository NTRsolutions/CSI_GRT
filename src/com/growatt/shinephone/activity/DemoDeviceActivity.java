package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Position;
import com.mylhyl.circledialog.CircleDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ContentView(R.layout.activity_demo_device)
public class DemoDeviceActivity extends DemoBase{
    @ViewInject(R.id.ivDemo)
    ImageView ivDemo;
    @ViewInject(R.id.ll_Demo)
    LinearLayout ll_Demo;
    @ViewInject(R.id.lineChart)
    LineChart lineChart;
    @ViewInject(R.id.tvDayPower)
    TextView tvDayPower;
    @ViewInject(R.id.tvDay)
    TextView tvDay;
    @ViewInject(R.id.tvTotal)
    TextView tvTotal;
    private int type = Constant.Device_Inv;
    private String demoStr;
    private int lan = 1;
    private List<ArrayList<Entry>> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
        initListener();
    }

    private void initListener() {
        ll_Demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder(DemoDeviceActivity.this)
                        .setWidth(0.7f)
                        .setTitle(getString(R.string.温馨提示))
                        .setText(getString(R.string.示范设备不能操作))
                        .setPositive(getString(R.string.all_ok), null)
                        .show();
            }
        });
    }

    private void initView() {
        tvDay.setText((new Random().nextInt(10)+40) + "kWh");
        tvTotal.setText((new Random().nextInt(100)+1900) + "kWh");
        if (type == Constant.Device_storage){
            tvDayPower.setText(R.string.storage_percent);
            MyUtils.initLineChart(this,lineChart,1,"",true,R.color.grid_bg_white,true,R.color.grid_bg_white,true,R.color.note_bg_white,R.color.grid_bg_white,R.color.grid_bg_white,R.color.highLightColor,true,R.string.m4,R.string.m5);
            initDatas(Constant.Device_storage);
        }else {
            tvDayPower.setText(R.string.inverter_unit);
            MyUtils.initLineChart(this,lineChart,0,"",true,R.color.grid_bg_white,true,R.color.grid_bg_white,true,R.color.note_bg_white,R.color.grid_bg_white,R.color.grid_bg_white,R.color.highLightColor,true,R.string.m4,R.string.m5);
            initDatas(Constant.Device_Inv);
        }
        MyUtils.setLineChartData(DemoDeviceActivity.this,lineChart,dataList,new int[]{R.color.chart_green_click_line},new int[]{R.color.chart_green_normal_line},1,R.color.note_bg_white);
    }

    private void initDatas(int type) {
        ArrayList<Entry> entries = new ArrayList<>();
        if (type == Constant.Device_storage){
            for (int i=0;i<25;i++){
                Entry entry = new Entry(i*30,new Random().nextInt(10)+90);
                entries.add(entry);
            }
        }else {
            for (int i=0;i<25;i++){
                Entry entry = new Entry(i*30,new Random().nextInt(300)+3700);
                entries.add(entry);
            }
        }
        dataList.add(entries);
    }

    private void initIntent() {
        type = getIntent().getIntExtra(Constant.Device_Type,Constant.Device_Inv);
        demoStr = new StringBuilder().append("(").append(getString(R.string.示例)).append(")").toString();
    }

    /**
     * 处理头部
     */
    private View headerView;
    private void initHeaderView() {
        lan = getLanguage();
        headerView = findViewById(R.id.headerView);
        setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    if (type == Constant.Device_Power){
            setHeaderTitle(headerView,getString(R.string.功率调节器) + demoStr);
            if (lan == 0){
                Glide.with(mContext).load(R.drawable.power_demo_cn).into(ivDemo);
            }else {
                Glide.with(mContext).load(R.drawable.power_demo_en).into(ivDemo);
            }
        }else if (type == Constant.Device_Charge){
            setHeaderTitle(headerView,getString(R.string.充电桩) + demoStr);
            if (lan == 0){
                Glide.with(mContext).load(R.drawable.charge_demo_cn).into(ivDemo);
            }else {
                Glide.with(mContext).load(R.drawable.charge_demo_en).into(ivDemo);
            }
        }else if (type == Constant.Device_storage){
        setHeaderTitle(headerView,getString(R.string.all_storage) + demoStr);
        if (lan == 0){
            Glide.with(mContext).load(R.drawable.storage_demo_cn).into(ivDemo);
        }else {
            Glide.with(mContext).load(R.drawable.storage_demo_en).into(ivDemo);
        }
    }else {
            setHeaderTitle(headerView,getString(R.string.all_interver) + demoStr);
            if (lan == 0){
                Glide.with(mContext).load(R.drawable.inverte_demo_cn).into(ivDemo);
            }else {
                Glide.with(mContext).load(R.drawable.inverte_demo_en).into(ivDemo);
            }
        }
    }


}
