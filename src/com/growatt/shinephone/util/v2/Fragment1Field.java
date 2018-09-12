package com.growatt.shinephone.util.v2;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created：2017/12/2 on 9:25
 * Author:gaideng on dg
 * Description:所有fragment的静态变量：用于fragment/EnergyFrg/EnergyFrg5/MixFrag
 */

public class Fragment1Field {
    /**
     * fragment1静态变量
     */
    //储能机sn
    public static String sn;
    public static String mixSn;
    //储能机类型和mix类型
    public static String deviceType = "0";
    public static boolean isMix = false;
    /**
     * fragment3静态变量
     */
    //fragment3下面图表详情界面EnergyDetial2Activity
    public static List<ArrayList<BarEntry>> barYList;
}
