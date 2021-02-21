package com.jerryjin.kit.model;

import android.graphics.Rect;

import androidx.annotation.Nullable;

import java.util.Objects;

/**
 * Author: Jerry
 * Generated at: 2020/8/1 19:42
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: AcornLake
 * Version: 2.0.0
 * Description:
 */
public class NotchInfo {

    @Nullable
    private final Rect notchRect;
    private final int notchWidth;
    private final int notchHeight;

    public NotchInfo(@Nullable Rect notchRect, int notchWidth, int notchHeight) {
        this.notchRect = notchRect;
        this.notchWidth = notchWidth;
        this.notchHeight = notchHeight;
    }

    public int getNotchWidth() {
        return notchWidth;
    }

    public int getNotchHeight() {
        return notchHeight;
    }

    @Nullable
    public Rect getNotchRect() {
        return notchRect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotchInfo notchInfo = (NotchInfo) o;
        return notchWidth == notchInfo.notchWidth &&
                notchHeight == notchInfo.notchHeight &&
                Objects.equals(notchRect, notchInfo.notchRect);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notchRect, notchWidth, notchHeight);
    }

    @Override
    public String toString() {
        return "NotchInfo{" +
                "notchRect=" + notchRect +
                ", notchWidth=" + notchWidth +
                ", notchHeight=" + notchHeight +
                '}';
    }
}
