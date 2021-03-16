package com.jerryjin.immersivehelper

import android.os.Bundle
import com.jerryjin.kit.ImmersiveActivity

/**
 * Author: Jerry
 * Generated at: 2020/8/4 21:17
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description:
 */
abstract class BaseActivity : ImmersiveActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (initArgs(intent.extras)) {
            initWindow()
            setContentView(getLayoutId())
            initView()
        } else {
            finish()
        }
    }

    protected open fun initArgs(args: Bundle?) = true
    protected open fun initWindow() {}
    protected open fun initView() {}
    abstract fun getLayoutId(): Int
}