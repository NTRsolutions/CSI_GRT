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

import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.adapter.OssJKDeviceTitleAdapter;
import com.growatt.shinephone.adapter.OssJKQuesTitleAdapter;
import com.growatt.shinephone.adapter.OssLogoutAdapter;
import com.growatt.shinephone.bean.OssJKDeviceTitleBean;
import com.growatt.shinephone.bean.OssJKInvDeviceNumBean;
import com.growatt.shinephone.bean.OssJKMainBean;
import com.growatt.shinephone.bean.OssJKQuesTitleBean;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
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
 * 集成商主入口
 */
@ContentView(R.layout.activity_oss_jk)
public class OssJKActivity extends DemoBase implements View.OnClickListener{
    @ViewInject(R.id.ivOssJkTitle)
    ImageView ivOssJkTitle;
    private long mExitTime;
    //设备管理recycleView及数据集合
    @ViewInject(R.id.recycleViewDevice)
    RecyclerView recycleViewDevice;
    private List<OssJKDeviceTitleBean> deviceList;
    private OssJKDeviceTitleAdapter deviceAdapter;
    private int[] imgResDevices = {R.drawable.ossjk_today_icon,R.drawable.ossjk_total_icon,R.drawable.ossjk_inv_icon,R.drawable.ossjk_power_icon};
    private String[] titleDevices;
    private String[] unitDevices;
    private RecyclerView.LayoutManager gridLayoutManager;
    //工单管理recycleView及数据集合
    @ViewInject(R.id.recycleViewQues)
    RecyclerView recycleViewQues;
    private List<OssJKQuesTitleBean> quesList;
    private OssJKQuesTitleAdapter quesAdapter;
    private int[] imgResQues = {R.drawable.ossjk_processing_icon,R.drawable.ossjk_follow_up2,R.drawable.ossjk_untreated2,R.drawable.ossjk_processed_icon};
    private String[] titleQues;
    //登出数据
    private RecyclerView serverRecycler;
    private CommonAdapter serverAdapter;
    private CustomBasePopupWindow mPopupWindow;
    private List<String> logoutList = new ArrayList<>();
    //已接入逆变器数据
    @ViewInject(R.id.tvInvTotal) TextView tvInvTotal;
    @ViewInject(R.id.tvOnlineNumInv) TextView tvOnlineNumInv;
    @ViewInject(R.id.tvWaitNumInv) TextView tvWaitNumInv;
    @ViewInject(R.id.tvErrNumInv) TextView tvErrNumInv;
    @ViewInject(R.id.tvOfflineNumInv) TextView tvOfflineNumInv;
    //初始化点击按钮
    @ViewInject(R.id.ll_onlineInv) LinearLayout ll_onlineInv;
    @ViewInject(R.id.ll_offlineInv) LinearLayout ll_offlineInv;
    @ViewInject(R.id.ll_errInv) LinearLayout ll_errInv;
    @ViewInject(R.id.ll_waitInv) LinearLayout ll_waitInv;
    @ViewInject(R.id.ll_total) LinearLayout ll_total;
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
        String title = "OSS系统";
        if (Cons.ossUserBean != null && (!TextUtils.isEmpty(Cons.ossUserBean.getCompany()))){
            title = Cons.ossUserBean.getCompany();
        }
        setHeaderTitle(headerView,title);
        logoutList.add(getString(R.string.fragment4_logout));
//        logoutList.add(getString(R.string.m71));
        setHeaderImage(headerView, R.drawable.oss_layout, Position.LEFT, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutDialog(v);
            }
        });
    }
    private void setOnclick(View... views) {
        for (View v : views){
            v.setOnClickListener(this);
        }
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
                                case 0://登出
                                    OssUtils.logoutOss(OssJKActivity.this,false);
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

    /**
     * 刷新集成商概览数据,和逆变器数量
     */
    private void refresh() {
        PostUtil.post(OssUrls.postOssJK_MainData(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {}
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        String obj = jsonObject.getJSONObject("obj").toString();
                        OssJKMainBean bean = new Gson().fromJson(obj,OssJKMainBean.class);
                        if (bean != null){
                            List<String> newList = new ArrayList<>();
                            newList.add(bean.getTodayEnergy());
                            newList.add(bean.getTotalEnergy());
                            newList.add(bean.getTotalInvNum());
                            newList.add(bean.getTotalPower());
                            initDeviceList(newList);
                        }
                    }else {
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
//                        MyControl.circlerDialog(OssJKActivity.this,getString(R.string.geographydata_obtain_no),result,false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void LoginError(String str) {}
        });
        //获得逆变器数量
        PostUtil.post(OssUrls.postOssJKDvice_num(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("deviceType", "0");
                params.put("accessStatus", "1");
                params.put("agentCode","0");
                params.put("plantName","");
                params.put("userName","");
                params.put("datalogSn","");
                params.put("deviceSn","");
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        JSONObject obj = jsonObject.getJSONObject("obj");
                                OssJKInvDeviceNumBean invBean = new Gson().fromJson(obj.toString(),OssJKInvDeviceNumBean.class);
                                tvInvTotal.setText(invBean.getTotalNum() + "");
                                tvOnlineNumInv.setText(invBean.getOnlineNum() + "");
                                tvOfflineNumInv.setText(invBean.getOfflineNum() + "");
                                tvErrNumInv.setText(invBean.getFaultNum() + "");
                                tvWaitNumInv.setText(invBean.getWaitNum() + "");
                    }else {
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
//                        toast(getString(R.string.datalogcheck_check_no_server) + "(" + result + ")");
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
     * 先初始化代理商列表：跳转到集成商设备搜索界面
     * @param v
     */
    @Event(value = R.id.llJKDevice)
    private void llJKDevice(View v){
        jumpTo(OssJkMainActivity.class,false);
    }

    private void initListener() {
        setOnclick(ll_errInv,ll_offlineInv,ll_onlineInv,ll_waitInv,ll_total);
        deviceAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                jumpTo(OssJkMainActivity.class,false);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return true;
            }
        });
        quesAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return true;
            }
        });
    }

    private void initRecyclerView() {
        gridLayoutManager = new GridLayoutManager(this,2);
        titleDevices = new String[]{getString(R.string.今日发电),getString(R.string.累计发电量),"逆变器总数","装机功率"};
        unitDevices = new String[]{"(kWh)","(kWh)","(台)","(W)"};
        recycleViewDevice.setLayoutManager(gridLayoutManager);
        deviceList = new ArrayList<>();
        deviceAdapter = new OssJKDeviceTitleAdapter(this,R.layout.item_ossjk_device_title,deviceList);
        recycleViewDevice.setAdapter(deviceAdapter);
        initDeviceList(null);

        recycleViewQues.setLayoutManager(new GridLayoutManager(this,2));
        titleQues = new String[]{getString(R.string.正在处理),getString(R.string.m76待跟进),getString(R.string.未处理),getString(R.string.已处理)};
        quesList = new ArrayList<>();
        quesAdapter = new OssJKQuesTitleAdapter(this,R.layout.item_ossjk_ques_title,quesList);
        recycleViewQues.setAdapter(quesAdapter);
        initQuesList(null);
    }
     private void initQuesList(List<String> contents) {
        if (contents == null){
            contents = new ArrayList<>();
            for (int i = 0;i<4;i++){ contents.add("");}
        }
        List<OssJKQuesTitleBean> newList = new ArrayList<>();
        for (int i = 0;i<4;i++){
            OssJKQuesTitleBean bean = new OssJKQuesTitleBean();
            bean.setImgRes(imgResQues[i]);
            bean.setTitle(titleQues[i]);
            bean.setContent(contents.get(i));
            newList.add(bean);
        }
        quesAdapter.addAll(newList,true);
    }
    private void initDeviceList(List<String> contents) {
        if (contents == null){
            contents = new ArrayList<>();
            for (int i = 0;i<4;i++){ contents.add("");}
        }
        List<OssJKDeviceTitleBean> newList = new ArrayList<>();
        for (int i = 0;i<4;i++){
            OssJKDeviceTitleBean bean = new OssJKDeviceTitleBean();
            bean.setImgRes(imgResDevices[i]);
            bean.setUnit(unitDevices[i]);
            bean.setTitle(titleDevices[i]);
            bean.setContent(contents.get(i));
            newList.add(bean);
        }
        deviceAdapter.addAll(newList,true);
    }

    private void initView() {
//        Glide.with(this).load(R.drawable.oss_title).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.oss_title).into(new GlideDrawableImageViewTarget(ivOssJkTitle, 1));
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
                 jumpInv(Constant.OssJK_State_all);
                break;
        }
    }
    public void jumpInv(int deviceStatue){
        Intent intent = new Intent(mContext,OssJKDeviceListActivity.class);
        intent.putExtra(Constant.OssJK_State,deviceStatue);
        intent.putExtra(Constant.OssJK_Dev_type,"0");
        intent.putExtra("accessStatus","1");
        intent.putExtra("agentCode","0");
        intent.putExtra("plantName","");
        intent.putExtra("userName","");
        intent.putExtra("datalogSn","");
        intent.putExtra("deviceSn","");
        jumpTo(intent,false);
    }
}
