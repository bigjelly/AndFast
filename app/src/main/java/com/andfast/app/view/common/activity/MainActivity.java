package com.andfast.app.view.common.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.widget.Toast;

import com.andfast.app.R;
import com.andfast.app.view.base.BaseActivity;
import com.andfast.app.view.common.TabManager;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private long mKeyTime = 0;
    TabManager mTabManager;


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mTabManager.changeTab(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initView() {
        TabLayout tabLayout = findView(R.id.tl_main_tabs);
        // 初始化底部的view
        mTabManager = TabManager.getInstance(getApplicationContext());
        mTabManager.initTabs(this,getIntent(),tabLayout);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getContentView() {
        return R.layout.act_main;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mKeyTime) > 2000) {
                mKeyTime = System.currentTimeMillis();
                Toast.makeText(getApplicationContext(), "确定要离开吗？", Toast.LENGTH_SHORT).show();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
