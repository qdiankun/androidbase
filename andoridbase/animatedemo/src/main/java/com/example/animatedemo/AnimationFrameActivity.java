package com.example.animatedemo;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

/**
 * Created by diankun on 2016/3/12.
 */
public class AnimationFrameActivity extends AppCompatActivity {


    private Button btn_frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frameanimation);

        btn_frame = (Button) findViewById(R.id.btn_frame);

        btn_frame.setBackgroundResource(R.drawable.frame_animation);
        AnimationDrawable drawable = (AnimationDrawable) btn_frame.getBackground();
        drawable.start();

    }
}
