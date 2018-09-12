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
 * 异步加载本地图片工具类
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
     * 从内存卡中异步加载本地图片
     * 
     * @param uri
     * @param imageView
     */
    public void displayFromSDCard(String path, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        ImageLoader.getInstance().displayImage("file://" + path, imageView);
    }

    /**
     * 从assets文件夹中异步加载图片
     * 
     * @param imageName
     *            图片名称，带后缀的，例如：1.png
     * @param imageView
     */
    public void dispalyFromAssets(String imageName, ImageView imageView) {
        // String imageUri = "assets://image.png"; // from assets
        ImageLoader.getInstance().displayImage("assets://" + imageName,
                imageView);
    }

    /**
     * 从drawable中异步加载本地图片
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
     * 从内容提提供者中抓取图片
     */
    public void displayFromContent(String uri, ImageView imageView) {
        // String imageUri = "content://media/external/audio/albumart/13"; //
        // from content provider
        ImageLoader.getInstance().displayImage("content://" + uri, imageView);
    }
    /**
	 * 根据要显示的ImageView的大小对图像进行压缩
	 * @param is 图像源
	 * @param iv 未来要显示图像的ImageView
	 * @return 压缩过后的图像
	 */
	
	public static Bitmap compress(InputStream is,Context c, View iv) {
		Bitmap bitmap = null;
		try{
			
			//1)先获得原始图像(图像源)的尺寸大小
			//借助Optioins来获取图像的大小
			//is----------byte[]
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buff = new byte[4096];
			int len = -1;
			while((len=is.read(buff))!=-1){
				out.write(buff, 0, len);
			}
			
			Options opts = new Options();
			opts.inJustDecodeBounds = true;
			//opts一旦设定了inJustDecodeBounds = true
			//BitmapFactory是不会返回bitmap对象，只会null
			BitmapFactory.decodeByteArray(out.toByteArray(),0, out.toByteArray().length, opts);
			int width = opts.outWidth;//图像的宽度(像素数)
			int height = opts.outHeight;//图像高度(像素数)
			//2)获取希望的宽度/高度
			int targetWidth  = iv.getWidth();//ImageView的宽度
			int targetHeight = iv.getHeight();//Imageview的高度
			//如果取ImageView宽度/高度取不到
			if(targetHeight==0||targetWidth==0){
				//可以手动指定值(80dp,100dp)
				//以当前设备屏幕的宽度/和高度来作为targetWiddth/targetHeight
				targetWidth = c.getResources().
						      getDisplayMetrics().
						      widthPixels;
				targetHeight = c.getResources().
						      getDisplayMetrics().
						      heightPixels;
			}
			
			//3)计算压缩比
			int sampleSize = 1;
			
			if(width*1.0/targetWidth>1||height*1.0/targetHeight>1){
				sampleSize = (int) Math.ceil(
						Math.max(width*1.0/targetWidth, 
								 height*1.0/targetHeight));
			}
			
			//4)压缩图像
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