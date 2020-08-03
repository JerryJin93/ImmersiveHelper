package com.jerryjin.kit.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * Author: Jerry
 * Generated at: 2020/8/3 15:28
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
public class ImmersiveLifecycleObserver implements DefaultLifecycleObserver {

    private ImmersiveLifecycle immersiveLifecycle;

    public ImmersiveLifecycleObserver(ImmersiveLifecycle immersiveLifecycle) {
        this.immersiveLifecycle = immersiveLifecycle;
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        if (immersiveLifecycle != null) {
            immersiveLifecycle.dispose();
        }
    }
}
