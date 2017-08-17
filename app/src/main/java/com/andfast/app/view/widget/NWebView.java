package com.andfast.app.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by mby on 17-8-17.
 */

public class NWebView extends WebView{

    private OnScrollChangedCallback mOnScrollChangedCallback;
    private WebSettings mWebSettings;

    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录

    public NWebView(Context context) {
        super(context);
        init();
    }

    public NWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public NWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        init();
    }

    private void init() {
        setClickable(false);
        setFocusable(false);

        mWebSettings = getSettings();

        mWebSettings.setJavaScriptEnabled(true); //设置WebView属性,运行执行js脚本
        mWebSettings.setUseWideViewPort(true);//设定支持viewport
        mWebSettings.setLoadWithOverviewMode(true);   //自适应屏幕
        mWebSettings.setBuiltInZoomControls(true); //支持缩放
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setSupportZoom(true);//设定支持缩放
        mWebSettings.setAllowFileAccess(true);//可访问文件
        mWebSettings.setDefaultTextEncodingName("UTF-8");
        mWebSettings.setDefaultFontSize(14);

        setWebViewClient(new WebViewClient() {
            //设置在webView点击打开的新网页在当前界面显示,而不跳转到新的浏览器中
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }


    public void openCache(String cacheDirPath){
        //设置缓存模式
        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        mWebSettings.setDomStorageEnabled(true);
        // 开启database storage API功能
        mWebSettings.setDatabaseEnabled(true);
        // 设置数据库缓存路径
        mWebSettings.setAppCachePath(cacheDirPath);
        mWebSettings.setAppCacheEnabled(true);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt);
        }
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    public interface OnScrollChangedCallback {
        //这里的dx和dy代表的是x轴和y轴上的偏移量，你也可以自己把l, t, oldl, oldt四个参数暴露出来
        void onScroll(int dx, int dy);
    }

    @Override
    public void destroy() {
        setWebViewClient(null);

        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(false);

        removeAllViewsInLayout();

        removeAllViews();
        super.destroy();
    }
}
