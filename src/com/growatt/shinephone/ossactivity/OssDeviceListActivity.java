package com.growatt.shinephone.ossactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DoActivity;
import com.growatt.shinephone.adapter.OssDeviceListAdapter;
import com.growatt.shinephone.bean.OssDeviceDatalogBean;
import com.growatt.shinephone.bean.OssDeviceInvBean;
import com.growatt.shinephone.bean.OssDeviceListBean;
import com.growatt.shinephone.bean.OssDeviceStorageBean;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.view.EmptyRecyclerView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.growatt.shinephone.R.string.DataloggersAct_more_data;

/**
 * oss 设备列表
 */
@ContentView(R.layout.activity_oss_device_list)
public class OssDeviceListActivity extends DoActivity implements RadioGroup.OnCheckedChangeListener{
    private String datalogTitle = "所属采集器";
    private String userName = "所属用户";
    String strNull = "暂无数据";
    //result code:服务器返回码
    private final int result_err = 0;//返回异常
    private final int result_normal = 1;//返回正常
    private final int result_serverErr = 2;//远程获取数据错误
    private final int result_searchTypeNull = 3;//搜索类型为空
    private final int result_serverUrlNull = 4;//搜索服务器地址为空
    private final int result_msgNull = 21;//搜索不到信息
    @ViewInject(R.id.recycleView)
    EmptyRecyclerView mRecycleView;
    @ViewInject(R.id.emptyView)
    View emptyView;
    @ViewInject(R.id.radioGroup)
    RadioGroup radioGroup;
    @ViewInject(R.id.radioGroup_rb1)
    RadioButton radioGroup_rb1;
    @ViewInject(R.id.radioGroup_rb2)
    RadioButton radioGroup_rb2;
    @ViewInject(R.id.radioGroup_rb3)
    RadioButton radioGroup_rb3;
    private Context context;
//    private List<OssDeviceListBean> invList = new ArrayList<>();
//    private List<OssDeviceListBean> storageList = new ArrayList<>();
//    private List<OssDeviceListBean> datalogList = new ArrayList<>();
    private List<OssDeviceListBean> mList = new ArrayList<>();
    private OssDeviceListAdapter mAdapter;
    //页码
    private int currentPage = 1;
    private int totalPage = 1;
    private int jumpType;
    /*情况一：在有电站Id时*/
    //电站Id
    private int plantId;
    /*情况二：在有设备序列号或者别名时*/
    //设备别名和sn号
    private String alias;
    private String deviceSn;

