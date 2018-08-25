package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.utils.ActionsUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Samsung extends DeviceAbstract {
    // crash "com.samsung.android.lool","com.samsung.android.sm.ui.battery.AppSleepListActivity"
    private static final String SAMSUNG_ACTION_POWERSAVING = "com.samsung.android.sm.ACTION_BATTERY";
    private static final String SAMSUNG_ACTION_NOTIFICATION = "com.samsung.android.sm.ACTION_SM_NOTIFICATION_SETTING";
    private static final String PACKAGE_MEMORYMANAGER = "com.samsung.memorymanager";

    // ANDROID 7.0
    private static final String SAMSUNG_POWERSAVING_PACKAGE_V3 = "com.samsung.android.lool";

    // ANDROID 6.0
    private static final String SAMSUNG_POWERSAVING_PACKAGE_V2 = "com.samsung.android.sm_cn";

    // ANDROID 5.0/5.1
    private static final String SAMSUNG_POWERSAVING_PACKAGE_V1 = "com.samsung.android.sm";

    // ANDROID 5.0/5.1
    private static final ComponentName SAMSUNG_COMPONENTNAMES_POWERSAVING_V1 = new ComponentName(SAMSUNG_POWERSAVING_PACKAGE_V1,
            "com.samsung.android.sm.ui.battery.BatteryActivity");
    // ANDROID 6.0
    private static final ComponentName SAMSUNG_COMPONENTNAMES_POWERSAVING_V2 = new ComponentName(SAMSUNG_POWERSAVING_PACKAGE_V2
            , "com.samsung.android.sm.ui.battery.BatteryActivity");
    // ANDROID 7.0
    private static final ComponentName SAMSUNG_COMPONENTNAMES_POWERSAVING_V3 = new ComponentName(SAMSUNG_POWERSAVING_PACKAGE_V3
            , "com.samsung.android.sm.ui.battery.BatteryActivity");

    // MEMORY MANAGER (NOT WORKING)
    private static final ComponentName SAMSUNG_COMPONENTNAMES_MEMORYMANAGER_V3 = new ComponentName(PACKAGE_MEMORYMANAGER
            , "com.samsung.memorymanager.RamActivity");

    private static final List<String> SAMSUNG_INTENTACTIONS = Arrays.asList(SAMSUNG_ACTION_POWERSAVING,
            SAMSUNG_ACTION_NOTIFICATION);

    @Override
    public boolean isThatRom() {
        return Build.BRAND.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.MANUFACTURER.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.FINGERPRINT.toLowerCase().contains(getDeviceManufacturer().toString());
    }

    @Override
    public Manufacturer getDeviceManufacturer() {
        return Manufacturer.SAMSUNG;
    }

    @Override
    public boolean isActionPowerSavingAvailable(Context context) {
        // SmartManager is not available before lollipop version
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
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
    public boolean needToUseAlongwithActionDoseMode() {
        return true;
    }

    @Override
    public List<Intent> getActionPowerSaving(Context context) {
        Intent intent = ActionsUtils.createIntent();
        intent.setAction(SAMSUNG_ACTION_POWERSAVING);
        if (ActionsUtils.isIntentAvailable(context, intent)) {
            return Collections.singletonList(intent);
        }
        // reset
        intent = ActionsUtils.createIntent(SAMSUNG_COMPONENTNAMES_POWERSAVING_V3);
        if (ActionsUtils.isIntentAvailable(context, intent)) {
            return Collections.singletonList(intent);
        }

        intent = ActionsUtils.createIntent(SAMSUNG_COMPONENTNAMES_POWERSAVING_V2);
        if (ActionsUtils.isIntentAvailable(context, intent)) {
            return Collections.singletonList(intent);
        }
        intent = ActionsUtils.createIntent(SAMSUNG_COMPONENTNAMES_POWERSAVING_V1);
        if (ActionsUtils.isIntentAvailable(context, intent)) {
            return Collections.singletonList(intent);
        }
        return null;
    }

    // FIXME Currently not working : not available, ITS NOT AUTOSTART ITS MEMORY MANAGER
    @Override
    public List<Intent> getActionAutoStart(Context context) {
        return Collections.singletonList(
                ActionsUtils.createIntent(SAMSUNG_COMPONENTNAMES_MEMORYMANAGER_V3));
    }

    // FIXME : NOTWORKOING NEED PERMISSIONS SETTINGS OR SOMETHINGS ELSE
    @Override
    public List<Intent> getActionNotification(Context context) {
        return Collections.singletonList(
                ActionsUtils.createIntent(SAMSUNG_ACTION_NOTIFICATION));
    }

    @Override
    public int getHelpImagePowerSaving() {
        return R.drawable.samsung;
    }

    @Override
    public List<ComponentName> getComponentNameList() {
        return Arrays.asList(SAMSUNG_COMPONENTNAMES_POWERSAVING_V1,
                SAMSUNG_COMPONENTNAMES_POWERSAVING_V2,
                SAMSUNG_COMPONENTNAMES_POWERSAVING_V3,
                SAMSUNG_COMPONENTNAMES_MEMORYMANAGER_V3);
    }

    @Override
    public List<String> getIntentActionList() {
        return SAMSUNG_INTENTACTIONS;
    }
}
