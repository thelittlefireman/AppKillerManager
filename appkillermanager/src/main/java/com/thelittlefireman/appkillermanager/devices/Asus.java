package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.models.KillerManagerActionType;
import com.thelittlefireman.appkillermanager.utils.ActionUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.DrawableRes;

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
    public KillerManagerAction getActionPowerSaving(Context context) {
        // Juste need to use the regular battery non optimization
        // permission =)
        return super.getActionDozeMode(context);
    }

    @Override
    public KillerManagerAction getActionAutoStart(Context context) {
        Intent intent = ActionUtils.createIntent(ASUS_COMPONENTNAMES_AUTOSTART);
        intent.putExtra("showNotice", true);
        return new KillerManagerAction(KillerManagerActionType.ACTION_AUTOSTART, getHelpImageAutoStart(), intent);
    }

    @Override
    public KillerManagerAction getActionNotification(Context context) {
        // Need to clic on notifications items
        Intent intent = ActionUtils.createIntent(ASUS_COMPONENTNAMES_NOTIFICATION);
        intent.putExtra("showNotice", true);
        return new KillerManagerAction(KillerManagerActionType.ACTION_NOTIFICATIONS, getHelpImageNotification(),
                                       intent);
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
