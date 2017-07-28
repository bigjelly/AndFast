
package com.andfast.app.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class StorageUtils {
    private static final String TAG = "StorageUtils";
    /**
     * 应用文件根目录，更新为缓存目录
     */
    private static String FILE_DIR_APP;
    /**
     * 应用Log保存目录
     */
    private static String FILE_DIR_APP_LOG;
    /**
     * 应用crash保存目录
     */
    private static String FILE_DIR_APP_CRASH;
    private static String FILE_DIR_APP_APK;

    public static void initExtDir(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File fileDir = context.getExternalFilesDir(null);
            if (null != fileDir) {
                FILE_DIR_APP = fileDir.getAbsolutePath() + File.separator;
            }
            File logDir = context.getExternalFilesDir("log");
            if (null != logDir) {
                LogUtils.setAutoSave(true);
                FILE_DIR_APP_LOG = logDir.getAbsolutePath() + File.separator;
            }
            File crashDir = context.getExternalFilesDir("crash");
            if (null != crashDir) {
                FILE_DIR_APP_CRASH = crashDir.getAbsolutePath() + File.separator;
            }
            File apkDir = context.getExternalFilesDir("apk");
            if (null != apkDir) {
                FILE_DIR_APP_APK = apkDir.getAbsolutePath() + File.separator;
            }
        } else {
            LogUtils.setAutoSave(false);
            LogUtils.w(TAG,"The external storage state is not MEDIA_MOUNTED.");
        }
    }

    /**
     * @param file 删除的文件或目录
     * @param saveDir 是否保存目录
     * @return true if success, else false
     */
    public static boolean delDir(File file, boolean saveDir) {
        boolean state = true;
        if (null == file || !file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File tmp : files) {
                    state &= delDir(tmp, saveDir);
                }
            }
            if (!saveDir) {
                state = file.delete();
            }
        } else {
            state = !file.exists() || file.delete();
        }

        return state;
    }

    /**
     * app 文件存储根目录
     */
    public static String getAppDir() {
        return FILE_DIR_APP;
    }

    /**
     * Log dir
     */
    public static String getLogDir() {
        return FILE_DIR_APP_LOG;
    }

    /**
     * Crash dir
     */
    public static String getCrashDir() {
        return FILE_DIR_APP_CRASH;
    }

    /**
     * 更新包存放位置
     */
    public static String getApkDir() {
        return FILE_DIR_APP_APK;
    }

    public static File getInternalFileDir(Context context) {
        if (null == context) {
            return null;
        }
        return context.getFilesDir();
    }

}
