package com.example.animatedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationTweenActivity extends AppCompatActivity {

    @Bind(R.id.ll_popup)
    LinearLayout mPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_tween);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_tween_alpha_hide)
    void tweenAlpahHide(View view) {
        //使用Xml中动画
        //mPopup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tween_alpha_hide));

        //使用代码实现动画
        Animation alpahHideAnimation = new AlphaAnimation(1.0f, 0f);
        alpahHideAnimation.setDuration(2000);
        mPopup.startAnimation(alpahHideAnimation);
    }

    @OnClick(R.id.btn_tween_alpha_show)
    void tweenAlphaShow(View view) {
        //使用Xml中动画
        //mPopup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tween_alpha_show));

        //使用代码实现动画
        Animation alpahHideAnimation = new AlphaAnimation(0f, 1.0f);
        alpahHideAnimation.setDuration(2000);
        mPopup.startAnimation(alpahHideAnimation);
    }

    @OnClick(R.id.btn_tween_scale)
    void tweenScal(View view) {
        //使用Xml中动画
//        mPopup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tween_scale));

        /**
         * ScaleAnimation(float fromX, float toX, float fromY, float toY,int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
         //第一个参数fromX为动画起始时 X坐标上的伸缩尺寸
         //第二个参数toX为动画结束时 X坐标上的伸缩尺寸
         //第三个参数fromY为动画起始时Y坐标上的伸缩尺寸
         //第四个参数toY为动画结束时Y坐标上的伸缩尺寸
         说明:
         以上四种属性值
         0.0表示收缩到没有
         1.0表示正常无伸缩
         值小于1.0表示收缩
         值大于1.0表示放大

         //第五个参数pivotXType为动画在X轴相对于物件位置类型
         //第六个参数pivotXValue为动画相对于物件的X坐标的开始位置
         //第七个参数pivotXType为动画在Y轴相对于物件位置类型
         //第八个参数pivotYValue为动画相对于物件的Y坐标的开始位置
         */

        //使用代码实现动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1.4f, 0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        mPopup.startAnimation(scaleAnimation);
    }

    @OnClick(R.id.btn_tween_translate_to_right)
    void tweenTranslateToRight(View view) {
        mPopup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tween_translate_to_right));

        /**
         *TranslateAnimation(float fromXDelta, float toXDelta,float fromYDelta, float toYDelta)
         //第一个参数fromXDelta为动画起始时 X坐标上的移动位置
         //第二个参数toXDelta为动画结束时 X坐标上的移动位置
         //第三个参数fromYDelta为动画起始时Y坐标上的移动位置
         //第四个参数toYDelta为动画结束时Y坐标上的移动位置
         */

        //使用代码实现动画
//        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 100f, 0f, 0f);
//        translateAnimation.setDuration(2000);
//        mPopup.startAnimation(translateAnimation);
    }

    @OnClick(R.id.btn_tween_translate_to_left)
    void tweenTranslateToLeft(View view) {
        //mPopup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tween_translate_to_left));

        //使用代码实现
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, -100f, 0f, 0f);
        translateAnimation.setDuration(2000);
        mPopup.startAnimation(translateAnimation);
    }

    @OnClick(R.id.btn_tween_translate_to_top)
    void tweenTranslateToTop(View view) {
        //mPopup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tween_translate_to_top));

        //使用代码实现
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0f, -100f);
        translateAnimation.setDuration(2000);
        mPopup.startAnimation(translateAnimation);
    }

    @OnClick(R.id.btn_tween_translate_to_bottom)
    void tweenTranslateToBottom(View view) {
//        mPopup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tween_translate_to_bottom));

        //使用代码实现
        TranslateAnimation translateAnimation = new TranslateAnimation(0f, 0f, 0f, 100f);
        translateAnimation.setDuration(2000);
        mPopup.startAnimation(translateAnimation);
    }

    @OnClick(R.id.btn_tween_rotate)
    void tweenRotate(View view) {
//        mPopup.startAnimation(AnimationUtils.loadAnimation(this, R.anim.tween_rotate));

        /**
         * RotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue)
         //第一个参数fromDegrees为动画起始时的旋转角度
         //第二个参数toDegrees为动画旋转到的角度
         //第三个参数pivotXType为动画在X轴相对于物件位置类型
         //第四个参数pivotXValue为动画相对于物件的X坐标的开始位置
         //第五个参数pivotXType为动画在Y轴相对于物件位置类型
         //第六个参数pivotYValue为动画相对于物件的Y坐标的开始位置
         */

        //使用代码实现
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 350f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        mPopup.startAnimation(rotateAnimation);

    }


}
