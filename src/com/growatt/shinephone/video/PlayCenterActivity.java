package com.growatt.shinephone.video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.DoActivity;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.GetUtil;
import com.growatt.shinephone.util.GetUtil.GetListener;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.util.Urlsutil;
import com.growatt.shinephone.view.MyRadioButton;

public class PlayCenterActivity extends DoActivity {
    View headerView;
    View headerListView;
	private ImageView ivPlay;
	private TextView tvContent;
	private ListView mListView;
	private PlayCenterAdapter mAdapter;
	private List<Map<String, String>> list;
	private ImageView ivPlaying;
	private String videoImgurl;
	private String videoTitle;
	private String videoPicurl;
	private String videoOutline;
	private String videoName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_center);
		getToIntent();
		initHeaderView();
		initHeaderListView();
		initListView();
		initListener();
	}

	

	private void getToIntent() {
		Intent intent = getIntent();
		videoImgurl=intent.getStringExtra("videoImgurl");
		videoTitle=intent.getStringExtra("videoTitle");
		videoPicurl=intent.getStringExtra("videoPicurl");
		videoOutline=intent.getStringExtra("videoOutline");
		videoName=intent.getStringExtra("videoName");
	}



	private void initListener() {
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(position>0){
				Intent intent=new Intent(PlayCenterActivity.this, PlayerActivity.class);
				intent.putExtra("videoPicurl", list.get(position-1).get("videoPicurl"));
				intent.putExtra("videoName", list.get(position-1).get("videoName"));
				intent.putExtra("type", Constant.OtherToPlayerAct);
				startActivity(intent);
				}
			}
		});
	}



	private void initListView() {
		mListView=(ListView)findViewById(R.id.listView_playcenter);
		list=new ArrayList<Map<String,String>>();
		initData();
		mAdapter=new PlayCenterAdapter(this, list);
		mListView.addHeaderView(headerListView);
		mListView.setAdapter(mAdapter);
	}

	private void initData() {
		for(int i=0;i<Cons.videoList.size();i++){
			 Map<String, String> map2 = Cons.videoList.get(i);
			Map<String, String> map=new HashMap<String, String>();
			map.put("videoImgurl", map2.get("videoImgurl"));
			map.put("videoTitle", map2.get("videoTitle"));
			map.put("videoPicurl", map2.get("videoPicurl"));
			map.put("videoOutline", map2.get("videoOutline"));
			map.put("videoName", map2.get("videoName"));
			 list.add(map);
		 }
	}

	private void initHeaderListView() {
		headerListView=getLayoutInflater().inflate(R.layout.header_palycenter_listview, null);
		ivPlay=(ImageView)headerListView.findViewById(R.id.image_playcenter_header);
		tvContent=(TextView)headerListView.findViewById(R.id.content_playcenter_header);
		ivPlaying=(ImageView)headerListView.findViewById(R.id.icon_playcenter_header);
		tvContent.setText(videoOutline);
		ImageHttp.ImageLoader(ivPlay, Urlsutil.getInstance().getProductImageInfo+videoImgurl);

		
		ivPlaying.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(PlayCenterActivity.this, PlayerActivity.class);
				intent.putExtra("videoPicurl", videoPicurl);
				intent.putExtra("videoName", videoName);
				intent.putExtra("type", Constant.OtherToPlayerAct);
				startActivity(intent);
			}
		});
	}

	private void initHeaderView() {
		headerView=findViewById(R.id.headerView);
		setHeaderTitle(headerView, getString(R.string.VideoCenterAct_title));
		setHeaderImage(headerView, R.drawable.back, Position.LEFT, new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

//	private void showShare() {
//		 ShareSDK.initSDK(this);
//		 OnekeyShare oks = new OnekeyShare();
//		 //�ر�sso��Ȩ
//		 oks.disableSSOWhenAuthorize(); 
//		 
//		// ����ʱNotification��ͼ�������  2.5.9�Ժ�İ汾�����ô˷���
//		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//		 // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
////		 oks.setTitle(getString(R.string.share));
//		 oks.setTitle("����");
//		 
//		 // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
//		 oks.setTitleUrl("http://sharesdk.cn");
//		 // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
//		 oks.setText("���Ƿ����ı�");
//		 //��������ͼƬ������΢����������ͼƬ��Ҫͨ����˺�����߼�д��ӿڣ�������ע�͵���������΢��
//		 oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
//		 // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
//		 //oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
//		 // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
//		 oks.setUrl("http://sharesdk.cn");
//		 // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
//		 oks.setComment("���ǲ��������ı�");
//		 // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
//		 oks.setSite(getString(R.string.app_name));
//		 // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
//		 oks.setSiteUrl("http://sharesdk.cn");
//		 
//		// ��������GUI
//		 oks.show(this);
//		 }

}
