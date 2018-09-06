package com.thelittlefireman.appkillermanager.models;

import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class KillerManagerAction {

    public static List<KillerManagerAction> generateKillerManagerActionList(List<Intent> intentList) {
        List<KillerManagerAction> killerManagerActionList = new ArrayList<>();
        for (Intent intent : intentList) {
            killerManagerActionList.add(new KillerManagerAction(intent));
        }
        return killerManagerActionList;
    }

    private String mHelpText;

    @DrawableRes
    private List<Integer> mHelpImage;

    @NonNull
    private Intent mIntentAction;

    public KillerManagerAction(@NonNull Intent intentAction) {
        this("", 0, intentAction);
    }

    public KillerManagerAction(String helpText, @NonNull Intent intentAction) {
        this(helpText, 0, intentAction);
    }

    public KillerManagerAction(int helpImage, @NonNull Intent intentAction) {
        this("", helpImage, intentAction);
    }

    public KillerManagerAction(String helpText, int helpImage, @NonNull Intent intentAction) {
        mHelpText = helpText;
        mHelpImage = new ArrayList<>(helpImage);
        mIntentAction = intentAction;
    }

    public String getHelpText() {
        return mHelpText;
    }

    public List<Integer> getHelpImage() {
        return mHelpImage;
    }

    @NonNull
    public Intent getIntentAction() {
        return mIntentAction;
    }
}
