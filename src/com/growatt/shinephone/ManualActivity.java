package com.growatt.shinephone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.growatt.shinephone.activity.CommondataActivity;
import com.growatt.shinephone.activity.DoActivity;
import com.growatt.shinephone.adapter.v2.ManualV2Adapter;
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

public class ManualActivity extends DoActivity {

	private View headerView;
	private RecyclerView mRecyclerView;
	private List<Map<String, String>> mList;
	private BaseQuickAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual);
		initHeaderView();
		initListView();
		initData();
		initListener();
	}
	private void initData() {
		Mydialog.Show(this, "");
         GetUtil.get(new Urlsutil().getUsQuestionListByType+"&language="+getLanguage()+"&type=1", new GetListener() {
			@Override
			public void success(String json) {
				Mydialog.Dismiss();
				try {
					if(json.length()<15){
						toast(R.string.all_data_inexistence);
						return;
					}
					JSONObject jsonObj=new JSONObject(json);
					String result = jsonObj.opt("result").toString();
					if("1".equals(result)){
						Object opt = jsonObj.opt("obj");
						if(opt!=null){
							JSONArray jsonArray = jsonObj.getJSONArray("obj");
							List<Map<String, String>> newList = new ArrayList<Map<String, String>>();
							for(int i=0;i<jsonArray.length();i++){
								JSONObject jsonObj2 = jsonArray.getJSONObject(i);
								Map<String, String> map=new HashMap<String, String>();
								map.put("id", jsonObj2.get("id").toString());
								map.put("title", jsonObj2.get("title").toString());
								map.put("content", jsonObj2.get("content").toString());
								map.put("imgurl", jsonObj2.get("imgurl").toString());
								newList.add(map);
							}
							mAdapter.setNewData(newList);
						}
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void error(String json) {
				
			}
		});
	}
	private void initListView() {
		mRecyclerView=(RecyclerView) findViewById(R.id.listView);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mList=new ArrayList<Map<String, String>>();
		mAdapter=new ManualV2Adapter(R.layout.item_manualact_listview, mList);
		mRecyclerView.setAdapter(mAdapter);
		mAdapter.setEmptyView(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent());
	}

	private void initHeaderView() {
		headerView=findViewById(R.id.headerView);
		setHeaderImage(headerView, R.drawable.back, Position.LEFT, new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		setHeaderTitle(headerView, getString(R.string.InfoCenterAct_install));
	}
	private void initListener() {
		mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				Mydialog.Show(ManualActivity.this, "");
				Map<String, String> item = ((ManualV2Adapter) adapter).getItem(position);
				GetUtil.get(new Urlsutil().getUsualQuestionInfo+"&id="+item.get("id")+"&language="+getLanguage(), new GetListener() {

					@Override
					public void success(String json) {
						Mydialog.Dismiss();
						try {
							JSONObject jsonObject=new JSONObject(json);
							Intent intent=new Intent(ManualActivity.this,CommondataActivity.class);
							Bundle bundle=new Bundle();
							bundle.putString("id", jsonObject.get("id").toString());
							bundle.putString("title", jsonObject.get("title").toString());
							bundle.putString("content", jsonObject.get("content").toString());
							intent.putExtras(bundle);
							startActivity(intent);
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
}
