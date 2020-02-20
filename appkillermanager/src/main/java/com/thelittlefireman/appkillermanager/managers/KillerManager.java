package com.thelittlefireman.appkillermanager.managers;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;

import com.thelittlefireman.appkillermanager.devices.DeviceBase;
import com.thelittlefireman.appkillermanager.utils.ActionsUtils;
import com.thelittlefireman.appkillermanager.utils.LogUtils;
import com.thelittlefireman.appkillermanager.utils.SystemUtils;

public class KillerManager {

    public static class NoActionFoundException extends Exception {
        NoActionFoundException() {
            this("Intent couldn't find action");
        }

        NoActionFoundException(String message) {
            super(message);
        }
    }

    public enum Actions {
        ACTION_AUTOSTART("ACTION_AUTOSTART"),
        ACTION_NOTIFICATIONS("ACTION_NOTIFICATIONS"),
        ACTION_POWERSAVING("ACTION_POWERSAVING");

        private String mValue;

        Actions(String value) {
            this.mValue = value;
        }

        public String toString() {
            return this.mValue;
        }
    }

    //private static DeviceBase sDevice;

    public static DeviceBase getDevice() {
        return DevicesManager.getDevice();
    }

    public static boolean isActionAvailable (Context context, Actions actions) {
        DeviceBase sDevice = DevicesManager.getDevice();
        boolean actionAvailable = false;
        if (sDevice != null) {
            switch (actions) {
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

    public static boolean isAnyActionAvailable(Context context){
        for (Actions action : Actions.values()){
            if (isActionAvailable(context, action)){
                return true;
            }
        }
        return false;
    }

    public static boolean isDeviceSupported(){
        return  DevicesManager.getDevice() != null;
    }

    /**
     * Return the intent for a specific action
     *
     * @param context the current context
     * @param actions the wanted actions
     * @return the intent
     */
    @Nullable
    private static Intent getIntentFromAction(Context context, Actions actions) {
        DeviceBase sDevice = DevicesManager.getDevice();
        if (sDevice != null) {
            Intent intent = null;
            switch (actions) {
                case ACTION_AUTOSTART:
                    intent = sDevice.getActionAutoStart(context);
                    break;
                case ACTION_POWERSAVING:
                    intent = sDevice.getActionPowerSaving(context);
                    break;
                case ACTION_NOTIFICATIONS:
                    intent = sDevice.getActionNotification(context);
                    break;
            }
            if (intent != null && ActionsUtils.isIntentAvailable(context, intent)) {
                // Intent found action succeed
                return intent;
            } else {
                LogUtils.e(KillerManager.class.getName(), "INTENT NOT FOUND :" +
                        ActionsUtils.getExtrasDebugInformations(intent) + "Actions \n" +
                        actions.name() + "SYSTEM UTILS \n" +
                        SystemUtils.getDefaultDebugInformation() + "DEVICE \n" +
                        sDevice.getExtraDebugInformations(context));
                // Intent not found action failed
                return null;
            }
        } else {
            // device not found action failed
            return null;
               /* LogUtils.e(KillerManager.class.getName(), "DEVICE NOT FOUND" + "SYSTEM UTILS \n" +
                        SystemUtils.getDefaultDebugInformation());*/
        }
    }

    /**
     * Execute the action
     *
     * @param context the current context
     * @param actions the wanted action to execute
     */
    private static void doAction(Context context, Actions actions) throws NoActionFoundException {
        // Avoid main app to crash when intent denied by using try catch
        try {
            Intent intent = getIntentFromAction(context, actions);
            if (intent != null && ActionsUtils.isIntentAvailable(context, intent)) {
                context.startActivity(intent);
                // Intent found action succeed
            }

        } catch (Exception e) {
            // Exception handle action failed
            LogUtils.e(KillerManager.class.getName(), e.getMessage());
            throw new NoActionFoundException();
        }
    }
/*
    *//**
     * Execute the action
     *
     * @param activity the current activity
     * @param actions the wanted action to execute
     *//*
    private static void doAction(Activity activity, Actions actions, Integer code) throws NoActionFoundException {
        // Avoid main app to crash when intent denied by using try catch
        try {
            Intent intent = getIntentFromAction(activity, actions);
            if (intent != null && ActionsUtils.isIntentAvailable(activity, intent)) {
                if (code == null) {
                    activity.startActivity(intent);
                    // Intent found action succeed
                }else {
                    activity.startActivityForResult(intent, code);
                }

            }

        } catch (Exception e) {
            // Exception handle action failed
            LogUtils.e(KillerManager.class.getName(), e.getMessage());
            throw new NoActionFoundException();
        }
    }*/


    public static void doActionAutoStart(Context context) throws NoActionFoundException {
        doAction(context, Actions.ACTION_AUTOSTART);
    }

    public static void doActionNotification(Context context) throws NoActionFoundException {
        doAction(context, Actions.ACTION_NOTIFICATIONS);
    }

    public static void doActionPowerSaving(Context context) throws NoActionFoundException {
        doAction(context, Actions.ACTION_POWERSAVING);
    }

    /*public static void doActionAutoStart(Activity activity, Integer code) throws NoActionFoundException {
        doAction(activity, Actions.ACTION_AUTOSTART, code );
    }

    public static void doActionNotification(Activity activity, Integer code) throws NoActionFoundException {
        doAction(activity, Actions.ACTION_NOTIFICATIONS, code);
    }

    public static void doActionPowerSaving(Activity activity, Integer code) throws NoActionFoundException {
        doAction(activity, Actions.ACTION_POWERSAVING, code);
    }*/
}
