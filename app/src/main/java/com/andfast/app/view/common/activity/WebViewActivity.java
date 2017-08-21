package com.andfast.app.view.common.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.andfast.app.R;
import com.andfast.app.view.base.BaseActivity;
import com.andfast.app.view.widget.NWebView;

import static com.andfast.app.R.id.fab;

/**
 * Created by mby on 17-8-17.
 * webview封装
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    // TODO: 17-8-18  activity的缓存没有生效，后续解决这个问题
    private static final String TAG = "WebViewActivity";
    private static final String APP_CACHE_DIRNAME = "webcache"; // web缓存目录

    private NWebView mWebView;
    private Toolbar mToolbar;
    private FloatingActionButton mFaButton;
    private AppBarLayout mAppBarLayout;
    private ProgressBar mProgressBar;

    @Override
    protected int getContentView() {
        return R.layout.act_webview;
    }

    @Override
    protected void initView() {
        super.initView();
        mAppBarLayout = findView(R.id.appBarLayout);
        mToolbar = findView(R.id.toolbar);
        mWebView = findView(R.id.webview);
        mFaButton = findView(R.id.fab);
        mProgressBar = findView(R.id.pb_progress);
        mFaButton.setOnClickListener(this);

        findView(R.id.toolbar_title).setVisibility(View.GONE);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        mWebView.setOnScrollChangedCallback(new NWebView.OnScrollChangedCallback() {
//            @Override
//            public void onScroll(int dx, int dy) {
//                if (dy > 0) {
//                    mAppBarLayout.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
//                    mFaButton.animate()
//                            .translationY(mFaButton.getHeight()+mFaButton.getMeasuredHeight())
//                            .setInterpolator(new AccelerateInterpolator(2))
//                            .start();
//                }else {
//                    mAppBarLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
//                    mFaButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//                }
//            }
//        });
    }

    @Override
    protected void initData() {
        super.initData();
        mWebView.loadUrl("https://www.baidu.com");//调用loadUrl方法为WebView加入链接 https://segmentfault.com/

        mWebView.setWebChromeClient(new WebChromeClient() {
            //这里设置获取到的网站title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    // 加载中
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    //我们需要重写回退按钮的时间,当用户点击回退按钮：
    //1.webView.canGoBack()判断网页是否能后退,可以则goback()
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case fab:
                mWebView.setScrollY(0);   //滚动到顶部
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.webview_act, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mWebView.reload();    //刷新当前页面
                mProgressBar.setVisibility(View.VISIBLE);
                return true;
            case R.id.action_clear_cache:
                mWebView.clearCache(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.destroy();
        }
    }
}
