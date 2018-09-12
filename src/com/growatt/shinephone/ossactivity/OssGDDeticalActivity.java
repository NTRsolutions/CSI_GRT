package com.growatt.shinephone.ossactivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DemoBase;
import com.growatt.shinephone.adapter.OssReplyQuesAdapter;
import com.growatt.shinephone.bean.OssGDQuestionListBean;
import com.growatt.shinephone.util.AddCQ;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.ImagePathUtil;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.OssUrls;
import com.growatt.shinephone.util.OssUtils;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.DialogParams;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单详情
 */
@ContentView(R.layout.activity_oss_gddetical)
public class OssGDDeticalActivity extends DemoBase {
    @ViewInject(R.id.tvTitle) TextView tvTitle;//派单标题
//内容头部
    @ViewInject(R.id.tvWorkTitle) TextView tvWorkTitle;//派单标题
    @ViewInject(R.id.tvServiceType) TextView tvServiceType;//服务类型
    @ViewInject(R.id.tvCustomerName) TextView tvCustomerName;//客户名称
    @ViewInject(R.id.tvContact) TextView tvContact;//客户联系方式
//    @ViewInject(R.id.tvAddress) TextView tvAddress;//客户地址
//    @ViewInject(R.id.tvRemarks) TextView tvRemarks;//所有备注
    private String[] serviceTypeStrs;
// 内容1
    @ViewInject(R.id.inTitle1) View inTitle1;private boolean inTitle1Flag;
    @ViewInject(R.id.inContent1) View inContent1;
    @ViewInject(R.id.ivDown1) ImageView ivDown1;
    @ViewInject(R.id.ivUp1) ImageView ivUp1;
    @ViewInject(R.id.btnReceiver1) Button btnReceiver1;
    @ViewInject(R.id.inTitle2) View inTitle2;private boolean inTitle2Flag;
    @ViewInject(R.id.tvGroupName) TextView tvGroupName;//区域名称
    @ViewInject(R.id.tvApplicationTime) TextView tvApplicationTime;//申请时间
    @ViewInject(R.id.tvDoorTime) TextView tvDoorTime;//要求到达时间
    @ViewInject(R.id.tvAppointment) TextView tvAppointment;//预约上门时间
    @ViewInject(R.id.tvRemarks1) TextView tvRemarks1;//显示的备注
    @ViewInject(R.id.etAddress1) EditText etAddress1;//详细地址
    @ViewInject(R.id.inContent2) View inContent2;
    @ViewInject(R.id.etRemarks1) EditText etRemarks1;//接收备注
    //需要显示灰色的控件
    @ViewInject(R.id.tvNoteContent141) TextView tvNoteContent141;
    @ViewInject(R.id.tvNoteContent142) TextView tvNoteContent142;
    @ViewInject(R.id.ivNoteContent143) ImageView ivNoteContent143;
    @ViewInject(R.id.tvNoteContent151) TextView tvNoteContent151;
    @ViewInject(R.id.tvNoteContent152) TextView tvNoteContent152;
    @ViewInject(R.id.tvNoteContent171) TextView tvNoteContent171;

//内容2
    @ViewInject(R.id.ivDown2) ImageView ivDown2;
    @ViewInject(R.id.ivUp2) ImageView ivUp2;
    @ViewInject(R.id.ivIcon2) ImageView ivIcon2;
    @ViewInject(R.id.lineUp21) View lineUp21;
    @ViewInject(R.id.lineUp22) View lineUp22;
    @ViewInject(R.id.lineContent2) View lineContent2;
    @ViewInject(R.id.btnFinish2) Button btnFinish2;
    @ViewInject(R.id.tvLocation) TextView tvLocation;//位置
    @ViewInject(R.id.tvRemarks2) TextView tvRemarks2;//显示备注
    @ViewInject(R.id.tvCompleteTime) TextView tvCompleteTime;//工单完成时间
    @ViewInject(R.id.inTitle3) View inTitle3;private boolean inTitle3Flag;
    @ViewInject(R.id.etRemarks2) EditText etRemarks2;//完成备注
    @ViewInject(R.id.llContentPhoto) LinearLayout llContentPhoto;//图片部分，显示或者隐藏
    //需要不可点击的状态
    @ViewInject(R.id.tvNoteContent211) TextView tvNoteContent211;
    @ViewInject(R.id.tvNoteContent212) TextView tvNoteContent212;
    @ViewInject(R.id.tvNoteContent221) TextView tvNoteContent221;
    @ViewInject(R.id.tvNoteContent222) TextView tvNoteContent222;
    @ViewInject(R.id.tvNoteContent231) TextView tvNoteContent231;
    @ViewInject(R.id.tvNoteContent241) TextView tvNoteContent241;
    @ViewInject(R.id.tvNoteContent251) TextView tvNoteContent251;
    @ViewInject(R.id.tvNoteContent252) TextView tvNoteContent252;
    @ViewInject(R.id.tvNoteContent261) TextView tvNoteContent261;
    @ViewInject(R.id.tvNoteContent271) TextView tvNoteContent271;
    @ViewInject(R.id.tvNoteContent281) TextView tvNoteContent281;
    @ViewInject(R.id.btnLocation) Button btnLocation;

