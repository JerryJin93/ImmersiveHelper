package com.jerryjin.kit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;

import static com.jerryjin.kit.ImmersiveHelper.TYPE_IMAGE_ON_TOP;
import static com.jerryjin.kit.ImmersiveHelper.TYPE_NAVIGATION_LAYOUT_ON_TOP;

/**
 * Author: Jerry
 * Generated at: 2019/4/10 10:44 PM
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version:
 * Description:
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@IntDef({TYPE_IMAGE_ON_TOP, TYPE_NAVIGATION_LAYOUT_ON_TOP})
public @interface ImmersiveType {
}
