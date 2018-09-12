package com.growatt.shinephone.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.sqlite.SqliteUtil;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.ImageHttp;
import com.growatt.shinephone.util.Urlsutil;

public class DatadetailedGridAdapter extends BaseAdapter{
	private Context context;
	private List<String>list;
	private LayoutInflater layoutInflater;
	ViewHoder hoder = null;
	public DatadetailedGridAdapter(Context context,List<String>list){
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		if (convertView == null) {
			hoder = new ViewHoder();
			convertView = layoutInflater.inflate(R.layout.datadetailed_grid_item, null);
			hoder.imageView=(ImageView)convertView.findViewById(R.id.imageView1);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHoder) convertView.getTag();
		}
//		if(new File(ShineApplication.Path+list.get(position)).exists()){
//			Bitmap bitmap=BitmapFactory.decodeFile(ShineApplication.Path+list.get(position));
//			hoder.imageView.setImageBitmap(bitmap);
//			System.out.println("�Ѵ�");
//		}else{
//			System.out.println("δ��");
//		}
		String name=list.get(position);
		//name=name.substring(0, name.length()-4);
//		ImageHttp.LoadImage(hoder.imageView, new Urlsutil().getImageInfo+SqliteUtil.inquirylogin().get("name")+"/"+name,name);
		String accountName="";
		if(Cons.userBean!=null){
			accountName=Cons.userBean.accountName;
		}
		ImageHttp.ImageLoader(hoder.imageView, new Urlsutil().getImageInfo+accountName+"/"+name);
		
		return convertView;
	}
	class ViewHoder {
		public ImageView imageView;
	}
}