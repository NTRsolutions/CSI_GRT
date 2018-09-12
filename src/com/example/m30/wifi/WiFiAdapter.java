package com.example.m30.wifi;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiConfiguration.KeyMgmt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.growatt.shinephone.R;


public class WiFiAdapter extends BaseAdapter {

	private Context mActivity;
	private LayoutInflater mInflater;
	private List<HashMap<String, String>> mTotalWiFiList;

	public WiFiAdapter(Context superActivity, String startSource, List<HashMap<String, String>> wifiList) {
		mActivity = superActivity;
		mInflater = LayoutInflater.from(mActivity);

		mTotalWiFiList = wifiList;
	}

	@Override
	public int getCount() {
		return mTotalWiFiList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {

		ViewHolder mViewHolder = null;
		HashMap<String, String> mItemWiFiList = mTotalWiFiList.get(position);

		//获取各个控件
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_wifi_list, null);

			mViewHolder = new ViewHolder();
			mViewHolder.sSSIDText = (TextView)convertView.findViewById(R.id.SSID_Text);
			mViewHolder.sSecurityText = (TextView)convertView.findViewById(R.id.Sec_Text);
			mViewHolder.sSignalLevelImage = (ImageView)convertView.findViewById(R.id.Signal_Image);
			mViewHolder.sCheckedButton = (RadioButton)convertView.findViewById(R.id.Select_Button);

			convertView.setTag(mViewHolder);
		}else {
			mViewHolder = (ViewHolder)convertView.getTag();
		}

		//禁用各控件的点击属性，便于统一进行选项单击事件的处理
		mViewHolder.sSignalLevelImage.setClickable(false);
		mViewHolder.sSSIDText.setClickable(false);
		mViewHolder.sSecurityText.setClickable(false);
		mViewHolder.sCheckedButton.setClickable(false);

		//设置各控件的值
		mViewHolder.sCheckedButton.setChecked(false);
		mViewHolder.sSSIDText.setText(mItemWiFiList.get("SSID"));
		mViewHolder.sSecurityText.setText(mItemWiFiList.get("SEC"));

//    		mViewHolder.sSignalLevelImage.setImageResource(R.drawable.wifi_signal);
		if (getSecurityId(mItemWiFiList.get("SEC")) == PublicClass.SECID_NONE){
			mViewHolder.sSignalLevelImage.setImageState(PublicClass.STATE_NONE,true);
		}else{
			mViewHolder.sSignalLevelImage.setImageState(PublicClass.STATE_SECURED,true);
		}

		mViewHolder.sSignalLevelImage.setImageLevel(Integer.parseInt(mItemWiFiList.get("LEVEL")));

		return convertView;
	}

	private class ViewHolder {
		ImageView sSignalLevelImage;
		TextView sSSIDText;
		TextView sSecurityText;
		RadioButton sCheckedButton;
	}


	//获取安全ID
	public int getSecurityId(WifiConfiguration config) {

		if (config.allowedKeyManagement.get(KeyMgmt.WPA_PSK)) {
			return PublicClass.SECID_PSK;
		}

		if (config.allowedKeyManagement.get(KeyMgmt.WPA_EAP) ||
				config.allowedKeyManagement.get(KeyMgmt.IEEE8021X)) {
			return PublicClass.SECID_EAP;
		}

		return (config.wepKeys[0] != null) ?PublicClass. SECID_WEP : PublicClass.SECID_NONE;
	}


	public int getSecurityId(String strCapabilities) {
		if (strCapabilities.contains("WEP")) {
			return PublicClass.SECID_WEP;
		} else if (strCapabilities.contains("PSK")) {
			return PublicClass.SECID_PSK;
		} else if (strCapabilities.contains("EAP")) {
			return PublicClass.SECID_EAP;
		}
		return PublicClass.SECID_NONE;
	}
} 