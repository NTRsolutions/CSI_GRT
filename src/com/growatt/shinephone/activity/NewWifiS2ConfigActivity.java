package com.growatt.shinephone.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.growatt.newwifi2config.EsptouchTask;
import com.growatt.newwifi2config.IEsptouchListener;
import com.growatt.newwifi2config.IEsptouchResult;
import com.growatt.newwifi2config.IEsptouchTask;
import com.growatt.newwifi2config.demo_activity.EspWifiAdminSimple;
import com.growatt.newwifi2config.task.__IEsptouchTask;
import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.OssDeviceDatalogBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.FragUtil;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.view.RippleBackground;
import com.mylhyl.circledialog.CircleDialog;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;


@ContentView(R.layout.activity_new_wifi_s2_config)
public class NewWifiS2ConfigActivity extends DemoBase implements View.OnClickListener {
    private static final String TAG = "NewWifiS2ConfigActivity";
    @ViewInject(R.id.textView1) EditText mTvApSsid;
    @ViewInject(R.id.editText1) EditText mEdtApPassword;
    @ViewInject(R.id.button2) Button mBtnConfirm;
    @ViewInject(R.id.button1) Button btnSsid;
    private EspWifiAdminSimple mWifiAdmin;
    private int mSpinnerTaskCount = 1;
    private String jumpType = "3";//3、2：采集器列表界面(跳转到Main)；1：注册添加采集器界面(跳转到Login或自动登录)；101：代表从oss跳转
    private IEsptouchTask mEsptouchTask;
    // without the lock, if the user tap confirm and cancel quickly enough,
    // the bug will arise. the reason is follows:
    // 0. task is starting created, but not finished
    // 1. the task is cancel for the task hasn't been created, it do nothing
    // 2. task is created
    // 3. Oops, the task should be cancelled, but it is running
    private final Object mLock = new Object();
    @ViewInject(R.id.content) RippleBackground rippleBackground;
    @ViewInject(R.id.centerImage) TextView tvTime;
    @ViewInject(R.id.button_stop) Button btnStop;
    private final int TIME_COUNT=200;
    int timeCount=TIME_COUNT;
    private InputMethodManager imm;
    private boolean stop = true;
    private String isNewWIFI = "0";
    private int num;//次数
    private String id;//采集器序列号
    private Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            Mydialog.Dismiss();
            switch (msg.what) {
                case 102:
                    if(!stop){
                        getDataLogInfo(FragUtil.dataLogUrl);
                    }
                    break;
                case 105:
                    timeCount--;
                    if(timeCount < 0||btnStop.getVisibility()==View.INVISIBLE){
                        handler.sendEmptyMessage(106);
                        if(timeCount<0){
                            tvTime.setVisibility(View.INVISIBLE);
                            if ("1".equals(isNewWIFI)){
                                jumpTo(Gif2Activity.class, false);
                            }else{
                                jumpTo(GifActivity.class, false);
                            }
                        }
                    }else{
                        handler.sendEmptyMessageDelayed(105, 1000);
                        tvTime.setText(timeCount+getString(R.string.WifiNewtoolAct_time_s));
                    }

                    break;
                case 106:
                    timeCount=TIME_COUNT;
                    rippleBackground.stopRippleAnimation();
                    mBtnConfirm.setVisibility(View.VISIBLE);
                    btnStop.setVisibility(View.INVISIBLE);
                    stopConfig();
                    tvTime.setText(TIME_COUNT+getString(R.string.WifiNewtoolAct_time_s));
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
    }

