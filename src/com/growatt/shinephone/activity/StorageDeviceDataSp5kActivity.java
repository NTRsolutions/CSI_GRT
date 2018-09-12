package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.StorageSp5kDeticalAdapter;
import com.growatt.shinephone.bean.StorageSpf5kInfoBean;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_storage_device_data_sp5k)
public class StorageDeviceDataSp5kActivity extends DoActivity {
    private TextView tv2;
    private TextView tv4;
    private TextView tv6;
    private TextView tv8;
    private TextView tv10;
    private TextView tv12;
    private String vac;
    private String alias;
    private String serialNum;
    private int capacity;
    private String vBat;
    private String datalogSn;
    private String StorageType;
    private String modelText;
    private String fwVersion;
    private String normalPower;
    @ViewInject(R.id.recycleView)
    RecyclerView mRecyclerView;
    private List<Map<String,String>> mList;
    private List<String> mNames;
    private List<String> mValues;
    private StorageSp5kDeticalAdapter mAdapter;
    private String activePower;
    private String apparentPower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        SetViews();
        initRecycleView();
        refresh();
    }

    private void initRecycleView() {
        mNames = Arrays.asList("Vb/Cb","Vpv","Ic_pv","Ppv","Ac_In","AC_Out"
                ,"PL","Per_Load","Epv_d","Epv_a","Ec_day","Ec_all","Ed_day","Ed_all");
        mValues = new ArrayList<>();
        mList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new StorageSp5kDeticalAdapter(this,R.layout.item_storage_sp5k_detical,mList);
        mRecyclerView.setAdapter(mAdapter);
        initData(mNames,mValues);
    }

    private void initData(List<String> mNames, List<String> mValues) {
        List<Map<String,String>> newList = new ArrayList<>();
        if (mValues == null){
            mValues = new ArrayList<>();
        }
        int valueSize = mValues.size();
        for (int i = 0,size = mNames.size();i<size;i++){
            Map<String,String> map = new HashMap<>();
            map.put("name",mNames.get(i));
            if (valueSize > i){
                map.put("value",mValues.get(i));
            }else {
                map.put("value","");
            }
            newList.add(map);
        }
        mAdapter.addAll(newList,true);
    }


    private void initIntent() {
        Bundle bundle=getIntent().getExtras();
        vac=bundle.getString("vac");
        vBat=bundle.getString("vBat");
        fwVersion=bundle.getString("fwVersion");
        alias=bundle.getString("alias");
        modelText=bundle.getString("modelText");
        normalPower=bundle.getString("normalPower");
        StorageType=bundle.getString("StorageType");
        datalogSn=bundle.getString("datalogSn");
        serialNum=bundle.getString("serialNum");
        activePower=bundle.getString("activePower");
        apparentPower=bundle.getString("apparentPower");
        capacity = Integer.parseInt(bundle.getString("capacity"));
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
        setHeaderTitle(headerView,alias);
    }
    private void SetViews() {
        tv2=(TextView)findViewById(R.id.textView2);
        tv4=(TextView)findViewById(R.id.textView4);
        tv6=(TextView)findViewById(R.id.textView6);
        tv8=(TextView)findViewById(R.id.textView8);
        tv10=(TextView)findViewById(R.id.textView10);
        tv12=(TextView)findViewById(R.id.textView12);
        tv2.setText(serialNum);
        tv4.setText(datalogSn);
        tv6.setText(StorageType);
        tv8.setText(activePower + "/" +apparentPower);
        tv10.setText(fwVersion);
        tv12.setText(modelText);
    }

    private void refresh() {
        Mydialog.Show(this, "");
        GetUtil.get(new Urlsutil().getStorageInfo+"&storageId="+serialNum, new GetUtil.GetListener() {

            @Override
            public void success(String json) {
                try {
                    StorageSpf5kInfoBean bean = new Gson().fromJson(json,StorageSpf5kInfoBean.class);
                    if (bean == null) return;
                    List<String> newValues = new ArrayList<>();
                    newValues.add(bean.getVbat() + "/" + bean.getCapacity() + "%" );
                    newValues.add(bean.getVpv1()+ "/" + bean.getVpv2());
                    newValues.add(bean.getiChargePV1()+ "/" + bean.getiChargePV2());
                    newValues.add(bean.getpCharge1()+ "/" + bean.getpCharge2());
                    newValues.add(bean.getvGrid()+ "/" + bean.getFreqGrid());
                    newValues.add(bean.getOutPutVolt()+ "/" + bean.getFreqOutPut());
                    newValues.add(bean.getOutPutPower());
                    newValues.add(bean.getLoadPercent());
                    newValues.add(bean.getEpvToday());
                    newValues.add(bean.getEpvTotal());
                    newValues.add(bean.geteBatChargeToday());
                    newValues.add(bean.geteBatChargeTotal());
                    newValues.add(bean.geteBatDisChargeToday());
                    newValues.add(bean.geteBatDisChargeTotal());
                    initData(mNames,newValues);
                } catch (Exception e) {
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
