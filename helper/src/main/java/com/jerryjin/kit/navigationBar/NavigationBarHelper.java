package com.jerryjin.kit.navigationBar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.reflect.Method;

/**
 * Author: Jerry
 * Generated at: 2019/3/29 11:17
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.2
 * Description: A helper for navigation bar of Android.
 */
@SuppressWarnings("WeakerAccess")
public class NavigationBarHelper {
    /**
     * Error code for Navigation Bar related method.
     */
    public static final int ERR_CODE = -1;
    /**
     * Default color for navigation bar if you want to change its color from default one.
     */
    public static final int DEFAULT_COLOR_FOR_CUSTOMIZED_NAVIGATION_BAR = Color.LTGRAY;
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

    /**
     * 判断虚拟导航栏是否显示
     *
     * @return true(显示虚拟导航栏)，false(不显示或不支持虚拟导航栏)
     */
    public static boolean isNavBarShow(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Method isNavBarShow(Activity activity) is invoked. Null given activity.");
            return false;
        }
        boolean show = false;
        Point point = getRealSize(activity);
        if (point != null) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            Configuration conf = window.getContext().getResources().getConfiguration();
            if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
                View contentView = decorView.findViewById(android.R.id.content);
                show = (point.x != contentView.getWidth());
            } else {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                Log.i(TAG, "visible height： " + rect.bottom + " real height: " + point.y);
                Log.i(TAG, "navigation bar height： " + getNavBarHeight(activity));
                show = (rect.bottom != point.y);
            }
        }
        return show;
    }

    @SuppressLint("ObsoleteSdkInt")
    public static Point getRealSize(Activity activity) {
        if (null == activity) {
            Log.e(TAG, "Null given activity.");
            return null;
        }
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point;
    }

    /**
     * Make a solid background for transparent navigation bar. Meanwhile, you can set the background color.
     * <br/>
     * It's only useful when the navigation bar is transparent and is shown. If the navigation bar is invisible, there won't be any change at all.
     *
     * @param activity           The given Activity.
     * @param desiredNavBarColor The color of the background you want to set.
     */
    public static void prepareCascadeBackgroundForTranslucentNavBar(Activity activity, int desiredNavBarColor) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return;
        }
        if (!hasNavBar(activity) || !isNavBarShow(activity)) {
            return;
        }
        View decorView = activity.getWindow().getDecorView();
        decorView.setBackgroundColor(desiredNavBarColor);
        ViewGroup rootLayout = decorView.findViewById(android.R.id.content);
        FrameLayout.LayoutParams rootParams = (FrameLayout.LayoutParams) rootLayout.getLayoutParams();
        rootParams.bottomMargin = getNavBarHeight(activity);
    }
}
