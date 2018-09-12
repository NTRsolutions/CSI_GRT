package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_oss_rfstick)
public class OssRFStickActivity extends DemoBase {
    @ViewInject(R.id.tvValue1)
    TextView tvValue1;//采集器序列号
    @ViewInject(R.id.tvValue2)
    TextView tvValue2;//采集器校验码
    @ViewInject(R.id.etValue3)
    EditText etValue3;//rf stick序列号
    private int jumpType;//100:由用户界面；101：由oss界面
    private String datalogSn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
    }
@Event(value = R.id.btnOk)
private void btnOk(View v){
    String rfSn = etValue3.getText().toString().trim();
    if (TextUtils.isEmpty(rfSn)){
        toast(R.string.all_blank);
        return;
    }
    String url = "";
    switch (jumpType){
        case 100:
            url = new Urlsutil().postSinglepairRFStick;
            break;
        case 101:
            url = OssUrls.postOssRFStick();
            break;
    }
    MyControl.configRFStick(this, url, datalogSn, rfSn, new OnHandlerListener() {
        @Override
        public void handlerDeal(int result,String msg) {
            switch (jumpType){
                case 100:
                    handlerRF.sendEmptyMessage(result);
                    break;
                case 101:
                    OssUtils.showOssDialog(OssRFStickActivity.this,msg,result,true,null);
                    break;
            }
//            handlerRF.sendEmptyMessage(result);
        }
    });
}
    private void initView() {
        tvValue1.setText(datalogSn);
        tvValue2.setText(AppUtils.validateWebbox(datalogSn));
    }
    private void initIntent() {
        Intent intent = getIntent();
        jumpType = intent.getIntExtra("jumpType",100);
        datalogSn = intent.getStringExtra("datalogSn");
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
        setHeaderTitle(headerView,getString(R.string.dataloggers_dialog_Configuration));
    }
    //oss删除handler
    private Handler handlerRF = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 0:text = getString(R.string.all_failed);break;
                case 1:text = getString(R.string.all_success);break;
                default:
                    text = getString(R.string.all_failed);break;
            }
            MyControl.circlerDialog(OssRFStickActivity.this,text,msg.what,true);
        }
    };
}
