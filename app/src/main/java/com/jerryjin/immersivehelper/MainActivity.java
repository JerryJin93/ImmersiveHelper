package com.jerryjin.immersivehelper;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;

import com.jerryjin.kit.interfaces.OptimizationCallback;
import com.jerryjin.kit.utils.log.Logger;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    private View mainLayout;
    private Button top;

    @Override
    protected void initView() {
        super.initView();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mainLayout = findViewById(R.id.main);
        top = findViewById(R.id.immersive);
        top.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DemoImmersiveActivity.class));
        });

        findViewById(R.id.fullscreen)
                .setOnClickListener(v -> {
                    startActivity(new Intent(MainActivity.this, DemoFullscreenActivity.class));
                });
    }


    @Override
    protected OptimizationCallback getOptimizationCallback() {
        return info -> {
            LinearLayout.LayoutParams paramsTopButton = (LinearLayout.LayoutParams) top.getLayoutParams();
            int offset = info.hasNotch() ? info.getNotchInfo().getNotchHeight() : info.getStatusBarHeight();
            int navBarOffset = info.getNavigationBarHeight();
            Logger.i(TAG, "callback", info.toString());
            if (info.isPortrait()) {
                mainLayout.setPadding(0, 0, 0, 0);
            } else if (info.isLandscapeLeftwards()) {
                mainLayout.setPadding(offset, 0, navBarOffset, 0);
            } else if (info.isReversedLandscape()) {
                mainLayout.setPadding(navBarOffset, 0, offset, 0);
            } else if (info.isPortraitUpSideDown()) {
                mainLayout.setPadding(0, 0, 0, offset);
            }
            if (info.hasNotch()) {
                if (!info.isPortrait()) {
                    paramsTopButton.topMargin = info.getStatusBarHeight();
                } else {
                    paramsTopButton.topMargin = info.getNotchInfo().getNotchHeight();
                }
            } else {
                paramsTopButton.topMargin = info.getStatusBarHeight();
            }
            top.setLayoutParams(paramsTopButton);
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isStatusBarTextDark() {
        return true;
    }
}
