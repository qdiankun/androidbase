package com.me.diankun.toolbardemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.me.diankun.toolbardemo.R;

/**
 * Created by diankun on 2016/1/20.
 */
public class ReuseToolbarActivity extends AppCompatActivity {

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reuse_toolbar);
        // Find the toolbar view inside the activity layout
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //set title
        mToolbar.setTitle("ReUse");
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(mToolbar);
    }

}
