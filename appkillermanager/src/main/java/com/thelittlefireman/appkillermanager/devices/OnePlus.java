package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.thelittlefireman.appkillermanager.utils.ActionsUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

public class OnePlus extends DeviceAbstract {
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
        if(Build.VERSION.SDK_INT == 30){
            return true;
        }else{
            return false;
        }
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
        /////NEED MODIFICATION////
        //This is only confirmed working on Android 11 OxygenOS, other Android versions are still unconfirmed.
        //The number 30 should be changed to VERSION_CODES.R After this project has updated compileSdkVersion and migrated to Androidx
        if(Build.VERSION.SDK_INT == 30){
            Intent intent = ActionsUtils.createIntent();
            intent.setComponent(
                    new ComponentName("com.android.settings",
                            "com.android.settings.Settings$BgOptimizeAppListActivity"));
            return intent;
        }else{
            return null;
        }
    }

    @Override
    public Intent getActionAutoStart(Context context) {
        Intent intent = ActionsUtils.createIntent();
        intent.setComponent(new ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view" +
                ".ChainLaunchAppListActivity"));
        return intent;
    }

    @Override
    public Intent getActionNotification(Context context) {
        return null;
    }

    @Override
    public String getExtraDebugInformations(Context context) {
        return null;
    }
}
