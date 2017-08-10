package com.andfast.app.view.video.adapter;

import android.content.Context;

import com.andfast.pullrecyclerview.BaseRecyclerAdapter;
import com.andfast.pullrecyclerview.BaseViewHolder;

import java.util.List;

/**
 * Created by mby on 17-8-2.
 */

public class TopListAdapter extends BaseRecyclerAdapter<String> {
    public TopListAdapter(Context context, int layoutResId, List<String> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(android.R.id.text1, item);
    }
}
