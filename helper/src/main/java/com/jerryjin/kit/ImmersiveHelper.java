package com.jerryjin.kit;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.jerryjin.kit.bean.DecorationInfo;
import com.jerryjin.kit.bean.NotchInfo;
import com.jerryjin.kit.interfaces.OnOptimizeCallback;
import com.jerryjin.kit.lifecycle.ImmersiveLifecycle;
import com.jerryjin.kit.lifecycle.ImmersiveLifecycleObserver;
import com.jerryjin.kit.manufacturer.Manufacturer;
import com.jerryjin.kit.notch.NotchFactory;
import com.jerryjin.kit.notch.NotchHelper;
import com.jerryjin.kit.utils.ActivityUIHelper;
import com.jerryjin.kit.utils.Utils;
import com.jerryjin.kit.utils.log.Logger;
import com.jerryjin.kit.utils.navigationBar.NavigationBarHelper;
import com.jerryjin.kit.utils.statusBar.StatusBarHelper;

import static com.jerryjin.kit.utils.log.Logger.ERR_NULL_ACTIVITY;
import static com.jerryjin.kit.utils.statusBar.StatusBarHelper.setTranslucentStatusBar;
import static com.jerryjin.kit.utils.statusBar.StatusBarHelper.toggleStatusBarTextColor;
import static com.jerryjin.kit.utils.statusBar.StatusBarHelper.toggleStatusBarTextColorForMIUI;

/**
 * Author: Jerry
 * Generated at: 2019/3/27 12:38 AM
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class ImmersiveHelper implements ImmersiveLifecycle {

    private static final String TAG = ImmersiveHelper.class.getSimpleName();

    private Activity activity;
    private boolean hasOptimized;
    private DecorationInfo decorationInfo;
    private boolean isStatusBarTextDark;
    private OnOptimizeCallback onOptimizeCallback;
    private OptimizationType optimizationType = OptimizationType.TYPE_IMMERSIVE;

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
        Window window = activity.getWindow();
        this.previousStatusBarColor = window.getStatusBarColor();
        this.previousNavigationBarColor = window.getNavigationBarColor();
    }

    /**
     * Set the status of the {@link Logger}.
     *
     * @param enable True if to enable, false otherwise.
     */
    public static void setLoggerStatus(boolean enable) {
        Logger.setLoggable(enable);
    }


    public ImmersiveHelper setCallback(OnOptimizeCallback onOptimizeCallback) {
        this.onOptimizeCallback = onOptimizeCallback;
        return this;
    }

    public ImmersiveHelper setStatusBarMode(boolean darkMode) {
        this.isStatusBarTextDark = darkMode;
        return this;
    }

    public ImmersiveHelper setOptimizationType(OptimizationType optimizationType) {
        this.optimizationType = optimizationType;
        return this;
    }

    public void optimize() {
        if (optimizationType == OptimizationType.TYPE_IMMERSIVE) {
            optimizeImmersive();
        } else if (optimizationType == OptimizationType.TYPE_FULLSCREEN) {
            optimizeFullScreen();
        }
    }

    private void optimizeImmersive() {
        NotchHelper.probeNotch(activity, hasNotch -> {
            NotchInfo notchInfo;
            if (hasNotch) {
                notchInfo = NotchHelper.optimize(activity);
            } else {
                notchInfo = null;
                setTranslucentStatusBar(activity);
            }
            hasOptimized = true;
            toggleStatusBarTextColor(activity, isStatusBarTextDark);
            if (Utils.getManufacturer().toLowerCase().equals(Manufacturer.MI)) {
                toggleStatusBarTextColorForMIUI(activity, isStatusBarTextDark);
            }
            if (onOptimizeCallback != null) {
                decorationInfo = new DecorationInfo.Builder()
                        .setOrientation(activity.getResources().getConfiguration().orientation)
                        .setNotchInfo(notchInfo)
                        .setStatusBarHeight(StatusBarHelper.getStatusBarHeight(activity))
                        .setNavigationBarHeight(NavigationBarHelper.getNavBarHeight(activity))
                        .build();
                onOptimizeCallback.onOptimized(decorationInfo);
            }
        });
    }


    private void optimizeFullScreen() {
        ActivityUIHelper.fullScreen(activity);
        hasOptimized = true;
        if (activity == null) {
            Logger.e(TAG, "optimizeFullScreen", ERR_NULL_ACTIVITY);
            return;
        }
        if (onOptimizeCallback != null) {
            activity.getWindow().getDecorView().post(() -> {
                decorationInfo = new DecorationInfo.Builder()
                        .setOrientation(activity.getResources().getConfiguration().orientation)
                        .setNotchInfo(NotchFactory.getNotch().obtainNotch(activity))
                        .setStatusBarHeight(StatusBarHelper.getStatusBarHeight(activity))
                        .setNavigationBarHeight(NavigationBarHelper.getNavBarHeight(activity))
                        .build();
                onOptimizeCallback.onOptimized(decorationInfo);
            });
        }
    }

    public void undoOptimization() {
        if (optimizationType == OptimizationType.TYPE_IMMERSIVE) {
            undoOptimizationForImmersive();
        } else if (optimizationType == OptimizationType.TYPE_FULLSCREEN) {
            undoOptimizationForFullScreen();
        }
    }

    private void undoOptimizationForImmersive() {
        hasOptimized = NotchHelper.undoOptimization(activity, previousStatusBarColor, previousNavigationBarColor);
        if (onOptimizeCallback != null) {
            if (decorationInfo == null) {
                throw new IllegalStateException("You cannot undo optimization for immersive after disposing.");
            }
            NotchInfo notchInfo = decorationInfo.getNotchInfo();
            if (notchInfo != null) {
                notchInfo.reset();
            }
            onOptimizeCallback.onOptimized(decorationInfo);
        }
    }

    private void undoOptimizationForFullScreen() {
        if (ActivityUIHelper.isFullScreen(activity)) {
            ActivityUIHelper.undoOptimizationForFullScreen(activity);
            hasOptimized = false;
            if (onOptimizeCallback != null) {
                if (decorationInfo == null) {
                    throw new IllegalStateException("You cannot undo optimization for fullscreen after disposing.");
                }
                if (decorationInfo.hasNotch()) {
                    decorationInfo.resetNotchInfo();
                }
                onOptimizeCallback.onOptimized(decorationInfo);
            }
        }
    }

    public void notifyConfigurationChanged(Configuration config) {
        if (decorationInfo == null) {
            throw new IllegalStateException("You cannot call notifyConfigurationChanged after disposing.");
        }
        if (!hasOptimized)
            optimize();
        decorationInfo.setOrientation(config.orientation);
        if (onOptimizeCallback != null) {
            onOptimizeCallback.onOptimized(decorationInfo);
        }
    }

    public void notifyWindowFocusChanged(boolean hasFocus) {
        if (decorationInfo == null) {
            throw new IllegalStateException("You cannot call notifyWindowFocusChanged after disposing.");
        }
        if (ActivityUIHelper.needOptimizeNavigationBar(activity) && onOptimizeCallback != null) {
            onOptimizeCallback.onOptimized(decorationInfo, hasFocus);
        }
    }

    @Override
    public void dispose() {
        initState();
    }

    private void initState() {
        activity = null;
        hasOptimized = false;
        decorationInfo = null;
        isStatusBarTextDark = false;
        optimizationType = OptimizationType.TYPE_IMMERSIVE;
        onOptimizeCallback = null;
        this.previousStatusBarColor = Color.TRANSPARENT;
    }
}
