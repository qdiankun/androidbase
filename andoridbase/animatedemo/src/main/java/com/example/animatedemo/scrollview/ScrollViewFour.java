package com.example.animatedemo.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.animatedemo.R;


/**
 * Class: ScrollViewFour
 * Description:View跟随手指移动而移动,首先计算得到偏移量，然后使用ScrollTo移动view
 *
 * @author diankun
 * @date 2015/11/19  15:49
 */
public class ScrollViewFour extends View {

    private int lastX = 0;
    private int lastY = 0;

    public ScrollViewFour(Context context) {
        this(context, null);
    }

    public ScrollViewFour(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollViewFour(Context context, AttributeSet attrs, int defStyleAttr) {
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
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                //移动指定偏移量，注意：ViewGroup移动的是子View，View移动的是内部的内容
                ((ViewGroup)getParent()).scrollBy(-offsetX, -offsetY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
