package com.me.diankun.drawdemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.me.diankun.drawdemo.utils.DensityUtils;
import com.me.diankun.drawdemo.utils.ScreenSizeUtil;

/**
 * Created by diankun on 2016/3/15.
 */
public class ScrawView extends View {


    private Paint mPaint;
    private Path mPath;

    //记录上次位置
    private float mLastX;
    private float mLastY;

    private Bitmap mCacheBitmap;
    private Canvas mCacheCanvas;

    private static final String TAG = ScrawView.class.getSimpleName();

    public ScrawView(Context context) {
        this(context, null);
    }

    public ScrawView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();

        initCache();
    }


    public Bitmap getCacheBitmap() {
        return mCacheBitmap;
    }

    private void initCache() {
        int screenWidth = ScreenSizeUtil.getScreenWidth(getContext());
        int height = DensityUtils.dp2px(getContext(), 200);
        mCacheBitmap = Bitmap.createBitmap(screenWidth, height, Bitmap.Config.ARGB_8888);
        mCacheCanvas = new Canvas(mCacheBitmap);
        mCacheCanvas.drawColor(Color.WHITE);
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(TAG, "onSizeChanged");
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        Log.i(TAG, "x = " + x + "\t y = " + y);
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
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
                mCacheCanvas.drawPath(mPath, mPaint);
                mPath.reset();
                break;
        }

        mLastX = x;
        mLastY = y;

        //重新绘制
        invalidate();
        //消费事件
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Log.i(TAG, "onDraw");

        super.onDraw(canvas);
        //通过画布绘制多点形成的图形
        //canvas.drawPath(mPath, mPaint);

        canvas.drawBitmap(mCacheBitmap, 0, 0, null);
        canvas.drawPath(mPath, mPaint);
    }
}
