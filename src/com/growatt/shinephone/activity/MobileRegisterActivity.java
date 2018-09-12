package com.growatt.shinephone.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@ContentView(R.layout.activity_mobile_register)
public class MobileRegisterActivity extends DoActivity {
    @ViewInject(R.id.etAgent) EditText etAgent;
    private View headerView;
    private EditText etPwd;
    private EditText etPhone;
    private Button btnRegister;
    private String sn;
    private CheckBox checkBox;
    private TextView terms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
        initListener();
    }
    private void initIntent() {
        Intent intent= getIntent();
        if (intent != null) {
            sn = intent.getStringExtra("sn");
        }
    }
    private void initListener() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = etPhone.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    toast(R.string.m18);
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                    toast(R.string.m20);
                    return;
                }
                if(!checkBox.isChecked()){
                    toast(R.string.all_terms_message);
                    return;
                }
                if (phone.length() != 11){
                    toast(R.string.m57);
                    return;
                }
                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(phone);
                if( !m.matches() ){
                    toast(R.string.m56);
                    return;
                }
                register(phone,pwd);
            }
        });
        terms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(MobileRegisterActivity.this,AgreementActivity.class));
            }
        });
    }

    private void register(final String phone, final String pwd) {
        Mydialog.Show(this,"");
        final String country = MyControl.getCountryAndPhoneCodeByCountryCode(this,1);
        //根据国家获取服务器地址
        GetUtil.get(new Urlsutil().getServerUrl+"&country="+ country, new GetUtil.GetListener() {

            @Override
            public void success(String json) {
                Mydialog.Dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(json);
                    String success=jsonObject.get("success").toString();
                    if(success.equals("true")){
                        String msg=jsonObject.getString("server");
                        System.out.println(msg);
                        Cons.url=msg;
                        SqliteUtil.url(msg);
                        //注册
                        registerByCountry(country,phone,pwd);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String json) {

            }
        });

    }

    private void registerByCountry(final  String country, final String phone, final String pwd) {
        Mydialog.Show(this,"");
        PostUtil.post(new Urlsutil().postMobileRegister, new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("regUserName",phone);
                params.put("regPassword",pwd);
//                if ((!TextUtils.isEmpty(sn)) && sn.length()==10){
//                    params.put("regDataLoggerNo",sn);
//                    params.put("regValidateCode", AppUtils.validateWebbox(sn));
//                }
                if(!TextUtils.isEmpty(sn)){
                    params.put("regDataLoggerNo",sn);
                }
                params.put("regLanguage",getLanguageStr());
                params.put("regCountry",country);
                params.put("agentCode",etAgent.getText().toString());
//                log(phone+":"+pwd+":"+sn+":"+AppUtils.validateWebbox(sn)+":"+getLanguageStr()+":"+country);
            }

            @Override
            public void success(String json) {
                Mydialog.Dismiss();
                //注册后操作
                registerOperation(json,phone,pwd);

            }

            @Override
            public void LoginError(String str) {
            }
        });
    }

    private void registerOperation(String json,String phone,String pwd) {
            try {
                //将用户名和密码保存
                Cons.regMap.setRegUserName(phone);
                Cons.regMap.setRegPassword(pwd);
                JSONObject jsonObject=new JSONObject(json).getJSONObject("back");
                String msg=jsonObject.optString("msg");
                if(jsonObject.opt("success").toString().equals("true")){
                    if(msg.equals("200")){
                        toast(R.string.DatalogCheckActivity_regist_success);
                        String type=jsonObject.get("datalogType").toString();
                        type=type.toLowerCase();
                        if (Constant.WiFi_Type_ShineWIFI.equals(type) || Constant.WiFi_Type_ShineWIFI_S.equals(type)){
                            MyUtils.configWifi(MobileRegisterActivity.this,type,"1",sn);
                        }else {
                            MyControl.autoLogin(MobileRegisterActivity.this, phone,pwd);
                        }
//                        if(type.contains("shinewifi")){
//                            ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//                            NetworkInfo.State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
//                            if(wifi == NetworkInfo.State.CONNECTED||wifi== NetworkInfo.State.CONNECTING){
//                                Map<String, Object> map = new GetWifiInfo(MobileRegisterActivity.this).Info();
//                                if(map.get("mAuthString").toString().equals("")){
//                                    AlertDialog builder = new AlertDialog.Builder(MobileRegisterActivity.this).
//                                            setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_connectwifi).
//                                            setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
//
//                                                @Override
//                                                public void onClick(DialogInterface arg0, int arg1) {
//                                                    arg0.dismiss();
//                                                }
//                                            }).create();
//                                    builder.show();
//                                    return;
//                                }
//                                Intent intent=new Intent(MobileRegisterActivity.this,SmartConnection.class);
//                                Bundle bundle=new Bundle();
//                                bundle.putString("type","1");
//                                bundle.putString("id",sn);
//                                bundle.putString("ssid",map.get("ssid").toString());
//                                bundle.putString("mAuthString",map.get("mAuthString").toString());
//                                bundle.putByte("mAuthMode",(Byte) map.get("mAuthMode"));
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//
//                            }else{
////											T.make(R.string.dataloggers_dialog_connectwifi);
//                                Intent intent=new Intent(MobileRegisterActivity.this,RegsuecessActivity.class);
//                                Bundle bundle=new Bundle();
//                                bundle.putString("sn", sn);
//                                bundle.putString("type", type);
//                                bundle.putString("act", "datalogcheck");
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                                finish();
//                            }
//                        }else{
//                            MyControl.autoLogin(MobileRegisterActivity.this, phone,pwd);
//                        }
                    }
                }else{

                    if(msg.equals("501")){
                        toast(R.string.datalogcheck_check_no_overstep);
                        return;
                    }
                    if(msg.equals("502")){
                        MyControl.putAppErrMsg("注册:"+phone+"-msg:"+msg,MobileRegisterActivity.this);
                        toast(R.string.datalogcheck_check_no_server);
                        return;
                    }
                    if(msg.equals("503")){
                        toast(R.string.datalogcheck_check_no_userexist);
                        return;
                    }
                    if(msg.equals("602")){
                        MyControl.putAppErrMsg("注册:"+phone+"-msg:"+msg,MobileRegisterActivity.this);
                        toast(R.string.datalogcheck_code_602);
                        return;
                    }
                    if(msg.equals("506")){
                        toast(R.string.datalogcheck_check_no_verification);
                        return;
                    }
                    if(msg.equals("603")){
                        toast(R.string.datalogcheck_check_add_datalog_err);
                        return;
                    }
                    if(msg.equals("604")){
                        toast(R.string.datalogcheck_check_no_agentcode);
                        return;
                    }
                    if(msg.equals("605")){
                        toast(R.string.datalogcheck_check_no_datalog_exist);
                        return;
                    }
                    if(msg.equals("606")){
                        toast(R.string.datalogcheck_check_no_datalog_server);
                        return;
                    }
                    if(msg.equals("607")){
                        toast(R.string.datalogcheck_check_no_datalog_server);
                        return;
                    }

                    if(msg.equals("504")){
                        toast(R.string.DatalogCheckAct_username_pwd_empty);
                        return;
                    }
                    if(msg.equals("505")){
                        toast(R.string.DatalogCheckAct_email_empty);
                        return;
                    }
                    if(msg.equals("509")){
                        toast(R.string.DatalogCheckAct_country_empty);
                        return;
                    }
                    if(msg.equals("608")){
                        toast(R.string.datalogcheck_code_608);
                        return;
                    }
                    if(msg.equals("609")){
                        toast(R.string.datalogcheck_code_609);
                        return;
                    }
                    if(msg.equals("701")){
                        MyControl.putAppErrMsg("注册:"+phone+"-msg:"+msg,MobileRegisterActivity.this);
                        toast(R.string.datalogcheck_code_701);
                        return;
                    }
                    if(msg.equals("702")){
                        toast(R.string.datalogcheck_code_702);
                        return;
                    }
                    if(msg.equals("507")){
                        toast(R.string.datalogcheck_check_no_agentcode);
                        return;
                    }
                    MyControl.putAppErrMsg("注册:"+phone+"-msg:"+msg,MobileRegisterActivity.this);
                    toast(msg+":"+getString(R.string.datalogcheck_check_no_server));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    private void initView() {
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPwd = (EditText) findViewById(R.id.etPwd);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        checkBox=(CheckBox)findViewById(R.id.checkBox1);
        terms=(TextView)findViewById(R.id.textView4);
        terms.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); //下划线
        terms.getPaint().setAntiAlias(true);//抗锯齿

        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan((int)getResources().getDimension(R.dimen.x10),false);
// 新建一个可以添加属性的文本对象
        //手机号
        SpannableString ss1 = new SpannableString(getString(R.string.m26));
        //密码
        SpannableString ss2 = new SpannableString(getString(R.string.Login_password_hint));
        //代理商
        SpannableString ss3 = new SpannableString(getString(R.string.userdata_message));

        ss1.setSpan(ass, 0, ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etPhone.setHint(new SpannedString(ss1)); // 一定要进行转换,否则属性会消失
        ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etPwd.setHint(new SpannedString(ss2)); // 一定要进行转换,否则属性会消失
        ss3.setSpan(ass, 0, ss3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        etAgent.setHint(new SpannedString(ss3)); // 一定要进行转换,否则属性会消失
    }

    private void initHeaderView() {
        headerView = findViewById(R.id.headerView);
        setHeaderTitle(headerView,getString(R.string.all_register));
        setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
