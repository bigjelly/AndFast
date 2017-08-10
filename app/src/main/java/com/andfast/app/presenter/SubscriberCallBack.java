package com.andfast.app.presenter;

import com.andfast.app.constant.GeneralID;
import com.andfast.app.net.ResultResponse;
import com.andfast.app.presenter.base.BaseCallBack;

/**
 * Created by mby on 17-8-6.
 */

public abstract class SubscriberCallBack<T> extends BaseCallBack<ResultResponse<T>> {
    private static final String TAG = "SubscriberCallBack";
    @Override
    public void onNext(ResultResponse<T> resultResponse) {
        if (resultResponse.success){
            onSuccess(resultResponse.data);
        }else {
            onFailure(resultResponse);
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        onFailure(new ResultResponse("", GeneralID.TYPE_NET_ERROR_CODE,false,"",e));
    }

    protected abstract void onFailure(ResultResponse response);
    protected abstract void onSuccess(T response);
}
