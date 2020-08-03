package com.jerryjin.kit.notch;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.jerryjin.kit.interfaces.INotch;
import com.jerryjin.kit.utils.Utils;
import com.jerryjin.kit.utils.Constants;
import com.jerryjin.kit.utils.log.Logger;
import com.jerryjin.kit.bean.NotchInfo;

import java.util.List;

import static com.jerryjin.kit.utils.log.Logger.ERR_NULL_ACTIVITY;
import static com.jerryjin.kit.utils.log.Logger.LOGGABLE;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 20:47
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
public abstract class AbsNotch implements INotch {

    private static final String manufacturer = Utils.getManufacturer();

    private static final String MSG_NULL_WINDOW_INSETS = "Null window insets.";
    private static final String MSG_NO_NOTCH_DETECTED = "No notch detected.";
    protected static final int[] ZERO_NOTCH = {0, 0};

    private String tag;

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
    }

    protected abstract boolean hasNotchOreo(Activity activity);

    @RequiresApi(api = Build.VERSION_CODES.P)
    protected boolean hasNotchPie(Activity activity) {
        if (activity == null) {
            return false;
        }
        final String methodName = "hasNotchPie";
        final String pattern = "Activity %s";
        final String param = LOGGABLE ? activity.getClass().getSimpleName() : Constants.EMPTY_STRING;
        WindowInsets windowInsets = activity.getWindow().getDecorView().getRootWindowInsets();
        if (windowInsets == null) {
            Logger.e(tag, methodName, Utils.format(pattern, param), MSG_NO_NOTCH_DETECTED);
            return false;
        }
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout == null) {
            Logger.i(tag, methodName, Utils.format(pattern, param), MSG_NO_NOTCH_DETECTED);
            return false;
        }
        return hasCutout(displayCutout.getBoundingRects());
    }

    protected abstract int[] getNotchSpecOreo(Activity activity);

    @RequiresApi(api = Build.VERSION_CODES.P)
    protected int[] getNotchSpecPie(Activity activity) {
        int[] notchSize = ZERO_NOTCH;
        String methodName = "getNotchSpecPie";
        if (activity == null) {
            Logger.e(tag, methodName, ERR_NULL_ACTIVITY);
            return notchSize;
        }
        WindowInsets windowInsets = activity.getWindow().getDecorView().getRootWindowInsets();
        String param = LOGGABLE ? activity.toString() : Constants.EMPTY_STRING;
        String pattern = "Activity %s";
        if (windowInsets == null) {
            Logger.i(tag, methodName, Utils.format(pattern, param), "Null WindowInsets, the DecorView hasn't been attached to Window yet.");
            return notchSize;
        }
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if (displayCutout == null) {
            Logger.i(tag, methodName, Utils.format(pattern, param), MSG_NO_NOTCH_DETECTED);
            return notchSize;
        }

        List<Rect> boundingRectList = displayCutout.getBoundingRects();
        if (!hasCutout(boundingRectList)) {
            Logger.i(tag, methodName, Utils.format(pattern, param), MSG_NO_NOTCH_DETECTED);
            return notchSize;
        }

        Rect rect = boundingRectList.get(0);
        notchSize[0] = rect.width();
        notchSize[1] = rect.height();
        return notchSize;
    }

    @Override
    public NotchInfo obtainNotch(Activity activity) {
        if (!hasNotch(activity)) return null;
        NotchInfo notchInfo = new NotchInfo();
        notchInfo.setManufacturer(manufacturer);
        int[] notchSpec;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            notchSpec = getNotchSpecPie(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notchSpec = getNotchSpecOreo(activity);
        } else {
            return null;
        }
        notchInfo.setNotchWidth(notchSpec[0]);
        notchInfo.setNotchHeight(notchSpec[1]);
        return notchInfo;
    }

    private boolean hasCutout(List<Rect> boundRectList) {
        if (boundRectList == null) {
            return false;
        }
        if (boundRectList.isEmpty()) {
            return false;
        }
        for (Rect rect : boundRectList) {
            if (rect.left != 0 || rect.top != 0 || rect.right != 0 || rect.bottom != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasNotch(Activity activity) {
        if (activity == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return hasNotchPie(activity);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return hasNotchOreo(activity);
        } else {
            return false;
        }
    }
}
