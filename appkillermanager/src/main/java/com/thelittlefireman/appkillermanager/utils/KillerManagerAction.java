package com.thelittlefireman.appkillermanager.utils;

public enum KillerManagerAction {
    ACTION_AUTOSTART("ACTION_AUTOSTART"),
    ACTION_NOTIFICATIONS("ACTION_NOTIFICATIONS"),
    ACTION_POWERSAVING("ACTION_POWERSAVING");

    private String mValue;

    KillerManagerAction(String value) {
        this.mValue = value;
    }

    public String toString() {
        return this.mValue;
    }
}