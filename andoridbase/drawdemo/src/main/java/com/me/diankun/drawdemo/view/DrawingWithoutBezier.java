package com.me.diankun.drawdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by diankun on 2016/3/15.
 */
public class DrawingWithoutBezier extends View {

    //记录上次位置
    private float mLastX;
    private float mLastY;

    private final Paint mGesturePaint = new Paint();
    private final Path mPath = new Path();

    /**
     * 在代码中使用
     *
     * @param context
     */
    public DrawingWithoutBezier(Context context) {
        this(context, null);
    }

    /**
     * 用于布局文件中使用
     *
     * @param context
     * @param attrs
     */
    public DrawingWithoutBezier(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mGesturePaint.setAntiAlias(true);
        mGesturePaint.setStyle(Paint.Style.STROKE);
        mGesturePaint.setStrokeWidth(5);
        mGesturePaint.setColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        Log.i("TAG", "x = " + x + " \t y = " + y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //mPath.rewind();
                //mPath.reset();
                //mPath绘制的绘制起点
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mLastX);
                float dy = Math.abs(y - mLastY);
                //两点之间的距离大于等于3时，连接连接两点形成直线
                if (dx >= 3 || dy >= 3) {
                    //两点连成直线
                    mPath.lineTo(x, y);
                }
                break;
            case MotionEvent.ACTION_UP:
                mPath.reset();
                break;
        }

        //记录上次位置
        mLastX = x;
        mLastY = y;

        //更新绘制
        invalidate();
        //消费当前事件
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("TAG", "onDraw");
        super.onDraw(canvas);
        canvas.drawPath(mPath, mGesturePaint);
    }
}