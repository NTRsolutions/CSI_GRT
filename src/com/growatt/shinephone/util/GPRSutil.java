package com.growatt.shinephone.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.growatt.shinephone.R;

public class GPRSutil {
	private static LocationManager lm=null;
	private static Map<String, Object> map;
	private static final String TAG="GpsActivity";
	private static GPRSlisteners gpSlistener;
	private static Activity con;
	public static void Gprs(Activity context,final GPRSlisteners gpSlisteners){
		gpSlistener=gpSlisteners;
		con=context;
		map=new HashMap<String, Object>();
		lm=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

		//�ж�GPS�Ƿ���������
		if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			T.make(context.getString(R.string.utf_open_gprs),context);
			//���ؿ���GPS�������ý���
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);   
			context.startActivityForResult(intent,0); 
			return ;
		}
		String bestProvider = lm.getBestProvider(getCriteria(), true);
		//��ȡλ����Ϣ
		//��������ò�ѯҪ��getLastKnownLocation�������˵Ĳ���ΪLocationManager.GPS_PROVIDER
		Location location= lm.getLastKnownLocation(bestProvider);    
		updateView(location);
		thread();
		//����״̬
		//�󶨼�������4������    
		//����1���豸����GPS_PROVIDER��NETWORK_PROVIDER����
		//����2��λ����Ϣ�������ڣ���λ����    
		//����3��λ�ñ仯��С���룺��λ�þ���仯������ֵʱ��������λ����Ϣ    
		//����4������    
		//��ע������2��3���������3��Ϊ0�����Բ���3Ϊ׼������3Ϊ0����ͨ��ʱ������ʱ���£�����Ϊ0������ʱˢ��   

		// 1�����һ�Σ�����Сλ�Ʊ仯����1�׸���һ�Σ�
		//ע�⣺�˴�����׼ȷ�ȷǳ��ͣ��Ƽ���service��������һ��Thread����run��sleep(10000);Ȼ��ִ��handler.sendMessage(),����λ��
	}
	//λ�ü���
	private static LocationListener locationListener=new LocationListener() {

		/**
		 * λ����Ϣ�仯ʱ����
		 */
		public void onLocationChanged(Location location) {
			updateView(location);
			Log.i(TAG, "ʱ�䣺"+location.getTime()); 
			Log.i(TAG, "���ȣ�"+location.getLongitude()); 
			Log.i(TAG, "γ�ȣ�"+location.getLatitude()); 
			Log.i(TAG, "���Σ�"+location.getAltitude()); 
		}

		/**
		 * GPS״̬�仯ʱ����
		 */
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch (status) {
			//GPS״̬Ϊ�ɼ�ʱ
			case LocationProvider.AVAILABLE:
				Log.i(TAG, "��ǰGPS״̬Ϊ�ɼ�״̬");
				break;
				//GPS״̬Ϊ��������ʱ
			case LocationProvider.OUT_OF_SERVICE:
				Log.i(TAG, "��ǰGPS״̬Ϊ��������״̬");
				break;
				//GPS״̬Ϊ��ͣ����ʱ
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				Log.i(TAG, "��ǰGPS״̬Ϊ��ͣ����״̬");
				break;
			}
		}
		/**
		 * GPS����ʱ����
		 */
		public void onProviderEnabled(String provider) {
			Location location=lm.getLastKnownLocation(provider);
			updateView(location);
		}

		/**
		 * GPS����ʱ����
		 */
		public void onProviderDisabled(String provider) {
			updateView(null);
		}


	};

	//״̬����
	static GpsStatus.Listener listener = new GpsStatus.Listener() {
		public void onGpsStatusChanged(int event) {
			switch (event) {
			//��һ�ζ�λ
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				Log.i(TAG, "��һ�ζ�λ");
				break;
				//����״̬�ı�
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				Log.i(TAG, "����״̬�ı�");
				//��ȡ��ǰ״̬
				GpsStatus gpsStatus=lm.getGpsStatus(null);
				//��ȡ���ǿ�����Ĭ�����ֵ
				int maxSatellites = gpsStatus.getMaxSatellites();
				//����һ�������������������� 
				Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
				int count = 0;     
				while (iters.hasNext() && count <= maxSatellites) {     
					GpsSatellite s = iters.next();     
					count++;     
				}   
				System.out.println("��������"+count+"������");
				break;
				//��λ����
			case GpsStatus.GPS_EVENT_STARTED:
				Log.i(TAG, "��λ����");
				break;
				//��λ����
			case GpsStatus.GPS_EVENT_STOPPED:
				Log.i(TAG, "��λ����");
				break;
			}
		};
	};

	/**
	 * ʵʱ�����ı�����
	 * 
	 * @param location
	 */
	private static void updateView(Location location){
		if(location!=null){
			System.out.println("�õ�map");
			map.put("lat", location.getLatitude());
			map.put("lon", location.getLongitude());
			map.put("city", getCityByLocation(con,location.getLongitude(),location.getLatitude()));
			lm.removeUpdates(locationListener);
			threadstop();
			gpSlistener.success(map);
		}else{
			lm.addGpsStatusListener(listener);
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, locationListener);
		}
	}
	//根据经纬度获取城市
	public static String getCityByLocation(Context context,double lng,double lat){
		String city=null;
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());                
		List<Address> list = null;                
		try {                    
			list = geocoder.getFromLocation(lat, lng, 1);                    
			if(list.size() == 0){
				
				}else {                       
					Address address = list.get(0);                       
					city=address.getLocality(); 
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return city;
	}
	public static void stopGprs(){
		if(lm!=null){
			Mydialog.Dismiss();
			threadstop();
		lm.removeUpdates(locationListener);
		}
	}
	public	interface GPRSlisteners{
		void success(Map<String, Object>map);
		void error();
	}
	private static Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				Mydialog.Dismiss();
				gpSlistener.error();
				break;

			default:
				break;
			}
		};
	};
	private static Thread threads;
	private static void thread(){
		threads=new Thread(){
			public void run() {
				try {
					Thread.sleep(10000);
					if(map.size()==0){
					handler.sendEmptyMessage(1);
					}else{
						System.out.println("map������");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		};
		threads.setDaemon(true);
		threads.start();
	}
	public static void threadstop(){
		System.out.println("xianchengtingzhi");
		if(threads!=null){
			threads.interrupt();
			threads=null;
		}
	}
	/**
	 * ���ز�ѯ����
	 * @return
	 */
	private static Criteria getCriteria(){
		Criteria criteria=new Criteria();
		//���ö�λ��ȷ�� Criteria.ACCURACY_COARSE�Ƚϴ��ԣ�Criteria.ACCURACY_FINE��ȽϾ�ϸ 
		criteria.setAccuracy(Criteria.ACCURACY_FINE);    
		//�����Ƿ�Ҫ���ٶ�
		criteria.setSpeedRequired(false);
		// �����Ƿ�������Ӫ���շ�  
		criteria.setCostAllowed(false);
		//�����Ƿ���Ҫ��λ��Ϣ
		criteria.setBearingRequired(false);
		//�����Ƿ���Ҫ������Ϣ
		criteria.setAltitudeRequired(false);
		// ���öԵ�Դ������  
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		return criteria;
	}
}
