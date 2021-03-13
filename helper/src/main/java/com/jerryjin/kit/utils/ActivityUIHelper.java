package com.jerryjin.kit.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.jerryjin.kit.interfaces.INotch;
import com.jerryjin.kit.interfaces.NotchCallback;
import com.jerryjin.kit.notch.Factory;
import com.jerryjin.kit.utils.log.Logger;

import static com.jerryjin.kit.utils.log.Logger.ERR_NULL_ACTIVITY;
import static com.jerryjin.kit.utils.log.Logger.LOGGABLE;

/**
 * Author: Jerry
 * Generated at: 2020/8/1 20:33
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description:
 */
public class ActivityUIHelper {

    private static final String TAG = "ActivityHelper";

    private static final int FULL_SCREEN_UI_OPTIONS =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void immersive(Activity activity) {
        if (activity == null) {
            Logger.e(TAG, "immersive", ERR_NULL_ACTIVITY);
            return;
        }
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    public static void fullScreen(Activity activity, Factory factory) {
        final String methodName = "fullScreen";
        if (activity == null) {
            Logger.e(TAG, methodName, ERR_NULL_ACTIVITY);
            return;
        }
        final View decorView = activity.getWindow().getDecorView();
        decorView.post(() -> {
            INotch notch = factory.getNotch();
            String param = LOGGABLE ? activity.toString() : LoggerConstants.EMPTY_STRING;
            Logger.i(TAG, methodName, StringHelper.format("Activity %s", param), "Let's apply notch first.");
            notch.applyNotch(activity, true);
            // WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            // attributes.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
            // activity.getWindow().setAttributes(attributes);
            decorView.setSystemUiVisibility(FULL_SCREEN_UI_OPTIONS);
        });
    }

    public static boolean isImmersive(Activity activity, Factory factory) {
        if (activity == null) {
            Logger.e(TAG, "isImmersive", ERR_NULL_ACTIVITY);
            return false;
        }
        Window window = activity.getWindow();
        return window.getStatusBarColor() == Color.TRANSPARENT &&
                ((window.getDecorView().getSystemUiVisibility() & View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION) == View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    public static boolean isFullScreen(Activity activity) {
        if (activity == null) {
            Logger.e(TAG, "isFullScreen", ERR_NULL_ACTIVITY);
            return false;
        }
        return (activity.getWindow().getDecorView().getSystemUiVisibility() & FULL_SCREEN_UI_OPTIONS) == FULL_SCREEN_UI_OPTIONS;
    }

    public static boolean needOptimizeNavigationBar(Activity activity) {
        if (activity == null) {
            Logger.e(TAG, "needOptimizeNavigationBar", ERR_NULL_ACTIVITY);
            return false;
        }
        int previousSystemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
        return !isFullScreen(activity) &&
                ((previousSystemUiVisibility & View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION) == View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    public static void probeNotch(Activity activity, Factory factory, NotchCallback callback) {
        if (activity == null) {
            Logger.e(TAG, "probeNotch", ERR_NULL_ACTIVITY);
            return;
        }
        applyNotch(activity, factory);
        activity.getWindow().getDecorView().post(() -> {
            boolean result = factory.getNotch().hasNotch(activity);
            if (callback != null) {
                callback.onProbeReady(result);
            }
        });
    }

    public static void applyNotch(Activity activity, Factory factory) {
        if (activity == null) {
            Logger.e(TAG, "applyNotch", ERR_NULL_ACTIVITY);
            return;
        }
        factory.getNotch().applyNotch(activity, true);
    }
}
