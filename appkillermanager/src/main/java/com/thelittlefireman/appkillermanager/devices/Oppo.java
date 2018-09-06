package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.utils.ActionUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Oppo extends DeviceAbstract {
    /**
     * java.lang.SecurityException:
     * Permission Denial: starting Intent { cmp=com.coloros.safecenter/.startupapp.StartupAppListActivity } f
     * rom ProcessRecord{7eba0ba 27527:crb.call.follow.mycrm/u0a229} (pid=27527, uid=10229)
     * requires oppo.permission.OPPO_COMPONENT_SAFE
     */
        /*    private static final String OPPO_COLOROS_NOTIFICATION_PACKAGER_V4 = "com.android.settings";
    private static final String OPPO_COLOROS_NOTIFICATION_ACTIVITY_V4 = "com.android.settings.applications.InstalledAppDetails";*/

    private static final String PACAKGE_AUTOSTART_COLORSOS_3_0 = "com.coloros.safecenter";

    private static final String PACAKGE_AUTOSTART_COLORSOS_2_1 = "com.color.oppoguardelf";

    private static final String PACAKGE_AUTOSTART_COLORSOS_OLDER = "com.oppo.safe";

    private static final List<ComponentName> OPPO_COMPONENTSNAMES_AUTOSTART_COLOROS_3_0 = Arrays.asList(
            // STARTUP Coloros >= 3.0
            new ComponentName(PACAKGE_AUTOSTART_COLORSOS_3_0, "com.coloros.safecenter.permission.startup.StartupAppListActivity"),
            new ComponentName(PACAKGE_AUTOSTART_COLORSOS_3_0, "com.coloros.safecenter.startupapp.StartupAppListActivity"));

    private static final List<ComponentName> OPPO_COMPONENTSNAMES_AUTOSTART_COLOROS_2_1 = Arrays.asList(
            // STARTUP Coloros >= 2.1
            new ComponentName(PACAKGE_AUTOSTART_COLORSOS_2_1, "com.color.safecenter.permission.startup.StartupAppListActivity"),
            new ComponentName(PACAKGE_AUTOSTART_COLORSOS_2_1, "com.color.safecenter.startupapp.StartupAppListActivity"));

    private static final List<ComponentName> OPPO_COMPONENTSNAMES_AUTOSTART_COLOROS_OLDER = Arrays.asList(
            // STARTUP OLDER VERSION
            new ComponentName(PACAKGE_AUTOSTART_COLORSOS_OLDER, "com.oppo.safe.permission.startup.StartupAppListActivity"));

    private static final String PACAKGE_POWERSAVING_COLORSOS_3_0 = "com.coloros.oppoguardelf";

    private static final List<ComponentName> OPPO_COMPONENTSNAMES_POWERSAVING_COLOROS = Arrays.asList(
            // POWER SAVING MODE
            new ComponentName(PACAKGE_POWERSAVING_COLORSOS_3_0, "com.coloros.powermanager.fuelgaue.PowerConsumptionActivity"),
            new ComponentName(PACAKGE_POWERSAVING_COLORSOS_3_0, "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity")
    );

    private static final String OPPO_COLOROS_NOTIFICATION_PACKAGER = "com.coloros.notificationmanager";
    // POWER SAVING MODE
    private static final ComponentName OPPO_COMPONENTSNAMES_NOTIFICATION_COLOROS = new ComponentName(OPPO_COLOROS_NOTIFICATION_PACKAGER,
            "com.coloros.notificationmanager.NotificationCenterActivity");


    @Override
    public boolean isThatRom() {
        return Build.BRAND.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.MANUFACTURER.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.FINGERPRINT.toLowerCase().contains(getDeviceManufacturer().toString());
    }

    @Override
    public Manufacturer getDeviceManufacturer() {
        return Manufacturer.OPPO;
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
        return true;
    }

    @Override
    public List<KillerManagerAction> getActionPowerSaving(Context context) {
        List<Intent> intentList;
        intentList = ActionUtils.createIntentList(OPPO_COMPONENTSNAMES_POWERSAVING_COLOROS);
        if (ActionUtils.isAtLeastOneIntentAvailable(context, intentList)) {
            return KillerManagerAction.generateKillerManagerActionList(
                    intentList);
        }
        return null;
    }

    @Override
    public List<KillerManagerAction> getActionAutoStart(Context context) {
        List<Intent> intentList;
        intentList = ActionUtils.createIntentList(OPPO_COMPONENTSNAMES_AUTOSTART_COLOROS_3_0);
        if (ActionUtils.isAtLeastOneIntentAvailable(context, intentList)) {
            return KillerManagerAction.generateKillerManagerActionList(
                    intentList);
        }

        intentList = ActionUtils.createIntentList(OPPO_COMPONENTSNAMES_AUTOSTART_COLOROS_2_1);
        if (ActionUtils.isAtLeastOneIntentAvailable(context, intentList)) {
            return KillerManagerAction.generateKillerManagerActionList(
                    intentList);
        }

        intentList = ActionUtils.createIntentList(OPPO_COMPONENTSNAMES_AUTOSTART_COLOROS_OLDER);
        if (ActionUtils.isAtLeastOneIntentAvailable(context, intentList)) {
            return KillerManagerAction.generateKillerManagerActionList(
                    intentList);
        }
        return null;
    }

    @Override
    public List<KillerManagerAction> getActionNotification(Context context) {
        return Collections.singletonList(new KillerManagerAction(
                ActionUtils.createIntent(OPPO_COMPONENTSNAMES_NOTIFICATION_COLOROS)));
    }

    @Override
    public List<ComponentName> getComponentNameList() {
        List<ComponentName> rst = new ArrayList<>();
        rst.addAll(OPPO_COMPONENTSNAMES_AUTOSTART_COLOROS_3_0);
        rst.addAll(OPPO_COMPONENTSNAMES_AUTOSTART_COLOROS_2_1);
        rst.addAll(OPPO_COMPONENTSNAMES_AUTOSTART_COLOROS_OLDER);
        rst.addAll(OPPO_COMPONENTSNAMES_POWERSAVING_COLOROS);
        rst.add(OPPO_COMPONENTSNAMES_NOTIFICATION_COLOROS);
        return rst;
    }

    @Override
    public List<String> getIntentActionList() {
        return null;
    }
}
