package com.me.diankun.toolbardemo.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.me.diankun.toolbardemo.R;
import com.me.diankun.toolbardemo.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diankun on 2016/1/21.
 */
public class TabToolbarActivity extends ToolbarActivity {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TabAdapter mTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar.setTitle("Tab");
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setUpViewPager();

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setUpViewPager() {
        mTabAdapter = new TabAdapter(getSupportFragmentManager());
        mTabAdapter.addFragment(TabFragment.newInstance(), "Title1");
        mTabAdapter.addFragment(TabFragment.newInstance(), "Title2");
        mViewPager.setAdapter(mTabAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tab_toolbar;
    }

    class TabAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments = new ArrayList<Fragment>();
        private List<String> mFragmentTitles = new ArrayList<String>();

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