    private int deviceType = 1;//设备类型 1：逆变器;2：储能机;3：采集器
    private String[] devices ;
    @ViewInject(R.id.tvDevice) TextView tvDevice;//设备类型
    private int completeType = 2;//完成状态;1：待观察;2：已完成
    private String[] completeTypeStrs ;
    @ViewInject(R.id.tvCompleteType) TextView tvCompleteType;//完成状态
    @ViewInject(R.id.etDeviceSN) EditText etDeviceSN;//设备序列号
    private String[] photoTypes;
    //图片
    private Uri imageUri;
    private File imageFile;
    private byte[] imageData;
    private File iconFile;
    private File file;
    private int photoType = 1;//1：代表现场报告单；2：代表其他
    /*现场报告单*/
    @ViewInject(R.id.recycleViewField2) RecyclerView recycleViewField2;
    private List<String> fieldService2s;
    private OssReplyQuesAdapter fieldServiceAdapter2;
    /*其他图片*/
    @ViewInject(R.id.recycleViewOther2) RecyclerView recycleViewOther2;
    private List<String> otherService2s;
    private OssReplyQuesAdapter otherServiceAdapter2;
//内容3
    @ViewInject(R.id.inContent3) View inContent3;
    @ViewInject(R.id.ivDown3) ImageView ivDown3;
    @ViewInject(R.id.ivUp3) ImageView ivUp3;
    @ViewInject(R.id.ivIcon3) ImageView ivIcon3;
    @ViewInject(R.id.lineUp31) View lineUp31;
    @ViewInject(R.id.lineUp32) View lineUp32;
    //    @ViewInject(R.id.tvLocation3) TextView tvLocation3;//位置
        @ViewInject(R.id.tvRemarks3) TextView tvRemarks3;//显示备注
    @ViewInject(R.id.tvApplicationTime3) TextView tvApplicationTime3;//工单申请时间
    @ViewInject(R.id.tvReceiveTime3) TextView tvReceiveTime3;//工单接收时间
    @ViewInject(R.id.tvAppointmentTime3) TextView tvAppointmentTime3;//工单预约时间
    @ViewInject(R.id.tvCompleteTime3) TextView tvCompleteTime3;//工单完成时间
//    @ViewInject(R.id.tvDevice3) TextView tvDevice3;
//    @ViewInject(R.id.tvDeviceSN3) TextView tvDeviceSN3;
    @ViewInject(R.id.tvCompleteType3) TextView tvCompleteType3;
    /*现场报告单*/
    @ViewInject(R.id.recycleViewField3) RecyclerView recycleViewField3;
    private List<String> fieldService3s;
    private OssReplyQuesAdapter fieldServiceAdapter3;
    /*其他图片*/
    @ViewInject(R.id.recycleViewOther3) RecyclerView recycleViewOther3;
    private List<String> otherService3s;
    private OssReplyQuesAdapter otherServiceAdapter3;

