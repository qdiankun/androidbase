package com.me.diankun.commonview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.me.diankun.commonview.adapter.NotifyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListViewNotifyActivity extends AppCompatActivity {

    @Bind(R.id.lv_add_item)
    ListView mListView;

    private List<String> mDatas;
    private NotifyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_notify);

        ButterKnife.bind(this);

        initDatas();
        mAdapter = new NotifyAdapter(mDatas, this);
        mListView.setAdapter(mAdapter);
    }

    private void initDatas() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            mDatas.add(String.valueOf((char) ('a' + i)));
        }
    }

    /**
     * ListView的底部添加一个Item
     *
     * @param view
     */
    @OnClick(R.id.btn_add_item)
    void addItem(View view) {
        mDatas.add("Welcome");
        mAdapter.notifyDataSetChanged();
        mListView.setSelection(1);//第二个位置
        //mListView.setSelection(mDatas.size() - 1);
    }
}
