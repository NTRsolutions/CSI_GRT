package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.v2.MaintenanceV2Adapter;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaintenanceActivity extends DoActivity {

	private RecyclerView mRecyclerView;
	private  List<Map<String, String>> list;
	private BaseQuickAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintenance);
		initHeaderView();
		initHead();
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
		setHeaderTitle(headerView,getString(R.string.fragment3_maintenance));
	}
	private void initHead() {
		View header = findViewById(R.id.header);
		header.setBackgroundColor(ContextCompat.getColor(this,R.color.gray));
		setHeaderTitle(header, getString(R.string.fragment3_maintenance), Position.LEFT);
		setHeaderImage(header, R.drawable.maintenance_1);
	}
	private void SetViews() {
		mRecyclerView=(RecyclerView) findViewById(R.id.listView1);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		list=new ArrayList<>();
		mAdapter=new MaintenanceV2Adapter(R.layout.item_faqactivity_listview, list);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setEmptyView(R.layout.empty_view,(ViewGroup) mRecyclerView.getParent());
	}

	private void SetListeners() {
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Map<String, String> item = ((MaintenanceV2Adapter) adapter).getItem(position);
				Intent intent=new Intent(MaintenanceActivity.this,MaintenancedataActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("id", item.get("id").toString());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		Mydialog.Show(MaintenanceActivity.this, "");
		GetUtil.get(new Urlsutil().getExtensionList+AppUtils.getLocale(), new GetListener() {
			
			@Override
			public void success(String json) {
				try {
					Mydialog.Dismiss();
					
					if(json.length()<10){
						toast(R.string.maintenance_list_no);
						return;
					}
					JSONArray jsonArray=new JSONArray(json);
					ArrayList<Map<String, String>> newList = new ArrayList<Map<String, String>>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject=jsonArray.getJSONObject(i);
						Map<String, String>map=new HashMap<String, String>();
						map.put("title", jsonObject.get("title").toString());
						map.put("id", jsonObject.get("id").toString());
						map.put("phoneNum", jsonObject.get("phoneNum").toString());
						map.put("area", jsonObject.get("area").toString());
						map.put("price", jsonObject.get("price").toString());
						map.put("outline", jsonObject.get("outline").toString());
						map.put("recommend", jsonObject.get("recommend").toString());
						map.put("supplier", jsonObject.get("supplier").toString());
						newList.add(map);
					}
					mAdapter.setNewData(newList);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
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
