package com.growatt.shinephone.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.RegionAdapter;
import com.growatt.shinephone.adapter.SNAdapter;
import com.growatt.shinephone.bean.ImageItem;
import com.growatt.shinephone.util.AddCQ;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.Bimp;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.FileUtils;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.PostUtil;
import com.growatt.shinephone.util.PostUtil.postListener;
import com.growatt.shinephone.util.Urlsutil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PutinActivity extends DoActivity {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop;
	private View views;
	private View ll_popup;
	private String pathImage;
	private Button bt1;
	private Button bt2;
	private Button bt3;
	private RelativeLayout parent;
	private static final int TAKE_PICTURE = 22;//���
	private final int IMAGE_OPEN = 11;        //��ͼƬ���
	private Button add;
	private EditText et1;
	private EditText et2;
	private Thread thread;
	private Spinner spinner;
	private RegionAdapter spinneradapter;
	private List<Map<String, Object>> lists;
	private TextView textView3;
	private String result="";
	private String ids="";
	public static List<String>list;
	private String questiontype;
	//private EditText et4;
	private int[]strs={R.string.PutingAct_etQuestion,R.string.putin_problem_item2,R.string.putin_problem_item3
			,R.string.putin_problem_item4,R.string.putin_problem_item5,R.string.putin_problem_item6
			,R.string.putin_problem_item7};
	//�������кſ�ѡ
//	private Spinner snSpinner;
	private EditText etSN;
	private Button btnSearch;
    List<String> snList;
    SNAdapter snAdapter;
    private PopupWindow popup;
//	String sn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_putin, null);
		setContentView(parentView);
		initHeaderView();
		SetViews();
		setIntent();
		Setlisteners();
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
		setHeaderTitle(headerView,getString(R.string.putin_title));
	}
	private void setIntent() {
		Intent intent=getIntent();
		if(intent!=null){
			Bundle bundle=intent.getExtras();
			if(bundle!=null){
			String title=bundle.getString("title");
			//String deviceType=bundle.getString("deviceType");
			//String deviceSerialNum=bundle.getString("deviceSerialNum");
			et1.setText(title);
			//et4.setText(deviceSerialNum);
			}
		}
	}
	private void SetViews() {
		list=new ArrayList<String>();
		lists=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < 7; i++) {
			Map<String, Object>map=new HashMap<String, Object>();
			map.put("plantName",getResources().getString(strs[i]));
			lists.add(map);
		}
		add=(Button)findViewById(R.id.button1);
		et1=(EditText)findViewById(R.id.editText1);
		et2=(EditText)findViewById(R.id.editText2);
		
//		snSpinner=(Spinner) findViewById(R.id.editText4);
        snList=new ArrayList<String>();
