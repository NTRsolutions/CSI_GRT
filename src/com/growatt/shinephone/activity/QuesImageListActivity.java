package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_ques_image_list)
public class QuesImageListActivity extends DemoBase {
    @ViewInject(R.id.recycleView)
    RecyclerView recyclerView;
    private ArrayList<String> imgs = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private int type;//1:代表oss图片详情；其他为server
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initRecyclerView();
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
        setHeaderTitle(headerView,getString(R.string.查看图片));
    }
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new com.zhy.adapter.recyclerview.CommonAdapter<String>(mContext,R.layout.item_ques_imgs,imgs){

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                String url = "";
                if (type == 1){
//                    url = new Urlsutil().getImageInfo + userName +"/"+s;
                    url = new Urlsutil().getImageInfo +s;
                }else {
//                    url = new Urlsutil().getImageInfo + (Cons.userBean == null ? "": Cons.userBean.accountName +"/"+s);
                    url = new Urlsutil().getImageInfo +s;
                }
                LogUtil.i("imgUrl:"+url);
                Glide.with(mContext).load(url).placeholder(R.drawable.loading).error(R.drawable.pic_service).dontAnimate().into((ImageView) holder.getView(R.id.ivImage));
            }
        };
        recyclerView.setAdapter(mAdapter);
    }

    private void initIntent() {
        Intent intent  = getIntent();
        imgs = intent.getStringArrayListExtra("img");
       type = intent.getIntExtra("type",0);
        if (type == 1){
            userName = intent.getStringExtra("userName");
        }
    }
}
