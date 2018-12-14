package com.thelittlefireman.appkillermanager.models;

import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class KillerManagerAction {

    private String mHelpText;

    @DrawableRes
    private List<Integer> mHelpImages;

    @NonNull
    private List<Intent> mIntentActionList;

    protected KillerManagerActionType mActionType;

    public KillerManagerAction() {
        this(KillerManagerActionType.ACTION_EMPTY, "", new ArrayList<Integer>(), new ArrayList<Intent>());
    }

    @Deprecated
    public KillerManagerAction(@NonNull KillerManagerActionType killerManagerActionType, @NonNull Intent intentAction) {
        this(killerManagerActionType, "", Collections.<Integer>emptyList(), Collections.singletonList(intentAction));
    }

    @Deprecated
    public KillerManagerAction(@NonNull KillerManagerActionType killerManagerActionType, @NonNull List<Intent>
            intentActionList) {
        this(killerManagerActionType, "", Collections.<Integer>emptyList(), intentActionList);
    }

    @Deprecated
    public KillerManagerAction(@NonNull KillerManagerActionType killerManagerActionType, String helpText, @NonNull
            List<Intent> intentActionList) {
        this(killerManagerActionType, helpText, Collections.<Integer>emptyList(), intentActionList);
    }

    @Deprecated
    public KillerManagerAction(@NonNull KillerManagerActionType killerManagerActionType, @DrawableRes int helpImages,
                               @NonNull
            Intent intentAction) {
        this(killerManagerActionType, "", Collections.singletonList(helpImages),
             Collections.singletonList(intentAction));
    }

    @Deprecated
    public KillerManagerAction(@NonNull KillerManagerActionType killerManagerActionType, @DrawableRes int helpImages,
                               @NonNull
            List<Intent> intentActionList) {
        this(killerManagerActionType, "", Collections.singletonList(helpImages), intentActionList);
    }

    // TODO CHANGE TO MULTIPLE IMAGE POSSIBILITY
    public KillerManagerAction(@NonNull KillerManagerActionType killerManagerActionType, String helpText, List<Integer>
            helpImageList, @NonNull
                                       List<Intent>
                                       intentActionList) {
        mHelpText = helpText;
        mHelpImages = helpImageList;
        mIntentActionList = intentActionList;
        mActionType = killerManagerActionType;
    }

    public static String toJson(KillerManagerAction killerManagerAction) {
        return new Gson().toJson(killerManagerAction);
    }

    public static KillerManagerAction fromJson(String json) {
        return new Gson().fromJson(json, KillerManagerAction.class);
    }

    public String getHelpText() {
        return mHelpText;
    }

    public List<Integer> getHelpImages() {
        return mHelpImages;
    }

    public static String toJsonList(List<KillerManagerAction> killerManagerActionList) {
        return new Gson().toJson(killerManagerActionList);
    }

    public static List<KillerManagerAction> fromJsonList(String json) {
        return new Gson().fromJson(json, new TypeToken<List<KillerManagerAction>>() {
        }.getType());
    }

    public KillerManagerActionType getActionType() {
        return mActionType;
    }

    public void setActionType(KillerManagerActionType actionType) {
        mActionType = actionType;
    }

    @NonNull
    public List<Intent> getIntentActionList() {
        return mIntentActionList;
    }
}
