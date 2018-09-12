
package com.growatt.shinephone.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.growatt.shinephone.R;
import com.growatt.shinephone.control.MyControl;
import com.growatt.shinephone.util.Cons;
import com.growatt.shinephone.util.EToast;
import com.growatt.shinephone.util.Position;

import org.xutils.x;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;

import cn.jpush.android.api.JPushInterface;


/**
 * Baseclass of all Activities of the Demo Application.
 * 
 * @author Philipp Jahoda
 */
public abstract class DemoBase extends FragmentActivity {

//    protected String[] mMonths = new String[] {
//            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
//    };
    protected int[] mParties ={
    		R.string.washer,R.string.air_condition,R.string.waterheater,R.string.television,R.string.light
    };
    Toast toast;
	protected Context mContext;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		x.view().inject(this);
//		if(VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
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
	//Log.d("save", "from onsaveinstancestate");
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
	/**
	 * ��ȡ��ǰ���
	 * @return
	 */
    public static int getCurrentYear(){
		
		return Calendar.getInstance().get(Calendar.YEAR);
	}
    /**
     * ��ȡϵͳ����
     * @return
     */
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
	//����ͷ�����ֵķ���
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
	public void setHeaderTvTitle(View headerView,String title,OnClickListener listener){
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
	public void setHeaderTvLeft(View headerView,String text,OnClickListener listener){
		TextView tv = (TextView) headerView.findViewById(R.id.tvLeft);
		if(text == null){
			tv.setText("");
		}else{
			tv.setText(text);
		}
		if(listener!=null){
			tv.setOnClickListener(listener);
		}
	}
	//���ط��������ñ���ʱ��ָ��λ�ã����ñ��������ʾ
	public void setHeaderTitle(View headerView,String title){
		setHeaderTitle(headerView, title, Position.CENTER);
	}

	/**
	 * 
	 * @param headerView
	 * @param resId
	 * @param position LEFT ������ͷ������ImageView
	 *                 RIGHT �� CENTER��Ϊ����ͷ���Ҳ��ImageView
	 * @param listener 
	 */
	public void setHeaderImage(View headerView,int resId,Position position,OnClickListener listener){
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

	//дһЩ������ת�ķ���
	//�򵥵Ľ�����ת������Ҫ����Intent���ݲ���
	public void jumpTo(Class<?> clazz,boolean isFinish){
		Intent intent = new Intent(this,clazz);
		startActivity(intent);
		if(isFinish){
			finish();
		}
	}
	
	//������תʱ����ҪIntentЯ������
	public void jumpTo(Intent intent,boolean isFinish){
		startActivity(intent);
		if(isFinish){
			finish();
		}
	}
	/**
	 * �ж��û���EditText���������Ϣ�Ƿ�����
	 * @param editTexts
	 * @return false ��ζ���û��������������
	 *         true  ��ζ��������һ��EditText�û�δ��������
	 */
	
	public boolean isEmpty(EditText... editTexts){
		
		for(EditText et:editTexts){
			if(TextUtils.isEmpty(et.getText().toString())){
				
//				String string = "����������!";
				//����string����һ��SpannableString
//				SpannableString ss = new SpannableString(string);
				
//				ss.setSpan(new ForegroundColorSpan(Color.GREEN), 3,5, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
//				ss.setSpan(new BackgroundColorSpan(Color.BLACK), 0, 4, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
//				ss.setSpan(new ImageSpan(this, R.drawable.ue059), 5, 6, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
//				ss.setSpan(new AbsoluteSizeSpan(20, true), 3, 5, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
//				
//				et.setError(ss);
				toast(getString(R.string.putin_on_data));
				return true;
			}
		}
		
		return false;
	}
    /**
     * ������λС��
     * @param str  ԭʼ����
     * @param num  ����С��λ��
     * @return
     */
    public String getNumberFormat(String str,int num){
    	BigDecimal bd=new BigDecimal(str);
    	return bd.setScale(num,BigDecimal.ROUND_HALF_UP)+"";
    }
	//获取屏幕密度
	public float getDensity(){
		return getResources().getDisplayMetrics().density;
	}
	public void onStart() {
		super.onStart();
	}

	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}

	public void onStop() {
		super.onStop();
	}
}
