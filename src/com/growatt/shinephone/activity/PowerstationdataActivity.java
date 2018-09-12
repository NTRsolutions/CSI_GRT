package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PowerstationdataActivity extends DoActivity {

	private TextView tv2;
	private TextView tv4;
	private TextView tv6;
	private TextView tv8;
	private RelativeLayout r1;
	private RelativeLayout r2;
	private RelativeLayout r3;
	private RelativeLayout r4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_powerstationdata);
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
		setHeaderTitle(headerView,getString(R.string.plantadmin_install));
	}
	private void SetViews() {
		tv2=(TextView)findViewById(R.id.textView2);
		tv4=(TextView)findViewById(R.id.textView4);
		tv6=(TextView)findViewById(R.id.textView6);
		tv8=(TextView)findViewById(R.id.textView8);
		r1=(RelativeLayout)findViewById(R.id.power_r1);
		r2=(RelativeLayout)findViewById(R.id.power_r2);
		r3=(RelativeLayout)findViewById(R.id.power_r3);
		r4=(RelativeLayout)findViewById(R.id.power_r4);
		tv2.setText(PlantAdminActivity.powerdata.getPlantName());
		tv4.setText(PlantAdminActivity.powerdata.getCreateData());
		tv6.setText(PlantAdminActivity.powerdata.getDesignCompany());
		tv8.setText(PlantAdminActivity.powerdata.getNormalPower()+"w");
	}

	private void SetListeners() {
		r1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				UpdateplantUtil.showupdate(PowerstationdataActivity.this,R.string.powerstation_change_plantname,R.string.powerstation_plantname_legnt,
						tv2.getText().toString(),new GetplantListener() {

					private String s;

					@Override
					public void put(EditText et) {
						s=et.getText().toString();
						PlantAdminActivity.powerdata.setPlantName(s);
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
//				showupdate("�޸�", "���10����", PlantAdminActivity.powerdata.getPlantName());
				toast(R.string.powerstation_no_change);
			}
		});
		r3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				UpdateplantUtil.showupdate(PowerstationdataActivity.this,R.string.all_prompt,R.string.powerstation_change_manufacturer ,
						tv6.getText().toString(),new GetplantListener() {

					private String s;

					@Override
					public void put(EditText et) {
						s=et.getText().toString();
						PlantAdminActivity.powerdata.setDesignCompany(s);
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
				UpdateplantUtil.showupdate(PowerstationdataActivity.this,R.string.powerstation_change_power, R.string.powerstation_power_unit,
						"",new GetplantListener() {

					private String s;

					@Override
					public void put(EditText et) {
						s=et.getText().toString();
						String regEx="[^0-9]";   
						Pattern p = Pattern.compile(regEx);   
						Matcher m = p.matcher(s);   
						s=m.replaceAll("").trim();
						PlantAdminActivity.powerdata.setNormalPower(s);
					}

					@Override
					public void ok() {
						tv8.setText(s+"w");
					}

					@Override
					public void no() {

					}
				});
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	public void showupdate(String title,String msg,String et){
		AlertDialog.Builder builder = new Builder(PowerstationdataActivity.this);
		EditText editText=new EditText(PowerstationdataActivity.this);
		TimeZone tz = TimeZone.getDefault();  
		String s = tz.getDisplayName(false, TimeZone.SHORT); 
		editText.setText(et);
		editText.setSelection(et.length());
		builder.setTitle(title).setMessage(msg).setView(editText).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {

				arg0.dismiss();
				updateplant();
			}
		}).setNegativeButton(R.string.all_no, new  DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		}).create();
		builder.show();
	}
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
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
				}
				break;
			case 1:
				Mydialog.Dismiss();
				toast(R.string.geographydata_change_no_data);
				break;
			default:
				break;
			}
		};
	};
	public void updateplant(){
		UpdateplantUtil.updateplant(PowerstationdataActivity.this, handler);
	}
}
