package com.growatt.shinephone.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUnit {
    private static SharedPreferencesUnit sharedPreferencesUnit;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //一个私有的构造方法
    private SharedPreferencesUnit(Context context){
        //属于文件上下文
        sharedPreferences =context.getSharedPreferences("info",context.MODE_PRIVATE );
        editor= sharedPreferences.edit();
    }
    //向外提供一个当前的对象
    public static SharedPreferencesUnit getInstance(Context context){
        if(sharedPreferencesUnit== null){
            sharedPreferencesUnit = new SharedPreferencesUnit(context);
        }
        return sharedPreferencesUnit;
    }

    //保存
    public void put(String key ,String value){
        editor.putString(key, value);
        editor.commit();
    }
    //获取
    public String get(String key){
        return sharedPreferences.getString(key, "0");
    }
    //保存
    public void putInt(String key ,int value){
        editor.putInt(key, value);
        editor.commit();
    }
    //获取
    public int getInt(String key){
        return sharedPreferences.getInt(key, 0);
    }
    public void clear(){
        editor.clear();
        editor.commit();
    }
}
