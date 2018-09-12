package com.growatt.shinephone.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.TimeActivity;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.T;

public class InverterDialog extends Dialog{
	private boolean flag=true;
	private LayoutInflater layoutInflater;
	public InverterDialog(final Context context,final int time,final String[]hours,final String[]mins) {
		super(context,R.style.myDialogTheme);
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View  view=inflater.inflate(R.layout.dialog_storage_time, null); 
		final TextView textView=(TextView)view.findViewById(R.id.textView2);
		Button bt=(Button)view.findViewById(R.id.button1);
		bt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(TimeActivity.dialogtime.isShowing()){
					TimeActivity.dialogtime.dismiss();
					TimeActivity.time="";
				}
			}
		});
		textView.setText(time+":__:__");
		final GridView gridView=(GridView)view.findViewById(R.id.gridView1);
		StorageAdapter adapter=new StorageAdapter(context,hours);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				if(flag==true){
					flag=false;
					Cons.hour=hours[position];
					textView.setText(time+":"+Cons.hour+":__");
					StorageAdapter adapter=new StorageAdapter(context,mins);
					gridView.setAdapter(adapter);
				}else{
					Cons.min=mins[position];
					if(TimeActivity.flag.equals("1")){
						TimeActivity.starttime.setText(Cons.hour+":"+Cons.min);
					}else if(TimeActivity.flag.equals("2")){
						TimeActivity.stoptime.setText(Cons.hour+":"+Cons.min);
					}
					if(TimeActivity.dialogtime.isShowing()){
						TimeActivity.dialogtime.dismiss();
						T.make(time+":"+Cons.hour+":"+Cons.min,context);
					}
				}
			}
		});
		setContentView(view);
	}
//	public interface Dialoglistener{
//		void listener();
//	}
	class StorageAdapter extends BaseAdapter{
		private String[]strs;
		public StorageAdapter(Context context,String[]str){
			this.strs=str;
			layoutInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return strs.length;
		}

		@Override
		public Object getItem(int arg0) {
			return strs[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			ViewHoder hoder = null;
			if (convertView == null) {
				hoder = new ViewHoder();
				convertView = layoutInflater.inflate(R.layout.storage_time_item, null);
				hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
				convertView.setTag(hoder);
			} else {
				hoder = (ViewHoder) convertView.getTag();
			}
			hoder.tv1.setText(strs[position]);
			return convertView;
		}
		class ViewHoder {
			public TextView tv1;
		}
	}
}
