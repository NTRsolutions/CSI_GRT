package com.growatt.shinephone.tool;

import java.util.List;

import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.tool.ChartFactory;

import org.achartengine.chart.XYChart;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Log;

/**
 * ��״ͼ
 * 
 * @author mike
 * 
 */
public class DrawHistogram1 extends AbstractDemoChart {

	public String getName() {
		
		return null;
	}

	public String getDesc() {
		
		return null;
	}

	public XYChart execute(Context context, int type, List<double[]> list, String title, String time, int maxTime, int xLable, int maxY, int nowTime, int color) {
		String[] titles = new String[] { title };
		int[] colors = new int[] { color };
		
		XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
//		SimpleSeriesRenderer r = new SimpleSeriesRenderer();  
//        r.setColor(Color.parseColor("#786ee5c8"));//������״ͼ����ɫ  
//        renderer.addSeriesRenderer(r); 
		renderer.setMargins(new int[]{0,0,-50,8});//��,��,��,��
		if(maxY==0){
			maxY=100;
		}
		if (type == 2) {
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
			renderer.addXTextLabel(24, "24");
			renderer.addXTextLabel(26, "26");
			renderer.addXTextLabel(28, "28");
			renderer.addXTextLabel(30, "30");
			renderer.setXLabels(8);
			renderer.setXAxisMin(0);
			renderer.setXAxisMax(23);
			renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
			renderer.setYLabels(6);
			renderer.setPanLimits(new double[] { 1, maxTime + 2.0, 0, maxY + maxY / 10 });// ��������̶�
			renderer.setApplyBackgroundColor(true);// ������ɫ��Ч
			renderer.setBackgroundColor(Color.parseColor("#3ec6f8"));// ������ɫ
			renderer.setMarginsColor(Color.parseColor("#3ec6f8"));// �߿������ɫ
			renderer.setAxesColor(Color.WHITE);// x����ɫ
			renderer.setZoomButtonsVisible(false);
			renderer.setShowGrid(false);
			renderer.setDisplayChartValues(false);
			renderer.setXLabels(0);// �����ֹ��˵�
			renderer.setXLabelsAlign(Align.CENTER);// �̶�����X����������������
			renderer.setYLabelsAlign(Align.LEFT);// Y����Y���������������
			renderer.setPanEnabled(false, false);
			renderer.setZoomEnabled(true,false);
			renderer.setZoomRate(1.0f);
			renderer.setBarSpacing(0.5f);
			
			renderer.setXLabelsColor(Color.WHITE);  
			renderer.setYLabelsColor(0, Color.WHITE);
			renderer.setLabelsTextSize(ShineApplication.density*8);
			renderer.setZoomLimits(new double[] { 1, maxTime + 2.0, 0, maxY + maxY / 10 });
			setChartSettings(renderer, "", "", 0, 32, 0, maxY + maxY / 10,Color.WHITE,Color.WHITE);
		} else if (type == 3) {
			renderer.addXTextLabel(2, "2");
			renderer.addXTextLabel(4, "4");
			renderer.addXTextLabel(6, "6");
			renderer.addXTextLabel(8, "8");
			renderer.addXTextLabel(10, "10");
			renderer.addXTextLabel(12, "12");
			renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
			renderer.setXLabels(13);
			renderer.setYLabels(5);
			renderer.setPanLimits(new double[] { 1, 13, 0, maxY + maxY / 10 });// ��������̶�
			renderer.setXAxisMin(0);
			renderer.setXAxisMax(12);
			renderer.setApplyBackgroundColor(true);// ������ɫ��Ч
			renderer.setBackgroundColor(Color.parseColor("#3ec6f8"));// ������ɫ
			renderer.setMarginsColor(Color.parseColor("#3ec6f8"));// �߿������ɫ
			renderer.setAxesColor(Color.WHITE);// x����ɫ
			renderer.setZoomButtonsVisible(false);
			renderer.setShowGrid(false);
			renderer.setDisplayChartValues(false);
			renderer.setXLabels(0);// �����ֹ��˵�
			renderer.setXLabelsAlign(Align.CENTER);// �̶�����X����������������
			renderer.setYLabelsAlign(Align.LEFT);// Y����Y���������������
			renderer.setPanEnabled(false, false);
			renderer.setZoomEnabled(true,false);
			renderer.setZoomRate(1.0f);
			renderer.setBarSpacing(0.5f);
			
			renderer.setXLabelsColor(Color.WHITE);  
			renderer.setYLabelsColor(0, Color.WHITE);
			renderer.setLabelsTextSize(ShineApplication.density*8);
			renderer.setZoomLimits(new double[] { 1, 13, 0, maxY + maxY / 10 });
			setChartSettings(renderer, "", "", 0, 13, 0, maxY + maxY / 10,Color.WHITE,Color.WHITE);
		} else if (type == 4) {
			int total = 0;
			int min = nowTime - 5;
			for(int i = min,j=1; i <= nowTime; j++,i++){
				total ++;
				renderer.addXTextLabel(j, ""+i);
			}
			renderer.getSeriesRendererAt(0).setDisplayChartValues(true);
			renderer.setYLabels(5);
			renderer.setXLabels(0);// �����ֹ��˵�
			renderer.setPanLimits(new double[] { 0, total+1, 0, maxY + maxY / 10 });// ��������̶�
			
			renderer.setApplyBackgroundColor(true);// ������ɫ��Ч
			renderer.setBackgroundColor(Color.parseColor("#3ec6f8"));// ������ɫ
			renderer.setMarginsColor(Color.parseColor("#3ec6f8"));// �߿������ɫ
			renderer.setAxesColor(Color.WHITE);// x����ɫ
			renderer.setZoomButtonsVisible(false);
			renderer.setShowGrid(false);
			renderer.setDisplayChartValues(false);
			renderer.setXLabels(0);// �����ֹ��˵�
			renderer.setXLabelsAlign(Align.CENTER);// �̶�����X����������������
			renderer.setYLabelsAlign(Align.LEFT);// Y����Y���������������
			renderer.setPanEnabled(false, false);
			renderer.setZoomEnabled(true,false);
			renderer.setZoomRate(1.0f);
			renderer.setBarSpacing(0.5f);
			
			renderer.setXLabelsColor(Color.WHITE);  
			renderer.setYLabelsColor(0,Color.WHITE);
			renderer.setLabelsTextSize(ShineApplication.density*8);
			renderer.setZoomLimits(new double[] { 0, total+1, 0, maxY + maxY / 10 });
			setChartSettings(renderer, "", "", 0, total+1, 0, maxY + maxY / 10,Color.WHITE,Color.WHITE);
		}
		if (list.size() == 0 ) {
			titles = new String[0];
		}
		return ChartFactory.getBarChartIntent(context, buildBarDataset(titles, list), renderer, Type.STACKED);
	}

	public XYChart execute(Context context, List<double[]> list, String title, String time, int maxTime, int xLable, int maxY, int nowTime, int color) {
		
		return null;
	}
}
