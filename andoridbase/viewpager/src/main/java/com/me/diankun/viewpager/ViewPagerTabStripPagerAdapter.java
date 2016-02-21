package com.me.diankun.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by diankun on 2016/2/19.
 */
public class ViewPagerTabStripPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{

    final int PAGE_COUNT = 3;
    // Tab Titles
    private String[] tabTitles = new String[]{"最新推荐", "游戏娱乐", "影音视频"};

    public ViewPagerTabStripPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        FooFragment fragment = null;
        switch (position) {
            case 0:
                fragment = FooFragment.newInstance(tabTitles[0]);
                break;
            case 1:
                fragment = FooFragment.newInstance(tabTitles[1]);
                break;
            case 2:
                fragment = FooFragment.newInstance(tabTitles[2]);
                break;
            default:
                break;
        }
        Log.d("TAG", "position=" + position + "\tfragment=" + fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.i("TAG", "position=" + position);
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        FooFragment fooFragment = (FooFragment) object;
        Log.e("TAG", "position=" + position + "\tfragment=" + fooFragment);
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getPageIconResId(int position) {
        return 0;
    }
}
