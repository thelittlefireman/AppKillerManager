package com.thelittlefireman.appkillermanager.deviceUi.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thelittlefireman.appkillermanager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SettingPageFragment extends Fragment {
    //https://guides.codepath.com/android/viewpager-with-fragmentpageradapter

    List<Integer> mHelpImageList;
    private ViewPager mHelpImageViewPager;

    private int mCurrentPage;

    private static final String HELP_TEXT ="HELP_TEXT";
    private static final String HELP_IMAGE="HELP_IMAGE";


    private String mContentMessage;

    private Intent mIntentAction;
    protected LayoutInflater mInfalter;

    public void setIntentAction(Intent intentAction) {
        mIntentAction = intentAction;
    }

    @DrawableRes
    private int mHelpImage;

    public static SettingPageFragment newInstance(String helpText, @DrawableRes int helpImage) {
        SettingPageFragment fragmentFirst = new SettingPageFragment();
        Bundle args = new Bundle();
        args.putString(HELP_TEXT, helpText);
        args.putInt(HELP_IMAGE, helpImage);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentMessage = getArguments().getString(HELP_TEXT);
        mHelpImage = getArguments().getInt(HELP_IMAGE);
        mHelpImageList = new ArrayList<>();
        mHelpImageList.add(
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInfalter = inflater;
        View mFragmentView = inflater.inflate(R.layout.md_dialog_main_content, container, false);
        TextView textView = mFragmentView.findViewById(R.id.md_content_message);

        textView.setText(mContentMessage);
        //TODO CUSTOM MESSAGE FOR SPECIFITQUE ACTIONS AND SPECIFIC DEVICE
        mContentMessage.setText(String.format(getString(R.string.dialog_huawei_notification),
                                              getString(R.string.app_name)));

        // Inflate the layout for this fragment

        mHelpImageViewPager = mFragmentView.findViewById(R.id.md_dialog_samsung_pager_imageView);
        mHelpImageViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mHelpImageList.size();
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup view, int position) {
                View myImageLayout = mInfalter.inflate(R.layout.md_dialog_help_image_viewpager_content, view, false);
                ImageView myImage = myImageLayout.findViewById(R.id.md_help_image_imageview);
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
}