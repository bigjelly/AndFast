package com.andfast.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mby on 17-8-10.
 */

public class NetworkUtils {

    public static boolean isAvailable(Context context) {
        ConnectivityManager manager = null;
        try {
            if (null != context) {
                manager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
            }
        } catch (Exception e) {
        }
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected() && networkinfo.isAvailable()) {
            return true;
        }
        return false;
    }

    public static boolean isConnecting(Context mContext) {
        ConnectivityManager manager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()
                || !networkinfo.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }

    public static int getNetworkType(Context mContext) {
        ConnectivityManager manager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return -1;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isConnected() || !networkinfo.isAvailable()) {
            return -1;
        }
        return networkinfo.getType();
    }

}
