package com.growatt.shinephone.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.growatt.shinephone.R;

@SuppressLint("AppCompatCustomView")
public class MyRadioButton extends RadioButton {
    private Drawable drawableTop;  
    private int mTopWith, mTopHeight;

    public MyRadioButton(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        initView(context, attrs);  
    }  
  
    public MyRadioButton(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        initView(context, attrs);  
    }  
  
    public MyRadioButton(Context context) {  
        super(context);  
        initView(context, null);  
    }  
  
    private void initView(Context context, AttributeSet attrs) {  
        if (attrs != null) {  
            float scale = context.getResources().getDisplayMetrics().density;  
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);  
            int n = a.getIndexCount();  
            for (int i = 0; i < n; i++) {  
                int attr = a.getIndex(i);  
                switch (attr) {  
                case R.styleable.MyRadioButton_drawableTop:  
                    drawableTop = a.getDrawable(attr);  
                    break;  
                case R.styleable.MyRadioButton_drawableTopWith:  
//                    mTopWith = (int) (a.getDimension(attr, 20) * scale + 0.5f);  
                	  mTopWith=a.getDimensionPixelSize(attr, 20);
                    break;  
                case R.styleable.MyRadioButton_drawableTopHeight:  
//                    mTopHeight = (int) (a.getDimension(attr, 20) * scale + 0.5f);  
                	mTopHeight=a.getDimensionPixelSize(attr, 20);
                    break;  
                }  
            }  
            a.recycle();  
            setCompoundDrawablesWithIntrinsicBounds(null, drawableTop, null, null);  
        }  
    }  
  
    // ����Drawable����Ĵ�С  
    @Override  
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {  
        if (top != null) {  
            top.setBounds(0, 0, mTopWith <= 0 ? top.getIntrinsicWidth() : mTopWith, mTopHeight <= 0 ? top.getMinimumHeight() : mTopHeight);  
        }  
          
        setCompoundDrawables(left, top, right, bottom);  
    }  
      
}  