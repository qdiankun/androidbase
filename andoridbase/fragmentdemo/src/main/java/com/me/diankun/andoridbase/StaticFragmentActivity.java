package com.me.diankun.andoridbase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.me.diankun.andoridbase.fragment.FooFragment;

/**
 * Created by diankun on 2016/2/17.
 */
public class StaticFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_fragment);

        if (savedInstanceState == null) {
            FooFragment fragmentDemo = (FooFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_static);
            Log.i("Static", "fragmentDemo = " + fragmentDemo);
        }
    }
}