    private int status = 2;//派单状态：1：已分配；2：待接收；3：服务中；4：已完成
    private int jumpWorkId;//工单派单ID；
    private OssGDQuestionListBean jumpBean;
//定位
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener;
    //裁剪图片
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "Crop";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initRecycleView();
        initHeaderView();
        initIntent();
        ossGDDetical(jumpWorkId);
    }

    private void initView() {
        devices = new String[]{getString(R.string.all_interver),getString(R.string.all_storage),"采集器"};
        photoTypes = new String[]{getString(R.string.all_photo_album),getString(R.string.all_photograph)};
        serviceTypeStrs = new String[]{"","现场维修","安装调试","培训","巡检"};
        completeTypeStrs = new String[]{"待观察","已完成"};
    }
    private void initRecycleView() {
     //第二页
        //报告单
        fieldService2s = new ArrayList<>();
        recycleViewField2.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        fieldServiceAdapter2 = new OssReplyQuesAdapter(mContext,R.layout.item_putin2,fieldService2s);
        recycleViewField2.setAdapter(fieldServiceAdapter2);
        //其他照片
        otherService2s = new ArrayList<>();
        recycleViewOther2.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        otherServiceAdapter2 = new OssReplyQuesAdapter(mContext,R.layout.item_putin2,otherService2s);
        recycleViewOther2.setAdapter(otherServiceAdapter2);
     //第三页
        //报告单
        fieldService3s = new ArrayList<>();
        recycleViewField3.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        fieldServiceAdapter3 = new OssReplyQuesAdapter(mContext,R.layout.item_putin2,fieldService3s,true);
        recycleViewField3.setAdapter(fieldServiceAdapter3);
        //其他照片
        otherService3s = new ArrayList<>();
        recycleViewOther3.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        otherServiceAdapter3 = new OssReplyQuesAdapter(mContext,R.layout.item_putin2,otherService3s,true);
        recycleViewOther3.setAdapter(otherServiceAdapter3);
    }
    private View headerView;
    private void initHeaderView() {
        headerView = findViewById(R.id.headerView);
        setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setHeaderTvTitle(headerView, getString(R.string.m搜索设备), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(OssDeviceActivity.class,false);
            }
        });
    }

    /**
     * 接收工单
     * @param v
     */
    @Event(value = R.id.btnReceiver1)
    private void btnReceiver1(View v){
        String appointment = tvAppointment.getText().toString();
        String address = etAddress1.getText().toString();
        if (TextUtils.isEmpty(appointment) || TextUtils.isEmpty(address)){
            toast(R.string.all_blank);
            return;
        }
        StringBuilder sb = new StringBuilder(tvRemarks1.getText()).append(etRemarks1.getText().toString());
        updateGDStatusReceiver(v,jumpWorkId,status,appointment,address,sb.toString());
    }
    /**
     * 完成工单
     * @param v
     */
    @Event(value = R.id.btnFinish2)
    private void btnFinish2(View v){
        String appointment = tvAppointment.getText().toString().trim();
        String location = tvLocation.getText().toString().trim();
        String completeTime = tvCompleteTime.getText().toString().trim();
        String remarks2 = etRemarks2.getText().toString();
        String deviceSerialNumber = etDeviceSN.getText().toString().trim();
        StringBuilder sb = new StringBuilder(tvRemarks2.getText()).append(remarks2);
        updateGDStatusFinish(v,jumpWorkId,status,completeType,location,completeTime,deviceType,deviceSerialNumber,sb.toString(),fieldService2s,otherService2s);
    }

    /**
     * 更新服务器工单状态:客服完成
     * @param orderId
     * @param status
     * @param completeType
     * @param location
     * @param completeTime
     * @param deviceType
     * @param deviceSerialNumber
     * @param remarks2
     * @param fieldService2s
     * @param otherService2s
     */
    private void updateGDStatusFinish(final View clickView, final int orderId, int status, int completeType, String location, String completeTime, int deviceType, String deviceSerialNumber, String remarks2, List<String> fieldService2s, List<String> otherService2s) {
        if (TextUtils.isEmpty(location) || TextUtils.isEmpty(completeTime) || fieldService2s == null || fieldService2s.size()<= 0){
            toast(R.string.all_blank);
            return;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("status", status);
        map.put("completeType", completeType);
        map.put("location", location);
        map.put("completeTime", completeTime);
        map.put("deviceType", deviceType);
        map.put("deviseSerialNumber", deviceSerialNumber);
        map.put("remarks", remarks2);
        int size1 = fieldService2s.size();
        for (int i = 1;i<6;i++){
            String value = size1 >= i ? fieldService2s.get(i-1):"";
            map.put("image"+i,value);
        }
        int size2 = otherService2s.size();
        for (int i = 6;i<11;i++){
            String value = size2 >= i-5 ? otherService2s.get(i-6):"";
            map.put("image"+i,value);
        }
        updateGDStatus(clickView,map,orderId);
    }
    public void updateGDStatus(final View clickView, Map map, final int orderId){
        LogUtil.i("imageMap:"+map.toString());
        clickView.setEnabled(false);
        Mydialog.Show(mContext);
        AddCQ.postUp(OssUrls.orderPerfectOssGD(), map, new GetUtil.GetListener() {
            @Override
            public void success(String json) {
                clickView.setEnabled(true);
                if(TextUtils.isEmpty(json)) return;
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        toast(R.string.all_success);
                        ossGDDetical(orderId);
                    }else {
                        OssUtils.showOssToast(mContext,jsonObject.getString("msg"),result);
//                        MyControl.circlerDialog(OssGDDeticalActivity.this,getString(R.string.all_failed),result,false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Mydialog.Dismiss();
                    clickView.setEnabled(true);
                }
            }
            @Override
            public void error(String json) {clickView.setEnabled(true);}
        });
    }
    /**
     * 更新服务器工单状态:客服接收
     * @param orderId:工单id
     * @param status:工单状态
     * @param appointment：预约时间
     * @param address：地址
     * @param remarks：备注
     */
    public void updateGDStatusReceiver(View clickView, int orderId, int status, String appointment, String address, String remarks){
        Map<String,Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("status", status);
        map.put("appointment", appointment);
        map.put("address", address);
        map.put("remarks", remarks);
        updateGDStatus(clickView,map,orderId);
    }
    /**
     * 预约时间
     * @param v
     */
    private StringBuilder sb;
    @Event(value = R.id.llAppointment)
    private void llAppointment(View v){
        if (status != 2) return;
        final Calendar c = Calendar.getInstance();
        sb =  new StringBuilder();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(mContext,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        sb.append(year).append("-").append((monthOfYear+1) < 10 ? ("0" + (monthOfYear+1)) : (monthOfYear+1))
                                .append("-").append((dayOfMonth < 10 ? ("0" + dayOfMonth) :  dayOfMonth));
                        tvAppointment.setText(sb.toString());
                        // 创建一个TimePickerDialog实例，并把它显示出来
                        new TimePickerDialog(mContext,
                                // 绑定监听器
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        int second = c.get(Calendar.SECOND);
                                        sb.append(" ").append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay)
                                                .append(":").append(minute < 10 ? "0" + minute :minute)
                                                .append(":").append(second < 10 ? "0"+ second : second);
                                        tvAppointment.setText(sb.toString());
                                    }
                                }
                                // 设置初始时间
                                , c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                                // true表示采用24小时制
                                true){
                            @Override
                            protected void onStop() {
//                                super.onStop();
                            }
                        }.show();
                    }
                }
                // 设置初始日期
                , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                .get(Calendar.DAY_OF_MONTH)){
            @Override
            protected void onStop() {
//                super.onStop();
            }
        }.show();
    }
    /**
     * 工单完成时间
     * @param v
     */
    @Event(value = R.id.llCompleteTime)
    private void llCompleteTime(View v){
        if (status != 3) return;
        tvCompleteTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
    /**
     * 经纬度获取
     * @param v
     */
    @Event(value = R.id.btnLocation)
    private void btnLocation(View v){
        if (status != 3) return;
        if (mLocationClient == null){
            initLocation();
        }
        Mydialog.Show(mContext);
        mLocationClient.start();
    }

    @Override
    protected void onDestroy() {
        if (mLocationClient != null){
            mLocationClient.unRegisterLocationListener(myListener);
            if (mLocationClient.isStarted()){
                mLocationClient.stop();
            }
        }
        super.onDestroy();
    }

    /**
     * 选择设备类型
     * @param v
     */
    @Event(value = R.id.llDeviceType)
    private void llDeviceType(View v){
        if (status != 3) return;
        new CircleDialog.Builder(this)
                .setTitle(getString(R.string.dataloggers_list_type))
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = R.style.MyDialogStyle;
                    }
                })
                .setItems(devices, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        deviceType = position + 1;
                        tvDevice.setText(devices[position]);
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }
    /**
     * 选择完成状态
     * @param v
     */
    @Event(value = R.id.llCompleteType)
    private void llCompleteType(View v){
        if (status != 3) return;
        new CircleDialog.Builder(this)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = R.style.MyDialogStyle;
                    }
                })
                .setItems(completeTypeStrs, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        completeType = position + 1;
                        tvCompleteType.setText(completeTypeStrs[position]);
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }
    /**
     * 现场服务单照片
     */
    @Event(value = R.id.llFieldService)
    private void llFieldService(View v){
        if (fieldService2s.size() >= 5){
            return;
        }
        photoType = 1;
        setServicPhoto();
    }

    /**
     * 打开相机或相册
     */
    private void setServicPhoto() {
        new CircleDialog.Builder(this)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        //增加弹出动画
                        params.animStyle = R.style.MyDialogStyle;
                    }
                })
                .setItems(photoTypes, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        imageFile  = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
//                        if (Build.VERSION.SDK_INT >= 24) {
//                            imageUri = FileProvider.getUriForFile(OssGDDeticalActivity.this, getPackageName(), imageFile);
//                        } else {
                            imageUri = Uri.fromFile(imageFile);
//                        }
                        switch (position){
                            case 0:
                                selectPicture();
                                break;
                            case 1:
                                takePicture();
                                break;
                        }
                    }
                })
                .setNegative(getString(R.string.all_no),null)
                .show();
    }

    /**
     * 其他现场照片
     */
    @Event(value = R.id.llOtherServices)
    private void llOtherServices(View v){
        if (otherService2s.size() >= 5){
            return;
        }
        photoType = 2;
        setServicPhoto();
    }
    /**
     * 根据工单派单id获取详情
     */
    public void ossGDDetical(final int orderId){
        PostUtil.post(OssUrls.orderDeticalOssGD(), new PostUtil.postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("orderId",orderId + "");
            }
            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        String beanJson = jsonObject.getJSONObject("obj").toString();
                        OssGDQuestionListBean bean = new Gson().fromJson(beanJson,OssGDQuestionListBean.class);
                        updateUI(bean);
                    }else {
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
     * 根据工单详情更新界面
     * @param bean
     */
    public void updateUI(OssGDQuestionListBean bean){
        if (bean != null){
            status = bean.getStatus();
            jumpWorkId = bean.getId();
            deviceType = bean.getDeviceType();
            //头部
            tvWorkTitle.setText(bean.getTitle());
            tvTitle.setText(bean.getTitle());
            int serviceType = bean.getServiceType();
            if (serviceType>0 && serviceType <= 4){
                tvServiceType.setText(serviceTypeStrs[serviceType]);
            }
            tvCustomerName.setText(bean.getCustomerName());
            tvContact.setText(bean.getContact());
            //备注
            switch (status){
                case 2:
                    tvRemarks1.setText(bean.getRemarks());
                    break;
                case 3:
                    tvRemarks2.setText(bean.getRemarks());
                    break;
                case 4:
                    tvRemarks3.setText(bean.getRemarks());
                    break;
            }
            //第一页
            tvGroupName.setText(bean.getGroupName());
            etAddress1.setText(bean.getAddress());
            tvApplicationTime.setText(bean.getApplicationTime());
            tvDoorTime.setText(bean.getDoorTime());
            tvAppointment.setText(bean.getAppointment());
            //第二页
            tvCompleteTime.setText(bean.getCompleteTime());
            tvLocation.setText(bean.getLocation());
            if (deviceType < 1 || deviceType > 3){
//                tvDevice.setText(devices[0]);
            }else {
                tvDevice.setText(devices[deviceType-1]);
            }
            etDeviceSN.setText(bean.getDeviseSerialNumber());
        //第三页
            tvApplicationTime3.setText(bean.getApplicationTime());
            tvReceiveTime3.setText(bean.getReceiveTime());
            tvAppointmentTime3.setText(bean.getAppointment());
            tvCompleteTime3.setText(bean.getCompleteTime());
            MyUtils.showAllView(llContentPhoto);
            if (status == 4){
                MyUtils.hideAllView(View.GONE,llContentPhoto);
                completeType = bean.getCompleteState();
                if (completeType < 1 || completeType > 2){
//                tvCompleteType.setText(completeTypeStrs[1]);
                }else {
                    tvCompleteType.setText(completeTypeStrs[completeType-1]);
                    tvCompleteType3.setText(completeTypeStrs[completeType-1]);
                }
            }
            //图片显示
            String fieldService = bean.getFieldService();
            if (!TextUtils.isEmpty(fieldService) && fieldService.contains("_")){
                String[] fields = fieldService.split("_");
                LogUtil.i("fields:" + fields.length);
                List<String> newList = new ArrayList<>();
                for (int i = 0,length = fields.length;i < length;i++){
                    newList.add(OssUrls.oss_photo_url + fields[i]);
                }
                fieldServiceAdapter3.addAll(newList,true);
            }
            String otherService = bean.getOtherServices();
            if (!TextUtils.isEmpty(otherService) && otherService.contains("_")){
                String[] fields = otherService.split("_");
                LogUtil.i("fields:" + fields.length);
                List<String> newList = new ArrayList<>();
                for (int i = 0,length = fields.length;i < length;i++){
                    newList.add(OssUrls.oss_photo_url + fields[i]);
                }
                otherServiceAdapter3.addAll(newList,true);
            }
            refresh();
        }
    }
    private void initIntent() {
        Intent intent = getIntent();
        jumpBean = intent.getParcelableExtra("bean");
        updateUI(jumpBean);
    }

    /**
     * 刷新工单流程状态颜色值以及按钮可用与禁用
     */
    private void refresh() {
        switch (status){
            case 2:
                receiveWork();
                break;
            case 3:
                processWork();
                break;
            case 4:
                finishWork();
                break;
        }
    }

    /**
     * 拨打电话
     * @param v
     */
    @Event(value = R.id.llCallPhone)
    private void llCallPhone(View v){
        final String phone = tvContact.getText().toString();
        if(TextUtils.isEmpty(phone)) return;
        String str=getResources().getString(R.string.AboutActivity_determine_call);
        AlertDialog builder = new AlertDialog.Builder(OssGDDeticalActivity.this).setTitle(R.string.AboutActivity_call)
                .setMessage(str+phone).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).setNegativeButton(R.string.all_no, null).create();
        builder.show();
    }
    /**
     * 派单已完成
     */
    private void finishWork() {
        //禁用内容二中的完成按钮
        btnReceiver1.setEnabled(false);
        btnFinish2.setEnabled(false);
        ivNoteContent143.setImageResource(R.drawable.ossgd_workoader_date_icon_1);
        setTextColorGray(tvNoteContent211,tvNoteContent212,tvNoteContent221,tvNoteContent222,
                tvNoteContent231,tvNoteContent241,tvNoteContent251,tvNoteContent252,
                tvNoteContent261,tvNoteContent271,tvNoteContent281,tvLocation,btnLocation,tvCompleteTime,tvDevice
        ,etDeviceSN,tvCompleteType,etRemarks2,tvNoteContent141,tvNoteContent142,tvAppointment,
                tvNoteContent151,tvNoteContent152,tvNoteContent171,etAddress1,etRemarks1);
        setDisEnable(etDeviceSN,etRemarks2,etRemarks1,etAddress1);
        setBtnBgCircleGray(btnReceiver1,btnFinish2);
        ivIcon2.setImageResource(R.drawable.ossgd_yuan_2);
        ivIcon3.setImageResource(R.drawable.ossgd_yuan_2);
        setLineBgYellow(lineUp21,lineUp22,lineContent2,lineUp31);
        if (!inTitle3Flag){
            inTitle3(null);
        }
    }
    /**
     * 派单服务中
     */
    private void processWork() {
        //禁用内容1中的接收按钮以及状态显示为灰色
        btnFinish2.setEnabled(true);
        btnReceiver1.setEnabled(false);
        setTextColorGray(tvNoteContent141,tvNoteContent142,tvAppointment,tvNoteContent151,tvNoteContent152,tvNoteContent171,etAddress1,etRemarks1);
        setDisEnable(etRemarks1,etAddress1);
        ivNoteContent143.setImageResource(R.drawable.ossgd_workoader_date_icon_1);
        setBtnBgCircleGray(btnReceiver1);
        setBtnBgCircleBlue(btnFinish2);
        ivIcon2.setImageResource(R.drawable.ossgd_yuan_2);
        ivIcon3.setImageResource(R.drawable.ossgd_yuan_1);
        setLineBgGray(lineUp31);
        setLineBgYellow(lineUp21,lineUp22,lineContent2);
        if (!inTitle2Flag){
            inTitle2(null);
        }
    }

    /**
     * 派单待接收
     */
    private void receiveWork() {
        btnReceiver1.setEnabled(true);
        btnFinish2.setEnabled(false);
        setBtnBgCircleGray(btnFinish2);
        setBtnBgCircleBlue(btnReceiver1);
        ivIcon2.setImageResource(R.drawable.ossgd_yuan_1);
        ivIcon3.setImageResource(R.drawable.ossgd_yuan_1);
        setLineBgGray(lineUp21,lineUp22,lineContent2,lineUp31);
        if (!inTitle1Flag){
            inTitle1(null);
        }
    }

    /**
     * 设置线条黄色
     * @param vs
     */
    public void setLineBgYellow(View... vs){
        for (View v : vs){
            v.setBackgroundColor(ContextCompat.getColor(this,R.color.oss_gd_yuan2));
        }
    }
    /**
     * 设置线条灰色
     * @param vs
     */
    public void setLineBgGray(View... vs){
        for (View v : vs){
            v.setBackgroundColor(ContextCompat.getColor(this,R.color.oss_gd_yuan1));
        }
    }
    /**
     * 设置字体为灰色
     * @param vs
     */
    public void setTextColorGray(TextView... vs){
        for (TextView v : vs){
            v.setTextColor(ContextCompat.getColor(this,R.color.note_bg_white));
        }
    }
    /**
     * 设置控件禁止获得焦点
     * @param vs
     */
    public void setDisEnable(View... vs){
        for (View v : vs){
            v.setEnabled(false);
        }
    }
    /**
     * 设置按钮禁用背景
     * @param vs
     */
    public void setBtnBgCircleGray(View... vs){
        for (View v : vs){
            v.setBackgroundResource(R.drawable.btn_circle_gray);
        }
    }
    /**
     * 设置按钮正常蓝色点击背景
     * @param vs
     */
    public void setBtnBgCircleBlue(View... vs){
        for (View v : vs){
            v.setBackgroundResource(R.drawable.selector_circle_btn_blue);
        }
    }
    /**
     * intitle1事件
     * @param v
     */
    @Event(value = R.id.inTitle1)
    private void inTitle1(View v){
        if (!inTitle1Flag){
            inTitle1Flag = true;
            MyUtils.showAllView(inContent1,ivUp1);
            MyUtils.hideAllView(View.GONE,ivDown1);
        }else {
            inTitle1Flag = false;
            MyUtils.hideAllView(View.GONE,inContent1,ivUp1);
            MyUtils.showAllView(ivDown1);
        }
    }
    /**
     * intitle2事件
     * @param v
     */
    @Event(value = R.id.inTitle2)
    private void inTitle2(View v){
        if (!inTitle2Flag){
            inTitle2Flag = true;
            MyUtils.showAllView(inContent2,ivUp2);
            MyUtils.hideAllView(View.GONE,ivDown2);
        }else {
            inTitle2Flag = false;
            MyUtils.hideAllView(View.GONE,inContent2,ivUp2);
            MyUtils.showAllView(ivDown2);
        }
    }
    /**
     * intitle3事件
     * @param v
     */
    @Event(value = R.id.inTitle3)
    private void inTitle3(View v){
        if (!inTitle3Flag){
            inTitle3Flag = true;
            MyUtils.showAllView(inContent3,ivUp3);
            MyUtils.hideAllView(View.GONE,ivDown3);
        }else {
            inTitle3Flag = false;
            MyUtils.hideAllView(View.GONE,inContent3,ivUp3);
            MyUtils.showAllView(ivDown3);
        }
    }
    /**
     * 拍照
     */
    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //将拍照的图片保存到本地
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
        Uri iconUri = null;
