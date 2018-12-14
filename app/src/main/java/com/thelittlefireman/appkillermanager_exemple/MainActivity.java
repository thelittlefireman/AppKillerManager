package com.thelittlefireman.appkillermanager_exemple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.models.KillerManagerActionType;
import com.thelittlefireman.appkillermanager.ui.DialogKillerManagerBuilder;
import com.thelittlefireman.appkillermanager.utils.LogUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.powerSavingManagerButton)
    Button powerSavingManagerButton;
    @BindView(R.id.autoStartManagerButton)
    Button autoStartManagerButton;
    @BindView(R.id.notificationManagerButton)
    Button notificationManagerButton;

    @BindView(R.id.idByDialog)
    AppCompatCheckBox mAppCompatCheckBoxByDialog;

    private KillerManagerActionType mAction;

    private KillerManager mKillerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKillerManager = KillerManager.getInstance(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
                    mAction = KillerManagerActionType.ACTION_POWERSAVING;
                    startDialog(mAction);
                } else {
                    mKillerManager.doAction(MainActivity.this, mAction);
                }
            }
        });
        autoStartManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAppCompatCheckBoxByDialog.isChecked()) {
                    mAction = KillerManagerActionType.ACTION_AUTOSTART;
                    startDialog(mAction);
                } else {
                    mKillerManager.doAction(MainActivity.this, mAction);
                }
            }
        });
        notificationManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAppCompatCheckBoxByDialog.isChecked()) {
                    mAction = KillerManagerActionType.ACTION_NOTIFICATIONS;
                    startDialog(mAction);
                } else {
                    mKillerManager.doAction(MainActivity.this, mAction);
                }
            }
        });
    }

    public void startDialog(KillerManagerActionType action) {

        new DialogKillerManagerBuilder().setActivity(this).setKillerManagerActionTypeList(action).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mKillerManager.onActivityResult(MainActivity.this, mAction, requestCode);
    }
}
