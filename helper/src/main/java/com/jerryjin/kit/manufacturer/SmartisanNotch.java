package com.jerryjin.kit.manufacturer;

import android.app.Activity;

import com.jerryjin.kit.notch.AbsNotch;
import com.jerryjin.kit.utils.Utils;
import com.jerryjin.kit.utils.log.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author: Jerry
 * Generated at: 2020/8/3 18:52
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version:
 * Description: 锤子手机刘海屏
 */
@SuppressWarnings("SpellCheckingInspection")
public class SmartisanNotch extends AbsNotch {

    private static final String TAG = "SmartisanNotch";

    @Override
    protected boolean hasNotchOreo(Activity activity) {
        return isNotchHardwareSupported();
    }

    protected boolean isNotchHardwareSupported() {
        final String methodName = "isNotchHardwareSupported";
        boolean supportNotch = false;
        try {
            Class<?> DisplayUtilsSmt = Class.forName("smartisanos.api.DisplayUtilsSmt");
            Method isFeatureSupport = DisplayUtilsSmt.getMethod("isFeatureSupport", int.class);
            supportNotch = (boolean) isFeatureSupport.invoke(DisplayUtilsSmt, 0x00000001);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, Utils.format("ClassNotFoundException e: %s", e.getMessage()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, Utils.format("NoSuchMethodException e: %s", e.getMessage()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, Utils.format("IllegalAccessException e: %s", e.getMessage()));
        } catch (InvocationTargetException e) {
            Logger.e(TAG, methodName, Utils.format("InvocationTargetException e: %s", e.getMessage()));
            e.printStackTrace();
        }
        Logger.i(TAG, methodName, String.valueOf(supportNotch));
        return supportNotch;
    }

    @Override
    protected int[] getNotchSpecOreo(Activity activity) {
        return new int[]{
                82, //刘海宽 px
                104 //刘海高 px
        };
    }
}