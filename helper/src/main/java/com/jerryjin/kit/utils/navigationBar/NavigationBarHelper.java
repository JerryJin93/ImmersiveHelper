package com.jerryjin.kit.utils.navigationBar;

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
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.jerryjin.kit.utils.StringHelper;
import com.jerryjin.kit.utils.Utils;
import com.jerryjin.kit.utils.log.Logger;
import com.jerryjin.kit.interfaces.OnNavigationBarStateChangeListener;

import java.lang.reflect.Method;

/**
 * Author: Jerry
 * Generated at: 2019/3/29 11:17
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.6
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

    @SuppressLint("PrivateApi")
    public static boolean hasNavBar(Context context) {
        boolean hasNavigationBar = false;
        Resources resources = context.getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = resources.getBoolean(id);
        }
        try {
            Class<?> systemPropertiesClass = Class.forName("android.os.SystemProperties");
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
     * @param activity Current Activity.
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

    @SuppressLint("ObsoleteSdkInt")
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
     * Tell me whether the navigation bar is visible or not.
     * <br/>
     * You'd better call it in onWindowFocusChanged(boolean hasFocus), or it may not work.
     *
     * @param activity The given Activity.
     * @return True if the navigation bar is visible, false otherwise.
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
                Log.i(TAG, "Window visible display frame height： " + rect.bottom + ". The real height of the screen: " + point.y);
                Log.i(TAG, "Navigation bar height： " + getNavBarHeight(activity));
                show = (rect.bottom != point.y);
            }
        }
        return show;
    }

    /**
     * Tell me whether the navigation bar is shown or not. This method is more effective than the other one.
     * <br/>
     * You'd better invoke current method in onWindowFocusChanged(boolean hasFocus), or it may not work.
     *
     * @param activity                           The given Activity.
     * @param onNavigationBarStateChangeListener OnNavigationBarStateChangeListener.
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void isNavBarShow(Activity activity, final OnNavigationBarStateChangeListener onNavigationBarStateChangeListener) {
        if (activity == null) {
            Log.e(TAG, "Method " +
                    "isNavBarShow(Activity activity, OnNavigationBarStateChangeListener onNavigationBarStateChangeListener)" +
                    " is invoked. Null given activity.");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            final int navigationBarHeight = getNavBarHeight(activity);
            activity.getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    if (insets != null) {
                        int bottom = insets.getSystemWindowInsetBottom();
                        Log.i(TAG, "SystemWindowInsetLeft: " + insets.getSystemWindowInsetLeft()
                                + ". SystemWindowInsetTop: " + insets.getSystemWindowInsetTop() + ". SystemWindowInsetRight: "
                                + insets.getSystemWindowInsetRight() + ". SystemWindowInsetBottom: " + insets.getSystemWindowInsetBottom());
                        boolean isShowing = bottom == navigationBarHeight;
                        if (bottom <= navigationBarHeight && onNavigationBarStateChangeListener != null) {
                            onNavigationBarStateChangeListener.onNavigationBarStateChanged(isShowing, navigationBarHeight);
                        }
                    }
                    return insets;
                }
            });
        }
    }

    /**
     * Get the real size of the device screen.
     *
     * @param activity The given Activity.
     * @return A Point object which contains the width and height of the screen.
     */
    @SuppressLint("ObsoleteSdkInt")
    public static Point getRealSize(Activity activity) {
        final String methodName = "getRealSize";
        if (null == activity) {
            Logger.e(TAG, methodName, "activity", "Null given activity.");
            return null;
        }
        final String param = activity.toString();
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            Logger.e(TAG, methodName, StringHelper.format("Activity %s", param), "Null WindowManager retrieved.");
            return null;
        }
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
