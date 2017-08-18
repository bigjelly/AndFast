package com.andfast.app.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.andfast.app.presenter.base.BasePresenter;

/**
 * Created by mby on 17-8-7.
 */

public abstract class BasePresenterFragment<P extends BasePresenter> extends BaseFragment {

    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mvpPresenter == null) mvpPresenter = createPresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
            mvpPresenter = null;
        }
    }
}
