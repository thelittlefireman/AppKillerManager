package com.thelittlefireman.appkillermanager.managers;

import okhttp3.Call;

public class CallWrapper{
    private  okhttp3.Call call;

    public CallWrapper(Call call) {
        this.call = call;
    }

    public Call getCall() {
        return call;
    }

    public boolean isExecuted(){
        return call.isExecuted();
    }

    public boolean isCanceled(){
        return call.isCanceled();
    }
}
