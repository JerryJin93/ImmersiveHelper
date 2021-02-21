package com.jerryjin.kit.interfaces;

import android.app.Activity;

import com.jerryjin.kit.model.NotchInfo;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 20:51
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description:
 */
public interface INotch {
    void applyNotch(Activity activity, boolean enable);

    boolean hasNotch(Activity activity);

    boolean isNotchApplied();

    NotchInfo obtainNotch(Activity activity);
}
