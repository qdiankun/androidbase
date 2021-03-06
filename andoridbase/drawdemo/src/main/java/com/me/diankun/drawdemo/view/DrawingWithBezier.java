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
 * Created by diankun on 2016/3/14.
 */
public class DrawingWithBezier extends View {

    //记录上次位置
    private float mLastX;
    private float mLastY;

    private final Paint mGesturePaint = new Paint();
    private final Path mPath = new Path();


    public DrawingWithBezier(Context context) {
        this(context, null);
    }

    public DrawingWithBezier(Context context, AttributeSet attrs) {
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
                    //设置贝塞尔曲线的操作点为起点和终点的一半
                    float cX = (x + mLastX) / 2;
                    float cY = (y + mLastY) / 2;

                    //二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，cX, cY为终点
                    mPath.quadTo(mLastX, mLastY, cX, cY);
                }
                break;
            case MotionEvent.ACTION_UP:
                //mPath.reset();
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
        super.onDraw(canvas);
        //通过画布绘制多点形成的图形
        canvas.drawPath(mPath, mGesturePaint);
    }


}
