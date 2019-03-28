package com.jerryjin.helper;

import android.app.Activity;

import com.wcl.notchfit.NotchFit;

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
    public static boolean hasNotch(Activity activity) {
        return NotchFit.hasNotch(activity);
    }
}
