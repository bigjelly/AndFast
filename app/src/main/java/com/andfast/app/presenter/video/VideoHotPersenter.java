package com.andfast.app.presenter.video;

import com.andfast.app.model.NewsData;
import com.andfast.app.model.TestModel;
import com.andfast.app.net.ResultResponse;
import com.andfast.app.presenter.SubscriberCallBack;
import com.andfast.app.presenter.base.BaseListPresenter;
import com.andfast.app.util.LogUtils;
import com.andfast.app.view.video.Impl.IVideoHotView;

import java.util.List;

/**
 * Created by mby on 17-8-5.
 */

public class VideoHotPersenter extends BaseListPresenter {

    private static final String TAG = "VideoHotPersenter";

    public VideoHotPersenter(IVideoHotView view) {
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
//                mView.onGetNewsDetailSuccess(data);
            }
        });
    }
    public void getNewsList(String channelCode){
        addSubscription(mApiService.getNewsList(channelCode, System.currentTimeMillis(), System.currentTimeMillis() / 1000), new SubscriberCallBack<List<NewsData>>() {
            @Override
            protected void onFailure(ResultResponse response) {
                LogUtils.d(TAG,"response");
            }

            @Override
            protected void onSuccess(List<NewsData> response) {
                LogUtils.d(TAG,"onSuccess response is "+response.size());
            }
        });
    }
}
