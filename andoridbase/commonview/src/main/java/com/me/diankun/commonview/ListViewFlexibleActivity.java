package com.me.diankun.commonview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.me.diankun.commonview.view.FlexibleListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListViewFlexibleActivity extends AppCompatActivity {

    @Bind(R.id.flexiable_listview)
    FlexibleListView mListView;

    private List<String> mDatas;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_flexible);

        ButterKnife.bind(this);

        initDatas();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);
    }


    private void initDatas() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            mDatas.add(String.valueOf((char) ('a' + i)));
        }
    }


}
