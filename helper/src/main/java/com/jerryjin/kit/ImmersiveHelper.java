package com.jerryjin.kit;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.jerryjin.kit.model.NotchInfo;
import com.jerryjin.kit.model.SystemUIInfo;
import com.jerryjin.kit.interfaces.OptimizationCallback;
import com.jerryjin.kit.lifecycle.ImmersiveLifecycle;
import com.jerryjin.kit.lifecycle.ImmersiveLifecycleObserver;
import com.jerryjin.kit.manufacturer.Manufacturer;
import com.jerryjin.kit.notch.Factory;
import com.jerryjin.kit.notch.NotchFactory;
import com.jerryjin.kit.notch.NotchHelper;
import com.jerryjin.kit.utils.ActivityUIHelper;
import com.jerryjin.kit.utils.OrientationHelper;
import com.jerryjin.kit.utils.Utils;
import com.jerryjin.kit.utils.log.Logger;
import com.jerryjin.kit.utils.navigationBar.NavigationBarHelper;
import com.jerryjin.kit.utils.statusBar.StatusBarHelper;

import static com.jerryjin.kit.utils.log.Logger.ERR_NULL_ACTIVITY;
import static com.jerryjin.kit.utils.log.Logger.i;
import static com.jerryjin.kit.utils.statusBar.StatusBarHelper.setTranslucentStatusBar;
import static com.jerryjin.kit.utils.statusBar.StatusBarHelper.toggleStatusBarTextColor;
import static com.jerryjin.kit.utils.statusBar.StatusBarHelper.toggleStatusBarTextColorForMIUI;

/**
 * Author: Jerry
 * Generated at: 2019/3/27 12:38 AM
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description: ImmersiveHelper.
 */
@SuppressWarnings("WeakerAccess")
public class ImmersiveHelper implements ImmersiveLifecycle {

    private static final String TAG = "ImmersiveHelper";

    private Activity activity;
    private SystemUIInfo systemUIInfo;
    private boolean isStatusBarTextDark = true;
    private OptimizationCallback optimizationCallback;
    private OptimizationType optimizationType = OptimizationType.TYPE_IMMERSIVE;
    private Factory factory = NotchFactory.getInstance();
    private Configuration config;

    private int previousStatusBarColor;
    private int previousNavigationBarColor;

    public ImmersiveHelper(Activity activity) {
        this.activity = activity;
        saveDecorationColor();
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).getLifecycle().addObserver(new ImmersiveLifecycleObserver(this));
        }
    }

    private void saveDecorationColor() {
        final String methodName = "saveDecorationColor";
        if (activity == null) {
            Logger.e(TAG, methodName, ERR_NULL_ACTIVITY);
            return;
        }
        Window window = activity.getWindow();
        if (window == null) {
            Logger.e(TAG, methodName, "Null window attached.");
            return;
        }
        this.previousStatusBarColor = window.getStatusBarColor();
        this.previousNavigationBarColor = window.getNavigationBarColor();
    }

    /**
     * Set the status of the {@link Logger}.
     *
     * @param enable True if to enable, false otherwise.
     */
    public static void enableLogger(boolean enable) {
        Logger.setLoggable(enable);
    }


    public ImmersiveHelper setCallback(OptimizationCallback onOptimizeCallback) {
        this.optimizationCallback = onOptimizeCallback;
        return this;
    }

    public ImmersiveHelper setFactory(Factory factory) {
        if (factory != null) {
            this.factory = factory;
        }
        return this;
    }

    public ImmersiveHelper setStatusBarDarkMode(boolean darkMode) {
        this.isStatusBarTextDark = darkMode;
        return this;
    }

    public ImmersiveHelper setOptimizationType(OptimizationType optimizationType) {
        this.optimizationType = optimizationType;
        return this;
    }

    public void optimize() {
        safetyCheck();
        if (optimizationType == OptimizationType.TYPE_IMMERSIVE) {
            optimizeImmersive();
        } else if (optimizationType == OptimizationType.TYPE_FULLSCREEN) {
            optimizeFullScreen();
        }
    }

    private void optimizeImmersive() {
        ActivityUIHelper.probeNotch(activity, factory, hasNotch -> {
            NotchInfo notchInfo;
            if (hasNotch) {
                notchInfo = NotchHelper.optimize(activity, factory);
            } else {
                notchInfo = null;
                setTranslucentStatusBar(activity);
            }
            toggleStatusBarTextColor(activity, isStatusBarTextDark);
            if (Utils.getManufacturer().toLowerCase().equals(Manufacturer.MI)) {
                toggleStatusBarTextColorForMIUI(activity, isStatusBarTextDark);
            }
            if (optimizationCallback != null) {
                Logger.e(TAG, "orientation", OrientationHelper.parseOrientation(OrientationHelper.getCurrentOrientation(activity)));
                systemUIInfo = new SystemUIInfo.Builder()
                        .setOrientation(OrientationHelper.getCurrentOrientation(activity))
                        .setNotchInfo(notchInfo)
                        .setWhetherNavigationBarShow(NavigationBarHelper.isNavBarShown(activity))
                        .setStatusBarHeight(StatusBarHelper.getStatusBarHeight(activity))
                        .setNavigationBarHeight(NavigationBarHelper.getNavBarHeight(activity))
                        .build();
                optimizationCallback.onOptimized(systemUIInfo);
            }
        });
    }


    private void optimizeFullScreen() {
        ActivityUIHelper.fullScreen(activity, factory);
        if (activity == null) {
            Logger.e(TAG, "optimizeFullScreen", ERR_NULL_ACTIVITY);
            return;
        }
        toggleStatusBarTextColor(activity, isStatusBarTextDark);
        if (Utils.getManufacturer().toLowerCase().equals(Manufacturer.MI)) {
            toggleStatusBarTextColorForMIUI(activity, isStatusBarTextDark);
        }
        if (optimizationCallback != null) {
            activity.getWindow().getDecorView().post(() -> {
                systemUIInfo = new SystemUIInfo.Builder()
                        .setOrientation(OrientationHelper.getCurrentOrientation(activity))
                        .setNotchInfo(factory.getNotch().obtainNotch(activity))
                        .setStatusBarHeight(StatusBarHelper.getStatusBarHeight(activity))
                        .setWhetherNavigationBarShow(NavigationBarHelper.isNavBarShown(activity))
                        .setNavigationBarHeight(NavigationBarHelper.getNavBarHeight(activity))
                        .build();
                optimizationCallback.onOptimized(systemUIInfo);
            });
        }
    }

    private void safetyCheck() {
        if (factory.getNotch() == null) {
            throw new IllegalArgumentException("Null notch instance is not allowed!");
        }
    }

    public void notifyWindowFocusChanged() {
        optimize();
    }

    public void notifyConfigurationChanged(Configuration newConfig) {
        this.config = newConfig;
        optimize();
    }

    @Override
    public void dispose() {
        initState();
    }

    private void initState() {
        activity = null;
        systemUIInfo = null;
        isStatusBarTextDark = false;
        optimizationType = OptimizationType.TYPE_IMMERSIVE;
        optimizationCallback = null;
        config = null;
        // PhoneWindow#generateLayout()
        this.previousStatusBarColor = 0;
        this.previousNavigationBarColor = 0;
    }
}
