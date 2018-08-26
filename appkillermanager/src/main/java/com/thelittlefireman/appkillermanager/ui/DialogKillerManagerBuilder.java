package com.thelittlefireman.appkillermanager.ui;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.utils.KillerManagerUtils;
import com.thelittlefireman.appkillermanager.utils.LogUtils;

import de.mrapp.android.dialog.WizardDialog;

public class DialogKillerManagerBuilder {
    private Activity mActivity;

    public DialogKillerManagerBuilder() {
        contentResMessage = -1;
        titleResMessage = -1;
        iconRes = -1;
    }

    public DialogKillerManagerBuilder(Activity activity) {
        this();
        mActivity = activity;
    }

    private KillerManager.Action mAction;

    private boolean enableDontShowAgain = true;

    private String titleMessage;
    private String contentMessage;

    @DrawableRes
    private int iconRes;

    @StringRes
    private int titleResMessage, contentResMessage;

    public DialogKillerManagerBuilder setActivity(Activity activity) {
        mActivity = activity;
        return this;
    }

    public DialogKillerManagerBuilder setAction(KillerManager.Action action) {
        mAction = action;
        return this;
    }

    public DialogKillerManagerBuilder setIconRes(@NonNull @DrawableRes int iconRes) {
        this.iconRes = iconRes;
        return this;
    }

    public DialogKillerManagerBuilder setDontShowAgain(boolean enable) {
        this.enableDontShowAgain = enable;
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
        if (mAction == null) {
            throw new NullPointerException("KillerManagerAction parameter can't be null");
        }
        KillerManager.init(mActivity);

        if (!KillerManager.isActionAvailable(mActivity, mAction)) {
            LogUtils.i(this.getClass().getName(), "This action is not available for this device no need to show the dialog");
            return;
        }

        if (KillerManager.getDevice() == null) {
            LogUtils.i(this.getClass().getName(), "Device not in the list no need to show the dialog");
            return;
        }


        if(KillerManager.getNumberOfIntentFromAction(mActivity,mAction)>1){
            // multiple intent
        }


        dialogBuilder.setHeaderBackground(R.drawable.header_background);
        dialogBuilder.addFragment(R.string.fragment1_title, Fragment1.class);
        dialogBuilder.addFragment(R.string.fragment2_title, Fragment2.class);
        dialogBuilder.addFragment(R.string.fragment3_title, Fragment3.class);
        dialogBuilder.setTabPosition(TabPosition.PREFER_HEADER);
        dialogBuilder.enableTabLayout(true);
        dialogBuilder.enableSwipe(true);
        dialogBuilder.showButtonBar(true);
        dialog.show();

        WizardDialog.Builder dialogBuilder = new WizardDialog.Builder(mActivity,R.style.MaterialDialog_Light_Fullscreen);
        dialogBuilder.showHeader(false);

        builder.positiveText(R.string.dialog_button)
                .customView(R.layout.md_dialog_custom_view, false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        KillerManager.doAction(mActivity, mAction);
                    }
                })
                .negativeText(android.R.string.cancel);

        if (iconRes != -1) {
            builder.iconRes(iconRes);
        } else {
            builder.iconRes(android.R.drawable.ic_dialog_alert);
        }

        if (titleResMessage != -1) {
            builder.title(titleResMessage);
        } else if (titleMessage != null && !titleMessage.isEmpty()) {
            builder.title(titleMessage);
        } else {
            builder.title(mActivity.getString(R.string.dialog_title_notification, KillerManager.getDevice().getDeviceManufacturer().toString()));
        }

        if (this.enableDontShowAgain) {
            builder.checkBoxPromptRes(R.string.dialog_do_not_show_again, false,
                    new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            KillerManagerUtils.setDontShowAgain(mActivity, mAction, isChecked);
                        }
                    });
        }

        if (!(enableDontShowAgain && KillerManagerUtils.isDontShowAgain(mActivity, mAction))) {
            materialDialog = builder.show();

            // init custom view
            initView(materialDialog.getCustomView());
        }
    }

    private void initView(View view) {
        TextView contentTextView = view.findViewById(R.id.md_content);
        CheckBox doNotShowAgainCheckBox = view.findViewById(R.id.md_promptCheckbox);
        ImageView helpImageView = view.findViewById(R.id.md_imageView);

        if (contentResMessage != -1) {
            contentTextView.setText(contentResMessage);
        } else if (contentMessage != null && !contentMessage.isEmpty()) {
            contentTextView.setText(contentMessage);
        } else {
            //TODO CUSTOM MESSAGE FOR SPECIFITQUE ACTIONS AND SPECIFIC DEVICE
            contentTextView.setText(String.format(mActivity.getString(R.string.dialog_huawei_notification),
                    mActivity.getString(
                            R.string.app_name)));
        }

        if (this.enableDontShowAgain) {
            doNotShowAgainCheckBox.setVisibility(View.VISIBLE);
            doNotShowAgainCheckBox.setText(R.string.dialog_do_not_show_again);
            doNotShowAgainCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    KillerManagerUtils.setDontShowAgain(mActivity, mAction, isChecked);
                }
            });
        }

        //TODO add other specific images
        int helpImageRes = 0;
        switch (mAction) {
            case ACTION_AUTOSTART:
                helpImageRes = KillerManager.getDevice().getHelpImageAutoStart();
                break;
            case ACTION_POWERSAVING:
                helpImageRes = KillerManager.getDevice().getHelpImagePowerSaving();
                break;
            case ACTION_NOTIFICATIONS:
                helpImageRes = KillerManager.getDevice().getHelpImageNotification();
                break;
        }

        if (helpImageRes != 0) {
            helpImageView.setImageResource(helpImageRes);
        }
    }
}