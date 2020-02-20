package com.thelittlefireman.appkillermanager_exemple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatCheckBox;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.ui.DialogKillerManagerBuilder;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class MainActivity extends Activity {
    TextView deviceName;
    TextView isDeviceSupported;
    Button powerSavingManagerButton;
    Button autoStartManagerButton;
    Button notificationManagerButton;
    AppCompatCheckBox mAppCompatCheckBoxByDialog;
    Button goToInstructonsActivityButton;

    KillerManager.Actions currentAction = null;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deviceName = (TextView) findViewById(R.id.device_name);
        isDeviceSupported = (TextView) findViewById(R.id.is_device_supported);
        powerSavingManagerButton = findViewById(R.id.powerSavingManagerButton);
        autoStartManagerButton = findViewById(R.id.autoStartManagerButton);
        notificationManagerButton = findViewById(R.id.notificationManagerButton);
        mAppCompatCheckBoxByDialog = (AppCompatCheckBox) findViewById(R.id.idByDialog);
        goToInstructonsActivityButton = findViewById(R.id.goToInstructonsActivityButton);


        //ButterKnife.bind(this);
        powerSavingManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAction = KillerManager.Actions.ACTION_POWERSAVING;
                if (mAppCompatCheckBoxByDialog.isChecked()) {
                    startDialog(currentAction);
                } else {
                    try {
                        KillerManager.doActionPowerSaving(MainActivity.this);
                    } catch (KillerManager.NoActionFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        autoStartManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAction = KillerManager.Actions.ACTION_AUTOSTART;
                if (mAppCompatCheckBoxByDialog.isChecked()) {
                    startDialog(currentAction);
                } else {
                    try {
                        KillerManager.doActionAutoStart(MainActivity.this);
                    } catch (KillerManager.NoActionFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        notificationManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAction = KillerManager.Actions.ACTION_NOTIFICATIONS;
                if (mAppCompatCheckBoxByDialog.isChecked()) {
                    startDialog(currentAction);
                } else {
                    try {
                        KillerManager.doActionNotification(MainActivity.this);
                    } catch (KillerManager.NoActionFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        final MainActivity self = this;
        goToInstructonsActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, InstructionsActivity.class);
                self.startActivity(intent);
            }
        });

        deviceName.setText( "Device name: " .concat(KillerManager.getDevice().getDeviceManufacturer().toString()) );
        isDeviceSupported.setText( "Is supported: ".concat(String.valueOf( KillerManager.isDeviceSupported()) ) );

    }

    public void startDialog(KillerManager.Actions actions) {

        try {
            new DialogKillerManagerBuilder(this).setAction(actions).show();

        } catch (DialogKillerManagerBuilder.UnAvailableActionException e) {
            e.printStackTrace();
        } catch (DialogKillerManagerBuilder.UnSupportedDeviceException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {

        super.onResume();
        if (currentAction == KillerManager.Actions.ACTION_AUTOSTART){
            // may show dalog to ask the user about the result of action
            // and store the result in preference
        }else if (currentAction == KillerManager.Actions.ACTION_NOTIFICATIONS){

        }else if (currentAction == KillerManager.Actions.ACTION_POWERSAVING){

        }
        // dont forget to nullify current action
        currentAction = null;
    }


}
