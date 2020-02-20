package com.thelittlefireman.appkillermanager.ui;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
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

import org.jetbrains.annotations.NotNull;

public class DialogKillerManagerBuilder {
    private Context mContext;
    
    /*private Activity mActivity;
    private Integer returnCode;*/
    
    private MaterialDialog materialDialog;
    private MaterialDialog.Builder builder;
    private OnNoActionFoundException onNoActionFoundException;
    private UnAvailableActionException unAvailableActionException;
    private UnSupportedDeviceException onUnSupportedDevice;

    public interface OnNoActionFoundException {
        void onNoActionFound(KillerManager.NoActionFoundException e);
        void onNoActionFound();
    }

    public static class UnAvailableActionException extends Exception {
        UnAvailableActionException() {
        }

        UnAvailableActionException(String message) {
            super(message);
        }
    }


    public static class  UnSupportedDeviceException extends Exception {

    }


    private DialogKillerManagerBuilder() {
        contentResMessage = -1;
        titleResMessage = -1;
        iconRes = -1;
    }

    public DialogKillerManagerBuilder(Context context) {
        this();
        mContext = context;
    }
    
    /*public DialogKillerManagerBuilder(Activity activity, Integer returnCode) {
        this();
        this.mActivity = activity;
        this.returnCode = returnCode;
    }*/

    private KillerManager.Actions mAction;

    private boolean enableDontShowAgain = true;

    private String titleMessage;
    private String contentMessage;

    @DrawableRes
    private int iconRes;

    @StringRes
    private int titleResMessage, contentResMessage;

    /*public DialogKillerManagerBuilder setContext(Context context) {
        mContext = context;
        return this;
    }

    public void setActivity(Activity mActivity, @NonNull Integer returnCode) {
        this.mActivity   = mActivity;
        this.returnCode = returnCode;
    }*/

    public DialogKillerManagerBuilder setAction(KillerManager.Actions action) {
        mAction = action;
        return this;
    }

    public DialogKillerManagerBuilder setIconRes(@NonNull @DrawableRes Integer iconRes) {
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

    public DialogKillerManagerBuilder setTitleMessage(@StringRes @NonNull Integer titleResMessage) {
        this.titleResMessage = titleResMessage;
        return this;
    }

    public DialogKillerManagerBuilder setContentMessage(@StringRes @NonNull Integer contentResMessage) {
        this.contentResMessage = contentResMessage;
        return this;
    }

    public DialogKillerManagerBuilder setOnNoActionFoundException(OnNoActionFoundException onNoActionFoundException) {
        this.onNoActionFoundException = onNoActionFoundException;
        return this;
    }

    public DialogKillerManagerBuilder setUnAvailableActionException(UnAvailableActionException unAvailableActionException) {
        this.unAvailableActionException = unAvailableActionException;
        return this;
    }

    public DialogKillerManagerBuilder setOnUnSupportedDevice(UnSupportedDeviceException onUnSupportedDevice) {
        this.onUnSupportedDevice = onUnSupportedDevice;
        return this;
    }

    public void build() throws NullPointerException{
        //MaterialDialog materialDialog;
        if (mContext == null ) {
            throw new NullPointerException("Context && mActivity can't be null at the same time");
        }
        if (mAction == null) {
            throw new NullPointerException("Action can't be null");
        }
        
        builder = new MaterialDialog.Builder(mContext);

        
        builder.positiveText(R.string.dialog_button)
                .customView(R.layout.md_dialog_custom_view, false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NotNull MaterialDialog dialog, @NotNull DialogAction which) {
                        try {
                            if (mAction == KillerManager.Actions.ACTION_AUTOSTART){
                                if (mContext != null) {
                                    KillerManager.doActionAutoStart(mContext);
                                }
                            }else if (mAction == KillerManager.Actions.ACTION_NOTIFICATIONS){
                                if (mContext != null) {
                                    KillerManager.doActionNotification(mContext);
                                }
                            }else if (mAction == KillerManager.Actions.ACTION_POWERSAVING){
                                if (mContext != null) {
                                    KillerManager.doActionPowerSaving(mContext);
                                }
                            }
                        } catch (KillerManager.NoActionFoundException e) {
                            if (onNoActionFoundException != null){
                                onNoActionFoundException.onNoActionFound(e);
                            }else{
                                dialog.dismiss();
                            }
                            e.printStackTrace();
                        }
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
            if (mContext != null) {
                builder.title(mContext.getString(R.string.dialog_title_notification, KillerManager.getDevice().getDeviceManufacturer().toString()));
            }
        }

        if (this.enableDontShowAgain) {
            if (mContext != null) {
                builder.checkBoxPromptRes(R.string.dialog_do_not_show_again, false,
                        new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                KillerManagerUtils.setDontShowAgain(mContext, mAction, isChecked);
                            }
                        });
            }
        }
    }

    private boolean isActionAvailable(){
        if (mContext != null) {
            return  KillerManager.isActionAvailable(mContext, mAction);
        }
        return false;
    }

    private boolean isDontShowAgain(){
        if(mContext != null){
           return KillerManagerUtils.isDontShowAgain(mContext, mAction);
        }
        return false;
    }

    public void show() throws NullPointerException, UnAvailableActionException, UnSupportedDeviceException {
        build();
        if (! KillerManager.isDeviceSupported()) {
            LogUtils.i(this.getClass().getName(), "Device not in the list no need to show the dialog");
            throw new UnSupportedDeviceException();
        }
        if (!isActionAvailable()) {
            LogUtils.i(this.getClass().getName(), "This action is not available for this device no need to show the dialog");
            throw  new UnAvailableActionException("This action is not available for this device no need to show the dialog");
        }

        if (!(enableDontShowAgain && isDontShowAgain())) {
            materialDialog = builder.show();
            // init custom view
            if (materialDialog.getCustomView() != null) {
                initView(materialDialog.getCustomView());
            }
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
            if (mContext != null) {
                contentTextView.setText(String.format(mContext.getString(R.string.dialog_huawei_notification),
                        mContext.getString(
                                R.string.app_name)));
            }
        }

        if (this.enableDontShowAgain) {
            doNotShowAgainCheckBox.setVisibility(View.VISIBLE);
            doNotShowAgainCheckBox.setText(R.string.dialog_do_not_show_again);
            if (mContext != null) {
                doNotShowAgainCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        KillerManagerUtils.setDontShowAgain(mContext, mAction, isChecked);
                    }
                });
            }
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

    public MaterialDialog getMaterialDialog() {
        return materialDialog;
    }

    public MaterialDialog.Builder getBuilder() {
        return builder;
    }
}