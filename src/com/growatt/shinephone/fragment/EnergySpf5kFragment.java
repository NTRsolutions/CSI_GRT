package com.growatt.shinephone.fragment;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.EnergyDetial2Activity;
import com.growatt.shinephone.activity.EnergyDetialActivity;
import com.growatt.shinephone.bean.EnergyOverviewBean;
import com.growatt.shinephone.bean.SPFBarChartBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.util.v2.Fragment1Field;
import com.mylhyl.circledialog.CircleDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.growatt.shinephone.util.v2.Fragment1Field.barYList;


public class EnergySpf5kFragment extends BaseFragment  {
    //    enableGridDashedLine(float lineLength, float spaceLength, float phase): 显示网格线虚线模式，"lineLength"控制短线条的长度，"spaceLength"控制两段线之间的间隔长度，"phase"控制开始的点
    private TextView tvPvOut;
    private TextView tvStorageOut;
    private TextView tvHomeOut;
    private TextView tvGridOut;
    //能源概览属性
    private TextView tvPV;
    private TextView tvStorage;
    private TextView tvHome;
    private TextView tvGrid;
    //能源产耗属性
    private String dateToday = "2017-04-01";//今天日期
    private String dateType = "0";//请求数据类型
    private TextView tvEpv;
    private TextView tvUserEnergy;
    //饼图数据集合
    List<String> pieList1;
    List<String> pieList2;
    //4条折线图属性
    private final int lineChartCount = 4;//折线图数量
    private float lineMaxX ;
    private float lineMinX ;
    //一条折线图
    private LineChart lineChartSoc;
    private List<ArrayList<Entry>> socList;//电池百分比数据
    //两条柱状图
    private final int dayTamp = 24*60*60*1000;//一天的时间戳
    private BarChart barChart;
    private LineChart lineChart;
    List<ILineDataSet> dataSets;
    List<ArrayList<Entry>> dataList;
    List<ArrayList<Entry>> newDataList;
    int colorId;
    int xy_grid;
    int textClickColor;
    int[] colors = {R.color.pv_line,R.color.storage_line,R.color.home_line,R.color.grid_line};
    int[] colors_a = {R.color.pv_line_a,R.color.storage_line_a,R.color.home_line_a,R.color.grid_line_a};
    boolean showPv=true;
    boolean showStorage=true;
    boolean showHome=true;
    boolean ShowGrid=true;
    private PieChart pieChart1;
    private PieChart pieChart2;
    List<Integer> pieColors;
    List<Integer> pieColors2;
    Resources res;
    //柱状图数据
    List<IBarDataSet> barSetDatas;
    List<String> barLables;//柱状图标注集合
    List<Integer> barColors;//柱状图颜色集合
    List<Integer> barColorsHigh;//柱状图颜色点击
//    public static List<ArrayList<BarEntry>> barYList;
    private float barEnd;
    float groupSpace = 0.06f;
    float barSpace = 0.02f; // x2 dataset
    float barWidth = 0.45f; // x2 dataset
    // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
    int barGroupCount = 7;
    float barStart = 1.0f ;
    private String storageSn;
    private SwipeRefreshLayout swipeRefresh;//下拉刷新
    //需要隐藏的容器
    private LinearLayout barContainer;
//    private LinearLayout overViewBottomContainer;
    // 储能机设备类型，储能机类型(0：SP2000,1：SP3000,2：SPF5000)
    private String deviceType = "0";
    //广播接收器
    private MyReceiver receiver;
    private IntentFilter filter;
    private SimpleDateFormat sdf;
    private SimpleDateFormat sdfYMD;
    //日期相关属性
    private FrameLayout bt1;
    private FrameLayout bt2;
    private TextView txData;
    private LinearLayout llDate;
    private Calendar calendar;
    private Context context;
    private FrameLayout frameLayouDetial;//查看曲线详细数据
    private FrameLayout frameLayouDetial2;//查看曲线详细数据
    private ImageView ivEmpty1;
    private ImageView ivEmpty2;//饼图数据为零时显示的图片
    //新id spf5k
    private TextView tvGridTitle;
    private TextView tvHomeTitle;
    private TextView tvChargeTitle;
    private TextView tvCharge;
    //饼图数据控件
    private TextView tvPie1Value1;
    private TextView tvPie1Value2;
    private TextView tvPie2Value1;
    private TextView tvPie2Value2;
    //当日累计控件
    private TextView tvDateType;
    private LinearLayout llDateType;
    private String[] dateTypes;
    private LinearLayout llLineChartNote;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_energy_spf5k, container, false);
        context = getActivity();
        try {
            initColor();
            initView(view);
            initSwipeRefresh(view);
            initChart();
//            initLineChart(lineChart, 0);
//            initLineChart(lineChartSoc, 1);
            initPieChart(view);
//            initBarChart(barChart);
            initListener(view);
            refresh();
            registerBroadCast();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    private void initChart() {
        MyUtils.initLineChart(context,lineChart,0,"W",true,R.color.grid_bg_white,true,R.color.grid_bg_white,false,R.color.note_bg_white,R.color.grid_bg_white,R.color.grid_bg_white,R.color.highLightColor,true,R.string.m4,R.string.m5);
        MyUtils.initLineChart(context,lineChartSoc,1,"W",true,R.color.grid_bg_white,true,R.color.grid_bg_white,false,R.color.note_bg_white,R.color.grid_bg_white,R.color.grid_bg_white,R.color.highLightColor,true,R.string.m4,R.string.m5);
        MyUtils.initBarChartEnergy(context,barChart,"kWh",true,R.color.note_bg_white,R.color.grid_bg_white,R.color.grid_bg_white,7,true,R.color.grid_bg_white,true,R.color.grid_bg_white,R.color.highLightColor);

//        MyUtils.initLineChart(context,lineChart,0,"W",false,R.color.zhou_line,R.color.zhou_line,R.color.xy_grid,0);
//        MyUtils.initLineChart(context,lineChartSoc,1,"W",false,R.color.zhou_line,R.color.zhou_line,R.color.xy_grid,0);
//        MyUtils.initBarChart(context,barChart,"kWh",true,R.color.zhou_line,R.color.zhou_line,R.color.xy_grid,7);
    }
    public void initLineChart(){
        MyUtils.initLineChart(context,lineChart,0,"W",true,R.color.grid_bg_white,true,R.color.grid_bg_white,false,R.color.note_bg_white,R.color.grid_bg_white,R.color.grid_bg_white,R.color.highLightColor,true,R.string.m4,R.string.m5);
    }
    public void initBarChart(){
        MyUtils.initBarChart(context,barChart,"",true,R.color.note_bg_white,R.color.grid_bg_white,R.color.grid_bg_white,true,R.color.grid_bg_white,true,R.color.grid_bg_white,R.color.highLightColor);
    }
    private void initSwipeRefresh(View view) {
        swipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.headerView);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }
    public void showAllText(){
        if (!showPv){
            showPv = true;
            setTextWhiteBack(tvPvOut);
        }
        if (!showHome){
            showHome = true;
            setTextWhiteBack(tvHomeOut);
        }
        if (!showStorage){
            showStorage = true;
            setTextWhiteBack(tvStorageOut);
        }
        if (!ShowGrid){
            ShowGrid = true;
            setTextWhiteBack(tvGridOut);
        }
    }

    //从网络获取数据
    private void refresh() {
        storageSn = Fragment1Field.sn;
//        storageSn = "SPF5K00002";
        deviceType = Fragment1Field.deviceType;
        showAllText();
        if ("2".equals(deviceType)){//spf5k
            showContainerSpf5k();
        }else if ("1".equals(deviceType)){//spf3k
            showContainerSpf3k();
        }else {
            showContainerSpf();
        }
        //刷新能源概览
        refreshEnergyOverview();
        //刷新能源产耗根据时间
        refreshEnergyPro(dateToday,dateType);
        //刷新储能能源
        refreshStorageEnergy(dateToday);
    }

    private void showContainerSpf() {
        MyUtils.hideAllView(View.GONE,tvChargeTitle,tvCharge,barContainer,tvHomeTitle,tvGridTitle,tvHome,tvGrid);
    }

    private void showContainerSpf3k() {
        MyUtils.showAllView(barContainer,tvHomeTitle,tvGridTitle,tvHome,tvGrid);
        MyUtils.hideAllView(View.GONE,tvChargeTitle,tvCharge);
    }

    private void showContainerSpf5k() {
        MyUtils.showAllView(barContainer,tvHomeTitle,tvGridTitle,tvChargeTitle,tvHome,tvGrid,tvCharge);
    }

    private void refreshStorageEnergy(final String dateToday) {
        if (TextUtils.isEmpty(storageSn)) return;
        PostUtil.post(new Urlsutil().postStorageEnergyData, new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("plantId", Cons.plant);
                params.put("storageSn", storageSn);
                params.put("date", dateToday);
            }

            @Override
            public void success(String json) {
                parsetorageEnergy(json);
            }

            @Override
            public void LoginError(String str) {

            }
        });
    }

    private void parsetorageEnergy(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if ("1".equals(jsonObject.get("result").toString())){
                JSONObject obj = jsonObject.getJSONObject("obj");
                //电池百分比数据
                JSONObject socDataObj = obj.getJSONObject("socData");
                setSocData(socDataObj);
                //充放电数据
                JSONObject cdsDataObj = obj.getJSONObject("cdsData");
                setCdsData(cdsDataObj);

            }else {
                // TODO 数据异常处理
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //充放电数据
    private void setCdsData(JSONObject cdsDataObj) throws JSONException {
        //四条曲线图处理.数据源清空
        if (barYList == null){
            barYList = new ArrayList<>();
        }else {
            barYList.clear();
        }
        for (int i=0;i<2;i++){
            barYList.add(new ArrayList<BarEntry>());
        }
        //解析数据
        barYList = parseBarChart2Data(barYList,cdsDataObj);
        //设置图表
        setBarChart2Data(barChart,barYList,2);

    }

    private void setBarChart2Data(BarChart mChart, List<ArrayList<BarEntry>> barYList, int count) {
        LogUtil.i("barYList-->"+barYList);
        if (mChart == null || barYList == null) return;
        if (barSetDatas == null){
            barSetDatas = new ArrayList<>();
        }
        BarData barData = mChart.getBarData();
        if (barData != null && barData.getDataSetCount() >= count){
            for ( int i = 0;i<count;i++){
                BarDataSet dataSet = (BarDataSet) barData.getDataSetByIndex(i);
                dataSet.setValues(barYList.get(i));
            }
            mChart.getXAxis().setAxisMaximum(barStart + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * barGroupCount);
            mChart.groupBars(barStart,groupSpace,barSpace);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        }else {
            for (int i=0;i<count;i++){
                BarDataSet barSet = new BarDataSet(barYList.get(i),barLables.get(i));
                barSet.setColor(barColors.get(i));
                barSet.setDrawValues(false);
                barSet.setHighLightColor(barColorsHigh.get(i));
//                barSet.setValueTextColor(colorId);
                barSetDatas.add(barSet);
            }
            BarData data = new BarData(barSetDatas);
            mChart.setData(data);
            mChart.getBarData().setBarWidth(barWidth);
            mChart.getXAxis().setAxisMinimum(barStart);
//            mChart.getXAxis().setAxisMinimum(barEnd);
            mChart.getXAxis().setAxisMaximum(barStart + mChart.getBarData().getGroupWidth(groupSpace, barSpace) * barGroupCount);
            mChart.groupBars(barStart,groupSpace,barSpace);
        }
        mChart.animateY(3000);
        mChart.invalidate();
    }

    private List<ArrayList<BarEntry>> parseBarChart2Data(List<ArrayList<BarEntry>> dataList, JSONObject jsonObject) throws JSONException {
        if(jsonObject.length()>0){
            String date = jsonObject.getString("date");
            long nowTime = MyUtils.dataToTimetamps("yyyy-MM-dd",date) ;
            List<Map<Float,Object>> mList = new ArrayList<>();
            for (int i = 0;i< 2;i++){
                Map<Float, Object> data = new HashMap<>();
                mList.add(data);
            }
            Iterator it = jsonObject.keys();
            while (it.hasNext()){
                String strKey = String.valueOf(it.next().toString());
                if ("date".equals(strKey)) break;
                JSONObject value =  jsonObject.getJSONObject(strKey);
                //将昨天“1”转换成具体日期int
                float timeTamps = nowTime/dayTamp - Integer.parseInt(strKey)+1;
                mList.get(0).put(timeTamps, value.get("charge").toString());
                mList.get(1).put(timeTamps, value.get("disCharge").toString());
            }
            List< List<Map.Entry<Float, Object>>> infoIdList = new ArrayList<>();
            for (int i = 0;i< mList.size();i++){
                List<Map.Entry<Float, Object>> infoId = new ArrayList<>(mList.get(i).entrySet());
                Collections.sort(infoId, new Comparator<Map.Entry<Float, Object>>() {
                    @Override
                    public int compare(Map.Entry<Float, Object> o1, Map.Entry<Float, Object> o2) {
                        return o1.getKey().compareTo(o2.getKey());
                    }
                });
                infoIdList.add(infoId);
            }
            for (int j = 0;j< infoIdList.size();j++){
                List<Map.Entry<Float, Object>> infoId = infoIdList.get(j);
                List<BarEntry> entrys = dataList.get(j);
                int size = infoId.size();
                barGroupCount = size;
                for (int i = 0; i < size; i++) {
                    Map.Entry<Float, Object> id = infoId.get(i);
                    BarEntry entry = new BarEntry(id.getKey(),Float.valueOf(id.getValue().toString()),id.getKey());
                    entrys.add(entry);
                    if ( i == 0){
                        barStart = id.getKey();
                    }
                    if (i == size -1){
                        barEnd = id.getKey();

                    }
                }
            }
            return dataList;
        }else{
            return dataList;
        }
    }

    //电池百分比数据
    private void setSocData(JSONObject socDataObj) throws Exception {
        if (socList == null){
            socList = new ArrayList<>();
        }else {
            socList.clear();
        }
//        List<Entry> socList = new ArrayList<>();
        socList.add(new ArrayList<Entry>());
//        socList = parseLineChart1(socList,socDataObj);
        socList = MyUtils.parseLineChart1Data(socList,socDataObj,1);
        MyUtils.setLineChartData(context,lineChartSoc,socList,new int[]{R.color.chart_green_click_line},new int[]{R.color.chart_green_normal_line},1,R.color.note_bg_white);

//        MyUtils.setLineChartData(context,lineChartSoc,socList,new int[]{colors[2]},new int[]{colors[2]},1,R.color.highLightColor);
        //设置图表
//        setLineChart1Data(lineChartSoc,socList,1);

    }





    private void refreshEnergyPro(final String dateToday,final String dateType) {
        if ( !TextUtils.isEmpty(storageSn)){
            PostUtil.post(new Urlsutil().postEnergyProdAndConsData, new PostUtil.postListener() {
                @Override
                public void Params(Map<String, String> params) {
                    params.put("plantId", Cons.plant);
                    params.put("storageSn", storageSn);
                    params.put("date", dateToday);
                    params.put("type", dateType);
                }

                @Override
                public void success(String json) {
                    swipeRefresh.setRefreshing(false);
                    parseEnergyPro(json);
                }

                @Override
                public void LoginError(String str) {
                    swipeRefresh.setRefreshing(false);
                }
            });
        }
    }

    private void refreshEnergyOverview() {
        if ( !TextUtils.isEmpty(storageSn)){
            PostUtil.post(new Urlsutil().postEnergyOverviewData, new PostUtil.postListener() {
                @Override
                public void Params(Map<String, String> params) {
                    params.put("plantId", Cons.plant);
                    params.put("storageSn", storageSn);
                }

                @Override
                public void success(String json) {
                    swipeRefresh.setRefreshing(false);
                    parseEnergyOverview(json);
                }

                @Override
                public void LoginError(String str) {
                    swipeRefresh.setRefreshing(false);
                }
            });
        }
    }
    //解析能源概览
    private void parseEnergyOverview(String json) {
        LogUtil.i("能源概览："+json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            if ("1".equals(jsonObject.get("result").toString())){
                String newJson = jsonObject.get("obj").toString();
                Gson gson = new Gson();
                EnergyOverviewBean bean = gson.fromJson(newJson,EnergyOverviewBean.class);
                setEnergyOverviewUI(bean);
            }else {
                // TODO 数据异常处理
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //解析能源产耗
    private void parseEnergyPro(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if ("1".equals(jsonObject.get("result").toString())){
                JSONObject obj = jsonObject.getJSONObject("obj");
                Gson gson = new Gson();
                SPFBarChartBean bean = gson.fromJson(obj.toString(),SPFBarChartBean.class);
                if (bean == null){
                    bean = new SPFBarChartBean();
                }
                String tvEpvStr = bean.geteChargeTotal();
                String tvUserEnergyStr = bean.geteDisChargeTotal();
                tvEpv.setText(tvEpvStr+"kWh");
                tvUserEnergy.setText(tvUserEnergyStr+"kWh");
                //判断饼图是否有数据,没有则填充空白图
                if (Float.parseFloat(tvEpvStr) <= 0){
                    if (ivEmpty1.getVisibility() == View.INVISIBLE){
                        ivEmpty1.setVisibility(View.VISIBLE);
                    }
//                    Glide.with(context).load(R.drawable.yuan2).asBitmap().into(ivEmpty1);
                }else {
                    if (ivEmpty1.getVisibility() == View.VISIBLE){
                        ivEmpty1.setVisibility(View.INVISIBLE);
                    }
                }
                if (Float.parseFloat(tvUserEnergyStr) <= 0){
                    if (ivEmpty2.getVisibility() == View.INVISIBLE){
                        ivEmpty2.setVisibility(View.VISIBLE);
                    }
//                    Glide.with(context).load(R.drawable.yuan2).asBitmap().into(ivEmpty2);
                }else {
                    if (ivEmpty2.getVisibility() == View.VISIBLE){
                        ivEmpty2.setVisibility(View.INVISIBLE);
                    }
                }
                setEnergyProData(obj,bean);
            }else {
                // TODO 数据异常处理
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setEnergyProData(JSONObject obj,SPFBarChartBean bean) throws Exception {
        //饼图图片处理
        if (pieList1 == null){
            pieList1 = new ArrayList<>();
        }else {
            pieList1.clear();
        }
        if (pieList2 == null){
            pieList2 = new ArrayList<>();
        }else {
            pieList2.clear();
        }
        String pie1S1 = bean.geteCharge();
        String pie1S2 = bean.geteAcCharge();
        float pie1S0 = Float.parseFloat(bean.geteChargeTotal());

        float pie2S0 = Float.parseFloat(bean.geteDisChargeTotal());
        String pie2S1 = bean.geteDisCharge();
        String pie2S2 = bean.geteAcDisCharge();

        pieList1.add(pie1S1);
        pieList1.add(pie1S2);
        pieList2.add(pie2S1);
        pieList2.add(pie2S2);
        setPieData(pieChart1,pieList1,1);
        setPieData(pieChart2,pieList2,2);
        //饼图数据初始化
        tvPie1Value1.setText(pie1S1 + "/" + MyUtils.mathPercent(pie1S0,pie1S1));
        tvPie1Value2.setText(pie1S2 + "/" + MyUtils.mathPercent(pie1S0,pie1S2));
        tvPie2Value1.setText(pie2S1 + "/" + MyUtils.mathPercent(pie2S0,pie2S1));
        tvPie2Value2.setText(pie2S2 + "/" + MyUtils.mathPercent(pie2S0,pie2S2));
        //四条曲线图处理.数据源清空
        if (dataList == null){
            dataList = new ArrayList<>();
        }else {
            dataList.clear();
        }
        for (int i=0;i<lineChartCount;i++){
            dataList.add(new ArrayList<Entry>());
        }
        //解析数据
        if ("0".equals(dateType)) {
            JSONObject chartData = obj.getJSONObject("chartData");
//        dataList = parseLineChart4ByTime(dataList,chartData);
            dataList = MyUtils.parseLineChartData(dataList, chartData, 4);
            LogUtil.i("dataList-->" + dataList);
            newDataList = MyUtils.removeDataLineChart(dataList, 3);
            //设置图表
            MyUtils.setLineChartData(context, lineChart, newDataList, colors, colors_a, 4, R.color.highLightColor);
        }
//        setLineChart4Data(lineChart,dataList,4);
    }

    //设置能源概览UI
    private void setEnergyOverviewUI(EnergyOverviewBean bean) {
        if (bean != null){
            tvPV.setText(bean.getEpvToday()+"/"+bean.getEpvTotal());
            tvStorage.setText(bean.geteDischargeToday()+"/"+bean.geteDischargeTotal());
            if ("1".equals(deviceType)) {//sp3000
                tvGrid.setText(bean.geteToUserToday() + "/" + bean.geteToUserTotal());
                tvHome.setText(bean.getUseEnergyToday() + "/" + bean.getUseEnergyTotal());
            }else if ("2".equals(deviceType)){//sp5000
                tvGrid.setText(bean.geteToUserToday() + "/" + bean.geteToUserTotal());
                tvHome.setText(bean.getUseEnergyToday() + "/" + bean.getUseEnergyTotal());
                tvCharge.setText(bean.geteChargeToday() + "/" + bean.geteChargeTotal());
            }
        }
    }





    private void initColor() {
        res = getActivity().getResources();
        colorId =  ContextCompat.getColor(context,R.color.zhou_line);
        xy_grid = ContextCompat.getColor(context,R.color.xy_grid);
        textClickColor =  ContextCompat.getColor(context,R.color.grid_bg_white);
        pieColors = new ArrayList<>();
        pieColors.add(ContextCompat.getColor(context,R.color.storage_line));
        pieColors.add(ContextCompat.getColor(context,R.color.home_line));

        pieColors2 = new ArrayList<>();
        pieColors2.add(ContextCompat.getColor(context,R.color.pv_line));
        pieColors2.add(ContextCompat.getColor(context,R.color.grid_line));

    }

    private void initPieChart(View view) {
        pieChart1 = (PieChart) view.findViewById(R.id.pieChart1);
        pieChart2 = (PieChart) view.findViewById(R.id.pieChart2);
        MyUtils.initPieChart(pieChart1);
        MyUtils.initPieChart(pieChart2);
    }


    private void setPieData(PieChart mChart,List<String> pieList,int colorIndex) {
        if (mChart == null || pieList == null) return;
        ArrayList<PieEntry> entries = new ArrayList<>();
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0,size =pieList.size(); i < size; i++) {
            String value = pieList.get(i);
            entries.add(new PieEntry(Float.parseFloat(value),value));
        }
        PieDataSet dataSet;
        PieData pieData = mChart.getData();
        if (pieData != null && pieData.getDataSetCount()>0){
            dataSet = (PieDataSet) pieData.getDataSetByIndex(0);
            dataSet.setValues(entries);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        }else {
            dataSet = new PieDataSet(entries, "");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            if (colorIndex == 1){
                dataSet.setColors(pieColors);
            }else {
                dataSet.setColors(pieColors2);

            }
//            dataSet.setValueLinePart1OffsetPercentage(80.f);
//            dataSet.setValueLinePart1Length(0.2f);
////            dataSet.setValueLinePart2Length(0.7f);
//            dataSet.setValueLinePart2Length(1.1f);
//            dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//            dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
//            dataSet.setValueLineColor(colorId);
            PieData data = new PieData(dataSet);
            data.setDrawValues(false);
            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(colorId);
            mChart.setData(data);
            mChart.highlightValues(null);
        }
        mChart.animateX(3000);
        mChart.invalidate();
    }

    private void initListener(View view) {
        frameLayouDetial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EnergyDetialActivity.class);
                LogUtil.i("datalist:"+dataList);
                Bundle bundle = new Bundle();
                if (dataList != null && dataList.size()>0){
                    for (int i = 0,size = dataList.size();i<size;i++){
                        bundle.putParcelableArrayList("list"+(i+1), dataList.get(i));
                    }
                }
                intent.putExtras(bundle);
                jumpTo(intent,false);
            }
        });
        frameLayouDetial2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EnergyDetial2Activity.class);
                Bundle bundle = new Bundle();
                if (socList != null && socList.size()>0){
                    bundle.putParcelableArrayList("socList", socList.get(0));
                }
                bundle.putInt("type",2);
                intent.putExtras(bundle);
                jumpTo(intent,false);
//                if (barYList != null && barYList.size()>0){
//                    for (int i = 0,size = barYList.size();i<size;i++){
//                        ArrayList<ArrayList<Map<Float,Float>>> barlist = new ArrayList<>();
////                        bundle.putParcelableArrayList("barYList"+(i+1), barYList.get(i));
//                        List<BarEntry> entries = barYList.get(i);
//                        ArrayList<Map<Float,Float>> list = new ArrayList<>();
//                        for (int j = 0,pos = entries.size();j<pos;j++){
//                            HashMap<Float,Float> map = new HashMap<>();
//                            BarEntry entry = entries.get(j);
//                            map.put(entry.getX(),entry.getY());
//                            list.add(map);
//                        }
//                        barlist.add(list);
//                        bundle.putSerializable("barYList"+(i+1),barlist);
//                    }
//                }

            }
        });
        tvPvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showPv){
                    clearDataSetByIndex(lineChart,0);
                    showPv = false;
                    setTextGrayBack(v);
                }else {
                    replaceDataSet(lineChart,newDataList,0);
                    setTextWhiteBack(v);
                    showPv = true;
                }
            }
        });
        tvStorageOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showStorage){
                    clearDataSetByIndex(lineChart,1);
                    showStorage = false;
                    setTextGrayBack(v);
                }else {
                    replaceDataSet(lineChart,newDataList,1);
                    setTextWhiteBack(v);
                    showStorage = true;
                }
            }
        });
        tvHomeOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showHome){
                    clearDataSetByIndex(lineChart,2);
                    showHome = false;
                    setTextGrayBack(v);
                }else {
                    replaceDataSet(lineChart,newDataList,2);
                    setTextWhiteBack(v);
                    showHome = true;
                }
            }
        });
        tvGridOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShowGrid){
                    clearDataSetByIndex(lineChart,3);
                    ShowGrid = false;
                    setTextGrayBack(v);
                }else {
                    replaceDataSet(lineChart,newDataList,3);
                    setTextWhiteBack(v);
                    ShowGrid = true;
                }
            }
        });
        txData.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    calendar.setTime(sdfYMD.parse(dateToday));
                    new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            if (MyUtils.isFutureTime(getActivity(),1,year,month,dayOfMonth)){return;}
                            dateToday = new StringBuilder().append(year).append("-")
                                    .append((month + 1) < 10 ? "0" + (month + 1) : (month + 1))
                                    .append("-")
                                    .append((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth).toString();
                            txData.setText(dateToday);
                            refresh();
                        }
                    }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)){
                        @Override
                        protected void onStop() {
                        }
                    }.show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long tamps = MyUtils.dataToTimetamps("yyyy-MM-dd",dateToday);
                dateToday = MyControl.getFormatDate("yyyy-MM-dd",new Date(tamps - dayTamp));
                txData.setText(dateToday);
                refresh();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long tamps = MyUtils.dataToTimetamps("yyyy-MM-dd",dateToday);
                if (MyUtils.isFutureTime(getActivity(),tamps)) return;
                dateToday = MyControl.getFormatDate("yyyy-MM-dd",new Date(tamps + dayTamp));
                txData.setText(dateToday);
                refresh();
            }
        });
        llDateType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CircleDialog.Builder(getActivity())
                        .setItems(dateTypes, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (dateTypes[position].equals(tvDateType.getText().toString())) return;
                                tvDateType.setText(dateTypes[position]);
                                dateType = position + "";
                                refreshEnergyPro(dateToday,dateType);
                                switch (position){
                                    case 0:
                                        MyUtils.showAllView(lineChart,llLineChartNote);
                                        break;
                                    case 1:
                                        MyUtils.hideAllView(View.GONE,lineChart,llLineChartNote);
                                        break;
                                }
                                LogUtil.i("energyspf5k:" + position);
                            }
                        })
                        .setNegative(getString(R.string.all_no),null)
                        .show();
            }
        });
    }

    public void setTextWhiteBack(View view){
        ((TextView)view).setTextColor(ContextCompat.getColor(getActivity(),R.color.note_bg_white));
    }
    public void setTextGrayBack(View view){
        ((TextView)view).setTextColor(textClickColor);
    }
    private void initView(View view) {
        llLineChartNote = (LinearLayout)view.findViewById(R.id.llLineChartNote);
        //饼图数据控件初始化
        tvPie1Value1 = (TextView) view.findViewById(R.id.tvPie1Value1);
        tvPie1Value2 = (TextView) view.findViewById(R.id.tvPie1Value2);
        tvPie2Value1 = (TextView) view.findViewById(R.id.tvPie2Value1);
        tvPie2Value2 = (TextView) view.findViewById(R.id.tvPie2Value2);
        //四条曲线容器
        tvDateType = (TextView) view.findViewById(R.id.tvDateType);
        llDateType = (LinearLayout) view.findViewById(R.id.llDateType);
        dateTypes = new String[]{getString(R.string.m63),getString(R.string.m64)};

        ivEmpty1 = (ImageView) view.findViewById(R.id.ivEmpty1);
        ivEmpty2 = (ImageView) view.findViewById(R.id.ivEmpty2);
        frameLayouDetial = (FrameLayout) view.findViewById(R.id.frameLayouDetial);
        frameLayouDetial2 = (FrameLayout) view.findViewById(R.id.frameLayouDetial2);
        sdf = new SimpleDateFormat("MM.dd");
        sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance();
        lineChart = (LineChart) view.findViewById(R.id.lineChart);
        lineChartSoc = (LineChart) view.findViewById(R.id.lineChartSoc);
        barChart = (BarChart) view.findViewById(R.id.barChart);
        tvPvOut = (TextView) view.findViewById(R.id.tvPvOut);
        tvStorageOut = (TextView) view.findViewById(R.id.tvStorageOut);
        tvHomeOut = (TextView) view.findViewById(R.id.tvHomeOut);
        tvGridOut = (TextView) view.findViewById(R.id.tvGridOut);
        tvPV = (TextView) view.findViewById(R.id.tvPV);
        tvStorage = (TextView) view.findViewById(R.id.tvStorage);
        tvHome = (TextView) view.findViewById(R.id.tvHome);
        tvGrid = (TextView) view.findViewById(R.id.tvGrid);
        tvEpv = (TextView) view.findViewById(R.id.tvEpv);
        tvUserEnergy = (TextView) view.findViewById(R.id.tvUserEnergy);
        //新id
        tvGridTitle = (TextView) view.findViewById(R.id.tvGridTitle);
        tvHomeTitle = (TextView) view.findViewById(R.id.tvHomeTitle);
        tvChargeTitle = (TextView) view.findViewById(R.id.tvChargeTitle);
        tvCharge = (TextView) view.findViewById(R.id.tvCharge);

        //柱状图数据
        barLables = new ArrayList<>();
        barLables.add(res.getString(R.string.m68));
        barLables.add(res.getString(R.string.m62));
        barColors = new ArrayList<>();
        barColorsHigh = new ArrayList<>();
        barColors.add(ContextCompat.getColor(context,R.color.chart_green_normal));
        barColors.add(ContextCompat.getColor(context,R.color.bar_data2));
        barColorsHigh.add(ContextCompat.getColor(context,R.color.chart_green_click));
        barColorsHigh.add(ContextCompat.getColor(context,R.color.bar_data2_click));
//需要隐藏的数据容器
        barContainer = (LinearLayout) view.findViewById(R.id.barContainer);
//        overViewBottomContainer = (LinearLayout) view.findViewById(overViewBottomContainer);
        dateToday = MyControl.getFormatDate("yyyy-MM-dd",null);
        llDate = (LinearLayout) view.findViewById(R.id.llDate);
        bt1=(FrameLayout)view.findViewById(R.id.btnadvance);
        bt2=(FrameLayout)view.findViewById(R.id.btnback);
        txData=(TextView)view.findViewById(R.id.txData);
        txData.setText(dateToday);
        LogUtil.i(dateToday);
    }


    private void replaceDataSet(LineChart lineChart,List<ArrayList<Entry>> dataList,int index) {
        LineData data = lineChart.getData();
        if (data != null) {
            LineDataSet set = (LineDataSet) data.getDataSetByIndex(index);
            if (dataList.size() > index){
                set.setValues(dataList.get(index));
            }
            data.notifyDataChanged();
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        }
    }
    private void clearDataSetByIndex(LineChart lineChart,int index) {
        LineData data = lineChart.getData();
        if (data != null) {
            LineDataSet set = (LineDataSet) data.getDataSetByIndex(index);
            set.setValues(new ArrayList<Entry>());
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        }
    }

    private void registerBroadCast() {
        receiver=new MyReceiver();
        filter = new IntentFilter();
        filter.addAction(Constant.Energy_Receiver);
        filter.setPriority(1000);
        if(receiver!=null){
            getActivity().registerReceiver(receiver, filter);
        }
    }
    @Override
    public void onDestroyView() {
        if(receiver!=null){
            getActivity().unregisterReceiver(receiver);
        }
        super.onDestroyView();
    }
    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Constant.Energy_Receiver.equals(intent.getAction())){
                refresh();
            }
        }
    }

}
