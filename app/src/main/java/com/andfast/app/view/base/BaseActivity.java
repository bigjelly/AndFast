package com.andfast.app.view.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.andfast.app.R;
import com.andfast.app.util.LogUtils;

/**
 * Created by mby on 17-7-31.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private final static String TAG = "BaseActivity";

    public Context mContext = null;

    private TextView mToolbarTitle;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(getContentView());
        mToolbar = getView(R.id.toolbar);
        mToolbarTitle = getView(R.id.toolbar_title);
        if (mToolbar != null) {
            //将Toolbar显示到界面
            setSupportActionBar(mToolbar);
        }
        if (mToolbarTitle != null) {
            //getTitle()的值是activity的android:lable属性值
            mToolbarTitle.setText(getTitle());
            //设置默认的标题不显示
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        initData();
        initView();
    }

    protected void initView() {
    }

    protected void initData() {
    }

    /**
     * 获取头部标题的TextView
     *
     * @return
     */
    public TextView getToolbarTitle() {
        return mToolbarTitle;
    }

    /**
     * 设置居中头部标题
     *
     * @param title
     */
    public void setToolBarCenterTitle(@StringRes int title) {
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        } else {
            setToolBarTitle(title);
        }
    }

    /**
     * 设置头部标题
     *
     * @param title
     */
    public void setToolBarTitle(@StringRes int title) {
        getToolbar().setTitle(title);
        setSupportActionBar(getToolbar());
    }

    /**
     * this Activity of tool bar.
     * 获取头部.
     *
     * @return support.v7.widget.Toolbar.
     */
    public Toolbar getToolbar() {
        return mToolbar;
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

    protected final <E extends View> E getView(@IdRes int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            LogUtils.e(TAG, "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }
}
