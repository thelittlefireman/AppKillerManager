package com.thelittlefireman.appkillermanager.deviceUi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.ui.DialogKillerManagerBuilder;
import com.thelittlefireman.appkillermanager.utils.KillerManagerAction;
import com.thelittlefireman.appkillermanager.utils.KillerManagerUtils;

public class DeviceBaseUi extends Fragment implements DeviceUi {

    protected View mRootView;

    protected KillerManagerAction mAction;

    protected DialogKillerManagerBuilder mDialogKillerManagerBuilder;

    protected CheckBox mDoNotShowAgainCheckBox;

    protected TextView mContentMessage;

    protected Button mButtonOpenSettings;
    protected Button mButtonClose;

    protected LayoutInflater mInfalter;
    protected KillerManager getKillerManager(Context context) {
        return KillerManager.getInstance(context);
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void onClicOpenSettings() {
        KillerManager.getInstance(getContext()).doAction(getActivity(),mAction);
    }

    @Override
    public void onClicClose() {
        //TODO
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInfalter = inflater;
        mRootView = inflater.inflate(R.layout.md_dialog_simple_ui, container, false);
        mButtonOpenSettings = mRootView.findViewById(R.id.md_button_open_settings);
        mButtonClose = mRootView.findViewById(R.id.md_button_close);

        mButtonOpenSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicOpenSettings();
            }
        });
        mButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicClose();
            }
        });
        // ----  Common UI ----
        if (mDialogKillerManagerBuilder.isEnableDontShowAgain()) {
            mDoNotShowAgainCheckBox.setVisibility(View.VISIBLE);
            mDoNotShowAgainCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    KillerManagerUtils.setDontShowAgain(getActivity(), mAction, isChecked);
                }
            });
        } else {
            mDoNotShowAgainCheckBox.setVisibility(View.GONE);
        }
        //TODO CUSTOM MESSAGE FOR SPECIFITQUE ACTIONS AND SPECIFIC DEVICE
        mContentMessage.setText(String.format(getString(R.string.dialog_huawei_notification),
                getString(R.string.app_name)));

        return mRootView;
    }

}
