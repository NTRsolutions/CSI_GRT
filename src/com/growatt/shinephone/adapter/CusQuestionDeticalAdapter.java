package com.growatt.shinephone.adapter;

import android.content.Context;
import android.os.Environment;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.ShineApplication;
import com.growatt.shinephone.bean.CustomQuestionDeticalBean;
import com.growatt.shinephone.util.MyUtils;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by dg on 2017/8/25.
 * 客户问题详情
 */

public class CusQuestionDeticalAdapter extends com.zhy.adapter.abslistview.CommonAdapter<CustomQuestionDeticalBean.ReplyList> {
    public CusQuestionDeticalAdapter(Context context, int layoutId, List<CustomQuestionDeticalBean.ReplyList> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, CustomQuestionDeticalBean.ReplyList item, int position) {
        //问题内容
        TextView tvMessage = viewHolder.getView(R.id.tvMessage);
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(item.getMessage(),Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(item.getMessage());
        }
        tvMessage.setText(result);
        int isAdmin = item.getIsAdmin();
        switch (isAdmin){
            case 1://客服
                viewHolder.setText(R.id.tvUserName,item.getJobId());
                break;
            case 0://客户
                viewHolder.setText(R.id.tvUserName,item.getAccountName());
                break;
            default:
                String name = item.getJobId();
                if (TextUtils.isEmpty(name)){
                    name = item.getAccountName();
                }
                viewHolder.setText(R.id.tvUserName,name);
                break;
        }
        viewHolder.setText(R.id.tvTime,item.getTime());
        //查看图片是否显示
        String attachment = item.getAttachment();
        View tvImage = viewHolder.getView(R.id.tvImage);
        if (TextUtils.isEmpty(attachment) || "null".equals(attachment)){
            MyUtils.hideAllView(View.INVISIBLE,tvImage);
        }else {
            MyUtils.showAllView(tvImage);
        }
        //回复问题头像：1：客服；0：客户
        switch (isAdmin){
            case 1:
                Glide.with(mContext).load(R.drawable.ossgd_kefu_icon).into((ImageView)viewHolder.getView(R.id.ivImage));
                break;
            case 0:
                Glide.with(mContext).load(Environment.getExternalStorageDirectory()+"/"+ ShineApplication.IMAGE_FILE_LOCATION)
                        .bitmapTransform(new CropCircleTransformation(mContext))
                        .placeholder(R.drawable.ossgd_user)
                        .error(R.drawable.ossgd_user)
                        .into((ImageView)viewHolder.getView(R.id.ivImage));
                break;
        }
    }
}
