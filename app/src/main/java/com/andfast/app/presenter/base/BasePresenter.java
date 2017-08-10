package com.andfast.app.presenter.base;

import com.andfast.app.AndFastApplication;
import com.andfast.app.constant.GeneralID;
import com.andfast.app.net.ApiRetrofit;
import com.andfast.app.net.ApiService;
import com.andfast.app.net.ResultResponse;
import com.andfast.app.util.NetworkUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mby on 17-8-5.
 */

public class BasePresenter<V> {

    protected ApiService mApiService = ApiRetrofit.getInstance().getApiService();
    protected V mView;
    private CompositeSubscription mCompositeSubscription;

    public BasePresenter(V view) {
        attachView(view);
    }

    public void attachView(V view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
        onUnsubscribe();
    }

    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        if (!NetworkUtils.isAvailable(AndFastApplication.getContext())){
            observable = Observable.create(new Observable.OnSubscribe<ResultResponse<V>>() {
                @Override
                public void call(Subscriber<? super ResultResponse<V>> subscriber) {
                    subscriber.onNext(new ResultResponse<V>("", GeneralID.TYPE_NET_UNAVAILABLE_CODE,false,"",null));
                }
            });
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
