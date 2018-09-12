package com.growatt.shinephone.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.SNAdapter;
import com.growatt.shinephone.util.AddCQ;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.view.Solve7PopupWindow;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@ContentView(R.layout.activity_add_plant)
public class AddPlantActivity extends DoActivity {

	private View headerView;
    EditText et1;

	Button btnOk;
//	private EditText et2;
	private TextView tv3;
	String[] timeZone;
//	private Spinner spinner;
	private TextView tv4;
	List<String> list;
	private PopupWindow popup;
	SNAdapter snAdapter;
	
	TextView tv2;
	SimpleDateFormat sdf;
//	String time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView();
		setHeaderView();
		setListener();
	}

	
	private void setView() {
		timeZone=new String[]{"-12","-11","-10","-9","-8","-7","-6","-5","-4","-3","-2",
			"-1","+1","+2","+3","+4","+5","+6","+7","+8","+9","+10","+11","+12"};
		et1=(EditText) findViewById(R.id.et1);
//        spinner=(Spinner) findViewById(R.id.spinner);
//        spinner.setAdapter(new AddPlantAdapter(this, list));
		
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        tv4.setText(Cons.userBean.getTimeZone());
        tv3.setText(Cons.userBean.getCounrty());
        list=Arrays.asList(timeZone);
//        et2=(EditText) findViewById(R.id.et2);
		btnOk=(Button) findViewById(R.id.btnOk);
		
		tv2=(TextView)findViewById(R.id.tv2);
		sdf=new SimpleDateFormat("yyyy-MM-dd");
		tv2.setText(sdf.format(System.currentTimeMillis()));
	}

	private void setHeaderView() {
		headerView=findViewById(R.id.headerView);
		setHeaderImage(headerView, R.drawable.back, Position.LEFT, new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		setHeaderTitle(headerView, getString(R.string.AddPlantActivity_add_plant));
		
	}
	public void getTimeZone(View v){
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.popop_plantnames, null);
		ListView lv=(ListView)contentView.findViewById(R.id.listView1);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				String timeZone=list.get(position);
				tv4.setText(timeZone);
				popup.dismiss();
			}
		});
		snAdapter=new SNAdapter(this, list);
		lv.setAdapter(snAdapter);
		popup = new Solve7PopupWindow(contentView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popup.setTouchable(true);
		popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popup.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return false;
				// �����������true�Ļ���touch�¼���������
				// ���غ� PopupWindow��onTouchEvent�������ã���������ⲿ�����޷�dismiss
			}
		});
		popup.setBackgroundDrawable(new ColorDrawable(0));
		// ���úò���֮����show
		popup.showAsDropDown(v);
	}
	
	public void getCalender(View v){
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.calender_item, null);
		CalendarView calendar=(CalendarView) contentView.findViewById(R.id.calendarView);
		calendar.setOnDateChangeListener(new OnDateChangeListener() {

			@Override
			public void onSelectedDayChange(CalendarView arg0, int arg1,
					int arg2, int arg3) {
				tv2.setText(sdf.format(arg0.getDate()));
			}
			
		});
		popup = new PopupWindow(contentView,LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		popup.setTouchable(true);
		popup.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return false;
				// �����������true�Ļ���touch�¼���������
				// ���غ� PopupWindow��onTouchEvent�������ã���������ⲿ�����޷�dismiss
			}
		});
		popup.setBackgroundDrawable(new ColorDrawable(0));
		// ���úò���֮����show
		popup.showAsDropDown(v);
	}
	private void setListener() {
		//����
          tv2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						getCalender(arg0);
					}
				});

		//����
		tv3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivityForResult(new Intent(AddPlantActivity.this,CountryActivity.class),102);
			}
		});
		//ʱ��
       tv4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getTimeZone(arg0);
			}
		});
       
		 final Handler handler=new Handler(){
				public void handleMessage(android.os.Message msg) {
					switch (msg.what) {
					case 0:
						Mydialog.Dismiss();
						String str=(String) msg.obj;
						if(str.equals("200")){
							toast(R.string.all_success);
							finish();
						}else if(str.equals("501")){
							toast(R.string.AddPlantActivity_add_plant_msg);
						}else if(str.equals("502")){
							toast(R.string.geographydata_change_no_namerepetition);
						}else if(str.equals("503")){
							toast(R.string.AddPlantActivity_add_plant_num);
						}else if(str.equals("504")){
							toast(R.string.AddPlantActivity_add_plant_country);
						}else if("701".equals(str)){
							toast(R.string.m7);
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
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(isEmpty(et1)) return;
				if(TextUtils.isEmpty(tv2.getText().toString())){
					toast(getString(R.string.putin_on_data));
					return;
				}
				if(TextUtils.isEmpty(tv3.getText().toString())){
					toast(getString(R.string.putin_on_data));
					return;
				}
				if(TextUtils.isEmpty(tv4.getText().toString())){
					toast(getString(R.string.putin_on_data));
					return;
				}
				Mydialog.Show(AddPlantActivity.this, "");
				 Map<String, Object> map=new HashMap<String, Object>();
				    map.put("plantName", et1.getText().toString());
				    map.put("plantDate", tv2.getText().toString());
				    map.put("plantCountry", tv3.getText().toString());
				    map.put("plantTimezone", tv4.getText().toString());
				    map.put("plantFirm", "0");
				    map.put("plantPower", "0");
				    map.put("plantCity", "0");
				    map.put("plantLng", "0");
				    map.put("plantLat", "0");
				    map.put("plantMoney", "rmb");
				    map.put("plantIncome", "1.2");
				    map.put("plantCoal", "0.4");
				    map.put("plantCo2", "0.997");
				    map.put("plantSo2", "1.5");
					AddCQ.postUp(new Urlsutil().postAddPlant, map, new GetListener() {
						
						@Override
						public void success(String str) {
							if(TextUtils.isEmpty(str)){
								handler.sendEmptyMessage(1);
								return;
							}
							try {
								JSONObject jsonObject=new JSONObject(str);
								Message msg=new Message();
								msg.what=0;
								msg.obj=jsonObject.get("msg").toString();
								handler.sendMessage(msg);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						
						@Override
						public void error(String json) {
							
						}
					});
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==102){
			if(resultCode==1){
				String result=data.getStringExtra("country");
				if(getString(R.string.Country_china).equals(result)){
					result="China";
				}
				if(!TextUtils.isEmpty(result)){
					Cons.regMap.setRegCountry(result);
					tv3.setText(result);
				}
			}
		}else{
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
