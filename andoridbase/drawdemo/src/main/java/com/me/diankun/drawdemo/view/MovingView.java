package com.me.diankun.drawdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.me.diankun.drawdemo.R;

/**
 * Created by diankun on 2016/3/15.
 */
public class MovingView  extends View {

    private Bitmap backgroundBitmap;
    private Bitmap handleBitmap;
    private Bitmap showBitmap;
    private int backgroundWidth;
    private int backgroundHeight;
    private int handleWidth;
    private int handleHeight;
    private int currentY;
    private int showHeight;
    private int topSpace;
    private Canvas mCanvas;
    private Rect backgroundSrc;

    public MovingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Movingview);
        //获取背景图片
        Drawable backgroundDrawable = a.getDrawable(R.styleable.Movingview_movingbackground);
        //获取拉手图片
        Drawable handleDrawable = a.getDrawable(R.styleable.Movingview_handlebackground);
        //获取顶端保留的高度大小。这个高度用做滑动到顶部保留的最小高度。
        topSpace = (int) a.getDimension(R.styleable.Movingview_extraspace, 10);
        backgroundBitmap = ((BitmapDrawable) backgroundDrawable).getBitmap();
        handleBitmap = ((BitmapDrawable) handleDrawable).getBitmap();
        //获取图片高宽
        backgroundWidth = backgroundBitmap.getWidth();
        backgroundHeight = backgroundBitmap.getHeight();
        handleWidth = handleBitmap.getWidth();
        handleHeight = handleBitmap.getHeight();

        //根据图片大小创建一个相同大小的bitmap
        showBitmap = Bitmap.createBitmap(backgroundWidth, backgroundHeight + (handleHeight >> 1), Bitmap.Config.RGB_565);
        //创建一个canvas，并绑定bitmap。
        mCanvas = new Canvas(showBitmap);
        //绘制定backgroundBitmap到showBitmap。
        mCanvas.drawBitmap(backgroundBitmap, 0, 0, null);
        //在backgroundBitmap底部的中间位置绘制拉手图片
        mCanvas.drawBitmap(handleBitmap, (backgroundWidth - handleWidth) >> 1, backgroundHeight - (handleHeight >> 1), null);
    }

    public MovingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MovingView(Context context) {
        super(context);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置图片的大小为此View的大小
        setMeasuredDimension(backgroundWidth, backgroundHeight + (handleHeight >> 1));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //更新绘制图片
        canvas.drawBitmap(showBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                currentY = (int) event.getY();
                showHeight = currentY;
                if(showHeight > backgroundHeight)
                    showHeight = backgroundHeight;
                if(showHeight < topSpace)
                    showHeight = topSpace;
                //清除图片
                mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                //根据滑动位置确定新绘制区域
                backgroundSrc = new Rect(0, 0, backgroundWidth, showHeight);
                //绘制背景
                mCanvas.drawBitmap(backgroundBitmap, backgroundSrc, backgroundSrc, null);
                //绘制拉手
                mCanvas.drawBitmap(handleBitmap, (backgroundWidth - handleWidth) >> 1, showHeight - (handleHeight >> 1), null);
                invalidate();//更新
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

}
