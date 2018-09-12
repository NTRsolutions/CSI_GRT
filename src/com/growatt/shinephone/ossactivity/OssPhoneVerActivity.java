package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.growatt.shinephone.activity.DoActivity;
import com.growatt.shinephone.activity.LoginActivity;
import com.growatt.shinephone.bean.OssUserBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.SharedPreferencesUnit;
import com.growatt.shinephone.util.T;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.growatt.shinephone.activity.ShineApplication.context;

@ContentView(R.layout.activity_oss_phone_ver)
public class OssPhoneVerActivity extends DoActivity {
    @ViewInject(R.id.etPhone)
    EditText etPhone;
    @ViewInject(R.id.etVCode)
    EditText etVCode;
    @ViewInject(R.id.etAreaCode)
    EditText etAreaCode;
    @ViewInject(R.id.btnSendCode)
    Button btnSendCode;
    private String phone;
    private String areaCode;
    private String vCode;
    private  final  int TOTAL_TIME=180;
    private  int TIME_COUNT=TOTAL_TIME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeaderView();
        initView();
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
        etAreaCode.setText(MyControl.getCountryAndPhoneCodeByCountryCode(this,2));
        ss1.setSpan(ass, 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etAreaCode.setHint(new SpannedString(ss1)); // 一定要进行转换,否则属性会消失
        ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etPhone.setHint(new SpannedString(ss2)); // 一定要进行转换,否则属性会消失
        ss3.setSpan(ass, 0, ss3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etVCode.setHint(new SpannedString(ss3)); // 一定要进行转换,否则属性会消失

        //获取焦点显示输入法
        etPhone.requestFocus();
        etPhone.setFocusableInTouchMode(true);
        etPhone.requestFocus();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                              MyUtils.showKeyboard(etPhone);
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
                jumpTo(LoginActivity.class,true);
            }
        });
        setHeaderTitle(headerView,"短信验证");
    }
    @Event(value = R.id.btnSendCode)
    private void sendSms(View v){
        vCode = "";
        //隐藏输入法
        MyUtils.hideKeyboard(v);
        if(isEmpty(etAreaCode,etPhone)) return;
        phone = etPhone.getText().toString().trim();
        if(!phone.equals(Cons.ossUserBean.getPhone())){
            toast("手机号码错误");
            return;
        }
        areaCode = etAreaCode.getText().toString().trim();
        while (areaCode.startsWith("+")){
            areaCode =  areaCode.replace("+","");
        }
        while (areaCode.startsWith("0")){
            areaCode =  areaCode.replace("0","");
        }
        if (TextUtils.isEmpty(areaCode) || areaCode.length()>5 ){
            toast(R.string.m27);
            return;
        }
        //发送后对button做处理
        //先获取服务器地址
        showAfterButton();
//        SqliteUtil.url(OssUrls.ossServerUrl);
//        Cons.guestyrl = "http://" + OssUrls.ossServerUrl;
        PostUtil.post(OssUrls.postOssSmsVerification(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("phoneNum",phone);
                params.put("areaCode",areaCode);
            }

            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject=new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        toast(R.string.all_success);
                        vCode = jsonObject.getJSONObject("obj").getString("validate");
                    }else {
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
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
//            phone = etPhone.getText().toString().trim();
            if (TextUtils.isEmpty(phone)){
                toast(R.string.输入手机号);
                return;
            }
            if (TextUtils.isEmpty(code)){
                toast(R.string.m21);
                return;
            }
            if(!phone.equals(Cons.ossUserBean.getPhone())){
                toast(R.string.m99您注册时填写的手机号是);
                return;
            }
            if (code.equals(vCode)) {
                //将手机号储存
                SharedPreferencesUnit.getInstance(mContext).put(Constant.OssUser_Phone,phone);
                int role = Cons.ossUserBean.getRole();
                if ( role == OssUserBean.SUPER_ADMIN || role == OssUserBean.CUSTOMER_ADMIN || role == OssUserBean.CUSTOMER_PERSON){
                    startActivity(new Intent(this, OssKeFuActivity.class));
                }else if (role == OssUserBean.DISTRIBUTOR_PERSON || role == OssUserBean.DISTRIBUTOR_NO_PERFECT || role == OssUserBean.INSTALLER_PERSON || role ==OssUserBean.INSTALLER_NO_PERFECT){
                    startActivity(new Intent(this, OssJKActivity.class));
                }else {
                    T.make(R.string.dataloggers_add_no_jurisdiction,context);
                    jumpTo(LoginActivity.class,true);
                }
            }else {
                toast(R.string.m22);
            }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            jumpTo(LoginActivity.class,true);
        }
        return super.onKeyDown(keyCode, event);
    }
}
