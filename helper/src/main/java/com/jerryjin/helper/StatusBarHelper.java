package com.ci123.babyweekend.tags;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Author: Jerry
 * Generated at: 2019/3/13 9:58
 * WeChat: enGrave93
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class StatusBarHelper {

    public static void setStatusBarBackgroundColor(Activity activity, int backgroundColor) {
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(backgroundColor);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTransparentStatusBar(activity);
            SystemBarTintManager tintManager = new SystemBarTintManager(activity);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(backgroundColor);
            fitViews(activity);
        }
    }

    public static void setTransparentStatusBar(Activity activity) {
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    // 需要判断，在Android M版本以下是无法改变状态栏字体的颜色的，这时候只能调节状态栏颜色或顶部导航栏颜色以表现出沉浸状态栏的效果*
    @TargetApi(Build.VERSION_CODES.M)
    public static void toggleStatusBarTextColor(Activity activity, boolean darkMode) {
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = activity.getWindow().getDecorView();
            decor.setSystemUiVisibility(darkMode ? View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR : View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    private static void fitViews(Activity activity) {
        if (activity == null) {
            return;
        }
        ViewGroup parent = activity.findViewById(android.R.id.content);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                child.setFitsSystemWindows(true);
                ((ViewGroup) child).setClipToPadding(true);
            }
        }
    }
}
