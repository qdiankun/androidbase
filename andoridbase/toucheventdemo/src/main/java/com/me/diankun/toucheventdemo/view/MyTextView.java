package com.me.diankun.toucheventdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/11/16.
 */
public class MyTextView extends TextView {

    private static final String TAG = "MyTextView";

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override

    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "dispatchTouchEvent " + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent " + event.getAction());
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                return false;
//            case MotionEvent.ACTION_UP:
//                return false;
//        }
        return super.onTouchEvent(event);
    }

}
