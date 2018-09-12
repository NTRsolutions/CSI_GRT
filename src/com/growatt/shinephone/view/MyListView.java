package com.growatt.shinephone.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.growatt.shinephone.R;

/**
 * Created by dg on 2017/6/23.
 */

public class MyListView extends ListView implements AbsListView.OnScrollListener {

      private int lastVisibleItem;//最后一个可见的item

     private int totalItemCount;//总的item
     private boolean isLoading = false;//是否正在加载数据
     private ILoadListener mListener;//回调接口，用来加载数据
     private View footer;//底布局
     public MyListView(Context context) {
        super(context);
          initView(context);
 }
public MyListView(Context context, AttributeSet attrs) {
             super(context, attrs);
              initView(context);
           }
     public MyListView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
              initView(context);
           }

   public interface ILoadListener{
        void loadData();
     }

         public void setOnILoadListener(ILoadListener listener){
            this.mListener = listener;
           }
   private void initView(Context context){

           footer = LayoutInflater.from(context).inflate(R.layout.load_page2, null);

              //注意，这句代码的意思是给自定义的ListView加上底布局
               this.addFooterView(footer);

             //首先需要隐藏这个底部布局
              footer.findViewById(R.id.load_layout).setVisibility(View.GONE);

             this.setOnScrollListener(this);//千万别忘记设定监听器

           }

    public void loadFinish(){
             isLoading = false;//不再加载了
              //底布局也要隐藏
              footer.findViewById(R.id.load_layout).setVisibility(View.GONE);

         }

            public void onScrollStateChanged(AbsListView view, int scrollState) {

              //如果最后一个可见item等于总的item，且当前滚动状态为滚动停止，就应该开始加加载数据了
                if(lastVisibleItem == totalItemCount && scrollState==SCROLL_STATE_IDLE){

                      if(!isLoading){
                          //设置底布局可见
                          footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                          isLoading = true;
                          //加载数据
                          mListener.loadData();
                      }
                }
            }

            /***
  98      * 该方法用来监听实时滚动中的item
  99      * firstVisibleItem:当前第一个可见的item
  100      * visibleItemCount:当前总共有多少个可见的item
  101      * totalItemCount:总的item
  102      */
            public void onScroll(AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
               this.lastVisibleItem = firstVisibleItem + visibleItemCount;
                this.totalItemCount = totalItemCount;

            }
}