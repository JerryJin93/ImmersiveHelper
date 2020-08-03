package com.jerryjin.kit.manufacturer;

import android.app.Activity;

import com.jerryjin.kit.utils.Utils;
import com.jerryjin.kit.utils.log.Logger;
import com.jerryjin.kit.notch.AbsNotch;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 21:19
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description: For non-domestic Android devices only.
 */
public class AndroidNotch extends AbsNotch {

    private static final String TAG = "AndroidNotch";
    private static final String MSG = "Non-domestic devices on Android O don't support Notch.";

    @Override
    protected boolean hasNotchOreo(Activity activity) {
        Logger.i(TAG, "hasNotchOreo", Utils.format("Activity %s", activity.getClass().getSimpleName()), MSG);
        return false;
    }

    @Override
    protected int[] getNotchSpecOreo(Activity activity) {
        Logger.i(TAG, "getNotchSpecOreo", Utils.format("Activity %s", activity.getClass().getSimpleName()), MSG);
        return ZERO_NOTCH;
    }
}
