package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.activity.LoginActivity;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.SharedPreferencesUnit;
import com.mylhyl.circledialog.CircleDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

@ContentView(R.layout.activity_oss_main)
public class OssMainActivity extends DemoBase {
    @ViewInject(R.id.ossLogout)
    Button ossLogout;
    private long mExitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeaderView();
    }
    private View headerView;
    private void initHeaderView() {
        headerView = findViewById(R.id.headerView);
        setHeaderTitle(headerView,"OSS系统");
    }
    @Event(type = View.OnClickListener.class,value = R.id.ivGongDan)
    private void ivGongDan(View v){
        if (Cons.ossUserBean != null && Cons.ossUserBean.getRole() == 5){//集成商界面
            initAgentList();
        }else {
            jumpTo(OssGongDanActivity.class,false);
        }
    }
    /**
     * 获取代理商列表
     */
    private void initAgentList() {
        Mydialog.Show(this,"");
        PostUtil.post(OssUrls.postOssJKAgentList(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {}
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        Intent intent = new Intent(OssMainActivity.this,OssJkMainActivity.class);
                        intent.putExtra("json",json);
                        startActivity(intent);
                    }else {
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
//                        MyControl.circlerDialog(OssMainActivity.this, getString(R.string.datalogcheck_check_no_server), result, false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {}
        });
    }
    @Event(type = View.OnClickListener.class,value =  R.id.ivDevice)
    private void ivDevice(View v){
        jumpTo(OssDeviceActivity.class,false);
    }
    @Event(value = R.id.ossLogout)
    private void ossLogout(View v){
        ossLogout.setTextColor(ContextCompat.getColor(mContext,R.color.oss_logout_click));
        new CircleDialog.Builder(OssMainActivity.this)
                .setWidth(0.7f)
                .setTitle(getString(R.string.温馨提示))
                .setText(getString(R.string.user_islogout))
                .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutOss();
                    }
                })
                .setNegative(getString(R.string.all_no), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ossLogout.setTextColor(ContextCompat.getColor(mContext,R.color.oss_logout_normal));
                    }
                })
                .setCancelable(false)
                .show();
    }

    /**
     * 注销oss账号，退出oss系统
     */
    private void logoutOss() {
        SqliteUtil.url("");
        SqliteUtil.plant("");
        SharedPreferencesUnit.getInstance(mContext).put(Constant.OssUser_Phone,"0");
        jumpTo(LoginActivity.class,true);
    }

}
