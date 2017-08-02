package com.andfast.app.view.me;

import com.andfast.app.R;
import com.andfast.app.view.base.BaseMainFragment;
import com.andfast.app.view.common.activity.MainActivity;

/**
 * Created by mby on 17-7-31.
 */

public class MeFragment extends BaseMainFragment {
    private MainActivity mMainActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.frg_me;
    }

    @Override
    public int getBarTitleString() {
        return R.string.tab_name_my;
    }
}
