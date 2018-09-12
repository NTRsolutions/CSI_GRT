package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.UserAdapter;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.PostUtil.postListener;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class UserActivity extends DoActivity {

	private List<Map<String, Object>>list;
	private String[]strs={Cons.userBean.phoneNum,Cons.userBean.email};
	private int[]images={R.drawable.phonenumber,R.drawable.email};
	private UserAdapter adapter;
	private ListView listview;
	private View parentView;
	private RelativeLayout relative;
//	private RelativeLayout relavite;
	private Button finish;
	private RelativeLayout agentCode;
//	private TextView agent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_user, null);
		setContentView(parentView);
		initHeaderView();
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
			}
		});
		setHeaderTitle(headerView,getString(R.string.user_title));
	}
	private void SetViews() {
		list=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < images.length; i++) {
			Map<String, Object>map=new HashMap<String, Object>();
			map.put("image", images[i]);
			map.put("str", strs[i]);
			list.add(map);
		}
		File file=new File(ShineApplication.IMAGE_FILE_LOCATION);
		if(!file.exists()){
			file.mkdirs();
		}
		finish=(Button) findViewById(R.id.finish);
		relative=(RelativeLayout)findViewById(R.id.relative);
		agentCode=(RelativeLayout)findViewById(R.id.relative1);
		
		listview=(ListView)findViewById(R.id.listView1);
		adapter=new UserAdapter(UserActivity.this, list);
		listview.setAdapter(adapter);
		
	}

	private int positions;
	private void SetListeners() {
		finish.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new Builder(UserActivity.this);
				builder.setTitle(R.string.all_prompt).setMessage(R.string.user_islogout).setPositiveButton(R.string.all_no,new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				}).setNegativeButton(R.string.all_ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
						//ɾ��������Ϣ��¼
						SqliteUtil.delectmessages();
						SqliteUtil.url("");
						SqliteUtil.plant("");
						Cons.guestyrl="";
						Cons.isflag=false;
						Cons.plants.clear();
						Cons.plant=null;
						SqliteUtil.url("");
						JPushInterface.setAlias(UserActivity.this,"",new TagAliasCallback() {
							
							@Override
							public void gotResult(int arg0, String alias, Set<String> tags) {
								if(arg0==0){
									System.out.println("设置别名成功");
									System.out.println(arg0);
									System.out.println(alias);
								}else{
									System.out.println("设置别名失败");
								}
								
							}
						});
						startActivity(new Intent(UserActivity.this,LoginActivity.class));
						finish();
					}
				}).create();
				builder.show();
			}
		});
		relative.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(UserActivity.this,UpdatepwdActivity.class));
			}
		});
		agentCode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new Builder(UserActivity.this);
				final EditText editText=new EditText(UserActivity.this);
				builder.setTitle(R.string.userdata_changeagent).setMessage(R.string.userdata_message).setView(editText).setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
						if(Cons.isflag==true){
							toast(R.string.all_experience);
							return;
						}
						final String et=editText.getText().toString();
						if(et.equals("")){
							toast(R.string.all_blank);
							return;
						}
						Mydialog.Show(UserActivity.this,"");
						PostUtil.post(new Urlsutil().updateUser, new postListener() {
							
							@Override
							public void success(String json) {
								Mydialog.Dismiss();
								try {
									JSONObject jsonObject=new JSONObject(json);
									if(jsonObject.get("success").toString().equals("true")){
									toast(R.string.all_success);
//									agent.setText(et);
									}else{
										toast(R.string.all_failed);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
							
							@Override
							public void Params(Map<String, String> params) {
								params.put("accountName", Cons.userBean.accountName);
								params.put("agentCode",editText.getText().toString() );
								
							}

							@Override
							public void LoginError(String str) {
								// TODO Auto-generated method stub
								
							}
						});
					}
				}).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						arg0.dismiss();
					}
				}).create();
				builder.show();
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,long arg3) {
				positions=position;
//				if(position==0){
//					Intent intent=new Intent(UserActivity.this,ChangPhoneActivity.class);
//					Bundle bundle=new Bundle();
//					bundle.putString("type", "1");
//					bundle.putString("PhoneNum", Cons.userBean.phoneNum);
//					bundle.putString("email",Cons.userBean.email);
//					intent.putExtras(bundle);
//					startActivityForResult(intent, 103);
//					return;
//				}
				Intent intent=new Intent(UserActivity.this,AmendsActivity.class);
				Bundle bundle=new Bundle();
				if(position==0){
					bundle.putString("type", "1");
				}else if(position==1){
					bundle.putString("type", "2");
				}else if(position==2){
					bundle.putString("type", "3");
				}
				bundle.putString("PhoneNum", Cons.userBean.phoneNum);
				bundle.putString("email",Cons.userBean.email);
				intent.putExtras(bundle);
				startActivityForResult(intent, 103);
			}
		});
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case 103:
			Map<String, Object> map = list.get(positions);
			switch (resultCode) {
			case 1:
				toast(R.string.all_success);
				String PhoneNum=data.getStringExtra("PhoneNum");
				map.put("str", PhoneNum);
//				Cons.userBean.phoneNum=PhoneNum;
				adapter.notifyDataSetChanged();
				break;
			case 2:
				toast(R.string.all_success);
				String email=data.getStringExtra("email");
				map.put("str", email);
//				Cons.userBean.email=email;
				adapter.notifyDataSetChanged();
				break;
			
			default:
				break;
			}
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
