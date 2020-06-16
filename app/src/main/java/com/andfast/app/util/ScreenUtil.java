package com.andfast.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

import com.andfast.app.AndFastApplication;

import java.lang.reflect.Method;

/**
 * 屏幕属性获取
 *
 * @author: boyuma
 * @datetime: 2020/6/16
 */
public class ScreenUtil {

    private static final String NAVIGATION_GESTURE = "navigation_gesture_on";
    private static final int NAVIGATION_GESTURE_OFF = 0;
    private static final String XIAOMI_FULLSCREEN_GESTURE = "force_fsg_nav_bar";

    public static boolean sDeviceDataInit = false;
    private static float sDisplayMetricsDensity;
    static int sDisplayMetricsWidthPixels;
    static int sDisplayMetricsHeightPixels;
    private static int realScreenHeight = 0;
    private static float sDisplayRate = 0.f;

    public static void initDeviceData() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager manager = (WindowManager) AndFastApplication
                .getContext().getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metric);
        int orientation = manager.getDefaultDisplay().getOrientation();
        if (orientation == Surface.ROTATION_90 || orientation == Surface.ROTATION_270) {
            sDisplayMetricsWidthPixels = metric.heightPixels; // 屏幕宽度（像素）
            sDisplayMetricsHeightPixels = metric.widthPixels; // 屏幕高度（像素）
        } else {
            sDisplayMetricsWidthPixels = metric.widthPixels; // 屏幕宽度（像素）
            sDisplayMetricsHeightPixels = metric.heightPixels; // 屏幕高度（像素）
        }
        sDisplayMetricsDensity = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        sDeviceDataInit = true;
    }

    public static int getEquipmentWidth() {
        if (!sDeviceDataInit) {
            initDeviceData();
        }

        return sDisplayMetricsWidthPixels;
    }

    public static int getEquipmentHeight() {
        if (!sDeviceDataInit) {
            initDeviceData();
        }

        return sDisplayMetricsHeightPixels;
    }

    public static float getEquipmentDensity() {
        if (!sDeviceDataInit) {
            initDeviceData();
        }

        return sDisplayMetricsDensity;
    }

    public static int getStatusBarHeight() {
        if (!ResourceReader.AFTER_KITKAT) {
            return 0;
        } else {
            int result = 0;
            int resourceId = AndFastApplication.getContext()
                    .getResources().getIdentifier("status_bar_height",
                            "dimen", "android");
            if (resourceId > 0) {
                result = AndFastApplication.getContext()
                        .getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }
    }

    public static DisplayMetrics getScreenSize(Activity activity) {
        DisplayMetrics result = null;
        try {
            result = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(result);
        } catch (Exception e) {
            LogUtils.e("", e.toString());
        }
        return result;
    }

    public static int getRealScreenOrientation() {
        int[] xy = getScreenDimensions();
        int orientation = AndFastApplication.getContext()
                .getResources().getConfiguration().orientation;
        if (orientation != Configuration.ORIENTATION_LANDSCAPE && xy[0] > xy[1]) {
            orientation = Configuration.ORIENTATION_LANDSCAPE;
        }
        return orientation;
    }

    public static int[] getScreenDimensions() {
        int[] dimensions = new int[2];
        Context ap = AndFastApplication.getContext();
        Display display = ((WindowManager) ap.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        dimensions[0] = display.getWidth();
        dimensions[1] = display.getHeight();
        return dimensions;
    }

    /**
     * 获取虚拟键高度(无论是否隐藏)
     */
    public static int getNavigationBarHeight() {
        int result = 0;
        int resourceId = AndFastApplication.getContext()
                .getResources().getIdentifier("navigation_bar_height",
                        "dimen", "android");
        if (resourceId > 0) {
            result = AndFastApplication.getContext()
                    .getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static boolean hasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            // 反射获取SystemProperties类，并调用它的get方法
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");

            // 判断是否隐藏了底部虚拟导航
            int navigationBarIsMin = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                navigationBarIsMin = Settings.Global.getInt(context.getContentResolver(),
                        "navigationbar_is_min", 0);
            }
            if ("1".equals(navBarOverride) || 1 == navigationBarIsMin) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }

    private static boolean isNavigationBarShow(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        Point realSize = new Point();
        display.getSize(size);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(realSize);
        }
        return realSize.y != size.y;
    }

    /**
     * 虚拟按键是否打开
     */
    public static boolean isNavigationBarShown(Activity activity) {
        switch (DeviceUtils.getDeviceType()) {
            case DeviceUtils.DEVICE_XIAOMI:
                int val = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    val = Settings.Global.getInt(activity.getContentResolver(), XIAOMI_FULLSCREEN_GESTURE, 0);
                }
                return val == 0;
            case DeviceUtils.DEVICE_HUAWEI:
                return hasNavigationBar(activity) && isNavigationBarShow(activity);
            default:
                return isNavigationBarShow(activity);
        }
    }

    public static void isNavigationBarExist(final Activity activity,
                                            final OnNavigationStateListener onNavigationStateListener) {
        if (activity == null) {
            if (onNavigationStateListener != null) {
                onNavigationStateListener.onNavigationState(false);
            }
            return;
        }
        final int height = getNavigationBarHeight();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            activity.getWindow().getDecorView().setOnApplyWindowInsetsListener(
                    new View.OnApplyWindowInsetsListener() {
                        @Override
                        public WindowInsets onApplyWindowInsets(View v, WindowInsets windowInsets) {
                            boolean isShowing = false;
                            int b = 0;
                            if (windowInsets != null) {
                                b = windowInsets.getSystemWindowInsetBottom();
                                isShowing = (b == height);
                            }
//                            v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), isShowing ? b : 0);

                            if (onNavigationStateListener != null && b <= height) {
                                onNavigationStateListener.onNavigationState(isShowing);
                            }
                            return windowInsets;
                        }
                    });
        } else {
            if (onNavigationStateListener != null) {
                onNavigationStateListener.onNavigationState(isNavigationBarShown(activity));
            }
        }
    }

    public interface OnNavigationStateListener {
        void onNavigationState(boolean isShowing);
    }

    /**
     * 不依赖Context 拿到屏幕的高度
     */
    public static int getScreenHeight() {
        return getEquipmentHeight();
    }

    /**
     * 获取屏幕原始高度，包括状态栏和虚拟按键，单位 px
     */
    public static int getRealScreenHeight() {
        if (realScreenHeight == 0) {
            int height = 0;
            try {
                WindowManager windowManager = (WindowManager) AndFastApplication.getContext()
                        .getSystemService(Context.WINDOW_SERVICE);
                Display display = windowManager.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                Class c = Class.forName("android.view.Display");
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, displayMetrics);
                height = displayMetrics.heightPixels;
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (height == 0) {
                height = getScreenHeight();
            }
            realScreenHeight = height;
        }
        return realScreenHeight;
    }

    /**
     * 不依赖Context 拿到屏幕的宽度
     */
    public static int getScreenWidth() {
        return getEquipmentWidth();
    }

    public static int dip2px(float dipValue) {
        if (!sDeviceDataInit) {
            initDeviceData();
        }
        return (int) (dipValue * sDisplayMetricsDensity + 0.5f);
    }

    public static boolean containsNotch() {
        return containsNotchInVivo();
    }

    private static final int NOTCH_IN_SCREEN_VIVO = 0x00000020; //  是否有凹槽
    private static final int ROUNDED_IN_SCREEN_VIVO = 0x00000008; //  是否有圆角

    // vivo手机是否有刘海
    private static boolean containsNotchInVivo() {
        boolean ret = false;
        try {
            ClassLoader cl = AndFastApplication.getContext().getClassLoader();
            Class ftFeature = cl.loadClass("android.util.FtFeature");
            Method get = ftFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) get.invoke(ftFeature, NOTCH_IN_SCREEN_VIVO);
        } catch (Exception e) {
            LogUtils.e("", e.toString());
        }
        return ret;
    }
}
