package com.andfast.app.view.video;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.andfast.app.R;
import com.andfast.app.view.base.BaseFragment;
import com.andfast.app.view.video.adapter.VideoPageAdapter;
import com.andfast.app.view.video.fragment.VideoHotFragment;
import com.andfast.app.view.video.fragment.VideoTopFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mby on 17-7-31.
 */

public class VideoFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.frg_video;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        TextView textView =findView(R.id.toolbar_title);
        textView.setText(R.string.tab_name_video);
        mTabLayout = findView(R.id.tablayout);
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
