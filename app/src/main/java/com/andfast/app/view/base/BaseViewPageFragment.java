package com.andfast.app.view.base;

/**
 * Created by mby on 17-8-3.
 */

public abstract class BaseViewPageFragment extends BaseFragment{

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            showFragmet();
        }else {
            hiddenFragment();
        }
    }

    protected void showFragmet() {}

    protected  void hiddenFragment(){}
}
