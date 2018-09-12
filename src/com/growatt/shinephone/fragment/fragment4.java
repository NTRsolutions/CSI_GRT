package com.growatt.shinephone.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.AboutActivity;
import com.growatt.shinephone.activity.LoginActivity;
import com.growatt.shinephone.activity.MessagesActivity;
import com.growatt.shinephone.activity.PlantAdminActivity;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.activity.ShineWifiSetActivity;
import com.growatt.shinephone.activity.UserActivity;
import com.growatt.shinephone.adapter.Myadapter;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.ui.HeadImageView;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.LoadLocalImageUtil;
import com.growatt.shinephone.util.T;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class fragment4 extends BaseFragment{
	private View view;
	private ListView listview;
	private ArrayList<Map<String, Object>>list;
	private int[]titles={R.string.fragment4_datamanage,R.string.fragment4_plantset,R.string.m71,R.string.fragment4_message,R.string.fragment4_about};
	private int[] images={R.drawable.ziliao,R.drawable.setup,R.drawable.toolx_icon,R.drawable.message,R.drawable.about};
	private Myadapter adapter;
	private ImageView imageview2;
	private TextView name;
	private PopupWindow pop;
	private View views;
	private LinearLayout ll_popup;

	private Button bt1;
	private Button bt2;
	private Button bt3;
	private RelativeLayout parent;
	//�������ͼ��
 
    /* ����ʶ���� */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
 
    // �ü���ͼƬ�Ŀ�(X)�͸�(Y),480 X 480�������Ρ�
    private static int output_X = 80;
    private static int output_Y = 80;
    View headerView;
	private View footerView;
	private Button btnLogout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			view=inflater.inflate(R.layout.fragment4, container, false);
			SetViews();
			SetListeners();
		return view;
	}
	
	private void SetViews() {
		listview=(ListView)view.findViewById(R.id.listView1);
		LayoutInflater inflater=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		headerView=inflater.inflate(R.layout.header_fragment4, listview,false);
//		headerView=getActivity().getLayoutInflater().inflate(R.layout.header_fragment4, listview,false);
		imageview2=(ImageView)headerView.findViewById(R.id.imageView2);
		//footview
				footerView=getActivity().getLayoutInflater().inflate(R.layout.footer_fragment4, listview,false);
				btnLogout=(Button)footerView.findViewById(R.id.logout);
				listview.addFooterView(footerView);
				btnLogout.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {

						AlertDialog.Builder builder = new Builder(getActivity());
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
								JPushInterface.setAlias(getActivity(),"",new TagAliasCallback() {
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
								startActivity(new Intent(getActivity(),LoginActivity.class));
								getActivity().finish();
							}
						}).create();
						builder.show();
					
					}
				});
		Bitmap bt=null;
		Bitmap bitmap=null;
		try {
			bt = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/"+ShineApplication.IMAGE_FILE_LOCATION);
			if(bt!=null){
				bt=compressBitmap(imageview2);
				imageview2.setImageBitmap(HeadImageView.toRoundBitmap(bt));
			}else{
				bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.demo);
				imageview2.setImageBitmap(bitmap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		if(bt != null && !bt.isRecycled()){ 
//	        // ���ղ�����Ϊnull
//	        bt.recycle(); 
//	        bt = null; 
//	     } 
//		if(bitmap != null && !bitmap.isRecycled()){ 
//	        // ���ղ�����Ϊnull
//	        bitmap.recycle(); 
//	        bitmap = null; 
//	      } 
//	     System.gc();
		name=(TextView)headerView.findViewById(R.id.textView_name);
		name.setText(Cons.userBean.accountName);
		TextPaint tp = name.getPaint();
		tp.setFakeBoldText(true);
		list=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < titles.length; i++) {
			Map<String, Object>map=new HashMap<String, Object>();
			map.put("title", getResources().getString(titles[i]));
			map.put("image", images[i]);
			list.add(map);
		}
		listview.addHeaderView(headerView);
		adapter=new Myadapter(getActivity(), list);
		listview.setAdapter(adapter);
	}
	private void SetListeners() {
		imageview2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(Cons.isflag==true){
					T.make(R.string.all_experience,getActivity());
					return;
				}
				pop = new PopupWindow(getActivity());
				views = getActivity().getLayoutInflater().inflate(R.layout.item_popupwindows, null);
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
				showWindow();
				ll_popup.startAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.activity_translate_in));
				pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			}
		});
		

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				switch (position) {
				case 1:
					startActivity(new Intent(getActivity(),UserActivity.class));
					break;
				case 2:
					startActivity(new Intent(getActivity(),PlantAdminActivity.class));
					break;
				case 4:
					startActivity(new Intent(getActivity(),MessagesActivity.class));
					break;
				case 3:
					startActivity(new Intent(getActivity(),ShineWifiSetActivity.class));
					break;
				case 5:
					startActivity(new Intent(getActivity(),AboutActivity.class));
					break;
				}
			}
		});
	}
	public void showWindow() {
		parent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//				cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(ShineApplication.IMAGE_FILE_LOCATION)));
//				startActivityForResult(cameraintent, 101);
				choseHeadImageFromCameraCapture();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				oldphone();
				choseHeadImageFromGallery();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
	}

	// �ӱ������ѡȡͼƬ��Ϊͷ��
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent("android.intent.action.PICK");
        // �����ļ�����
        intentFromGallery.setType("image/*");
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }
    // �����ֻ����������Ƭ��Ϊͷ��
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 
        // �жϴ洢���Ƿ���ã��洢��Ƭ�ļ�
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), ShineApplication.IMAGE_FILE_LOCATION)));
        }
 
        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }
    /**
     * ����豸�Ƿ����SDCard�Ĺ��߷���
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // �д洢��SDCard
            return true;
        } else {
            return false;
        }
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode,
            Intent intent) {
 
        // �û�û�н�����Ч�����ò���������
        if (resultCode == getActivity().RESULT_CANCELED) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.Fragment4_cancel), Toast.LENGTH_LONG).show();
            return;
        }
 
        switch (requestCode) {
        case CODE_GALLERY_REQUEST:
            cropRawPhoto(intent.getData());
            break;
 
        case CODE_CAMERA_REQUEST:
            if (hasSdcard()) {
                File tempFile = new File(
                        Environment.getExternalStorageDirectory(),
                        ShineApplication.IMAGE_FILE_LOCATION);
                cropRawPhoto(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(getActivity(), getActivity().getString(R.string.Fragment4_sd), Toast.LENGTH_LONG)
                        .show();
            }
 
            break;
 
        case CODE_RESULT_REQUEST:
            if (intent != null) {
                setImageToHeadView(intent);
            }
 
            break;
        }
 
        super.onActivityResult(requestCode, resultCode, intent);
    }
 
    /**
     * �ü�ԭʼ��ͼƬ
     */
    public void cropRawPhoto(Uri uri) {
 
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
 
        // ���òü�
        intent.putExtra("crop", true);
 
        // aspectX , aspectY :��ߵı���
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
 
        // outputX , outputY : �ü�ͼƬ���
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);
 
        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }
 
    /**
     * ��ȡ����ü�֮���ͼƬ���ݣ�������ͷ�񲿷ֵ�View
     */
    private void setImageToHeadView(Intent intent) {
    	Bundle extras = intent.getExtras();
        if (extras != null) {
            //��ͼƬѹ�����ļ�
            Bitmap photo=null;
            try {
				photo = extras.getParcelable("data");
				//��ͼƬѹ��������
				   saveBitmap(photo);
				   photo=compressBitmap(imageview2);
				   imageview2.setImageBitmap(HeadImageView.toRoundBitmap(photo));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
               if(photo != null && !photo.isRecycled()){ 
            	   // ���ղ�����Ϊnull
            	   photo.recycle(); 
            	   photo = null; 
               } 
               System.gc();
        }
    }
   

	private Bitmap compressBitmap(ImageView iv) {
		Bitmap addbmp=null;
		try {
			InputStream in=new FileInputStream(Environment.getExternalStorageDirectory()+"/"+ShineApplication.IMAGE_FILE_LOCATION);
			addbmp=LoadLocalImageUtil.compress(in, getActivity(), iv);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return addbmp;
	}

	/** ����ͼƬ */
    public void saveBitmap(Bitmap avatar) {
    	
    	  File f = new File(
                  Environment.getExternalStorageDirectory(),
                  ShineApplication.IMAGE_FILE_LOCATION);
     if (f.exists()) {
      f.delete();
     }
     try {
      FileOutputStream out = new FileOutputStream(f);
      avatar.compress(Bitmap.CompressFormat.PNG, 100, out);
      out.flush();
      out.close();
     } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
     } catch (IOException e) {
      e.printStackTrace();
     }

    }
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//		switch (requestCode) {
//		case 100:
//			if(data!=null&& resultCode == getActivity().RESULT_OK){
//				if(new File(ShineApplication.IMAGE_FILE_LOCATION).exists()){
//					try {
////						InputStream in=new FileInputStream(ShineApplication.IMAGE_FILE_LOCATION);
//						InputStream in=new FileInputStream(new File(ShineApplication.IMAGE_FILE_LOCATION));
//						Bitmap bm=LoadLocalImageUtil.compress(in, getActivity(), imageview2);
//						in.close();
//						imageview2.setImageBitmap(HeadImageView.toRoundBitmap(bm));
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			break;
//
//		case 101:
//			if(data!=null&& resultCode == getActivity().RESULT_OK){
//			startPhotoZoom(Uri.fromFile(new File(ShineApplication.IMAGE_FILE_LOCATION)));
//			}
//			break;
//		case 102:
//			if(data!=null&& resultCode == getActivity().RESULT_OK){
//			sentPicToNext(data);
//			}
//			break;
//		case 103:
//			break;
//		case 104:
//			if(data!=null&& resultCode == getActivity().RESULT_OK){
//				Uri str= data.getData();
//			Uri newUri = Uri.parse("file:///" +getPath(getActivity(),str)); 
//			Intent intent = new Intent("com.android.camera.action.CROP");
//			intent.setDataAndType(newUri, "image/*");
//			intent.putExtra("output", Uri.fromFile(new File(ShineApplication.IMAGE_FILE_LOCATION)));
//			intent.putExtra("crop", "true");
//			intent.putExtra("aspectX", 1);
//			intent.putExtra("aspectY", 1);
//			intent.putExtra("outputX", 300);
//			intent.putExtra("outputY", 300);
//			intent.putExtra("return-data", true);
//			intent.putExtra("noFaceDetection", true);
//			startActivityForResult(intent, 100);
//			}
//			break;
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//
//	}
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		// TODO Auto-generated method stub
		super.setMenuVisibility(menuVisible);
		if(this.getView()!=null){
			this.getView().setVisibility(menuVisible?View.VISIBLE:View.GONE);
		}
	}
	protected void oldphone() {
		Intent photoPickerIntent = new Intent("android.intent.action.PICK");
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, 104);
	}
	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, 102);
	}

	// �����м��ú��ͼƬ���ݵ���һ��������
	private void sentPicToNext(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			if (photo==null) {
			}else {
				imageview2.setImageBitmap(HeadImageView.toRoundBitmap(photo));
			}

			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] photodata = baos.toByteArray();
				System.out.println(photodata.toString());
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
		}
	}
