package com.jerryjin.kit.utils

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast

/**
 * Author: Jerry
 * Generated at: 2020/8/4 21:15
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description:
 */
class MainHandlerGlobal {
    private var mainHandler = Handler(Looper.getMainLooper())
    private lateinit var application: Application

    private val toastAction: (String, Int) -> Runnable = { msg, duration ->
        Runnable {
            if (this@MainHandlerGlobal::application.isInitialized)
                Toast.makeText(application, msg, duration).show()
        }
    }

    fun post(action: Runnable) = mainHandler.post(action)

    fun postDelayed(millis: Long, action: Runnable) = mainHandler.postDelayed(action, millis)

    fun sendMessage(msg: Message) = mainHandler.sendMessage(msg)

    fun sendMessageAtTime(msg: Message, upTimeMillis: Long) =
            mainHandler.sendMessageAtTime(msg, upTimeMillis)

    fun sendMessageAtFrontOfQueue(msg: Message) = mainHandler.sendMessageAtFrontOfQueue(msg)

    fun toast(msg: String) = mainHandler.post(toastAction(msg, LENGTH_SHORT))

    fun toast(msg: String, duration: Int) = mainHandler.post(toastAction(msg, duration))

    fun setMainDelegate(delegate: Handler) {
        if (delegate.looper.thread == Looper.getMainLooper().thread) {
            this.mainHandler = delegate
        }
    }

    companion object {

        private const val TAG = "MainHandlerGlobal"
        const val LENGTH_SHORT = Toast.LENGTH_SHORT
        const val LENGTH_LONG = Toast.LENGTH_LONG


        private val instance by lazy { MainHandlerGlobal() }

        fun initialize(application: Application) {
            instance.application = application
        }

        fun retrieve() = instance
    }
}