package com.jerryjin.kit.notch;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.jerryjin.kit.model.NotchInfo;
import com.jerryjin.kit.OrientationSpec;
import com.jerryjin.kit.interfaces.INotch;
import com.jerryjin.kit.utils.LoggerConstants;
import com.jerryjin.kit.utils.OrientationHelper;
import com.jerryjin.kit.utils.StringHelper;
import com.jerryjin.kit.utils.log.Logger;

import java.util.List;

import static com.jerryjin.kit.utils.log.Logger.ERR_NULL_ACTIVITY;
import static com.jerryjin.kit.utils.log.Logger.LOGGABLE;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 20:47
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description:
 */
public abstract class AbsNotch implements INotch {

    private static final String MSG_NULL_WINDOW_INSETS = "Null window insets.";
    private static final String MSG_NO_NOTCH_DETECTED = "No notch detected.";
    protected static final int[] ZERO_NOTCH = {0, 0};

    private boolean isNotchApplied;
    private final String tag;

    public AbsNotch() {
        this.tag = getClass().getSimpleName();
    }

    @Override
    public final void applyNotch(Activity activity, boolean enable) {
        if (enable) {
            applyNotchImpl(activity);
        } else {
            recoverNotchImpl(activity);
        }
    }

    private void applyNotchImpl(Activity activity) {
        if (activity == null) {
            Logger.e(tag, "applyNotchImpl", ERR_NULL_ACTIVITY);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            applyNotchPie(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            applyNotchOreo(activity);
        }
    }

    private void recoverNotchImpl(Activity activity) {
        if (activity == null) {
            Logger.e(tag, "recoverNotchImpl", ERR_NULL_ACTIVITY);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            recoverNotchPie(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            recoverNotchOreo(activity);
        }
    }

    protected void applyNotchOreo(Activity activity) {
    }

    protected void recoverNotchOreo(Activity activity) {
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    protected void applyNotchPie(Activity activity) {
        if (activity == null) {
            Logger.e(tag, "applyNotchPie", ERR_NULL_ACTIVITY);
            return;
        }
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        activity.getWindow().setAttributes(lp);
        isNotchApplied = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    protected void recoverNotchPie(Activity activity) {
        if (activity == null) {
            Logger.e(tag, "recoverNotchPie", ERR_NULL_ACTIVITY);
            return;
        }
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER;
        activity.getWindow().setAttributes(lp);
        isNotchApplied = false;
    }

    protected abstract boolean hasNotchOreo(Activity activity);

    @RequiresApi(api = Build.VERSION_CODES.P)
    protected boolean hasNotchPie(Activity activity) {
        final String methodName = "hasNotchPie";
        if (activity == null) {
            Logger.e(tag, methodName, ERR_NULL_ACTIVITY);
            return false;
        }
        final String pattern = "Activity %s";
        final String param = LOGGABLE ? activity.getClass().getSimpleName() : LoggerConstants.EMPTY_STRING;
        WindowInsets windowInsets = activity.getWindow().getDecorView().getRootWindowInsets();
        if (windowInsets == null) {
            Logger.e(tag, methodName, StringHelper.format(pattern, param), MSG_NULL_WINDOW_INSETS);
            return false;
        }
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout == null) {
            Logger.e(tag, methodName, StringHelper.format(pattern, param), MSG_NO_NOTCH_DETECTED);
            return false;
        }
        Logger.d(tag, methodName, StringHelper.format(pattern, param), "Going to figure out whether there is any cutout or not.");
        return hasCutout(displayCutout.getBoundingRects());
    }

    protected abstract int[] getNotchSpecOreo(Activity activity);

    @RequiresApi(api = Build.VERSION_CODES.P)
    protected Rect getNotchSpecPie(Activity activity) {
        Rect notchSpec = new Rect();
        String methodName = "getNotchSpecPie";
        if (activity == null) {
            Logger.e(tag, methodName, ERR_NULL_ACTIVITY);
            return notchSpec;
        }
        WindowInsets windowInsets = activity.getWindow().getDecorView().getRootWindowInsets();
        final String param = StringHelper.getActivityAsParamForLogger(activity);
        if (windowInsets == null) {
            Logger.i(tag, methodName, param, "Null WindowInsets, the DecorView hasn't been attached to Window yet.");
            return notchSpec;
        }
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout == null) {
            Logger.i(tag, methodName, param, MSG_NO_NOTCH_DETECTED);
            return notchSpec;
        }

        List<Rect> boundingRectList = displayCutout.getBoundingRects();
        Logger.d(tag, methodName, param, "Going to figure out whether there is any cutout or not.");

        if (!hasCutout(boundingRectList)) {
            Logger.i(tag, methodName, param, MSG_NO_NOTCH_DETECTED);
            return notchSpec;
        }

        notchSpec = boundingRectList.get(0);
        Logger.i(tag, methodName, param, notchSpec.toShortString());
        return notchSpec;
    }

    @Override
    public NotchInfo obtainNotch(Activity activity) {
        final String methodName = "obtainNotch";
        final String param = StringHelper.getActivityAsParamForLogger(activity);

        if (!hasNotch(activity)) {
            Logger.e(tag, methodName, param, "No notch detected.");
            return null;
        }

        int[] notchSpec = new int[]{0, 0};
        Rect rect = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            rect = getNotchSpecPie(activity);
            notchSpec[0] = rect.width();
            notchSpec[1] = rect.height();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notchSpec = getNotchSpecOreo(activity);
        }
        Logger.i(tag, methodName, param, StringHelper.format("The notch spec is: width = %d, height = %d.", notchSpec[0], notchSpec[1]));
        int orientation = OrientationHelper.getCurrentOrientation(activity);
        boolean isPortrait = (orientation == OrientationSpec.PORTRAIT || orientation == OrientationSpec.PORTRAIT_REVERSED);
        Logger.i(tag, methodName, param, "portrait = " + isPortrait);
        return new NotchInfo(rect, isPortrait ? notchSpec[0] : notchSpec[1], isPortrait ? notchSpec[1] : notchSpec[0]);
    }

    private boolean hasCutout(List<Rect> boundRectList) {
        final String methodName = "hasCutout";
        if (boundRectList == null) {
            return false;
        }
        if (boundRectList.isEmpty()) {
            return false;
        }
        for (Rect rect : boundRectList) {
            Logger.i(tag, methodName, rect.toShortString());
            if (rect.left != 0 || rect.top != 0 || rect.right != 0 || rect.bottom != 0) {
                Logger.i(tag, methodName, "We got notch here.");
                return true;
            }
        }
        Logger.i(tag, methodName, "There is not any notch has been found.");
        return false;
    }

    @Override
    public boolean hasNotch(Activity activity) {
        final String methodName = "hasNotch";
        if (activity == null) {
            Logger.e(tag, methodName, ERR_NULL_ACTIVITY);
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return hasNotchPie(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return hasNotchOreo(activity);
        } else {
            Logger.i(tag, methodName, StringHelper.format("Activity %s", activity.getClass().getSimpleName()),
                    "Current OS version is under Android O, we assert there is no Notch available.");
            return false;
        }
    }

    protected void notifyNotchStatus(boolean applied) {
        this.isNotchApplied = applied;
    }

    @Override
    public boolean isNotchApplied() {
        return isNotchApplied;
    }
}
