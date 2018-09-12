package com.growatt.shinephone.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.growatt.shinephone.R;
import com.growatt.shinephone.bean.CustomQuestionListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dg on 2017/8/24.
 * 用户问题列表
 */

public class UserQuestionListAdapter extends BaseQuickAdapter<CustomQuestionListBean,BaseViewHolder>{
    public UserQuestionListAdapter(@LayoutRes int layoutResId, @Nullable List<CustomQuestionListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CustomQuestionListBean item) {
        String able= mContext.getResources().getConfiguration().locale.getCountry();
        int status = item.getStatus();
        int resId = R.drawable.pend_ing_cn;
        if(able.equals("CN")||able.equals("cn")){
            switch (status){
                case 0:
                    resId = R.drawable.pend_ing_cn;
                    break;
                case 1:
                    resId = R.drawable.proce_ssing_cn;
                    break;
                case 2:
                    resId = R.drawable.proce_ssed_cn;
                    break;
                case 3:
                    resId = R.drawable.status_waiting;
                    break;
            }
        }else{
            switch (status){
                case 0:
                    resId = R.drawable.pend_ing_en;
                    break;
                case 1:
                    resId = R.drawable.proce_ssing_en;
                    break;
                case 2:
                    resId = R.drawable.proce_ssed_en;
                    break;
                case 3:
                    resId = R.drawable.status_waiting_en;
                    break;
            }
        }
        helper.setBackgroundRes(R.id.textView1,resId);
        helper.setText(R.id.textView2,item.getTitle());
        ArrayList<CustomQuestionListBean.ReplyList> replyList = item.getReplyList();
        if (replyList != null && replyList.size()>0){
            CustomQuestionListBean.ReplyList reply = replyList.get(0);
            int isAdmin = reply.getIsAdmin();
            switch (isAdmin){
                case 1://客服
                    helper.setText(R.id.textView3, reply.getJobId() + ":");
                    break;
                case 0://客户
                    helper.setText(R.id.textView3, reply.getAccountName() + ":");
                    break;
                default:
                    String name = reply.getJobId();
                    if (TextUtils.isEmpty(name)){
                        name = reply.getAccountName();
                    }
                    helper.setText(R.id.textView3, name + ":");
                    break;
            }
            helper.setText(R.id.textView4, reply.getMessage());
        }else {
            helper.setText(R.id.textView3, item.getAccountName() + ":");
            helper.setText(R.id.textView4, item.getContent());
        }
        helper.setText(R.id.textView5, item.getLastTime());
    }
}
