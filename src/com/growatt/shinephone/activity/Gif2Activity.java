package com.growatt.shinephone.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.ant.liao.GifView;
import com.growatt.shinephone.R;
import com.growatt.shinephone.util.MyUtils;
import com.growatt.shinephone.util.Position;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.activity_gif2)
public class Gif2Activity extends MyAutoActivity{
    @ViewInject(R.id.gifRed)
    GifView gifRed;
    @ViewInject(R.id.gifGreen)
    GifView gifGreen;
    @ViewInject(R.id.gifBlue)
    GifView gifBlue;
    @ViewInject(R.id.headerView)
    View headerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader();
        initView();
    }

    private void initView() {
        int height = MyUtils.getScreenHeightNoStatusBar(this);
        gifRed.setGifImage(R.drawable.wifi_set_new_red);
        gifRed.setShowDimension(250*height/1280, 250*height/1280);
        gifGreen.setGifImage(R.drawable.wifi_set_new_green);
        gifGreen.setShowDimension(250*height/1280, 250*height/1280);
        gifBlue.setGifImage(R.drawable.wifi_set_new_blue);
        gifBlue.setShowDimension(250*height/1280, 250*height/1280);
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
    @Event(type = View.OnClickListener.class,value = R.id.btnBack)
    private void btnBack(View v){
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
