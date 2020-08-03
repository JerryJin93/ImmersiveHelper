package com.jerryjin.kit.bean;

import android.content.res.Configuration;

import java.util.Objects;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 19:17
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
public class DecorationInfo {

    @Orientation
    private int orientation;
    private NotchInfo notchInfo;
    private boolean hasNotch;
    private int statusBarHeight;
    private int navigationBarHeight;

    private DecorationInfo(Builder builder) {
        this.orientation = builder.orientation;
        this.notchInfo = builder.notchInfo;
        this.hasNotch = notchInfo != null;
        this.statusBarHeight = builder.statusBarHeight;
        this.navigationBarHeight = builder.navigationBarHeight;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(@Orientation int orientation) {
        this.orientation = orientation;
    }

    public NotchInfo getNotchInfo() {
        return notchInfo;
    }

    public boolean hasNotch() {
        return hasNotch;
    }

    public int getStatusBarHeight() {
        return statusBarHeight;
    }

    public int getNavigationBarHeight() {
        return navigationBarHeight;
    }

    public boolean isLandscape() {
        return orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public boolean isPortrait() {
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public void resetNotchInfo() {
        this.notchInfo = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecorationInfo that = (DecorationInfo) o;
        return orientation == that.orientation &&
                hasNotch == that.hasNotch &&
                statusBarHeight == that.statusBarHeight &&
                navigationBarHeight == that.navigationBarHeight &&
                Objects.equals(notchInfo, that.notchInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orientation, notchInfo, hasNotch, statusBarHeight, navigationBarHeight);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return "DecorationInfo{" +
                "orientation=" + orientation +
                ", notchInfo=" + notchInfo +
                ", hasNotch=" + hasNotch +
                ", statusBarHeight=" + statusBarHeight +
                ", navigationBarHeight=" + navigationBarHeight +
                '}';
    }

    public static class Builder {

        @Orientation
        private int orientation;
        private NotchInfo notchInfo;
        private int statusBarHeight;
        private int navigationBarHeight;


        public Builder setOrientation(@Orientation int orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder setNotchInfo(NotchInfo notchInfo) {
            this.notchInfo = notchInfo;
            return this;
        }

        public Builder setStatusBarHeight(int statusBarHeight) {
            this.statusBarHeight = statusBarHeight;
            return this;
        }

        public Builder setNavigationBarHeight(int navigationBarHeight) {
            this.navigationBarHeight = navigationBarHeight;
            return this;
        }

        public DecorationInfo build() {
            return new DecorationInfo(this);
        }
    }
}
