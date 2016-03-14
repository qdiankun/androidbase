package com.example.animatedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diankun on 2016/3/12.
 */
public class ChangeAnimActivity extends AppCompatActivity {


    private ListView mListView;
    private List<String> mDatas;
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeanim);

        initDatas();
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);

        //点击进入另外一个Activity的动画
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ChangeAnimActivity.this, NextActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.start_enter_anim, R.anim.start_exit_anim);
            }
        });

    }

    private void initDatas() {

        mDatas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mDatas.add("Item  -  " + (i + 1));
        }
    }
}