    //设备信息搜索类型
    private int device_searchType ;
    private final int device_searchType_datalog = 0;//根据采集器
    private final int device_searchType_inv = 1;//根据逆变器
    private final int device_searchType_storage = 2;//根据储能机
    //获取设备的原始数据集合
    private ArrayList<OssDeviceDatalogBean> listDatalog = new ArrayList<>();
    private ArrayList<OssDeviceInvBean> listInv = new ArrayList<>();
    private ArrayList<OssDeviceStorageBean> listStorage = new ArrayList<>();
    //分页加载
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private int nowPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initRecycleView();
        initIntent();
        initHeaderView();
        initListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        nowPage = 1;
        switch (jumpType){
            case Constant.ossPlantToDeviceList:
                searchPlantDownDevice(plantId,device_searchType,nowPage);
                break;
            case Constant.ossSnToDeviceList:
                searchSnDownDeviceInfor(deviceSn,alias,OssUrls.ossCRUDUrl,device_searchType,nowPage);
                break;
        }
    }

    private void initIntent() {
        Intent intent = getIntent();
        currentPage = intent.getIntExtra(Constant.currentPage,1);
        totalPage = intent.getIntExtra(Constant.totalPage,1);
        device_searchType = intent.getIntExtra("deviceType",device_searchType_inv);
        jumpType = intent.getIntExtra(Constant.ossJumpToDeviceList,Constant.ossPlantToDeviceList);
        switch (jumpType){
            case Constant.ossPlantToDeviceList:
                ossPlantListTo(intent);
                break;
            case Constant.ossSnToDeviceList:
                ossDeviceSnTo(intent);
                break;
        }
    }
    /**
     * 由设备sn或别名跳转而来
     * @param intent
     */
    private void ossDeviceSnTo(Intent intent) {
        deviceSn = intent.getStringExtra("deviceSn");
        alias = intent.getStringExtra("alias");
        switch (device_searchType){
            case device_searchType_datalog:
                radioGroup_rb1.setChecked(true);
                break;
            case device_searchType_inv:
                radioGroup_rb2.setChecked(true);
                break;
            case device_searchType_storage:
                radioGroup_rb3.setChecked(true);
                break;
        }
        //调用接口获取数据
        searchSnDownDeviceInfor(deviceSn,alias,OssUrls.ossCRUDUrl,device_searchType,1);
    }

    /**
     * 根据设备sn或者别名搜索设备列表信息
     * @param deviceSn：设备序列号
     * @param alias：别名
     * @param url：服务器地址
     * @param deviceType：设备类型
     * @param page：
     */
    private void searchSnDownDeviceInfor(final String deviceSn, final String alias, final String url, final int deviceType, final int page) {
        Mydialog.Show(this,"");
        PostUtil.post(OssUrls.postOssSearchDevice(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("deviceSn",deviceSn);
                params.put("alias",alias);
                params.put("serverAddr",url);
                params.put("deviceType",deviceType+"");
                params.put("page",page+"");
            }
            @Override
            public void success(String json) {
                parseDeviceList(json,page);
            }

            @Override
            public void LoginError(String str) {
                if (nowPage > 1){ nowPage = nowPage-1;}
            }
        });
    }

    /**
     * 由电站列表跳转而来
     * @param intent
     */
    private void ossPlantListTo(Intent intent) {
        plantId = intent.getIntExtra("plantId",0);
        List<OssDeviceListBean> newList = new ArrayList<>();
        switch (device_searchType){
            case device_searchType_datalog:
                ArrayList<OssDeviceDatalogBean> listDatalog = intent.getParcelableArrayListExtra(Constant.jumpList);
                if (listDatalog != null) {
                    this.listDatalog = listDatalog;
                    for (OssDeviceDatalogBean beanDatalog : listDatalog) {
                        OssDeviceListBean bean = new OssDeviceListBean();
                        bean.setTvSn(beanDatalog.getSerialNum());
                        bean.setTvAlias(beanDatalog.getAlias());
                        bean.setTvLost(beanDatalog.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online));
                        bean.setTvType(beanDatalog.getDeviceType());
                        bean.setTvDatalogTitle(userName);
                        bean.setTvDatalogContent(beanDatalog.getUserName());
                        bean.setDeviceType(0);
                        if (beanDatalog.isLost()){
                            bean.setStatus(0);
                        }else {
                            bean.setStatus(1);
                        }
                        newList.add(bean);
                    }
                    mAdapter.addAll(newList,true);
                }else {
                    searchPlantDownDevice(plantId,device_searchType,1);
                }
                radioGroup_rb1.setChecked(true);
                break;
            case device_searchType_inv:
                ArrayList<OssDeviceInvBean> listInv = intent.getParcelableArrayListExtra(Constant.jumpList);
                if (listInv != null) {
                    this.listInv = listInv;
                    for (OssDeviceInvBean beanInv : listInv) {
                        OssDeviceListBean bean = new OssDeviceListBean();
                        bean.setTvSn(beanInv.getSerialNum());
                        bean.setTvAlias(beanInv.getAlias());
                        bean.setTvLost(beanInv.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online));
                        bean.setTvType(getString(R.string.all_interver));
                        bean.setTvDatalogTitle(datalogTitle);
                        bean.setTvDatalogContent(beanInv.getDataLogSn());
                        bean.setDeviceType(1);
                        bean.setStatus(beanInv.getStatus());
                        newList.add(bean);
                    }
                    mAdapter.addAll(newList,true);
                }else {
                    searchPlantDownDevice(plantId,device_searchType,1);
                }
                radioGroup_rb2.setChecked(true);
                break;
            case device_searchType_storage:
                ArrayList<OssDeviceStorageBean> listStorage = intent.getParcelableArrayListExtra(Constant.jumpList);
                if (listStorage != null) {
                    this.listStorage = listStorage;
                    for (OssDeviceStorageBean beanStorage : listStorage) {
                        OssDeviceListBean bean = new OssDeviceListBean();
                        bean.setTvSn(beanStorage.getSerialNum());
                        bean.setTvAlias(beanStorage.getAlias());
                        bean.setTvLost(beanStorage.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online));
                        bean.setTvType(getString(R.string.all_storage));
                        bean.setTvDatalogTitle(datalogTitle);
                        bean.setTvDatalogContent(beanStorage.getDataLogSn());
                        bean.setDeviceType(2);
                        bean.setStatus(beanStorage.getStatus());
                        newList.add(bean);
                    }
                    mAdapter.addAll(newList,true);
                }else {
                    searchPlantDownDevice(plantId,device_searchType,1);
                }
                radioGroup_rb3.setChecked(true);
                break;
        }
    }
