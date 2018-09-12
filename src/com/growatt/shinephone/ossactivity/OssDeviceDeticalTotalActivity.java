package com.growatt.shinephone.ossactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.adapter.OssSetTotalAdapter;
import com.growatt.shinephone.bean.OssDeviceDatalogBean;
import com.growatt.shinephone.bean.OssDeviceDeticalBean;
import com.growatt.shinephone.bean.OssDeviceInvBean;
import com.growatt.shinephone.bean.OssDeviceStorageBean;
import com.growatt.shinephone.bean.OssJkDeviceInvDeticalBean;
import com.growatt.shinephone.bean.OssJkDeviceStorageDeticalBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.GetWifiInfo;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.mylhyl.circledialog.CircleDialog;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import mediatek.android.IoTManager.SmartConnection;

@ContentView(R.layout.activity_oss_device_detical_total)
public class OssDeviceDeticalTotalActivity extends DemoBase {
    @ViewInject(R.id.recycleView2)
    RecyclerView mRecyclerView2;
    private OssSetTotalAdapter mAdapter2;
    private List<OssDeviceDeticalBean> mList2 = new ArrayList<>();
    @ViewInject(R.id.recycleView)
    RecyclerView mRecyclerView;
    private OssSetTotalAdapter mAdapter;
    private OssDeviceInvBean invBean;
    private OssDeviceStorageBean storageBean;
    private OssDeviceDatalogBean datalogBean;
    private OssJkDeviceInvDeticalBean jkInvBean;//集成商逆变器实体
    private OssJkDeviceStorageDeticalBean jkStorageBean;//集成商储能机实体
    private int deviceType;//0:代表oss采集器；1：oss逆变器；2：oss储能机；3：oss集成逆变器；4：oss集成商储能机
    //采集器类型对应编码
    private int deviceTypeIndicate;

