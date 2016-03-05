package com.me.diankun.commonview.dragview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * 方式一：使用Layout来实现滑动---使用相对坐标
 * <p/>
 * getX() 与 getRawX() 区别
 * <p/>
 * getX()是表示Widget相对于自身左上角的x坐标,
 * getRawX()是表示相对于屏幕左上角的x坐标值
 * (注意:这个屏幕左上角是手机屏幕左上角,不管activity是否有titleBar或是否全屏幕),getY(),getRawY()一样的道理
 * <p/>
 * Created by diankun on 2016/3/4.
 */
public class DragViewOne extends TextView {

    // 记录首次按下的位置
    private int mStartX = 0;
    private int mStartY = 0;
    //滑动的最小距离
    private int mScaledTouchSlop;


    public DragViewOne(Context context) {
        this(context, null);
    }

    public DragViewOne(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragViewOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Log.i("TAG", "mScaledTouchSloup = " + mScaledTouchSlop);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //Widget相对于自身左上角的x坐标,
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.i("TAG", "x = " + x + "\t y = " + y);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                //记录起始位置
                mStartX = x;
                mStartY = y;
                Log.d("TAG", "mStartX = " + mStartX + "\t mStartY = " + mStartY);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mStartX;
                int deltaY = y - mStartY;
                //Log.d("TAG", "deltaX = " + deltaX + "\t deltaY = " + deltaY);
                //使用layout来实现滑动
                layout(getLeft() + deltaX, getTop() + deltaY, getRight() + deltaX, getBottom() + deltaY);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        //消费事件
        return true;
    }
}
