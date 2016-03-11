package com.me.diankun.drawdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

/**
 * url:http://www.cnblogs.com/tony-yang-flutter/p/3581874.html
 * Canvas.saveLayerAlpha(float left, float top, float right, float bottom, int alpha, int saveFlags)
 * 使用Canvas绘制图层，注意：
 * 本身和save方法差不多,单独分配了一个画布用于绘制图层，定义了一个画布区域(可以设置透明度),此方法的所有绘制都在此区域绘制
 * 直到调用了canvas.resotre()方法为止，
 * <p/>
 * 如下例子：在调用saveLayerAlpha方法之前绘制了一个“圆形”;然后调用saveLayerAlpha方法之后绘制了一个“圆形”此时这两个圆形并不在同一个图层
 */
public class DrawLayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LayerView(this));
    }


    private class LayerView extends View {

        private Paint mPaint;

        private static final int LAYER_FLAGS = Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG;

        public LayerView(Context context) {
            this(context, null);
        }

        public LayerView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);

        }

        public LayerView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initPaint();
        }

        private void initPaint() {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            //清屏操作
            canvas.drawColor(Color.WHITE);
            //绘制蓝色的圆
            mPaint.setColor(Color.BLUE);
            canvas.drawCircle(150, 150, 100, mPaint);

            //绘制下一个图层
            mPaint.setColor(Color.GREEN);
            canvas.saveLayerAlpha(150, 150, 350, 350, 127, LAYER_FLAGS);
            canvas.drawCircle(250, 250, 100, mPaint);
            canvas.restore();
        }
    }
}
