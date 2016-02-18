package com.me.diankun.andoridbase.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.me.diankun.andoridbase.R;

/**
 * Created by diankun on 2016/2/18.
 */
public class IndexFragment extends Fragment {

    private int mIndex = -1;
    private TextView mTitle ;

    public static IndexFragment newInstance(int index) {
        IndexFragment indexFragment = new IndexFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        indexFragment.setArguments(bundle);
        return indexFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null) mIndex = bundle.getInt("index");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle = (TextView) view.findViewById(R.id.tv_content);
        mTitle.setText("Fragment : " + mIndex);
    }
}
