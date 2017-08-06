package com.andfast.app.view.video;

import com.andfast.app.R;
import com.andfast.app.presenter.home.HomeHotPersenter;
import com.andfast.app.view.base.BaseMainFragment;
import com.andfast.app.view.common.activity.MainActivity;

/**
 * Created by mby on 17-7-31.
 */

public class VideoFragment extends BaseMainFragment{
    private MainActivity mMainActivity;

    @Override
    protected HomeHotPersenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_video;
    }

    @Override
    public int getBarTitleString() {
        return R.string.tab_name_video;
    }
}
