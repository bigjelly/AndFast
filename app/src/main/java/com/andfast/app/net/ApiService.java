package com.andfast.app.net;

import com.andfast.app.model.TestModel;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by mby on 17-8-5.
 */

public interface ApiService {

    /**
     * 获取新闻详情
     */
    @GET
    Observable<ResultResponse<TestModel>> getNewsDetail(@Url String url);
}
