package com.jerryjin.kit.notch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;

import com.jerryjin.kit.model.NotchInfo;
import com.jerryjin.kit.interfaces.INotch;
import com.jerryjin.kit.utils.ActivityUIHelper;
import com.jerryjin.kit.utils.log.Logger;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 21:41
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description:
 */
public class NotchHelper {

    private static final String TAG = "NotchHelper";
    private static final String MSG_NOT_TO_OPTIMIZE = "Current OS version is under Android Oreo, it's unnecessary to optimize.";
    private static final String MSG_NOT_TO_UNDO_OPTIMIZATION = "Current OS version is under Android Oreo, it's unnecessary to undo optimization.";

    @SuppressLint("ObsoleteSdkInt")
    public static NotchInfo optimize(Activity activity, Factory factory) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            Logger.e(TAG, "optimize", "Activity activity", MSG_NOT_TO_OPTIMIZE);
            return null;
        }
        ActivityUIHelper.immersive(activity);
        INotch notch = factory.getNotch();
        return notch.obtainNotch(activity);
    }
}
