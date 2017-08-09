package com.andfast.app.view.base;

import android.view.View;

import com.andfast.app.presenter.base.BasePresenter;
import com.andfast.app.view.common.activity.MainActivity;

/**
 * Created by mby on 17-8-2.
 * 在mainactivity上的fragment基类
 */

public abstract class BaseMainFragment<P extends BasePresenter> extends BaseFragment<P> {
    private MainActivity mMainActivity;

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mMainActivity = getParentActivity();
    }

    @Override
    protected void showFragmet() {
        super.showFragmet();
    }

}
