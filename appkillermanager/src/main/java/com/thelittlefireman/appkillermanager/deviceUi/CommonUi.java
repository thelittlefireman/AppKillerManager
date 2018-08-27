package com.thelittlefireman.appkillermanager.deviceUi;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.devices.DeviceBase;
import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.utils.KillerManagerAction;

public class CommonUi extends DeviceAbstractUi {

    private ImageView mHelpImageView;

    @Override
    public int getLayout() {
        return R.layout.md_dialog_ui;
    }

    @Override
    public View onCreateView() {
        mHelpImageView = mRootView.findViewById(R.id.md_dialog_help_imageView);

        //TODO add other specific images
        int helpImageRes = 0;
        switch (mAction) {
            case ACTION_AUTOSTART:
                helpImageRes = mDevice.getHelpImageAutoStart();
                break;
            case ACTION_POWERSAVING:
                helpImageRes = mDevice.getHelpImagePowerSaving();
                break;
            case ACTION_NOTIFICATIONS:
                helpImageRes = mDevice.getHelpImageNotification();
                break;
        }

        if (helpImageRes != 0) {
            mHelpImageView.setImageResource(helpImageRes);
        }

        return mRootView;
    }
}
