package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.adapter.OssJKQuesTitleAdapter;
import com.growatt.shinephone.adapter.OssLogoutAdapter;
import com.growatt.shinephone.bean.OssGDQuestionListBean;
import com.growatt.shinephone.bean.OssJKQuesTitleBean;
import com.growatt.shinephone.bean.OssKForGDMainBean;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.SharedPreferencesUnit;
import com.growatt.shinephone.view.CustomBasePopupWindow;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * oss客服主入口
 */
@ContentView(R.layout.activity_oss_ke_fu)
public class OssKeFuActivity extends DemoBase {
    //最新消息
    @ViewInject(R.id.llNewMsg) LinearLayout llNewMsg;
    @ViewInject(R.id.tvNewUser) TextView tvNewUser;
    @ViewInject(R.id.tvNewContent) TextView tvNewContent;
    @ViewInject(R.id.tvNewNote) TextView tvNewNote;
    @ViewInject(R.id.ivNewsPoint) ImageView ivNewsPoint;
    @ViewInject(R.id.ivGif1) ImageView ivGif1;
    private int newsType = 0;//0:代表没有消息;1代表是客服消息；2代表是工单消息；
    private OssKForGDMainBean.QuestionReply replyBeanNews;
    private OssKForGDMainBean.WorkOrder ticketSystemBeanNews;
    //客服工单主标题
    @ViewInject(R.id.tvKeFu) TextView tvKeFu;
    @ViewInject(R.id.tvGD) TextView tvGD;

