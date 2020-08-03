package com.jerryjin.kit.bean;

import android.content.res.Configuration;

import androidx.annotation.IntDef;

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
@IntDef({Configuration.ORIENTATION_UNDEFINED, Configuration.ORIENTATION_PORTRAIT, Configuration.ORIENTATION_LANDSCAPE})
public @interface Orientation {
}
