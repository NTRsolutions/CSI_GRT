package com.growatt.shinephone.adapter;

import android.content.Context;

import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.OssGDQuestionListBean;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;


/**
 * Created by dg on 2017/6/29.
 */

public class OssKFListAdapter implements ItemViewDelegate<OssGDQuestionListBean> {
    private Context mContent;
    public OssKFListAdapter() {
    }
    public OssKFListAdapter(Context mContent) {
        this.mContent = mContent;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_oss_kfgd_list;
    }

    @Override
    public boolean isForViewType(OssGDQuestionListBean item, int position) {
        return item.getType() == 1;
    }

    @Override
    public void convert(ViewHolder viewHolder, OssGDQuestionListBean item, int position) {
        viewHolder.setText(R.id.textView2,item.getTitle());
        viewHolder.setText(R.id.textView5,item.getCreaterTime());
        viewHolder.setText(R.id.textView3,item.getAccountName() + ":");
        viewHolder.setText(R.id.textView4,item.getContent());
        int status = item.getStatus();
        switch (status){
            case 0:
                viewHolder.setImageResource(R.id.ivIcon,R.drawable.circle_osskf_no);
                viewHolder.setText(R.id.tvStatus,mContent.getString(R.string.未处理));
                break;
            case 1:
                viewHolder.setImageResource(R.id.ivIcon,R.drawable.circle_osskf_processing);
                viewHolder.setText(R.id.tvStatus,mContent.getString(R.string.正在处理));
                break;
            case 2:
                viewHolder.setImageResource(R.id.ivIcon,R.drawable.circle_osskf_yes);
                viewHolder.setText(R.id.tvStatus,mContent.getString(R.string.已处理));
                break;
            case 3:
                viewHolder.setImageResource(R.id.ivIcon,R.drawable.circle_osskf_wait);
                viewHolder.setText(R.id.tvStatus,mContent.getString(R.string.m76待跟进));
                break;
        }
    }
}