    private List<OssDeviceDeticalBean> mList = new ArrayList<>();
    private int[] imgValue = {R.drawable.oss_device_set_selector,R.drawable.oss_device_edit_selector,R.drawable.oss_device_delete_selector,R.drawable.oss_device_config_selector};
    private String[] imgName;
    private int imgSize = 3;
    private String deviceSn = "";//设备序列号
    private int textSize = 10;//文本数量为10
    private int aliseIndex = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeaderView();
        initRecyclerView();
        initIntent();
        initListener();
    }

    private void initListener() {
        mAdapter2.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                if (position < imgSize) return;
                switch (position){
                    case 0://跳转到设置界面
                        switch (deviceType){
                            case 0:
                                clickDatalogItemSet();
                                break;
                            case 1:
                            case 3:
                                clickInvItemSet();
                                break;
                            case 2:
                            case 4:
                                clickStorageItemSet();
                                break;
                        }
                        break;
                    case 1://跳转到编辑界面
                        Intent intent = new Intent(mContext,OssEditActivity.class);
                        intent.putExtra("sn",deviceSn);
                        intent.putExtra("alias",mList.get(aliseIndex).getValue1());
                        intent.putExtra("deviceType",deviceType);
                        startActivity(intent);
                        break;
                    case 2://删除操作
                        new CircleDialog.Builder(OssDeviceDeticalTotalActivity.this)
                                .setWidth(0.7f)
                                .setTitle(getString(R.string.警告))
                                .setText(getString(R.string.dataloggers_declte_prompt))
                                .setPositive(getString(R.string.all_ok), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        switch (deviceType){
                                            case 3://集成商逆变器
                                                deviceType = 1;
                                                break;
                                            case 4://集成商储能机
                                                deviceType = 2;
                                                break;
                                        }
                                        MyControl.deviceDelete(mContext, deviceSn, deviceType, new OnHandlerListener() {
                                            @Override
                                            public void handlerDeal(int result,String msg) {
                                                OssUtils.showOssDialog(OssDeviceDeticalTotalActivity.this,msg,result,true,null);
//                                                handlerOssDel.sendEmptyMessage(result);
                                            }
                                        });
                                    }
                                })
                                .setNegative(getString(R.string.all_no),null)
                                .show();
                        break;
                    case 3://配置界面
                        MyControl.configWifiOss(OssDeviceDeticalTotalActivity.this,deviceTypeIndicate,deviceSn);
                        break;
                }
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private void jumpNewWifiConfig() {
            Map<String, Object> map = new GetWifiInfo(this).Info();
            if(map.get("mAuthString").toString().equals("")){
                AlertDialog builder = new AlertDialog.Builder(this).setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_connectwifi).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                }).create();
                builder.show();
                return;
            }
            Intent intent=new Intent(this,SmartConnection.class);
            Bundle bundle=new Bundle();
            bundle.putString("type","101");
            bundle.putString("id",deviceSn);
            bundle.putString("ssid",map.get("ssid").toString());
            bundle.putString("mAuthString",map.get("mAuthString").toString());
            bundle.putByte("mAuthMode",(Byte) map.get("mAuthMode"));
            intent.putExtras(bundle);
            startActivity(intent);
    }

    /**
     * 跳转到设备设置界面：储能机、逆变器、采集器
     */
    private void clickStorageItemSet() {
        //储能机型号
        if (storageBean != null && storageBean.getDeviceType() == 2){//spf5k
            Intent intent = new Intent(mContext,OssStorageSpf5kSetActivity.class);
            intent.putExtra("serialNum",deviceSn);
            startActivity(intent);
        }else {
            Intent intent = new Intent(mContext,OssStorageSetActivity.class);
            intent.putExtra("sn",deviceSn);
            startActivity(intent);
        }
    }
    private void clickInvItemSet() {
        Intent intent = new Intent(mContext,OssInvSetActivity.class);
        intent.putExtra("sn",deviceSn);
        startActivity(intent);
    }
    private void clickDatalogItemSet() {
        Intent intent = new Intent(mContext,OssDatalogSetActivity.class);
        intent.putExtra("datalogSn",deviceSn);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //刷新界面
        switch (deviceType){
            case 0:
            case 1:
            case 2://oss设备详情
                searchSnDownDeviceInfor(deviceSn,"",OssUrls.ossCRUDUrl,deviceType,1);
                break;
            case 3:
                getOssJkDeviceDetical(deviceSn,1);
                break;
            case 4://oss集成商设备详情
                getOssJkDeviceDetical(deviceSn,2);
                break;
        }
    }

    /**
     * 获取集成商设备详情
     * @param deviceSn
     * @param deviceType
     */
    private void getOssJkDeviceDetical(final String deviceSn, final int deviceType) {
        //获取集成商设备详情数据
        Mydialog.Show(this,"");
        PostUtil.post(OssUrls.postOssJKDvice_info(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("deviceSn",deviceSn);
                params.put("deviceType",deviceType + "");
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        JSONObject obj = jsonObject.getJSONObject("obj");
                        switch (deviceType){
                            case 1:
                                JSONArray arr = obj.getJSONArray("invList");
                                if (arr != null && arr.length()>0){
                                    jkInvBean = new Gson().fromJson(arr.getJSONObject(0).toString(),OssJkDeviceInvDeticalBean.class);
                                    addJKInvDatas(jkInvBean);
                                }
                                break;
                            case 2:
                                JSONArray arr2 = obj.getJSONArray("storageList");
                                if (arr2 != null && arr2.length()>0){
                                    jkStorageBean = new Gson().fromJson(arr2.getJSONObject(0).toString(),OssJkDeviceStorageDeticalBean.class);
                                    addJKStorageDatas(jkStorageBean);
                                }
                                break;
                        }

                    }else {
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
//                        MyControl.circlerDialog(OssDeviceDeticalTotalActivity.this,getString(R.string.datalogcheck_check_no_server),result,false);
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
                parseDeviceInfor(json);
            }
            @Override
            public void LoginError(String str) {

            }
        });
    }

    private void parseDeviceInfor(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int result = jsonObject.getInt("result");
            if (result == 1){
                JSONObject obj = jsonObject.getJSONObject("obj");
                deviceType = obj.getInt("deviceType");
                switch (deviceType){
                    case 0:
                        JSONArray jsonArrDatalog = obj.getJSONArray("datalogList");
                        if (jsonArrDatalog != null && jsonArrDatalog.length()>0){
                            OssDeviceDatalogBean datalogBean = new Gson().fromJson(jsonArrDatalog.getJSONObject(0).toString(), OssDeviceDatalogBean.class);
                            addDatalogDatas(datalogBean);
                        }
                        break;
                    case 1:
                        JSONArray jsonArrInv = obj.getJSONArray("invList");
                        if (jsonArrInv != null && jsonArrInv.length()>0){
                            OssDeviceInvBean datalogBean = new Gson().fromJson(jsonArrInv.getJSONObject(0).toString(), OssDeviceInvBean.class);
                            addInvDatas(datalogBean);
                        }
                        break;
                    case 2:
                        JSONArray jsonArrStorage = obj.getJSONArray("storageList");
                        if (jsonArrStorage != null && jsonArrStorage.length()>0){
                            OssDeviceStorageBean datalogBean = new Gson().fromJson(jsonArrStorage.getJSONObject(0).toString(), OssDeviceStorageBean.class);
                            addStorageDatas(datalogBean);
                        }
                        break;
                }
            }else {
                OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Mydialog.Dismiss();
        }
    }

    private void initIntent() {
        Intent intent = getIntent();
        deviceType = intent.getIntExtra("deviceType",0);
        imgName = new String[]{"设置" , "编辑" , "删除","配置工具"};
        switch (deviceType){
            case 0:
                datalogBean = intent.getParcelableExtra("bean");
                deviceSn = datalogBean.getSerialNum();
                addDatalogDatas(datalogBean);
                break;
            case 1:
                invBean = intent.getParcelableExtra("bean");
                deviceSn = invBean.getSerialNum();
                addInvDatas(invBean);
                break;
            case 2:
                storageBean = intent.getParcelableExtra("bean");
                deviceSn = storageBean.getSerialNum();
                addStorageDatas(storageBean);
                break;
            case 3:
                jkInvBean = intent.getParcelableExtra("bean");
                deviceSn = jkInvBean.getSerialNum();
                addJKInvDatas(jkInvBean);
                break;
            case 4:
                jkStorageBean = intent.getParcelableExtra("bean");
                deviceSn = jkStorageBean.getSerialNum();
                addJKStorageDatas(jkStorageBean);
                break;
        }
    }

    private void addJKStorageDatas(OssJkDeviceStorageDeticalBean jkStorageBean) {
        List<String>  storageNameList = Arrays.asList(getString(R.string.dataloggers_list_serial), getString(R.string.inverterdevicedata_alias), "所属采集器", "连接状态",
                getString(R.string.storagedps_list1_item1), getString(R.string.storagedps_list1_item2), "状态", getString(R.string.inverterdevicedata_property), "储能机型号",
                getString(R.string.xlistview_header_last_time));
        String statusText = mContext.getString(R.string.all_Lost);
        switch (jkStorageBean.getStatus()){
            case 0://闲置
                statusText = mContext.getString(R.string.all_Standby);
                break;
            case 1://充电
                statusText = mContext.getString(R.string.all_Charge);
                break;
            case 2://放电
                statusText = mContext.getString(R.string.all_Discharge);
                break;
            case 3://故障
                statusText = mContext.getString(R.string.all_Fault);
                break;
            case 4://flash等待
                statusText = mContext.getString(R.string.all_Flash);
                break;
            default://断开
                statusText = mContext.getString(R.string.all_Flash);
                break;
        }
        List<String> storageValueList = Arrays.asList( jkStorageBean.getSerialNum(),jkStorageBean.getAlias(),jkStorageBean.getDataLogSn(),jkStorageBean.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online),
                jkStorageBean.getpCharge()+"",jkStorageBean.getpDischarge()+"",statusText,jkStorageBean.getFwVersion(),jkStorageBean.getModelText(),jkStorageBean.getLastUpdateTimeText());
        List<OssDeviceDeticalBean> newList = new ArrayList<>();
        for (int i = 0,size = storageNameList.size(); i<size ; i++ ){
            OssDeviceDeticalBean bean = new OssDeviceDeticalBean(true);
            bean.setName1(storageNameList.get(i));
            bean.setValue1(storageValueList.get(i));
            newList.add(bean);
        }
        List<OssDeviceDeticalBean> newList2 = new ArrayList<>();
        for (int i=0; i<imgSize ; i++){
            OssDeviceDeticalBean bean = new OssDeviceDeticalBean(false);
            bean.setImgResId(imgValue[i]);
            bean.setValue2(imgName[i]);
            newList2.add(bean);
        }
        mAdapter.addAll(newList,true);
        mAdapter2.addAll(newList2,true);
    }

    private void addJKInvDatas(OssJkDeviceInvDeticalBean jkInvBean) {
        List<String> invNameList = Arrays.asList(getString(R.string.dataloggers_list_serial),getString(R.string.inverterdevicedata_alias),"所属采集器","连接状态",
                getString(R.string.inverterdevicedata_power),getString(R.string.当前功率),getString(R.string.今日发电),getString(R.string.累计发电量),"逆变器型号",
                getString(R.string.xlistview_header_last_time));
        List<String> invValueList = Arrays.asList( jkInvBean.getSerialNum(),jkInvBean.getAlias(),jkInvBean.getDataLogSn(),jkInvBean.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online),
                jkInvBean.getNominalPower() + "",jkInvBean.getPower()+"",jkInvBean.geteToday()+"",jkInvBean.geteTotal()+"",jkInvBean.getModelText(),jkInvBean.getLastUpdateTimeText());
        List<OssDeviceDeticalBean> newList = new ArrayList<>();
        for (int i = 0,size = invNameList.size(); i<size ; i++ ){
            OssDeviceDeticalBean bean = new OssDeviceDeticalBean(true);
            bean.setName1(invNameList.get(i));
            bean.setValue1(invValueList.get(i));
            newList.add(bean);
        }
        List<OssDeviceDeticalBean> newList2 = new ArrayList<>();
        for (int i=0; i<imgSize ; i++){
            OssDeviceDeticalBean bean = new OssDeviceDeticalBean(false);
            bean.setImgResId(imgValue[i]);
            bean.setValue2(imgName[i]);
            newList2.add(bean);
        }
        mAdapter.addAll(newList,true);
        mAdapter2.addAll(newList2,true);
    }

    private void addStorageDatas(OssDeviceStorageBean storageBean) {
        List<String>  storageNameList = Arrays.asList(getString(R.string.dataloggers_list_serial), getString(R.string.inverterdevicedata_alias), "所属采集器", "连接状态",
                    getString(R.string.storagedps_list1_item1), getString(R.string.storagedps_list1_item2), "状态", getString(R.string.inverterdevicedata_property), "储能机型号",
                    getString(R.string.xlistview_header_last_time));
        String statusText = mContext.getString(R.string.all_Lost);
        switch (storageBean.getStatus()){
            case 0://闲置
                statusText = mContext.getString(R.string.all_Standby);
                break;
            case 1://充电
                statusText = mContext.getString(R.string.all_Charge);
                break;
            case 2://放电
                statusText = mContext.getString(R.string.all_Discharge);
                break;
            case 3://故障
                statusText = mContext.getString(R.string.all_Fault);
                break;
            case 4://flash等待
                statusText = mContext.getString(R.string.all_Flash);
                break;
            default://断开
                statusText = mContext.getString(R.string.all_Flash);
                break;
        }
        List<String> storageValueList = Arrays.asList( storageBean.getSerialNum(),storageBean.getAlias(),storageBean.getDataLogSn(),storageBean.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online),
                storageBean.getpCharge()+"",storageBean.getpDischarge()+"",statusText,storageBean.getFwVersion(),storageBean.getModelText(),storageBean.getLastUpdateTimeText());
        List<OssDeviceDeticalBean> newList = new ArrayList<>();
        for (int i = 0,size = storageNameList.size(); i<size ; i++ ){
            OssDeviceDeticalBean bean = new OssDeviceDeticalBean(true);
            bean.setName1(storageNameList.get(i));
            bean.setValue1(storageValueList.get(i));
            newList.add(bean);
        }
        List<OssDeviceDeticalBean> newList2 = new ArrayList<>();
        for (int i=0; i<imgSize ; i++){
            OssDeviceDeticalBean bean = new OssDeviceDeticalBean(false);
            bean.setImgResId(imgValue[i]);
            bean.setValue2(imgName[i]);
            newList2.add(bean);
        }
        mAdapter.addAll(newList,true);
        mAdapter2.addAll(newList2,true);
    }

    private void addInvDatas(OssDeviceInvBean invBean) {
        List<String> invNameList = Arrays.asList(getString(R.string.dataloggers_list_serial),getString(R.string.inverterdevicedata_alias),"所属采集器","连接状态",
                getString(R.string.inverterdevicedata_power),getString(R.string.当前功率),getString(R.string.今日发电),getString(R.string.累计发电量),"逆变器型号",
                getString(R.string.xlistview_header_last_time));
        List<String> invValueList = Arrays.asList( invBean.getSerialNum(),invBean.getAlias(),invBean.getDataLogSn(),invBean.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online),
                invBean.getNominalPower() + "",invBean.getPower()+"",invBean.geteToday()+"",invBean.geteTotal()+"",invBean.getModelText(),invBean.getLastUpdateTimeText());
        List<OssDeviceDeticalBean> newList = new ArrayList<>();
        for (int i = 0,size = invNameList.size(); i<size ; i++ ){
            OssDeviceDeticalBean bean = new OssDeviceDeticalBean(true);
            bean.setName1(invNameList.get(i));
            bean.setValue1(invValueList.get(i));
            newList.add(bean);
        }
        List<OssDeviceDeticalBean> newList2 = new ArrayList<>();
        for (int i=0; i<imgSize ; i++){
            OssDeviceDeticalBean bean = new OssDeviceDeticalBean(false);
            bean.setImgResId(imgValue[i]);
            bean.setValue2(imgName[i]);
            newList2.add(bean);
        }
        mAdapter.addAll(newList,true);
        mAdapter2.addAll(newList2,true);
    }

    private void addDatalogDatas(OssDeviceDatalogBean datalogBean) {
        deviceTypeIndicate = datalogBean.getDeviceTypeIndicate();
        List<String> storageNameList = Arrays.asList(getString(R.string.dataloggers_list_serial),getString(R.string.inverterdevicedata_alias),getString(R.string.dataloggers_list_type),
                getString(R.string.register_xml_username),"连接状态","IP及端口号",getString(R.string.inverterdevicedata_property),"服务器地址",getString(R.string.xlistview_header_last_time),
                "校验码");
        String aCode = AppUtils.validateWebbox(datalogBean.getSerialNum());
        List<String> storageValueList = Arrays.asList(datalogBean.getSerialNum(),datalogBean.getAlias(),datalogBean.getDeviceType(),datalogBean.getUserName(),datalogBean.isLost() ? getString(R.string.all_Lost) : getString(R.string.all_online),
                datalogBean.getClientUrl(),datalogBean.getParamBean().getFirmwareVersionBuild(),datalogBean.getParamBean().getServerUrl(),datalogBean.getLastUpdateTimeText(),aCode);
        if (deviceTypeIndicate == 2 || deviceTypeIndicate == 6 || deviceTypeIndicate == 9){ imgSize = 4 ;}
        List<OssDeviceDeticalBean> newList = new ArrayList<>();
        for (int i = 0,size = storageNameList.size(); i<size ; i++ ){
            OssDeviceDeticalBean bean = new OssDeviceDeticalBean(true);
            bean.setName1(storageNameList.get(i));
            bean.setValue1(storageValueList.get(i));
            newList.add(bean);
        }
        List<OssDeviceDeticalBean> newList2 = new ArrayList<>();
        for (int i=0; i<imgSize ; i++){
            OssDeviceDeticalBean bean = new OssDeviceDeticalBean(false);
            bean.setImgResId(imgValue[i]);
            bean.setValue2(imgName[i]);
            newList2.add(bean);
        }
        mAdapter.addAll(newList,true);
        mAdapter2.addAll(newList2,true);
    }

    private void initRecyclerView() {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        mAdapter = new OssSetTotalAdapter(mContext,mList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView2.setLayoutManager(new GridLayoutManager(mContext,2));
        mAdapter2 = new OssSetTotalAdapter(mContext,mList2);
        mRecyclerView2.setAdapter(mAdapter2);
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
        setHeaderTitle(headerView,"设备详情");
    }
    //oss删除handler
    private Handler handlerOssDel = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "";
            switch (msg.what){
                case 0:text = "删除失败";break;
                case 1:text = "删除成功";break;
                case 2:text = "远程更新数据错误";break;
                case 3:text = "设备类型为空";break;
                case 4:text = "服务器地址为空";break;

            }
            MyControl.circlerDialog(OssDeviceDeticalTotalActivity.this,text,msg.what);
        }
    };
}
