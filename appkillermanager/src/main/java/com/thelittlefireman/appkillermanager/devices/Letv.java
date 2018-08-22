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

public class Letv extends DeviceAbstract {
    private static final String LETV_PACKAGE = "com.letv.android.letvsafe";

    private static final List<ComponentName> LETV_COMPONENTNAMES= Arrays.asList(
            // POWER SAVING
            new ComponentName(LETV_PACKAGE,"com.letv.android.letvsafe.BackgroundAppManageActivity"),
            // AUTO START
            new ComponentName(LETV_PACKAGE, "com.letv.android.letvsafe.AutobootManageActivity"));

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
    public Intent getActionPowerSaving(Context context) {
        Intent intent = ActionsUtils.createIntent();
        intent.setComponent(LETV_COMPONENTNAMES.get(0));
        return intent;
    }

    @Override
    public Intent getActionAutoStart(Context context) {
        Intent intent = ActionsUtils.createIntent();
        intent.setComponent(LETV_COMPONENTNAMES.get(1));
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
        return LETV_COMPONENTNAMES;
    }

    @Override
    public List<String> getIntentActionList() {
        return Collections.emptyList();
    }
}
