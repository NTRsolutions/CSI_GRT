package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.bean.OssDeviceStorageBean;
import com.growatt.shinephone.util.Position;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_oss_device_detial_storage)
public class OssDeviceDetialStorageActivity extends DemoBase {
    @ViewInject(R.id.tvValueSn)
    TextView tvValueSn;
    @ViewInject(R.id.tvValueAlias)
    TextView tvValueAlias;
    @ViewInject(R.id.tvValueDatalog)
    TextView tvValueDatalog;
    @ViewInject(R.id.tvValueStatus)
    TextView tvValueStatus;
    @ViewInject(R.id.tvValueChargePower)
    TextView tvValueChargePower;
    @ViewInject(R.id.tvValueDisChargePower)
    TextView tvValueDisChargePower;
    @ViewInject(R.id.tvValueVersion)
    TextView tvValueVersion;
    @ViewInject(R.id.tvValueServerIP)
    TextView tvValueServerIP;
    @ViewInject(R.id.tvValueModel)
    TextView tvValueModel;
    @ViewInject(R.id.tvValueLastTime)
    TextView tvValueLastTime;
    @ViewInject(R.id.tvNameChargePower)
    TextView tvNameChargePower;
    @ViewInject(R.id.tvNameDisChargePower)
    TextView tvNameDisChargePower;
    private OssDeviceStorageBean storageBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
    }

    private void initView() {
        tvValueSn.setText(storageBean.getSerialNum());
        tvValueAlias.setText(storageBean.getAlias());
        tvValueDatalog.setText(storageBean.getDataLogSn());
        tvValueStatus.setText(storageBean.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online));
        tvValueChargePower.setText(storageBean.getpCharge()+"");
        tvValueDisChargePower.setText(storageBean.getpDischarge()+"");
        tvValueVersion.setText(storageBean.getFwVersion());
        tvValueServerIP.setText(storageBean.getTcpServerIp());
        tvValueModel.setText(storageBean.getModelText());
        tvValueLastTime.setText(storageBean.getLastUpdateTimeText());

        tvNameChargePower.setText(getString(R.string.storagedps_list1_item1) + "W");
        tvNameDisChargePower.setText(getString(R.string.storagedps_list1_item2) + "W");
    }

    private void initIntent() {
        Intent intent = getIntent();
        storageBean = intent.getParcelableExtra("bean");
        LogUtil.i("storageBean:"+storageBean.toString());
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
            }
        });
        setHeaderTitle(headerView,getString(R.string.mm39));
    }
    /**设置*/
    @Event(value = R.id.flSet)
    private void deviceSet(View v){
        if (storageBean != null && storageBean.getDeviceType() == 2){//SPF5k
            jumpTo(OssStorageSpf5kSetActivity.class,false);
        }else {
            jumpTo(OssStorageSetActivity.class,false);
        }
    }
    /**编辑*/
    @Event(value = R.id.flEdit)
    private void deviceEdit(View v){
        Intent intent = new Intent(mContext,OssEditActivity.class);
        intent.putExtra("sn",storageBean.getSerialNum());
        intent.putExtra("alias",storageBean.getAlias());
        intent.putExtra("deviceType",2);
        startActivity(intent);
    }
    /**删除*/
    @Event(value = R.id.flDelete)
    private void deviceDelete(View v){
    }
}