//跳转到界面详情
    public void jumpDetical(int position){
        Intent intent = new Intent(mContext,OssDeviceDeticalTotalActivity.class);
        intent.putExtra("deviceType",device_searchType);
        switch (device_searchType){
            case device_searchType_datalog:
                intent.putExtra("bean",listDatalog.size() > position ? listDatalog.get(position):new OssDeviceDatalogBean());
                break;
            case device_searchType_inv:
                intent.putExtra("bean",listInv.size() > position ? listInv.get(position):new OssDeviceInvBean());
                break;
            case device_searchType_storage:
                intent.putExtra("bean",listStorage.size() > position ? listStorage.get(position):new OssDeviceStorageBean());
                break;
        }
        startActivity(intent);
//        if (mAdapter.getItemCount() == 1){
//            finish();
//        }
    }
    private void initListener() {
        radioGroup.setOnCheckedChangeListener(this);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                jumpDetical(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return true;
            }
        });
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                // 滑动状态停止并且剩余少于两个item时，自动加载下一页
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1>=mLayoutManager.getItemCount()) {
                    if (nowPage < totalPage){
                        nowPage = nowPage + 1;
                        switch (jumpType){
                            case Constant.ossPlantToDeviceList:
                                searchPlantDownDevice(plantId,device_searchType,nowPage);
                                break;
                            case Constant.ossSnToDeviceList:
                                searchSnDownDeviceInfor(deviceSn,alias,OssUrls.ossCRUDUrl,device_searchType,nowPage);
                                break;
                        }
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

    private void initRecycleView() {
        mLayoutManager = new LinearLayoutManager(context);
        mRecycleView.setLayoutManager(mLayoutManager);
        mAdapter = new OssDeviceListAdapter(context,R.layout.item_oss_devicelist,mList);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setEmptyView(emptyView);
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
        setHeaderTitle(headerView,"设备列表");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        nowPage =1;
        switch (checkedId){
            case R.id.radioGroup_rb1:
                device_searchType = device_searchType_datalog;
                break;
            case R.id.radioGroup_rb2:
                device_searchType = device_searchType_inv;
                break;
            case R.id.radioGroup_rb3:
                device_searchType = device_searchType_storage;
                break;
        }
        //根据跳转类型执行搜索操作
        switch (jumpType){
            case Constant.ossPlantToDeviceList:
                searchPlantDownDevice(plantId,device_searchType,1);
                break;
            case Constant.ossSnToDeviceList:
                searchSnDownDeviceInfor(deviceSn,alias,OssUrls.ossCRUDUrl,device_searchType,1);
                break;
        }
    }
    /**
     * 根据电站id,设备类型搜索电站下所有设备
     * @param plantId：电站id
     * @param deviceType:要搜索的设备类型
     * @param page:页数
     */
    private void searchPlantDownDevice(final int plantId, final int deviceType, final int page) {
        Mydialog.Show(this,"");
        PostUtil.post(OssUrls.postOssSearchPlant_DeviceList(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("plantId",plantId + "");
                params.put("deviceType",deviceType + "");
                params.put("page",page + "");
            }
            @Override
            public void success(String json) {
                    parseDeviceList(json,page);
            }
            @Override
            public void LoginError(String str) {
                if (nowPage > 1){ nowPage = nowPage-1;}
                Mydialog.Dismiss();
            }
        });
    }

    private void parseDeviceList(String json,int page) {
        Mydialog.Dismiss();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("result");
            if (result == 1) {
                JSONObject obj = jsonObject.getJSONObject("obj");
                int deviceType = obj.getInt("deviceType");
                int currentPage = obj.getInt("currentPage");
                totalPage = obj.getInt("totalPage");
                List<OssDeviceListBean> newList = new ArrayList<>();
                switch (deviceType) {
                    case device_searchType_datalog:
                        ArrayList<OssDeviceDatalogBean> datalogBeans = new ArrayList<>();
                        JSONArray jsonArrDatalog = obj.getJSONArray("datalogList");
                        for (int i = 0, length = jsonArrDatalog.length(); i < length; i++) {
                            OssDeviceDatalogBean beanDatalog = new Gson().fromJson(jsonArrDatalog.getJSONObject(i).toString(), OssDeviceDatalogBean.class);
                            datalogBeans.add(beanDatalog);
                            OssDeviceListBean bean = new OssDeviceListBean();
                            bean.setTvSn(beanDatalog.getSerialNum());
                            bean.setTvAlias(beanDatalog.getAlias());
                            bean.setTvLost(beanDatalog.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online));
                            bean.setTvType(beanDatalog.getDeviceType());
                            bean.setTvDatalogTitle(userName);
                            bean.setDeviceType(0);
                            if (beanDatalog.isLost()){
                                bean.setStatus(0);
                            }else {
                                bean.setStatus(1);
                            }
                            bean.setTvDatalogContent(beanDatalog.getUserName());
                            newList.add(bean);
                        }
                        if (page == 1) {
                            if (newList.size() == 0) {toast(strNull);}
                            mAdapter.addAll(newList, true);
                            listDatalog.clear();
                            listDatalog.addAll(datalogBeans);
                        } else {
                            if (newList.size() == 0) {toast(DataloggersAct_more_data);}
                            mAdapter.addAll(newList, false);
                            listDatalog.addAll(datalogBeans);
                        }
                        break;
                    case device_searchType_inv:
                        ArrayList<OssDeviceInvBean> invBeans = new ArrayList<>();
                        JSONArray jsonArrInv = obj.getJSONArray("invList");
                        for (int i = 0, length = jsonArrInv.length(); i < length; i++) {
                            OssDeviceInvBean beanInv = new Gson().fromJson(jsonArrInv.getJSONObject(i).toString(), OssDeviceInvBean.class);
                            invBeans.add(beanInv);
                            OssDeviceListBean bean = new OssDeviceListBean();
                            bean.setTvSn(beanInv.getSerialNum());
                            bean.setTvAlias(beanInv.getAlias());
                            bean.setTvLost(beanInv.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online));
                            bean.setTvType(getString(R.string.all_interver));
                            bean.setTvDatalogTitle(datalogTitle);
                            bean.setDeviceType(1);
                            bean.setStatus(beanInv.getStatus());
                            bean.setTvDatalogContent(beanInv.getDataLogSn());
                            newList.add(bean);
                        }
                        if (page == 1) {
                            if (newList.size() == 0) {toast(strNull);}
                            mAdapter.addAll(newList, true);
                            listInv.clear();
                            listInv.addAll(invBeans);
                        } else {
                            if (newList.size() == 0) {toast(DataloggersAct_more_data);}
                            mAdapter.addAll(newList, false);
                            listInv.addAll(invBeans);
                        }
                        break;
                    case device_searchType_storage:
                        ArrayList<OssDeviceStorageBean> storageBeans = new ArrayList<>();
                        JSONArray jsonArrStorage = obj.getJSONArray("storageList");
                        for (int i = 0, length = jsonArrStorage.length(); i < length; i++) {
                            OssDeviceStorageBean beanStorage = new Gson().fromJson(jsonArrStorage.getJSONObject(i).toString(), OssDeviceStorageBean.class);
                            storageBeans.add(beanStorage);
                            OssDeviceListBean bean = new OssDeviceListBean();
                            bean.setTvSn(beanStorage.getSerialNum());
                            bean.setTvAlias(beanStorage.getAlias());
                            bean.setTvLost(beanStorage.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online));
                            bean.setTvType(getString(R.string.all_storage));
                            bean.setTvDatalogTitle(datalogTitle);
                            bean.setTvDatalogContent(beanStorage.getDataLogSn());
                            bean.setDeviceType(2);
                            bean.setStatus(beanStorage.getStatus());
                            newList.add(bean);
                        }
                        if (page == 1) {
                            if (newList.size() == 0) {toast(strNull);}
                            mAdapter.addAll(newList, true);
                            listStorage.clear();
                            listStorage.addAll(storageBeans);
                        } else {
                            if (newList.size() == 0) {toast(DataloggersAct_more_data);}
                            mAdapter.addAll(newList, false);
                            listStorage.addAll(storageBeans);
                        }
                        break;
                }
//                //数量唯一时直接跳转
//                if (mAdapter.getItemCount() == 1){jumpDetical(0);}
            } else {
                if (page == 1) {
                    mAdapter.addAll(new ArrayList<OssDeviceListBean>(), true);
//                    handler.sendEmptyMessage(result);
                    OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
                }
                if (nowPage > 1){ nowPage = nowPage-1;}
            }
        }catch (Exception e){
            e.printStackTrace();
            if (nowPage > 1){ nowPage = nowPage-1;}
            Mydialog.Dismiss();
        }
    }

    /**
     * 错误信息处理
     */
    android.os.Handler handler = new android.os.Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case result_err:
                    toast(R.string.datalogcheck_check_no_server);
                    break;
                case result_normal:
                    break;
                case result_serverErr:
                    toast(R.string.datalogcheck_check_no_server);
                    break;
                case result_searchTypeNull:
                    break;
                case result_serverUrlNull:
                    break;
                case result_msgNull:
                    toast(strNull);
                    break;
            }
        }
    };
}
