package com.andfast.app.view.home.adapter;

import android.content.Context;

import com.andfast.app.R;
import com.andfast.app.model.Topic;
import com.andfast.pullrecyclerview.BaseRecyclerAdapter;
import com.andfast.pullrecyclerview.BaseViewHolder;

import java.util.List;

/**
 * Created by mby on 17-8-8.
 */

public class HomeListAdapter extends BaseRecyclerAdapter<Topic> {

    public HomeListAdapter(Context context, int layoutResId, List<Topic> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Topic item) {
        holder.setText(R.id.tv_title,item.title);
        holder.setText(R.id.tv_last_reply,item.last_reply_at.toString());
        holder.setText(R.id.tv_reply_count,String.valueOf(item.reply_count));
        holder.setText(R.id.tv_visit_count,String.valueOf(item.visit_count));
    }
}
