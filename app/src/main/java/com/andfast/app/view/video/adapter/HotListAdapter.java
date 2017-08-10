package com.andfast.app.view.video.adapter;

import android.content.Context;

import com.andfast.app.model.TestModel;
import com.andfast.pullrecyclerview.BaseRecyclerAdapter;
import com.andfast.pullrecyclerview.BaseViewHolder;

import java.util.List;

/**
 * Created by mby on 17-8-2.
 */

public class HotListAdapter extends BaseRecyclerAdapter<TestModel> {
    public HotListAdapter(Context context, int layoutResId, List<TestModel> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, TestModel item) {
        holder.setText(android.R.id.text1, item.name);
    }
}
