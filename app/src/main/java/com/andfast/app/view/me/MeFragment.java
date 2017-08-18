package com.andfast.app.view.me;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.andfast.app.R;
import com.andfast.app.util.LogUtils;
import com.andfast.app.util.StorageUtils;
import com.andfast.app.view.base.BaseFragment;
import com.andfast.app.view.common.activity.WebViewActivity;
import com.andfast.app.view.widget.NWebView;

/**
 * Created by mby on 17-7-31.
 */

public class MeFragment extends BaseFragment {

    private static final String TAG = "MeFragment";
    private NWebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.frg_me;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        TextView toolbarTitle = findView(R.id.toolbar_title);
        toolbarTitle.setText(R.string.tab_name_my);

        mWebView = findView(R.id.webview);
        mWebView.loadUrl("https://github.com/bigjelly/AndFast");

        mWebView.openCache(StorageUtils.getCacheDir()+"webcache");

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                LogUtils.i(TAG,"keyCode:"+keyCode);
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    mWebView.goBack();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.destroy();
        }
    }
}
