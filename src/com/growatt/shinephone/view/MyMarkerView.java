package com.growatt.shinephone.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.MyUtils;

import java.util.Date;

/**
 * Custom implementation of the MarkerView.
 * 
 * @author Philipp Jahoda
 */
public class MyMarkerView extends MarkerView {
//    public static final int minTamp = 60*1000;//一分钟的时间戳
//    public static SimpleDateFormat sdf_hm = new SimpleDateFormat("HH:mm");
    private TextView tvContent;
    private Context mContext;
    private int mXTextId;//x轴数据名称
    private int mYTextId;//y轴数据名称m
    private boolean mShowXy = false;//显示xy名称
    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        mContext = context;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }
    public MyMarkerView(Context context, int layoutResource,int textColor) {
        super(context, layoutResource);
        mContext = context;
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent.setTextColor(ContextCompat.getColor(context,textColor));
    }
    public MyMarkerView(Context context, int layoutResource, int textColor,boolean showXy,int xTextId,int yTextId) {
        super(context, layoutResource);
        mContext = context;
        mXTextId = xTextId;
        mYTextId = yTextId;
        mShowXy = showXy ;
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvContent.setTextColor(ContextCompat.getColor(context,textColor));
    }
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (!mShowXy) {
            if (e instanceof CandleEntry) {
                CandleEntry ce = (CandleEntry) e;
                tvContent.setText("" + ce.getHigh());
            } else {
                tvContent.setText("" + e.getY());
            }
        }else {
            StringBuilder sb = new StringBuilder();
            if (e instanceof CandleEntry) {
                CandleEntry ce = (CandleEntry) e;
                sb.append(mContext.getText(mXTextId)).append(":").append(MyUtils.sdf_hm.format(new Date((long) (ce.getX() * MyUtils.minTamp)))).append("\n").append(mContext.getText(mYTextId)).append(":").append(ce.getHigh());
                tvContent.setText(sb.toString());
            } else {
                sb.append(mContext.getText(mXTextId)).append(":").append(MyUtils.sdf_hm.format(new Date((long) (e.getX() * MyUtils.minTamp)))).append("\n").append(mContext.getText(mYTextId)).append(":").append(e.getY());
                tvContent.setText(sb.toString());
            }
        }
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
