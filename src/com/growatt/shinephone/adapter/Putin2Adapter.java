package com.growatt.shinephone.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.growatt.shinephone.R;
import com.growatt.shinephone.activity.BigPictureActivity;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;



/**
 * Created by dg on 2017/6/22.
 */

public class Putin2Adapter extends com.zhy.adapter.recyclerview.CommonAdapter<String>{


    public Putin2Adapter(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, String file, final int position) {
        ImageView ivPhoto = holder.getView(R.id.ivPhoto);
        Glide.with(mContext).load(file).into(ivPhoto);
        ImageView ivDelete = holder.getView(R.id.ivDelete);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(position);
                notifyDataSetChanged();
            }
        });
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String file = mDatas.get(position);
//                String filePath = file.getAbsolutePath();
                Intent intent = new Intent(mContext, BigPictureActivity.class);
                intent.putExtra("path",file);
                intent.putExtra("type",1);
                mContext.startActivity(intent);
            }
        });
    }
}
