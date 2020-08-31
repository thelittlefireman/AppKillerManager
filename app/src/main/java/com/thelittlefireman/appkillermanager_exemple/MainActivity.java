package com.thelittlefireman.appkillermanager_exemple;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.ui.DialogKillerManagerBuilder;

public class MainActivity extends Activity {

    Button powerSavingManagerButton;
    Button autoStartManagerButton;
    Button notificationManagerButton;

    AppCompatCheckBox mAppCompatCheckBoxByDialog;

    public void bind(){
        powerSavingManagerButton = findViewById(R.id.powerSavingManagerButton);
        autoStartManagerButton = findViewById(R.id.autoStartManagerButton);
        notificationManagerButton = findViewById(R.id.notificationManagerButton);
        mAppCompatCheckBoxByDialog= findViewById(R.id.idByDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KillerManager.init(this);
        setContentView(R.layout.activity_main);
        bind();
        powerSavingManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAppCompatCheckBoxByDialog.isChecked()) {
                    startDialog(KillerManager.Actions.ACTION_POWERSAVING);
                } else {
                    KillerManager.doActionPowerSaving(MainActivity.this);
                }
            }
        });
        autoStartManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAppCompatCheckBoxByDialog.isChecked()) {
                    startDialog(KillerManager.Actions.ACTION_AUTOSTART);
                } else {
                    KillerManager.doActionAutoStart(MainActivity.this);
                }
            }
        });
        notificationManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAppCompatCheckBoxByDialog.isChecked()) {
                    startDialog(KillerManager.Actions.ACTION_NOTIFICATIONS);
                } else {
                    KillerManager.doActionNotification(MainActivity.this);
                }
            }
        });
    }

    public void startDialog(KillerManager.Actions actions) {

        new DialogKillerManagerBuilder().setContext(this).setAction(actions).show();

    }
}
