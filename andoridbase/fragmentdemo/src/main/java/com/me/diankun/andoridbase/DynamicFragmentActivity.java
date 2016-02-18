package com.me.diankun.andoridbase;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.me.diankun.andoridbase.fragment.FooFragment;

/**
 * Created by diankun on 2016/2/17.
 */
public class DynamicFragmentActivity extends AppCompatActivity {

    private Button mFindFragmentBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_fragment);
        FooFragment fooFragment = new FooFragment();
        Log.i("Dynamic", "fooFragment = " + fooFragment);
        mFindFragmentBtn = (Button) findViewById(R.id.findFragment);
        mFindFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FooFragment fragment = (FooFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
                Log.i("Dynamic", "findfragment = " + fragment);
            }
        });
        //添加Fragment
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.frame_content, fooFragment,"foo").commit();
        }
    }
}
