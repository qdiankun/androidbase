package com.me.diankun.andoridbase.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.me.diankun.andoridbase.R;

/**
 * Created by diankun on 2016/3/3.
 */
public class NewsDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
    }
}
