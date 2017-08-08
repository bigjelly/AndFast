package com.andfast.app.view.video;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.andfast.app.R;
import com.andfast.app.model.TestModel;
import com.andfast.app.net.ResultResponse;
import com.andfast.app.presenter.video.VideoPersenter;
import com.andfast.app.view.base.BaseMainFragment;
import com.andfast.app.view.common.activity.MainActivity;
import com.andfast.app.view.video.Impl.IVideoView;
import com.andfast.app.view.video.adapter.VideoPageAdapter;
import com.andfast.app.view.video.fragment.VideoHotFragment;
import com.andfast.app.view.video.fragment.VideoTopFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mby on 17-7-31.
 */

public class VideoFragment extends BaseMainFragment<VideoPersenter> implements IVideoView {

    @Override
    protected int getLayoutId() {
        return R.layout.frg_video;
    }

    @Override
    protected VideoPersenter createPresenter() {
        return new VideoPersenter(this);
    }

    @Override
    public int getBarTitleString() {
        return R.string.tab_name_video;
    }

    @Override
    public void onError(int type, ResultResponse response) {

    }

    @Override
    public void onVideoDetailSuccess(List<TestModel> newsDetail) {

    }
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private MainActivity mMainActivity;

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mTabLayout = findView(R.id.hometab);
        mViewPager = findView(R.id.homeviewpager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initData() {
        super.initData();
        String[] titles = getResources().getStringArray(R.array.tabs_home);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new VideoHotFragment());
        fragments.add(new VideoTopFragment());
        VideoPageAdapter adapter = new VideoPageAdapter(this.getChildFragmentManager(),
                fragments, titles);
        mViewPager.setAdapter(adapter);
    }
}
