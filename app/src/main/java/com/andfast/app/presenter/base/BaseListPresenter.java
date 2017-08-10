package com.andfast.app.presenter.base;

import com.andfast.app.view.base.Impl.IBasePullListView;

/**
 * Created by mby on 17-8-7.
 * RecyclerView
 */

public class BaseListPresenter extends BasePresenter<IBasePullListView> {
    public BaseListPresenter(IBasePullListView view) {
        super(view);
    }
}
