package com.growatt.shinephone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.m30.wifi.PublicClass;
import com.example.m30.wifi.WiFiAdapter;
import com.example.m30.wifi.WiFiAdmin;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WIFIlistActivity extends DoActivity {
	  private String mStartSource;
		private WiFiAdmin mWiFiAdmin;
	    private WiFiAdapter mWiFiAdapter;
	    private List<HashMap<String, String>> mWiFiList;
	    private HashMap<String, String> mSelectedWiFiInfo = new HashMap<String, String>();
	    
	    private ListView mWiFiListView;
	    private TextView mPwdText;
	    private EditText mPwdEdit;
	    private Button mConfirmButton;
	    private Button mRefleshButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifilist);
        Bundle mBundle = getIntent().getExtras();
        mStartSource = mBundle.getString("Data");
		initHeaderView();
        mWiFiAdmin =  new WiFiAdmin(this,mStartSource);
        mWiFiList = new ArrayList<HashMap<String, String>>();
        mWiFiAdapter = new WiFiAdapter(this, mStartSource, mWiFiList);
        scanWiFiSignal();
        if (mWiFiList.isEmpty()){
        	toast(R.string.wifilist_no_wifi);
        }

        mWiFiListView = (ListView) findViewById(R.id.WiFiList);
        mWiFiListView.setAdapter(mWiFiAdapter);  
        mWiFiListView.setOnItemClickListener(new ListItemClickListener());
        //��ȡ�����
        mPwdText = (TextView) findViewById(R.id.wifi_pwdlabel);
        mPwdEdit = (EditText) findViewById(R.id.wifi_pwdtext); 
        mPwdText.setVisibility(View.GONE);
        mPwdEdit.setVisibility(View.GONE);
        //��ȡˢ�°�ť�������õ����¼�����Ӧ����
        mRefleshButton = (Button) findViewById(R.id.RefleshButton); 
        mRefleshButton.setOnClickListener(new RefleshButtonClickListener());
        
        //��ȡȷ����ť�������õ����¼�����Ӧ����
        mSelectedWiFiInfo.clear();
        mConfirmButton = (Button) findViewById(R.id.mConfirmButton); 
        mConfirmButton.setOnClickListener(new ConfirmButtonClickListener());
        mConfirmButton.setEnabled(false);
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
		setHeaderTitle(headerView,getString(R.string.wifilist_title));
	}
    class ListItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent,View view,int position,long ID){
		
			//�Ȱ�����item�е�radionButton����Ϊfalse
			for(int i =0 ;i <parent.getChildCount();i++)
			{
				View mChildView = parent.getChildAt(i);
				RadioButton mAllButton = (RadioButton)mChildView.findViewById(R.id.Select_Button);
				mAllButton.setChecked(false);
			}
			//Ȼ���ѡ�е���Ϊtrue
			RadioButton mSelectedButton = (RadioButton)view.findViewById(R.id.Select_Button);
			mSelectedButton.setChecked(true);
			//����ѡ�е�WiFi�б�
			mSelectedWiFiInfo.clear();
			mSelectedWiFiInfo.putAll(mWiFiList.get(position));
			//�������ʹ�������
			if (mWiFiAdapter.getSecurityId(mSelectedWiFiInfo.get("SEC")) == PublicClass.SECID_NONE){
				mPwdText.setVisibility(View.GONE);
		        mPwdEdit.setVisibility(View.GONE);
			}else if(mStartSource.equals(PublicClass.START_WIFI)){
				mPwdText.setVisibility(View.VISIBLE);
		        mPwdEdit.setVisibility(View.VISIBLE);
		        mPwdEdit.setText("12345678");
			}
			
			//ʹ��ȷ����ť
			mConfirmButton.setEnabled(true);
		}
	}
    
	//��׽���ؼ�
	@Override
	public void onBackPressed() {
		int mStartFailId;
		Intent mBackIntent = getIntent();
		
		if (mStartSource.equals(PublicClass.START_WIFI)){
			mStartFailId = PublicClass.RES_SELECT_WIFI_FAIL;
		}else{
			mStartFailId = PublicClass.RES_SELECT_ROUTER_FAIL;
		}

		mSelectedWiFiInfo.clear();
		mBackIntent.putExtra("SelectedData",mSelectedWiFiInfo);
		WIFIlistActivity.this.setResult(mStartFailId, mBackIntent);

		WIFIlistActivity.this.finish();
	}
	
	//ȷ����ť��Ӧ�¼�
	class ConfirmButtonClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			int mStartOkId;
			if (mStartSource.equals(PublicClass.START_WIFI)){
				mStartOkId = PublicClass.RES_SELECT_WIFI_OK;
			}else{
				mStartOkId = PublicClass.RES_SELECT_ROUTER_OK;
			}

			if(mSelectedWiFiInfo.isEmpty() != true){
				
				//�ж������Ƿ��ṩ
				if((mPwdEdit.getVisibility() == View.VISIBLE)&&(mPwdEdit.length() == 0)){
					
					return;
				}
				
				//���ص�ԭActivity
				Intent mReturnIntent = getIntent();
				mReturnIntent.putExtra("SelectedData",mSelectedWiFiInfo);
				mReturnIntent.putExtra("WifiPwd",mPwdEdit.getText().toString());
				setResult(mStartOkId, mReturnIntent);
				finish();	
			}
		}
	}
	
	//ˢ�°�ť��Ӧ�¼�
	class RefleshButtonClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			//����ɨ��Wi-Fi�źţ�����Wi-Fi��Ϣ�б�
			scanWiFiSignal();
			
			//֪ͨ������ˢ����ʾ
			mWiFiAdapter.notifyDataSetChanged();
			
			//��ʾ��ˢ��
//			T.make("ˢ��");
			
			//���������
			mPwdText.setVisibility(View.GONE);
	        mPwdEdit.setVisibility(View.GONE);
		}
	}

	//ɨ�������ź�
	private void scanWiFiSignal(){
		
		//�����Wi-Fi��Ϣ�б�
		mWiFiList.clear();
		
		//����������
        mWiFiAdmin.openNetCard();
        
        //������������Ƿ�򿪣���δ�򿪣��ȴ�3���ӣ�
        int iLoop =0;
        while(iLoop++ < 4){
        	
        	//�������򿪣����˳�ѭ��
    		if (mWiFiAdmin.checkNetCardState()){
    			break;
    		}
        			
    		//��ʱ500����
    		try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
        	
        //������δ�򿪣�����ʾ��Ϣ���˳�
    	if(mWiFiAdmin.checkNetCardState() != true){
    		toast(R.string.ahtool_selectrouter);
    		return;
    	}

        //��ȡɨ����
    	List<HashMap<String, String>> mTempList = mWiFiAdmin.getWiFiScanResult();
        if (mTempList == null){
        	return;
        }
        
        //��ɨ����Ϊ�գ����ظ�ɨ����
        iLoop =0;
        while(mTempList.isEmpty()){
        	
        	mTempList = mWiFiAdmin.getWiFiScanResult();
        	
        	iLoop++;
        	if(iLoop > 4){
        		break;
        	}
        	
    		//��ʱ500����
    		try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }        
        
        //ˢ��Wi-Fi��Ϣ�б�
        mWiFiList.addAll(mTempList);
	}


}
