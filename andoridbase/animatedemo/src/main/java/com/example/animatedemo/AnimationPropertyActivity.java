package com.example.animatedemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.animatedemo.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by diankun on 2016/3/11.
 */
public class AnimationPropertyActivity  extends AppCompatActivity {

    @Bind(R.id.ll_popup)
    LinearLayout mPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_property);
        ButterKnife.bind(this);
    }

    /**
     * 测试ValueAnimator使用
     *
     * @param view
     */
    @OnClick(R.id.btn_value_animator)
    void changeValue(View view) {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) valueAnimator.getAnimatedValue();
                Log.i("TAG", "currentValue : " + currentValue);
            }
        });
        valueAnimator.start();
    }

    /**
     * 将透明度从 显示变成透明，再从透明变成现实
     *
     * @param view
     */
    @OnClick(R.id.btn_property_alpha)
    void changeAlpha(View view) {
        //第一个参数要动画的view,第二个参数属性名，后几个参数是值的变化
        ObjectAnimator animator = ObjectAnimator.ofFloat(mPopup, "alpha", 1f, 0f, 1f);
        animator.setDuration(3000);
        animator.start();
    }

    /**
     * 旋转360度
     *
     * @param view
     */
    @OnClick(R.id.btn_property_rotation)
    void changeRotation(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mPopup, "rotation", 0f, 360f);
        animator.setDuration(3000);
        animator.start();
    }

    /**
     * 从当前位置左移width的宽度，再返回之前的位置
     *
     * @param view
     */
    @OnClick(R.id.btn_property_translate)
    void changeTranslate(View view) {
        float currentX = mPopup.getTranslationX();
        float width = mPopup.getWidth();
        ObjectAnimator animator = ObjectAnimator.ofFloat(mPopup, "translationX", currentX, -width, currentX);
        animator.setDuration(3000);
        animator.start();
    }

    /**
     * 在X轴方向上，先放大3倍，然后在回到之前的大小
     *
     * @param view
     */
    @OnClick(R.id.btn_property_scale)
    void changeScale(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mPopup, "scaleX", 1f, 3f, 1f);
        animator.setDuration(3000);
        animator.start();
    }

    /**
     * 先移进屏幕，然后旋转360度，同时淡入淡出
     *
     * @param view
     */
    @OnClick(R.id.btn_property_set)
    void changeSet(View view) {
        float translationX = mPopup.getTranslationX();
        float width = mPopup.getWidth();
        ObjectAnimator translate = ObjectAnimator.ofFloat(mPopup, "translationX", -width, translationX);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mPopup, "rotation", 0f, 360f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mPopup, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(alpha).with(rotation).after(translate);
        animSet.setDuration(5000);
        //监听开始和结束
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ToastUtils.showShort("动画结束");
            }

            @Override
            public void onAnimationStart(Animator animation) {
                ToastUtils.showShort("动画开始");
            }
        });
        animSet.start();
    }


    /**
     * 使用xml实现组合动画
     *
     * @param view
     */
    @OnClick(R.id.btn_property_xml)
    void changeSetUseXml(View view) {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.property_animator_set);
        animator.setTarget(mPopup);
        animator.start();
    }

}