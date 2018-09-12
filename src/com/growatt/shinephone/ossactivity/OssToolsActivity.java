package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.activity.MipcaActivityCapture;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.Position;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_oss_tools)
public class OssToolsActivity extends DemoBase {
    @ViewInject(R.id.et_sn)
    EditText etSn;
    @ViewInject(R.id.tvCode)
    TextView tvCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeadView();
    }
    @Event(value = R.id.btn_next)
    private void btnNext(View v){
        String sn = etSn.getText().toString().trim();
        if (sn.length() != 10){
            toast(R.string.dataloggers_add_no_number);
            return;
        }
        tvCode.setText(AppUtils.validateWebbox(sn));
        toast(R.string.all_success);
    }
    @Event(value = R.id.btn_scan)
    private void btn_scan(View v){
        Intent intent = new Intent();
        intent.setClass(this, MipcaActivityCapture.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, 105);
    }

    private View headerView;
    private void initHeadView() {
        headerView = findViewById(R.id.headerView);
        setHeaderImage(headerView, R.drawable.back, Position.LEFT, new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        setHeaderTitle(headerView, getString(R.string.m71));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null&&resultCode==RESULT_OK){
            if(requestCode==105){
                Bundle bundle=data.getExtras();
                String s=bundle.getString("result");
                if(!s.equals("")){
                    etSn.setText(s);
                    tvCode.setText(AppUtils.validateWebbox(s));
                    toast(R.string.all_success);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
