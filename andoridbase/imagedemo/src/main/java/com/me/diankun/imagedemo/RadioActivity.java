package com.me.diankun.imagedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.me.diankun.imagedemo.weidget.RadioImageView;

/**
 * Created by diankun on 2016/2/18.
 */
public class RadioActivity extends AppCompatActivity {

    private RadioImageView iv_radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        iv_radio = (RadioImageView) findViewById(R.id.iv_radio);
        iv_radio.setOriginalWdith(20,20);
        iv_radio.setImageDrawable(getResources().getDrawable(R.drawable.jialin));
    }
}
