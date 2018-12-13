package com.thelittlefireman.appkillermanager.ui;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.deviceUi.SettingFragment;
import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.utils.KillerManagerUtils;
import com.thelittlefireman.appkillermanager.utils.LogUtils;

import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import de.mrapp.android.dialog.WizardDialog;

public class DialogKillerManagerBuilder {
    private static final String DIALOG_TAG = "KILLER_MANAGER_DIALOG";
    private AppCompatActivity mActivity;
    private KillerManager mKillerManager;
    private List<KillerManagerAction> mKillerManagerActionList;
    private String titleMessage;
    private String contentMessage;
    @DrawableRes
    private int iconRes;
    @StringRes
    private int titleResMessage, contentResMessage;

    public DialogKillerManagerBuilder() {
        contentResMessage = -1;
        titleResMessage = -1;
        iconRes = -1;
    }

    public DialogKillerManagerBuilder(AppCompatActivity activity) {
        this();
        mActivity = activity;
    }

    public DialogKillerManagerBuilder setActivity(AppCompatActivity activity) {
        mActivity = activity;
        return this;
    }

    public DialogKillerManagerBuilder setIconRes(@NonNull @DrawableRes int iconRes) {
        this.iconRes = iconRes;
        return this;
    }

    public DialogKillerManagerBuilder setTitleMessage(@NonNull String titleMessage) {
        this.titleMessage = titleMessage;
        return this;
    }

    public DialogKillerManagerBuilder setContentMessage(@NonNull String contentMessage) {
        this.contentMessage = contentMessage;
        return this;
    }

    public DialogKillerManagerBuilder setTitleMessage(@StringRes @NonNull int titleResMessage) {
        this.titleResMessage = titleResMessage;
        return this;
    }

    public DialogKillerManagerBuilder setContentMessage(@StringRes @NonNull int contentResMessage) {
        this.contentResMessage = contentResMessage;
        return this;
    }

    public void show() {

        WizardDialog materialDialog;
        if (mActivity == null) {
            throw new NullPointerException("Activity parameter can't be null");
        }
        mKillerManager = KillerManager.getInstance(mActivity);

        /*if (!mKillerManager.isActionAvailable(mActivity, mAction)) {
            LogUtils.i(this.getClass().getName(),
                       "This action is not available for this device no need to show the dialog");
            return;
        }*/

        if (mKillerManager.getDevice() == null) {
            LogUtils.i(this.getClass().getName(), "Device not in the list no need to show the dialog");
            return;
        }

        WizardDialog.Builder dialogBuilder = new WizardDialog.Builder(mActivity,
                                                                      R.style.MaterialDialog_Light_Fullscreen);
        final SettingFragment settingFragment = SettingFragment.newInstance(mKillerManagerActionList);
        dialogBuilder.addFragment(settingFragment);
        dialogBuilder.showHeader(false);

        if (iconRes != -1) {
            dialogBuilder.setIcon(iconRes);
        } else {
            dialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        }

        if (titleResMessage != -1) {
            dialogBuilder.setTitle(titleResMessage);
        } else if (titleMessage != null && !titleMessage.isEmpty()) {
            dialogBuilder.setTitle(titleMessage);
        } else {
            dialogBuilder.setTitle(mActivity.getString(R.string.dialog_title_notification,
                                                       mKillerManager.getDevice().getDeviceManufacturer().toString()));
        }

        if (!(mEnableDontShowAgain && KillerManagerUtils.isDontShowAgain(mActivity, mAction))) {
            materialDialog = dialogBuilder.create();
            materialDialog.show(mActivity.getSupportFragmentManager(), DIALOG_TAG);
        }
    }
}