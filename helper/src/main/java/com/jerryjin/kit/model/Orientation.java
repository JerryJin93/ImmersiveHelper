package com.jerryjin.kit.model;

import androidx.annotation.IntDef;

import com.jerryjin.kit.OrientationSpec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Jerry
 * Generated at: 2020/8/3 16:19
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@IntDef({OrientationSpec.PORTRAIT, OrientationSpec.LANDSCAPE, OrientationSpec.LANDSCAPE_REVERSED, OrientationSpec.PORTRAIT_REVERSED})
public @interface Orientation {
}
