package com.growatt.shinephone.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.Position;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_gif)
public class GifActivity extends DoActivity {
	@ViewInject(R.id.headerView)
	View headerView;
//    @ViewInject(R.id.gif_fail)
//    GifView gvFail;
//    @ViewInject(R.id.gif_ok)
//    GifView gvOk;
    @ViewInject(R.id.tvLED)
    TextView tvLED;
	private float density;
	@ViewInject(R.id.ivFail)
	ImageView ivFail;
	@ViewInject(R.id.ivOk)
	ImageView ivOk;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initHeader();
		initView();
	}
	private void initHeader() {
		setHeaderImage(headerView, R.drawable.back, Position.LEFT, new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		setHeaderTitle(headerView, getString(R.string.fragment4_shinewifi));
	}
	private void initView() {
		tvLED.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		tvLED.getPaint().setAntiAlias(true);
		Glide.with(this).load(R.drawable.gif_connected_ok).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivOk);
		Glide.with(this).load(R.drawable.gif_connected_failed).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivFail);
//		density=getResources().getDisplayMetrics().density;
//		gvOk.setGifImage(R.drawable.gif_connected_ok);
//		gvOk.setShowDimension((int)(density*136), (int)(density*136));
//		gvFail.setGifImage(R.drawable.gif_connected_failed);
//		gvFail.setShowDimension((int)(density*136), (int)(density*136));
	}

    @Event(type=View.OnClickListener.class,value=R.id.tvBack)
    private void tvBack(View v){
    	finish();
    }


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
