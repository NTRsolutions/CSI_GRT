package com.growatt.shinephone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.growatt.shinephone.R;

public class Analysefragment extends Fragment{
	private View view;
	private ListView listview;
	private String[]strs1={};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.analyse_frag2, container, false);
		SetViews();
		SetListeners();
		return view;
	}

	private void SetViews() {
		listview=(ListView)view.findViewById(R.id.listView1);
		
	}

	private void SetListeners() {
		
	}
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if(this.getView()!=null){
			this.getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
		}
	}
}
