package com.growatt.shinephone.ossactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.adapter.OssDeviceAdapter;
import com.growatt.shinephone.adapter.OssServerListAdapter;
import com.growatt.shinephone.bean.OssDeviceDatalogBean;
import com.growatt.shinephone.bean.OssDeviceInvBean;
import com.growatt.shinephone.bean.OssDeviceStorageBean;
import com.growatt.shinephone.bean.OssPlantListBean;
import com.growatt.shinephone.bean.OssSearchAllPlantListBean;
import com.growatt.shinephone.bean.OssSearchAllUserListBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.view.CustomBasePopupWindow;
import com.growatt.shinephone.view.EmptyRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.growatt.shinephone.R.string.DataloggersAct_more_data;

@ContentView(R.layout.activity_oss_device)
public class OssDeviceActivity extends DemoBase {
    String strNull = "暂无数据";
    //result code:服务器返回码
    private final int result_err = 0;//返回异常
    private final int result_normal = 1;//返回正常
    private final int result_serverErr = 2;//远程获取数据错误
    private final int result_searchTypeNull = 3;//搜索类型为空
    private final int result_serverUrlNull = 4;//搜索服务器地址为空
    private final int result_msgNull = 21;//搜索不到信息
    @ViewInject(R.id.tvTitle)
    TextView tvTitle;
    @ViewInject(R.id.flDelete)
    FrameLayout flDelete;
    @ViewInject(R.id.etSearch)
    EditText etSearch;
    @ViewInject(R.id.radioGroup1)
    RadioGroup radioGroup1;
    @ViewInject(R.id.radioGroup2)
    RadioGroup radioGroup2;
    @ViewInject(R.id.radioGroup3)
    RadioGroup radioGroup3;
    @ViewInject(R.id.radioGroup4)
    RadioGroup radioGroup4;
    @ViewInject(R.id.radioGroup5)
    RadioGroup radioGroup5;
    @ViewInject(R.id.radioGroup1_rb1)
    RadioButton radioGroup1_rb1;
    @ViewInject(R.id.recycleView)
    EmptyRecyclerView recyclerView;
    @ViewInject(R.id.emptyView)
    View emptyView;
    @ViewInject(R.id.llServer)
    LinearLayout llServer;//选择服务器
    @ViewInject(R.id.tvServer)
    TextView tvServerName;
    @ViewInject(R.id.tvNowServerUrl)
    TextView tvNowServerUrl;//当前搜索的服务器地址
    private boolean changeedGroup = false;  //radioGroup12改变
    private boolean changeedGroup35 = false;  //radioGroup345改变
    private Context context;
    private List<OssPlantListBean> datas;
    private OssDeviceAdapter adapter;
    private List<Map<String,String>> serverList = new ArrayList<>();//服务器列表数据
    private RecyclerView serverRecycler;//服务器列表控件
    private CommonAdapter serverAdapter;//服务器列表适配器
    private CustomBasePopupWindow mPopupWindow;
    //用户信息搜索类型常量
    private final int user_searchType_username = 0;//根据用户名
    private final int user_searchType_phone = 1;//根据手机号
    private final int user_searchType_email = 2;//根据邮箱
    private final int user_searchType_plant = 3;//根据电站
    private  int user_searchType = user_searchType_plant;
    //搜索的是用户信息还是设备信息
    private int searchType = 2;//1:用户信息；2：设备信息
    //用户信息页数
    private int currentPage;//当前页数
    private int totalPage;//总页数
    //设备信息搜索类型
    private final int device_searchType_inv = 1;//根据逆变器
    private final int device_searchType_datalog = 0;//根据采集器
    private final int device_searchType_storage = 2;//根据储能机
    private int device_searchType = device_searchType_inv;
    //设备搜索是按序列号还是别名
    private final int device_searchType_nameSn = 1;//代表序列号
    private final int device_searchType_nameAlias = 2;//代表别名
    private int device_searchType_name = device_searchType_nameSn;//1:代表序列号；2：代表别名
    //分页
    private  int lastVisibleItem;
    private  LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initHeaderView();
        initView();
        initRecycleView();
        initListener();
    }

    private void initView() {
//        tvNowServerUrl.setText(OssUrls.ossCRUDUrl);
    }


    public void clearDatas(){
        datas.clear();
        adapter.notifyDataSetChanged();
    }
    private void initRecycleView() {
        datas = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new OssDeviceAdapter(context,R.layout.item_oss_plantlist,datas);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setAdapter(adapter);
    }

    /**删除操作：删除edittext内容，对list数据做清空操作*/
    @Event(type = View.OnClickListener.class,value = R.id.flDelete)
    private void flDelete(View v){
        etSearch.setText("");
        clearDatas();
    }
    /**具体搜索操作*/
    @Event(type = View.OnClickListener.class,value = R.id.flSearch)
    private void flSearch(View v){
        try{
            String searchStr = etSearch.getText().toString().trim();
            String currentUrl = tvNowServerUrl.getText().toString();
            if (TextUtils.isEmpty(currentUrl)){
                MyControl.circlerDialog(OssDeviceActivity.this,getString(R.string.m80获取服务器地址不成功),-1,false);
                return;
            }
            if (TextUtils.isEmpty(searchStr)){
                toast(R.string.all_blank);
                return;
            }
            currentPage = 1;
            totalPage = 1;
            if (searchType == 1) {//用户信息
                searchUserInfor(searchStr, 1);
            } else if (searchType == 2) {//设备信息
                searchDeviceInfor(searchStr, 1);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 搜索设备信息:直接跳转到OSSDeviceList
     * @param searchStr：用户搜索的内容
     */
    private void searchDeviceInfor(final String searchStr, final int page) {
        String deviceSn = "";
        String alias = "";
        if (device_searchType_name == device_searchType_nameSn){
            deviceSn = searchStr;
        }else {
            alias = searchStr;
        }
        Intent intent = new Intent(this,OssDeviceListActivity.class);
        intent.putExtra(Constant.currentPage,currentPage);
        intent.putExtra(Constant.totalPage,totalPage);
        intent.putExtra("deviceSn",deviceSn);
        intent.putExtra("alias",alias);
        intent.putExtra("deviceType",device_searchType);
        intent.putExtra(Constant.ossJumpToDeviceList,Constant.ossSnToDeviceList);
        startActivity(intent);
    }

    /**
     * 搜索用户信息
     * @param searchStr：用户搜索的内容
     * @param page：搜索的页数
     */
    private void searchUserInfor(final String searchStr,final int page) {
        //具体搜索操作
        Mydialog.Show(this,"");
        PostUtil.post(OssUrls.postOssSearchAll(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("searchType",user_searchType + "");
                params.put("param",searchStr);
                params.put("page",page + "");
                params.put("serverAddr",OssUrls.ossCRUDUrl);
            }
            @Override
            public void success(String json) {
                Mydialog.Dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == result_normal){
                        JSONObject obj = jsonObject.getJSONObject("obj");
                        int searchTypeResult = obj.getInt("searchType");
                        currentPage = obj.getInt("currentPage");
                        totalPage = obj.getInt("totalPage");
                        List<OssPlantListBean> newList = new ArrayList<>();
                        if ( searchTypeResult == user_searchType_username || searchTypeResult == user_searchType_phone || searchTypeResult == user_searchType_email){
                            JSONArray jsonArr = obj.getJSONArray("userList");
                            for (int i = 0 ,size = jsonArr.length(); i < size ; i ++){
                                OssPlantListBean userBean = new OssPlantListBean();
                                OssSearchAllUserListBean bean = new Gson().fromJson(jsonArr.getJSONObject(i).toString(),OssSearchAllUserListBean.class);
                                userBean.setSearchType(searchTypeResult);
                                switch (searchTypeResult){
                                    case user_searchType_username:
                                        userBean.setName1(getString(R.string.register_xml_username));
                                        userBean.setValue1(bean.getAccountName());
                                        userBean.setName2(getString(R.string.m4));
                                        userBean.setValue2(bean.getCreateDate());
                                        userBean.setId(bean.getId());
                                        break;
                                    case user_searchType_phone:
                                        userBean.setName2(getString(R.string.register_xml_username));
                                        userBean.setValue2(bean.getAccountName());
                                        userBean.setName1(getString(R.string.register_xml_phone));
                                        userBean.setValue1(bean.getPhoneNum());
                                        userBean.setId(bean.getId());
                                        break;
                                    case user_searchType_email:
                                        userBean.setName2(getString(R.string.register_xml_username));
                                        userBean.setValue2(bean.getAccountName());
                                        userBean.setName1(getString(R.string.register_xml_email));
                                        userBean.setValue1(bean.getEmail());
                                        userBean.setId(bean.getId());
                                        break;
                                }
                                newList.add(userBean);
                            }
                        }else if (searchTypeResult == user_searchType_plant){
                            JSONArray jsonArr = obj.getJSONArray("plantList");
                            for (int i = 0,size = jsonArr.length();i<size;i++){
                                OssPlantListBean userBean = new OssPlantListBean();
                                OssSearchAllPlantListBean bean = new Gson().fromJson(jsonArr.getJSONObject(i).toString(),OssSearchAllPlantListBean.class);
                                userBean.setSearchType(searchTypeResult);
                                userBean.setName2(getString(R.string.register_xml_username));
                                userBean.setValue2(bean.getUserAccount());
                                userBean.setName1(getString(R.string.powerstation_plantname));
                                userBean.setValue1(bean.getPlantName());
                                userBean.setId(bean.getId());
                                newList.add(userBean);
                            }
                        }
                        if (page == 1) {
                            if (newList.size() == 0) {toast(strNull);}
                            adapter.addAll(newList, true);
                        } else {
                            if (newList.size() == 0) {toast(DataloggersAct_more_data);}
                            adapter.addAll(newList, false);
                        }
                        //item数量唯一自动跳转
                        if (adapter.getItemCount() == 1){jumpOther(0);}
                    }else {
//                        handler.sendEmptyMessage(result);
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
                        currentPage = currentPage -1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    currentPage = currentPage -1;
                    Mydialog.Dismiss();
                }
            }

            @Override
            public void LoginError(String str) {
                currentPage = currentPage -1;
                Mydialog.Dismiss();
            }
        });
    }

    /**
     * 由主界面跳转到其他界面
     * @param position
     */
    public  void jumpOther(int position){
        OssPlantListBean bean = datas.get(position);
        int searchType = bean.getSearchType();
        switch (searchType){
            case user_searchType_username:
                //获取用户下电站信息
                searchUserDownPlantInfor(bean.getValue1(),1);
                break;
            case user_searchType_phone:
            case user_searchType_email:
                //获取用户下电站信息
                searchUserDownPlantInfor(bean.getValue2(),1);
                break;
            case user_searchType_plant:
                //直接跳转到设备列表或搜索后再跳转
                Intent intent = new Intent(mContext,OssDeviceListActivity.class);
                intent.putExtra("currentPage",1);
                intent.putExtra("totalPage",1);
                intent.putExtra("deviceType",device_searchType_inv);
                intent.putExtra("plantId",bean.getId());
                intent.putExtra(Constant.ossJumpToDeviceList,Constant.ossPlantToDeviceList);
                intent.putParcelableArrayListExtra("list",null);
                startActivity(intent);
//                        searchPlantDownDevice(bean.getId(),device_searchType_inv,1);
                break;
        }
    }

    private void initListener() {
        startRadioGroupListener(radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5);
        autoRadioGroupTextSize(radioGroup1,radioGroup2,radioGroup3,radioGroup4,radioGroup5);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                clearDatas();
                if (s.toString().length() > 0 ){
                    if (flDelete != null && flDelete.getVisibility() == View.INVISIBLE){
                        flDelete.setVisibility(View.VISIBLE);
                    }
                }else {
                    if (flDelete != null && flDelete.getVisibility() == View.VISIBLE){
                        flDelete.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                jumpOther(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return true;
            }
        });
        llServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (mPopupWindow == null) {
                        mPopupWindow = new CustomBasePopupWindow(mContext, R.layout.item_oss_serverlist, v.getWidth(), true) {
                            @Override
                            public void init() {
                                serverRecycler = (RecyclerView) mPopView.findViewById(R.id.recycleView);
                                serverRecycler.setLayoutManager(new LinearLayoutManager(mContext));
                                serverAdapter = new OssServerListAdapter(mContext, R.layout.item_oss_serveritem, serverList);
                                serverRecycler.setAdapter(serverAdapter);
                                //服务器列表item点击事件
                                serverAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                        mPopupWindow.dismiss();
                                        StringBuilder sb = new StringBuilder();
                                        String url = serverList.get(position).get(Constant.OssServer_url);
                                        if (getLanguage() == 0){//中文
                                            sb.append(serverList.get(position).get(Constant.OssServer_cn));
                                        }else {
                                            sb.append(serverList.get(position).get(Constant.OssServer_en));
                                        }
                                        sb.append(":").append(url);
                                        tvServerName.setText(sb.toString());
                                        OssUrls.ossCRUDUrl = url;
                                        OssUrls.ossCRUDUrlHostName =  url;
                                        tvNowServerUrl.setText(OssUrls.ossCRUDUrl);
                                    }

                                    @Override
                                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                        return false;
                                    }
                                });
                            }
                        };
                    }
                    serverAdapter.addAll(Cons.ossServerList,true);
                    mPopupWindow.showAsDowm(v);
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
                        currentPage = currentPage + 1;
                        String searchStr = etSearch.getText().toString().trim();
                        if (TextUtils.isEmpty(searchStr)){
                            toast(R.string.all_blank);
                            return;
                        }
                        if (searchType == 1) {//用户信息
                            searchUserInfor(searchStr, currentPage);
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
                Mydialog.Dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        JSONObject obj = jsonObject.getJSONObject("obj");
                        int deviceType = obj.getInt("deviceType");
                        int currentPage = obj.getInt("currentPage");
                        int totalPage = obj.getInt("totalPage");
                        switch (deviceType){
                            case device_searchType_datalog:
                                ArrayList<OssDeviceDatalogBean> datalogBeans = new ArrayList<>();
                                JSONArray jsonArrDatalog = obj.getJSONArray("datalogList");
                                for (int i = 0,length = jsonArrDatalog.length();i<length;i++){
                                    OssDeviceDatalogBean bean = new Gson().fromJson(jsonArrDatalog.getJSONObject(i).toString(),OssDeviceDatalogBean.class);
                                    datalogBeans.add(bean);
                                }
                                if (datalogBeans.size()>0){
                                    Intent intent = new Intent(mContext,OssDeviceListActivity.class);
                                    intent.putExtra("currentPage",currentPage);
                                    intent.putExtra("totalPage",totalPage);
                                    intent.putExtra("deviceType",deviceType);
                                    intent.putExtra("plantId",plantId);
                                    intent.putExtra(Constant.ossJumpToDeviceList,Constant.ossPlantToDeviceList);
                                    intent.putParcelableArrayListExtra("list",datalogBeans);
                                    startActivity(intent);
                                }else {
                                    toast(strNull);
                                }
                                break;
                            case device_searchType_inv:
                                ArrayList<OssDeviceInvBean> invBeans = new ArrayList<>();
                                JSONArray jsonArrInv = obj.getJSONArray("invList");
                                for (int i = 0,length = jsonArrInv.length();i<length;i++){
                                    OssDeviceInvBean bean = new Gson().fromJson(jsonArrInv.getJSONObject(i).toString(),OssDeviceInvBean.class);
                                    invBeans.add(bean);
                                }
                                if (invBeans.size()>0){
                                    Intent intent = new Intent(mContext,OssDeviceListActivity.class);
                                    intent.putExtra("currentPage",currentPage);
                                    intent.putExtra("totalPage",totalPage);
                                    intent.putExtra("deviceType",deviceType);
                                    intent.putExtra("plantId",plantId);
                                    intent.putExtra(Constant.ossJumpToDeviceList,Constant.ossPlantToDeviceList);
                                    intent.putParcelableArrayListExtra("list",invBeans);
                                    startActivity(intent);
                                }else {
                                    toast(strNull);
                                }
                                break;
                            case device_searchType_storage:
                                ArrayList<OssDeviceStorageBean> storageBeans = new ArrayList<>();
                                JSONArray jsonArrStorage = obj.getJSONArray("storageList");
                                for (int i = 0,length = jsonArrStorage.length();i<length;i++){
                                    OssDeviceStorageBean bean = new Gson().fromJson(jsonArrStorage.getJSONObject(i).toString(),OssDeviceStorageBean.class);
                                    storageBeans.add(bean);
                                }
                                if (storageBeans.size()>0){
                                    Intent intent = new Intent(mContext,OssDeviceListActivity.class);
                                    intent.putExtra("currentPage",currentPage);
                                    intent.putExtra("totalPage",totalPage);
                                    intent.putExtra("deviceType",deviceType);
                                    intent.putExtra("plantId",plantId);
                                    intent.putExtra(Constant.ossJumpToDeviceList,Constant.ossPlantToDeviceList);
                                    intent.putParcelableArrayListExtra("list",storageBeans);
                                    startActivity(intent);
                                }else {
                                    toast(strNull);
                                }
                                break;
                        }
                    }else {
//                        handler.sendEmptyMessage(result);
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }
            @Override
            public void LoginError(String str) {
                Mydialog.Dismiss();
            }
        });
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
                    if (result == result_normal){
                        JSONObject obj = jsonObject.getJSONObject("obj");
                        int currentPage = obj.getInt("currentPage");
                        int totalPage = obj.getInt("totalPage");
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
                        if (newList.size()>0){
                            //跳转至电站列表界面
                            Intent intent = new Intent(mContext,OssPlantListActivity.class);
                            intent.putParcelableArrayListExtra("list",newList);
                            intent.putExtra("currentPage",currentPage);
                            intent.putExtra("totalPage",totalPage);
                            intent.putExtra("userName",userName);
                            startActivity(intent);
                        }else {
                            toast(strNull);
                        }
                    }else {
//                        handler.sendEmptyMessage(result);
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                }
            }

            @Override
            public void LoginError(String str) {
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
        setHeaderTitle(headerView,"设备搜索");
    }

    /**
     * radioGroup初始化监听器
     */
    RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (!changeedGroup) {
                changeedGroup = true;
                if (group == radioGroup1) {
                    //清空radioGroup2 3 4 5并隐藏345
                    clearRadioGroupCheck(radioGroup2,radioGroup3,radioGroup4,radioGroup5);
                    hideRadioGroupCheck(radioGroup3,radioGroup4,radioGroup5);
                    showToTitle(group,checkedId);
                    //是用户信息判断搜索类型
                    searchType = 1;
                    switch (checkedId){
                        case R.id.radioGroup1_rb2:
                            user_searchType = user_searchType_username;
                            break;
                        case R.id.radioGroup1_rb3:
                            user_searchType = user_searchType_phone;
                            break;
                        case R.id.radioGroup1_rb4:
                            user_searchType = user_searchType_email;
                            break;
                        case R.id.radioGroup1_rb1:
                            user_searchType = user_searchType_plant;
                            break;
                    }
                } else if (group == radioGroup2) {
                    clearRadioGroupCheck(radioGroup1);
                    //判断是逆变器、储能机或采集器
                    checkDevice(group,(RadioButton)group.findViewById(checkedId));
                    showToTitle(group,checkedId);
                    //设备信息，判断搜索设备
                    searchType = 2;
                    switch (checkedId){
                        case R.id.radioGroup2_rb1:
                            device_searchType = device_searchType_inv;
                            break;
                        case R.id.radioGroup2_rb2:
                            device_searchType = device_searchType_storage;
                            break;
                        case R.id.radioGroup2_rb3:
                            device_searchType = device_searchType_datalog;
                            break;
                    }
                }
                changeedGroup = false;
            }
            if (!changeedGroup35) {
                changeedGroup35 = true;
                if (group == radioGroup3) {
                    clearRadioGroupCheck(radioGroup5,radioGroup4);
                    hideRadioGroupCheck(radioGroup5,radioGroup4);
                    //判断是序列号还是别名
                    switch (checkedId){
                        case R.id.radioGroup3_rb1:
                            device_searchType_name = device_searchType_nameSn;
                            break;
                        case R.id.radioGroup3_rb2:
                            device_searchType_name = device_searchType_nameAlias;
                            break;
                    }
                } else if (group == radioGroup4) {
                    clearRadioGroupCheck(radioGroup3,radioGroup5);
                    hideRadioGroupCheck(radioGroup3,radioGroup5);
                    //判断是序列号还是别名
                    switch (checkedId){
                        case R.id.radioGroup4_rb1:
                            device_searchType_name = device_searchType_nameSn;
                            break;
                        case R.id.radioGroup4_rb2:
                            device_searchType_name = device_searchType_nameAlias;
                            break;
                    }
                } else if (group == radioGroup5){
                    clearRadioGroupCheck(radioGroup3,radioGroup4);
                    hideRadioGroupCheck(radioGroup3,radioGroup4);
                    //判断是序列号还是别名
                    switch (checkedId){
                        case R.id.radioGroup5_rb1:
                            device_searchType_name = device_searchType_nameSn;
                            break;
                        case R.id.radioGroup5_rb2:
                            device_searchType_name = device_searchType_nameAlias;
                            break;
                    }
                }
                changeedGroup35 = false;
            }
        }
    };

    /**
     * 根据选中的radiobutton,显示不同的标题
     * @param group
     * @param checkedId
     */
    private void showToTitle(RadioGroup group, int checkedId) {
        String title = ((RadioButton)group.findViewById(checkedId)).getText().toString();
        tvTitle.setText("当前属性:"+title);
    }

    /**
     * 根据具体设备显示不同的RadioGroup
     * @param group
     * @param rb
     */
    private void checkDevice(RadioGroup group,RadioButton rb) {
        switch (group.indexOfChild(rb)){
            case 0:
                showRadioGroupCheck(radioGroup3);
                radioGroup3.check(R.id.radioGroup3_rb1);
                break;
            case 1:
                showRadioGroupCheck(radioGroup4);
                radioGroup4.check(R.id.radioGroup4_rb1);
                break;
            case 2:
                showRadioGroupCheck(radioGroup5);
                radioGroup5.check(R.id.radioGroup5_rb1);
                break;
        }
    }
    /**
     * 清除radiogroup选中
     */
    public void clearRadioGroupCheck(RadioGroup... radioGroups){
        for (RadioGroup rg : radioGroups){
            rg.clearCheck();
        }
    }
    /**
     * 隐藏radiogroup
     */
    public void hideRadioGroupCheck(RadioGroup... radioGroups){
        for (RadioGroup rg : radioGroups){
            if (rg.getVisibility() == View.VISIBLE){
                rg.setVisibility(View.INVISIBLE);
            }
        }
    }
    /**
     * 显示radiogroup
     */
    public void showRadioGroupCheck(RadioGroup... radioGroups){
        for (RadioGroup rg : radioGroups){
            if (rg.getVisibility() == View.INVISIBLE){
                rg.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * radiogroup设置监听
     */
    public void startRadioGroupListener(RadioGroup... radioGroups){
        for (RadioGroup rg : radioGroups){
            rg.setOnCheckedChangeListener(listener);
        }
    }
    /**
     * radiogroup下radioButton字体自适应
     */
    public void autoRadioGroupTextSize(RadioGroup... radioGroups){
        //暂时不处理
//        for (RadioGroup rg : radioGroups){
//            for (int i = 0,size = rg.getChildCount();i< size;i++){
//                RadioButton rb = (RadioButton) rg.getChildAt(i);
//                MyUtils.autoTextSize(rb);
//            }
//        }
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