    private View headerView;
    private void initHeaderView() {
        headerView = findViewById(R.id.headerView);
        setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myFinish();
            }
        });
        setHeaderTitle(headerView,getString(R.string.fragment4_shinewifi) + getString(R.string.wifilist_configuration) );
    }

    /**
     * 停止配置
     */
    public void stopConfig(){
        synchronized (mLock) {
            if (__IEsptouchTask.DEBUG) {
                Log.i(TAG, "progress dialog is canceled");
            }
            if (mEsptouchTask != null) {
                mEsptouchTask.interrupt();
            }
        }
    }
    public void myFinish(){
        handler.sendEmptyMessage(106);
        if ("3".equals(jumpType)){
            jumpTo(MainActivity.class,true);
        }else if ("1".equals(jumpType)){
            MyControl.autoLogin(this, Cons.regMap.getRegUserName(),Cons.regMap.getRegPassword());
        }else if ("101".equals(jumpType)){
            finish();
        }else {
            jumpTo(MainActivity.class,true);
        }
    }
    private void initIntent() {
        Intent intent = getIntent();
        jumpType = intent.getStringExtra("jumpType");
        id = intent .getStringExtra("sn");

    }

    private void initView() {
        mWifiAdmin = new EspWifiAdminSimple(this);
        mBtnConfirm.setOnClickListener(this);
        btnSsid.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // display the connected ap's ssid
        String apSsid = mWifiAdmin.getWifiConnectedSsid();
        if (apSsid != null) {
            mTvApSsid.setText(apSsid);
        } else {
            mTvApSsid.setText("");
        }
        boolean isApSsidEmpty = TextUtils.isEmpty(apSsid);
        mBtnConfirm.setEnabled(!isApSsidEmpty);
    }
    @Override
    public void onClick(View v) {
        if(v == btnSsid){
            String apSsid = mWifiAdmin.getWifiConnectedSsid();
            if (apSsid != null) {
                mTvApSsid.setText(apSsid);
            } else {
                mTvApSsid.setText("");
            }
        }
        if (v == btnStop){
            stop=true;
            num=0;
            tvTime.setVisibility(View.INVISIBLE);
            handler.sendEmptyMessage(106);
        }
        if (v == mBtnConfirm) {
            String apSsid = mTvApSsid.getText().toString();
            if (TextUtils.isEmpty(apSsid)){
                toast(R.string.ahtool_wifi_blank);
                return;
            }
            //开始配置
            stop=false;
            //隐藏输入法
            if(imm==null){
                imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            }
            imm.hideSoftInputFromWindow(mEdtApPassword.getWindowToken(), 0);
            //开启动画
            rippleBackground.startRippleAnimation();
            mBtnConfirm.setVisibility(View.INVISIBLE);
            btnStop.setVisibility(View.VISIBLE);
            tvTime.setVisibility(View.VISIBLE);
            handler.sendEmptyMessageDelayed(105, 1000);
            String url=new Urlsutil().getDatalogInfo+"&datalogSn="+id;
            if ("101".equals(jumpType)){
                url = OssUrls.postOssSearchDevice();
            }
            String apPassword = mEdtApPassword.getText().toString();
            String apBssid = mWifiAdmin.getWifiConnectedBssid();
            String taskResultCountStr = Integer.toString(mSpinnerTaskCount);
            if (__IEsptouchTask.DEBUG) {
                Log.d(TAG, "mBtnConfirm is clicked, mEdtApSsid = " + apSsid
                        + ", " + " mEdtApPassword = " + apPassword);
            }
            new EsptouchAsyncTask3().execute(apSsid, apBssid, apPassword, taskResultCountStr);
            getDataLogInfo(url);
        }
    }
    private void onEsptoucResultAddedPerform(final IEsptouchResult result) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
