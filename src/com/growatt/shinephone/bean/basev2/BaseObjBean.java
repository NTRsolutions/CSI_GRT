package com.growatt.shinephone.bean.basev2;

/**
 * Created：2017/11/23 on 16:23
 * Author:gaideng on dg
 * Description:
 */

public class BaseObjBean<T>{
    private int result;
    private T obj;
    private String msg;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
