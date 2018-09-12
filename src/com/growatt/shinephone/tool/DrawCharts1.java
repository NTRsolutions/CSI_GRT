package com.growatt.shinephone.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

import com.growatt.shinephone.activity.ShineApplication;

import org.achartengine.chart.PointStyle;
import org.achartengine.chart.XYChart;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * 曲线图
 * 
 * @author mike
 * 
 */
public class DrawCharts1 extends com.growatt.shinephone.tool.AbstractDemoChart {
	@SuppressLint("ResourceAsColor")
	public XYChart execute(final Context context, List<double[]> dataList, String title, String time, int maxTiem, int xLable, int maxY, int nowTime, int color) {
		String[] titles = new String[] { title }; // 标题
		List<double[]> x = new ArrayList<double[]>();

		for (int i = 0; i < titles.length; i++) {
			x.add(new double[] { 0.5, 1, 1.5, 2, 2.5, 3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5, 9, 9.5, 10, 10.5, 11, 11.5, 12, 12.5, 13, 13.5, 14, 14.5, 15, 15.5, 16, 16.5, 17, 17.5, 18, 18.5,
					19, 19.5, 20, 20.5, 21, 21.5, 22, 22.5, 23, 23.5 });
		}

		List<double[]> y = dataList;

		int[] colors = new int[] { color };
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT, PointStyle.POINT };
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
		}
		if(maxY==0){
			maxY=100;
		}
		setChartSettings(renderer, "", "", 4, nowTime + 10, 0,  maxY + maxY / 10, Color.WHITE, Color.WHITE);// 0.5是x坐标显示的最小值，12.5是x坐标显示的最大值
		renderer.setXLabels(23);// 设置x坐标刻度10是没2个距离一个刻度，没有规律，与显示最大值有关
		// nian 12 yue 30 ri 30 fen 20
		renderer.setYLabels(6);
		renderer.setShowGridX(true);
		renderer.setGridColor(Color.WHITE);
		renderer.setMargins(new int[]{0,0,-50,8});//上,左,下,右
		renderer.setApplyBackgroundColor(true);// 背景颜色生效
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setTextTypeface("sans_serif", Typeface.BOLD);
		renderer.setYLabelsAlign(Align.LEFT);
		renderer.setBackgroundColor(Color.parseColor("#3ec6f8"));// 背景颜色
		renderer.setMarginsColor(Color.parseColor("#3ec6f8"));// 边框外侧颜色
		renderer.setAxesColor(Color.WHITE);// x轴颜色
		renderer.setZoomEnabled(true);
		renderer.setXLabels(0);// 把数字过滤掉
		renderer.setXLabelsColor(Color.WHITE);  // x轴上刻度颜色
		renderer.setYLabelsColor(0, Color.WHITE);  // y轴上刻度颜色
		renderer.setXAxisMin(0);
		renderer.setXAxisMax(26);
		renderer.setLabelsTextSize(ShineApplication.density*8);
		renderer.addXTextLabel(2, "2");
		renderer.addXTextLabel(4, "4");
		renderer.addXTextLabel(6, "6");
		renderer.addXTextLabel(8, "8");
		renderer.addXTextLabel(10, "10");
		renderer.addXTextLabel(12, "12");
		renderer.addXTextLabel(14, "14");
		renderer.addXTextLabel(16, "16");
		renderer.addXTextLabel(18, "18");
		renderer.addXTextLabel(20, "20");
		renderer.addXTextLabel(22, "22");

		renderer.setZoomRate(1.0f);
		renderer.setBarSpacing(1.0f);
		renderer.setPanLimits(new double[] { 4, 20, 0,  maxY + maxY / 10 });// 横纵坐标刻度
		renderer.setZoomButtonsVisible(false);
		renderer.setPanEnabled(false, false);
		renderer.setZoomEnabled(true,false);
		renderer.setZoomLimits(new double[] {4,20, 0,  maxY + maxY / 10});
//		renderer.setClickEnabled(true); //是否可以点击  
//		renderer.setSelectableBuffer(5); //点击区域的大小 

				length = renderer.getSeriesRendererCount();
				for (int i = 0; i < length; i++) {
					XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
					seriesRenderer.setFillBelowLine(i == length - 1);
					seriesRenderer.setFillBelowLineColor(Color.parseColor("#6078e9fb"));
					seriesRenderer.setLineWidth(3.5f);
				}
		if (dataList != null && dataList.size() > 0) {
			org.achartengine.chart.XYChart chart = com.growatt.shinephone.tool.ChartFactory.getCubicLineChartIntent(context, buildDataset(titles, x, y), renderer, 0f, "图形");

			return chart;
		}
		return null;

	}


	public String getName() {
		return "abc";
	}

	public String getDesc() {
		return "asvdd";
	}

}
