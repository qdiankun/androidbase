package com.me.diankun.andoridbase.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.diankun.andoridbase.R;

/**
 * Created by diankun on 2016/2/17.
 */
public class FooFragment extends Fragment {


    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static FooFragment newInstance(int someInt, String someTitle) {
        FooFragment fooFragment = new FooFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        args.putString("someTitle", someTitle);
        fooFragment.setArguments(args);
        return fooFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get back arguments
        int someInt = getArguments().getInt("someInt", 0);
        String someTitle = getArguments().getString("someTitle", "title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.fragment_foo, container, false);
        // Setup handles to view objects here
        return view;
    }
}
