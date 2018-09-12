package com.growatt.shinephone.fragment;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.AddPlantActivity;
import com.growatt.shinephone.activity.DataloggersActivity;
import com.growatt.shinephone.activity.DemoDeviceActivity;
import com.growatt.shinephone.activity.InverterActivity;
import com.growatt.shinephone.activity.MipcaActivityCapture;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.activity.StorageActivity;
import com.growatt.shinephone.activity.StorageSp5kActivity;
import com.growatt.shinephone.activity.UserdataActivity;
import com.growatt.shinephone.adapter.FragspinnerAdapter;
import com.growatt.shinephone.bean.DeviceTotalBean;
import com.growatt.shinephone.bean.StorageStatusBean;
import com.growatt.shinephone.bean.mix.MixStatusBean;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnAnimationEndLinster;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.AddCQ;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.FileUtils;
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
import com.growatt.shinephone.util.UpdateManager;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.util.mix.MixUtil;
import com.growatt.shinephone.util.v2.Fragment1Field;
import com.growatt.shinephone.view.Solve7PopupWindow;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigItems;
import com.mylhyl.circledialog.params.ItemsParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.growatt.shinephone.util.Cons.userId;
import static com.growatt.shinephone.util.v2.Fragment1Field.sn;


public class fragment1 extends BaseFragment{
	private View view;
	View v;
	private ListView listview;
	private SwipeAdapter adapter;
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
						lists.get(Clickpositions).put("deviceAilas", alis);
						adapter=new SwipeAdapter(getActivity(), lists);
						listview.setAdapter(adapter);
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
//			case 102:
//				getDataLogInfo(FragUtil.dataLogUrl);
//			   break;
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
	private View footerView;
	private ListView footListView;
	private SwipeAdapter footAdapter;
	private List<Map<String, Object>> footList;
	private UpdateManager up;
	protected int app_code;
	protected String picurl;
//	private ImageView ivRotate1;
//	private ImageView ivRotate2;
//	private ImageView ivRotate3;
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
	private View fl_demo1;
	private View fl_demo2;
	private View fl_demo3;
	private View fl_demo4;
	private List<String> addTypes;//采集器添加的方式
	private Context mContext;
    /*mix部分*/
    private MixStatusBean mixBean = new MixStatusBean();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment1, container, false);
		frgIntent=new Intent(Constant.Frag_Receiver);
		initFloatView();
		SetViews();
		initSwipeRefresh(view);
		SetListeners();
		registerBroadCast();
		MyUtils.checkUpdate(getActivity(),Constant.LoginActivity_Updata);
		if(Cons.isCodeUpdate){
			getAPPMessage();
		}
