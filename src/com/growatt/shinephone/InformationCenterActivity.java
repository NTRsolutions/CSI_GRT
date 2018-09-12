package com.growatt.shinephone;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.data.LineRadarDataSet;
import com.growatt.shinephone.activity.DoActivity;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.video.VideoCenterActivity;

public class InformationCenterActivity extends DoActivity {

	private LinearLayout videoll;
	private LinearLayout questionll;
	private LinearLayout install;
	private LinearLayout companyll;
	private View headerView;
	private LinearLayout videoll2;
	private LinearLayout questionll2;
	private LinearLayout install2;
	private LinearLayout companyll2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information_center);
		ShineApplication.getInstance().addActivity(this);
		initHeaderView();
		initView();
		initListener();
	}

	private void initHeaderView() {
		headerView=findViewById(R.id.headerView);
//		TextView tv = (TextView) headerView.findViewById(R.id.tvTitle);
//		headerView.setBackgroundColor(Color.WHITE);
//		tv.setTextColor(Color.BLACK);
		setHeaderTitle(headerView, getResources().getString(R.string.fragment3_information));
		setHeaderImage(headerView, R.drawable.back, Position.LEFT,new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void initListener() {
		videoll.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(InformationCenterActivity.this, VideoCenterActivity.class));
			}
		});
		questionll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(InformationCenterActivity.this, FAQActivity.class));
			}
		});
		companyll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(InformationCenterActivity.this, CompanyActivity.class));
			}
		});
        videoll2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(InformationCenterActivity.this, VideoCenterActivity.class));
			}
		});
		questionll2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(InformationCenterActivity.this, FAQActivity.class));
			}
		});
		companyll2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(InformationCenterActivity.this, CompanyActivity.class));
			}
		});
		install.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				jumpTo(ManualActivity.class, false);
			}
		});
		install2.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		jumpTo(ManualActivity.class, false);
	}
    });
	}

	private void initView() {
		videoll=(LinearLayout)findViewById(R.id.infoCenter_video);
		questionll=(LinearLayout)findViewById(R.id.infoCenter_question);
		install=(LinearLayout)findViewById(R.id.infoCenter_install);
		companyll=(LinearLayout)findViewById(R.id.infoCenter_company);
		
		videoll2=(LinearLayout)findViewById(R.id.infoCenter_video2);
		questionll2=(LinearLayout)findViewById(R.id.infoCenter_question2);
		install2=(LinearLayout)findViewById(R.id.infoCenter_install2);
		companyll2=(LinearLayout)findViewById(R.id.infoCenter_company2);
	}



}