    @ViewInject(R.id.ivOssJkTitle)
    ImageView ivOssJkTitle;
    private long mExitTime;
    //客服recycleView及数据集合
    @ViewInject(R.id.recycleViewQues)
    RecyclerView recycleViewQues;
    private List<OssJKQuesTitleBean> quesList;
    private OssJKQuesTitleAdapter quesAdapter;
    private int[] imgResQues = {R.drawable.ossjk_follow_up2,R.drawable.ossjk_untreated2};
    private String[] titleQues;
    //工单recycleView及数据集合
    @ViewInject(R.id.recycleViewGD)
    RecyclerView recycleViewGD;
    private List<OssJKQuesTitleBean> gdList;
    private OssJKQuesTitleAdapter gdAdapter;
    private int[] imgResGD = {R.drawable.osskf_follow_up,R.drawable.osskf_dntreated};
    private String[] titleGD;
    //登出数据
    private RecyclerView serverRecycler;
    private CommonAdapter serverAdapter;
    private CustomBasePopupWindow mPopupWindow;
    private List<String> logoutList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeaderView();
        initView();
        initRecyclerView();
        initListener();
        MyUtils.checkUpdate(mContext,Constant.LoginActivity_Updata);
    }

    @Override
    protected void onResume() {
        refresh();
        super.onResume();
    }
    private View headerView;
    private void initHeaderView() {
        headerView = findViewById(R.id.headerView);
        setHeaderTitle(headerView,"OSS系统");
        logoutList.add(getString(R.string.fragment4_logout));
        logoutList.add(getString(R.string.m71));
        setHeaderImage(headerView, R.drawable.oss_layout, Position.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog(v);
            }
        });
    }
    private void logoutDialog(View v) {
        if (mPopupWindow == null) {
            mPopupWindow = new CustomBasePopupWindow(mContext, R.layout.item_oss_serverlist, (int) getResources().getDimension(R.dimen.x100), true) {
                @Override
                public void init() {
                    serverRecycler = (RecyclerView) mPopView.findViewById(R.id.recycleView);
                    serverRecycler.setLayoutManager(new LinearLayoutManager(mContext));
                    serverAdapter = new OssLogoutAdapter(mContext,R.layout.item_oss_serveritem,logoutList);
                    serverRecycler.setAdapter(serverAdapter);
                    //服务器列表item点击事件
                    serverAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                            mPopupWindow.dismiss();
                            switch (position){
                                case 0:
                                    OssUtils.logoutOss(OssKeFuActivity.this,true);
                                    break;
                                case 1://计算校验码
                                    jumpTo(OssToolsActivity.class,false);
                                    break;
                            }
                        }
                        @Override
                        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                            return false;
                        }
                    });
                }
            };
        }
        mPopupWindow.showAsDowm(v);
    }
    private void refresh() {
        PostUtil.post(OssUrls.questionOverviewOss(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {}
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        String obj = jsonObject.getJSONObject("obj").toString();
                        OssKForGDMainBean bean = new Gson().fromJson(obj,OssKForGDMainBean.class);
                        if (bean != null){
                            List<String> kfList = new ArrayList<>();
                            kfList.add(bean.getWaitFollowNum() + "");
                            kfList.add(bean.getNotProcessedNum() + "");
                            initQuesList(kfList);
                            List<String> gdList = new ArrayList<>();
                            gdList.add(bean.getWaitReceiveNum() + "");
                            gdList.add(bean.getInServiceNum() + "");
                            initGDList(gdList);
                            replyBeanNews = bean.getQuestionReply();
                            ticketSystemBeanNews  = bean.getWorkOrder();
                            MyUtils.hideAllView(View.GONE,llNewMsg);
                            newsType = 0 ;
                            MyUtils.showAllView(ivNewsPoint);
                            //获取存储的时间
                            String sovleTime = SharedPreferencesUnit.getInstance(mContext).get(Constant.OssNewsTime);
                            if (ticketSystemBeanNews != null){
                                MyUtils.showAllView(llNewMsg);
                                newsType = 2;
                                tvNewUser.setText(ticketSystemBeanNews.getCustomerName());
                                tvNewContent.setText(ticketSystemBeanNews.getTitle());
                                StringBuilder sb = new StringBuilder();
                                sb.append("(").append(tvGD.getText()).append(")");
                                tvNewNote.setText(sb.toString());
                                if (sovleTime.equals(ticketSystemBeanNews.getLastUpdateTime())){
                                    MyUtils.hideAllView(View.INVISIBLE,ivNewsPoint);
                                }
                            }
                            if (replyBeanNews != null){
                                MyUtils.showAllView(llNewMsg);
                                newsType = 1;
                                int isAdmin = replyBeanNews.getIsAdmin();
                                switch (isAdmin){
                                    case 0://客户
                                        tvNewUser.setText(replyBeanNews.getAccountName());
                                        break;
                                    case 1://客服
                                        tvNewUser.setText(replyBeanNews.getJobId());
                                        break;
                                    default:
                                        String name = replyBeanNews.getAccountName();
                                        if (TextUtils.isEmpty(name)){
                                            name = replyBeanNews.getJobId();
                                        }
                                        tvNewUser.setText(name);
                                        break;
                                }

                                tvNewContent.setText(replyBeanNews.getMessage());
                                StringBuilder sb = new StringBuilder();
                                sb.append("(").append(tvKeFu.getText()).append(")");
                                tvNewNote.setText(sb.toString());
                                if (sovleTime.equals(replyBeanNews.getTime())){
                                    MyUtils.hideAllView(View.INVISIBLE,ivNewsPoint);
                                }
                            }
                        }
                    }
                    else {
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void LoginError(String str) {}
        });
    }
    /**
     * 跳转到客服或工单详情
     * @param v
     */
    @Event(value = R.id.llNewMsg)
    private void llNewMsg(View v){
        //设置为消息已查看
        //将手机号储存
        switch (newsType){
            case 1://跳转到客服详情
                SharedPreferencesUnit.getInstance(mContext).put(Constant.OssNewsTime,replyBeanNews.getTime());
                jumpToReplyQues();
                break;
            case 2://跳转到工单详情
                SharedPreferencesUnit.getInstance(mContext).put(Constant.OssNewsTime,ticketSystemBeanNews.getLastUpdateTime());
                Intent intent = new Intent(OssKeFuActivity.this,OssGDDeticalActivity.class);
                OssGDQuestionListBean bean = new OssGDQuestionListBean();
                bean.setStatus(ticketSystemBeanNews.getStatus());
                bean.setId(ticketSystemBeanNews.getId());
                bean.setTitle(ticketSystemBeanNews.getTitle());
                bean.setCustomerName(ticketSystemBeanNews.getCustomerName());
                bean.setContact(ticketSystemBeanNews.getContact());
                bean.setServiceType(ticketSystemBeanNews.getServiceType());
                intent.putExtra("bean",bean);
                jumpTo(intent,false);
                break;
        }
    }
    private void jumpToReplyQues() {
        Intent mIntent = new Intent(this,OssGDReplyQues2Activity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString("id", replyBeanNews.getQuestionId() + "");
        mBundle.putString("userId",replyBeanNews.getUserId() + "");
        mBundle.putString("content",  "");
        mBundle.putString("title",  "");
        mBundle.putString("status",  "0");
        mBundle.putString("lastTime", "");
        mBundle.putString("serverUrl", "");
        mBundle.putInt("type",101);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
    }
    /**
     * 跳转到客服设备管理界面
     * @param v
     */
    @Event(value = R.id.flDeviceSearch)
    private void flDeviceSearch(View v){
        jumpTo(OssDeviceActivity.class,false);
    }
    /**
     * 跳转到客服设备管理界面
     * @param v
     */
    @Event(value = R.id.btnDevice)
    private void btnDevice(View v){
        jumpTo(OssDeviceActivity.class,false);
    }
    /**
     * 跳转到客服系统
     * @param v
     */
    @Event(value = R.id.llKeFu)
    private void llKeFu(View v){
        Intent intent = new Intent(this,OssGongDanActivity.class);
        intent.putExtra("type",1);
        intent.putExtra("status",10);
        jumpTo(intent,false);
    }
    /**
     * 跳转到工单系统
     * @param v
     */
    @Event(value = R.id.llGD)
    private void llGD(View v){
        Intent intent = new Intent(this,OssGongDanActivity.class);
        intent.putExtra("type",2);
        intent.putExtra("status",10);
        jumpTo(intent,false);
    }
    private void initListener() {
        quesAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                int status = 3;
                switch (position){
                    case 0: status = 3; break;
                    case 1: status = 1; break;
                }
                Intent intent = new Intent(OssKeFuActivity.this,OssGongDanActivity.class);
                intent.putExtra("type",1);
                intent.putExtra("status",status);
                jumpTo(intent,false);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return true;
            }
        });
        gdAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                int status = 2;
                switch (position){
                    case 0: status = 2; break;
                    case 1: status = 3; break;
                }
                Intent intent = new Intent(OssKeFuActivity.this,OssGongDanActivity.class);
                intent.putExtra("type",2);
                intent.putExtra("status",status);
                jumpTo(intent,false);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return true;
            }
        });
    }
    private void initRecyclerView() {
        //客服系统
        recycleViewQues.setLayoutManager(new GridLayoutManager(this,2));
        titleQues = new String[]{getString(R.string.m76待跟进),getString(R.string.正在处理)};
        quesList = new ArrayList<>();
        quesAdapter = new OssJKQuesTitleAdapter(this,R.layout.item_ossjk_ques_title,quesList);
        recycleViewQues.setAdapter(quesAdapter);
        initQuesList(null);
        //工单系统
        recycleViewGD.setLayoutManager(new GridLayoutManager(this,2));
        titleGD = new String[]{"待接收","服务中"};
        gdList = new ArrayList<>();
        gdAdapter = new OssJKQuesTitleAdapter(this,R.layout.item_ossjk_ques_title,gdList);
        recycleViewGD.setAdapter(gdAdapter);
        initGDList(null);
    }

    /**
     * 初始化工单数据并刷新界面
     * @param contents
     */
    private void initGDList(List<String> contents) {
        if (contents == null){
            contents = new ArrayList<>();
            for (int i = 0;i<2;i++){ contents.add("");}
        }
        List<OssJKQuesTitleBean> newList = new ArrayList<>();
        for (int i = 0;i<2;i++){
            OssJKQuesTitleBean bean = new OssJKQuesTitleBean();
            bean.setImgRes(imgResGD[i]);
            bean.setTitle(titleGD[i]);
            bean.setContent(contents.get(i));
            newList.add(bean);
        }
        gdAdapter.addAll(newList,true);
    }
    /**
     * 初始化客服数据并刷新界面
     * @param contents
     */
    private void initQuesList(List<String> contents) {
        if (contents == null){
            contents = new ArrayList<>();
            for (int i = 0;i<2;i++){ contents.add("");}
        }
        List<OssJKQuesTitleBean> newList = new ArrayList<>();
        for (int i = 0;i<2;i++){
            OssJKQuesTitleBean bean = new OssJKQuesTitleBean();
            bean.setImgRes(imgResQues[i]);
            bean.setTitle(titleQues[i]);
            bean.setContent(contents.get(i));
            newList.add(bean);
        }
        quesAdapter.addAll(newList,true);
    }
    private void initView() {
//        Glide.with(this).load(R.drawable.oss_title).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.oss_title).into(new GlideDrawableImageViewTarget(ivOssJkTitle, 1));
        Glide.with(this).load(R.drawable.oss_message_icon2).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivGif1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_BACK){
            if (System.currentTimeMillis() - mExitTime > 2000){
                toast(R.string.MainActivity_exit);
                mExitTime = System.currentTimeMillis();
            }else {
                ShineApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