//		initTest();
		return view;
	}

	private void initTest() {
		GetUtil.get("http://192.168.3.214:8081/ShineServer_2016/newInverterAPI.do?op=getInverterParams_max&maxId=INVERSNERR", new GetListener() {
			@Override
			public void error(String json) {

			}

			@Override
			public void success(String json) {
				LogUtil.i("逆变器测试1：" + json);
			}
		});
		GetUtil.get("http://192.168.3.214:8081/ShineServer_2016/newInverterAPI.do?op=getInverterDetailData_max&maxId=INVERSNERR", new GetListener() {
			@Override
			public void error(String json) {

			}

			@Override
			public void success(String json) {
				LogUtil.i("逆变器测试2：" + json);
			}
		});
	}

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
		storageView = LayoutInflater.from(getActivity()).inflate(R.layout.header_frag2_storage,listview,false);
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
		storageSpf5kView= LayoutInflater.from(getActivity()).inflate(R.layout.header_storage_sp5k,listview,false);
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

	private ImageView ivRotateMix;
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

	private Animation mixRotate;
	private Animation mixPvAlpha;
	private Animation mix_alpha_reverse;
	private Animation mix_alpha_horiz;
	private void initStorageMixHeader() {
		mixHead= LayoutInflater.from(getActivity()).inflate(R.layout.header_storage_mix,listview,false);
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
		mixRotate = AnimationUtils.loadAnimation(getActivity(), R.anim.mix_rotate);
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
			listview.removeHeaderView(v);
			listview.addHeaderView(storageView);
//			listview.addHeaderView(mixHead);
			deviceDType = 1;
		}
	}
	//显示Mix头部
	private void showMixHeader(){
		if (deviceDType != 3) {
			listview.removeHeaderView(v);
			listview.addHeaderView(mixHead);
			deviceDType = 3;
		}
	}
	//显示储能头部spf2k和spf3k
	private void showStorageSpf5kHeader(){
		if (deviceDType != 2) {
			listview.removeHeaderView(v);
			listview.addHeaderView(storageSpf5kView);
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
		MyUtils.showAllView(ivGrid2UserMix,llBatRightMix);
		MyUtils.hideAllView(View.INVISIBLE,ivUser2GridMix,llBatLeftMix);
		/*测试数据*/
//		mixBean.setPpv(3);
//		mixBean.setPactogrid(3);
//		mixBean.setPactouser(0);
//		mixBean.setChargePower(3);
//		mixBean.setPdisCharge1(0);
		if (mixBean != null) {
			//解析动画
			//0标题
			String mixTitle = MixUtil.getMixStatusText(mixBean.getStatus(),getActivity());
			tvMixTitle.setText(mixTitle);
			//1.电池充放电
			if (mixBean.getChargePower() > 0) {//充电
				MyUtils.showAllView(llBatLeftMix);
				ivBarLeftS.startAnimation(mix_alpha_horiz);
				ivBarLeftBig.startAnimation(mix_alpha_reverse);
				MyUtils.hideAllView(View.INVISIBLE,llBatRightMix);
				tvBarPMix.setText(MixUtil.toHtml(new StringBuilder()
						.append(getString(R.string.storagedps_list1_item1))
						.append(":")
						.append("<font color='#55A24E'>")
						.append(mixBean.getChargePower())
						.append("</font>")
						.append("W").toString())
				);
			} else if (mixBean.getPdisCharge1() > 0){//放电
				ivBarRightBig.startAnimation(mix_alpha_horiz);
				ivBarRightS.startAnimation(mix_alpha_reverse);
				tvBarPMix.setText(MixUtil.toHtml(new StringBuilder()
						.append(getString(R.string.storagedps_list1_item2))
						.append(":")
						.append("<font color='#55A24E'>")
						.append(mixBean.getPdisCharge1())
						.append("</font>")
						.append("W").toString())
				);
			}else{
				tvBarPMix.setText(MixUtil.toHtml(new StringBuilder()
						.append(getString(R.string.storagedps_list1_item1))
						.append(":")
						.append("<font color='#55A24E'>")
						.append("0.0")
						.append("</font>")
						.append("W").toString()));
			}
			tvBarSocMix.setText(MixUtil.toHtml(new StringBuilder()
					.append(getString(R.string.m49))
					.append("<font color='#55A24E'>")
					.append(mixBean.getSOC())
					.append("</font>")
					.append("%").toString())
			);
			//2.pv功率
			if (ivPvMix != null) {
				if (mixBean.getPpv() > 0) {
					ivPvMix.startAnimation(mixPvAlpha);
				}
				tvPvMix.setText(MixUtil.toHtml(new StringBuilder()
								.append(getString(R.string.inverterdps_pv))
								.append(":")
								.append("<font color='#55A24E'>")
								.append(mixBean.getPpv())
								.append("</font>")
								.append("W").toString()
						)
				);
			}
			//3.用户用电
			if (mixBean.getpLocalLoad() > 0){
				ivHomeRightS.startAnimation(mix_alpha_horiz);
				ivHomeRightB.startAnimation(mix_alpha_reverse);
			}
			tvLoadMix.setText(MixUtil.toHtml(new StringBuilder()
					.append(getString(R.string.m211用电功率))
					.append(":")
					.append("<font color='#B1A660'>")
					.append(mixBean.getpLocalLoad())
					.append("</font>")
					.append("W").toString())
			);
			//4.电网馈电和取电
			if (mixBean.getPactogrid() > 0){//到电网
				MyUtils.showAllView(ivUser2GridMix);
				ivUser2GridMix.startAnimation(mixPvAlpha);
				MyUtils.hideAllView(View.INVISIBLE,ivGrid2UserMix);
				tvGridMix.setText(MixUtil.toHtml(new StringBuilder()
						.append(getString(R.string.m213馈回电网))
						.append(":")
						.append("<font color='#B17070'>")
						.append(mixBean.getPactogrid())
						.append("</font>")
						.append("W").toString())
				);
			}else if (mixBean.getPactouser()>0){//电网取电到用户
				ivGrid2UserMix.startAnimation(mixPvAlpha);
				tvGridMix.setText(MixUtil.toHtml(new StringBuilder()
						.append(getString(R.string.m212并网功率))
						.append(":")
						.append("<font color='#B17070'>")
						.append(mixBean.getPactouser())
						.append("</font>")
						.append("W").toString())
				);
			}else {
				tvGridMix.setText(MixUtil.toHtml(new StringBuilder()
						.append(getString(R.string.m213馈回电网))
						.append(":")
						.append("<font color='#B17070'>")
						.append("0.0")
						.append("</font>")
						.append("W").toString())
				);
			}
		}
	}


	//获取储能机状态
	private void getStorageStatus() {
		if (!(deviceDType == 1 || deviceDType==2)) return;
//		String sn = "";
		int length = lists.size();
		for (int i = 0;i<length;i++){
			if (lists.get(i).get("deviceType").toString().equals("storage")){
				sn = lists.get(i).get("deviceSn").toString();
				break;
			}
		}
		final String finalSn = sn;
		//测试获取储能机能源
//		getStorageEnergy(Cons.plant,finalSn);
		PostUtil.post(new Urlsutil().postStorageSystemStatusData, new postListener() {
			@Override
			public void Params(Map<String, String> params) {
				params.put("plantId",Cons.plant);
				params.put("storageSn", finalSn);
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
				Fragment1Field.deviceType = bean.getDeviceType();
				if ("2".equals(Fragment1Field.deviceType)) {//spf5k
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
		tvPv1.setText(bean.getPpv1());
		tvPv2.setText(bean.getPpv2());
		Fragment1Field.deviceType = bean.getDeviceType();
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
	}
	/**
	 * spf5000动画
	 * @param bean
	 */
	private void startStorageSpf5kAnim(StorageStatusBean bean) {
		isAnimationSpf5k = true;
		String status = bean.getStatus();
		//测试
//		status = "12";
		//电池充电
		if ("5".equals(status) || "6".equals(status) || "7".equals(status) || "12".equals(status) || "9".equals(status)|| "10".equals(status)|| "8".equals(status)){
			startStorageSpf5Anim1();
		}
		//电池放电
		if ( "2".equals(status)){
			startStorageSpf5Anim2();
		}
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
			listview.removeHeaderView(storageView);
			listview.removeHeaderView(storageSpf5kView);
			listview.removeHeaderView(mixHead);
			listview.addHeaderView(v);
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
		sn = "";
		v=getActivity().getLayoutInflater().inflate(R.layout.header_swipmenulistview4, listview,false);
		ivEtPlant=(ImageView) view.findViewById(R.id.imageView1);
		ivXiala=(ImageView) view.findViewById(R.id.xiala);
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
//		tv2=(TextView)v.findViewById(R.id.textView2);
//		tv5=(TextView)v.findViewById(R.id.textView5);
//		tv8=(TextView)v.findViewById(R.id.textView8);

		tvTotalPower=(TextView)v.findViewById(R.id.tvTotalPower);
		tvTotalEnergy=(TextView)v.findViewById(R.id.tvTotalEnergy);
		tvTotalRevenue=(TextView)v.findViewById(R.id.tvTotalRevenue);

//		floatView=(LinearLayout)v.findViewById(R.id.r4s);
		ll_Image=(LinearLayout)v.findViewById(R.id.ll_Image);//头图片
		ll_Circle=(LinearLayout)v.findViewById(R.id.ll_Circle);
		circle1=v.findViewById(R.id.circle1);
		circle2=v.findViewById(R.id.circle2);
		circle3=v.findViewById(R.id.circle3);
		listview=(ListView)view.findViewById(R.id.ptr_refreshListview);
		listview.setDivider(new ColorDrawable(Color.BLACK));
		listview.setDividerHeight(0);
		rl4=(LinearLayout) v.findViewById(R.id.r4);
		//新增储能头部
		initStorageHeader();
		initStorageSpf5kHeader();
		initStorageMixHeader();
		listview.addHeaderView(v);
//新增脚部
		/*初始化脚部*/
		listFooter = LayoutInflater.from(getActivity()).inflate(R.layout.item_frag1_footer,listview,false);
		 fl_demo1 = listFooter.findViewById(R.id.fl_demo1);
		 fl_demo2 = listFooter.findViewById(R.id.fl_demo2);
		 fl_demo3 = listFooter.findViewById(R.id.fl_demo3);
		 fl_demo4 = listFooter.findViewById(R.id.fl_demo4);
		listview.addFooterView(listFooter);
				lists=new ArrayList<Map<String,Object>>();
				adapter=new SwipeAdapter(getActivity(), lists);
				listview.setAdapter(adapter);
				
		add=(ImageView)view.findViewById(R.id.imageView2);
		title=(TextView)view.findViewById(R.id.title);
		
		
	}
   

	public void refresh(){
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
									MyUtils.showAllView(fl_demo1,fl_demo4);
									//刷新成功后更新数据
									sn = null;
									Fragment1Field.mixSn = null;
									Fragment1Field.isMix = false;
									if (pos != -1){
										positions=pos;
										Cons.plant = plant=Cons.plants.get(pos).get("plantId").toString();
//										SqliteUtil.plant(plant);
										title.setText(Cons.plants.get(pos).get("plantName").toString());
									}
									try {
										Mydialog.Dismiss();
										//通知锟斤拷锟斤拷锟斤拷锟捷硷拷锟窖撅拷锟侥变，锟斤拷锟斤拷锟斤拷锟酵ㄖ拷锟斤拷锟矫达拷锟斤拷锟斤拷锟剿拷锟絤ListItems锟侥硷拷锟斤拷
										if(adapter!=null){
										adapter.notifyDataSetChanged();  
										}
										// Call onRefreshComplete when the list has been refreshed.  
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
										if(lists==null){
											lists=new ArrayList<Map<String,Object>>();
											}
										lists.clear();
										JSONObject jsonObject2=new JSONObject(json);
										Gson gson = new Gson();
										DeviceTotalBean refreshBean = gson.fromJson(json,DeviceTotalBean.class);
										JSONArray jsonArray=jsonObject2.getJSONArray("deviceList");
										List<Map<String, Object>> sss = SqliteUtil.inquiryids(Cons.plant);
										String plantMoneyText=jsonObject2.get("plantMoneyText").toString();
										String todayDischarge=jsonObject2.get("todayDischarge").toString();
										String useEnergy=jsonObject2.get("useEnergy").toString();
										String todayEnergy=jsonObject2.get("todayEnergy").toString();
										String invTodayPpv=jsonObject2.get("invTodayPpv").toString();
										String storagePuser=jsonObject2.get("storagePuser").toString();
										String storagePgrid=jsonObject2.get("storagePgrid").toString();
										String storageTodayPpv=jsonObject2.get("storageTodayPpv").toString();
										int  nominalPower = refreshBean.getNominalPower();
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
//											tvTotalPower.setText(nominalPower + "W");
											MyUtils.setTextView2TextSize(mContext,nominalPower + "",unitStr,tvTotalPower);
										}else {
											totalD = divide(totalD, 1000, 2);
											unitStr = "kW";
//											tvTotalPower.setText(totalD + "kW");
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
//												tv4.setText(d+"kW");
												MyUtils.setTextView2TextSize(mContext,d + "",unitStr,tv4);
											}else{
												unitStr = "W";
//												tv4.setText(invTodayPpv + "W");
												MyUtils.setTextView2TextSize(mContext,invTodayPpv + "",unitStr,tv4);
											}
											d=Double.parseDouble(todayEnergy);
											if(d>=10000){
												unitStr = "MWh";
												d=divide(d, 1000, 2);
//												tv7.setText(d+"MWh");
												MyUtils.setTextView2TextSize(mContext,d + "",unitStr,tv7);
											}else{
												unitStr = "kWh";
//												tv7.setText(todayEnergy + "kWh");
												MyUtils.setTextView2TextSize(mContext,todayEnergy + "",unitStr,tv7);
											}
										int length = jsonArray.length();
										for (int i = 0; i < length; i++) {
											JSONObject jsonObject=jsonArray.getJSONObject(i);
											String deviceType = jsonObject.get("deviceType").toString();
											String deviceSn = jsonObject.get("deviceSn").toString();
											Map<String, Object> map=new HashMap<String, Object>();
											map.put("deviceAilas", jsonObject.get("deviceAilas").toString());
											map.put("deviceType", deviceType);
											map.put("deviceSn", deviceSn);
											map.put("deviceStatus", jsonObject.opt("deviceStatus"));
											map.put("energy", jsonObject.get("energy").toString());
											map.put("datalogSn", jsonObject.get("datalogSn").toString());
//											map.put("lost", jsonObject.get("lost").toString());
											if(!jsonObject.isNull("power")){
												//隐藏逆变器demo
												MyUtils.hideAllView(View.GONE,fl_demo1);
												map.put("eToday", jsonObject.get("eToday").toString());
												map.put("power", jsonObject.get("power").toString());
												map.put("invType",jsonObject.optString("invType"));
											}
											if(!jsonObject.isNull("capacity")){
												//隐藏储能机demo
												MyUtils.hideAllView(View.GONE,fl_demo4);
												map.put("pCharge", jsonObject.get("pCharge").toString());
												map.put("capacity", jsonObject.get("capacity").toString());
												map.put("storageType",jsonObject.optInt("storageType"));
											}
											//判断是否有mix机器
											if (!Fragment1Field.isMix && "mix".equals(deviceType)){
												Fragment1Field.mixSn = deviceSn;
												Fragment1Field.isMix = true;
											}
											map.put("plant", Cons.plant);
											String id=""+System.currentTimeMillis();
											id=id.substring(3, id.length()-2);
											map.put("id", id);
												for (int j = 0; j < sss.size(); j++) {
													Map<String, Object> m=sss.get(j);
													if(m.get("deviceSn").toString().equals(jsonObject.get("deviceSn").toString())){
														map.put("id",m.get("id").toString());
														break;
													}
												}
												map.put("userId", userId);
											SqliteUtil.ids(map);
											lists.add(map);
										}
										//显示头部
										showInvHeader();
										if (Fragment1Field.isMix){
											showMixHeader();
										}else {
											for (int i = 0; i < length; i++) {
												JSONObject jsonObject = jsonArray.getJSONObject(i);
												if (!jsonObject.isNull("capacity")) {
													if (jsonObject.optInt("storageType") == 2) {//spf5000
														showStorageSpf5kHeader();
													} else {
														showStorageHeader();
													}
													break;
												}
											}
										}
										if(jsonArray.length()>0){
											Collections.sort(lists, new MapComparatorDesc());
											adapter=new SwipeAdapter(getActivity(), lists);
											listview.setAdapter(adapter);
											//获取储能机状态
											getStorageStatus();
											//获取mix系统状态
											getMixSysStatus();

											ListAdapter adapter = listview.getAdapter();
											if (adapter == null) {
												return; 
											}
											View item = adapter.getView(0, null, listview);
											item.measure(0, 0);
											height=item.getMeasuredHeight();
										}else{
											LogUtil.i("没有设备："+ jsonArray.toString());
											showInvHeader();
											MyUtils.showAllView(fl_demo1,fl_demo4);
											T.make(R.string.fragment1_nothing,getActivity());
											adapter=new SwipeAdapter(getActivity(), lists);
											listview.setAdapter(adapter);
										}
										
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
		fl_demo1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), DemoDeviceActivity.class);
				intent.putExtra(Constant.Device_Type,Constant.Device_Inv);
				jumpTo(intent,false);
			}
		});
		fl_demo2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), DemoDeviceActivity.class);
				intent.putExtra(Constant.Device_Type,Constant.Device_Power);
				jumpTo(intent,false);
			}
		});
		fl_demo3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), DemoDeviceActivity.class);
				intent.putExtra(Constant.Device_Type,Constant.Device_Charge);
				jumpTo(intent,false);
			}
		});
		fl_demo4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), DemoDeviceActivity.class);
				intent.putExtra(Constant.Device_Type,Constant.Device_storage);
				jumpTo(intent,false);
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
		//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷幕锟斤拷锟斤拷
