package com.growatt.shinephone.video;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.growatt.shinephone.R;
/**
 * http://blog.csdn.net/lmj623565791/article/details/42160391
 * @author zhy
 *
 */
public class ViewPagerIndicator extends LinearLayout  
{  
    /** 
     * 绘制三角形的画笔 
     */  
    private Paint mPaint;  
    /** 
     * path构成一个三角形 
     */  
    private Path mPath;  
    /** 
     * 三角形的宽度 
     */  
    private int mTriangleWidth;  
    /** 
     * 三角形的高度 
     */  
    private int mTriangleHeight;  
      
    /** 
     * 三角形的宽度为单个Tab的1/6 
     */  
//    private static final float RADIO_TRIANGEL = 1.0f / 6;  
    private static final float RADIO_TRIANGEL = 1.0f / 2;  
    /** 
     * 三角形的最大宽度 
     */  
    private final int DIMENSION_TRIANGEL_WIDTH = (int) (getScreenWidth() / 3 * RADIO_TRIANGEL);  
      
      
    /** 
     * 初始时，三角形指示器的偏移量 
     */  
    private int mInitTranslationX;  
    /** 
     * 手指滑动时的偏移量 
     */  
    private float mTranslationX;  
  
    /** 
     * 默认的Tab数量 
     */  
    private static final int COUNT_DEFAULT_TAB = 1;  
    /** 
     * tab数量 
     */  
    private int mTabVisibleCount = COUNT_DEFAULT_TAB;  
  
    /** 
     * tab上的内容 
     */  
    private List<String> mTabTitles;  
    /** 
     * 与之绑定的ViewPager 
     */  
    public ViewPager mViewPager;  
      
    /** 
     * 标题正常时的颜色 
     */  
    private static final int COLOR_TEXT_NORMAL = 0xff000000;  
    /** 
     * 标题选中时的颜色 
     */  
    private static final int COLOR_TEXT_HIGHLIGHTCOLOR = 0xFF11a7F3;  
  
    public ViewPagerIndicator(Context context)  
    {  
        this(context, null);  
    }  
  
    public ViewPagerIndicator(Context context, AttributeSet attrs)  
    {  
        super(context, attrs);  
  
        // 获得自定义属性，tab的数量  
        TypedArray a = context.obtainStyledAttributes(attrs,  
                R.styleable.ViewPagerIndicator);  
        mTabVisibleCount = a.getInt(R.styleable.ViewPagerIndicator_x_item_count,  
                COUNT_DEFAULT_TAB);  
        if (mTabVisibleCount < 0)  
            mTabVisibleCount = COUNT_DEFAULT_TAB;  
        a.recycle();  
  
        // 初始化画笔  
        mPaint = new Paint();  
        mPaint.setAntiAlias(true);  
        mPaint.setColor(Color.parseColor("#FF11a7F3"));  
        mPaint.setStyle(Style.FILL);  
        mPaint.setPathEffect(new CornerPathEffect(3));  
  
    }  

	/**
	 * 缁樺埗鎸囩ず鍣�
	 */
	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		canvas.save();
		// 鐢荤瑪骞崇Щ鍒版纭殑浣嶇疆
		canvas.translate(mInitTranslationX + mTranslationX, getHeight() + 1);
		canvas.drawPath(mPath, mPaint);
		canvas.restore();

