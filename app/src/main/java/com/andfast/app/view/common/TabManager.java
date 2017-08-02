package com.andfast.app.view.common;

import android.content.Context;
import android.content.Intent;
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
import com.andfast.app.view.common.activity.MainActivity;
import com.andfast.app.view.widget.SpacingTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mby on 17-8-1.
 */

public class TabManager {

    private TabLayout mTabLayout;
    private Fragment mCurrentFragment;
    private LayoutInflater mInflater;
    private int mCurrentIdx;
    private int mLastIdx;
    private TextView[] mTabTextView = new TextView[MainTab.values().length];

    private List<TabReselectListener> mTabReselectListeners;

    private static Context mContext;


    public interface TabReselectListener {
        void onTabReselect();
    }

    private TabManager(){

    }

    public static TabManager getInstance(Context context){
        mContext = context;
        return TabMangerHolder.sInstance;
    }

    private static class TabMangerHolder{
        private static final TabManager sInstance = new TabManager();
    }


    public void initTabs(MainActivity mainActivity,Intent intent,TabLayout tabLayout) {
        mTabLayout = tabLayout;
        mInflater = LayoutInflater.from(mContext);
        tabLayout.addOnTabSelectedListener(getTabSelectedListener(mainActivity));

        MainTab[] mainTabs = MainTab.values();
        for (int i = 0; i < mainTabs.length; i++) {
            MainTab mainTab = mainTabs[i];
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(getTabItemView(i, mainTab)).setTag(new TabInfo(mainTab.getClazz())), false);
        }
        changeTab(intent);
        mTabTextView[mLastIdx].setTextColor(mContext.getResources().getColor(R.color.tab_font_red));
    }

    public void changeTab(Intent intent) {
        int tab = intent.getIntExtra(GeneralID.Extra.TAB, 0);
        tab = Math.min(tab, MainTab.values().length - 1);
        mTabLayout.getTabAt(tab).select();
        mTabTextView[mLastIdx].setTextColor(mContext.getResources().getColor(R.color.tab_font_red));
    }

    public View getTabItemView(int i, MainTab mainTab) {
        View view = mInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageview = (ImageView) view.findViewById(R.id.tab_imageview);
        SpacingTextView textView = (SpacingTextView) view.findViewById(R.id.tab_textview);
        imageview.setImageResource(mainTab.getResIcon());
        textView.setText(mainTab.getResName());
        if (i != 0) {
            textView.setLetterSpacing(1);
        }
        textView.setTextColor(mContext.getResources().getColor(R.color.tab_font_normal));
        mTabTextView[i] = textView;
        return view;
    }

    public TabLayout.OnTabSelectedListener getTabSelectedListener(final MainActivity mainActivity){

        return new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getTag() == null) {
                    return;
                }
                FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                TabInfo info = (TabInfo) tab.getTag();
                if (info.fragment == null) {
                    info.fragment = Fragment.instantiate(mainActivity, info.clss.getName(), info.args);
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
                FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
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

    }

    private void changeTabcolor() {
        //title_common_bg  tv_share_picture
        mTabTextView[mLastIdx].setTextColor(mContext.getResources().getColor(R.color.share_picture));
        mCurrentIdx = mTabLayout.getSelectedTabPosition();
        mTabTextView[mCurrentIdx].setTextColor(mContext.getResources().getColor(R.color.tab_font_red));
        mLastIdx = mCurrentIdx;
    }

    public static final class TabInfo {
        private final Class<?> clss;
        private final Bundle args;
        private Fragment fragment;

        public TabInfo(Class<?> _class) {
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
    public void clear(){
        mContext = null;

    }
}
