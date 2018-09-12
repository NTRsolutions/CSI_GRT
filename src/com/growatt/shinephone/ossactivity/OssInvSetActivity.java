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
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.DialogParams;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;

@ContentView(R.layout.activity_oss_inv_set)
public class OssInvSetActivity extends DemoBase {
    @ViewInject(R.id.recycleView)
    RecyclerView mRecyclerView;
    private OssDatalogSetAdapter mAdapter;
    private String[] datas;
    private String sn;
    private String[] paramName = {"pv_on_off","pv_active_p_rate","pv_reactive_p_rate","pv_pf_cmd_memory_state",
            "pv_power_factor","pf_sys_year","pv_grid_voltage_high","pv_grid_voltage_low","set_any_reg"};
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
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                switch (position){
                    case 0://开关机
                        Intent in1 = new Intent(mContext,OssDeviceTurnOnOffActivity.class);
                        in1.putExtra("type",1);
                        in1.putExtra("paramId",paramName[position]);
                        in1.putExtra("deviceType",1);
                        in1.putExtra("sn",sn);
                        in1.putExtra("title",datas[position]);
                        startActivity(in1);
                        break;
                    case 1:
                    case 2://设置有功和无功功率百分比
                        Intent in2 = new Intent(mContext,OssPVRateActivity.class);
                        in2.putExtra("type",1);
                        in2.putExtra("paramId",paramName[position]);
                        in2.putExtra("sn",sn);
                        in2.putExtra("title",datas[position]);
                        startActivity(in2);
                        break;
                    case 3://是否储存pf命令
                        setPFTurnOnOff(position);
                        break;
                    case 4://设置pf
                        Intent in3 = new Intent(mContext,OssSetPFActivity.class);
                        in3.putExtra("type",1);
                        in3.putExtra("paramId",paramName[position]);
                        in3.putExtra("sn",sn);
                        in3.putExtra("title",datas[position]);
                        startActivity(in3);
                        break;
                    case 5://设置时间
                        Intent in4 = new Intent(mContext,OssSetTimeActivity.class);
                        in4.putExtra("type",1);
                        in4.putExtra("paramId",paramName[position]);
                        in4.putExtra("sn",sn);
                        in4.putExtra("title",datas[position]);
                        startActivity(in4);
                        break;
                    case 6:
                    case 7://设置市电电压上下限
                        Intent in5 = new Intent(mContext,OssPVGridSingleSetActivity.class);
                        in5.putExtra("type",1);
                        in5.putExtra("paramId",paramName[position]);
                        in5.putExtra("sn",sn);
                        in5.putExtra("title",datas[position]);
                        startActivity(in5);
                        break;
                    case 8://高级设置
                        Intent in6 = new Intent(mContext,OssAdvanceSetActivity.class);
                        in6.putExtra("type",1);
                        in6.putExtra("paramId",paramName[position]);
                        in6.putExtra("sn",sn);
                        in6.putExtra("title",datas[position]);
                        startActivity(in6);
                        break;
                }
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**
     * 是否储存pf命令
     */
    private void setPFTurnOnOff(final int position) {
        final String[] items = { "关闭","开启"};
        new CircleDialog.Builder(this)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = R.style.MyDialogStyle;
                    }
                })
                .setTitle(datas[position])
                .setItems(items, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        MyControl.invSet(OssInvSetActivity.this, sn, paramName[position], pos +"", "", new OnHandlerListener() {
                            @Override
                            public void handlerDeal(int result,String msg) {
                                OssUtils.showOssDialog(OssInvSetActivity.this,msg,result,true,null);
//                                handlerInv.sendEmptyMessage(result);
                            }
                        });
                    }
                })
                .setNegative(getString(R.string.all_no), null)
                .show();
    }

    private void initIntent() {
        Intent intent = getIntent();
        sn = intent.getStringExtra("sn");
    }
    private void initRecyclerView() {
        datas = new String[]{getString(R.string.inverterset_switch),getString(R.string.inverterset_activepower),
                getString(R.string.inverterset_wattlesspower),getString(R.string.inverterset_pforder),
                getString(R.string.inverterset_pfvalue),getString(R.string.inverterset_time),
                getString(R.string.inverterset_voltage_high),getString(R.string.m87设置市电电压下限),
                "高级设置"};
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
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
        setHeaderTitle(headerView,getString(R.string.all_interver));
    }
    //逆变器handler
    private Handler handlerInv = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 0:text = "设置失败";break;
                case 1:text = "设置成功";break;
                case 2:text = "逆变器服务器错误";break;
                case 3:text = "逆变器不在线";break;
                case 4:text = "采集器序列号为空";break;
                case 5:text = "采集器不在线";break;
                case 6:text = "参数ID不存在";break;
                case 7:text = "参数值为空";break;
                case 8:text = "参数值不在范围内";break;
                case 9:text = "时间参数错误 ";break;
                case 10:text = "设置类型为空";break;
                case 11:text = "服务器地址为空";break;
                case 12:text = "远程设置错误";break;
            }
            MyControl.circlerDialog(OssInvSetActivity.this,text,msg.what);
        }
    };
}
