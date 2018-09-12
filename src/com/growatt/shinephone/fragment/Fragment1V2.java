package com.growatt.shinephone.fragment;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.AddPlantActivity;
import com.growatt.shinephone.activity.DataloggersActivity;
import com.growatt.shinephone.activity.InverterActivity;
import com.growatt.shinephone.activity.MipcaActivityCapture;
import com.growatt.shinephone.activity.StorageActivity;
import com.growatt.shinephone.activity.StorageSp5kActivity;
import com.growatt.shinephone.activity.UserdataActivity;
import com.growatt.shinephone.adapter.FragspinnerAdapter;
import com.growatt.shinephone.adapter.v2.Fragment1V2Adapter;
import com.growatt.shinephone.adapter.v2.Fragment1V2Item;
import com.growatt.shinephone.bean.DeviceTotalBean;
import com.growatt.shinephone.bean.StorageStatusBean;
import com.growatt.shinephone.bean.mix.HtmlBean;
import com.growatt.shinephone.bean.mix.MixStatusBean;
import com.growatt.shinephone.bean.v2.Fragment1ListBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnAnimationEndLinster;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.AddCQ;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.GlideUtils;
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.MyUtilsTotal;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.PostUtil.postListener;
import com.growatt.shinephone.util.T;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.util.max.Arith;
import com.growatt.shinephone.util.mix.MixUtil;
import com.growatt.shinephone.util.v2.FragV2Util;
import com.growatt.shinephone.util.v2.Fragment1Field;
import com.growatt.shinephone.util.v2.MyFragmentV1Top;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigItems;
import com.mylhyl.circledialog.params.ItemsParams;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.growatt.shinephone.util.v2.Fragment1Field.deviceType;


public class Fragment1V2 extends BaseFragment implements BaseQuickAdapter .OnItemClickListener{
    private View view;
    View v;
    //列表数据
    private SwipeMenuRecyclerView mRecyclerView;
    private List<Fragment1ListBean> mList;
    private Fragment1V2Adapter mAdapter;

    private int height;
    private FragspinnerAdapter spadapter;
    private ImageView add;

    private Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            Mydialog.Dismiss();
            switch (msg.what) {
                case 0:
                    String json=(String) msg.obj;
                    try {
                        JSONObject jsonObject=new JSONObject(json);
                        if(jsonObject.getString("msg").toString().equals("200")){
                            T.make(R.string.all_success,getActivity());
//                            mAdapter.remove(Clickpositions);
                            refresh();
//                            Fragment1ListBean item = mAdapter.getItem(Clickpositions);
//                            item.setDeviceAilas(alis);
//                            mAdapter.notifyItemRangeChanged(Clickpositions,1);
//                            lists.get(Clickpositions).put("deviceAilas", alis);
                        }
                        if(jsonObject.getString("msg").toString().equals("501")){
                            T.make(R.string.serviceerror,getActivity());
                        } else if ("701".equals(jsonObject.getString("msg").toString())){
                            T.make(R.string.m7,getActivity());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    T.make(R.string.serviceerror,getActivity());
                    break;
                default:
                    break;
            }
        };
    };

    private int Clickpositions=0;
    private int positions=0;
    private String plant;
    private TextView tv1,tv3,tv4,tv6,tv7,tv9;
    private TextView tvTotalPower,tvTotalEnergy,tvTotalRevenue;
    private TextView title;
    //	private PullToRefreshListView pulllayout;
    private TextView tv11,tv21,tv31,tv41,tv51,tv61,tv71,tv81,tv91;
    private LinearLayout rl4;//锟斤拷图锟斤拷锟斤拷
    ImageView ivXiala;//锟斤拷锟斤拷锟斤拷锟斤拷
    ImageView ivEtPlant;
    //String  dataLogUrl;//锟斤拷取锟缴硷拷锟斤拷锟斤拷息url;锟斤拷锟斤拷锟斤拷FragUtil
    //锟姐播锟斤拷锟斤拷锟斤拷
    MyReceiver receiver;
    private IntentFilter filter;
    //锟矫讹拷锟酵编辑时刷锟斤拷
    Intent frgIntent;
    private List<Map<String, Object>> footList;
    protected int app_code;
    protected String picurl;
    private View circle1;
    private View circle2;
    private View circle3;
    private Animation operatingAnim;
    private Animation trasAnim;
    private LinearLayout ll_Image;//头部图片
    private LinearLayout ll_Circle;//图片外圈
    private View floatHeader;
    private View storageView;//储能头部布局spf2k/3k
    private View storageSpf5kView;//储能头部布局spf5k
    private View line0,line1,line2,line3,line4,line5,line6,line7,line8,line9,line10;
    private ImageView ivId1,ivId2,ivId3,ivId4;
    private TextView tvPv1,tvPv2,tvPCharge,tvPacCharge,tvUserLoad,tvPacTo,tvCapacity;
    private RelativeLayout storageLayout;
    private int deviceDType = 0;//0:代表逆变器；1：代表sp2k,代表sp3k;2:代表sp5k;3:代表mix
    private static final String TAG = "fragment1";
    private final int storageTime = 1000;
    private boolean isAnimation = false;
    private boolean isAnimationSpf5k = false;
    private StorageStatusBean bean;
    private FrameLayout frameNote;


    private SwipeRefreshLayout swipeRefresh;//下拉刷新
    /*初始化脚部*/
    private View listFooter;
//    private View fl_demo1;
//    private View fl_demo2;
//    private View fl_demo3;
//    private View fl_demo4;
    private List<String> addTypes;//采集器添加的方式
    private Context mContext;
    /*mix部分*/
    private MixStatusBean mixBean = new MixStatusBean();
    private int mPreFirst = 0;
    /**
     * mix头部字体颜色:绿色/红/灰/灰蓝
     */
    private String[] mMixColors = {
            "#55A24E","#B17070","#aaaaaa","#52a4b3"
    };

    /**
     * 所有设备是否断开
     */
    private boolean allLost = true;
    /**
     * 返回oss界面
     */
    private LinearLayout llBackOss;
    /**
     * 全局控制
     */
    private List<View> mViewList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_fragment1_v2, container, false);
        frgIntent=new Intent(Constant.Frag_Receiver);
        initRecyclerView(view);
        initFloatView();
        SetViews();
        initSwipeRefresh(view);
        SetListeners();
        registerBroadCast();
        MyUtils.checkUpdate(getActivity(),Constant.LoginActivity_Updata);
        if(Cons.isCodeUpdate){
            getAPPMessage();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initRecyclerView(View view) {
        mRecyclerView=(SwipeMenuRecyclerView)view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mList = new ArrayList<>();
        mAdapter = new Fragment1V2Adapter(mList);
        // 设置侧滑
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }
    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
//            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext); // 各种文字和图标属性设置。
//            leftMenu.addMenuItem(deleteItem); // 在Item左侧添加一个菜单。
            float textsize = MyUtils.px2dip(getActivity(),getResources().getDimension(R.dimen.x12));
            LogUtil.i("侧滑类型："+viewType);
            if (viewType > Fragment1V2Item.DEVICE_TYPE_MAX || viewType < Fragment1V2Item.DEVICE_TYPE_MIN ) return;
            int width = getResources().getDimensionPixelSize(R.dimen.x60);
            SwipeMenuItem topItem = new SwipeMenuItem(mContext)
                    .setBackgroundColor(ContextCompat.getColor(mContext,R.color.inverter_wait))
                    .setText(getString(R.string.fragment1_top))
                    .setTextSize((int) textsize)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(LayoutParams.MATCH_PARENT); // 各种文字和图标属性设置。
            SwipeMenuItem editItem = new SwipeMenuItem(mContext)
                    .setBackgroundColor(ContextCompat.getColor(mContext,R.color.hint_bg_white))
                    .setText(getString(R.string.fragment1_edit))
                    .setTextSize((int) textsize)
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(LayoutParams.MATCH_PARENT); // 各种文字和图标属性设置。
            rightMenu.addMenuItem(topItem); // 在Item右侧添加一个菜单。
            rightMenu.addMenuItem(editItem); // 在Item右侧添加一个菜单。
        }
    };
    SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
