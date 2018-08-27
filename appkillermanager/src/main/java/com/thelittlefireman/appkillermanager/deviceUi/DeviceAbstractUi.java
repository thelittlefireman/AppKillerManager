package com.thelittlefireman.appkillermanager.deviceUi;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.devices.DeviceBase;
import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.ui.DialogKillerManagerBuilder;
import com.thelittlefireman.appkillermanager.utils.KillerManagerAction;
import com.thelittlefireman.appkillermanager.utils.KillerManagerUtils;

public abstract class DeviceAbstractUi extends Fragment implements DeviceUi {

    protected View mRootView;

    protected DeviceBase mDevice = KillerManager.getDevice();

    protected KillerManagerAction mAction;

    protected DialogKillerManagerBuilder mDialogKillerManagerBuilder;

    private CheckBox mDoNotShowAgainCheckBox;

    protected LayoutInflater mInflater;

    @CallSuper
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        mRootView = inflater.inflate(R.layout.md_dialog_samsung_ui, container, false);

        // ----  Common UI ----
        if (mDialogKillerManagerBuilder.isEnableDontShowAgain()) {
            mDoNotShowAgainCheckBox.setVisibility(View.VISIBLE);
            mDoNotShowAgainCheckBox.setText(R.string.dialog_do_not_show_again);
            mDoNotShowAgainCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    KillerManagerUtils.setDontShowAgain(getActivity(), mAction, isChecked);
                }
            });
        }
        return onCreateView();
    }

}
