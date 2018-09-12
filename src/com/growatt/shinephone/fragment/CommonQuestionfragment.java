package com.growatt.shinephone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.CommondataActivity;
import com.growatt.shinephone.adapter.CommonAdapter;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonQuestionfragment extends Fragment{

	private View view;
	private ListView listview;
	private List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
	private CommonAdapter adapter;
	private SwipeRefreshLayout swipeRefreshLayout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.commonfragment, container, false);
		SetViews();
		SetListeners();
		return view;
	}
	private void SetViews() {
		listview=(ListView)view.findViewById(R.id.listView_common);
		adapter=new CommonAdapter(getActivity(), list);
		listview.setAdapter(adapter);
	}
	private void SetListeners() {
		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
		swipeRefreshLayout.setColorSchemeResources(R.color.headerView);
		refresh();
		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				refresh();
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				Mydialog.Show(getActivity(), "");
				GetUtil.get(new Urlsutil().getUsualQuestionInfo+"&id="+list.get(position).get("id")+"&language="+AppUtils.getLocale(), new GetListener() {
					
					@Override
					public void success(String json) {
						Mydialog.Dismiss();
						try {
							JSONObject jsonObject=new JSONObject(json);
							Intent intent=new Intent(getActivity(),CommondataActivity.class);
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
	public void refresh(){
		GetUtil.get(new Urlsutil().getUsualQuestionList+"&language="+AppUtils.getLocale(), new GetListener() {

			@Override
			public void success(String json) {
				if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){ swipeRefreshLayout.setRefreshing(false);}
				try {
					if(json.length()<15){
//						T.make(R.string.all_data_inexistence,getActivity());
						return;
					}
					JSONArray jsonArray=new JSONArray(json);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject=jsonArray.getJSONObject(i);
						Map<String, Object>map=new HashMap<String, Object>();
						map.put("id", jsonObject.get("id").toString());
						map.put("title", jsonObject.get("title").toString());
						map.put("content", jsonObject.get("content").toString());
						list.add(map);
					}
					adapter=new CommonAdapter(getActivity(), list);
					listview.setAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
					if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){ swipeRefreshLayout.setRefreshing(false);}
				}

			}

			@Override
			public void error(String json) {
				if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){ swipeRefreshLayout.setRefreshing(false);}
			}
		});
	}

	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if(this.getView()!=null){
			this.getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
		}
	}
}
