package com.andfast.app.net;

import com.andfast.app.model.NewsData;
import com.andfast.app.model.TestModel;
import com.andfast.app.model.Topic;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by mby on 17-8-5.
 */

public interface ApiService {

    String HOST = "https://";
    String API_SERVER_URL = HOST + "cnodejs.org/api/v1/";

    String URL_ARTICLE_FEED = "/api/article/recent/";

    String GET_ARTICLE_LIST = "api/news/feed/v54/?refer=1&count=20&loc_mode=4&device_id=34960436458";

    /**
     * 获取新闻详情
     */
    @GET
    Observable<ResultResponse<TestModel>> getNewsDetail(@Url String url);

    /**
     * 获取新闻列表
     *
     * @param category 频道
     * @return
     */
    @GET(GET_ARTICLE_LIST)
    Observable<ResultResponse<List<NewsData>>> getNewsList(@Query("category") String category, @Query("min_behot_time") long lastTime, @Query("last_refresh_sub_entrance_interval") long currentTime);

    /**
     * 获取首页文章列表
     *
     * @param pageIndex
     * @param limit
     * @param mdrender
     * @return
     */
    @GET("topics")
    Observable<ResultResponse<List<Topic>>> getTopicPage(@Query("page") Integer pageIndex, @Query("limit") Integer limit, @Query("mdrender") Boolean mdrender);
}
