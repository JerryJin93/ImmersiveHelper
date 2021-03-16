package com.jerryjin.kit.manufacturer;

import android.app.Activity;

import com.jerryjin.kit.notch.AbsNotch;
import com.jerryjin.kit.utils.StringHelper;
import com.jerryjin.kit.utils.log.Logger;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 21:19
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description: For non-domestic Android devices only. OK
 */
public class AndroidNotch extends AbsNotch {

    private static final String TAG = "AndroidNotch";
    private static final String MSG = "Non-indigenous devices on Android O don't support Notch.";

    @Override
    protected boolean hasNotchOreo(Activity activity) {
        Logger.i(TAG, "hasNotchOreo", StringHelper.getActivityAsParamForLogger(activity), MSG);
        return false;
    }

    @Override
    protected int[] getNotchSpecOreo(Activity activity) {
        Logger.i(TAG, "getNotchSpecOreo", StringHelper.getActivityAsParamForLogger(activity), MSG);
        return ZERO_NOTCH;
    }
}
