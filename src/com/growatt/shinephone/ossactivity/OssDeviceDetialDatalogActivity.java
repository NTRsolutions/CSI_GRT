package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.bean.OssDeviceDatalogBean;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Position;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_oss_device_detial)
public class OssDeviceDetialDatalogActivity extends DemoBase{
    @ViewInject(R.id.tvValueSn)
    TextView tvValueSn;
    @ViewInject(R.id.tvValueAlias)
    TextView tvValueAlias;
    @ViewInject(R.id.tvValueDeviceType)
    TextView tvValueDeviceType;
    @ViewInject(R.id.tvValueUserName)
    TextView tvValueUserName;
    @ViewInject(R.id.tvValueVersion)
    TextView tvValueVersion;
    @ViewInject(R.id.tvValueStatus)
    TextView tvValueStatus;
    @ViewInject(R.id.tvValueIPPort)
    TextView tvValueIPPort;
    @ViewInject(R.id.tvValueServerIP)
    TextView tvValueServerIP;
    @ViewInject(R.id.tvValueLastTime)
    TextView tvValueLastTime;
    @ViewInject(R.id.tvValueCreatTime)
    TextView tvValueCreatTime;
    private OssDeviceDatalogBean datalogBean;
    //采集器类型对应编码
    private int deviceTypeIndicate;
    @ViewInject(R.id.flConfig)
    FrameLayout flConfig;
    @ViewInject(R.id.tvConfig)
    TextView tvConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
        initListener();
    }

    private void initView() {
        tvValueSn.setText(datalogBean.getSerialNum());
        tvValueAlias.setText(datalogBean.getAlias());
        tvValueDeviceType.setText(datalogBean.getDeviceType());
        tvValueUserName.setText(datalogBean.getUserName());
        tvValueVersion.setText(datalogBean.getParamBean().getFirmwareVersionBuild());
        tvValueStatus.setText(datalogBean.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online));
        tvValueIPPort.setText(datalogBean.getClientUrl());
        tvValueServerIP.setText(datalogBean.getTcpServerIp());
        tvValueLastTime.setText(datalogBean.getLastUpdateTimeText());
        tvValueCreatTime.setText(datalogBean.getCreateDate());
    }

    private void initIntent() {
        Intent intent = getIntent();
        datalogBean = intent.getParcelableExtra("bean");
        deviceTypeIndicate = datalogBean.getDeviceTypeIndicate();
        if (deviceTypeIndicate == 2 || deviceTypeIndicate == 6 || deviceTypeIndicate == 9){
            MyUtils.showAllView(flConfig,tvConfig);
        }else {
            MyUtils.hideAllView(View.INVISIBLE,flConfig,tvConfig);
        }
        LogUtil.i("datalogBean:"+datalogBean.toString());
    }

    private void initListener() {

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
        setHeaderTvTitle(headerView, "保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        setHeaderTitle(headerView,"设备名称");
    }

    /**
     * 具体保存数据的逻辑
     */
    private void saveData() {
    }

    /**设置*/
    @Event(value = R.id.flSet)
    private void deviceSet(View v){
        Intent intent = new Intent(mContext,OssDatalogSetActivity.class);
        intent.putExtra("datalogSn",datalogBean.getSerialNum());
        startActivity(intent);
    }
    /**编辑*/
    @Event(value = R.id.flEdit)
    private void deviceEdit(View v){
        Intent intent = new Intent(mContext,OssEditActivity.class);
        intent.putExtra("sn",datalogBean.getSerialNum());
        intent.putExtra("alias",datalogBean.getAlias());
        intent.putExtra("deviceType",0);
        startActivity(intent);
    }
    /**删除*/
    @Event(value = R.id.flDelete)
    private void deviceDelete(View v){
    }
    /**配置*/
    @Event(value = R.id.flConfig)
    private void deviceConfig(View v){
    }
}
