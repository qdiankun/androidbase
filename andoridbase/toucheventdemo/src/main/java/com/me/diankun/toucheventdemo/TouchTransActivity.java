package com.me.diankun.toucheventdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by diankun on 2016/3/12.
 */
public class TouchTransActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchtrans);

    }

    public void onClick(View view) {
        Intent i = new Intent(TouchTransActivity.this, DealConfictActivity.class);
        startActivity(i);
    }
}
