package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.utils.ActionsUtils;
import com.thelittlefireman.appkillermanager.utils.Manufacturer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.thelittlefireman.appkillermanager.utils.SystemUtils.getEmuiRomName;

public class Huawei extends DeviceAbstract {
    private static final String HUAWEI_SYSTEMMANAGER_PACKAGE_NAME = "com.huawei.systemmanager";
    private static final String HUAWEI_ACTION_POWERSAVING = "huawei.intent.action.HSM_PROTECTED_APPS";
    private static final String HUAWEI_ACTION_AUTOSTART = "huawei.intent.action.HSM_BOOTAPP_MANAGER";
    private static final String HUAWEI_ACTION_NOTIFICATION = "huawei.intent.action.NOTIFICATIONMANAGER";

    private static final List<ComponentName> HUAWEI_COMPONENTNAMES = Arrays.asList(
            new ComponentName(HUAWEI_SYSTEMMANAGER_PACKAGE_NAME, "com.huawei.systemmanager.optimize.bootstart.BootStartActivity"),
            new ComponentName(HUAWEI_SYSTEMMANAGER_PACKAGE_NAME, "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"),
            new ComponentName(HUAWEI_SYSTEMMANAGER_PACKAGE_NAME, "com.huawei.permissionmanager.ui.MainActivity"));

    private static final List<String> HUAWEI_ACTIONS = Arrays.asList(HUAWEI_ACTION_POWERSAVING, HUAWEI_ACTION_AUTOSTART, HUAWEI_ACTION_NOTIFICATION);
    // TODO NOT SUR IT WORKS ON EMUI 5

    @Override
    public boolean isThatRom() {
        return isEmotionUI_23() ||
                isEmotionUI_3() ||
                isEmotionUI_301() ||
                isEmotionUI_31() ||
                isEmotionUI_41() ||
                Build.BRAND.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.MANUFACTURER.equalsIgnoreCase(getDeviceManufacturer().toString()) ||
                Build.FINGERPRINT.toLowerCase().contains(getDeviceManufacturer().toString());
    }

    public static boolean isEmotionUI_301() {
        return "EmotionUI_3.0.1".equalsIgnoreCase(getEmuiRomName());
    }

    public static boolean isEmotionUI_31() {
        return "EmotionUI_3.1".equalsIgnoreCase(getEmuiRomName());
    }

    public static boolean isEmotionUI_41() {
        return "EmotionUI_4.1".equalsIgnoreCase(getEmuiRomName());
    }

    public static boolean isEmotionUI_3() {
        return "EmotionUI_3.0".equalsIgnoreCase(getEmuiRomName());
    }

    public static boolean isEmotionUI_23() {
        return "EmotionUI_2.3".equalsIgnoreCase(getEmuiRomName()) || Build.DISPLAY.toLowerCase().contains("emui2.3") || "EMUI 2.3".equalsIgnoreCase(getEmuiRomName());
    }

    @Override
    public Manufacturer getDeviceManufacturer() {
        return Manufacturer.HUAWEI;
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
        return true;
    }

    @Override
    public List<Intent> getActionPowerSaving(Context context) {
        return Collections.singletonList(ActionsUtils.createIntent(HUAWEI_ACTION_POWERSAVING));
    }

    @Override
    public List<Intent> getActionAutoStart(Context context) {
        // AUTOSTART not used in huawei
        return null;
        /*Intent intent = ActionsUtils.createIntent();
        intent.setAction(HUAWEI_ACTION_AUTOSTART);
        if (ActionsUtils.isIntentAvailable(context, intent)) {
            return intent;
        } else {
            intent = ActionsUtils.createIntent();
            intent.setComponent(getComponentNameAutoStart(context));
            return intent;
        }*/
    }

    @Override
    public List<Intent> getActionNotification(Context context) {
        return Collections.singletonList(ActionsUtils.createIntent(HUAWEI_ACTION_NOTIFICATION));
    }

    @Override
    public String getExtraDebugInformations(Context context) {
        String result = super.getExtraDebugInformations(context);
        StringBuilder stringBuilder = new StringBuilder(result);
        stringBuilder.append("ROM_VERSION").append(getEmuiRomName());
        stringBuilder.append("HuaweiSystemManagerVersionMethod:").append(getHuaweiSystemManagerVersion(context));
        PackageManager manager = context.getPackageManager();
        PackageInfo info;
        String versionStr = "";
        try {
            info = manager.getPackageInfo(HUAWEI_SYSTEMMANAGER_PACKAGE_NAME, 0);
            versionStr = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        stringBuilder.append("HuaweiSystemManagerPackageVersion:").append(versionStr);
        return stringBuilder.toString();
    }

    @Override
    public int getHelpImagePowerSaving() {
        return R.drawable.huawei_powersaving;
    }

    @Override
    public List<ComponentName> getComponentNameList() {
        return HUAWEI_COMPONENTNAMES;
    }

    @Override
    public List<String> getIntentActionList() {
        return HUAWEI_ACTIONS;
    }

    private ComponentName getComponentNameAutoStart(Context context) {
        int mVersion = getHuaweiSystemManagerVersion(context);
        if (mVersion == 4 || mVersion == 5) {
            return HUAWEI_COMPONENTNAMES.get(1);
        } else if (mVersion == 6) {
            return HUAWEI_COMPONENTNAMES.get(2);
        } else {
            return HUAWEI_COMPONENTNAMES.get(0);
        }
    }

    private static int getHuaweiSystemManagerVersion(Context context) {
        int version = 0;
        int versionNum = 0;
        int thirdPartFirtDigit = 0;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(HUAWEI_SYSTEMMANAGER_PACKAGE_NAME, 0);
            Log.i(Huawei.class.getName(), "manager info = " + info.toString());
            String versionStr = info.versionName;
            String versionTmp[] = versionStr.split("\\.");
            if (versionTmp.length >= 2) {
                if (Integer.parseInt(versionTmp[0]) == 5) {
                    versionNum = 500;
                } else if (Integer.parseInt(versionTmp[0]) == 4) {
                    versionNum = Integer.parseInt(versionTmp[0] + versionTmp[1] + versionTmp[2]);
                } else {
                    versionNum = Integer.parseInt(versionTmp[0] + versionTmp[1]);
                }

            }
            if (versionTmp.length >= 3) {
                thirdPartFirtDigit = Integer.valueOf(versionTmp[2].substring(0, 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (versionNum >= 330) {
            if (versionNum >= 500) {
                version = 6;
            } else if (versionNum >= 400) {
                version = 5;
            } else if (versionNum >= 331) {
                version = 4;
            } else {
                version = (thirdPartFirtDigit == 6 || thirdPartFirtDigit == 4 || thirdPartFirtDigit == 2) ? 3 : 2;
            }
        } else if (versionNum != 0) {
            version = 1;
        }
        return version;
    }
}
