package com.growatt.shinephone.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.DatadetailedAdapter;
import com.growatt.shinephone.adapter.DatadetailedGridAdapter;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.AddCQ;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.FileUtils;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatadetailedActivity extends DoActivity {
	/**
	 */
//	if(VERSION.SDK_INT < 14) {  
//		cursor.close();  
//	} 
	private TextView title;
	private ListView listview; 
	private List<Map<String, Object>>list;
	private DatadetailedAdapter adapter=null;
	private String id;
	private EditText et1;
	private Button ok;
	private ImageView image;
	private Uri photoUri;
	private String picPath;
//	private Thread thread;
	private TextView tv;
	private GridView gridview;
	private PopupWindow pop;
	private View view;
	private LinearLayout ll_popup;
	private RelativeLayout parent;
	private Button bt1;
	private Button bt2;
	private Button bt3;
	private View parentView;
	private InputMethodManager imm;
	private boolean flag=false;
	private TextView leixing;
	private HashMap<String, Object> countmap;
	private List<String> gridlists;
	private int[]strs={R.string.PutingAct_etQuestion,R.string.putin_problem_item2,R.string.putin_problem_item3
			,R.string.putin_problem_item4,R.string.putin_problem_item5,R.string.putin_problem_item6
			,R.string.putin_problem_item7};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_datadetailed, null);
		setContentView(parentView);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
