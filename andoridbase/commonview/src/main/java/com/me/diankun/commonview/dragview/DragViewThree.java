package com.me.diankun.commonview.dragview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.TextView;

/**
 * 方式三：使用offsetLeftAndRight()和offsetTopAndBottom()方法来实现
 * <p/>
 * 其实这两个方法分别是对左右移动和上下移动的封装，传入的就是偏移量。此时将DragView中的onTouchEvent代码简单替换即可
 * <p/>
 * Created by diankun on 2016/3/4.
 */
public class DragViewThree extends TextView {

    // 记录上次按下的位置
    private int mLastX = 0;
    private int mLastY = 0;
    //滑动的最小距离
    private int mScaledTouchSlop;


    public DragViewThree(Context context) {
        super(context, null);
    }

    public DragViewThree(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public DragViewThree(Context context, AttributeSet attrs, int defStyleAttr) {
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
                offsetLeftAndRight(deltaX);
                offsetTopAndBottom(deltaY);
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
