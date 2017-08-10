package com.andfast.app.presenter.base;

import rx.Subscriber;

/**
 * Created by mby on 17-8-6.
 */

public abstract class BaseCallBack<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }
}
