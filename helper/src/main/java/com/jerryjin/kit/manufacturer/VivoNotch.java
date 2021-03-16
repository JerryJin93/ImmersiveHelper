package com.jerryjin.kit.manufacturer;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.jerryjin.kit.notch.AbsNotch;
import com.jerryjin.kit.utils.ScreenUtils;
import com.jerryjin.kit.utils.StringHelper;
import com.jerryjin.kit.utils.Utils;
import com.jerryjin.kit.utils.log.Logger;

import java.lang.reflect.Method;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 21:30
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description: Vivo手机刘海屏 OK
 */
@SuppressWarnings("SpellCheckingInspection")
public class VivoNotch extends AbsNotch {

    private static final String TAG = "VivoNotch";

    private static final int VIVO_NOTCH = 0x00000020; //是否有刘海
    private static final int VIVO_FILLET = 0x00000008; //是否有圆角

    protected boolean isNotchHardwareSupported(Activity activity) {
        boolean isNotchHardwareSupported = false;
        final String methodName = "isNotchHardwareSupported";
        final String param = StringHelper.getActivityAsParamForLogger(activity);
        try {
            ClassLoader classLoader = activity.getClassLoader();
            @SuppressLint("PrivateApi")
            Class<?> FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            //noinspection ConstantConditions
            isNotchHardwareSupported = (boolean) method.invoke(FtFeature, VIVO_NOTCH) | (boolean) method.invoke(FtFeature, VIVO_FILLET);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("ClassNotFoundException e: %s.", e.getMessage()));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("NoSuchMethodException e: %s.", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, methodName, param, StringHelper.format("Exception e: %s.", e.getMessage()));
        }
        Logger.i(TAG, methodName, param,
                StringHelper.getNotchHardwareSupportedStatusMsgForLogger(Utils.getManufacturer(), Utils.getModel(), isNotchHardwareSupported));
        return isNotchHardwareSupported;
    }

    @Override
    protected boolean hasNotchOreo(Activity activity) {
        return isNotchHardwareSupported(activity);
    }

    @Override
    protected int[] getNotchSpecOreo(Activity activity) {
        return new int[]{
                ScreenUtils.dp2intPx(activity, 100), //刘海宽度
                ScreenUtils.dp2intPx(activity, 27) //刘海高度
        };
    }
}
