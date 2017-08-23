package com.andfast.app;

import android.app.Application;
import android.content.Context;

import com.andfast.app.util.LogUtils;
import com.andfast.app.util.StorageUtils;

/**
 * Created by mby on 17-7-28.
 */

public class AndFastApplication extends Application {

    private final static String TAG = "AndFastApplication";
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        StorageUtils.initExtDir(getApplicationContext());
        initLog();
        CrashHandler.getInstance().init(getApplicationContext());
        LogUtils.i(TAG,"<><><><><><><><><>");
        LogUtils.i(TAG," app is start!");
        LogUtils.i(TAG,"<><><><><><><><><>");
    }

    private void initLog() {
        LogUtils.setRootTag("andfast");
        LogUtils.setLogPath(StorageUtils.getLogDir());
        LogUtils.setSaveRuntimeInfo(true);
        LogUtils.setAutoSave(true);
        LogUtils.setDebug(BuildConfig.LOG_DEBUG);
    }

    public static Context getContext(){
        return mContext;
    }
}
