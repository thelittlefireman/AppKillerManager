package com.thelittlefireman.appkillermanager.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.thelittlefireman.appkillermanager.managers.KillerManager;

import static android.content.Context.MODE_PRIVATE;

public class KillerManagerUtils {
    private static final String DONT_SHOW_AGAIN = "DONT_SHOW_AGAIN";
    private static final String IS_DONE         = "IS_DONE_";

    private static SharedPreferences getSharedPreferences(Context mContext){
        return mContext.getSharedPreferences("KillerManager", MODE_PRIVATE);
    }

    /**
     * Set for a specifique actions that we dont need to show the popupAgain
     *
     * @param mContext
     * @param action
     * @param enable
     */
    public static void setDontShowAgain(Context mContext, KillerManager.Actions action, boolean enable){
        final SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        editor.putBoolean(DONT_SHOW_AGAIN+action.toString(),enable);
        editor.apply();
    }


    public static void setAllDontShowAgain(Context mContext, boolean enable){
        final SharedPreferences.Editor editor = getSharedPreferences(mContext).edit();
        for ( KillerManager.Actions action : KillerManager.Actions.values()){
            editor.putBoolean(DONT_SHOW_AGAIN+action.toString(),enable);
        }
        editor.apply();
    }

    public static boolean isDontShowAgain(Context mContext, KillerManager.Actions action){
        return getSharedPreferences(mContext).getBoolean(DONT_SHOW_AGAIN+action.toString(),false);
    }

    public static boolean isAllDontShowAgain(Context context){
        for ( KillerManager.Actions action : KillerManager.Actions.values()){
            if ( ! isDontShowAgain(context, action)){
                return false;
            }
        }
        return true;
    }

    public static boolean isAnyDontShowAgain(Context context){
        for ( KillerManager.Actions action : KillerManager.Actions.values()){
            if ( isDontShowAgain(context, action)){
                return true;
            }
        }
        return false;
    }

    public static void updateIsActionDone( Context context, KillerManager.Actions action, boolean b) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(IS_DONE+action.toString(), b);
        editor.apply();
    }


    public static boolean isActionDone( Context context, KillerManager.Actions action) {
        final SharedPreferences prfs = getSharedPreferences(context);
        return prfs.getBoolean(IS_DONE+action.toString(), false);
    }



}
