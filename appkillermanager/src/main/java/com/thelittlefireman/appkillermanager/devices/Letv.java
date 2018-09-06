package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.utils.ActionUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Letv extends DeviceAbstract {
    private static final String LETV_PACKAGE = "com.letv.android.letvsafe";

    private static final ComponentName LETV_COMPONENTNAMES_POWERSAVE = new ComponentName(LETV_PACKAGE,
            "com.letv.android.letvsafe.BackgroundAppManageActivity");

    private static final ComponentName LETV_COMPONENTNAMES_AUTOSTART = new ComponentName(LETV_PACKAGE,
            "com.letv.android.letvsafe.AutobootManageActivity");

    @Override
    public boolean isThatRom() {
        return Build.BRAND.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.MANUFACTURER.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.FINGERPRINT.toLowerCase().contains(getDeviceManufacturer().toString());
    }

    @Override
    public Manufacturer getDeviceManufacturer() {
        return Manufacturer.LETV;
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
    public List<KillerManagerAction> getActionPowerSaving(Context context) {
        return Collections.singletonList(new KillerManagerAction(
                ActionUtils.createIntent(LETV_COMPONENTNAMES_POWERSAVE)));
    }

    @Override
    public List<KillerManagerAction> getActionAutoStart(Context context) {
        return Collections.singletonList(new KillerManagerAction(
                ActionUtils.createIntent(LETV_COMPONENTNAMES_AUTOSTART)));
    }

    @Override
    public List<KillerManagerAction> getActionNotification(Context context) {
        return null;
    }

    @Override
    public List<ComponentName> getComponentNameList()
    {
        return Arrays.asList(LETV_COMPONENTNAMES_AUTOSTART,
                LETV_COMPONENTNAMES_POWERSAVE);
    }

    @Override
    public List<String> getIntentActionList() {
        return Collections.emptyList();
    }
}
