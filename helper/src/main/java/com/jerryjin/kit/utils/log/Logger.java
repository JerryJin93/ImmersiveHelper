package com.jerryjin.kit.utils.log;

import android.util.Log;

import com.jerryjin.kit.utils.LoggerConstants;
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

    public static void d(String clsName, String msg) {
        d(clsName, LoggerConstants.EMPTY_STRING, msg);
    }

    public static void d(String clsName, String methodName, String msg) {
        d(clsName, methodName, LoggerConstants.EMPTY_STRING, msg);
    }

    public static void d(String clsName, String methodName, String params, String msg) {
        d(clsName, methodName, params, msg, null);
    }

    public static void d(String clsName, String methodName, String params, String msg, Throwable throwable) {
        if (!LOGGABLE) {
            Log.d(SDK_NAME, LOGGER_MSG);
            return;
        }
        Log.d(SDK_NAME, inflateMsg(clsName, methodName, params, KotlinCompat.mutableString(msg).append(attachThrowable(throwable)).toString()));
    }

    public static void e(String clsName, String msg) {
        e(clsName, LoggerConstants.EMPTY_STRING, msg);
    }

    public static void e(String clsName, String methodName, String msg) {
        e(clsName, methodName, LoggerConstants.EMPTY_STRING, msg);
    }

    public static void e(String clsName, String methodName, String params, String msg) {
        e(clsName, methodName, params, msg, null);
    }

    public static void e(String clsName, String methodName, String params, String msg, Throwable throwable) {
        if (!LOGGABLE) {
            Log.e(SDK_NAME, LOGGER_MSG);
            return;
        }
        Log.e(SDK_NAME, inflateMsg(clsName, methodName, params, KotlinCompat.mutableString(msg).append(attachThrowable(throwable)).toString()));
    }

    public static void i(String clsName, String msg) {
        i(clsName, LoggerConstants.EMPTY_STRING, msg);
    }

    public static void i(String clsName, String methodName, String msg) {
        i(clsName, methodName, LoggerConstants.EMPTY_STRING, msg);
    }

    public static void i(String clsName, String methodName, String params, String msg) {
        i(clsName, methodName, params, msg, null);
    }

    public static void i(String clsName, String methodName, String params, String msg, Throwable throwable) {
        if (!LOGGABLE) {
            Log.i(SDK_NAME, LOGGER_MSG);
            return;
        }
        Log.i(SDK_NAME, inflateMsg(clsName, methodName, params, KotlinCompat.mutableString(msg).append(attachThrowable(throwable)).toString()));
    }

    public static void v(String clsName, String msg) {
        v(clsName, LoggerConstants.EMPTY_STRING, msg);
    }

    public static void v(String clsName, String methodName, String msg) {
        v(clsName, methodName, LoggerConstants.EMPTY_STRING, msg);
    }

    public static void v(String clsName, String methodName, String params, String msg) {
        v(clsName, methodName, params, msg, null);
    }

    public static void v(String clsName, String methodName, String params, String msg, Throwable throwable) {
        if (!LOGGABLE) {
            Log.v(SDK_NAME, LOGGER_MSG);
            return;
        }
        Log.v(SDK_NAME, inflateMsg(clsName, methodName, params, KotlinCompat.mutableString(msg).append(attachThrowable(throwable)).toString()));
    }

    public static void w(String clsName, String msg) {
        w(clsName, LoggerConstants.EMPTY_STRING, msg);
    }

    public static void w(String clsName, String methodName, String msg) {
        w(clsName, methodName, LoggerConstants.EMPTY_STRING, msg);
    }

    public static void w(String clsName, String methodName, String params, String msg) {
        w(clsName, methodName, params, msg, null);
    }

    public static void w(String clsName, String methodName, String params, String msg, Throwable throwable) {
        if (!LOGGABLE) {
            Log.w(SDK_NAME, LOGGER_MSG);
            return;
        }
        Log.w(SDK_NAME, inflateMsg(clsName, methodName, params, KotlinCompat.mutableString(msg).append(attachThrowable(throwable)).toString()));
    }

    private static String attachThrowable(Throwable throwable) {
        return (throwable == null ? "" : "\n") + Log.getStackTraceString(throwable);
    }

    private static String inflateMsg(String prefix, String methodName, String params, String msg) {
        return KotlinCompat.mutableString(prefix)
                .append(LoggerConstants.LOG_DELIMITER)
                .append(methodName)
                .append(LoggerConstants.LOG_BRACKET_LEFT)
                .append(params)
                .append(LoggerConstants.LOG_BRACKET_RIGHT)
                .append(LoggerConstants.PUSH)
                .append(msg)
                .toString();
    }
}
