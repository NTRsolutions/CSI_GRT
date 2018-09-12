package com.growatt.shinephone.view;

import android.content.Context;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;

/**
 * Created by dg on 2017/8/2.
 */

public class MyLineChart extends LineChart {
    public MyLineChart(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
