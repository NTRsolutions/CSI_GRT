package com.growatt.shinephone.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.growatt.shinephone.R;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.mylhyl.circledialog.CircleDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Calendar;
import java.util.Date;

/**
 * 储能机 强制放电时间段 设置充电时间
 */
@ContentView(R.layout.activity_storage_spf5k_uti)
public class StorageSPF5kUtiActivity extends DemoBase {
    @ViewInject(R.id.tvStartTime)
    TextView tvStartTime;
    @ViewInject(R.id.tvEndTime)
    TextView tvEndTime;
    @ViewInject(R.id.tvNote1)
    TextView tvNote1;
    @ViewInject(R.id.tvNote2)
    TextView tvNote2;
    //跳转而来的数据
    private String sn;//设备sn
    private String paramId;
    private String title;
    private int type = 0;//由哪里跳转而来：2--oss市电输出;3--oss市电充电;4--server市电输出;5--server市电充电
    java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("HH");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initListener();
    }

    @Event(value = R.id.btnOk)
    private void btnOk(View v){
        final String startTime = tvStartTime.getText().toString().trim();
        final String endTime = tvEndTime.getText().toString().trim();
        new CircleDialog.Builder(this)
                .setWidth(0.7f)
                .setTitle(getString(R.string.警告))
                .setText(title + ":" + startTime + "～" + endTime)
                .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (type){
                            case 2:
                            case 3:
                                ossUtiOutput(startTime,endTime);
                                break;
                            case 4:
                            case 5:
                                serverUtiOutput(startTime,endTime);
                                break;
                        }
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }
    /**
     * 储能机SPF5k oss市电输出/充电时间段设置
     * @param startTime
     * @param endTime
     */
    private void ossUtiOutput(String startTime, String endTime) {
        OssUtils.storageSetSPF5k(StorageSPF5kUtiActivity.this, sn, paramId,startTime, endTime,"","", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                OssUtils.showOssDialog(StorageSPF5kUtiActivity.this,msg,result,true,null);
            }
        });
    }

    /**
     * 储能机SPF5k server市电输出/充电时间段设置
     * @param startTime
     * @param endTime
     */
    private void serverUtiOutput(String startTime, String endTime) {
        MyControl.storageSPF5KSetServer(this, sn, paramId, startTime, endTime, "", "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                handlerStorageServer.sendEmptyMessage(result);
            }
        });

    }

    private void serverStorageSetDisChargeTime(String startTime, String endTime) {
        String[] starts = startTime.split(":");
        String[] ends = endTime.split(":");
        MyControl.storageSetServer(this, sn, paramId, starts[0], starts[1], ends[0], ends[1], new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
//                OssUtils.showOssDialog(OssStorageDischargeTimeSetActivity.this,msg,result,true,null);
                handlerStorageServer.sendEmptyMessage(result);
            }
        });
    }

    private void serverStorageSetChargeTime(String startTime, String endTime) {
        String[] starts = startTime.split(":");
        String[] ends = endTime.split(":");
        MyControl.storageSetServer(this, sn, paramId, starts[0], starts[1], ends[0], ends[1], new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
//                OssUtils.showOssDialog(OssStorageDischargeTimeSetActivity.this,msg,result,true,null);
                handlerStorageServer.sendEmptyMessage(result);
            }
        });
    }

    private void ossStorageSetChargeTime(String startTime, String endTime) {
        String[] starts = startTime.split(":");
        String[] ends = endTime.split(":");
        MyControl.storageSet(this, sn, paramId, starts[0], starts[1], ends[0], ends[1], new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                OssUtils.showOssDialog(StorageSPF5kUtiActivity.this,msg,result,true,null);
//                handlerStorage.sendEmptyMessage(result);
            }
        });
    }

    private void ossStorageSetDisChargeTime(String startTime, String endTime) {
        String[] starts = startTime.split(":");
        String[] ends = endTime.split(":");
        MyControl.storageSet(this, sn, paramId, starts[0], starts[1], ends[0], ends[1], new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                OssUtils.showOssDialog(StorageSPF5kUtiActivity.this,msg,result,true,null);
            }
        });
    }

    private void initListener() {
        tvStartTime.setText(sdf2.format(new Date()));
        tvEndTime.setText(sdf2.format(new Date()));
        // 为设置时间按钮绑定监听器
        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                // 创建一个TimePickerDialog实例，并把它显示出来
                new TimePickerDialog(mContext,
                        // 绑定监听器
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tvStartTime.setText(hourOfDay + "");
                            }
                        }
                        // 设置初始时间
                        , c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                        // true表示采用24小时制
                        true).show();
            }
        });
        // 为设置时间按钮绑定监听器
        tvEndTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                // 创建一个TimePickerDialog实例，并把它显示出来
                new TimePickerDialog(mContext,
                        // 绑定监听器
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                tvEndTime.setText(hourOfDay + "");
                            }
                        }
                        // 设置初始时间
                        , c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                        // true表示采用24小时制
                        true).show();
            }
        });
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
        setHeaderTitle(headerView,title);
    }
    private void initIntent() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        sn = intent.getStringExtra("sn");
        paramId = intent.getStringExtra("paramId");
        title = intent.getStringExtra("title");
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
            MyControl.circlerDialog(StorageSPF5kUtiActivity.this,text,msg.what);
        }
    };
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
            MyControl.circlerDialog(StorageSPF5kUtiActivity.this,text,msg.what);
        }
    };
}
