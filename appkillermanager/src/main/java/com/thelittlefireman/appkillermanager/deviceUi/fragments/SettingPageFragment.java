package com.thelittlefireman.appkillermanager.deviceUi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.thelittlefireman.appkillermanager.R;
import com.thelittlefireman.appkillermanager.managers.KillerManager;
import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.utils.KillerManagerUtils;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SettingPageFragment extends Fragment  implements SettingPageFragmentClicListener {
    //https://guides.codepath.com/android/viewpager-with-fragmentpageradapter

    private ViewPager mHelpImageViewPager;

    private int mCurrentPage;

    private static final String KILLER_MANAGER_ACTION ="KILLER_MANAGER_ACTION";
    private static final String KILLER_MANAGER_DONT_SHOW_AGAIN = "KILLER_MANAGER_DONT_SHOW_AGAIN";

    private String mContentMessage;

    private KillerManager mKillerManager;

    private KillerManagerAction mKillerManagerAction;

    private boolean mDontShowAgain;

    protected LayoutInflater mInfalter;
    protected CheckBox mDoNotShowAgainCheckBox;

    protected Button mButtonOpenSettings;
    protected Button mButtonClose;

    public static SettingPageFragment newInstance(KillerManagerAction killerManagerAction, boolean dontShowAgain) {
        SettingPageFragment fragmentFirst = new SettingPageFragment();
        Bundle args = new Bundle();
        args.putString(KILLER_MANAGER_ACTION, KillerManagerAction.toJson(killerManagerAction));
        args.putBoolean(KILLER_MANAGER_DONT_SHOW_AGAIN, dontShowAgain);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKillerManager = KillerManager.getInstance(getContext());
        mKillerManagerAction = KillerManagerAction.fromJson(getArguments().getString(KILLER_MANAGER_ACTION));
        mDontShowAgain = getArguments().getBoolean(KILLER_MANAGER_DONT_SHOW_AGAIN);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInfalter = inflater;
        View mFragmentView = inflater.inflate(R.layout.md_dialog_main_content, container, false);
        TextView textView = mFragmentView.findViewById(R.id.md_content_message);
        mDoNotShowAgainCheckBox = mFragmentView.findViewById(R.id.md_promptCheckbox);
        mButtonOpenSettings = mFragmentView.findViewById(R.id.md_button_open_settings);
        mButtonClose = mFragmentView.findViewById(R.id.md_button_close);
        mHelpImageViewPager = mFragmentView.findViewById(R.id.md_help_image_viewpager);

        textView.setText(mContentMessage);
        mButtonOpenSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicOpenSettings();
            }
        });
        mButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicClose();
            }
        });
        // ----  Common UI ----
        if (mDontShowAgain) {
            mDoNotShowAgainCheckBox.setVisibility(View.VISIBLE);
            mDoNotShowAgainCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    KillerManagerUtils.setDontShowAgain(getActivity(), isChecked);
                }
            });
        } else {
            mDoNotShowAgainCheckBox.setVisibility(View.GONE);
        }
       /* //TODO CUSTOM MESSAGE FOR SPECIFITQUE ACTIONS AND SPECIFIC DEVICE
        mContentMessage.setText(String.format(getString(R.string.dialog_huawei_notification),
                                              getString(R.string.app_name)));*/

        // Inflate the layout for this fragment
        mHelpImageViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mKillerManagerAction.getHelpImages().size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup view, int position) {
                View myImageLayout = mInfalter.inflate(R.layout.md_dialog_main_content_image_item, view, false);
                ImageView myImage = myImageLayout.findViewById(R.id.md_help_image);
                myImage.setImageResource(mKillerManagerAction.getHelpImages().get(position));
                view.addView(myImageLayout, 0);
                return myImageLayout;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view.equals(object);
            }
        });

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (mCurrentPage == mKillerManagerAction.getHelpImages().size()) {
                    mCurrentPage = 0;
                }
                mHelpImageViewPager.setCurrentItem(mCurrentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);

        return mFragmentView;
    }

    @Override
    public void onClicOpenSettings() {
        mKillerManager.doAction(getActivity(), mKillerManagerAction.getActionType());
    }

    @Override
    public void onClicClose() {
        //TODO
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        mKillerManager.onActivityResult(getActivity(), mKillerManagerAction.getActionType(), requestCode);
    }
}