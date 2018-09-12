package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.UpdateplantUtil;
import com.growatt.shinephone.util.UpdateplantUtil.GetplantListener;

public class CapitalActivity extends DoActivity {
	private TextView tv2;
	private TextView tv4;
	private TextView tv6;
	private TextView tv8;
	private RelativeLayout r1;
	private RelativeLayout r2;
	private RelativeLayout r3;
	private RelativeLayout r4;
	String[] moneyTypes = {"RMB","USD","EUR","AUD","JPY","GBP"};
	private TextView tv10;
	private RelativeLayout r5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capital);
		initHeaderView();
		SetViews();
		SetListeners();
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
		setHeaderTitle(headerView,getString(R.string.capital_title));
	}
	private void SetViews() {
		tv2=(TextView)findViewById(R.id.textView2);
		tv4=(TextView)findViewById(R.id.textView4);
		tv6=(TextView)findViewById(R.id.textView6);
		tv8=(TextView)findViewById(R.id.textView8);
		tv10=(TextView)findViewById(R.id.textView10);
		r1=(RelativeLayout)findViewById(R.id.power_r1);
		r2=(RelativeLayout)findViewById(R.id.power_r2);
		r3=(RelativeLayout)findViewById(R.id.power_r3);
		r4=(RelativeLayout)findViewById(R.id.power_r4);
		r5=(RelativeLayout)findViewById(R.id.power_r5);

		if(PlantAdminActivity.powerdata!=null){
		tv2.setText(getDecimal(PlantAdminActivity.powerdata.getFormulaMoney()));
		tv4.setText(getDecimal(PlantAdminActivity.powerdata.getFormulaCoal()));
		tv6.setText(getDecimal(PlantAdminActivity.powerdata.getFormulaCo2()));
		tv8.setText(getDecimal(PlantAdminActivity.powerdata.getFormulaSo2()));
		if(PlantAdminActivity.powerdata.getFormulaMoneyUnit()!=null){
			tv10.setText(PlantAdminActivity.powerdata.getFormulaMoneyUnit());
		}
		}
	}
	  public String getDecimal(String a){
		  if(TextUtils.isEmpty(a)) return "0.00";
		  if(a.contains(",")){
			  a.replace("", ".");
		  }
		  int b=(int)((Double.parseDouble(a))*100+0.5);
		 return b/100.0+"";
	  }
	private void SetListeners() {
		r1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				UpdateplantUtil.showupdate(CapitalActivity.this,R.string.powerstation_change_income, R.string.powerstation_change_income_messafe,
						tv2.getText().toString(),new GetplantListener() {

					private String s;

					@Override
					public void put(EditText et) {
						s=et.getText().toString();
						PlantAdminActivity.powerdata.setFormulaMoney(s);
					}

					@Override
					public void ok() {
						tv2.setText(s);
					}

					@Override
					public void no() {

					}
				});
			}
		});
		r2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				UpdateplantUtil.showupdate(CapitalActivity.this,R.string.capital_economize_coal, R.string.powerstation_change_coal_quantity,
						tv4.getText().toString(),new GetplantListener() {

					private String s;

					@Override
					public void put(EditText et) {
						s=et.getText().toString();
						PlantAdminActivity.powerdata.setFormulaCoal(s);
					}

					@Override
					public void ok() {
						tv4.setText(s);
					}

					@Override
					public void no() {

					}
				});
			}
		});
		r3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				UpdateplantUtil.showupdate(CapitalActivity.this,R.string.powerstation_change_co2,R.string.powerstation_change_coal_quantity,
						tv6.getText().toString(),new GetplantListener() {

					private String s;

					@Override
					public void put(EditText et) {
						s=et.getText().toString();
						PlantAdminActivity.powerdata.setFormulaCo2(s);
					}

					@Override
					public void ok() {
						tv6.setText(s);
					}

					@Override
					public void no() {

					}
				});
			}
		});
		r4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				UpdateplantUtil.showupdate(CapitalActivity.this,R.string.powerstation_change_so2,R.string.powerstation_change_coal_quantity,
						tv8.getText().toString(),new GetplantListener() {

					private String s;

					@Override
					public void put(EditText et) {
						s=et.getText().toString();
						PlantAdminActivity.powerdata.setFormulaSo2(s);
					}

					@Override
					public void ok() {
						tv8.setText(s);
					}

					@Override
					public void no() {

					}
				});
			}
		});
		r5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new Builder(CapitalActivity.this);
				builder.setTitle(R.string.capital_monetaryunit).setItems(moneyTypes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int position) {
						dialog.dismiss();
						PlantAdminActivity.powerdata.setFormulaMoneyUnit(moneyTypes[position]);
						UpdateplantUtil.updateplant(CapitalActivity.this, handler);
						tv10.setText(moneyTypes[position]);
					}
				}).setNegativeButton(R.string.all_no,new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				}).create();
				builder.show();
			}
		});
	}
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Mydialog.Dismiss();
				String str=(String) msg.obj;
				if(str.equals("200")){
					toast(R.string.all_success);
				}else if(str.equals("501")){
					toast(R.string.geographydata_change_no_data);
				}else if(str.equals("502")){
					toast(R.string.geographydata_change_no_namerepetition);
				}else if(str.equals("503")){
					toast(R.string.geographydata_change_no_picture);
				}else if(str.equals("504")){
					toast(R.string.geographydata_change_no_map);
				}else if ("701".equals(str)){
					toast(R.string.m7);
				}
				break;

			default:
				break;
			}
		};
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
