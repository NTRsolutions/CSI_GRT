package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Position;

public class RebackPwdActivity extends DoActivity {
    private Button userNameBtn;
    private Button phoneBtn;
    private View headerView;
    private TextView tvNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reback_pwd);
        initHeaderView();
        initView();
        initListener();
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
    private void initListener() {
        userNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RebackPwdActivity.this,RetrievepwdActivity.class));
            }
        });
        //通过手机号找回密码
        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RebackPwdActivity.this,RebackPwdByPhoneActivity.class));
            }
        });
    }

    private void initView() {
        userNameBtn = (Button) findViewById(R.id.userNameBtn);
        phoneBtn = (Button) findViewById(R.id.phoneBtn);
        tvNote = (TextView) findViewById(R.id.tvNote);
        if ( getLanguage() == 0){
            phoneBtn.setVisibility(View.VISIBLE);
            tvNote.setVisibility(View.VISIBLE);
        }else {
            phoneBtn.setVisibility(View.INVISIBLE);
            tvNote.setVisibility(View.INVISIBLE);
        }
    }
}
