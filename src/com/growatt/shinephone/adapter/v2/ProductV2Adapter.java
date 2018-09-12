package com.growatt.shinephone.adapter.v2;


import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.GlideUtils;
import com.growatt.shinephone.util.Urlsutil;

import java.util.List;
import java.util.Map;

/**
 * Created by dg on 2017/10/12.
 * ProductsActivity更多产品适配器
 */

public class ProductV2Adapter extends BaseQuickAdapter<Map<String,Object>,BaseViewHolder>{


    public ProductV2Adapter(@LayoutRes int layoutResId, @Nullable List<Map<String, Object>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Map<String, Object> item) {
        String identifying=item.get("identifying").toString();
        TextView tv3 = helper.getView(R.id.textView3);
        ImageView imageView = helper.getView(R.id.imageView1);

        if(identifying.equals("0")){
            tv3.setText(R.string.all_suggest);
            tv3.setBackgroundColor(Color.parseColor("#ff0000"));
        }else if(identifying.equals("1")){
            tv3.setBackgroundColor(Color.parseColor("#00ff00"));
            tv3.setText("NEW");
        }else{
            tv3.setText("");
            tv3.setBackgroundColor(Color.parseColor("#0000ff00"));
        }
        String name=item.get("icon").toString();
        String[] names = name.split("/");
        name=names[names.length-1];
        GlideUtils.getInstance().showImageContext(mContext,Urlsutil.getInstance().getProductImageInfo+name,imageView);
        helper.setText(R.id.textView2,item.get("feature").toString());
        helper.setText(R.id.textView1,item.get("productName").toString());
    }
}
