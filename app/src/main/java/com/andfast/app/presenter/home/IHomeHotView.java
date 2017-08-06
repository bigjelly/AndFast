package com.andfast.app.presenter.home;

import com.andfast.app.model.TestModel;
import com.andfast.app.presenter.base.IBaseDetailView;

import java.util.List;

/**
 * Created by mby on 17-8-6.
 */

public interface IHomeHotView extends IBaseDetailView<TestModel> {
    void onGetNewsDetailSuccess(List<TestModel> newsDetail);
}
