package com.jerryjin.kit.model;

import com.jerryjin.kit.OrientationSpec;
import com.jerryjin.kit.utils.OrientationHelper;

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
public class SystemUIInfo {

    @Orientation
    private int orientation;
    private NotchInfo notchInfo;
    private final boolean hasNotch;
    private final boolean isNavigationBarShown;
    private final int statusBarHeight;
    private final int navigationBarHeight;

    private SystemUIInfo(Builder builder) {
        this.orientation = builder.orientation;
        this.notchInfo = builder.notchInfo;
        this.hasNotch = notchInfo != null;
        this.isNavigationBarShown = builder.isNavigationBarShown;
        this.statusBarHeight = builder.statusBarHeight;
        this.navigationBarHeight = builder.navigationBarHeight;
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

    public boolean isNavigationBarShown() {
        return isNavigationBarShown;
    }

    public boolean isLandscapeLeftwards() {
        return orientation == OrientationSpec.LANDSCAPE;
    }

    public boolean isReversedLandscape() {
        return orientation == OrientationSpec.LANDSCAPE_REVERSED;
    }

    public boolean isPortrait() {
        return orientation == OrientationSpec.PORTRAIT;
    }

    public boolean isPortraitUpSideDown() {
        return orientation == OrientationSpec.PORTRAIT_REVERSED;
    }

    public void resetNotchInfo() {
        this.notchInfo = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemUIInfo that = (SystemUIInfo) o;
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
                "orientation=" + OrientationHelper.parseOrientation(orientation) +
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
        private boolean isNavigationBarShown;
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

        public Builder setWhetherNavigationBarShow(boolean isNavigationBarShown) {
            this.isNavigationBarShown = isNavigationBarShown;
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

        public SystemUIInfo build() {
            return new SystemUIInfo(this);
        }
    }
}
