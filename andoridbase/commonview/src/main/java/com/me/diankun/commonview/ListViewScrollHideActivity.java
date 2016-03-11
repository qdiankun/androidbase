package com.me.diankun.commonview;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListViewScrollHideActivity extends AppCompatActivity {

    @Bind(R.id.listview)
    ListView mListView;

    @Bind(R.id.toolbar)
    Toolbar mToolBar;

    private List<String> mDatas;
    private ArrayAdapter<String> mAdapter;

    private int mTouchSlop;//触发移动事件的最短距离
    private float mFirstY;//记录手指按下去时的y坐标
    private float mCurrentY;//当前Y轴的坐标
    private ObjectAnimator animator;//动画

    private static final int MOVE_DOWN = 0;
    private static final int MOVE_UP = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_scroll_hide);

        ButterKnife.bind(this);

        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        //添加Header,避免第一个Item被Toolbar遮挡
        View headView = new View(this);
        //abc_action_bar_default_height_material获取系统的ActionBar的高度
        headView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.abc_action_bar_default_height_material)));
        mListView.addHeaderView(headView);

        initDatas();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, mDatas);
        mListView.setAdapter(mAdapter);

        //设置Touch监听事件
        mListView.setOnTouchListener(mTouchListener);
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mFirstY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurrentY = event.getY();
                    if (mCurrentY - mFirstY > mTouchSlop) {
                        //向下滑动
                        toolbarAnim(MOVE_DOWN);
                    } else {
                        //向上滑动
                        toolbarAnim(MOVE_UP);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return false;
        }
    };

    private void toolbarAnim(int state) {
        //停止之前的动画
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        if (state == MOVE_DOWN) {
            animator = ObjectAnimator.ofFloat(mToolBar, "translationY", mToolBar.getTranslationY(), 0);
        } else if (state == MOVE_UP) {
            animator = ObjectAnimator.ofFloat(mToolBar, "translationY", mToolBar.getTranslationY(), -mToolBar.getHeight());
        }
        animator.start();
    }

    private void initDatas() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 26; i++) {
            mDatas.add(String.valueOf((char) ('a' + i)));
        }
    }
}
