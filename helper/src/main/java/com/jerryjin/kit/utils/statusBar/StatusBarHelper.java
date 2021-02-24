package com.jerryjin.kit.utils.statusBar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Author: Jerry
 * Generated at: 2019/3/13 9:58
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class StatusBarHelper {

    /**
     * Error code for Navigation Bar related method.
     */
    public static final int ERR_CODE = -1;
    /**
     * The margin top of the top edge of the decor view by default, which equals to status bar height.
     */
    public static final int DEFAULT_NOTCH_MARGIN_TOP = 44;
    public static final int DEFAULT_OFFSET = 20;
    private static final String TAG = StatusBarHelper.class.getSimpleName();

    @SuppressLint("ObsoleteSdkInt")
    public static void setStatusBarBackgroundColor(Activity activity, int backgroundColor) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(backgroundColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatusBar(activity); // ?
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(backgroundColor);
            fitViews(activity);
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void setTranslucentStatusBar(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Log.e(TAG, "OS version under KITKAT doesn't support transparent status bar.");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
            // 沉浸式效果
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    // 需要判断，在Android M版本以下是无法改变状态栏字体的颜色的，这时候只能调节状态栏颜色或顶部导航栏颜色以表现出沉浸状态栏的效果*

    /**
     * Toggle the text color in status bar.
     *
     * @param activity The given Activity.
     * @param darkMode True if the text color is dark, false otherwise.
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void toggleStatusBarTextColor(Activity activity, boolean darkMode) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = activity.getWindow().getDecorView();
            int systemUiVisibility = decor.getSystemUiVisibility();
            decor.setSystemUiVisibility(darkMode ? systemUiVisibility | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : systemUiVisibility & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    private static void fitViews(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return;
        }
        ViewGroup parent = activity.findViewById(android.R.id.content);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                child.setFitsSystemWindows(true);
                ((ViewGroup) child).setClipToPadding(true);
            }
        }
    }

    /**
     * Toggle the text color in status bar, exclusively on MIUI 6.
     *
     * @param activity The given Activity.
     * @param darkMode True if the text color is dark, false otherwise.
     */
    public static void toggleStatusBarTextColorForMIUI(Activity activity, boolean darkMode) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return;
        }
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            @SuppressLint("PrivateApi")
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkMode ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getStatusBarHeight(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return ERR_CODE;
        }
        int result = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        Log.i(TAG, "status bar height: " + result);
        return result;
    }
}
