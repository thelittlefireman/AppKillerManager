package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.thelittlefireman.appkillermanager.utils.ActionsUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ZTE extends DeviceAbstract {

    private static final String ZTE_PACKAGE_NAME ="com.zte.heartyservice";

    private static final List<ComponentName> ZTE_COMPONENTNAMES = Arrays.asList(
            // AUTO START
            new ComponentName(ZTE_PACKAGE_NAME, "com.zte.heartyservice.autorun.AppAutoRunManager"),
            // POWER SAVING
            new ComponentName(ZTE_PACKAGE_NAME, "com.zte.heartyservice.setting.ClearAppSettingsActivity"));

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
    public Intent getActionPowerSaving(Context context) {
        Intent intent = ActionsUtils.createIntent();
        intent.setComponent(ZTE_COMPONENTNAMES.get(1));
        return intent;
    }

    @Override
    public Intent getActionAutoStart(Context context) {
        Intent intent = ActionsUtils.createIntent();
        intent.setComponent(ZTE_COMPONENTNAMES.get(0));
        return intent;
    }

    @Override
    public Intent getActionNotification(Context context) {
        return null;
    }

    @Override
    public int getHelpImagePowerSaving() {
        return 0;
    }

    @Override
    public List<ComponentName> getComponentNameList() {
        return ZTE_COMPONENTNAMES;
    }

    @Override
    public List<String> getIntentActionList() {
        return Collections.emptyList();
    }
}
