package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * 市电电压设置：只有一个参数设置界面
 */
@ContentView(R.layout.activity_oss_pvgrid_single_set)
public class OssPVGridSingleSetActivity extends DemoBase {
    @ViewInject(R.id.tvName)
    TextView tvName;
    @ViewInject(R.id.etValue)
    EditText etValue;
    @ViewInject(R.id.btnOk)
    Button btnOk;
    //跳转而来的数据
    private String sn;//设备sn
    private String paramId;
    private String title;
    /**
     * //由哪里跳转而来：1--oss逆变器市电电压上限和下限设置；2--server逆变器市电电压上限和下限设置；3--server Max;4--server JLINV锦浪
     */
    private int type = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
    }

    private void initView() {
        tvName.setText(title);
    }

    @Event(value = R.id.btnOk)
    private void btnOk(View v){
        final String time = etValue.getText().toString().trim();
        if (TextUtils.isEmpty(time)) {
            toast(R.string.all_blank);
            return;
        }
        new CircleDialog.Builder(this)
                .setWidth(0.7f)
                .setTitle(getString(R.string.警告))
                .setText(title + ":" + time)
                .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (type){
                            case 1:
                                ossInvPVGrid(time);
                                break;
                            case 2:
                                serverInvPVGrid(time);
                                break;
                            case 4:
                                serverJLINVPVGrid(time);
                                break;
                        }
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }

    private void serverInvPVGrid(String time) {
        MyControl.invSetServer(this, sn, paramId, time, "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
//                OssUtils.showOssDialog(OssPVGridSingleSetActivity.this,msg,result,true,null);
                handlerInvServer.sendEmptyMessage(result);
            }
        });
    }
    private void serverJLINVPVGrid(String time) {
        MyControl.invSetJLINVServer(this, sn, paramId, time, "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                handlerInvServer.sendEmptyMessage(result);
            }
        });
    }

    private void ossInvPVGrid(String time) {
        MyControl.invSet(this, sn, paramId, time, "", new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                OssUtils.showOssDialog(OssPVGridSingleSetActivity.this,msg,result,true,null);
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
            MyControl.circlerDialog(OssPVGridSingleSetActivity.this,text,msg.what);
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
            MyControl.circlerDialog(OssPVGridSingleSetActivity.this,text,msg.what);
        }
    };
}
