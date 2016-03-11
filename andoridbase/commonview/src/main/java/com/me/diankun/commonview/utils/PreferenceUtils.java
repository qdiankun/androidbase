package com.me.diankun.commonview.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by diank on 2015/9/15.
 */
public class PreferenceUtils {

    public static final String PREFERENCE_FILE_NAME = "note.settings";

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor shareEditor;

    private static PreferenceUtils preferenceUtils = null;

    private PreferenceUtils(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        shareEditor = sharedPreferences.edit();
    }

    public static PreferenceUtils getInstance(Context context) {
        if (preferenceUtils == null) {
            synchronized (PreferenceUtils.class) {
                if (preferenceUtils == null) {
                    preferenceUtils = new PreferenceUtils(context);
                }
            }
        }
        return preferenceUtils;
    }

    public String getStringParam(String key) {
        return getStringParam(key, "");
    }

    public String getStringParam(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void saveParam(String key, String value) {
        shareEditor.putString(key, value).commit();
    }

    public boolean getBooleanParam(String key) {
        return getBooleanParam(key, false);
    }

    public boolean getBooleanParam(String key, boolean defaultBoolean) {
        return sharedPreferences.getBoolean(key, defaultBoolean);
    }

    public void saveParam(String key, boolean value) {
        shareEditor.putBoolean(key, value).commit();
    }

    public int getIntParam(String key) {
        return getIntParam(key, 0);
    }

    public int getIntParam(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void saveParam(String key, int value) {
        shareEditor.putInt(key, value).commit();
    }

    public long getLongParam(String key) {
        return getLongParam(key, 0);
    }

    public long getLongParam(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public void saveParam(String key, long value) {
        shareEditor.putLong(key, value).commit();
    }


}
