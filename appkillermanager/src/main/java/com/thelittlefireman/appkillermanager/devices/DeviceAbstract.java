package com.thelittlefireman.appkillermanager.devices;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.annotation.CallSuper;

import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.utils.ActionUtils;
import com.thelittlefireman.appkillermanager.utils.LogUtils;

public abstract class DeviceAbstract implements DeviceBase {
    @CallSuper
    @Override
    public String getExtraDebugInformations(Context context) {
        // ----- PACAKGE INFORMATIONS ----- //
        StringBuilder resultBuilder = new StringBuilder();
        if(getComponentNameList() !=null) {
            for (ComponentName componentName : getComponentNameList()) {
                resultBuilder.append(componentName.getPackageName() + componentName.getClassName());
                resultBuilder.append(":");
                resultBuilder.append(ActionUtils.isIntentAvailable(context, componentName));
            }
        }
        if(getIntentActionList() !=null) {
            for (String intentAction : getIntentActionList()) {
                resultBuilder.append(intentAction);
                resultBuilder.append(":");
                resultBuilder.append(ActionUtils.isIntentAvailable(context, intentAction));
            }
        }
        return resultBuilder.toString();
    }

    @Override
    public boolean needToUseAlongwithActionDoseMode() {
        return false;
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
    public KillerManagerAction getActionDozeMode(Context context) {
        //Android 7.0+ Doze
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            boolean ignoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(context.getPackageName());
            if (!ignoringBatteryOptimizations) {
                Intent dozeIntent = ActionUtils.createIntent();
                // Cannot fire Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                // due to Google play device policy restriction !
                dozeIntent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                dozeIntent.setData(Uri.parse("package:" + context.getPackageName()));
                // TODO add text
                return new KillerManagerAction(dozeIntent);
            } else {
                LogUtils.i(this.getClass().getName(), "getActionDozeMode" + "App is already enable to ignore doze " +
                        "battery optimization");
            }
        }
        return null;
    }
}
