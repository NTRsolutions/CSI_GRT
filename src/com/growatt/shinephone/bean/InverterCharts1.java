package com.growatt.shinephone.bean;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.graphics.Typeface;

import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.tool.AbstractDemoChart;

import org.achartengine.chart.PointStyle;
import org.achartengine.chart.XYChart;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

public class InverterCharts1 extends AbstractDemoChart{
	List<double[]> x;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return "";
	}
	public InverterCharts1(List<double[]> x){
		this.x=x;
	}
	@Override
	public XYChart execute(Context context, List<double[]> list, String title,String time,
			int XAxisMax,int XAxisMin,  int maxY, int nowTime,int color) {
		String[] titles = new String[] { title }; // ����
		int[] colors = new int[] { color };
		if(maxY==0){
			maxY=100;
		}
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT };
		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);//����
		//		AbstractDemoChart�еķ�������renderer.  
		int length = renderer.getSeriesRendererCount();  
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
		}
		//		0.5, 12.5, -10, 40
		setChartSettings(renderer,"", "", 0, nowTime, 0,  maxY + maxY / 10,	Color.WHITE, Color.WHITE);
			//����AbstractDemoChart�еķ�������ͼ���renderer����.  
		renderer.setXLabels(XAxisMax-XAxisMin);// X�����������
		renderer.setYLabels(10);// Y���
//		renderer.setShowGrid(true);//�Ƿ���ʾ���� 
		renderer.setShowGridX(true);
		renderer.setGridColor(Color.DKGRAY);
		renderer.setXLabelsPadding(0);
		renderer.setMargins(new int[]{0,0,-60,0});//��,��,��,��
		renderer.setXLabelsAlign(Align.CENTER);//�̶�����̶ȱ�ע֮������λ�ù�ϵ  
		renderer.setYLabelsAlign(Align.CENTER);//�̶�����̶ȱ�ע֮������λ�ù�ϵ  
		renderer.setZoomButtonsVisible(true);//�Ƿ���ʾ�Ŵ���С��ť  
		renderer.setApplyBackgroundColor(true);// ������ɫ��Ч
		renderer.setTextTypeface("sans_serif", Typeface.BOLD);
		renderer.setYLabelsAlign(Align.LEFT);
		renderer.setBackgroundColor(Color.parseColor("#3ec6f8"));// ������ɫ
		renderer.setMarginsColor(Color.parseColor("#3ec6f8"));// �߿������ɫ
		renderer.setAxesColor(Color.WHITE);// x����ɫ
		renderer.setZoomEnabled(true);
		renderer.setXLabelsColor(Color.WHITE);  // x���Ͽ̶���ɫ
		renderer.setYLabelsColor(0,Color.WHITE);  // y���Ͽ̶���ɫ
		 renderer.setLabelsTextSize(ShineApplication.density*8);// ������̶����ֵĴ�С
	        renderer.setLegendTextSize(14);// ����ͼ�����ִ�С
		renderer.setXAxisMin(XAxisMin);
		renderer.setXAxisMax(XAxisMax);
		renderer.setZoomRate(1.0f);
		renderer.setBarSpacing(1.0f);
		renderer.setPanLimits(new double[] {4, 20, 0,  maxY + maxY / 10});// ��������̶�
		renderer.setZoomButtonsVisible(false);
		renderer.setPanEnabled(false, false);
		renderer.setZoomEnabled(true,false);
		renderer.setZoomLimits(new double[] {4,20, 0,  maxY + maxY / 10});
		length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
			seriesRenderer.setFillBelowLine(i == length - 1);
			seriesRenderer.setFillBelowLineColor(Color.parseColor("#6078e9fb"));//�ı��ڲ���ɫ
			seriesRenderer.setLineWidth(3.5f);
		}
		if (list != null && list.size() > 0) {
			org.achartengine.chart.XYChart chart = com.growatt.shinephone.tool.ChartFactory.getCubicLineChartIntent(context, buildDataset(titles, x,list), renderer, 0f, "ͼ��");
			return chart;
		}

		return null;
	}

}
