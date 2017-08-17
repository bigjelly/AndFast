package com.andfast.app.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.andfast.app.util.LogUtils;

/**
 * Created by mby on 17-7-31.
 */

public abstract class BaseActivity extends AppCompatActivity{
    private final static String TAG = "BaseActivity";

    public Context mContext = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(getContentView());
        initView();
        initData();
    }

    protected void initView() {
    }

    protected void initData() {
    }


    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    protected boolean isShowBacking() {
        return true;
    }

    @LayoutRes
    protected abstract int getContentView();

    /**
     * 界面跳转
     *
     * @param tarActivity
     */
    protected void intent2Activity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(mContext, tarActivity);
        startActivity(intent);
    }

    protected final <E extends View> E findView(@IdRes int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            LogUtils.e(TAG, "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }
}