//	@SuppressLint("NewApi") 
//	public  String getPath(final Context context, final Uri uri) { 
//
//		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT; 
//
//		// DocumentProvider 
//		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) { 
//			if (isExternalStorageDocument(uri)) { 
//				final String docId = DocumentsContract.getDocumentId(uri); 
//				final String[] split = docId.split(":"); 
//				final String type = split[0]; 
//
//				if ("primary".equalsIgnoreCase(type)) { 
//					return Environment.getExternalStorageDirectory() + "/"+ split[1]; 
//				}
//			}else if (isDownloadsDocument(uri)) { 
//
//				final String id = DocumentsContract.getDocumentId(uri); 
//				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(id)); 
//
//				return getDataColumn(context, contentUri, null, null); 
//			}else if (isMediaDocument(uri)) { 
//				final String docId = DocumentsContract.getDocumentId(uri); 
//				final String[] split = docId.split(":"); 
//				final String type = split[0]; 
//
//				Uri contentUri = null; 
//				if ("image".equals(type)) { 
//					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; 
//				} else if ("video".equals(type)) { 
//					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI; 
//				} else if ("audio".equals(type)) { 
//					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; 
//				} 
//
//				final String selection = "_id=?"; 
//				final String[] selectionArgs = new String[]{split[1]}; 
//
//				return getDataColumn(context, contentUri, selection,selectionArgs); 
//			} 
//		}else if ("content".equalsIgnoreCase(uri.getScheme())) { 
//			return getDataColumn(context, uri, null, null); 
//		}
//		else if ("file".equalsIgnoreCase(uri.getScheme())) { 
//			return uri.getPath(); 
//		} 
//
//		return null; 
//	} 

	/** 
	 * Get the value of the data column for this Uri. This is useful for 
	 * MediaStore Uris, and other file-based ContentProviders. 
	 * 
	 * @param context    The context. 
	 * @param uri      The Uri to query. 
	 * @param selection   (Optional) Filter used in the query. 
	 * @param selectionArgs (Optional) Selection arguments used in the query. 
	 * @return The value of the _data column, which is typically a file path. 
	 */
	private static String getDataColumn(Context context, Uri uri,String selection, String[] selectionArgs) { 

		Cursor cursor = null; 
		final String column = "_data"; 
		final String[] projection = {column}; 

		try { 
			cursor = context.getContentResolver().query(uri, projection,selection, selectionArgs, null); 
			if (cursor != null && cursor.moveToFirst()) { 
				final int column_index = cursor.getColumnIndexOrThrow(column); 
				return cursor.getString(column_index); 
			} 
		} finally { 
			if (cursor != null) 
				cursor.close(); 
		} 
		return null; 
	} 

	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is ExternalStorageProvider. 
	 */
	private static boolean isExternalStorageDocument(Uri uri) { 
		return "com.android.externalstorage.documents".equals(uri.getAuthority()); 
	} 

	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is DownloadsProvider. 
	 */
	private static boolean isDownloadsDocument(Uri uri) { 
		return "com.android.providers.downloads.documents".equals(uri.getAuthority()); 
	} 

	/** 
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is MediaProvider. 
	 */
	private static boolean isMediaDocument(Uri uri) { 
		return "com.android.providers.media.documents".equals(uri.getAuthority()); 
	}
}
