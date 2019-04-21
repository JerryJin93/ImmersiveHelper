package com.jerryjin.kit;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.jerryjin.kit.navigationBar.NavigationBarHelper;
import com.jerryjin.kit.statusBar.OnOptimizeCallback;
import com.wcl.notchfit.args.NotchProperty;
import com.wcl.notchfit.core.OnNotchCallBack;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Author: Jerry
 * Generated at: 2019/4/10 10:37 PM
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version:
 * Description:
 */
public abstract class ImmersiveActivity extends AppCompatActivity {

    protected int navBarHeight;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getLayoutId());
    }

    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        navBarHeight = NavigationBarHelper.getNavBarHeight(this);
        if (getImmersiveType() == ImmersiveHelper.TYPE_IMAGE_ON_TOP) {
            ImmersiveHelper.optimize(this, new OnNotchCallBack() {
                @Override
                public void onNotchReady(NotchProperty notchProperty) {
                    if (notchProperty.isNotchEnable()) {
                        resolveNotch();
                    }
                }
            }, isStatusBarInDarkMode(), new OnOptimizeCallback() {
                @Override
                public void onOptimized(int statusBarHeight, boolean isNavBarShow, int navigationBarHeight) {
                    // TODO: 2019/4/10  
                }
            });
        } else {
            ImmersiveHelper.optimize(this, new OnNotchCallBack() {
                @Override
                public void onNotchReady(NotchProperty notchProperty) {
                    if (notchProperty.isNotchEnable()) {
                        resolveNotch();
                    }
                }
            }, getTopLayoutColor(), new OnOptimizeCallback() {
                @Override
                public void onOptimized(int statusBarHeight, boolean isNavBarShow, int navigationBarHeight) {
                    // TODO: 2019/4/10  
                }
            });
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (NavigationBarHelper.isNavBarShow(this) && ImmersiveHelper.hasNotch(this)) {
            resolveBottomInNotchMode();
        }
    }


    @ImmersiveType
    protected abstract int getImmersiveType();

    protected abstract int getTopLayoutColor();

    protected abstract boolean isStatusBarInDarkMode();

    protected abstract void resolveNotch();

    protected abstract void resolveBottomInNotchMode();

}
