package com.example.animatedemo;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * 修改Button宽度的三种方法
 * ① 给对象添加getter和setter方法
 * ② 用一个类来包装原始对象，间接为其体统getter和setter方法
 * ③ 采用ValueAnimator，监听动画过程，自己实现属性的改变
 *
 *第一种方法无法实现，SDK中Button的getter和setter已经规定死了
 *
 * Created by diankun on 2016/3/12.
 */
public class ModifyButtonWidthActivity extends AppCompatActivity {


    private Button btn_change_width;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_button_widht);

        btn_change_width = (Button) findViewById(R.id.btn_change_width);
        btn_change_width.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //methodOne();

                methodTwo();
            }
        });
    }

    private void methodTwo() {

        perforAnimate(btn_change_width, btn_change_width.getWidth(), 500);
    }

    /**
     * e.g 时间过了一半，当前值50，比例0.5；假设Button的起始宽度100px,最终宽度500px
     *      则Button增加的宽度占总增加的宽度的一半， 总增加宽度 500-100 = 400
     *                                             当前时刻增加宽度 400*0.5 = 200
     *                                             Button此刻的宽度 100 + 200 = 300
     *      通过整型估值器内部实现
     * @param target
     * @param start
     * @param end
     */
    private void perforAnimate(final Button target, final int start, final int end) {

        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            //持有一个IntEvaluator对象，方便下面估值使用
            private IntEvaluator intEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取到当前动画的进度，整型，1~100
                int animatedValue = (int) animation.getAnimatedValue();
                Log.i("TAG", "animateValue = " + animatedValue);

                //获取当前进度占整个动画的比例，浮点值，0~1
                float fraction = animation.getAnimatedFraction();
                //直接调用估值器，通过比例计算出宽度，设置给Button
                target.getLayoutParams().width = intEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });
        //在5秒钟内将一个数从1变到100
        valueAnimator.setDuration(5000).start();
    }

    private void methodOne() {
        ViewWraper viewWraper = new ViewWraper(btn_change_width);
        ObjectAnimator.ofInt(viewWraper, "width", 500).setDuration(500).start();
    }

    private static class ViewWraper {

        private View mTarget;

        public ViewWraper(View view) {
            mTarget = view;
        }

        public int getWidth() {
            return mTarget.getWidth();
        }

        public void setWidth(int widht) {
            mTarget.getLayoutParams().width = widht;
            mTarget.requestLayout();
        }

    }
}
