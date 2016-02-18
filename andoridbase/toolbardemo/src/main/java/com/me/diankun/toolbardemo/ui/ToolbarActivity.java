package com.me.diankun.toolbardemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.me.diankun.toolbardemo.R;

/**
 * 含有Toolbar的Activity基类
 * Created by diankun on 2016/1/20.
 */
public abstract class ToolbarActivity extends AppCompatActivity {


    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initToolbar();
    }

    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
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


    public void onToolbarClick() {}

}
