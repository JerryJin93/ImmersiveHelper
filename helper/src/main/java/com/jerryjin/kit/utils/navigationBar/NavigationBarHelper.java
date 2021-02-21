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
import android.widget.FrameLayout;

import com.jerryjin.kit.OrientationSpec;
import com.jerryjin.kit.interfaces.OnNavigationBarStateChangeListener;
import com.jerryjin.kit.utils.OrientationHelper;
import com.jerryjin.kit.utils.ScreenUtils;

import java.lang.reflect.Method;

/**
 * Author: Jerry
 * Generated at: 2019/3/29 11:17
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
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

    private static boolean isNavigationBarShown = true;

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
    public static boolean isNavBarShown(Activity activity) {
        if (activity == null) {
            Log.e(TAG, "Method isNavBarShow(Activity activity) is invoked. Null given activity.");
            return false;
        }
        boolean shown;
        Point point = ScreenUtils.getRealSize(activity);
        Window window = activity.getWindow();
        View decorView = window.getDecorView();
        Configuration conf = window.getContext().getResources().getConfiguration();
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        Log.i(TAG, "Window visible display frame height： " + rect.bottom + ". The real height of the screen: " + point.y);
        Log.i(TAG, "Navigation bar height： " + getNavBarHeight(activity));
        isNavigationBarShown = shown = Configuration.ORIENTATION_LANDSCAPE == conf.orientation ?
                rect.width() < point.x : rect.bottom != point.y;
        return shown;
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
    public static void isNavBarShown(Activity activity, final OnNavigationBarStateChangeListener onNavigationBarStateChangeListener) {
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
                        final int left = insets.getSystemWindowInsetLeft();
                        final int top = insets.getSystemWindowInsetTop();
                        final int right = insets.getSystemWindowInsetRight();
                        final int bottom = insets.getSystemWindowInsetBottom();
                        Log.i(TAG, "SystemWindowInsetLeft: " + insets.getSystemWindowInsetLeft()
                                + ". SystemWindowInsetTop: " + insets.getSystemWindowInsetTop() + ". SystemWindowInsetRight: "
                                + insets.getSystemWindowInsetRight() + ". SystemWindowInsetBottom: " + insets.getSystemWindowInsetBottom());
                        int orientation = OrientationHelper.getCurrentOrientation(activity);
                        boolean isShowing = (left == navigationBarHeight && orientation == OrientationSpec.LANDSCAPE_REVERSED) ||
                                (top == navigationBarHeight && orientation == OrientationSpec.PORTRAIT_REVERSED) ||
                                (bottom == navigationBarHeight && orientation == OrientationSpec.PORTRAIT) ||
                                (right == navigationBarHeight && orientation == OrientationSpec.LANDSCAPE);
                        if (isShowing != isNavigationBarShown && onNavigationBarStateChangeListener != null) {
                            onNavigationBarStateChangeListener.onNavigationBarStateChanged(isNavigationBarShown = isShowing, navigationBarHeight);
                        }
                    }
                    return insets;
                }
            });
        }
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
        if (!hasNavBar(activity) || !isNavBarShown(activity)) {
            return;
        }
        View decorView = activity.getWindow().getDecorView();
        decorView.setBackgroundColor(desiredNavBarColor);
        ViewGroup rootLayout = decorView.findViewById(android.R.id.content);
        FrameLayout.LayoutParams rootParams = (FrameLayout.LayoutParams) rootLayout.getLayoutParams();
        rootParams.bottomMargin = getNavBarHeight(activity);
    }
}