//            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition() - mAdapter.getHeaderLayoutCount(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
//            toast("方向：" + direction + "----" + adapterPosition + "----" + menuPosition);
            if (menuPosition == 0) {//置顶
                Fragment1ListBean item = mAdapter.getItem(adapterPosition);
                item.setTimeId(System.currentTimeMillis() + "");
                item.setUserId(Cons.userId);
                item.setPlantId(Cons.plant);
                SqliteUtil.ids(item);
                Collections.sort(mAdapter.getData(), new MyFragmentV1Top());
                mAdapter.notifyDataSetChanged();
                mRecyclerView.scrollToPosition(0);
            }else if(menuPosition == 1){//编辑
                Clickpositions=adapterPosition;
                selectdialog(adapterPosition);
            }
        }
    };

    private void initSwipeRefresh(View view) {
        swipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.headerView);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(ll_Circle!=null&&ll_Circle.getVisibility()==View.INVISIBLE){
                    ll_Circle.setVisibility(View.VISIBLE);
                }
                if(operatingAnim != null && circle1 != null&&(!operatingAnim.hasStarted())){
                    circle1.startAnimation(operatingAnim);
                    circle2.startAnimation(operatingAnim);
                    circle3.startAnimation(operatingAnim);
                }else if (operatingAnim != null && circle1 != null && operatingAnim.hasStarted()) {
                    circle1.clearAnimation();
                    circle2.clearAnimation();
                    circle3.clearAnimation();
                    circle1.startAnimation(operatingAnim);
                    circle2.startAnimation(operatingAnim);
                    circle3.startAnimation(operatingAnim);
                }
                if(trasAnim!=null&& ll_Image!=null){
                    ll_Image.startAnimation(trasAnim);
                }
                refresh();
            }
        });
    }
    //Spf拼接弹框内容
    public String  storageDetial(){
        StringBuilder sb = new StringBuilder();
        if (bean != null){
            sb.append(getString(R.string.m45)).append(bean.getPpv1()).append("W").append("\n\n")
                    .append(getString(R.string.m46)).append(bean.getPpv2()).append("W").append("\n\n")
                    .append(getString(R.string.m47)).append(bean.getpCharge1()).append("W").append("\n\n")
                    .append(getString(R.string.m48)).append(bean.getpCharge2()).append("W").append("\n\n")
                    .append(getString(R.string.m49)).append(bean.getCapacity()).append("%").append("\n\n")
                    .append(getString(R.string.m50)).append(bean.getPacCharge()).append("W").append("\n\n")
                    .append(getString(R.string.m51)).append(bean.getUserLoad()).append("W").append("\n\n")
                    .append(getString(R.string.m52)).append(bean.getPacToGrid()).append("W").append("\n\n")
                    .append(getString(R.string.m53)).append(bean.getPacToUser()).append("W").append("\n")
            ;
        }else {
            sb.append(getString(R.string.m45)).append("0.0W").append("\n\n")
                    .append(getString(R.string.m46)).append("0.0W").append("\n\n")
                    .append(getString(R.string.m47)).append("0.0W").append("\n\n")
                    .append(getString(R.string.m48)).append("0.0W").append("\n\n")
                    .append(getString(R.string.m49)).append("0.0%").append("\n\n")
                    .append(getString(R.string.m50)).append("0.0W").append("\n\n")
                    .append(getString(R.string.m51)).append("0.0W").append("\n\n")
                    .append(getString(R.string.m52)).append("0.0W").append("\n\n")
                    .append(getString(R.string.m53)).append("0.0W").append("\n")
            ;
        }
        return sb.toString();
    }
    //拼接弹框内容
    public String  storageSpf5kDetial(){
        StringBuilder sb = new StringBuilder();
        if (bean != null){
            sb
                    .append(getString(R.string.m电池电压)).append(":").append(bean.getvBat()).append("V").append("\n\n")
                    .append(getString(R.string.m120PV1PV2电压)).append(":").append(bean.getvPv1()).append("/").append(bean.getvPv2()).append("V").append("\n\n")
                    .append(getString(R.string.m121PV1PV2充电电流)).append(":").append(bean.getiPv1()).append("/").append(bean.getiPv2()).append("A").append("\n\n")
                    .append(getString(R.string.m122总充电电流)).append(":").append(bean.getiTotal()).append("A").append("\n\n")
                    .append(getString(R.string.m123AC输入电压和频率)).append(":").append(bean.getvAcInput()).append("V/").append(bean.getfAcInput()).append("HZ").append("\n\n")
                    .append(getString(R.string.m124AC输出电压和频率)).append(":").append(bean.getvAcOutput()).append("V/").append(bean.getfAcOutput()).append("HZ").append("\n\n")
                    .append(getString(R.string.m负载功率)).append(":").append(bean.getLoadPower()).append("W").append("\n\n")
                    .append(getString(R.string.m126负载百分比)).append(":").append(bean.getLoadPrecent()).append("%").append("\n\n")
            ;
        }else {
            sb
                    .append(getString(R.string.m电池电压)).append(":").append("0.0").append("V").append("\n\n")
                    .append(getString(R.string.m120PV1PV2电压)).append(":").append("0.0").append("/").append("0.0").append("V").append("\n\n")
                    .append(getString(R.string.m121PV1PV2充电电流)).append(":").append("0.0").append("/").append("0.0").append("A").append("\n\n")
                    .append(getString(R.string.m122总充电电流)).append(":").append("0.0").append("A").append("\n\n")
                    .append(getString(R.string.m123AC输入电压和频率)).append(":").append("0.0").append("V/").append("0.0").append("HZ").append("\n\n")
                    .append(getString(R.string.m124AC输出电压和频率)).append(":").append("0.0").append("V/").append("0.0").append("HZ").append("\n\n")
                    .append(getString(R.string.m负载功率)).append(":").append("0.0").append("W").append("\n\n")
                    .append(getString(R.string.m126负载百分比)).append(":").append("0.0").append("%").append("\n\n")
            ;
        }
        return sb.toString();
    }
    //初始化储能头部spf2k/3k
    private void initStorageHeader() {
        storageView = LayoutInflater.from(getActivity()).inflate(R.layout.header_frag2_storage,mRecyclerView,false);
        View headerText = storageView.findViewById(R.id.llMyDeviceText);
        mViewList.add(headerText);
        storageLayout = (RelativeLayout) storageView.findViewById(R.id.activity_storagy);
        frameNote = (FrameLayout) storageView.findViewById(R.id.frameNote);
        //点击弹出详情
        frameNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyControl.textClick(frameNote,storageDetial());
            }
        });
        line0 = storageView.findViewById(R.id.line0);
        line1 = storageView.findViewById(R.id.line1);
        line2 = storageView.findViewById(R.id.line2);
        line3 = storageView.findViewById(R.id.line3);
        line4 = storageView.findViewById(R.id.line4);
        line5 = storageView.findViewById(R.id.line5);
        line6 = storageView.findViewById(R.id.line6);
        line7 = storageView.findViewById(R.id.line7);
        line8 = storageView.findViewById(R.id.line8);
        line9 = storageView.findViewById(R.id.line9);
        line10 = storageView.findViewById(R.id.line10);
//		line11 = storageView.findViewById(R.id.line11);
        ivId1 = (ImageView) storageView.findViewById(R.id.ivId1);
        ivId2 = (ImageView) storageView.findViewById(R.id.ivId2);
        ivId3 = (ImageView) storageView.findViewById(R.id.ivId3);
        ivId4 = (ImageView) storageView.findViewById(R.id.ivId4);
        tvPv1 = (TextView) storageView.findViewById(R.id.tvPv1);
        tvPv2 = (TextView) storageView.findViewById(R.id.tvPv2);
        tvPCharge = (TextView) storageView.findViewById(R.id.tvPCharge);
        tvPacCharge = (TextView) storageView.findViewById(R.id.tvPacCharge);
        tvUserLoad = (TextView) storageView.findViewById(R.id.tvUserLoad);
        tvPacTo = (TextView) storageView.findViewById(R.id.tvPacToGrid);
        tvCapacity = (TextView) storageView.findViewById(R.id.tvCapacity);
    }
    //初始化储能头部spf2k/3k
    private TextView tvLine2;
    private TextView tvLine3;
    private TextView tvLine4;
    private TextView tvLine42;
    private TextView tvLine5;
    private TextView tvCapacitySpf5k;
    private TextView tvStorageStatus;
    private FrameLayout frameNoteSpf5k;
    private ImageView ivId1Spf5k;
    private ImageView ivId2Spf5k;
    private ImageView ivId3Spf5k;
    private View spf5kLine1;
    private View spf5kLine2;
    private View spf5kLine3;
    private View spf5kLine4;
    private void initStorageSpf5kHeader() {
        storageSpf5kView= LayoutInflater.from(getActivity()).inflate(R.layout.header_storage_sp5k,mRecyclerView,false);
        View headerText = storageSpf5kView.findViewById(R.id.llMyDeviceText);
        mViewList.add(headerText);
        frameNoteSpf5k = (FrameLayout) storageSpf5kView.findViewById(R.id.frameNote);
        //点击弹出详情
        frameNoteSpf5k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拼接spf5k数据
                MyControl.textClick(frameNoteSpf5k,storageSpf5kDetial());
            }
        });
        tvLine2 = (TextView) storageSpf5kView.findViewById(R.id.tvLine2);
        tvLine3 = (TextView) storageSpf5kView.findViewById(R.id.tvLine3);
        tvLine4 = (TextView) storageSpf5kView.findViewById(R.id.tvLine4);
        tvLine42 = (TextView) storageSpf5kView.findViewById(R.id.tvLine42);
        tvLine5 = (TextView) storageSpf5kView.findViewById(R.id.tvLine5);
        tvStorageStatus = (TextView) storageSpf5kView.findViewById(R.id.tvStorageStatus);
        tvCapacitySpf5k = (TextView) storageSpf5kView.findViewById(R.id.tv4);
        ivId1Spf5k = (ImageView) storageSpf5kView.findViewById(R.id.ivId1);
        ivId2Spf5k = (ImageView) storageSpf5kView.findViewById(R.id.ivId2);
        ivId3Spf5k = (ImageView) storageSpf5kView.findViewById(R.id.ivId3);
        spf5kLine1 = storageSpf5kView.findViewById(R.id.line1);
        spf5kLine2 = storageSpf5kView.findViewById(R.id.line2);
        spf5kLine3 = storageSpf5kView.findViewById(R.id.line3);
        spf5kLine4 = storageSpf5kView.findViewById(R.id.line4);
    }
    //初始化mix头部
    private View mixHead;
    /*文本显示对象*/
    private TextView tvPvMix;
    private TextView tvBarPMix;
    private TextView tvBarSocMix;
    private TextView tvLoadMix;
    private TextView tvGridMix;
    private TextView tvMixTitle;
    private View frameNoteMix;

    private ImageView ivMainIcon;
    private ImageView ivRotateMix;
    private ImageView ivGridMix;
    private ImageView ivPvMix;
    private ImageView ivGrid2UserMix;
    private ImageView ivUser2GridMix;

    private LinearLayout llBatRightMix;
    private LinearLayout llBatLeftMix;
    private ImageView ivBarRightBig;
    private ImageView ivBarRightS;
    private ImageView ivBarLeftBig;
    private ImageView ivBarLeftS;

    private LinearLayout llHomeRightMix;
    private ImageView ivHomeRightS;
    private ImageView ivHomeRightB;

    private RotateAnimation mixRotate;
    private Animation mixPvAlpha;
    private Animation mix_alpha_reverse;
    private Animation mix_alpha_horiz;
    private void initStorageMixHeader() {
        mixHead= LayoutInflater.from(getActivity()).inflate(R.layout.header_storage_mix,mRecyclerView,false);
        View headerText = mixHead.findViewById(R.id.llMyDeviceText);
        mViewList.add(headerText);
        //显示的文本对象
        tvPvMix = (TextView) mixHead.findViewById(R.id.tvPvMix);
        tvBarPMix = (TextView) mixHead.findViewById(R.id.tvBarPMix);
        tvBarSocMix = (TextView) mixHead.findViewById(R.id.tvBarSocMix);
        tvLoadMix = (TextView) mixHead.findViewById(R.id.tvLoadMix);
        tvGridMix = (TextView) mixHead.findViewById(R.id.tvGridMix);
        tvMixTitle = (TextView) mixHead.findViewById(R.id.tvMixTitle);
        frameNoteMix =  mixHead.findViewById(R.id.frameNoteMix);

        //动画对象
        ivRotateMix = (ImageView) mixHead.findViewById(R.id.ivRotateMix);
        ivMainIcon = (ImageView) mixHead.findViewById(R.id.ivMainIcon);
        ivGridMix = (ImageView) mixHead.findViewById(R.id.ivGrid);
        ivPvMix = (ImageView) mixHead.findViewById(R.id.ivPvMix);
        ivGrid2UserMix = (ImageView) mixHead.findViewById(R.id.ivGrid2UserMix);


        //左
        llBatRightMix = (LinearLayout) mixHead.findViewById(R.id.llBatRightMix);
        llBatLeftMix = (LinearLayout) mixHead.findViewById(R.id.llBatLeftMix);
        ivUser2GridMix = (ImageView) mixHead.findViewById(R.id.ivUser2GridMix);
        ivBarRightBig = (ImageView) mixHead.findViewById(R.id.ivBarRightBig);
        ivBarRightS = (ImageView) mixHead.findViewById(R.id.ivBarRightS);
        ivBarLeftBig = (ImageView) mixHead.findViewById(R.id.ivBarLeftBig);
        ivBarLeftS = (ImageView) mixHead.findViewById(R.id.ivBarLeftS);
        //右边
        llHomeRightMix = (LinearLayout) mixHead.findViewById(R.id.llHomeRightMix);
        ivHomeRightS = (ImageView) mixHead.findViewById(R.id.ivHomeRightS);
        ivHomeRightB = (ImageView) mixHead.findViewById(R.id.ivHomeRightB);
        //开始动画
        mixPvAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.mix_alpha);
        mix_alpha_reverse = AnimationUtils.loadAnimation(getActivity(), R.anim.mix_alpha_reverse);
        mix_alpha_horiz = AnimationUtils.loadAnimation(getActivity(), R.anim.mix_alpha_horiz);
        mixRotate = (RotateAnimation)AnimationUtils.loadAnimation(getActivity(), R.anim.mix_rotate);
        //动态创建动画


        //设置匀速选择，xml中不起作用
        mixRotate.setInterpolator(new LinearInterpolator());
        ivRotateMix.startAnimation(mixRotate);
        //点击弹出详情
        frameNoteMix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyControl.textClick(frameNoteMix,MixUtil.mixStorageDetial(mixBean,getActivity()));
            }
        });
    }
    //显示储能头部spf2k和spf3k
    private void showStorageHeader(){
        if (deviceDType != 1) {
//            mAdapter.removeHeaderView(v);
            mAdapter.removeAllHeaderView();
            mAdapter.addHeaderView(storageView);
//			listview.addHeaderView(mixHead);
            deviceDType = 1;
        }
    }
    //显示Mix头部
    private void showMixHeader(){
        if (deviceDType != 3) {
//            mAdapter.removeHeaderView(v);
            mAdapter.removeAllHeaderView();
            mAdapter.addHeaderView(mixHead);
            deviceDType = 3;
        }
    }
    //显示储能头部spf2k和spf3k
    private void showStorageSpf5kHeader(){
        if (deviceDType != 2) {
//            mAdapter.removeHeaderView(v);
            mAdapter.removeAllHeaderView();
            mAdapter.addHeaderView(storageSpf5kView);
            deviceDType = 2;
        }
    }
    //	//显示储能头部spf2k和spf3k
