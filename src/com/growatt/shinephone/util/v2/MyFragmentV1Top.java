package com.growatt.shinephone.util.v2;

import com.growatt.shinephone.bean.v2.Fragment1ListBean;

import java.util.Comparator;

/**
 * Created：2017/12/4 on 19:53
 * Author:gaideng on dg
 * Description:FragmentV2置顶比较器
 */

public class MyFragmentV1Top implements Comparator<Fragment1ListBean> {
    @Override
    public int compare(Fragment1ListBean o1, Fragment1ListBean o2) {
        Long v1 = Long.valueOf(o1.getTimeId());
        Long v2 = Long.valueOf(o2.getTimeId());
        if (v2 != null) {
            return v2.compareTo(v1);
        }
        return 0;
    }
}
