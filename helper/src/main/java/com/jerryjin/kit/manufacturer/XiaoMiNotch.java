package com.jerryjin.kit.manufacturer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.jerryjin.kit.notch.AbsNotch;
import com.jerryjin.kit.utils.StringHelper;
import com.jerryjin.kit.utils.Utils;
import com.jerryjin.kit.utils.log.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 21:22
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description: Android P以下小米手机刘海屏适配
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

    /**
     * 0x00000100 | 0x00000200 竖屏绘制到耳朵区
     * 0x00000100 | 0x00000400 横屏绘制到耳朵区
     * 0x00000100 | 0x00000200 | 0x00000400 横竖屏都绘制到耳朵区
     */
    private static final int FLAG_NOTCH = 0x00000100 | 0x00000200 | 0x00000400;

    @Override
    protected void applyNotchOreo(Activity activity) {
        final String methodName = "applyNotchOreo";
        final String param = StringHelper.getActivityAsParamForLogger(activity);
        if (!isNotchSettingEnabled(activity)) {
            Logger.i(TAG, methodName, param, StringHelper.format("The notch setting has not been enabled on current XiaoMi device %s.", Utils.getModel()));
            return;
        }
        try {
            //noinspection JavaReflectionMemberAccess
            Method method = Window.class.getMethod("addExtraFlags", int.class);
            method.invoke(activity.getWindow(), FLAG_NOTCH);
            Logger.i(TAG, methodName, param, StringHelper.format("We have applied notch on XiaoMi device %s.", Utils.getModel()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("NoSuchMethodException e: %s.", e.getMessage()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("IllegalAccessException e: %s.", e.getMessage()));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("InvocationTargetException e: %s.", e.getMessage()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void applyNotchPie(Activity activity) {
        if (!isNotchSettingEnabled(activity)) {
            Logger.e(TAG, "applyNotchPie", StringHelper.getActivityAsParamForLogger(activity),
                    StringHelper.format("We cannot apply notch on current %s device %s since the notch setting has not been enabled.",
                            Utils.getManufacturer(), Utils.getModel()));
            return;
        }
        super.applyNotchPie(activity);
    }

    @Override
    protected void recoverNotchOreo(Activity activity) {
        final String methodName = "recoverNotchOreo";
        final String param = StringHelper.getActivityAsParamForLogger(activity);
        try {
            //noinspection JavaReflectionMemberAccess
            Method method = Window.class.getMethod("clearExtraFlags", int.class);
            method.invoke(activity.getWindow(), FLAG_NOTCH);
            Logger.i(TAG, methodName, param, StringHelper.format("We have recovered notch on XiaoMi device %s.", Utils.getModel()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("NoSuchMethodException e: %s.", e.getMessage()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("IllegalAccessException e: %s.", e.getMessage()));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("InvocationTargetException e: %s.", e.getMessage()));
        }
    }

    @Override
    protected boolean hasNotchOreo(Activity activity) {
        return isNotchSettingEnabled(activity) && isNotchHardwareSupported() && isAppNotchEnabled(activity);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected boolean hasNotchPie(Activity activity) {
        return super.hasNotchPie(activity) && isNotchSettingEnabled(activity);
    }

    @Override
    protected int[] getNotchSpecOreo(Activity activity) {
        final String methodName = "getNotchSpecOreo";
        final String param = StringHelper.getActivityAsParamForLogger(activity);
        int[] notchSpec = new int[]{0, 0};
        Resources resources = activity.getResources();
        try {
            //获取 Notch / 凹口 / 刘海 的高度和宽度（适用MIUI 8.6.26版本及以上）
            int widthResourceId = resources.getIdentifier("notch_width", "dimen", "android");
            if (widthResourceId > 0) {
                notchSpec[0] = resources.getDimensionPixelSize(widthResourceId);
            }
            int heightResourceId = resources.getIdentifier("notch_height", "dimen", "android");
            if (heightResourceId > 0) {
                notchSpec[1] = resources.getDimensionPixelSize(heightResourceId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("Exception e: %s.", e.getMessage()));
        }

        if (notchSpec[0] != 0 || notchSpec[1] != 0) {
            return notchSpec;
        }

        Logger.e(TAG, methodName, param, "We cannot get notch spec from the Resources object, so let's try another way...");

        //MIUI接口获取刘海参数失败后，通过具体设备获取
        switch (Utils.getModel()) {
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
            Class<?> cls = Class.forName("android.os.SystemProperties");
            Method getIntMethod = cls.getMethod("getInt", String.class, int.class);
            @SuppressWarnings("ConstantConditions")
            int invoke = (int) getIntMethod.invoke(null, "ro.miui.notch", 0);
            if (invoke == 1) {
                Logger.i(TAG, methodName, "The current XiaoMi device " + Build.MODEL + " has notch physically.");
                return true;
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, StringHelper.format("InvocationException e: %s.", e.getMessage()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, StringHelper.format("NoSuchMethodException e: %s.", e.getMessage()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, StringHelper.format("IllegalAccessException e: %s.", e.getMessage()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, StringHelper.format("ClassNotFoundException e: %s.", e.getMessage()));
        }
        Logger.i(TAG, methodName, "The current XiaoMi device " + Build.MODEL + " doesn't have notch physically.");
        return false;
    }

    /**
     * 系统设置中是否开启了刘海区域使用
     *
     * @param activity {@link Activity}
     * @return true: 在系统设置开启刘海屏的使用 false: 未开启刘海屏的使用
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private boolean isNotchSettingEnabled(Activity activity) {
        //系统设置是否支持刘海屏使用。0:开启，1:关闭
        boolean isNotchSettingEnabled = Settings.Global.getInt(activity.getContentResolver(), "force_black", 0) == 0;
        Logger.i(TAG, "isNotchSettingEnabled", StringHelper.getActivityAsParamForLogger(activity),
                StringHelper.getNotchStatusMsgForLogger(Utils.getManufacturer(), Utils.getModel(), isNotchSettingEnabled));
        return isNotchSettingEnabled;
    }

    /**
     * App中是否开启了刘海区域使用
     *
     * @param activity {@link Activity}
     * @return True: 在APP中开启了刘海区域的使用 False: 未开启刘海区域的使用
     */
    private boolean isAppNotchEnabled(Activity activity) {
        final String methodName = "isAppNotchEnabled";
        final String param = StringHelper.getActivityAsParamForLogger(activity);
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        try {
            Field extraFlagsField = attributes.getClass().getField("extraFlags");
            extraFlagsField.setAccessible(true);
            int extraFlags = (int) extraFlagsField.get(attributes);
            if ((extraFlags & FLAG_NOTCH) == FLAG_NOTCH) {
                Logger.i(TAG, methodName, param,
                        StringHelper.format("The current app on Xiaomi Device %s has applied notch.", Utils.getModel()));
                return true;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("NoSuchFieldException e: %s.", e.getMessage()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("IllegalAccessException e: %s.", e.getMessage()));
        }
        Logger.i(TAG, methodName, param,
                StringHelper.format("The current app on Xiaomi Device %s hasn't applied notch.", Utils.getModel()));
        return false;
    }

}
