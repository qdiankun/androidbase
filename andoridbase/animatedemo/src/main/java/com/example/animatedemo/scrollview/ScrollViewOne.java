package com.example.animatedemo.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.animatedemo.R;


/**
 * Class: ScrollViewOne
 * Description: View跟随手指移动而移动，使用getX()，getY()计算偏移量;进行偏移的使用可以使用layout(l,r,t,b)或是offsetLeftAndRight(offsetX)，offsetTopAndBottom(offsetY)
 *
 * @author diankun
 * @date 2015/11/19  14:33
 */
public class ScrollViewOne extends View {

    //View按下去是的位置
    private int lastX = 0;
    private int lastY = 0;

    public ScrollViewOne(Context context) {
        this(context, null);
    }

    public ScrollViewOne(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollViewOne(Context context, AttributeSet attrs, int defStyleAttr) {
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
                //计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                //在当前的left,top,right,bottom的基础上加上偏移量
                layout(getLeft() + offsetX, getTop() + offsetY, getRight() + offsetX, getBottom() + offsetY);
                //或者直接使用如下方法
                //offsetLeftAndRight(offsetX);
                //offsetTopAndBottom(offsetY);
                break;
            case MotionEvent.ACTION_UP:

                break;

        }
        return true;
    }


}
