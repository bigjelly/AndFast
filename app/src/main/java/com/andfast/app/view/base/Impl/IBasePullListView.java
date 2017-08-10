package com.andfast.app.view.base.Impl;

/**
 * Created by mby on 17-8-7.
 * RecyclerView刷新的基类
 */

public interface IBasePullListView<E> extends IBaseDetailView{
    void onRefreshDetailSuccess(E refreshDetail);

    void onLoadMoreSuccess(E loadMore);
}
