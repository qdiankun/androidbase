package com.me.diankun.toucheventdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.me.diankun.toucheventdemo.view.MyLayout;

/**
 * Created by diankun on 2016/3/12.
 */
public class DealConfictActivity extends AppCompatActivity {

    private static final String TAG = DealConfictActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealconfict);


        MyLayout myLayout = (MyLayout) findViewById(R.id.mylayout);
        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

//                Log.i(TAG, "myLayout.setOnTouchListener " + motionEvent.getAction());

                //可以用来拦截事件
                return false;
            }
        });

    }
}
