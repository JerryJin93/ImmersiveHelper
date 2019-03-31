package com.jerryjin.kit;

import android.graphics.Bitmap;

/**
 * Author: Jerry
 * Generated at: 2019/3/31 4:45 PM
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.0
 * Description:
 */
public class BitmapHelper {

    public static Bitmap clip(Bitmap src, int x, int y) {
        int width = src.getWidth();
        int height = src.getHeight();
        return Bitmap.createBitmap(src, x, y, width, height, null, false);
    }
}
