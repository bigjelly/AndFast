package com.andfast.app.util;

/**
 * 安全的类型转换
 *
 * @author: boyuma
 * @datetime: 2020/6/16
 */
public class JavaTypesHelper {
    /**
     * 把String类型转换为int型，异常则返回默认值
     */
    public static int toInt(String val, int defaultValue) {
        if (val == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(val);
        } catch (Exception e) {
            // IGNORE
        }

        return defaultValue;
    }

    /**
     * 把String类型转换为long型，异常则返回默认值
     */
    public static long toLong(String val, long defaultValue) {
        if (val == null) {
            return defaultValue;
        }

        try {
            return Long.parseLong(val);
        } catch (Exception e) {
            // IGNORE
        }

        return defaultValue;
    }

    /**
     * 把String类型转换为float型，异常则返回默认值
     */
    public static float toFloat(String val, float defaultValue) {
        if (val == null) {
            return defaultValue;
        }

        try {
            return Float.parseFloat(val);
        } catch (Exception e) {
            // IGNORE
        }

        return defaultValue;
    }

    /**
     * 把String类型转换为double型，异常则返回默认值
     */
    public static double toDouble(String val, double defaultValue) {
        if (val == null) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(val);
        } catch (Exception e) {
            // IGNORE
        }

        return defaultValue;
    }

    /**
     * 把String类型转换为boolean型，异常则返回默认值
     */
    public static boolean toBoolean(String val, boolean defaultValue) {
        if (val == null) {
            return defaultValue;
        }

        try {
            return Boolean.parseBoolean(val);
        } catch (Exception e) {
            // IGNORE
        }

        return defaultValue;
    }
}
