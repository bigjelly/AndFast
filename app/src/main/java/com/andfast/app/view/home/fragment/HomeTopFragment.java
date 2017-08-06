package com.andfast.app.view.home.fragment;

import com.andfast.app.R;
import com.andfast.app.model.TestModel;
import com.andfast.app.util.LogUtils;
import com.andfast.app.view.base.BaseListFragment;
import com.andfast.app.view.common.TabManager;
import com.andfast.app.view.home.adapter.HotListAdapter;
import com.andfast.pullrecyclerview.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mby on 17-8-2.
 */

public class HomeTopFragment extends BaseListFragment implements TabManager.TabReselectListener {
    private final static String TAG = "HomeTopFragment";
    private List<TestModel> mDataList = new ArrayList<>();

    @Override
    public BaseRecyclerAdapter getListAdapter() {
        return new HotListAdapter(mContext, R.layout.lay_hometop_list_item,mDataList);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            TabManager.getInstance(getContext()).addTabReselectListener(this);
        }else {
            TabManager.getInstance(getContext()).removeTabReselectListener(this);
        }
    }

    @Override
    public void onTabReselect() {
        LogUtils.d(TAG, "onTabReselect!!!");
    }
}
