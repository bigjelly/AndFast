package com.andfast.app.presenter.video;

import com.andfast.app.model.NewsData;
import com.andfast.app.net.ResultResponse;
import com.andfast.app.presenter.SubscriberCallBack;
import com.andfast.app.presenter.base.BaseListPresenter;
import com.andfast.app.util.LogUtils;
import com.andfast.app.view.video.Impl.IVideoTopView;

import java.util.List;

/**
 * Created by mby on 17-8-5.
 */

public class VideoTopPersenter extends BaseListPresenter {

    private static final String TAG = "VideoTopPersenter";

    public VideoTopPersenter(IVideoTopView view) {
        super(view);
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
