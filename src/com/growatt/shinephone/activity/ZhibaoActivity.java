package com.growatt.shinephone.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.FragspinnerAdapter;
import com.growatt.shinephone.adapter.ZhibaoAdapter;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.ui.MyListview;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.PostUtil.postListener;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ZhibaoActivity extends DoActivity {

	private ImageView back;
	private MyListview listView;
	private List<Map<String, Object>>list;
	private ZhibaoAdapter adapter;
	private FragspinnerAdapter spadapter;
	private int positions=0;
	private String plant;
	private TextView title;
	private PopupWindow popup;
	private View imageView1;
	private ImageView emptyView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhibao);
		SetViews();
		SetListeners();

	}

	private void SetViews() {
		back=(ImageView)findViewById(R.id.back);
		listView=(MyListview)findViewById(R.id.gridView1);
		emptyView=(ImageView)findViewById(R.id.emptyView);
		if(getLanguage()==0){
			emptyView.setImageResource(R.drawable.zhibao_emptyview);
		}else{
			emptyView.setImageResource(R.drawable.zhibao_emptyview_en);
		}
		title=(TextView)findViewById(R.id.title);
		imageView1=findViewById(R.id.imageView1);
		String p=SqliteUtil.inquiryplant();
		if(p.equals("")){
			Getplant(Cons.plants.get(0).get("plantId").toString());
			title.setText(Cons.plants.get(0).get("plantName").toString());
		}else{
				for (int i = 0; i < Cons.plants.size(); i++) {
					if(Cons.plants.get(i).get("plantId").toString().equals(p)){
						System.out.println(Cons.plants.get(i).get("plantName").toString());
						title.setText(Cons.plants.get(i).get("plantName").toString());
						positions=i;
						break;
					}
				}
				Getplant(p);
		}
			
	}
	public void Getplant(final String plant){
		Mydialog.Show(ZhibaoActivity.this, "");
		PostUtil.post(new Urlsutil().getQualityInformation, new postListener() {
			
			@Override
			public void success(String json) {
				try {
					JSONArray jsonArray=new JSONArray(json);
					list=new ArrayList<Map<String,Object>>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject=jsonArray.getJSONObject(i);
						Map<String, Object>map=new HashMap<String, Object>();
						map.put("model", jsonObject.get("model").toString());
						map.put("isHas", jsonObject.get("isHas").toString());
						map.put("deviceSN", jsonObject.get("deviceSN").toString());
						map.put("outTime", jsonObject.get("outTime").toString());
						map.put("maturityTime", jsonObject.get("maturityTime").toString());
						map.put("deviceType", jsonObject.get("deviceType").toString());
						list.add(map);
					}
					adapter=new ZhibaoAdapter(ZhibaoActivity.this, list);
					listView.setAdapter(adapter);
					listView.setEmptyView(emptyView);
					Mydialog.Dismiss();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void Params(Map<String, String> params) {
				params.put("plantId", plant);
				params.put("pageNum", "1");
				params.put("pageSize", "100");
				Locale locale = getResources().getConfiguration().locale;
				String language = locale.getLanguage().toLowerCase();
				int a;
				if(language.contains("cn")||language.contains("zh")){
					a=0;
				}else if(language.contains("en")){
					a=1;
					
				}else{
					a=3;
				}
			}

			@Override
			public void LoginError(String str) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	private void SetListeners() {
     title.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getplantname(arg0);
			}
		});
     imageView1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				getplantname(arg0);
			}
		});
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	public void getplantname(View v){
		View contentView = LayoutInflater.from(ZhibaoActivity.this).inflate(
				R.layout.popop_plantnames, null);
		ListView lv=(ListView)contentView.findViewById(R.id.listView1);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(positions!=position){
					positions=position;
					plant=Cons.plants.get(positions).get("plantId").toString();
					SqliteUtil.plant(plant);
					Getplant(plant);
					title.setText(Cons.plants.get(positions).get("plantName").toString());
					}
				popup.dismiss();
			}
		});
		spadapter=new FragspinnerAdapter(ZhibaoActivity.this, Cons.plants,false,0);
		lv.setAdapter(spadapter);
		popup = new PopupWindow(contentView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popup.setTouchable(true);
		popup.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return false;
				// �����������true�Ļ���touch�¼���������
				// ���غ� PopupWindow��onTouchEvent�������ã���������ⲿ�����޷�dismiss
			}
		});

		// ���������PopupWindow�ı����������ǵ���ⲿ������Back�����޷�dismiss����
		// �Ҿ���������API��һ��bug
		popup.setBackgroundDrawable(new ColorDrawable(0));
		// ���úò���֮����show
		popup.showAsDropDown(v);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
