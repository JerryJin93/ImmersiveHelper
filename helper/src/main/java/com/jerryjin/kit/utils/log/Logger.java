package com.jerryjin.kit.utils.log;

import android.util.Log;

import com.jerryjin.kit.utils.Constants;
import com.jerryjin.kit.utils.KotlinCompat;

/**
 * Author: Jerry
 * Generated at: 2020/8/1 20:42
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description: For debug use only.
 */
public final class Logger {

    private static final String SDK_NAME = "ImmersiveHelper";
    private static final String LOGGER_MSG = "The log switch is off right now.";

    public static boolean LOGGABLE;

    public static final String ERR_NULL_ACTIVITY = "Null given activity.";

    private Logger() {
    }

    public static void setLoggable(boolean openLog) {
        LOGGABLE = openLog;
    }

    public static void d(String tag, String msg) {
        d(tag, Constants.EMPTY_STRING, msg);
    }

    public static void d(String tag, String methodName, String msg) {
        d(tag, methodName, Constants.EMPTY_STRING, msg);
    }

    public static void d(String tag, String methodName, String params, String msg) {
        d(tag, methodName, params, msg, null);
    }

    public static void d(String tag, String methodName, String params, String msg, Throwable throwable) {
        if (!LOGGABLE) {
            Log.d(SDK_NAME, LOGGER_MSG);
            return;
        }
        Log.d(tag, inflateMsg(tag, methodName, params, KotlinCompat.mutableString(msg).append(attachThrowable(throwable)).toString()));
    }

    public static void e(String tag, String msg) {
        e(tag, Constants.EMPTY_STRING, msg);
    }

    public static void e(String tag, String methodName, String msg) {
        e(tag, methodName, Constants.EMPTY_STRING, msg);
    }

    public static void e(String tag, String methodName, String params, String msg) {
        e(tag, methodName, params, msg, null);
    }

    public static void e(String tag, String methodName, String params, String msg, Throwable throwable) {
        if (!LOGGABLE) {
            Log.e(SDK_NAME, LOGGER_MSG);
            return;
        }
        Log.e(tag, inflateMsg(tag, methodName, params, KotlinCompat.mutableString(msg).append(attachThrowable(throwable)).toString()));
    }

    public static void i(String tag, String msg) {
        i(tag, Constants.EMPTY_STRING, msg);
    }

    public static void i(String tag, String methodName, String msg) {
        i(tag, methodName, Constants.EMPTY_STRING, msg);
    }

    public static void i(String tag, String methodName, String params, String msg) {
        i(tag, methodName, params, msg, null);
    }

    public static void i(String tag, String methodName, String params, String msg, Throwable throwable) {
        if (!LOGGABLE) {
            Log.i(SDK_NAME, LOGGER_MSG);
            return;
        }
        Log.i(tag, inflateMsg(tag, methodName, params, KotlinCompat.mutableString(msg).append(attachThrowable(throwable)).toString()));
    }

    public static void v(String tag, String msg) {
        v(tag, Constants.EMPTY_STRING, msg);
    }

    public static void v(String tag, String methodName, String msg) {
        v(tag, methodName, Constants.EMPTY_STRING, msg);
    }

    public static void v(String tag, String methodName, String params, String msg) {
        v(tag, methodName, params, msg, null);
    }

    public static void v(String tag, String methodName, String params, String msg, Throwable throwable) {
        if (!LOGGABLE) {
            Log.v(SDK_NAME, LOGGER_MSG);
            return;
        }
        Log.v(tag, inflateMsg(tag, methodName, params, KotlinCompat.mutableString(msg).append(attachThrowable(throwable)).toString()));
    }

    public static void w(String tag, String msg) {
        w(tag, Constants.EMPTY_STRING, msg);
    }

    public static void w(String tag, String methodName, String msg) {
        w(tag, methodName, Constants.EMPTY_STRING, msg);
    }

    public static void w(String tag, String methodName, String params, String msg) {
        w(tag, methodName, params, msg, null);
    }

    public static void w(String tag, String methodName, String params, String msg, Throwable throwable) {
        if (!LOGGABLE) {
            Log.w(SDK_NAME, LOGGER_MSG);
            return;
        }
        Log.w(tag, inflateMsg(tag, methodName, params, KotlinCompat.mutableString(msg).append(attachThrowable(throwable)).toString()));
    }

    private static String attachThrowable(Throwable throwable) {
        return (throwable == null ? "" : "\n") + Log.getStackTraceString(throwable);
    }

    private static String inflateMsg(String prefix, String methodName, String params, String msg) {
        return KotlinCompat.mutableString(prefix)
                .append(Constants.LOG_DELIMITER)
                .append(methodName)
                .append(Constants.LOG_BRACKET_LEFT)
                .append(params)
                .append(Constants.LOG_BRACKET_RIGHT)
                .append(Constants.PUSH)
                .append(msg)
                .toString();
    }
}
