package com.me.diankun.andoridbase;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.me.diankun.andoridbase.fragment.IndexFragment;

/**
 * Created by diankun on 2016/2/18.
 */
public class BackstckActivity extends AppCompatActivity {

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backstack);
    }

    public void addToBackstack(View view) {
        index++;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, IndexFragment.newInstance(index));
        ft.addToBackStack(""+index);
        ft.commit();
    }

    public void removeToBackstack(View view) {
        getSupportFragmentManager().popBackStack();
    }



}
