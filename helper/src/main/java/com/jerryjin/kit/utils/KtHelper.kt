package com.jerryjin.kit.utils

/**
 * Author: Jerry
 * Generated at: 2020/8/1 20:47
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description:
 */
fun StringBuilder.deleteLast() = this.deleteCharAt(length - 1)
fun String.mutate() = StringBuilder(this)

class KotlinCompat {
    companion object {

        @JvmStatic
        fun deleteLast(str: String) = str.mutate().deleteLast().toString()

        @JvmStatic
        fun mutableString(str: String) = str.mutate()

        @JvmStatic
        fun <T> apply(invoker: T, action: T.() -> Unit): T = invoker.apply(action)
    }
}

class LoggerConstants {
    companion object {
        const val EMPTY_STRING = ""
        const val PERIOD = "."
        const val LOG_DELIMITER = "#"
        const val LOG_BRACKET_LEFT = "("
        const val LOG_BRACKET_RIGHT = ")"
        const val PUSH = " -> "
        const val PATTERN_PARAM_ACTIVITY = "Activity %s"
        const val LOG_ACTIVITY = "Activity"
    }
}