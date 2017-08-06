package com.andfast.app.view.base;

import android.os.SystemClock;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import com.andfast.app.R;
import com.andfast.app.model.TestModel;
import com.andfast.app.presenter.home.HomeHotPersenter;
import com.andfast.app.presenter.home.IHomeHotView;
import com.andfast.pullrecyclerview.BaseRecyclerAdapter;
import com.andfast.pullrecyclerview.PullRecyclerView;
import com.andfast.pullrecyclerview.layoutmanager.XLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mby on 17-8-2.
 * list的基累
 */

public abstract class BaseListFragment extends BaseFragment<HomeHotPersenter> implements PullRecyclerView.OnRecyclerRefreshListener, View.OnClickListener,IHomeHotView {

    private final static String TAG = "BaseListFragment";

    private PullRecyclerView mPullRecyclerView;
    private XLinearLayoutManager mLayoutManager;
    protected BaseRecyclerAdapter mAdapter;
    private View mEmptyView;


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mPullRecyclerView = findView(R.id.pull_recycler_view);
        mPullRecyclerView.setOnRecyclerRefreshListener(this);
        // 初始化PullRecyclerView
        mLayoutManager = new XLinearLayoutManager(getContext());
        mPullRecyclerView.setLayoutManager(mLayoutManager);

        if (mAdapter != null) {
            mPullRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter = getListAdapter();
            mPullRecyclerView.setAdapter(mAdapter);
        }

        mPullRecyclerView.setEmptyView(R.layout.lay_empty_view);
        mEmptyView = mPullRecyclerView.getAdapter().getEmptyView();
        mEmptyView.findViewById(R.id.empty_view_btn).setOnClickListener(this);
        mEmptyView.setVisibility(View.GONE);


        mPullRecyclerView.setColorSchemeResources(R.color.colorAccent); // 设置下拉刷新的旋转圆圈的颜色
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_simple_item_decoration));
        mPullRecyclerView.addItemDecoration(itemDecoration);

        mPullRecyclerView.enablePullRefresh(true); // 开启下拉刷新，默认即为true，可不用设置
        mPullRecyclerView.enableLoadDoneTip(true, R.string.load_done_tip); // 开启数据全部加载完成时的底部提示，默认为false
    }


    @Override
    protected int getLayoutId() {
        return R.layout.frg_pull_recyclerview;
    }

    @Override
    public void onPullRefresh() {
        mPullRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList list = new ArrayList<>();
                for (int i= 0;i<20;i++){
                    TestModel testModel = new TestModel();
                    testModel.name = "test push data "+i;
                    list.add(testModel);
                }
                mvpPresenter.getComment();
                if (list != null && list.size() >0 && SystemClock.currentThreadTimeMillis()%2 ==0){
                    mAdapter.replaceAll(list);
                    mPullRecyclerView.stopRefresh();
                    mPullRecyclerView.enableLoadMore(true);
                }else {
                    mEmptyView.setVisibility(View.VISIBLE);
                    mPullRecyclerView.stopRefresh();
                    mPullRecyclerView.enableLoadMore(false);
                }
            }
        },1000);
    }

    @Override
    public void onLoadMore() {
        mPullRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList list = new ArrayList<>();
                for (int i= 0;i<20;i++){
                    TestModel testModel = new TestModel();
                    testModel.name = "test load data "+i;
                    list.add(testModel);
                }

                if (list != null && list.size() >0){
                    mAdapter.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    mPullRecyclerView.stopLoadMore();
                    mPullRecyclerView.enableLoadMore(false);
                }else {
                    mPullRecyclerView.enableLoadMore(false);
                }
            }
        },1000);
    }

    protected PullRecyclerView getPullRecyclerView() {
        return mPullRecyclerView;
    }

    public abstract BaseRecyclerAdapter getListAdapter();

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.empty_view_btn:
                mPullRecyclerView.postRefreshing();
                break;
        }
    }

    @Override
    public void onGetNewsDetailSuccess(List<TestModel> newsDetail) {

    }

    @Override
    protected HomeHotPersenter createPresenter() {
        return new HomeHotPersenter(this);
    }
}
