package com.thelittlefireman.appkillermanager.models;

public enum KillerManagerActionType {
    ACTION_AUTOSTART("ACTION_AUTOSTART"),
    ACTION_NOTIFICATIONS("ACTION_NOTIFICATIONS"),
    ACTION_POWERSAVING("ACTION_POWERSAVING"),
    ACTION_EMPTY("ACTION_EMPTY");

    private String mValue;

    KillerManagerActionType(String value) {
        this.mValue = value;
    }

    public String toString() {
        return this.mValue;
    }
}