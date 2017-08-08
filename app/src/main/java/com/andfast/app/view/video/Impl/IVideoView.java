package com.andfast.app.view.video.Impl;

import com.andfast.app.model.TestModel;
import com.andfast.app.view.base.Impl.IBaseDetailView;

import java.util.List;

/**
 * Created by mby on 17-8-6.
 */

public interface IVideoView extends IBaseDetailView {
    void onVideoDetailSuccess(List<TestModel> newsDetail);
}
