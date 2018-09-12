package com.growatt.shinephone.video;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.growatt.shinephone.R;

public class MyListAdapter extends BaseAdapter
{
    private LayoutInflater inflater;
    private List<ItemBean> items;
    private ItemBean item;
    private OnShowItemClickListener onShowItemClickListener;
    
    public MyListAdapter(List<ItemBean> list,Context context)
    {
	items=list;
	inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
	// TODO 自动生成的方法存根
	return items.size();
    }

    @Override
    public Object getItem(int position) {
	// TODO 自动生成的方法存根
	return items.get(position);
    }
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
 

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	// TODO 自动生成的方法存根
	ViewHolder holder;
	if(convertView==null)
	{
	    holder=new ViewHolder();
	    convertView=inflater.inflate(R.layout.item_cachevideoact_listview, null);
	    holder.img=(ImageView) convertView.findViewById(R.id.imageView1);
	    holder.cb=(CheckBox) convertView.findViewById(R.id.checkBox1);
	    holder.title=(TextView)convertView.findViewById(R.id.title);
	    holder.size=(TextView) convertView.findViewById(R.id.size);
	    holder.time=(TextView) convertView.findViewById(R.id.time);
	    convertView.setTag(holder);
	}else
	{
	   holder=(ViewHolder) convertView.getTag();
	}
	item=items.get(position);
	if(item.isShow())
	{
	    holder.cb.setVisibility(View.VISIBLE);
	}
	else
	{
	    holder.cb.setVisibility(View.GONE);
	}
	holder.img.setImageBitmap(item.getBitmap());
	holder.title.setText(item.getTitle());
	holder.size.setText(item.getSize());
	holder.time.setText(item.getDuration());
	
	holder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    
	   

	    @Override
	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked)
		{
		    item.setChecked(true);
		}
		else
		{
		    item.setChecked(false);
		}
		//回调方法，讲item加入已选择的
		onShowItemClickListener.onShowItemClick(item);
	    }
	});
	//监听后设置选择状态
	holder.cb.setChecked(item.isChecked());
	return convertView;
    }
    
    public class ViewHolder
    {
	ImageView img;
	CheckBox cb;
	TextView title,size,time;
	
    }
    
    public interface OnShowItemClickListener {
	public void onShowItemClick(ItemBean bean);
    }
    
    public void setOnShowItemClickListener(OnShowItemClickListener onShowItemClickListener) {
	this.onShowItemClickListener = onShowItemClickListener;
}

}
