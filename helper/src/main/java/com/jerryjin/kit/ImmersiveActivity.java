package com.jerryjin.kit;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jerryjin.kit.interfaces.OnOptimizeCallback;

/**
 * Author: Jerry
 * Generated at: 2019/4/10 10:37 PM
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
public abstract class ImmersiveActivity extends AppCompatActivity {

    protected ImmersiveHelper helper = new ImmersiveHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper.setCallback(getOptimizationCallback())
                .setStatusBarMode(isStatusBarTextLight())
                .setOptimizationType(getOptimizationType())
                .optimize();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        helper.notifyWindowFocusChanged();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        helper.notifyConfigurationChanged(newConfig);
    }

    protected abstract OnOptimizeCallback getOptimizationCallback();

    protected OptimizationType getOptimizationType() {
        return OptimizationType.TYPE_IMMERSIVE;
    }

    protected abstract boolean isStatusBarTextLight();

}
