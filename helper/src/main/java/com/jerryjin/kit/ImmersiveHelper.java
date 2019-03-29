package com.jerryjin.kit;

import android.app.Activity;
import android.util.Log;

import com.jerryjin.kit.navigationBar.NavigationBarHelper;
import com.jerryjin.kit.statusBar.OnStatusBarOptimizeCallback;
import com.wcl.notchfit.NotchFit;
import com.wcl.notchfit.args.NotchScreenType;
import com.wcl.notchfit.core.OnNotchCallBack;

import androidx.annotation.Size;

import static com.jerryjin.kit.statusBar.StatusBarHelper.isLightColor;
import static com.jerryjin.kit.statusBar.StatusBarHelper.setStatusBarBackgroundColor;
import static com.jerryjin.kit.statusBar.StatusBarHelper.setTranslucentStatusBar;
import static com.jerryjin.kit.statusBar.StatusBarHelper.toggleStatusBarTextColor;

/**
 * Author: Jerry
 * Generated at: 2019/3/27 12:38 AM
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
public class ImmersiveHelper {

    private static final String TAG = ImmersiveHelper.class.getSimpleName();

    private ImmersiveHelper() {
    }

    public static boolean hasNotch(Activity activity) {
        return NotchFit.hasNotch(activity);
    }

    /**
     * Optimization for screen with cutout or immersive Status Bar, must be called in onAttachedToWindow.
     *
     * @param activity        The current Activity.
     * @param callBack        OnNotchCallBack.
     * @param accordanceColor The status bar color you want it to be.
     * @param navBarHeight    The height of the Navigation Bar, you should only pass an int array of 1 element.
     */
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
            setTranslucentStatusBar(activity, fitViews);
            toggleStatusBarTextColor(activity, true);
            if (onStatusBarOptimizeCallback != null) {
                onStatusBarOptimizeCallback.onOptimized(navBarHeight[0]);
            }
        }
    }
}
