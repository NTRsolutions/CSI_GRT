package com.growatt.shinephone.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.PhotoenlargeActivity;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.Urlsutil;

public class DatadetailedAdapter extends BaseAdapter{
	private Context context;
	private List<Map<String, Object>>list;
	private LayoutInflater layoutInflater;
	private ArrayList<String> lists;
	public DatadetailedAdapter(Context context,List<Map<String, Object>>list){
		this.context=context;
		this.list=list;
		layoutInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHoder hoder = null;
		if (convertView == null) {
			hoder = new ViewHoder();
			convertView = layoutInflater.inflate(R.layout.datadetailed_item, null);
			hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
//			hoder.tv2 = (TextView) convertView.findViewById(R.id.textView2);
			hoder.webView=(WebView)convertView.findViewById(R.id.webview);
			hoder.tv3 = (TextView) convertView.findViewById(R.id.textView3);
			hoder.iamgeView = (ImageView) convertView.findViewById(R.id.imageView1);
			hoder.iamgeView2 = (ImageView) convertView.findViewById(R.id.imageView2);
			hoder.iamgeView3 = (ImageView) convertView.findViewById(R.id.imageView3);
			hoder.iamgeView4 = (ImageView) convertView.findViewById(R.id.imageView4);
			hoder.linear_images = (LinearLayout) convertView.findViewById(R.id.linear_images);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
//		TextPaint tp = hoder.tv1.getPaint();
//		tp.setFakeBoldText(true); 
		hoder.tv1.setText(list.get(position).get("userName").toString()+":");
//		if(list.get(position).get("message").toString().equals("")){
//			hoder.tv2.setVisibility(View.GONE);
//		}else{
//			hoder.tv2.setVisibility(View.VISIBLE);
//			hoder.tv2.setText(list.get(position).get("message").toString());
//		}
		if(TextUtils.isEmpty(list.get(position).get("message").toString())){
			hoder.webView.setVisibility(View.GONE);
		}else{
			hoder.webView.setVisibility(View.VISIBLE);
			hoder.webView.loadDataWithBaseURL(null,list.get(position).get("message").toString(), "text/html", "utf-8", null);
		}
		
		hoder.tv3.setText(list.get(position).get("time").toString());
		String questionImage = list.get(position).get("attachment").toString();
		System.out.println("questionImage="+questionImage);
		if(TextUtils.isEmpty(questionImage)||"null".equals(questionImage)){
			hoder.linear_images.setVisibility(View.GONE);
		}else{
			hoder.linear_images.setVisibility(View.VISIBLE);
		}
		String isadmin=list.get(position).get("isAdmin").toString();
		if(isadmin.equals("1")){
			hoder.iamgeView.setImageResource(R.drawable.kefu);
		}else{
			hoder.iamgeView.setImageResource(R.drawable.me);
			
		}
		String accountName="";
		if(Cons.userBean!=null){
			accountName=Cons.userBean.accountName;
		}
		if(questionImage.length()>6&&questionImage.contains(".")){
			hoder.linear_images.setVisibility(View.VISIBLE);
			if(questionImage.contains("_")){
				lists=new ArrayList<String>();
				String temp[]=questionImage.split("_");
				for (int i = 0; i < temp.length; i++) {
					String name=temp[i];
					lists.add(name);
					if(i==0){
//						ImageHttp.LoadImage(hoder.iamgeView2, new Urlsutil().getQuestionIng+"&userId="+Cons.userId+"&imageName="+name,name);
//						ImageHttp.LoadImage(hoder.iamgeView2, new Urlsutil().getImageInfo+SqliteUtil.inquirylogin().get("name")+"/"+name,name);
						ImageHttp.ImageLoader(hoder.iamgeView2, new Urlsutil().getImageInfo+accountName+"/"+name);
						
					}
					if(i==1){
//						ImageHttp.LoadImage(hoder.iamgeView3, new Urlsutil().getQuestionIng+"&userId="+Cons.userId+"&imageName="+name,name);
//						ImageHttp.LoadImage(hoder.iamgeView3, new Urlsutil().getImageInfo+SqliteUtil.inquirylogin().get("name")+"/"+name,name);
						ImageHttp.ImageLoader(hoder.iamgeView2, new Urlsutil().getImageInfo+accountName+"/"+name);
						
					}
					if(i==2){
//						ImageHttp.LoadImage(hoder.iamgeView4, new Urlsutil().getQuestionIng+"&userId="+Cons.userId+"&imageName="+name,name);
//						ImageHttp.LoadImage(hoder.iamgeView4, new Urlsutil().getImageInfo+SqliteUtil.inquirylogin().get("name")+"/"+name,name);
						ImageHttp.ImageLoader(hoder.iamgeView2, new Urlsutil().getImageInfo+accountName+"/"+name);
						
					}
				}
			}else{
				String name=questionImage;
				lists.add(name);
				//name=name.substring(0, name.length()-4);
//				ImageHttp.LoadImage(hoder.iamgeView2, new Urlsutil().getQuestionIng+"&userId="+Cons.userId+"&imageName="+name,name);
//				ImageHttp.LoadImage(hoder.iamgeView2, new Urlsutil().getImageInfo+SqliteUtil.inquirylogin().get("name")+"/"+name,name);
				ImageHttp.ImageLoader(hoder.iamgeView2, new Urlsutil().getImageInfo+accountName+"/"+name);
			}
		
			hoder.iamgeView2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent(context,PhotoenlargeActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("name", (list.get(position).get("attachment").toString().split("_"))[0]);
					intent.putExtras(bundle);
					context.startActivity(intent);
					
				}
			});
			hoder.iamgeView3.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(lists.size()<2){
						return;
					}
					Intent intent=new Intent(context,PhotoenlargeActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("name", (list.get(position).get("attachment").toString().split("_"))[1]);
					intent.putExtras(bundle);
					context.startActivity(intent);
					
				}
			});
			hoder.iamgeView4.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(lists.size()<3){
						return;
					}
					Intent intent=new Intent(context,PhotoenlargeActivity.class);
					Bundle bundle=new Bundle();
					bundle.putString("name", (list.get(position).get("attachment").toString().split("_"))[2]);
					intent.putExtras(bundle);
					context.startActivity(intent);
					
				}
			});
			
//		DatadetailedGridAdapter gridadapter = new DatadetailedGridAdapter(context, lists);
//		hoder.gridView.setAdapter(gridadapter);
//		hoder.gridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//					long arg3) {
//				Intent intent=new Intent(context,PhotoenlargeActivity.class);
//				Bundle bundle=new Bundle();
//				bundle.putString("name", lists.get(position));
//				intent.putExtras(bundle);
//				context.startActivity(intent);
//			}
//		});
		}else{
			hoder.linear_images.setVisibility(View.GONE);
		}
		return convertView;
	}
	class ViewHoder {
		public ImageView iamgeView4;
		public ImageView iamgeView3;
		public ImageView iamgeView2;
		public TextView tv1;
//		public TextView tv2;
		public TextView tv3;
		public ImageView iamgeView;
		public LinearLayout linear_images;
		public WebView webView;
	}
}