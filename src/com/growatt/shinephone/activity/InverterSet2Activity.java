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
import com.growatt.shinephone.ossactivity.OssDeviceTurnOnOffActivity;
import com.growatt.shinephone.ossactivity.OssPVGridSingleSetActivity;
import com.growatt.shinephone.ossactivity.OssPVRateActivity;
import com.growatt.shinephone.ossactivity.OssSetPFActivity;
import com.growatt.shinephone.ossactivity.OssSetTimeActivity;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.v2.DeviceTypeItemStr;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.DialogParams;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.Arrays;
@ContentView(R.layout.activity_inverter_set2)
public class InverterSet2Activity extends DemoBase {
	@ViewInject(R.id.recycleView)
	RecyclerView mRecyclerView;
	private OssDatalogSetAdapter mAdapter;
	private String[] datas;
	private String sn;
	private String[] paramName = {"pv_on_off","pv_active_p_rate","pv_reactive_p_rate","pv_pf_cmd_memory_state",
			"pv_power_factor","pf_sys_year","pv_grid_voltage_high","pv_grid_voltage_low","set_any_reg"};
	private String[] paramNameMax = {"max_cmd_on_off"};
	private String[] paramNameJLINV = {"pv_on_off","pv_active_p_rate","pv_reactive_p_rate",
			"pv_power_factor","pf_sys_year","pv_grid_voltage_high","pv_grid_voltage_low"};
	private String mDeviceType = DeviceTypeItemStr.DEVICE_INVERTER_STR;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initIntent();
		initHeaderView();
		initRecyclerView();
		SetListeners();
	}

	private void initIntent() {
		Bundle bundle=getIntent().getExtras();
		sn=bundle.getString("inverterId");
        mDeviceType = bundle.getString("deviceType");
	}

	private void initRecyclerView() {
		if (DeviceTypeItemStr.DEVICE_INVERTER_MAX_STR.equals(mDeviceType)){
			datas = new String[]{
					getString(R.string.inverterset_switch)
			};
		}else if (DeviceTypeItemStr.DEVICE_INVERTER_JLINV_STR.equals(mDeviceType)){
			datas = new String[]{
					getString(R.string.inverterset_switch), getString(R.string.inverterset_activepower),
					getString(R.string.inverterset_wattlesspower),
					getString(R.string.inverterset_pfvalue), getString(R.string.inverterset_time),
					getString(R.string.inverterset_voltage_high), getString(R.string.m87设置市电电压下限)
			};
		}else {
			datas = new String[]{getString(R.string.inverterset_switch), getString(R.string.inverterset_activepower),
					getString(R.string.inverterset_wattlesspower), getString(R.string.inverterset_pforder),
					getString(R.string.inverterset_pfvalue), getString(R.string.inverterset_time),
					getString(R.string.inverterset_voltage_high), getString(R.string.m87设置市电电压下限)};
		}
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
		setHeaderTitle(headerView,getString(R.string.inverterset_title));
	}

	/**
	 * 匹配用户密码
	 * @param pwd
	 */
	private void matchUserPwd(final String pwd, final int position) {
		final EditText et= new EditText(InverterSet2Activity.this);
		et.setTransformationMethod(PasswordTransformationMethod.getInstance());
		AlertDialog dialog = new AlertDialog.Builder(InverterSet2Activity.this).setTitle(R.string.reminder)
				.setIcon(android.R.drawable.ic_menu_info_details).setMessage(R.string.inverterset_operate).setView(
						et).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int swich) {
						String s=et.getText().toString();
						if (s.equals(pwd)){
							setInv(position);
						}else {
							MyControl.circlerDialog(InverterSet2Activity.this,getString(R.string.password_prompt),-1,false);
						}
					}
				}).setNegativeButton(R.string.all_no, null).create();
		dialog.setCancelable(true);
		dialog.show();
	}

	private void setInv(int position) {
        //max
        if (DeviceTypeItemStr.DEVICE_INVERTER_MAX_STR.equals(mDeviceType)){
            switch (position){
                case 0://开关机
                    Intent in1 = new Intent(mContext,OssDeviceTurnOnOffActivity.class);
                    in1.putExtra("type",2);
                    in1.putExtra("paramId",paramNameMax[position]);
                    in1.putExtra("deviceType",3);
                    in1.putExtra("sn",sn);
                    in1.putExtra("title",datas[position]);
                    startActivity(in1);
                    break;
                case 1:
                case 2://设置有功和无功功率百分比
                    Intent in2 = new Intent(mContext,OssPVRateActivity.class);
                    in2.putExtra("type",3);
                    in2.putExtra("paramId",paramNameMax[position]);
                    in2.putExtra("sn",sn);
                    in2.putExtra("title",datas[position]);
                    startActivity(in2);
                    break;
                case 3://是否储存pf命令
                    setPFTurnOnOff(position,1);
                    break;
                case 4://设置pf
                    Intent in3 = new Intent(mContext,OssSetPFActivity.class);
                    in3.putExtra("type",3);
                    in3.putExtra("paramId",paramNameMax[position]);
                    in3.putExtra("sn",sn);
                    in3.putExtra("title",datas[position]);
                    startActivity(in3);
                    break;
                case 5://设置时间
                    Intent in4 = new Intent(mContext,OssSetTimeActivity.class);
                    in4.putExtra("type",7);
                    in4.putExtra("paramId",paramNameMax[position]);
                    in4.putExtra("sn",sn);
                    in4.putExtra("title",datas[position]);
                    startActivity(in4);
                    break;
                case 6:
                case 7://设置市电电压上下限
                    Intent in5 = new Intent(mContext,OssPVGridSingleSetActivity.class);
                    in5.putExtra("type",3);
                    in5.putExtra("paramId",paramNameMax[position]);
                    in5.putExtra("sn",sn);
                    in5.putExtra("title",datas[position]);
                    startActivity(in5);
                    break;
            }
        }else if (DeviceTypeItemStr.DEVICE_INVERTER_JLINV_STR.equals(mDeviceType)){
			//锦浪逆变器
			switch (position){
				case 0://开关机
					Intent in1 = new Intent(mContext,OssDeviceTurnOnOffActivity.class);
					in1.putExtra("type",2);
					in1.putExtra("paramId",paramNameJLINV[position]);
					in1.putExtra("deviceType",4);
					in1.putExtra("sn",sn);
					in1.putExtra("title",datas[position]);
					startActivity(in1);
					break;
				case 1:
				case 2://设置有功和无功功率百分比
					Intent in2 = new Intent(mContext,OssPVRateActivity.class);
					in2.putExtra("type",4);
					in2.putExtra("paramId",paramNameJLINV[position]);
					in2.putExtra("sn",sn);
					in2.putExtra("title",datas[position]);
					startActivity(in2);
					break;
//				case 3://是否储存pf命令
//					setPFTurnOnOff(position,0);
//					break;
				case 3://设置pf
					Intent in3 = new Intent(mContext,OssSetPFActivity.class);
					in3.putExtra("type",4);
					in3.putExtra("paramId",paramNameJLINV[position]);
					in3.putExtra("sn",sn);
					in3.putExtra("title",datas[position]);
					startActivity(in3);
					break;
				case 4://设置时间
					Intent in4 = new Intent(mContext,OssSetTimeActivity.class);
					in4.putExtra("type",7);
					in4.putExtra("paramId",paramNameJLINV[position]);
					in4.putExtra("sn",sn);
					in4.putExtra("title",datas[position]);
					startActivity(in4);
					break;
				case 5:
				case 6://设置市电电压上下限
					Intent in5 = new Intent(mContext,OssPVGridSingleSetActivity.class);
					in5.putExtra("type",4);
					in5.putExtra("paramId",paramNameJLINV[position]);
					in5.putExtra("sn",sn);
					in5.putExtra("title",datas[position]);
					startActivity(in5);
					break;
			}
		}else {//逆变器
            switch (position){
                case 0://开关机
                    Intent in1 = new Intent(mContext,OssDeviceTurnOnOffActivity.class);
                    in1.putExtra("type",2);
                    in1.putExtra("paramId",paramName[position]);
                    in1.putExtra("deviceType",1);
                    in1.putExtra("sn",sn);
                    in1.putExtra("title",datas[position]);
                    startActivity(in1);
                    break;
                case 1:
                case 2://设置有功和无功功率百分比
                    Intent in2 = new Intent(mContext,OssPVRateActivity.class);
                    in2.putExtra("type",2);
                    in2.putExtra("paramId",paramName[position]);
                    in2.putExtra("sn",sn);
                    in2.putExtra("title",datas[position]);
                    startActivity(in2);
                    break;
                case 3://是否储存pf命令
                    setPFTurnOnOff(position,0);
                    break;
                case 4://设置pf
                    Intent in3 = new Intent(mContext,OssSetPFActivity.class);
                    in3.putExtra("type",2);
                    in3.putExtra("paramId",paramName[position]);
                    in3.putExtra("sn",sn);
                    in3.putExtra("title",datas[position]);
                    startActivity(in3);
                    break;
                case 5://设置时间
                    Intent in4 = new Intent(mContext,OssSetTimeActivity.class);
                    in4.putExtra("type",3);
                    in4.putExtra("paramId",paramName[position]);
                    in4.putExtra("sn",sn);
                    in4.putExtra("title",datas[position]);
                    startActivity(in4);
                    break;
                case 6:
                case 7://设置市电电压上下限
                    Intent in5 = new Intent(mContext,OssPVGridSingleSetActivity.class);
                    in5.putExtra("type",2);
                    in5.putExtra("paramId",paramName[position]);
                    in5.putExtra("sn",sn);
                    in5.putExtra("title",datas[position]);
                    startActivity(in5);
                    break;
            }
        }
	}
	/**
	 * 是否储存pf命令
	 * @param type:0:逆变器；1：代表Max
	 */
	private void setPFTurnOnOff(final int position, final int type) {
		final String[] items = { getString(R.string.all_close),getString(R.string.all_open)};
		new CircleDialog.Builder(this)
				.configDialog(new ConfigDialog() {
					@Override
					public void onConfig(DialogParams params) {
						//增加弹出动画
						params.animStyle = R.style.MyDialogStyle;
					}
				})
				.setTitle(datas[position])
				.setItems(items, new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
						switch (type){
							case 0:
								MyControl.invSetServer(InverterSet2Activity.this, sn, paramName[position], pos +"", "", new OnHandlerListener() {
									@Override
									public void handlerDeal(int result,String msg) {
										handlerInvServer.sendEmptyMessage(result);
									}
								});
								break;
							case 1:
								MyControl.invSetMaxServer(InverterSet2Activity.this, sn, paramName[position], pos +"", "", new OnHandlerListener() {
									@Override
									public void handlerDeal(int result,String msg) {
										handlerInvServer.sendEmptyMessage(result);
									}
								});
								break;
						}
					}
				})
				.setNegative(getString(R.string.all_no), null)
				.show();
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
				if (position != 5){
					//获取密码匹配后设置逆变器参数
					MyControl.getPasswordByDevice(InverterSet2Activity.this, 1, new OnHandlerStrListener() {
						@Override
						public void handlerDealStr(String result) {
							//弹框让用户输入密码进行匹配
							matchUserPwd(result,position);
						}
					});
				}else {//直接设置逆变器参数
					setInv(position);
				}
			}
			@Override
			public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
				return true;
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	//server逆变器handler
	private Handler handlerInvServer = new Handler(Looper.getMainLooper()){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			String text = "";
			switch (msg.what){
				case 200:text = getString(R.string.all_success);break;
				case 501:text = getString(R.string.inverterset_set_no_server);break;
				case 502:text = getString(R.string.inverterset_set_interver_no_server);break;
				case 503:text = getString(R.string.inverterset_set_interver_no_online);break;
				case 504:text = getString(R.string.inverterset_set_no_numberblank);break;
				case 505:text = getString(R.string.inverterset_set_no_online);break;
				case 506:text = getString(R.string.inverterset_set_no_facility);break;
				case 507:text = getString(R.string.inverterset_set_no_blank);break;
				case 508:text = getString(R.string.inverterset_set_no_value);break;
				case 509:text = getString(R.string.inverterset_set_no_time);break;
				case 701:text = getString(R.string.m7);break;
				default:text = getString(R.string.inverterset_set_no);break;
			}
			MyControl.circlerDialog(InverterSet2Activity.this,text,msg.what);
		}
	};
}
