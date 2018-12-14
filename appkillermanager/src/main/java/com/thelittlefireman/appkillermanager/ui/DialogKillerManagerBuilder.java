package com.thelittlefireman.appkillermanager.ui;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.deviceUi.SettingFragment;
import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.models.KillerManagerActionType;
import com.thelittlefireman.appkillermanager.utils.KillerManagerUtils;
import com.thelittlefireman.appkillermanager.utils.LogUtils;

import java.util.ArrayList;
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
    private List<KillerManagerActionType> mKillerManagerActionTypeList;
    private String titleMessage;
    private String contentHeaderMessage;
    private boolean mEnableDontShowAgain;

    @DrawableRes
    private int mIconRes;

    @StringRes
    private int mTitleResMessage, mContentHeaderResMessage;

    public DialogKillerManagerBuilder() {
        mContentHeaderResMessage = -1;
        mTitleResMessage = -1;
        mIconRes = -1;
        mKillerManagerActionTypeList = new ArrayList<>();
        mKillerManagerActionTypeList.add(KillerManagerActionType.ACTION_EMPTY);
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
        this.mIconRes = iconRes;
        return this;
    }

    public DialogKillerManagerBuilder setTitleMessage(@NonNull String titleMessage) {
        this.titleMessage = titleMessage;
        return this;
    }

    public DialogKillerManagerBuilder setEnableDontShowAgain(@NonNull boolean isDontShowAgain) {
        this.mEnableDontShowAgain = isDontShowAgain;
        return this;
    }

    public DialogKillerManagerBuilder setContentHeaderMessage(@NonNull String contentHeaderMessage) {
        this.contentHeaderMessage = contentHeaderMessage;
        return this;
    }

    public DialogKillerManagerBuilder setTitleMessage(@StringRes @NonNull int titleResMessage) {
        this.mTitleResMessage = titleResMessage;
        return this;
    }

    public DialogKillerManagerBuilder setContentHeaderMessage(@StringRes @NonNull int contentResMessage) {
        this.mContentHeaderResMessage = contentResMessage;
        return this;
    }

    public DialogKillerManagerBuilder setKillerManagerActionTypeList(
            @NonNull KillerManagerActionType killerManagerActionTypeList) {
        this.mKillerManagerActionTypeList.add(killerManagerActionTypeList);
        return this;
    }

    public void show() {

        WizardDialog materialDialog;
        if (mActivity == null) {
            throw new NullPointerException("Activity parameter can't be null");
        }
        mKillerManager = KillerManager.getInstance(mActivity);

        if (mKillerManager.getDevice() == null) {
            LogUtils.i(this.getClass().getName(), "Device not in the list no need to show the dialog");
            return;
        }

        WizardDialog.Builder dialogBuilder = new WizardDialog.Builder(mActivity,
                                                                      R.style.MaterialDialog_Light_Fullscreen);
        List<KillerManagerAction> killerManagerActionList = mKillerManager.getKillerManagerActionFromActionType
                (mActivity, mKillerManagerActionTypeList);

        if (killerManagerActionList.isEmpty()) {
            LogUtils.i(this.getClass().getName(),
                       "No action available for this device no need to show the dialog");
            return;
        }

        dialogBuilder.addFragment(SettingFragment.class,
                                  SettingFragment.generateArguments(killerManagerActionList, mEnableDontShowAgain));
        dialogBuilder.showHeader(false);
        if (mContentHeaderResMessage != -1) {
            dialogBuilder.setMessage(mContentHeaderResMessage);
        } else {
            dialogBuilder.setMessage(contentHeaderMessage);
        }

        if (mIconRes != -1) {
            dialogBuilder.setIcon(mIconRes);
        } else {
            dialogBuilder.setIcon(android.R.drawable.ic_dialog_alert);
        }

        if (mTitleResMessage != -1) {
            dialogBuilder.setTitle(mTitleResMessage);
        } else if (titleMessage != null && !titleMessage.isEmpty()) {
            dialogBuilder.setTitle(titleMessage);
        } else {
            dialogBuilder.setTitle(mActivity.getString(R.string.dialog_title_notification,
                                                       mKillerManager.getDevice().getDeviceManufacturer().toString()));
        }

        // TODO CHANGE
        if (!(mEnableDontShowAgain && KillerManagerUtils.isDontShowAgain(mActivity))) {
            materialDialog = dialogBuilder.create();
            materialDialog.show(mActivity.getSupportFragmentManager(), DIALOG_TAG);
        }
    }
}