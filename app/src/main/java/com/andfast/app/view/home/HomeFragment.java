package com.andfast.app.view.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.andfast.app.R;
import com.andfast.app.util.LogUtils;
import com.andfast.app.view.base.BaseMainFragment;
import com.andfast.app.view.common.TabManager;
import com.andfast.app.view.common.activity.MainActivity;
import com.andfast.app.view.home.adapter.HomePageAdapter;
import com.andfast.app.view.home.fragment.HomeHotFragment;
import com.andfast.app.view.home.fragment.HomeTopFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mby on 17-7-31.
 */

public class HomeFragment extends BaseMainFragment implements TabManager.TabReselectListener {

    private static final String TAG = "HomeFragment";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private MainActivity mMainActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.frg_home;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        TabManager.getInstance(getContext()).addTabReselectListener(this);
        mTabLayout = findView(R.id.hometab);
        mViewPager = findView(R.id.homeviewpager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initData() {
        super.initData();
        String[] titles = getResources().getStringArray(R.array.tabs_home);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeTopFragment());
        fragments.add(new HomeHotFragment());
        HomePageAdapter adapter = new HomePageAdapter(this.getChildFragmentManager(),
                fragments, titles);
        mViewPager.setAdapter(adapter);
    }

    @Override
    protected void showFragmet() {
        super.showFragmet();
        TabManager.getInstance(getContext()).addTabReselectListener(this);
    }

    @Override
    public int getBarTitleString() {
        return R.string.tab_name_home;
    }

    @Override
    protected void hiddenFragment() {
        super.hiddenFragment();
        TabManager.getInstance(getContext()).removeTabReselectListener(this);
    }

    @Override
    public void onTabReselect() {
        LogUtils.d(TAG, "onTabReselect");
    }
}
