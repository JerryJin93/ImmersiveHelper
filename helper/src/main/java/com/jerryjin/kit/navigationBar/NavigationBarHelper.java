package com.jerryjin.kit.navigationBar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.reflect.Method;

/**
 * Author: Jerry
 * Generated at: 2019/3/29 11:17
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description: A helper for navigation bar of Android.
 */
@SuppressWarnings("WeakerAccess")
public class NavigationBarHelper {
    /**
     * Error code for Navigation Bar related method.
     */
    public static final int ERR_CODE = -1;
    private static final String TAG = NavigationBarHelper.class.getSimpleName();

    private NavigationBarHelper() {
    }

    @SuppressWarnings("unchecked")
    @SuppressLint("PrivateApi")
    public static boolean hasNavBar(Context context) {
        boolean hasNavigationBar = false;
        Resources resources = context.getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = resources.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }

    /**
     * Get the height of the navigation bar.
     *
     * @param activity Current activity.
     * @return The height of the navigation bar.
     */
    public static int getNavBarHeight(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return ERR_CODE;
        }
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    public static void setNavBarColor(Activity activity, int color) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(color);
        }
    }

    public static boolean isNavBarShow(Activity activity) {
        if (null == activity) {
            Log.e(TAG, "Null given activity.");
            return false;
        }
        /*
          获取应用区域高度
         */
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        int activityHeight = outRect.height();
        /*
          获取状态栏高度
         */
        int statusBarHeight = getStatusBarHeight(activity);
        /*
          屏幕物理高度 减去 状态栏高度
         */
        int remainHeight = getRealHeight(activity) - statusBarHeight;
        /*
          剩余高度跟应用区域高度相等 说明导航栏没有显示 否则相反
         */
        return activityHeight != remainHeight;
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
        return result;
    }

    @SuppressLint("ObsoleteSdkInt")
    public static int getRealHeight(Activity activity) {
        if (null == activity) {
            Log.e(TAG, "Null given activity.");
            return ERR_CODE;
        }
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    public static void prepareCascadeBackgroundForTranslucentNavBar(Activity activity, ViewGroup rootLayout, int desiredNavBarColor) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return;
        }
        if (!hasNavBar(activity)) {
            return;
        }
        if (isNavBarShow(activity)) {
            activity.getWindow().getDecorView().setBackgroundColor(desiredNavBarColor);
            FrameLayout.LayoutParams rootParams = (FrameLayout.LayoutParams) rootLayout.getLayoutParams();
            rootParams.bottomMargin = getNavBarHeight(activity);
        }
    }
}
