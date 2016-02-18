package com.me.diankun.imagedemo;

import android.app.Activity;

/**
 * Created by diankun on 2016/2/18.
 */
public class ScreenSizeUtil {

    public static int getScreenWidth(Activity activity)
    {
        return activity.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Activity activity)
    {
        return activity.getResources().getDisplayMetrics().heightPixels;
    }
}
