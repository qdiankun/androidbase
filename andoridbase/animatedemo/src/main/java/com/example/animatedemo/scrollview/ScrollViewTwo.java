package com.example.animatedemo.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.animatedemo.R;


/**
 * Class: ScrollViewTwo
 * Description: View跟随手指移动而移动,使用getRawX(),getRawY()计算移动的偏移量，需要每次都记录上次的位置
 *
 * @author diankun
 * @date 2015/11/19  15:11
 */
public class ScrollViewTwo extends View {

    //记录上次的位置
    private int lastX = 0;
    private int lastY = 0;


    public ScrollViewTwo(Context context) {
        this(context, null);
    }

    public ScrollViewTwo(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollViewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBackground();
    }

    private void initBackground() {
        setBackgroundColor(getResources().getColor(R.color.transparent_red));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                //计算偏移量
                int offsetX = rawX - lastX;
                int offsetY = rawY - lastY;
                //移动指定的偏移量
                offsetLeftAndRight(offsetX);
                offsetTopAndBottom(offsetY);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        //记录上次移动的位置
        lastX = rawX;
        lastY = rawY;
        return true;
    }
}
