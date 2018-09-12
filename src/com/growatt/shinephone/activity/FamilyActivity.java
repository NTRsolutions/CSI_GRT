package com.growatt.shinephone.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.FamilyMyadapter;
import com.growatt.shinephone.util.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FamilyActivity  extends DemoBase{

	private PieChart mChart;
	private Typeface tf;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_family);
		initHeaderView();
		SetViews();
		SetListeners();
	}
	private View headerView;
	private void initHeaderView() {
		String demoStr = new StringBuilder().append("(").append(getString(R.string.示例)).append(")").toString();
		headerView = findViewById(R.id.headerView);
		setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		setHeaderTitle(headerView,getString(R.string.family_title) + demoStr);
	}
	private void SetViews() {
		list=new ArrayList<Map<String,Object>>();
		listview=(ListView)findViewById(R.id.listView1);
		mChart = (PieChart) findViewById(R.id.chart1);
		mChart.setUsePercentValues(true);
//		mChart.setHoleColorTransparent(true);
		mChart.setHighlightPerTapEnabled(true);
		mChart.setHoleRadius(0);  //实心圆
		tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

		mChart.getDescription().setEnabled(false);

		mChart.setDrawCenterText(true);

		mChart.setDrawHoleEnabled(true);

		mChart.setRotationAngle(0);
		// enable rotation of the chart by touch
		mChart.setRotationEnabled(true);

		// mChart.setUnit(" 鈧�");
		// mChart.setDrawUnitsInChart(true);

		// add a selection listener
		mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
			@Override
			public void onValueSelected(Entry e, Highlight h) {
				if (e == null)
					return;
			}

			@Override
			public void onNothingSelected() {

			}
		});
		// mChart.setTouchEnabled(false);
		mChart.setTransparentCircleRadius(0f);
		mChart.setHoleRadius(0f);
//		mChart.setCenterText("设 备");
//		mChart.setCenterTextColor(Color.parseColor("#ffffff"));

		setData(4, 100);

		mChart.animateXY(1500, 1500);
		// mChart.spin(2000, 0, 360);
		Legend l = mChart.getLegend();
//		l.setPosition(LegendPosition.BELOW_CHART_RIGHT);
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
		l.setXEntrySpace(7f);
		l.setYEntrySpace(5f);
	}

	private void SetListeners() {
		mChart.animateXY(1800, 1800);
	}
	private List<Map<String, Object>> list;
	private void setData(int count, float range) {

		float mult = range;

		ArrayList<PieEntry> yVals1 = new ArrayList<>();
		ArrayList<String> xVals = new ArrayList<String>();

		for (int i = 0; i < count + 1; i++) {
			xVals.add(getResources().getString(mParties[i % mParties.length]));
		}
		for (int i = 0; i < count + 1; i++) {
			yVals1.add(new PieEntry((float) (Math.random() * mult) + mult / 5, xVals.get(i)));
		}


		PieDataSet dataSet = new PieDataSet(yVals1, "");
		dataSet.setSliceSpace(3f);

		// add a lot of colors

		ArrayList<Integer> colors = new ArrayList<Integer>();

		for (int c : ColorTemplate.VORDIPLOM_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.JOYFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.COLORFUL_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.LIBERTY_COLORS)
			colors.add(c);

		for (int c : ColorTemplate.PASTEL_COLORS)
			colors.add(c);

		colors.add(ColorTemplate.getHoloBlue());

		dataSet.setColors(colors);
		PieData data = new PieData(dataSet);
		data.setValueFormatter(new PercentFormatter());
		data.setValueTextSize(11f);
		data.setValueTextColor(Color.parseColor("#ff666666"));
		data.setValueTypeface(tf);
		mChart.setData(data);
		//        mChart.setBackgroundColor(Color.parseColor("#ffffff"));
		// undo all highlights
		mChart.highlightValues(null);
		mChart.invalidate();
		float total=0;
		for(int i=0;i<5;i++){
			total=total+yVals1.get(i).getValue();
		}
		for (int i = 0; i <5; i++) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("title1", getResources().getString(mParties[i]));
			map.put("title2",getNumberFormat(yVals1.get(i).getValue()+"",1)+"W");
			if(total>0){
				map.put("title3",getNumberFormat(yVals1.get(i).getValue()*100/total+"",1)+"%");
			}else{
				map.put("title3","20%");
			}
			list.add(map);
		}
		FamilyMyadapter adapter = new FamilyMyadapter(FamilyActivity.this, list);
		listview.setAdapter(adapter);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
//	 public String getFormat(String s){
//			DecimalFormat df = new DecimalFormat("0.0");
//			double d=new Double(s);
//			String db = df.format(d);
//			return db;
//		}
}
