package com.growatt.shinephone.listener;

import java.util.Map;

/**
 * Created by dg on 2017/6/13.
 */

public interface OnGetListener {
    //参数
    void Params(Map<String, String> params);
    //返回成功
    void success( String json);
    //返回失败
    void error(String err);
}
