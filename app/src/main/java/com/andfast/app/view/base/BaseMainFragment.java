package com.andfast.app.view.base;

import android.view.View;

import com.andfast.app.view.common.activity.MainActivity;

/**
 * Created by mby on 17-8-2.
 * 在mainactivity上的fragment基类
 */

public abstract class BaseMainFragment extends BaseFragment {
    private MainActivity mMainActivity;
    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mMainActivity = getParentActivity();
        mMainActivity.setToolBarCenterTitle(getBarTitleString());
    }

    @Override
    protected void showFragmet() {
        super.showFragmet();
        mMainActivity.setToolBarCenterTitle(getBarTitleString());
    }
    /**
     * 获取居中头部标题
     */
    public abstract int getBarTitleString();

}
