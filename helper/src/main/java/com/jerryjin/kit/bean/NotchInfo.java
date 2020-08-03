package com.jerryjin.kit.bean;

import java.util.Objects;

/**
 * Author: Jerry
 * Generated at: 2020/8/1 19:42
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
public class NotchInfo {

    private String manufacturer;
    private int notchWidth;
    private int notchHeight;

    public NotchInfo() {
    }

    public NotchInfo(String manufacturer, int notchWidth, int notchHeight) {
        this.manufacturer = manufacturer;
        this.notchWidth = notchWidth;
        this.notchHeight = notchHeight;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getNotchWidth() {
        return notchWidth;
    }

    public void setNotchWidth(int notchWidth) {
        this.notchWidth = notchWidth;
    }

    public int getNotchHeight() {
        return notchHeight;
    }

    public void setNotchHeight(int notchHeight) {
        this.notchHeight = notchHeight;
    }

    public void reset() {
        this.notchWidth = 0;
        this.notchHeight = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotchInfo notchInfo = (NotchInfo) o;
        return notchWidth == notchInfo.notchWidth &&
                notchHeight == notchInfo.notchHeight &&
                manufacturer.equals(notchInfo.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturer, notchWidth, notchHeight);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return "NotchInfo{" +
                "manufacturer='" + manufacturer + '\'' +
                ", notchWidth=" + notchWidth +
                ", notchHeight=" + notchHeight +
                '}';
    }
}
