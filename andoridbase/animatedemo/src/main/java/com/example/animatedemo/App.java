package com.example.animatedemo;

import android.app.Application;

import com.example.animatedemo.utils.ToastUtils;

/**
 * Created by diankun on 2016/3/11.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.register(this);
    }
}
