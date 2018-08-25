package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.thelittlefireman.appkillermanager.utils.ActionsUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.Arrays;
import java.util.List;

public class Vivo extends DeviceAbstract {
    private static final String PACAKAGE_AUTOSTART_2_6 = "com.iqoo.secure";
    private static final List<ComponentName> VIVO_COMPONENTNAMES_2_6 = Arrays.asList(
            //Funtouch OS 2.6 and lower version
            new ComponentName(PACAKAGE_AUTOSTART_2_6, "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity")// == ACTION com.iqoo.secure.settingwhitelist
    );

    //private final String p1c2 = "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"; //java.lang.SecurityException: Permission Denial:

    private static final String PACAKAGE_AUTOSTART_3_0 = "com.vivo.permissionmanager";
    private static final List<ComponentName> VIVO_COMPONENTNAMES_3_0 = Arrays.asList(
            //Funtouch OS 3.0 and higher version
            new ComponentName(PACAKAGE_AUTOSTART_3_0, "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));

    // TODO TEST "com.vivo.abe", "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity"

    @Override
    public boolean isThatRom() {
        return false;
    }

    @Override
    public Manufacturer getDeviceManufacturer() {
        return Manufacturer.VIVO;
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
    public List<Intent> getActionPowerSaving(Context context) {
        return null;
    }

    @Override
    public List<Intent> getActionAutoStart(Context context) {
        List<Intent> intentList;
        intentList = ActionsUtils.createIntentList(VIVO_COMPONENTNAMES_2_6);
        if (ActionsUtils.isAtLeastOneIntentAvailable(context, intentList)) {
            return intentList;
        }

        intentList = ActionsUtils.createIntentList(VIVO_COMPONENTNAMES_3_0);
        if (ActionsUtils.isAtLeastOneIntentAvailable(context, intentList)) {
            return intentList;
        }
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
        return null;
    }

    @Override
    public List<String> getIntentActionList() {
        return null;
    }
}
