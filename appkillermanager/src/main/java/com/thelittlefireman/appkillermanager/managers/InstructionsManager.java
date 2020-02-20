package com.thelittlefireman.appkillermanager.managers;

import androidx.annotation.NonNull;

import com.thelittlefireman.appkillermanager.utils.Instructions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class InstructionsManager {
    private static String TAG = "InstructionsManager";
    OnStart onStart;
    OnSuccess onSuccess;
    OnFailed onFailed;

    // By default if the first call failed by 404 code, it will make another general url
    boolean onNotFoundGetGeneralInstructions = true;
    // Is the general url called?
    boolean generalInstructionsCalled        = false;

    public interface OnStart {
        void onStart();
    }

    public interface OnSuccess {
        public void onSuccess(@NonNull ResponseWrapper responseWrapper);
        public void onSuccess(@NonNull Instructions instructions);
    }

    public interface OnFailed {
        void onFailed( @Nullable CallWrapper callWrapper, @Nullable ResponseWrapper responseWrapper, @Nullable IOException e);
    }

    public InstructionsManager(OnSuccess onSuccess, OnFailed onFailed) {
        this.onSuccess = onSuccess;
        this.onFailed = onFailed;
    }

    public InstructionsManager() {
    }


    public InstructionsManager setOnStart(OnStart onStart) {
        this.onStart = onStart;
        return this;
    }

    public InstructionsManager setOnSuccess(OnSuccess onSuccess) {
        this.onSuccess = onSuccess;
        return this;
    }

    public InstructionsManager setOnFailed(OnFailed onFailed) {
        this.onFailed = onFailed;
        return this;
    }

    public boolean isOnNotFoundGetGeneralInstructions() {
        return onNotFoundGetGeneralInstructions;
    }

    public InstructionsManager setOnNotFoundGetGeneralInstructions(boolean onNotFoundGetGeneralInstructions) {
        this.onNotFoundGetGeneralInstructions = onNotFoundGetGeneralInstructions;
        return this;
    }


    public void getManufacturerInstructions() {

        String manufacturerName = null;
        if (DevicesManager.getDevice() != null) {
            manufacturerName = DevicesManager.getDevice().getDeviceManufacturer().name();
        }
        getManufacturerInstructions(manufacturerName);
    }

    public String getDKMAUrl() {
        String url = "https://dontkillmyapp.com/";
        String manufacturerName = null;
        if (DevicesManager.getDevice() != null) {
            manufacturerName = DevicesManager.getDevice().getDeviceManufacturer().name();
        }

        String[] array = new String[]{
                "huawei",
                "samsung",
                "oneplus",
                "xiaomi",
                "meizu",
                "asus",
                "wiko",
                "lenovo",
                "oppo",
                "nokia",
                "sony",
                "google",
                "htc"
        };

        ArrayList<String> manufacturerList = new ArrayList<String>(Arrays.asList(array));
        if (manufacturerName != null && manufacturerList.contains(manufacturerName.toLowerCase()) ){
            url = url + manufacturerName.toLowerCase();
        }
        return url;
    }

    // Don't use this directly, It is written For testing multiple manufacturers in the same device,
    public void getManufacturerInstructions(String manufacturerName){
        OkHttpClient client = new OkHttpClient();
        String url = buildUrl(manufacturerName);

        final Request request = new Request.Builder()
                .url(url)
                .build();

        final InstructionsManager self = this;
        client
                .newCall(request)

                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        onFailedCall(onFailed, call, null, e);
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            if (onSuccess != null) {
                                //self.storedResponse = response;
                                // response object is one time read object
                                // You can't read it more than one time
                                // so, passing Instructions object is more safe
                                Instructions instructions = new Instructions(new ResponseWrapper(response));

                                // use this if you want to manage the response by your self
                                //onSuccess.onSuccess(new ResponseWrapper(response));
                                onSuccess.onSuccess(instructions);
                            }
                        } else {
                             onFailedCall(onFailed, call, response, null);
                        }
                    }
                }) ;
    }

    private void onFailedCall(OnFailed onFailed, Call call, Response response, IOException e) {
        if (
            onNotFoundGetGeneralInstructions &&
            ! generalInstructionsCalled &&
            response != null &&
            response.code() == 404
        ){
            getManufacturerInstructions("general");
            generalInstructionsCalled = true;
        }else {
            if (onFailed != null) {
               onFailed.onFailed(new CallWrapper(call), new ResponseWrapper(response), null);
            }
        }
    }

    private String buildUrl(String manufacturer) {
        return "https://dontkillmyapp.com/api/v2/MANUFACTURER.json".replace("MANUFACTURER", manufacturer.toLowerCase());
    }
}
