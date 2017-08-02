package com.andfast.app.view.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mby on 17-8-2.
 */

public class HomePageAdapter extends FragmentPagerAdapter {

    private String[] mTabsTitle;
    List<Fragment> fragmentList = new ArrayList<Fragment>();

    public HomePageAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] tabsTitle) {
        super(fm);
        this.fragmentList = fragmentList;
        this.mTabsTitle = tabsTitle;
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsTitle[position];
    }
}
