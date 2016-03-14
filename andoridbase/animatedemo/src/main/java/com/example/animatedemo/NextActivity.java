package com.example.animatedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by diankun on 2016/3/12.
 */
public class NextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.finish_enter_anim, R.anim.finish_exit_anim);
    }

    /**
     * 退出当前Activity时的动画
     */
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
//    }
}
