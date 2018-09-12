package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

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

@ContentView(R.layout.activity_oss_datalog_advance_set)
public class OssAdvanceSetActivity extends DemoBase {
    @ViewInject(R.id.etValue1)
    EditText etValue1;
    @ViewInject(R.id.etValue2)
    EditText etValue2;
    //跳转而来的数据
    private String sn;//设备sn
    private String paramId;
    private String title;
    private int type = 1;//由哪里跳转而来：0--oss采集器高级设置；1--oss逆变器高级设置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
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
    @Event(value = R.id.btnOk)
    private void btnOk(View v){
        if (isEmpty(etValue1,etValue2)) return;
        final String name = etValue1.getText().toString().trim();
        final String value = etValue2.getText().toString().trim();
        new CircleDialog.Builder(this)
                .setWidth(0.7f)
                .setTitle(getString(R.string.警告))
                .setText(title + ":" + name + "=" + value)
                .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (type){
                            case 0:
                                ossDatalogAdvanceSet(name,value);
                                break;
                            case 1:
                                ossInvAdvanceSet(name,value);
                                break;
                        }
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }

    private void ossInvAdvanceSet(String name, String value) {
        MyControl.invSet(this, sn, paramId, name, value, new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
//                handlerInv.sendEmptyMessage(result);
                OssUtils.showOssDialog(OssAdvanceSetActivity.this,msg,result,true,null);
            }
        });
    }

    private void ossDatalogAdvanceSet(String name, String value) {
        MyControl.datalogSet(this, sn, paramId, name, value, new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
//                handlerDatalog.sendEmptyMessage(result);
                OssUtils.showOssDialog(OssAdvanceSetActivity.this,msg,result,true,null);
            }
        });
    }

    private void initIntent() {
        Intent intent = getIntent();
        sn = intent.getStringExtra("sn");
        type = intent.getIntExtra("type",0);
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
            MyControl.circlerDialog(OssAdvanceSetActivity.this,text,msg.what);
        }
    };
    //采集器handler
    private Handler handlerDatalog = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 0:text = "设置失败";break;
                case 1:text = "设置成功";break;
                case 2:text = "采集器服务器错误";break;
                case 3:text = "采集器不在线";break;
                case 11:text = "服务器地址为空";break;
                case 12:text = "远程设置错误";break;
            }
            MyControl.circlerDialog(OssAdvanceSetActivity.this,text,msg.what);
        }
    };
}
