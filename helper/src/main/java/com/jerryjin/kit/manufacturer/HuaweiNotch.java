package com.jerryjin.kit.manufacturer;

import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.jerryjin.kit.notch.AbsNotch;
import com.jerryjin.kit.utils.StringHelper;
import com.jerryjin.kit.utils.Utils;
import com.jerryjin.kit.utils.log.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 21:23
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description: 华为手机刘海屏
 */
@SuppressWarnings("SpellCheckingInspection")
public class HuaweiNotch extends AbsNotch {

    private static final String TAG = "HuaweiNotch";

    /*刘海屏全屏显示FLAG*/
    public static final int FLAG_NOTCH = 0x00010000;

    @Override
    protected boolean hasNotchOreo(Activity activity) {
        return isNotchHardwareSupported(activity) && isNotchSettingEnabled(activity) && isAppNotchEnabled(activity);
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
        try {
            ClassLoader classLoader = activity.getClassLoader();
            Class<?> HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            notchSpec = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("InvocationTargetException e: %s.", e.getMessage()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("NoSuchMethodException e: %s.", e.getMessage()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("IllegalAccessException e: %s.", e.getMessage()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("ClassNotFoundException e: %s.", e.getMessage()));
        }
        return notchSpec;
    }

    @Override
    protected void applyNotchOreo(Activity activity) {
        final String methodName = "applyNotchOreo";
        final String param = StringHelper.getActivityAsParamForLogger(activity);
        if (!isNotchSettingEnabled(activity)) {
            Logger.i(TAG, methodName, param, StringHelper.format("The notch setting has not been enabled on current Huawei device %s.", Utils.getModel()));
            return;
        }

        Window window = activity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        try {
            Class<?> layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor<?> con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = con.newInstance(attributes);
            Method method = layoutParamsExCls.getMethod("addHwFlags", int.class);
            method.invoke(layoutParamsExObj, FLAG_NOTCH);
            Logger.i(TAG, methodName, param, StringHelper.format("We have applied notch on Huawei device %s.", Utils.getModel()));
        } catch (InstantiationException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("InstantiationException e: %s.", e.getMessage()));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("InvocationTargetException e: %s.", e.getMessage()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("NoSuchMethodException e: s%.", e.getMessage()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("IllegalAccessException e: %s.", e.getMessage()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("ClassNotFoundException e: %s.", e.getMessage()));
        }
    }

    @Override
    protected void recoverNotchOreo(Activity activity) {
        final String methodName = "recoverNotchOreo";
        final String param = StringHelper.getActivityAsParamForLogger(activity);
        Window window = activity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        try {
            Class<?> layoutParamsExCls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            Constructor<?> con = layoutParamsExCls.getConstructor(WindowManager.LayoutParams.class);
            Object layoutParamsExObj = con.newInstance(attributes);
            Method method = layoutParamsExCls.getMethod("clearHwFlags ", int.class);
            method.invoke(layoutParamsExObj, FLAG_NOTCH);
            Logger.i(TAG, methodName, param, StringHelper.format("We have recovered notch on Huawei device %s.", Utils.getModel()));
        } catch (InstantiationException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("InstantiationException e: %s.", e.getMessage()));
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("InvocationTargetException e: %s.", e.getMessage()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("NoSuchMethodException e: %s.", e.getMessage()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("IllegalAccessException e: %s.", e.getMessage()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("ClassNotFoundException e: %s.", e.getMessage()));
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

    /**
     * 设备硬件是否是刘海屏
     *
     * @param activity {@link Activity}
     * @return True: 支持刘海屏 False: 不支持
     */
    private boolean isNotchHardwareSupported(Activity activity) {
        final String methodName = "isNotchHardwareSupported";
        final String param = StringHelper.getActivityAsParamForLogger(activity);
        try {
            ClassLoader classLoader = activity.getClassLoader();
            Class<?> HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            boolean isNotchHardwareSupported = (boolean) get.invoke(HwNotchSizeUtil);
            if (isNotchHardwareSupported) {
                Logger.i(TAG, methodName, param, StringHelper.getNotchHardwareSupportedStatusMsgForLogger(Utils.getManufacturer(), Utils.getModel(), true));
                return true;
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("InvocationTargetException e: %s.", e.getMessage()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("NoSuchMethodException e: %s.", e.getMessage()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("IllegalAccessException e: %s.", e.getMessage()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("ClassNotFoundException e: %s.", e.getMessage()));
        }
        Logger.i(TAG, methodName, param, StringHelper.getNotchHardwareSupportedStatusMsgForLogger(Utils.getManufacturer(), Utils.getModel(), false));
        return false;
    }

    /**
     * 系统设置中是否开启了刘海区域使用
     *
     * @param activity {@link Activity}
     * @return True: 系统设置中开启了刘海区域的使用 False: 系统设置中未开启刘海区域的使用
     */
    private boolean isNotchSettingEnabled(Activity activity) {
        //判断刘海屏系统设置开关是否关闭“隐藏显示区域”
        final String DISPLAY_NOTCH_STATUS = "display_notch_status";
        // 0表示“默认开启”，1表示“隐藏显示区域”
        int isNotchSwitchOpen = Settings.Secure.getInt(activity.getContentResolver(), DISPLAY_NOTCH_STATUS, 0);
        boolean isSettingEnable = isNotchSwitchOpen == 0;
        Logger.i(TAG, "isSettingNotchEnable", StringHelper.getActivityAsParamForLogger(activity),
                StringHelper.getNotchStatusMsgForLogger(Utils.getManufacturer(), Utils.getModel(), isSettingEnable));
        return isSettingEnable;
    }

    /**
     * App是否开启了刘海区域使用
     *
     * @param activity {@link Activity}
     * @return True: 在App中开启了刘海区域的使用 False: 未开启刘海区域的使用
     */
    private boolean isAppNotchEnabled(Activity activity) {
        //优先判断华为手机程序控制，检查是否用用程序开启了刘海区域的使用
        final String methodName = "isSoftAppNotchEnable";
        final String param = StringHelper.getActivityAsParamForLogger(activity);
        try {
            WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
            Field hwFlagsField = attributes.getClass().getField("hwFlags");
            hwFlagsField.setAccessible(true);
            int hwFlags = (int) hwFlagsField.get(attributes);
            boolean isAppNotchEnabled = (hwFlags & FLAG_NOTCH) == FLAG_NOTCH;
            if (isAppNotchEnabled) {
                Logger.i(TAG, methodName, param, StringHelper.format("The current app on Huawei device %s hasn't applied notch.", Utils.getModel()));
                return true;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("IllegalAccessException e: %s.", e.getMessage()));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("NoSuchFieldException e: %s.", e.getMessage()));
        }
        Logger.i(TAG, methodName, param, StringHelper.format("The current app on Huawei device %s hasn't applied notch.", Utils.getModel()));
        return false;
    }
}
