package com.growatt.shinephone.activity;

import android.os.Bundle;

public abstract class BaseActivity extends DoActivity {
	

    /**
     * 初始化布�?资源文件
     */
    public abstract int initResource();

    /**
     * 初始化组�?
     */
    public abstract void initComponent();

    /**
     * 初始化数�?
     */
    public abstract void initData();

    /**
     * 添加监听
     */
    public abstract void addListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initResource());
        initComponent();
        initData();
        addListener();
    }

}
