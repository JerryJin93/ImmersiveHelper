package com.jerryjin.kit.utils;

import android.graphics.Color;

/**
 * Author: Jerry
 * Generated at: 2019/3/31 4:42 PM
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 1.0.0
 * Description:
 */
public class ColorHelper {

    /**
     * Tell me the given color is light or dark.
     *
     * @param color The given color.
     * @return True if it's light color, false otherwise.
     */
    @SuppressWarnings("RedundantIfStatement")
    public static boolean isLightColor(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        if (darkness < 0.5) {
            return true; // It's a light color
        } else {
            return false; // It's a dark color
        }
    }
}
