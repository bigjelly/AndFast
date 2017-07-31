package com.andfast.app.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.andfast.app.R;
import com.andfast.app.util.LogUtils;
import com.andfast.app.view.base.BaseFragment;
import com.andfast.app.view.common.activity.MainActivity;

/**
 * Created by mby on 17-7-31.
 */

public class HomeFragment extends BaseFragment implements MainActivity.TabReselectListener {

    private static final String TAG = "HomeFragment";

    MainActivity mMainActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.frg_home;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = (MainActivity) this.getActivity();
        mMainActivity.addTabReselectListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.d(TAG, "onHiddenChanged :" + hidden);
        if (!hidden) {
            mMainActivity.addTabReselectListener(this);

        }else {
            mMainActivity.removeTabReselectListener(this);
        }
    }

    @Override
    public void onTabReselect() {
        LogUtils.d(TAG, "onTabReselect");
    }
}
