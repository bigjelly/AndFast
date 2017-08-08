package com.andfast.app.view.video.fragment;

import com.andfast.app.R;
import com.andfast.app.model.TestModel;
import com.andfast.app.net.ResultResponse;
import com.andfast.app.presenter.video.VideoTopPersenter;
import com.andfast.app.view.base.BaseListViewFragment;
import com.andfast.app.view.video.Impl.IVideoTopView;
import com.andfast.app.view.video.adapter.HotListAdapter;
import com.andfast.pullrecyclerview.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mby on 17-8-2.
 */

public class VideoTopFragment extends BaseListViewFragment<VideoTopPersenter> implements IVideoTopView {
    private final static String TAG = "VideoTopFragment";
    private List<TestModel> mDataList = new ArrayList<>();


    @Override
    protected VideoTopPersenter createPresenter() {
        return new VideoTopPersenter(this);
    }

    @Override
    public BaseRecyclerAdapter getListAdapter() {
        return new HotListAdapter(mContext, R.layout.lay_hometop_list_item,mDataList);
    }

    @Override
    public void onGetNewsDetailSuccess(List<TestModel> newsDetail) {
    }

    @Override
    public void onRefreshDetailSuccess(Object refreshDetail) {

    }

    @Override
    public void onLoadMoreSuccess(Object loadMore) {

    }

    @Override
    public void onError(int type, ResultResponse response) {

    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
