package com.me.diankun.andoridbase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by diankun on 2016/2/17.
 */
public class RssfeedActivity extends AppCompatActivity implements MyListenerFragment.OnItemSelectedListener {
    // Can be any fragment, `DetailFragment` is just an example
    MyListenerFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rssfeed);
        // Get access to the detail view fragment by id
        fragment = (MyListenerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detailFragment);
    }

    // Now we can define the action to take in the activity when the fragment event fires
    // This is implementing the `OnItemSelectedListener` interface methods
    @Override
    public void onRssItemSelected(String link) {
        Toast.makeText(RssfeedActivity.this, "Toast = " + link, Toast.LENGTH_SHORT).show();
    }
}