package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.OssDatalogSetAdapter;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.listener.OnHandlerListener;
import com.growatt.shinephone.listener.OnHandlerStrListener;
import com.growatt.shinephone.ossactivity.OssChargeSocLimitSetActivity;
import com.growatt.shinephone.ossactivity.OssDeviceTurnOnOffActivity;
import com.growatt.shinephone.ossactivity.OssSPSetActivity;
import com.growatt.shinephone.ossactivity.OssSetTimeActivity;
import com.growatt.shinephone.ossactivity.OssSocDownLimitSetActivity;
import com.growatt.shinephone.ossactivity.OssStorageDischargeTimeSetActivity;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Position;
import com.mylhyl.circledialog.CircleDialog;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;


@ContentView(R.layout.activity_storage_set2)
public class StorageSet2Activity extends DemoBase {
	@ViewInject(R.id.recycleView)
	RecyclerView mRecyclerView;
	private OssDatalogSetAdapter mAdapter;
	private String[] datas;
	private String[] paramName = {"storage_cmd_on_off","storage_lithium_battery","storage_cmd_sys_year"
			,"storage_cmd_forced_discharge_enable","storage_cmd_forced_discharge_time1","storage_fdt_open_voltage"
			,"storage_ac_charge_enable_disenable","storage_ac_charge_hour_start","storage_ac_charge_soc_limit"
	};
	private String sn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle=getIntent().getExtras();
		sn = bundle.getString("serialNum");
		initHeaderView();
		initRecyclerView();
		SetListeners();
	}
	private void initRecyclerView() {
		datas = new String[]{getString(R.string.storageset_list_item1),getString(R.string.storageset_list_item2),getString(R.string.storageset_list_item6),
				getString(R.string.storageset_list_item3),getString(R.string.storageset_list_item4),
				getString(R.string.storageset_list_item5),getString(R.string.m84充电使能),getString(R.string.m85充电时间段),getString(R.string.m86充电电池SOC设置)};
		mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
		mAdapter = new OssDatalogSetAdapter(mContext,R.layout.item_oss_datalogset, Arrays.asList(datas));
		mRecyclerView.setAdapter(mAdapter);
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
		setHeaderTitle(headerView,getString(R.string.storageset_title));
	}
	private void SetListeners() {
		mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(View view, RecyclerView.ViewHolder holder, final int position) {
					if(Cons.isflag==true){
						toast(R.string.all_experience);
						return;
					}
//					if (position < 0){
					if (position != 2){
						//获取密码匹配后设置储能机参数
						MyControl.getPasswordByDevice(StorageSet2Activity.this, 2, new OnHandlerStrListener() {
							@Override
							public void handlerDealStr(String result) {
								//弹框让用户输入密码进行匹配
								matchUserPwd(result,position);
							}
						});
					}else {//直接设置储能机参数
						setStorage(position);
					}
			}
			@Override
			public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
				return true;
			}
		});
	}

	/**
	 * 匹配用户密码
	 * @param pwd
	 */
	private void matchUserPwd(final String pwd, final int position) {
		final EditText et= new EditText(StorageSet2Activity.this);
		et.setTransformationMethod(PasswordTransformationMethod.getInstance());
		AlertDialog dialog = new AlertDialog.Builder(StorageSet2Activity.this).setTitle(R.string.reminder)
				.setIcon(android.R.drawable.ic_menu_info_details).setMessage(R.string.inverterset_operate).setView(
						et).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int swich) {
						String s=et.getText().toString();
						if (s.equals(pwd)){
							setStorage(position);
						}else {
							MyControl.circlerDialog(StorageSet2Activity.this,getString(R.string.password_prompt),-1,false);
						}
					}
				}).setNegativeButton(R.string.all_no, null).create();
		dialog.setCancelable(true);
		dialog.show();
	}

	/**
	 * 设置参数
	 * @param position
	 */
	private void setStorage(final int position) {
		switch (position){
			case 0://开关机
				Intent in0 = new Intent(mContext,OssDeviceTurnOnOffActivity.class);
				in0.putExtra("type",2);
				in0.putExtra("paramId",paramName[position]);
				in0.putExtra("deviceType",2);
				in0.putExtra("sn",sn);
				in0.putExtra("title",datas[position]);
				startActivity(in0);
				break;
			case 1://soc下限
				Intent in1 = new Intent(mContext,OssSocDownLimitSetActivity.class);
				in1.putExtra("paramId",paramName[position]);
				in1.putExtra("type",4);
				in1.putExtra("sn",sn);
				in1.putExtra("title",datas[position]);
				startActivity(in1);
				break;
			case 2://时间
				Intent in5 = new Intent(mContext,OssSetTimeActivity.class);
				in5.putExtra("type",4);
				in5.putExtra("paramId",paramName[position]);
				in5.putExtra("sn",sn);
				in5.putExtra("title",datas[position]);
				startActivity(in5);
				break;
			case 3:
			case 6://强制放电使能;充电使能
				String[] items = {getString(R.string.m89禁止),getString(R.string.m88使能)};
				new CircleDialog.Builder(StorageSet2Activity.this)
						.setTitle(datas[position])
						.setItems(items, new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
								MyControl.storageSetServer(StorageSet2Activity.this, sn, paramName[position], pos + "","","","", new OnHandlerListener() {
									@Override
									public void handlerDeal(int result,String msg) {
										handlerStorageServer.sendEmptyMessage(result);
									}
								});
							}
						})
						.setNegative(getString(R.string.all_no),null)
						.show();
				break;
			case 4://强制放电时间段
			case 7://强制充电时间段
				int type = 2;
				if (position == 4){ type = 4;}
				if (position == 7){ type = 5;}
				Intent in4 = new Intent(mContext,OssStorageDischargeTimeSetActivity.class);
				in4.putExtra("type",type);
				in4.putExtra("paramId",paramName[position]);
				in4.putExtra("sn",sn);
				in4.putExtra("title",datas[position]);
				startActivity(in4);
				break;
			case 5://设置sp组串电压
				Intent in6 = new Intent(mContext,OssSPSetActivity.class);
				in6.putExtra("type",4);
				in6.putExtra("paramId",paramName[position]);
				in6.putExtra("sn",sn);
				in6.putExtra("title",datas[position]);
				startActivity(in6);
				break;
			case 8:
				Intent in8 = new Intent(mContext,OssChargeSocLimitSetActivity.class);
				in8.putExtra("type",4);
				in8.putExtra("paramId",paramName[position]);
				in8.putExtra("sn",sn);
				in8.putExtra("title",datas[position]);
				startActivity(in8);
				break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}


	//server储能机handler
	private Handler handlerStorageServer = new Handler(Looper.getMainLooper()){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String text = "";
			switch (msg.what){
				case 200:text = getString(R.string.all_success);break;
				case 501:text = getString(R.string.inverterset_set_no_server);break;
				case 502:text = getString(R.string.inverterset_set_interver_no_server);break;
				case 503:text = getString(R.string.inverterset_set_no_numberblank);break;
				case 504:text = getString(R.string.inverterset_set_interver_no_online);break;
				case 505:text = getString(R.string.inverterset_set_no_online);break;
				case 506:text = getString(R.string.storageset_no_type);break;
				case 507:text = getString(R.string.inverterset_set_no_blank);break;
				case 508:text = getString(R.string.storageset_no_value);break;
				case 509:text = getString(R.string.storageset_no_time);break;
				case 701:text = getString(R.string.m7);break;
				default:text = getString(R.string.inverterset_set_other);break;
			}
			MyControl.circlerDialog(StorageSet2Activity.this,text,msg.what);
		}
	};
}
