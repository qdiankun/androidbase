package com.me.diankun.commonview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.me.diankun.commonview.adapter.LayoutAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListViewLayoutActivity extends AppCompatActivity {

    @Bind(R.id.listview)
    ListView mListVew;
    private List<String> mViewDatas;
    private LayoutAdapter mViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_layout);

        ButterKnife.bind(this);
        initDatas();
        mViewAdapter = new LayoutAdapter(mViewDatas, this);
        mListVew.setAdapter(mViewAdapter);

        mListVew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewAdapter.setmCurrentPosition(position);
                mViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initDatas() {
        mViewDatas = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            mViewDatas.add(String.valueOf((char) ('a' + i)));
        }
    }
}
