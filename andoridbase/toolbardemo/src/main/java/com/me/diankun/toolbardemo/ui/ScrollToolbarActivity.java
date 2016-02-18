package com.me.diankun.toolbardemo.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.me.diankun.toolbardemo.utils.DividerItemDecoration;
import com.me.diankun.toolbardemo.R;
import com.me.diankun.toolbardemo.adapter.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diankun on 2016/1/20.
 */
public class ScrollToolbarActivity extends ToolbarActivity {

    private RecyclerView mRecyclerView;
    private SimpleAdapter mAdapter;
    private List<String> mDatas;

    private static final String TTITLE_NAME = "Toolbar Demo";
    @Override
    public int getLayoutId() {
        return R.layout.activity_scroll_toolbar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mToolbar.setTitle(TTITLE_NAME);

        initData();

        setRecyclerView();

//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle(TTITLE_NAME);
    }

    private void setRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //set adapter
        mAdapter = new SimpleAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        //set layoutmanager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //set Decorations
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);

    }

    private void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            mDatas.add("Item  " + i);
        }
    }

    @Override
    public boolean canBack() {
        return true;
    }
}
