package com.jerryjin.kit.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.TypedValue
import android.view.WindowManager

/**
 * Author: Jerry
 * Generated at: 2021/2/21 18:58
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description: Screen utils.
 *
 * @since 2.0.0
 */
class ScreenUtils {

    companion object {

        @JvmStatic
        fun dp2px(context: Context, dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)

        @JvmStatic
        fun dp2intPx(context: Context, dp: Float): Int = (dp2px(context, dp) + 0.5f).toInt()


        /**
         * Get the real size of the device screen.
         *
         * @param activity The given Activity.
         * @return A Point object which contains the width and height of the screen.
         */
        @SuppressLint("ObsoleteSdkInt")
        @JvmStatic
        fun getRealSize(activity: Activity): Point =
                (activity.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.let {
                    val point = Point()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        it.getRealSize(point)
                    } else {
                        it.getSize(point)
                    }
                    point
                }
    }

}