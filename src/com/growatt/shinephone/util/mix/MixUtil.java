package com.growatt.shinephone.util.mix;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.mix.HtmlBean;
import com.growatt.shinephone.bean.mix.MixSetBean;
import com.growatt.shinephone.bean.mix.MixStatusBean;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.view.MyMarkerView;

import java.util.Map;

/**
 * Created：2017/11/20 on 14:48
 * Author:gaideng on dg
 * Description:
 */

public class MixUtil {
    //动画时间
    public static void clearAllAnim(View... views){
        if (views != null) {
            for (View v : views) {
                if (v != null) {
                    v.clearAnimation();
                }
            }
        }
    }
    /**
     * 设置html文本
     */
    public static Spanned toHtml(String html){
        Spanned result = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_COMPACT);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
    /**
     * 设置html文本模板:需要#
     */
    public static Spanned setHtml(HtmlBean bean){
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(bean.getTitle())){
            sb.append(bean.getTitle()).append(": ");
        }

        sb.append("<font color='")
                .append(bean.getColor())
                .append("'>")
                .append(bean.getValue())
                .append("</font>");
        if (!TextUtils.isEmpty(bean.getLast())){
            sb.append(bean.getLast());
        }
        return toHtml(sb.toString());
    }
    //Spf拼接弹框内容
    public static String  mixStorageDetial(MixStatusBean bean, Context context){
        StringBuilder sb = new StringBuilder();
        if (bean != null){
            sb.append(context.getString(R.string.m192第一路PV电压)).append(":").append(bean.getvPv1()).append("V").append("\n\n")
                    .append(context.getString(R.string.m193第一路PV功率)).append(":").append(bean.getpPv1()).append("W").append("\n\n")
                    .append(context.getString(R.string.m194第二路PV电压)).append(":").append(bean.getvPv2()).append("V").append("\n\n")
                    .append(context.getString(R.string.m195第二路PV功率)).append(":").append(bean.getpPv2()).append("W").append("\n\n")
                    .append(context.getString(R.string.m196电池电压)).append(":").append(bean.getvBat()).append("V").append("\n\n")
                    .append(context.getString(R.string.m197电网电压)).append(":").append(bean.getvAc1()).append("V").append("\n\n")
                    .append(context.getString(R.string.m198电网频率)).append(":").append(bean.getfAc()).append("Hz").append("\n\n")
                    .append(context.getString(R.string.m199EPS电压)).append(":").append(bean.getUpsVac1()).append("V").append("\n\n")
                    .append(context.getString(R.string.m200EPS频率)).append(":").append(bean.getUpsFac()).append("Hz").append("\n")
            ;
        }else {
            sb.append(context.getString(R.string.m192第一路PV电压)).append(":0.0V").append("\n\n")
                    .append(context.getString(R.string.m193第一路PV功率)).append(":0.0W").append("\n\n")
                    .append(context.getString(R.string.m194第二路PV电压)).append(":0.0V").append("\n\n")
                    .append(context.getString(R.string.m195第二路PV功率)).append(":0.0W").append("\n\n")
                    .append(context.getString(R.string.m196电池电压)).append(":0.0V").append("\n\n")
                    .append(context.getString(R.string.m197电网电压)).append(":0.0V").append("\n\n")
                    .append(context.getString(R.string.m198电网频率)).append(":0.0Hz").append("\n\n")
                    .append(context.getString(R.string.m199EPS电压)).append(":0.0V").append("\n\n")
                    .append(context.getString(R.string.m200EPS频率)).append(":0.0Hz").append("\n")
            ;
        }
        return sb.toString();
    }

    /**
     * 根据mix状态返回对应文本信息
     * @param status
     * @param context
     * @return
     */
    public static String getMixStatusText(int status,Context context) {
        String mixTitle = "";
        switch (status){
            case 0:mixTitle = context.getString(R.string.m201等待模式) ;break;
            case 1:mixTitle = context.getString(R.string.m202自检模式) ;break;
            case 2:mixTitle = context.getString(R.string.m203保留模式) ;break;
            case 3:mixTitle = context.getString(R.string.m204SysFault模式) ;break;
            case 4:mixTitle = context.getString(R.string.m205Flash模式) ;break;
            case 5:mixTitle = context.getString(R.string.m206并网工作中) ;break;
            case 6:mixTitle = context.getString(R.string.m206并网工作中) ;break;
            case 7:mixTitle = context.getString(R.string.m207离网工作中) ;break;
            case 8:mixTitle = context.getString(R.string.m207离网工作中) ;break;
            case 9:mixTitle = context.getString(R.string.all_Lost) ;break;
            default:mixTitle = context.getString(R.string.all_Lost) ;break;
        }
        return mixTitle;
    }
    /**
     * 根据mix状态返回对应文本信息
     * @param status
     * @param context
     * @return
     */
    public static String getMixStatusTextList(int status,Context context) {
        String mixTitle = "";
        switch (status){
            case 0:mixTitle = context.getString(R.string.m201等待模式) ;break;
            case 1:mixTitle = context.getString(R.string.m202自检模式) ;break;
            case 2:mixTitle = context.getString(R.string.m203保留模式) ;break;
            case 3:mixTitle = context.getString(R.string.all_Fault) ;break;
            case 4:mixTitle = context.getString(R.string.m205Flash模式) ;break;
            case 5:mixTitle = context.getString(R.string.all_Normal) ;break;
            case 6:mixTitle = context.getString(R.string.all_Normal) ;break;
            case 7:mixTitle = context.getString(R.string.all_Normal) ;break;
            case 8:mixTitle = context.getString(R.string.all_Normal) ;break;
            case 9:case -1:mixTitle = context.getString(R.string.all_Lost) ;break;
        }
        return mixTitle;
    }
    /**
     * mix功能设置
     * @param context
     * @param bean
     * @param handlerListener
     */
    public static void mixSet(Context context, final MixSetBean bean, final OnHandlerListener handlerListener) {
        Mydialog.Show(context);
        PostUtil.post(new Urlsutil().postMixSetApi, new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("serialNum", bean.getSerialNum());
                params.put("type", bean.getType());
                params.put("param1", bean.getParam1());
                int requestPos = bean.getRequestPos();
                if (requestPos == 3){
                    params.put("param2", bean.getParam2());
                }
                if (requestPos == 2) {
                    params.put("param16", bean.getParam16());
                }
                    if (requestPos == 1 || requestPos == 2) {
                        params.put("param2", bean.getParam2());
                        params.put("param3", bean.getParam3());
                        params.put("param4", bean.getParam4());
                        params.put("param5", bean.getParam5());
                        params.put("param6", bean.getParam6());
                        params.put("param7", bean.getParam7());
                        params.put("param8", bean.getParam8());
                        params.put("param9", bean.getParam9());
                        params.put("param10", bean.getParam10());
                        params.put("param11", bean.getParam11());
                        params.put("param12", bean.getParam12());
                        params.put("param13", bean.getParam13());
                        params.put("param14", bean.getParam14());
                        params.put("param15", bean.getParam15());
                }
            }
            @Override
            public void success(String json) {
                try {
                    handlerListener.handlerDeal(1,json);
                } catch (Exception e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {}
        });
    }

    /**
     * 初始化柱状图
     * @param context
     * @param mChart
     * @param unit
     * @param isTouchEnable
     * @param XYTextColorId
     * @param XAxisLineColorId
     * @param yGridLineColorId
     * @param labelCount
     */
    public static void initBarChartEnergy(Context context, BarChart mChart, final String unit, boolean isTouchEnable, int XYTextColorId, int XAxisLineColorId, int yGridLineColorId, int labelCount, boolean hasYAxis, int yAxisColor, boolean hasXGrid, int xGridColor, int heighLightColor) {
        //颜色转换
        int mXYTextColorId = ContextCompat.getColor(context,XYTextColorId);
        int mXAxisLineColorId = ContextCompat.getColor(context,XAxisLineColorId);
        int myGridLineColorId = ContextCompat.getColor(context,yGridLineColorId);
        int myYAxisColor = ContextCompat.getColor(context,yAxisColor);
        int myXGridColor = ContextCompat.getColor(context,xGridColor);
        mChart.getDescription().setEnabled(false);
        mChart.setTouchEnabled(isTouchEnable);
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
//        mChart.setDrawBorders(true);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);
        mChart.setDrawBarShadow(false);
//        mChart.setMaxVisibleValueCount(barGroupCount);
//        mChart.setDrawYValues(true); // 设置是否显示y轴的值的数据
//        mChart.setValuePaintColor(colorId);//设置表格中y轴的值的颜色，但是必须设置
        mChart.setDrawGridBackground(false);
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        if (isTouchEnable){
            MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view,heighLightColor);
            mv.setChartView(mChart); // For bounds control
            mChart.setMarker(mv); // Set the marker to the chart
        }
        mChart.animateY(1500);
        Legend l = mChart.getLegend();
        l.setEnabled(false);
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
////        l.setForm(Legend.LegendForm.CIRCLE);//这是左边显示小图标的形状
//        l.setDrawInside(true);
//        l.setYOffset(0f);
//        l.setXOffset(10f);
//        l.setYEntrySpace(0f);
//        l.setTextSize(context.getResources().getDimension(R.dimen.x3));
//        l.setTextColor(mXYTextColorId);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(hasXGrid);
        xAxis.setGridColor(myXGridColor);
        xAxis.setAxisLineWidth(1.0f);
        xAxis.setGridLineWidth(0.5f);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelCount);
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // 设置X轴的位置
        xAxis.setTextColor(mXYTextColorId);//设置x轴文本颜色
        xAxis.setAxisLineColor(mXAxisLineColorId);
        xAxis.setDrawLabels(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int)value + "";
            }
        });
        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setAxisMinValue(0.0f);//设置0值下面没有间隙
        leftAxis.setAxisMinimum(0.0f);//设置0值下面没有间隙
//        leftAxis.setValueFormatter(new LargeValueFormatter());
//        leftAxis.setValueFormatter(new DefaultAxisValueFormatter(1));
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (float)(Math.round(value*10))/10 + unit;
            }
        });
        leftAxis.setDrawAxisLine(hasYAxis);
        leftAxis.setAxisLineColor(myYAxisColor);
        leftAxis.setAxisLineWidth(1.0f);
        leftAxis.setGridLineWidth(0.5f);
//		leftAxis.enableGridDashedLine(10f,10f,0f);
        leftAxis.setTextColor(mXYTextColorId);
        leftAxis.setGridColor(myGridLineColorId);
//        leftAxis.setAxisLineColor(colorId);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
    }
}
