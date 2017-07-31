package com.andfast.app.view.common.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andfast.app.R;
import com.andfast.app.constant.GeneralID;
import com.andfast.app.view.base.BaseActivity;
import com.andfast.app.view.common.MainTab;
import com.andfast.app.view.widget.SpacingTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private TabLayout mTabLayout;
    private Fragment mCurrentFragment;
    private LayoutInflater mInflater;
    private int mCurrentIdx;
    private int mLastIdx;
    private TextView[] mTabTextView = new TextView[MainTab.values().length];

    private List<TabReselectListener> mTabReselectListeners;
    public interface TabReselectListener {
        void onTabReselect();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        changeTab(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = getView(R.id.tl_main_tabs);
        // 初始化底部的view
        mTabLayout.addOnTabSelectedListener(mTabSelectedListener);
        initTabs();
    }

    private void initTabs() {
        mInflater = LayoutInflater.from(this);

        MainTab[] mainTabs = MainTab.values();
        for (int i = 0; i < mainTabs.length; i++) {
            MainTab mainTab = mainTabs[i];
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabItemView(i, mainTab)).setTag(new TabInfo(mainTab.getClazz())), false);
        }
        changeTab(getIntent());
        mTabTextView[mLastIdx].setTextColor(getResources().getColor(R.color.tab_font_red));
    }

    private void changeTab(Intent intent) {
        int tab = intent.getIntExtra(GeneralID.Extra.TAB, 0);
        tab = Math.min(tab, MainTab.values().length - 1);
        mTabLayout.getTabAt(tab).select();
        mTabTextView[mLastIdx].setTextColor(getResources().getColor(R.color.tab_font_red));
    }

    private View getTabItemView(int i, MainTab mainTab) {
        View view = mInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageview = (ImageView) view.findViewById(R.id.tab_imageview);
        SpacingTextView textView = (SpacingTextView) view.findViewById(R.id.tab_textview);
        imageview.setImageResource(mainTab.getResIcon());
        textView.setText(mainTab.getResName());
        if (i != 0) {
            textView.setLetterSpacing(1);
        }
        textView.setTextColor(Color.parseColor("#222222"));
        mTabTextView[i] = textView;
        return view;
    }

    TabLayout.OnTabSelectedListener mTabSelectedListener = new TabLayout.OnTabSelectedListener(){

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            if (tab.getTag() == null) {
                return;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            TabInfo info = (TabInfo) tab.getTag();
            if (info.fragment == null) {
                info.fragment = Fragment.instantiate(MainActivity.this, info.clss.getName(), info.args);
                transaction.add(R.id.realtabcontent, info.fragment);
            } else {
                transaction.show(info.fragment);
            }
            mCurrentFragment = info.fragment;
            transaction.commit();
            changeTabcolor();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            if (tab.getTag() == null)
                return;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            TabInfo tabInfo = (TabInfo) tab.getTag();
            if (tabInfo.fragment != null) {
                fragmentTransaction.hide(tabInfo.fragment);
            }
            fragmentTransaction.commit();
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            if (null != mTabReselectListeners) {
                for (int i = mTabReselectListeners.size() - 1; i >= 0; i--) {
                    mTabReselectListeners.get(i).onTabReselect();
                }
            }
        }
    };

    private void changeTabcolor() {
        //title_common_bg  tv_share_picture
        mTabTextView[mLastIdx].setTextColor(getResources().getColor(R.color.share_picture));
        mCurrentIdx = mTabLayout.getSelectedTabPosition();
        mTabTextView[mCurrentIdx].setTextColor(getResources().getColor(R.color.tab_font_red));
        mLastIdx = mCurrentIdx;
    }

    static final class TabInfo {
        private final Class<?> clss;
        private final Bundle args;
        private Fragment fragment;

        TabInfo(Class<?> _class) {
            this(_class, null);
        }

        TabInfo(Class<?> _class, Bundle _args) {
            clss = _class;
            args = _args;
        }
    }

    public void addTabReselectListener(TabReselectListener l) {
        if (l == null)
            return;
        if (mTabReselectListeners == null) {
            mTabReselectListeners = new ArrayList<>();
        }
        mTabReselectListeners.add(l);
    }

    public void removeTabReselectListener(TabReselectListener l) {
        if (l == null)
            return;
        if (mTabReselectListeners == null || mTabReselectListeners.isEmpty())
            return;
        mTabReselectListeners.remove(l);
    }
}
