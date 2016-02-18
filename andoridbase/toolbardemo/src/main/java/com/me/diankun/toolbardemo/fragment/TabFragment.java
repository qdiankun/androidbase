package com.me.diankun.toolbardemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.diankun.toolbardemo.utils.DividerItemDecoration;
import com.me.diankun.toolbardemo.R;
import com.me.diankun.toolbardemo.adapter.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diankun on 2016/1/21.
 */
public class TabFragment extends Fragment {



    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private SimpleAdapter mAdapter;

    // newInstance constructor for creating fragment with arguments
    public static TabFragment newInstance() {
        TabFragment tabFragment = new TabFragment();
        return tabFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        setUpRecyclerView(view);
        return view;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setUpRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        initDatas();
        mAdapter = new SimpleAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        //set layoutmanager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //set Decorations
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    private void initDatas() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            mDatas.add("Item "+i);
        }
    }

}
