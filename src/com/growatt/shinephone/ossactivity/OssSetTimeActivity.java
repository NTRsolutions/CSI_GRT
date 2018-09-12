package com.growatt.shinephone.ossactivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
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
 * 时间设置
 */
@ContentView(R.layout.activity_oss_set_time)
public class OssSetTimeActivity extends DemoBase {
    @ViewInject(R.id.tvName)
    TextView tvName;
    @ViewInject(R.id.tvDate)
    TextView tvDate;
    @ViewInject(R.id.tvTime)
    TextView tvTime;
    @ViewInject(R.id.btnOk)
    Button btnOk;
    //跳转而来的数据
    private String sn;//设备sn
    private String paramId;
    private String title;
    /**
     * //由哪里跳转而来：1--oss逆变器时间设置;2--oss储能机时间设置;3--server逆变器时间设置;4--server储能机时间设置;5--oss储能机SPF5k时间设置;6--server储能机SPF5k时间设置;7--server JLINV 逆变器
     */
    private int type = 1;
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
    java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
        initListener();
    }
    private void initListener() {
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
                new DatePickerDialog(mContext,
                        // 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                tvDate.setText(year + "-" + ((monthOfYear+1) < 10 ? ("0" + (monthOfYear+1)) : (monthOfYear+1))
                                        + "-" + (dayOfMonth < 10 ? ("0" + dayOfMonth) :  dayOfMonth));
                            }
                        }
                        // 设置初始日期
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // 为设置时间按钮绑定监听器
        tvTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                // 创建一个TimePickerDialog实例，并把它显示出来
                new TimePickerDialog(mContext,
                        // 绑定监听器
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,int hourOfDay, int minute) {
                                tvTime.setText((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay)+ ":" + (minute < 10 ? "0" + minute :minute));
                            }
                        }
                        // 设置初始时间
                        , c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                        // true表示采用24小时制
                        true).show();
            }
        });
    }
    private void initView() {
        tvName.setText(title);
        tvDate.setText(sdf.format(new Date()));
        tvTime.setText(sdf2.format(new Date()));
    }
    @Event(value = R.id.btnOk)
    private void btnOk(View v){
        final String time = tvDate.getText().toString().trim() + " " + tvTime.getText().toString().trim();
        new CircleDialog.Builder(this)
                .setWidth(0.7f)
                .setTitle(getString(R.string.警告))
                .setText(title + ":" + time)
                .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (type){
                            case 1:
                                ossInvSetTime(time);
                                break;
                            case 2:
                            ossStorageSetTime(time);
                            break;
                            case 3:
                                serverInvSetTime(time);
                                break;
                            case 4:
                                serverStorageSetTime(time);
                                break;
                            case 5:
                                ossStorageSPF5kSetTime(time);
                                break;
                            case 6:
                                serverStorageSPF5kSetTime(time);
                                break;
                            case 7:
                                serverJLINVSetTime(time);
                                break;
                        }
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }
    /**
     * oss SPF5k 时间设置
     * @param time
     */
    private void ossStorageSPF5kSetTime(String time) {
        OssUtils.storageSetSPF5k(this, sn, paramId,time, "","","", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                OssUtils.showOssDialog(OssSetTimeActivity.this,msg,result,true,null);
            }
        });
    }

    /**
     * server SPF5k 时间设置
     * @param time
     */
    private void serverStorageSPF5kSetTime(String time) {
        MyControl.storageSPF5KSetServer(this, sn, paramId, time,"","","", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                handlerStorageServer.sendEmptyMessage(result);
            }
        });
    }

    private void serverInvSetTime(String time) {
        MyControl.invSetServer(this, sn, paramId, time, "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
//                OssUtils.showOssDialog(OssSetTimeActivity.this,msg,result,true,null);
                handlerInvServer.sendEmptyMessage(result);
            }
        });
    }
    private void serverJLINVSetTime(String time) {
        MyControl.invSetJLINVServer(this, sn, paramId, time, "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                handlerInvServer.sendEmptyMessage(result);
            }
        });
    }

    private void serverStorageSetTime(String time) {
        MyControl.storageSetServer(this, sn, paramId, time, "", "", "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
//                OssUtils.showOssDialog(OssSetTimeActivity.this,msg,result,true,null);
                handlerStorageServer.sendEmptyMessage(result);
            }
        });
    }

    private void ossStorageSetTime(String time) {
        MyControl.storageSet(this, sn, paramId, time, "", "", "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                OssUtils.showOssDialog(OssSetTimeActivity.this,msg,result,true,null);
//                handlerStorage.sendEmptyMessage(result);
            }
        });
    }

    private void ossInvSetTime(String time) {
        MyControl.invSet(this, sn, paramId, time, "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                OssUtils.showOssDialog(OssSetTimeActivity.this,msg,result,true,null);
//                handlerInv.sendEmptyMessage(result);
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
        type = intent.getIntExtra("type",1);
        sn = intent.getStringExtra("sn");
        paramId = intent.getStringExtra("paramId");
        title = intent.getStringExtra("title");
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
            MyControl.circlerDialog(OssSetTimeActivity.this,text,msg.what);
        }
    };
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
            }
            MyControl.circlerDialog(OssSetTimeActivity.this,text,msg.what);
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
            MyControl.circlerDialog(OssSetTimeActivity.this,text,msg.what);
        }
    };
    //server逆变器handler
    private Handler handlerInvServer = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 200:text = getString(R.string.all_success);break;
                case 501:text = getString(R.string.inverterset_set_no_server);break;
                case 502:text = getString(R.string.inverterset_set_interver_no_server);break;
                case 503:text = getString(R.string.inverterset_set_interver_no_online);break;
                case 504:text = getString(R.string.inverterset_set_no_numberblank);break;
                case 505:text = getString(R.string.inverterset_set_no_online);break;
                case 506:text = getString(R.string.inverterset_set_no_facility);break;
                case 507:text = getString(R.string.inverterset_set_no_blank);break;
                case 508:text = getString(R.string.inverterset_set_no_value);break;
                case 509:text = getString(R.string.inverterset_set_no_time);break;
                case 510:
                case 511:
                case 512:
                    text = getString(R.string.storageset_no_value);break;
                case 701:text = getString(R.string.m7);break;
                default:text = getString(R.string.inverterset_set_no);break;
            }
            MyControl.circlerDialog(OssSetTimeActivity.this,text,msg.what);
        }
    };
}
