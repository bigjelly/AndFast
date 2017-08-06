package com.andfast.app.presenter;

import com.andfast.app.net.ResultResponse;
import com.andfast.app.presenter.base.BaseCallBack;

import java.net.SocketTimeoutException;

/**
 * Created by mby on 17-8-6.
 */

public abstract class SubscriberCallBack<T> extends BaseCallBack<ResultResponse<T>> {
    @Override
    public void onNext(ResultResponse<T> resultResponse) {
        if (resultResponse.error_code == 10000){
            onSuccess(resultResponse.data);
        }else {
            onFailure(resultResponse);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (e instanceof SocketTimeoutException){

        }
        onFailure(new ResultResponse("",1004,"",e));
    }

    protected abstract void onFailure(ResultResponse response);
    protected abstract void onSuccess(T response);
}
