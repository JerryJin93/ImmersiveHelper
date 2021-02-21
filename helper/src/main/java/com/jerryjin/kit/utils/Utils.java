package com.jerryjin.kit.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.jerryjin.kit.utils.log.Logger;

import java.lang.reflect.Method;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 19:21
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description:
 */
public class Utils {

    private static final String TAG = "Utils";
    public static int ERR_CODE = -1;

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static int getScreenOrientation(Context context) {
        if (context == null) {
            return ERR_CODE;
        }
        return context.getResources().getConfiguration().orientation;
    }

    @SuppressLint("PrivateApi")
    public static String getStringFromSystemProperties(String key) {
        String value = "";
        Class<?> cls;
        try {
            cls = Class.forName("android.os.SystemProperties");
            Method hideMethod = cls.getMethod("get", String.class);
            Object object = cls.newInstance();
            value = (String) hideMethod.invoke(object, key);
        } catch (Exception e) {
            Logger.e(TAG, "getStringFromSystemProperties", StringHelper.format("String %s", key), StringHelper.format("Exception e: %s", e.getMessage()));
        }
        return value;
    }
}
