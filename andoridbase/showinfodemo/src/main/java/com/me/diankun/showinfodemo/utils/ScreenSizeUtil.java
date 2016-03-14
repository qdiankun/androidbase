package com.me.diankun.showinfodemo.utils;

import android.app.Activity;

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
