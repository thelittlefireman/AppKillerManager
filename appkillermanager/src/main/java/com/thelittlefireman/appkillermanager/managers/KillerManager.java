package com.thelittlefireman.appkillermanager.managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.thelittlefireman.appkillermanager.BuildConfig;
import com.thelittlefireman.appkillermanager.devices.DeviceBase;
import com.thelittlefireman.appkillermanager.utils.KillerManagerAction;
import com.thelittlefireman.appkillermanager.utils.ActionUtils;
import com.thelittlefireman.appkillermanager.utils.LogUtils;
import com.thelittlefireman.appkillermanager.utils.SystemUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class KillerManager {

    private static DeviceBase sDevice;

    private static HashMap<KillerManagerAction, Integer> sCurrentNbActions;

    private static final int REQUEST_CODE_KILLER_MANAGER_ACTION = 52000;

    public static DeviceBase getDevice() {
        return sDevice;
    }

    public static void init(Context context) {
        // log error into a distant request bin logs for helps to debug
        // please do no change the adress
        /*HyperLog.initialize(context);
        HyperLog.setLogLevel(Log.VERBOSE);
        HyperLog.setURL("API URL");*/
        sDevice = DevicesManager.getDevice();
        sCurrentNbActions = new HashMap<>();
        sCurrentNbActions.put(KillerManagerAction.ACTION_AUTOSTART, 0);
        sCurrentNbActions.put(KillerManagerAction.ACTION_NOTIFICATIONS, 0);
        sCurrentNbActions.put(KillerManagerAction.ACTION_POWERSAVING, 0);
    }


    public static boolean isActionAvailable(Context context, KillerManagerAction killerManagerAction) {
        sDevice = DevicesManager.getDevice();
        boolean actionAvailable = false;
        if (sDevice != null) {
            switch (killerManagerAction) {
                case ACTION_AUTOSTART:
                    actionAvailable = sDevice.isActionAutoStartAvailable(context);
                    break;
                case ACTION_POWERSAVING:
                    actionAvailable = sDevice.isActionPowerSavingAvailable(context);
                    break;
                case ACTION_NOTIFICATIONS:
                    actionAvailable = sDevice.isActionNotificationAvailable(context);
                    break;
            }
        }
        return actionAvailable;
    }

    public static int getNumberOfIntentFromAction(Context context, KillerManagerAction killerManagerAction) {
        return getIntentFromAction(context, killerManagerAction).size();
    }

    /**
     * Return the intent for a specific killerManagerAction
     *
     * @param context the current context
     * @param killerManagerAction  the wanted killerManagerAction
     * @return the intent
     */
    private static List<Intent> getIntentFromAction(Context context, KillerManagerAction killerManagerAction) {
        init(context);
        sDevice = DevicesManager.getDevice();
        if (sDevice != null) {
            List<Intent> intentList = null;
            switch (killerManagerAction) {
                case ACTION_AUTOSTART:
                    intentList = sDevice.getActionAutoStart(context);
                    break;
                case ACTION_POWERSAVING:
                    intentList = sDevice.getActionPowerSaving(context);
                    break;
                case ACTION_NOTIFICATIONS:
                    intentList = sDevice.getActionNotification(context);
                    break;
            }
            if (intentList != null && !intentList.isEmpty() && ActionUtils.isAtLeastOneIntentAvailable(context, intentList)) {
                // Intent found killerManagerAction succeed
                return intentList;
            } else {
                LogUtils.e(KillerManager.class.getName(), "INTENT NOT FOUND :" +
                        ActionUtils.getExtrasDebugInformations(intentList) +
                        "LibraryVersionName :" + BuildConfig.VERSION_NAME +
                        "LibraryVersionCode :" + BuildConfig.VERSION_CODE +
                        "KillerManagerAction \n" + killerManagerAction.name() + "SYSTEM UTILS \n" +
                        SystemUtils.getDefaultDebugInformation() + "DEVICE \n" +
                        sDevice.getExtraDebugInformations(context));
                // Intent not found killerManagerAction failed
                return Collections.emptyList();
            }
        } else {
            // device not found killerManagerAction failed
            return Collections.emptyList();
               /* LogUtils.e(KillerManager.class.getName(), "DEVICE NOT FOUND" + "SYSTEM UTILS \n" +
                        SystemUtils.getDefaultDebugInformation());*/
        }
    }

    /**
     * Execute the killerManagerAction
     *
     * @param activity the current activity
     * @param killerManagerAction   the wanted killerManagerAction to execute
     * @return true : killerManagerAction succeed; false killerManagerAction failed
     */
    public static boolean doAction(Activity activity, KillerManagerAction killerManagerAction) {
        return doAction(activity, killerManagerAction, sCurrentNbActions.get(killerManagerAction));
    }

    /**
     * Execute the killerManagerAction
     *
     * @param activity the current activity
     * @param killerManagerAction   the wanted killerManagerAction to execute
     * @return true : killerManagerAction succeed; false killerManagerAction failed
     */
    private static boolean doAction(Activity activity, KillerManagerAction killerManagerAction, int index) {
        // Avoid main app to crash when intent denied by using try catch
        try {
            List<Intent> intentList = getIntentFromAction(activity, killerManagerAction);
            if (intentList != null && !intentList.isEmpty() && intentList.size() > index) {
                Intent intent = intentList.get(index);
                if (ActionUtils.isIntentAvailable(activity, intent)) {
                    activity.startActivityForResult(intent, REQUEST_CODE_KILLER_MANAGER_ACTION);
                    // Intent found killerManagerAction succeed
                    return true;
                }
            }

        } catch (Exception e) {
            // Exception handle killerManagerAction failed
            LogUtils.e(KillerManager.class.getName(), e.getMessage());
            return false;
        }
        return false;
    }

    public static void onActivityResult(Activity activity, KillerManagerAction killerManagerAction, int requestCode) {
        if(requestCode == REQUEST_CODE_KILLER_MANAGER_ACTION) {
            int value = sCurrentNbActions.get(killerManagerAction);
            value++;
            sCurrentNbActions.put(killerManagerAction, value);

            List<Intent> intentList = getIntentFromAction(activity, killerManagerAction);
            if (intentList != null && !intentList.isEmpty() && intentList.size() > sCurrentNbActions.get(killerManagerAction)) {
                doAction(activity, killerManagerAction, sCurrentNbActions.get(killerManagerAction));
            } else {
                // reset if no more intent
                sCurrentNbActions.put(killerManagerAction, 0);
            }
        }
    }

    public static void onActivityResultForDialog(Activity activity, KillerManagerAction killerManagerAction, int requestCode) {
        if(requestCode == REQUEST_CODE_KILLER_MANAGER_ACTION) {

        }
    }
}
