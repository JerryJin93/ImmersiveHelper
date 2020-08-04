package com.jerryjin.kit.notch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;

import com.jerryjin.kit.bean.NotchInfo;
import com.jerryjin.kit.interfaces.INotch;
import com.jerryjin.kit.interfaces.NotchCallback;
import com.jerryjin.kit.utils.ActivityUIHelper;
import com.jerryjin.kit.utils.StringHelper;
import com.jerryjin.kit.utils.Utils;
import com.jerryjin.kit.utils.log.Logger;

import static com.jerryjin.kit.utils.log.Logger.ERR_NULL_ACTIVITY;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 21:41
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
public class NotchHelper {

    private static final String TAG = "NotchHelper";
    private static final String MSG_NOT_TO_OPTIMIZE = "Current OS version is under Android Oreo, it's unnecessary to optimize.";
    private static final String MSG_NOT_TO_UNDO_OPTIMIZATION = "Current OS version is under Android Oreo, it's unnecessary to undo optimization.";

    @SuppressLint("ObsoleteSdkInt")
    public static NotchInfo optimize(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Logger.e(TAG, "optimize", "Activity activity", MSG_NOT_TO_OPTIMIZE);
            return null;
        }
        ActivityUIHelper.immersive(activity);
        INotch notch = NotchFactory.getNotch();
        notch.applyNotch(activity, true);

        return notch.obtainNotch(activity);
    }

    @SuppressLint("ObsoleteSdkInt")
    public static boolean undoOptimization(Activity activity, int previousStatusBarColor, int previousNavigationBarColor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Logger.e(TAG, "undoOptimization", StringHelper.format("Activity activity, int %d", previousStatusBarColor), MSG_NOT_TO_UNDO_OPTIMIZATION);
            return false;
        }
        if (!ActivityUIHelper.isImmersive(activity)) return false;
        NotchFactory.getNotch().applyNotch(activity, false);
        ActivityUIHelper.undoOptimizationForImmersive(activity, previousStatusBarColor, previousNavigationBarColor);
        return true;
    }

    public static void probeNotch(Activity activity, NotchCallback callback) {
        if (activity == null) {
            Logger.e(TAG, "probeNotch", ERR_NULL_ACTIVITY);
            return;
        }
        activity.getWindow().getDecorView().post(() -> {
            boolean result = NotchFactory.getNotch().hasNotch(activity);
            if (callback != null) {
                callback.onProbeReady(result);
            }
        });
    }
}