//		if(bundle.containsKey(""))
		id=bundle.getString("id");
		log("userId="+Cons.userId);
		if(TextUtils.isEmpty(Cons.userId)){
			List<Map<String, Object>> sss = SqliteUtil.inquiryids(SqliteUtil.inquiryplant());
			Cons.userId=sss.get(0).get("userId").toString();
			log("userId="+Cons.userId);
		}
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		initHeaderView();
		SetViews();
		SetListenes();
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
		setHeaderTitle(headerView,getString(R.string.datadetailed_title));
	}
	private void SetViews() {
		et1=(EditText)findViewById(R.id.editText1);
		ok=(Button)findViewById(R.id.button1);
		image=(ImageView)findViewById(R.id.imageView1);
		title=(TextView)findViewById(R.id.textView2);
		
		leixing=(TextView)findViewById(R.id.textView4);
		listview=(ListView)findViewById(R.id.listView1);
		LayoutInflater lif = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View  headerView =lif.inflate(R.layout.headview,listview, false);
		tv=(TextView)headerView.findViewById(R.id.textView_content);
		gridview=(GridView)headerView.findViewById(R.id.gridView1);
		RelativeLayout r1 = (RelativeLayout)headerView.findViewById(R.id.r1);
		r1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		listview.addHeaderView(headerView);
		pop = new PopupWindow(DatadetailedActivity.this);
		view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setFocusable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		parent = (RelativeLayout) view.findViewById(R.id.parent);
		bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
	}
	public void post_upload(){
		  AddCQ.postUp(new Urlsutil().replyMessage, map, new GetListener() {
				
				@Override
				public void success(String json) {
					if(TextUtils.isEmpty(json)) return;
					Message msg=new Message();
					msg.what=1;
					msg.obj=json;
					handler.sendMessage(msg);
				}
				@Override
				public void error(String json) {
				}
			});
	  }
	private void SetListenes() {
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(DatadetailedActivity.this,PhotoenlargeActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("name", gridlists.get(position));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		parent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(Cons.isflag==true){
					toast(R.string.all_experience);
					return;
				}
				if( et1.getText().toString().equals("")){
					return;
				}
				map=new HashMap<String, Object>();
				map.put("questionId", id);
				map.put("image1", "");
				map.put("image2","");
				map.put("image3","");
				map.put("userId", Cons.userId);
				map.put("message", et1.getText().toString());
				Mydialog.Show(DatadetailedActivity.this, "");
				post_upload();
			}
		});

		image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(Cons.isflag==true){
					toast(R.string.all_experience);
					return;
				}
				imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);//���ؼ���
				ll_popup.startAnimation(AnimationUtils.loadAnimation(DatadetailedActivity.this,R.anim.activity_translate_in));
				pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			}
		});
		bt1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				newphoto();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				oldphone();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		Mydialog.Show(DatadetailedActivity.this, "");
		GetUtil.get(new Urlsutil().getQuestionInfoDetail+"&"+"questionId="+id+"&userId="+Cons.userId, new GetListener() {

			@Override
			public void success(String json) {
				Mydialog.Dismiss();
				try {
					JSONObject jsonObject=new JSONObject(json);
					countmap=new HashMap<String, Object>();
					countmap.put("id", jsonObject.get("id").toString());
					countmap.put("questionType", jsonObject.get("questionType").toString());
					countmap.put("userID", jsonObject.get("userID").toString());
					countmap.put("title", jsonObject.get("title").toString());
					countmap.put("questionDevice", jsonObject.get("questionDevice").toString());
					countmap.put("content", jsonObject.get("content").toString());
					countmap.put("attachment", jsonObject.get("attachment").toString());
					countmap.put("remark", jsonObject.get("remark").toString());
					countmap.put("status", jsonObject.get("status").toString());
					countmap.put("createrTime", jsonObject.get("createrTime").toString());
					countmap.put("lastTime", jsonObject.get("lastTime").toString());
					countmap.put("workerId", jsonObject.get("workerId").toString());
					countmap.put("groupId", jsonObject.get("groupId").toString());
					countmap.put("name", jsonObject.get("name").toString());
					countmap.put("country", jsonObject.get("country").toString());
					countmap.put("attachmentList", jsonObject.get("attachmentList").toString());
					countmap.put("accountName", jsonObject.get("accountName").toString());
					JSONArray jsonArray=jsonObject.getJSONArray("serviceQuestionReplyBean");
					list=new ArrayList<Map<String,Object>>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject1=jsonArray.getJSONObject(i);
						Map<String, Object>map=new HashMap<String, Object>();
						map.put("id", jsonObject1.get("id").toString());
						map.put("questionId", jsonObject1.get("questionId").toString());
						map.put("userId", jsonObject1.get("userId").toString());
						map.put("message", jsonObject1.get("message").toString());
						map.put("time", jsonObject1.get("time").toString());
						map.put("userName", jsonObject1.get("userName").toString());
						map.put("attachment", jsonObject1.get("attachment").toString());
						map.put("isAdmin", jsonObject1.get("isAdmin").toString());
						map.put("jobId", jsonObject1.get("jobId").toString());
						map.put("workerId", jsonObject1.get("workerId").toString());
						list.add(map);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				adapter=new DatadetailedAdapter(DatadetailedActivity.this, list);
				listview.setAdapter(adapter);
				listview.setSelection(list.size()-1);
				String titles=countmap.get("title").toString();
				String content=countmap.get("content").toString();
				tv.setText(content);
				title.setText(titles);
				leixing.setText(getString(strs[Integer.parseInt(countmap.get("questionType").toString())]));
				String questionImage = countmap.get("attachment").toString();
				if(questionImage.length()>0&&questionImage.contains(".")){
					List<String> lists=new ArrayList<String>();
					if(questionImage.contains("_")){
						String temp[]=questionImage.split("_");
						for (int i = 0; i < temp.length; i++) {
							lists.add(temp[i]);
						}
					}else{
						lists.add(questionImage);
					}
					gridlists=lists;
					DatadetailedGridAdapter gridadapter = new DatadetailedGridAdapter(DatadetailedActivity.this, lists);
					gridview.setAdapter(gridadapter);
				}
			}

			@Override
			public void error(String json) {
				toast(R.string.all_http_failed);
			}
		});
	}
	public void newphoto() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		try {
			startActivityForResult(openCameraIntent, TAKE_PICTURE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void oldphone(){
		Intent intent = new Intent(Intent.ACTION_PICK,       
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);  
		try {
			startActivityForResult(intent, IMAGE_OPEN);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	private final int TAKE_PICTURE = 22;//���
	private final int IMAGE_OPEN = 11;        //��ͼƬ���
	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (resultCode == RESULT_OK) {
						Bitmap bm = (Bitmap) data.getExtras().get("data");
						long ls=System.currentTimeMillis();
						File file=new File(ShineApplication.Path+"/"+Cons.plant+ls+".png");
						if(!file.exists()){
							try {
								file.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						bm=AppUtils.comp(bm);
						FileUtils.saveBitmap(bm,ShineApplication.Path+"/"+Cons.plant+ls+".png");
						map=new HashMap<String, Object>();
						map.put("questionId", id);
						map.put("image1",ShineApplication.Path+"/"+Cons.plant+ls+".png");
						map.put("image2","");
						map.put("image3","");
						map.put("userId", Cons.userId);
						map.put("message", "");
						Mydialog.Show(DatadetailedActivity.this, "");
						post_upload();
				
			}
			break;
		case IMAGE_OPEN:
			if(resultCode==RESULT_OK ){
				Uri uri = data.getData();  
				System.out.println(uri);
				if (!TextUtils.isEmpty(uri.getAuthority())) {  
					//��ѯѡ��ͼƬ  
					Cursor cursor = getContentResolver().query(  
							uri,  
							new String[] { MediaStore.Images.Media.DATA },  
							null,   
							null,   
							null);  
					//���� û�ҵ�ѡ��ͼƬ  
					if (null == cursor) {  
						return;  
					}  
					cursor.moveToFirst();  
					final String pathImage = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)); 
					final long ls=System.currentTimeMillis();
					if(!TextUtils.isEmpty(pathImage)){
								File file=new File(ShineApplication.Path+"/"+Cons.plant+ls+".png");
								Bitmap addbmp=BitmapFactory.decodeFile(pathImage);
								if(!file.exists()){
									try {
										file.createNewFile();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
								Bitmap bit=AppUtils.comp(addbmp);
								FileUtils.saveBitmap(bit, ShineApplication.Path+"/"+Cons.plant+ls+".png");
								map=new HashMap<String, Object>();
								map.put("questionId", id);
								map.put("image1",ShineApplication.Path+"/"+Cons.plant+ls+".png");
								map.put("image2","");
								map.put("image3","");
								map.put("userId", Cons.userId);
								map.put("message", "");
								Mydialog.Show(DatadetailedActivity.this, "");
								post_upload();
							};
						
					if(VERSION.SDK_INT < 14) {  
						cursor.close();  
					} 
				}
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
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			Mydialog.Dismiss();
			switch (msg.what) {
			case 1:
				 
				if(msg.obj.toString().length()<10){
					toast(R.string.datadetailed_message_failed);
					return;
				}
				toast(R.string.DatadetailedAct_send_success);
				try {
					JSONObject jsonObject1=new JSONObject((String)msg.obj).getJSONObject("questionBean");
					JSONArray jsonArray=jsonObject1.getJSONArray("serviceQuestionReplyBean");
					list=new ArrayList<Map<String,Object>>();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject=jsonArray.getJSONObject(i);
						Map<String, Object>map=new HashMap<String, Object>();
						map.put("id", jsonObject.get("id").toString());
						map.put("questionId", jsonObject.get("questionId").toString());
						map.put("workerId", jsonObject.get("workerId").toString());
						map.put("message", jsonObject.get("message").toString());
						map.put("time", jsonObject.get("time").toString());
						map.put("attachment", jsonObject.get("attachment").toString());
						map.put("isAdmin", jsonObject.get("isAdmin").toString());
						map.put("userName", jsonObject.get("userName").toString());
						map.put("jobId", jsonObject.get("jobId").toString());
						map.put("userId", jsonObject.get("userId").toString());
						map.put("attachmentList", jsonObject.get("attachmentList").toString());
						list.add(map);
					}
//					ImageHttp.copyFile(map.get("imageName").toString(), newPath);
//					if(adapter!=null){
//						adapter.notifyDataSetChanged();
//					}else{
						adapter=new DatadetailedAdapter(DatadetailedActivity.this, list);
						listview.setAdapter(adapter);
						listview.setSelection(list.size()-1);
//					}
					et1.setText("");
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};
	private Map<String, Object> map;


}
