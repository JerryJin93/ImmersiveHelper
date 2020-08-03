package com.jerryjin.kit.manufacturer;

import android.app.Activity;
import android.text.TextUtils;

import com.jerryjin.kit.notch.AbsNotch;
import com.jerryjin.kit.utils.Constants;
import com.jerryjin.kit.utils.Utils;
import com.jerryjin.kit.utils.log.Logger;
import com.jerryjin.kit.utils.statusBar.StatusBarHelper;

import static com.jerryjin.kit.utils.log.Logger.ERR_NULL_ACTIVITY;
import static com.jerryjin.kit.utils.log.Logger.LOGGABLE;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 21:29
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description: OPPO手机刘海屏
 */
@SuppressWarnings("SpellCheckingInspection")
public class OppoNotch extends AbsNotch {

    private static final String TAG = "OppoNotch";

    @Override
    protected boolean hasNotchOreo(Activity activity) {
        return isNotchHardwareSupported(activity);
    }

    /**
     * OPPO不提供接口获取刘海尺寸，目前其有刘海屏的机型尺寸规格都是统一的。不排除以后机型会有变化。
     * 其显示屏宽度为1080px，高度为2280px。刘海区域则都是宽度为324px, 高度为80px;
     * int[0]值为刘海宽度 int[1]值为刘海高度
     */
    @Override
    protected int[] getNotchSpecOreo(Activity activity) {
        int[] notchSize = getNotchSpecFromSystemProperties();
        if (notchSize == null) {
            notchSize = new int[]{
                    324, //刘海宽度
                    StatusBarHelper.getStatusBarHeight(activity) //状态栏高度模拟刘海高度
            };
        }
        return notchSize;
    }

    /**
     * 判断OPPO手机的设置菜单里是否开启了刘海区域
     *
     * @param activity {@link Activity}
     * @return True: 已开启刘海 False: 未开启刘海
     */
    private boolean isNotchHardwareSupported(Activity activity) {
        final String methodName = "isNotchHardwareSupported";
        if (activity == null) {
            Logger.e(TAG, "", ERR_NULL_ACTIVITY);
            return false;
        }
        boolean isNotchHardwareSupported = activity.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
        final String param = LOGGABLE ? activity.getClass().getSimpleName() : Constants.EMPTY_STRING;
        final String paramStr = Utils.format("Activity %s", param);
        Logger.i(TAG, methodName, paramStr, "Notch on the current OPPO device is " + (isNotchHardwareSupported ? "enabled" : "disabled") + Constants.PERIOD);
        return isNotchHardwareSupported;
    }

    /**
     * 通过OPPO属性接口获取刘海参数
     * [ro.oppo.screen.heteromorphism]: [378,0:702,80]
     *
     * @return 刘海参数
     */
    private int[] getNotchSpecFromSystemProperties() {
        final String methodName = "getNotchSpecFromSystemProperties";
        String notchSpec = Utils.getStringFromSystemProperties("ro.oppo.screen.heteromorphism");
        if (TextUtils.isEmpty(notchSpec)) {
            Logger.e(TAG, methodName, "Cannot retrive notchSpec from system properties.");
            return null;
        }
        try {
            String[] split = notchSpec.replace("[", "").replace("]", "").split(":");
            if (split.length == 2) {
                String[] splitLeftTop = split[0].split(",");
                String[] splitRightBottom = split[1].split(",");
                int[] pointLeftTop = new int[]{Integer.parseInt(splitLeftTop[0]), Integer.parseInt(splitLeftTop[1])};
                int[] pointRightBottom = new int[]{Integer.parseInt(splitRightBottom[0]), Integer.parseInt(splitRightBottom[1])};
                int notchWidth = pointRightBottom[0] - pointLeftTop[0];
                int notchHeight = pointRightBottom[1] - pointLeftTop[1];
                return new int[]{notchWidth, notchHeight};
            }
        } catch (Exception e) {
            Logger.e(TAG, methodName, Utils.format("Exception e: %s", e.getMessage()));
        }
        return null;
    }

}
