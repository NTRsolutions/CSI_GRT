package com.growatt.shinephone.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.growatt.shinephone.InformationCenterActivity;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.MaintenanceActivity;
import com.growatt.shinephone.activity.ProductsActivity;
import com.growatt.shinephone.activity.UserdataActivity;
import com.growatt.shinephone.adapter.Myadapter;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Urlsutil;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fragment3 extends BaseFragment{
	private View view;
	private TextView title;
	private ListView listview;
	private Myadapter adapter;
//	private int[]titles={R.string.fragment3_serve,R.string.fragment3_quality,
//			R.string.fragment3_maintenance,R.string.fragment3_product};
	private int[]titles={R.string.fragment3_serve,R.string.fragment3_information,
			R.string.fragment3_maintenance,R.string.fragment3_product};
//	private int[] images={R.drawable.client,R.drawable.zhibao,R.drawable.increment,R.drawable.product};
	private int[] images={R.drawable.client,R.drawable.information_center,R.drawable.increment,R.drawable.product};
	private ArrayList<Map<String, Object>> list;
	private List<Map<String, Object>> advlist;
	View headerView;
	private Banner banner;
	private List<String> bannerList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment3, container, false);
		SetViews();
		SetListeners();
		return view;
	}
	
	private void SetViews() {
		headerView=getActivity().getLayoutInflater().inflate(R.layout.header_fragment3, null);
		banner = (Banner) headerView.findViewById(R.id.banner);
		bannerList = new ArrayList<>();

		title=(TextView)view.findViewById(R.id.textView_title);
		listview=(ListView)view.findViewById(R.id.listView1);
		list=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object>map=new HashMap<String, Object>();
			String a=getResources().getString(titles[i]);
			map.put("title", a);
			map.put("image", images[i]);
			list.add(map);
		}

		adapter=new Myadapter(getActivity(), list);
		listview.addHeaderView(headerView);
		listview.setAdapter(adapter);
	}
	private void SetListeners() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				switch (position) {
				case 1:
					startActivity(new Intent(getActivity(),UserdataActivity.class));
					break;
//				case 1:
//			
//					MyAlertDialog.setDialogOne(getActivity(), R.string.fragment3_quality,R.string.Fragment3_zhibao);
//					//startActivity(new Intent(getActivity(),ZhibaoActivity.class));
//					break;
				case 2:
					startActivity(new Intent(getActivity(),InformationCenterActivity.class));
					break;
				case 3:
					startActivity(new Intent(getActivity(),MaintenanceActivity.class));
					break;
				case 4:
					startActivity(new Intent(getActivity(),ProductsActivity.class));
					break;
				}
			}
		});
		GetUtil.get(new Urlsutil().getAdvertisingList+AppUtils.getLocale(), new GetListener() {

			@Override
			public void success(String json) {
				if(json.length()>10){
					try {
						advlist=new ArrayList<Map<String,Object>>();
						JSONArray jsonArray=new JSONArray(json);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject=jsonArray.getJSONObject(i);
							String name=jsonObject.get("name").toString();
							bannerList.add(new Urlsutil().GetUrl()+"/"+name);
						}
						initBanner();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
				
				}
			}

			@Override
			public void error(String json) {
		
			}
		});
	}
    public void initBanner(){
		banner.setBackgroundColor(Color.parseColor("#00000000"));
		banner.setImages(bannerList);
		banner.setBannerAnimation(Transformer.DepthPage);
		banner.setDelayTime(5000);
		banner.setImageLoader(new ImageLoader() {
			@Override
			public void displayImage(Context context, Object path, ImageView imageView) {
				Glide.with(getActivity()).load(path).placeholder(R.drawable.loading).error(R.drawable.pic_service).into(imageView);
			}
		});
		banner.start();
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
