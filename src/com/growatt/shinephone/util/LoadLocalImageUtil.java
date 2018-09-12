package com.growatt.shinephone.util;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * �첽���ر���ͼƬ������
 * 
 * @author tony
 * 
 */
public class LoadLocalImageUtil {
    private LoadLocalImageUtil() {
    }

    private static LoadLocalImageUtil instance = null;

    public static synchronized LoadLocalImageUtil getInstance() {
        if (instance == null) {
            instance = new LoadLocalImageUtil();
        }
        return instance;
    }

    /**
     * ���ڴ濨���첽���ر���ͼƬ
     * 
     * @param uri
     * @param imageView
     */
    public void displayFromSDCard(String path, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        ImageLoader.getInstance().displayImage("file://" + path, imageView);
    }

    /**
     * ��assets�ļ������첽����ͼƬ
     * 
     * @param imageName
     *            ͼƬ���ƣ�����׺�ģ����磺1.png
     * @param imageView
     */
    public void dispalyFromAssets(String imageName, ImageView imageView) {
        // String imageUri = "assets://image.png"; // from assets
        ImageLoader.getInstance().displayImage("assets://" + imageName,
                imageView);
    }

    /**
     * ��drawable���첽���ر���ͼƬ
     * 
     * @param imageId
     * @param imageView
     */
    public void displayFromDrawable(int imageId, ImageView imageView) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        ImageLoader.getInstance().displayImage("drawable://" + imageId,
                imageView);
    }

    /**
     * ���������ṩ����ץȡͼƬ
     */
    public void displayFromContent(String uri, ImageView imageView) {
        // String imageUri = "content://media/external/audio/albumart/13"; //
        // from content provider
        ImageLoader.getInstance().displayImage("content://" + uri, imageView);
    }
    /**
	 * ����Ҫ��ʾ��ImageView�Ĵ�С��ͼ�����ѹ��
	 * @param is ͼ��Դ
	 * @param iv δ��Ҫ��ʾͼ���ImageView
	 * @return ѹ�������ͼ��
	 */
	
	public static Bitmap compress(InputStream is,Context c, View iv) {
		Bitmap bitmap = null;
		try{
			
			//1)�Ȼ��ԭʼͼ��(ͼ��Դ)�ĳߴ��С
			//����Optioins����ȡͼ��Ĵ�С
			//is----------byte[]
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buff = new byte[4096];
			int len = -1;
			while((len=is.read(buff))!=-1){
				out.write(buff, 0, len);
			}
			
			Options opts = new Options();
			opts.inJustDecodeBounds = true;
			//optsһ���趨��inJustDecodeBounds = true
			//BitmapFactory�ǲ��᷵��bitmap����ֻ��null
			BitmapFactory.decodeByteArray(out.toByteArray(),0, out.toByteArray().length, opts);
			int width = opts.outWidth;//ͼ��Ŀ��(������)
			int height = opts.outHeight;//ͼ��߶�(������)
			//2)��ȡϣ���Ŀ��/�߶�
			int targetWidth  = iv.getWidth();//ImageView�Ŀ��
			int targetHeight = iv.getHeight();//Imageview�ĸ߶�
			//���ȡImageView���/�߶�ȡ����
			if(targetHeight==0||targetWidth==0){
				//�����ֶ�ָ��ֵ(80dp,100dp)
				//�Ե�ǰ�豸��Ļ�Ŀ��/�͸߶�����ΪtargetWiddth/targetHeight
				targetWidth = c.getResources().
						      getDisplayMetrics().
						      widthPixels;
				targetHeight = c.getResources().
						      getDisplayMetrics().
						      heightPixels;
			}
			
			//3)����ѹ����
			int sampleSize = 1;
			
			if(width*1.0/targetWidth>1||height*1.0/targetHeight>1){
				sampleSize = (int) Math.ceil(
						Math.max(width*1.0/targetWidth, 
								 height*1.0/targetHeight));
			}
			
			//4)ѹ��ͼ��
			opts.inSampleSize = sampleSize;
			opts.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeByteArray(out.toByteArray(), 0, out.toByteArray().length,opts);
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return bitmap;
	}

}