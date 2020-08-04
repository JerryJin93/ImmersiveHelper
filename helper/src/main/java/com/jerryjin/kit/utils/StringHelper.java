package com.jerryjin.kit.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.jerryjin.kit.utils.log.Logger;
import static com.jerryjin.kit.utils.log.Logger.LOGGABLE;

import java.util.Locale;

/**
 * Author: Jerry
 * Generated at: 2020/8/3 21:38
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
public final class StringHelper {

    private static final String TAG = "StringHelper";
    private static final String UNKNOWN = "unknown";

    public static String format(String pattern, Object... args) {
        if (pattern == null) {
            Logger.e(TAG, "format", "String pattern, Object... args");
            return LoggerConstants.EMPTY_STRING;
        }
        return String.format(Locale.getDefault(), pattern, args);
    }

    public static String getActivityAsParamForLogger(Activity activity) {
        return LOGGABLE && activity != null ? format(LoggerConstants.PATTERN_PARAM_ACTIVITY, activity.getClass().getSimpleName())
                : LoggerConstants.LOG_ACTIVITY;
    }

    public static String getNotchStatusMsgForLogger(String brand, String model, boolean status) {
        return format("Notch on current %s device %s has been %s.",
                TextUtils.isEmpty(brand) ? UNKNOWN : brand, TextUtils.isEmpty(model) ? UNKNOWN : model, status ? "enabled" : "disabled");
    }

    public static String getNotchHardwareSupportedStatusMsgForLogger(String brand, String model, boolean status) {
        return format("Notch on current %s device %s is %s.",
                TextUtils.isEmpty(brand) ? UNKNOWN : brand, TextUtils.isEmpty(model) ? UNKNOWN : model, status ? "supported" : "not supported");
    }

    public static String getExceptionMsgForLogger(Exception e) {
        return e != null ? format("%s e: %s.", e.getClass().getSimpleName(), e.getMessage())
                : LoggerConstants.EMPTY_STRING;
    }
}
