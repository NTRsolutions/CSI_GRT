package com.growatt.shinephone.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.Powerdata;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.LoadLocalImageUtil;
import com.growatt.shinephone.util.Mydialog;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.UpdateplantUtil;
import com.growatt.shinephone.util.Urlsutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PlantImagesActivity extends DoActivity {
	private static final int TAKE_PICTURE = 22;//���
	private static final int IMAGE_OPEN = 11;        //��ͼƬ���
	private View parentView;
	private PopupWindow pop;
	private View views;
	private LinearLayout ll_popup;
	private RelativeLayout parent;
	private Button bt1;
	private Button bt2;
	private Button bt3;
	private ImageView imageview1;
	int index=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_plant_phone, null);
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
		setHeaderTitle(headerView,getString(R.string.plantadmin_pic));
	}
	private void SetViews() {
		imageview1=(ImageView)findViewById(R.id.imageView1);
		imageview1.setImageResource(R.drawable.pic_service);
		pop = new PopupWindow(PlantImagesActivity.this);
		views = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
		ll_popup = (LinearLayout) views.findViewById(R.id.ll_popup);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(views);
		parent = (RelativeLayout) views.findViewById(R.id.parent);
		bt1 = (Button) views.findViewById(R.id.item_popupwindows_camera);
		bt2 = (Button) views.findViewById(R.id.item_popupwindows_Photo);
		bt3 = (Button) views.findViewById(R.id.item_popupwindows_cancel);
		if(PlantAdminActivity.powerdata!=null){
			String name=PlantAdminActivity.powerdata.getPlantImgName();
			log("name:"+name);
			if((!TextUtils.isEmpty(name))&&name!="null"&&(!TextUtils.isEmpty(Cons.userBean.getAccountName()))){
				ImageHttp.ImageLoader(imageview1, new Urlsutil().getPlantImageInfo+Cons.userBean.getAccountName()+"/"+name,false);

//				ImageHttp.LoadImage(imageview1, new Urlsutil().plantAgetImg+"&id="+Cons.plant+"----",name);
			}
			}
	}

	private void SetListeners() {
		imageview1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				index=1;
				ll_popup.startAnimation(AnimationUtils.loadAnimation(PlantImagesActivity.this,R.anim.activity_translate_in));
				pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
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
	}
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			Mydialog.Dismiss();
			switch (msg.what) {
			case 0:
				String str=(String) msg.obj;
				if(str.equals("200")){
					toast(R.string.all_success);
					Bitmap addbmp=null;
					try {
						InputStream in=new FileInputStream(ShineApplication.plantimage);
						addbmp=LoadLocalImageUtil.compress(in, PlantImagesActivity.this, imageview1);
						in.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(addbmp!=null){
						imageview1.setImageBitmap(addbmp);
						}
					PlantAdminActivity.powerdata.setImage1(null);
				}else if(str.equals("501")){
					toast(R.string.geographydata_change_no_data);
				}else if(str.equals("502")){
					toast(R.string.geographydata_change_no_namerepetition);
				}else if(str.equals("503")){
					toast(R.string.geographydata_change_no_picture);
				}else if(str.equals("504")){
					toast(R.string.geographydata_change_no_map);
				}else if ("701".equals(str)){
					toast(R.string.m7);
				}
				break;
			case 1:
				Mydialog.Dismiss();
				toast(R.string.geographydata_change_no_data);
				break;
			default:
				break;
			}
		};
	};
	public void updateplant(){
		UpdateplantUtil.updateplant(PlantImagesActivity.this, handler);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if(data!=null&& resultCode == RESULT_OK){
				Bitmap bm = (Bitmap) data.getExtras().get("data");
//				Mydialog.Show(PlantImagesActivity.this,R.string.all_uploading);
				File file=new File(ShineApplication.plantimage);
				if(!file.exists()){
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				Bitmap bit=AppUtils.comp(bm);
				FileOutputStream fos=null;
				try {
					fos = new FileOutputStream(file);
					bit.compress(CompressFormat.JPEG, 100, fos);
					fos.flush();
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					//������Դ
					if(bm!=null&&!bm.isRecycled()){
						bm.recycle();
						bm=null;
					}
					if(bit!=null&&!bit.isRecycled()){
						bit.recycle();
						bit=null;
					}
					System.gc();
				}
				if(PlantAdminActivity.powerdata==null){
                	PlantAdminActivity.powerdata=new Powerdata();
                }
				PlantAdminActivity.powerdata.setImage1(ShineApplication.plantimage);
				updateplant();
			}
			break;
		case IMAGE_OPEN:
			if(data!=null&&resultCode==RESULT_OK ){
//					Mydialog.Show(PlantImagesActivity.this,R.string.all_uploading);
					Uri uri = data.getData();
					System.out.println(uri);
					if (!TextUtils.isEmpty(uri.getAuthority())) {
						//��ѯѡ��ͼƬ
						Cursor cursor = getContentResolver().query(uri,new String[] { MediaStore.Images.Media.DATA },null,null,null);  
						//���� û�ҵ�ѡ��ͼƬ  
						if (null == cursor) {  
							return;  
						}  
						cursor.moveToFirst();
						String pathImage = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
						System.out.println(pathImage);
						if(cursor!=null&&!cursor.isClosed()){
							   cursor.close();
							   cursor=null;
							}
						System.gc();
						File file=new File(ShineApplication.plantimage);
						Bitmap addbmp=BitmapFactory.decodeFile(pathImage);
						if(!file.exists()){
							try {
								file.createNewFile();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						Bitmap bit=AppUtils.comp(addbmp);
						FileOutputStream fos;
						try {
							fos = new FileOutputStream(file);
							bit.compress(CompressFormat.JPEG, 100, fos);
							fos.flush();
							fos.close();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							//������Դ
							if(addbmp!=null&&!addbmp.isRecycled()){
								addbmp.recycle();
								addbmp=null;
							}
							if(bit!=null&&!bit.isRecycled()){
								bit.recycle();
								bit=null;
							}
							System.gc();
						}
						 if(PlantAdminActivity.powerdata==null){
	                        	PlantAdminActivity.powerdata=new Powerdata();
	                        }
						PlantAdminActivity.powerdata.setImage1(ShineApplication.plantimage);
						if(!TextUtils.isEmpty(pathImage)){
							updateplant();
						}

					}
			}
			break;

		default:
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
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


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
