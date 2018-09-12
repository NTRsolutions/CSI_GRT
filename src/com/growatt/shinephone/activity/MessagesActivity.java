package com.growatt.shinephone.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.MessageAdapter;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.Position;

import java.util.List;
import java.util.Map;

public class MessagesActivity extends DoActivity {

	private ListView listview;
	private MessageAdapter adapter;
	private List<Map<String, Object>> list;
	private ImageView emptyView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);
		initHeaderView();
		SetViews();
		SetListeners();
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
		setHeaderTitle(headerView,getString(R.string.fragment4_message));
		setHeaderTvTitle(headerView, getString(R.string.message_clean), new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog builder = new AlertDialog.Builder(MessagesActivity.this).setTitle(R.string.message_clearaway).setMessage(R.string.message_isclearaway)
						.setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();
								SqliteUtil.delectmessages();
								list.clear();
								adapter.notifyDataSetChanged();
							}
						}).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();
							}
						}).create();
				builder.show();
			}
		});
	}
	private void SetViews() {
		listview=(ListView)findViewById(R.id.listView1);
		emptyView=(ImageView)findViewById(R.id.emptyView);
		if(getLanguage()==0){
			emptyView.setImageResource(R.drawable.message_emptyview);
		}else{
			emptyView.setImageResource(R.drawable.message_emptyview_en);
		}
		list = SqliteUtil.inquirymsg();
		adapter=new MessageAdapter(list, MessagesActivity.this);
		listview.setAdapter(adapter);
		listview.setEmptyView(emptyView);
	}

	private void SetListeners() {
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				Intent intent=new Intent(MessagesActivity.this,MessagedataActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("content", list.get(position).get("content").toString());
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				AlertDialog builder = new AlertDialog.Builder(MessagesActivity.this).setTitle(R.string.MessagesActivity_delete).setMessage(R.string.MessagesActivity_delete_msg)
						.setPositiveButton(R.string.all_ok, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();
								SqliteUtil.delectmessage(list.get(arg2).get("msgid").toString());
								list.remove(arg2);
								adapter.notifyDataSetChanged();
							}
						}).setNegativeButton(R.string.all_no, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								arg0.dismiss();
							}
						}).create();
				builder.show();
				
				return false;
			}
		});
		
	}	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}
