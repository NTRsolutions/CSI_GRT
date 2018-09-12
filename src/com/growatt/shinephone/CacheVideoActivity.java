package com.growatt.shinephone;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import com.growatt.shinephone.activity.DoActivity;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.util.AppUtils;
import com.growatt.shinephone.util.Constant;
import com.growatt.shinephone.util.Position;
import com.growatt.shinephone.video.ItemBean;
import com.growatt.shinephone.video.MyListAdapter;
import com.growatt.shinephone.video.MyListAdapter.OnShowItemClickListener;
import com.growatt.shinephone.video.PlayerActivity;

public class CacheVideoActivity extends DoActivity implements OnShowItemClickListener {

	private View headerView;
    private ListView listView;
    private List<ItemBean> dataList,selectedList;
    private MyListAdapter myAdapter;
	private List<String> getFiles;
	private CheckBox checkBox;
	private ImageView emptyView;
    private static boolean isShow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_cache_video);
			if(AppUtils.sdcardIsExist()){
				getFiles = AppUtils.getFilesList(ShineApplication.VIDEO_PATH, ".mp4", true);  
			}
			initHeaderView();
			initView();
			initListener();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initListener() {
        checkBox.setOnClickListener(new OnClickListener() {
		    
		    @Override
		    public void onClick(View v) {
			// ȫѡ
		    	if(checkBox.isChecked()){
				for (ItemBean bean : dataList) {
					if (!bean.isChecked()) {
						bean.setChecked(true);
						if (!selectedList.contains(bean)) {
							selectedList.add(bean);
						}
					}
				 }
		    	}else{
		    		for (ItemBean bean : dataList) {
						if (bean.isChecked()) {
							bean.setChecked(false);
							if (selectedList.contains(bean)) {
								selectedList.remove(bean);
							}
						}
					 }
		    	}
				myAdapter.notifyDataSetChanged();
		    }
		});
		
		
		
	}



	private void initView() {
		isShow=false;
		checkBox=(CheckBox) findViewById(R.id.checkBox1);
		listView=(ListView) findViewById(R.id.listView1);
		emptyView=(ImageView)findViewById(R.id.emptyView);
		try {
			if(getLanguage()==0){
//				emptyView.setImageBitmap(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.cachevideo_empthview)));
				emptyView.setImageResource(R.drawable.cachevideo_empthview);
			}else{
//				emptyView.setImageBitmap(BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.cachevideo_empthview_en)));
				emptyView.setImageResource(R.drawable.cachevideo_empthview_en);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		listView.setEmptyView(emptyView);
		dataList=new ArrayList<ItemBean>();
		selectedList=new ArrayList<ItemBean>();
		if(getFiles!=null&&getFiles.size()>0){
		for(String string:getFiles)
		{
			String[] split = string.split("/");
			String title=split[split.length-1];
			if(title.length()>4){
			title=title.substring(0, title.length()-4);
			}
		    ItemBean item=new ItemBean();
		    item.setUriPath(string);
		    item.setBitmap(AppUtils.getVideoThumbnail(string, 100, 80, Thumbnails.MICRO_KIND));
		    item.setDuration(getString(R.string.CacheVideoAct_cache_time)+":"+AppUtils.getVideoDuration(string));
		    item.setSize(getString(R.string.CacheVideoAct_cache_size)+":"+AppUtils.getFileOrFilesSize(string, 3)+"M");
		    item.setTitle(title);
		    item.setChecked(false);
		    item.setShow(isShow);
		    log(item.toString()+""+string);
		    dataList.add(item);
		}
	}
		myAdapter=new MyListAdapter(dataList, this);
		myAdapter.setOnShowItemClickListener(CacheVideoActivity.this);
		listView.setAdapter(myAdapter);
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
			// �����ʾ���������ѡ�����������ϣ�������ת����
			if (isShow) {
				ItemBean item = dataList.get(position);
				boolean isChecked = item.isChecked();
				if (isChecked) {
				    item.setChecked(false);
				} else {
				    item.setChecked(true);
				}
				myAdapter.notifyDataSetChanged();
			}else{
				Intent intent=new Intent(CacheVideoActivity.this, PlayerActivity.class);
				intent.putExtra("type", Constant.CacheVideoActToPlayerAct);
				intent.putExtra("uri", dataList.get(position).getUriPath());
				jumpTo(intent, false);
			}
		    }
		});
	}


	private void initHeaderView() {
		headerView=findViewById(R.id.headerView);
		setHeaderImage(headerView, R.drawable.back,Position.LEFT, new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		setHeaderTitle(headerView, getString(R.string.CacheVideoAct_cachelist));
		setHeaderTvTitle(headerView, getString(R.string.CacheVideoAct_delete), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//�ж��Ƿ���ɾ���û�����ж�checkbox״̬����������ʾ������������
				if (selectedList!=null && selectedList.size()>0) {
					//���û�ȷ���Ƿ�ɾ��
					Builder builder = new AlertDialog.Builder(CacheVideoActivity.this)
					.setTitle(getString(R.string.CacheVideoAct_delete))
					.setIcon(android.R.drawable.ic_menu_info_details)
					.setMessage(getString(R.string.CacheVideoAct_deletelist))
					.setPositiveButton(getString(R.string.CacheVideoAct_delete_yes), new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							dataList.removeAll(selectedList);
							//ɾ��sd�ļ�
							for(ItemBean item:selectedList){
								File file=new File(item.getUriPath());
								AppUtils.deleteSDFile(file);
							}
							myAdapter.notifyDataSetChanged();
							selectedList.clear();
							 if(dataList==null||dataList.size()==0){
								 checkBox.setChecked(false);
							 }
							 //ɾ����ɺ����ز�ȡ����ѡ��
							 if (isShow) {
									selectedList.clear();
									for (ItemBean bean : dataList) {
										bean.setChecked(false);
										bean.setShow(false);
									}
									myAdapter.notifyDataSetChanged();
									isShow = false;
								}
								if(checkBox.getVisibility()==View.VISIBLE){
									checkBox.setVisibility(View.GONE);
								}
								if(checkBox.isChecked()){
									checkBox.setChecked(false);
								}
							 
						}
					})
					.setNegativeButton(getString(R.string.CacheVideoAct_delete_no), new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							if (isShow) {
								selectedList.clear();
								for (ItemBean bean : dataList) {
									bean.setChecked(false);
									bean.setShow(false);
								}
								myAdapter.notifyDataSetChanged();
								isShow = false;
							}
							if(checkBox.getVisibility()==View.VISIBLE){
								checkBox.setVisibility(View.GONE);
							}
							if(checkBox.isChecked()){
								checkBox.setChecked(false);
							}
						}
					});
					builder.create().show();
					
				} else {
//					toast(getString(R.string.CacheVideoAct_please_select));
					//��ʾ������checkbox,�Լ�item��check��
					if(checkBox.getVisibility()==View.GONE){
					  if(dataList!=null&&dataList.size()>0){
						checkBox.setVisibility(View.VISIBLE);
						isShow=true;
						selectedList.clear();
						for(ItemBean item:dataList)
						{
							item.setShow(isShow);
						}
						myAdapter.notifyDataSetChanged();
					  }
					}else{
						checkBox.setVisibility(View.GONE);
						if (isShow) {
							selectedList.clear();
							for (ItemBean bean : dataList) {
								bean.setChecked(false);
								bean.setShow(false);
							}
							myAdapter.notifyDataSetChanged();
							isShow = false;
						}
					}
				}
			}
		});
		
	}

	
    @Override
    public void onShowItemClick(ItemBean bean) {
	// TODO �Զ����ɵķ������
	if (bean.isChecked() && !selectedList.contains(bean)) {
		selectedList.add(bean);
	} else if (!bean.isChecked() && selectedList.contains(bean)) {
		selectedList.remove(bean);
	}
    }
   
   

}
