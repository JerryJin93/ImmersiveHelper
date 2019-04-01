package com.jerryjin.immersivehelper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.jerryjin.kit.ViewHelper;
import com.jerryjin.kit.navigationBar.NavigationBarHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String tag = MainActivity.class.getSimpleName();
        Log.e(tag, "isNavBarShow: " + NavigationBarHelper.isNavBarShow(this));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ViewHelper.clearUiOption(MainActivity.this, View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        }, 5000);
    }
}
