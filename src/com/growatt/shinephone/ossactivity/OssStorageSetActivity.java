package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.adapter.OssDatalogSetAdapter;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.mylhyl.circledialog.CircleDialog;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;

@ContentView(R.layout.activity_oss_storage_set)
public class OssStorageSetActivity extends DemoBase{
    @ViewInject(R.id.recycleView)
    RecyclerView mRecyclerView;
    private OssDatalogSetAdapter mAdapter;
    private String[] datas;
    private String sn;
    private String[] paramName = {"storage_cmd_on_off","storage_lithium_battery","storage_cmd_sys_year"
            ,"storage_cmd_forced_discharge_enable","storage_cmd_forced_discharge_time1","storage_fdt_open_voltage"
            ,"storage_ac_charge_enable_disenable","storage_ac_charge_hour_start","storage_ac_charge_soc_limit"
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initRecyclerView();
        initListener();
    }
    private void initListener() {
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                switch (position){
                    case 0://开关机
                        Intent in0 = new Intent(mContext,OssDeviceTurnOnOffActivity.class);
                        in0.putExtra("type",1);
                        in0.putExtra("paramId",paramName[position]);
                        in0.putExtra("deviceType",2);
                        in0.putExtra("sn",sn);
                        in0.putExtra("title",datas[position]);
                        startActivity(in0);
                        break;
                    case 1://soc下限
                        Intent in1 = new Intent(mContext,OssSocDownLimitSetActivity.class);
                        in1.putExtra("paramId",paramName[position]);
                        in1.putExtra("type",2);
                        in1.putExtra("sn",sn);
                        in1.putExtra("title",datas[position]);
                        startActivity(in1);
                        break;
                    case 2://时间
                        Intent in5 = new Intent(mContext,OssSetTimeActivity.class);
                        in5.putExtra("type",2);
                        in5.putExtra("paramId",paramName[position]);
                        in5.putExtra("sn",sn);
                        in5.putExtra("title",datas[position]);
                        startActivity(in5);
                        break;
                    case 3:
                    case 6://强制放电使能;充电使能
                        String[] items = {getString(R.string.m89禁止),getString(R.string.m88使能)};
                        new CircleDialog.Builder(OssStorageSetActivity.this)
                                .setTitle(datas[position])
                                .setItems(items, new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                                        MyControl.storageSet(OssStorageSetActivity.this, sn, paramName[position], pos + "", new OnHandlerListener() {
                                            @Override
                                            public void handlerDeal(int result,String msg) {
                                                OssUtils.showOssDialog(OssStorageSetActivity.this,msg,result,true,null);
//                                                handlerStorage.sendEmptyMessage(result);
                                            }
                                        });
                                    }
                                })
                                .setNegative(getString(R.string.all_no),null)
                                .show();
                        break;
                    case 4://强制放电时间段
                    case 7://强制充电时间段
                        int type = 2;
                        if (position == 4){ type = 2;}
                        if (position == 7){ type = 3;}
                        Intent in4 = new Intent(mContext,OssStorageDischargeTimeSetActivity.class);
                        in4.putExtra("type",type);
                        in4.putExtra("paramId",paramName[position]);
                        in4.putExtra("sn",sn);
                        in4.putExtra("title",datas[position]);
                        startActivity(in4);
                        break;
                    case 5://设置sp组串电压
                        Intent in6 = new Intent(mContext,OssSPSetActivity.class);
                        in6.putExtra("type",2);
                        in6.putExtra("paramId",paramName[position]);
                        in6.putExtra("sn",sn);
                        in6.putExtra("title",datas[position]);
                        startActivity(in6);
                        break;
                    case 8:
                        Intent in8 = new Intent(mContext,OssChargeSocLimitSetActivity.class);
                        in8.putExtra("type",2);
                        in8.putExtra("paramId",paramName[position]);
                        in8.putExtra("sn",sn);
                        in8.putExtra("title",datas[position]);
                        startActivity(in8);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
    private void initIntent() {
        Intent intent = getIntent();
        sn = intent.getStringExtra("sn");
    }
    private void initRecyclerView() {
        datas = new String[]{getString(R.string.storageset_list_item1),getString(R.string.storageset_list_item2),getString(R.string.storageset_list_item6),
                getString(R.string.storageset_list_item3),getString(R.string.storageset_list_item4),
                getString(R.string.storageset_list_item5),getString(R.string.m84充电使能),getString(R.string.m85充电时间段),getString(R.string.m86充电电池SOC设置)};
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        mAdapter = new OssDatalogSetAdapter(mContext,R.layout.item_oss_datalogset, Arrays.asList(datas));
        mRecyclerView.setAdapter(mAdapter);
    }
    /**
     * 处理头部
     */
    private View headerView;
    private void initHeaderView() {
        headerView = findViewById(R.id.headerView);
        setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setHeaderTitle(headerView,getString(R.string.storageset_title));
    }
    //储能机handler
    private Handler handlerStorage = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 0:text = "设置失败";break;
                case 1:text = "设置成功";break;
                case 2:text = "储能机服务器错误";break;
                case 3:text = "储能机不在线";break;
                case 4:text = "储能机序列号为空";break;
                case 5:text = "采集器不在线";break;
                case 6:text = "参数ID不存在";break;
                case 7:text = "参数值为空";break;
                case 8:text = "参数值不在范围内";break;
                case 9:text = "时间参数错误 ";break;
                case 10:text = "设置类型为空";break;
                case 11:text = "服务器地址为空";break;
                case 12:text = "远程设置错误";break;
                default:
                    text = "设置失败";
                    break;
            }
            MyControl.circlerDialog(OssStorageSetActivity.this,text,msg.what);
        }
    };
}
