package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.github.mikephil.charting.data.Entry;
import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.EnergyDetialAdapter;
import com.growatt.shinephone.util.Position;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class EnergyDetialActivity extends DoActivity {
    private List<List<Entry>> dataList;
    private EnergyDetialAdapter adapter;
    private ListView listView;
    private View headerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_detial);
        initHeaderView();
        getData();
        initView();
    }
    private void initHeaderView() {
        headerView = findViewById(R.id.headerView);
        setHeaderTitle(headerView,getString(R.string.fragment2_title));
        setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initView() {
        listView = (ListView) findViewById(R.id.lvData);
        if (dataList == null || dataList.size() == 0){
            dataList = new ArrayList<>();
            for (int i= 0;i<4;i++){
                dataList.add(new ArrayList<Entry>());
            }
        }
        adapter = new EnergyDetialAdapter(this,dataList);
        listView.setAdapter(adapter);
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            dataList = new ArrayList<>();
            ArrayList<Entry> list1 = bundle.getParcelableArrayList("list1");
            ArrayList<Entry> list2 = bundle.getParcelableArrayList("list2");
            ArrayList<Entry> list3 = bundle.getParcelableArrayList("list3");
            ArrayList<Entry> list4 = bundle.getParcelableArrayList("list4");
            dataList.add(list1);
            dataList.add(list2);
            dataList.add(list3);
            dataList.add(list4);
            LogUtil.i("datalistEnergyde:" + list1.toString());
        }
    }
}
