package com.growatt.shinephone.util;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.bean.Powerdata;

import org.apache.http.HttpException;
import org.xutils.common.Callback.Cancelable;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;


public class AddCQ {

	/**
	 * 上传文件
	 * @param url
	 * @param getListener
	 */
	public static void postUp(final String url, Map<String,Object> map,final com.growatt.shinephone.util.GetUtil.GetListener getListener){
		final Handler handler=new Handler(Looper.getMainLooper()){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Mydialog.Dismiss();
				String a=(String) msg.obj;
				switch (msg.what) {
				case 0:
					getListener.success(a);
					break;
				case 1:
					getListener.error(a);
					break;
					case 2:	//超时重新登录
						MyUtils.serverTimeOutLogin();
					break;

				}
			}
		};
		try {
			Cancelable cancle = XUtil.UpLoadFile(url, map, new CommonCallback<String>() {

				@Override
				public void onSuccess(String result) {
					Log.i("TAG", "postUp_utl:"+url);
					Log.i("TAG", "postUp_result:"+result);
					 if(TextUtils.isEmpty(result)){
		                	Message msg=new Message();
		                	msg.what=1;
		                	handler.sendMessage(msg);
		                }else if(result.contains("<!DOCTYPE")){
		                	//重新做登陆操作
		                	Message.obtain(handler, 2, url).sendToTarget();
		                }else{
		                	Message msg=new Message();
		                	msg.what=0;
		                	msg.obj=result;
		                	handler.sendMessage(msg);
		                }
				}

				@Override
				public void onError(Throwable ex, boolean isOnCallback) {
//					 Toast.makeText(x.app(), ShineApplication.context.getString(R.string.Xutil_network_err), Toast.LENGTH_LONG).show();
					if(ex instanceof HttpException){
						T.make(R.string.Xutil_network_err, ShineApplication.context);
					}else if(ex instanceof SocketTimeoutException){
						T.make(R.string.all_server_overtime, ShineApplication.context);
					}else if(ex instanceof UnknownHostException){
						T.make(R.string.serviceerror, ShineApplication.context);
					}else{
						T.make(R.string.serviceerror, ShineApplication.context);
					}
					 Message.obtain(handler, 1, ex.getMessage()).sendToTarget();
				}

				@Override
				public void onCancelled(CancelledException cex) {

				}

				@Override
				public void onFinished() {

				}
			});
			if(cancle==null){
				Message.obtain(handler, 1,url).sendToTarget();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Message msg=new Message();
			msg.what=2;
			msg.obj=e.toString();
			handler.sendMessage(msg);
		}

	}
	/**
     * 显示图片（默认情况）
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     */
    public static void display(ImageView imageView, String iconUrl) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setIgnoreGif(false)//是否忽略gif图。false表示不忽略。不写这句，默认是true
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.drawable.pic_service)
                .setLoadingDrawableId(R.drawable.loading)
                .build();
        x.image().bind(imageView, iconUrl,imageOptions);
    }
    /**
     * 显示圆角图片
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     * @param radius    圆角半径，
     */
    public static void display(ImageView imageView, String iconUrl, int radius) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setRadius(DensityUtil.dip2px(radius))
                .setIgnoreGif(false)
                .setCrop(true)//是否对图片进行裁剪
               .setFailureDrawableId(R.drawable.pic_service)
                .setLoadingDrawableId(R.drawable.loading)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }

    /**
     * 显示圆形头像，第三个参数为true
     *
     * @param imageView  图像控件
     * @param iconUrl    图片地址
     * @param isCircluar 是否显示圆形
     */
    public static void display(ImageView imageView, String iconUrl, boolean isCircluar) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setCircular(isCircluar)
                .setCrop(true)
                .setFailureDrawableId(R.drawable.pic_service)
                .setLoadingDrawableId(R.drawable.loading)
                .build();
        x.image().bind(imageView, iconUrl, imageOptions);
    }
