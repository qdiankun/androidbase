package com.me.diankun.imagedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.me.diankun.imagedemo.weidget.ShowMaxImageView1;

/**
 * Created by diankun on 2016/2/18.
 */
public class MaxActivity extends AppCompatActivity {


    private ShowMaxImageView1 mMaxImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max);
        mMaxImg = (ShowMaxImageView1) findViewById(R.id.iv_max);
        //mMaxImg.setImageResource(R.drawable.meiz8);


    }

    @Override
    public void onWindowFocusChanged(boolean focus) {
        super.onWindowFocusChanged(focus);
        // get the imageviews width and height here
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.meizhi1);
        mMaxImg.setImageBitmap(bitmap);
    }
}
