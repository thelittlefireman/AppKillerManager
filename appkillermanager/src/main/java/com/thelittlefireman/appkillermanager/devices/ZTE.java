package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.models.KillerManagerActionType;
import com.thelittlefireman.appkillermanager.utils.ActionUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ZTE extends DeviceAbstract {

    private static final String ZTE_PACKAGE_NAME = "com.zte.heartyservice";

    private static final ComponentName ZTE_COMPONENTNAMES_AUTOSTART = new ComponentName(ZTE_PACKAGE_NAME,
            "com.zte.heartyservice.autorun.AppAutoRunManager");

    private static final ComponentName ZTE_COMPONENTNAMES_POWERSAVE = new ComponentName(ZTE_PACKAGE_NAME,
            "com.zte.heartyservice.setting.ClearAppSettingsActivity");

    @Override
    public boolean isThatRom() {
        return Build.BRAND.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.MANUFACTURER.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.FINGERPRINT.toLowerCase().contains(getDeviceManufacturer().toString());
    }

    @Override
    public Manufacturer getDeviceManufacturer() {
        return Manufacturer.ZTE;
    }

    @Override
    public boolean isActionPowerSavingAvailable(Context context) {
        return true;
    }

    @Override
    public boolean isActionAutoStartAvailable(Context context) {
        return true;
    }

    @Override
    public boolean isActionNotificationAvailable(Context context) {
        return false;
    }

    @Override
    public KillerManagerAction getActionPowerSaving(Context context) {
        return new KillerManagerAction(KillerManagerActionType.ACTION_POWERSAVING, ActionUtils.createIntent
                (ZTE_COMPONENTNAMES_POWERSAVE));
    }

    @Override
    public KillerManagerAction getActionAutoStart(Context context) {
        return new KillerManagerAction(KillerManagerActionType.ACTION_AUTOSTART, ActionUtils.createIntent
                (ZTE_COMPONENTNAMES_AUTOSTART));
    }

    @Override
    public KillerManagerAction getActionNotification(Context context) {
        return null;
    }

    @Override
    public List<ComponentName> getComponentNameList() {
        return Arrays.asList(ZTE_COMPONENTNAMES_AUTOSTART,
                ZTE_COMPONENTNAMES_POWERSAVE);
    }

    @Override
    public List<String> getIntentActionList() {
        return Collections.emptyList();
    }
}
