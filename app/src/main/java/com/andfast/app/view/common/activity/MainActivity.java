package com.andfast.app.view.common.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;

import com.andfast.app.R;
import com.andfast.app.view.base.BaseActivity;
import com.andfast.app.view.common.TabManager;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    TabManager mTabManager;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mTabManager.changeTab(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTabManager.clear();
    }

    @Override
    protected void initView() {
        TabLayout tabLayout = getView(R.id.tl_main_tabs);
        // 初始化底部的view
        mTabManager = TabManager.getInstance(getApplicationContext());
        mTabManager.initTabs(this,getIntent(),tabLayout);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
}