//        if (Build.VERSION.SDK_INT >= 24) {
//            iconUri = FileProvider.getUriForFile(this, getPackageName(), file);
//        } else {
            iconUri = Uri.fromFile(file);
//        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, iconUri);
        startActivityForResult(intent, 102);
    }

    /**
     * 从相册选择
     */
    public void selectPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 101) {//从相册获取
            Uri uri = data.getData();
            //根据uri查找图片地址
            ContentResolver resolver = getContentResolver();
            String[] pojo = {MediaStore.Images.Media.DATA};
            Cursor cursor = resolver.query(uri, pojo, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
                cursor.moveToFirst();
                String path = cursor.getString(columnIndex);
                file = new File(path);
                cursor.close();
            }
//            crop(uri);//裁剪头像
            startCropActivity(uri);
        } else if (resultCode == RESULT_OK && requestCode == 102) {//拍照
            Uri uri1 = null;
//            if (Build.VERSION.SDK_INT >= 24) {
//                uri1 = getImageContentUri(this, file);
//            } else {
                uri1 = Uri.fromFile(file);
//            }
//            crop(uri1);//裁剪头像
            startCropActivity(uri1);
        }
        if (resultCode == RESULT_OK && requestCode == 103) {//收到截取后的图像
            if (imageUri != null){
                Bitmap bitmap = decodeUriAsBitmap(imageUri);
                if (bitmap != null) {
                    //将头像转为二进制数组写入临时文件传给服务器
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(bitmap.getWidth() * bitmap.getHeight() * 4);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    imageData = baos.toByteArray();
                    try {
                        iconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath(), System.currentTimeMillis() + "quesIcon.png");
                        if (!iconFile.exists()) {
                            iconFile.createNewFile();
                        }
                        FileOutputStream out = new FileOutputStream(iconFile);
                        out.write(imageData);
                        out.flush();
                        out.close();
                        addFileToRecyclerViewField(iconFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //裁剪
        if (resultCode == RESULT_OK) {
           if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
                destinationFileName += System.currentTimeMillis()+".jpg";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));

//        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);
//
        uCrop.start(this);
    }

    /**
     * In most cases you need only to set crop aspect ration and max size for resulting image.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop basisConfig(@NonNull UCrop uCrop) {
        return uCrop;
    }
    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            String path = ImagePathUtil.getRealPathFromUri(this,resultUri);
            addFileToRecyclerViewField(path);
            LogUtil.i("path=="+ path);
        } else {
            toast(R.string.all_failed);
        }
    }
    /**
     * 添加图片到控件，初次添加时显示控件，隐藏控件逻辑在adapter中
     */
    public void addFileToRecyclerViewField(String filePath){
        List<String> newList = new ArrayList<>();
        newList.add(filePath);
        switch (photoType){
            case 1:
                fieldServiceAdapter2.addAll(newList,false);
                break;
            case 2:
                otherServiceAdapter2.addAll(newList,false);
                break;
        }

    }
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e("TAG", "handleCropError: ", cropError);
            Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            toast(R.string.all_failed);
        }
    }
    private Bitmap decodeUriAsBitmap(Uri uri){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
    /**
     * Sometimes you want to adjust more options, it's done via {@link com.yalantis.ucrop.UCrop.Options} class.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();

        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        return uCrop.withOptions(options);
    }
    /**
     * 添加图片到控件，初次添加时显示控件，隐藏控件逻辑在adapter中
     * @param file
     */
    public void addFileToRecyclerViewField(File file){
        List<String> newList = new ArrayList<>();
        newList.add(file.getAbsolutePath());
        switch (photoType){
            case 1:
                fieldServiceAdapter2.addAll(newList,false);
                break;
            case 2:
                otherServiceAdapter2.addAll(newList,false);
                break;
        }

    }
    /**
     * 7.0 获取图片文件uri
     *
     * @param context
     * @param file
     * @return
     */
    private Uri getImageContentUri(Context context, File file) {
        String filePath = file.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=?", new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (file.exists()) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            }
        }
        return null;
    }
    /***
     * 调用android 自带的裁剪功能，对图片进行裁剪
     *
     * @param uri 被裁剪图片的路径
     */
    private void crop(Uri uri) {
        //隐式意图调用android自身的图片截取界面
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 9);
        intent.putExtra("aspectY", 16);
        intent.putExtra("outputX", 450);
        intent.putExtra("outputY", 800);
        //设置了true的话直接返回bitmap，可能会很占内存
        intent.putExtra("return-data", false);
        //设置输出的格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        //设置输出的地址
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, 103);//强截取的头像返回（bundle）
    }
    private void initLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//		option.setCoorType("WGS84");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000 * 10;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(true);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myListener);
    }
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location){
                Message msg = Message.obtain();
                msg.what = 100;
                msg.obj = location;
                locationHandler.sendMessage(msg);
            }
        }
        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }
    Handler locationHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    BDLocation location = (BDLocation) msg.obj;
