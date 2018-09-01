package com.thelittlefireman.appkillermanager.deviceUi;

import android.support.annotation.LayoutRes;
import android.view.View;

public interface DeviceUi {

    @LayoutRes
    int getLayout();
    void onClicOpenSettings();
    void onClicClose();
}
