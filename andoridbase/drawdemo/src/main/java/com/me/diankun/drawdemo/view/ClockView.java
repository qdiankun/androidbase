package com.me.diankun.drawdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Class: ClockView
 * Description: 徐医生的Chapter6中自定义Clock
 *
 * @author diankun
 * @date 2015/11/24  16:24
 */
public class ClockView extends View {

    private Paint mPaint;

    private float density = getResources().getDisplayMetrics().density;

    private static final String TAG = "ClockView";

    private int ring = (int) (2 * density);

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置空心模式和画笔宽度为4
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(ring);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Log.i(TAG, "density :" + density);

        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        float centerX = canvasWidth / 2;
        float centerY = canvasHeight / 2;
        float radius = canvasWidth / 2 - ring / 2;
        //绘制外面大圆
        canvas.drawCircle(centerX, centerY, radius, mPaint);
        //绘制刻度
        canvas.drawLine(centerX, centerY - radius, centerX, centerY - radius + 45, mPaint);
        for (int i = 0; i < 24; i++) {
            //注意:已经画的刻度线是不会跟着移动的
            //如果是0,9,12,18时，刻度线加粗
            if (i == 0 || i == 6 || i == 9 || i == 12 || i == 18) {
                //绘制刻度线
                mPaint.setStrokeWidth(ring);
                canvas.drawLine(centerX, centerY - radius, centerX, centerY - radius + 60, mPaint);
                //绘制刻度对应的文字
                String value = String.valueOf(i);
                mPaint.setStrokeWidth(density);
                mPaint.setTextSize(30);
                canvas.drawText(value, centerX - 5, centerY - radius + 90, mPaint);
            } else {
                //绘制刻度线
                mPaint.setStrokeWidth(ring);
                canvas.drawLine(centerX, centerY - radius, centerX, centerY - radius + 30, mPaint);
                //绘制刻度对应的文字
                String value = String.valueOf(i);
                mPaint.setStrokeWidth(density);
                mPaint.setTextSize(30);
                canvas.drawText(value, centerX - 5, centerY - radius + 60, mPaint);
            }
            //旋转15度
            canvas.rotate(15, centerX, centerY);
        }
        //绘制指针
        canvas.save();// 在save()与restore()方法中间进行的移动，旋转等操作对下次绘画不起作用，下次绘画使用的坐标是save()保存前的坐标
        mPaint.setStrokeWidth(10 * density);
        canvas.translate(centerX, centerY);
        canvas.drawPoint(0, 0, mPaint);//在坐标原点绘制点，然后移动点到圆的中心
        canvas.drawLine(0, 0, 100, 100, mPaint);//绘制时针
        canvas.drawLine(0, 0, 100, 200, mPaint);//绘制分针
        canvas.restore();
    }
}
