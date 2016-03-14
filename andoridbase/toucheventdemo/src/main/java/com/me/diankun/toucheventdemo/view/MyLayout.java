package com.me.diankun.toucheventdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2015/11/16.
 */
public class MyLayout extends LinearLayout {
    private static final String TAG = "MyLayout";

    //记录上次滑动的位置
    private int mLastX;
    private int mLastY;
    //滑动的最小距离
    private int mTouchSloup;


    public MyLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
        //初始化滑动的最小距离
        mTouchSloup = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "dispatchTouchEvent " + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //Log.i(TAG, "onInterceptTouchEvent " + ev.getAction());
        //return true;//拦截事件
        //return super.onInterceptTouchEvent(ev);

        boolean intercepted = false;
        //当前位置
        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //此处必须返回false,必须要让子View可以接收到事件
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:

                //计算差值
                int deltaX = Math.abs(x - mLastX);
                int deltaY = Math.abs(y - mLastY);
                //Log.d(TAG, "deltaX =  " + deltaX + "\t deltaY = " + deltaY + "\t mTouchSloup " + mTouchSloup);
                //判断是否滑动
                //if (deltaX > mTouchSloup || deltaY > mTouchSloup) {
                    //当前趋向于水平方向滑动，当前View处理事件，否则子View处理事件
                    Log.d(TAG, "deltaX =  " + deltaX + "\t deltaY = " + deltaY);
                    if (deltaX > deltaY) {
                        intercepted = true;
                    } else {
                        intercepted = false;
                    }
                //}
                break;

            case MotionEvent.ACTION_UP:
                //如果子View有Click事件，返回false让子View处理
                intercepted = false;
                break;
        }

        //记录上次滑动的位置
        mLastX = x;
        mLastY = y;
        Log.i(TAG, "onInterceptTouchEvent " + ev.getAction() + "\t intercepted = " + intercepted);
        return intercepted;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent " + event.getAction());
        return true;
        //return super.onTouchEvent(event);
    }
}
