package com.jerryjin.kit.navigationBar;

/**
 * Author: Jerry
 * Generated at: 2019/4/20 8:57 PM
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description: A listener for listening the state of the Navigation Bar.
 */
public interface OnNavigationBarStateChangeListener {
    /**
     * Callback to be invoked when the state of the Navigation Bar is changed.
     * <p>
     * There are two kinds of state: Shown -> isShown = true; Not shown -> isShown = false;
     *
     * @param isShown             True if the Navigation Bar is shown, false otherwise.
     * @param navigationBarHeight The height of the Navigation Bar.
     */
    void onNavigationBarStateChanged(boolean isShown, int navigationBarHeight);
}
