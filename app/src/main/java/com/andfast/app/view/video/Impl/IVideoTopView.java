package com.andfast.app.view.video.Impl;

import com.andfast.app.model.TestModel;
import com.andfast.app.view.base.Impl.IBasePullListView;

import java.util.List;

/**
 * Created by mby on 17-8-6.
 */

public interface IVideoTopView extends IBasePullListView {
    void onGetNewsDetailSuccess(List<TestModel> newsDetail);
}
