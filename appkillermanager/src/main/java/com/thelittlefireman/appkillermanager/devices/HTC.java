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

public class HTC extends DeviceAbstract {

    private static final String HTC_PITROAD_PACKAGE_NAME = "com.htc.pitroad";
    private static final ComponentName HTC_COMPONENENTNAMES_POWERSAVING = new ComponentName(HTC_PITROAD_PACKAGE_NAME,
            "com.htc.pitroad.landingpage.activity.LandingPageActivity");

    @Override
    public boolean isThatRom() {
        return Build.BRAND.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.MANUFACTURER.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.FINGERPRINT.toLowerCase().contains(getDeviceManufacturer().toString());
    }

    @Override
    public Manufacturer getDeviceManufacturer() {
        return Manufacturer.HTC;
    }

    @Override
    public boolean isActionPowerSavingAvailable(Context context) {
        return true;
    }

    @Override
    public boolean isActionAutoStartAvailable(Context context) {
        return false;
    }

    @Override
    public boolean isActionNotificationAvailable(Context context) {
        return false;
    }

    @Override
    public List<Intent> getActionPowerSaving(Context context) {
        Intent intent = ActionsUtils.createIntent(HTC_COMPONENENTNAMES_POWERSAVING);
        return Collections.singletonList(intent);
    }

    @Override
    public List<Intent> getActionAutoStart(Context context) {
        return null;
    }

    @Override
    public List<Intent> getActionNotification(Context context) {
        return null;
    }


    @Override
    public int getHelpImagePowerSaving() {
        return 0;
    }

    @Override
    public List<ComponentName> getComponentNameList() {
        return Collections.singletonList(HTC_COMPONENENTNAMES_POWERSAVING);
    }

    @Override
    public List<String> getIntentActionList() {
        return null;
    }

    @Override
    public boolean needToUseAlongwithActionDoseMode(){
        return true;
    }
}
