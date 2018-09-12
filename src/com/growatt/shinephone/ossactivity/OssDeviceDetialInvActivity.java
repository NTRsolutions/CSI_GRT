package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.bean.OssDeviceInvBean;
import com.growatt.shinephone.util.Position;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_oss_device_detial_inv)
public class OssDeviceDetialInvActivity extends DemoBase {
    @ViewInject(R.id.tvValueSn)
    TextView tvValueSn;
    @ViewInject(R.id.tvValueAlias)
    TextView tvValueAlias;
    @ViewInject(R.id.tvValueDatalog)
    TextView tvValueDatalog;
    @ViewInject(R.id.tvValueStatus)
    TextView tvValueStatus;
    @ViewInject(R.id.tvValueRPower)
    TextView tvValueRPower;
    @ViewInject(R.id.tvValueNPower)
    TextView tvValueNPower;
    @ViewInject(R.id.tvValueToday)
    TextView tvValueToday;
    @ViewInject(R.id.tvValueTotal)
    TextView tvValueTotal;
    @ViewInject(R.id.tvValueModel)
    TextView tvValueModel;
    @ViewInject(R.id.tvValueLastTime)
    TextView tvValueLastTime;
    //名称
    @ViewInject(R.id.tvNameNPower)
    TextView tvNameNPower;
    @ViewInject(R.id.tvNameToday)
    TextView tvNameToday;
    @ViewInject(R.id.tvNameTotal)
    TextView tvNameTotal;

    private OssDeviceInvBean invBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initView();
    }

    private void initView() {
        tvValueSn.setText(invBean.getSerialNum());
        tvValueAlias.setText(invBean.getAlias());
        tvValueDatalog.setText(invBean.getDataLogSn());
        tvValueStatus.setText(invBean.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online));
        tvValueRPower.setText(invBean.getNominalPower() + "");
        tvValueNPower.setText(invBean.getPower()+"");
        tvValueToday.setText(invBean.geteToday()+"");
        tvValueTotal.setText(invBean.geteTotal()+"");
        tvValueModel.setText(invBean.getModelText());
        tvValueLastTime.setText(invBean.getLastUpdateTimeText());

        tvNameNPower.setText(getString(R.string.当前功率) + "(W)");
        tvNameToday.setText(getString(R.string.今日发电) + "(kWh)");
        tvNameTotal.setText(getString(R.string.累计发电量) + "(kWh)");

    }

    private void initIntent() {
        Intent intent = getIntent();
        invBean = intent.getParcelableExtra("bean");
        LogUtil.i("invBean:"+invBean.toString());
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
        setHeaderTitle(headerView,getString(R.string.mm40));
    }
    /**设置*/
    @Event(value = R.id.flSet)
    private void deviceSet(View v){
        jumpTo(OssInvSetActivity.class,false);
    }
    /**编辑*/
    @Event(value = R.id.flEdit)
    private void deviceEdit(View v){
        Intent intent = new Intent(mContext,OssEditActivity.class);
        intent.putExtra("sn",invBean.getSerialNum());
        intent.putExtra("alias",invBean.getAlias());
        intent.putExtra("deviceType",1);
        startActivity(intent);
    }
    /**删除*/
    @Event(value = R.id.flDelete)
    private void deviceDelete(View v){
    }
}
