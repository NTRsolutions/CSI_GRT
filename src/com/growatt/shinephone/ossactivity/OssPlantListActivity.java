package com.growatt.shinephone.ossactivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.adapter.OssDeviceAdapter;
import com.growatt.shinephone.bean.OssPlantListBean;
import com.growatt.shinephone.bean.OssSearchAllPlantListBean;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@ContentView(R.layout.activity_oss_plant_list)
public class OssPlantListActivity extends DemoBase {
    String strNull = "暂无数据";
    @ViewInject(R.id.recycleView)
    RecyclerView recyclerView;
    private List<OssPlantListBean> datas;
    private OssDeviceAdapter adapter;
    //上级页面获取到的数据
    private ArrayList<OssPlantListBean> intentList;//数据list
    private int currentPage = 1;//当前页面
    private int totalPage = 1;//总页码
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initHeaderView();
        initRecyclerView();
        initListener();
    }
    //跳转到设备列表
    public void jumpDeviceList(int position){
        Intent intent = new Intent(mContext,OssDeviceListActivity.class);
        intent.putExtra("currentPage",currentPage);
        intent.putExtra("totalPage",totalPage);
        intent.putExtra("deviceType",1);
        intent.putExtra("plantId",datas.get(position).getId());
        intent.putExtra(Constant.ossJumpToDeviceList,Constant.ossPlantToDeviceList);
        intent.putParcelableArrayListExtra("list",null);
        startActivity(intent);
//        if (adapter.getItemCount() == 1){finish();}
    }
    private void initListener() {
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
               jumpDeviceList(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                // 滑动状态停止并且剩余少于两个item时，自动加载下一页
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1>=mLayoutManager.getItemCount()) {
                    if (currentPage < totalPage){
                        searchUserDownPlantInfor(userName,++currentPage);
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
    }

    private void initIntent() {
        Intent intent = getIntent();
        intentList = intent.getParcelableArrayListExtra("list");
        userName = intent.getStringExtra("userName");
    }

    private void initRecyclerView() {
        datas = new ArrayList<>();
        datas.addAll(intentList);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new OssDeviceAdapter(mContext,R.layout.item_oss_plantlist,datas);
        recyclerView.setAdapter(adapter);
//        if (adapter.getItemCount() == 1){
//            jumpDeviceList(0);
//        }
    }
    /**
     * 搜索用户下电站信息
     * @param userName
     * @param page:当前搜索页面
     */
    private void searchUserDownPlantInfor(final String userName, final int page) {
        Mydialog.Show(this,"");
        PostUtil.post(OssUrls.postOssSearchUser_PlantList(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("userName",userName);
                params.put("page",page + "");
            }

            @Override
            public void success(String json) {
                Mydialog.Dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        JSONObject obj = jsonObject.getJSONObject("obj");
                        int currentPage = obj.getInt("currentPage");
                        totalPage = obj.getInt("totalPage");
                        ArrayList<OssPlantListBean> newList = new ArrayList<>();
                        JSONArray jsonArr = obj.getJSONArray("plantList");
                        for (int i = 0,size = jsonArr.length();i<size;i++){
                            OssPlantListBean userBean = new OssPlantListBean();
                            OssSearchAllPlantListBean bean = new Gson().fromJson(jsonArr.getJSONObject(i).toString(),OssSearchAllPlantListBean.class);
                            userBean.setName2(getString(R.string.geographydata_timezone));
                            userBean.setValue2(bean.getTimezone());
                            userBean.setName1(getString(R.string.powerstation_plantname));
                            userBean.setValue1(bean.getPlantName());
                            userBean.setId(bean.getId());
                            newList.add(userBean);
                        }
                        //分页
                        if (page == 1){
                            if (newList.size() == 0){toast(strNull);}
                            adapter.addAll(newList,true);
                        }else {
                            if (newList.size() == 0){
                                toast(R.string.DataloggersAct_more_data);
                                if (currentPage>1){currentPage = currentPage-1;}
                            }
                            adapter.addAll(newList,false);
                        }
                    }else {
                        if (currentPage>1){
                            currentPage = currentPage-1;
                        }
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
//                        handler.sendEmptyMessage(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                   if (currentPage > 1){currentPage = currentPage-1;}
                    Mydialog.Dismiss();
                }
            }

            @Override
            public void LoginError(String str) {
                if (currentPage > 1){currentPage = currentPage-1;}
                Mydialog.Dismiss();
            }
        });
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
        setHeaderTvTitle(headerView, "修改用户信息", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到用户信息界面：传入用户名
                Intent intent = new Intent(OssPlantListActivity.this,OssUserInfoActivity.class);
                intent.putExtra("userName",userName);
                jumpTo(intent,false);
            }
        });
        setHeaderTitle(headerView,getString(R.string.电站列表));
    }

    //逆变器handler
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 0:text = "返回异常";break;
                case 1:text = "成功";break;
                case 2:text = "远程获取数据错误";break;
                case 4:text = "服务器地址为空";break;
                case 5:text = "搜索不到信息";break;
                default:
                    text = msg.what +  "";
                    break;
            }
            toast(text);
        }
    };
}
