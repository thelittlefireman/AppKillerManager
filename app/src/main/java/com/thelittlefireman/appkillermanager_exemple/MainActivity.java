package com.thelittlefireman.appkillermanager_exemple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;

import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.ui.DialogKillerManagerBuilder;
import com.thelittlefireman.appkillermanager.utils.LogUtils;

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

    private KillerManager.Actions mActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KillerManager.init(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        KillerManager.init(this);
        LogUtils.registerLogCustomListener(new LogUtils.LogCustomListener() {
            @Override
            public void i(String tag, String message) {
                // Custom Log
            }

            @Override
            public void e(String tag, String message) {
                // Custom Log
            }
        });
        powerSavingManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAppCompatCheckBoxByDialog.isChecked()) {
                    mActions = KillerManager.Actions.ACTION_POWERSAVING;
                    startDialog(mActions);
                } else {
                    KillerManager.doAction(MainActivity.this, mActions);
                }
            }
        });
        autoStartManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAppCompatCheckBoxByDialog.isChecked()) {
                    mActions = KillerManager.Actions.ACTION_AUTOSTART;
                    startDialog(mActions);
                } else {
                    KillerManager.doAction(MainActivity.this, mActions);
                }
            }
        });
        notificationManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAppCompatCheckBoxByDialog.isChecked()) {
                    mActions = KillerManager.Actions.ACTION_NOTIFICATIONS;
                    startDialog(mActions);
                } else {
                    KillerManager.doAction(MainActivity.this, mActions);
                }
            }
        });
    }

    public void startDialog(KillerManager.Actions actions) {

        new DialogKillerManagerBuilder().setContext(this).setAction(actions).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        KillerManager.onActivityResult(MainActivity.this, mActions);
    }
}
