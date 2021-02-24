package com.jerryjin.immersivehelper

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import com.jerryjin.kit.OptimizationType
import com.jerryjin.kit.interfaces.OptimizationCallback
import com.jerryjin.kit.utils.log.Logger
import com.jerryjin.kit.utils.navigationBar.NavigationBarHelper
import kotlinx.android.synthetic.main.activity_demo_fullscreen.*

class DemoFullscreenActivity : BaseActivity() {

    companion object {
        private const val TAG = "DemoFullScreenActivity"
    }

    override fun initWindow() {
        super.initWindow()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
    }

    override fun getLayoutId(): Int = R.layout.activity_demo_fullscreen

    override fun getOptimizationType(): OptimizationType = OptimizationType.TYPE_FULLSCREEN

    override fun getOptimizationCallback(): OptimizationCallback = OptimizationCallback {
        val titleParams = text_title.layoutParams as ConstraintLayout.LayoutParams
        if (it.hasNotch()) {
            if (!it.isPortrait) {
                titleParams.topMargin = it.statusBarHeight
            } else {
                titleParams.topMargin = it.notchInfo.notchHeight
            }
        } else {
            titleParams.topMargin = it.statusBarHeight
        }
        Logger.i(TAG, "NavigationBar is shown or not: " + it.isNavigationBarShown)
        text_title.layoutParams = titleParams
    }

    override fun isStatusBarTextDark(): Boolean = false

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        NavigationBarHelper.isNavBarShown(this) { isShowing, height ->
            Log.e("ImmersiveHelper", "NaviShow2 $isShowing")
        }
    }
}