//		setRelativeLayout(width,(int) Math.ceil(width*5/8.0));
		
//		plant=SqliteUtil.inquiryplant();
//		if(plant.equals("")){
			if(Cons.plants.size()>0){
				plant = Cons.plant = Cons.plants.get(0).get("plantId").toString();
//			Getplant(Cons.plant);
				refresh();
			title.setText(Cons.plants.get(0).get("plantName").toString());
			}
//
//		}else{
//			if(Cons.plants.size()>0){
//
//				for (int i = 0; i < Cons.plants.size(); i++) {
//					if(Cons.plants.get(i).get("plantId").toString().equals(plant)){
//						title.setText(Cons.plants.get(i).get("plantName").toString());
//						positions=i;
//						break;
//					}
//				}
//				if(TextUtils.isEmpty(title.getText().toString())){
//					title.setText(Cons.plants.get(0).get("plantName").toString());
//				}
//				Cons.plant = plant;
////				Getplant(plant);
//				refresh();
//
//			}
//		}
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
//							final EditText et2=(EditText)v.findViewById(R.id.editText2);
//							if(et1.getText().toString().equals("")||et2.getText().toString().equals("")){
//								T.make(R.string.all_blank,getActivity());
//								return;
//							}
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
//									params.put("validCode", et2.getText().toString().trim());
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
							String url = "";
							if( lists.get(position).get("deviceType").toString().equals("inverter")){
								url=new Urlsutil().deleteInverter;
								id="inverterId";
							}else if( lists.get(position).get("deviceType").toString().equals("storage")){
								url=new Urlsutil().deleteStorage;
								id="storageId";
							}
							PostUtil.post(url, new postListener() {

								@Override
								public void success(String json) {
									try {
										JSONObject jsonObject=new JSONObject(json);
										String s=jsonObject.optString("msg");
										if(s.equals("200")){
											T.make(R.string.all_success,getActivity());
//											deletePattern(listview.getChildAt(position), position);
											dialog.dismiss();
											refresh();
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
									params.put(id, lists.get(position).get("deviceSn").toString());
								}
								@Override
								public void LoginError(String str) {
									
								}
							});
							dialog.dismiss();
							Mydialog.Show(getActivity(), "");
		                     refresh();
						}
					}).setNegativeButton(R.string.all_no, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Mydialog.Show(getActivity(), "");
		                     refresh();
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
				Mydialog.Show(getActivity(), "");
                refresh();
			}
		}).create();
		builder.show();
	}
	public void dialog1(Context context,final int position){
		Builder builder = new Builder(getActivity());
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View  view=inflater.inflate(R.layout.modifyordelete, null); 
		builder.setView(view).setTitle(R.string.fragment1_alias).setPositiveButton(R.string.all_ok, new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
				alis=et.getText().toString().trim();
				maps=setmap(lists.get(position).get("deviceType").toString(),alis, "", lists.get(position).get("deviceSn").toString());
				Mydialog.Show(getActivity(), "");
				String url="";
				if("inverter".equals(lists.get(Clickpositions).get("deviceType").toString())){
					url=new Urlsutil().updateInvInfo;
				}
				else if ("mix".equals(lists.get(Clickpositions).get("deviceType").toString())){
					url=new Urlsutil().postUpdateMixInfoAPI;
				}
				else{
					url=new Urlsutil().updateStorageInfo;
				}
				
				AddCQ.postUp(url, maps, new GetListener() {
					
					@Override
					public void success(String res) {
						if(res!=null){
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
				Mydialog.Show(getActivity(), "");
                refresh();
			}
		}).create();
		image=(ImageView)view.findViewById(R.id.imageView1);
		if(lists.get(position).get("deviceType").toString().equals("inverter")){
			image.setImageResource(R.drawable.nibianqi);
		}else if(lists.get(position).get("deviceType").toString().equals("storage")){
			image.setImageResource(R.drawable.chunengji);
		}else{

		}
		et=(EditText)view.findViewById(R.id.editText1);
		et.setText(lists.get(position).get("deviceAilas").toString());
		et.setSelection(lists.get(position).get("deviceAilas").toString().length());
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

				if (temp.length() > 10) {  
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
	public void newphoto() {
		Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷蘸锟斤拷锟狡拷拇锟斤拷锟铰凤拷锟�
		cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/"+ShineApplication.IMAGE_FILE_LOCATION)));
		startActivityForResult(cameraintent, 101);
	}
	public void oldphone(){
		Intent intent = new Intent("android.intent.action.PICK");
		intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
		intent.putExtra("output", Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/"+ShineApplication.IMAGE_FILE_LOCATION)));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 锟矫硷拷锟斤拷锟斤拷锟�
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);// 锟斤拷锟酵计拷锟叫�
		intent.putExtra("outputY", 300);
		startActivityForResult(intent, 102);
	}
//	public void getDataLogInfo(final String url) {
//		FragUtil.dataLogUrl=url;
//		if(num>=16) return;
//		GetUtil.get(url, new GetListener() {
//			
//			@Override
//			public void success(String json) {
//				try {
//					boolean lost=new JSONObject(json).getBoolean("lost");
//					if(lost==true){
////						if(FragUtil.se==null){
////						FragUtil.se = Executors.newSingleThreadScheduledExecutor();
////						}
////						// 锟斤拷Activity锟斤拷媒锟斤拷悖�5锟斤拷锟接凤拷锟斤拷一锟轿凤拷锟斤拷锟斤拷
////						FragUtil.se.scheduleAtFixedRate(new ScrollTask(), 1, 5, TimeUnit.SECONDS);
//						num++;
//						handler.postDelayed(new Runnable() {
//							
//							@Override
//							public void run() {
//								handler.sendEmptyMessage(102);
//							}
//						}, 5000);
//					}else{
////						FragUtil.se.shutdown();
//						num=0;
//						FragUtil.dataLogUrl=null;
//						T.make(R.string.dataloggers_add_success);
//						refresh();
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				
//			}
//			
//			@Override
//			public void error(String json) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
//		
//	}
//	/**
//	 * 锟斤拷锟叫碉拷锟斤拷锟斤拷锟今，碉拷锟斤拷锟缴硷拷锟斤拷锟角凤拷锟斤拷锟斤拷锟�
//	 * 
//	 * @author Administrator
//	 * 
//	 */
//	public  class ScrollTask implements Runnable {
//
//		public void run() {
//		   Message msg=new Message();
//		   msg.what=2;
//		   handler.sendMessage(msg);
//		}
//
//	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 101:
			if(data!=null){
				
				startPhotoZoom(Uri.fromFile(new File(Environment.getExternalStorageDirectory()+"/"+ShineApplication.IMAGE_FILE_LOCATION)));
			}
			break;
		case 102:
			if(data!=null){
				sentPicToNext(data);
			}
			break;
		case 105:
			if(data!=null){
				Bundle bundle=data.getExtras();
				final String s=bundle.getString("result");
//				if (TextUtils.isEmpty(s) || s.length() != 10){
//					T.make(R.string.datalogcheck_code_702,getActivity());
//					return;
//				}
				if (TextUtils.isEmpty(s)){
					T.make(R.string.datalogcheck_code_702,getActivity());
					return;
				}
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
								//TODO 锟斤拷映晒锟斤拷锟窖拷杉锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟角凤拷锟斤拷锟斤拷锟斤拷
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
//						params.put("validCode", AppUtils.validateWebbox(s));
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
	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true锟斤拷锟斤拷锟斤拷锟节匡拷锟斤拷锟斤拷intent锟斤拷锟斤拷锟斤拷锟斤拷示锟斤拷view锟斤拷锟皆硷拷锟斤拷
		intent.putExtra("crop", "true");

		// aspectX aspectY 锟角匡拷叩谋锟斤拷锟�
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 锟角硷拷锟斤拷图片锟侥匡拷锟�
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, 102);
	}
	// 锟斤拷锟斤拷锟叫硷拷锟矫猴拷锟酵计拷锟斤拷莸锟斤拷锟揭伙拷锟斤拷锟斤拷锟斤拷锟�
	private void sentPicToNext(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			if (photo==null) {
				return;
			}else {
				FileUtils.saveBitmap(photo,Environment.getExternalStorageDirectory()+"/"+ShineApplication.IMAGE_FILE_LOCATION,"");
			}
			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] photodata = baos.toByteArray();
			} catch (Exception e) {
				e.getStackTrace();
			} finally {
				if (baos != null) {
					try {
						baos.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			maps=setmap(lists.get(Clickpositions).get("deviceType").toString(),et.getText().toString(), Environment.getExternalStorageDirectory()+"/"+ShineApplication.IMAGE_FILE_LOCATION, lists.get(positions).get("deviceSn").toString());
			Bitmap bitmap=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/"+ShineApplication.IMAGE_FILE_LOCATION);
			image.setImageBitmap(bitmap);
			Mydialog.Show(getActivity(), "");
			String url="";
			if("inverter".equals(lists.get(Clickpositions).get("deviceType").toString())){
				url=new Urlsutil().updateInvInfo;
			}else{
				url=new Urlsutil().updateStorageInfo;
			}
			AddCQ.postUp(url, maps, new GetListener() {
				
				@Override
				public void success(String res) {
					if(res!=null){
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
	}
	private Map<String, Object>maps;
	private PopupWindow popup;
	
//	class AddThread implements Runnable{
//		@Override
//		public void run() {
//			String res=AddCQ.updateInvInfo(maps);
//			if(res!=null){
//				Message msg=new Message();
//				msg.what=0;
//				msg.obj=res;
//				handler.sendMessage(msg);
//			}else{
//				Message msg=new Message();
//				msg.what=1;
//				handler.sendMessage(msg);
//			}
//		}
//	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	public Map<String, Object> setmap(String type,String alias,String image,String id){
		Map<String, Object>map=new HashMap<String, Object>();
		map.put("alias",alias);
		map.put("image",image);
		map.put("type",type);
		if(type.equals("inverter")){
			map.put("inverterId",id);
//			map.put("url",new Urlsutil().updateInvInfo);
		}else if ("mix".equals(type)){
			map.put("mixId",id);
		}
		else{
//			map.put("url",new Urlsutil().updateStorageInfo);
			map.put("storageId",id);
		}
		return map;
	}
	public String Doubles(String s){
		DecimalFormat df = new DecimalFormat("0.00");
		double d =Double.parseDouble(s);
		String db = df.format(d);
		return db;
	}
	 static class MapComparatorDesc implements Comparator<Map<String, Object>> {
	        @Override
	        public int compare(Map<String, Object> m1, Map<String, Object> m2) {
	            Integer v1 = Integer.valueOf(m1.get("id").toString());
	            Integer v2 = Integer.valueOf(m2.get("id").toString());
	            if (v2 != null) {
	                return v2.compareTo(v1);
	            }
	            return 0;
	        }
	  
	    }
	public void getplantname(View v){
		View contentView = LayoutInflater.from(getActivity()).inflate(
				R.layout.popop_plantnames, null);
		ListView lv=(ListView)contentView.findViewById(R.id.listView1);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(positions!=position){
//					positions=position;
//					plant=Cons.plants.get(positions).get("plantId").toString();
//					SqliteUtil.plant(plant);
//					Cons.plant=plant;
//					sn = null;
//					title.setText(Cons.plants.get(positions).get("plantName").toString());
					Mydialog.Show(getActivity());
					refresh(position);
					}
				popup.dismiss();
			}
		});
		spadapter=new FragspinnerAdapter(getActivity(), Cons.plants,false,0);
		lv.setAdapter(spadapter);
		popup = new Solve7PopupWindow(contentView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
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
	
	
	
	    public class SwipeAdapter extends BaseAdapter{
	    	private Context context;
	    	private List<Map<String, Object>> list;
	    	private LayoutInflater layoutInflater;
//	    	RelativeLayout relativeLayout;
	        Button top;
//	        Button edit;
	        private int downX;
	        private int downY;
	     
	    	public SwipeAdapter(Context context,List<Map<String, Object>> list){
	    		this.context=context;
	    		this.list=list;
	    		if(context==null){
	    			layoutInflater=LayoutInflater.from(ShineApplication.context);
	    		}else{
	    			layoutInflater = LayoutInflater.from(context);
	    		}
	    		
	    		
	    	}
	    	@Override
	    	public int getCount() {
	    		return list.size();
	    	}

	    	@Override
	    	public Object getItem(int arg0) {
	    		return list.get(arg0);
	    	}

	    	@Override
	    	public long getItemId(int arg0) {
	    		return arg0;
	    	}

	    	@Override
	    	public View getView(final int position, View convertView, ViewGroup arg2) {
	    		ViewHoder hoder = null;
	    		if (convertView == null) {
	    			hoder = new ViewHoder();
	    			convertView = layoutInflater.inflate(R.layout.swipemenu_item, null);
	    			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
	    			hoder.tv3 = (TextView) convertView.findViewById(R.id.textView3);
	    			hoder.tv4 = (TextView) convertView.findViewById(R.id.textView4);
	    			hoder.tv5 = (TextView) convertView.findViewById(R.id.textView5);
	    			hoder.tv6 = (TextView) convertView.findViewById(R.id.textView6);
	    			hoder.tv7 = (TextView) convertView.findViewById(R.id.textView7);
	    			hoder.imageView=(ImageView)convertView.findViewById(R.id.imageView1);
	    			hoder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.conversatinListview_front);//锟斤拷取锟斤拷要锟斤拷锟斤拷锟斤拷item
	    			hoder.top=(Button) convertView.findViewById(R.id.conversationlist_top);
	    			hoder.edit=(Button) convertView.findViewById(R.id.conversationlist_edit);
	    			convertView.setTag(hoder);
	    		} else {
	    			hoder = (ViewHoder) convertView.getTag();
	    		}
	    		top=hoder.top;
	    		hoder.relativeLayout.setOnClickListener(new View.OnClickListener() {
	    			private Intent intent;
	    			@Override
	    			public void onClick(View arg0) {
	    			Map<String,Object> map = list.get(position);
					String deviceType = map.get("deviceType").toString();
	    			if(("inverter").equals(deviceType)){
	    				intent=new Intent(context,InverterActivity.class);
						Object obj = list.get(position).get("invType");
						if (obj != null && "1".equals(obj)) {
							intent.putExtra("invType", 1);
						}else {
							intent.putExtra("invType", 0);
						}
	    			}else if(("storage") .equals(deviceType)&& map.containsKey("storageType") && "2".equals(map.get("storageType").toString())){
	    				intent=new Intent(context,StorageSp5kActivity.class);
	    			}else if(("storage").equals(deviceType)){
//						intent=new Intent(context,StorageSp5kActivity.class);
						intent=new Intent(context,StorageActivity.class);
					}else if(map.get("deviceType").toString().contains("box")){
	    				intent=new Intent(context,InverterActivity.class);
	    			}else if ("mix".equals(deviceType)){
//						intent=new Intent(context,MixMainActivity.class);
					}
					if (intent != null) {
						Bundle bundle = new Bundle();
						bundle.putString("deviceAilas", list.get(position).get("deviceAilas").toString());
						bundle.putString("id", list.get(position).get("deviceSn").toString());
						bundle.putString("datalogSn", list.get(position).get("datalogSn").toString());
						intent.putExtras(bundle);
						context.startActivity(intent);
					}
	    			}
	    			
	    		});
	    		hoder.edit.setOnClickListener(new View.OnClickListener() {
	    			
	    			@Override
	    			public void onClick(View arg0) {
	    				//getActivity().sendBroadcast(frgIntent);
	    				Clickpositions=position;
	    				selectdialog(position);
	    			}
	    		});
	    		hoder.top.setOnClickListener(new View.OnClickListener() {
	    			
	    			@Override
	    			public void onClick(View arg0) {
	    				String timenew=System.currentTimeMillis()+"";
	    				timenew=timenew.substring(3,timenew.length()-1);
	    				System.out.println("timenew="+timenew);
	    				Map<String, Object> m=list.get(position);
	    				m.put("id",""+timenew);
	    				m.put("userId", userId);
	    				SqliteUtil.ids(m);
	    				Collections.sort(list, new MapComparatorDesc());
	    				Mydialog.Show(getActivity(), "");
	                     refresh();
	    				notifyDataSetChanged();
	    				listview.setSelection(0);
	    				
	    			}
	    		});
	    		hoder.relativeLayout.setOnTouchListener(new OnTouchListener() {
	                private Point pointDownPoint;
	                private Point pointUpPoint;
	                private boolean isdelete;
	                boolean result = false;
	                boolean isOpen = false;
	    			
	                @Override
	                public boolean onTouch(View v, MotionEvent event) {
	                    int bottomWidth = top.getWidth()*2;

	                    switch (event.getAction()) {
	                        case MotionEvent.ACTION_DOWN:
	                            // Log.i("", "ACTION_DOWN");
	                            downX = (int) event.getRawX();
	                            downY=(int)event.getRawY();
	                            result = false;
	                            //break;
	                        case MotionEvent.ACTION_MOVE:
	                            // Log.i("", "ACTION_MOVE");
	                            // if (isAniming)
	                            // break;
	                            int dx = (int) (event.getRawX() - downX);
	                            int dy=(int)(event.getRawY()-downY);
	                            // Log.i("", "dy___" + dx);
	                            if(Math.abs(dx)>Math.abs(dy)){
	                            if (isOpen) {
	                                // 锟斤拷状态
	                                // 锟斤拷锟揭伙拷锟斤拷
	                                if (dx > 0 && dx < bottomWidth) {
	                                    v.setTranslationX(dx - bottomWidth);
	                                    // 锟斤拷锟斤拷锟狡讹拷锟斤拷锟斤拷止锟斤拷锟�
	                                    result = true;
	                                }
	                            } else {
	                                // 锟秸猴拷状态
	                                // 锟斤拷锟斤拷锟狡讹拷
	                                if (dx <-3 && Math.abs(dx) < bottomWidth) {
	                                    v.setTranslationX(dx);
	                                    // 锟斤拷锟斤拷锟狡讹拷锟斤拷锟斤拷止锟斤拷锟�
	                                    result = true;
	                                }
	                            }
	                            }
	                            break;
	                        case MotionEvent.ACTION_CANCEL:
	                        case MotionEvent.ACTION_UP:
	                            // Log.i("", "ACTION_UP" + v.getTranslationX());
	                            // 锟斤拷取锟窖撅拷锟狡讹拷锟斤拷
	                            float ddx = v.getTranslationX();
	                            // 锟叫断打开伙拷锟角关憋拷

	                            if (ddx <= 0 && ddx > -(bottomWidth / 2)) {
	                                // 锟截憋拷
	                                ObjectAnimator oa1 = ObjectAnimator.ofFloat(v, "translationX", ddx, 0).setDuration(100);
	                                oa1.start();
	                                oa1.addListener(new Animator.AnimatorListener() {
	                                    @Override
	                                    public void onAnimationStart(Animator animation) {
	                                    }

	                                    @Override
	                                    public void onAnimationRepeat(Animator animation) {
	                                    }

	                                    @Override
	                                    public void onAnimationEnd(Animator animation) {
	                                        isOpen = false;
	                                        result = false;
	                                    }

	                                    @Override
	                                    public void onAnimationCancel(Animator animation) {
	                                        isOpen = false;
	                                        result = false;
	                                    }
	                                });

	                            }
	                            if (ddx <= -(bottomWidth / 2) && ddx > -bottomWidth) {
	                                // 锟斤拷
	                                ObjectAnimator oa1 = ObjectAnimator.ofFloat(v, "translationX", ddx, -bottomWidth)
	                                        .setDuration(100);
	                                oa1.start();
	                                result = true;
	                                isOpen = true;
	                            }
								float removeX = event.getRawX()-downX;
								if (Math.abs(removeX) < bottomWidth/10){
									result = false;
								}
	                            break;
	                    }
	                    return result;
	                }
	            });
				String deviceStatus = list.get(position).get("deviceStatus").toString();
				String deviceType = list.get(position).get("deviceType").toString();
	    		if(("inverter").equals(deviceType)){
	    			hoder.tv7.setText(list.get(position).get("eToday").toString()+"kWh");
	    			hoder.tv5.setText(list.get(position).get("power").toString()+"W");
	    			String as=context.getResources().getString(R.string.inverter_todayquantity);
	    			hoder.tv6.setText(as+":");
//	    			String subString = list.get(position).get("deviceSn").toString().substring(0, 2);
					int resId = R.drawable.frg1_inverter;
					GlideUtils.getInstance().showImageAct(getActivity(),resId,resId,resId,hoder.imageView);
	    			if("1".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Waiting);
	    				hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#11a7F3"));
	    			}else if("2".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Normal);
	    				hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#79e681"));
	    			}else if("4".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Fault);
	    				hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#ff5652"));
	    			}else if("5".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Lost);
	    				hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#a3a3a3"));
	    			}else{
	    				hoder.tv3.setText(R.string.all_Lost);
	    				hoder.tv1.setTextColor(Color.parseColor("#a3a3a3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#a3a3a3"));
	    			}
	    			hoder.tv4.setText(R.string.all_power);
	    		}else if(("storage").equals(deviceType)){
//	    			hoder.imageView.setImageResource(R.drawable.chunengji);
					GlideUtils.getInstance().showImageAct(getActivity(),R.drawable.frg1_storage,R.drawable.frg1_storage,R.drawable.frg1_storage,hoder.imageView);
	    			hoder.tv7.setText(list.get(position).get("capacity").toString());
	    			hoder.tv5.setText(list.get(position).get("pCharge").toString()+"W");
	    			hoder.tv6.setText(R.string.storage_percent2);
	    			if("0".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Standby);
	    				hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#2de2e9"));
	    			}else if("1".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Charge);
	    				hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#79e681"));
	    			}else if("2".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Discharge);
	    				hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#ded35b"));
	    			}else if("3".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Fault);
	    				hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#ff5652"));
	    			}else if("4".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Flash);
	    				hoder.tv1.setTextColor(Color.parseColor("#a3a3a3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#a3a3a3"));
	    			}else if("-1".equals(deviceStatus)){
                        hoder.tv3.setText(R.string.all_Flash);
                        hoder.tv1.setTextColor(Color.parseColor("#a3a3a3"));
                        hoder.tv3.setTextColor(Color.parseColor("#a3a3a3"));
                    }else{
	    				hoder.tv3.setText(R.string.m186在线);
						hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#79e681"));
	    			}
	    			hoder.tv4.setText(R.string.all_power);
	    		}
				else if("mix".equals(deviceType)){
					try {
						hoder.tv7.setText(list.get(position).get("eToday").toString()+"kWh");
						hoder.tv5.setText(list.get(position).get("power").toString()+"W");
					} catch (Exception e) {
						e.printStackTrace();
					}
					String as=context.getResources().getString(R.string.inverter_todayquantity);
					hoder.tv6.setText(as+":");
//	    			String subString = list.get(position).get("deviceSn").toString().substring(0, 2);
					int resId = R.drawable.mix_icon;
					GlideUtils.getInstance().showImageAct(getActivity(),resId,resId,resId,hoder.imageView);
					int status = Integer.parseInt(deviceStatus);
					hoder.tv3.setText(MixUtil.getMixStatusTextList(status,getActivity()));
					if("0".equals(deviceStatus)){
						hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
						hoder.tv3.setTextColor(Color.parseColor("#11a7F3"));
					}else if("1".equals(deviceStatus)){
						hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
						hoder.tv3.setTextColor(Color.parseColor("#2de2e9"));
					}else if("3".equals(deviceStatus)){
						hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
						hoder.tv3.setTextColor(Color.parseColor("#ff5652"));
					}else if("4".equals(deviceStatus)){
						hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
						hoder.tv3.setTextColor(Color.parseColor("#ded35b"));
					}
					else if (status == 9){
						hoder.tv1.setTextColor(Color.parseColor("#a3a3a3"));
						hoder.tv3.setTextColor(Color.parseColor("#a3a3a3"));
					}else if (status >0){
						hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
						hoder.tv3.setTextColor(Color.parseColor("#79e681"));
					}else{
						hoder.tv3.setText(R.string.all_Lost);
						hoder.tv1.setTextColor(Color.parseColor("#a3a3a3"));
						hoder.tv3.setTextColor(Color.parseColor("#a3a3a3"));
					}
					hoder.tv4.setText(R.string.all_power);
				}
	    		else if(deviceType.contains("box")){
	    			hoder.tv7.setText("");
	    			hoder.tv5.setText("");
	    			hoder.tv6.setText("");
	    			hoder.tv4.setText("");
					GlideUtils.getInstance().showImageAct(getActivity(),R.drawable.frg1_inverter,R.drawable.frg1_inverter,R.drawable.frg1_inverter,hoder.imageView);
//	    			hoder.imageView.setImageResource(R.drawable.inverter);
	    			if("1".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Waiting);
	    				hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#11a7F3"));
	    			}else if("2".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Normal);
	    				hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#79e681"));
	    			}else if("4".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Fault);
	    				hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#ff5652"));
	    			}else if("5".equals(deviceStatus)){
	    				hoder.tv3.setText(R.string.all_Lost);
	    				hoder.tv1.setTextColor(Color.parseColor("#11a7F3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#a3a3a3"));
	    			}else{
	    				hoder.tv3.setText(R.string.all_Lost);
	    				hoder.tv1.setTextColor(Color.parseColor("#a3a3a3"));
	    				hoder.tv3.setTextColor(Color.parseColor("#a3a3a3"));
	    			}
	    		}
	    		hoder.tv1.setText(list.get(position).get("deviceAilas").toString());
	    		return convertView;
	    	}
	    	class ViewHoder {
	    		public TextView tv4;
	    		public TextView tv6;
	    		public TextView tv5;
	    		public TextView tv1;
	    		public TextView tv7;
	    		public TextView tv3;
	    		public ImageView imageView;
	    		public RelativeLayout relativeLayout;
	    		public Button top;
	    		public Button edit;
	    	}
	    	public String Doubles(String s){
	    		if(!s.equals("0")){
	    		DecimalFormat df = new DecimalFormat("0.00");
	    		
	    		double d =Double.parseDouble(s);
	    		String db = df.format(d);
	    		return db;
	    		}else{
	    			return "0";
	    			
	    		}
	    		
	    	}
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

	
	
	
	
	
	
	
	
	
	
}
