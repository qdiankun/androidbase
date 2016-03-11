package com.me.diankun.commonview.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;

import com.me.diankun.commonview.R;
import com.me.diankun.commonview.utils.PreferenceUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by diank on 2015/9/16.
 */
public abstract class ToolbarActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    protected PreferenceUtils mPreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPreferenceUtils = PreferenceUtils.getInstance(this);
        //initTheme();

        setContentView(getLayoutId());
        ButterKnife.bind(this);

        initToolbar();
    }

//    private void initTheme() {
//        int position = mPreferenceUtils.getIntParam(getString(R.string.choosed_theme_position));
//        ThemeUtils.Theme theme = ThemeUtils.Theme.mapValue2Theme(position);
//        ThemeUtils.changeTheme(this,theme);
//        //设置状态栏颜色
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            getWindow().setStatusBarColor(getStatusBarColor());
//        }
//    }

    protected void initToolbar() {
        if (mToolbar == null)
            return;
        //动态设置背景色
        //mToolbar.setBackgroundColor(getColorPrimary());
        mToolbar.setTitle(R.string.app_name);
        //动态设置标题颜色
        //mToolbar.setTitleTextColor(getColor(R.color.action_bar_title_color));
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
    }

    //点击回去箭头返回
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 能否返回
     * @return boolean
     */
    public boolean canBack() {
        return true;
    }

    public int getStatusBarColor(){
        return getDarkColorPrimary();
    }

    public int getColorPrimary(){
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public int getDarkColorPrimary(){
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }



    public abstract int getLayoutId();
}