//	private void showStorageSpf5kHeader(){
//		if (deviceDType != 2) {
//			listview.removeHeaderView(v);
//			listview.addHeaderView(storageSpf5kView);
//			deviceDType = 2;
//		}
//	}
	/*Mix部分*/
    //获取MIX系统状态
    private void getMixSysStatus() {
        if (!(deviceDType == 3)) return;
        PostUtil.post(new Urlsutil().postMixSystemStatus, new postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("plantId",Cons.plant);
                params.put("mixId", Fragment1Field.mixSn);
            }
            @Override
            public void success(String json) {
                try{
                    JSONObject jsonObject = new JSONObject(json);
                    int result = jsonObject.getInt("result");
                    if (result == 1){
                        Gson gson = new Gson();
                        mixBean = gson.fromJson(jsonObject.getJSONObject("obj").toString(), MixStatusBean.class);
                        initMixAnimAndUi(mixBean);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void LoginError(String str) {
            }
        });
    }

    /**
     * 初始化mix动画
     */
    private void initMixAnimAndUi(MixStatusBean mixBean) {
        ivRotateMix.startAnimation(mixRotate);
        MixUtil.clearAllAnim(ivPvMix,ivUser2GridMix,ivGrid2UserMix,ivBarRightBig,ivBarRightS,ivBarLeftBig,ivBarLeftS,ivHomeRightS,ivHomeRightB);
//        MyUtils.showAllView(ivGrid2UserMix,llBatRightMix);
        MyUtils.hideAllView(View.INVISIBLE,ivUser2GridMix,llBatLeftMix,ivGrid2UserMix,llBatRightMix,ivPvMix,ivHomeRightS,ivHomeRightB);
		/*测试数据*/
//		mixBean.setPpv(0);
//        mixBean.setpLocalLoad(0);
//		mixBean.setPactogrid(0);
//		mixBean.setPactouser(1);
//		mixBean.setChargePower(0);
//		mixBean.setPdisCharge1(1);
//        mixBean.setLost("lost");
        if (mixBean != null) {
            //解析动画
            //0标题
            String lost = mixBean.getLost();
            String mixTitle = "";
            String mixTitleColor = "";
            //优先级
            String priorityChoose = mixBean.getPriorityChoose();
            String priorValue = "";
            if ("0".equals(priorityChoose)){
                priorValue = getString(R.string.m208负载优先);
            }else if ("1".equals(priorityChoose)){
                priorValue = getString(R.string.m209电池优先);
            }else if ("2".equals(priorityChoose)){
                priorValue = getString(R.string.m210电网优先);
            }
            //离线
            if (!TextUtils.isEmpty(lost) && lost.contains("lost")){
                mixTitle = getString(R.string.不在线);
                mixTitleColor = mMixColors[2];
                GlideUtils.getInstance().showImageAct(getActivity(),R.drawable.mix_system_big_gray,R.drawable.mix_system_big_gray,R.drawable.mix_system_big_gray,ivMainIcon);
                GlideUtils.getInstance().showImageAct(getActivity(),R.drawable.mix_system_s_gray,R.drawable.mix_system_s_gray,R.drawable.mix_system_s_gray,ivRotateMix);
            }
            //故障
            else if (mixBean.getUwSysWorkMode() == 3){
                mixTitle = getString(R.string.m204SysFault模式) + "(" + priorValue + ")";
                mixTitleColor = mMixColors[1];
                GlideUtils.getInstance().showImageAct(getActivity(),R.drawable.mix_system_big_red,R.drawable.mix_system_big_red,R.drawable.mix_system_big_red,ivMainIcon);
                GlideUtils.getInstance().showImageAct(getActivity(),R.drawable.mix_system_s_red,R.drawable.mix_system_s_red,R.drawable.mix_system_s_red,ivRotateMix);
            //正常
            }else {
                mixTitleColor = mMixColors[0];
                mixTitle = MixUtil.getMixStatusText(mixBean.getUwSysWorkMode(),getActivity()) + "(" + priorValue + ")";
                GlideUtils.getInstance().showImageAct(getActivity(),R.drawable.mix_system_big_green,R.drawable.mix_system_big_green,R.drawable.mix_system_big_green,ivMainIcon);
                GlideUtils.getInstance().showImageAct(getActivity(),R.drawable.mix_system_s_green,R.drawable.mix_system_s_green,R.drawable.mix_system_s_green,ivRotateMix);
            }
            tvMixTitle.setText(MixUtil.setHtml(new HtmlBean(mixTitleColor,mixTitle)));
            //1.电池充放电
            if (mixBean.getChargePower() > 0) {//充电
                MyUtils.showAllView(llBatLeftMix);
                ivBarLeftS.startAnimation(mix_alpha_horiz);
                ivBarLeftBig.startAnimation(mix_alpha_reverse);
//                MyUtils.hideAllView(View.INVISIBLE,llBatRightMix);
                tvBarPMix.setText(MixUtil.setHtml(new HtmlBean(getString(R.string.m265充电功率),mMixColors[0],mixBean.getChargePower()+"","W")));
            } else if (mixBean.getPdisCharge1() > 0){//放电
                MyUtils.showAllView(llBatRightMix);
                ivBarRightBig.startAnimation(mix_alpha_horiz);
                ivBarRightS.startAnimation(mix_alpha_reverse);
                tvBarPMix.setText(MixUtil.setHtml(new HtmlBean(getString(R.string.m266放电功率),mMixColors[0],mixBean.getPdisCharge1()+"","W")));
            }else{
                tvBarPMix.setText(MixUtil.setHtml(new HtmlBean(getString(R.string.m265充电功率),mMixColors[0],"0.0","W")));
            }
                tvBarSocMix.setText(MixUtil.setHtml(new HtmlBean(getString(R.string.重复22),mMixColors[0],mixBean.getSOC()+"","%")));
            //2.pv功率
            if (ivPvMix != null) {
                if (mixBean.getPpv() > 0) {
                    MyUtils.showAllView(ivPvMix);
                    ivPvMix.startAnimation(mixPvAlpha);
                }
                tvPvMix.setText(MixUtil.setHtml(new HtmlBean(getString(R.string.m260光伏功率),mMixColors[0],mixBean.getPpv()+"","W")));
            }
            //3.用户用电
            if (mixBean.getpLocalLoad() > 0){
                MyUtils.showAllView(ivHomeRightS,ivHomeRightB);
                ivHomeRightS.startAnimation(mix_alpha_horiz);
                ivHomeRightB.startAnimation(mix_alpha_reverse);
            }
            tvLoadMix.setText(MixUtil.setHtml(new HtmlBean(getString(R.string.m211用电功率),mMixColors[3],mixBean.getpLocalLoad()+"","W")));
            //4.馈电功率和取电
            if (mixBean.getPactogrid() > 0){//到电网
                GlideUtils.getInstance().showImageAct(getActivity(),R.drawable.mix_grid_green,R.drawable.mix_grid_green,R.drawable.mix_grid_green,ivGridMix);
                MyUtils.showAllView(ivUser2GridMix);
                ivUser2GridMix.startAnimation(mixPvAlpha);
                tvGridMix.setText(MixUtil.setHtml(new HtmlBean(getString(R.string.m262馈电功率),mMixColors[0],mixBean.getPactogrid()+"","W")));
            }else if (mixBean.getPactouser()>0){//电网取电到用户
                GlideUtils.getInstance().showImageAct(getActivity(),R.drawable.mix_grid_red,R.drawable.mix_grid_red,R.drawable.mix_grid_red,ivGridMix);
                MyUtils.showAllView(ivGrid2UserMix);
                ivGrid2UserMix.startAnimation(mixPvAlpha);
                tvGridMix.setText(MixUtil.setHtml(new HtmlBean(getString(R.string.m261取电功率),mMixColors[1],mixBean.getPactouser()+"","W")));
            }else {
                GlideUtils.getInstance().showImageAct(getActivity(),R.drawable.mix_grid_green,R.drawable.mix_grid_green,R.drawable.mix_grid_green,ivGridMix);
                tvGridMix.setText(MixUtil.setHtml(new HtmlBean(getString(R.string.m262馈电功率),mMixColors[0],"0.0","W")));
            }

            //离线
            if (!TextUtils.isEmpty(lost) && lost.contains("lost")){
                MixUtil.clearAllAnim(ivPvMix,ivUser2GridMix,ivGrid2UserMix,ivBarRightBig,ivBarRightS,ivBarLeftBig,ivBarLeftS,ivHomeRightS,ivHomeRightB);
                MyUtils.hideAllView(View.INVISIBLE,ivUser2GridMix,llBatLeftMix,ivGrid2UserMix,llBatRightMix,ivPvMix,ivHomeRightS,ivHomeRightB);
            }
        }
    }


    //获取储能机状态
    private void getStorageStatus() {
        if (!(deviceDType == 1 || deviceDType==2)) return;
        PostUtil.post(new Urlsutil().postStorageSystemStatusData, new postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("plantId",Cons.plant);
                params.put("storageSn", Fragment1Field.sn);
            }

            @Override
            public void success(String json) {
                try{
                    //解析储能机json开始动画
                    parseStorageStatus(json);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void LoginError(String str) {

            }
        });

    }

    private void getStorageEnergy(final String plant, final String finalSn) {
        PostUtil.post(new Urlsutil().postEnergyProdAndConsData, new postListener() {
            @Override
            public void Params(Map<String, String> params) {
                params.put("plantId",plant);
                params.put("storageSn", finalSn);
                params.put("date", "2017-03-22");
                params.put("type", "0");
            }

            @Override
            public void success(String json) {

            }

            @Override
            public void LoginError(String str) {

            }
        });
    }

    //解析储能机状态
    private void parseStorageStatus(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if ("1".equals(jsonObject.get("result").toString())){
                String newJson = jsonObject.get("obj").toString();
                Gson gson = new Gson();
                bean = gson.fromJson(newJson,StorageStatusBean.class);
                deviceType = bean.getDeviceType();
                if ("2".equals(deviceType)) {//spf5k
                    setStorageSpf5kData(bean);
                    startStorageSpf5kAnim(bean);
                }else {//spf2k/3k
                    setStorageData(bean);
                    startStorageAnim(bean);
                }
            }else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setStorageData(StorageStatusBean bean) {
        //pv1 + pv2
        tvPv1.setText(String.valueOf(Arith.sub(Double.parseDouble(bean.getPpv1()),Double.parseDouble(bean.getPpv2()))));
        tvPv2.setText(bean.getPpv2());
        deviceType = bean.getDeviceType();
        if (Double.valueOf(bean.getpCharge())>0){
            tvPCharge.setText(bean.getpCharge());
        }else {
            tvPCharge.setText(bean.getpDisCharge());
        }
        if (Double.valueOf(bean.getPacToGrid()) > 0) {
            tvPacTo.setText(bean.getPacToGrid());
        }else {
            tvPacTo.setText(bean.getPacToUser());
        }
        tvPacCharge.setText(bean.getPacCharge());
        tvUserLoad.setText(bean.getUserLoad());
        tvCapacity.setText(bean.getCapacity()+"%");
    }
    private void setStorageSpf5kData(StorageStatusBean bean) {
        tvLine2.setText(bean.getPanelPower());
        tvLine3.setText(bean.getGridPower());
        tvLine4.setText(bean.getLoadPower());
        tvLine42.setText(bean.getApparentPower());
        //正负代表充放电
        String batPower = bean.getBatPower();
        try{
            if (!TextUtils.isEmpty(batPower) && batPower.startsWith("-")){
                batPower = batPower.substring(1);
            }
            tvLine5.setText(batPower);
        }catch (Exception e){
            e.printStackTrace();
            tvLine5.setText(batPower);
        }
        setStorageSpf5kStatus(bean.getStatus(),tvStorageStatus);
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.mm42)).append("(").append(bean.getCapacity()).append("%").append(")");
        tvCapacitySpf5k.setText(sb.toString());
    }

    /**
     * 设置储能机状态文本
     * @param status
     * @param tv
     */
    private void setStorageSpf5kStatus(String status,TextView tv) {
        int  resId = 0;
        switch (status){
            case "0":
                resId = R.string.all_Standby;
                break;
            case "1":
                resId = R.string.all_Charge;
                break;
            case "2":
                resId = R.string.all_Discharge;
                break;
            case "3":
                resId = R.string.all_Fault;
                break;
            case "4":
                resId = R.string.all_Lost;
                break;
            case "5":
                resId = R.string.m111PV充电;
                break;
            case "6":
                resId = R.string.m112AC充电;
                break;
            case "7":
                resId = R.string.m113联合充电模式;
                break;
            case "8":
                resId = R.string.m117联合充电和市电旁路带载;
                break;
            case "9":
                resId = R.string.m115PV充电和市电旁路带载;
                break;
            case "10":
                resId = R.string.m116AC充电和市电旁路带载;
                break;
            case "11":
                resId = R.string.m118市电旁路带载;
                break;
            case "12":
                resId = R.string.m114PV充电和电池带载;
                break;
        }
        tv.setText(resId);
    }

    /**
     * spf2000/3000动画
     * @param bean
     */
    private void startStorageAnim(StorageStatusBean bean) {
        isAnimation = true;
//		bean.setPacToUser("100");
//		bean.setPacToGrid("0");
//		bean.setStatus("1");
        if (Double.valueOf(bean.getPacToGrid()) > 0) {
            //走向从ppv1/ppv2--->储能机--->逆变器--->电网
            //-----------------------------逆变器--->用户负载
            startStorageAnim0();
        }else {
            //走向从ppv1/ppv2--->储能机--->逆变器--->用户负载
            startStorageAnim1();
        }

        if (Double.valueOf(bean.getPacToUser()) > 0 && "1".equals(bean.getStatus())) {
            //走向从电网--->用户负载
            //------电网--->储能机--->电池
            startStorageAnim2();
        }else if (Double.valueOf(bean.getPacToUser()) > 0 && "2".equals(bean.getStatus())){
            //走向从电网--->用户负载
            startStorageAnim3();
            //电池--->储能机（放电）
            startStorageAnim4();
        }else if (Double.valueOf(bean.getPacToUser()) > 0){
            //走向从电网--->用户负载
            startStorageAnim3();
        }else if ("2".equals(bean.getStatus())){
            //电池--->储能机（放电）
            startStorageAnim4();
        }else if ("1".equals(bean.getStatus())){
            //储能机--->电池（充电）
            startStorageAnim5();
        }
        //列表都不在线，移除动画
        if (allLost) {
            MixUtil.clearAllAnim(ivId1, ivId2, ivId3, ivId4);
        }
    }
    /**
     * spf5000动画
     * @param bean
     */
    private void startStorageSpf5kAnim(StorageStatusBean bean) {
        isAnimationSpf5k = true;
        String status = bean.getStatus();
        String batPower = bean.getBatPower();
        if (!TextUtils.isEmpty(batPower)){
            float batPowerF = Float.parseFloat(batPower);
            //放电
            if (batPowerF > 0){
                startStorageSpf5Anim2();
                //充电
            }else if (batPowerF < 0){
                startStorageSpf5Anim1();
            }
        }
//        //测试
////		status = "12";
//        //电池充电
//        if ("5".equals(status) || "6".equals(status) || "7".equals(status) || "12".equals(status) || "9".equals(status)|| "10".equals(status)|| "8".equals(status)){
//            startStorageSpf5Anim1();
//        }
//        //电池放电
//        if ( "2".equals(status)){
//            startStorageSpf5Anim2();
//        }
        int statusIn = Integer.parseInt(status);
        switch (statusIn){
            case 2://Discharge:储能机----》用户
                startStorageSpf5Anim3();
                break;
            case 5://PV charge:面板---》储能机
                startStorageSpf5Anim4();
                break;
            case 6://AC charge：电网---》储能机
                startStorageSpf5Anim5();
                break;
            case 7://Combine charge：面板/电网---》储能机
                startStorageSpf5Anim4();
                startStorageSpf5Anim5();
                break;
            case 8://Combine charge and Bypass：面板--》储能机；电网--》储能机--》用户
            case 9://PV charge and Bypass：面板--》储能机；电网--》储能机--》用户
                startStorageSpf5Anim4();
                startStorageSpf5Anim6();
                break;
            case 10://AC charge and Bypass：电网--》储能机---》用户
            case 11://Bypass：电网--》储能机--》用户
                startStorageSpf5Anim6();
                break;
            case 12://PV charge and Discharge：面板--》储能机---》用户
                startStorageSpf5Anim7();
                break;
        }
        //列表都不在线，移除动画
        if (allLost) {
            MixUtil.clearAllAnim(ivId1Spf5k, ivId2Spf5k, ivId3Spf5k);
        }
    }
    //电池充电
    private void startStorageSpf5Anim1() {
        MyControl.startStorageAnimation(getActivity(), ivId1Spf5k, spf5kLine4, 0, 1, 0, 1500, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                startStorageSpf5Anim1();
            }
        });
    }
    //电池放电
    private void startStorageSpf5Anim2() {
        MyControl.startStorageAnimation(getActivity(), ivId1Spf5k, spf5kLine4, 0, -1, 0, 1500, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                startStorageSpf5Anim2();
            }
        });
    }
    //储能机----》用户
    private void startStorageSpf5Anim3() {
        MyControl.startStorageAnimation(getActivity(), ivId2Spf5k, spf5kLine3, 1, 0, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                startStorageSpf5Anim3();
            }
        });
    }
    //面板---》储能机
    private void startStorageSpf5Anim4() {
        MyControl.startStorageAnimation(getActivity(), ivId2Spf5k, spf5kLine1, 1, 0, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                startStorageSpf5Anim4();
            }
        });
    }
    //面板--》储能机---》用户
    private void startStorageSpf5Anim7() {
        MyControl.startStorageAnimation(getActivity(), ivId2Spf5k, spf5kLine1, 1, 0, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                MyControl.startStorageAnimation(getActivity(), ivId2Spf5k, spf5kLine3, 1, 0, new OnAnimationEndLinster() {
                    @Override
                    public void endAnimation() {
                        startStorageSpf5Anim7();
                    }
                });
            }
        });
    }
    //电网---》储能机
    private void startStorageSpf5Anim5() {
        MyControl.startStorageAnimation(getActivity(), ivId3Spf5k, spf5kLine2, 1, 0, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                startStorageSpf5Anim5();
            }
        });
    }
    //电网--》储能机--》用户
    private void startStorageSpf5Anim6() {
        MyControl.startStorageAnimation(getActivity(), ivId3Spf5k, spf5kLine2, 1, 0, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                MyControl.startStorageAnimation(getActivity(), ivId3Spf5k, spf5kLine3, 1, 0, new OnAnimationEndLinster() {
                    @Override
                    public void endAnimation() {
                        startStorageSpf5Anim6();
                    }
                });
            }
        });
    }
    private void startStorageAnim5() {
        MyControl.startStorageAnimation(getActivity(), ivId3, line4, 0, 1, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                startStorageAnim5();
            }
        });
    }
    private void startStorageAnim4() {
        MyControl.startStorageAnimation(getActivity(), ivId3, line4, 0, -1, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                startStorageAnim4();
            }
        });
    }

    private void startStorageAnim3() {
        MyControl.startStorageAnimation(getActivity(), ivId4, line3, -1, 0, -1, storageTime, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                MyControl.startStorageAnimation(getActivity(), ivId4, line10, 0, 1, new OnAnimationEndLinster() {
                    @Override
                    public void endAnimation() {
                        startStorageAnim3();
                    }
                });
            }
        });
    }
    private void startStorageAnim22() {
        MyControl.startStorageAnimation(getActivity(), ivId3, line3, -1, 0, -1, storageTime, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                MyControl.startStorageAnimation(getActivity(), ivId3, line10, 0, 1, new OnAnimationEndLinster() {
                    @Override
                    public void endAnimation() {
                        startStorageAnim22();
                    }
                });
            }
        });
    }
    private void startStorageAnim2() {
        MyControl.startStorageAnimation(getActivity(), ivId3, line3, -1, 0, -1, storageTime, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                MyControl.startStorageAnimation(getActivity(), ivId3, line8, 0, 1, 0, storageTime, new OnAnimationEndLinster() {
                    @Override
                    public void endAnimation() {
                        MyControl.startStorageAnimation(getActivity(), ivId4, line9, 0, 1, 0, storageTime, new OnAnimationEndLinster() {
                            @Override
                            public void endAnimation() {
                            }
                        });
                        MyControl.startStorageAnimation(getActivity(), ivId3, line7, -1, 0,4*storageTime, new OnAnimationEndLinster() {
                            @Override
                            public void endAnimation() {
                                MyControl.startStorageAnimation(getActivity(), ivId3, line6, 0, -1, storageTime, new OnAnimationEndLinster() {
                                    @Override
                                    public void endAnimation() {
                                        MyControl.startStorageAnimation(getActivity(), ivId3, line5, -1, 0, storageTime, new OnAnimationEndLinster() {
                                            @Override
                                            public void endAnimation() {
                                                MyControl.startStorageAnimation(getActivity(), ivId3, line4, 0, 1, new OnAnimationEndLinster() {
                                                    @Override
                                                    public void endAnimation() {
                                                        startStorageAnim2();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
    private void startStorageAnim0() {
        if (!isAnimation) return;
//		MyControl.startStorageAnimation(getActivity(), ivId2, line1, 1, 0, new OnAnimationEndLinster() {
//			@Override
//			public void endAnimation() {
//			}
//		});
        MyControl.startStorageAnimation(getActivity(), ivId1, line0, 1, 0, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                MyControl.startStorageAnimation(getActivity(), ivId1, line2, 1, 0, new OnAnimationEndLinster() {
                    @Override
                    public void endAnimation() {
                        MyControl.startStorageAnimation(getActivity(), ivId1, line3, 1, 0,1, storageTime, new OnAnimationEndLinster() {
                            @Override
                            public void endAnimation() {
                                MyControl.startStorageAnimation(getActivity(), ivId2, line3, 1, 0,-1, storageTime, new OnAnimationEndLinster() {
                                    @Override
                                    public void endAnimation() {
                                    }
                                });
                                MyControl.startStorageAnimation(getActivity(), ivId1, line10, 0, 1, new OnAnimationEndLinster() {
                                    @Override
                                    public void endAnimation() {
                                        startStorageAnim0();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
    private void startStorageAnim1() {
        if (!isAnimation) return;
//		MyControl.startStorageAnimation(getActivity(), ivId2, line1, 1, 0, new OnAnimationEndLinster() {
//			@Override
//			public void endAnimation() {
//			}
//		});
        MyControl.startStorageAnimation(getActivity(), ivId1, line0, 1, 0, new OnAnimationEndLinster() {
            @Override
            public void endAnimation() {
                MyControl.startStorageAnimation(getActivity(), ivId1, line2, 1, 0, new OnAnimationEndLinster() {
                    @Override
                    public void endAnimation() {
                        MyControl.startStorageAnimation(getActivity(), ivId1, line3, 1, 0,1, storageTime, new OnAnimationEndLinster() {
                            @Override
                            public void endAnimation() {
                                MyControl.startStorageAnimation(getActivity(), ivId1, line10, 0, 1, new OnAnimationEndLinster() {
                                    @Override
                                    public void endAnimation() {
                                        startStorageAnim1();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    //显示逆变器头部
    private void showInvHeader(){
        if (deviceDType != 0) {
            mAdapter.removeAllHeaderView();
            mAdapter.addHeaderView(v);
            deviceDType = 0;
        }
    }
    private void initFloatView() {
        floatHeader = view.findViewById(R.id.floatHeader);
        tv11=(TextView)view.findViewById(R.id.textView1);
        tv21=(TextView)view.findViewById(R.id.textView2);
        tv31=(TextView)view.findViewById(R.id.textView3);
        tv41=(TextView)view.findViewById(R.id.textView4);
        tv51=(TextView)view.findViewById(R.id.textView5);
        tv61=(TextView)view.findViewById(R.id.textView6);
        tv71=(TextView)view.findViewById(R.id.textView7);
        tv81=(TextView)view.findViewById(R.id.textView8);
        tv91=(TextView)view.findViewById(R.id.textView9);

    }

    private void getAPPMessage() {
        GetUtil.get(new Urlsutil().getAPPMessage, new GetListener() {

            @Override
            public void success(String json) {
                try {
                    JSONObject jsonObj=new JSONObject(json);
                    String result = jsonObj.opt("result").toString();
                    if("1".equals(result)){
                        Object opt = jsonObj.opt("obj");
                        if(opt!=null){
//								JSONArray jsonArray = jsonObj.getJSONArray("obj");
//								for(int i=0;i<jsonArray.length();i++){
//									JSONObject jsonObj2 = jsonArray.getJSONObject(i);
//									app_code=jsonObj2.getInt("code");
//									picurl=jsonObj2.getString("picurl");
//								}
                            JSONObject jsonObject = jsonObj.getJSONObject("obj");
                            app_code=jsonObject.getInt("code");
                            picurl=jsonObject.getString("picurl");
                            if(getLanguage()!=0){
                                picurl=jsonObject.getString("enpicurl");
                            }
                            showAppCodeWindow();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String json) {

            }
        });
    }

    protected void showAppCodeWindow() {

        // 一锟斤拷锟皆讹拷锟斤拷牟锟斤拷郑锟斤拷锟轿拷锟绞撅拷锟斤拷锟斤拷锟�
        View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.item_app_code_dialog, null);
        ImageView ivPic=(ImageView) contentView.findViewById(R.id.picture_dialog);
        ImageView ivCancel=(ImageView) contentView.findViewById(R.id.cancel);
        ImageHttp.ImageLoader(ivPic, Urlsutil.getInstance().getProductImageInfo+picurl);
        Builder builderAlert = new Builder(getActivity(),R.style.MyDialogStyle);
        builderAlert.setView(contentView);
        final AlertDialog dialog=builderAlert.create();
        dialog.show();
        ivCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void registerBroadCast() {
        receiver=new MyReceiver();
        filter = new IntentFilter();
        filter.addAction(Constant.Frag_Receiver);
        filter.addAction("intent.action.Message_My");
        filter.setPriority(1000);
        //注锟斤拷悴�
        if(receiver!=null){
            getActivity().registerReceiver(receiver, filter);
        }

    }

    @Override
    public void onDestroyView() {
        if(receiver!=null){
            getActivity().unregisterReceiver(receiver);
        }
        super.onDestroyView();
    }
    private void SetViews() {
        Fragment1Field.sn = "";
        v=getActivity().getLayoutInflater().inflate(R.layout.header_swipmenulistview4, mRecyclerView,false);
        View headerText = v.findViewById(R.id.llMyDeviceText);
        mViewList.add(headerText);
        ivEtPlant=(ImageView) view.findViewById(R.id.imageView1);
        ivXiala=(ImageView) view.findViewById(R.id.xiala);
        llBackOss = (LinearLayout) view.findViewById(R.id.llBackOss);
        if (Constant.isOss2Server){
            MyUtils.showAllView(llBackOss);
        }
        operatingAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_fragment1_head);
        trasAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.tran_frg1_head);
        operatingAnim.setInterpolator(new LinearInterpolator());
        //锟角凤拷锟揭伙拷谓锟斤拷锟斤拷锟揭筹拷锟�
        if(Cons.isFirst){
            Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.xiala_trans);
            ivXiala.startAnimation(anim);
            anim.setAnimationListener(new AnimationListener() {

                @Override
                public void onAnimationStart(Animation arg0) {
                    ivXiala.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation arg0) {

                }

                @Override
                public void onAnimationEnd(Animation arg0) {
                    ivXiala.setVisibility(View.GONE);
                    Cons.isFirst=false;
                }
            });
        }
        if("2".equals(Cons.userBean.getRightlevel())||Cons.userBean.getParentUserId()>0){
            Cons.isflag=true;
        }else{
            Cons.isflag=false;

        }
        tv1=(TextView)v.findViewById(R.id.textView1);
        tv3=(TextView)v.findViewById(R.id.textView3);
        tv4=(TextView)v.findViewById(R.id.textView4);
        tv6=(TextView)v.findViewById(R.id.textView6);
        tv7=(TextView)v.findViewById(R.id.textView7);
        tv9=(TextView)v.findViewById(R.id.textView9);
        tvTotalPower=(TextView)v.findViewById(R.id.tvTotalPower);
        tvTotalEnergy=(TextView)v.findViewById(R.id.tvTotalEnergy);
        tvTotalRevenue=(TextView)v.findViewById(R.id.tvTotalRevenue);
        ll_Image=(LinearLayout)v.findViewById(R.id.ll_Image);//头图片
        ll_Circle=(LinearLayout)v.findViewById(R.id.ll_Circle);
        circle1=v.findViewById(R.id.circle1);
        circle2=v.findViewById(R.id.circle2);
        circle3=v.findViewById(R.id.circle3);

        rl4=(LinearLayout) v.findViewById(R.id.r4);
        //新增储能头部
        initStorageHeader();
        initStorageSpf5kHeader();
        initStorageMixHeader();
        mAdapter.addHeaderView(v);
//新增脚部
//		initFooter();
        initFooter2();
        add=(ImageView)view.findViewById(R.id.imageView2);
        title=(TextView)view.findViewById(R.id.title);
    }
    /*初始化脚部*/
//    public void initFooter(){
//        listFooter = LayoutInflater.from(getActivity()).inflate(R.layout.item_frag1v2_footer,mRecyclerView,false);
//        fl_demo1 = listFooter.findViewById(R.id.fl_demo1);
//        fl_demo2 = listFooter.findViewById(R.id.fl_demo2);
//        fl_demo3 = listFooter.findViewById(R.id.fl_demo3);
//        fl_demo4 = listFooter.findViewById(R.id.fl_demo4);
//        View[] footViews = new View[]{fl_demo1,fl_demo4,fl_demo2,fl_demo3};
//        int[] titleIds = new int[]{R.string.all_interver,R.string.all_storage,R.string.功率调节器,R.string.充电桩};
//        int[] imageIds = new int[]{R.drawable.frg1_inverter,R.drawable.frg1_storage,R.drawable.power_regulator,R.drawable.ev_charger};
//        String[] paramsTest = new String[]{"50kWh","100%","50kWh","100%"};
//        for (int i = 0,len = footViews.length;i<len;i++){
//            View footView = footViews[i];
//            ImageView footIv = (ImageView) footView.findViewById(R.id.ivIcon);
//            TextView tvDeviceSn = (TextView) footView.findViewById(R.id.tvDeviceSn);
//            TextView tvDeviceStatus = (TextView) footView.findViewById(R.id.tvDeviceStatus);
//            TextView tvParam1 = (TextView) footView.findViewById(R.id.tvParam1);
//            TextView tvParam2 = (TextView) footView.findViewById(R.id.tvParam2);
//            tvDeviceSn.setText(titleIds[i]);
//            tvDeviceStatus.setText(R.string.未连接);
//            tvDeviceStatus.setTextColor(ContextCompat.getColor(getActivity(),R.color.note2_bg_white));
//            tvParam1.setText("5000W");
//            tvParam2.setText(paramsTest[i]);
//            GlideUtils.getInstance().showImageAct(getActivity(),imageIds[i],imageIds[i],imageIds[i],footIv);
//        }
//        mAdapter.addFooterView(listFooter);
//    }
    /*初始化脚部*/
    public void initFooter2() {
        listFooter = LayoutInflater.from(getActivity()).inflate(R.layout.footer_fragment1v2, (ViewGroup) mRecyclerView.getParent(), false);
        ImageView ivAddDatalog = (ImageView) listFooter.findViewById(R.id.ivAddDatalog);
        Button btnDatalogList = (Button) listFooter.findViewById(R.id.btnDatalogList);
        ivAddDatalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDatalog();
            }
        });
        btnDatalogList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpDatalogList();
            }
        });
        mAdapter.addFooterView(listFooter);
    }
    private void jumpDatalogList() {
        if (!TextUtils.isEmpty(Cons.plant)) {
            Intent intent = new Intent(getActivity(), DataloggersActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", Cons.plant);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            T.make(R.string.fragment1_nothing, getActivity());
        }
    }
    private void addDatalog() {
        if (Cons.isflag) {
            T.make(R.string.all_experience, getActivity());
            return;
        }
        if (addTypes == null) {
            addTypes = new ArrayList<>();
            addTypes.add(getString(R.string.all_twodimension));
            addTypes.add(getString(R.string.ScanAct_manually));
        }
        new CircleDialog.Builder(getActivity())
                .setTitle(getString(R.string.Register_add_collector))
                .configItems(new ConfigItems() {
                    @Override
                    public void onConfig(ItemsParams params) {
                        params.textColor = ContextCompat.getColor(getActivity(), R.color.title_bg_white);
                    }
                })
                .setItems(addTypes, new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                adddatalogTwoDim();
                                break;
                            case 1:
                                adddatalogSlecter();
                                break;
                        }
                    }
                })
                .setNegative(getString(R.string.all_no), null)
                .show();
    }

    public void refresh(){
//        Mydialog.Show(getActivity());
        refresh(-1);
    }
    public void refresh(final int pos) {
        if (mContext == null){
            mContext = getActivity();
        }
        String refPlant = "";
        if (pos == -1){
            refPlant = Cons.plant;
        }else {
            refPlant = Cons.plants.get(pos).get("plantId").toString();
        }
        // 锟斤拷锟斤拷刷锟铰诧拷锟斤拷
        if (TextUtils.isEmpty(refPlant)){
            Mydialog.Dismiss();
            if(swipeRefresh!=null){
                swipeRefresh.setRefreshing(false);
            }
            if(ll_Circle!=null&&ll_Circle.getVisibility()==View.VISIBLE){
                ll_Circle.setVisibility(View.INVISIBLE);
            }
            if (operatingAnim != null && circle1 != null && operatingAnim.hasStarted()) {
                circle1.startAnimation(operatingAnim);
                circle2.startAnimation(operatingAnim);
                circle3.startAnimation(operatingAnim);
            }
            return;
        }
        GetUtil.get1(new Urlsutil().questionAPI+"&plantId="+refPlant+"&pageNum=1"+"&pageSize="+"100", new GetListener() {

            @Override
            public void success(String json) {
//                MyUtils.showAllView(fl_demo1,fl_demo4);
                Mydialog.Dismiss();
                //刷新成功后更新数据
                Fragment1Field.sn = null;
                Fragment1Field.mixSn = null;
                Fragment1Field.isMix = false;
                if (pos != -1){
                    positions=pos;
                    Cons.plant = plant=Cons.plants.get(pos).get("plantId").toString();
                    title.setText(Cons.plants.get(pos).get("plantName").toString());
                }
                try {
                    Mydialog.Dismiss();
                    if(mAdapter!=null){
                        mAdapter.notifyDataSetChanged();
                    }
                    if(swipeRefresh!=null){
                        swipeRefresh.setRefreshing(false);
                    }
                    if(ll_Circle!=null&&ll_Circle.getVisibility()==View.VISIBLE){
                        ll_Circle.setVisibility(View.INVISIBLE);
                    }
                    if (operatingAnim != null && circle1 != null && operatingAnim.hasStarted()) {
                        circle1.startAnimation(operatingAnim);
                        circle2.startAnimation(operatingAnim);
                        circle3.startAnimation(operatingAnim);
                    }
                    Gson gson = new Gson();
                    DeviceTotalBean refreshBean = gson.fromJson(json,DeviceTotalBean.class);
                    String plantMoneyText = refreshBean.getPlantMoneyText();
                    String todayEnergy = refreshBean.getTodayEnergy();
                    String invTodayPpv = refreshBean.getInvTodayPpv();
                    int  nominalPower = refreshBean.getNominal_Power();
                    String totalEnergy = refreshBean.getTotalEnergy();
                    String totalMoneyText = refreshBean.getTotalMoneyText();
                    //总收益
                    String[] moneyTotals = totalMoneyText.split("/");
                    if (moneyTotals == null){
                        tvTotalRevenue.setText(totalMoneyText);
                    }else if (moneyTotals.length == 1){
                        tvTotalRevenue.setText(moneyTotals[0]);
                    }else if (moneyTotals.length > 1){
                        MyUtils.setTextView2TextSize(mContext,moneyTotals[0] + "",moneyTotals[1],tvTotalRevenue);
                    }else {
                        tvTotalRevenue.setText(totalMoneyText);
                    }
                    //单位
                    String unitStr = "";
                    double totalD = Double.parseDouble(nominalPower + "");
                    if (nominalPower < 10*1000){
                        unitStr = "W";
                        MyUtils.setTextView2TextSize(mContext,nominalPower + "",unitStr,tvTotalPower);
                    }else {
                        totalD = divide(totalD, 1000, 2);
                        unitStr = "kW";
                        MyUtils.setTextView2TextSize(mContext,totalD + "",unitStr,tvTotalPower);
                    }
                    totalD = Double.parseDouble(totalEnergy);
                    if (totalD < 10*1000){
                        unitStr = "kWh";
//											tvTotalEnergy.setText(totalEnergy + "kWh");
                        MyUtils.setTextView2TextSize(mContext,totalEnergy + "",unitStr,tvTotalEnergy);
                    }else {
                        unitStr = "MWh";
                        totalD = divide(totalD, 1000, 2);
//											tvTotalEnergy.setText(totalD + "MWh");
                        MyUtils.setTextView2TextSize(mContext,totalD + "",unitStr,tvTotalEnergy);
                    }
                    //当日收益
                    String[] moneyTodays = plantMoneyText.split("/");
                    if (moneyTodays == null){
                        tv1.setText(plantMoneyText);
                    }else if (moneyTodays.length == 1){
                        tv1.setText(moneyTodays[0]);
                    }else if (moneyTodays.length > 1){
                        MyUtils.setTextView2TextSize(mContext,moneyTodays[0] + "",moneyTodays[1],tv1);
                    }else {
                        tv1.setText(plantMoneyText);
                    }
                    double d=Double.parseDouble(invTodayPpv);
                    if(d>=10000){
                        unitStr = "kW";
                        d=divide(d, 1000, 2);
                        MyUtils.setTextView2TextSize(mContext,d + "",unitStr,tv4);
                    }else{
                        unitStr = "W";
                        MyUtils.setTextView2TextSize(mContext,invTodayPpv + "",unitStr,tv4);
                    }
                    d=Double.parseDouble(todayEnergy);
                    if(d>=10000){
                        unitStr = "MWh";
                        d=divide(d, 1000, 2);
                        MyUtils.setTextView2TextSize(mContext,d + "",unitStr,tv7);
                    }else{
                        unitStr = "kWh";
                        MyUtils.setTextView2TextSize(mContext,todayEnergy + "",unitStr,tv7);
                    }
                    List<Fragment1ListBean> deviceList = refreshBean.getDeviceList();
                    //查询本地数据库中当前电站id集合数据
                    List<Fragment1ListBean> topList = SqliteUtil.inquiryidsByPlant(Cons.plant);
                    if (deviceList != null && deviceList.size()>0){
                        //有设备
                        boolean hasMix = false;
                        boolean hasInverter = false;
                        boolean hasStorageSpf5k = false;
                        int spf5kPos = -1;
                        boolean hasStorage = false;
                        allLost = true;
                        for (int i=0,size=deviceList.size();i<size;i++){
                            Fragment1ListBean bean = deviceList.get(i);
                            //设置所有设备是否断开
                            allLost = allLost && bean.isLost();
                            //设置置顶所需数据
                            if (topList != null && topList.size()>0){
                                for (Fragment1ListBean topBean:topList) {
                                    if (bean.getDeviceSn().equals(topBean.getDeviceSn())){
                                        bean.setTimeId(topBean.getTimeId());
                                        bean.setPlantId(topBean.getPlantId());
                                        bean.setUserId(topBean.getUserId());
                                    }
                                }
                            }

                            String deviceType = bean.getDeviceType();
                            if ("mix".equals(deviceType)){
                                Fragment1Field.mixSn = bean.getDeviceSn();
                                Fragment1Field.isMix = true;
                                hasMix = true;
                            }else if ("storage".equals(deviceType)){
                                hasStorage = true;
                                Fragment1Field.sn = bean.getDeviceSn();
                                if (bean.getStorageType() == 2) {//spf5000
                                    hasStorageSpf5k = true;
                                    spf5kPos = i;
                                }
                            }else if ("inverter".equals(deviceType)){
                                hasInverter = true;
                            }
                        }
                        if (hasMix){
                            showMixHeader();
                            //获取mix系统状态
                            getMixSysStatus();
                        }else if (hasStorage){
                            //隐藏储能机demo
//                            MyUtils.hideAllView(View.GONE,fl_demo4);
                            if (hasStorageSpf5k){
                                showStorageSpf5kHeader();
                                if (spf5kPos != -1){
                                    Fragment1Field.sn = deviceList.get(spf5kPos).getDeviceSn();
                                }
                            }else {
                                showStorageHeader();
                            }
                            //获取储能机状态
                            getStorageStatus();
                        }else if (hasInverter){
                            showInvHeader();
                            //隐藏逆变器demo
//                            MyUtils.hideAllView(View.GONE,fl_demo1);
                        }else {
                            showInvHeader();
                        }
                        Collections.sort(deviceList, new MyFragmentV1Top());
                        MyUtils.hideAllView(View.GONE, listFooter);
                        MyUtils.showAllView(mViewList.toArray(new View[0]));
                    }else {
                        MyUtils.showAllView(listFooter);
                        MyUtils.hideAllView(View.GONE, mViewList.toArray(new View[0]));
                        //无设备
                        showInvHeader();
//                        MyUtils.showAllView(fl_demo1,fl_demo4);
                        T.make(R.string.fragment1_nothing,getActivity());
                    }
                    mAdapter.setNewData(deviceList);
                } catch (Exception e) {
                    Mydialog.Dismiss();
                    e.printStackTrace();
                }
            }
            @Override
            public void error(String json) {
                Mydialog.Dismiss();
                if(swipeRefresh!=null){
                    swipeRefresh.setRefreshing(false);
                }
                if(ll_Circle!=null&&ll_Circle.getVisibility()==View.VISIBLE){
                    ll_Circle.setVisibility(View.INVISIBLE);
                }
                if (operatingAnim != null && circle1 != null && operatingAnim.hasStarted()) {
                    circle1.startAnimation(operatingAnim);
                    circle2.startAnimation(operatingAnim);
                    circle3.startAnimation(operatingAnim);
                }
            }
        });
    }


    private void SetListeners(){
        //新增demo 设备点击
//        fl_demo1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), DemoDeviceActivity.class);
//                intent.putExtra(Constant.Device_Type,Constant.Device_Inv);
//                jumpTo(intent,false);
//            }
//        });
//        fl_demo2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), DemoDeviceActivity.class);
//                intent.putExtra(Constant.Device_Type,Constant.Device_Power);
//                jumpTo(intent,false);
//            }
//        });
//        fl_demo3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), DemoDeviceActivity.class);
//                intent.putExtra(Constant.Device_Type,Constant.Device_Charge);
//                jumpTo(intent,false);
//            }
//        });
//        fl_demo4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), DemoDeviceActivity.class);
//                intent.putExtra(Constant.Device_Type,Constant.Device_storage);
//                jumpTo(intent,false);
//            }
//        });
        llBackOss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除Oss登录到server标志
                Constant.isOss2Server = false;
//                LogoutUtil.oss2ServerLogout(getActivity());
            }
        });
        //新增文本点击事件3.25
        MyUtilsTotal.mulitTextViewClick(tvTotalPower,tvTotalEnergy,tvTotalRevenue,tv1,tv4,tv7);
        title.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getplantname(arg0);
            }
        });
        ivEtPlant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getplantname(arg0);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showPopupWindow(arg0);
            }
        });




        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        System.out.println("屏幕宽度="+width);
        LayoutParams pp = rl4.getLayoutParams();
        swipeRefresh.getLayoutParams();
        pp.height =(int) Math.ceil(width*5/8.0);
        rl4.setLayoutParams(pp);
        //设置悬浮view高度
        LayoutParams lpFloat = floatHeader.getLayoutParams();
        lpFloat.height = (int)(width*0.24);
        floatHeader.setLayoutParams(lpFloat);
        if(Cons.plants.size()>0){
            plant = Cons.plant = Cons.plants.get(0).get("plantId").toString();
            refresh();
            title.setText(Cons.plants.get(0).get("plantName").toString());
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                if (firstItem !=0 ){
                    mPreFirst = firstItem;
                }
                if (mPreFirst != 0 && firstItem == 0 ){
                    mPreFirst = 0;
                    //移回屏幕后重新开始动画
                    switch (deviceDType){
                        case 1:
                            startStorageAnim(bean);
                        case 2:
                            startStorageSpf5kAnim(bean);
                            break;
                        case 3:
                            initMixAnimAndUi(mixBean);
                            break;
                    }
                }
            }
        });
    }

    private ArrayList<Map<String, Object>> lists;
    private EditText et;
    private ImageView image;


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(this.getView()!=null){
            this.getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
        }
    }
    public void deletePattern(final View view, final int position) {

        AnimationListener al = new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //                mDBHelper.deleteCustomPattern(mPatternList.get(position));
                //                list.remove(position);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        collapse(view, al);

    }

    private void collapse(final View view, AnimationListener al) {
        final int originHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1.0f) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = originHeight - (int) (originHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        if (al != null) {
            animation.setAnimationListener(al);
        }
        animation.setDuration(300);
        view.startAnimation(animation);
    }

    private void showPopupWindow(View view) {

        // 一锟斤拷锟皆讹拷锟斤拷牟锟斤拷郑锟斤拷锟轿拷锟绞撅拷锟斤拷锟斤拷锟�
        View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.pop_window, null);
        // 锟斤拷锟矫帮拷钮锟侥碉拷锟斤拷录锟�1
        TextView button1 = (TextView) contentView.findViewById(R.id.bt1);
        TextView button2 = (TextView) contentView.findViewById(R.id.bt2);
        TextView button3 = (TextView) contentView.findViewById(R.id.bt3);
        final PopupWindow popupWindow = new PopupWindow(contentView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if (addTypes == null){
                    addTypes = new ArrayList<>();
                    addTypes.add(getString(R.string.all_twodimension));
                    addTypes.add(getString(R.string.ScanAct_manually));
                }
                new CircleDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Register_add_collector))
                        .configItems(new ConfigItems() {
                            @Override
                            public void onConfig(ItemsParams params) {
                                params.textColor = ContextCompat.getColor(getActivity(),R.color.title_bg_white);
                            }
                        })
                        .setItems(addTypes, new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                switch (position){
                                    case 0:
                                        adddatalogTwoDim();
                                        break;
                                    case 1:
                                        adddatalogSlecter();
                                        break;
                                }
                            }
                        })
                        .setNegative(getString(R.string.all_no),null)
                        .show();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                if(Cons.plants.size()>0){
                    Intent intent=new Intent(getActivity(),DataloggersActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("id", Cons.plants.get(positions).get("plantId").toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    T.make(R.string.fragment1_nothing,getActivity());
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                startActivity(new Intent(getActivity(), AddPlantActivity.class));
            }
        });

        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

        // 锟斤拷锟斤拷锟斤拷锟斤拷锟絇opupWindow锟侥憋拷锟斤拷锟斤拷锟斤拷锟斤拷锟角碉拷锟斤拷獠匡拷锟斤拷锟斤拷锟紹ack锟斤拷锟斤拷锟睫凤拷dismiss锟斤拷锟斤拷
        // 锟揭撅拷锟斤拷锟斤拷锟斤拷锟斤拷API锟斤拷一锟斤拷bug
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        popupWindow.setAnimationStyle(R.style.Popup_Anim);
        // 锟斤拷锟矫好诧拷锟斤拷之锟斤拷锟斤拷show
        popupWindow.showAsDropDown(view, 0, 5);

    }

    private void adddatalogSlecter() {
        final View v=LayoutInflater.from(getActivity()).inflate(R.layout.retrievepwd, null);
        AlertDialog dialog=new Builder(getActivity()).setTitle(R.string.retrievepwd_putin_num)
                .setView(v).setPositiveButton(R.string.all_ok, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        final EditText et1=(EditText)v.findViewById(R.id.editText1);
//                        final EditText et2=(EditText)v.findViewById(R.id.editText2);
//                        if(et1.getText().toString().equals("")||et2.getText().toString().equals("")){
//                            T.make(R.string.all_blank,getActivity());
//                            return;
//                        }
                        if(TextUtils.isEmpty(et1.getText().toString())){
                            T.make(R.string.all_blank,getActivity());
                            return;
                        }
                        arg0.dismiss();
                        Mydialog.Show(getActivity(), "");
                        PostUtil.post(new Urlsutil().addDatalog, new postListener() {

                            @Override
                            public void success(String json) {
                                Mydialog.Dismiss();
                                try {
                                    JSONObject jsonObject=new JSONObject(json);
                                    if(jsonObject.get("success").toString().equals("true")){
                                        String str=jsonObject.get("msg").toString();
                                        if(str.equals("200")){
                                            T.make(R.string.all_success_reminder,getActivity());
                                            String url=new Urlsutil().getDatalogInfo+"&datalogSn="+et1.getText().toString().trim();
                                            //TODO 锟斤拷映晒锟斤拷锟窖拷杉锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟角凤拷锟斤拷锟斤拷锟斤拷
                                            //getDataLogInfo(url);
                                            //锟街讹拷锟斤拷硬杉锟斤拷锟绞憋拷锟斤拷锟絯ifi锟斤拷锟斤拷
                                            AddToSetWifi(et1.getText().toString().trim(), jsonObject);
                                        }
                                    }else{
                                        String msg=jsonObject.get("msg").toString();
                                        if(msg.equals("501")){
                                            T.make(R.string.serviceerror,getActivity());

                                        }else if(msg.equals("502")){
                                            T.make(R.string.dataloggers_add_no_jurisdiction,getActivity());

                                        }else if(msg.equals("503")){
                                            T.make(R.string.dataloggers_add_no_number,getActivity());

                                        }else if(msg.equals("504")){
                                            T.make(R.string.dataloggers_add_no_v,getActivity());

                                        }else if(msg.equals("505")){
                                            T.make(R.string.dataloggers_add_no_full,getActivity());

                                        }else if(msg.equals("506")){
                                            T.make(R.string.dataloggers_add_no_exist,getActivity());

                                        }else if(msg.equals("507")){
                                            T.make(R.string.dataloggers_add_no_matching,getActivity());
                                        }else if("701".equals(msg)){
                                            T.make(R.string.m7,getActivity());
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }



                            @Override
                            public void Params(Map<String, String> params) {
                                params.put("plantId", Cons.plant);
                                params.put("datalogSN", et1.getText().toString().trim());
//                                params.put("validCode", et2.getText().toString().trim());
                                params.put("validCode", "");
                            }

                            @Override
                            public void LoginError(String str) {

                            }
                        });
                    }
                }).setNegativeButton(R.string.all_no, new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                }).create();
        dialog.show();
    }

    private void adddatalogTwoDim() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), MipcaActivityCapture.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, 105);
    }

    String id="";
    private String alis;
    public void selectdialog(final int position){
        Builder builder = new  Builder(getActivity());
        String a=getResources().getString(R.string.fragment1_item1);
        String b=getResources().getString(R.string.fragment1_item2);
        builder.setTitle(R.string.putin_problem_item1).setItems(new String[]{a,b}, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                dialog.dismiss();
                if(Cons.isflag==true){
                    T.make(R.string.all_experience,getActivity());
                    return;
                }
                switch (index) {
                    case 1:
                        dialog1(getActivity(), position);
                        break;
                    case 0:
                        Builder builder = new Builder(getActivity());
                        builder.setTitle(R.string.all_prompt).setMessage(R.string.fragment1_isrelieve).setPositiveButton(R.string.all_ok, new OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                final Fragment1ListBean bean = mAdapter.getItem(position);
                                //设置url
                                String url="";
                                if("inverter".equals(bean.getDeviceType())){
                                    if ("1".equals(bean.getInvType())){
                                        url = new Urlsutil().postDeletemax;
                                        id="maxId";
                                    }else if ("2".equals(bean.getInvType())){
                                        url = new Urlsutil().postDelete_jlinv;
                                        id="jlinvId";
                                    }else {
                                        url = new Urlsutil().deleteInverter;
                                        id="inverterId";
                                    }
                                }
                                else if ("mix".equals(bean.getDeviceType())){
                                    url=new Urlsutil().postDeleteMixAPI;
                                    id="mixId";
                                }
                                else{
                                    url=new Urlsutil().deleteStorage;
                                    id="storageId";
                                }
                                Mydialog.Show(getActivity());
                                PostUtil.post(url, new postListener() {
                                    @Override
                                    public void success(String json) {
                                        try {
                                            JSONObject jsonObject=new JSONObject(json);
                                            String s=jsonObject.optString("msg");
                                            if(s.equals("200")){
                                                T.make(R.string.all_success,getActivity());
                                                dialog.dismiss();
                                                mAdapter.remove(position);
//                                                refresh();
                                            }
                                            if(s.equals("501")){
                                                T.make(R.string.all_failed,getActivity());
                                            }
                                            if(s.equals("502")){
                                                T.make(R.string.m7,getActivity());
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    @Override
                                    public void Params(Map<String, String> params) {
                                        params.put(id, bean.getDeviceSn());
                                    }
                                    @Override
                                    public void LoginError(String str) {
                                    }
                                });
                            }
                        }).setNegativeButton(R.string.all_no, new OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                        builder.show();
                        break;

                    default:
                        break;
                }
            }
        }).setPositiveButton(R.string.all_no, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                dialog.dismiss();
            }
        }).create();
        builder.show();
    }
    public void dialog1(Context context,final int position){
        //设置Map请求参数实体
        final Fragment1ListBean bean = mAdapter.getItem(position);
        Builder builder = new Builder(getActivity());
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  view=inflater.inflate(R.layout.modifyordelete, null);
        builder.setView(view).setTitle(R.string.fragment1_alias).setPositiveButton(R.string.all_ok, new OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
                alis=et.getText().toString().trim();
                bean.setDeviceAilas(alis);
                Map<String,Object> maps = FragV2Util.getDeviceInfoMap(bean);
                Mydialog.Show(getActivity(), "");
                //设置url
                String url="";
                if("inverter".equals(bean.getDeviceType())){
                    if ("1".equals(bean.getInvType())){
                        url = new Urlsutil().postUpdateMaxInfo;
                    }else if ("2".equals(bean.getInvType())){
                        url = new Urlsutil().postUpdateInfo_jlinv;
                    }else {
                        url = new Urlsutil().updateInvInfo;
                    }
                }
                else if ("mix".equals(bean.getDeviceType())){
                    url=new Urlsutil().postUpdateMixInfoAPI;
                }
                else{
                    url=new Urlsutil().updateStorageInfo;
                }
                AddCQ.postUp(url, maps, new GetListener() {
                    @Override
                    public void success(String res) {
                        if(!TextUtils.isEmpty(res)){
                            Message msg=new Message();
                            msg.what=0;
                            msg.obj=res;
                            handler.sendMessage(msg);
                        }else{
                            Message msg=new Message();
                            msg.what=1;
                            handler.sendMessage(msg);
                        }
                    }
                    @Override
                    public void error(String json) {
                    }
                });
            }
        }).setNegativeButton(R.string.all_no, new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        }).create();
        et=(EditText)view.findViewById(R.id.editText1);
        et.setText(bean.getDeviceAilas());
        et.setSelection(bean.getDeviceAilas().length());
        et.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int editStart ;
            private int editEnd ;
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                temp=s;
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                editStart = et.getSelectionStart();
                editEnd = et.getSelectionEnd();
                if (temp.length() > 20) {
                    s.delete(editStart-1, editEnd);
                    int tempSelection = editStart;
                    et.setText(s);
                    et.setSelection(tempSelection);
                }
            }
        });
        alis=et.getText().toString().trim();
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 105:
                if(data!=null){
                    Bundle bundle=data.getExtras();
                    final String s=bundle.getString("result");
                    System.out.println("result="+s);
                    Mydialog.Show(getActivity(), "");
                    PostUtil.post(new Urlsutil().addDatalog, new postListener() {

                        @Override
                        public void success(String json) {
                            Log.i("tag", json);
                            try {
                                Mydialog.Dismiss();
                                JSONObject jsonObject=new JSONObject(json);
                                String msg=jsonObject.get("msg").toString();
                                if(msg.equals("200")){
                                    T.make(R.string.all_success_reminder,getActivity());
                                    String url=new Urlsutil().getDatalogInfo+"&datalogSn="+s;
                                    //getDataLogInfo(url);

                                    //锟斤拷映晒锟斤拷锟斤拷锟阶拷锟斤拷锟斤拷锟絯ifi锟侥斤拷锟斤拷
                                    AddToSetWifi(s, jsonObject);
                                }else if(msg.equals("501")){
                                    T.make(R.string.serviceerror,getActivity());

                                }else if(msg.equals("502")){
                                    T.make(R.string.dataloggers_add_no_jurisdiction,getActivity());

                                }else if(msg.equals("503")){
                                    T.make(R.string.dataloggers_add_no_number,getActivity());

                                }else if(msg.equals("504")){
                                    T.make(R.string.dataloggers_add_no_v,getActivity());

                                }else if(msg.equals("505")){
                                    T.make(R.string.dataloggers_add_no_full,getActivity());

                                }else if(msg.equals("506")){
                                    T.make(R.string.dataloggers_add_no_exist,getActivity());

                                }else if(msg.equals("507")){
                                    T.make(R.string.dataloggers_add_no_matching,getActivity());
                                }else if("701".equals(msg)){
                                    T.make(R.string.m7,getActivity());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void Params(Map<String, String> params) {
                            params.put("plantId", Cons.plant);
                            params.put("datalogSN", s);
//                            params.put("validCode", AppUtils.validateWebbox(s));
                            params.put("validCode", "");
                        }
                        @Override
                        public void LoginError(String str) {

                        }
                    });
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     *
     * @param sn 锟缴硷拷锟斤拷锟斤拷锟叫猴拷
     * @param jsonObject
     * @throws JSONException
     */
    public void AddToSetWifi(final String sn,
                             JSONObject jsonObject) throws JSONException {
        String type=jsonObject.get("datalogType").toString();
        MyUtils.configWifi(getActivity(),type,"2",sn);
    }

    private PopupWindow popup;
    public void getplantname(View v){
        View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_frg1v2_recyclerview, null);
        ListView lv=(ListView)contentView.findViewById(R.id.listView1);
        View flCon = contentView.findViewById(R.id.frameLayout);
        flCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popup != null){
                    popup.dismiss();
                }
            }
        });
        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {
                if(positions!=position){
                    Mydialog.Show(getActivity());
                    refresh(position);
                }
                popup.dismiss();
            }
        });
        spadapter=new FragspinnerAdapter(getActivity(), Cons.plants,false,0);
        lv.setAdapter(spadapter);
        popup = new PopupWindow(contentView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        popup.setTouchable(true);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popup.setTouchInterceptor(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
                // 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟絫rue锟侥伙拷锟斤拷touch锟铰硷拷锟斤拷锟斤拷锟斤拷锟斤拷
                // 锟斤拷锟截猴拷 PopupWindow锟斤拷onTouchEvent锟斤拷锟斤拷锟斤拷锟矫ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷獠匡拷锟斤拷锟斤拷薹锟絛ismiss
            }
        });
        popup.setBackgroundDrawable(new ColorDrawable(0));
        popup.setAnimationStyle(R.style.Popup_Anim);
        // 锟斤拷锟矫好诧拷锟斤拷之锟斤拷锟斤拷show
        popup.showAsDropDown(v);
    }

    /**
     * @param v1
     * @param v2
     * @param scale 锟皆斤拷锟斤拷锟斤拷锟斤拷锟轿恍★拷锟�
     * @return
     */
    public  double divide(double v1, int v2,int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(v2+"");
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(Constant.Frag_Receiver.equals(intent.getAction())){
                Mydialog.Show(getActivity(), "");
                refresh();
            }
            if("intent.action.Message_My".equals(intent.getAction())){
                String id=intent.getStringExtra("id");
                Intent i = new Intent(getActivity(), UserdataActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", id);
                getActivity().startActivity(i);
                getActivity().removeStickyBroadcast(intent);

            }
        }

    }

    /**
     * item点击事件
     * @param adapter
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter == mAdapter){
            Fragment1ListBean item = mAdapter.getItem(position);
            String deviceType = item.getDeviceType();
            Intent intent = null;
            if(("inverter").equals(deviceType)){
                intent=new Intent(mContext,InverterActivity.class);
                String invType = item.getInvType();
                //Max
                if ("1".equals(invType)) {
                    intent.putExtra("invType", 1);
                }else if ("2".equals(invType)){
                    intent.putExtra("invType", 2);
                }else {
                    intent.putExtra("invType", 0);
                }
            }else if("storage" .equals(deviceType)){
                if (item.getStorageType() == 2){
                    intent=new Intent(mContext,StorageSp5kActivity.class);
                }else {
                    intent=new Intent(mContext,StorageActivity.class);
                }
            }else if ("mix".equals(deviceType)){
//                intent=new Intent(mContext,MixMainActivity.class);
            }
            if (intent != null) {
                Bundle bundle = new Bundle();
                bundle.putString("deviceAilas", item.getDeviceAilas());
                bundle.putString("id", item.getDeviceSn());
                bundle.putString("datalogSn", item.getDatalogSn());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        }
    }


}
