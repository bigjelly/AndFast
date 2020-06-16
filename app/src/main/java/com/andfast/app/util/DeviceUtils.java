package com.andfast.app.util;

import android.os.Build;
import android.text.TextUtils;

/**
 * 设备工具类，
 *
 * @author: boyuma
 * @datetime: 2020/6/16
 */
public class DeviceUtils {
    /**
     * 是否有凹槽 - Vivo
     */
    public static final int NOTCH_IN_SCREEN_VIVO = 0x00000020;
    /**
     * 是否有圆角 - Vivo
     */
    public static final int ROUNDED_IN_SCREEN_VIVO = 0x00000008;
    /**
     * 没有获取设备信息
     */
    public static final int DEVICE_NULL = 0;
    /**
     * 不关心的设备
     */
    public static final int DEVICE_UNKOWN = 1;
    /**
     * 小米
     */
    public static final int DEVICE_XIAOMI = 2;
    /**
     * 三星
     */
    public static final int DEVICE_SAMSUNG = 3;
    /**
     * 华为
     */
    public static final int DEVICE_HUAWEI = 4;
    /**
     * Voio
     */
    public static final int DEVICE_VIVO = 5;
    /**
     * Oppo
     */
    public static final int DEVICE_OPPO = 6;
    /**
     * Meizu
     */
    public static final int DEVICE_MEIZU = 7;

    /**
     * 当前设备厂商
     */
    private static int sDeviceType = DEVICE_NULL;

    public static int getDeviceType() {
        if (sDeviceType == DEVICE_NULL) {
            String model = Build.MODEL;
            String carrier = Build.MANUFACTURER;
            String brand = Build.BRAND;

            if (model != null) {
                model = model.toLowerCase();
            }

            if (carrier != null) {
                carrier = carrier.toLowerCase();
            }

            if (brand != null) {
                brand = brand.toLowerCase();
            }

            if (TextUtils.isEmpty(model) || TextUtils.isEmpty(carrier) || TextUtils.isEmpty(brand)) {
                sDeviceType = DEVICE_UNKOWN;
            } else if (model.contains("xiaomi") || carrier.contains("xiaomi") || brand.contains("xiaomi")) {
                sDeviceType = DEVICE_XIAOMI;
            } else if (model.contains("samsung") || carrier.contains("samsung") || brand.contains("samsung")) {
                sDeviceType = DEVICE_SAMSUNG;
            } else if (model.contains("huawei") || carrier.contains("huawei") || brand.contains("huawei")) {
                sDeviceType = DEVICE_HUAWEI;
            } else if (model.contains("vivo") || carrier.contains("vivo") || brand.contains("vivo")) {
                sDeviceType = DEVICE_VIVO;
            } else if (model.contains("oppo") || carrier.contains("oppo") || brand.contains("oppo")) {
                sDeviceType = DEVICE_OPPO;
            } else if (model.contains("meizu") || carrier.contains("meizu") || brand.contains("meizu")) {
                sDeviceType = DEVICE_MEIZU;
            } else {
                sDeviceType = DEVICE_UNKOWN;
            }
        }
        return sDeviceType;
    }
}
