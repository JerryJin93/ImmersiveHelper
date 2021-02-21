package com.jerryjin.immersivehelper;

import android.app.Application;

import com.jerryjin.kit.ImmersiveHelper;

/**
 * Author: Jerry
 * Generated at: 2020/8/4 18:05
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description:
 */
public class App extends Application {

    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ImmersiveHelper.enableLogger(true);
    }
}
