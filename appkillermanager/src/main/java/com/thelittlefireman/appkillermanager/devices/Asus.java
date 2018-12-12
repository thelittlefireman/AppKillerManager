package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.DrawableRes;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.utils.ActionUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Asus extends DeviceAbstract {

    private static final String ASUS_PACAKGE_MOBILEMANAGER = "com.asus.mobilemanager";

    private static final ComponentName ASUS_COMPONENTNAMES_NOTIFICATION = new ComponentName(ASUS_PACAKGE_MOBILEMANAGER,
            "com.asus.mobilemanager.entry.FunctionActivity");

    private static final ComponentName ASUS_COMPONENTNAMES_AUTOSTART =new ComponentName(ASUS_PACAKGE_MOBILEMANAGER,
            "com.asus.mobilemanager.autostart.AutoStartActivity");
    @Override
    public boolean isThatRom() {
        return Build.BRAND.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.MANUFACTURER.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.FINGERPRINT.toLowerCase().contains(getDeviceManufacturer().toString());
    }

    @Override
    public Manufacturer getDeviceManufacturer() {
        return Manufacturer.ASUS;
    }

    @Override
    public boolean isActionPowerSavingAvailable(Context context) {
        return super.isActionDozeModeNotNecessary(context);
    }

    @Override
    public boolean isActionAutoStartAvailable(Context context) {
        return true;
    }

    @Override
    public boolean isActionNotificationAvailable(Context context) {
        return true;
    }

    @Override
    public List<KillerManagerAction> getActionPowerSaving(Context context) {
        // Juste need to use the regular battery non optimization
        // permission =)
        return Collections.singletonList(super.getActionDozeMode(context));
    }

    @Override
    public List<KillerManagerAction> getActionAutoStart(Context context) {
        Intent intent = ActionUtils.createIntent(ASUS_COMPONENTNAMES_AUTOSTART);
        intent.putExtra("showNotice", true);
        return Collections.singletonList(new KillerManagerAction(getHelpImageAutoStart(),intent));
    }

    @Override
    public List<KillerManagerAction> getActionNotification(Context context) {
        // Need to clic on notifications items
        Intent intent = ActionUtils.createIntent(ASUS_COMPONENTNAMES_NOTIFICATION);
        intent.putExtra("showNotice", true);
        return Collections.singletonList(new KillerManagerAction(getHelpImageNotification(),intent));
    }


    @DrawableRes
    private int getHelpImageAutoStart() {
        return R.drawable.asus_autostart;
    }


    @DrawableRes
    private int getHelpImageNotification() {
        return R.drawable.asus_notification;
    }

    @Override
    public List<ComponentName> getComponentNameList() {
        return Arrays.asList(ASUS_COMPONENTNAMES_AUTOSTART,
                ASUS_COMPONENTNAMES_NOTIFICATION);
    }

    @Override
    public List<String> getIntentActionList() {
        return null;
    }
}
