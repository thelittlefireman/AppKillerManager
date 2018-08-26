package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.thelittlefireman.appkillermanager.utils.ActionUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.Collections;
import java.util.List;

public class OnePlus extends DeviceAbstract {
    private static final String ONEPLUS_PACKAGE = "com.oneplus.security";
    private static final ComponentName ONEPLUS_COMPONENTNAMES =  new ComponentName(ONEPLUS_PACKAGE,
            "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity");

    @Override
    public boolean isThatRom() {
        return Build.BRAND.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.MANUFACTURER.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.FINGERPRINT.toLowerCase().contains(getDeviceManufacturer().toString());
    }

    // This is mandatory for new oneplus version android 8
    @Override
    public boolean needToUseAlongwithActionDoseMode(){
        return true;
    }

    @Override
    public Manufacturer getDeviceManufacturer() {
        return Manufacturer.ONEPLUS;
    }

    @Override
    public boolean isActionPowerSavingAvailable(Context context) {
        return false;
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
    public List<Intent>  getActionPowerSaving(Context context) {
        return null;
    }

    @Override
    public List<Intent> getActionAutoStart(Context context) {
        return Collections.singletonList(
                ActionUtils.createIntent(ONEPLUS_COMPONENTNAMES));
    }

    @Override
    public List<Intent>  getActionNotification(Context context) {
        return null;
    }

    @Override
    public int getHelpImagePowerSaving() {
        return 0;
    }

    @Override
    public List<ComponentName> getComponentNameList() {
        return Collections.singletonList(ONEPLUS_COMPONENTNAMES);
    }

    @Override
    public List<String> getIntentActionList() {
        return Collections.emptyList();
    }
}
