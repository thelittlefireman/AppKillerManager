package com.thelittlefireman.appkillermanager.utils;

import android.util.Log;

public class LogUtils {

    private static LogCustomListener slogCustomListener;

    public static void registerLogCustomListener(LogCustomListener logCustomListener){
        slogCustomListener = logCustomListener;
    }

    public interface LogCustomListener{
        void i(String tag, String message);
        void e(String tag, String message);
    }

    public static void i(String tag, String message){
        if(slogCustomListener !=null){
            slogCustomListener.i(tag,message);
        }
        Log.i(tag,message);
        //HyperLog.i(tag,message);
    }
    public static void e(String tag, String message){
        if(slogCustomListener !=null){
            slogCustomListener.e(tag,message);
        }
        Log.e(tag,message);
        //HyperLog.e(tag,message);
    }
}
