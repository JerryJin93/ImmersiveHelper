package com.jerryjin.kit.utils;

import android.app.Activity;
import android.view.View;

import com.jerryjin.kit.utils.log.Logger;

import static com.jerryjin.kit.utils.log.Logger.ERR_NULL_ACTIVITY;
/**
 * Author: Jerry
 * Generated at: 2019/4/1 10:46 PM
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 1.0.2
 * Description:
 */
@SuppressWarnings("WeakerAccess")
public class ViewHelper {

    public static final String TAG = ViewHelper.class.getSimpleName();

    public static void addUiOptions(Activity activity, int... visibilities) {
        if (activity == null || visibilities == null || visibilities.length == 0) {
            return;
        }
        View decorView = activity.getWindow().getDecorView();
        // save origin system ui visibility to uiOptions.
        int uiOptions = decorView.getSystemUiVisibility();
        // set new system ui visibilities.
        setUiOptions(activity, visibilities);
        // get the set system ui visibilities.
        int setUiOptions = decorView.getSystemUiVisibility();
        // add origin and the set one.
        decorView.setSystemUiVisibility(uiOptions | setUiOptions);
    }

    public static void addUiOption(Activity activity, int option) {
        if (activity == null) {
            return;
        }
        if (option == 0) {
            Logger.i(TAG, "addUiOption");
            return;
        }
        View decorView = activity.getWindow().getDecorView();
        int previousSystemUiVisibility = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(previousSystemUiVisibility | option);
    }

    public static void setUiOptions(Activity activity, int... visibilities) {
        if (activity == null || visibilities == null || visibilities.length == 0) {
            return;
        }
        int uiOptions = 0;
        // 0000 | 0001 = 0001;
        for (int visibility : visibilities) {
            uiOptions |= visibility;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
    }

    public static void clearUiOption(Activity activity, int visibility) {
        if (activity == null) {
            Logger.e(TAG, "clearUiOption", ERR_NULL_ACTIVITY);
            return;
        }
        View decorView = activity.getWindow().getDecorView();
        int originVisibility = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(originVisibility & ~visibility);
    }

    public static void clearUiOptions(Activity activity, int... visibilities) {
        if (activity == null) {
            Logger.e(TAG, "clearUiOption", ERR_NULL_ACTIVITY);
            return;
        }
        for (int visibility : visibilities) {
            clearUiOption(activity, visibility);
        }
    }
}
