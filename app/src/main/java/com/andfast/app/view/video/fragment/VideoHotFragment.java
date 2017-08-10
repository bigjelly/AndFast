package com.andfast.app.view.video.fragment;

import android.view.View;

import com.andfast.app.R;
import com.andfast.app.model.TestModel;
import com.andfast.app.model.Topic;
import com.andfast.app.net.ResultResponse;
import com.andfast.app.presenter.home.HomePresenter;
import com.andfast.app.view.base.BaseListViewFragment;
import com.andfast.app.view.home.Impl.IHomeListView;
import com.andfast.app.view.home.adapter.HomeListAdapter;
import com.andfast.pullrecyclerview.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.andfast.app.constant.GeneralID.TYPE_LOAD_MORE;
import static com.andfast.app.constant.GeneralID.TYPE_PULL_REFRESH;

/**
 * Created by mby on 17-8-2.
 */

public class VideoHotFragment extends BaseListViewFragment<HomePresenter> implements IHomeListView {

    private final static String TAG = "VideoHotFragment";

    private List<TestModel> mDataList = new ArrayList<>();

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mAppBarLayout.setVisibility(View.GONE);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public BaseRecyclerAdapter getListAdapter() {
        return new HomeListAdapter(mContext, R.layout.lay_item_topic, new ArrayList<Topic>());
    }

    @Override
    public void onRefreshDetailSuccess(List<Topic> refreshDetail) {
        mAdapter.replaceAll(refreshDetail);
        mPullRecyclerView.stopRefresh();
        mPullRecyclerView.enableLoadMore(true);
    }

    @Override
    public void onLoadMoreSuccess(List<Topic> loadMore) {
        mAdapter.addAll(loadMore);
        mAdapter.notifyDataSetChanged();
        mPullRecyclerView.stopLoadMore();
        mPullRecyclerView.enableLoadMore(true);
    }

    @Override
    public void onError(int type, ResultResponse response) {
        switch (type) {
            case TYPE_PULL_REFRESH:
                mEmptyView.setVisibility(View.VISIBLE);
                mPullRecyclerView.stopRefresh();
                mPullRecyclerView.enableLoadMore(false);
                break;
            case TYPE_LOAD_MORE:
                mPullRecyclerView.enableLoadMore(false);
                break;
        }
    }

    @Override
    public void onPullRefresh() {
        mvpPresenter.getPullRefresh();
    }

    @Override
    public void onLoadMore() {
        mvpPresenter.getLoadMore();
    }
}
