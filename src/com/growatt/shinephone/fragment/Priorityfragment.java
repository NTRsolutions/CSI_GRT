package com.growatt.shinephone.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.PriorityAdapter;
import com.growatt.shinephone.util.T;

public class Priorityfragment extends Fragment{
	private View view;
private int[]titles={R.string.priorit_item1,R.string.priorit_item2,R.string.priorit_item3
		,R.string.priorit_item4,R.string.priorit_item5,R.string.priorit_item6};
private int[]images={R.drawable.priority1,R.drawable.priority2,R.drawable.priority3,R.drawable.priority4,R.drawable.priority5,R.drawable.priority6};
private  List<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
private ListView listview;
private PriorityAdapter adapter;
@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.priority_frag2, container, false);
		SetViews();
		SetListeners();
		return view;
	}
	private void SetViews() {
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object>map=new HashMap<String, Object>();
			map.put("title", getResources().getString(titles[i]));
			map.put("image", images[i]);
			list.add(map);
		}
		listview=(ListView)view.findViewById(R.id.listview1);
		adapter=new PriorityAdapter(getActivity(), list);
		listview.setAdapter(adapter);
	}

	private void SetListeners() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				T.make(R.string.fragment2_loading,getActivity());
			}
		});
	}
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		// TODO Auto-generated method stub
		super.setMenuVisibility(menuVisible);
		if(this.getView()!=null){
			this.getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
		}
	}
}
