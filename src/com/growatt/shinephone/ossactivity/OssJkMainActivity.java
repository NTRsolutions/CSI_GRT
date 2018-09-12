package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.adapter.OssJKAccessStatusAdapter;
import com.growatt.shinephone.adapter.OssJKAgentAdapter;
import com.growatt.shinephone.bean.OssJKInvDeviceNumBean;
import com.growatt.shinephone.bean.OssJKStorageDeviceNumBean;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.view.CustomBasePopupWindow;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_oss_jk_main)
public class OssJkMainActivity extends DemoBase implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{
    @ViewInject(R.id.tvTotalNum) TextView tvTotalNum;
    @ViewInject(R.id.tvSearch) TextView tvSearch;
    @ViewInject(R.id.tvAccessTypeTxt) TextView tvAccessTypeTxt;
    //储能机view
    @ViewInject(R.id.tvOnlineNumStorage) TextView tvOnlineNumStorage;
    @ViewInject(R.id.tvOfflineNumStorage) TextView tvOfflineNumStorage;
    @ViewInject(R.id.tvErrNumStorage) TextView tvErrNumStorage;
    @ViewInject(R.id.tvChargeNumStorage) TextView tvChargeNumStorage;
    @ViewInject(R.id.tvDischargeNumStorage) TextView tvDischargeNumStorage;
    //逆变器view
    @ViewInject(R.id.tvOnlineNumInv) TextView tvOnlineNumInv;
    @ViewInject(R.id.tvOfflineNumInv) TextView tvOfflineNumInv;
    @ViewInject(R.id.tvErrNumInv) TextView tvErrNumInv;
    @ViewInject(R.id.tvWaitNumInv) TextView tvWaitNumInv;
    //edittext view
    @ViewInject(R.id.etPlantName) EditText etPlantName;
    @ViewInject(R.id.etUserName) EditText etUserName;
    @ViewInject(R.id.etDeviceSN) EditText etDeviceSN;
    @ViewInject(R.id.etDatalogSN) EditText etDatalogSN;
    //初始化点击按钮
    @ViewInject(R.id.ll_onlineInv) LinearLayout ll_onlineInv;
    @ViewInject(R.id.ll_offlineInv) LinearLayout ll_offlineInv;
    @ViewInject(R.id.ll_errInv) LinearLayout ll_errInv;
    @ViewInject(R.id.ll_waitInv) LinearLayout ll_waitInv;
    @ViewInject(R.id.ll_total) LinearLayout ll_total;
    @ViewInject(R.id.ll_onlineStorage) LinearLayout ll_onlineStorage;
    @ViewInject(R.id.ll_offlineStorage) LinearLayout ll_offlineStorage;
    @ViewInject(R.id.ll_errStorage) LinearLayout ll_errStorage;
    @ViewInject(R.id.ll_chargeStorage) LinearLayout ll_chargeStorage;
    @ViewInject(R.id.ll_disChargeStorage) LinearLayout ll_disChargeStorage;

