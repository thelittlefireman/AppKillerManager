package com.thelittlefireman.appkillermanager.models;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class KillerManagerAction {

    //TODO CHANGE
    public static List<KillerManagerAction> generateKillerManagerActionList(List<Intent> intentList) {
        List<KillerManagerAction> killerManagerActionList = new ArrayList<>();
        for (Intent intent : intentList) {
            killerManagerActionList.add(new KillerManagerAction(intent));
        }
        return killerManagerActionList;
    }

    private String mHelpText;

    private boolean mEnableDontShowAgain;

    public boolean isEnableDontShowAgain() {
        return mEnableDontShowAgain;
    }

    @DrawableRes
    private List<Integer> mHelpImages;

    @NonNull
    private List<Intent> mIntentActionList;

    protected KillerManagerActionType mActionType;

    public KillerManagerAction(@NonNull List<Intent> intentActionList) {
        this("", 0, intentActionList);
    }

    public KillerManagerAction(String helpText, @NonNull List<Intent> intentActionList) {
        this(helpText, 0, intentActionList);
    }

    public KillerManagerAction(int helpImages, @NonNull List<Intent> intentActionList) {
        this("", helpImages, intentActionList);
    }

    // TODO CHANGE TO MULTIPLE IMAGE POSSIBILITY
    public KillerManagerAction(String helpText, List<Integer> helpImageList, @NonNull List<Intent> intentActionList) {
        mHelpText = helpText;
        mHelpImages = helpImageList;
        mIntentActionList = intentActionList;
    }

    public String getHelpText() {
        return mHelpText;
    }

    public List<Integer> getHelpImages() {
        return mHelpImages;
    }

    @NonNull
    public List<Integer> getIntentAction() {
        return mIntentActionList;
    }
}
