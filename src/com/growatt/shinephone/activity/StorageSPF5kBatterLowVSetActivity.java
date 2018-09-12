package com.growatt.shinephone.activity;

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
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.mylhyl.circledialog.CircleDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.math.BigDecimal;

/**
 * 锂电池SOC下限设定
 */
@ContentView(R.layout.activity_storage_spf5k_batter_low_vset)
public class StorageSPF5kBatterLowVSetActivity extends DemoBase {
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
    private int type = 2;//由哪里跳转而来：2--oss储能机电池低压点;4--server储能机电池低压点
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initListener();
    }
    public  float mathFloat(float f1,float f2){
        BigDecimal b1 = new BigDecimal(Float.toString(f1));
        BigDecimal b2 = new BigDecimal(Float.toString(f2));
        float ss = b1.subtract(b2).floatValue();
        return ss;
    }
    private void initListener() {
        tvPv.setText(Math.round((seekBar.getProgress()*1.0f/10 + 44.4f)*10)/10.0f + "");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPv.setText(Math.round((progress*1.0f/10 + 44.4f)*10)/10.0f + "");
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
                            case 2://oss储能机锂电池
                                setOssBatteryLow(value);
                                break;
                            case 4://server储能机锂电池
                                setServerBatteryLow(value);
                                break;
                        }
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }

    /**
     * 储能机SPF5k 设置server电池低压点
     * @param value
     */
    private void setServerBatteryLow(String value) {
        MyControl.storageSPF5KSetServer(this, sn, paramId, value, "", "", "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                handlerStorageServer.sendEmptyMessage(result);
            }
        });
    }
    /**
     * 储能机SPF5k 设置oss电池低压点
     * @param value
     */
    private void setOssBatteryLow(String value) {
        OssUtils.storageSetSPF5k(this, sn, paramId,value, "","","", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                OssUtils.showOssDialog(StorageSPF5kBatterLowVSetActivity.this,msg,result,true,null);
            }
        });
    }

    private void sendRequest2(String value) {
        MyControl.storageSetServer(this, sn, paramId, value, "", "", "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
//                OssUtils.showOssDialog(OssSocDownLimitSetActivity.this,msg,result,true,null);
                handlerStorageServer.sendEmptyMessage(result);
            }
        });
    }

    private void sendRequest1(String value) {
        MyControl.storageSet(this, sn, paramId, value, "", "", "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                OssUtils.showOssDialog(StorageSPF5kBatterLowVSetActivity.this,msg,result,true,null);
//                handlerStorage.sendEmptyMessage(result);
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
            MyControl.circlerDialog(StorageSPF5kBatterLowVSetActivity.this,text,msg.what);
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
            MyControl.circlerDialog(StorageSPF5kBatterLowVSetActivity.this,text,msg.what);
        }
    };
}
