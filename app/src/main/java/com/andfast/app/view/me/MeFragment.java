package com.andfast.app.view.me;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.andfast.app.R;
import com.andfast.app.presenter.video.VideoHotPersenter;
import com.andfast.app.view.base.BaseMainFragment;
import com.andfast.app.view.common.activity.WebViewActivity;
import com.andfast.app.view.widget.NWebView;

/**
 * Created by mby on 17-7-31.
 */

public class MeFragment extends BaseMainFragment<VideoHotPersenter> {

    @Override
    protected VideoHotPersenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_me;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        TextView toolbarTitle = findView(R.id.toolbar_title);
        toolbarTitle.setText(R.string.tab_name_my);

        NWebView webView = findView(R.id.webview);
        webView.loadUrl("https://github.com/bigjelly/AndFast");

        FloatingActionButton faButton = findView(R.id.fab);
        faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                startActivity(intent);
            }
        });
    }
}