//Powerdata实体类转换成map
	public static Map<String, Object> powerdataBeanToMap(Powerdata powerdata){
		Map<String, Object> map=new HashMap<String, Object>();
		if(powerdata!=null && !TextUtils.isEmpty(Cons.plant)){
			map.put("plantID", Cons.plant);
			map.put("plantName", powerdata.getPlantName());
			map.put("plantFirm", powerdata.getDesignCompany());
			map.put("plantDate", powerdata.getCreateData());
			map.put("plantPower", powerdata.getNormalPower());
			map.put("plantCountry", powerdata.getCountry());
			map.put("plantCity", powerdata.getCity());
			map.put("plantTimezone", powerdata.getTimeZone());
			map.put("plantLng", powerdata.getPlant_lng());
			map.put("plantLat", powerdata.getPlant_lat());
			map.put("plantMoney", powerdata.getFormulaMoneyUnit());
			map.put("plantIncome", powerdata.getFormulaMoney());
			map.put("plantCoal", powerdata.getFormulaCoal());
			map.put("plantCo2", powerdata.getFormulaCo2());
			map.put("plantSo2", powerdata.getFormulaSo2());
			if(!TextUtils.isEmpty(powerdata.getImage1())){
				map.put("plantImg", powerdata.getImage1());
			}
			if(!TextUtils.isEmpty(powerdata.getImage2())){
				map.put("plantMap", powerdata.getImage2());
			}
			
		}
		return map;
	}
	
//	public static String updateplantdata(Powerdata powerdata,String url){
//		HttpClient  client = DefaultHttpClientTool.getInstance();
//		HttpPost httpPost = new HttpPost(url);
//		httpPost.addHeader("charset", "UTF-8");         //���к���Ҫ
//		MultipartEntity m = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,null,Charset.forName("UTF-8"));
//		try {
//			m.addPart("plantID", new StringBody(Cons.plant,Charset.forName("UTF-8")));
//			m.addPart("plantName", new StringBody(powerdata.getPlantName(),Charset.forName("UTF-8")));
//			m.addPart("plantFirm", new StringBody(powerdata.getDesignCompany(),Charset.forName("UTF-8")));
//			m.addPart("plantDate", new StringBody(powerdata.getCreateData(),Charset.forName("UTF-8")));
//			m.addPart("plantPower", new StringBody(powerdata.getNormalPower(),Charset.forName("UTF-8")));
//			m.addPart("plantCountry", new StringBody(powerdata.getCountry(),Charset.forName("UTF-8")));
//			m.addPart("plantCity", new StringBody(powerdata.getCity(),Charset.forName("UTF-8")));
//			m.addPart("plantTimezone", new StringBody(powerdata.getTimeZone(),Charset.forName("UTF-8")));
//			m.addPart("plantLng", new StringBody(powerdata.getPlantName(),Charset.forName("UTF-8")));
//			m.addPart("plantLat", new StringBody(powerdata.getPlantName(),Charset.forName("UTF-8")));
//			m.addPart("plantMoney", new StringBody(powerdata.getFormulaMoneyUnit(),Charset.forName("UTF-8")));
//			m.addPart("plantIncome", new StringBody(powerdata.getFormulaMoney(),Charset.forName("UTF-8")));
//			m.addPart("plantCoal", new StringBody(powerdata.getFormulaCoal(),Charset.forName("UTF-8")));
//			m.addPart("plantCo2", new StringBody(powerdata.getFormulaCo2(),Charset.forName("UTF-8")));
//			m.addPart("plantSo2", new StringBody(powerdata.getFormulaSo2(),Charset.forName("UTF-8")));
//			if(powerdata.getImage1()!=null){
//				System.out.println("��1");
//				m.addPart("plantImg",new FileBody(new File(powerdata.getImage1())));
//			}
//			if(powerdata.getImage2()!=null){
//				System.out.println("��2");
//				m.addPart("plantMap",new FileBody(new File(powerdata.getImage2())));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		httpPost.setEntity(m);
//		String result = null;
//		HttpResponse httpResponse = null;
//		try {
//			httpResponse = client.execute(httpPost);
//			System.out.println(httpResponse.getStatusLine().getStatusCode());
//			if (httpResponse.getStatusLine().getStatusCode() == 200) {
////				ImageHttp.copyFile(map.get("imageName").toString(), newPath);
//				result = EntityUtils.toString(httpResponse.getEntity(),HTTP.UTF_8);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//		
//	}
	

}
