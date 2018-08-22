package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.utils.ActionsUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.Arrays;
import java.util.List;

public class Samsung extends DeviceAbstract {
    // crash "com.samsung.android.lool","com.samsung.android.sm.ui.battery.AppSleepListActivity"
    private static final String SAMSUNG_SYSTEMMANAGER_POWERSAVING_ACTION = "com.samsung.android.sm.ACTION_BATTERY";
    private static final String SAMSUNG_SYSTEMMANAGER_NOTIFICATION_ACTION = "com.samsung.android.sm.ACTION_SM_NOTIFICATION_SETTING";
    private static final String SAMSUNG_SYSTEMMANAGER_AUTOSTART_PACKAGE_V1 = "com.samsung.memorymanager";
    // ANDROID 7.0
    private static final String SAMSUNG_SYSTEMMANAGER_POWERSAVING_PACKAGE_V3 = "com.samsung.android.lool";

    // ANDROID 6.0
    private static final String SAMSUNG_SYSTEMMANAGER_POWERSAVING_PACKAGE_V2 = "com.samsung.android.sm_cn";

    // ANDROID 5.0/5.1
    private static final String SAMSUNG_SYSTEMMANAGER_POWERSAVING_PACKAGE_V1 = "com.samsung.android.sm";

    private static final List<ComponentName> SAMSUNG_COMPONENTNAMES = Arrays.asList(
            // ANDROID 5.0/5.1
            new ComponentName(SAMSUNG_SYSTEMMANAGER_POWERSAVING_PACKAGE_V1, "com.samsung.android.sm.ui.battery.BatteryActivity"),
            // ANDROID 6.0
            new ComponentName(SAMSUNG_SYSTEMMANAGER_POWERSAVING_PACKAGE_V2, "com.samsung.android.sm.ui.battery.BatteryActivity"),
            // ANDROID 7.0
            new ComponentName(SAMSUNG_SYSTEMMANAGER_POWERSAVING_PACKAGE_V3, "com.samsung.android.sm.ui.battery.BatteryActivity"),
            // MEMORY MANAGER (NOT WORKING)
            new ComponentName(SAMSUNG_SYSTEMMANAGER_AUTOSTART_PACKAGE_V1, "com.samsung.memorymanager.RamActivity"));

    private static final List<String> SAMSUNG_INTENTACTIONS = Arrays.asList(SAMSUNG_SYSTEMMANAGER_POWERSAVING_ACTION,
            SAMSUNG_SYSTEMMANAGER_AUTOSTART_PACKAGE_V1,
            SAMSUNG_SYSTEMMANAGER_NOTIFICATION_ACTION);

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
    public boolean needToUseAlongwithActionDoseMode() {
        return true;
    }

    @Override
    public Intent getActionPowerSaving(Context context) {
        Intent intent = ActionsUtils.createIntent();
        intent.setAction(SAMSUNG_SYSTEMMANAGER_POWERSAVING_ACTION);
        if (ActionsUtils.isIntentAvailable(context, intent)) {
            return intent;
        }
        // reset
        intent = ActionsUtils.createIntent();
        intent.setComponent(SAMSUNG_COMPONENTNAMES.get(2));
        if (ActionsUtils.isIntentAvailable(context, intent)) {
            return intent;
        }

        intent.setComponent(SAMSUNG_COMPONENTNAMES.get(1));
        if (ActionsUtils.isIntentAvailable(context, intent)) {
            return intent;
        }
        intent.setComponent(SAMSUNG_COMPONENTNAMES.get(0));
        if (ActionsUtils.isIntentAvailable(context, intent)) {
            return intent;
        }
        return null;
    }

    // FIXME Currently not working : not available, ITS NOT AUTOSTART ITS MEMORY MANAGER
    @Override
    public Intent getActionAutoStart(Context context) {
        Intent intent = ActionsUtils.createIntent();
        intent.setComponent(SAMSUNG_COMPONENTNAMES.get(3));
        return intent;
    }

    // FIXME : NOTWORKOING NEED PERMISSIONS SETTINGS OR SOMETHINGS ELSE
    @Override
    public Intent getActionNotification(Context context) {
        Intent intent = ActionsUtils.createIntent();
        intent.setAction(SAMSUNG_SYSTEMMANAGER_NOTIFICATION_ACTION);
        return null;
    }

    @Override
    public int getHelpImagePowerSaving() {
        return R.drawable.samsung;
    }

    @Override
    public List<ComponentName> getComponentNameList() {
        return SAMSUNG_COMPONENTNAMES;
    }

    @Override
    public List<String> getIntentActionList() {
        return SAMSUNG_INTENTACTIONS;
    }
}
