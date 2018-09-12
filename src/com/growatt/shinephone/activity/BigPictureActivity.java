package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.growatt.shinephone.R;
import com.growatt.shinephone.ui.ZoomImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_big_picture)
public class BigPictureActivity extends DemoBase {
    @ViewInject(R.id.ivBigImage)
    ZoomImageView ivBigImage;
    //跳转而来的数据
    private String filePath;//file文件路径
    private int type;//由哪里跳转而来:1:由新版提交问题Putin2Adapter；由工单派单图片OssReplyQuesAdapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initView();
        initListener();
    }

    private void initListener() {
        ivBigImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        Glide.with(this).load(filePath).placeholder(R.drawable.loading).error(R.drawable.pic_service).into(ivBigImage);
    }

    private void initIntent() {
        Intent intent = getIntent();
        filePath = intent.getStringExtra("path");
        type = intent.getIntExtra("type",1);
    }
}
