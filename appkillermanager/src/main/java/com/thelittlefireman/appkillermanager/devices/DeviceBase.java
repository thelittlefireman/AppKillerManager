package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;

import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.List;

public interface DeviceBase {
    boolean isThatRom();
    Manufacturer getDeviceManufacturer();
    boolean isActionPowerSavingAvailable(Context context);
    boolean isActionAutoStartAvailable(Context context);
    boolean isActionNotificationAvailable(Context context);
    boolean needToUseAlongwithActionDoseMode();
    List<KillerManagerAction> getActionPowerSaving(Context context);
    List<KillerManagerAction> getActionAutoStart(Context context);
    // FIXME IS IT REALY NEEDED ? ==> REPLACE BY OTHER FUNCTION ?
    List<KillerManagerAction> getActionNotification(Context context);
    // TODO ADD FOR MEMORY OPTIMIZATION : https://github.com/00aj99/CRomAppWhitelist
    String getExtraDebugInformations(Context context);

    /**
     * Function common in all devices
     * @param context the current context
     * @return the Intent to open the doze mode settings
     */
    KillerManagerAction getActionDozeMode(Context context);
    boolean isActionDozeModeNotNecessary(Context context);

    List<ComponentName> getComponentNameList();
    List<String> getIntentActionList();
}
