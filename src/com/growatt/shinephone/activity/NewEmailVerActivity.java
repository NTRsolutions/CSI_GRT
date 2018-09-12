package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.growatt.shinephone.R;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnCirclerDialogListener;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

;

@ContentView(R.layout.activity_new_email_ver)
public class NewEmailVerActivity extends DemoBase {
    @ViewInject(R.id.etEmail)
    EditText etEmail;
    @ViewInject(R.id.etVCode)
    EditText etVCode;
    @ViewInject(R.id.btnSendCode)
    Button btnSendCode;
    private String email;
//    private String areaCode;
    private String vCode;
    private  final  int TOTAL_TIME=180;
    private  int TIME_COUNT=TOTAL_TIME;
    //跳转而来的信息
    private String jumpEmail;
    private int type;//跳转过来的类型:100:代表server验证
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
    }
    private void initIntent() {
        Intent intent  = getIntent();
        jumpEmail = intent.getStringExtra("email");
        type = intent.getIntExtra("type",100);
        etEmail.setText(jumpEmail);
    }
    private void initView() {
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan((int)getResources().getDimension(R.dimen.x10),false);
// 新建一个可以添加属性的文本对象
        //国家编码
        SpannableString ss1 = new SpannableString(getString(R.string.m42));
        //手机号
        SpannableString ss2 = new SpannableString(getString(R.string.m26));
        //验证码
        SpannableString ss3 = new SpannableString(getString(R.string.m21));
        ss1.setSpan(ass, 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etEmail.setHint(new SpannedString(ss2)); // 一定要进行转换,否则属性会消失
        ss3.setSpan(ass, 0, ss3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etVCode.setHint(new SpannedString(ss3)); // 一定要进行转换,否则属性会消失

        //获取焦点显示输入法
        etEmail.requestFocus();
        etEmail.setFocusableInTouchMode(true);
        etEmail.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               MyUtils.showKeyboard(etEmail);
                           }
                       },
                200);
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
        setHeaderTitle(headerView,getString(R.string.m98验证用户邮箱));
    }
    @Event(value = R.id.btnSendCode)
    private void sendSms(View v){
        vCode = "";
        //隐藏输入法
        MyUtils.hideKeyboard(v);
        if(isEmpty(etEmail)) return;
        email = etEmail.getText().toString().trim();
        //发送后对button做处理
        //先获取服务器地址
        showAfterButton();
        PostUtil.post(new Urlsutil().postValPhoneOrEmail, new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("type","0");
                params.put("content",email);
            }

            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject=new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        vCode = jsonObject.getJSONObject("obj").getString("validate");
                        toast(R.string.all_success);
                    }else {
                        mHandler.sendEmptyMessage(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void LoginError(String str) {
                LogUtil.i("error: "+str);
            }
        });
    }
    //显示灰色button
    private void showAfterButton() {
        if (btnSendCode.isEnabled() == true) {
            btnSendCode.setEnabled(false);
            btnSendCode.setBackgroundColor(getResources().getColor(R.color.none_color2));
        }
        //显示文本
        btnSendCode.setText(TIME_COUNT + getString(R.string.WifiNewtoolAct_time_s));
        //发送消息
        handler.sendEmptyMessageDelayed(102,1000);
    }
    //显示正常button
    private void showBeforeButton() {
        btnSendCode.setEnabled(true);
        btnSendCode.setBackgroundColor(getResources().getColor(R.color.btn_top));
        TIME_COUNT = TOTAL_TIME;
        //显示文本
        btnSendCode.setText(R.string.m23);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String errorCode = (String) msg.obj;
            switch (msg.what){
                case 101://发送验证码
                    if ("501".equals(errorCode)){
                        toast(R.string.m30);
                    }else if ("502".equals(errorCode)){
                        toast(R.string.m18);
                    }else if ("503".equals(errorCode)){
                        toast(R.string.m31);
                    }
                    break;
                case 102://发送验证码后倒计时
                    TIME_COUNT--;
                    if (TIME_COUNT <= 0){
                        showBeforeButton();
                    }else {
                        showAfterButton();
                    }
                    break;
            }
        }
    };
    @Event(value = R.id.btnOk)
    private void finish(View v){
            String code = etVCode.getText().toString().trim();
            if (TextUtils.isEmpty(email)){
                toast(R.string.输入邮箱地址);
                return;
            }
            if (TextUtils.isEmpty(code)){
                toast(R.string.m21);
                return;
            }
            if (code.equals(vCode)) {
                //更新用户验证信息
                updateUserEmail();
            }else {
                toast(R.string.m22);
            }
    }

    private void updateUserEmail() {
        Mydialog.Show(this);
        PostUtil.post(new Urlsutil().postUpdateVal, new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("type","0");
                params.put("userName", Cons.userBean != null ? Cons.userBean.getAccountName():"");
                params.put("content",email);
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        Cons.isValiEmail = true;
                        Cons.userBean.setEmail(email);
                        MyControl.circlerDialog(NewEmailVerActivity.this, getString(R.string.all_success), result, new OnCirclerDialogListener() {
                            @Override
                            public void onCirclerPositive() {
                                setResult(RESULT_OK);
                                finish();
                            }
                        });
                    }else {
                        mHandler.sendEmptyMessage(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {
            }
        });
    }

    Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){

            }
            MyControl.circlerDialog(NewEmailVerActivity.this,getString(R.string.all_failed),msg.what,false);
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
