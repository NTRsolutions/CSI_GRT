package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

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

/**
 * SPF5k:最大充电电流设置
 */
@ContentView(R.layout.activity_oss_storage_spf5_max_currentk)
public class OssStorageSPF5MaxCurrentkActivity extends DemoBase {
    @ViewInject(R.id.tvPv)
    TextView tvPv;
    @ViewInject(R.id.seekBar)
    SeekBar seekBar;
    @ViewInject(R.id.btnOk)
    Button btnOk;
    //跳转而来的数据
    private String sn;//设备sn
    private String paramId;
    private String title;
    private int type = 2;//由哪里跳转而来：2--oss储能机最大充电电流;4--server储能机最大充电电流
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initListener();
    }

    private void initListener() {
        tvPv.setText((seekBar.getProgress() + 10) + "");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPv.setText((seekBar.getProgress() + 10) + "");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    @Event(value = R.id.btnOk)
    private void btnOk(View v){
        final String value = tvPv.getText().toString();
        new CircleDialog.Builder(this)
                .setWidth(0.7f)
                .setTitle(getString(R.string.警告))
                .setText(title + ":" + value)
                .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (type){
                            case 2://oss储能机最大充电电流
                                setOssMaxCurrent(value);
                                break;
                            case 4://server储能机最大充电电流
                                setServerMaxCurrent(value);
                                break;
                        }
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }
    /**
     * 储能机SPF5k 设置server最大充电电流
     * @param value
     */
    private void setServerMaxCurrent(String value) {
        MyControl.storageSPF5KSetServer(this, sn, paramId, value, "", "", "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                handlerStorageServer.sendEmptyMessage(result);
            }
        });
    }
    /**
     * 储能机SPF5k 设置oss最大充电电流
     * @param value
     */
    private void setOssMaxCurrent(String value) {
        OssUtils.storageSetSPF5k(this, sn, paramId,value, "","","", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                OssUtils.showOssDialog(OssStorageSPF5MaxCurrentkActivity.this,msg,result,true,null);
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
        type = intent.getIntExtra("type",2);
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
            MyControl.circlerDialog(OssStorageSPF5MaxCurrentkActivity.this,text,msg.what);
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
            MyControl.circlerDialog(OssStorageSPF5MaxCurrentkActivity.this,text,msg.what);
        }
    };
}
