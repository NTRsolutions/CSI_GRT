package com.growatt.shinephone.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.m30.wifi.InputWifi;
import com.example.m30.wifi.InputWifi.InputListener;
import com.example.m30.wifi.WifiConnector;
import com.example.m30.wifi.WifiConnector.SecurityMode;
import com.example.m30.wifi.WifiConnector.WifiConnectListener;
import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.DataloggersAdapter;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.GlideUtils;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.PostUtil.postListener;
import com.growatt.shinephone.util.SharedPreferencesUnit;
import com.growatt.shinephone.util.Urlsutil;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigItems;
import com.mylhyl.circledialog.params.ItemsParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataloggersActivity extends DemoBase {

	private String id;
	private DataloggersAdapter adapter;
	private ListView listview;
	Intent intent;
	 private int lastItem;
	    private int count;
	    private View moreView;
	    int page=1;
		private ImageView emptyView;
//	private FrameLayout flGuede;
	private List<String> addTypes;//采集器添加的方式
	private ImageView ivGuide;
	private GlideUtils glide;
	private Activity mAct;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dataloggers);
		mAct = this;
		ivGuide=(ImageView) findViewById(R.id.ivGuide);
		glide = GlideUtils.getInstance();
		Bundle bundle=getIntent().getExtras();
		id=bundle.getString("id");
		initHeaderView();
		initGuidePage();
		SetViews();
		SetListeners();
	}
	private View headerView;
	private void initHeaderView() {
		headerView = findViewById(R.id.headerView);
		setHeaderImage(headerView,R.drawable.back, Position.LEFT,new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
				sendBroadcast(intent);
			}
		});
		setHeaderTitle(headerView,getString(R.string.dataloggers_title));
		setHeaderTvTitle(headerView,getString(R.string.DataloggersAct_add),new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				showPopupWindow(v);
			}
		});
	}
	public void initGuidePage(){
		SharedPreferencesUnit sdf=SharedPreferencesUnit.getInstance(this);
		String guide=sdf.get("datalogGuide");
		try {
			if (!"1".equals(guide)){
				ivGuide.setVisibility(View.VISIBLE);
				sdf.put("datalogGuide","1");
				if(getLanguage()==0) {
					glide.showImageAct(mAct,R.drawable.datalog_guide,ivGuide);
//				flGuede.setBackgroundResource(R.drawable.datalog_guide);
				}else{
					glide.showImageAct(mAct,R.drawable.datalog_guide_en,ivGuide);
//				flGuede.setBackgroundResource(R.drawable.datalog_guide_en);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			ivGuide.setVisibility(View.GONE);
			SharedPreferencesUnit.getInstance(this).put("datalogGuide","1");
		}
		ivGuide.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ivGuide.setVisibility(View.GONE);
			}
		});
	}

	private void SetViews() {
		intent=new Intent(Constant.Frag_Receiver);
		listview=(ListView)findViewById(R.id.listView1);
		list=new ArrayList<Map<String,Object>>();
		//��ҳ
		moreView = getLayoutInflater().inflate(R.layout.load_page, null);
		moreView.setVisibility(View.GONE);
		count=list.size();
		listview.addFooterView(moreView);
		//���ÿ�view
				emptyView=(ImageView)findViewById(R.id.emptyView);
				if(getLanguage()==0){
					emptyView.setImageResource(R.drawable.datalog_emptyview);
				}else{
					emptyView.setImageResource(R.drawable.datalog_emptyview_en);
				}
				listview.setEmptyView(emptyView);
		adapter=new DataloggersAdapter(DataloggersActivity.this, list);
		listview.setAdapter(adapter);
		 
	}


	private ArrayList<Map<String, Object>> list;
	public void AddToSetWifi(final String sn,
			JSONObject jsonObject) throws JSONException {
		String type=jsonObject.get("datalogType").toString();
		MyControl.configWifiServer(DataloggersActivity.this,type,sn);
	}

	private void showPopupWindow(View view) {
		if (addTypes == null){
			addTypes = new ArrayList<>();
			addTypes.add(getString(R.string.all_twodimension));
			addTypes.add(getString(R.string.ScanAct_manually));
		}
		new CircleDialog.Builder(this)
				.setTitle(getString(R.string.Register_add_collector))
				.configItems(new ConfigItems() {
					@Override
					public void onConfig(ItemsParams params) {
						params.textColor = ContextCompat.getColor(DataloggersActivity.this,R.color.title_bg_white);
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

	/**
	 * 扫描二维码添加采集器
	 */
	private void adddatalogTwoDim() {
		Intent intent = new Intent();
		intent.setClass(DataloggersActivity.this, MipcaActivityCapture.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, 105);
	}
	/**
	 * 手动输入添加采集器
	 */
	private void adddatalogSlecter() {
			final View v=LayoutInflater.from(DataloggersActivity.this).inflate(R.layout.retrievepwd, null);
			Dialog dialog=new AlertDialog.Builder(DataloggersActivity.this).setTitle(R.string.retrievepwd_putin_num)

					.setView(v).setPositiveButton(R.string.all_ok, new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							final EditText et1=(EditText)v.findViewById(R.id.editText1);
//							final EditText et2=(EditText)v.findViewById(R.id.editText2);
//							if(et1.getText().toString().equals("")||et2.getText().toString().equals("")){
//								toast(R.string.all_blank);
//								return;
//							}
							if(TextUtils.isEmpty(et1.getText().toString())){
								toast(R.string.all_blank);
								return;
							}
							arg0.dismiss();
							Mydialog.Show(DataloggersActivity.this, "");
							PostUtil.post(new Urlsutil().addDatalog, new postListener() {

								@Override
								public void success(String json) {
									Mydialog.Dismiss();
									try {
										JSONObject jsonObject=new JSONObject(json);
										if(jsonObject.get("success").toString().equals("true")){
											String str=jsonObject.get("msg").toString();
											if(str.equals("200")){
												toast(R.string.all_success_reminder);
												String url=new Urlsutil().getDatalogInfo+"&datalogSn="+et1.getText().toString().trim();
												//TODO 锟斤拷映晒锟斤拷锟窖拷杉锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟角凤拷锟斤拷锟斤拷锟斤拷
												//getDataLogInfo(url);
												//锟街讹拷锟斤拷硬杉锟斤拷锟绞憋拷锟斤拷锟絯ifi锟斤拷锟斤拷
												AddToSetWifi(et1.getText().toString().trim(), jsonObject);
											}
										}else{
											String msg=jsonObject.get("msg").toString();
											if(msg.equals("501")){
												toast(R.string.serviceerror);

											}else if(msg.equals("502")){
												toast(R.string.dataloggers_add_no_jurisdiction);

											}else if(msg.equals("503")){
												toast(R.string.dataloggers_add_no_number);

											}else if(msg.equals("504")){
												toast(R.string.dataloggers_add_no_v);

											}else if(msg.equals("505")){
												toast(R.string.dataloggers_add_no_full);

											}else if(msg.equals("506")){
												toast(R.string.dataloggers_add_no_exist);

											}else if(msg.equals("507")){
												toast(R.string.dataloggers_add_no_matching);
											}else if("701".equals("msg")){
												toast(R.string.m7);
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
	private void SetListeners() {
		listview.setOnScrollListener(new AbsListView.OnScrollListener() {
			
			@Override
	        public void onScroll(AbsListView view, int firstVisibleItem,
	                        int visibleItemCount, int totalItemCount) {
	                 
	                
	                 
	                lastItem = firstVisibleItem + visibleItemCount - 1;  //��1����Ϊ������˸�addFooterView
	                 
	        }

	        @Override
	        public void onScrollStateChanged(AbsListView view, int scrollState) {
	                //�����������ǣ������һ��item�����������ݵ�����ʱ�����и���
	                if(lastItem == count  && scrollState == this.SCROLL_STATE_IDLE){
	                    moreView.setVisibility(View.VISIBLE);
	                    handler.sendEmptyMessage(100);
	                          
	                }
	                 
	        }
		}); //����listview�Ĺ����¼�
		Mydialog.Show(DataloggersActivity.this,"");
		loadData();
	
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				dataloggers(position);
				return false;
			}
		});
	}

	private void loadData() {
	GetUtil.get(new Urlsutil().datalogAlist+"&id="+id+"&currentPage="+page, new GetListener() {
			
			@Override
			public void success(String json) {
				Mydialog.Dismiss();
				moreView.setVisibility(View.GONE);
				try {
					JSONArray jsonArray=new JSONArray(json);
					
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject=jsonArray.getJSONObject(i);
						Map<String, Object> map=new HashMap<String, Object>();
						map.put("datalog_sn", jsonObject.get("datalog_sn").toString());
						map.put("lost", jsonObject.get("lost").toString());
						map.put("alias", jsonObject.get("alias").toString());
						map.put("device_type", jsonObject.get("device_type").toString());
						map.put("update_interval", jsonObject.get("update_interval").toString());
						map.put("unit_id", jsonObject.get("unit_id").toString());
						map.put("client_url", jsonObject.get("client_url").toString());
						list.add(map);
					}
					count=list.size();
				   adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void error(String json) {
				
			}
		});
	}


	private GetWifiList md;
	public void dataloggers(final int position){
		String a=getResources().getString(R.string.dataloggers_dialog_change);
		String b=getResources().getString(R.string.dataloggers_dialog_Configuration);
		String c=getResources().getString(R.string.dataloggers_dialog_delete);
		AlertDialog builder = new AlertDialog.Builder(this).setTitle(R.string.dataloggers_title).
				setItems(new String[] {a ,b,c}, new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int swichs) {
				ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
				switch (swichs) {
				case 0:
					updateAlias(position);
					break;
				case 1:
					MyControl.configWifiServer(DataloggersActivity.this,String.valueOf(list.get(position).get("device_type")),list.get(position).get("datalog_sn").toString());
//					if(wifi == State.CONNECTED||wifi==State.CONNECTING){
//						String device_type=String.valueOf(list.get(position).get("device_type"));
//						if("shinewifi".equals(device_type.toLowerCase())){
//							Map<String, Object> map = new GetWifiInfo(DataloggersActivity.this).Info();
//							Log.i(TAG, "onClick: "+map.get("mAuthString").toString());
////							if(map.get("mAuthString").toString().equals("")){
//							if(TextUtils.isEmpty(map.get("mAuthString").toString())){
//								AlertDialog builder = new AlertDialog.Builder(DataloggersActivity.this).setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_connectwifi).setPositiveButton(R.string.all_ok, new OnClickListener() {
//
//									@Override
//									public void onClick(DialogInterface arg0, int arg1) {
//										arg0.dismiss();
//									}
//								}).create();
//								builder.show();
//								return;
//							}
//							Intent intent=new Intent(DataloggersActivity.this,SmartConnection.class);
//							Bundle bundle=new Bundle();
//							bundle.putString("type","3");
//							bundle.putString("id",list.get(position).get("datalog_sn").toString());
//							bundle.putString("ssid",map.get("ssid").toString());
//							bundle.putString("mAuthString",map.get("mAuthString").toString());
//							bundle.putByte("mAuthMode",(Byte) map.get("mAuthMode"));
//							intent.putExtras(bundle);
//							startActivity(intent);
//						}else{
//							md=new GetWifiList(DataloggersActivity.this);
//							md.setCancelable(false);
//							md.show();
//						}
//					}else{
//						toast(R.string.all_wifi_failed);
//						AlertDialog builder = new AlertDialog.Builder(DataloggersActivity.this).setTitle(R.string.all_prompt).setMessage(R.string.dataloggers_dialog_connectwifi).setPositiveButton(R.string.all_ok, new OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface arg0, int arg1) {
//								arg0.dismiss();
//							}
//						}).create();
//						builder.show();
//					}
					break;
				case 2:
					delectdatalog(position);
					break;

				default:
					break;
				}
				
			}
		}).setNegativeButton(R.string.all_no, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		}).create();
		builder.show();
	}
	public void updateAlias(final int position){
		final EditText et=new EditText(this);
		et.setMaxEms(24);
		et.setHint(list.get(position).get("alias").toString());
		AlertDialog builder = new AlertDialog.Builder(this).setTitle(R.string.dataloggers_dialog_change).setMessage(R.string.dataloggers_dialog_edittext).setView(et)
				.setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Mydialog.Show(DataloggersActivity.this, "");
						PostUtil.post(new Urlsutil().datalogAupdate, new postListener() {
							
							@Override
							public void success(String json) {
								Mydialog.Dismiss();
								try {
									JSONObject jsonObject=new JSONObject(json);
									String success=jsonObject.get("success").toString();
									if(success.equals("false")){
										toast(R.string.all_failed);
									}else{
										toast(R.string.all_success);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							
							@Override
							public void Params(Map<String, String> params) {
								params.put("datalogSN",list.get(position).get("datalog_sn").toString() );
								params.put("alias", et.getText().toString());
								params.put("unitId",list.get(position).get("update_interval").toString());
							}

							@Override
							public void LoginError(String str) {
								// TODO Auto-generated method stub
								
							}
						});
					}
				}).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).create();
		builder.show();
	}

	public void delectdatalog(final int position){
		AlertDialog builder = new AlertDialog.Builder(this).setTitle(R.string.dataloggers_declte_title).setMessage(R.string.dataloggers_declte_prompt)
				.setPositiveButton(R.string.all_ok,new  DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Mydialog.Show(DataloggersActivity.this, "");
						PostUtil.post(new Urlsutil().datalogAdel, new postListener() {
							
							@Override
							public void success(String json) {
								Mydialog.Dismiss();
								try {
									JSONObject jsonObject=new JSONObject(json);
									String success=jsonObject.get("success").toString();
									if(success.equals("false")){
										toast(R.string.all_failed);
									}else{
										toast(R.string.all_success);
										list.remove(position);
										adapter.notifyDataSetChanged();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							
							@Override
							public void Params(Map<String, String> params) {
								params.put("datalogSN",list.get(position).get("datalog_sn").toString() );
							}

							@Override
							public void LoginError(String str) {
								// TODO Auto-generated method stub
								
							}
						});
						arg0.dismiss();
					}
				}).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
						
					}
				}).create();
		builder.show();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 101:
			if(data!=null){
				Bundle bundle=data.getExtras();
				final String s=bundle.getString("result");
				System.out.println("result="+s);
				Mydialog.Show(DataloggersActivity.this, "");
				PostUtil.post(new Urlsutil().addDatalog, new postListener() {
					
					@Override
					public void success(String json) {
						Mydialog.Dismiss();
						try {
							JSONObject jsonObject=new JSONObject(json);
							String str=jsonObject.get("msg").toString();
							if(str.equals("200")){
								toast(R.string.all_success);
							}
							if(str.equals("501")){
								toast(R.string.dataloggers_add_no_server);
							}
							if(str.equals("502")){
								toast(R.string.dataloggers_add_no_jurisdiction);
							}
							if(str.equals("503")){
								toast(R.string.dataloggers_add_no_numberandplant);
							}
							if(str.equals("504")){
								toast(R.string.dataloggers_add_no_full);
							}
							if(str.equals("505")){
								toast(R.string.dataloggers_add_no_full);
							}
							if(str.equals("506")){
								toast(R.string.dataloggers_add_no_exist);
							}
							if(str.equals("507")){
								toast(R.string.dataloggers_add_no_matching);
							}
							if(str.equals("701")){
								toast(R.string.m7);
							}
							
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					
					@Override
					public void Params(Map<String, String> params) {
						params.put("plantID", id);
						params.put("datalogSN", s);
//						params.put("validCode", AppUtils.validateWebbox(s));
						params.put("validCode", "");
					}

					@Override
					public void LoginError(String str) {
						// TODO Auto-generated method stub
						
					}
				});
			}
			
			break;
		case 105:
			if(data!=null){
				Bundle bundle=data.getExtras();
				final String s=bundle.getString("result");
				System.out.println("result="+s);
				Mydialog.Show(this, "");
				PostUtil.post(new Urlsutil().addDatalog, new postListener() {
					
					@Override
					public void success(String json) {
						Log.i("tag", json);
						try {
							Mydialog.Dismiss();
							JSONObject jsonObject=new JSONObject(json);
							String msg=jsonObject.get("msg").toString();
							if(msg.equals("200")){
								toast(R.string.all_success_reminder);
								//TODO 锟斤拷映晒锟斤拷锟窖拷杉锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟角凤拷锟斤拷锟斤拷锟斤拷
								String url=new Urlsutil().getDatalogInfo+"&datalogSn="+s;
								//getDataLogInfo(url);
								
								//锟斤拷映晒锟斤拷锟斤拷锟阶拷锟斤拷锟斤拷锟絯ifi锟侥斤拷锟斤拷
								AddToSetWifi(s, jsonObject);
							}else if(msg.equals("501")){
								toast(R.string.serviceerror);
								
							}else if(msg.equals("502")){
								toast(R.string.dataloggers_add_no_jurisdiction);
								
							}else if(msg.equals("503")){
								toast(R.string.dataloggers_add_no_number);
								
							}else if(msg.equals("504")){
								toast(R.string.dataloggers_add_no_v);
								
							}else if(msg.equals("505")){
								toast(R.string.dataloggers_add_no_full);
								
							}else if(msg.equals("506")){
								toast(R.string.dataloggers_add_no_exist);
								
							}else if(msg.equals("507")){
								toast(R.string.dataloggers_add_no_matching);
							}else if ("701".equals(msg)){
								toast(R.string.m7);
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
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2:
				Mydialog.Dismiss();
				Intent intent=new Intent(DataloggersActivity.this,AhToolActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("SSID",ssidmap.get("SSID").toString());
				bundle.putString("WifiPwd","12345678");
				bundle.putString("et",ssidmap.get("SSID").toString());
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case 3:
				Mydialog.Dismiss();
				toast(R.string.all_error);
				break;
			case 100:
				 page++;
				 if(count%20!=0){
					 toast(getString(R.string.DataloggersAct_more_data));
					 listview.removeFooterView(moreView);
				 }
				 loadData();  //���ظ������ݣ��������ʹ���첽����
                 
				break;
			}
		};
	};
	private HashMap<String, String> ssidmap;
	private List<HashMap<String, String>> wifilists;
	public class GetWifiList extends Dialog{
		private EditText et;
		public GetWifiList(Context context) {
			super(context,R.style.dialog1);
			LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View  view=inflater.inflate(R.layout.inputwifiwindow, null);
			Button bt1=(Button)view.findViewById(R.id.button1);
			Button bt2=(Button)view.findViewById(R.id.button2);
			et=(EditText)view.findViewById(R.id.editText_pwd);
			et.setText("12345678");
			final ListView listView=(ListView)view.findViewById(R.id.listView1);
			InputWifi.getwifi(DataloggersActivity.this, listView, new InputListener() {

				@Override
				public void Error() {
					toast(R.string.dataloggers_inquire_no);
				}

				@Override
				public void Right(List<HashMap<String, String>> WiFiList) {
					wifilists = WiFiList;
				}
			});
			listView.setOnItemClickListener(new OnItemClickListener() {


				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					md.dismiss();
					ssidmap=wifilists.get(position);
					Mydialog.Show(DataloggersActivity.this,"");
					new WifiConnector(DataloggersActivity.this, new WifiConnectListener() {

						@Override
						public void OnWifiConnectCompleted(boolean isConnected) {
							if(isConnected==true){
								handler.sendEmptyMessage(2);
							}else{
								handler.sendEmptyMessage(3);
							}
						}
					}).connect(ssidmap.get("SSID"),et.getText().toString(), SecurityMode.WPA);
				}
			});
			bt1.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					InputWifi.getwifi(DataloggersActivity.this, listView, new InputListener() {

						@Override
						public void Error() {
							toast(R.string.dataloggers_inquire_no);
						}

						@Override
						public void Right(List<HashMap<String, String>> WiFiList) {
							wifilists=WiFiList;
						}
					});
				}
			});
			bt2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					md.dismiss();
				}
			});
			setContentView(view);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
			sendBroadcast(intent);
		}
		return super.onKeyDown(keyCode, event);
	}

}
