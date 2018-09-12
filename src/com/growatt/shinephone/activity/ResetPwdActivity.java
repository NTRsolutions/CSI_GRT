package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.growatt.shinephone.R;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.util.Map;

public class ResetPwdActivity extends DoActivity {
    private View headerView;
    private EditText etPwd;
    private EditText etRePwd;
    private Button btnOk;
    private String phone;
    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        initIntent();
        initHeaderView();
        initView();
        initListener();
    }

    private void initIntent() {
       Intent intent= getIntent();
        phone = intent.getStringExtra("phone");
        code = intent.getStringExtra("code");
    }

    private void initListener() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPwd();
            }
        });
    }

    private void resetPwd() {
        final String pwd = etPwd.getText().toString().trim();
        final String rePwd = etRePwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)){
            toast(R.string.Login_password_hint);
            return;
        }
        if (TextUtils.isEmpty(rePwd)){
            toast(R.string.Login_password_hint);
            return;
        }
        if (!pwd.equals(rePwd)){
            toast(R.string.register_password_no_same);
            return;
        }
        //获取服务器地址后重置密码
        Mydialog.Show(this,"");
        btnOk.setEnabled(false);
        GetUtil.getParams(new Urlsutil().getUserServerUrl, new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("userName", phone);
            }
            @Override
            public void success(String json) {
                try {
                JSONObject  jsonObject = new JSONObject(json);
                String success=jsonObject.get("success").toString();
                if(success.equals("true")) {
                    String msg = jsonObject.getString("msg");
                    SqliteUtil.url(msg);
                    Cons.guestyrl = "http://" + msg;
                    PostUtil.post(Cons.guestyrl+new Urlsutil().postRestartUserPassword2, new PostUtil.postListener() {
//                    PostUtil.post(new Urlsutil().serverurl +Urlsutil.getInstance().postRestartUserPassword2, new PostUtil.postListener() {
                        @Override
                        public void Params(Map<String, String> params) {
                            params.put("phoneNum",phone);
                            params.put("password",pwd);
                            params.put("validate",code);
                        }

                        @Override
                        public void success(String json) {
                            Mydialog.Dismiss();
                            btnOk.setEnabled(true);
                            try {
                                JSONObject jsonObj=new JSONObject(json);
                                String result = jsonObj.opt("result").toString();
                                if("1".equals(result)){
                                    toast(R.string.修改成功);
                                    jumpTo(LoginActivity.class,true);
                                }else {
                                    String msg = jsonObj.getString("msg");
                                    if ( !TextUtils.isEmpty(msg)){
                                        Message message = Message.obtain();
                                        message.obj = msg;
                                        message.what = 102;
                                        handler.sendMessage(message);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void LoginError(String str) {
                            Mydialog.Dismiss();
                            btnOk.setEnabled(true);
                            LogUtil.i( "error: "+str);
                        }
                    });
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void LoginError(String str) {
                Mydialog.Dismiss();
            }
        });

    }

    private void initView() {
        etPwd = (EditText) findViewById(R.id.etPwd);
        etRePwd = (EditText) findViewById(R.id.etRePwd);
        btnOk = (Button) findViewById(R.id.btnOk);
    }

    private void initHeaderView() {
        headerView = findViewById(R.id.headerView);
        setHeaderTitle(headerView,getString(R.string.retrievepwd_title));
        setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String errorCode = (String) msg.obj;
            switch (msg.what){
                case 102://根据手机号修改密码
                    if ("502".equals(errorCode)){
//                        toast("手机号码或者密码为空");
                    }else if ("503".equals(errorCode)){
                        toast(R.string.m31);
                    }else if ("504".equals(errorCode)){
                        toast(R.string.m33);
                    }else if ("505".equals(errorCode)){
                        toast(R.string.m32);
                    }else {
                        toast(errorCode);
                    }
                    break;
            }
        }
    };
}
