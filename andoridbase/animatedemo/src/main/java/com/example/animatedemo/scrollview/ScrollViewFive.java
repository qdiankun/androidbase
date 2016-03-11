package com.example.animatedemo.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.animatedemo.R;


/**
 * Class: ScrollViewFive
 * Description:View跟随手指移动而移动,首先计算得到偏移量，然后使用ScrollTo移动view;松开手指返回
 *
 * @author diankun
 * @date 2015/11/19  16:30
 */
public class ScrollViewFive extends View {

    private int lastX = 0;
    private int lastY = 0;
    private Scroller mScroller;

    public ScrollViewFive(Context context) {
        this(context, null);
    }

    public ScrollViewFive(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollViewFive(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBackground(context);
    }

    private void initBackground(Context context) {
        setBackgroundColor(getResources().getColor(R.color.transparent_red));
        //初始化Scroller
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断Scroller是否执行完毕
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 通过重绘来不断调用computeScroll
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                //移动指定偏移量，注意：ViewGroup移动的是子View，View移动的是内部的内容
                ((ViewGroup) getParent()).scrollBy(-offsetX, -offsetY);
                break;
            case MotionEvent.ACTION_UP:
                // 手指离开时，执行滑动过程
                mScroller.startScroll(getScrollX(), getScrollY(), -getScrollX(), -getScrollY(),5000);
                invalidate();
                break;
        }
        return true;
    }

}
