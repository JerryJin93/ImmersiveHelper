package com.jerryjin.kit.manufacturer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.view.Window;

import com.jerryjin.kit.notch.AbsNotch;
import com.jerryjin.kit.utils.log.Logger;
import com.wcl.notchfit.utils.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 21:22
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description: 小米手机刘海屏
 */
@SuppressWarnings("SpellCheckingInspection")
public class XiaoMiNotch extends AbsNotch {

    private static final String TAG = "XiaoMiNotch";

    private static final String MI_8 = "MI 8";
    private static final String MI_8_SE = "MI 8 SE";
    private static final String MI_8_EXPLORER_EDITION = "MI 8 Explorer Edition";
    private static final String MI_8_UD = "MI 8 UD";
    private static final String MI_8_LITE = "MI8Lite";
    private static final String POCO_F1 = "POCO F1";
    private static final String RED_MI_6_PRO = "Redmi 6 Pro";

    private static final int FLAG_NOTCH = 0x00000100 | 0x00000200 | 0x00000400;

    @Override
    protected void applyNotchOreo(Activity activity) {
        if (!isSettingNotchEnable(activity)) {
            Logger.i(TAG, "applyNotchOreo", "");
            return;
        }
        try {
            //noinspection JavaReflectionMemberAccess
            Method method = Window.class.getMethod("addExtraFlags",
                    int.class);
            method.invoke(activity.getWindow(), FLAG_NOTCH);
        } catch (Exception e) {
            LogUtils.i("xiaomi addExtraFlags not found.");
        }
    }

    @Override
    protected void recoverNotchOreo(Activity activity) {
        try {
            //noinspection JavaReflectionMemberAccess
            Method method = Window.class.getMethod("clearExtraFlags ",
                    int.class);
            method.invoke(activity.getWindow(), FLAG_NOTCH);
        } catch (Exception e) {
            LogUtils.i("xiaomi clearExtraFlags not found.");
        }
    }

    @Override
    protected boolean hasNotchOreo(Activity activity) {
        return isSettingNotchEnable(activity) && isNotchHardwareSupported() && isSoftAppNotchEnable(activity);
    }

    @Override
    protected int[] getNotchSpecOreo(Activity activity) {
        int[] notchSpec = new int[]{0, 0};
        try {
            //获取 Notch / 凹口 / 刘海 的高度和宽度（适用MIUI 8.6.26版本及以上）
            int widthResourceId = activity.getResources().getIdentifier("notch_width", "dimen", "android");
            if (widthResourceId > 0) {
                notchSpec[0] = activity.getResources().getDimensionPixelSize(widthResourceId);
            }
            int heightResourceId = activity.getResources().getIdentifier("notch_height", "dimen", "android");
            if (heightResourceId > 0) {
                notchSpec[1] = activity.getResources().getDimensionPixelSize(heightResourceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (notchSpec[0] != 0 || notchSpec[1] != 0) {
            return notchSpec;
        }

        //MIUI接口获取刘海参数失败后，通过具体设备获取
        switch (Build.MODEL) {
            case MI_8:
            case MI_8_EXPLORER_EDITION:
            case MI_8_UD:
                injectNotchSpec(notchSpec, 560, 89);
                break;
            case MI_8_SE:
                injectNotchSpec(notchSpec, 540, 85);
                break;
            case MI_8_LITE:
                injectNotchSpec(notchSpec, 296, 82);
                break;
            case POCO_F1:
                injectNotchSpec(notchSpec, 588, 86);
                break;
            case RED_MI_6_PRO:
                injectNotchSpec(notchSpec, 352, 89);
                break;
        }
        return notchSpec;
    }

    private void injectNotchSpec(int[] notchSpec, int width, int height) {
        if (notchSpec == null) {
            Logger.e(TAG, "injectNotchSpec", "Null given array.");
            return;
        }
        notchSpec[0] = width;
        notchSpec[1] = height;
    }

    protected boolean isNotchHardwareSupported() {
        final String methodName = "isNotchHardwareSupported";
        try {
            @SuppressLint("PrivateApi")
            Class<?> aClass = Class.forName("android.os.SystemProperties");
            Method getInt = aClass.getMethod("getInt", String.class, int.class);
            int invoke = (int) getInt.invoke(null, "ro.miui.notch", 0);
            if (invoke == 1) {
                Logger.i(TAG, methodName, "The current XiaoMi device " + Build.MODEL + " has notch physically.");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, e.getMessage());
        }
        Logger.i(TAG, methodName, "The current XiaoMi device " + Build.MODEL + " doesn't have notch physically.");
        return false;
    }

    /**
     * 系统设置中是否开启了刘海区域使用
     *
     * @param activity {@link Activity}
     * @return true: 在系统设置开启刘海屏的使用 false: 在系统设置中未开启刘海屏的使用
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean isSettingNotchEnable(Activity activity) {
        //系统设置是否支持刘海屏使用。0:开启，1:关闭
        return Settings.Global.getInt(activity.getContentResolver(), "force_black", 0) == 0;
    }

    /**
     * app中是否开启了刘海区域使用
     *
     * @param activity {@link Activity}
     * @return
     */
    private boolean isSoftAppNotchEnable(Activity activity) {
        try {
            Field extraFlagsField = activity.getWindow().getAttributes().getClass().getField("extraFlags");
            extraFlagsField.setAccessible(true);
            int extraFlags = (int) extraFlagsField.get(activity.getWindow().getAttributes());
            if ((extraFlags & FLAG_NOTCH) == FLAG_NOTCH) {
                return true;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

}
