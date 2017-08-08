package com.andfast.app.presenter.home;

import com.andfast.app.constant.GeneralID;
import com.andfast.app.model.Topic;
import com.andfast.app.net.ResultResponse;
import com.andfast.app.presenter.SubscriberCallBack;
import com.andfast.app.presenter.base.BasePresenter;
import com.andfast.app.view.home.Impl.IHomeListView;

import java.util.List;

/**
 * Created by mby on 17-8-8.
 */

public class HomePresenter extends BasePresenter<IHomeListView> {
    public HomePresenter(IHomeListView view) {
        super(view);
    }

    public void getPullRefresh(){
        addSubscription(mApiService.getTopicPage(1,20,true), new SubscriberCallBack<List<Topic>>() {
            @Override
            protected void onFailure(ResultResponse response) {
                mView.onError(GeneralID.TPYE_PULL_REFRESH,response);
            }

            @Override
            protected void onSuccess(List<Topic> response) {
                mView.onRefreshDetailSuccess(response);
            }
        });
    }

    public void getLoadMore(){
        addSubscription(mApiService.getTopicPage(1,20,true),new SubscriberCallBack<List<Topic>>() {
            @Override
            protected void onFailure(ResultResponse response) {
                mView.onError(GeneralID.TPYE_LOAD_MORE,response);
            }

            @Override
            protected void onSuccess(List<Topic> response) {
                mView.onLoadMoreSuccess(response);
            }
        });
    }
}
