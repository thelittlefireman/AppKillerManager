package com.thelittlefireman.appkillermanager.ui;

import android.app.Activity;
import androidx.annotation.NonNull;

import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.thelittlefireman.appkillermanager.managers.CallWrapper;
import com.thelittlefireman.appkillermanager.managers.InstructionsManager;
import com.thelittlefireman.appkillermanager.managers.ResponseWrapper;
import com.thelittlefireman.appkillermanager.utils.Encoding;
import com.thelittlefireman.appkillermanager.utils.Instructions;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class InstructionsViewer {
    private String TAG = "InstructionsViewer";


    public  interface OnSuccessCompletion {
        public void CompleteBy(ResponseWrapper responseWrapper);
        public void CompleteBy(Instructions instructions);

    }

    public interface OnFailedCompletion {
        public void CompleteBy(CallWrapper callWrapper,
                               ResponseWrapper responseWrapper,
                               Exception e);
    }

    Activity activity;
    ViewGroup viewGroup;
    WebView webView;
    int position = 0;
    String mime_type = "text/html";
    String encoding = Encoding.UTF_8.toString() ;//"UTF-8";

    OnSuccessCompletion onSuccessCompletion;
    OnFailedCompletion onFailedCompletion;

    public InstructionsViewer(Activity activity, ViewGroup viewGroup) {
        this.activity = activity;
        this.viewGroup = viewGroup;
    }

    public InstructionsViewer(ViewGroup viewGroup, OnSuccessCompletion onSuccessCompletion,
                              OnFailedCompletion onFailedCompletion) {
        this.viewGroup = viewGroup;
        this.onSuccessCompletion = onSuccessCompletion;
        this.onFailedCompletion = onFailedCompletion;
    }

    public InstructionsViewer(Activity activity, WebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    public InstructionsViewer(Activity activity, WebView webView,
                              OnSuccessCompletion onSuccessCompletion,
                              OnFailedCompletion onFailedCompletion) {
        this.activity = activity;
        this.webView = webView;
        this.onSuccessCompletion = onSuccessCompletion;
        this.onFailedCompletion = onFailedCompletion;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    public void setWebView(WebView webView) {
        this.webView = webView;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setOnSuccessCompletion(OnSuccessCompletion onSuccessCompletion) {
        this.onSuccessCompletion = onSuccessCompletion;
    }

    public void setOnFailedCompletion(OnFailedCompletion onFailedCompletion) {
        this.onFailedCompletion = onFailedCompletion;
    }

    private String formHeader() {
        return "<?xml version=\"1.0\" encoding=\"" + encoding + "\" ?>";
    }

    public void show() {
        new InstructionsManager()
                .setOnSuccess(setOnSuccess())
                .setOnFailed(setOnFailure())
                .getManufacturerInstructions();
    }

    // Don't use this directly, It is written For testing multiple manufacturers in the same device,
    public void show(String manufactureName) {
        new InstructionsManager()
                .setOnSuccess(setOnSuccess())
                .setOnFailed(setOnFailure())
                .getManufacturerInstructions(manufactureName);
    }

    private InstructionsManager.OnFailed setOnFailure() {
        return new InstructionsManager.OnFailed() {
            @Override
            public void onFailed(@Nullable CallWrapper callWrapper,
                                 @Nullable ResponseWrapper responseWrapper,
                                 @Nullable IOException e) {
                if (onFailedCompletion != null) {
                    onFailedCompletion.CompleteBy(
                            new CallWrapper(null),
                            responseWrapper,
                            e
                    );
                }
            }
        };
    }

    private InstructionsManager.OnSuccess setOnSuccess() {
        return new InstructionsManager.OnSuccess() {
            @Override
            public void onSuccess(@NonNull final ResponseWrapper responseWrapper) {
            }

            @Override
            public void onSuccess(@NonNull final Instructions instructions) {
                activity.runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                if (webView == null){
                                    webView = new WebView(activity);
                                }
                                if (viewGroup != null){
                                    viewGroup.addView(webView, position);
                                }

                                WebSettings websetting = webView.getSettings();
                                websetting.setDefaultTextEncodingName(encoding);

                                try {
                                    String original = instructions.getUser_solution();
                                    webView.loadData(
                                            original,
                                            "text/html",
                                            encoding);
                                    if (onSuccessCompletion != null) {
                                        onSuccessCompletion.CompleteBy(instructions);
                                    }
                                } catch (Exception e) {
                                    if (onFailedCompletion != null) {
                                        onFailedCompletion.CompleteBy(
                                                new CallWrapper(null),
                                                new ResponseWrapper(null),
                                                e
                                        );
                                    }
                                }

                            }
                        }
                );
            }
        };
    }
};
