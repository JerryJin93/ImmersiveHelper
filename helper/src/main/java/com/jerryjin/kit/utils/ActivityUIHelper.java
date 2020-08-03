package com.jerryjin.kit.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.jerryjin.kit.interfaces.INotch;
import com.jerryjin.kit.notch.NotchFactory;
import com.jerryjin.kit.utils.log.Logger;

import static com.jerryjin.kit.utils.log.Logger.ERR_NULL_ACTIVITY;
import static com.jerryjin.kit.utils.log.Logger.LOGGABLE;
import static com.jerryjin.kit.utils.log.Logger.w;

/**
 * Author: Jerry
 * Generated at: 2020/8/1 20:33
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
public class ActivityUIHelper {

    private static final String TAG = "ActivityHelper";

    private static final int FULL_SCREEN_UI_OPTIONS =
            View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
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

    public static void undoOptimizationForImmersive(Activity activity, int previousStatusBarColor, int previousNavigationBarColor) {
        if (activity == null) {
            Logger.e(TAG, "recoverImmersive", ERR_NULL_ACTIVITY);
            return;
        }
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.setStatusBarColor(previousStatusBarColor);
        window.setNavigationBarColor(previousNavigationBarColor);
        ViewHelper.clearUiOption(activity, View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    public static void fullScreen(Activity activity) {
        final String methodName = "fullScreen";
        if (activity == null) {
            Logger.e(TAG, methodName, ERR_NULL_ACTIVITY);
            return;
        }
        final View decorView = activity.getWindow().getDecorView();
        decorView.post(() -> {
            INotch notch = NotchFactory.getNotch();
            if (notch.hasNotch(activity)) {
                String param = LOGGABLE ? activity.toString() : Constants.EMPTY_STRING;
                Logger.i(TAG, methodName, Utils.format("Activity %s", param), "Let's apply notch first.");
                notch.applyNotch(activity, true);
            }
            decorView.setSystemUiVisibility(FULL_SCREEN_UI_OPTIONS);
        });
    }

    public static void undoOptimizationForFullScreen(Activity activity) {
        final String methodName = "recoverFullScreen";
        if (activity == null) {
            Logger.e(TAG, methodName, ERR_NULL_ACTIVITY);
            return;
        }
        ViewHelper.clearUiOption(activity, FULL_SCREEN_UI_OPTIONS);
    }

    public static boolean isImmersive(Activity activity) {
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
}
