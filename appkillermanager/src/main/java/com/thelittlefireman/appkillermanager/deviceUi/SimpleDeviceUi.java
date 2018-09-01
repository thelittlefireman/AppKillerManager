package com.thelittlefireman.appkillermanager.deviceUi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.devices.DeviceBase;

import static com.thelittlefireman.appkillermanager.utils.KillerManagerAction.ACTION_AUTOSTART;

public class SimpleDeviceUi extends DeviceBaseUi {

    private ImageView mHelpImageView;


    @Override
    public int getLayout() {
        return R.layout.md_dialog_simple_ui;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        mHelpImageView = mRootView.findViewById(R.id.md_dialog_help_imageView);
        mContentMessage = mRootView.findViewById(R.id.md_content_message);

        DeviceBase deviceBase = getKillerManager(getContext()).getDevice();

        //TODO add other specific images
        int helpImageRes = 0;
        switch (mAction) {
            case ACTION_AUTOSTART:
                helpImageRes = deviceBase.getHelpImageAutoStart();
                break;
            case ACTION_POWERSAVING:
                helpImageRes = deviceBase.getHelpImagePowerSaving();
                break;
            case ACTION_NOTIFICATIONS:
                helpImageRes = deviceBase.getHelpImageNotification();
                break;
        }

        if (helpImageRes != 0) {
            mHelpImageView.setImageResource(helpImageRes);
        }
        return mRootView;
    }

}
