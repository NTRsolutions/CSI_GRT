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
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.mylhyl.circledialog.CircleDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_oss_edit)
public class OssEditActivity extends DemoBase {
    @ViewInject(R.id.tvValueSn)
    TextView tvValueSn;
    @ViewInject(R.id.etValueAlias)
    EditText etValueAlias;
    private  String sn;
    private int deviceType;//0:oss采集器编辑；1：oss逆变器编辑；2：oss储能机编辑;3:oss集成商逆变器编辑；4：oss集成商储能机编辑
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
    }

    private void initIntent() {
        Intent intent = getIntent();
        sn = intent.getStringExtra("sn");
        deviceType = intent.getIntExtra("deviceType",0);
        String alias = intent.getStringExtra("alias");
        tvValueSn.setText(sn);
        etValueAlias.setText(alias);
    }
    @Event(value = R.id.btnOk)
    private void btnOk(View v){
        final String newAlias = etValueAlias.getText().toString().trim();
        if (TextUtils.isEmpty(newAlias)){
            toast(R.string.all_blank);
        }else {
            new CircleDialog.Builder(this)
                    .setWidth(0.7f)
                    .setTitle(getString(R.string.警告))
                    .setText(getString(R.string.dataloggers_dialog_change) + ":" + newAlias)
                    .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (deviceType){
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    ossEditAlise(newAlias);
                                    break;
                            }
                        }
                    })
                    .setNegative(getString(R.string.all_no),null)
                    .show();
        }
    }

    private void ossEditAlise(String newAlias) {
        MyControl.deviceEdit(this, sn, newAlias, "", deviceType, new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                OssUtils.showOssDialog(OssEditActivity.this,msg,result,true,null);
//                handlerOssEdit.sendEmptyMessage(result);
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
        setHeaderTitle(headerView,getString(R.string.fragment1_edit));
    }
    //oss编辑handler
    private Handler handlerOssEdit = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 0:text = "修改失败";break;
                case 1:text = "修改成功";break;
                case 2:text = "远程更新数据错误";break;
                case 3:text = "设备类型为空";break;
                case 4:text = "服务器地址为空";break;

            }
            MyControl.circlerDialog(OssEditActivity.this,text,msg.what);
        }
    };
}
