package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.growatt.shinephone.R;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.util.Map;


public class ChangPhoneActivity extends DoActivity {
    private EditText etAreaCode;
    private EditText etPhone;
    private EditText etVCode;
    private Button btnSendCode;
    private Button btnOk;
    private View headerView;
    private String phone;
    private String areaCode;
    private String vCode;
    private boolean canClick = true;
    private  final  int TOTAL_TIME=120;
    private  int TIME_COUNT=TOTAL_TIME;

    private String type;
    private String PhoneNum;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reback_pwd_by_phone);
        initHeaderView();
        initView();
        initListener();
    }

    private void initHeaderView() {
        Bundle bundle=getIntent().getExtras();
        PhoneNum=bundle.getString("PhoneNum");
        email=bundle.getString("email");
        type=bundle.getString("type");
        headerView = findViewById(R.id.headerView);
        setHeaderTitle(headerView,getString(R.string.retrievepwd_title));
        setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListener() {
        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showAfterButton();//测试
                sendVCode();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = etVCode.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    toast(R.string.m21);
                    return;
                }
                if (TextUtils.isEmpty(code)){
                    toast(R.string.m21);
                    return;
                }
                if (code.equals(vCode)) {
                    {
                        if(Cons.isflag==true){
                            toast(R.string.all_experience);
                            return;
                        }
                        Mydialog.Show(ChangPhoneActivity.this,"");
                        PostUtil.post(Urlsutil.getInstance().updateUser, new PostUtil.postListener() {

                            @Override
                            public void success(String json) {
                                Mydialog.Dismiss();
                                try {
                                    JSONObject jsonObject=new JSONObject(json);
                                    if(jsonObject.get("success").toString().equals("true")){

                                        Intent intent=new Intent(ChangPhoneActivity.this,UserActivity.class);
                                        Bundle bundle=new Bundle();
                                        if(type.equals("1")){
                                            bundle.putString("PhoneNum", phone);
                                            bundle.putString("email", email);
                                            intent.putExtras(bundle);
                                            setResult(1, intent);
                                        }else if(type.equals("2")){
                                            bundle.putString("PhoneNum", phone);
                                            bundle.putString("email", email);
                                            intent.putExtras(bundle);
                                            setResult(2, intent);
                                        }
                                        toast(R.string.all_success);
                                        finish();
                                    } else if ("701".equals(jsonObject.get("msg").toString())){
                                        toast(R.string.m7);
                                    }else{
                                        toast(R.string.all_failed);
                                    }
                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void Params(Map<String, String> params) {
                                params.put("accountName", Cons.userBean.accountName);
                                if(type.equals("1")){
                                    params.put("phoneNum",  phone);
                                    params.put("email",  email);
                                }else{
                                    params.put("phoneNum",  phone);
                                    params.put("email",  email);
                                }
                            }

                            @Override
                            public void LoginError(String str) {
                                // TODO Auto-generated method stub

                            }
                        });

                    }
                }else {
                    toast(R.string.m22);
                }
            }
        });

    }

    private void sendVCode() {
        vCode = "";
        if(isEmpty(etAreaCode,etPhone)) return;
        phone = etPhone.getText().toString().trim();
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
        PostUtil.post(new Urlsutil().postSmsVerification, new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("phoneNum",phone);
                params.put("areaCode",areaCode);
            }

            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObj=new JSONObject(json);
                    String result = jsonObj.opt("result").toString();
                    if("1".equals(result)){

                        Object opt = jsonObj.opt("obj");
                        if(opt!=null){
                            JSONObject obj = jsonObj.getJSONObject("obj");
                            vCode = obj.optString("validate");
                            String statusCode = obj.getString("statusCode");
                            if ("200".equals(statusCode)){
                                toast(R.string.all_success);
                                //发送成功后对button做处理
                                showAfterButton();
                            }else {
                                String code = obj.getString("code");
                                if ("9003".equals(code)){
                                    toast(R.string.m28);
                                }else if ("9004".equals(code)){
                                    toast(R.string.m29);
                                }
                            }
                        }
                    }else {
                        String msg = jsonObj.getString("msg");
                        if ( !TextUtils.isEmpty(msg)){
                            Message message = Message.obtain();
                            message.obj = msg;
                            message.what = 101;
                            handler.sendMessage(message);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void LoginError(String str) {
                LogUtil.i( "error: "+str);
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

        etAreaCode = (EditText) findViewById(R.id.etAreaCode);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etVCode = (EditText) findViewById(R.id.etVCode);
        btnSendCode = (Button) findViewById(R.id.btnSendCode);
        btnOk = (Button) findViewById(R.id.btnOk);
        etAreaCode.setText(MyControl.getCountryAndPhoneCodeByCountryCode(this,2));
        etPhone.requestFocus();

        ss1.setSpan(ass, 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etAreaCode.setHint(new SpannedString(ss1)); // 一定要进行转换,否则属性会消失
        ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etPhone.setHint(new SpannedString(ss2)); // 一定要进行转换,否则属性会消失
        ss3.setSpan(ass, 0, ss3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etVCode.setHint(new SpannedString(ss3)); // 一定要进行转换,否则属性会消失
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
}
