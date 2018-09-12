package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.adapter.OssDatalogSetAdapter;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigInput;
import com.mylhyl.circledialog.params.InputParams;
import com.mylhyl.circledialog.view.listener.OnInputClickListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;
import java.util.Map;

@ContentView(R.layout.activity_datalog_set)
public class OssDatalogSetActivity extends DemoBase {
    //result code:服务器返回码
    private final int result_err = 0;//返回异常
    private final int result_normal = 1;//返回正常
    private final int result_serverErr = 2;//采集器服务器错误
    private final int result_datalogOffline = 3;//采集器当前不在线
    private final int result_serverUrlNull = 11;//服务器地址为空
    private final int result_RemoteErr = 12;//远程设置错误
    @ViewInject(R.id.recycleView)
    RecyclerView mRecyclerView;
    private OssDatalogSetAdapter mAdapter;
    private String[] datas ={"设置IP","设置域名","重启采集器","清除采集器记录","高级设置"};
    private String[] paramIds ={"1","2","4","3","0"};
    private String datalogSn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initRecyclerView();
        initListener();
    }

    private void initIntent() {
        Intent intent = getIntent();
        datalogSn = intent.getStringExtra("datalogSn");
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
                switch (position){
                    case 0://设置ip
                        new CircleDialog.Builder(OssDatalogSetActivity.this)
                                .setWidth(0.7f)
                                .setCancelable(false)
                                .setTitle(datas[position])
                                .configInput(new ConfigInput() {
                                    @Override
                                    public void onConfig(InputParams params) {
                                        params.hintText = "请输入需要设置的IP地址";
                                        params.inputHeight = (int) mContext.getResources().getDimension(R.dimen.y20);
                                    }
                                })
                                .setPositiveInput(getString(R.string.all_ok), new OnInputClickListener() {
                                    @Override
                                    public void onClick(String text, View v) {
                                        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(text.trim())){toast(R.string.all_blank);}
                                        datalogSet(datalogSn,paramIds[position],text.trim(),"");
                                    }
                                })
                                .setNegative(getString(R.string.all_no),null)
                                .show();
                        break;
                    case 1://设置域名
                        new CircleDialog.Builder(OssDatalogSetActivity.this)
                                .setWidth(0.7f)
                                .setCancelable(false)
                                .setTitle(datas[position])
                                .configInput(new ConfigInput() {
                                    @Override
                                    public void onConfig(InputParams params) {
                                        params.hintText = "请输入需要设置的域名";
                                        params.inputHeight = (int) mContext.getResources().getDimension(R.dimen.y20);
                                    }
                                })
                                .setPositiveInput(getString(R.string.all_ok), new OnInputClickListener() {
                                    @Override
                                    public void onClick(String text, View v) {
                                        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(text.trim())){toast(R.string.all_blank);}
                                        datalogSet(datalogSn,paramIds[position],text.trim(),"");
                                    }
                                })
                                .setNegative(getString(R.string.all_no),null)
                                .show();
                        break;
                    case 2://重启采集器
                        new CircleDialog.Builder(OssDatalogSetActivity.this)
                                .setWidth(0.7f)
                                .setTitle(getString(R.string.温馨提示))
                                .setText(datas[position])
                                .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        datalogSet(datalogSn,paramIds[position],"","");
                                    }
                                })
                                .setNegative(getString(R.string.all_no),null)
                                .show();
                        break;
                    case 3://清除采集器信息
                        new CircleDialog.Builder(OssDatalogSetActivity.this)
                                .setWidth(0.7f)
                                .setTitle(getString(R.string.温馨提示))
                                .setText(datas[position])
                                .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        datalogSet(datalogSn,paramIds[position],"","");
                                    }
                                })
                                .setNegative(getString(R.string.all_no),null)
                                .show();
                        break;
                    case 4://高级设置
                        Intent intent = new Intent(mContext,OssAdvanceSetActivity.class);
                        intent.putExtra("sn",datalogSn);
                        intent.putExtra("type",0);
                        intent.putExtra("paramId",paramIds[position]);
                        intent.putExtra("title",datas[position]);
                        startActivity(intent);
                        break;
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**
     * 采集器设置
     * @param datalogSn：序列号
     * @param paramType：设置类型
     * @param param_1：参数值
     * @param param_2
     */
    public void datalogSet(final String datalogSn, final String paramType, final String param_1, final String param_2){
        Mydialog.Show(this,"");
        PostUtil.post(OssUrls.postOssDatalogSet(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("datalogSn",datalogSn);
                params.put("paramType",paramType);
                params.put("param_1",param_1);
                params.put("param_2",param_2);
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
//                    handler.sendEmptyMessage(result);
                    OssUtils.showOssDialog(OssDatalogSetActivity.this,jsonObject.getString("msg"),result,true,null);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }

            @Override
            public void LoginError(String str) {
                Mydialog.Dismiss();
            }
        });
    }
    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new OssDatalogSetAdapter(mContext,R.layout.item_oss_datalogset, Arrays.asList(datas));
        mRecyclerView.setAdapter(mAdapter);
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
        setHeaderTitle(headerView,"采集器设置");
    }
    /**
     * 错误信息处理
     */
    android.os.Handler handler = new android.os.Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case result_err:
                    toast(R.string.datalogcheck_check_no_server);
                    break;
                case result_normal:
                    toast(R.string.all_success);
                    break;
                case result_serverErr:
                    toast(R.string.datalogcheck_check_no_server);
                    break;
                case result_datalogOffline:
                    toast("采集器不在线");
                    break;
                case result_serverUrlNull:
                    break;
                case result_RemoteErr:
                    toast("远程设置错误");
                    break;
            }
        }
    };
    //采集器handler
    private Handler handlerDatalog = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 0:text = "设置失败";break;
                case 1:text = "设置成功";break;
                case 2:text = "采集器服务器错误";break;
                case 3:text = "采集器不在线";break;
                case 11:text = "服务器地址为空";break;
                case 12:text = "远程设置错误";break;
            }
            MyControl.circlerDialog(OssDatalogSetActivity.this,text,msg.what);
        }
    };
}
