package com.jerryjin.kit;

import android.app.Activity;
import android.util.Log;

import com.jerryjin.kit.navigationBar.NavigationBarHelper;
import com.jerryjin.kit.statusBar.OnStatusBarOptimizeCallback;
import com.wcl.notchfit.NotchFit;
import com.wcl.notchfit.args.NotchScreenType;
import com.wcl.notchfit.core.OnNotchCallBack;

import androidx.annotation.Size;

import static com.jerryjin.kit.ColorHelper.isLightColor;
import static com.jerryjin.kit.statusBar.StatusBarHelper.setStatusBarBackgroundColor;
import static com.jerryjin.kit.statusBar.StatusBarHelper.setTranslucentStatusBar;
import static com.jerryjin.kit.statusBar.StatusBarHelper.toggleStatusBarTextColor;
import static com.jerryjin.kit.statusBar.StatusBarHelper.toggleStatusBarTextColorForMIUI;

/**
 * Author: Jerry
 * Generated at: 2019/3/27 12:38 AM
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.1
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class ImmersiveHelper {

    private static final String TAG = ImmersiveHelper.class.getSimpleName();

    private ImmersiveHelper() {
    }

    public static boolean hasNotch(Activity activity) {
        return NotchFit.hasNotch(activity);
    }

    /**
     * Optimization for screen with cutout or immersive status bar, must be called in onAttachedToWindow.
     *
     * @param activity        The current Activity.
     * @param callBack        OnNotchCallBack.
     * @param accordanceColor The status bar color you want it to be.
     * @param navBarHeight    The height of the Navigation Bar, you should only pass an int array of 1 element.
     */
    @Deprecated
    public static void optimize(Activity activity, OnNotchCallBack callBack, int accordanceColor, @Size(1) int[] navBarHeight) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return;
        }
        if (navBarHeight == null) {
            Log.e(TAG, "Null given int array, creating a new one...");
            navBarHeight = new int[1];
        }
        navBarHeight[0] = NavigationBarHelper.hasNavBar(activity) ? NavigationBarHelper.getNavBarHeight(activity) : NavigationBarHelper.ERR_CODE;
        if (hasNotch(activity)) {
            Log.i(TAG, "The current device has a cutout or notch, let's fit it.");
            NotchFit.fit(activity, NotchScreenType.TRANSLUCENT, callBack);
        } else {
            Log.i(TAG, "The current device doesn't has a cutout or notch. We're going to have a immersive experience.");
            setStatusBarBackgroundColor(activity, accordanceColor);
            toggleStatusBarTextColor(activity, isLightColor(accordanceColor));
        }
    }

    @Deprecated
    public static void optimize(Activity activity, OnNotchCallBack onNotchCallBack, boolean fitViews,
                                OnStatusBarOptimizeCallback onStatusBarOptimizeCallback, @Size(1) int[] navBarHeight) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return;
        }
        if (navBarHeight == null) {
            Log.e(TAG, "Null given int array, creating a new one...");
            navBarHeight = new int[1];
        }
        navBarHeight[0] = NavigationBarHelper.hasNavBar(activity) ? NavigationBarHelper.getNavBarHeight(activity) : NavigationBarHelper.ERR_CODE;
        if (hasNotch(activity)) {
            Log.i(TAG, "The current device has a cutout or notch, let's fit it.");
            NotchFit.fit(activity, NotchScreenType.TRANSLUCENT, onNotchCallBack);
        } else {
            Log.i(TAG, "The current device doesn't has a cutout or notch. We're going to have a immersive experience.");
            setTranslucentStatusBar(activity);
            toggleStatusBarTextColor(activity, true);
            if (onStatusBarOptimizeCallback != null) {
                onStatusBarOptimizeCallback.onOptimized(navBarHeight[0]);
            }
        }
    }

    /**
     * Optimization for screen with cutout or immersive status bar. The navigation bar will not be translucent.
     * <br/>
     * However, if there is a view displays image or video on the top of the window, this method is not suitable.
     *
     * @param activity        The Activity for optimization.
     * @param hasNotch        The current device has notch or not.
     * @param onNotchCallBack OnNotchCallBack.
     * @param statusBarColor  The color of the status bar you want to set.
     */
    public static void optimize(Activity activity, boolean hasNotch, OnNotchCallBack onNotchCallBack, int statusBarColor) {
        optimize(activity, hasNotch, NotchScreenType.TRANSLUCENT_STATUS_BAR_ONLY, onNotchCallBack, statusBarColor);
    }

    /**
     * Optimization for screen with cutout or immersive status bar. The navigation bar will not be translucent.
     * <br/>
     * And you should call this method in onAttachedWindow().
     * <br/>
     * However, if there is a view displays image or video on the top of the window, this method is not suitable.
     *
     * @param activity        The Activity for optimization.
     * @param onNotchCallBack OnNotchCallBack.
     * @param statusBarColor  The color of the status bar you want to set.
     */
    public static void optimize(Activity activity, OnNotchCallBack onNotchCallBack, int statusBarColor) {
        optimize(activity, hasNotch(activity), onNotchCallBack, statusBarColor);
    }


    /**
     * Optimization for screen with cutout or immersive status bar.
     * <br/>
     * However, if there is a view displays image or video on the top of the window, this method is not suitable.
     *
     * @param activity        The Activity for optimization.
     * @param hasNotch        The current device has notch or not.
     * @param type            The type of notch you want to apply for.
     * @param onNotchCallBack OnNotchCallBack.
     * @param statusBarColor  The color of the status bar you want to set.
     */
    public static void optimize(Activity activity, boolean hasNotch, NotchScreenType type, OnNotchCallBack onNotchCallBack, int statusBarColor) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return;
        }
        boolean darkMode = isLightColor(statusBarColor);
        if (hasNotch) {
            Log.i(TAG, "The current device has a cutout or notch, let's fit it.");
            NotchFit.fit(activity, type, onNotchCallBack);
        } else {
            setStatusBarBackgroundColor(activity, statusBarColor);
        }
        toggleStatusBarTextColor(activity, darkMode);
        toggleStatusBarTextColorForMIUI(activity, darkMode);
    }

    /**
     * Optimization for screen with cutout or immersive status bar.
     * <br/>
     * And you should call this method in onAttachedWindow().
     * <br/>
     * However, if there is a view displays image or video on the top of the window, this method is not suitable.
     *
     * @param activity        The Activity for optimization.
     * @param type            The type of notch you want to apply for.
     * @param onNotchCallBack OnNotchCallBack.
     * @param statusBarColor  The color of the status bar you want to set.
     */
    public static void optimize(Activity activity, NotchScreenType type, OnNotchCallBack onNotchCallBack, int statusBarColor) {
        optimize(activity, hasNotch(activity), type, onNotchCallBack, statusBarColor);
    }

    /**
     * Optimization for screen with cutout or immersive status bar.
     *
     * @param activity        The Activity for optimization.
     * @param hasNotch        The current device has notch or not.
     * @param type            The type of notch you want to apply for.
     * @param onNotchCallBack OnNotchCallBack.
     * @param darkMode        True if you want the color of text in status bar to be dark, false otherwise.
     */
    public static void optimize(Activity activity, boolean hasNotch, NotchScreenType type, OnNotchCallBack onNotchCallBack,
                                boolean darkMode) {
        if (activity == null) {
            Log.e(TAG, "Null given activity.");
            return;
        }
        if (hasNotch) {
            Log.i(TAG, "The current device has a cutout or notch, let's fit it.");
            NotchFit.fit(activity, type, onNotchCallBack);
        } else {
            setTranslucentStatusBar(activity);
        }
        toggleStatusBarTextColor(activity, darkMode);
        toggleStatusBarTextColorForMIUI(activity, darkMode);
    }

    /**
     * Optimization for screen with cutout or immersive status bar. And you should call this method in onAttachedWindow().
     *
     * @param activity        The Activity for optimization.
     * @param type            The type of notch you want to apply for.
     * @param onNotchCallBack OnNotchCallBack.
     * @param darkMode        True if you want the color of text in status bar to be dark, false otherwise.
     */
    public static void optimize(Activity activity, NotchScreenType type, OnNotchCallBack onNotchCallBack,
                                boolean darkMode) {
        optimize(activity, hasNotch(activity), type, onNotchCallBack, darkMode);
    }

    /**
     * Optimization for screen with cutout or immersive status bar. The navigation bar will not be translucent.
     *
     * @param activity        The Activity for optimization.
     * @param hasNotch        The current device has notch or not.
     * @param onNotchCallBack OnNotchCallBack.
     * @param darkMode        True if you want the color of text in status bar to be dark, false otherwise.
     */
    public static void optimize(Activity activity, boolean hasNotch, OnNotchCallBack onNotchCallBack, boolean darkMode) {
        optimize(activity, hasNotch, NotchScreenType.TRANSLUCENT_STATUS_BAR_ONLY, onNotchCallBack, darkMode);
    }

    /**
     * Optimization for screen with cutout or immersive status bar. And you should call this method in onAttachedWindow().
     *
     * @param activity        The Activity for optimization.
     * @param onNotchCallBack OnNotchCallBack.
     * @param darkMode        True if you want the color of text in status bar to be dark, false otherwise.
     */
    public static void optimize(Activity activity, OnNotchCallBack onNotchCallBack, boolean darkMode) {
        optimize(activity, hasNotch(activity), onNotchCallBack, darkMode);
    }

    // TODO: 2019/3/31 cascade method

}
