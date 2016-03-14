package com.me.diankun.toucheventdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2015/11/16.
 */
public class MyView extends View {
    private static final String TAG = "MyView";

    public MyView(Context context, AttributeSet attrs) {
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
        //Log.i(TAG, "onTouchEvent " + event.getAction()+"\t super.onTouchEvent(event) = "+super.onTouchEvent(event));
        //return super.onTouchEvent(event);
        return true;
    }

}
