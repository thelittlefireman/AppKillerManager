package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.thelittlefireman.appkillermanager.utils.ActionsUtils;
import com.thelittlefireman.appkillermanager.utils.LogUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;
import com.thelittlefireman.appkillermanager.utils.SystemUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Xiaomi extends DeviceAbstract {

    // TODO TEST new Intent().setComponent(ComponentName("com.miui.securitycenter", "com.miui.powercenter.PowerSettings"))
    private static final String MIUI_ACTION_PERMS = "miui.intent.action.APP_PERM_EDITOR";
    private static final String MIUI_ACTION_PERMS_EXTRA = "extra_pkgname";

    // region ------ vars AUTOSTART
    private static final String MIUI_PACKAGE_POWERSAVE = "com.miui.powerkeeper";
    private static final String MIUI_ACTION_POWER_SAVE_LIST = "miui.intent.action.POWER_HIDE_MODE_APP_LIST"; //  OPEN DEFAULT LIST BATTERYSAVER
    private static final ComponentName MIUI_COMPONENTSNAMES_POWERSAVE_LIST = new ComponentName(MIUI_PACKAGE_POWERSAVE, "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity"); // == ACTION POWER_HIDE_MODE_APP_LIST

    private static final String MIUI_ACTION_POWER_SAVE = "ACTION miui.intent.action.HIDDEN_APPS_CONFIG_ACTIVITY"; //  OPEN DEFAULT LIST BATTERYSAVER
    private static final ComponentName MIUI_COMPONENTSNAMES_POWERSAVE = new ComponentName(MIUI_PACKAGE_POWERSAVE,
            "com.miui.powerkeeper.ui.HiddenAppsConfigActivity");// ONE SPECIFIQUE APP == ACTION miui.intent.action.HIDDEN_APPS_CONFIG_ACTIVITY

    private static final String MIUI_ACTION_POWER_SAVE_EXTRA_NAME = "package_name";
    private static final String MIUI_ACTION_POWER_SAVE_EXTRA_LABEL = "package_label";
    // endregion

    // region ------ vars AUTOSTART
    private static final String MIUI_ACTION_AUTOSTART_LIST = "miui.intent.action.OP_AUTO_START";
    private static final String MIUI_PACKAGE_AUTOSTART = "com.miui.securitycenter";
    private static final ComponentName MIUI_COMPONENTSNAMES_AUTOSTART = new ComponentName(MIUI_PACKAGE_AUTOSTART,
            "com.miui.permcenter.autostart.AutoStartDetailManagementActivity");

    private static final String MIUI_ACTION_AUTOSTART_EXTRA_NAME = "pkg_name";
    private static final String MIUI_ACTION_AUTOSTART_EXTRA_LABEL = "pkg_label";
    private static final String MIUI_ACTION_AUTOSTART_EXTRA_ACTION = "action"; // default 3 unknown parameter
    private static final String MIUI_ACTION_AUTOSTART_EXTRA_POSITION = "pkg_position"; // default -1 unknown position
    private static final String MIUI_ACTION_AUTOSTART_EXTRA_WHITE_LIST = "pkg_label"; // default need to be false to be handle

    // endregion

    private static final String MIUI_VERSION_NAME_PROPERTY = "ro.miui.ui.version.name";

    @Override
    public boolean isThatRom() {
        return Build.BRAND.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.MANUFACTURER.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.FINGERPRINT.toLowerCase().contains(getDeviceManufacturer().toString());
    }

    @Override
    public Manufacturer getDeviceManufacturer() {
        return Manufacturer.XIAOMI;
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
    public List<Intent> getActionPowerSaving(Context context) {
        Intent intent = ActionsUtils.createIntent(MIUI_ACTION_POWER_SAVE);
        intent.putExtra(MIUI_ACTION_POWER_SAVE_EXTRA_NAME, context.getPackageName());
        intent.putExtra(MIUI_ACTION_POWER_SAVE_EXTRA_LABEL, SystemUtils.getApplicationName(context));
        return Collections.singletonList(intent);
    }

    @Override
    public List<Intent> getActionAutoStart(Context context) {
        Intent intent = ActionsUtils.createIntent(MIUI_COMPONENTSNAMES_AUTOSTART);
        intent.putExtra(MIUI_ACTION_AUTOSTART_EXTRA_NAME, context.getPackageName());
        intent.putExtra(MIUI_ACTION_AUTOSTART_EXTRA_LABEL, SystemUtils.getApplicationName(context));
        intent.putExtra(MIUI_ACTION_AUTOSTART_EXTRA_ACTION, 3);
        intent.putExtra(MIUI_ACTION_AUTOSTART_EXTRA_POSITION, -1);
        intent.putExtra(MIUI_ACTION_AUTOSTART_EXTRA_WHITE_LIST, false);
        return Collections.singletonList(intent);
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
        return Arrays.asList(MIUI_COMPONENTSNAMES_AUTOSTART,
                MIUI_COMPONENTSNAMES_POWERSAVE,
                MIUI_COMPONENTSNAMES_POWERSAVE_LIST);
    }

    @Override
    public List<String> getIntentActionList() {
        return Arrays.asList(MIUI_ACTION_POWER_SAVE_LIST,
                MIUI_ACTION_AUTOSTART_LIST);
    }

    @Override
    public String getExtraDebugInformations(Context context) {
        String rst = super.getExtraDebugInformations(context);
        rst += MIUI_VERSION_NAME_PROPERTY + getMiuiRomVersionName();
        return rst;
    }

    private static String getMiuiRomVersionName() {
        try {
            return SystemUtils.getSystemProperty(MIUI_VERSION_NAME_PROPERTY);
        } catch (Exception e) {
            LogUtils.e(SystemUtils.class.getName(), e.getMessage());
            return "";
        }
    }

}
