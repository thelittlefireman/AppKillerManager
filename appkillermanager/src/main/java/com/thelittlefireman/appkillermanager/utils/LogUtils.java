package com.thelittlefireman.appkillermanager.utils;

import android.util.Log;

public class LogUtils {

    // Bydefault, No log . to be ready for play store publish
    // for testing , change this value.
    private static boolean log = false;
    public static void i(String tag, String message) {
        if (log) {
            Log.i(tag, message);
            //HyperLog.i(tag,message);
        }
    }
    public static void e(String tag, String message) {
        if (log) {
            Log.e(tag, message);
            //HyperLog.e(tag,message);
        }
    }
}
