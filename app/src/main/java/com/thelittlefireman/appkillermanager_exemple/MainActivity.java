package com.thelittlefireman.appkillermanager_exemple;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;

import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.ui.DialogKillerManagerBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    @BindView(R.id.powerSavingManagerButton)
    Button powerSavingManagerButton;
    @BindView(R.id.autoStartManagerButton)
    Button autoStartManagerButton;
    @BindView(R.id.notificationManagerButton)
    Button notificationManagerButton;

    @BindView(R.id.idByDialog)
    AppCompatCheckBox mAppCompatCheckBoxByDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KillerManager.init(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
