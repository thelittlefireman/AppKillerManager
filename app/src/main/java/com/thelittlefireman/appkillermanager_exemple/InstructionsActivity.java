package com.thelittlefireman.appkillermanager_exemple;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.Guideline;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.thelittlefireman.appkillermanager.managers.CallWrapper;
import com.thelittlefireman.appkillermanager.managers.InstructionsManager;
import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.managers.ResponseWrapper;
import com.thelittlefireman.appkillermanager.ui.DialogKillerManagerBuilder;
import com.thelittlefireman.appkillermanager.ui.InstructionsViewer;
import com.thelittlefireman.appkillermanager.utils.Instructions;
import com.thelittlefireman.appkillermanager.utils.LogUtils;

import java.io.IOException;


public class InstructionsActivity extends AppCompatActivity {
    private static String TAG = "InstructionsActivity";

    Button instructionsManagerButton;
    Button viewInstructionsButton;
    LinearLayout vieweLayout;
    EditText manufactureName;


    private Guideline guideline2;
    private LinearLayout viewerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        instructionsManagerButton = findViewById(R.id.instructionsManagerButton);
        viewInstructionsButton = findViewById(R.id.viewInstructionsButton);
        guideline2 = (Guideline) findViewById(R.id.guideline2);
        viewerLayout = (LinearLayout) findViewById(R.id.viewerLayout);
        manufactureName = findViewById(R.id.manufactureName);
        instructionsManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InstructionsManager()
                        .setOnStart(new InstructionsManager.OnStart() {
                            @Override
                            public void onStart() {
                                LogUtils.i(TAG, "Start getting manufacturer instructions");
                            }
                        })
                        .setOnSuccess(new InstructionsManager.OnSuccess() {
                            @Override
                            public void onSuccess(@NonNull ResponseWrapper responseWrapper) {
                                Instructions instructions = new Instructions(responseWrapper);
                                if ( ! instructions.isHasException()){
                                    LogUtils.i(TAG,instructions.getName() + instructions.getUser_solution());
                                    //Do your stuff here
                                }
                            }

                            @Override
                            public void onSuccess(@NonNull Instructions instructions) {
                                if ( ! instructions.isHasException()){
                                    LogUtils.i(TAG,instructions.getName() + instructions.getUser_solution());
                                    //Do your stuff here
                                }
                            }
                        })
                        .setOnFailed(new InstructionsManager.OnFailed() {
                            @Override
                            public void onFailed(CallWrapper callWrapper,
                                                 ResponseWrapper responseWrapper,
                                                 IOException e) {
                                LogUtils.i(TAG, "Is Call cancelled?" + callWrapper.isCanceled());
                                LogUtils.i(TAG, "Is Call Executed?" + callWrapper.isExecuted());
                                LogUtils.i(TAG, "Response message" + responseWrapper.getMessage());
                                LogUtils.i(TAG, "Is successfull ?" + responseWrapper.isSuccessful());
                                LogUtils.i(TAG, "Is redirect ?" + responseWrapper.isRedirect());
                            }
                        })
                        .getManufacturerInstructions();
            }
        });

        final InstructionsActivity self = this;
        viewInstructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manufactureName.getText().toString().equals("")){
                    new InstructionsViewer(self, vieweLayout).show();
                }else{
                    new InstructionsViewer(self, vieweLayout).show(manufactureName.getText().toString());
                }



            }
        });

        Button openInBrowserBtn = (Button) findViewById(R.id.open_in_browser_btn);
        openInBrowserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(new InstructionsManager().getDKMAUrl()));
                startActivity(browserIntent);
            }
        });



    }

    public void startDialog(KillerManager.Actions actions) {

        try {
            new DialogKillerManagerBuilder(this)
                    .setAction(actions).show();
        } catch (DialogKillerManagerBuilder.UnAvailableActionException e) {
            e.printStackTrace();
        } catch (DialogKillerManagerBuilder.UnSupportedDeviceException e) {
            e.printStackTrace();
        }

    }
}
