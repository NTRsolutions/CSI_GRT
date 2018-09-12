package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.EnergyDetial2Adapter;
import com.growatt.shinephone.adapter.EnergyDetial3Adapter;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.v2.Fragment1Field;

import java.util.ArrayList;
import java.util.List;


public class EnergyDetial2Activity extends DoActivity {
    private List<ArrayList<BarEntry>> list2;
    private EnergyDetial2Adapter adapter2;
    private ListView listView2;

    private List<Entry> list3;
    private EnergyDetial3Adapter adapter3;
    private ListView listView3;
    private View headerView;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_detial2);
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
        listView2 = (ListView) findViewById(R.id.lvData2);
        list2 = Fragment1Field.barYList;
//        switch (type){
//            case 1:
//                list2 = EnergyFragment.barYList;
//                break;
//            case 2:
//                list2 = EnergySpf5kFragment.barYList;
//                break;
//            case 3:
//                list2 = MixFragment.barYList;
//                break;
//            default:
//                list2 = EnergyFragment.barYList;
//                break;
//        }
        if (list2 == null || list2.size() == 0){
            list2 = new ArrayList<>();
            for (int i= 0;i<2;i++){
                list2.add(new ArrayList<BarEntry>());
            }
        }
        adapter2 = new EnergyDetial2Adapter(this,list2);
        listView2.setAdapter(adapter2);

        listView3 = (ListView) findViewById(R.id.lvData3);
        if (list3 == null){
            list3 = new ArrayList<>();
        }
        adapter3 = new EnergyDetial3Adapter(this,list3);
        listView3.setAdapter(adapter3);
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            list2 = new ArrayList<>();
//            ArrayList<BarEntry> barlist1 = bundle.getParcelableArrayList("barYList1");
//            ArrayList<BarEntry> barlist2 = bundle.getParcelableArrayList("barYList2");
//            list2.add(barlist1);
//            list2.add(barlist2);
            list3 = bundle.getParcelableArrayList("socList");
        }
        type = bundle.getInt("type");
    }
}
