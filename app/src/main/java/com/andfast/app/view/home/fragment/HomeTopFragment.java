package com.andfast.app.view.home.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.andfast.app.R;
import com.andfast.app.util.LogUtils;
import com.andfast.app.view.base.BaseFragment;
import com.andfast.app.view.common.TabManager;
import com.andfast.app.view.home.adapter.TopListAdapter;
import com.andfast.pullrecyclerview.BaseRecyclerAdapter;
import com.andfast.pullrecyclerview.PullRecyclerView;
import com.andfast.pullrecyclerview.layoutmanager.XLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mby on 17-8-2.
 */

public class HomeTopFragment extends BaseFragment implements TabManager.TabReselectListener {
    private final static String TAG = "HomeTopFragment";

    private PullRecyclerView mPullRecyclerView;
    private List<String> mDataList = new ArrayList<>();
    private BaseRecyclerAdapter<String> mAdapter;
    private int pageSize = 0;

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        TabManager.getInstance(getContext()).addTabReselectListener(this);
        mPullRecyclerView = findView(R.id.pull_recycler_view);

        // 初始化PullRecyclerView
        mPullRecyclerView.setLayoutManager(new XLinearLayoutManager(getContext()));
        mAdapter = new TopListAdapter(getContext(), R.layout.lay_hometop_list_item, mDataList);
        mPullRecyclerView.setAdapter(mAdapter);

        mPullRecyclerView.setColorSchemeResources(R.color.colorAccent); // 设置下拉刷新的旋转圆圈的颜色
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_simple_item_decoration));
        mPullRecyclerView.addItemDecoration(itemDecoration);

        mPullRecyclerView.enablePullRefresh(true); // 开启下拉刷新，默认即为true，可不用设置
        mPullRecyclerView.enableLoadDoneTip(true, R.string.load_done_tip); // 开启数据全部加载完成时的底部提示，默认为false

        mPullRecyclerView.setOnRecyclerRefreshListener(new PullRecyclerView.OnRecyclerRefreshListener() {
            @Override
            public void onPullRefresh() {
                mPullRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.d(TAG,"onPullRefresh!!!");
                        mDataList.clear();
                        mDataList.addAll(getData());
                        mAdapter.notifyDataSetChanged();
                        // 或者直接使用BaseRecyclerAdapter中封装的方法
                        //mAdapter.replaceAll(mDataList);
                        mPullRecyclerView.stopRefresh();
                        mPullRecyclerView.enableLoadMore(true); // 当剩余还有大于0页的数据时，开启上拉加载更多
                        pageSize = 2;
                    }
                },1500);
            }

            @Override
            public void onLoadMore() {
                LogUtils.d(TAG,"onLoadMore!!!");
                pageSize--;
                mPullRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDataList.addAll(getData());
                        mAdapter.notifyDataSetChanged();
                        // 或者直接使用BaseRecyclerAdapter中封装的方法
                        //mAdapter.addAll(mDataList);

                        mPullRecyclerView.stopLoadMore();
                        mPullRecyclerView.enableLoadMore(pageSize > 0);
                    }
                },2000);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void showFragmet() {
        super.showFragmet();
        TabManager.getInstance(getContext()).addTabReselectListener(this);
    }

    @Override
    protected void hiddenFragment() {
        super.hiddenFragment();
        TabManager.getInstance(getContext()).removeTabReselectListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_home_top;
    }

    public List<String> getData() {
        for (int i = 0;i<30;i++){
            mDataList.add("page "+pageSize+" data --> "+i);
        }
        return mDataList;
    }

    @Override
    public void onTabReselect() {
        LogUtils.d(TAG,"onTabReselect!!!");
        mPullRecyclerView.postRefreshing();
    }
}
