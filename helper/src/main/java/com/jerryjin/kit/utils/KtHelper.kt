package com.jerryjin.kit.utils

/**
 * Author: Jerry
 * Generated at: 2020/8/1 20:47
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
fun String.mutate() = StringBuilder(this)

class KotlinCompat {
    companion object {

        @JvmStatic
        fun mutableString(str: String) = str.mutate()
    }
}

class Constants {
    companion object {
        const val EMPTY_STRING = ""
        const val PERIOD = "."
        const val LOG_DELIMITER = "#"
        const val LOG_BRACKET_LEFT = "("
        const val LOG_BRACKET_RIGHT = ")"
        const val PUSH  = " -> "
    }
}