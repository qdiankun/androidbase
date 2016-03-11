package com.example.animatedemo.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.animatedemo.utils.ColorEvaluator;
import com.example.animatedemo.utils.PointEvaluator;


/**
 * Class: MyView
 * Description:
 *
 * @author diankun
 * @date 2015/11/2  16:09
 */
public class CircleView extends View {

    public static final float RADIUS = 100f;

    private Point currentPoint;

    private Paint mPaint;

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        //刷新视图
        invalidate();
    }

    //    public MyView(Context context) {
//        super(context, null);
//    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        //设置抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置画笔颜色
        mPaint.setColor(Color.BLUE);
    }

//    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        //设置抗锯齿
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        //设置画笔颜色
//        mPaint.setColor(Color.BLUE);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            //起始点
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void startAnimation() {
        Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        //监听变化
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                //刷新视图
                invalidate();
            }
        });
        ObjectAnimator animator2 = ObjectAnimator.ofObject(this, "color", new ColorEvaluator(), "#0000FF", "#FF0000");
        //组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(animator2);
        animatorSet.setDuration(5000);
        animatorSet.start();
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

}
