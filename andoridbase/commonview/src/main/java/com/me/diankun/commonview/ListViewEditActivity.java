package com.me.diankun.commonview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.me.diankun.commonview.adapter.EditListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListViewEditActivity extends AppCompatActivity {


    @Bind(R.id.listview)
    ListView mListView;
    private List<String> mDatas;
    private EditListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_edit);

        ButterKnife.bind(this);

        initDatas();
        mAdapter = new EditListAdapter(mDatas, this);
        mListView.setAdapter(mAdapter);
    }

    private void initDatas() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            mDatas.add(String.valueOf((char) ('a' + i)));
        }
    }
}