		super.dispatchDraw(canvas);
	}

	/**
	 * 鍒濆鍖栦笁瑙掑舰鐨勫搴�
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		mTriangleWidth = (int) (w / mTabVisibleCount * RADIO_TRIANGEL);// 1/6 of
																		// width
		mTriangleWidth = Math.min(DIMENSION_TRIANGEL_WIDTH, mTriangleWidth);

		// 鍒濆鍖栦笁瑙掑舰
		initTriangle();

		// 鍒濆鏃剁殑鍋忕Щ閲�
		mInitTranslationX = getWidth() / mTabVisibleCount / 2 - mTriangleWidth
				/ 2;
	}

	/**
	 * 璁剧疆鍙鐨則ab鐨勬暟閲�
	 * 
	 * @param count
	 */
	public void setVisibleTabCount(int count)
	{
		this.mTabVisibleCount = count;
	}

	/**
	 * 璁剧疆tab鐨勬爣棰樺唴瀹� 鍙�夛紝鍙互鑷繁鍦ㄥ竷灞�鏂囦欢涓啓姝�
	 * 
	 * @param datas
	 */
	public void setTabItemTitles(List<String> datas)
	{
		// 濡傛灉浼犲叆鐨刲ist鏈夊�硷紝鍒欑Щ闄ゅ竷灞�鏂囦欢涓缃殑view
		if (datas != null && datas.size() > 0)
		{
			this.removeAllViews();
			this.mTabTitles = datas;
            this.mTabVisibleCount=datas.size();
			for (String title : mTabTitles)
			{
				// 娣诲姞view
				addView(generateTextView(title));
			}
			// 璁剧疆item鐨刢lick浜嬩欢
			setItemClickEvent();
		}

	}

	/**
	 * 瀵瑰鐨刅iewPager鐨勫洖璋冩帴鍙�
	 * 
	 * @author zhy
	 * 
	 */
	public interface PageChangeListener
	{
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels);

		public void onPageSelected(int position);

		public void onPageScrollStateChanged(int state);
	}

	// 瀵瑰鐨刅iewPager鐨勫洖璋冩帴鍙�
	private PageChangeListener onPageChangeListener;

	// 瀵瑰鐨刅iewPager鐨勫洖璋冩帴鍙ｇ殑璁剧疆
	public void setOnPageChangeListener(PageChangeListener pageChangeListener)
	{
		this.onPageChangeListener = pageChangeListener;
	}

	// 璁剧疆鍏宠仈鐨刅iewPager
	public void setViewPager(ViewPager mViewPager, int pos)
	{
		this.mViewPager = mViewPager;

		mViewPager.setOnPageChangeListener(new OnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
				// 璁剧疆瀛椾綋棰滆壊楂樹寒
				resetTextViewColor();
				highLightTextView(position);

				// 鍥炶皟
				if (onPageChangeListener != null)
				{
					onPageChangeListener.onPageSelected(position);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels)
			{
				// 婊氬姩
				scroll(position, positionOffset);

				// 鍥炶皟
				if (onPageChangeListener != null)
				{
					onPageChangeListener.onPageScrolled(position,
							positionOffset, positionOffsetPixels);
				}

			}

			@Override
			public void onPageScrollStateChanged(int state)
			{
				// 鍥炶皟
				if (onPageChangeListener != null)
				{
					onPageChangeListener.onPageScrollStateChanged(state);
				}

			}
		});
		// 璁剧疆褰撳墠椤�
		mViewPager.setCurrentItem(pos);
		// 楂樹寒
		highLightTextView(pos);
	}

	/**
	 * 楂樹寒鏂囨湰
	 * 
	 * @param position
	 */
	protected void highLightTextView(int position)
	{
		View view = getChildAt(position);
		if (view instanceof TextView)
		{
			((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHTCOLOR);
		}

	}

	/**
	 * 閲嶇疆鏂囨湰棰滆壊
	 */
	private void resetTextViewColor()
	{
		for (int i = 0; i < getChildCount(); i++)
		{
			View view = getChildAt(i);
			if (view instanceof TextView)
			{
				((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
			}
		}
	}

	/**
	 * 璁剧疆鐐瑰嚮浜嬩欢
	 */
	public void setItemClickEvent()
	{
		int cCount = getChildCount();
		for (int i = 0; i < cCount; i++)
		{
			final int j = i;
			View view = getChildAt(i);
			view.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					mViewPager.setCurrentItem(j);
				}
			});
		}
	}

	/**
	 * 鏍规嵁鏍囬鐢熸垚鎴戜滑鐨凾extView
	 * 
	 * @param text
	 * @return
	 */
	private TextView generateTextView(String text)
	{
		TextView tv = new TextView(getContext());
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.width = getScreenWidth() / mTabVisibleCount;
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(COLOR_TEXT_NORMAL);
		tv.setText(text);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		tv.setLayoutParams(lp);
		return tv;
	}

	/**
	 * 鍒濆鍖栦笁瑙掑舰鎸囩ず鍣�
	 */
	private void initTriangle()
	{
		mPath = new Path();

		mTriangleHeight = (int) (mTriangleWidth / 2 / Math.sqrt(2));
		mPath.moveTo(0, 0);
		mPath.lineTo(mTriangleWidth, 0);
		mPath.lineTo(mTriangleWidth, -3);
//		mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
		mPath.lineTo(0,-3);
//		mPath.lineTo(0,0);
		
		mPath.close();
	}

	/**
	 * 鎸囩ず鍣ㄨ窡闅忔墜鎸囨粴鍔紝浠ュ強瀹瑰櫒婊氬姩
	 * 
	 * @param position
	 * @param offset
	 */
	public void scroll(int position, float offset)
	{
		/**
		 * <pre>
		 *  0-1:position=0 ;1-0:postion=0;
		 * </pre>
		 */
		// 涓嶆柇鏀瑰彉鍋忕Щ閲忥紝invalidate
		mTranslationX = getWidth() / mTabVisibleCount * (position + offset);

		int tabWidth = getScreenWidth() / mTabVisibleCount;

		// 瀹瑰櫒婊氬姩锛屽綋绉诲姩鍒板�掓暟鏈�鍚庝竴涓殑鏃跺�欙紝寮�濮嬫粴鍔�
		if (offset > 0 && position >= (mTabVisibleCount - 2)
				&& getChildCount() > mTabVisibleCount)
		{
			if (mTabVisibleCount != 1)
			{
				this.scrollTo((position - (mTabVisibleCount - 2)) * tabWidth
						+ (int) (tabWidth * offset), 0);
			} else
			// 涓篶ount涓�1鏃� 鐨勭壒娈婂鐞�
			{
				this.scrollTo(
						position * tabWidth + (int) (tabWidth * offset), 0);
			}
		}

		invalidate();
	}

	/**
	 * 璁剧疆甯冨眬涓璿iew鐨勪竴浜涘繀瑕佸睘鎬э紱濡傛灉璁剧疆浜唖etTabTitles锛屽竷灞�涓璿iew鍒欐棤鏁�
	 */
	@Override
	protected void onFinishInflate()
	{
		Log.e("TAG", "onFinishInflate");
		super.onFinishInflate();

		int cCount = getChildCount();

		if (cCount == 0)
			return;

		for (int i = 0; i < cCount; i++)
		{
			View view = getChildAt(i);
			LinearLayout.LayoutParams lp = (LayoutParams) view
					.getLayoutParams();
			lp.weight = 0;
			lp.width = getScreenWidth() / mTabVisibleCount;
			view.setLayoutParams(lp);
		}
		// 璁剧疆鐐瑰嚮浜嬩欢
		setItemClickEvent();

	}

	/**
	 * 鑾峰緱灞忓箷鐨勫搴�
	 * 
	 * @return
	 */
	public int getScreenWidth()
	{
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

}
