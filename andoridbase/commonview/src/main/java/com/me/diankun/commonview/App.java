package com.me.diankun.commonview;

import android.app.Application;

import com.me.diankun.commonview.utils.ToastUtils;

/**
 * Created by diankun on 2016/3/11.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //注册ToastUtils
        ToastUtils.register(this);
    }
}
