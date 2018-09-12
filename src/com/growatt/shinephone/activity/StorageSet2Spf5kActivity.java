package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.OssDatalogSetAdapter;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.listener.OnHandlerStrListener;
import com.growatt.shinephone.ossactivity.OssSetTimeActivity;
import com.growatt.shinephone.ossactivity.OssStorageSPF5MaxCurrentkActivity;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Position;
import com.mylhyl.circledialog.CircleDialog;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;


@ContentView(R.layout.activity_storage_set2_spf5k)
public class StorageSet2Spf5kActivity extends DemoBase {
    @ViewInject(R.id.recycleView)
    RecyclerView mRecyclerView;
    private OssDatalogSetAdapter mAdapter;
    private String[] datas;
    private String[] paramName = {
            "storage_spf5000_ac_output_source","storage_spf5000_charge_source","storage_spf5000_uti_output"
            ,"storage_spf5000_uti_charge","storage_spf5000_pv_input_model","storage_spf5000_ac_input_model"
            ,"storage_spf5000_ac_discharge_voltage","storage_spf5000_ac_discharge_frequency","storage_spf5000_overlad_restart"
            ,"storage_spf5000_overtemp_restart","storage_spf5000_buzzer","storage_spf5000_max_charge_current","storage_spf5000_batter_low_voltage"
            ,"storage_spf5000_battery_type","storage_spf5000_system_time"
    };
    private String sn;
    private FragmentActivity act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        sn = bundle.getString("serialNum");
        act = this;
        initHeaderView();
        initRecyclerView();
        SetListeners();
    }
    private void initRecyclerView() {
        datas = new String[]{
                getString(R.string.mSpf5k_AC输出源),getString(R.string.mSpf5k_充电源),getString(R.string.mSpf5k_市电输出)
                ,getString(R.string.mSpf5k_市电充电),getString(R.string.mSpf5k_光伏接入模式), getString(R.string.mSpf5k_AC输入模式)
                ,getString(R.string.mSpf5k_AC输出电压),getString(R.string.mSpf5k_AC输出频率),getString(R.string.mSpf5k_过载重启)
                ,getString(R.string.mSpf5k_过温重启),getString(R.string.mSpf5k_蜂鸣器),getString(R.string.mSpf5k_最大充电电流)
                ,getString(R.string.mSpf5k_电池低压点),getString(R.string.mSpf5k_电池类型),getString(R.string.mSpf5k_系统时间)
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new OssDatalogSetAdapter(mContext,R.layout.item_oss_datalogset, Arrays.asList(datas));
        mRecyclerView.setAdapter(mAdapter);
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
        setHeaderTitle(headerView,getString(R.string.storageset_title));
    }
    private void SetListeners() {
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                if(Cons.isflag==true){
                    toast(R.string.all_experience);
                    return;
                }
//                if (position != 2){
                    //获取密码匹配后设置储能机参数
                    MyControl.getPasswordByDevice(StorageSet2Spf5kActivity.this, 2, new OnHandlerStrListener() {
                        @Override
                        public void handlerDealStr(String result) {
                            //弹框让用户输入密码进行匹配
                            matchUserPwd(result,position);
                        }
                    });
//                }else {//直接设置储能机参数
//                    setStorage(position);
//                }
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return true;
            }
        });
    }

    /**
     * 匹配用户密码
     * @param pwd
     */
    private void matchUserPwd(final String pwd, final int position) {
        final EditText et= new EditText(StorageSet2Spf5kActivity.this);
        et.setTransformationMethod(PasswordTransformationMethod.getInstance());
        AlertDialog dialog = new AlertDialog.Builder(StorageSet2Spf5kActivity.this).setTitle(R.string.reminder)
                .setIcon(android.R.drawable.ic_menu_info_details).setMessage(R.string.inverterset_operate).setView(
                        et).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int swich) {
                        String s=et.getText().toString();
                        if (s.equals(pwd)){
                            setStorage(position);
                        }else {
                            MyControl.circlerDialog(StorageSet2Spf5kActivity.this,getString(R.string.password_prompt),-1,false);
                        }
                    }
                }).setNegativeButton(R.string.all_no, null).create();
        dialog.setCancelable(true);
        dialog.show();
    }

    /**
     * 设置参数
     * @param position
     */
    private void setStorage(final int position) {
        String[] items0;
        switch (position){
            case 0://AC输出源
                items0 = new String[]{getString(R.string.mSpf5k_电池优先),getString(R.string.mSpf5k_光伏优先),getString(R.string.mSpf5k_市电优先)};
                setSpf5kCircleDialog(items0,datas,paramName,position);
                break;
            case 1://充电源
                items0 = new String[]{getString(R.string.mSpf5k_光伏优先),getString(R.string.mSpf5k_光伏市电),getString(R.string.mSpf5k_仅光伏)};
                setSpf5kCircleDialog(items0,datas,paramName,position);
                break;
            case 2://市电输出
            case 3://市电充电
                int type = 0;
                if (position == 2){ type = 4;}
                if (position == 3){ type = 5;}
                Intent in4 = new Intent(mContext,StorageSPF5kUtiActivity.class);
                in4.putExtra("type",type);
                in4.putExtra("paramId",paramName[position]);
                in4.putExtra("sn",sn);
                in4.putExtra("title",datas[position]);
                startActivity(in4);
                break;

            case 4://光伏接入模式
                items0 = new String[]{getString(R.string.mSpf5k_独立),getString(R.string.mSpf5k_并联)};
                setSpf5kCircleDialog(items0,datas,paramName,position);
                break;
            case 5://AC输入模式
                items0 = new String[]{"90-280","170-280"};
                setSpf5kCircleDialog(items0,datas,paramName,position);
                break;
            case 6://AC输出电压
                items0 = new String[]{"208","230","240"};
                setSpf5kCircleDialog(items0,datas,paramName,position);
                break;
            case 7://AC输出频率
                items0 = new String[]{"50Hz","60Hz"};
                setSpf5kCircleDialog(items0,datas,paramName,position);
                break;
            case 8://过载重启
                items0 = new String[]{getString(R.string.mSpf5k_重启),getString(R.string.mSpf5k_不重启),getString(R.string.mSpf5k_切换到市电)};
                setSpf5kCircleDialog(items0,datas,paramName,position);
                break;
            case 9://过温重启
                items0 = new String[]{getString(R.string.mSpf5k_重启),getString(R.string.mSpf5k_不重启)};
                setSpf5kCircleDialog(items0,datas,paramName,position);
                break;
            case 10://蜂鸣器
                items0 = new String[]{getString(R.string.重复5),getString(R.string.重复4)};
                setSpf5kCircleDialog(items0,datas,paramName,position);
                break;
            case 11://最大充电电流
                Intent in10 = new Intent(mContext,OssStorageSPF5MaxCurrentkActivity.class);
                in10.putExtra("type",4);
                in10.putExtra("paramId",paramName[position]);
                in10.putExtra("sn",sn);
                in10.putExtra("title",datas[position]);
                startActivity(in10);
                break;
            case 12://电池低压
                Intent in5 = new Intent(mContext,StorageSPF5kBatterLowVSetActivity.class);
                in5.putExtra("type",4);
                in5.putExtra("paramId",paramName[position]);
                in5.putExtra("sn",sn);
                in5.putExtra("title",datas[position]);
                startActivity(in5);
                break;
            case 13://电池类型
                items0 = new String[]{getString(R.string.mSpf5k_铅酸电池),getString(R.string.mSpf5k_锂电池),getString(R.string.mSpf5k_自定义铅酸电池)};
                setSpf5kCircleDialog(items0,datas,paramName,position);
                break;
            case 14://系统时间
                Intent in6 = new Intent(mContext,OssSetTimeActivity.class);
                in6.putExtra("type",6);
                in6.putExtra("paramId",paramName[position]);
                in6.putExtra("sn",sn);
                in6.putExtra("title",datas[position]);
                startActivity(in6);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
public void setSpf5kCircleDialog(Object items, String[] datas, final String[] paramName, final int position){
    new CircleDialog.Builder(StorageSet2Spf5kActivity.this)
            .setTitle(datas[position])
            .setItems(items, new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    MyControl.storageSPF5KSetServer(StorageSet2Spf5kActivity.this, sn, paramName[position], pos + "","","","", new OnHandlerListener() {
                        @Override
                        public void handlerDeal(int result,String msg) {
                            handlerStorageServer.sendEmptyMessage(result);
                        }
                    });
                }
            })
            .setNegative(getString(R.string.all_no),null)
            .show();
}

    //server储能机handler
    private Handler handlerStorageServer = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 200:text = getString(R.string.all_success);break;
                case 501:text = getString(R.string.inverterset_set_no_server);break;
                case 502:text = getString(R.string.inverterset_set_interver_no_server);break;
                case 503:text = getString(R.string.inverterset_set_no_numberblank);break;
                case 504:text = getString(R.string.inverterset_set_interver_no_online);break;
                case 505:text = getString(R.string.inverterset_set_no_online);break;
                case 506:text = getString(R.string.storageset_no_type);break;
                case 507:text = getString(R.string.inverterset_set_no_blank);break;
                case 508:text = getString(R.string.storageset_no_value);break;
                case 509:text = getString(R.string.storageset_no_time);break;
                case 701:text = getString(R.string.m7);break;
                default:text = getString(R.string.inverterset_set_other);break;
            }
            MyControl.circlerDialog(StorageSet2Spf5kActivity.this,text,msg.what);
        }
    };
}
