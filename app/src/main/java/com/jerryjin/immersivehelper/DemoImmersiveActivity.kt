package com.jerryjin.immersivehelper

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.jerryjin.kit.interfaces.OptimizationCallback
import com.jerryjin.kit.utils.navigationBar.NavigationBarHelper
import kotlinx.android.synthetic.main.activity_demo_immersive.*

class DemoImmersiveActivity : BaseActivity() {

    companion object {
        private const val TAG = "DemoImmersiveActivity"
    }

    override fun getOptimizationCallback(): OptimizationCallback = OptimizationCallback {
        val params = main.layoutParams as FrameLayout.LayoutParams
        Log.e("ImmersiveHelper", it.toString())
        if (it.hasNotch()) {
            val textLayoutParams = text.layoutParams as ConstraintLayout.LayoutParams
            if (!it.isPortrait) {
                //params.topMargin = it.statusBarHeight
                textLayoutParams.also { params1 ->
                    if (it.isLandscapeLeftwards) {
                        params1.leftMargin = it.notchInfo.notchHeight
                        params1.rightMargin = it.navigationBarHeight
                    } else {
                        params1.leftMargin = it.navigationBarHeight
                        params1.rightMargin = it.notchInfo.notchHeight
                    }
                    text.layoutParams = params1
                }

            } else {
                params.topMargin = it.notchInfo.notchHeight
                textLayoutParams.leftMargin = 0
                textLayoutParams.rightMargin = 0
                text.layoutParams = textLayoutParams
            }
        } else {
            params.topMargin = it.statusBarHeight
        }
        Log.e(TAG, "NavigationBar is shown or not: " + it.isNavigationBarShown)
        main.layoutParams = params
    }

    override fun isStatusBarTextDark(): Boolean = false

    override fun getLayoutId(): Int = R.layout.activity_demo_immersive

    override fun initView() {
        window.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        supportActionBar?.hide()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        NavigationBarHelper.isNavBarShown(this) { isShowing, height->
            Log.e("ImmersiveHelper", "NaviShow2 $isShowing")
        }
    }
}