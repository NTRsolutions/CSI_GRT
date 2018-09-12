package com.growatt.shinephone.listener;

import java.util.Map;

/**
 * Created by dg on 2017/6/13.
 */

public interface OnPostListener {
    //参数
    void Params(Map<String, Object> params);
    //返回成功
    void success( String json);
    //返回失败
    void error(String str);
}