//                String text = result.getBssid() + " is connected to the wifi";
//                Toast.makeText(NewWifiS2ConfigActivity.this, text,
//                        Toast.LENGTH_LONG).show();
            }

        });
    }
    private IEsptouchListener myListener = new IEsptouchListener() {

        @Override
        public void onEsptouchResultAdded(final IEsptouchResult result) {
            onEsptoucResultAddedPerform(result);
        }
    };
    private class EsptouchAsyncTask3 extends AsyncTask<String, Void, List<IEsptouchResult>> {



        @Override
        protected void onPreExecute() {

        }

        @Override
        protected List<IEsptouchResult> doInBackground(String... params) {
            int taskResultCount = 1;
            synchronized (mLock) {
                // !!!NOTICE
                String apSsid = mWifiAdmin.getWifiConnectedSsidAscii(params[0]);
                String apBssid = params[1];
                String apPassword = params[2];
                String taskResultCountStr = params[3];
                taskResultCount = Integer.parseInt(taskResultCountStr);
                mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, NewWifiS2ConfigActivity.this);
                mEsptouchTask.setEsptouchListener(myListener);
            }
            List<IEsptouchResult> resultList = mEsptouchTask.executeForResults(taskResultCount);
            return resultList;
        }

        @Override
        protected void onPostExecute(List<IEsptouchResult> result) {
            IEsptouchResult firstResult = result.get(0);
            // check whether the task is cancelled and no results received
            if (!firstResult.isCancelled()) {
                int count = 0;
                // max results to be displayed, if it is more than maxDisplayCount,
                // just show the count of redundant ones
                final int maxDisplayCount = 5;
                // the task received some results including cancelled while
                // executing before receiving enough results
                if (firstResult.isSuc()) {
                    StringBuilder sb = new StringBuilder();
                    for (IEsptouchResult resultInList : result) {
                        sb.append("成功, 路由器mac = "
                                + resultInList.getBssid()
                                + ",InetAddress = "
                                + resultInList.getInetAddress()
                                .getHostAddress() + "\n");
                        count++;
                        if (count >= maxDisplayCount) {
                            break;
                        }
                    }
                    if (count < result.size()) {
                        sb.append(getString(R.string.all_success)).append("\nthere's " + (result.size() - count)
                                + " more result(s) without showing\n");
                    }
                    toast(R.string.m107wifi连接路由器);
                } else {
                    toast(R.string.all_failed);
                }
                stopConfig();
            }
        }
    }
    public void getDataLogInfo(final String url) {
        FragUtil.dataLogUrl=url;
        if ("101".equals(jumpType)){//oss界面跳转：服务器地址为oss
            parseOssConfig(url);
        }else {//服务器地址为server
            parseServerConfig(url);
        }
    }

    private void parseServerConfig(String url) {
        if(num>=60||btnStop.getVisibility()==View.INVISIBLE){
//			handler.sendEmptyMessage(106);
            num=0;
            return;
        }
        GetUtil.get1(url, new GetUtil.GetListener() {
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    boolean lost=jsonObject.getBoolean("lost");
                    isNewWIFI = jsonObject.get("isNewWIFI").toString();
                    LogUtil.i(lost+";isNewWifi:"+isNewWIFI);
                    if(lost==true){
                        num++;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.sendEmptyMessage(102);
                            }
                        }, 5000);

                    }else{
//						IoTManager.StopSmartConnection();
                        handler.sendEmptyMessage(106);
                        num=0;
                        FragUtil.dataLogUrl=null;
                        new CircleDialog.Builder(NewWifiS2ConfigActivity.this)
                                .setWidth(0.7f)
                                .setTitle(getString(R.string.dataloggers_add_success))
                                .setText(getString(R.string.all_success_reminder))
                                .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                      myFinish();
                                    }
                                })
                                .show();
                    }
                } catch (Exception e) {e.printStackTrace();}
            }
            @Override
            public void error(String json) {}
        });
    }

    private void parseOssConfig(String url) {
        if(num>=60||btnStop.getVisibility()==View.INVISIBLE){
            num=0;
            return;
        }
        searchSnDownDeviceInfor(id,"",url,0,1);
    }
    /**
     * 根据设备sn或者别名搜索设备列表信息
     * @param deviceSn：设备序列号
     * @param alias：别名
     * @param url：服务器地址
     * @param deviceType：设备类型
     * @param page：
     */
    private void searchSnDownDeviceInfor(final String deviceSn, final String alias, final String url, final int deviceType, final int page) {
        PostUtil.post(OssUrls.postOssSearchDevice(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("deviceSn",deviceSn);
                params.put("alias",alias);
                params.put("serverAddr",OssUrls.ossCRUDUrl);
                params.put("deviceType",deviceType+"");
                params.put("page",page+"");
            }
            @Override
            public void success(String json) {
                parseDeviceInfor(json);
            }
            @Override
            public void LoginError(String str) {

            }
        });
    }
    private void parseDeviceInfor(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("result");
            if (result == 1){
                JSONObject obj = jsonObject.getJSONObject("obj");
                int deviceType = obj.getInt("deviceType");
                switch (deviceType){
                    case 0:
                        JSONArray jsonArrDatalog = obj.getJSONArray("datalogList");
                        if (jsonArrDatalog != null && jsonArrDatalog.length()>0){
                            OssDeviceDatalogBean datalogBean = new Gson().fromJson(jsonArrDatalog.getJSONObject(0).toString(), OssDeviceDatalogBean.class);
                            boolean lost = datalogBean.isLost();
                            if(lost==true){
                                num++;
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        handler.sendEmptyMessage(102);
                                    }
                                }, 5000);

                            }else{
                                handler.sendEmptyMessage(106);
                                num=0;
                                FragUtil.dataLogUrl=null;
                                new CircleDialog.Builder(NewWifiS2ConfigActivity.this)
                                        .setWidth(0.7f)
                                        .setTitle(getString(R.string.dataloggers_add_success))
                                        .setText(getString(R.string.all_success_reminder))
                                        .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                              myFinish();
                                            }
                                        })
                                        .show();
                            }
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            myFinish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
