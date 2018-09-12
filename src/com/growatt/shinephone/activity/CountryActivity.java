package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.CountryAdapter;
import com.growatt.shinephone.bean.CitynameBean;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.PinYinUtil;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.view.MyLetterView;
import com.growatt.shinephone.view.MyLetterView.OnTouchLetterListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CountryActivity extends DoActivity {
	private ListView listview;
	private List<String> list;
	private List<String> list1;
	private CountryAdapter adapter;
	private EditText editText;
	private MyLetterView myLetterView;
	private ArrayList<CitynameBean> citynames;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geography_country);
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
		setHeaderTitle(headerView,getString(R.string.country_title));
		setHeaderTvTitle(headerView, getString(R.string.all_ok), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String str=editText.getText().toString();
				if(!TextUtils.isEmpty(str)){
					Intent intent=new Intent();
					intent.putExtra("country", str);
					setResult(1, intent);
					finish();
				}
			}
		});
	}
	private void SetViews() {
		editText=(EditText)findViewById(R.id.editText1);
		listview=(ListView)findViewById(R.id.listView1);
		myLetterView = (MyLetterView) findViewById(R.id.mlv_city_letters);
		citynames = new ArrayList<CitynameBean>();
		adapter = new CountryAdapter(CountryActivity.this, citynames);
		listview.setAdapter(adapter);
	}

	private void SetListeners() {
         myLetterView.setOnTouchLetterListener(new OnTouchLetterListener() {
			
			@Override
			public void onTouchLetter(String letter) {
			
					char section = letter.charAt(0);
					if(adapter!=null){
						listview.setSelection(adapter.getPositionForSection(section)+1);
						}
			}

			@Override
			public void onRelaseLetter() {
				// TODO Auto-generated method stub
				
			}
		});
		editText.addTextChangedListener(new TextWatcher() {


			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String str=s.toString();
				str=str.toLowerCase();
				System.out.println(str);
				list1 =new ArrayList<String>();
				if(list!=null){
				for (int i = 0; i <list.size(); i++) {
					String str1=list.get(i).toLowerCase();
					if(str1.indexOf(str)!=-1){
						list1.add(list.get(i));
					}
				}
				List<CitynameBean> newList = new ArrayList<CitynameBean>();
				//Log.d("TAG",list.toString());
				for (int i = 0; i <list1.size(); i++) {
					String cityname = list1.get(i);//��������
					CitynameBean bean = new CitynameBean();
					bean.setCityName(cityname);
					bean.setPyName(PinYinUtil.getPinYin(cityname));
					bean.setSortLetter(bean.getPyName().charAt(0));
					newList.add(bean);
				}
				adapter.addAll(newList, true);
			}
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				TextView tv=(TextView)view.findViewById(R.id.textView1);
				editText.setText(tv.getText().toString());
				editText.setSelection(tv.getText().toString().length());
				citynames.clear();
				adapter.notifyDataSetChanged();
				//直接跳转到上级界面
				String str=editText.getText().toString();
				if(!TextUtils.isEmpty(str)){
					Intent intent=new Intent();
					intent.putExtra("country", str);
					setResult(1, intent);
					finish();
				}
			}
		});
		Mydialog.Show(CountryActivity.this, "");
		SqliteUtil.time((System.currentTimeMillis()+500000)+"");
		GetUtil.get(new Urlsutil().getCountryCity, new GetListener() {

			@Override
			public void success(String json) {
				Mydialog.Dismiss();
				try {
					JSONArray jsonArray=new JSONArray(json);
					list=new ArrayList<String>();
					String[] strs = new String[jsonArray.length()];
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject=jsonArray.getJSONObject(i);
						strs[i]=jsonObject.get("country").toString();
					}
					Arrays.sort(strs,String.CASE_INSENSITIVE_ORDER);
					List<CitynameBean> newList = new ArrayList<CitynameBean>();
					for (int i = 0; i < strs.length-1; i++) {
						list.add(strs[i]);
						String cityname = strs[i];//��������
						CitynameBean bean = new CitynameBean();
						bean.setCityName(cityname);
						bean.setPyName(PinYinUtil.getPinYin(cityname));
						bean.setSortLetter(bean.getPyName().charAt(0));
						newList.add(bean);
					}
					//��citynamesҪ���ճ������Ƶ�ƴ�������ֵ�˳������
					Collections.sort(newList, new Comparator<CitynameBean>() {

						@Override
						public int compare(CitynameBean lhs, CitynameBean rhs) {
							return lhs.getPyName().compareTo(rhs.getPyName());
						}
					});
					//���й��ö�
					String nameZh=strs[strs.length-1];
					CitynameBean beanZh = new CitynameBean();
					beanZh.setCityName(nameZh);
					beanZh.setPyName(PinYinUtil.getPinYin(nameZh));
					beanZh.setSortLetter(beanZh.getPyName().charAt(0));
					if(nameZh.contains(getString(R.string.Country_china))){
						newList.add(0, beanZh);
						list.add(0, nameZh);
						
					}else{
						newList.add(beanZh);
						list.add(nameZh);
					}
					
					adapter.addAll(newList, true);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void error(String json) {

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
}
