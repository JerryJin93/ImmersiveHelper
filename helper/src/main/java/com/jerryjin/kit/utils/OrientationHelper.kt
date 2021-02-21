package com.jerryjin.kit.utils

import android.app.Activity
import android.view.Surface
import com.jerryjin.kit.model.Orientation
import com.jerryjin.kit.OrientationSpec
import java.util.*

/**
 * Author: Jerry
 * Generated at: 2020/8/4 18:19
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
object OrientationHelper {

    private const val UNKNOWN = "unknown"

    @JvmStatic
    fun parseOrientation(@Orientation orientation: Int): String {
        val locale = Locale.getDefault()
        val chnMap = mapOf(
                OrientationSpec.PORTRAIT to "竖屏",
                OrientationSpec.PORTRAIT_REVERSED to "反向竖屏",
                OrientationSpec.LANDSCAPE to "向左横屏",
                OrientationSpec.LANDSCAPE_REVERSED to "向右横屏",
                OrientationSpec.UNKNOWN to "未知")
        val engMap = mapOf(
                OrientationSpec.PORTRAIT to "portrait",
                OrientationSpec.PORTRAIT_REVERSED to "portrait reversed",
                OrientationSpec.LANDSCAPE to "landscape",
                OrientationSpec.LANDSCAPE_REVERSED to "landscape reversed",
                OrientationSpec.UNKNOWN to UNKNOWN)
        val getResult: (Int, Locale) -> String? = { shadowOrientation, shadowLocale ->
            if (shadowLocale == Locale.SIMPLIFIED_CHINESE || shadowLocale == Locale.CHINESE || shadowLocale == Locale.TRADITIONAL_CHINESE) {
                chnMap[shadowOrientation]
            } else {
                engMap[shadowOrientation]
            }
        }
        emptyList<String>()
        return getResult(orientation, locale) ?: UNKNOWN
    }

    @JvmStatic
    fun getCurrentOrientation(activity: Activity?): Int {
        return when (activity?.windowManager?.defaultDisplay?.rotation ?: 0) {
            Surface.ROTATION_0 -> OrientationSpec.PORTRAIT
            Surface.ROTATION_90 -> OrientationSpec.LANDSCAPE
            Surface.ROTATION_180 -> OrientationSpec.PORTRAIT_REVERSED
            Surface.ROTATION_270 -> OrientationSpec.LANDSCAPE_REVERSED
            else -> OrientationSpec.UNKNOWN
        }
    }

}