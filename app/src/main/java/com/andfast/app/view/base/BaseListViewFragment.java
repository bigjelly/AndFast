package com.andfast.app.view.base;

import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andfast.app.R;
import com.andfast.app.presenter.base.BasePresenter;
import com.andfast.pullrecyclerview.BaseRecyclerAdapter;
import com.andfast.pullrecyclerview.PullRecyclerView;
import com.andfast.pullrecyclerview.layoutmanager.XLinearLayoutManager;

/**
 * Created by mby on 17-8-2.
 * list的基类
 */

    public abstract class BaseListViewFragment<P extends BasePresenter> extends BaseFragment<P> implements PullRecyclerView.OnRecyclerRefreshListener, View.OnClickListener {

    private final static String TAG = "BaseListViewFragment";

    protected PullRecyclerView mPullRecyclerView;
    private XLinearLayoutManager mLayoutManager;
    protected BaseRecyclerAdapter mAdapter;
    protected View mEmptyView;
    protected AppBarLayout mAppBarLayout;
    protected Button mEmptyBtn;
    protected TextView mEmptyText;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mPullRecyclerView = findView(R.id.pull_recycler_view);
        mAppBarLayout = findView(R.id.appBarLayout);
        mToolbar = findView(R.id.toolbar);
        mToolbarTitle = findView(R.id.toolbar_title);

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
        mEmptyBtn = (Button) mEmptyView.findViewById(R.id.empty_view_btn);
        mEmptyText = (TextView) mEmptyView.findViewById(R.id.empty_view_ico_tv);
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

    protected PullRecyclerView getPullRecyclerView() {
        return mPullRecyclerView;
    }

    public abstract BaseRecyclerAdapter getListAdapter();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.empty_view_btn:
                mPullRecyclerView.postRefreshing();
                break;
        }
    }

    /**
     * 设置居中头部标题
     *
     * @param title
     */
    protected void setToolBarCenterTitle(@StringRes int title){
                if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        } else {
            setToolBarTitle(title);
        }
    }
        /**
         * 设置头部标题
         *
         * @param title
         */
    public void setToolBarTitle(@StringRes int title) {
        mToolbar.setTitle(title);
    }
}
