package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.mylhyl.circledialog.CircleDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * 设置设备开关机
 * deviceType:1:逆变器；2：储能机；3：Max;4:JLINV锦浪
 */
@ContentView(R.layout.activity_oss_device_turn_on_off)
public class OssDeviceTurnOnOffActivity extends DemoBase {
    private int deviceType = 1;
    private String title;
    private String sn;
    private String paramId;
    private int type = 1;//由哪里跳转而来：1--oss设置开关机界面；2-server设置开关机界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
    }
    /**
     * 开机设置
     */
    @Event(value = R.id.ivTurnOn)
    private void ivTurnOn(View v){
        final String[] paramValue = {"","0101","0101","0101","190"};
        final String[] note = {"",getString(R.string.是否确定开启逆变器),getString(R.string.是否确定开启储能机),getString(R.string.是否确定开启逆变器),getString(R.string.是否确定开启逆变器)};
        new CircleDialog.Builder(this)
                        .setWidth(0.7f)
                        .setTitle(getString(R.string.警告))
                        .setText(note[deviceType])
                        .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switch (type){
                                    case 1:
                                        sendRequestTurnOn1(paramId,paramValue);
                                        break;
                                    case 2:
                                        sendRequestTurnOnSever(paramId,paramValue);
                                        break;
                                }
                            }
                        })
                        .setNegative(getString(R.string.all_no),null)
                        .show();
    }

    /**
     * server开机设置
     * @param paramId
     * @param paramValue
     */
    private void sendRequestTurnOnSever(String paramId, String[] paramValue) {
        switch (deviceType){
            case 1:
                MyControl.invSetServer(mContext, sn, paramId, paramValue[deviceType], "", new OnHandlerListener() {
                    @Override
                    public void handlerDeal(int result,String msg) {
//                        OssUtils.showOssDialog(OssDeviceTurnOnOffActivity.this,msg,result,true,null);
                        handlerInvServer.sendEmptyMessage(result);
                    }
                });
                break;
            case 2:
                MyControl.storageSetServer(mContext, sn, paramId, paramValue[deviceType], "","","", new OnHandlerListener() {
                    @Override
                    public void handlerDeal(int result,String msg) {
//                        OssUtils.showOssDialog(OssDeviceTurnOnOffActivity.this,msg,result,true,null);
                        handlerStorageServer.sendEmptyMessage(result);
                    }
                });
            case 4:
                MyControl.invSetJLINVServer(mContext, sn, paramId, paramValue[deviceType], "", new OnHandlerListener() {
                    @Override
                    public void handlerDeal(int result,String msg) {
//                        OssUtils.showOssDialog(OssDeviceTurnOnOffActivity.this,msg,result,true,null);
                        handlerInvServer.sendEmptyMessage(result);
                    }
                });
                break;
        }
    }

    private void sendRequestTurnOn1(String paramName, String[] paramValue) {
        switch (deviceType){
            case 1:
                MyControl.invSet(mContext, sn, paramName, paramValue[deviceType], "", new OnHandlerListener() {
                    @Override
                    public void handlerDeal(int result,String msg) {
                        OssUtils.showOssDialog(OssDeviceTurnOnOffActivity.this,msg,result,true,null);
//                        handlerInv.sendEmptyMessage(result);
                    }
                });
                break;
            case 2:
                MyControl.storageSet(mContext, sn, paramName, paramValue[deviceType], "","","", new OnHandlerListener() {
                    @Override
                    public void handlerDeal(int result,String msg) {
                        OssUtils.showOssDialog(OssDeviceTurnOnOffActivity.this,msg,result,true,null);
//                        handlerStorage.sendEmptyMessage(result);
                    }
                });
                break;
        }
    }
    /**
     * 关机设置
     */
    @Event(value = R.id.ivTurnOff)
    private void ivTurnOff(View v){
        final String[] paramValue = {"","0000","0000","0000","222"};
        final String[] note = {"",getString(R.string.是否确定关闭逆变器),getString(R.string.是否确定关闭储能机),getString(R.string.是否确定关闭逆变器),getString(R.string.是否确定关闭逆变器)};
        new CircleDialog.Builder(this)
                .setWidth(0.7f)
                .setTitle(getString(R.string.警告))
                .setText(note[deviceType])
                .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (type){
                            case 1:
                                sendRequestTurnOff1(paramId,paramValue);
                                break;
                            case 2:
                                sendRequestTurnOffServer(paramId,paramValue);
                                break;
                        }
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }

    private void sendRequestTurnOffServer(String paramId, String[] paramValue) {
        switch (deviceType){
            case 1:
                MyControl.invSetServer(mContext, sn, paramId, paramValue[deviceType], "", new OnHandlerListener() {
                    @Override
                    public void handlerDeal(int result,String msg) {
//                        OssUtils.showOssDialog(OssDeviceTurnOnOffActivity.this,msg,result,true,null);
                        handlerInvServer.sendEmptyMessage(result);
                    }
                });
                break;
            case 2:
                MyControl.storageSetServer(mContext, sn, paramId, paramValue[deviceType], "","","", new OnHandlerListener() {
                    @Override
                    public void handlerDeal(int result,String msg) {
//                        OssUtils.showOssDialog(OssDeviceTurnOnOffActivity.this,msg,result,true,null);
                        handlerStorageServer.sendEmptyMessage(result);
                    }
                });
                break;
            case 4:
                MyControl.invSetJLINVServer(mContext, sn, paramId, paramValue[deviceType], "", new OnHandlerListener() {
                    @Override
                    public void handlerDeal(int result,String msg) {
                        handlerInvServer.sendEmptyMessage(result);
                    }
                });
                break;
        }
    }

    private void sendRequestTurnOff1(String paramName, String[] paramValue) {
        switch (deviceType){
            case 1:
                MyControl.invSet(mContext, sn, paramName, paramValue[deviceType], "", new OnHandlerListener() {
                    @Override
                    public void handlerDeal(int result,String msg) {
                        OssUtils.showOssDialog(OssDeviceTurnOnOffActivity.this,msg,result,true,null);
//                        handlerInv.sendEmptyMessage(result);
                    }
                });
                break;
            case 2:
                MyControl.storageSet(mContext, sn, paramName, paramValue[deviceType], "","","", new OnHandlerListener() {
                    @Override
                    public void handlerDeal(int result,String msg) {
                        OssUtils.showOssDialog(OssDeviceTurnOnOffActivity.this,msg,result,true,null);
//                        handlerStorage.sendEmptyMessage(result);
                    }
                });
                break;
        }
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
        paramId = intent.getStringExtra("paramId");
        type = intent.getIntExtra("type",1);
        deviceType = intent.getIntExtra("deviceType",1);
        title = intent.getStringExtra("title");
        sn = intent.getStringExtra("sn");
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
            MyControl.circlerDialog(OssDeviceTurnOnOffActivity.this,text,msg.what);
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
            MyControl.circlerDialog(OssDeviceTurnOnOffActivity.this,text,msg.what);
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
            MyControl.circlerDialog(OssDeviceTurnOnOffActivity.this,text,msg.what);
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
            MyControl.circlerDialog(OssDeviceTurnOnOffActivity.this,text,msg.what);
        }
    };
}
