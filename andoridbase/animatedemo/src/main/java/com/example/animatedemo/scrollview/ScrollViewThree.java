package com.example.animatedemo.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.animatedemo.R;


/**
 * Class: ScrollViewThree
 * Description:View跟随手指移动而移动,首先计算得到偏移量，然后使用layoutparams移动view
 *
 * @author diankun
 * @date 2015/11/19  15:20
 */
public class ScrollViewThree extends View {

    //View按下去是的位置
    private int lastX = 0;
    private int lastY = 0;

    public ScrollViewThree(Context context) {
        this(context, null);
    }

    public ScrollViewThree(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollViewThree(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBackground();
    }

    private void initBackground() {
        setBackgroundColor(getResources().getColor(R.color.transparent_red));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
                params.leftMargin = getLeft() + offsetX;
                params.topMargin = getTop() + offsetY;
                setLayoutParams(params);
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
