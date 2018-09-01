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
    private static final int REQUEST_CODE_KILLER_MANAGER_ACTION = 52000;

    private KillerManager(Context context) {
        // log error into a distant request bin logs for helps to debug
        // please do no change the adress
        /*HyperLog.initialize(context);
        HyperLog.setLogLevel(Log.VERBOSE);
        HyperLog.setURL("API URL");*/
        mDevice = DevicesManager.getDevice();
        mCurrentNbActions = new HashMap<>();
        mCurrentNbActions.put(KillerManagerAction.ACTION_AUTOSTART, 0);
        mCurrentNbActions.put(KillerManagerAction.ACTION_NOTIFICATIONS, 0);
        mCurrentNbActions.put(KillerManagerAction.ACTION_POWERSAVING, 0);
    }

    private static KillerManager sKillerManager;

    public static synchronized KillerManager getInstance(Context context) {
        if (sKillerManager == null) {
            sKillerManager = new KillerManager(context);
        }
        return sKillerManager;
    }

    private DeviceBase mDevice;

    private HashMap<KillerManagerAction, Integer> mCurrentNbActions;

    public DeviceBase getDevice() {
        return mDevice;
    }

    public boolean isActionAvailable(Context context, KillerManagerAction killerManagerAction) {
        mDevice = DevicesManager.getDevice();
        boolean actionAvailable = false;
        if (mDevice != null) {
            switch (killerManagerAction) {
                case ACTION_AUTOSTART:
                    actionAvailable = mDevice.isActionAutoStartAvailable(context);
                    break;
                case ACTION_POWERSAVING:
                    actionAvailable = mDevice.isActionPowerSavingAvailable(context);
                    break;
                case ACTION_NOTIFICATIONS:
                    actionAvailable = mDevice.isActionNotificationAvailable(context);
                    break;
            }
        }
        return actionAvailable;
    }

    /**
     * Return the intent for a specific killerManagerAction
     *
     * @param context             the current context
     * @param killerManagerAction the wanted killerManagerAction
     * @return the intent
     */
    private List<Intent> getIntentFromAction(Context context, KillerManagerAction killerManagerAction) {
        mDevice = DevicesManager.getDevice();
        if (mDevice != null) {
            List<Intent> intentList = null;
            switch (killerManagerAction) {
                case ACTION_AUTOSTART:
                    intentList = mDevice.getActionAutoStart(context);
                    break;
                case ACTION_POWERSAVING:
                    intentList = mDevice.getActionPowerSaving(context);
                    break;
                case ACTION_NOTIFICATIONS:
                    intentList = mDevice.getActionNotification(context);
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
                        mDevice.getExtraDebugInformations(context));
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
     * @param activity            the current activity
     * @param killerManagerAction the wanted killerManagerAction to execute
     * @return true : killerManagerAction succeed; false killerManagerAction failed
     */
    public boolean doAction(Activity activity, KillerManagerAction killerManagerAction) {
        return doAction(activity, killerManagerAction, mCurrentNbActions.get(killerManagerAction));
    }

    /**
     * Execute the killerManagerAction
     *
     * @param activity            the current activity
     * @param killerManagerAction the wanted killerManagerAction to execute
     * @return true : killerManagerAction succeed; false killerManagerAction failed
     */
    private boolean doAction(Activity activity, KillerManagerAction killerManagerAction, int index) {
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

    public void onActivityResult(Activity activity, KillerManagerAction killerManagerAction, int requestCode) {
        if (requestCode == REQUEST_CODE_KILLER_MANAGER_ACTION) {
            int value = mCurrentNbActions.get(killerManagerAction);
            value++;
            mCurrentNbActions.put(killerManagerAction, value);

            List<Intent> intentList = getIntentFromAction(activity, killerManagerAction);
            if (intentList != null && !intentList.isEmpty() && intentList.size() > mCurrentNbActions.get(killerManagerAction)) {
                doAction(activity, killerManagerAction, mCurrentNbActions.get(killerManagerAction));
            } else {
                // reset if no more intent
                mCurrentNbActions.put(killerManagerAction, 0);
            }
        }
    }

    /*private void onActivityResultForDialog(Activity activity, KillerManagerAction killerManagerAction, int requestCode) {
        if (requestCode == REQUEST_CODE_KILLER_MANAGER_ACTION) {
            int value = mCurrentNbActions.get(killerManagerAction);
            value++;
            mCurrentNbActions.put(killerManagerAction, value);
            List<Intent> intentList = getIntentFromAction(activity, killerManagerAction);
            if (intentList == null || intentList.isEmpty() || intentList.size() <= mCurrentNbActions.get(killerManagerAction)) {
                // reset if no more intent
                mCurrentNbActions.put(killerManagerAction, 0);
            }
        }
    }*/
}
