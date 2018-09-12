package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.adapter.OssGDorKFListAdapter;
import com.growatt.shinephone.bean.OssGDQuestionListBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.view.MyListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.growatt.shinephone.R.id.radioGroup_rb0;
import static com.growatt.shinephone.R.id.radioGroup_rb1;

@ContentView(R.layout.activity_oss_gong_dan)
public class OssGongDanActivity extends DemoBase implements RadioGroup.OnCheckedChangeListener,MyListView.ILoadListener{
    //头部group
    @ViewInject(R.id.radioGroup0) RadioGroup radioGroup0;
    @ViewInject(R.id.radioGroup0_rb1) RadioButton radioGroup0_rb1;
    @ViewInject(R.id.radioGroup0_rb2) RadioButton radioGroup0_rb2;
    @ViewInject(R.id.etContent)
    EditText etContent;
    @ViewInject(R.id.flSearch)
    FrameLayout flSearch;
    @ViewInject(R.id.listView)
    MyListView listView;
    @ViewInject(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    //头部数据
    private TextView tvCount;//显示当前页面数量和总数
    private TextView tvHeaderText;//显示当前状态文本
    private String headerText;
    private RadioButton radioGroupKF_rb0;
    private RadioButton radioGroupKF_rb1;
    private RadioButton radioGroupKF_rb2;
    private RadioButton radioGroupKF_rb3;
    private RadioButton radioGroupKF_rb4;
    private RadioButton radioGroupGD_rb0;
    private RadioButton radioGroupGD_rb1;
    private RadioButton radioGroupGD_rb2;
    private RadioButton radioGroupGD_rb3;
    //客服列表和radiobutton
    //分页数据
    private int currentPage = 1;
    private int totalPage = 1;
    private int totalKF ;//客服问题总数
    private RadioGroup radioGroupKF;
    private OssGDorKFListAdapter mAdapter;
    private List<OssGDQuestionListBean> mList;//总list
    private List<OssGDQuestionListBean> kfList = new ArrayList<>();//客服list
    private View header_listView;//列表头部
    private int status = 10;//客服问题状态：问题状态；10：所有；0：未处理；1：正在处理；2：已解决；3：待跟进
    //工单列表
    private RadioGroup radioGroupGD;
    private int totalGD ;//客服问题总数
    private List<OssGDQuestionListBean> gdList = new ArrayList<>();//工单list
    private int gdStatus = 0;//问题状态-0：所有;1：已分配;2：待接收;3：服务中;4：已完成
    //分页数据
    private int currentPageGD = 1;
    private int totalPageGD = 1;

    //跳转获取数据
    private int jumpType = 1;//1:代表客服系统；2：代表工单系统
    private int jumpStatus;//客服系统或者工单系统的问题状态
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeaderView();
        initView();
        initIntent();
        initListView();
        initListener();
        flSearch(null);
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
        setHeaderTitle(headerView,getString(R.string.mD_Oss系统));
        setHeaderTvTitle(headerView, getString(R.string.m搜索设备), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(OssDeviceActivity.class,false);
            }
        });
    }
    private void initIntent() {
        Intent intent = getIntent();
        jumpType = intent.getIntExtra("type",1);
        jumpStatus = intent.getIntExtra("status",0);
        switchRadioGroup(jumpType);
        switch (jumpType){
            case 1:
                switch (jumpStatus){
                    case 0:radioGroupKF.check(R.id.radioGroup_rb3); headerText = radioGroupKF_rb3.getText().toString();break;
                    case 1:radioGroupKF.check(R.id.radioGroup_rb1); headerText = radioGroupKF_rb1.getText().toString();break;
                    case 3:radioGroupKF.check(R.id.radioGroup_rb2);headerText = radioGroupKF_rb2.getText().toString();break;
                    case 10:radioGroupKF.check(radioGroup_rb0);headerText = radioGroupKF_rb0.getText().toString();break;
                }
                status = jumpStatus;
                break;
            case 2:
                switch (jumpStatus){
                    case 2:radioGroupGD.check(R.id.radioGroup3_rb1);headerText = radioGroupGD_rb1.getText().toString();break;
                    case 3:radioGroupGD.check(R.id.radioGroup3_rb2);headerText = radioGroupGD_rb2.getText().toString();break;
                    case 10:radioGroupGD.check(R.id.radioGroup3_rb0);headerText = radioGroupGD_rb0.getText().toString();break;
                }
                gdStatus = jumpStatus;
                break;
        }
        tvHeaderText.setText(headerText);
    }
    public void setCountText(int mTotalNum){
        StringBuilder sb = new StringBuilder()
                .append(mAdapter.getCount())
                .append("/")
                .append(mTotalNum);
        tvCount.setText(sb.toString());
    }
    /**
     * 根据jumpType隐藏或显示相应radioGroup
     */
    public void switchRadioGroup(int jumpType){
        switch (jumpType){
            case 1:
                radioGroup0.check(R.id.radioGroup0_rb1);
                MyUtils.showAllView(radioGroupKF);
                MyUtils.hideAllView(View.GONE,radioGroupGD);
                break;
            case 2:
                radioGroup0.check(R.id.radioGroup0_rb2);
                MyUtils.showAllView(radioGroupGD);
                MyUtils.hideAllView(View.GONE,radioGroupKF);
                break;
        }
    }
    private void initListener() {
        listView.setOnILoadListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(etContent.getText().toString().trim(),status,gdStatus,1);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (jumpType){
                    case 1:
                        jumpToReplyQues(position);
                        break;
                    case 2:
                        Intent intent = new Intent(OssGongDanActivity.this,OssGDDeticalActivity.class);
                        intent.putExtra("bean",mList.get(position-1));
                        jumpTo(intent,false);
                        break;
                }
            }
        });
        radioGroupListener(radioGroup0,radioGroupKF,radioGroupGD);
    }
    public void radioGroupListener(RadioGroup... rgs){
        for (RadioGroup rg:rgs){
            rg.setOnCheckedChangeListener(this);
        }
    }
    private void jumpToReplyQues(int position) {
        OssGDQuestionListBean bean = mList.get(position-1);
        Intent mIntent = new Intent(this,OssGDReplyQues2Activity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString("id", bean.getId() + "");
        mBundle.putString("userId",bean.getUserId() + "");
        mBundle.putString("content", bean.getContent());
        mBundle.putString("title", bean.getTitle() );
        mBundle.putString("status", bean.getStatus() + "");
        mBundle.putString("lastTime", bean.getLastTime());
        mBundle.putString("serverUrl", bean.getServerUrl());
        mBundle.putInt("type",101);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
    }

    @Event(value = R.id.flSearch)
    private void flSearch(View v){
        refresh(etContent.getText().toString().trim(),status,gdStatus,1);
    }

    /**
     * 整体刷新入口
     * @param content
     * @param status:客服问题状态
     * @param gdStatus：工单状态
     * @param page
     */
    private void refresh(String content, int status, int gdStatus,int page) {
        switch (jumpType){
            case 1://客服问题列表
                refreshKF(content,status,page);

                break;
            case 2://工单问题列表
                refreshGD(content,gdStatus,page);
                break;
        }
    }
    /**
     * 执行刷新操作：刷新工单
     */
    private void refreshGD(final String content, final int status, final int page) {
        Mydialog.Show(this,"");
        PostUtil.post(OssUrls.orderListOssGD(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("content",content);
                params.put("status",status + "");
                params.put("page",page + "");
            }
            @Override
            public void success(String json) {
                swipeRefreshLayout.setRefreshing(false);
                Mydialog.Dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        JSONObject obj = jsonObject.getJSONObject("obj");
                        currentPageGD = obj.getInt("offset");
                        totalPageGD = obj.getInt("pages");
                        totalGD = obj.getInt("total");
                        JSONArray array = obj.getJSONArray("datas");
                        List<OssGDQuestionListBean> newList = new ArrayList<>();
                        for (int i = 0,length = array.length();i<length;i++){
                            OssGDQuestionListBean bean = new Gson().fromJson(array.getJSONObject(i).toString(),OssGDQuestionListBean.class);
                            bean.setType(2);
                            newList.add(bean);
                        }
                        if (page == 1){
                            mAdapter.addAll(newList,true);
                        }else {
                            mAdapter.addAll(newList,false);
                        }
                        //将数据放在临时容器，并设置当前数量和总数
                        setCountText(totalGD);
                        gdList.clear();
                        gdList.addAll(mList);
                        //然后通知加载数据已经完成了
                        listView.loadFinish();
                    }else {
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
//                        handlerGD.sendEmptyMessage(result);
                        if (currentPageGD > 1){ currentPageGD--;}
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listView.loadFinish();
                    swipeRefreshLayout.setRefreshing(false);
                    if (currentPageGD > 1){ currentPageGD--;}
                }
            }
            @Override
            public void LoginError(String str) {
                Mydialog.Dismiss();
                listView.loadFinish();
                swipeRefreshLayout.setRefreshing(false);
                if (currentPageGD > 1){ currentPageGD--;}
            }
        });
    }


    /**
     * 执行刷新操作：刷新客服
     */
    public void refreshKF(final String content, final int status, final int page){
        Mydialog.Show(this,"");
        PostUtil.post(OssUrls.questionListOss(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("content",content);
                params.put("status",status + "");
                params.put("page",page + "");
            }
            @Override
            public void success(String json) {
                swipeRefreshLayout.setRefreshing(false);
                Mydialog.Dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        JSONObject obj = jsonObject.getJSONObject("obj");
                        JSONObject pager = obj.getJSONObject("pager");
                        currentPage = pager.getInt("offset");
                        totalPage = pager.getInt("pages");
                        totalKF = pager.getInt("total");
                        JSONArray array = pager.getJSONArray("datas");
                        List<OssGDQuestionListBean> newList = new ArrayList<>();
                        for (int i = 0,length = array.length();i<length;i++){
                            OssGDQuestionListBean bean = new Gson().fromJson(array.getJSONObject(i).toString(),OssGDQuestionListBean.class);
                            bean.setType(1);
                            newList.add(bean);
                        }
                        if (page == 1){
                            mAdapter.addAll(newList,true);
                        }else {
                            mAdapter.addAll(newList,false);
                        }
                        //将数据放在临时容器，并设置当前数量和总数
                        setCountText(totalKF);
                        kfList.clear();
                        kfList.addAll(mList);
                        //然后通知加载数据已经完成了
                         listView.loadFinish();
                    }else {
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
//                        handlerGD.sendEmptyMessage(result);
                        if (currentPage > 1){ currentPage--;}
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    listView.loadFinish();
                    swipeRefreshLayout.setRefreshing(false);
                    if (currentPage > 1){ currentPage--;}
                }
            }
            @Override
            public void LoginError(String str) {
                Mydialog.Dismiss();
                listView.loadFinish();
                swipeRefreshLayout.setRefreshing(false);
                if (currentPage > 1){ currentPage--;}
            }
        });
    }
    private void initView() {
        header_listView = LayoutInflater.from(mContext).inflate(R.layout.header_gongdanlist,listView,false);
        radioGroupKF = (RadioGroup) header_listView.findViewById(R.id.radioGroup);
        radioGroupKF_rb0 = (RadioButton) header_listView.findViewById(radioGroup_rb0);
        radioGroupKF_rb1 = (RadioButton) header_listView.findViewById(radioGroup_rb1);
        radioGroupKF_rb2 = (RadioButton) header_listView.findViewById(R.id.radioGroup_rb2);
        radioGroupKF_rb3 = (RadioButton) header_listView.findViewById(R.id.radioGroup_rb3);
        radioGroupKF_rb4 = (RadioButton) header_listView.findViewById(R.id.radioGroup_rb4);
        radioGroupGD_rb0 = (RadioButton) header_listView.findViewById(R.id.radioGroup3_rb0);
        radioGroupGD_rb1 = (RadioButton) header_listView.findViewById(R.id.radioGroup3_rb1);
        radioGroupGD_rb2 = (RadioButton) header_listView.findViewById(R.id.radioGroup3_rb2);
        radioGroupGD_rb3 = (RadioButton) header_listView.findViewById(R.id.radioGroup3_rb3);
        radioGroupGD = (RadioGroup) header_listView.findViewById(R.id.radioGroup3);
        tvCount = (TextView) header_listView.findViewById(R.id.tvCount);
        tvHeaderText = (TextView) header_listView.findViewById(R.id.tvHeaderText);
        swipeRefreshLayout.setColorSchemeResources(R.color.headerView);
    }

    private void initListView() {
        mList = new ArrayList<>();
        mAdapter = new OssGDorKFListAdapter(mContext,mList);
        listView.addHeaderView(header_listView);
        listView.setAdapter(mAdapter);
    }

    /**
     * 设置状态值：如全部问题、待跟进等,以及状态status
     * @param rg
     */
    public void setStatusText(RadioGroup rg){
        int checkId = rg.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) rg.findViewById(checkId);
        if (rb == null){
            RadioButton rb0 = (RadioButton) rg.getChildAt(0);
            if (rb0 != null){
                tvHeaderText.setText(rb0.getText().toString());
            }else {
                tvHeaderText.setText(R.string.all_time_all);
            }
        }else {
            tvHeaderText.setText(rb.getText().toString());
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        if (group == radioGroup0){
            switch (checkedId){
                case R.id.radioGroup0_rb1:
                    jumpType = 1;
                    mAdapter.addAll(kfList,true);
                    setCountText(totalKF);
                    setStatusText(radioGroupKF);
                    radioGroupKF_rb0.setChecked(true);
                    break;
                case R.id.radioGroup0_rb2:
                    jumpType = 2;
                    mAdapter.addAll(gdList,true);
                    setCountText(totalGD);
                    setStatusText(radioGroupGD);
                    radioGroupGD_rb0.setChecked(true);
                    break;
            }
            switchRadioGroup(jumpType);
        }
        if (group == radioGroupKF){
            switch (checkedId){
                case R.id.radioGroup_rb0:
                    status = 10;
                    break;
                case R.id.radioGroup_rb1:
                    status = 1;
                    break;
                case R.id.radioGroup_rb2:
                    status = 3;
                    break;
                case R.id.radioGroup_rb3:
                    status = 0;
                    break;
                case R.id.radioGroup_rb4:
                    status = 2;
                    break;
            }
            refreshKFShowStatus(group, checkedId);
        }
        if (group == radioGroupGD){
            switch (checkedId){
                case R.id.radioGroup3_rb0:
                    gdStatus = 10;
                    break;
                case R.id.radioGroup3_rb1:
                    gdStatus = 2;
                    break;
                case R.id.radioGroup3_rb2:
                    gdStatus = 3;
                    break;
                case R.id.radioGroup3_rb3:
                    gdStatus = 4;
                    break;
            }
            refreshGDShowStatus(group, checkedId);
        }

    }

    private void refreshGDShowStatus(RadioGroup group, @IdRes int checkedId) {
        refreshGD(etContent.getText().toString().trim(),gdStatus,1);
        headerText = ((RadioButton)group.findViewById(checkedId)).getText().toString();
        tvHeaderText.setText(headerText);
    }

    /**
     * 刷新并显示状态
     * @param group
     * @param checkedId
     */
    private void refreshKFShowStatus(RadioGroup group, @IdRes int checkedId) {
        refreshKF(etContent.getText().toString().trim(),status,1);
        headerText = ((RadioButton)group.findViewById(checkedId)).getText().toString();
        tvHeaderText.setText(headerText);
    }

    Handler handlerGD = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 0 :
                    text = getString(R.string.datalogcheck_check_no_server);
                    break;
                case 2:
                    text = "远程获取数据出错";
                    break;
                case 3:
                    text = "登录超时";
                    break;
                case 4:
                    text = "搜索服务器地址为空";
                    break;
                case 21:
                    text = "搜索不到信息";
                    break;
                default:text = getString(R.string.datalogcheck_check_no_server);
                    break;
            }
            MyControl.circlerDialog(OssGongDanActivity.this,text,msg.what,false);
        }
    };

    @Override
    public void loadData() {
        switch (jumpType){
            case 1:
                if (currentPage < totalPage){
                    currentPage ++ ;
                    refresh(etContent.getText().toString().trim(),status,gdStatus,currentPage);
                }else {
                    listView.loadFinish();
                }
                break;
            case 2:
                if (currentPageGD < totalPageGD){
                    currentPageGD ++ ;
                    refresh(etContent.getText().toString().trim(),status,gdStatus,currentPageGD);
                }else {
                    listView.loadFinish();
                }
                break;
        }

    }
}
