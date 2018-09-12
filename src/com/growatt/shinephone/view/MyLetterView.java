package com.growatt.shinephone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class MyLetterView extends View{

	public static String[] letters={
		"A","B","C","D","E","F",
		"G","H","I","J","K","L","M","N","O","P",
		"Q","R","S","T","U","V","W","X","Y","Z","#"};

	private Paint paint;

	private OnTouchLetterListener listener;

	public void setOnTouchLetterListener(OnTouchLetterListener listener){
		this.listener = listener;
	}

	public MyLetterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}

	private void initPaint() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 11, getResources().getDisplayMetrics()));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//自定义View分配给每个字符串的高度
		int height = getHeight() / letters.length;
		//自定义View分配给每个字符串的宽度
		int width = getWidth();

		for(int i=0;i<letters.length;i++){
			//计算要绘制的字符串所占的空间大小
			Rect bounds = new Rect();
			paint.getTextBounds(letters[i], 0, letters[i].length(), bounds );
			//画字符串的起始横坐标
			float x = width/2 -bounds.width()/2;
			//画字符串的起始纵坐标
			float y = height/2+bounds.height()/2+height*i;
			canvas.drawText(letters[i], x, y, paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();
		//落下，移动，抬起，取消
		switch (action) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			//自定义View获得灰色背景
			setBackgroundColor(Color.LTGRAY);
			//根据手指当前位置，换算出可能当前手指位置的字符
			float yPos = event.getY();
			int idx = (int) ((yPos*letters.length)/getHeight());
			//如果设置了监听器，就调用监听器的回调方法，将字符传入
			if(idx>=0 && idx<letters.length){
				if(listener!=null){
					listener.onTouchLetter(letters[idx]);
				}
			}

			break;

		default:
			setBackgroundColor(Color.TRANSPARENT);
			if(listener!=null){
				listener.onRelaseLetter();
			}
			break;
		}

		return true;
	}

	public interface OnTouchLetterListener{
		void onTouchLetter(String letter);
		void onRelaseLetter();
	}

}
