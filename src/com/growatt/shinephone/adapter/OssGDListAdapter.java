package com.growatt.shinephone.adapter;

import android.content.Context;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.OssGDQuestionListBean;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

/**
 * Created by dg on 2017/6/29.
 */

public class OssGDListAdapter implements ItemViewDelegate<OssGDQuestionListBean> {
    private Context mContent;

    public OssGDListAdapter(Context mContent) {
        this.mContent = mContent;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_oss_kfgd_list;
    }

    @Override
    public boolean isForViewType(OssGDQuestionListBean item, int position) {
        return item.getType() == 2;
    }

    @Override
    public void convert(ViewHolder viewHolder, OssGDQuestionListBean item, int position) {
        viewHolder.setText(R.id.textView2,item.getTitle());
        viewHolder.setText(R.id.textView5,item.getApplicationTime());
        viewHolder.setText(R.id.textView3,item.getGroupName() + " ");
        viewHolder.setText(R.id.textView4,item.getAddress());
        int status = item.getStatus();
        switch (status){
            case 2:
                viewHolder.setImageResource(R.id.ivIcon,R.drawable.circle_ossgd_wait);
                viewHolder.setText(R.id.tvStatus,"待接收");
                break;
            case 3:
                viewHolder.setImageResource(R.id.ivIcon,R.drawable.circle_ossgd_processing);
                viewHolder.setText(R.id.tvStatus,"服务中");
                break;
            case 4:
                viewHolder.setImageResource(R.id.ivIcon,R.drawable.circle_ossgd_yes);
                viewHolder.setText(R.id.tvStatus,"已完成");
                break;
        }
    }
}
