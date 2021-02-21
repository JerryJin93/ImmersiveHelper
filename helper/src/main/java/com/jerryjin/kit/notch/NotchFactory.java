package com.jerryjin.kit.notch;

import com.jerryjin.kit.interfaces.INotch;
import com.jerryjin.kit.manufacturer.AndroidNotch;
import com.jerryjin.kit.manufacturer.HuaweiNotch;
import com.jerryjin.kit.manufacturer.Manufacturer;
import com.jerryjin.kit.manufacturer.OppoNotch;
import com.jerryjin.kit.manufacturer.SmartisanNotch;
import com.jerryjin.kit.manufacturer.VivoNotch;
import com.jerryjin.kit.manufacturer.XiaoMiNotch;
import com.jerryjin.kit.utils.Utils;

/**
 * Author: Jerry
 * Generated at: 2020/7/26 20:48
 * GitHub: https://github.com/JerryJin93
 * Blog:
 * WeChat: enGrave93
 * Version: 2.0.0
 * Description:
 */
public final class NotchFactory implements Factory {

    private static volatile NotchFactory instance;
    private INotch notch;

    private NotchFactory() {
    }

    public static NotchFactory getInstance() {
        if (instance == null) {
            synchronized (NotchFactory.class) {
                if (instance == null) {
                    instance = new NotchFactory();
                }
            }
        }
        return instance;
    }

    public INotch getNotch() {
        if (notch == null) {
            String manufacturer = Utils.getManufacturer();
            switch (manufacturer) {
                case Manufacturer.MI:
                    notch = new XiaoMiNotch();
                    break;
                case Manufacturer.HUAWEI:
                    notch = new HuaweiNotch();
                    break;
                case Manufacturer.OPPO:
                    notch = new OppoNotch();
                    break;
                case Manufacturer.VIVO:
                    notch = new VivoNotch();
                    break;
                case Manufacturer.SMARTISAN:
                    notch = new SmartisanNotch();
                    break;
                default:
                    notch = new AndroidNotch();
                    break;
            }
        }
        return notch;
    }
}
