package com.growatt.shinephone.activity;

/**
 * Created by dg on 2017/3/10.
 */
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.growatt.shinephone.R;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.EToast;
import com.growatt.shinephone.util.Position;
import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.x;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;
public abstract class MyAutoActivity extends AutoLayoutActivity {
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        if(savedInstanceState!=null){
            savedInstanceState(savedInstanceState);
            return;
        }
        ShineApplication.getInstance().addActivity(this);
    }
    public void savedInstanceState(Bundle b) {
        Intent intent=new Intent(ShineApplication.context,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        ShineApplication.context.startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putInt("num", Cons.num);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    @Override
    protected void onDestroy() {
        EToast.reset();
        super.onDestroy();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    public static int getCurrentYear(){

        return Calendar.getInstance().get(Calendar.YEAR);
    }
    public int getLanguage(){
        int lan=1;
        Locale locale=getResources().getConfiguration().locale;
        String language=locale.getLanguage();
        if(language.toLowerCase().contains("zh")){
            language="zh_cn";
            lan=0;
        }
        if(language.toLowerCase().contains("en")){
            language="en";
            lan=1;
        }
        if(language.toLowerCase().contains("fr")){
            language="fr";
            lan=2;
        }
        if(language.toLowerCase().contains("ja")){
            language="ja";
            lan=3;
        }
        if(language.toLowerCase().contains("it")){
            language="it";
            lan=4;
        }
        if(language.toLowerCase().contains("ho")){
            language="ho";
            lan=5;
        }
        if(language.toLowerCase().contains("tk")){
            language="tk";
            lan=6;
        }
        if(language.toLowerCase().contains("pl")){
            language="pl";
            lan=7;
        }
        if(language.toLowerCase().contains("gk")){
            language="gk";
            lan=8;
        }
        if(language.toLowerCase().contains("gm")){
            language="gm";
            lan=9;
        }
        return lan;
    }
    public void setHeaderTitle(View headerView,String title,Position position){
        TextView tv = (TextView) headerView.findViewById(R.id.tvTitle);

        if(title==null){
            tv.setText("TITLE");
        }else{
            tv.setText(title);
        }

        switch (position) {
            case LEFT:
                tv.setGravity(Gravity.LEFT);
                break;

            default:
                tv.setGravity(Gravity.CENTER);
                break;
        }

    }
    public void setHeaderTvTitle(View headerView,String title,View.OnClickListener listener){
        TextView tv = (TextView) headerView.findViewById(R.id.tvRight);

        if(title==null){
            tv.setText("");
        }else{
            tv.setText(title);
        }
        if(listener!=null){
            tv.setOnClickListener(listener);
        }

    }
    public void setHeaderTitle(View headerView,String title){
        setHeaderTitle(headerView, title, Position.CENTER);
    }

    public void setHeaderImage(View headerView,int resId,Position position,View.OnClickListener listener){
        ImageView iv = null;
        switch (position) {
            case LEFT:
                iv = (ImageView) headerView.findViewById(R.id.ivLeft);
                break;

            default:
                iv = (ImageView) headerView.findViewById(R.id.ivRight);
                break;
        }

        iv.setImageResource(resId);
//		iv.setColorFilter(Color.WHITE,Mode.SRC_ATOP);
        if(listener!=null){
            iv.setOnClickListener(listener);
        }
    }

    public void setHeaderImage(View headerView,int resId,Position position){
        setHeaderImage(headerView, resId, position, null);
    }

    public void setHeaderImage(View headerView,int resId){
        setHeaderImage(headerView, resId, Position.LEFT);
    }

    //дһЩ��ӡ����˾����Log�ķ���

    public void toast(String text){

        if(TextUtils.isEmpty(text)){
            return;
        }
        if(MyControl.isNotificationEnabled(this)){
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }else{
            EToast.makeText(this, text, EToast.LENGTH_LONG).show();
        }
    }
    public void toast(int resId){
        String text=getString(resId);
        if(TextUtils.isEmpty(text)){
            return;
        }
        if(MyControl.isNotificationEnabled(this)){
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }else{
            EToast.makeText(this, text, EToast.LENGTH_LONG).show();
        }
    }
    public void log(String log){
        Log.d("TAG",this.getClass().getSimpleName()+": "+log);
    }

    public void toastAndLog(String text,String log){
        toast(text);
        log(log);
    }

    public void jumpTo(Class<?> clazz,boolean isFinish){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
        if(isFinish){
            finish();
        }
    }
    public void jumpTo(Intent intent,boolean isFinish){
        startActivity(intent);
        if(isFinish){
            finish();
        }
    }

    public boolean isEmpty(EditText... editTexts){

        for(EditText et:editTexts){
            if(TextUtils.isEmpty(et.getText().toString())){
                toast(getString(R.string.putin_on_data));
                return true;
            }
        }

        return false;
    }
    public String getNumberFormat(String str,int num){
        BigDecimal bd=new BigDecimal(str);
        return bd.setScale(num,BigDecimal.ROUND_HALF_UP)+"";
    }
    //获取屏幕密度
    public float getDensity(){
        return getResources().getDisplayMetrics().density;
    }

}