//                    //获取定位结果
//                    StringBuffer sb = new StringBuffer(256);
//                    sb.append("time : ");
//                    sb.append(location.getTime());    //获取定位时间
//                    sb.append("\nerror code : ");
//                    sb.append(location.getLocType());    //获取类型类型
//                    sb.append("\nlatitude : ");
//                    sb.append(location.getLatitude());    //获取纬度信息
//                    sb.append("\nlontitude : ");
//                    sb.append(location.getLongitude());    //获取经度信息
//                    sb.append("\nradius : ");
//                    sb.append(location.getRadius());    //获取定位精准度
                    LogUtil.i("定位："+ Thread.currentThread().getName()+":"+location.getLocType());
                    Mydialog.Dismiss();
                    int code = location.getLocType();
                    if (code == 61 || code == 66 || code == 161) {
                        String city=location.getCity();
                        double lat = location.getLatitude();
                        double lng = location.getLongitude();
                        StringBuilder sb = new StringBuilder();
                        sb.append(city).append("(").append(lng).append(",").append(lat).append(")");
                        tvLocation.setText(sb.toString());
                        if (mLocationClient.isStarted()) {
                            mLocationClient.stop();
                        }
                    }else if(code == 167){//定位权限被关闭
                        new CircleDialog.Builder(OssGDDeticalActivity.this)
                                .setWidth(0.7f)
                                .setTitle(getString(R.string.reminder))
                                .setText(getString(R.string.utf_open_gprs))
                                .setPositive(getString(R.string.action_settings), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    } else {
                        //定位失败
                        try {
                            AlertDialog.Builder builder = new AlertDialog.Builder(OssGDDeticalActivity.this);
                            builder.setTitle(R.string.geographydata_obtain_no).setMessage(R.string.geographydata_obtain_again).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int arg1) {
                                    dialog.dismiss();
                                    Mydialog.Show(OssGDDeticalActivity.this,R.string.register_gain_longandlat);
                                    if (!mLocationClient.isStarted()){
                                        mLocationClient.start();
                                    }
                                }
                            }).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int arg1) {
                                    dialog.dismiss();
                                    if (mLocationClient.isStarted()){
                                        mLocationClient.stop();
                                    }
                                }
                            }).create();
                            builder.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };
}
