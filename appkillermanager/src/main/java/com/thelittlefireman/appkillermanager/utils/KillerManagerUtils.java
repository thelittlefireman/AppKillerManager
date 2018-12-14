package com.thelittlefireman.appkillermanager.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class KillerManagerUtils {
    private static final String DONT_SHOW_AGAIN = "DONT_SHOW_AGAIN";
    private static SharedPreferences getSharedPreferences(Context mContext){
        return mContext.getSharedPreferences("KillerManager", MODE_PRIVATE);
    }

    /**
     * Set for a specifique actions that we dont need to show the popupAgain
     *
     * @param mContext
     * @param enable
     */
    public static void setDontShowAgain(Context mContext, boolean enable) {
        final SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.putBoolean(DONT_SHOW_AGAIN, enable);
        editor.apply();
    }

    public static boolean isDontShowAgain(Context mContext) {
        return getSharedPreferences(mContext).getBoolean(DONT_SHOW_AGAIN, false);
    }
}
