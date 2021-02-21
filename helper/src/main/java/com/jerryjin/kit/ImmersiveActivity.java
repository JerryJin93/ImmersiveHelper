package com.jerryjin.kit;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jerryjin.kit.interfaces.OptimizationCallback;
import com.jerryjin.kit.utils.KotlinCompat;

import org.jetbrains.annotations.NotNull;

/**
 * Author: Jerry
 * Generated at: 2019/4/10 10:37 PM
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description:
 */
public abstract class ImmersiveActivity extends AppCompatActivity {

    protected ImmersiveHelper helper = new ImmersiveHelper(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KotlinCompat.apply(helper.setCallback(getOptimizationCallback()), helper -> {
            onPreOptimize(helper.setStatusBarDarkMode(isStatusBarTextDark())
                    .setOptimizationType(getOptimizationType()));
            return null;
        }).optimize();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        helper.notifyWindowFocusChanged();
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        helper.notifyConfigurationChanged(newConfig);
    }

    protected abstract OptimizationCallback getOptimizationCallback();

    protected OptimizationType getOptimizationType() {
        return OptimizationType.TYPE_IMMERSIVE;
    }

    /**
     * Get the text color of status bar.
     *
     * @return True if the text is in dark color, false otherwise.
     */
    protected boolean isStatusBarTextDark() {
        return false;
    }

    protected void onPreOptimize(ImmersiveHelper helper) {
    }

}
