package com.example.animatedemo.dragview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * 方式二：使用Layout来实现滑动---使用绝对坐标实现
 * 参考如下的图解:http://www.processon.com/apps/56d94c86e4b097bfa11f84b3
 * <p/>
 * Created by diankun on 2016/3/4.
 */
public class DragViewTwo extends TextView {

    // 记录上次按下的位置
    private int mLastX = 0;
    private int mLastY = 0;
    //滑动的最小距离
    private int mScaledTouchSlop;


    public DragViewTwo(Context context) {
        super(context, null);
    }

    public DragViewTwo(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public DragViewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        mScaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        Log.i("TAG", "mScaledTouchSloup = " + mScaledTouchSlop);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //上次绝对坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录起始位置
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                //使用layout来实现滑动
                layout(getLeft() + deltaX, getTop() + deltaY, getRight() + deltaX, getBottom() + deltaY);
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }
        //记录上次位置
        mLastX = x;
        mLastY = y;
        return true;
    }
}
