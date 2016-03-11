package com.me.diankun.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.me.diankun.recyclerviewdemo.adapter.ChooseListAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by diankun on 2016/3/11.
 */
public class ChooseListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private ChooseListAdapter mAdapter;
    private List<String> mDatas;
    //对应的Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooselist);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        initRecyclerView();
    }

    private void initRecyclerView() {
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //设置适配器
        mDatas = Arrays.asList(getResources().getStringArray(R.array.main_items));
        mAdapter = new ChooseListAdapter(mDatas, this);
        mRecyclerView.setAdapter(mAdapter);
        //设置分割线
        //mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        //设置动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置点击事件
        mAdapter.setOnMainItemClickListener(new ChooseListAdapter.OnMainItemClickListener() {
            @Override
            public void onMainItemClick(View view, int position) {
                Toast.makeText(ChooseListActivity.this, "点击了：" + mDatas.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
