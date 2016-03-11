package com.example.animatedemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * Created by diankun on 2016/3/4.
 */
public class CustomTextView extends TextView {


    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    //滑动的最小距离
    private int mScaledTouchSlop;

    private static final String TAG = CustomTextView.class.getSimpleName();


    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Log.i("TAG", "mScaledTouchSloup = " + mScaledTouchSlop);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.i(TAG, "x = " + x + " \t y = " + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        //记录上次位置
//        mLastX = x;
//        mLastY = y;
        return true;
    }
}
