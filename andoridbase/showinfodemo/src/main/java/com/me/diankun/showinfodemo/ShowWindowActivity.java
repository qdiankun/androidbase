package com.me.diankun.showinfodemo;

import android.app.Dialog;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by diankun on 2016/3/13.
 */
public class ShowWindowActivity extends AppCompatActivity implements View.OnTouchListener {


    private Button mFloatButton;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    private Button btn_test;
    private Button btn_dialog_window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_window);

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);


        btn_test = (Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindoButton();
            }
        });

        btn_dialog_window = (Button) findViewById(R.id.btn_dialog_window);
        btn_dialog_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogWindow();
            }
        });

    }

    private void showDialogWindow() {
        Dialog dialog = new Dialog(this.getApplicationContext());
        TextView textView = new TextView(this);
        textView.setText("this is toast!");
        dialog.setContentView(textView);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        dialog.show();
    }

    private void showWindoButton() {

        if (mFloatButton != null) return;

        mFloatButton = new Button(this);
        mFloatButton.setText("click me");
        mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
                PixelFormat.TRANSPARENT);
        /**
         * FLAG_NOT_TOUCHABLE 不需要获取到焦点
         * FLAG_NOT_TOUCHABLE 当前window区域以外的单击事件传递给底层Window,区域以内自己处理
         * FLAG_SHOW_WHEN_LOCKED 可以让window显示在锁屏界面上
         */
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        //设置Button跟随手指的滑动而滑动
        mFloatButton.setOnTouchListener(this);
//        mFloatButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ShowWindowActivity.this, "This is FloatButton", Toast.LENGTH_SHORT).show();
//            }
//        });
        mWindowManager.addView(mFloatButton, mLayoutParams);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFloatButton != null) {
            mWindowManager.removeView(mFloatButton);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        Log.i("TAG", "rawX = " + rawX + "rawY = " + rawY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE:
                mLayoutParams.x = rawX;
                mLayoutParams.y = rawY;
                mWindowManager.updateViewLayout(mFloatButton, mLayoutParams);
                break;
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }

        return false;
    }
}
