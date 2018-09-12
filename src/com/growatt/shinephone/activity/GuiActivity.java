package com.growatt.shinephone.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.growatt.shinephone.R;
import com.growatt.shinephone.adapter.GuideAdapter;
import com.growatt.shinephone.util.Cons;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GuiActivity extends DoActivity {

	private ViewPager guideViewPager;
	private Button guideButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gui);
		Cons.isFirst=true;
		setUpView();
		addListener();
	}

	private void addListener() {
		guideViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// ������״̬�ı仯
				// ����ֻ��������Ϳ�����
				// ��Ϊ2��ʱ����ǵ���ҳ
				if (arg0 == 2) {
					// ������ʾ
					guideButton.setVisibility(View.VISIBLE);
				} else {
					// ���ò���ʾ
					guideButton.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});

		guideButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				guideButton.setTextColor(Color.parseColor("#7811a7F3"));
				GuiActivity.this.startActivity(new Intent(GuiActivity.this,
						LoginActivity.class));
				finish();
			}
		});
	}
	private void setUpView() {
		guideButton = (Button) findViewById(R.id.guide_button);
		guideViewPager = (ViewPager) findViewById(R.id.guide_viewpage);
		List<View> guides = new ArrayList<View>();
		String able= getResources().getConfiguration().locale.getCountry(); 
		if(able.equals("CN")||able.equals("cn")){
			ImageView imageView = new ImageView(this);
//			imageView.setBackgroundResource(R.drawable.cn_1);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView.setImageBitmap(readBitMap(this, R.drawable.cn_1));

			guides.add(imageView);

			ImageView imageView1 = new ImageView(this);
//			imageView1.setBackgroundResource(R.drawable.en_2);
			imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView1.setImageBitmap(readBitMap(this, R.drawable.cn_2));
			guides.add(imageView1);

			ImageView imageView11 = new ImageView(this);
			imageView11.setScaleType(ImageView.ScaleType.FIT_XY);
//			imageView11.setBackgroundResource(R.drawable.cn_3);
			imageView11.setImageBitmap(readBitMap(this, R.drawable.cn_3));
			guides.add(imageView11);
		}else{
			ImageView imageView = new ImageView(this);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//			imageView.setBackgroundResource(R.drawable.en_1);
			imageView.setImageBitmap(readBitMap(this, R.drawable.en_1));
			guides.add(imageView);

			ImageView imageView1 = new ImageView(this);
			imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
//			imageView1.setBackgroundResource(R.drawable.cn_2);
			imageView1.setImageBitmap(readBitMap(this, R.drawable.en_2));
			guides.add(imageView1);

			ImageView imageView11 = new ImageView(this);
//			imageView11.setBackgroundResource(R.drawable.en_3);
			imageView11.setScaleType(ImageView.ScaleType.FIT_XY);
			imageView11.setImageBitmap(readBitMap(this, R.drawable.en_3));
			guides.add(imageView11);
		}
		
		GuideAdapter guideAdapter = new GuideAdapter(guides);
		guideViewPager.setAdapter(guideAdapter);
	}
	/**
	* 以最省内存的方式读取本地资源的图片
	* @param context
	*@param resId
	* @return
	*/
	public static Bitmap readBitMap(Context context, int resId){
	BitmapFactory.Options opt = new BitmapFactory.Options();
	opt.inPreferredConfig = Bitmap.Config.RGB_565; 
	opt.inPurgeable = true;
	opt.inInputShareable = true;
	//获取资源图片
	InputStream is = context.getResources().openRawResource(resId);
	return BitmapFactory.decodeStream(is,null,opt);
	}
}
