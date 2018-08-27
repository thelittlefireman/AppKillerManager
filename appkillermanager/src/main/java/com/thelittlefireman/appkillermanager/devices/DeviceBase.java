package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;

import com.thelittlefireman.appkillermanager.deviceUi.DeviceAbstractUi;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.List;

public interface DeviceBase {
    boolean isThatRom();
    Manufacturer getDeviceManufacturer();
    boolean isActionPowerSavingAvailable(Context context);
    boolean isActionAutoStartAvailable(Context context);
    boolean isActionNotificationAvailable(Context context);
    boolean needToUseAlongwithActionDoseMode();
    List<Intent> getActionPowerSaving(Context context);
    List<Intent> getActionAutoStart(Context context);
    // FIXME IS IT REALY NEEDED ? ==> REPLACE BY OTHER FUNCTION ?
    List<Intent> getActionNotification(Context context);
    // TODO ADD FOR MEMORY OPTIMIZATION : https://github.com/00aj99/CRomAppWhitelist
    String getExtraDebugInformations(Context context);

    /**
     * Function common in all devices
     * @param context the current context
     * @return the Intent to open the doze mode settings
     */
    Intent getActionDozeMode(Context context);
    boolean isActionDozeModeNotNecessary(Context context);
    @DrawableRes int getHelpImagePowerSaving();
    @DrawableRes int getHelpImageAutoStart();
    @DrawableRes int getHelpImageNotification();

    List<ComponentName> getComponentNameList();
    List<String> getIntentActionList();

    Class<? extends DeviceAbstractUi> getDeviceUi();
}
