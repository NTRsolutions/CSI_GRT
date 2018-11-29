
package com.growatt.shinephone.ui;


public class StorageDialog {
//	private boolean flag=true;
//	private LayoutInflater layoutInflater;
//	public StorageDialog(final Context context,final String time,final String[]hours,final String[]mins) {
//		super(context,R.style.myDialogTheme);
//		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View  view=inflater.inflate(R.layout.dialog_storage_time, null); 
//		final TextView textView=(TextView)view.findViewById(R.id.textView2);
//		Button bt=(Button)view.findViewById(R.id.button1);
//		bt.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				if(StorageSetActivity.dialogtime.isShowing()){
//					StorageSetActivity.dialogtime.dismiss();
//					StorageSetActivity.time="";
//				}
//			}
//		});
//		textView.setText(time+"：__:__");
//		final GridView gridView=(GridView)view.findViewById(R.id.gridView1);
//		StorageAdapter adapter=new StorageAdapter(context,hours);
//		gridView.setAdapter(adapter);
//		gridView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View view, int position,
//					long arg3) {
//				if(flag==true){
//					flag=false;
//					Cons.hour=hours[position];
//					textView.setText(time+"："+Cons.hour+":__");
//					StorageAdapter adapter=new StorageAdapter(context,mins);
//					gridView.setAdapter(adapter);
//				}else{
//					Cons.min=mins[position];
//					if(StorageSetActivity.flag.equals("1")){
//						StorageSetActivity.starttime.setText(Cons.hour+" : "+Cons.min);
//					}else if(StorageSetActivity.flag.equals("2")){
//						StorageSetActivity.stoptime.setText(Cons.hour+" : "+Cons.min);
//					}
//					if(StorageSetActivity.dialogtime.isShowing()){
//						StorageSetActivity.dialogtime.dismiss();
//						T.make(time+"："+Cons.hour+":"+Cons.min);
//					}
//				}
//			}
//		});
//		setContentView(view);
//	}
//	class StorageAdapter extends BaseAdapter{
//		private String[]strs;
//		public StorageAdapter(Context context,String[]str){
//			this.strs=str;
//			layoutInflater = LayoutInflater.from(context);
//		}
//		@Override
//		public int getCount() {
//			return strs.length;
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			return strs[arg0];
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			return arg0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup arg2) {
//			ViewHoder hoder = null;
//			if (convertView == null) {
//				hoder = new ViewHoder();
//				convertView = layoutInflater.inflate(R.layout.storage_time_item, null);
//				hoder.tv1 = (TextView) convertView.findViewById(R.id.textView1);
//				convertView.setTag(hoder);
//			} else {
//				hoder = (ViewHoder) convertView.getTag();
//			}
//			hoder.tv1.setText(strs[position]);
//			return convertView;
//		}
//		class ViewHoder {
//			public TextView tv1;
//		}
//	}
}
