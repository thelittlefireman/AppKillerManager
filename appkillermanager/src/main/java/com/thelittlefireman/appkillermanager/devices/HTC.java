package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.utils.ActionUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

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
    public List<KillerManagerAction> getActionPowerSaving(Context context) {
        Intent intent = ActionUtils.createIntent(HTC_COMPONENENTNAMES_POWERSAVING);
        return Collections.singletonList(new KillerManagerAction(intent));
    }

    @Override
    public List<KillerManagerAction> getActionAutoStart(Context context) {
        return null;
    }

    @Override
    public List<KillerManagerAction> getActionNotification(Context context) {
        return null;
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
