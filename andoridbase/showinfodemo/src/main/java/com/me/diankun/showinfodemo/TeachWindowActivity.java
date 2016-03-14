package com.me.diankun.showinfodemo;

import android.annotation.TargetApi;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.me.diankun.showinfodemo.utils.ScreenSizeUtil;

/**
 * Created by diankun on 2016/3/13.
 * <p>
 * 出处凯子哥Blog : http://blog.csdn.net/zhaokaiqiang1992/article/details/40423785
 */
public class TeachWindowActivity extends AppCompatActivity {


    private WindowManager mWindowManager;
    private ImageView mImageView;
    private WindowManager.LayoutParams mLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_window);

        mWindowManager = getWindowManager();

        showTeachWindow();
    }

    private void showTeachWindow() {
        //初始化ImageView
        mImageView = new ImageView(this);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setImageResource(R.drawable.guide);

        // 设置LayoutParams参数
        mLayoutParams = new WindowManager.LayoutParams();
        // 设置显示的类型，TYPE_PHONE指的是来电话的时候会被覆盖，其他时候会在最前端，显示位置在stateBar下面，其他更多的值请查阅文档
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        // 设置显示格式
        mLayoutParams.format = PixelFormat.RGBA_8888;
        // 设置对齐方式
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        // 设置宽高
        mLayoutParams.width = ScreenSizeUtil.getScreenWidth(this);
        mLayoutParams.height = ScreenSizeUtil.getScreenHeight(this);
        // 添加到当前的窗口上
        mWindowManager.addView(mImageView, mLayoutParams);

        // 点击图层之后，将图层移除
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowManager.removeView(mImageView);
            }
        });

    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mImageView.isAttachedToWindow() && mImageView != null) {
                mWindowManager.removeView(mImageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
