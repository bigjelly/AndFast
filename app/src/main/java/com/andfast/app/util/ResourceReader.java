package com.andfast.app.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.andfast.app.AndFastApplication;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件获取
 *
 * @author: boyuma
 * @datetime: 2020/6/16
 */
public class ResourceReader {

    public static String getString(int id) {
        return AndFastApplication.getContext().getResources().getString(id);
    }

    public static String getString(int resId, Object... formatArgs) {
        return AndFastApplication.getContext().getResources().getString(resId, formatArgs);
    }

    public static int getDimensionPixelOffset(int id) {
        return AndFastApplication.getContext().getResources().getDimensionPixelOffset(id);
    }

    public static int getDimensionPixelSize(int id) {
        return AndFastApplication.getContext().getResources().getDimensionPixelSize(id);
    }

    public static float getDimension(int id) {
        return AndFastApplication.getContext().getResources().getDimension(id);
    }

    public static Drawable getDrawable(int id) {
        return AndFastApplication.getContext().getResources().getDrawable(id);
    }

    public static Drawable getDrawable(Context context, @DrawableRes int resId) {
        if (context == null) {
            context = AndFastApplication.getContext();
        }
        if (AFTER_LOLLIPOP) {
            return context.getDrawable(resId);
        } else {
            return context.getResources().getDrawable(resId);
        }
    }

    public static int getColor(int id) {
        return AndFastApplication.getContext().getResources().getColor(id);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static int getColor(@ColorRes int id, Context context) {
        if (context == null) {
            context = AndFastApplication.getContext();
        }
        if (AFTER_M) {
            return context.getResources().getColor(id, context.getTheme());
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static ColorStateList getColorStateList(int id) {
        return AndFastApplication.getContext().getResources().getColorStateList(id);
    }

    public static InputStream getAssets(String fileName) throws IOException {
        return AndFastApplication.getContext().getResources().getAssets().open(fileName);
    }

    public static String[] getStringArray(int id) {
        return AndFastApplication.getContext().getResources().getStringArray(id);
    }

    public static int[] getIntArray(int id) {
        return AndFastApplication.getContext().getResources().getIntArray(id);
    }

    public static TypedArray getTypedArray(int id) {
        return AndFastApplication.getContext().getResources().obtainTypedArray(id);
    }

    public static int getInteger(int id) {
        return AndFastApplication.getContext().getResources().getInteger(id);
    }

    //  api 14
    public static boolean AFTER_ICE_CREAM = afterIceCream();

    //  api 14
    private static boolean afterIceCream() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    //  api 16
    public static boolean AFTER_JELLY_BEAN = afterJellybean();

    //  api 16
    private static boolean afterJellybean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    //  api 17
    public static boolean AFTER_JELLY_BEAN_MR2 = afterJellybeanMR2();

    //  api 17
    private static boolean afterJellybeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    //  api 18
    public static boolean AFTER_JELLY_BEAN_MR1 = afterJellybeanMR1();

    //  api 18
    private static boolean afterJellybeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    //  api 19
    public static final boolean AFTER_KITKAT = afterKitkat();

    //  api 19
    private static boolean afterKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    //  api 21
    public static final boolean AFTER_LOLLIPOP = afterLollipop();

    //  api 21
    private static boolean afterLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    //  api 23
    public static final boolean AFTER_M = afterM();

    //  api 23
    private static boolean afterM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (view == null) {
            return;
        }
        if (AFTER_JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}
