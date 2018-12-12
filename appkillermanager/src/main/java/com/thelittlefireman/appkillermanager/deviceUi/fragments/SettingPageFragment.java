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
import com.thelittlefireman.appkillermanager.models.KillerManagerAction;
import com.thelittlefireman.appkillermanager.utils.KillerManagerUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SettingPageFragment extends Fragment  implements SettingPageFragmentClicListener {
    //https://guides.codepath.com/android/viewpager-with-fragmentpageradapter

    List<Integer> mHelpImageList;
    private ViewPager mHelpImageViewPager;

    private int mCurrentPage;

    private static final String KILLER_MANAGER_ACTION ="KILLER_MANAGER_ACTION";

    private String mContentMessage;

    private KillerManagerAction mKillerManagerAction;

    protected LayoutInflater mInfalter;
    protected CheckBox mDoNotShowAgainCheckBox;

    protected Button mButtonOpenSettings;
    protected Button mButtonClose;

    public static SettingPageFragment newInstance(KillerManagerAction killerManagerAction) {
        SettingPageFragment fragmentFirst = new SettingPageFragment();
        Bundle args = new Bundle();
        args.putString(KILLER_MANAGER_ACTION, killerManagerAction.getjSOn);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mKillerManagerAction = getArguments().getString(HELP_TEXT).fromJson;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInfalter = inflater;
        View mFragmentView = inflater.inflate(R.layout.md_dialog_main_content, container, false);
        TextView textView = mFragmentView.findViewById(R.id.md_content_message);

        textView.setText(mContentMessage);
        mButtonOpenSettings = mFragmentView.findViewById(R.id.md_button_open_settings);
        mButtonClose = mFragmentView.findViewById(R.id.md_button_close);


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
        if (mKillerManagerAction.isEnableDontShowAgain()) {
            mDoNotShowAgainCheckBox.setVisibility(View.VISIBLE);
            mDoNotShowAgainCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    KillerManagerUtils.setDontShowAgain(getActivity(), mActionType, isChecked);
                }
            });
        } else {
            mDoNotShowAgainCheckBox.setVisibility(View.GONE);
        }
       /* //TODO CUSTOM MESSAGE FOR SPECIFITQUE ACTIONS AND SPECIFIC DEVICE
        mContentMessage.setText(String.format(getString(R.string.dialog_huawei_notification),
                                              getString(R.string.app_name)));*/

        // Inflate the layout for this fragment

        mHelpImageViewPager = mFragmentView.findViewById(R.id.md_help_image_viewpager);
        mHelpImageViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mHelpImageList.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup view, int position) {
                View myImageLayout = mInfalter.inflate(R.layout.md_dialog_main_content_image_item, view, false);
                ImageView myImage = myImageLayout.findViewById(R.id.md_help_image);
                myImage.setImageResource(mHelpImageList.get(position));
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
                if (mCurrentPage == mHelpImageList.size()) {
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
        mKillerManager.doAction(getActivity(), mActionType);
    }

    @Override
    public void onClicClose() {
        //TODO
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        mKillerManager.onActivityResult(getActivity(), mActionType, requestCode);
    }
}