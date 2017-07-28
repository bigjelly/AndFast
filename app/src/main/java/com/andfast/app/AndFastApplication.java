package com.andfast.app;

import android.app.Application;

import com.andfast.app.util.LogUtils;
import com.andfast.app.util.StorageUtils;

/**
 * Created by mby on 17-7-28.
 */

public class AndFastApplication extends Application {
    private final static String TAG = "AndFastApplication";

    @Override
    public void onCreate() {
        super.onCreate();
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
        LogUtils.setDebug(true);
    }
}