//		snList.add(getResources().getString(R.string.putin_problem_item1));
//	    snAdapter=new SNAdapter(this, snList);
//	    snSpinner.setAdapter(snAdapter);
		//et4=(EditText)findViewById(R.id.editText4);
		etSN=(EditText)findViewById(R.id.et_sn);
		btnSearch=(Button)findViewById(R.id.btn_search);
		
		
		textView3=(TextView)findViewById(R.id.textView3);
		spinner=(Spinner)findViewById(R.id.spinner1);
		pop = new PopupWindow(PutinActivity.this);
		views = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
		ll_popup = (LinearLayout) views.findViewById(R.id.ll_popup);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(views);
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		parent = (RelativeLayout) views.findViewById(R.id.parent);
		bt1 = (Button) views.findViewById(R.id.item_popupwindows_camera);
		bt2 = (Button) views.findViewById(R.id.item_popupwindows_Photo);
		bt3 = (Button) views.findViewById(R.id.item_popupwindows_cancel);
		spinneradapter=new RegionAdapter(PutinActivity.this,lists, false);
		spinner.setAdapter(spinneradapter);
		
	}
	public void getSN(View v, final List<String> sns){
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.popop_plantnames, null);
		ListView lv=(ListView)contentView.findViewById(R.id.listView1);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				String sn=sns.get(position);
				etSN.setText(sn);
				popup.dismiss();
			}
		});
		snAdapter=new SNAdapter(this, sns);
		lv.setAdapter(snAdapter);
		popup = new PopupWindow(contentView,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popup.setTouchable(true);
		popup.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		popup.setBackgroundDrawable(new ColorDrawable(0));
		// ���úò���֮����show
		popup.showAsDropDown(v);
	}
	private void Setlisteners() {
          btnSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(final View arg0) {
				Mydialog.Show(PutinActivity.this, "");
				PostUtil.post(new Urlsutil().getQualityInformation, new postListener() {
					
					@Override
					public void success(String json) {
						try {
							JSONArray jsonArray=new JSONArray(json);
							List<String> sns=new ArrayList<String>();
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject jsonObject=jsonArray.getJSONObject(i);
								sns.add(jsonObject.get("deviceSN").toString());
							}
							Collections.sort(sns);
					        getSN(arg0,sns);
							Mydialog.Dismiss();
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
					
					@Override
					public void Params(Map<String, String> params) {
//						params.put("plantId", Cons.plants.get(0).get("plantId").toString());
						params.put("plantId", Cons.plant);
						params.put("pageNum", "1");
						params.put("pageSize", "100");
						Locale locale = getResources().getConfiguration().locale;
						String language = locale.getLanguage().toLowerCase();
						int a;
						if(language.contains("cn")||language.contains("zh")){
							a=0;
						}else if(language.contains("en")){
							a=1;
							
						}else{
							a=3;
						}
					}

					@Override
					public void LoginError(String str) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		   });
	
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long id) {
				if(position==0){
					questiontype="6";
				}else{
					questiontype=position+"";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(Cons.isflag==true&&!"guest".equals(Cons.userBean.getAccountName())){
					toast(R.string.all_experience);
					return;
				}
				
				if(et1.getText().toString().equals("")){
					toast(R.string.putin_on_title);
					return;
				}
				if(et2.getText().toString().equals("")){
					toast(R.string.putin_on_data);
					return;
				}
//				if(TextUtils.isEmpty(etSN.getText().toString())){
//					toast(R.string.PutinActivity_putin_sn);
//					return;
//				}
				map=new HashMap<String, Object>();
				map.put("title", et1.getText().toString());
				map.put("content", et2.getText().toString());
				map.put("userId",Cons.userId);
				map.put("questionType",questiontype);
				map.put("questionDevice",etSN.getText().toString());
				System.err.println("list="+list.size());
				if(list.size()>0){
					if(list.size()==1){
						map.put("image1",list.get(0));
						map.put("image2","");
						map.put("image3","");
					}else if(list.size()==2){
						map.put("image1",list.get(0));
						map.put("image2",list.get(1));
						map.put("image3","");
					}else if(list.size()==3){
						map.put("image1",list.get(0));
						map.put("image2",list.get(1));
						map.put("image3",list.get(2));
					}
				}else{
					map.put("image1","");
					map.put("image2","");
					map.put("image3","");
				}
				Mydialog.Show(PutinActivity.this,"");
				AddCQ.postUp(new Urlsutil().AddCQ, map, new GetListener() {
					
					@Override
					public void success(String res) {
						if(!TextUtils.isEmpty(res)){
							Message msg=new Message();
							msg.what=1;
							msg.obj=res;
							mhandler.sendMessage(msg);
						}
					}
					
					@Override
					public void error(String json) {
					}
				});
			}
		});
		parent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
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
				//				overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
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
		
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {


			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					ll_popup.startAnimation(AnimationUtils.loadAnimation(PutinActivity.this,R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Deletedialog(arg2);

				}
			}
		});

	}
	protected void Deletedialog(final int position) {
		AlertDialog.Builder builder = new Builder(PutinActivity.this);
		builder.setMessage(R.string.putin_delete_message);
		builder.setTitle(R.string.all_prompt);
		builder.setPositiveButton(R.string.all_ok, new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
				Bimp.tempSelectBitmap.remove(position);
				list.remove(position);
				adapter.notifyDataSetChanged();
				toast(R.string.all_success);
			}
		});
		builder.setNegativeButton(R.string.all_no,new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		});
		builder.create().show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 3:
			if(resultCode==2){
				result=data.getStringExtra("device");
				ids=data.getStringExtra("id");
				textView3.setText(result);
			}
			break;
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				String fileName = String.valueOf(System.currentTimeMillis())+".png";
				bm=AppUtils.comp(bm);
				FileUtils.saveBitmap(bm,ShineApplication.Path+"/"+fileName, fileName);
				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);
				list.add(ShineApplication.Path+"/"+fileName);
				adapter.notifyDataSetChanged();
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
					pathImage = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)); 
					if(!TextUtils.isEmpty(pathImage)){
						String fileName = String.valueOf(System.currentTimeMillis())+".png";
						Bitmap addbmp=BitmapFactory.decodeFile(pathImage);
						addbmp=AppUtils.comp(addbmp);
						FileUtils.saveBitmap(addbmp,ShineApplication.Path+"/"+fileName, fileName);
						ImageItem takePhoto = new ImageItem();
						takePhoto.setBitmap(addbmp);
						Bimp.tempSelectBitmap.add(takePhoto);
						list.add(ShineApplication.Path+"/"+fileName);
					}
					if(VERSION.SDK_INT < 14) {  
						cursor.close();  
					} 
				}
			}
			adapter.notifyDataSetChanged();
			break;
		}

	}

	public void newphoto() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}
	public void oldphone(){
		Intent intent = new Intent(Intent.ACTION_PICK,       
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);  
		startActivityForResult(intent, IMAGE_OPEN); 
	}
	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if(Bimp.tempSelectBitmap.size() == 9){
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position ==Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	@Override
	protected void onDestroy() {
		if(Bimp.tempSelectBitmap.size()!=0){
			Bimp.tempSelectBitmap.clear();
		}
		if(list.size()!=0){
			list.clear();
		}
		if (thread != null) { 
			thread.interrupt();// �ж��߳� 
			thread = null; 
		} 
		super.onDestroy();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
//			overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		}
		return super.onKeyDown(keyCode, event);
	}
	private Handler mhandler=new Handler(){
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 1:
				String s=(String) msg.obj;
				Mydialog.Dismiss();
				if(s.contains("true")){
					toast(R.string.all_success);
					sendBroadcast(new Intent(Constant.MyQuestionfragment_Receiver));
					finish();
				}else{
					toast(R.string.all_failed);
				}
				break;

			default:
				break;
			}
		};
	};
	private Map<String, Object> map;

}
