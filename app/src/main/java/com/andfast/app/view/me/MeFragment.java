package com.andfast.app.view.me;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.andfast.app.R;
import com.andfast.app.presenter.video.VideoHotPersenter;
import com.andfast.app.util.LogUtils;
import com.andfast.app.view.base.BaseMainFragment;
import com.andfast.app.view.common.activity.WebViewActivity;
import com.andfast.app.view.widget.NWebView;

/**
 * Created by mby on 17-7-31.
 */

public class MeFragment extends BaseMainFragment<VideoHotPersenter> {

    private static final String TAG = "MeFragment";

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

        final NWebView webView = findView(R.id.webview);
        webView.loadUrl("https://github.com/bigjelly/AndFast");

        webView.setWebViewClient(new WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                LogUtils.i(TAG,"keyCode:"+keyCode);
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
                return false;
            }
        });

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
