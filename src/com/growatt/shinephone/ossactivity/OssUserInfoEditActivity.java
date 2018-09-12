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


@ContentView(R.layout.activity_oss_user_info_edit)
public class OssUserInfoEditActivity extends DemoBase {
    @ViewInject(R.id.tvName) TextView tvName;
    @ViewInject(R.id.etValue) EditText etValue;
    @ViewInject(R.id.btnOk) Button btnOk;

    //跳转数据
    private String title;
    private String value;
    private int position;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
    }
@Event(R.id.btnOk)
private void btnOk(View v){
    final String newValue = etValue.getText().toString().trim();
    if (TextUtils.isEmpty(newValue)){
        toast(R.string.all_blank);
        return;
    }
    StringBuilder sb = new StringBuilder().append(title).append(":").append(newValue);
    new CircleDialog.Builder(this)
            .setWidth(0.7f)
            .setTitle(getString(R.string.警告))
            .setText(sb.toString())
            .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position){
                        case 0:
                            editUserInfo(userName,"","","",newValue,"","","0");
                            break;
                        case 1:
                            editUserInfo(userName,newValue,"","","","","","0");
                            break;
                        case 2:
                            editUserInfo(userName,"","","","","",newValue,"0");
                            break;
                        case 3:
                            editUserInfo(userName,"",newValue,"","","","","0");
                            break;
                        case 4:
                            editUserInfo(userName,"","","","",newValue,"","0");
                            break;
                    }
                }
            })
            .setNegative(getString(R.string.all_no),null)
            .show();
}

    private void editUserInfo( String userName,  String userEmail,  String userTimezone,  String userLanguage,  String userActiveName,  String userCompanyName,  String userPhoneNum,  String userEnableResetPass) {
        MyControl.editOssUserInfo(this, userName, userEmail, userTimezone, userLanguage, userActiveName, userCompanyName, userPhoneNum, userEnableResetPass, new OnHandlerListener() {
            @Override
            public void handlerDeal(int result,String msg) {
                OssUtils.showOssDialog(OssUserInfoEditActivity.this,msg,result,true,null);
//                handlerUserInfo.sendEmptyMessage(result);
            }
        });
    }

    private void initView() {
        tvName.setText(title);
        etValue.setText(value);
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
        title = intent.getStringExtra("title");
        value = intent.getStringExtra("value");
        userName = intent.getStringExtra("userName");
        position = intent.getIntExtra("position",0);
    }
    //oss用户信息handler
    private Handler handlerUserInfo = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 0:text = getString(R.string.datalogcheck_check_no_server);break;
                case 1:text = getString(R.string.修改成功);break;
                case 2:text =  "修改用户信息失败";break;
                case 3:text = "服务器地址为空";break;
                case 4:text = "邮箱为空";break;
                case 5:text = "用户不存在";break;
            }
            MyControl.circlerDialog(OssUserInfoEditActivity.this,text,msg.what);
        }
    };
}
