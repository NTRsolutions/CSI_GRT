package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.adapter.OssJKDeviceStorAdapter;
import com.growatt.shinephone.bean.OssJkDeviceStorBean;
import com.growatt.shinephone.bean.OssJkDeviceStorageDeticalBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_oss_jkdevice_list_stor)
public class OssJKDeviceListStorActivity extends DemoBase implements RadioGroup.OnCheckedChangeListener{
    @ViewInject(R.id.radioGroup)
    RadioGroup radioGroup;
    @ViewInject(R.id.recycleView)
    RecyclerView recyclerView;
    //跳转来的数据
    private int device_state;
    private int device_type;
    private int accessStatus;
    private String agentCode;
    private String plantName;
    private String userName;
    private String datalogSn;
    private String deviceSn;

    private OssJKDeviceStorAdapter mAdapter;
    private List<OssJkDeviceStorBean> mList;
    private LinearLayoutManager mLayoutManager;
    //json数据
    private int currentPage = 1;
    private int totalPage = 1;
    //分页
    private int lastVisibleItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initRecycleView();
        initListener();
    }

    private void initRecycleView() {
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLayoutManager);
        mList = new ArrayList<>();
        mAdapter = new OssJKDeviceStorAdapter(R.layout.item_ossjk_device,mList);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(R.layout.empty_view,(ViewGroup) recyclerView.getParent());
    }
    @Override
    protected void onResume() {
        super.onResume();
        //刷新数据
        refresh(1);
    }

    private void refresh(final int page) {
        Mydialog.Show(this,"");
        PostUtil.post(OssUrls.postOssJKDvice_list(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("deviceType",device_type + "");
                params.put("accessStatus",accessStatus + "");
                params.put("agentCode",agentCode);
                params.put("plantName",plantName);
                params.put("userName",userName);
                params.put("datalogSn",datalogSn);
                params.put("deviceSn",deviceSn);
                params.put("deviceStatus",device_state + "");
                params.put("page",page + "");
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        JSONObject obj = jsonObject.getJSONObject("obj");
                        currentPage = obj.getInt("currentPage");
                        totalPage = obj.getInt("totalPage");
                        JSONArray arr = obj.getJSONArray("storageList");
                        List<OssJkDeviceStorBean> newList = new ArrayList<>();
                        for (int i = 0,length = arr.length();i<length;i++){
                            OssJkDeviceStorBean bean = new Gson().fromJson(arr.getJSONObject(i).toString(),OssJkDeviceStorBean.class);
                            newList.add(bean);
                        }
                        if (currentPage == 1){
//                            mAdapter.addAll(newList,true);
                            mAdapter.setNewData(newList);
                        }else {
//                            mAdapter.addAll(newList,false);
                            mAdapter.addData(newList);
                        }
                    }else {
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
//                        MyControl.circlerDialog(OssJKDeviceListStorActivity.this,getString(R.string.datalogcheck_check_no_server),result,false);
                        if (currentPage > 1){currentPage--;}
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (currentPage > 1){currentPage--;}
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {
                if (currentPage > 1){currentPage--;}
            }
        });
    }
    private void initListener() {
        radioGroup.setOnCheckedChangeListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                // 滑动状态停止并且剩余少于两个item时，自动加载下一页
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1>=mLayoutManager.getItemCount()) {
                    if (currentPage++ < totalPage){
                        refresh(currentPage);
                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取加载的最后一个可见视图在适配器的位置。
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (adapter.getItemCount() < position){
                    return;
                }
                jumpDetical(2,position);
            }
        });
    }
    private void jumpDetical(final int deviceType, int position) {
//        final OssJkDeviceStorBean bean = mList.get(position);
        final OssJkDeviceStorBean bean = mAdapter.getItem(position);
        //获取集成商设备详情数据
        Mydialog.Show(this, "");
        PostUtil.post(OssUrls.postOssJKDvice_info(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("deviceSn", bean.getSeriaNum());
                params.put("deviceType", deviceType + "");
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1) {
                        JSONObject obj = jsonObject.getJSONObject("obj");
                        JSONArray arr = obj.optJSONArray("storageList");
                        if (arr != null && arr.length() > 0) {
                            OssJkDeviceStorageDeticalBean bean = new Gson().fromJson(arr.getJSONObject(0).toString(), OssJkDeviceStorageDeticalBean.class);
                            Intent intent = new Intent(mContext, OssDeviceDeticalTotalActivity.class);
                            intent.putExtra("deviceType", 4);
                            intent.putExtra("bean", bean);
                            startActivity(intent);
                        }else{
                            MyControl.circlerDialog(OssJKDeviceListStorActivity.this,getString(R.string.mD_暂无设备详情数据),1,false);
                        }
                    }else if (result == 22){
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
                    }else {
                        MyControl.circlerDialog(OssJKDeviceListStorActivity.this,getString(R.string.mD_暂无设备详情数据),result,false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }

            @Override
            public void LoginError(String str) {
            }
        });
    }
    private void initIntent() {
        Intent intent = getIntent();
        device_state = intent.getIntExtra(Constant.OssJK_State,Constant.OssJK_State_online);
        device_type = intent.getIntExtra(Constant.OssJK_Dev_type,Constant.OssJK_DeviceType_stor);
        accessStatus = intent.getIntExtra("accessStatus",0);
        agentCode = intent.getStringExtra("agentCode");
        userName = intent.getStringExtra("userName");
        plantName = intent.getStringExtra("plantName");
        deviceSn = intent.getStringExtra("deviceSn");
        datalogSn = intent.getStringExtra("datalogSn");
        switch (device_state){
            case Constant.OssJK_State_online:
                radioGroup.check(R.id.radioGroup_rb1);
                break;
            case Constant.OssJK_State_err:
                radioGroup.check(R.id.radioGroup_rb3);
                break;
            case Constant.OssJK_State_offline:
                radioGroup.check(R.id.radioGroup_rb4);
                break;
            case Constant.OssJK_State_charge_storage:
                radioGroup.check(R.id.radioGroup_rb5);
                break;
            case Constant.OssJK_State_discharge_storage:
                radioGroup.check(R.id.radioGroup_rb6);
                break;
        }
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
        setHeaderTitle(headerView,"储能机列表");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.radioGroup_rb1:
                device_state = Constant.OssJK_State_online;
                break;
            case R.id.radioGroup_rb3:
                device_state = Constant.OssJK_State_err;
                break;
            case R.id.radioGroup_rb4:
                device_state = Constant.OssJK_State_offline;
                break;
            case R.id.radioGroup_rb5:
                device_state = Constant.OssJK_State_charge_storage;
                break;
            case R.id.radioGroup_rb6:
                device_state = Constant.OssJK_State_discharge_storage;
                break;
        }
        refresh(1);
    }
}
