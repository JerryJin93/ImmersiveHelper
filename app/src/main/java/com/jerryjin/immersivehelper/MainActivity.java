package com.jerryjin.immersivehelper;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jerryjin.kit.ImmersiveHelper;
import com.jerryjin.kit.OptimizationType;
import com.jerryjin.kit.utils.navigationBar.NavigationBarHelper;

public class MainActivity extends AppCompatActivity {

    private Button top;

    private ImmersiveHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        String tag = MainActivity.class.getSimpleName();
        Log.e(tag, "isNavBarShow: " + NavigationBarHelper.isNavBarShow(this));

        // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // ViewHelper.clearUiOption(MainActivity.this, View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        }, 5000);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ImmersiveHelper.enableLogger(true);

        helper = new ImmersiveHelper(this)
                .setStatusBarMode(false)
                .setCallback(info -> {
                    if (info.hasNotch()) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) top.getLayoutParams();
                        params.topMargin = info.getNotchInfo().getNotchHeight();
                        top.setLayoutParams(params);
                    } else {

                    }
                }).setOptimizationType(OptimizationType.TYPE_IMMERSIVE);
        helper.optimize();
    }

    private void initViews() {
        top = findViewById(R.id.immersive_status_bar_with_img_on_top);
        top.setOnClickListener(v -> {
            // TODO: 2019/4/3
            helper.undoOptimization();
        });

        findViewById(R.id.immersive_status_bar_with_solid_color)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2019/4/3  
                    }
                });

        findViewById(R.id.cutout)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 2019/4/3  
                    }
                });
    }
}
