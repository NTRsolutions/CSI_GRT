package com.growatt.shinephone.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.FamilyActivity;
import com.growatt.shinephone.activity.PhotovoltaicActivity;
import com.growatt.shinephone.activity.PowerstationActivity;
import com.growatt.shinephone.adapter.AnalyseAdapter;
import com.growatt.shinephone.adapter.PriorityAdapter;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.T;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fragment2 extends BaseFragment{
	private View view;
	private int[]titles={R.string.fragment2_right_item1,R.string.fragment2_right_item2,
			R.string.fragment2_right_item3,R.string.fragment2_right_item4,
			R.string.fragment2_right_item5,R.string.fragment2_right_item6};
	private int[]images={R.drawable.priority1,R.drawable.priority2,R.drawable.priority3,R.drawable.priority4,R.drawable.priority5,R.drawable.priority6};
	private int[]titles1={R.string.fragment2_left_item3,R.string.fragment2_left_item4,R.string.fragment2_left_item1,R.string.fragment2_left_item2};
	private int[]images1={R.drawable.analyse3,R.drawable.dianliang,R.drawable.analyse1,R.drawable.analyse2};
	private  ArrayList<Map<String, Object>>list=new ArrayList<Map<String,Object>>();
	private  ArrayList<Map<String, Object>>list1=new ArrayList<Map<String,Object>>();
	private ListView listview;
	private RadioGroup radioGroup;
	private PriorityAdapter adapter;
	private TextView tv1;
	private TextView tv2;
	private MyReceiver receiver;
	private IntentFilter filter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment2, container, false); 
		SetViews();
		SetListeners();
		registerBroadCast();
		return view;
	}
	
	private void SetViews() {
		
		radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object>map=new HashMap<String, Object>();
			String a=getResources().getString(titles[i]);
			map.put("title",a );
			map.put("image", images[i]);
			list.add(map);
		}
		for (int i = 0; i < titles1.length; i++) {
			Map<String, Object>map=new HashMap<String, Object>();
			String a=getResources().getString(titles1[i]);
			map.put("title",a);
			map.put("image", images1[i]);
			map.put("day", "");
			map.put("mouth", "");
			list1.add(map);
		}
		tv1=(TextView)view.findViewById(R.id.textView1);
		tv2=(TextView)view.findViewById(R.id.textView2);
		tv1.setVisibility(View.VISIBLE);
		tv2.setVisibility(View.INVISIBLE);
		listview=(ListView)view.findViewById(R.id.listView1);
		adapter1=new AnalyseAdapter(getActivity(), list1);
		listview.setAdapter(adapter1);
	}
	int index = 0;
	private AnalyseAdapter adapter1;
	private void SetListeners() {
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_button1:
					index = 0;
					tv1.setVisibility(View.VISIBLE);
					tv2.setVisibility(View.INVISIBLE);
					adapter1=new AnalyseAdapter(getActivity(), list1);
					listview.setAdapter(adapter1);
					break;
				case R.id.radio_button2:
					index = 1;
					tv2.setVisibility(View.VISIBLE);
					tv1.setVisibility(View.INVISIBLE);
					adapter=new PriorityAdapter(getActivity(), list);
					listview.setAdapter(adapter);
					break;
				}
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			private Intent intent;

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Bundle bundle=new Bundle();
				if(index==0){
					switch (position) {
					case 0:
						intent=new Intent(getActivity(),PowerstationActivity.class);
						bundle.putString("title",getResources().getString(R.string.fragment2_left_item3));
						bundle.putInt("type", 1);
						intent.putExtras(bundle);
						startActivity(intent);
						break;
					case 1:
						intent=new Intent(getActivity(),PhotovoltaicActivity.class);
						bundle.putString("title",getResources().getString(R.string.fragment2_left_item4));
						intent.putExtras(bundle);
						startActivity(intent);
						break;
					case 2:
						intent=new Intent(getActivity(),PowerstationActivity.class);
						bundle.putString("title",getResources().getString(R.string.fragment2_left_item1));
						bundle.putInt("type", 2);
						intent.putExtras(bundle);
						startActivity(intent);
						break;
					case 3:
						intent=new Intent(getActivity(),FamilyActivity.class);
						startActivity(intent);

						break;
					
					default:
						break;
					}
				}else{
					T.make(R.string.fragment2_loading,getActivity());
				}
			}
		});
		
	}
	public void get(){
		Mydialog.Show(getActivity(), "");
		GetUtil.get(new Urlsutil().getEnergyList+Cons.plant, new GetListener() {
			
			@Override
			public void success(String json) {
				Mydialog.Dismiss();
				try {
					JSONObject jsonObject=new JSONObject(json);
					JSONObject jsonObject1=jsonObject.getJSONObject("familyEnergy");
					JSONObject jsonObject2=jsonObject.getJSONObject("familyDevice");
					JSONObject jsonObject3=jsonObject.getJSONObject("storageEnergy");
					JSONObject jsonObject4=jsonObject.getJSONObject("photovoltaicEnergy");
//					list1.get(2).put("mouth", jsonObject1.get("monthEnergy").toString());
//					list1.get(2).put("day", jsonObject1.get("todayEnergy").toString());
					list1.get(2).put("mouth", "0kWh");
					list1.get(2).put("day", "0kWh");
					list1.get(3).put("mouth", "0kWh");
					list1.get(3).put("day", "0kWh");
//					list1.get(3).put("mouth", jsonObject2.get("mostEnergy").toString());
//					list1.get(3).put("day", jsonObject2.get("mostPower").toString());
					list1.get(0).put("mouth", jsonObject4.get("monthEnergy").toString());
					list1.get(0).put("day", jsonObject4.get("todayEnergy").toString());
					list1.get(1).put("mouth", jsonObject3.get("monthEnergy").toString());
					list1.get(1).put("day", jsonObject3.get("todayEnergy").toString());
					plantid=Cons.plant;
					adapter1.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void error(String json) {
				
			}
		});
	}
	public String plantid="";
	@Override
	public void onStart() {
		Log.i("��������","onStart()");
		if(plantid.equals("")){
			get();
		}else{
			if(!plantid.equals(Cons.plant)){
				get();
			}
		}
		super.onStart();
	}
	@Override
	public void onResume() {
		Log.i("��������","onResume()");
		super.onResume();
	}
	@Override
	public void onPause() {
		Log.i("��������","onPause()");
		super.onPause();	
	}
	@Override
	public void onStop() {
		Log.i("��������","onStop()");
		super.onStop();
	}
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if(this.getView()!=null){
			this.getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
		}
	}
	private void registerBroadCast() {
		receiver=new MyReceiver();
		 filter = new IntentFilter();  
	     filter.addAction(Constant.Frag2_Receiver);  
	     filter.setPriority(1000);
	     //注锟斤拷悴�
	     if(receiver!=null){
	     getActivity().registerReceiver(receiver, filter);
	     }
		
	}

    @Override
    public void onDestroyView() {
    	if(receiver!=null){
    	getActivity().unregisterReceiver(receiver);
    	}
    	super.onDestroyView();
    }
	 public class MyReceiver extends BroadcastReceiver {  
   	  
	       
	        @Override  
	        public void onReceive(Context context, Intent intent) {
	        	if(Constant.Frag2_Receiver.equals(intent.getAction())){
//	        		if(plantid.equals("")){
//	        			get();
//	        		}else{
//	        			if(!plantid.equals(Cons.plant)){
//	        				get();
//	        			}
//	        		}
					get();
	        	}
	     
	        }  
	      
	    } 
}
