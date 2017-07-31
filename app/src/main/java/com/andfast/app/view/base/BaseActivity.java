package com.andfast.app.view.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.andfast.app.util.LogUtils;

/**
 * Created by mby on 17-7-31.
 */

public class BaseActivity extends AppCompatActivity {
    private final static String TAG ="BaseActivity";

    public Context mContext = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mContext = getApplicationContext();
    }


    protected final <E extends View> E getView (@IdRes int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            LogUtils.e(TAG, "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }
}
