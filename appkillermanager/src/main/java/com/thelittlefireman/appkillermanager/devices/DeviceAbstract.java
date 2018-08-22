package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;

import com.thelittlefireman.appkillermanager.utils.ActionsUtils;
import com.thelittlefireman.appkillermanager.utils.LogUtils;

public abstract class DeviceAbstract implements DeviceBase {
    @CallSuper
    @Override
    public String getExtraDebugInformations(Context context) {
        // ----- PACAKGE INFORMATIONS ----- //
        StringBuilder resultBuilder = new StringBuilder();
        for (ComponentName componentName : getComponentNameList()) {
            resultBuilder.append(componentName.getPackageName()+componentName.getClassName());
            resultBuilder.append(":");
            resultBuilder.append(ActionsUtils.isIntentAvailable(context, componentName));
        }
        for (String intentAction : getIntentActionList()) {
            resultBuilder.append(intentAction);
            resultBuilder.append(":");
            resultBuilder.append(ActionsUtils.isIntentAvailable(context, intentAction));
        }
        return resultBuilder.toString();
    }

    @Override
    public boolean needToUseAlongwithActionDoseMode() {
        return false;
    }

    @Override
    @DrawableRes
    public int getHelpImageAutoStart() {
        return 0;
    }

    @Override
    @DrawableRes
    public int getHelpImageNotification() {
        return 0;
    }

    @DrawableRes
    @Override
    public int getHelpImagePowerSaving() {
        return 0;
    }

    @Override
    public boolean isActionDozeModeNotNecessary(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            return pm.isIgnoringBatteryOptimizations(context.getPackageName());
        }
        return false;
    }

    @Override
    public Intent getActionDozeMode(Context context) {
        //Android 7.0+ Doze
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            boolean ignoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(context.getPackageName());
            if (!ignoringBatteryOptimizations) {
                Intent dozeIntent = ActionsUtils.createIntent();
                // Cannot fire Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                // due to Google play device policy restriction !
                dozeIntent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                dozeIntent.setData(Uri.parse("package:" + context.getPackageName()));
                return dozeIntent;
            } else {
                LogUtils.i(this.getClass().getName(), "getActionDozeMode" + "App is already enable to ignore doze " +
                        "battery optimization");
            }
        }
        return null;
    }
}
