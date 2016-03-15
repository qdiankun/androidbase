package com.me.diankun.drawdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by diankun on 2016/3/15.
 */
public class MyPathView   extends View {
    private int width;
    private int height;
    private Paint mPaintThreeAgl;
    private Path paththree;
    private Paint mPaintBeisr;
    private Path mPathBeisr;
    private Paint mPaintText;
    private Path path;
    private int count;


    private final int MSG_WHAT_QUXIAN=0x44;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_WHAT_QUXIAN:
                    count++;
                    if(count>=200){//由于周期为200，所以每200变0
                        count=0;
                    }
                    invalidate();
                    handler.sendEmptyMessageDelayed(MSG_WHAT_QUXIAN,50);
                    break;
                default:
                    break;
            }
        }
    };

    public MyPathView(Context context) {
        super(context);
    }

    public MyPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaintThreeAgl = new Paint();
        mPaintThreeAgl.setStyle(Paint.Style.STROKE);//设置为空心
        paththree = new Path();
        mPaintText = new Paint();
        mPaintText.setTextSize(20);
//贝塞尔
        mPaintBeisr=new Paint();
        mPaintBeisr.setStyle(Paint.Style.STROKE);
        mPathBeisr = new Path();

        //曲线
        path = new Path();
        handler.sendEmptyMessage(MSG_WHAT_QUXIAN);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*
        曲线，需要Hander
         */
        path.reset();//每次需要重置路径
        path.moveTo(count, 100);//移动初始位置，造成水流移动的效果
        for (int i=0;i<100;i++) {
            path.rQuadTo(50, 50, 100, 0);//以当前位置为原点进行绘制
            path.rQuadTo(50, -50, 100, 0);
        }
        canvas.drawPath(path,mPaintBeisr);
    }
}