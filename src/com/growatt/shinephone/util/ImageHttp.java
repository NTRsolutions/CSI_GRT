package com.growatt.shinephone.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.ShineApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageHttp {
	private static Thread thread;
	private static DisplayImageOptions options=null;
	public static void LoadImage
(final ImageView img, final String path,final String name){
		
		final Handler handler=new Handler(){
				public void handleMessage(android.os.Message msg) {
					switch (msg.what) {
					case 0:
//						LoadLocalImageUtil.getInstance().displayFromSDCard(ShineApplication.Path+"/"+name.substring(0, name.length()-4), img);
						LoadLocalImageUtil.getInstance().displayFromSDCard(ShineApplication.Path+"/"+name, img);
						break;
					case 1:
//						LoadLocalImageUtil.getInstance().displayFromSDCard(ShineApplication.Path+"/"+name.substring(0, name.length()-4), img);
						LoadLocalImageUtil.getInstance().displayFromSDCard(ShineApplication.Path+"/"+name, img);
						break;

					default:

						break;
					}
				};
			};
//			name=name.substring(0, name.length()-4);
		final String bit = useTheImage(name.substring(0, name.length()-4));
//		final String bit = useTheImage(name);
		
			thread=new Thread(){
				@Override
				public void run() {
					super.run();
					if(TextUtils.isEmpty(bit)){
					try {
						HttpClient client =DefaultHttpClientTool.getInstance();
						HttpGet get;
						/**
						 * ��ɵ�Ƶ����󷽷� ÿ���ط���ͼƬ���󷽷���Ȼ���ǲ�һ����
						 */
						if(path.contains("----")){
							 get = new HttpGet(path.replace("----", ""));
						}else{
							 get = new HttpGet(path);
						}
						HttpResponse response = client.execute(get);
						System.out.println("imagecode="+response.getStatusLine().getStatusCode());
						if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
							InputStream is = response.getEntity().getContent();
							Bitmap bitmap = BitmapFactory.decodeStream(is);
							is.close();
							saveFile(bitmap,name);
//							saveFile(bitmap,name.substring(0, name.length()-4));
							Message msg=new Message();
							msg.what=0;
							handler.sendMessage(msg);
						}else{
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					}else{
						Message msg=new Message();
						msg.what=1;
						handler.sendMessage(msg);
						
					}
				}
			};
		thread.setDaemon(true);
		thread.start();

	}
	/**
	 * ����ͼƬ��SD����
	 * 
	 * @param bm
	 * @param fileName
	 * 
	 */
	private static void saveFile(Bitmap bm,String name) {
		try {
			File file = new File(ShineApplication.Path+"/"+name);
			// ����ͼƬ�����ļ���
			boolean sdCardExist = Environment.getExternalStorageState().equals(
					android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
			if (sdCardExist) {
				File maiduo = new File(ShineApplication.Path+"/");
				// ����ļ��в�����
				if (!maiduo.exists()) {
					// ����ָ����·�������ļ���
					maiduo.mkdirs();
					// ����ļ��в�����
				} 
				// ���ͼƬ�Ƿ����
				if (!file.exists()) {
					file.createNewFile();
				}
			}

			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(file));
			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * ʹ��SD���ϵ�ͼƬ
	 * 
	 */
	private static String useTheImage(String name) {

		// ����ļ�·��
		String imageSDCardPath =ShineApplication.Path
				+ "/"
				+ name;
		System.out.println("imageSDCardPath="+imageSDCardPath);
		File file = new File(imageSDCardPath);
		if (!file.exists()) {
			return "";
		}else{
			return imageSDCardPath;
		}
	}
	/**
	 * ����ͼƬ
	 */
	protected static ImageLoader imageLoader;
	public static void ImageLoader(final ImageView imageView,String imageurl){
		// ʹ��DisplayImageOptions.Builder()����DisplayImageOptions  
		if(options==null){
		 options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.loading) // ����ͼƬ�����ڼ���ʾ��ͼƬ
		.showImageForEmptyUri(R.drawable.pic_service) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
		.showImageOnFail(R.drawable.pic_service) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
		.cacheInMemory(true) // default  �������ص�ͼƬ�Ƿ񻺴����ڴ���
		.cacheOnDisk(true) // default  �������ص�ͼƬ�Ƿ񻺴���SD����
		.bitmapConfig(Bitmap.Config.RGB_565)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.build();
		}
		if(imageLoader==null){
			imageLoader=ImageLoader.getInstance();
		}
		imageLoader.displayImage(imageurl, imageView,options,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason failReason) {
				 switch (failReason.getType()) {  
	                case IO_ERROR:  
	                    //handler.sendEmptyMessage();  
	                    break;  
	                case DECODING_ERROR:  
	                    break;  
	                      
	                case NETWORK_DENIED:  
	                    break;  
	                      
	                case OUT_OF_MEMORY:  
	                	ImageLoader.getInstance().clearMemoryCache();
	                    ImageLoader.getInstance().clearDiskCache();
	                    break;  
	                      
	                case UNKNOWN:  
	                    break;  
	                default:  
	                    break;  
	                }  
			}
			
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	public static void ImageLoader(final ImageView imageView,String imageurl,boolean flag){
		// ʹ��DisplayImageOptions.Builder()����DisplayImageOptions  
		if(options==null){
		 options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.loading) // ����ͼƬ�����ڼ���ʾ��ͼƬ
		.showImageForEmptyUri(R.drawable.pic_service) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
		.showImageOnFail(R.drawable.pic_service) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
		.bitmapConfig(Bitmap.Config.RGB_565)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.build();
		}
		if(imageLoader==null){
			imageLoader=ImageLoader.getInstance();
		}
		imageLoader.displayImage(imageurl, imageView,options,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason failReason) {
				 switch (failReason.getType()) {  
	                case IO_ERROR:  
	                    //handler.sendEmptyMessage();  
	                    break;  
	                case DECODING_ERROR:  
	                    break;  
	                      
	                case NETWORK_DENIED:  
	                    break;  
	                      
	                case OUT_OF_MEMORY:  
	                	ImageLoader.getInstance().clearMemoryCache();
	                    ImageLoader.getInstance().clearDiskCache();
	                    break;  
	                      
	                case UNKNOWN:  
	                    break;  
	                default:  
	                    break;  
	                }  
			}
			
			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
















}
