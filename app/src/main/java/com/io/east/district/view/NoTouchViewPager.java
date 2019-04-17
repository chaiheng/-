package com.io.east.district.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/***
 * @author sirLL
 * @email 1725790539@qq.com
 * create at 2018/5/3 下午3:13
 * description: 使ViewPager不能滑动
 */
public class NoTouchViewPager extends ViewPager {

    public NoTouchViewPager(Context context) {
        super(context);
    }

    public NoTouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }


}
