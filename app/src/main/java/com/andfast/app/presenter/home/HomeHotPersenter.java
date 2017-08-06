package com.andfast.app.presenter.home;

import com.andfast.app.model.TestModel;
import com.andfast.app.presenter.base.BasePresenter;
import com.andfast.app.net.ResultResponse;
import com.andfast.app.presenter.SubscriberCallBack;

import java.util.List;

/**
 * Created by mby on 17-8-5.
 */

public class HomeHotPersenter extends BasePresenter<IHomeHotView>{
    public HomeHotPersenter(IHomeHotView view) {
        super(view);
    }
    public void getComment() {
        addSubscription(mApiService.getNewsDetail(""), new SubscriberCallBack<ResultResponse<List<TestModel>>>(){
            @Override
            protected void onFailure(ResultResponse response) {

            }

            @Override
            protected void onSuccess(ResultResponse<List<TestModel>> response) {
                List<TestModel> data = response.data;
                mView.onGetNewsDetailSuccess(data);
            }
        });
    }
}