    @ViewInject(R.id.invLayout)
    View invLayout;
    @ViewInject(R.id.storageLayout)
    View storageLayout;
    @ViewInject(R.id.ll_agent)
    LinearLayout ll_agent;
    @ViewInject(R.id.radioGroup)
    RadioGroup radioGroup;
    @ViewInject(R.id.tvAccessStatus)
    TextView tvAccessStatus;//j集成商设备接入状态文本显示
    @ViewInject(R.id.tvAgent)
    TextView tvAgent;//集成商下当前代理商
    //所选设备类型
    private int device_type = Constant.OssJK_DeviceType_inv;
    //是否接入列表属性（已接入、未接入）
    private RecyclerView recyclerView_status;
    private OssJKAccessStatusAdapter adapter_status;
//    private List<String> list_status = Arrays.asList("所有设备","已接入设备","未接入设备");
    private List<String> list_status = Arrays.asList("已接入设备","未接入设备");
    private CustomBasePopupWindow pop_status;
    //2-已接入;3-未接入
    private int access_status = Constant.OssJK_DeviceStatus_yes;//接入类型
    //代理商列表属性
    private RecyclerView recyclerView_agent;
    private OssJKAgentAdapter adapter_agent;
    private List<Map<String,String>> list_agent = new ArrayList<>();
    private CustomBasePopupWindow pop_agent;
    private String agentCode = "0";//代理商编码
    //代理商数据集合
    List<Map<String,String>> newAgentList = new ArrayList<>();//数据源
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initIntent();
        initHeaderView();
        initView();
        initListener();
        initAgentList();
        refreshNum();
    }

    private void initView() {
        tvAccessTypeTxt.setText(list_status.get(0));
        HashMap<String,String> map1 = new HashMap<>();
        map1.put(Constant.Agent_Name,"所有代理商");
        map1.put(Constant.Agent_Code,"0");
        HashMap<String,String> map2 = new HashMap<>();
        map2.put(Constant.Agent_Name,"无代理商");
        map2.put(Constant.Agent_Code,"1");
        newAgentList.add(map1);
        newAgentList.add(map2);
    }

    public void refreshNum(){
        Mydialog.Show(this,"");
        PostUtil.post(OssUrls.postOssJKDvice_num(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("deviceType",device_type + "");
                params.put("accessStatus",access_status + "");
                params.put("agentCode",agentCode);
                params.put("plantName",etPlantName.getText().toString().trim());
                params.put("userName",etUserName.getText().toString().trim());
                params.put("datalogSn",etDatalogSN.getText().toString().trim());
                params.put("deviceSn",etDeviceSN.getText().toString().trim());
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        JSONObject obj = jsonObject.getJSONObject("obj");
                        switch (device_type){
                            case Constant.OssJK_DeviceType_inv:
                                OssJKInvDeviceNumBean invBean = new Gson().fromJson(obj.toString(),OssJKInvDeviceNumBean.class);
                                tvTotalNum.setText(invBean.getTotalNum() + "");
                                tvOnlineNumInv.setText(invBean.getOnlineNum() + "");
                                tvOfflineNumInv.setText(invBean.getOfflineNum() + "");
                                tvErrNumInv.setText(invBean.getFaultNum() + "");
                                tvWaitNumInv.setText(invBean.getWaitNum() + "");
                                break;
                            case Constant.OssJK_DeviceType_stor:
                                OssJKStorageDeviceNumBean storageBean = new Gson().fromJson(obj.toString(),OssJKStorageDeviceNumBean.class);
                                tvTotalNum.setText(storageBean.getTotalNum() + "");
                                tvOnlineNumStorage.setText(storageBean.getOnlineNum() + "");
                                tvOfflineNumStorage.setText(storageBean.getOfflineNum() + "");
                                tvErrNumStorage.setText(storageBean.getFaultNum() + "");
                                tvChargeNumStorage.setText(storageBean.getChargeNum() + "");
                                tvDischargeNumStorage.setText(storageBean.getDischargeNum() + "");
                                break;
                        }
                    }else {
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
//                        toast(getString(R.string.datalogcheck_check_no_server) + "(" + result + ")");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {}
        });
    }
    private void initIntent() {
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        try {
        JSONObject jsonObject = new JSONObject(json);
        HashMap<String,String> map1 = new HashMap<>();
        map1.put(Constant.Agent_Name,"所有代理商");
        map1.put(Constant.Agent_Code,"0");
        HashMap<String,String> map2 = new HashMap<>();
        map2.put(Constant.Agent_Name,"无代理商");
        map2.put(Constant.Agent_Code,"1");
            newAgentList.add(map1);
            newAgentList.add(map2);
        JSONArray obj = jsonObject.getJSONArray("obj");
        for (int i = 0,length = obj.length();i < length ; i++){
            JSONObject agent = obj.getJSONObject(i);
            HashMap<String,String> map = new HashMap<>();
            map.put(Constant.Agent_Name,agent.getString(Constant.Agent_Name));
            map.put(Constant.Agent_Code,agent.getString(Constant.Agent_Code));
            newAgentList.add(map);
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取代理商列表
     */
    private void initAgentList() {
        PostUtil.post(OssUrls.postOssJKAgentList(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {}
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        JSONArray obj = jsonObject.getJSONArray("obj");
                        for (int i = 0,length = obj.length();i < length ; i++){
                            JSONObject agent = obj.getJSONObject(i);
                            HashMap<String,String> map = new HashMap<>();
                            map.put(Constant.Agent_Name,agent.getString(Constant.Agent_Name));
                            map.put(Constant.Agent_Code,agent.getString(Constant.Agent_Code));
                            newAgentList.add(map);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void LoginError(String str) {}
        });
    }


    private void initListener() {
        radioGroup.setOnCheckedChangeListener(this);
        setOnclick(ll_errInv,ll_offlineInv,ll_onlineInv,ll_waitInv,ll_total,ll_chargeStorage,ll_errStorage,ll_offlineStorage,ll_onlineStorage,ll_chargeStorage,ll_disChargeStorage,tvSearch);
    }

    private void setOnclick(View... views) {
        for (View v : views){
            v.setOnClickListener(this);
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
        setHeaderTitle(headerView,getString(R.string.设备管理));
    }

    /**
     * 弹框显示设备接入状态
     */
    @Event(value = R.id.ll_AccessStatus)
    private void showAccessStatusPop(View v){
        if (pop_status == null) {
            pop_status = new CustomBasePopupWindow(mContext, R.layout.item_oss_serverlist, v.getWidth(), true) {
                @Override
                public void init() {
                    recyclerView_status = (RecyclerView) mPopView.findViewById(R.id.recycleView);
                    recyclerView_status.setLayoutManager(new LinearLayoutManager(mContext));
                    adapter_status = new OssJKAccessStatusAdapter(mContext, R.layout.item_oss_serveritem, list_status);
                    recyclerView_status.setAdapter(adapter_status);
                    adapter_status.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                            pop_status.dismiss();
                            tvAccessStatus.setText(list_status.get(position));
                            tvAccessTypeTxt.setText(list_status.get(position));
                            //设置接入类型
                            int type = position + 1;
                            switch (type){
                                case 0:
                                    access_status = Constant.OssJK_DeviceStatus_all;
                                    break;
                                case 1:
                                    access_status = Constant.OssJK_DeviceStatus_yes;
                                    break;
                                case 2:
                                    access_status = Constant.OssJK_DeviceStatus_no;
                                    break;
                            }
                            refreshNum();//刷新数量
                        }

                        @Override
                        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                            return false;
                        }
                    });
                }
            };
        }
        pop_status.showAsDowm(v);
    }

    /**
     * 弹框显示代理商列表
     */
    @Event(value = R.id.ll_agent)
    private void showAgentListPop(View v){
        if (pop_agent == null){
            pop_agent = new CustomBasePopupWindow(mContext,R.layout.item_oss_serverlist,v.getWidth(),true) {
                @Override
                public void init() {
                    recyclerView_agent = (RecyclerView) mPopView.findViewById(R.id.recycleView);
                    recyclerView_agent.setLayoutManager(new LinearLayoutManager(mContext));
                    list_agent.addAll(newAgentList);
                    adapter_agent = new OssJKAgentAdapter(mContext,R.layout.item_oss_serveritem,list_agent);
                    recyclerView_agent.setAdapter(adapter_agent);
                    adapter_agent.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                            pop_agent.dismiss();
                            Map<String,String> map = list_agent.get(position);
                            tvAgent.setText(map.get(Constant.Agent_Name));
                            agentCode = map.get(Constant.Agent_Code);
                            refreshNum();//刷新数量
                        }

                        @Override
                        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                            return false;
                        }
                    });
                }
            };
        }
        adapter_agent.addAll(newAgentList,true);
        pop_agent.showAsDowm(v);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.radioGroup_rb1:
                //设置设备类型
                device_type = Constant.OssJK_DeviceType_inv;
                //隐藏或显示设备状态
                MyUtils.showAllView(invLayout);
                MyUtils.hideAllView(View.INVISIBLE,storageLayout);
                //设置文本内容
                refreshNum();
                break;
            case R.id.radioGroup_rb2:
                device_type = Constant.OssJK_DeviceType_stor;
                MyUtils.showAllView(storageLayout);
                MyUtils.hideAllView(View.INVISIBLE,invLayout);
                refreshNum();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_onlineInv:
                jumpInv(Constant.OssJK_State_online);
                break;
            case R.id.ll_offlineInv:
                jumpInv(Constant.OssJK_State_offline);
                break;
            case R.id.ll_waitInv:
                jumpInv(Constant.OssJK_State_wait_inv);
                break;
            case R.id.ll_errInv:
                jumpInv(Constant.OssJK_State_err);
                break;
            case R.id.ll_total:
                if ( radioGroup.getCheckedRadioButtonId() == R.id.radioGroup_rb1){
                    jumpInv(Constant.OssJK_State_all);
                }else {
                    jumpStorage(Constant.OssJK_State_all);
                }
                break;
            case R.id.ll_onlineStorage:
                jumpStorage(Constant.OssJK_State_online);
                break;
            case R.id.ll_offlineStorage:
                jumpStorage(Constant.OssJK_State_offline);
                break;
            case R.id.ll_errStorage:
                jumpStorage(Constant.OssJK_State_err);
                break;
            case R.id.ll_chargeStorage:
                jumpStorage(Constant.OssJK_State_charge_storage);
                break;
            case R.id.ll_disChargeStorage:
                jumpStorage(Constant.OssJK_State_discharge_storage);
                break;
            case R.id.tvSearch:
                refreshNum();
                break;
        }
    }
    public void jumpInv(int deviceStatue){
        Intent intent = new Intent(mContext,OssJKDeviceListActivity.class);
        intent.putExtra(Constant.OssJK_State,deviceStatue);
        intent.putExtra(Constant.OssJK_Dev_type,device_type);
        intent.putExtra("accessStatus",access_status);
        intent.putExtra("agentCode",agentCode);
        intent.putExtra("plantName",etPlantName.getText().toString().trim());
        intent.putExtra("userName",etUserName.getText().toString().trim());
        intent.putExtra("datalogSn",etDatalogSN.getText().toString().trim());
        intent.putExtra("deviceSn",etDeviceSN.getText().toString().trim());
        jumpTo(intent,false);
    }
    public void jumpStorage(int deviceStatue){
        Intent intent = new Intent(mContext,OssJKDeviceListStorActivity.class);
        intent.putExtra(Constant.OssJK_State,deviceStatue);
        intent.putExtra(Constant.OssJK_Dev_type,device_type);
        intent.putExtra("accessStatus",access_status);
        intent.putExtra("agentCode",agentCode);
        intent.putExtra("plantName",etPlantName.getText().toString().trim());
        intent.putExtra("userName",etUserName.getText().toString().trim());
        intent.putExtra("datalogSn",etDatalogSN.getText().toString().trim());
        intent.putExtra("deviceSn",etDeviceSN.getText().toString().trim());
        jumpTo(intent,false);
    }
}
