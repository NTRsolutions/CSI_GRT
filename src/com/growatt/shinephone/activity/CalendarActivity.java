package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.growatt.shinephone.R;
import com.growatt.shinephone.ui.CalendarView;
import com.growatt.shinephone.ui.CalendarView.OnItemClickListener;
import com.growatt.shinephone.util.Position;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarActivity extends DoActivity {

	private Button bt1;
	private Button bt2;
	private CalendarView calendar;
	private String[] ya;
	private SimpleDateFormat format;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		initHeaderView();
		SetViews();
		Setlisteners();
	}
	private View headerView;
	private void initHeaderView() {
		headerView = findViewById(R.id.headerView);
		setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		setHeaderTitle(headerView,getString(R.string.calendar_amend));
	}
	private void SetViews() {
		format = new SimpleDateFormat("yyyy-MM-dd");
		bt1=(Button)findViewById(R.id.button1);
		bt2=(Button)findViewById(R.id.button2);
		calendar=(CalendarView)findViewById(R.id.calendar);
		calendar.setSelectMore(false);
		ya = calendar.getYearAndmonth().split("-");
//		Date date = format.parse("2015-01-01");
//		calendar.setCalendarData(date);
	}

	private void Setlisteners() {

		bt1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String leftYearAndmonth = calendar.clickLeftMonth(); 
				ya = leftYearAndmonth.split("-"); 
			}
		});
		
		bt2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String rightYearAndmonth = calendar.clickRightMonth();
				ya = rightYearAndmonth.split("-"); 
			}
		});
		calendar.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void OnItemClick(Date selectedStartDate, Date selectedEndDate,
					Date downDate) {
				 String date = format.format(downDate);
				 toast(date);
				 
			}
		});
	}

}
