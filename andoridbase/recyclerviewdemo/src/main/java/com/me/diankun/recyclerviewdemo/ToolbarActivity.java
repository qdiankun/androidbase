package com.me.diankun.recyclerviewdemo;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by diankun on 2016/2/25.
 */
public abstract class ToolbarActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;
    @Bind(R.id.app_bar_layout)
    protected AppBarLayout mAppBar;

    protected boolean mIsHidden = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initToolbar();
    }

    protected void initToolbar() {
        if (mToolbar == null)
            return;
        //mToolbar.setBackgroundColor(getColorPrimary());//动态设置背景色
        mToolbar.setTitle(R.string.app_name);
        //mToolbar.setTitleTextColor(getColor(R.color.action_bar_title_color));//动态设置标题颜色
        mToolbar.collapseActionView();
        setSupportActionBar(mToolbar);

        //是否有返回箭头
        if (canBack()) {
            if (getSupportActionBar() != null) {
                ActionBar actionbar = getSupportActionBar();
                actionbar.setDisplayHomeAsUpEnabled(true);// 给左上角图标的左边加上一个返回的图标"《"
                // 对应ActionBar.DISPLAY_HOME_AS_UP
                actionbar.setDisplayShowHomeEnabled(true); // 使左上角图标可点击，对应id为android.R.id.home
                actionbar.setHomeButtonEnabled(true); // false 就无法点击
                actionbar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            }
        }
        //Toolbar点击事件
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onToolbarClick();
            }
        });
    }

    //点击回去箭头返回
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 能否返回
     *
     * @return boolean
     */
    public boolean canBack() {
        return false;
    }

    public abstract int getLayoutId();

    public void openActivity(Class<? extends Activity> clazz) {
        Intent i = new Intent(this, clazz);
        startActivity(i);
    }


    public void onToolbarClick() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    protected void hideOrShowToolbar() {
        ObjectAnimator animator = null;
        if (mIsHidden) {
            animator = ObjectAnimator.ofFloat(mAppBar, "translationY", -mAppBar.getHeight(), 0);
        } else {
            animator = ObjectAnimator.ofFloat(mAppBar, "translationY", 0, -mAppBar.getHeight());
        }
        //animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
        mIsHidden = !mIsHidden;
    }
}