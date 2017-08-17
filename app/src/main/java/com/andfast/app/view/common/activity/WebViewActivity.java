package com.andfast.app.view.common.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.andfast.app.R;
import com.andfast.app.view.base.BaseActivity;
import com.andfast.app.view.widget.NWebView;

import static com.andfast.app.R.id.fab;

/**
 * Created by mby on 17-8-17.
 * webview封装
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG ="WebViewActivity";
    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录

    private NWebView mWebView;
    private Toolbar mToolbar;
    private FloatingActionButton mFaButton;
    private AppBarLayout mAppBarLayout;

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


        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true); //设置WebView属性,运行执行js脚本
        settings.setUseWideViewPort(true);//设定支持viewport
        settings.setLoadWithOverviewMode(true);   //自适应屏幕
        settings.setBuiltInZoomControls(true); //支持缩放
        settings.setDisplayZoomControls(false);
        settings.setSupportZoom(true);//设定支持缩放
        settings.setAllowFileAccess(true);//可访问文件
        settings.setDefaultTextEncodingName("UTF-8");

        //设置缓存模式
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        settings.setDomStorageEnabled(true);
        // 开启database storage API功能
        settings.setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACHE_DIRNAME;
        Log.i("cachePath", cacheDirPath);
        // 设置数据库缓存路径
        settings.setAppCachePath(cacheDirPath);
        settings.setAppCacheEnabled(true);
        Log.i("databasepath", settings.getDatabasePath());

        mWebView.setOnScrollChangedCallback(new NWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int dx, int dy) {
                if (dy > 0) {
                    mAppBarLayout.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
                    mFaButton.animate()
                            .translationY(mFaButton.getHeight()+mFaButton.getMeasuredHeight())
                            .setInterpolator(new AccelerateInterpolator(2))
                            .start();
                }else {
                    mAppBarLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
                    mFaButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                }
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient());

    }

    @Override
    protected void initData() {
        super.initData();


        mWebView.loadUrl("https://segmentfault.com/q/1010000002590371");//调用loadUrl方法为WebView加入链接

        mWebView.setWebChromeClient(new WebChromeClient() {
            //这里设置获取到的网站title
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getSupportActionBar().setTitle(title);
            }
        });
    }

    //我们需要重写回退按钮的时间,当用户点击回退按钮：
    //1.webView.canGoBack()判断网页是否能后退,可以则goback()
    //2.如果不可以连续点击两次退出App,否则弹出提示Toast
    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
        mWebView = null;
    }
}
